package org.rtss.mosad_backend.service.credit_management;

import org.rtss.mosad_backend.dto.credit_dtos.RepaymentRequestDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentResponseDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDetailsDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentDTO;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.RepaymentDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreditService {
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private CreditDTOMapper creditDTOMapper;

    @Autowired
    private RepaymentDTOMapper repaymentDTOMapper;

    public CreditDTO saveCredit(CreditDTO creditDTO) {
        return creditDTOMapper.toDTO(creditRepository.save(creditDTOMapper.toEntity(creditDTO)));
    }

    public List<CreditDTO> getAllCredits() {
        return creditDTOMapper.toDTOList(creditRepository.findAll());
    }

    //get credit details with customer name and contact number
    public List<CreditDetailsDTO> getAllCreditDetails() {
        List<Object[]> results = creditRepository.findAllCreditDetails();

        // Use a map to group repayments by creditId
        Map<Long, CreditDetailsDTO> creditDetailsMap = new HashMap<>();

        for (Object[] row : results) {
            Long creditId = (Long) row[0];
            double balance = (double) row[1];
            Date dueDate = (Date) row[2];
            String customerName = (String) row[3];
            String contactNumber = (String) row[4];

            // Repayment details
            Long repaymentId = (Long) row[5];
            Date repaymentDate = (Date) row[6];
            Double repaymentAmount = (Double) row[7];

            // Get or create CreditDetailsDTO
            CreditDetailsDTO creditDetails = creditDetailsMap.computeIfAbsent(creditId, id ->
                    new CreditDetailsDTO(creditId, customerName, contactNumber, balance, dueDate, new ArrayList<>())
            );

            // Track unique repayments using a Set of repayment IDs
            Set<Long> existingRepaymentIds = creditDetails.getRepayments().stream()
                    .map(RepaymentDTO::getRepaymentId)
                    .collect(Collectors.toSet());

            // Add repayment only if it's not already added
            if (repaymentId != null && !existingRepaymentIds.contains(repaymentId)) {
                creditDetails.getRepayments().add(new RepaymentDTO(repaymentId, repaymentDate, repaymentAmount));
            }
        }

        return new ArrayList<>(creditDetailsMap.values());
    }

    public ResponseEntity<RepaymentResponseDTO> addRepayment( RepaymentRequestDTO repaymentRequest) {
        Optional<Credit> creditOptional = creditRepository.findById(repaymentRequest.getCreditId());
        if (creditOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Credit credit = creditOptional.get();
        Repayment repayment = new Repayment();
        repayment.setDate(repaymentRequest.getDate());
        repayment.setAmount(repaymentRequest.getAmount());
        repayment.setCredit(credit);

        repayment = repaymentRepository.save(repayment);

        RepaymentResponseDTO responseDTO = new RepaymentResponseDTO(
                repayment.getRepaymentId(),
                repayment.getDate(),
                repayment.getAmount(),
                repayment.getCredit().getCreditId()
        );

        return ResponseEntity.ok(responseDTO);
    }

}

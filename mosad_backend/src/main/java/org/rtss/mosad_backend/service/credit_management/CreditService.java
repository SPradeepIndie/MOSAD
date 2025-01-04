package org.rtss.mosad_backend.service.credit_management;

import org.rtss.mosad_backend.dto.credit_dtos.*;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.RepaymentDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.exceptions.CreditException;
import org.rtss.mosad_backend.exceptions.RepaymentException;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    // Save credit
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        try {
            Credit credit = creditDTOMapper.toEntity(creditDTO);
            return creditDTOMapper.toDTO(creditRepository.save(credit));
        } catch (Exception ex) {
            throw new CreditException("Failed to save credit: " + ex.getMessage());
        }
    }

    // Get all credits
    public List<CreditDTO> getAllCredits() {
        try {
            return creditDTOMapper.toDTOList(creditRepository.findAll());
        } catch (Exception ex) {
            throw new CreditException("Failed to fetch credits: " + ex.getMessage());
        }
    }

    // Get credit details with customer name and contact number
    public List<CreditDetailsDTO> getAllCreditDetails() {
        try {
            List<Object[]> results = creditRepository.findAllCreditDetails();

            // Group repayments by creditId using a map
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

                // Add repayment if not already present
                if (repaymentId != null) {
                    boolean exists = creditDetails.getRepayments().stream()
                            .anyMatch(r -> r.getRepaymentId().equals(repaymentId));
                    if (!exists) {
                        creditDetails.getRepayments().add(new RepaymentDTO(repaymentId, repaymentDate, repaymentAmount));
                    }
                }
            }

            return new ArrayList<>(creditDetailsMap.values());
        } catch (Exception ex) {
            throw new CreditException("Failed to get credit details: " + ex.getMessage());
        }
    }

    // Add repayment
    public ResponseEntity<RepaymentResponseDTO> addRepayment(RepaymentRequestDTO repaymentRequest) {
        try {
            Credit credit = creditRepository.findById(repaymentRequest.getCreditId())
                    .orElseThrow(() -> new CreditException("Credit not found for ID: " + repaymentRequest.getCreditId()));

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
        } catch (CreditException ex) {
            throw ex; // Custom exception already defined
        } catch (Exception ex) {
            throw new RepaymentException("Failed to process repayment: " + ex.getMessage());
        }
    }
}

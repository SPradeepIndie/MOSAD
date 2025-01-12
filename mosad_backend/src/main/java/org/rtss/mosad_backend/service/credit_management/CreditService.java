package org.rtss.mosad_backend.service.credit_management;

import org.rtss.mosad_backend.dto.credit_dtos.*;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    private final RepaymentRepository repaymentRepository;

    private final CreditDTOMapper creditDTOMapper;

    private final CustomerRepository customerRepository;

    public CreditService(CreditRepository creditRepository, RepaymentRepository repaymentRepository, CreditDTOMapper creditDTOMapper, CustomerRepository customerRepository) {
        this.creditRepository = creditRepository;
        this.repaymentRepository = repaymentRepository;
        this.creditDTOMapper = creditDTOMapper;
        this.customerRepository = customerRepository;
    }

    // Save credit
    public ResponseEntity<CreditDTO> saveCredit(CreditDTO creditDTO) {

        if (creditDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID must not be null ");
        }

        // Fetch the Customer entity using customer_id from the DTO
        Customer customer = customerRepository.findById(creditDTO.getCustomerId())
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Customer not found"))));

        // Map the CreditDTO to a Credit entity
        Credit credit=creditDTOMapper.toEntity(creditDTO);
        credit.setCustomer(customer); // Associate the Customer entity

        // Save the Credit entity
        Credit savedCredit = creditRepository.save(credit);

        // Convert the saved Credit entity back to a DTO
        return new ResponseEntity<>(creditDTOMapper.toDTOWithCustomer(savedCredit), HttpStatus.CREATED);
    }


    // Get all credits
    public List<CreditDTO> getAllCredits() {
        try {
            return creditDTOMapper.toDTOList(creditRepository.findAll());
        } catch (Exception ex) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to fetch credits: " + ex.getMessage())));
        }
    }

    //Get credit by id
    public CreditDTO getCreditById(Long creditId) {
        try {
            Credit credit = creditRepository.findById(creditId)
                    .orElseThrow(() -> new RuntimeException("Credit not found for ID: " + creditId));
            return creditDTOMapper.toDTOWithCustomer(credit);
        } catch (Exception ex) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to fetch credit: " + ex.getMessage())));
        }
    }

    //Get


    // Get all credits with repayments
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

                //Bill details
                Long billId = (Long) row[8];


                // Get or create CreditDetailsDTO
                CreditDetailsDTO creditDetails = creditDetailsMap.computeIfAbsent(creditId, id ->
                        new CreditDetailsDTO(creditId, customerName, contactNumber, balance, dueDate, new ArrayList<>(),billId)
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
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to fetch credit details: " + ex.getMessage())));
        }
    }

    // Add repayment
    public ResponseEntity<RepaymentResponseDTO> addRepayment(RepaymentRequestDTO repaymentRequest) {
        try {
            Credit credit = creditRepository.findById(repaymentRequest.getCreditId())
                    .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Credit not found"))));

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
        } catch (Exception ex) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to add repayment: " + ex.getMessage())));
        }
    }

    public List<Credit> getCreditsBtDueDate(String date)  {
        Timestamp dueDate = Timestamp.valueOf(date);
        return creditRepository.findCreditByDueDate(dueDate);
    }


}

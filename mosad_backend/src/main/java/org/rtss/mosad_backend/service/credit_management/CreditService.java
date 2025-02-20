package org.rtss.mosad_backend.service.credit_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.credit_dtos.*;
import org.rtss.mosad_backend.dto_mapper.credit_dto_mapper.CreditDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.credit_repository.RepaymentRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    private final RepaymentRepository repaymentRepository;

    private final CreditDTOMapper creditDTOMapper;

    private final CustomerRepository customerRepository;

    private final BillRepository billRepository;

    public CreditService(CreditRepository creditRepository, RepaymentRepository repaymentRepository, CreditDTOMapper creditDTOMapper, CustomerRepository customerRepository, BillRepository billRepository) {
        this.creditRepository = creditRepository;
        this.repaymentRepository = repaymentRepository;
        this.creditDTOMapper = creditDTOMapper;
        this.customerRepository = customerRepository;
        this.billRepository = billRepository;
    }

    // Save credit
    public ResponseEntity<CreditDTO> saveCredit(AddCreditDTO creditDTO) {

        if (creditDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID must not be null ");
        }

        // Fetch the Customer entity using customer_id from the DTO
        Customer customer = customerRepository.findById(creditDTO.getCustomerId())
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Customer not found"))));

        Bill bill=billRepository.findById(creditDTO.getBillId())
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Bill not found"))));
        // Map the CreditDTO to a Credit entity
        Credit credit=new Credit();
        credit.setBalance(creditDTO.getBalance());
        credit.setDueDate(creditDTO.getDueDate());
        credit.setCustomer(customer); // Associate the Customer entity
        credit.setBill(bill);

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
    public List<CreditDetailsDTO> getAllCreditDetails(String customerType) {

        try {

            List<Object[]> results;
            if (customerType.equalsIgnoreCase("Retail")) {
                results = creditRepository.findAllRetailCustomerCreditDetails();
            } else {
                results = creditRepository.findAllNormalCustomerCreditDetails();
            }
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

    // Delete repayment by id
    public ResponseEntity<ResponseDTO> deleteRepaymentById(Long repaymentId) {
        try {
            Repayment repayment = repaymentRepository.findById(repaymentId)
                    .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Repayment not found"))));

            repaymentRepository.delete(repayment);

            ResponseDTO responseDTO = new ResponseDTO(true, "Repayment deleted successfully");

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception ex) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to delete repayment: " + ex.getMessage())));
        }
    }

    //Update repayment
    public ResponseEntity<RepaymentResponseDTO> updateRepayment(RepaymentResponseDTO repaymentUpdate) {
        try {
            Repayment repayment = repaymentRepository.findById(repaymentUpdate.getRepaymentId())
                    .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Repayment not found"))));

            repayment.setDate(repaymentUpdate.getDate());
            repayment.setAmount(repaymentUpdate.getAmount());

            repayment = repaymentRepository.save(repayment);

            RepaymentResponseDTO responseDTO = new RepaymentResponseDTO(
                    repayment.getRepaymentId(),
                    repayment.getDate(),
                    repayment.getAmount(),
                    repayment.getCredit().getCreditId()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception ex) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Failed to update repayment: " + ex.getMessage())));
        }
    }

    public List<Credit> getCreditsBtDueDate(String date)  {
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Invalid date format")));
        }
        return creditRepository.findCreditByDueDate(dueDate);
    }


}

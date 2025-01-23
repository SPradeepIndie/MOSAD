package org.rtss.mosad_backend.service.bill_management;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final CustomerService customerService;

    // Constructor injection for dependencies
    public BillService(BillRepository billRepository, CustomerService customerService) {
        this.billRepository = billRepository;
        this.customerService = customerService;
    }

    // Method to add a bill and update customer details
    public BillDTO addBill(Long customerId, String customerName, String contactNumber, BillDTO billDTO) {
        // Retrieve the customer by name and contact number
        Customer customer = customerService.getCustomerByNameAndContact(customerName, contactNumber);

        if (customer == null) {
            throw new RuntimeException("Customer not found with the provided details.");
        }

        // Map BillDTO to Bill entity
        Bill bill = new Bill();
        bill.setTotalAmount(billDTO.getTotalAmount());
        bill.setAdvance(billDTO.getAdvance());
        bill.setBalance(billDTO.getBalance());
        bill.setCustomer(customer);

        // Save the Bill
        Bill savedBill = billRepository.save(bill);

        // Map back to DTO
        BillDTO responseDTO = new BillDTO();
        responseDTO.setId(savedBill.getId());
        responseDTO.setTotalAmount(savedBill.getTotalAmount());
        responseDTO.setAdvance(savedBill.getAdvance());
        responseDTO.setBalance(savedBill.getBalance());

        return responseDTO;
    }

    // Method to get all bills
    public List<BillDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(bill -> {
            BillDTO dto = new BillDTO();
            dto.setId(bill.getId());
            dto.setTotalAmount(bill.getTotalAmount());
            dto.setAdvance(bill.getAdvance());
            dto.setBalance(bill.getBalance());
            return dto;
        }).toList();
    }
}

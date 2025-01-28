package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    private final CustomerService customerService;


    @Autowired
    private BillDTOMapper billDTOMapper;
    @Autowired
    private CustomerDTOMapper customerDTOMapper;
    @Autowired
    private CustomerRepository customerRepository;

    public BillService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public BillDTO createBill(BillDTO billDTO, CustomerDTO customerDTO) {
        // Convert CustomerDTO to Customer entity
        Customer customer = customerDTOMapper.toEntity(customerDTO);

        // Save the Customer entity
        Customer savedCustomer = customerRepository.save(customer);

        // Convert BillDTO to Bill entity
        Bill bill = billDTOMapper.toEntity(billDTO);

        // Set the relationship between Bill and Customer
        bill.setCustomer(savedCustomer);

        // Save the Bill entity
        Bill savedBill = billRepository.save(bill);

        // Convert the saved Bill entity back to DTO and return it
        return billDTOMapper.toDTO(savedBill);
    }


    public List<BillDTO> getAllBills() {
        return billRepository.findAll().stream()
                .map(billDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BillDTO getBillById(Long id) {
        return billRepository.findById(id)
                .map(billDTOMapper::toDTO)
                .orElse(null);
    }
}

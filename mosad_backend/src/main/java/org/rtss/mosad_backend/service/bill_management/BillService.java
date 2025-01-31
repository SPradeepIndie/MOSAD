package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.dto_mapper.BillItemDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
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
    @Autowired
    private CustomerContactDTOMapper customerContactDTOMapper;
    @Autowired
    private BillItemDTOMapper billItemDTOMapper;

    public BillService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public BillDTO createBill(BillDTO billDTO, CustomerDTO customerDTO, List<BillItemDTO> billItemDTO) {
        Bill bill = billDTOMapper.toEntity(billDTO);
        Customer customer = customerDTOMapper.toEntity(customerDTO);

        // Convert CustomerContact to CustomerContactDTO before passing
        CustomerContactDTO customerContactDTO = customerContactDTOMapper.customerContactToCustomerContactDTO(customer.getCustomerContact());

        customer = customerService.addCustomer(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerType(),
                customerContactDTO
        );

        bill.setCustomer(customer);

        // Convert BillItemDTOs to BillItem entities and set the bill reference
        List<BillItem> billItems = billItemDTO.stream()
                .map(billItemDTOMapper::toEntity) // Convert DTO to entity
                .peek(item -> item.setBill(bill)) // Set the bill reference in each item
                .collect(Collectors.toList());

        // Set the billItems to the bill
        bill.setBillItems(billItems);

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

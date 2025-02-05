package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.bill_dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.dto_mapper.bill_dto_mapper.BillItemDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
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
    @Autowired
    private BillItemService billItemService;
    @Autowired
    private BillItemRepository billItemRepository;

    public BillService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public BillDTO createBill(BillDTO billDTO, CustomerDTO customerDTO, List<BillItemDTO> billItemDTO) {

        Bill bill = billDTOMapper.toEntity(billDTO);
        customerDTO = customerService.addCustomer(customerDTO);
        Customer customer = customerDTOMapper.toEntity(customerDTO);
        bill.setCustomer(customer);

        List<BillItem> billItems = billItemDTO.stream()
                .map(dto -> {
                    BillItem billItem = billItemDTOMapper.toBillItemEntity(dto);
                    billItem.setBill(bill);
                    return billItem;
                })
                .collect(Collectors.toList());
        bill.setBillItems(billItems);

        Bill savedBill = billRepository.save(bill);

        // Save all BillItems
        billItemRepository.saveAll(billItems);

        // Convert the saved Bill entity back to DTO and return it
        return billDTOMapper.toDTO(savedBill);
    }


    public List<BillDTO> getAllBills() {
        return billRepository.findAll().stream()
                .map(billDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

}

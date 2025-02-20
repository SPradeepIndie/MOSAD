package org.rtss.mosad_backend.service.bill_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDetailsDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDetailsDTO;
import org.rtss.mosad_backend.dto_mapper.bill_dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.dto_mapper.bill_dto_mapper.BillItemDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {


    //Model mappers Injection
    private final BillDTOMapper billDTOMapper;
    private final CustomerDTOMapper customerDTOMapper;
    private final CustomerContactDTOMapper customerContactDTOMapper;
    private final BillItemDTOMapper billItemDTOMapper;

    //Services Injection
    private final CustomerService customerService;

    //Repository Injection
    private final BillRepository billRepository;


    public BillService(BillRepository billRepository, CustomerService customerService, BillDTOMapper billDTOMapper, CustomerDTOMapper customerDTOMapper, CustomerContactDTOMapper customerContactDTOMapper, BillItemDTOMapper billItemDTOMapper) {
        this.billRepository = billRepository;
        this.customerService = customerService;
        this.billDTOMapper = billDTOMapper;
        this.customerDTOMapper = customerDTOMapper;
        this.customerContactDTOMapper = customerContactDTOMapper;
        this.billItemDTOMapper = billItemDTOMapper;
    }


    public ResponseDTO createBill(BillDetailsDTO billDetailsDTO, CustomerDetailsDTO customerDetailsDTO, List<BillItemDTO> billItemDTO) {

        Bill bill = billDTOMapper.toEntity(billDetailsDTO.getBillDTO());
        Customer customer = customerService.extractCustomer(customerDetailsDTO);

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
//        billItemRepository.saveAll(billItems);
//
//        for (BillItem billItem : billItems) {
//            // Fetch the corresponding item from the database
//            Item item = itemRepository.findById(billItem.getItem().getId())
//                    .orElseThrow(() -> new RuntimeException("Item not found"));
//
//            // Reduce the item quantity
//            int updatedQuantity = item.getQuantity() - billItem.getQuantity();
//            if (updatedQuantity < 0) {
//                throw new RuntimeException("Insufficient stock for item: " + item.getName());
//            }
//            item.setQuantity(updatedQuantity);
//
//            // Save the updated item
//            itemRepository.save(item);
//        }

        // Convert the saved Bill entity back to DTO and return it

        return new ResponseDTO(true,"Bill saved successfuly");
    }


    public List<BillDetailsDTO> getAllBills() {
        List<Bill> bills=billRepository.findAll();
        if(bills.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no bills recorded in system");
        }
        return bills.stream().map(bill -> {
            BillDTO billDTO = billDTOMapper.toDTO(bill);
            CustomerDTO customerDTO = customerDTOMapper.toCustomerDTO(bill.getCustomer());
            CustomerContactDTO customerContactDTO = customerContactDTOMapper.customerContactToCustomerContactDTO(bill.getCustomer().getCustomerContact());
            CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO(customerDTO,customerContactDTO);

            List<BillItemDTO> billItems = bill.getBillItems().stream()
                    .map(billItemDTOMapper::toBillItemDTO)
                    .toList();

            return new BillDetailsDTO(billDTO, customerDetailsDTO, billItems);
        }).toList();

    }

}

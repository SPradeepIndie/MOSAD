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
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemBranchRepository;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemRepo;
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

    private final ItemBranchRepository itemBranchRepository;

    private final ItemRepo itemRepository;

    private final BillItemRepository billItemRepository;


    public BillService(BillRepository billRepository, CustomerService customerService, BillDTOMapper billDTOMapper, CustomerDTOMapper customerDTOMapper, CustomerContactDTOMapper customerContactDTOMapper, BillItemDTOMapper billItemDTOMapper, ItemBranchRepository itemBranchRepository, ItemRepo itemRepository, BillItemRepository billItemRepository) {
        this.billRepository = billRepository;
        this.customerService = customerService;
        this.billDTOMapper = billDTOMapper;
        this.customerDTOMapper = customerDTOMapper;
        this.customerContactDTOMapper = customerContactDTOMapper;
        this.billItemDTOMapper = billItemDTOMapper;
        this.itemBranchRepository = itemBranchRepository;
        this.itemRepository = itemRepository;
        this.billItemRepository = billItemRepository;
    }


    public ResponseDTO createBill(BillDetailsDTO billDetailsDTO, CustomerDetailsDTO customerDetailsDTO, List<BillItemDTO> billItemDTO) {



        for(BillItemDTO item : billItemDTO){
            System.out.println("\n"+item.getItemId()+"\n");
        }


        Bill bill = billDTOMapper.toEntity(billDetailsDTO.getBillDTO());
        Customer customer = customerService.extractCustomer(customerDetailsDTO);

        bill.setCustomer(customer);

        List<BillItem> billItems = billItemDTO.stream()
                .map(dto -> {
                    System.out.println("\n inside map"+dto.getItemId()+"\n");
                    //BillItem billItem = billItemDTOMapper.toBillItemEntity(dto);
                    BillItem billItem = new BillItem();
                    billItem.setBill(bill);
                    billItem.setDescription(dto.getDescription());
                    billItem.setQuantity(dto.getQuantity());
                    billItem.setUnitPrice(dto.getUnitPrice());
                    billItem.setItem(itemRepository.findById(dto.getItemId()).orElseThrow(() -> new RuntimeException("Item not found")));
                    System.out.println("\n inside map"+billItem.getItem().getItemId()+"\n");


                    return billItem;
                })
                .collect(Collectors.toList());

        bill.setBillItems(billItems);

        Bill savedBill = billRepository.save(bill);

         //Save all BillItems
        billItemRepository.saveAll(billItems);

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

    public ResponseDTO updateItemQuantity(Long itemId, Long branchId, Integer quantity) {
        // Find the ItemBranch entity by itemId and branchId
        ItemBranch itemBranch = itemBranchRepository.findByItemIdAndBranchId(itemId, branchId);

        if (itemBranch != null) {
            // Update the availableQuantity
            itemBranch.setAvailableQuantity(quantity);

            // Save the updated entity
            itemBranchRepository.save(itemBranch);

            return new ResponseDTO(true, "Stock updated");
        } else {
            return new ResponseDTO(false, "ItemBranch not found for the given itemId and branchId");
        }
    }


}

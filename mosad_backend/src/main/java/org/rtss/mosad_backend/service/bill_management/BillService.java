package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.bill_repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    private final ItemRepository itemRepository; // Assuming you have a repository for Item entity

    public BillService(BillRepository billRepository, BillItemRepository billItemRepository, ItemRepository itemRepository) {
        this.billRepository = billRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ResponseDTO addBill(BillDTO billDTO) {
        try {
            // Create a new Bill entity from BillDTO
            Bill bill = new Bill();
            bill.setCustomer(billDTO.getCustomerId());
            bill.setAdvance(billDTO.getAdvance());
            bill.setTotalAmount(billDTO.getTotalAmount());
            bill.setBalance(billDTO.getBalance());
            bill.setDate(billDTO.getDate());



            // Save the Bill (this will cascade and save the items if properly mapped in JPA)
            Bill savedBill = saveBill(bill);

            // Create a response DTO
            ResponseDTO response = new ResponseDTO();
            response.setSuccess(true);
            response.setMessage("Bill added successfully");
            response.setData(savedBill); // You can customize the response further if needed

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error while adding bill: " + e.getMessage(), e); // Rethrow exception

            // Handle exceptions and return an error response
            /*ResponseDTO response = new ResponseDTO();
            response.setSuccess(false);
            response.setMessage("Failed to add bill: " + e.getMessage());
            return response;*/
        }
    }


    @Transactional
    public Bill saveBill(Bill bill) {
        Bill savedBill = billRepository.save(bill);

        // Deduct stock from items after bill is created
        for (BillItem billItem : savedBill.getItems()) {
            Item item = billItem.getItem();
            item.setAvailableQuantity(item.getAvailableQuantity() - billItem.getQuantity());
            itemRepository.save(item);  // Update the item stock
        }

        return savedBill;
    }


    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // New method to handle the printing of the bill and updating item quantities
    public Bill printBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        // Update stock quantities based on the bill items
        for (BillItem billItem : bill.getItems()) {
            Item item = billItem.getItem();
            int newQuantity = item.getAvailableQuantity() - billItem.getQuantity();
            item.setAvailableQuantity(newQuantity); // Update the quantity
            itemRepository.save(item); // Persist the updated item
        }

        // Mark the bill as printed or update any other necessary status
        // (Optional: you can add a status field in Bill to track print status)

        return bill; // Return the updated bill
    }
}


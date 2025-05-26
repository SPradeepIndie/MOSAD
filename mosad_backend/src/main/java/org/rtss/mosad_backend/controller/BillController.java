package org.rtss.mosad_backend.controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.bill_dtos.*;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponeDTO> createBill(@RequestBody BillDetailsDTO billDetailsDTO) {
        return  ResponseEntity.ok(billService.createBill(billDetailsDTO, billDetailsDTO.getAddCustomerDTO(), billDetailsDTO.getBillItemDTO()));

    }

    @GetMapping
    public ResponseEntity<List<BillDetailsDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @PutMapping("/updatestock")
    public ResponseEntity<ResponseDTO> updateItemQuantity(@RequestParam Long itemId, @RequestParam Long branchId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(billService.updateItemQuantity(itemId, branchId, quantity));
    }

    @GetMapping("/getByBranch")
    public ResponseEntity<List<BillDetailsDTO>> getByBranch(@RequestParam Long branchId) {
        return ResponseEntity.ok(new ArrayList<BillDetailsDTO>());
    }

    @GetMapping("/getnormal")
    public ResponseEntity<List<BillNormalCustomerDTO>> getCustomerByContact(@RequestParam String contactNumber){
        List<BillNormalCustomerDTO> customerDTOS=billService.getCustomersByContact(contactNumber);
        return ResponseEntity.ok(customerDTOS);


    }
    @GetMapping("/getretail")
    public ResponseEntity<List<BillRetailCustomerDTO>> getUsersByContact(@RequestParam String contactNumber){
        List<BillRetailCustomerDTO> userDTOS=billService.getUsersByContact(contactNumber);
        return ResponseEntity.ok(userDTOS);
    }

}

package org.rtss.mosad_backend.controller.bill_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDetailsDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createBill(@RequestBody BillDetailsDTO billDetailsDTO) {
        return  ResponseEntity.ok(billService.createBill(billDetailsDTO, billDetailsDTO.getAddCustomerDTO(), billDetailsDTO.getBillItemDTO()));

    }

    @GetMapping
    public ResponseEntity<List<BillDetailsDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }
}

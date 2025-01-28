package org.rtss.mosad_backend.controller.bill_management;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<BillDTO> createBill(@RequestBody BillDTO billDTO , CustomerDTO customerDTO) {
        BillDTO createdBill = billService.createBill(billDTO , customerDTO);
        return ResponseEntity.ok(createdBill);
    }

    @GetMapping
    public ResponseEntity<List<BillDTO>> getAllBills() {
        List<BillDTO> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> getBillById(@PathVariable Long id) {
        BillDTO bill = billService.getBillById(id);
        if (bill != null) {
            return ResponseEntity.ok(bill);
        }
        return ResponseEntity.notFound().build();
    }
}

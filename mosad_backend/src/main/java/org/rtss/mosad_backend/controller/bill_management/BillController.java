package org.rtss.mosad_backend.controller.bill_management;

import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Bill savedBill = billService.saveBill(bill);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billService.getBillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/
}
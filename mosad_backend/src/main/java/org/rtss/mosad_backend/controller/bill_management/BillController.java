package org.rtss.mosad_backend.controller.bill_management;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.service.bill_management.BillService;
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
    public BillDTO addBill(@RequestParam Long customerId,
                           @RequestParam String customerName,
                           @RequestParam String contactNumber,
                           @RequestBody BillDTO billDTO) {
        // Add the bill and update customer details
        return billService.addBill(customerId, customerName, contactNumber, billDTO);
    }

    @GetMapping
    public List<BillDTO> getAllBills() {
        // Get all bills
        return billService.getAllBills();
    }
}

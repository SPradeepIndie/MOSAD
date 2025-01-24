package org.rtss.mosad_backend.controller.bill_management;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.service.bill_management.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    public BillDTO createBill(@RequestBody BillDTO billDTO) {
        // Create the Bill entity
        Bill bill = BillDTOMapper.toEntity(billDTO);

        // Set the current date
        bill.setDate(LocalDate.now());

        // Calculate balance
        bill.setBalance(bill.getTotalAmount() - bill.getAdvance());

        // Save the bill
        Bill savedBill = billService.saveBill(bill);

        // Return the saved bill as DTO
        return BillDTOMapper.toDTO(savedBill);
    }

    @GetMapping("/{billId}")
    public BillDTO getBill(@PathVariable Long billId) {
        Bill bill = billService.findBillById(billId);
        if (bill != null) {
            return BillDTOMapper.toDTO(bill);
        }
        return null;
    }
}

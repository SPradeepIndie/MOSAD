package org.rtss.mosad_backend.controller.credit_management_controller;

import org.rtss.mosad_backend.dto.credit_dtos.RepaymentRequestDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentResponseDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDetailsDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.service.credit_management.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;


    //get all credits
    @GetMapping("/all")
    public List<CreditDTO> getAllCredits() {
        return creditService.getAllCredits();
    }

    //all credits with repayments
    @GetMapping("/all-credit-details")
    public List<CreditDetailsDTO> getAllCreditDetails() {
        return creditService.getAllCreditDetails();
    }

    //get credit by id
    @GetMapping("/{creditId}")
    public CreditDTO getCreditById(@PathVariable Long creditId) {
        return creditService.getCreditById(creditId);
    }

    @PostMapping("/add-credit")
    public ResponseEntity<CreditDTO> addCredit(@RequestBody CreditDTO creditDTO) {
        return ResponseEntity.ok(creditService.saveCredit(creditDTO)).getBody();
    }


    @PostMapping("/add-repayment")
    public ResponseEntity<RepaymentResponseDTO> addRepayment(@RequestBody RepaymentRequestDTO repaymentRequest) {

        return ResponseEntity.ok(creditService.addRepayment(repaymentRequest).getBody());
    }

    @GetMapping("/get-credits-by-due-date")
    public List<Credit> getCreditsBtDueDate(@RequestParam String date) {

            return creditService.getCreditsBtDueDate(date);

    }
}

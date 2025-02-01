package org.rtss.mosad_backend.controller.credit_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentRequestDTO;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentResponseDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDetailsDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.service.credit_management.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {


    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }


    //get all credits
    @GetMapping("/all")
    public List<CreditDTO> getAllCredits() {
        return creditService.getAllCredits();
    }

    //all credits with repayments
    @GetMapping("/all-credit-details/{customerType}")
    public List<CreditDetailsDTO> getAllCreditDetails(@PathVariable String customerType) {
        return creditService.getAllCreditDetails(customerType);
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

    @DeleteMapping("/delete-repayment")
    public ResponseEntity<ResponseDTO> deleteRepayment(@Param("repaymentId") Long repaymentId) {
        System.out.println("\n\n"+repaymentId+"\n\n");
        return ResponseEntity.ok(creditService.deleteRepaymentById(repaymentId).getBody());
    }

    @GetMapping("/get-credits-by-due-date")
    public List<Credit> getCreditsBtDueDate(@RequestParam String date) {
            return creditService.getCreditsBtDueDate(date);
    }
}

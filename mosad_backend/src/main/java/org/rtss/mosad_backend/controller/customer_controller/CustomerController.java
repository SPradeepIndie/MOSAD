package org.rtss.mosad_backend.controller.customer_controller;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Other existing methods

    @PutMapping("/{id}/update-details")
    public CustomerDTO updateCustomerDetails(@PathVariable Long id, @RequestParam String customerName, @RequestParam String contactNumber) {
        // Update customer and contact details
        return customerService.updateCustomerDetails(id, customerName, contactNumber);
    }
}

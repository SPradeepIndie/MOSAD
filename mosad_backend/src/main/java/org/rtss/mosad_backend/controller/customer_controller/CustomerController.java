/*package org.rtss.mosad_backend.controller.customer_controller;

import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestParam String name, @RequestParam List<String> contactNumbers) {
        return customerService.saveCustomer(name, contactNumbers);
    }
}*/

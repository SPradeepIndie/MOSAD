package org.rtss.mosad_backend.controller.customer_controller;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public Customer updateCustomer(
            @RequestParam Long customerId,
            @RequestParam String customerName,
            @RequestParam String customerType,
            @RequestParam CustomerContactDTO customerContactDTO) {


        return customerService.addCustomer(customerId, customerName,customerType ,customerContactDTO);
    }

}

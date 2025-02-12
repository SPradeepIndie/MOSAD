package org.rtss.mosad_backend.controller.customer_controller;


import org.rtss.mosad_backend.dto.customer_dtos.CustomerDetailsDTO;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.service.customer_management.CustomerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO>  updateCustomer(@RequestBody CustomerDetailsDTO customerDetailsDTO) {
        CustomerDTO customerDto = customerService.addCustomer(customerDetailsDTO);

        return ResponseEntity.ok(customerDto);

    }

    @GetMapping("/get")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDTOs);
    }


}

package org.rtss.mosad_backend.controller.customer_controller;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.service.customer_management.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        // Save the customer and return the saved DTO
        return customerService.saveCustomer(customerDTO);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        // Fetch all customers and return as DTO list
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        // Fetch a specific customer by ID
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        // Update the customer and return the updated DTO
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        // Delete the customer by ID
        customerService.deleteCustomer(id);
    }

    @GetMapping("/search/by-contact")
    public List<CustomerContactDTO> getCustomerByContact(@RequestParam String contactNumber) {
        // Fetch all customers with contacts and return as DTO list
        return customerService.getCustomerByContact(contactNumber);
    }

    @GetMapping("/search")
    public List<CustomerDTO> getCustomersByContact(@RequestParam String contactNumber) {
        // Fetch all customers with contacts and return as DTO list
        return customerService.getCustomersByContact(contactNumber);
    }


}

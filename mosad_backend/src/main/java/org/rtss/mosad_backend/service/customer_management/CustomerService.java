package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.repository.customer_repository.CustomerContactRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerContactRepository customerContactRepository;

    @Autowired
    private CustomerDTOMapper customerDTOMapper;

    @Autowired
    private CustomerContactDTOMapper customerContactDTOMapper;

    // Method to update customer details and contact information
    public CustomerDTO updateCustomerDetails(Long customerId, String customerName, String contactNumber) {
        // Find the customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Update the customer name
        customer.setName(customerName);

        // Check if the contact number exists or needs to be added
        CustomerContact customerContact = customer.getContacts().stream()
                .filter(contact -> contact.getContactNumber().equals(contactNumber))
                .findFirst()
                .orElse(null);

        if (customerContact == null) {
            // If contact does not exist, create a new one
            customerContact = new CustomerContact();
            customerContact.setCustomer(customer);
            customerContact.setContactNumber(contactNumber);
            customer.getContacts().add(customerContact);
        } else {
            // If contact exists, update the contact number (optional if you want to change)
            customerContact.setContactNumber(contactNumber);
        }

        // Save the updated customer and contact
        customerRepository.save(customer);
        return customerDTOMapper.toCustomerDTO(customer);
    }

    public Customer getCustomerByNameAndContact(String name, String contactNumber) {
        return customerRepository.findByNameAndContact(name, contactNumber);

    }
}

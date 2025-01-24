package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(String name, List<String> contactNumbers) {
        Customer customer = new Customer(name);
        for (String contact : contactNumbers) {
            customer.getContacts().add(new CustomerContact(contact, customer));
        }
        return customerRepository.save(customer);
    }
}

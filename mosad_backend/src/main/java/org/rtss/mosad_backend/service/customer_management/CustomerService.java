package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.repository.customer_repository.CustomerContactRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // Save a new customer
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDTOMapper.toCustomerEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerDTOMapper.toCustomerDTO(savedCustomer);
    }

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        return customerDTOMapper.toCustomerDTOList(customerRepository.findAll());
    }

    // Get a specific customer by ID
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return customerDTOMapper.toCustomerDTO(customer);
    }

    // Update an existing customer
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        // Update the fields of the existing customer
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setContacts(customerDTOMapper.toCustomerEntity(customerDTO).getContacts());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerDTOMapper.toCustomerDTO(updatedCustomer);
    }

    // Delete a customer by ID
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    public List<CustomerContactDTO> getCustomerByContact(String contactNumber) {
        System.out.println("\n Service ekata Awa  "+contactNumber);
        List<CustomerContact> customers= customerContactRepository.findCustomerContactsByContactNumber(contactNumber);
        System.out.println("\n Customers are \n"+customers+"\n\n");
        return customerContactDTOMapper.toCustomerDTOList(customers);
    }
    public List<CustomerDTO> getCustomersByContact(String contactNumber){

        List<Customer> customers=customerContactRepository.findCustomersByContactNumber(contactNumber);

        return customerDTOMapper.toCustomerDTOList(customers);
    }
}

package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.entity.customer.CustomerType;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.customer_repository.CustomerContactRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerContactRepository customerContactRepository;
    private final CustomerDTOMapper customerDTOMapper;
    private final CustomerContactDTOMapper customerContactDTOMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerContactRepository customerContactRepository, CustomerDTOMapper customerDTOMapper, CustomerContactDTOMapper customerContactDTOMapper) {
        this.customerRepository = customerRepository;
        this.customerContactRepository = customerContactRepository;
        this.customerDTOMapper = customerDTOMapper;
        this.customerContactDTOMapper = customerContactDTOMapper;
    }

    // Save a new customer
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getContacts() == null || customerDTO.getContacts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A customer must have at least one contact.");
        }
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
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Customer not found with ID: " + id))));
        return customerDTOMapper.toCustomerDTO(customer);
    }

    // Update an existing customer
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Customer not found with ID : " + id))));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setCustomerType(CustomerType.valueOf(customerDTO.getCustomerType()));
        existingCustomer.setContacts(customerDTOMapper.toCustomerEntity(customerDTO).getContacts());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerDTOMapper.toCustomerDTO(updatedCustomer);
    }


    // Delete a customer by ID
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST," Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    public List<CustomerContactDTO> getCustomerByContact(String contactNumber) {

        List<CustomerContact> customers= customerContactRepository.findCustomerContactsByContactNumber(contactNumber);

        return customerContactDTOMapper.toCustomerDTOList(customers);
    }
    public List<CustomerDTO> getCustomersByContact(String contactNumber){

        List<Customer> customers=customerContactRepository.findCustomersByContactNumber(contactNumber);

        return customerDTOMapper.toCustomerDTOList(customers);
    }
}

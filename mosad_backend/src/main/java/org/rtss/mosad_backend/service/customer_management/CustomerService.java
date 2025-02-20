package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDetailsDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.customer_repository.CustomerContactRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList= customerRepository.findAll();
        if(customerList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No customers found");
        }
        return customerList.stream().map(customerDTOMapper::toCustomerDTO).toList();
    }

    // Get a specific customer by ID
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotValidException(new HashSet<>(List.of("Customer not found with ID: " + id))));
        return customerDTOMapper.toCustomerDTO(customer);
    }

    @Transactional
    public CustomerDTO addCustomer(CustomerDetailsDTO customerDetailsDTO) {
        Customer customer = extractCustomer(customerDetailsDTO);
        Customer savedCustomer =  customerRepository.saveAndFlush(customer);

        return customerDTOMapper.toCustomerDTO(savedCustomer);
    }

    public Customer extractCustomer(CustomerDetailsDTO customerDetailsDTO) {
        Customer customer = customerDTOMapper.toCustomerEntity(customerDetailsDTO.getCustomerDTO());
        CustomerContact customerContact = customerContactDTOMapper.customerContactDTOToCustomerContact(customerDetailsDTO.getCustomerContactDTO());
        customerContact.setCustomer(customer);
        customer.setCustomerContact(customerContact);
        return customer;
    }

    // Delete a customer by ID
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST," Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    public List<CustomerDTO> getCustomersByContact(String contactNumber) {
        List<Customer> customers = customerContactRepository.findCustomersByContactNumber(contactNumber);
        return customers.stream().map(customerDTOMapper::toCustomerDTO).toList();
    }
}

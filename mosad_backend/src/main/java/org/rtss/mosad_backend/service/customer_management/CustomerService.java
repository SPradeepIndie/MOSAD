package org.rtss.mosad_backend.service.customer_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.rtss.mosad_backend.repository.customer_repository.CustomerContactRepository;
import org.rtss.mosad_backend.repository.customer_repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.rtss.mosad_backend.dto_mapper.customer_dto_mapper.CustomerContactDTOMapper;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerContactRepository customerContactRepository;

    @Autowired
    private CustomerContactDTOMapper customerContactDTOMapper;




    @Transactional
    public Customer addCustomer(Long customerId, String customerName, String customerType, CustomerContactDTO customerContactDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setCustomerType(customerType);
        customer.setCustomerContact(customerContactDTOMapper.customerContactDTOToCustomerContact(customerContactDTO));
        return customerRepository.save(customer);
    }
}

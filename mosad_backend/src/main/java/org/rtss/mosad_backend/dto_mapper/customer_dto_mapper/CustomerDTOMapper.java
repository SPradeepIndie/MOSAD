package org.rtss.mosad_backend.dto_mapper.customer_dto_mapper;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDTOMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerType(customer.getCustomerType());

        // Map CustomerContact to CustomerContactDTO
        if (customer.getCustomerContact() != null) {
            CustomerContactDTO contactDTO = new CustomerContactDTO();
            contactDTO.setContactNumber(customer.getCustomerContact().getContactNumber());
            customerDTO.setCustomerContactDTO(contactDTO);
        }



        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerType(customerDTO.getCustomerType());

        // Map CustomerContactDTO to CustomerContact
        if (customerDTO.getCustomerContactDTO() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setContactNumber(customerDTO.getCustomerContactDTO().getContactNumber());
            customer.setCustomerContact(customerContact);
        }



        return customer;
    }
}

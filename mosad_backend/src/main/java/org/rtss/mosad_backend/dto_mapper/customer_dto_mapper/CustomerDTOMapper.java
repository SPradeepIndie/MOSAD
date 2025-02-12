package org.rtss.mosad_backend.dto_mapper.customer_dto_mapper;


import org.modelmapper.ModelMapper;

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
            contactDTO.setCustomerContactId(customer.getCustomerContact().getCustomerContactId());
            contactDTO.setContactNumber(customer.getCustomerContact().getContactNumber());
            customerDTO.setCustomerContactDTO(contactDTO);
        }



        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }


        if (customer.getCustomerContact() != null) {
            customer.getCustomerContact().setCustomer(customer);

        }



        return customer;
    }
}

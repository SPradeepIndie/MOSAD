package org.rtss.mosad_backend.dto_mapper.customer_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerContactDTOMapper {

    private final ModelMapper modelMapper;

    public CustomerContactDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerContactDTO customerContactToCustomerContactDTO(CustomerContact customerContact) {
        return modelMapper.map(customerContact, CustomerContactDTO.class);
    }

    public CustomerContact customerContactDTOToCustomerContact(CustomerContactDTO customerContactDTO) {
        return modelMapper.map(customerContactDTO, CustomerContact.class);
    }

    public List<CustomerContactDTO> toCustomerDTOList(List<CustomerContact> customers) {
        return customers.stream()
                .map(this::customerContactToCustomerContactDTO)
                .collect(Collectors.toList());
    }
}
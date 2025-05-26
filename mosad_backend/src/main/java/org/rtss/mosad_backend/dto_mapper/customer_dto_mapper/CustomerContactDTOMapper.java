package org.rtss.mosad_backend.dto_mapper.customer_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerContactDTO;
import org.rtss.mosad_backend.entity.customer.CustomerContact;
import org.springframework.stereotype.Component;

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
}
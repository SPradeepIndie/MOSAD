package org.rtss.mosad_backend.dto_mapper.customer_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDTOMapper {

    private final ModelMapper modelMapper;

    public CustomerDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Map Customer Entity to DTO
    public CustomerDTO toCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    // Map Customer DTO to Entity
    public Customer toCustomerEntity(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);

        if (customer.getCustomerContact() != null) {
            customer.getCustomerContact().setCustomer(customer);
        }

        return customer;
    }

    // Map List of Customer Entities to DTOs
    public List<CustomerDTO> toCustomerDTOList(List<Customer> customers) {
        return customers.stream()
                .map(this::toCustomerDTO)
                .collect(Collectors.toList());
    }
}

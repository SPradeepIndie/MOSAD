package org.rtss.mosad_backend.dto_mapper.bill_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;

import org.springframework.stereotype.Component;

@Component
public class BillDTOMapper {

    private final ModelMapper modelMapper;

    public BillDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BillDTO toDTO(Bill bill) {
        return modelMapper.map(bill, BillDTO.class);
    }


    public Bill toEntity(BillDTO dto) {
        return modelMapper.map(dto, Bill.class);
    }
}

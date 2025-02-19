package org.rtss.mosad_backend.dto_mapper.credit_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreditDTOMapper {
    private final ModelMapper modelMapper;

    public CreditDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CreditDTO toDTO(Credit credit){
        return modelMapper.map(credit,CreditDTO.class);
    }

    public Credit toEntity(CreditDTO creditDTO){
        return modelMapper.map(creditDTO,Credit.class);
    }

    public List<CreditDTO> toDTOList(List<Credit> credits){
        List<CreditDTO> creditDTOS=new ArrayList<>();
        for(Credit credit:credits){
            creditDTOS.add(toDTO(credit));
        }
        return creditDTOS;

    }

    public CreditDTO toDTOWithCustomer(Credit credit){
        return new CreditDTO(credit.getCreditId(),credit.getBalance(),credit.getDueDate(),credit.getCustomer().getCustomerId());
    }
}

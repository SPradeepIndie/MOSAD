package org.rtss.mosad_backend.dto_mapper.credit_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.credit_dtos.RepaymentDTO;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.springframework.stereotype.Component;

@Component
public class RepaymentDTOMapper {
    private final ModelMapper modelMapper;

    public RepaymentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RepaymentDTO toDTO(Repayment repayment) {
        return modelMapper.map(repayment, RepaymentDTO.class);
    }

    public Repayment toEntity(RepaymentDTO repaymentDTO) {
        return modelMapper.map(repaymentDTO, Repayment.class);
    }

}

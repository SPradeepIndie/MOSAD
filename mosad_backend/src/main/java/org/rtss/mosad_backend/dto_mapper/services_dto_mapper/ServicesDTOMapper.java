package org.rtss.mosad_backend.dto_mapper.services_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.services_dtos.ServicesDTO;
import org.rtss.mosad_backend.entity.services_section.servicesSection;
import org.springframework.stereotype.Component;

@Component
public class ServicesDTOMapper {

    private final ModelMapper modelMapper;

    public ServicesDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ServicesDTO toDTO(servicesSection services) {
        return modelMapper.map(services, ServicesDTO.class);
    }

    public servicesSection toEntity(ServicesDTO dto) {
        return modelMapper.map(dto, servicesSection.class);
    }

}

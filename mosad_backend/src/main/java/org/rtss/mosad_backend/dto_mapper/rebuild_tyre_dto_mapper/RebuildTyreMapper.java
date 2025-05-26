package org.rtss.mosad_backend.dto_mapper.rebuild_tyre_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.rebuild_tyre_dtos.RebuildTyreDto;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;
import org.springframework.stereotype.Component;

@Component
public class RebuildTyreMapper {

   private final ModelMapper modelMapper;

    public RebuildTyreMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RebuildTyre toEntity(RebuildTyreDto dto) {

        return modelMapper.map(dto,RebuildTyre.class);
    }

    public  RebuildTyreDto toDto(RebuildTyre tyre) {

        return modelMapper.map(tyre,RebuildTyreDto.class);
    }
}

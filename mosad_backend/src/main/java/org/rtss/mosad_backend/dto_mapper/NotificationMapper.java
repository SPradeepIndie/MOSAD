package org.rtss.mosad_backend.dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.NotificationDTO;
import org.rtss.mosad_backend.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Notification toEntity(NotificationDTO dto) {
        return modelMapper.map(dto, Notification.class);
    }

    public NotificationDTO toDTO(Notification entity) {
        return modelMapper.map(entity, NotificationDTO.class);
    }
}

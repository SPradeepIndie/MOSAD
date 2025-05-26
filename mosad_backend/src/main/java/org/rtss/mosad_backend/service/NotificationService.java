package org.rtss.mosad_backend.service;

import org.rtss.mosad_backend.dto.NotificationDTO;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.entity.Notification;
import org.rtss.mosad_backend.dto_mapper.NotificationMapper;
import org.rtss.mosad_backend.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    public ResponseDTO addNotification(NotificationDTO dto) {
        Notification entity = notificationMapper.toEntity(dto);
        Notification saved = notificationRepository.save(entity);
        System.out.println("saved notification: " + saved);
        //return notificationMapper.toDTO(saved);
        return new ResponseDTO(true, "Notification added successfully");
    }


    public List<NotificationDTO> getNotificationsByType(String type) {
        List<Notification> notifications = notificationRepository.findByType(type);
        List<NotificationDTO> list = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO dto = notificationMapper.toDTO(notification);
            list.add(dto);
        }
        return list;
    }



    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}

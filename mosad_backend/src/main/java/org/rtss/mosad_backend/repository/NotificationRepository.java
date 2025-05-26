package org.rtss.mosad_backend.repository;

import org.rtss.mosad_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByType(String type);
}

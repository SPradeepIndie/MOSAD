package org.rtss.mosad_backend.controller;

import org.rtss.mosad_backend.dto.NotificationDTO;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.service.NotificationService;
import org.rtss.mosad_backend.service.mail_service.WhatsAppNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final WhatsAppNotificationService wanotificationService;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(WhatsAppNotificationService notificationService, NotificationService notificationService1) {
        this.wanotificationService = notificationService;
        this.notificationService = notificationService1;
    }

    @PostMapping("/hello")
    public String sendHelloWorldTemplate(@RequestParam String to) {
        wanotificationService.sendHelloWorldTemplate(to);
        return "Success";
    }
    @PostMapping("/credit")
    public String sendCreditReminderTemplate(@RequestParam String to, @RequestParam String name,@RequestParam String amount,@RequestParam String dueDate) {
        wanotificationService.sendCreditReminder(to, name, amount, dueDate);
        return "Success";
    }
    @GetMapping("/{type}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(@PathVariable String type) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok().body(notificationService.addNotification(notificationDTO));
    }


}

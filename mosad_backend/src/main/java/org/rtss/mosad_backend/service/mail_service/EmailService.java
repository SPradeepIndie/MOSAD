package org.rtss.mosad_backend.service.mail_service;

import org.rtss.mosad_backend.config.JavaMailSender.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("hakmanaedm@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.body());

        mailSender.javaMailSender().send(message);

    }
}

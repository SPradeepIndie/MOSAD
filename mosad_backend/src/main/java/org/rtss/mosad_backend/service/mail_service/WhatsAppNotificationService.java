package org.rtss.mosad_backend.service.mail_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppNotificationService {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.phone.id}")
    private String phoneId;

    @Value("${whatsapp.access.token}")
    private String accessToken;

    private static final String CREDIT_MESSAGE_TEMPLATE = """
    {
      "messaging_product": "whatsapp",
      "to": "%s",
      "type": "template",
      "template": {
        "name": "credit_due",
        "language": { "code": "en_US" },
        "components": [
          {
            "type": "body",
            "parameters": [
              { "type": "text", "text": "%s" },
              { "type": "text", "text": "%s" },
              { "type": "text", "text": "%s" }
            ]
          }
        ]
      }
    }
    """;

    public void sendCreditReminder(String to, String name, String amount, String dueDate) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + phoneId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        String requestBody = CREDIT_MESSAGE_TEMPLATE.formatted(to, name, amount, dueDate);


        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response.getBody());

    }

}
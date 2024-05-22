package com.example.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationPush extends TextWebSocketHandler {

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the new session to the session map
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the session map
        sessions.remove(session.getId());
    }

    public void sendEmailNotification(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("sender@example.com")); // 발신자 설정
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPushNotification(String title, String body, Map<String, String> data) {
        String payload = createPayload(title, body, data);
        for (WebSocketSession session : sessions.values()) {
            try {
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createPayload(String title, String body, Map<String, String> data) {
        // Create a JSON-like payload (for simplicity, not using a JSON library here)
        StringBuilder payload = new StringBuilder();
        payload.append("{")
                .append("\"title\":\"").append(title).append("\",")
                .append("\"body\":\"").append(body).append("\",");

        if (data != null && !data.isEmpty()) {
            payload.append("\"data\":{");
            data.forEach((key, value) -> payload.append("\"").append(key).append("\":\"").append(value).append("\","));
            // Remove the trailing comma
            payload.setLength(payload.length() - 1);
            payload.append("}");
        }
        payload.append("}");

        return payload.toString();
    }
}

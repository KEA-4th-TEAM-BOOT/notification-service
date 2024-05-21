package com.example.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class NotificationPush {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailNotification(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("sender@example.com")); // 발신자 설정
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPushNotification(String title, String body, Map<String, String> data) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setToken("YOUR_DEVICE_TOKEN") // 디바이스 토큰 설정
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Push notification response: " + response);
    }
}
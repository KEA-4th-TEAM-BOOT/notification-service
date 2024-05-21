package com.example.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "notifications.queue")
    public void handleNotification(String message) {
        System.out.println("Received notification: " + message);

        // 실제 알림 처리 로직
        String[] parts = message.split(":", 2);
        String notificationType = parts[0];
        String data = parts.length > 1 ? parts[1].trim() : "";

        switch (notificationType) {
            case "blog.post.created":
                handleBlogPostCreatedNotification(data);
                break;
            case "comment.added":
                handleCommentAddedNotification(data);
                break;
            case "user.followed":
                handleUserFollowedNotification(data);
                break;
            // 추가적인 알림 유형 처리
            default:
                System.out.println("Unknown notification type: " + notificationType);
                break;
        }
    }

    private void handleBlogPostCreatedNotification(String data) {
        // 블로그 게시글 작성 알림 처리 로직
        System.out.println("게시글이 정상적으로 업로드 되었습니다: " + data);
        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", data);
    }

    private void handleCommentAddedNotification(String data) {
        // 댓글 추가 알림 처리 로직
        System.out.println(data + "다른 유저가 댓글을 남겼습니다.: ");
        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", data);
    }

    private void handleUserFollowedNotification(String data) {
        // 사용자 팔로우 알림 처리 로직
        System.out.println("새로운 유저가 팔로우하였습니다.: " + data);
        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", data);
    }
}
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
        String[] dataParts = data.split(":");
        String userId = dataParts[0];
        String postId = dataParts[1];

        String notificationMessage = "User " + userId + " created a new blog post with ID " + postId;
        System.out.println(notificationMessage);

        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }

    private void handleCommentAddedNotification(String data) {
        // 댓글 추가 알림 처리 로직
        String[] dataParts = data.split(":");
        String userId = dataParts[0];
        String postId = dataParts[1];
        String commentId = dataParts[2];

        String notificationMessage = "User " + userId + " added a new comment with ID " + commentId + " to post " + postId;
        System.out.println(notificationMessage);

        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }

    private void handleUserFollowedNotification(String data) {
        // 사용자 팔로우 알림 처리 로직
        String[] dataParts = data.split(":");
        String followerId = dataParts[0];
        String followedId = dataParts[1];

        String notificationMessage = "User " + followerId + " followed user " + followedId;
        System.out.println(notificationMessage);

        // 웹 클라이언트로 알림 전송
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }
}
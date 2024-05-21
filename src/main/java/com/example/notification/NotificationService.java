package com.example.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private NotificationPush notificationPush;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String message, String routingKey) {
        rabbitTemplate.convertAndSend("notifications", routingKey, message);
    }

    public void sendBlogPostCreatedNotification(String userId, String postId) {
        String message = "blog.post.created:" + userId + ":" + postId;
        sendNotification(message, "notifications.blog.queue");
        // 이메일 알림 전송
        notificationPush.sendEmailNotification("user@example.com", "새 블로그 게시물", "새로운 블로그 게시물이 업로드되었습니다.");
    }

    public void sendCommentAddedNotification(String userId, String postId, String commentId) {
        String message = "comment.added:" + userId + ":" + postId + ":" + commentId;
        sendNotification(message, "notifications.comment.queue");
    }

    public void sendUserFollowedNotification(String followerId, String followedId) throws ExecutionException, FirebaseMessagingException, InterruptedException {
        String message = "user.followed:" + followerId + ":" + followedId;
        sendNotification(message, "notifications.follow.queue");
        // 푸시 알림 전송
        try {
            notificationPush.sendPushNotification("새로운 팔로워", followerId + "님이 당신을 팔로우했습니다.", null);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
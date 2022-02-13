package com.example.ecommerce.controller.socket;

import com.example.ecommerce.model.dto.chat.MessageForm;
import com.example.ecommerce.model.dto.notification.NotificationForm;
import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.notification.Notification;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.message.IMessageService;
import com.example.ecommerce.service.notification.INotificationService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin
@RestController
public class WebSocketController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private INotificationService notificationService;

    @MessageMapping("/messages/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public Message save(MessageForm messageForm){
        Message message = new Message();
        message.setDate(LocalDate.now());
        message.setUser(messageForm.getUser());
        message.setContent(messageForm.getContent());
        message.setRoomChat(messageForm.getRoomChat());
        message.setStatus(false);
        return messageService.save(message);
    }
    @MessageMapping("/notifications/{username}")
    @SendTo("/topic/notifications/{username}")
    public Notification saveNotification(NotificationForm notificationForm){
        Notification notification = new Notification();
        notification.setUrl(notificationForm.getUrl());
        notification.setContent(notificationForm.getContent());
        notification.setStatus(true);
        notification.setUser(notificationForm.getUser());
        return notificationService.save(notification);
    }
}

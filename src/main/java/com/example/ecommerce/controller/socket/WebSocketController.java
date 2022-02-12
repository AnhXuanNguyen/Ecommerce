package com.example.ecommerce.controller.socket;

import com.example.ecommerce.model.dto.chat.MessageForm;
import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@CrossOrigin
@RestController
public class WebSocketController {
    @Autowired
    private IMessageService messageService;
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
}

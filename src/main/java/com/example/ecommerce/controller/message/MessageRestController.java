package com.example.ecommerce.controller.message;

import com.example.ecommerce.model.dto.chat.MessageForm;
import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/messages")
public class MessageRestController {
    @Autowired
    private IMessageService messageService;

    @PutMapping("/room-chat")
    public ResponseEntity<Iterable<Message>> findAllByRoomChat(@RequestBody RoomChat roomChat){
        return new ResponseEntity<>(messageService.findAllByRoomChat(roomChat), HttpStatus.ACCEPTED);
    }
    @PostMapping
    public ResponseEntity<Message> save(@RequestBody MessageForm messageForm){
        Message message = new Message();
        message.setDate(LocalDate.now());
        message.setUser(messageForm.getUser());
        message.setContent(messageForm.getContent());
        message.setRoomChat(messageForm.getRoomChat());
        return new ResponseEntity<>(messageService.save(message), HttpStatus.CREATED);
    }
}

package com.example.ecommerce.controller.roomchat;

import com.example.ecommerce.service.roomchat.IRoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rooms")
public class RoomChatRestController {
    @Autowired
    private IRoomChatService roomChatService;
}

package com.example.ecommerce.controller.message;

import com.example.ecommerce.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/messages")
public class MessageRestController {
    @Autowired
    private IMessageService messageService;
}

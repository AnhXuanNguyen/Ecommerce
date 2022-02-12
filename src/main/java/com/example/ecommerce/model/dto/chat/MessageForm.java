package com.example.ecommerce.model.dto.chat;

import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {
    private String content;
    private User user;
    private RoomChat roomChat;
}

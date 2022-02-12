package com.example.ecommerce.model.dto.chat;

import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatForm {
    private String name;
    private Shop shop;
    private User user;
}

package com.example.ecommerce.model.dto.notification;

import com.example.ecommerce.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationForm {
    private String content;
    private String url;
    private User user;
}

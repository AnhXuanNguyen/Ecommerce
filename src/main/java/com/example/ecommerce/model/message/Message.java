package com.example.ecommerce.model.message;

import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate date;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @JsonBackReference
    @ManyToOne(targetEntity = RoomChat.class)
    private RoomChat roomChat;
}

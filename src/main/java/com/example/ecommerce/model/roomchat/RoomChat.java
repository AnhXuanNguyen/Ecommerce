package com.example.ecommerce.model.roomchat;

import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;
    @OneToMany(mappedBy = "roomChat")
    private List<Message> messages;
    @ManyToOne(targetEntity = User.class)
    private User user;
}

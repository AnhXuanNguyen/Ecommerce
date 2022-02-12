package com.example.ecommerce.model.user;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.comment.Comment;
import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.notification.Notification;
import com.example.ecommerce.model.order.OrderProduct;
import com.example.ecommerce.model.role.Role;
import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.MyShop;
import com.example.ecommerce.model.shop.Shop;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
    private String email;
    private String phone;
    private Double wallet;
    private Double lockWallet;
    private String address;
    private LocalDate date;
    private String username;
    private String password;
    private Boolean status;
    @OneToMany(mappedBy = "user")
    private List<MyShop> shops;
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Message> messages;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<OrderProduct> orderProducts;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<RoomChat> roomChats;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

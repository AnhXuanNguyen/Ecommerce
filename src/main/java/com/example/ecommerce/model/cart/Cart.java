package com.example.ecommerce.model.cart;

import com.example.ecommerce.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long totalMoney;
    @OneToMany(mappedBy = "cart")
    private List<ItemCart> itemCarts;
    @OneToOne(targetEntity = User.class)
    private User user;
}

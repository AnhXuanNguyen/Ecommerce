package com.example.ecommerce.model.order;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.cart.ItemCart;
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
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private Long moneyOrder;
    private EnumOrder enumOrder;
    @OneToMany(mappedBy = "orderProduct")
    private List<ItemCart> itemCarts;
    @ManyToOne(targetEntity = User.class)
    private User user;
}

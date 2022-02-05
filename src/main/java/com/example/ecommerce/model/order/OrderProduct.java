package com.example.ecommerce.model.order;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.cart.ItemCart;
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
    private EnumOrder enumOrder;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_products_item_carts",
            joinColumns = {@JoinColumn(name = "order_product_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_cart_id")})
    private List<ItemCart> itemCarts;
}

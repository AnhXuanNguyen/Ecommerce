package com.example.ecommerce.model.cart;

import com.example.ecommerce.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = Product.class)
    private Product product;
    private Long quantity;
    private LocalDate date;
    private String comment;
    @JsonIgnore
    @ManyToOne(targetEntity = Cart.class)
    private Cart cart;
}

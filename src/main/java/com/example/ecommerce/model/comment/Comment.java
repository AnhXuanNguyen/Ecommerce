package com.example.ecommerce.model.comment;

import com.example.ecommerce.model.product.Product;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private LocalDate date;
    private Long score;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @JsonBackReference
    @ManyToOne(targetEntity = Product.class)
    private Product product;
}

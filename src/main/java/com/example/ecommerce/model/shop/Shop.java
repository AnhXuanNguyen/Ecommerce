package com.example.ecommerce.model.shop;

import com.example.ecommerce.enums.EnumFollowShop;
import com.example.ecommerce.enums.EnumShop;
import com.example.ecommerce.enums.EnumShopType;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
    private LocalDate startOpen;
    private Long viewShop;
    private Long countFollow;
    private Long turnover;
    private EnumShop status;
    private EnumShopType type;
    private EnumFollowShop ownerOrFollow;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<Product> products;
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User user;
}

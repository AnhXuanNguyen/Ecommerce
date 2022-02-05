package com.example.ecommerce.model.product;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.model.comment.Comment;
import com.example.ecommerce.model.image.Image;
import com.example.ecommerce.model.origin.Origin;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private Long countBuy;
    @Lob
    @Column(columnDefinition = "varchar(5000)")
    private String description;
    private LocalDate dayUpdate;
    @ManyToOne(targetEntity = Origin.class)
    private Origin origin;
    private String brand;
    @OneToMany(mappedBy = "product")
    private List<Image> images;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;
    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;
    @OneToMany(mappedBy = "product")
    private List<Comment> comments;
}

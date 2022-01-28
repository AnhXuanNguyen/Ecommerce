package com.example.ecommerce.model.dto.product;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.model.origin.Origin;
import com.example.ecommerce.model.shop.Shop;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreate {
    private String name;
    private Long price;
    private Long quantity;
    private String description;
    private Origin origin;
    private String brand;
    private List<String> images;
    private List<Category> categories;
    private Shop shop;
}

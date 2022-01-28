package com.example.ecommerce.model.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCreate {
    @NotEmpty
    private String name;
    private String description;
    private String avatar;
}

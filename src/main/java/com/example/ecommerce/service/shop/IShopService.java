package com.example.ecommerce.service.shop;

import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.service.IGeneralService;

import java.util.Optional;

public interface IShopService extends IGeneralService<Shop> {
    Optional<Shop> findByProducts(Product product);
}

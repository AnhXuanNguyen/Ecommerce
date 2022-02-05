package com.example.ecommerce.repository;

import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByShop(Shop shop, Pageable pageable);
}

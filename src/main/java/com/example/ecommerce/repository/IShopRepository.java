package com.example.ecommerce.repository;

import com.example.ecommerce.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Long> {
}

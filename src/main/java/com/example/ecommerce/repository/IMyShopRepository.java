package com.example.ecommerce.repository;

import com.example.ecommerce.model.shop.MyShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMyShopRepository extends JpaRepository<MyShop, Long> {
}

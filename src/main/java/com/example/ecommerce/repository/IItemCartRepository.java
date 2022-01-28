package com.example.ecommerce.repository;

import com.example.ecommerce.model.cart.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemCartRepository extends JpaRepository<ItemCart, Long> {
}

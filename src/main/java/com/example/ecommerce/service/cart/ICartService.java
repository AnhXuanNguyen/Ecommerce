package com.example.ecommerce.service.cart;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.IGeneralService;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {
    Optional<Cart> findByUser(User user);
}

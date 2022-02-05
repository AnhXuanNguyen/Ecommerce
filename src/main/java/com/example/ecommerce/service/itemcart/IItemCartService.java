package com.example.ecommerce.service.itemcart;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.cart.ItemCart;
import com.example.ecommerce.service.IGeneralService;

public interface IItemCartService extends IGeneralService<ItemCart> {
    Iterable<ItemCart> findAllByCart(Cart cart);
}

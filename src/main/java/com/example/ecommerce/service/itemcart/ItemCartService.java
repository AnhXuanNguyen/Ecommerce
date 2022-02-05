package com.example.ecommerce.service.itemcart;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.cart.ItemCart;
import com.example.ecommerce.repository.IItemCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ItemCartService implements IItemCartService{
    @Autowired
    private IItemCartRepository iItemCartRepository;
    @Override
    public Iterable<ItemCart> findAll() {
        return iItemCartRepository.findAll();
    }

    @Override
    public Optional<ItemCart> findById(Long id) {
        return iItemCartRepository.findById(id);
    }

    @Override
    public ItemCart save(ItemCart itemCart) {
        return iItemCartRepository.save(itemCart);
    }

    @Override
    public void deleteById(Long id) {
        iItemCartRepository.deleteById(id);
    }

    @Override
    public Iterable<ItemCart> findAllByCart(Cart cart) {
        return iItemCartRepository.findAllByCart(cart);
    }
}

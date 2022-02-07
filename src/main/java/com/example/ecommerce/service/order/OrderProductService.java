package com.example.ecommerce.service.order;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.order.OrderProduct;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.repository.IOrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderProductService implements IOrderProductService{
    @Autowired
    private IOrderProductRepository orderProductRepository;
    @Override
    public Iterable<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    @Override
    public Optional<OrderProduct> findById(Long id) {
        return orderProductRepository.findById(id);
    }

    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public void deleteById(Long id) {
        orderProductRepository.deleteById(id);
    }

    @Override
    public Iterable<OrderProduct> findPendingOrderByShopId(Long shopId) {
        return orderProductRepository.findPendingOrderByShopId(shopId);
    }

    @Override
    public Page<OrderProduct> findAllByShopId(Long shopId, Pageable pageable) {
        return orderProductRepository.findAllByShopId(shopId, pageable);
    }

    @Override
    public Iterable<OrderProduct> findConfirmOrderByShopId(Long shopId) {
        return orderProductRepository.findConfirmOrderByShopId(shopId);
    }

    @Override
    public Iterable<OrderProduct> findCompleteOrderByShopId(Long shopId) {
        return orderProductRepository.findCompleteOrderByShopId(shopId);
    }

    @Override
    public Iterable<OrderProduct> findAllByUserAndEnumOrder(User user, EnumOrder enumOrder) {
        return orderProductRepository.findAllByUserAndEnumOrder(user, enumOrder);
    }

    @Override
    public Iterable<OrderProduct> findAllByUser(User user) {
        return orderProductRepository.findAllByUser(user);
    }
}

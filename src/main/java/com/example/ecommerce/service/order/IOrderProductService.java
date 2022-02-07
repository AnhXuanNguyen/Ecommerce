package com.example.ecommerce.service.order;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.order.OrderProduct;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderProductService extends IGeneralService<OrderProduct> {
    Iterable<OrderProduct> findPendingOrderByShopId(Long shopId);
    Page<OrderProduct> findAllByShopId(Long shopId, Pageable pageable);
    Iterable<OrderProduct> findConfirmOrderByShopId(Long shopId);
    Iterable<OrderProduct> findCompleteOrderByShopId(Long shopId);
    Iterable<OrderProduct> findAllByUserAndEnumOrder(User user, EnumOrder enumOrder);
    Iterable<OrderProduct> findAllByUser(User user);
}

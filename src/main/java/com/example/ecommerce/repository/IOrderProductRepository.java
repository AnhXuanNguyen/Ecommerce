package com.example.ecommerce.repository;

import com.example.ecommerce.enums.EnumOrder;
import com.example.ecommerce.model.order.OrderProduct;
import com.example.ecommerce.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query(value = "select * from order_product join item_cart ic on order_product.id = ic.order_product_id join product p on ic.product_id = p.id join shop s on p.shop_id = s.id where s.id = ? and order_product.enum_order = 1 group by order_product.id", nativeQuery = true)
    Iterable<OrderProduct> findPendingOrderByShopId(Long shopId);
    @Query(value = "select * from order_product op join item_cart ic on op.id = ic.order_product_id join product p on ic.product_id = p.id join shop s on p.shop_id = s.id where s.id = ? group by op.id", nativeQuery = true)
    Page<OrderProduct> findAllByShopId(Long shopId, Pageable pageable);
    @Query(value = "select * from order_product join item_cart ic on order_product.id = ic.order_product_id join product p on ic.product_id = p.id join shop s on p.shop_id = s.id where s.id = 1 and order_product.enum_order = 0 group by order_product.id", nativeQuery = true)
    Iterable<OrderProduct> findConfirmOrderByShopId(Long shopId);
    @Query(value = "select * from order_product join item_cart ic on order_product.id = ic.order_product_id join product p on ic.product_id = p.id join shop s on p.shop_id = s.id where s.id = 1 and order_product.enum_order = 2 group by order_product.id", nativeQuery = true)
    Iterable<OrderProduct> findCompleteOrderByShopId(Long shopId);
    Iterable<OrderProduct> findAllByUserAndEnumOrder(User user, EnumOrder enumOrder);
    Iterable<OrderProduct> findAllByUser(User user);
}

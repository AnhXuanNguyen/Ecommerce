package com.example.ecommerce.repository;

import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "select * from users join my_shop ms on users.id = ms.user_id where ms.id = ? and ms.follow_or_owner = 1", nativeQuery = true)
    Optional<User> findUserByMyShopId(Long myShopId);
    @Query(value = "select * from users join order_product op on users.id = op.user_id join item_cart ic on op.id = ic.order_product_id join product p on ic.product_id = p.id where op.enum_order = 2 and p.id = ? group by users.id", nativeQuery = true)
    Iterable<User> findAllByUserBuyProduct(Long productId);
    @Query(value = "select * from users u join my_shop ms on u.id = ms.user_id join shop s on ms.shop_id = s.id where ms.follow_or_owner = 1 and s.id = ? group by u.id", nativeQuery = true)
    Optional<User> findByShopId(Long shopId);
}

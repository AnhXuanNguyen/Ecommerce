package com.example.ecommerce.repository;

import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomChatRepository extends JpaRepository<RoomChat, Long> {
    @Query(value = "select * from room_chat where shop_id = ? and user_id = ?", nativeQuery = true)
    Optional<RoomChat> findByShopIdAndUserId(Long shopId, Long userId);
    Iterable<RoomChat> findAllByShop(Shop shop);
}

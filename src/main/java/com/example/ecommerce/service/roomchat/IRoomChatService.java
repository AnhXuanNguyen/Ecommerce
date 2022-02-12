package com.example.ecommerce.service.roomchat;

import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.IGeneralService;

import java.util.Optional;

public interface IRoomChatService extends IGeneralService<RoomChat> {
    Optional<RoomChat> findByShopIdAndUserId(Long shopId, Long userId);
    Iterable<RoomChat> findAllByShop(Shop shop);
}

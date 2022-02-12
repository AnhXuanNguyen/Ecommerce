package com.example.ecommerce.service.roomchat;

import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.repository.IRoomChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomChatService implements IRoomChatService{
    @Autowired
    private IRoomChatRepository roomChatRepository;
    @Override
    public Iterable<RoomChat> findAll() {
        return roomChatRepository.findAll();
    }

    @Override
    public Optional<RoomChat> findById(Long id) {
        return roomChatRepository.findById(id);
    }

    @Override
    public RoomChat save(RoomChat roomChat) {
        return roomChatRepository.save(roomChat);
    }

    @Override
    public void deleteById(Long id) {
        roomChatRepository.deleteById(id);
    }

    @Override
    public Optional<RoomChat> findByShopIdAndUserId(Long shopId, Long userId) {
        return roomChatRepository.findByShopIdAndUserId(shopId, userId);
    }

    @Override
    public Iterable<RoomChat> findAllByShop(Shop shop) {
        return roomChatRepository.findAllByShop(shop);
    }
}

package com.example.ecommerce.repository;

import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.roomchat.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findAllByRoomChat(RoomChat roomChat);
}

package com.example.ecommerce.repository;

import com.example.ecommerce.model.roomchat.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomChatRepository extends JpaRepository<RoomChat, Long> {
}

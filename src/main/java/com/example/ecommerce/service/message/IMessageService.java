package com.example.ecommerce.service.message;

import com.example.ecommerce.model.message.Message;
import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.service.IGeneralService;

public interface IMessageService extends IGeneralService<Message> {
    Iterable<Message> findAllByRoomChat(RoomChat roomChat);
}

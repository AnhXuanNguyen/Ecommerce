package com.example.ecommerce.controller.roomchat;

import com.example.ecommerce.model.dto.chat.RoomChatForm;
import com.example.ecommerce.model.roomchat.RoomChat;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.service.roomchat.IRoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/rooms")
public class RoomChatRestController {
    @Autowired
    private IRoomChatService roomChatService;

    @PostMapping
    public ResponseEntity<RoomChat> save(@RequestBody RoomChatForm roomChatForm){
        Optional<RoomChat> currentRoomChat = roomChatService.findByShopIdAndUserId(roomChatForm.getShop().getId(), roomChatForm.getUser().getId());
        if (currentRoomChat.isPresent()){
            return new ResponseEntity<>(currentRoomChat.get(), HttpStatus.OK);
        }
        RoomChat roomChat = new RoomChat();
        roomChat.setMessages(new ArrayList<>());
        roomChat.setName(roomChatForm.getName());
        roomChat.setShop(roomChatForm.getShop());
        roomChat.setUser(roomChatForm.getUser());
        return new ResponseEntity<>(roomChatService.save(roomChat), HttpStatus.CREATED);
    }
    @GetMapping("/{shopId}/{userId}")
    public ResponseEntity<RoomChat> findRoomChatByShopIdAndUserId(@PathVariable Long shopId, @PathVariable Long userId){
        Optional<RoomChat> roomChat = roomChatService.findByShopIdAndUserId(shopId, userId);
        if (!roomChat.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roomChat.get(), HttpStatus.OK);
    }
    @PutMapping("/shop")
    public ResponseEntity<Iterable<RoomChat>> findAllByShop(@RequestBody Shop shop){
        return new ResponseEntity<>(roomChatService.findAllByShop(shop), HttpStatus.OK);
    }
}

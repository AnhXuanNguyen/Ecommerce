package com.example.ecommerce.controller.shop;

import com.example.ecommerce.enums.EnumFollowShop;
import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.enums.EnumShop;
import com.example.ecommerce.model.dto.shop.ShopCreate;
import com.example.ecommerce.model.role.Role;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.shop.IShopService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/shops")
public class ShopRestController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Shop>> findAll(){
        return new ResponseEntity<>(shopService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Shop> findById(@PathVariable Long id){
        Optional<Shop> currentShop = shopService.findById(id);
        if (!currentShop.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentShop.get().setViewShop(currentShop.get().getViewShop()+1);
        return new ResponseEntity<>(shopService.save(currentShop.get()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Shop> save(@RequestBody ShopCreate shopCreate){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().getRoles().add(new Role(3L, EnumRoles.ROLE_PROVIDER));
        Shop shop = new Shop();
        shop.setName(shopCreate.getName());
        shop.setDescription(shopCreate.getDescription());
        shop.setViewShop(0L);
        shop.setCountFollow(0L);
        shop.setUser(currentUser.get());
        shop.setStatus(EnumShop.ACTIVITY);
        shop.setStartOpen(LocalDate.now());
        shop.setAvatar(shopCreate.getAvatar());
        shop.setOwnerOrFollow(EnumFollowShop.OWNER);
        shop.setTurnover(0L);
        return new ResponseEntity<>(shopService.save(shop), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id){
        shopService.deleteById(id);
        Optional<Shop> currentShop = shopService.findById(id);
        if (!currentShop.isPresent()){
            return new ResponseEntity<>("delete successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("delete fails", HttpStatus.NO_CONTENT);
    }
}

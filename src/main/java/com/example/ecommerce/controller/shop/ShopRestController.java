package com.example.ecommerce.controller.shop;

import com.example.ecommerce.enums.EnumFollowShop;
import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.enums.EnumShop;
import com.example.ecommerce.enums.EnumShopType;
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
    public ResponseEntity<?> save(@RequestBody ShopCreate shopCreate){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int check = 0;
        for (Role role : currentUser.get().getRoles()){
            if (role.getName() == EnumRoles.ROLE_PROVIDER){
                check = 1;
            }
            if (role.getName() == EnumRoles.ROLE_VIP){
                check = 2;
                break;
            }
        }
        if (check == 0 || check == 2){
            if (check == 0){
                currentUser.get().getRoles().add(new Role(3L, EnumRoles.ROLE_PROVIDER));
            }
            if (check == 2 && currentUser.get().getShops().size() >= 5){
                return new ResponseEntity<>("Đã đạt giới hạn tạo shop", HttpStatus.NO_CONTENT);
            }
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
            shop.setType(EnumShopType.NORMAL);
            return new ResponseEntity<>(shopService.save(shop), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Hãy trở thành VIP để tạo thêm shop mới", HttpStatus.NO_CONTENT);
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

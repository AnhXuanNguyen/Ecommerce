package com.example.ecommerce.controller.shop;

import com.example.ecommerce.enums.EnumFollowShop;
import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.enums.EnumShop;
import com.example.ecommerce.enums.EnumShopType;
import com.example.ecommerce.model.dto.shop.ShopCreate;
import com.example.ecommerce.model.dto.shop.ShopFollow;
import com.example.ecommerce.model.role.Role;
import com.example.ecommerce.model.shop.MyShop;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.myShop.IMyShopService;
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
    @Autowired
    private IMyShopService myShopService;

    @GetMapping
    public ResponseEntity<Iterable<Shop>> findAll(){
        return new ResponseEntity<>(shopService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MyShop> findById(@PathVariable Long id){
        Optional<MyShop> currentMyShop = myShopService.findById(id);
        if (!currentMyShop.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentMyShop.get().getShop().setViewShop(currentMyShop.get().getShop().getViewShop() + 1);
        shopService.save(currentMyShop.get().getShop());
        return new ResponseEntity<>(currentMyShop.get(), HttpStatus.OK);
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
            shop.setStatus(EnumShop.ACTIVITY);
            shop.setStartOpen(LocalDate.now());
            shop.setAvatar(shopCreate.getAvatar());
            shop.setTurnover(0L);
            shop.setType(EnumShopType.NORMAL);
            MyShop myShop = new MyShop();
            myShop.setShop(shopService.save(shop));
            myShop.setUser(currentUser.get());
            myShop.setFollowOrOwner(EnumFollowShop.OWNER);
            return new ResponseEntity<>(myShopService.save(myShop), HttpStatus.ACCEPTED);
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
    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> findShopById(@PathVariable Long id){
        Optional<Shop> currentShop = shopService.findById(id);
        if (!currentShop.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentShop.get(), HttpStatus.OK);
    }
    @PutMapping("/follow")
    public ResponseEntity<?> followShop(@RequestBody ShopFollow shopFollow){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (int i = 0; i < currentUser.get().getShops().size(); i++){
            MyShop myShop = currentUser.get().getShops().get(i);
            Shop shop = myShop.getShop();
            if (shop.getId() == shopFollow.getShop().getId()){
                return new ResponseEntity<>(myShop, HttpStatus.OK);
            }
        }
        shopFollow.getShop().setCountFollow(shopFollow.getShop().getCountFollow()+1);
        shopService.save(shopFollow.getShop());
        MyShop myShop = myShopService.save(new MyShop());
        myShop.setShop(shopFollow.getShop());
        myShop.setUser(currentUser.get());
        myShop.setFollowOrOwner(EnumFollowShop.FOLLOW);
        currentUser.get().getShops().add(myShop);
        userService.save(currentUser.get());
        return new ResponseEntity<>(myShopService.save(myShop), HttpStatus.ACCEPTED);
    }
}

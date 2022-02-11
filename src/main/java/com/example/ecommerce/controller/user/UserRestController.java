package com.example.ecommerce.controller.user;

import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.dto.user.UserChangeAvatar;
import com.example.ecommerce.model.dto.user.UserChangePassword;
import com.example.ecommerce.model.dto.user.UserEdit;
import com.example.ecommerce.model.dto.user.UserRecharge;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.cart.ICartService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ICartService cartService;

    @GetMapping
    public ResponseEntity<Page<User>> findAll(@PageableDefault(value = 4) Pageable pageable){
        return new ResponseEntity<>(userService.findAllPage(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> currentUser = userService.findById(id);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }
    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@RequestBody UserEdit userEdit){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = currentUser.get();
        user.setName(userEdit.getName());
        user.setAvatar(userEdit.getAvatar());
        user.setPhone(userEdit.getPhone());
        user.setEmail(userEdit.getEmail());
        user.setAddress(userEdit.getAddress());
        Optional<Cart> currentCart = cartService.findByUser(user);
        if (!currentCart.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentCart.get().setName("Giỏ hàng của "+user.getName());
        cartService.save(currentCart.get());
        return new ResponseEntity<>(userService.save(user), HttpStatus.ACCEPTED);
    }
    @PutMapping("/change-avatar")
    public ResponseEntity<User> changeAvatar(@RequestBody UserChangeAvatar userChangeAvatar){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().setAvatar(userChangeAvatar.getAvatar());
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.ACCEPTED);
    }
    @PutMapping("/change-password")
    public ResponseEntity<User> changePassword(@RequestBody UserChangePassword userChangePassword){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userChangePassword.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> currentUser = userService.findByUsername(username);
        currentUser.get().setPassword(passwordEncoder.encode(userChangePassword.getNewPassword()));
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.ACCEPTED);
    }
    @PutMapping("/recharge")
    public ResponseEntity<User> recharge(@RequestBody UserRecharge userRecharge){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().setWallet(currentUser.get().getWallet()+userRecharge.getMoney());
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        Optional<User> currentUser = userService.findById(id);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>("delete success", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("delete fails", HttpStatus.NO_CONTENT);
    }
    @GetMapping("/info")
    public ResponseEntity<User> getInfo(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }
    @GetMapping("/user/{myShopId}")
    public ResponseEntity<User> findByMyShopId(@PathVariable Long myShopId){
        Optional<User> currentUser = userService.findUserByMyShopId(myShopId);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }
    @GetMapping("/user-buyed/{productId}")
    public ResponseEntity<Iterable<User>> findAllUserBuyer(@PathVariable Long productId){
        return new ResponseEntity<>(userService.findAllByUserBuyProduct(productId), HttpStatus.OK);
    }
    @GetMapping("/user-shop/{shopId}")
    public ResponseEntity<User> findByShops(@PathVariable Long shopId){
        Optional<User> currentUser = userService.findByShopId(shopId);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }
}

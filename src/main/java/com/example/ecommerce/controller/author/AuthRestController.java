package com.example.ecommerce.controller.author;

import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.model.cart.Cart;
import com.example.ecommerce.model.dto.user.UserRegister;
import com.example.ecommerce.model.role.Role;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.model.jwt.JwtRequest;
import com.example.ecommerce.model.jwt.JwtResponse;
import com.example.ecommerce.service.cart.ICartService;
import com.example.ecommerce.service.jwt.JwtService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthRestController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister){
        userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        User user = userService.save(new User(userRegister.getName(), userRegister.getEmail(), userRegister.getUsername(), userRegister.getPassword()));
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(new Role(2L, EnumRoles.ROLE_USER));
        user.setRoles(rolesSet);
        Cart cart = new Cart();
        cart.setName("Rỏ hàng của "+userRegister.getName());
        cart.setTotalMoney(0L);
        cart.setUser(user);
        cart.setItemCarts(new ArrayList<>());
        cartService.save(cart);
        user.setWallet(0.0);
        user.setLockWallet(0.0);
        user.setDate(LocalDate.now());
        user.setStatus(false);
        user.setAvatar("https://firebasestorage.googleapis.com/v0/b/ecommerce-3990f.appspot.com/o/hinh-avatar-trang-cho-nam-va-con-than-lan.jpg?alt=media&token=e01f7e0e-a2a8-4152-b428-ff802ac56148");
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(jwtRequest.getUsername()).get();
        currentUser.setStatus(true);
        userService.save(currentUser);
        Optional<Cart> cart = cartService.findByUser(currentUser);
        for (Role role : currentUser.getRoles()){
            if (role.getName() == EnumRoles.ROLE_ADMIN){
                return ResponseEntity.ok(new JwtResponse(currentUser.getName(), userDetails.getUsername(),currentUser.getAvatar(), token, userDetails.getAuthorities(), currentUser.getWallet()));
            }
        }
        int totalItem = cart.get().getItemCarts().size();
        return ResponseEntity.ok(new JwtResponse(currentUser.getName(), userDetails.getUsername(),currentUser.getAvatar(), token, userDetails.getAuthorities(), totalItem, currentUser.getWallet(), currentUser.getLockWallet()));
    }
}
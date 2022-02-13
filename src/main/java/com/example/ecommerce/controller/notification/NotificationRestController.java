package com.example.ecommerce.controller.notification;

import com.example.ecommerce.model.notification.Notification;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.notification.INotificationService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationRestController {
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Notification>> findAllByUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notificationService.findAllByUser(currentUser.get()), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        notificationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

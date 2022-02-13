package com.example.ecommerce.repository;

import com.example.ecommerce.model.notification.Notification;
import com.example.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUser(User user);
}

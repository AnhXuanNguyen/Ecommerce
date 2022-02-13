package com.example.ecommerce.service.notification;

import com.example.ecommerce.model.notification.Notification;
import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.IGeneralService;

public interface INotificationService extends IGeneralService<Notification> {
    Iterable<Notification> findAllByUser(User user);
}

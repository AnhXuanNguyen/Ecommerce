package com.example.ecommerce.service.user;

import com.example.ecommerce.model.user.User;
import com.example.ecommerce.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    Page<User> findAllPage(Pageable pageable);
}

package com.example.ecommerce.repository;

import com.example.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "select * from users join my_shop ms on users.id = ms.user_id where ms.id = ? and ms.follow_or_owner = 1", nativeQuery = true)
    Optional<User> findUserByMyShopId(Long myShopId);
}

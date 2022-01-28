package com.example.ecommerce.repository;

import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
     Role findByName(EnumRoles name);
}

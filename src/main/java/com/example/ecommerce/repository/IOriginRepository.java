package com.example.ecommerce.repository;

import com.example.ecommerce.model.origin.Origin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOriginRepository extends JpaRepository<Origin, Long> {
}

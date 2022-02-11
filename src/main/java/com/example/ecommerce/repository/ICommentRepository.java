package com.example.ecommerce.repository;

import com.example.ecommerce.model.comment.Comment;
import com.example.ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByProduct(Product product, Pageable pageable);
}

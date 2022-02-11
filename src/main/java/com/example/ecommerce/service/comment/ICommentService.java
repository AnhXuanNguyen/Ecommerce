package com.example.ecommerce.service.comment;

import com.example.ecommerce.model.comment.Comment;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService extends IGeneralService<Comment> {
    Page<Comment> findAllByProduct(Product product, Pageable pageable);
}

package com.example.ecommerce.controller.comment;

import com.example.ecommerce.model.comment.Comment;
import com.example.ecommerce.model.dto.comment.CommentForm;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentRestController {
    @Autowired
    private ICommentService commentService;

    @PutMapping("/page")
    public ResponseEntity<Page<Comment>> findAllByProduct(@RequestBody Product product, @PageableDefault(value = 3) Pageable pageable){
        return new ResponseEntity<>(commentService.findAllByProduct(product, pageable), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody CommentForm commentForm){
        Comment comment = new Comment();
        comment.setScore(commentForm.getScore());
        comment.setDate(LocalDate.now());
        comment.setComment(commentForm.getComment());
        comment.setProduct(commentForm.getProduct());
        comment.setUser(commentForm.getUser());
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.ACCEPTED);
    }
}

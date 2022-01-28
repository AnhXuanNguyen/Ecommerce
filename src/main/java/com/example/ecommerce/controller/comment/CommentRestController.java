package com.example.ecommerce.controller.comment;

import com.example.ecommerce.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentRestController {
    @Autowired
    private ICommentService commentService;
}

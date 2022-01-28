package com.example.ecommerce.controller.category;

import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryRestController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Optional<Category> currentCategory = categoryService.findById(id);
        if (!currentCategory.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentCategory.get(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> currentCategory = categoryService.findById(id);
        if (!currentCategory.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentCategory.get().setName(category.getName());
        return new ResponseEntity<>(categoryService.save(currentCategory.get()), HttpStatus.ACCEPTED);
    }
    @PostMapping
    private ResponseEntity<Category> save(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }
}

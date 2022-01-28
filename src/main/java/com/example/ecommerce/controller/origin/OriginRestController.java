package com.example.ecommerce.controller.origin;

import com.example.ecommerce.model.origin.Origin;
import com.example.ecommerce.service.origin.IOriginService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/origins")
public class OriginRestController {
    @Autowired
    private IOriginService originService;

    @GetMapping
    public ResponseEntity<Iterable<Origin>> findAll(){
        return new ResponseEntity<>(originService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Origin>> findAllPage(@PageableDefault(value = 4)Pageable pageable){
        return new ResponseEntity<>(originService.findAllPage(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Origin> findById(@PathVariable Long id){
        Optional<Origin> currentOrigin = originService.findById(id);
        if (!currentOrigin.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentOrigin.get(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Origin> updateOrigin(@PathVariable Long id, @RequestBody Origin origin){
        Optional<Origin> currentOrigin = originService.findById(id);
        if (!currentOrigin.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentOrigin.get().setName(origin.getName());
        return new ResponseEntity<>(originService.save(currentOrigin.get()), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        originService.deleteById(id);
        Optional<Origin> currentOrigin = originService.findById(id);
        if (!currentOrigin.isPresent()){
            return new ResponseEntity<>("delete successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("delete fails", HttpStatus.NO_CONTENT);
    }
}

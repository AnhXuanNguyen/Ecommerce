package com.example.ecommerce.controller.product;

import com.example.ecommerce.model.dto.product.ProductCreate;
import com.example.ecommerce.model.dto.product.ProductEdit;
import com.example.ecommerce.model.image.Image;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.model.shop.Shop;
import com.example.ecommerce.service.image.IImageService;
import com.example.ecommerce.service.origin.IOriginService;
import com.example.ecommerce.service.product.IProductService;
import com.example.ecommerce.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductRestController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IImageService iImageService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(@PageableDefault(value = 8) Pageable pageable){
        return new ResponseEntity<>(productService.findAllPage(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> currentProduct = productService.findById(id);
        if (!currentProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentProduct.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductCreate productCreate){
        Product product = productService.save(new Product());
        product.setName(productCreate.getName());
        product.setPrice(productCreate.getPrice());
        product.setQuantity(productCreate.getQuantity());
        product.setDescription(productCreate.getDescription());
        product.setOrigin(productCreate.getOrigin());
        product.setBrand(productCreate.getBrand());
        if (productCreate.getImages().size() > 0){
            product.setImages(new ArrayList<>());
            for (String imageUrl: productCreate.getImages()){
                Image image = iImageService.save(new Image(imageUrl, product));
                product.getImages().add(image);
            }
        }
        product.setCategories(productCreate.getCategories());
        product.setShop(productCreate.getShop());
        product.setCountBuy(0L);
        product.setDayUpdate(LocalDate.now());
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductEdit productEdit){
        Optional<Product> currentProduct = productService.findById(id);
        if (!currentProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = currentProduct.get();
        product.setName(productEdit.getName());
        product.setQuantity(product.getQuantity() + productEdit.getQuantity());
        product.setPrice(productEdit.getPrice());
        product.setDescription(productEdit.getDescription());
        product.setOrigin(productEdit.getOrigin());
        product.setCategories(productEdit.getCategories());
        product.setBrand(productEdit.getBrand());
        return new ResponseEntity<>(productService.save(product), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return new ResponseEntity<>("delete success !!!", HttpStatus.NO_CONTENT);
    }
    @PutMapping("/my-shop")
    public ResponseEntity<Page<Product>> findByShopId(@RequestBody Shop shop, @PageableDefault(value = 8) Pageable pageable){
        return new ResponseEntity<>(productService.findAllByShop(shop,pageable), HttpStatus.OK);
    }
}

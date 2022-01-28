package com.example.ecommerce.service.product;

import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAllPage(Pageable pageable);
}

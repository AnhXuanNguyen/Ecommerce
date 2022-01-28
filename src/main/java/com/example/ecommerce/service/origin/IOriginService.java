package com.example.ecommerce.service.origin;

import com.example.ecommerce.model.origin.Origin;
import com.example.ecommerce.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOriginService extends IGeneralService<Origin> {
    Page<Origin> findAllPage(Pageable pageable);
}

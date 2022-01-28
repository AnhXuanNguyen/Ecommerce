package com.example.ecommerce.service.origin;

import com.example.ecommerce.model.origin.Origin;
import com.example.ecommerce.repository.IOriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OriginService implements IOriginService{
    @Autowired
    private IOriginRepository originRepository;
    @Override
    public Iterable<Origin> findAll() {
        return originRepository.findAll();
    }

    @Override
    public Optional<Origin> findById(Long id) {
        return originRepository.findById(id);
    }

    @Override
    public Origin save(Origin origin) {
        return originRepository.save(origin);
    }

    @Override
    public void deleteById(Long id) {
        originRepository.deleteById(id);
    }

    @Override
    public Page<Origin> findAllPage(Pageable pageable) {
        return originRepository.findAll(pageable);
    }
}

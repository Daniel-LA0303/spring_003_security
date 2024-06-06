package com.la.springboot.webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.la.springboot.webapp.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}

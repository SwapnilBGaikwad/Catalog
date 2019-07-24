package com.example.service;

import com.example.model.Product;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products;

    public ProductService() {
        products = new LinkedList<>();
    }

    void add(Product product) {
        products.add(product);
    }

    public List<Product> get() {
        return products;
    }
}

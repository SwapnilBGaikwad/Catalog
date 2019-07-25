package com.example.service;

import com.example.client.PriceComputationClient;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products;
    private PriceComputationClient computationClient;

    @Autowired
    public ProductService(PriceComputationClient computationClient) {
        this.computationClient = computationClient;
        products = new LinkedList<>();
    }

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> get() {
        return products;
    }

    public Double getRoundedUpCost() {
        return computationClient.getTotalPrice(products);
    }
}

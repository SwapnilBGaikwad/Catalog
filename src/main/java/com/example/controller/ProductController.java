package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping("/")
    @ResponseBody
    public List<Product> getProducts() {
        return service.get();
    }

    @RequestMapping("/cost")
    @ResponseBody
    public Double getProductsCost() {
        return service.getCost();
    }
}
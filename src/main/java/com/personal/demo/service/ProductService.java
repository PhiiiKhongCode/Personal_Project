package com.personal.demo.service;

import com.personal.demo.Entity.Category;
import com.personal.demo.Entity.Product;
import com.personal.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllPagingAndSorting() {
        return productRepository.findAll();
    }

    public List<Product> findCatePhone() {
        return productRepository.findCatePhone();
    }

    public List<Product> findCateLaptop() {
        return productRepository.findCateLaptop();
    }

    public List<Product> findCateCamera() {
        return productRepository.findCateCamera();
    }

    public List<Product> findbyAvailable(Boolean available) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>." + available);
        return productRepository.findbyAvailable(available);
    }

    public Optional<Product> findOne(Integer id) {
        return productRepository.findById(id);
    }

    public Product update(Product product) {
        Optional<Product> op = productRepository.findById(product.getId());
        return op.map(p -> {
            p.setName(product.getName());
            p.setCategory(product.getCategory());
            p.setCreateDate(product.getCreateDate());
            p.setPrice(product.getPrice());
            p.setImage(product.getImage());
            p.setAvailable(product.getAvailable());
            return productRepository.save(p);
        }).orElse(null);
    }

    public Product add(Product product) {
        Optional<Product> op = Optional.of(new Product());
        return op.map(p -> {
            p.setName(product.getName());
            p.setCategory(Category.builder().id(product.getCategory().getId()).build());
            p.setPrice(product.getPrice());
            p.setAvailable(product.getAvailable());
            p.setCreateDate(product.getCreateDate());
            p.setImage(product.getImage());
            return productRepository.save(p);
        }).orElse(null);
    }
}

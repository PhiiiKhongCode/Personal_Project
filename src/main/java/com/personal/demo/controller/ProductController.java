package com.personal.demo.controller;

import com.personal.demo.Entity.Product;
import com.personal.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAllPagingAndSorting());
    }

    @GetMapping(value = "/phone")
    public ResponseEntity<?> findCatePhone() {
        return ResponseEntity.ok(productService.findCatePhone());
    }

    @GetMapping(value = "/laptop")
    public ResponseEntity<?> findCateLaptop() {
        return ResponseEntity.ok(productService.findCateLaptop());
    }

    @GetMapping(value = "/camera")
    public ResponseEntity<?> findCateCamera() {
        return ResponseEntity.ok(productService.findCateCamera());
    }

    @GetMapping(value = "/lst/{avai}")
    public ResponseEntity<?> findbyAvailable(@PathVariable(name = "avai") Boolean avai) {
        return ResponseEntity.ok(productService.findbyAvailable(avai));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(productService.findOne(id));
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<?> save(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<?> update(@RequestBody Product product) {
        return ResponseEntity.ok(productService.update(product));
    }
}

package com.personal.demo.controller;

import com.personal.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/category")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}

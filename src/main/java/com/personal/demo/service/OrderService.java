package com.personal.demo.service;

import com.personal.demo.Entity.Order;
import com.personal.demo.repository.OrderDetailRepository;
import com.personal.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    public Order save(Order order){
        Order result = orderRepository.save(order);
        return result;
    }
}

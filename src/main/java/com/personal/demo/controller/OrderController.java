package com.personal.demo.controller;

import com.personal.demo.Entity.Account;
import com.personal.demo.Entity.Order;
import com.personal.demo.Entity.OrderDetail;
import com.personal.demo.Entity.Product;
import com.personal.demo.payload.request.CartRequest;
import com.personal.demo.payload.request.CartsAndOrderRequest;
import com.personal.demo.payload.request.OrderRequest;
import com.personal.demo.repository.OrderDetailRepository;
import com.personal.demo.service.AccountService;
import com.personal.demo.service.OrderDetailService;
import com.personal.demo.service.OrderService;
import com.personal.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return orderService.findAll();
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CartsAndOrderRequest cartsAndOrderRequest, HttpServletRequest request) {
         List<CartRequest> cartRequests = cartsAndOrderRequest.getCartDetails();
         OrderRequest orderRequest = cartsAndOrderRequest.getOrder();


        Account account = accountService.finAccountFromRequest(request);

        Order order = orderRequest.mapOrderRequestToOrder(account);
        Order result = orderService.save(order);

        cartRequests.forEach(cart -> {
            Product product = productService.findOne(cart.getId()).get();
            OrderDetail orderDetail = cart.mapCartToOrderDetail(product, order);
            orderDetailService.save(orderDetail);
        });

        return ResponseEntity.status(201).body(order);
    }
}

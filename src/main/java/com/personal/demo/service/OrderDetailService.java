package com.personal.demo.service;

import com.personal.demo.Entity.OrderDetail;
import com.personal.demo.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetail save(OrderDetail orderDetail){
       return orderDetailRepository.save(orderDetail);
    }
}

package com.personal.demo.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Component
@Data
@Entity
@Table(name = "Orderdetails")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "Productid")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "Orderid")
    private Order order;
}

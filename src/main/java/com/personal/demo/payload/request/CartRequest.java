package com.personal.demo.payload.request;

import com.personal.demo.Entity.Category;
import com.personal.demo.Entity.Order;
import com.personal.demo.Entity.OrderDetail;
import com.personal.demo.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CartRequest {
    private Integer id;
    private String name;
    private String image;
    private Double price;
    private Boolean available;
    private Category category;
    private Integer qtt;

    public OrderDetail mapCartToOrderDetail(Product product, Order order){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        orderDetail.setPrice(price);
        orderDetail.setQuantity(qtt);
        return orderDetail;
    }
}

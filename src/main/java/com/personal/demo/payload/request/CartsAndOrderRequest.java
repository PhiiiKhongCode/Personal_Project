package com.personal.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartsAndOrderRequest {
    private List<CartRequest> cartDetails;
    private OrderRequest order;
}

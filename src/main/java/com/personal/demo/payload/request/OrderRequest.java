package com.personal.demo.payload.request;

import com.personal.demo.Entity.Account;
import com.personal.demo.Entity.Order;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class OrderRequest {

    private String address;

    private Date createDate;

    private Boolean status;

    public Order mapOrderRequestToOrder(Account account){
        return new Order(null, address, createDate, status, account, null);
    }
}

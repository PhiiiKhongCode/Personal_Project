package com.personal.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Component
@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Address")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    private Date createDate;

    @Column(name = "Status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "CustomerName")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;
}
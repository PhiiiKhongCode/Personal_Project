package com.personal.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Component
@Builder
@Data
@Entity
@Table(name = "Categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "Name")
    private String name;
//    @JsonIgnore
//    @OneToMany(mappedBy = "category")
//    List<Product> products;

}

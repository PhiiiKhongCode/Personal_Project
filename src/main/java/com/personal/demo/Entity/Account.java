package com.personal.demo.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

;

@Entity
@Table(name = "Accounts")
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Account implements Serializable {

    @Id
    @Column(name = "Username", unique = true)
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Email")
    private String email;

    @Column(name = "Status")
    private Boolean status;

//    @JsonIgnore
//    @OneToMany(mappedBy = "account")
//    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AccountsRoles", joinColumns = @JoinColumn(name = "Username"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Role> authorities;

    public Account(String username, String password, String fullname, String email, Boolean status, Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.status = status;
        this.authorities = authorities;
    }
}

package com.personal.demo.repository;

import com.personal.demo.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findAccountByUsername(String username);

    Boolean existsByUsername(String username);
}

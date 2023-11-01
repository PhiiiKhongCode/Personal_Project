package com.personal.demo.service;

import com.personal.demo.Entity.Account;
import com.personal.demo.Entity.CustomUserDetails;
import com.personal.demo.repository.AccountRepository;
import com.personal.demo.util.JwtAuthenticationFilter;
import com.personal.demo.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service("accountService")
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findAccountByUsername(username);
        System.out.println(account.get());
        if (account == null) throw new UsernameNotFoundException("User not found");
        CustomUserDetails userDetails = CustomUserDetails.mapUserToUserDetail(account.get());
        return userDetails;
    }

    public Account saveUser(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> findByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public boolean existByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public Account finAccountFromRequest(HttpServletRequest request){
        String jwt = jwtAuthenticationFilter.getJwtFromRequest(request);
        String username = jwtTokenProvider.getUsernameFromToken(jwt);

        return findByUsername(username).orElse(null);
    }
}

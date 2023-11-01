package com.personal.demo.controller;
import com.personal.demo.Entity.Account;
import com.personal.demo.Entity.CustomUserDetails;
import com.personal.demo.Entity.Role;
import com.personal.demo.Entity.RoleEnum;
import com.personal.demo.payload.request.LoginRequest;
import com.personal.demo.payload.request.SignUpRequest;
import com.personal.demo.payload.response.JwtResponse;
import com.personal.demo.payload.response.MessageResponse;
import com.personal.demo.service.AccountService;
import com.personal.demo.service.RoleService;
import com.personal.demo.util.JwtAuthenticationFilter;
import com.personal.demo.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthorizeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        if (accountService.existByUsername(signUpRequest.getUsername())) {
            Account account = accountService.findByUsername(signUpRequest.getUsername()).get();
            account.setStatus(true);
            accountService.saveUser(account);
            return ResponseEntity.badRequest().body(new MessageResponse("User is already exist"));
        }
        List<String> roleStr = signUpRequest.getRoleStr();
        Set<Role> roles = new HashSet<>();
        AtomicInteger status = new AtomicInteger();
        if (roleStr.isEmpty()) {
            roles.add(roleService.findByName(RoleEnum.ROLE_USER).get());
        } else {
            roleStr.forEach(item -> {
                switch (item) {
                    case "user":
                        roles.add(roleService.findByName(RoleEnum.ROLE_USER).get());
                        break;
                    case "moderator":
                        roles.add(roleService.findByName(RoleEnum.ROLE_MODERATOR).get());
                        status.set(1);
                        break;
                    case "admin":
                        roles.add(roleService.findByName(RoleEnum.ROLE_ADMIN).get());
                        status.set(1);
                        break;
                }
            });
        }
        Account account = new Account(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getFullname(), signUpRequest.getEmail(), status.get()==1? false:true, roles);
        accountService.saveUser(account);
        return ResponseEntity.created(null).body(new MessageResponse("User register successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
        System.out.println("Authenticate in login");
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println("Pre Authenticate");
        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        System.out.println("Authenticated");
        System.out.println("Set authen to security context");
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        //genarate token from user

        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationResult.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetails);
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        System.out.println("Test validate token: " + jwtTokenProvider.validateToken(jwt));
        System.out.println(jwt);
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetails.getUsername(), roles));
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping("/manage-account")
    public ResponseEntity<?> update(@RequestBody SignUpRequest signUpRequest){
        Account account = accountService.findByUsername(signUpRequest.getUsername()).get();
        account.setUsername(signUpRequest.getUsername());
        account.setFullname(signUpRequest.getFullname());
        account.setEmail(signUpRequest.getEmail());

        Account result = accountService.saveUser(account);
        return ResponseEntity.ok().body(account);
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/manage-account")
    public ResponseEntity<?> getAccount(HttpServletRequest request){
        Account account = accountService.finAccountFromRequest(request);
        System.out.println(account);
        if(account == null) return ResponseEntity.badRequest().body(new MessageResponse("Some issue in server internal"));
        return ResponseEntity.ok(account);
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping("/manage-account")
    public ResponseEntity<?> offAccount(HttpServletRequest request){
        Account account = accountService.finAccountFromRequest(request);
        if(account == null) return ResponseEntity.badRequest().body(new MessageResponse("Some issue in server internal"));
        account.setStatus(false);
        accountService.saveUser(account);
        return ResponseEntity.ok().body(new MessageResponse("Account is disable"));
    }

}

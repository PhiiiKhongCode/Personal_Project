package com.personal.demo.payload.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> lstRole;

    public JwtResponse(String token, String username, List<String> lstRole) {
        this.token = token;
        this.username = username;
        this.lstRole = lstRole;
    }
}

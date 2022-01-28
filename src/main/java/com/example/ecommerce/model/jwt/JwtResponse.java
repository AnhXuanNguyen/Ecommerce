package com.example.ecommerce.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String name;
    private String username;
    private String avatar;
    private String type = "Bearer";
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String name, String username, String avatar, String token, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.token = token;
        this.authorities = authorities;
    }
}

package com.example.ecommerce.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegister {
    @NotEmpty
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}

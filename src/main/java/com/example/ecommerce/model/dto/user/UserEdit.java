package com.example.ecommerce.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEdit {
    @NotEmpty
    private String name;
    private String avatar;
    @Email
    private String email;
    private String phone;
    private String address;
}

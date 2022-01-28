package com.example.ecommerce.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeAvatar {
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
}

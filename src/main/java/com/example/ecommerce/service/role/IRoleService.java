package com.example.ecommerce.service.role;

import com.example.ecommerce.enums.EnumRoles;
import com.example.ecommerce.model.role.Role;
import com.example.ecommerce.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(EnumRoles name);
}

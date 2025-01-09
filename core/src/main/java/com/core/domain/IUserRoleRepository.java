package com.core.domain;


import com.core.domain.entity.UserRole;

import java.util.List;

public interface IUserRoleRepository {
    List<UserRole> selectByUserId(String id);
}

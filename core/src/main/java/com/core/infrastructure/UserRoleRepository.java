package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.core.domain.IUserRoleRepository;
import com.core.domain.entity.UserRole;
import com.core.infrastructure.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleRepository implements IUserRoleRepository {
    private final UserRoleMapper userRoleMapper;

    public UserRoleRepository(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserRole> selectByUserId(String id) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        return userRoleMapper.selectList(wrapper);
    }
}

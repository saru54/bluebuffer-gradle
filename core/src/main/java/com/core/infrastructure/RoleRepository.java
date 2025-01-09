package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.core.domain.IRoleRepository;
import com.core.domain.Role;
import com.core.infrastructure.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRepository implements IRoleRepository {
    private final RoleMapper roleMapper;

    public RoleRepository(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void insert(List<Role> roles) {
        roleMapper.insert(roles);

    }

    @Override
    public void update(Role role) {
        LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Role::getId, role.getId());
        roleMapper.update(role, wrapper);

    }

    @Override
    public void update(List<Role> roles) {
        roleMapper.batchUpdate(roles);
    }


    @Override
    public Role findByName(String name) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getName, name);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public Role findById(String id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);

        return roleMapper.selectOne(wrapper);
    }


    @Override
    public List<Role> findAll() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        return roleMapper.selectList(wrapper);
    }


    @Override
    public void delete(Role role) {
        roleMapper.deleteById(role.getId());
    }
}

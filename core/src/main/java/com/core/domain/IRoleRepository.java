package com.core.domain;

import java.util.List;

public interface IRoleRepository {
    Role findByName(String name);

    void insert(Role role);

    void insert(List<Role> roles);

    void update(Role role);

    void update(List<Role> roles);

    Role findById(String id);

    List<Role> findAll();

    void delete(Role role);
}

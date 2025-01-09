package com.core;

import com.core.domain.IRoleRepository;
import com.core.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {
    @Autowired
    private IRoleRepository roleRepository;

    @Test
    public void testInsertAndDelete(){
        Role role = new Role("test", "test");
        roleRepository.insert(role);
        Role findRole = roleRepository.findById(role.getId());
        Assertions.assertNotNull(findRole);

        roleRepository.delete(role);
        Role findRole2 = roleRepository.findById(role.getId());
        Assertions.assertNull(findRole2);
    }

    @Test
    public void testInsertAndUpdate(){
        Role role = new Role("test", "test");
        roleRepository.insert(role);
        role.setDescription("update");
        roleRepository.update(role);
        Role findRole = roleRepository.findById(role.getId());
        Assertions.assertEquals(role.getDescription(), findRole.getDescription());
        roleRepository.delete(findRole);
    }





}

package com.core;


import com.core.domain.User;
import com.core.domain.UserDomainService;
import com.core.domain.cq.UserQuery;
import com.core.domain.cq.UserUpdateCommand;
import com.core.domain.dto.UserInfoDTO;
import com.core.domain.entity.UserRole;
import com.core.domain.entity.UserSubscribe;
import com.core.infrastructure.UserRepository;
import com.core.infrastructure.UserRoleRepository;
import com.core.infrastructure.UserSubscribeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
public class UserTest {
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserSubscribeRepository userSubscribeRepository;
    @Transactional
    @Test
    public void testRegisterAndDelete() {
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        Assertions.assertNotNull(user);
        List<UserRole> userRoles = userRoleRepository.selectByUserId(user.getId());
        Assertions.assertEquals(1, userRoles.size());
        userRepository.delete(user);
        Assertions.assertNull(userRepository.findOne(new UserQuery("test","123",null,null)));
        Assertions.assertEquals(0,userRoleRepository.selectByUserId(user.getId()).size());
    }

    @Transactional
    @Test
    public void testLogin(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        String token = userDomainService.loginByNameAndPassword("test","123");
        Assertions.assertNotNull(token);
        userRepository.delete(userRepository.findOne(new UserQuery("test","123",null,null)));
    }
    @Transactional
    @Test
    public void testSubscribeAndDeSubscribe(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        userDomainService.registerByNameAndPassword("test2","123", null,null);
        var user1 = userRepository.findOne(new UserQuery("test","123",null,null));
        var user2 = userRepository.findOne(new UserQuery("test2","123",null,null));

        userDomainService.subscribe( user1.getId(), user2.getId());
        List<UserSubscribe> subscribes = userSubscribeRepository.findByUserId(user1.getId());
        Assertions.assertEquals(1, subscribes.size());
        userDomainService.subscribe( user1.getId(), user2.getId());
        List<UserSubscribe> subscribes2 = userSubscribeRepository.findByUserId(user1.getId());
        Assertions.assertEquals(0, subscribes2.size());


        userRepository.delete(user1);
        userRepository.delete(user2);
    }
    @Transactional
    @Test
    public void testGetUserInfo(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        userDomainService.registerByNameAndPassword("test2","123", null,null);
        var user1 = userRepository.findOne(new UserQuery("test","123",null,null));
        var user2 = userRepository.findOne(new UserQuery("test2","123",null,null));
        UserInfoDTO response = userDomainService.getUserInfo(user1.getId(),user2.getId());
        Assertions.assertEquals(false,response.getSubscribe());
        userDomainService.subscribe( user1.getId(), user2.getId());
        UserInfoDTO response2 = userDomainService.getUserInfo(user1.getId(),user2.getId());
        Assertions.assertEquals(true,response2.getSubscribe());
        userDomainService.subscribe( user1.getId(), user2.getId());


    }
    @Test
    @Transactional
    public void testUpdate(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        var user1 = userRepository.findOne(new UserQuery("test","123",null,null));
        userDomainService.update(new UserUpdateCommand(user1.getId(),null,null, null,null,"自我介绍",null));
        var user2 = userRepository.findOne(new UserQuery("test","123",null,null));
        Assertions.assertEquals("自我介绍",user2.getDescription());
        userRepository.delete(user2);
    }

    @Test
    @Transactional
    public void testCreateDefaultAdmin(){
        User user = userDomainService.createDefaultAdminUser();
        if(user != null){
            User findUser = userRepository.findOne(new UserQuery(user.getId()));
            Assertions.assertNotNull(findUser);
            Assertions.assertEquals(1,findUser.getRoles().size());
        }else {
            User findUser = userRepository.findOne(new UserQuery("admin","admin",null,null));
            Assertions.assertNotNull(findUser);
            Assertions.assertEquals(1,findUser.getRoles().size());
        }

    }
}

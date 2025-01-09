package com.core.infrastructure;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.core.domain.IUserRepository;
import com.core.domain.Role;
import com.core.domain.User;
import com.core.domain.cq.UserQuery;
import com.core.domain.cq.UserSubscribeQuery;
import com.core.domain.cq.UserUpdateCommand;
import com.core.domain.entity.ClubMember;
import com.core.domain.entity.UserRole;
import com.core.domain.entity.UserSubscribe;
import com.core.infrastructure.mapper.*;
import com.core.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepository implements IUserRepository {
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserSubscribeMapper userSubscribeMapper;
    private final ClubMemberMapper clubMemberMapper;
    public UserRepository(UserMapper userMapper, RedisUtil redisUtil, RoleMapper roleMapper, UserRoleMapper userRoleMapper, UserSubscribeMapper userSubscribeMapper, ClubMemberMapper clubMemberMapper) {
        this.userMapper = userMapper;
        this.redisUtil = redisUtil;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.userSubscribeMapper = userSubscribeMapper;
        this.clubMemberMapper = clubMemberMapper;
    }



    @Override
    public User findById(String targetUserId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, targetUserId);
        User user = userMapper.selectOne(wrapper);
        if(user != null) {
            List<Role> roles = roleMapper.selectByUserId(user.getId());
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public String getUserVersion(String name) {

        return redisUtil.get(name);

    }
    @Transactional
    @Override
    public void insert(User user) {
        userMapper.insert(user);
        List<UserRole> userRoles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            userRoles.add(new UserRole(user.getId(), role.getId()));
        });
        userRoleMapper.insert(userRoles);
    }

    @Override
    public void deleteUserVersion(String name) {
        redisUtil.set(name, null);
    }

    @Override
    public void  updateUserVersion(String name) {
        String version = redisUtil.get(name);
        if(version != null) {
            String newVersion  =  String.valueOf( Long.parseLong(version) + 1 );
            redisUtil.set(name, newVersion);
        }
        else{
            redisUtil.set(name, "1");
        }



    }
    @Transactional
    @Override
    public void insert(List<User> users) {
        userMapper.insert(users);
        List<UserRole> userRoles = new ArrayList<>();
        users.forEach(user -> {
            user.getRoles().forEach(role -> {
                userRoles.add(new UserRole(user.getId(), role.getId()));
            });
        });
        userRoleMapper.insert(userRoles);
    }

    @Override
    public void update(UserUpdateCommand command) {
        userMapper.update(command);
    }
    //批量更新 需要添加事务
    @Transactional
    @Override
    public void update(List<User> users) {
        userMapper.batchUpdate(users);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userMapper.deleteById(user.getId());


        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);



    }


    @Override
    public User findOne(UserQuery query) {
        User user = userMapper.findOne(query);
        if(user != null) {
            List<Role> roles = roleMapper.selectByUserId(user.getId());
            user.setRoles(roles);
            List<UserSubscribe> userSubscribers = userSubscribeMapper.findList(new UserSubscribeQuery(user.getId(),null));
            user.setSubscribers(userSubscribers.stream().map(UserSubscribe::getSubscribeUserId).collect(Collectors.toList()));

        }
        return user;
    }

    @Override
    public List<User> findList(UserQuery query) {
        List<User> users = userMapper.findList(query);
        for(User user : users) {
            if(user != null) {
                List<Role> roles = roleMapper.selectByUserId(user.getId());
                user.setRoles(roles);
                List<UserSubscribe> userSubscribers = userSubscribeMapper.findList(new UserSubscribeQuery(user.getId(),null));
                user.setSubscribers(userSubscribers.stream().map(UserSubscribe::getSubscribeUserId).collect(Collectors.toList()));
            }

        }
        return users;
    }


    @Override
    public List<User> findByIds(List<String> ids) {
        LambdaQueryWrapper<User> wrapperUser = new LambdaQueryWrapper<>();
        wrapperUser.in(User::getId, ids);
        List<User> users = userMapper.selectList(wrapperUser);
        for(User user : users) {
            if(user != null) {
                List<Role> roles = roleMapper.selectByUserId(user.getId());
                user.setRoles(roles);
                List<UserSubscribe> userSubscribers = userSubscribeMapper.findList(new UserSubscribeQuery(user.getId(),null));
                user.setSubscribers(userSubscribers.stream().map(UserSubscribe::getSubscribeUserId).collect(Collectors.toList()));
            }

        }
        return users;
    }

    @Override
    public Boolean checkClubSubscribeCondition(String userId, String clubId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getUserId, userId);
        wrapper.eq(ClubMember::getClubId, clubId);
        ClubMember clubMember = clubMemberMapper.selectOne(wrapper);
        return clubMember != null;
    }
}

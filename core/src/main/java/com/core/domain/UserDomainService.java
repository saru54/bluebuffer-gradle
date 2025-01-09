package com.core.domain;


import com.core.attribute.RoleType;
import com.core.domain.cq.UserQuery;
import com.core.domain.cq.UserUpdateCommand;
import com.core.domain.dto.UserBasicInfoDTO;
import com.core.domain.dto.UserInfoDTO;
import com.core.domain.entity.UserSubscribe;
import com.core.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDomainService {
    private final IUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final IRoleRepository roleRepository;
    private final IUserSubscribeRepository userSubscribeRepository;

    public UserDomainService(IUserRepository userRepository, JwtUtil jwtUtil, IRoleRepository roleRepository, IUserSubscribeRepository userSubscribeRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
        this.userSubscribeRepository = userSubscribeRepository;
    }
    public User createDefaultAdminUser(){
        User findUser = userRepository.findOne(new UserQuery("admin","admin",null,null));
        if(findUser != null){
            return null;
        }else{
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName(RoleType.ADMIN.toString().toLowerCase()));
            User user = new User("admin","admin",null,null,roles);
            userRepository.insert(user);
            return user;
        }

    }
    public String loginByNameAndPassword(String name, String password) {
        User user = userRepository.findOne(new UserQuery(name,password,null,null));
        if(user == null){
            return null;
        }
        userRepository.updateUserVersion(name);
        String version = userRepository.getUserVersion(name);

        String roles = transRolesToNameString(user.getRoles());
        return jwtUtil.getJwtToken(user.getId(),version,user.getName(),roles);
    }
    public void registerByNameAndPassword(String name, String password, String image, String description) {
        User user = userRepository.findOne(new UserQuery(name,password,null,null));
        if(user == null){
            Role role = roleRepository.findByName(RoleName.USER);
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            userRepository.insert(new User(name, password, image,description, roleList));
        }

    }
    public void deleteAccountByNameAndPassword(String name, String password) {
        User user = userRepository.findOne(new UserQuery(name,password,null,null));
        if(user == null){
            return;
        }
        userRepository.delete(user);
        userRepository.deleteUserVersion(name);
    }
    public void subscribe(String userId, String targetUserId) {
        User user = userRepository.findById(targetUserId);
        if(user != null){

            UserSubscribe userSubscribe = userSubscribeRepository.findByUserAndSubscribeUserId(userId,targetUserId);
            if(userSubscribe == null){
                userSubscribe = new UserSubscribe(userId,targetUserId);
                userSubscribeRepository.insert(userSubscribe);
            }else{
                userSubscribeRepository.delete(userSubscribe);
            }
        }
    }
    private String transRolesToNameString(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for (Role role : roles) {
            sb.append(role.getName());
            sb.append(",");

        }
        return sb.toString();
    }
    public UserInfoDTO getUserInfo(String userId, String targetUserId) {
        User user = userRepository.findOne(new UserQuery(userId));
        boolean isSubscribe = false;
        for(var subUserId : user.getSubscribers()){
            if (subUserId.equals(targetUserId)) {
                isSubscribe = true;
                break;
            }
        }

        return new UserInfoDTO(user.getId(),user.getName(),user.getDescription(),isSubscribe,user.getImage());
    }
    public void update(UserUpdateCommand command) {
        userRepository.update(command);
    }

    public List<UserBasicInfoDTO> getUserBasicInfo(List<String> userIds) {
        List<UserBasicInfoDTO> userBasicInfoDTOs = new ArrayList<>();
        return userBasicInfoDTOs;
    }
}

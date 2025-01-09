package com.core.controller.controller;


import com.core.attribute.NotAuth;
import com.core.controller.request.LoginByNameAndPwdRequest;
import com.core.controller.request.RegisterByNameAndPwdRequest;
import com.core.domain.UserDomainService;
import com.core.domain.cq.UserUpdateCommand;
import com.core.domain.dto.UserInfoDTO;
import com.core.util.BloomFilter;
import com.core.util.LocalStoreUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserDomainService userDomainService;
    private final LocalStoreUtil localStoreUtil;
    private final BloomFilter bloomFilter;
    public UserController(UserDomainService userDomainService, LocalStoreUtil localStoreUtil, BloomFilter bloomFilter) {
        this.userDomainService = userDomainService;
        this.localStoreUtil = localStoreUtil;
        this.bloomFilter = bloomFilter;
    }
    @NotAuth
    @PostMapping("/login")
    public ResponseEntity<String>  loginByNameAndPwd(@RequestBody LoginByNameAndPwdRequest request) {
        String token = userDomainService.loginByNameAndPassword(request.name(), request.password());
        return ResponseEntity.ok(token);
    }
    @NotAuth
    @PostMapping("/register")
    public ResponseEntity<String> registerByNameAndPwd(@RequestBody RegisterByNameAndPwdRequest request) {
        userDomainService.registerByNameAndPassword(request.name(),request.password(),request.image(),request.description());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String userId) {
        userDomainService.subscribe(localStoreUtil.getUserId(), userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserUpdateCommand command) {
        if(!Objects.equals(localStoreUtil.getUserId(), command.getId())){
            return ResponseEntity.badRequest().build();
        }
        userDomainService.update(command);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/verityName")
    public ResponseEntity<Boolean> verityName(@RequestParam String name) {

        return ResponseEntity.ok(bloomFilter.mightContain(name));
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<UserInfoDTO> getUserInfo(@RequestParam String userId , @RequestParam String targetUserId) {
        UserInfoDTO userInfoDTO = userDomainService.getUserInfo(userId, targetUserId);
        return ResponseEntity.ok().body(userInfoDTO);
    }
}

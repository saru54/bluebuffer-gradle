package com.core.controller.adminController;


import com.core.attribute.AuthRole;
import com.core.attribute.RoleType;
import com.core.domain.UserDomainService;
import com.core.domain.cq.UserUpdateCommand;
import com.core.util.LocalStoreUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthRole(roles = RoleType.ADMIN)
@RequestMapping("/admin/user")
@RestController
public class AdminUserController {
    private final UserDomainService userDomainService;
    private final LocalStoreUtil localStoreUtil;
    public AdminUserController(UserDomainService userDomainService, LocalStoreUtil localStoreUtil) {
        this.userDomainService = userDomainService;
        this.localStoreUtil = localStoreUtil;
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserUpdateCommand command) {
        userDomainService.update(command);
        return ResponseEntity.ok().build();
    }

}

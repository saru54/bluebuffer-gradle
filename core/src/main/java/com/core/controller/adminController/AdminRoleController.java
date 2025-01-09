package com.core.controller.adminController;


import com.core.attribute.AuthRole;
import com.core.attribute.RoleType;
import com.core.controller.request.InsertRoleRequest;
import com.core.domain.IRoleRepository;
import com.core.domain.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AuthRole(roles = RoleType.ADMIN)
@RequestMapping("/admin/role")
@RestController
public class AdminRoleController {
    private final IRoleRepository roleRepository;

    public AdminRoleController(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertRole(@RequestBody InsertRoleRequest request) {
        roleRepository.insert(new Role(request.name(), request.description()));
        return ResponseEntity.ok().build();
    }


}

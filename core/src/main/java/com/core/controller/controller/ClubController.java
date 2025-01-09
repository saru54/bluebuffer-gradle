package com.core.controller.controller;


import com.core.attribute.AuthClubAdmin;
import com.core.domain.ClubDomainService;
import com.core.domain.cq.ClubAdminOperationCommand;
import com.core.domain.cq.ClubDeleteBlogCommand;
import com.core.domain.cq.ClubRegisterCommand;
import com.core.domain.cq.ClubUpdateCommand;
import com.core.domain.dto.ClubInfoDTO;
import com.core.util.LocalStoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/club")
public class ClubController {
    private final ClubDomainService clubDomainService;
    private final LocalStoreUtil localStoreUtil;
    public ClubController(ClubDomainService clubDomainService, LocalStoreUtil localStoreUtil) {
        this.clubDomainService = clubDomainService;
        this.localStoreUtil = localStoreUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClubRegisterCommand command) {

        if(!localStoreUtil.getUserId().equals(command.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        clubDomainService.register(command);
        return ResponseEntity.ok("Register successful");
    }

    @GetMapping("/getClubInfo")
    public ResponseEntity<ClubInfoDTO> getClubInfo(@RequestParam String userId , @RequestParam String clubId) {
        if(!localStoreUtil.getUserId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }
        ClubInfoDTO clubInfoDTO = clubDomainService.getClubInfo(userId,clubId);
        return ResponseEntity.ok(clubInfoDTO);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String userId , @RequestParam String clubId) {
        if(!localStoreUtil.getUserId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }
        clubDomainService.subscribe(userId,clubId);
        return ResponseEntity.ok("Subscribe successful");
    }
    @AuthClubAdmin
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam ClubAdminOperationCommand operationCommand, @RequestBody ClubUpdateCommand command) {
        if(!localStoreUtil.getUserId().equals(operationCommand.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        clubDomainService.update(command);
        return ResponseEntity.ok("Update successful");
    }

    @AuthClubAdmin
    @PostMapping("/deleteBlog")
    public ResponseEntity<String> deleteBlog(@RequestParam ClubAdminOperationCommand operationCommand, @RequestBody ClubDeleteBlogCommand command) {
        if(!localStoreUtil.getUserId().equals(operationCommand.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Delete blog successful");
    }
}

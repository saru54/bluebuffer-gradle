package com.core.controller.adminController;


import com.core.domain.cq.ClubUpdateCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/club")
public class AdminClubController {


    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ClubUpdateCommand command) {

        return ResponseEntity.ok("update");
    }
}

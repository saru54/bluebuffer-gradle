package com.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping("/send")
    public ResponseEntity<String> send(){
        return  ResponseEntity.ok("success");
    }
}

package com.recommend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @PostMapping("/getBlogRecommend")
    public ResponseEntity<String> getBlogRecommend() {
        return null;
    }
    @PostMapping("/getClubRecommend")
    public ResponseEntity<String> getClubRecommend() {
        return null;
    }


}

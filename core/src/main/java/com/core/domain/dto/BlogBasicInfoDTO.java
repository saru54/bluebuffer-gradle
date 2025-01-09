package com.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogBasicInfoDTO {
    private String id;
    private String title;
    private String content;
    private List<String> images = new ArrayList<String>() ;
    private ClubBasicInfoDTO club;
    private UserBasicInfoDTO author;
    private LocalDateTime createTime;
    private Long likeCount;

//    public BlogBasicInfoDTO(String id, String title, String content, List<String> images, ClubBasicInfoDTO club, UserBasicInfoDTO author, LocalDateTime createTime, Long likeCount) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.images = images;
//        this.club = club;
//        this.author = author;
//        this.createTime = createTime;
//        this.likeCount = likeCount;
//    }
}

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
public class BlogInfoDTO {
    private String id;
    private String title;
    private String content;
    private List<String> images = new ArrayList<String>() ;
    private ClubBasicInfoDTO club;
    private UserBasicInfoDTO user;
    private LocalDateTime createTime;
    private Boolean like;
    private Boolean collect;

}

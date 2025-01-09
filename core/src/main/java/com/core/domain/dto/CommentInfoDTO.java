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
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoDTO {
    private String id;
    private String content;
    private Long likeCount;
    private List<String> images = new ArrayList<String>();
    private UserBasicInfoDTO author;
    private Boolean like;
    private LocalDateTime createTime;
}

package com.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentInfoDTO {
    private String id;

    private String content;

    private String commentId;

    private String blogId;

    private Long likeCount;

    private UserBasicInfoDTO author;

    private UserBasicInfoDTO respondUser;

    private Boolean like;

    private LocalDateTime createTime;
}

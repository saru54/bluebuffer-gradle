package com.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import com.core.domain.dto.UserBasicInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@TableName("comment")
@NoArgsConstructor

public class Comment extends BaseEntity {
    private String content;
    private String userId;
    private String blogId;
    private Long likeCount;
    @TableField(exist = false)
    private List<String> images = new ArrayList<String>();
    @TableField(exist = false)
    private UserBasicInfoDTO author;
    public Comment(String content, String userId, String blogId, List<String> images) {
        this.content = content;
        this.userId = userId;
        this.blogId = blogId;
        this.likeCount = 0L;
        this.images = images;
    }
}

package com.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import com.core.domain.dto.UserBasicInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@TableName("subcomment")
@NoArgsConstructor
public class SubComment extends BaseEntity {
    private String content;
    @TableField("comment_id")
    private String commentId;
    @TableField("blog_id")
    private String blogId;
    @TableField("respond_user_id")
    private String respondUserId;
    @TableField("user_id")
    private String userId;
    @TableField("like_count")
    private Long likeCount;
    @TableField(exist=false)
    private UserBasicInfoDTO author;
    @TableField(exist = false)
    private UserBasicInfoDTO respondUser;


    public SubComment(String content,  String commentId, String blogId, String userId,String respondUserId) {
        this.content = content;
        this.commentId = commentId;
        this.blogId = blogId;
        this.respondUserId = respondUserId;
        this.userId = userId;
        this.likeCount = 0L;
    }
}

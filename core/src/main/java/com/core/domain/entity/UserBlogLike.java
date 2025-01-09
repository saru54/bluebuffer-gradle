package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@TableName("user_blog_like")
public class UserBlogLike extends BaseEntity {
    @TableField("user_id")
    private String userId;
    @TableField("blog_id")
    private String blogId;
}

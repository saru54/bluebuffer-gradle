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
@TableName("club_blog")
public class ClubBlog extends BaseEntity {
    @TableField("club_id")
    private String clubId;
    @TableField("blog_id")
    private String blogId;
}

package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@TableName("blog_image")
@Getter
@Setter
@AllArgsConstructor
public class BlogImage extends BaseEntity {
    @TableField("blog_id")
    private String blogId;
    private String url;
}

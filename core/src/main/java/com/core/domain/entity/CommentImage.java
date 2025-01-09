package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@TableName("comment_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentImage extends BaseEntity {
    @TableField("comment_id")
    private String commentId;
    private String url;
}
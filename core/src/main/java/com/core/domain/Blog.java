package com.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import com.core.domain.dto.ClubBasicInfoDTO;
import com.core.domain.dto.UserBasicInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@TableName("blog")

public class Blog extends BaseEntity {
    private String title;
    private String content;

    @TableField("club_id")
    private String clubId;
    @TableField(exist = false)
    private ClubBasicInfoDTO club;
    @TableField("like_count")
    private Long likeCount;
    @TableField(exist = false)
    private List<String> images = new ArrayList<>();
    @TableField(exist=false)
    private UserBasicInfoDTO author;
    @TableField("user_id")
    private String userId;
    public Blog() {}
    public Blog(String title, String content,UserBasicInfoDTO author,ClubBasicInfoDTO club,List<String> images) {

        this.title = title;
        this.content = content;
        this.author = author;
        this.club = club;
        this.images = images;
        this.userId = author.getId();
        this.clubId = club.getId();
        this.likeCount = 0L;
    }
    public Blog(String title, String content,String userId,String clubId,List<String> images) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.clubId = clubId;
        this.images = images;
        this.likeCount = 0L;
    }



}

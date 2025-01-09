package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("club_member")
public class ClubMember extends BaseEntity {
    @TableField("club_id")
    private String clubId;
    @TableField("user_id")
    private String userId;

    public ClubMember(String userId,String clubId) {
        this.clubId = clubId;
        this.userId = userId;
    }
}

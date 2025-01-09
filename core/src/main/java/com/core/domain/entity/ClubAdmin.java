package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("club_admin")
public class ClubAdmin extends BaseEntity {
    @TableField("club_id")
    private String clubId;
    @TableField("user_id")
    private String userId;

    public ClubAdmin(String clubId, String userId) {
        this.clubId = clubId;
        this.userId = userId;
    }
}

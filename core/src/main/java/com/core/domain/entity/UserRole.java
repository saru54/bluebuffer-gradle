package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@TableName(value = "user_role")
public class UserRole extends BaseEntity {

    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "role_id")
    private String roleId;

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}

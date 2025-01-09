package com.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;

@TableName(value = "user_subscribe")
public class UserSubscribe extends BaseEntity {
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "subscribe_user_id")
    private String subscribeUserId;

    public UserSubscribe(String userId, String subscribeUserId) {
        this.userId = userId;
        this.subscribeUserId = subscribeUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscribeUserId() {
        return subscribeUserId;
    }

    public void setSubscribeUserId(String subscribeUserId) {
        this.subscribeUserId = subscribeUserId;
    }
}

package com.core.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class BaseEntity {

    @TableId(type = IdType.INPUT)
    private String id;
    @TableField(value = "create_time")
    private LocalDateTime createTime;



    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
        this.createTime = LocalDateTime.now();

    }





}

package com.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@TableName("club")
@Getter
@Setter
@NoArgsConstructor
public class Club extends BaseEntity {

    private String name;
    private String description;
    private String image;
    @TableField(exist = false)
    private List<User> admins;


    public Club(String name, String description, String image, List<User> admins) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.admins = admins;
    }
}

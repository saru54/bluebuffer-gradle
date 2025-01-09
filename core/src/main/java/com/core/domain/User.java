package com.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.common.BaseEntity;
import com.core.util.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@TableName(value = "user")
public class User extends BaseEntity {

    private String name;
    private String password;
    private String email;
    private String image;
    private String description;

    @TableField(exist = false)
    private List<Role> roles = new ArrayList<Role>();

    @TableField(exist = false)
    private List<String> subscribers = new ArrayList<>();


    public User()  {}

    public User(String name , String password,  String image, String description, List<Role> roles) {
        this.name = name;
        this.password = EncryptUtil.sha256Encrypt(password);
        this.image = image;
        this.description = description;
        this.roles = roles;

    }
    public User(String name ,String email, String password,  String image, String description,List<Role> roles) {
        this.name = name;
        this.password = EncryptUtil.sha256Encrypt(password);
        this.image = image;
        this.email = email;
        this.description = description;

        this.roles = roles;
    }




}

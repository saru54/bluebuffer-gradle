package com.core.domain.cq;


import com.core.util.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserQuery {
    private String id;
    private String name;
    private String password;
    private String email;

    private String phone;

    public UserQuery(String id) {
        this.id = id;
    }

    public UserQuery(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password != null? EncryptUtil.sha256Encrypt(password) : null;
        this.email = email;
        this.phone = phone;
    }
}

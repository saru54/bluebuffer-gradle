package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter

public class UserUpdateCommand {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String description;
    private String image;


}

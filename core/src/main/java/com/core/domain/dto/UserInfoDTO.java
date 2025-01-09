package com.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    private String id;
    private String name;
    private String description;
    private Boolean subscribe;
    private String image;

}

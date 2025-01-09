package com.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBasicInfoDTO {
    private String id;
    private String name;
    private String image;
}

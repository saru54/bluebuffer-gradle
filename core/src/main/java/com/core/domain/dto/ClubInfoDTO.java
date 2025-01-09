package com.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ClubInfoDTO {
    private String name;
    private String id;
    private String description;
    private String image;

    private List<UserBasicInfoDTO> admins = new ArrayList<>();

    private Boolean subscribe;
}

package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubRegisterCommand {
    private String name;
    private String description;
    private String image;
    private String userId;

}

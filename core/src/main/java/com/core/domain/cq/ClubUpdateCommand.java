package com.core.domain.cq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubUpdateCommand {
    private String userId;
    private String clubId;
    private String name;
    private String description;
    private String image;

    public ClubUpdateCommand(String userId, String clubId, String name, String description, String image) {
        this.userId = userId;
        this.clubId = clubId;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}

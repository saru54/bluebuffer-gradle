package com.core.domain.cq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogQuery {
    private String id;
    private String title;
    private String clubId;
    private String userId;

    public BlogQuery(String id) {
        this.id = id;

    }

    public BlogQuery(String title, String clubId, String userId) {
        this.title = title;
        this.clubId = clubId;
        this.userId = userId;
    }
}

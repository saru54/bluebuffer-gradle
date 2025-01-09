package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogDeleteCommand {
    private String id;
    private String userId;
}

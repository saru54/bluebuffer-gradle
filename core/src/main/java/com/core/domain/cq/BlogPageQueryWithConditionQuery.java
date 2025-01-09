package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogPageQueryWithConditionQuery {
    private Integer page;
    private Integer pageSize;
    private String userId;

}

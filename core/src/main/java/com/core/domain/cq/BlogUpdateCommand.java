package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogUpdateCommand {
    private String id;
    private String title;
    private String content;

}

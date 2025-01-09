package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlogInsertCommand {
    private String userId;
    private String clubId;
    private String title;
    private String content;
    private List<String> images = new ArrayList<>();


}

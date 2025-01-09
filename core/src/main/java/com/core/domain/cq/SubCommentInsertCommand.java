package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubCommentInsertCommand {
    private String content;
    private String commentId;
    private String blogId;
    private String userId;
    private String respondUserId;
}

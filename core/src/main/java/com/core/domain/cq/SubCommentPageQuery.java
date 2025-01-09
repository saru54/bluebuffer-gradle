package com.core.domain.cq;

public record SubCommentPageQuery(Integer page, Integer pageSize, String commentId, String userId) {
}

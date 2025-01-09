package com.core.domain.cq;

public record CommentPageQuery(Integer page,Integer pageSize, String blogId , String userId) {
}

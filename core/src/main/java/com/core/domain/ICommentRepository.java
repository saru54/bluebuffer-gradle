package com.core.domain;

import com.core.common.PageResult;
import com.core.domain.cq.CommentPageQuery;
import com.core.domain.cq.SubCommentPageQuery;
import com.core.domain.dto.CommentInfoDTO;
import com.core.domain.dto.SubCommentInfoDTO;

public interface ICommentRepository {
    void insert(Comment comment);

    void delete(Comment comment);

    Comment findById(String id);

    PageResult<CommentInfoDTO> findCommentInfoByPage(CommentPageQuery query);

    Boolean checkCommentLikeCondition(String userId, String commentId);

    void like(String userId, String commentId);

    void insertSubComment(SubComment subComment);

    PageResult<SubCommentInfoDTO> findSubCommentInfoByPage(SubCommentPageQuery subCommentPageQuery);

    void deleteSubComment(SubComment subComment);
}

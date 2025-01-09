package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.core.domain.Comment;
import com.core.domain.cq.CommentPageQuery;
import com.core.domain.cq.SubCommentPageQuery;
import com.core.domain.dto.CommentInfoDTO;
import com.core.domain.dto.SubCommentInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    Comment findById(String id);

    IPage<CommentInfoDTO> findCommentInfoByPage(IPage<CommentInfoDTO> page,@Param("query") CommentPageQuery query);

    IPage<SubCommentInfoDTO> findSubCommentInfoByPage(IPage<SubCommentInfoDTO> page, @Param("query") SubCommentPageQuery query);
}

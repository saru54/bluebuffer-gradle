package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.common.PageResult;
import com.core.domain.Comment;
import com.core.domain.ICommentRepository;
import com.core.domain.SubComment;
import com.core.domain.cq.CommentPageQuery;
import com.core.domain.cq.SubCommentPageQuery;
import com.core.domain.dto.CommentInfoDTO;
import com.core.domain.dto.SubCommentInfoDTO;
import com.core.domain.entity.CommentImage;
import com.core.domain.entity.UserCommentLike;
import com.core.infrastructure.mapper.CommentImageMapper;
import com.core.infrastructure.mapper.CommentMapper;
import com.core.infrastructure.mapper.SubCommentMapper;
import com.core.infrastructure.mapper.UserCommentLikeMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class CommentRepository implements ICommentRepository {
    private final CommentMapper commentMapper;
    private final CommentImageMapper commentImageMapper;
    private final UserCommentLikeMapper userCommentLikeMapper;
    private final SubCommentMapper subCommentMapper;
    public CommentRepository(CommentMapper commentMapper, CommentImageMapper commentImageMapper, UserCommentLikeMapper userCommentLikeMapper, SubCommentMapper subCommentMapper) {
        this.commentMapper = commentMapper;
        this.commentImageMapper = commentImageMapper;
        this.userCommentLikeMapper = userCommentLikeMapper;
        this.subCommentMapper = subCommentMapper;
    }
    @Transactional
    @Override
    public void insert(Comment comment) {
        commentMapper.insert(comment);
        if(comment.getImages() != null && !comment.getImages().isEmpty()) {
            commentImageMapper.insert(comment.getImages().stream().map(s-> new CommentImage(comment.getId(),s)).collect(Collectors.toList()));
        }
    }
    @Transactional
    @Override
    public void delete(Comment comment) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getId,comment.getId());
        commentMapper.delete(wrapper);
        LambdaQueryWrapper<CommentImage> imageWrapper = new LambdaQueryWrapper<>();
        imageWrapper.eq(CommentImage::getCommentId,comment.getId());
        commentImageMapper.delete(imageWrapper);
    }

    @Override
    public Comment findById(String id) {
        return commentMapper.findById(id);
    }

    @Override
    public PageResult<CommentInfoDTO> findCommentInfoByPage(CommentPageQuery query) {
        IPage<CommentInfoDTO> page = new Page<>(query.page(),query.pageSize());
        page  = commentMapper.findCommentInfoByPage(page,query);
        PageResult<CommentInfoDTO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    @Override
    public Boolean checkCommentLikeCondition(String userId, String commentId) {
        LambdaQueryWrapper<UserCommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCommentLike::getUserId,userId);
        wrapper.eq(UserCommentLike::getCommentId,commentId);
        return userCommentLikeMapper.selectOne(wrapper)!=null;
    }

    @Override
    public void like(String userId, String commentId) {
        if(!checkCommentLikeCondition(userId,commentId)) {
            UserCommentLike userCommentLike = new UserCommentLike(userId,commentId);
            userCommentLikeMapper.insert(userCommentLike);
        }else{
            LambdaQueryWrapper<UserCommentLike> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCommentLike::getUserId,userId);
            wrapper.eq(UserCommentLike::getCommentId,commentId);
            userCommentLikeMapper.delete(wrapper);
        }
    }

    @Override
    public void insertSubComment(SubComment subComment) {
        subCommentMapper.insert(subComment);
    }

    @Override
    public PageResult<SubCommentInfoDTO> findSubCommentInfoByPage(SubCommentPageQuery query) {
        IPage<SubCommentInfoDTO> page = new Page<>(query.page(),query.pageSize());
        page  = commentMapper.findSubCommentInfoByPage(page,query);
        PageResult<SubCommentInfoDTO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    @Override
    public void deleteSubComment(SubComment subComment) {
        subCommentMapper.deleteById(subComment.getId());
    }
}

package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.domain.entity.UserCommentLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCommentLikeMapper extends BaseMapper<UserCommentLike> {
}

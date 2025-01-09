package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.core.domain.entity.UserBlogLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBlogLikeMapper extends BaseMapper<UserBlogLike> {
}

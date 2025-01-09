package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.domain.cq.UserSubscribeQuery;
import com.core.domain.entity.UserSubscribe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSubscribeMapper extends BaseMapper<UserSubscribe> {
    List<UserSubscribe> findList(UserSubscribeQuery query);
}

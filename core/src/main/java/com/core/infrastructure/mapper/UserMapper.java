package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.core.domain.User;
import com.core.domain.cq.UserQuery;
import com.core.domain.cq.UserUpdateCommand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    void batchUpdate(@Param("list") List<User> users);

    User findOne(UserQuery query);

    List<User> findList(UserQuery query);

    void update(UserUpdateCommand command);
}

package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.core.domain.Club;
import com.core.domain.cq.ClubQuery;
import com.core.domain.cq.ClubUpdateCommand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {

    Club findOne(ClubQuery clubQuery);

    void update(ClubUpdateCommand command);
}

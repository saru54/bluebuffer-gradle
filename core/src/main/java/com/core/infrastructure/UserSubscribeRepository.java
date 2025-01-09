package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.core.domain.IUserSubscribeRepository;
import com.core.domain.entity.UserSubscribe;
import com.core.infrastructure.mapper.UserSubscribeMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSubscribeRepository implements IUserSubscribeRepository {
    private final UserSubscribeMapper userSubscribeMapper;

    public UserSubscribeRepository(UserSubscribeMapper userSubscribeMapper) {
        this.userSubscribeMapper = userSubscribeMapper;
    }

    @Override
    public UserSubscribe findByUserAndSubscribeUserId(String userId, String targetUserId) {
        LambdaQueryWrapper<UserSubscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSubscribe::getUserId, userId);
        wrapper.eq(UserSubscribe::getSubscribeUserId, targetUserId);
        return userSubscribeMapper.selectOne(wrapper);
    }

    @Override
    public void insert(UserSubscribe userSubscribe) {
        userSubscribeMapper.insert(userSubscribe);
    }

    @Override
    public List<UserSubscribe> findByUserId(String id) {
        LambdaQueryWrapper<UserSubscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSubscribe::getUserId, id);
        return userSubscribeMapper.selectList(wrapper);
    }

    @Override
    public void delete(UserSubscribe userSubscribe) {
        LambdaQueryWrapper<UserSubscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSubscribe::getUserId, userSubscribe.getUserId());
        userSubscribeMapper.delete(wrapper);
    }
}

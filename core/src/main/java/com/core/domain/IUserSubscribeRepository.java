package com.core.domain;


import com.core.domain.entity.UserSubscribe;

import java.util.List;

public interface IUserSubscribeRepository {
    UserSubscribe findByUserAndSubscribeUserId(String userId, String targetUserId);

    void insert(UserSubscribe userSubscribe);

    List<UserSubscribe> findByUserId(String id);

    void delete(UserSubscribe userSubscribe);
}

package com.core.domain;


import com.core.domain.cq.UserQuery;
import com.core.domain.cq.UserUpdateCommand;

import java.util.List;

public interface IUserRepository {

    String getUserVersion(String name );

    void updateUserVersion(String name);
    void deleteUserVersion(String name);

    void insert(User user);

    void insert(List<User> users);

    void update(UserUpdateCommand command);

    void update(List<User> users);

    void delete(User user);

    User findById(String targetUserId);

    User findOne(UserQuery query);
    List<User> findList(UserQuery query);

    List<User> findByIds(List<String> ids);

    Boolean checkClubSubscribeCondition(String userId, String clubId);
}

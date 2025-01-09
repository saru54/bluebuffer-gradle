package com.core.domain;


import com.core.domain.cq.ClubQuery;
import com.core.domain.cq.ClubUpdateCommand;

public interface IClubRepository {
    Club insert(Club club);

    Club findOne(ClubQuery clubQuery);

    void delete(Club club);

    void addClubSubscribe(String userId, String clubId);

    void deleteClubSubscribe(String userId, String clubId);

    void update(ClubUpdateCommand command);

    Boolean checkClubAdminCondition(String userId, String clubId);
}

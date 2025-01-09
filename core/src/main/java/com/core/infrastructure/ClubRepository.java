package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.core.domain.Club;
import com.core.domain.IClubRepository;
import com.core.domain.User;
import com.core.domain.cq.ClubQuery;
import com.core.domain.cq.ClubUpdateCommand;
import com.core.domain.entity.ClubAdmin;
import com.core.domain.entity.ClubMember;
import com.core.infrastructure.mapper.ClubAdminMapper;
import com.core.infrastructure.mapper.ClubMapper;
import com.core.infrastructure.mapper.ClubMemberMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClubRepository implements IClubRepository {
    private final ClubMapper clubMapper;
    private final ClubAdminMapper clubAdminMapper;
    private final UserRepository userRepository;
    private final ClubMemberMapper clubMemberMapper;
    public ClubRepository(ClubMapper clubMapper, ClubAdminMapper clubAdminMapper, UserRepository userRepository, ClubMemberMapper clubMemberMapper) {
        this.clubMapper = clubMapper;
        this.clubAdminMapper = clubAdminMapper;
        this.userRepository = userRepository;
        this.clubMemberMapper = clubMemberMapper;
    }

    @Transactional
    @Override
    public Club insert(Club club) {
        clubMapper.insert(club);
        List<ClubAdmin> clubAdmins = new ArrayList<ClubAdmin>();
        for (var admin : club.getAdmins()) {
            clubAdmins.add(new ClubAdmin(club.getId(), admin.getId()));
        }
        clubAdminMapper.insert(clubAdmins);
        return club;
    }

    @Override
    public Club findOne(ClubQuery clubQuery) {
        Club club = clubMapper.findOne(clubQuery);
        if(club != null) {
            LambdaQueryWrapper<ClubAdmin> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ClubAdmin::getClubId, club.getId());
            List<ClubAdmin> clubAdmins = clubAdminMapper.selectList(wrapper);
            List<String> ids =  clubAdmins.stream().map(ClubAdmin::getId).collect(Collectors.toList());

            List<User> admins =  userRepository.findByIds(ids);
            club.setAdmins(admins);
        }
        return club;

    }
    @Transactional
    @Override
    public void delete(Club club) {
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getId, club.getId());
        clubMapper.delete(wrapper);
        LambdaQueryWrapper<ClubAdmin> wrapperAdmin = new LambdaQueryWrapper<>();
        wrapperAdmin.eq(ClubAdmin::getClubId, club.getId());
        clubAdminMapper.delete(wrapperAdmin);
    }

    @Override
    public void addClubSubscribe(String userId, String clubId) {
        ClubMember clubMember = new ClubMember(userId, clubId);
        clubMemberMapper.insert(clubMember);

    }

    @Override
    public void deleteClubSubscribe(String userId, String clubId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getUserId, userId);
        wrapper.eq(ClubMember::getClubId, clubId);
        clubMemberMapper.delete(wrapper);
    }

    @Override
    public void update(ClubUpdateCommand command) {
        clubMapper.update(command);
    }


    @Override
    public Boolean checkClubAdminCondition(String userId, String clubId) {
        LambdaQueryWrapper<ClubAdmin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubAdmin::getUserId, userId);
        wrapper.eq(ClubAdmin::getClubId, clubId);
        ClubAdmin clubAdmin = clubAdminMapper.selectOne(wrapper);
        return clubAdmin != null;
    }
}

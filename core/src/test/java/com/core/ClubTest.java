package com.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.core.domain.*;
import com.core.domain.cq.ClubQuery;
import com.core.domain.cq.ClubRegisterCommand;
import com.core.domain.cq.ClubUpdateCommand;
import com.core.domain.cq.UserQuery;
import com.core.domain.dto.ClubInfoDTO;
import com.core.domain.entity.ClubMember;
import com.core.infrastructure.mapper.ClubMemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ClubTest {
    @Autowired
    private IClubRepository clubRepository;
    @Autowired
    private ClubDomainService clubDomainService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    @Transactional
    @Test
    public void testInsertAndDelete() {
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Assertions.assertNotNull(club);
        clubRepository.delete(club);
        Club findClub = clubRepository.findOne(new ClubQuery(null,"test"));
        Assertions.assertNull(findClub);
    }
    @Test
    @Transactional
    public void testGetClubInfo(){
        Club club = clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));
        User user = userRepository.findOne(new UserQuery("admin","admin",null,null));
        ClubInfoDTO clubInfoDTO = clubDomainService.getClubInfo(user.getId(),club.getId());
        Assertions.assertNotNull(clubInfoDTO);
        Assertions.assertEquals("介绍",clubInfoDTO.getDescription());
        clubRepository.delete(club);
    }
    @Test
    @Transactional
    public void testSubscribe(){
        Club club = clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));

        clubDomainService.subscribe(user.getId(),club.getId());
        ClubInfoDTO clubInfoDTO = clubDomainService.getClubInfo(user.getId(),club.getId());

        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getUserId,user.getId());
        wrapper.eq(ClubMember::getClubId,club.getId());
        ClubMember clubMember = clubMemberMapper.selectOne(wrapper);
        Assertions.assertNotNull(clubMember);
        Assertions.assertEquals(true,clubInfoDTO.getSubscribe());
        clubDomainService.subscribe(user.getId(),club.getId());
        ClubInfoDTO clubInfoDTO2 = clubDomainService.getClubInfo(user.getId(),club.getId());
        Assertions.assertEquals(false,clubInfoDTO2.getSubscribe());


        clubRepository.delete(club);
        userRepository.delete(user);

    }

    @Test
    @Transactional
    public void testUpdate(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        System.out.println("************************");
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        Club club = clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,user.getId()));
        clubDomainService.update(new ClubUpdateCommand(user.getId(),club.getId(),null,"new",null));
        Club findClub = clubRepository.findOne(new ClubQuery(null,"test"));
        Assertions.assertEquals("new",findClub.getDescription());

        userRepository.delete(user);
        clubRepository.delete(club);
    }
}

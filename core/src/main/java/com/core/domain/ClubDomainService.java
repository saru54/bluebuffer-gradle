package com.core.domain;


import com.core.domain.cq.ClubQuery;
import com.core.domain.cq.ClubRegisterCommand;
import com.core.domain.cq.ClubUpdateCommand;
import com.core.domain.cq.UserQuery;
import com.core.domain.dto.ClubInfoDTO;
import com.core.domain.dto.UserBasicInfoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubDomainService {
    private final IClubRepository clubRepository;
    private final IUserRepository userRepository;

    public ClubDomainService(IClubRepository clubRepository, IUserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    public Club register(ClubRegisterCommand command) {
        User user = userRepository.findOne(new UserQuery(command.getUserId()));
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Club club = new Club(command.getName(),command.getDescription(),command.getImage(),userList);
        clubRepository.insert(club);
        return club;
    }

    public ClubInfoDTO getClubInfo(String userId, String clubId) {
        Club club = clubRepository.findOne(new ClubQuery(clubId,null));
        Boolean bool = userRepository.checkClubSubscribeCondition(userId,clubId);
        List<UserBasicInfoDTO> list = club.getAdmins().stream().map(user ->
                new UserBasicInfoDTO(user.getId(),user.getName(),user.getImage())).collect(Collectors.toList());
        return new ClubInfoDTO(club.getName(),club.getId(),club.getDescription(),club.getImage(),list,bool);
    }

    public void subscribe(String userId, String clubId) {
        if(!userRepository.checkClubSubscribeCondition(userId, clubId)) {
            clubRepository.addClubSubscribe(userId,clubId);
        }else{
            clubRepository.deleteClubSubscribe(userId,clubId);
        }
    }

    public void update(ClubUpdateCommand command) {

        clubRepository.update(command);

    }
}

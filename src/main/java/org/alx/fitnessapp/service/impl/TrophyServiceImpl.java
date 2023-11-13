package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.TrophyUserDTOConverter;
import org.alx.fitnessapp.model.dto.TrophyEnum;
import org.alx.fitnessapp.model.dto.TrophyUserDTO;
import org.alx.fitnessapp.model.entity.Trophy;
import org.alx.fitnessapp.model.entity.TrophyUser;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.TrophyRepository;
import org.alx.fitnessapp.repository.TrophyUserRepository;
import org.alx.fitnessapp.service.TrophyService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TrophyServiceImpl implements TrophyService {


    private final TrophyRepository trophyRepository;
    private final TrophyUserRepository trophyUserRepository;
    private final UserService userService;
    private final TrophyUserDTOConverter converter;

    @Override
    public TrophyUserDTO achieveWelcome() {
        User loggedUser = userService.getLoggedUser();
        Trophy trophy = trophyRepository.findTrophyByTrophyName(TrophyEnum.IT_BEGINS_NOW.toString());
        TrophyUser existingTrophyUser = trophyUserRepository.findTrophyUserByTrophyAndUser(trophy, loggedUser);

        if (existingTrophyUser != null && existingTrophyUser.getIsAchieved()) {
            return null;
        } else {
            TrophyUser trophyUser = new TrophyUser();
            trophyUser.setUser(loggedUser);
            trophyUser.setDateAchieved(LocalDate.now());
            trophyUser.setIsAchieved(Boolean.TRUE);
            trophyUser.setTrophy(trophy);

            return converter.convertTrophyUserToTrophyUserDTO(trophyUserRepository.save(trophyUser));
        }
    }
}

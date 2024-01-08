package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.dto.TrophyEnum;
import org.alx.fitnessapp.model.dto.TrophyUserDTO;
import org.alx.fitnessapp.model.entity.Trophy;
import org.alx.fitnessapp.model.entity.TrophyUser;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.TrophyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrophyServiceTest extends ConfigBaseTest {

    @Autowired
    TrophyService trophyService;
    @Autowired
    TrophyRepository trophyRepository;

    @Test
    void achieveWelcome() {
        User user = userService.getLoggedUser();
        Trophy trophy = new Trophy();
        trophy.setId(1);
        trophy.setTrophyName(TrophyEnum.WELCOME.toString());
        when(trophyRepository.findTrophyByTrophyName(TrophyEnum.WELCOME.toString())).thenReturn(trophy);

        TrophyUser existingTrophyUser = new TrophyUser();
        existingTrophyUser.setId(1);
        existingTrophyUser.setUser(user);
        existingTrophyUser.setTrophy(trophy);
        existingTrophyUser.setDateAchieved(LocalDate.now());
        existingTrophyUser.setIsAchieved(true);

        when(trophyUserRepository.findTrophyUserByTrophyAndUser(trophy, user)).thenReturn(existingTrophyUser);

        TrophyUserDTO result = trophyService.achieveWelcome();

        verify(userService, times(2)).getLoggedUser();
        verify(trophyRepository, times(1)).findTrophyByTrophyName(TrophyEnum.WELCOME.toString());
        verify(trophyUserRepository, times(1)).findTrophyUserByTrophyAndUser(trophy, user);

        // Verify that the method returns null when the trophy is already achieved
        assertNull(result);
    }
}

package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DayServiceTest extends ConfigBaseTest {

    @Autowired
    DayService dayService;

    @BeforeEach
    void setupDays() {

    }

    @Test
    void autoCreateDayTest() throws DayException {
        String result = dayService.autoCreateDay();
        User user = userService.getLoggedUser();

        String expected = user.getGoal().getWeeklyExercise() + " days created!";

        assertEquals(expected, result);
    }

    @Test
    void createDayTest() {
        DayDTO day = new DayDTO();

        String result = dayService.createDay(day);
        String expecting = "Successfully created a workout day!";

        assertEquals(result, expecting);
    }

    @Test
    void getDaysTest() {
        List<DayDTO> days = dayService.getDays();

        User user = userService.getLoggedUser();

        assertFalse(days.isEmpty());
        assertNotNull(user);
        assertEquals(user.getGoal().getWeeklyExercise(), days.size());
    }

}

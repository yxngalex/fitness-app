package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.entity.Day;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void deleteDayTest() {
        User loggedUser = userService.getLoggedUser();

        DayDTO dayDTO = new DayDTO();
        dayDTO.setLoggedDate(LocalDate.now());

        Day existingDay = new Day();
        existingDay.setId(1);
        existingDay.setLoggedDate(dayDTO.getLoggedDate());

        WorkoutRoutine existingWorkoutRoutine = new WorkoutRoutine();
        existingDay.setWorkoutRoutine(existingWorkoutRoutine);

        when(dayRepository.findDayByUserIdAndLoggedDate(loggedUser.getId(), dayDTO.getLoggedDate()))
                .thenReturn(existingDay);

        String result = dayService.deleteDay(dayDTO);

        assertTrue(result.contains("Deleted"));

        verify(dayRepository).delete(existingDay);
        verify(exerciseStatsRepository).deleteAll(existingWorkoutRoutine.getExerciseStats());
        verify(workoutRoutineRepository).delete(existingWorkoutRoutine);
    }

}

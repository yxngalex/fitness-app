package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.DayRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DayServiceTest extends ConfigBaseTest {

    @Autowired
    DayService dayService;
    @Autowired
    GoalDTOConverter goalDTOConverter;

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
        User user = userService.getLoggedUser();
        List<Day> mockDays = getMockDays(user.getGoal().getWeeklyExercise(), user);

        when(dayRepository.findAllByUserId(user.getId())).thenReturn(mockDays);

        List<DayDTO> result = dayService.getDays();

        assertFalse(result.isEmpty());
        assertNotNull(user);
        assertEquals(user.getGoal().getWeeklyExercise(), result.size());
        verify(userService, times(2)).getLoggedUser();
        verify(dayRepository, times(1)).findAllByUserId(user.getId());
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

    private List<Day> getMockDays(int numberOfDays, User user) {
        List<Day> mockDays = new ArrayList<>();

        for (int i = 0; i < numberOfDays; i++) {
            Day mockDay = new Day();
            mockDay.setLoggedDate(LocalDate.now().minusDays(i));
            mockDay.setBmr(123d);

            Category c = new Category();
            c.setId(1);
            c.setCategoryDescription("123");
            c.setCategoryName("123");

            Exercise e = new Exercise();
            e.setId(1);
            e.setCategory(c);
            e.setExerciseDescription("Test");
            e.setExerciseName("Test");

            ExerciseStats es = new ExerciseStats();
            es.setId(1);
            es.setExerciseWeight(1d);
            es.setSet(1);
            es.setReps(1);
            es.setExercise(e);

            List<ExerciseStats> list = new ArrayList<>();
            list.add(es);

            WorkoutRoutine wro = new WorkoutRoutine();
            wro.setExerciseStats(list);
            wro.setCategory(c);
            wro.setGoal(user.getGoal());
            wro.setDateStart(LocalDate.now().minusDays(i));


            mockDay.setWorkoutRoutine(wro);
            mockDay.setNutrition(null);

            mockDays.add(mockDay);
        }

        return mockDays;
    }
}

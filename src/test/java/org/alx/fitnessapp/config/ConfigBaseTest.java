package org.alx.fitnessapp.config;

import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.dto.TrophyUserDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.*;
import org.alx.fitnessapp.security.UserDetailsServiceImpl;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Component
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ComponentScan(basePackages = "org.alx.fitnessapp")
public class ConfigBaseTest {

    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected GoalRepository goalRepository;
    @MockBean
    protected WorkoutRoutineRepository workoutRoutineRepository;
    @MockBean
    protected DayRepository dayRepository;
    @MockBean
    protected ExerciseStatsRepository exerciseStatsRepository;
    @MockBean
    protected MealRepository mealRepository;
    @MockBean
    protected NutritionRepository nutritionRepository;
    @MockBean
    protected TrophyUserRepository trophyUserRepository;
    @MockBean
    protected TrophyRepository trophyRepository;
    @MockBean
    protected UserService userService;
    protected UserDetailsService userDetailsService;

    @BeforeEach()
    public void setup() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        User mockUser = getUser();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(userService.getLoggedUser()).thenReturn(mockUser);
        when(goalRepository.save(any())).thenReturn(new Goal());
        when(userRepository.save(any())).thenReturn(new User());
        when(workoutRoutineRepository.save(any())).thenReturn(new WorkoutRoutine());
        when(dayRepository.save(any())).thenReturn(new Day());
        when(trophyRepository.findTrophyByTrophyName(anyString())).thenReturn(getTrophy());
        when(dayRepository.findAllByUserId(anyInt())).thenReturn(mockingDaysByUser(mockUser));
        when(dayRepository.findDayByUserIdAndLoggedDate(anyInt(), any(LocalDate.class))).thenReturn(getDay(mockUser));
        when(exerciseStatsRepository.save(any())).thenReturn(new ExerciseStats());
        when(mealRepository.save(any())).thenReturn(new Meal());
        when(nutritionRepository.save(any())).thenReturn(new Nutrition());
        doNothing().when(dayRepository).delete(any(Day.class));
        setAuthentication(mockUser);
    }

    @AfterEach()
    public void cleanup() {
        SecurityContextHolder.clearContext();
    }

    private void setAuthentication(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Trophy getTrophy() {
        Trophy trophy = new Trophy();
        trophy.setId(1);
        trophy.setTrophyName("test");
        trophy.setTrophyDescription("test");
        trophy.setTrophyImage(null);

        return trophy;
    }


    private static User getUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setFirstName("userTest");
        mockUser.setLastName("userTest");
        mockUser.setUsername("userTest");
        mockUser.setPassword("userTest");
        mockUser.setEmail("userTest@mail.com");
        mockUser.setAge(21);
        mockUser.setWeight(68.8);
        mockUser.setHeight(180);
        mockUser.setGender(GenderEnum.MAN.name());

        Goal goal = new Goal();
        goal.setId(1);
        goal.setBodyTypeGoal(BodyTypeGoalEnum.GAIN_WEIGHT.name());
        goal.setWeightGoal(72.0);
        goal.setWeeklyExercise(4);

        mockUser.setGoal(goal);

        return mockUser;
    }

    private Day getDay(User user) {
        Nutrition n = new Nutrition();
        n.setId(1);
        n.setProtein(2.0);
        n.setFat(2.0);
        n.setCarbs(2.0);
        n.setCalories(2.0);

        Day day = new Day();
        day.setNutrition(n);
        day.setBmr(123.4);
        day.setLoggedDate(LocalDate.now());
        day.setUser(user);

        return day;
    }

    private List<Day> mockingDaysByUser(User user) {
        List<Day> list = new ArrayList<>();
        for (int i = 0; i < user.getGoal().getWeeklyExercise(); i++) {
            Day day = new Day();
            day.setUser(user);

            list.add(day);
        }

        return list;
    }
}

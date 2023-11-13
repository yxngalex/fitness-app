package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GoalServiceTest extends ConfigBaseTest {

    @Autowired
    private GoalService goalService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoalDTOConverter converter;

    private final double WEIGHT_GOAL = 70.0;
    private final int WEEKLY_EXERCISE = 5;
    private final String BODY_TYPE_GOAL = BodyTypeGoalEnum.GAIN_WEIGHT.name();

    User user;

    @BeforeEach()
    public void getLoggedUser() {
        user = userService.getLoggedUser();
    }

    @Test
    void createOrUpdateGoalTest() {

        // SAVE

        GoalDTO goal = new GoalDTO();
        goal.setBodyTypeGoal(BODY_TYPE_GOAL);
        goal.setWeightGoal(WEIGHT_GOAL);
        goal.setWeeklyExercise(WEEKLY_EXERCISE);

        goalService.createOrUpdateGoal(goal);

        assertNotNull(user.getGoal());
        assertEquals(user.getGoal().getWeightGoal(), WEIGHT_GOAL);
        assertEquals(user.getGoal().getWeeklyExercise(), WEEKLY_EXERCISE);
        assertEquals(user.getGoal().getBodyTypeGoal(), BODY_TYPE_GOAL);

        // UPDATE

        double newWeightGoal = 62.3;
        int newWeeklyExercise = 6;
        String newBodyTypeEnum = BodyTypeGoalEnum.LOSE_WEIGHT.name();

        GoalDTO updateGoal = converter.convertGoalToGoalDTO(user.getGoal());

        updateGoal.setWeightGoal(newWeightGoal);
        updateGoal.setWeeklyExercise(newWeeklyExercise);
        updateGoal.setBodyTypeGoal(newBodyTypeEnum);

        goalService.createOrUpdateGoal(updateGoal);

        assertNotNull(user.getGoal());
        assertEquals(user.getGoal().getWeightGoal(), newWeightGoal);
        assertEquals(user.getGoal().getWeeklyExercise(), newWeeklyExercise);
        assertEquals(user.getGoal().getBodyTypeGoal(), newBodyTypeEnum);
    }

    @Test
    void getGoalTest() {
        GoalDTO g = new GoalDTO();
        g.setBodyTypeGoal(BODY_TYPE_GOAL);
        g.setWeightGoal(WEIGHT_GOAL);
        g.setWeeklyExercise(WEEKLY_EXERCISE);

        goalService.createOrUpdateGoal(g);

        GoalDTO goal = converter.convertGoalToGoalDTO(user.getGoal());

        GoalDTO getGoal = goalService.getGoal();

        assertNotNull(getGoal);
        assertEquals(getGoal.getWeightGoal(), goal.getWeightGoal());
        assertEquals(getGoal.getBodyTypeGoal(), goal.getBodyTypeGoal());
        assertEquals(getGoal.getWeeklyExercise(), goal.getWeeklyExercise());
    }
}

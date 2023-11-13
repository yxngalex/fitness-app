package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest extends ConfigBaseTest {

    @Autowired
    private GoalService goalService;
    @Autowired
    private UserService userService;

    UserDTO user;

    @BeforeEach()
    public void getLoggedUser() {
    }


    @Test
    void createOrUpdateGoalTest() {
        GoalDTO goal = new GoalDTO();
        goal.setBodyTypeGoal(BodyTypeGoalEnum.MAINTAIN_WEIGHT.name());
        goal.setWeightGoal(70.0);
        goal.setWeeklyExercise(5);

        user.setGoal(goal);

        goalService.createOrUpdateGoal(goal);

        assertNotNull(goal);
    }

    @Test
    void getGoalTest() {
        assertNotNull(user.getGoal());
        assertEquals(user.getGoal().getWeightGoal(), 70.0);
    }
}

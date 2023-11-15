package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest extends ConfigBaseTest {

    @Autowired
    private UserService userService;

    UserDTO user;

    @BeforeEach
    void setSavedUser() {
        user = new UserDTO();
        user.setUsername("userTest");
        user.setPassword("userTest");
        user.setFirstName("userTest");
        user.setLastName("userTest");
        user.setEmail("userTest@mail.com");
        user.setAge(21);
        user.setWeight(68.8);
        user.setHeight(180);
        user.setGender(GenderEnum.MAN.name());
        when(userService.getByUsername(anyString())).thenReturn(user);
    }

    @Test
    void registerUserTest() {
        GoalDTO goal = new GoalDTO();
        goal.setWeightGoal(12.3);
        goal.setBodyTypeGoal(BodyTypeGoalEnum.MAINTAIN_WEIGHT.name());
        goal.setWeeklyExercise(6);

        user.setGoal(goal);

        userService.registerUser(user);

        UserDTO savedUser = userService.getByUsername(user.getUsername());

        assertNotNull(savedUser);
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertEquals(savedUser.getHeight(), user.getHeight());
        assertEquals(savedUser.getWeight(), user.getWeight());
        assertEquals(savedUser.getGender(), user.getGender());
    }

    @Test
    void getLoggedUser() {
        User u = userService.getLoggedUser();

        assertNotNull(u);
        assertEquals(u.getUsername(), user.getUsername());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getEmail(), user.getEmail());
        assertEquals(u.getHeight(), user.getHeight());
        assertEquals(u.getWeight(), user.getWeight());
        assertEquals(u.getGender(), user.getGender());
    }

    @Test
    void getByUsername() {
        UserDTO u = userService.getByUsername(user.getUsername());

        assertNotNull(u);
        assertEquals(u.getUsername(), user.getUsername());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getEmail(), user.getEmail());
        assertEquals(u.getHeight(), user.getHeight());
        assertEquals(u.getWeight(), user.getWeight());
        assertEquals(u.getGender(), user.getGender());
    }

}

package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExerciseServiceTest extends ConfigBaseTest {
    @Autowired
    private ExerciseService exerciseService;

    @Test
    void getRandomExercisesByCategory() {
        List<Exercise> exercises = exerciseService.getRandomExercisesByCategory("CARDIO",5);

        assertFalse(exercises.isEmpty());
        assertEquals(4, exercises.size());
    }

    @Test
    void getRandomExercisesWithoutCategory() {
        List<Exercise> exercises = exerciseService.getRandomExercisesWithoutCategory();

        assertFalse(exercises.isEmpty());
        assertEquals(5, exercises.size());
    }

    @Test
    void getAllExercise() {
        List<ExerciseDTO> allExercises = exerciseService.getAllExercise();

        assertFalse(allExercises.isEmpty());
    }

    @Test
    void getExerciseAutoComplete() {
        List<ExerciseDTO> pushupExercises = exerciseService.getExerciseAutoComplete("Push Ups");


        assertFalse(pushupExercises.isEmpty());
        assertEquals(1, pushupExercises.size());
        assertEquals("Push Ups", pushupExercises.get(0).getExerciseName());

        List<ExerciseDTO> exercisesByName = exerciseService.getExerciseAutoComplete("Cardio");
        assertFalse(exercisesByName.isEmpty());
        assertEquals(4, exercisesByName.size());
    }

}

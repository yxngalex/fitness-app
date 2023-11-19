package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.CategoryDTOConverter;
import org.alx.fitnessapp.converter.ExerciseDTOConverter;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.converter.WorkoutRoutineDTOConverter;
import org.alx.fitnessapp.exception.WorkoutException;
import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.dto.ExerciseStatsDTO;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.CategoryRepository;
import org.alx.fitnessapp.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WorkoutRoutineServiceTest extends ConfigBaseTest {
    @Autowired
    private WorkoutRoutineService workoutRoutineService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private ExerciseService exerciseService;
    @MockBean
    private ExerciseRepository exerciseRepository;
    @Autowired
    private CategoryDTOConverter catConverter;
    @Autowired
    private GoalDTOConverter goalConverter;
    @Autowired
    private ExerciseDTOConverter exConverter;
    @Autowired
    private WorkoutRoutineDTOConverter wrConverter;

    List<Category> categoryForRoutine;
    List<Exercise> exercises;

    Exercise e;
    Category cat;

    @BeforeEach
    void setupWorkoutRoutine() {
        Category c1 = new Category();
        c1.setId(1);
        c1.setCategoryDescription("test");
        c1.setCategoryName("TEST1");

        cat = new Category();
        cat.setId(2);
        cat.setCategoryDescription("test");
        cat.setCategoryName("TEST2");

        categoryForRoutine = new ArrayList<>();
        categoryForRoutine.add(c1);
        categoryForRoutine.add(cat);

        when(categoryService.getAllCategoriesRandomized()).thenReturn(categoryForRoutine);

        e = new Exercise();
        e.setId(1);
        e.setCategory(c1);
        e.setExerciseName("test");
        e.setFavorited(false);
        e.setExerciseDescription("test");

        Exercise ex2 = new Exercise();
        ex2.setId(2);
        ex2.setCategory(cat);
        ex2.setExerciseName("test2");
        ex2.setFavorited(false);
        ex2.setExerciseDescription("test2");

        exercises = List.of(e, ex2);

        when(exerciseService.getRandomExercisesByCategory(any(), anyInt())).thenReturn(exercises);
        when(exerciseService.getRandomExercisesWithoutCategory()).thenReturn(exercises);
    }

    @Test
    void autoCreateWorkoutRoutine() throws WorkoutException {
        workoutRoutineService.autoCreateWorkoutRoutine();

        verify(workoutRoutineRepository, times(2)).save(any());
        verify(exerciseStatsRepository, times(4)).save(any());
    }

    @Test
    void createWorkoutRoutine() {
        WorkoutRoutineDTO toSave = new WorkoutRoutineDTO();
        toSave.setDateStart(LocalDate.now());
        toSave.setCategoryDTO(catConverter.convertCategoryToCategoryDTO(cat));
        toSave.setGoalDTO(goalConverter.convertGoalToGoalDTO(userService.getLoggedUser().getGoal()));

        List<ExerciseStatsDTO> exDTO = new ArrayList<>();

        ExerciseStatsDTO exs1 = new ExerciseStatsDTO();
        exs1.setReps(12);
        exs1.setSet(4);
        exs1.setExerciseDTO(exConverter.convertExerciseToExerciseDTO(exercises.get(0)));
        exs1.setExerciseWeight(12.0);

        ExerciseStatsDTO exs2 = new ExerciseStatsDTO();
        exs2.setReps(12);
        exs2.setSet(4);
        exs2.setExerciseDTO(exConverter.convertExerciseToExerciseDTO(exercises.get(1)));
        exs2.setExerciseWeight(12.0);

        exDTO.add(exs1);
        exDTO.add(exs2);

        toSave.setExerciseStatsDTO(exDTO);

        when(categoryRepository.findCategoryByCategoryName(anyString())).thenReturn(cat);
        when(exerciseRepository.findExerciseByCategoryCategoryNameAndExerciseName(anyString(), anyString())).thenReturn(e);

        WorkoutRoutine result = workoutRoutineService.createWorkoutRoutine(toSave);

        assertNotNull(result);
        assertEquals(result.getCategory().getCategoryName(), toSave.getCategoryDTO().getCategoryName());
        assertEquals(result.getCategory().getCategoryDescription(), toSave.getCategoryDTO().getCategoryDescription());
        assertEquals(result.getGoal().getWeeklyExercise(), toSave.getGoalDTO().getWeeklyExercise());
        assertEquals(result.getGoal().getWeightGoal(), toSave.getGoalDTO().getWeightGoal());
        assertEquals(result.getGoal().getBodyTypeGoal(), toSave.getGoalDTO().getBodyTypeGoal());
        assertEquals(result.getExerciseStats().get(0).getExercise().getExerciseName(), toSave.getExerciseStatsDTO().get(0).getExerciseDTO().getExerciseName());

    }

    @Test
    void getWorkoutRoutineList() {
        User user = userService.getLoggedUser();

        WorkoutRoutine toSave = createExistingWorkoutRoutine(user);

        List<WorkoutRoutine> workoutRoutines = new ArrayList<>();
        workoutRoutines.add(toSave);

        when(workoutRoutineRepository.findAllByUserId(user.getId())).thenReturn(workoutRoutines);


        List<WorkoutRoutineDTO> expectedDTOs = List.of(wrConverter.convertWorkoutRoutineToWorkoutRoutineDTO(workoutRoutines.get(0)));

        List<WorkoutRoutineDTO> actualDTOs = workoutRoutineService.getWorkoutRoutineList();

        assertEquals(expectedDTOs.size(), actualDTOs.size());
        for (int i = 0; i < expectedDTOs.size(); i++) {
            assertEquals(expectedDTOs.get(i), actualDTOs.get(i));
        }

        // called 2 times, once in this method and once in actual method
        verify(userService, times(2)).getLoggedUser();
        verify(workoutRoutineRepository, times(1)).findAllByUserId(user.getId());
    }

    @Test
    void updateWorkoutRoutine() {
        User u = userService.getLoggedUser();
        WorkoutRoutineDTO inputDTO = createWorkoutRoutine(u);
        WorkoutRoutine existingData = createExistingWorkoutRoutine(u);

        when(categoryRepository.findCategoryByCategoryName(anyString())).thenReturn(cat);
        when(workoutRoutineRepository.findByCategoryIdAndGoalIdAndDateStart(anyInt(), anyInt(), any(LocalDate.class))).thenReturn(existingData);
        when(workoutRoutineRepository.save(any(WorkoutRoutine.class))).thenReturn(existingData);

        WorkoutRoutineDTO resultDTO = workoutRoutineService.updateWorkoutRoutine(inputDTO);

        assertNotNull(resultDTO);
        assertEquals(resultDTO.getCategoryDTO().getCategoryName(), inputDTO.getCategoryDTO().getCategoryName());
        assertEquals(resultDTO.getCategoryDTO().getCategoryDescription(), inputDTO.getCategoryDTO().getCategoryDescription());
        assertEquals(resultDTO.getGoalDTO().getWeeklyExercise(), inputDTO.getGoalDTO().getWeeklyExercise());
        assertEquals(resultDTO.getGoalDTO().getWeightGoal(), inputDTO.getGoalDTO().getWeightGoal());
        assertEquals(resultDTO.getGoalDTO().getBodyTypeGoal(), inputDTO.getGoalDTO().getBodyTypeGoal());
        assertEquals(resultDTO.getExerciseStatsDTO().get(0).getExerciseDTO().getExerciseName(), inputDTO.getExerciseStatsDTO().get(0).getExerciseDTO().getExerciseName());
        verify(userService, times(2)).getLoggedUser();
        verify(categoryRepository, times(1)).findCategoryByCategoryName(anyString());
        verify(workoutRoutineRepository, times(1)).findByCategoryIdAndGoalIdAndDateStart(anyInt(), anyInt(), any(LocalDate.class));
        verify(workoutRoutineRepository, times(1)).save(any(WorkoutRoutine.class));
    }

    @Test
    void deleteWorkoutRoutine() {
        User u = userService.getLoggedUser();

        WorkoutRoutineDTO inputDTO = createWorkoutRoutine(u);
        WorkoutRoutine existingWorkoutRoutine = createExistingWorkoutRoutine(u);

        when(categoryRepository.findCategoryByCategoryName(anyString())).thenReturn(cat);
        when(workoutRoutineRepository.findByCategoryIdAndGoalIdAndDateStart(anyInt(), anyInt(), any(LocalDate.class))).thenReturn(existingWorkoutRoutine);

        String result = workoutRoutineService.deleteWorkoutRoutine(inputDTO);

        assertEquals("Successfully deleted a workout routine", result);
        verify(userService, times(2)).getLoggedUser();
        verify(categoryRepository, times(1)).findCategoryByCategoryName(anyString());
        verify(workoutRoutineRepository, times(1)).findByCategoryIdAndGoalIdAndDateStart(anyInt(), anyInt(), any(LocalDate.class));
        verify(exerciseStatsRepository, times(2)).deleteById(anyInt());
        verify(workoutRoutineRepository, times(1)).deleteById(anyInt());
    }

    private WorkoutRoutineDTO createWorkoutRoutine(User u) {
        WorkoutRoutineDTO inputDTO = new WorkoutRoutineDTO();
        inputDTO.setDateStart(LocalDate.now());
        inputDTO.setCategoryDTO(catConverter.convertCategoryToCategoryDTO(cat));
        inputDTO.setGoalDTO(goalConverter.convertGoalToGoalDTO(u.getGoal()));

        List<ExerciseStatsDTO> exDTO = new ArrayList<>();

        ExerciseStatsDTO exs1 = new ExerciseStatsDTO();
        exs1.setReps(12);
        exs1.setSet(4);
        exs1.setExerciseDTO(exConverter.convertExerciseToExerciseDTO(exercises.get(0)));
        exs1.setExerciseWeight(12.0);

        ExerciseStatsDTO exs2 = new ExerciseStatsDTO();
        exs2.setReps(12);
        exs2.setSet(4);
        exs2.setExerciseDTO(exConverter.convertExerciseToExerciseDTO(exercises.get(1)));
        exs2.setExerciseWeight(12.0);

        exDTO.add(exs1);
        exDTO.add(exs2);

        inputDTO.setExerciseStatsDTO(exDTO);

        return inputDTO;
    }

    private WorkoutRoutine createExistingWorkoutRoutine(User u) {
        WorkoutRoutine existingData = new WorkoutRoutine();
        existingData.setId(1);
        existingData.setDateStart(LocalDate.now());
        existingData.setCategory(cat);
        existingData.setGoal(u.getGoal());

        List<ExerciseStats> ex = new ArrayList<>();

        ExerciseStats e1 = new ExerciseStats();
        e1.setId(1);
        e1.setReps(12);
        e1.setSet(4);
        e1.setExercise(exercises.get(0));
        e1.setExerciseWeight(12.0);

        ExerciseStats e2 = new ExerciseStats();
        e2.setId(2);
        e2.setReps(12);
        e2.setSet(4);
        e2.setExercise(exercises.get(1));
        e2.setExerciseWeight(12.0);

        ex.add(e1);
        ex.add(e2);

        existingData.setExerciseStats(ex);
        return existingData;
    }
}

package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.WorkoutRoutineDTOConverter;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.CategoryRepository;
import org.alx.fitnessapp.repository.ExerciseRepository;
import org.alx.fitnessapp.repository.ExerciseStatsRepository;
import org.alx.fitnessapp.repository.WorkoutRoutineRepository;
import org.alx.fitnessapp.service.CategoryService;
import org.alx.fitnessapp.service.ExerciseService;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.stereotype.Service;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkoutRoutineServiceImpl implements WorkoutRoutineService {
    private final UserService userService;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final CategoryService categoryService;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseStatsRepository exerciseStatsRepository;
    private final WorkoutRoutineDTOConverter converter;
    private final ExerciseService exerciseService;

    @Override
    public String createWorkoutRoutineWithGoal() {
        User loggedInUser = userService.getLoggedUser();

        if (loggedInUser.getGoal() != null) {
            if (loggedInUser.getGoal().getWeeklyExercise() >= 3 && loggedInUser.getGoal().getWeeklyExercise() <= 6) {
                List<Category> categoriesForRoutine = categoryService.getAllCategoriesRandomized();

//                Category breakCategory = categoriesForRoutine.remove(1);

                for (int i = 0; i < loggedInUser.getGoal().getWeeklyExercise(); i++) {
                    if (!categoriesForRoutine.get(i).getCategoryName().equals("MIXED")) {
                        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
                        workoutRoutine.setDateStart(LocalDate.now().plusDays(i));
                        workoutRoutine.setGoal(loggedInUser.getGoal());
                        workoutRoutine.setCategory(categoriesForRoutine.get(i));

                        if (!categoriesForRoutine.get(i).getCategoryName().equals("BREAK")) {

                            List<Exercise> exercises = exerciseService.getRandomExercisesByCategory(categoriesForRoutine.get(i).getCategoryName(), 5);

                            List<ExerciseStats> statsList = new ArrayList<>();

                            for (Exercise exercise : exercises) {

                                ExerciseStats stat = new ExerciseStats();
                                stat.setExercise(exercise);
                                stat.setSet(1);
                                stat.setReps(2);
                                stat.setExerciseWeight(12.3);

                                exerciseStatsRepository.save(stat);

                                statsList.add(stat);
                            }

                            workoutRoutine.setExerciseStats(statsList);
                        }
                        workoutRoutineRepository.save(workoutRoutine);
                    }
                }
                return "Sucessfully created " + loggedInUser.getGoal().getWeeklyExercise() + " workout routines!";
            } else if (loggedInUser.getGoal().getWeeklyExercise() < 3) {
                return "Nothing yet";
            }
        }
        return "Error while creating a workout routine!";
    }

    @Override
    public String createWorkoutRoutineWithoutGoal(WorkoutRoutineDTO workoutRoutineDTO) {
        return null;
    }
}

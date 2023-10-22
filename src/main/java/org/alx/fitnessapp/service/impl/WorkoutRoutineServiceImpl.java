package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.CategoryRepository;
import org.alx.fitnessapp.repository.ExerciseRepository;
import org.alx.fitnessapp.repository.ExerciseStatRepository;
import org.alx.fitnessapp.repository.WorkoutRoutineRepository;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutRoutineServiceImpl implements WorkoutRoutineService {
    private final UserService userService;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final CategoryRepository categoryRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseStatRepository exerciseStatRepository;

    @Override
    public String createWorkoutRoutineWithGoal() {
        User loggedInUser = userService.getLoggedUser();

        /*
            Create workout plan for a user that has a goal. Based on the goal value it needs to create a workout plan with up to 6-7 exercises
         */
        if (loggedInUser.getGoal() != null) {
            // mog izbrisem ovo
//            if (loggedInUser.getWeight() < loggedInUser.getGoal().getWeightGoal()) {
                if (loggedInUser.getGoal().getWeeklyExercise() >= 3 && loggedInUser.getGoal().getWeeklyExercise() <= 6) {
//                    List<Category> categoriesForRoutine = categoryRepository.findCategoryForWorkoutRoutine();
                    List<Category> categoriesForRoutine = categoryRepository.findAll();

                    for (int i = 0; i < loggedInUser.getGoal().getWeeklyExercise(); i++) {
                        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
                        workoutRoutine.setDateStart(LocalDate.now().plusDays(i));
                        workoutRoutine.setGoal(loggedInUser.getGoal());

                        categoriesForRoutine.forEach(c -> {
                            workoutRoutine.setCategory(c);

                            if (!c.getCategoryName().equals("BREAK")) {
                                List<Exercise> exercisesByCategory = exerciseRepository.findAllByCategoryCategoryName(c.getCategoryName());
                                List<ExerciseStat> exerciseStatsToSave = new ArrayList<>();

                                exercisesByCategory.forEach(e -> {
                                    if (loggedInUser.getAge() < 20) {
                                        ExerciseStat stat = new ExerciseStat();
                                        stat.setSet(3);
                                        stat.setReps(10);
                                        stat.setExerciseWeight(10.0);
                                        stat.setExercise(e);

                                        exerciseStatsToSave.add(exerciseStatRepository.save(stat));
                                    } else if (loggedInUser.getAge() < 30) {
                                        ExerciseStat stat = new ExerciseStat();
                                        stat.setSet(3);
                                        stat.setReps(10);
                                        stat.setExerciseWeight(10.0);
                                        stat.setExercise(e);

                                        exerciseStatsToSave.add(exerciseStatRepository.save(stat));
                                    } else {
                                        ExerciseStat stat = new ExerciseStat();
                                        stat.setSet(3);
                                        stat.setReps(10);
                                        stat.setExerciseWeight(10.0);
                                        stat.setExercise(e);

                                        exerciseStatsToSave.add(exerciseStatRepository.save(stat));
                                    }
                                });
                                workoutRoutine.setExerciseStats(exerciseStatsToSave);
                            }
                        });

                        workoutRoutineRepository.save(workoutRoutine);
                    }
                    return "Sucessfully created " + loggedInUser.getGoal().getWeeklyExercise() + " workout routines!";
                } else if (loggedInUser.getGoal().getWeeklyExercise() < 3) {
                    return "Nothing yet";
                }
//            } else if (loggedInUser.getWeight() > loggedInUser.getGoal().getWeightGoal()) {
//                if (loggedInUser.getGoal().getWeeklyExercise() >= 3 && loggedInUser.getGoal().getWeeklyExercise() <= 6) {
//                    return "Nothing yet";
//
//                } else if (loggedInUser.getGoal().getWeeklyExercise() < 3) {
//                    return "Nothing yet";
//                }
//            }
        }

        return "Error while creating workout routine!";
    }
}

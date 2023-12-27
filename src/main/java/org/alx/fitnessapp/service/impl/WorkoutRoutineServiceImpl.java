package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.WorkoutRoutineDTOConverter;
import org.alx.fitnessapp.exception.WorkoutException;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.*;
import org.alx.fitnessapp.service.CategoryService;
import org.alx.fitnessapp.service.ExerciseService;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRoutineServiceImpl implements WorkoutRoutineService {
    private final UserService userService;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final CategoryService categoryService;
    private final ExerciseStatsRepository exerciseStatsRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseService exerciseService;
    private final WorkoutRoutineDTOConverter converter;
    private final CategoryRepository categoryRepository;


    @Override
    public List<WorkoutRoutine> autoCreateWorkoutRoutine() throws WorkoutException {
        User loggedInUser = userService.getLoggedUser();

        if (loggedInUser.getGoal() != null) {
            if (loggedInUser.getGoal().getWeeklyExercise() >= 3 && loggedInUser.getGoal().getWeeklyExercise() <= 6) {
                List<Category> categoriesForRoutine = categoryService.getAllCategoriesRandomized();
                List<WorkoutRoutine> savedRoutines = new ArrayList<>();

                for (int i = 0; i < loggedInUser.getGoal().getWeeklyExercise(); i++) {
                    if (!workoutRoutineRepository.existsByDateStart(LocalDate.now().plusDays(i), loggedInUser.getUsername())) {
                        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
                        workoutRoutine.setDateStart(LocalDate.now().plusDays(i));
                        workoutRoutine.setGoal(loggedInUser.getGoal());
                        workoutRoutine.setCategory(categoriesForRoutine.get(i));

                        if (!categoriesForRoutine.get(i).getCategoryName().equals("BREAK")) {
                            List<Exercise> exercises = exerciseService.getRandomExercisesByCategory(categoriesForRoutine.get(i).getCategoryName(), 5);

                            List<ExerciseStats> exerciseStats = saveExerciseStats(loggedInUser, exercises);

                            workoutRoutine.setExerciseStats(exerciseStats);
                        }
                        workoutRoutineRepository.save(workoutRoutine);
                        savedRoutines.add(workoutRoutine);
                    } else {
                        throw new WorkoutException("Already created workouts for next " + loggedInUser.getGoal().getWeeklyExercise() + " days!");
                    }
                }
                return savedRoutines;
            } else if (loggedInUser.getGoal().getWeeklyExercise() < 3) {
                Category mixedCategory = categoryService.getMixedCategory();
                List<WorkoutRoutine> savedRoutines = new ArrayList<>();
                for (int i = 0; i < loggedInUser.getGoal().getWeeklyExercise(); i++) {
                    if (!workoutRoutineRepository.existsByDateStart(LocalDate.now().plusDays(i), loggedInUser.getUsername())) {
                        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
                        workoutRoutine.setDateStart(LocalDate.now().plusDays(i));
                        workoutRoutine.setGoal(loggedInUser.getGoal());
                        workoutRoutine.setCategory(mixedCategory);

                        List<Exercise> exercises = exerciseService.getRandomExercisesWithoutCategory();

                        List<ExerciseStats> exerciseStats = saveExerciseStats(loggedInUser, exercises);

                        workoutRoutine.setExerciseStats(exerciseStats);

                        workoutRoutineRepository.save(workoutRoutine);
                        savedRoutines.add(workoutRoutine);
                    } else {
                        throw new WorkoutException("Already created workouts for next " + loggedInUser.getGoal().getWeeklyExercise() + " days!");
                    }
                }
                return savedRoutines;
            }
        }
        return null;
    }


    @Override
    public WorkoutRoutine createWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO) {
        WorkoutRoutine wroToSave = converter.convertWorkoutRoutineDTOToWorkoutRoutine(workoutRoutineDTO);
        Category cat = categoryRepository.findCategoryByCategoryName(workoutRoutineDTO.getCategoryDTO().getCategoryName());
        List<ExerciseStats> stats = new ArrayList<>();
        User loggedInUser = userService.getLoggedUser();

        for (ExerciseStats exerciseStat : wroToSave.getExerciseStats()) {
            ExerciseStats exStat = new ExerciseStats();
            Exercise e = exerciseRepository.findExerciseByCategoryCategoryNameAndExerciseName(
                    exerciseStat.getExercise().getCategory().getCategoryName(), exerciseStat.getExercise().getExerciseName());

            exStat.setExercise(e);
            exStat.setSet(exerciseStat.getSet());
            exStat.setReps(exerciseStat.getReps());
            exStat.setExerciseWeight(exerciseStat.getExerciseWeight());

            exerciseStatsRepository.save(exStat);

            stats.add(exStat);
        }

        wroToSave.setExerciseStats(stats);
        wroToSave.setCategory(cat);
        wroToSave.setGoal(loggedInUser.getGoal());

        return workoutRoutineRepository.save(wroToSave);
    }

    @Override
    public List<WorkoutRoutineDTO> getWorkoutRoutineList() {
        User user = userService.getLoggedUser();
        List<WorkoutRoutine> workoutRoutines = workoutRoutineRepository.findAllByUserId(user.getId());
        List<WorkoutRoutineDTO> dtos = new ArrayList<>();

        for (WorkoutRoutine workoutroutine : workoutRoutines) {
            WorkoutRoutineDTO dto = converter.convertWorkoutRoutineToWorkoutRoutineDTO(workoutroutine);

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public String updateWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO) {
        User loggedInUser = userService.getLoggedUser();
        Category cat = categoryRepository.findCategoryByCategoryName(workoutRoutineDTO.getCategoryDTO().getCategoryName());
        Goal goal = loggedInUser.getGoal();
        WorkoutRoutine existingData = workoutRoutineRepository.findByCategoryIdAndGoalIdAndDateStart(cat.getId(), goal.getId(), workoutRoutineDTO.getDateStart());
        WorkoutRoutine dataToSave = converter.convertWorkoutRoutineDTOToWorkoutRoutine(workoutRoutineDTO);

        List<ExerciseStats> existingExerciseStats = existingData.getExerciseStats();
        List<ExerciseStats> newExerciseStats = dataToSave.getExerciseStats();


        for (ExerciseStats newStats : newExerciseStats) {

            boolean exists = existingExerciseStats.stream()
                    .anyMatch(existingStats ->
                            existingStats.getExercise().getExerciseName().equals(newStats.getExercise().getExerciseName()) &&
                                    Objects.equals(existingStats.getExerciseWeight(), newStats.getExerciseWeight()) &&
                                    Objects.equals(existingStats.getSet(), newStats.getSet()) &&
                                    Objects.equals(existingStats.getReps(), newStats.getReps()));
            if (!exists) {
                exerciseStatsRepository.save(newStats);
                existingExerciseStats.add(newStats);
            }
        }
        existingData.setExerciseStats(existingExerciseStats);
        existingData.setDateStart(dataToSave.getDateStart());
        existingData.setDateFinish(dataToSave.getDateFinish());

        workoutRoutineRepository.save(existingData);

        return workoutRoutineDTO.getCategoryDTO().getCategoryName() + " workout updated successfully.";
    }

    @Override
    public String deleteWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO) {
        User u = userService.getLoggedUser();
        Category cat = categoryRepository.findCategoryByCategoryName(workoutRoutineDTO.getCategoryDTO().getCategoryName());

        // focuses on category, date and goal id
        WorkoutRoutine wor = workoutRoutineRepository.findByCategoryIdAndGoalIdAndDateStart(cat.getId(), u.getGoal().getId(), workoutRoutineDTO.getDateStart());

        if (wor.getExerciseStats() != null) {
            for (ExerciseStats exerciseStat : wor.getExerciseStats())
                exerciseStatsRepository.deleteById(exerciseStat.getId());
        }

        workoutRoutineRepository.deleteById(wor.getId());

        return "Successfully deleted a workout routine";
    }

    private List<ExerciseStats> saveExerciseStats(User loggedInUser, List<Exercise> exercises) {
        List<ExerciseStats> statsList = new ArrayList<>();

        for (Exercise exercise : exercises) {
            ExerciseStats stat = setExerciseStatsBasedOnAge(exercise, loggedInUser.getAge());

            exerciseStatsRepository.save(stat);

            statsList.add(stat);
        }

        return statsList;
    }

    private ExerciseStats setExerciseStatsBasedOnAge(Exercise exercise, int age) {
        ExerciseStats stat = new ExerciseStats();
        stat.setExercise(exercise);

        if (age < 20) {
            stat.setSet(3);
            stat.setReps(10);
            stat.setExerciseWeight(10.0);
        } else if (age < 30) {
            stat.setSet(4);
            stat.setReps(12);
            stat.setExerciseWeight(20.0);
        } else {
            stat.setSet(3);
            stat.setReps(10);
            stat.setExerciseWeight(12.3);
        }
        return stat;
    }
}

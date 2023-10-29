package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.DayDTOConverter;
import org.alx.fitnessapp.converter.WorkoutRoutineDTOConverter;
import org.alx.fitnessapp.exception.DailyActivityException;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.*;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;
    private final WorkoutRoutineDTOConverter workoutRoutineConverter;
    private final WorkoutRoutineService workoutRoutineService;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final ExerciseStatsRepository exerciseStatsRepository;
    private final ExerciseRepository exerciseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    // Kreirati dane za workout rutine koje se kreiraju automatski po datumu
    @Override
    public String autoCreateDay() {
        User loggedUser = userService.getLoggedUser();
        List<WorkoutRoutine> workouts = workoutRoutineRepository.findAllByUserId(loggedUser.getId());
        try {
            List<Day> days = new ArrayList<>();
            if (!workouts.isEmpty()) {
                for (WorkoutRoutine workout : workouts) {
                    Day day = new Day();
                    day.setUser(loggedUser);
                    day.setBmr(getBmr(dailyActivity(BMRCalculator(loggedUser), loggedUser), loggedUser.getGoal().getBodyTypeGoal()));
                    day.setLoggedDate(LocalDate.now());
                    day.setLoggedDate(workout.getDateStart());
                    days.add(day);
                }

                dayRepository.saveAll(days);
            }
            return "Successfully created" + days.size() + " days!";
        } catch (DailyActivityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createDay(DayDTO dayDTO) {
        User loggedUser = userService.getLoggedUser();
        try {
            Day day = new Day();
            day.setUser(loggedUser);
            day.setBmr(getBmr(dailyActivity(BMRCalculator(loggedUser), loggedUser), loggedUser.getGoal().getBodyTypeGoal()));
            day.setLoggedDate(LocalDate.now());

            if (dayDTO.getWorkoutRoutineDTO() != null) {

                workoutRoutineService.createWorkoutRoutine(dayDTO.getWorkoutRoutineDTO());

                // TBC
//                wToSave.setExerciseStats(stats);

//                workoutRoutineRepository.save(wToSave);

//                day.setWorkoutRoutine(workoutRoutineRepository.save(wToSave));
            }

            return "Successfully created a workout day!";
        } catch (DailyActivityException e) {
            throw new RuntimeException(e);
        }
    }

    private double BMRCalculator(User user) {
        double calories = 0d;
        if (user.getGender().equalsIgnoreCase(GenderEnum.MAN.getValue())) {
            if (user.getAge() == 18) {
                calories = user.getWeight() * 1.0 * 24 * 1.0;
            } else if (user.getAge() > 18 && user.getAge() <= 28) {
                calories = user.getWeight() * 1.0 * 24 * 0.95;
            } else if (user.getAge() > 28 && user.getAge() <= 38) {
                calories = user.getWeight() * 1.0 * 24 * 0.90;
            } else {
                calories = user.getWeight() * 1.0 * 24 * 0.85;
            }
        } else if (user.getGender().equalsIgnoreCase(GenderEnum.WOMAN.getValue())) {
            if (user.getAge() >= 18 && user.getAge() <= 20) {
                calories = user.getWeight() * 1.0 * 24 * 0.95;
            } else if (user.getAge() > 20 && user.getAge() <= 28) {
                calories = user.getWeight() * 1.0 * 24 * 0.90;
            } else {
                calories = user.getWeight() * 1.0 * 24 * 0.85;
            }
        }
        return calories;
    }

    private double dailyActivity(double bmr, User user) throws DailyActivityException {
        if (user.getGoal().getWeeklyExercise() == 0)
            return bmr * 1.2;
        else if (user.getGoal().getWeeklyExercise() >= 1 && user.getGoal().getWeeklyExercise() <= 3)
            return bmr * 1.55;
        else if (user.getGoal().getWeeklyExercise() >= 4 && user.getGoal().getWeeklyExercise() <= 5)
            return bmr * 1.65;
        else if (user.getGoal().getWeeklyExercise() == 6)
            return bmr * 1.8;
        else
            throw new DailyActivityException("User exercise needs to be at most 6 days per week");
    }

    private double getBmr(double bmr, String goalEnum) {
        if (BodyTypeGoalEnum.LOSE_WEIGHT.name().equals(goalEnum)) {
            return bmr - 500;
        } else if (BodyTypeGoalEnum.MAINTAIN_WEIGHT.getValue().equals(goalEnum)) {
            return bmr;
        } else {
            return bmr + 500;
        }
    }

}

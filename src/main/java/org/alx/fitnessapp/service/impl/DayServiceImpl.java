package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.DayDTOConverter;
import org.alx.fitnessapp.converter.NutritionDTOConverter;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.exception.InvalidBodyTypeGoalException;
import org.alx.fitnessapp.exception.DailyActivityException;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.*;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;
    private final WorkoutRoutineService workoutRoutineService;
    private final UserService userService;
    private final DayDTOConverter converter;
    private final NutritionDTOConverter nutritionConverter;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final ExerciseStatsRepository exerciseStatsRepository;

    @Override
    public String autoCreateDay() throws DayException {
        User loggedUser = userService.getLoggedUser();
        List<Day> days = new ArrayList<>();
        List<WorkoutRoutine> workouts = workoutRoutineService.autoCreateWorkoutRoutine();
        try {
            if (!workouts.isEmpty()) {
                for (WorkoutRoutine workout : workouts) {
                    if (!dayRepository.existsByUserIdAndLoggedDate(loggedUser.getId(), workout.getDateStart())) {
                        Day day = new Day();
                        day.setUser(loggedUser);
                        double bmr = BMRCalculator(loggedUser);
                        double bmrWithGoal = getBmr(dailyActivity(bmr, loggedUser), loggedUser.getGoal().getBodyTypeGoal());
                        DecimalFormat format = new DecimalFormat("0.#");
                        double formatted = Double.parseDouble(format.format(bmrWithGoal));

                        day.setBmr(formatted);
                        day.setLoggedDate(workout.getDateStart());
                        day.setWorkoutRoutine(workout);
                        days.add(day);
                    } else {
                        throw new DayException("Days already exist!");
                    }
                }

                dayRepository.saveAll(days);
            }
            return days.size() + " days created!";
        } catch (DailyActivityException | InvalidBodyTypeGoalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createDay(DayDTO dayDTO) {
        User loggedUser = userService.getLoggedUser();
        try {
            Day day = new Day();
            day.setUser(loggedUser);

            double bmr = BMRCalculator(loggedUser);
            double bmrWithGoal = getBmr(dailyActivity(bmr, loggedUser), loggedUser.getGoal().getBodyTypeGoal());
            DecimalFormat format = new DecimalFormat("0.#");
            double formatted = Double.parseDouble(format.format(bmrWithGoal));

            day.setBmr(formatted);

            day.setLoggedDate(LocalDate.now());

            if (dayDTO.getWorkoutRoutineDTO() != null) {
                WorkoutRoutine wro = workoutRoutineService.createWorkoutRoutine(dayDTO.getWorkoutRoutineDTO());
                day.setWorkoutRoutine(wro);
            }

            dayRepository.save(day);

            return "Successfully created a workout day!";
        } catch (DailyActivityException | InvalidBodyTypeGoalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DayDTO> getDays() {
        User loggedUser = userService.getLoggedUser();
        List<Day> day = dayRepository.findAllByUserId(loggedUser.getId());

        List<DayDTO> dtos = new ArrayList<>();
        for (Day d : day) {
            dtos.add(converter.convertDayToDayDTO(d));
        }
        return dtos;
    }

    @Override
    public String deleteDay(DayDTO dayDTO) {
        User loogedUser = userService.getLoggedUser();
        Day day = dayRepository.findDayByUserIdAndLoggedDate(loogedUser.getId(), dayDTO.getLoggedDate());
        if (day != null) {
            LocalDate date = day.getLoggedDate();

            WorkoutRoutine wro = day.getWorkoutRoutine();

            dayRepository.delete(day);
            exerciseStatsRepository.deleteAll(wro.getExerciseStats());
            workoutRoutineRepository.delete(wro);
            return "Deleted " + date;
        }
        return "Day you're trying to delete, doesn't exist";
    }

    @Override
    public DayDTO getDayByDate(LocalDate date) {
        User loggedUser = userService.getLoggedUser();

        return converter.convertDayToDayDTO(dayRepository.findDayByUserIdAndLoggedDate(loggedUser.getId(), date));
    }

    @Override
    public NutritionDTO getOverallNutrition() {
        User loggedUser = userService.getLoggedUser();
        List<Day> allDays = dayRepository.findAllByUserId(loggedUser.getId());

        Nutrition overallNutrition = new Nutrition();

        double overallCalories = 0.0;
        double overallProtein = 0.0;
        double overallCarbs = 0.0;
        double overallFat = 0.0;

        for (Day day : allDays) {
            Nutrition nutrition = day.getNutrition();

            if (nutrition != null) {
                overallCalories += nutrition.getCalories();
                overallProtein += nutrition.getProtein();
                overallCarbs += nutrition.getCarbs();
                overallFat += nutrition.getFat();
            }
        }

        overallNutrition.setCalories(overallCalories);
        overallNutrition.setProtein(overallProtein);
        overallNutrition.setCarbs(overallCarbs);
        overallNutrition.setFat(overallFat);

        return nutritionConverter.convertNutritionToNutritionDTO(overallNutrition);
    }

    @Override
    public BmiDTO calculateBmi() {
        User loggedUser = userService.getLoggedUser();
        BmiDTO bmi = new BmiDTO();

        double heightInMeters = loggedUser.getHeight() / 100.0;
        bmi.setBmi(loggedUser.getWeight() / (heightInMeters * heightInMeters));

        if (bmi.getBmi() < 18.5) {
            bmi.setBmiCategory("Underweight");
        } else if (bmi.getBmi() < 24.9) {
            bmi.setBmiCategory("Normal weight");
        } else if (bmi.getBmi() < 29.9) {
            bmi.setBmiCategory("Overweight");
        } else {
            bmi.setBmiCategory("Obese");
        }

        return bmi;
    }

    private double BMRCalculator(User user) {
        double calories = 0d;
        if (user.getGender().equalsIgnoreCase(GenderEnum.MALE.getValue())) {
            calories = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());
        } else if (user.getGender().equalsIgnoreCase(GenderEnum.FEMALE.getValue())) {
            calories = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
        }
        return calories;
    }

    private double dailyActivity(double bmr, User user) throws DailyActivityException {
        if (user.getGoal().getWeeklyExercise() == 0)
            return bmr * 1.2;
        else if (user.getGoal().getWeeklyExercise() >= 1 && user.getGoal().getWeeklyExercise() <= 3)
            return bmr * 1.375;
        else if (user.getGoal().getWeeklyExercise() >= 4 && user.getGoal().getWeeklyExercise() <= 5)
            return bmr * 1.55;
        else if (user.getGoal().getWeeklyExercise() == 6)
            return bmr * 1.725;
        else
            throw new DailyActivityException("User exercise needs to be at most 6 days per week");
    }

    private double getBmr(double bmr, String goalEnum) throws InvalidBodyTypeGoalException {
        if (BodyTypeGoalEnum.LOSE_WEIGHT.name().equals(goalEnum)) {
            return bmr - 500;
        } else if (BodyTypeGoalEnum.MAINTAIN_WEIGHT.name().equals(goalEnum)) {
            return bmr;
        } else if (BodyTypeGoalEnum.GAIN_WEIGHT.name().equals(goalEnum)) {
            return bmr + 300;
        } else {
            throw new InvalidBodyTypeGoalException("Body type goal is invalid");
        }
    }

}

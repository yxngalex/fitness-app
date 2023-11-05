package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.DayDTOConverter;
import org.alx.fitnessapp.exception.BodyTypeGoalException;
import org.alx.fitnessapp.exception.DailyActivityException;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.GenderEnum;
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
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseStatsRepository exerciseStatsRepository;

    @Override
    public String autoCreateDay() throws Exception {
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
                        throw new Exception("Days already exist!");
                    }
                }

                dayRepository.saveAll(days);
            }
            return days.size() + " days created!";
        } catch (DailyActivityException | BodyTypeGoalException e) {
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
        } catch (DailyActivityException | BodyTypeGoalException e) {
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
        return "Day, you're trying to delete, doesn't exist";
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

    private double getBmr(double bmr, String goalEnum) throws BodyTypeGoalException {
        if (BodyTypeGoalEnum.LOSE_WEIGHT.name().equals(goalEnum)) {
            return bmr - 500;
        } else if (BodyTypeGoalEnum.MAINTAIN_WEIGHT.name().equals(goalEnum)) {
            return bmr;
        } else if (BodyTypeGoalEnum.GAIN_WEIGHT.name().equals(goalEnum)) {
            return bmr + 300;
        } else {
            throw new BodyTypeGoalException("Body type goal is invalid");
        }
    }

}

package org.alx.fitnessapp.service;

import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.model.dto.BmiDTO;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.NutritionDTO;

import java.time.LocalDate;
import java.util.List;

public interface DayService {

    String autoCreateDay() throws DayException;

    String createDay(DayDTO dayDTO);

    List<DayDTO> getDays();

    String deleteDay(DayDTO dayDTO);

    DayDTO getDayByDate(LocalDate date);

    NutritionDTO getOverallNutrition();

    BmiDTO calculateBmi();

    DayDTO getClosestDay(LocalDate date);
}

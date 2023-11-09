package org.alx.fitnessapp.service;

import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.model.dto.DayDTO;

import java.util.List;

public interface DayService {

    String autoCreateDay() throws DayException;
    String createDay(DayDTO dayDTO);
    List<DayDTO> getDays();
    String deleteDay(DayDTO dayDTO);
}

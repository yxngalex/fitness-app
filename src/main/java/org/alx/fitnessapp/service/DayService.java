package org.alx.fitnessapp.service;

import org.alx.fitnessapp.exception.DayExceptionAbstract;
import org.alx.fitnessapp.model.dto.DayDTO;

import java.util.List;

public interface DayService {

    String autoCreateDay() throws DayExceptionAbstract;
    String createDay(DayDTO dayDTO);
    List<DayDTO> getDays();
    String deleteDay(DayDTO dayDTO);
}

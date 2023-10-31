package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.DayDTO;

import java.util.List;

public interface DayService {

    String autoCreateDay() throws Exception;
    String createDay(DayDTO dayDTO);
    List<DayDTO> getDays();
}

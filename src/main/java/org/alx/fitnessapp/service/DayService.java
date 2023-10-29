package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.DayDTO;

public interface DayService {

    String autoCreateDay();
    String createDay(DayDTO dayDTO);
}

package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.entity.Day;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public DayDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public DayDTO convertDayToDayDTO(Day day) {
        return modelMapper.map(day, DayDTO.class);
    }

    public Day convertDayDTOToDay(DayDTO dto) {
        return modelMapper.map(dto, Day.class);
    }
}

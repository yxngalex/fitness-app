package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoalDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public GoalDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public GoalDTO convertGoalToGoalDTO(Goal goal) {
        return modelMapper.map(goal, GoalDTO.class);
    }

    public Goal convertGoalDTOToGoal(GoalDTO dto) {
        return modelMapper.map(dto, Goal.class);
    }
}

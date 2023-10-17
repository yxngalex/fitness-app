package org.alx.fitnessapp.converter;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalDTOConverter {
    private final ModelMapper modelMapper;

    public GoalDTO convertGoalToGoalDTO(Goal goal) {
        return modelMapper.map(goal, GoalDTO.class);
    }

    public Goal convertGoalDTOToGoal(GoalDTO dto) {
        return modelMapper.map(dto, Goal.class);
    }
}

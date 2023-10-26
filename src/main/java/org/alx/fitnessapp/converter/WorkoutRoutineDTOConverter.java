package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutRoutineDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public WorkoutRoutineDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public WorkoutRoutineDTO convertWorkoutRoutineToWorkoutRoutineDTO(WorkoutRoutine workoutRoutine) {
        return modelMapper.map(workoutRoutine, WorkoutRoutineDTO.class);
    }

    public WorkoutRoutine convertWorkoutRoutineDTOToWorkoutRoutine(WorkoutRoutineDTO dto) {
        return modelMapper.map(dto, WorkoutRoutine.class);
    }
}

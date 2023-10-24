package org.alx.fitnessapp.converter;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkoutRoutineDTOConverter {
    private final ModelMapper modelMapper;

    public WorkoutRoutineDTO convertWorkoutRoutineToWorkoutRoutineDTO(WorkoutRoutine workoutRoutine) {
        return modelMapper.map(workoutRoutine, WorkoutRoutineDTO.class);
    }

    public WorkoutRoutine convertWorkoutRoutineDTOToWorkoutRoutine(WorkoutRoutineDTO dto) {
        return modelMapper.map(dto, WorkoutRoutine.class);
    }
}

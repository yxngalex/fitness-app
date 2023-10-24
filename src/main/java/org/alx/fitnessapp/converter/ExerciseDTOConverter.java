package org.alx.fitnessapp.converter;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseDTOConverter {
    private final ModelMapper modelMapper;

    public ExerciseDTO convertExerciseToExerciseDTO(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDTO.class);
    }

    public Exercise convertExerciseDTOToExercise(ExerciseDTO dto) {
        return modelMapper.map(dto, Exercise.class);
    }
}

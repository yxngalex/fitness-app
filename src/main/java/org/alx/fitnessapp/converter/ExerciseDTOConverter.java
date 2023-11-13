package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.model.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public ExerciseDTO convertExerciseToExerciseDTO(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDTO.class);
    }

    public Exercise convertExerciseDTOToExercise(ExerciseDTO dto) {
        return modelMapper.map(dto, Exercise.class);
    }
}

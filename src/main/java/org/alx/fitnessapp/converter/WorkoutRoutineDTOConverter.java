package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.alx.fitnessapp.util.ImageUtil;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutRoutineDTOConverter {

    private final ModelMapper modelMapper;
    private final CategoryDTOConverter categoryDTOConverter;

    @Autowired
    public WorkoutRoutineDTOConverter(ModelMapper modelMapper, CategoryDTOConverter categoryDTOConverter) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
        this.categoryDTOConverter = categoryDTOConverter;

        modelMapper.addConverter(new ExerciseToDTOConverter(categoryDTOConverter));
        modelMapper.addConverter(new ExerciseDTOToEntityConverter());
    }

    public WorkoutRoutineDTO convertWorkoutRoutineToWorkoutRoutineDTO(WorkoutRoutine workoutRoutine) {
        return modelMapper.map(workoutRoutine, WorkoutRoutineDTO.class);
    }

    public WorkoutRoutine convertWorkoutRoutineDTOToWorkoutRoutine(WorkoutRoutineDTO dto) {
        return modelMapper.map(dto, WorkoutRoutine.class);
    }

    private static class ExerciseToDTOConverter extends AbstractConverter<Exercise, ExerciseDTO> {
        private final CategoryDTOConverter categoryDTOConverter;

        public ExerciseToDTOConverter(CategoryDTOConverter categoryDTOConverter) {
            this.categoryDTOConverter = categoryDTOConverter;
        }

        @Override
        protected ExerciseDTO convert(Exercise source) {
            if (source == null)
                return null;

            ExerciseDTO dto = new ExerciseDTO();
            dto.setCategoryDTO(categoryDTOConverter.convertCategoryToCategoryDTO(source.getCategory()));
            dto.setExerciseName(source.getExerciseName());
            dto.setExerciseDescription(source.getExerciseDescription());

            String base64Image = ImageUtil.convertBlobToBase64(source.getImage());
            dto.setImage(base64Image);

            return dto;
        }
    }

    private class ExerciseDTOToEntityConverter extends AbstractConverter<ExerciseDTO, Exercise> {
        @Override
        protected Exercise convert(ExerciseDTO source) {
            if (source == null) {
                return null;
            }

            Exercise exercise = new Exercise();
            exercise.setCategory(categoryDTOConverter.convertCategoryDTOToCategory(source.getCategoryDTO()));
            exercise.setExerciseName(source.getExerciseName());
            exercise.setExerciseDescription(source.getExerciseDescription());

            if (source.getImage() != null) {
                byte[] img = ImageUtil.convertBase64ToBlob(source.getImage());
                exercise.setImage(ImageUtil.createBlob(img));
            }

            return exercise;
        }
    }
}

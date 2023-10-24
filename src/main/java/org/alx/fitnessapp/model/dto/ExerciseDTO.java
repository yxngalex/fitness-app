package org.alx.fitnessapp.model.dto;

import lombok.Data;
import org.alx.fitnessapp.model.entity.Category;

@Data
public class ExerciseDTO {
    private CategoryDTO categoryDTO;
    private String exerciseName;
    private String exerciseDescription;
}

package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.ExerciseDTOConverter;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.repository.ExerciseRepository;
import org.alx.fitnessapp.service.ExerciseService;
import org.alx.fitnessapp.util.ImageUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final EntityManager entityManager;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseDTOConverter converter;

    @Override
    @Transactional
    public List<Exercise> getRandomExercisesByCategory(String categoryName, int limit) {

        Query q = entityManager.createQuery("SELECT e FROM Exercise e JOIN Category c ON e.category.id = c.id WHERE c.categoryName = :categoryName ORDER BY RAND()");
        q.setParameter("categoryName", categoryName);
        q.setMaxResults(limit);

        return (List<Exercise>) q.getResultList();
    }

    @Override
    public List<Exercise> getRandomExercisesWithoutCategory() {
        Query q = entityManager.createQuery("SELECT e FROM Exercise e ORDER BY RAND()");
        q.setMaxResults(5);

        return (List<Exercise>) q.getResultList();
    }

    @Override
    public List<ExerciseDTO> getAllExercise() {
        List<Exercise> exercises = exerciseRepository.findAll();
        List<ExerciseDTO> dtos = new ArrayList<>();

        for (Exercise exercise : exercises) {
            ExerciseDTO ex = converter.convertExerciseToExerciseDTO(exercise);
//            ex.setImage(ImageUtil.convertImageToBase64(exercise.getImage()));
            dtos.add(ex);
        }

        return dtos;
    }

    @Override
    public List<ExerciseDTO> getExerciseAutoComplete(String value) {
        List<ExerciseDTO> exerciseList = getAllExercise();

        return exerciseList.stream()
                .filter(f -> f.getExerciseName().toLowerCase(Locale.ROOT).startsWith(value.toLowerCase(Locale.ROOT))
                        || f.getCategoryDTO().getCategoryName().toLowerCase(Locale.ROOT).startsWith(value.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }
}

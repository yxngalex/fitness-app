package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.repository.ExerciseRepository;
import org.alx.fitnessapp.service.ExerciseService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final EntityManager entityManager;
    private final ExerciseRepository exerciseRepository;

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
}

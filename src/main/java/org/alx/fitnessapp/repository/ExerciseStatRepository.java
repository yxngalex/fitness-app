package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.ExerciseStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseStatRepository extends JpaRepository<ExerciseStat, Integer> {
}

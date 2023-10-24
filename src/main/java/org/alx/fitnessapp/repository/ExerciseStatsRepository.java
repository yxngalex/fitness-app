package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.ExerciseStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseStatsRepository extends JpaRepository<ExerciseStats, Integer> {
}

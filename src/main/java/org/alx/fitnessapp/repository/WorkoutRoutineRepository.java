package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Integer> {

}

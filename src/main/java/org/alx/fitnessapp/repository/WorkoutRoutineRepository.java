package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Integer> {


    WorkoutRoutine findByCategoryIdAndGoalIdAndDateStart(int catId, int goalId, LocalDate dateStart);
    List<WorkoutRoutine> findAllByGoalId(int id);

}

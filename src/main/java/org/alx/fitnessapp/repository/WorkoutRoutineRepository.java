package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Integer> {


    WorkoutRoutine findByCategoryIdAndGoalIdAndDateStart(int catId, int goalId, LocalDate dateStart);

    List<WorkoutRoutine> findAllByGoalId(int id);

    @Query("SELECT w FROM WorkoutRoutine w JOIN User u ON w.goal.id = u.goal.id WHERE u.id = :id ORDER BY w.dateStart ASC")
    List<WorkoutRoutine> findAllByUserId(@Param("id") int id);

    Boolean existsByDateStart(LocalDate date);

}

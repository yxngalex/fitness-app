package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "workout_routine")
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKOUT_ROUTINE_ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CATEGORY_ID")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "workout_routine_exercise_stats",
            joinColumns = @JoinColumn(name = "FK_WORKOUT_ROUTINE_ID"),
            inverseJoinColumns = @JoinColumn(name = "FK_EXERCISE_STATS_ID"))
    private List<ExerciseStats> exerciseStats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_GOAL_ID")
    private Goal goal;

    @Column(name = "DATE_START")
    private LocalDate dateStart;

    @Column(name = "DATE_FINISH")
    private LocalDate dateFinish;

}
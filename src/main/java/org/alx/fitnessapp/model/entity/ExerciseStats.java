package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exercise_stats")
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_STATS_ID", nullable = false)
    private Integer id;

//    @ManyToMany(mappedBy = "exerciseStats", cascade = CascadeType.ALL)
//    private List<WorkoutRoutine> workoutRoutines;
    @ManyToMany(mappedBy = "exerciseStats")
    private List<WorkoutRoutine> workoutRoutines;

    @Column(name = "`SET`")
    private Integer set;

    @Column(name = "REPS")
    private Integer reps;

    @Column(name = "EXERCISE_WEIGHT", precision = 10)
    private Double exerciseWeight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_EXERCISE_ID")
    private Exercise exercise;


}
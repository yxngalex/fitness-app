package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "day")
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAY_ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "FK_NUTRITION_ID", nullable = false)
    private Nutrition nutrition;

    @ManyToOne
    @JoinColumn(name = "FK_WORKOUT_ROUTINE_ID", nullable = false)
    private WorkoutRoutine workoutRoutine;

    @Column(name = "LOGGED_DATE")
    private LocalDate loggedDate;

    @Column(name = "BMR")
    private Double bmr;
}
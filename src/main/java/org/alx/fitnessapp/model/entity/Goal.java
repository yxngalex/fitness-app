package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GOAL_ID", nullable = false)
    private Integer id;

    @Column(name = "WEIGHT_GOAL", precision = 10)
    private Double weightGoal;

    @Column(name = "BODY_TYPE_GOAL")
    private String bodyTypeGoal;

    @Column(name = "WEEKLY_EXERCISE")
    private Integer weeklyExercise;

}
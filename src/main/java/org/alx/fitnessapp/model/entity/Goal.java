package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    private User user;

    @Column(name = "WEIGHT_GOAL", precision = 10)
    private Double weightGoal;

    @Column(name = "BODY_TYPE_GOAL")
    private String bodyTypeGoal;

    @Column(name = "WEEKLY_EXERCISE")
    private Integer weeklyExercise;

}
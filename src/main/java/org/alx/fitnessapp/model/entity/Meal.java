package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "meal")
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEAL_ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_DAY_ID", nullable = false)
    private Day Day;

    @ManyToMany
    @JoinTable(name = "food_meal", joinColumns = @JoinColumn(name = "FK_MEAL_ID"), inverseJoinColumns = @JoinColumn(name = "FK_FOOD_ID"))
    private List<Food> foodList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "FK_NUTRITION_ID")
    private Nutrition nutrition;

    @Column(name = "MEAL_NAME")
    private String mealName;

}
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
@Table(name = "nutrition")
@NoArgsConstructor
@AllArgsConstructor
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUTRITION_ID", nullable = false)
    private Integer id;

    @Column(name = "CALORIES", precision = 10)
    private Double calories;

    @Column(name = "PROTEIN", precision = 10)
    private Double protein;

    @Column(name = "CARBS", precision = 10)
    private Double carbs;

    @Column(name = "FAT", precision = 10)
    private Double fat;

}
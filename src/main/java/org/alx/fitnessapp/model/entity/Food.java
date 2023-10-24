package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "food")
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_ID", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "FK_NUTRITION_ID", nullable = false)
    private Nutrition nutrition;

    @ManyToMany(mappedBy = "foodList")
    private List<Meal> mealList;

    @Lob
    @Column(name = "FOOD_NAME")
    private String foodName;

    @Lob
    @Column(name = "FOOD_GROUP")
    private String foodGroup;

    @Column(name = "SERVING")
    private Integer serving;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

}
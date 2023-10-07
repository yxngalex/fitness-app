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
@Where(clause = "WHERE IS_DELETED is null or IS_DELETED = false")
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

    @Column(name = "LOGGED_DATE")
    private LocalDate loggedDate;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

}
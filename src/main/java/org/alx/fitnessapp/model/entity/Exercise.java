package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "exercise")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "WHERE IS_DELETED is null or IS_DELETED = false")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CATEGORY_ID", nullable = false)
    private Category category;

    @Column(name = "EXERCISE_NAME")
    private String exerciseName;

    @Column(name = "EXERCISE_DESCRIPTION")
    private String exerciseDescription;

    @Column(name = "FAVORITED")
    private Boolean favorited;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

}
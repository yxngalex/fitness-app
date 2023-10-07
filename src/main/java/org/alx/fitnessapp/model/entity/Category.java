package org.alx.fitnessapp.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Lob
    @Column(name = "CATEGORY_DESCRIPTION")
    private String categoryDescription;

}
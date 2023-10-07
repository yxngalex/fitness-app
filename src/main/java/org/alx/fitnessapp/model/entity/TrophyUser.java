package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "trophy_user")
@NoArgsConstructor
@AllArgsConstructor
public class TrophyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TROPHY_USER_ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TROPHY_ID")
    private Trophy trophy;

    @Column(name = "IS_ACHIEVED")
    private Boolean isAchieved;

    @Column(name = "DATE_ACHIEVED")
    private LocalDate dateAchieved;

}
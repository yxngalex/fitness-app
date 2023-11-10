package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TrophyUser> trophyUsers = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "FK_GOAL_ID")
    private Goal goal;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "HEIGHT", nullable = false)
    private Integer height;

    @Column(name = "WEIGHT", precision = 10, nullable = false)
    private Double weight;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "GENDER", nullable = false)
    private String gender;

}
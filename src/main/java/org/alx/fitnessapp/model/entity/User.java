package org.alx.fitnessapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
@Where(clause = "WHERE IS_DELETED is null or IS_DELETED = false")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TrophyUser> trophyUsers = new ArrayList<>();

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HEIGHT", precision = 10)
    private Double height;

    @Column(name = "WEIGHT", precision = 10)
    private Double weight;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "CURRENT_BODY_TYPE")
    private String currentBodyType;

}
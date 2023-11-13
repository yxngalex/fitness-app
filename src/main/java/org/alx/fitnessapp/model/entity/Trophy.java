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
@Table(name = "trophy")
@NoArgsConstructor
@AllArgsConstructor
public class Trophy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TROPHY_ID", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "trophy", cascade = CascadeType.ALL)
    private List<TrophyUser> trophyUsers;

    @Column(name = "TROPHY_NAME")
    private String trophyName;

    @Column(name = "TROPHY_DESCRIPTION")
    private String trophyDescription;

    @Column(name = "TROPHY_IMAGE")
    private String trophyImage;

}
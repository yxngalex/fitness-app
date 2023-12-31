package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Integer height;
    private Double weight;
    private Integer age;
    private String gender;
    private GoalDTO goal;
}

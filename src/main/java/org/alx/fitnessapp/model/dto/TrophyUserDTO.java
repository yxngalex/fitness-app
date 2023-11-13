package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrophyUserDTO {
    private TrophyDTO trophy;
    private LocalDate dateAchieved;
    private Boolean isAchieved;

}

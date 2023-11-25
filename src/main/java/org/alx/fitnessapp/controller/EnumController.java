package org.alx.fitnessapp.controller;

import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enum")
public class EnumController {

    @GetMapping("/gender")
    public ResponseEntity<List<GenderEnum>> getGenders() {
        return ResponseEntity.ok(Arrays.asList(GenderEnum.values()));
    }

    @GetMapping("/bodyType")
    public ResponseEntity<List<BodyTypeGoalEnum>> getBodyTypeGoals() {
        return ResponseEntity.ok(Arrays.asList(BodyTypeGoalEnum.values()));
    }
}

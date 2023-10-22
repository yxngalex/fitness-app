package org.alx.fitnessapp.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class WorkoutRoutineController {
    private final WorkoutRoutineService workoutRoutineService;

    @PostMapping("/create")
    public ResponseEntity<String> createWorkoutRoutineWithGoal() {
        return ResponseEntity.ok(workoutRoutineService.createWorkoutRoutineWithGoal());
    }
}

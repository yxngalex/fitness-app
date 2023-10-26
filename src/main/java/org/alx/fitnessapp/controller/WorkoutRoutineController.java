package org.alx.fitnessapp.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.service.ExerciseService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class WorkoutRoutineController {
    private final WorkoutRoutineService workoutRoutineService;

    @PostMapping("/create/auto")
    public ResponseEntity<String> autoCreateWorkoutRoutine() {
        return ResponseEntity.ok(workoutRoutineService.autoCreateWorkoutRoutine());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.createWorkoutRoutine(workoutRoutineDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<WorkoutRoutineDTO>> getWorkoutRoutineList() {
        return ResponseEntity.ok(workoutRoutineService.getWorkoutRoutineList());
    }

    @PostMapping("/update")
    public ResponseEntity<WorkoutRoutineDTO> updateWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.updateWorkoutRoutine(workoutRoutineDTO));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.deleteWorkoutRoutine(workoutRoutineDTO));
    }
}

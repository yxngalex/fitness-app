package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.WorkoutRoutineDTOConverter;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class WorkoutRoutineController {
    private final WorkoutRoutineService workoutRoutineService;
    private final WorkoutRoutineDTOConverter converter;

    @PostMapping("/create/auto")
    public ResponseEntity<List<WorkoutRoutineDTO>> autoCreateWorkoutRoutine() {
        List<WorkoutRoutineDTO> convertedWors = new ArrayList<>();
        try {
            for (WorkoutRoutine workoutRoutine : workoutRoutineService.autoCreateWorkoutRoutine()) {
                WorkoutRoutineDTO wroDTO = converter.convertWorkoutRoutineToWorkoutRoutineDTO(workoutRoutine);
                convertedWors.add(wroDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(convertedWors);
    }

    @PostMapping("/create")
    public ResponseEntity<WorkoutRoutineDTO> createWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(converter.convertWorkoutRoutineToWorkoutRoutineDTO(workoutRoutineService.createWorkoutRoutine(workoutRoutineDTO)));
    }

    @GetMapping("/get")
    public ResponseEntity<List<WorkoutRoutineDTO>> getWorkoutRoutineList() {
        return ResponseEntity.ok(workoutRoutineService.getWorkoutRoutineList());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.updateWorkoutRoutine(workoutRoutineDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.deleteWorkoutRoutine(workoutRoutineDTO));
    }
}

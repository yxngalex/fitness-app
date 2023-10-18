package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping("/create")
    public ResponseEntity<String> createGoal(@RequestBody GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.createGoal(goalDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<GoalDTO> getGoal() {
        return ResponseEntity.ok(goalService.getGoal());
    }

    @PostMapping("/update")
    public ResponseEntity<GoalDTO> updateGoal(@RequestBody GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.updateGoal(goalDTO));
    }

    @PostMapping("/delete/{goalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable Integer goalId) {
        return ResponseEntity.ok(goalService.deleteGoal(goalId));
    }
}

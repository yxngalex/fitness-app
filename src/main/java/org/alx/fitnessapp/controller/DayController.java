package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.service.DayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/day")
@RequiredArgsConstructor
public class DayController {
    private final DayService dayService;

    @PostMapping("/auto")
    public ResponseEntity<String> autoCreateDay() {
        try {
            return ResponseEntity.ok(dayService.autoCreateDay());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDay(@RequestBody DayDTO dayDTO) {
        return ResponseEntity.ok(dayService.createDay(dayDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<DayDTO>> getDaysForUser() {
        return ResponseEntity.ok(dayService.getDays());
    }
}

package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.exception.WorkoutException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.service.DayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        } catch (WorkoutException | DayException e) {
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

    @GetMapping("/getByDate")
    public ResponseEntity<DayDTO> getDayByDate(@RequestParam("date")
                                               @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return ResponseEntity.ok(dayService.getDayByDate(date));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDay(@RequestBody DayDTO dayDTO) {
        return ResponseEntity.ok(dayService.deleteDay(dayDTO));
    }
}

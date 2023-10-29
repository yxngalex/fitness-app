package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.service.DayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/day")
@RequiredArgsConstructor
public class DayController {
    private final DayService dayService;

    @PostMapping("/create")
    public ResponseEntity<String> createDay(@RequestBody DayDTO dayDTO) {
        return ResponseEntity.ok(dayService.createDay(dayDTO));
    }
}

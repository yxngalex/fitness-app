package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.TrophyUserDTO;
import org.alx.fitnessapp.service.TrophyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trophy")
@RequiredArgsConstructor
public class TrophyController {

    private final TrophyService trophyService;

    @PostMapping("/welcome")
    public ResponseEntity<TrophyUserDTO> welcomeAchievement() {
        return ResponseEntity.ok(trophyService.achieveWelcome());
    }

}

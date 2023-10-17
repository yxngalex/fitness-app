package org.alx.fitnessapp.controller;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.service.GoalService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addGoal(@RequestBody UserDTO userDTO) {
        Assert.notNull(userDTO);
        Assert.notNull(userDTO.getGoals());

        return new ResponseEntity<>(goalService.updateGoalList(userDTO), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<GoalDTO>> getGoal(@RequestBody UserDTO userDTO) {
        Assert.notNull(userDTO);

        return new ResponseEntity<>(userService.getGoals(userDTO), HttpStatus.OK);
    }
}

package com.springboot.clickerapplication.controller;

import com.springboot.clickerapplication.dto.BallDto;
import com.springboot.clickerapplication.service.BallService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ball")
@RequiredArgsConstructor
public class BallController {

    private final BallService ballService;

    @GetMapping("/shop/all")
    public ResponseEntity<List<BallDto>> getAllBalls(){
        return ResponseEntity.ok(ballService.getAllBall());
    }
}

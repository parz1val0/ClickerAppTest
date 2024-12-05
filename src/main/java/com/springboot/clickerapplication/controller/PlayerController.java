package com.springboot.clickerapplication.controller;

import com.springboot.clickerapplication.dto.PlayerDto;
import com.springboot.clickerapplication.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/shop/all")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        return ResponseEntity.ok(playerService.getAllPlayer());
    }
}

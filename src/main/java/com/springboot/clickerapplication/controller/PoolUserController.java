package com.springboot.clickerapplication.controller;

import com.springboot.clickerapplication.dto.UserPoolWithUserBalanceDto;
import com.springboot.clickerapplication.service.UserpoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pool_users")
@RequiredArgsConstructor
public class PoolUserController {
    private final UserpoolService userpoolService;

    @GetMapping(path = "/get/{telegramid}")
    public ResponseEntity<UserPoolWithUserBalanceDto> getUserPoolWithUserBalanceDto(@PathVariable String telegramid){
        return ResponseEntity.ok(userpoolService.getUserPoolWithUserBalance(telegramid));
    }
}

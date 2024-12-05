package com.springboot.clickerapplication.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String telegramId;
    private String username;
    private double balance;
    private int currentBallId;
    private int currentPlayerId;
    private int counterFriend;
    private Long energy;
    private boolean isAdmin=false;
    private int dailyBonus;
}

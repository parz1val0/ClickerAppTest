package com.springboot.clickerapplication.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForAdminDto {
    private String telegramId;
    private double balance;
    private Long energy;
    private String email;
    private String phone;
}

package com.springboot.clickerapplication.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBalanceDto {
    private Double balance;
    private Long energy;
}

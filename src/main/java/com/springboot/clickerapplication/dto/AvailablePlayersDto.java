package com.springboot.clickerapplication.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AvailablePlayersDto {
    private Long id;
    private String name;
    private BigDecimal value;
    private String photo;
}


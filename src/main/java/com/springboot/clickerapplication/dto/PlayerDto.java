package com.springboot.clickerapplication.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlayerDto {
    private Long id;
    private String name;
    private Integer price;
    private BigDecimal value;
    private String photo;
}

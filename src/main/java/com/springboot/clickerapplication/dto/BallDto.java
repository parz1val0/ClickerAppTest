package com.springboot.clickerapplication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BallDto {
    private Long id;
    private String name;
    private int price;
    private String photo;
}

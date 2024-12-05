package com.springboot.clickerapplication.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AvailableBallsDto {
    private Long id;
    private String name;
    private String photo;
}

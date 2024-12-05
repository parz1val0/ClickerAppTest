package com.springboot.clickerapplication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PoolUserDto {
    private int amount;
    private String nameSurname;
    private String photo;
}

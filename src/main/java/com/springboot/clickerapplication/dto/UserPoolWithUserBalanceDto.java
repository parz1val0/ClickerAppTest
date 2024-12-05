package com.springboot.clickerapplication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPoolWithUserBalanceDto {
    private Double balance;
    private List<PoolUserDto> poolUserDtoList;
}

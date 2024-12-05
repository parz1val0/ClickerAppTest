package com.springboot.clickerapplication.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeDto {
    private String promoCode;
    private Double value;
}

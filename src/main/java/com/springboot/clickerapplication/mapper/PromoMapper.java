package com.springboot.clickerapplication.mapper;

import com.springboot.clickerapplication.dto.PromoCodeDto;
import com.springboot.clickerapplication.models.Promo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PromoMapper {

    default PromoCodeDto fromPromoToDto(Promo promo) {
        if (promo == null) {
            return null;
        }
        return PromoCodeDto.builder()
                .promoCode(promo.getPromoCode())
                .value(promo.getValue())
                .build();
    }

    default Promo fromDtoToPromo(PromoCodeDto promoCodeDto) {
        if (promoCodeDto == null) {
            return null;
        }
        return Promo.builder()
                .promoCode(promoCodeDto.getPromoCode())
                .value(promoCodeDto.getValue())
                .build();
    }

    default List<PromoCodeDto> fromPromoListToDtoList(List<Promo> promos) {
        if (promos == null || promos.isEmpty()) {
            return List.of();
        }
        return promos.stream()
                .map(this::fromPromoToDto)
                .collect(Collectors.toList());
    }

    default List<Promo> fromDtoListToPromoList(List<PromoCodeDto> promoCodeDtos) {
        if (promoCodeDtos == null || promoCodeDtos.isEmpty()) {
            return List.of();
        }
        return promoCodeDtos.stream()
                .map(this::fromDtoToPromo)
                .collect(Collectors.toList());
    }
}


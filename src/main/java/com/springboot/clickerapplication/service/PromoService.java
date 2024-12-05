package com.springboot.clickerapplication.service;

import com.springboot.clickerapplication.dto.PromoCodeDto;
import com.springboot.clickerapplication.mapper.PromoMapper;
import com.springboot.clickerapplication.models.Promo;
import com.springboot.clickerapplication.repository.PromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoService {
    private final PromoRepository promoRepository;
    private final PromoMapper promoMapper;

    @Transactional
    public boolean createPromo(PromoCodeDto promoCodeDto){
        Promo promo = promoMapper.fromDtoToPromo(promoCodeDto);
        promoRepository.save(promo);
        return promoRepository.findByPromoCode(promo.getPromoCode()).isPresent();
    }
    @Transactional
    public void deletePromo(String promocode){
        Promo promo = promoRepository.findByPromoCode(promocode).orElse(null);
        promoRepository.delete(promo);
    }
    @Transactional
    public PromoCodeDto changePromo(String promo, Double value){
        Promo promoentity = promoRepository.findByPromoCode(promo).orElse(null);
        promoentity.setValue(value);
        promoRepository.save(promoentity);
        return promoMapper.fromPromoToDto(promoRepository.findByPromoCode(promo).orElse(null));
    }
    public List<PromoCodeDto> getAll(){
        return promoMapper.fromPromoListToDtoList(promoRepository.findAll());
    }
}

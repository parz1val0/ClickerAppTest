package com.springboot.clickerapplication.repository;

import com.springboot.clickerapplication.models.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoRepository extends JpaRepository<Promo,Long> {
    Optional<Promo> findByPromoCode(String promoCode);
}
package com.springboot.clickerapplication.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "promo")
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4,name = "promo_code")
    private  String promoCode;

    @ManyToMany(mappedBy = "promos")
    private List<User> promoUsers;

    private double value;
}


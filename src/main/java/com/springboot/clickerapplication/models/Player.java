package com.springboot.clickerapplication.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "player")
@ToString(exclude = "userPlayerList")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(precision = 10, scale = 3)
    private BigDecimal value;

    private String photo;

    @ManyToMany(mappedBy = "players")
    private List<User> userPlayerList ;
}

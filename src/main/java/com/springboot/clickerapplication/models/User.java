package com.springboot.clickerapplication.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(exclude = {"players","balls","promos"})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id",length = 128)
    private String telegramId;

    @Column(length = 100)
    private String username;

    @Column()
    private double balance;

    @Column(name = "current_ball_id")
    private int currentBallId;

    @Column(name = "current_player_id")
    private int currentPlayerId;

    @Column(name = "counter_friend")
    private int counterFriend;

    @Column(name = "unique_referal_link",length = 128)
    private String uniqueReferalLink;


    @Column(name = "login_date")
    private LocalDateTime loginDate;
    @Column(name = "energy")
    private Long energy;

    private Long clicks;
    @Column(name="last_energy_regen_time")
    private LocalDateTime lastEnergyRegenTime;
    @Column(name = "counter_daily")
    private  Integer counterDaily;



    @ManyToMany()
    @JoinTable(name = "user_player"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "players_id"))
    private List<Player> players = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "user_ball"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "ball_id"))
    private List<Ball> balls;

    @Builder.Default
    @ManyToMany()
    @JoinTable(name = "user_promo"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "prom_id"))
    private List<Promo> promos = new ArrayList<>();
}

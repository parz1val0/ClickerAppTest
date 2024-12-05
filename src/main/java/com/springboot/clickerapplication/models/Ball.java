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
@Table(name = "ball")
@ToString(exclude = "userBalls")
public class Ball {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private  String name;

    private Integer price;

    private String photo;

    @ManyToMany(mappedBy = "balls")
    private List<User> userBalls ;
}

package com.springboot.clickerapplication.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pool_user")
@ToString
public class PoolUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo;
    @Column(length = 128,name = "name_surname")
    private  String nameSurname;
}

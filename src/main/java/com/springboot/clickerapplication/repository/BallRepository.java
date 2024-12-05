package com.springboot.clickerapplication.repository;

import com.springboot.clickerapplication.models.Ball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BallRepository extends JpaRepository<Ball,Long> {
}

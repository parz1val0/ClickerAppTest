package com.springboot.clickerapplication.mapper;

import com.springboot.clickerapplication.dto.BallDto;
import com.springboot.clickerapplication.models.Ball;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface BallMapper {
    default BallDto map(Ball ball) {
        if (ball == null) {
            return null;
        }

        return BallDto.builder()
                .id(ball.getId())
                .name(ball.getName())
                .price(ball.getPrice())
                .photo(ball.getPhoto())
                .build();
    }

    default List<BallDto> fromBallToBallDto(List<Ball> balls) {
        if (balls == null || balls.isEmpty()) {
            return List.of();
        }

        return balls.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}

package com.springboot.clickerapplication.service;

import com.springboot.clickerapplication.dto.BallDto;
import com.springboot.clickerapplication.mapper.BallMapper;
import com.springboot.clickerapplication.repository.BallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BallService {
    private final BallRepository ballRepository;
    private final  BallMapper ballMapper;
    public List<BallDto> getAllBall(){
        return ballMapper.fromBallToBallDto(ballRepository.findAll());
    }
}

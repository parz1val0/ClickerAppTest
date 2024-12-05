package com.springboot.clickerapplication.service;

import com.springboot.clickerapplication.dto.PlayerDto;
import com.springboot.clickerapplication.mapper.PlayerMapper;
import com.springboot.clickerapplication.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public List<PlayerDto> getAllPlayer(){
        return playerMapper.fromPlayerToPlayerDto(playerRepository.findAll());
    }
}

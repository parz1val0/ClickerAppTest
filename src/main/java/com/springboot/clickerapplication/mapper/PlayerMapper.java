package com.springboot.clickerapplication.mapper;

import com.springboot.clickerapplication.dto.PlayerDto;
import com.springboot.clickerapplication.models.Player;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

     default PlayerDto map(Player player) {
          if (player == null) {
               return null;
          }

          return PlayerDto.builder()
                  .id(player.getId())
                  .name(player.getName())
                  .price(player.getPrice())
                  .value(player.getValue())
                  .photo(player.getPhoto())
                  .build();
     }

     default List<PlayerDto> fromPlayerToPlayerDto(List<Player> players) {
          if (players == null || players.isEmpty()) {
               return List.of();
          }

          return players.stream()
                  .map(this::map)
                  .collect(Collectors.toList());
     }
}

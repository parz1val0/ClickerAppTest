package com.springboot.clickerapplication.mapper;

import com.springboot.clickerapplication.dto.UserDto;
import com.springboot.clickerapplication.dto.UserForAdminDto;
import com.springboot.clickerapplication.models.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserDto fromUserToUserDto(User user, Boolean isAdmin, Boolean dailyBonusAvailable){
        return UserDto.builder()
                .telegramId(user.getTelegramId())
                .username(user.getUsername())
                .balance(user.getBalance())
                .currentPlayerId(user.getCurrentPlayerId())
                .currentBallId(user.getCurrentBallId())
                .energy(user.getEnergy())
                .isAdmin(isAdmin)
                .dailyBonus(user.getCounterDaily())
                .build();
    }
    default List<UserForAdminDto> fromUserListToUserAdminDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(this::fromUserToUserForAdminDto)
                .collect(Collectors.toList());
    }
    default UserForAdminDto fromUserToUserForAdminDto(User user){
        return UserForAdminDto.builder()
                .telegramId(user.getTelegramId())
                .balance(user.getBalance())
                .energy(user.getEnergy())
                .build();
    }
}

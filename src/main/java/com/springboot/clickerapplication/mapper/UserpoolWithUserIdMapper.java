package com.springboot.clickerapplication.mapper;

import com.springboot.clickerapplication.dto.PoolUserDto;
import com.springboot.clickerapplication.dto.UserPoolWithUserBalanceDto;
import com.springboot.clickerapplication.models.PoolUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Mapper(componentModel = "spring")
public interface UserpoolWithUserIdMapper {

    default UserPoolWithUserBalanceDto fromBalanceAmountAndPoolListToUserPoolWithUserBalanceDto(
            Double balance,
            List<Integer> amount,
            List<PoolUser> poolUsers) {

        List<PoolUserDto> poolUserDtoList = IntStream.range(0, poolUsers.size())
                .mapToObj(i -> PoolUserDto.builder()
                        .amount(amount.get(i))
                        .nameSurname(poolUsers.get(i).getNameSurname())
                        .photo(poolUsers.get(i).getPhoto())
                        .build())
                .collect(Collectors.toList());

        return UserPoolWithUserBalanceDto.builder()
                .balance(balance)
                .poolUserDtoList(poolUserDtoList)
                .build();
    }
}

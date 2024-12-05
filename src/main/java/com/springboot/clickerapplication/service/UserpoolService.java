package com.springboot.clickerapplication.service;

import com.springboot.clickerapplication.dto.UserPoolWithUserBalanceDto;
import com.springboot.clickerapplication.mapper.UserpoolWithUserIdMapper;
import com.springboot.clickerapplication.repository.PoolUserRepository;
import com.springboot.clickerapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserpoolService {
    private final UserRepository userRepository;
    private final PoolUserRepository userPoolRepository;
    private final UserpoolWithUserIdMapper userpoolWithUserIdMapper;
   public static final  int size = 10;
    public static final  int min = 20;
    public static final  int max = 400;



    public UserPoolWithUserBalanceDto getUserPoolWithUserBalance(String id){
        return userpoolWithUserIdMapper.fromBalanceAmountAndPoolListToUserPoolWithUserBalanceDto(
                userRepository.getBalanceById(id),
                fillListWithRandom(),
                userPoolRepository.findRandomPoolUsers()
                );
    }

   static List<Integer> fillListWithRandom(){
        return new Random().ints(size, min, max + 1)
                .boxed()
                .toList();
    }

}

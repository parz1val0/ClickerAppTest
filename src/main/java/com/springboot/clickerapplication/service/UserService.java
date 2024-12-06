package com.springboot.clickerapplication.service;

import com.springboot.clickerapplication.dto.*;
import com.springboot.clickerapplication.mapper.UserMapper;
import com.springboot.clickerapplication.models.Ball;
import com.springboot.clickerapplication.models.Player;
import com.springboot.clickerapplication.models.Promo;
import com.springboot.clickerapplication.models.User;
import com.springboot.clickerapplication.repository.BallRepository;
import com.springboot.clickerapplication.repository.PlayerRepository;
import com.springboot.clickerapplication.repository.PromoRepository;
import com.springboot.clickerapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PromoRepository promoRepository;
    private final UserMapper userMapper;
    private final PlayerRepository playerRepository;
    private final BallRepository ballRepository;
    private final UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public BigInteger getCounter(){
        return userRepository.findAmountOfAll();
    }
    public Page<UserForAdminDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserForAdminDto> userDtoList = userMapper.fromUserListToUserAdminDtoList(userPage.getContent());
        return new PageImpl<>(userDtoList, pageable, userPage.getTotalElements());
    }
    public List<AvailableBallsDto> getAvailableBalls(String telegramid){
        return userRepository.findAvailableBallsByUserId(telegramid);
    }
    public List<AvailablePlayersDto> getAvailablePlayers(String telegramId){
        return userRepository.findAvailablePlayersByUserId(telegramId);
    }
    @Transactional
    public boolean usePromo(String promoCode,String telegramId){
        promoCode=promoCode.replace("\"", "");
        User user = userRepository.findByTelegramId(telegramId).orElse(null);
        List<Promo> promos = user.getPromos();
        Promo promocode = promoRepository.findByPromoCode(promoCode).orElse(null);
        if(!promos.contains(promocode)){
            user.setBalance(user.getBalance()+promocode.getValue());
            user.getPromos().add(promocode);
            userRepository.save(user);
            return true;
        }
        else return false;
    }
    @Transactional
    public boolean buyBall(String telegramId,Integer ballId){
        User byTelegramId = userRepository.findByTelegramId(telegramId).get();
        Ball ball = ballRepository.findById(ballId.longValue()).get();
        if((byTelegramId.getBalance()-ball.getPrice().longValue())<0){
            return false;
        }
        else {
            byTelegramId.setBalance(byTelegramId.getBalance()-ball.getPrice().longValue());
            byTelegramId.getBalls().add(ball);
            userRepository.save(byTelegramId);
            return true;
        }
    }
    @Transactional
    public UpdateBalanceDto updateBalance(String telegramId, Long clicks) {
        User byTelegramId = userRepository.findByTelegramId(telegramId).orElse(null);
        if (byTelegramId == null) {
            throw new IllegalArgumentException("Пользователь с таким telegramId не найден");
        }
        BigDecimal value = byTelegramId.getPlayers().stream()
                .filter(p -> p.getId().intValue() == byTelegramId.getCurrentPlayerId())
                .map(Player::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Текущий игрок не найден у пользователя"));
        if(clicks>byTelegramId.getEnergy()*500){
            clicks=byTelegramId.getEnergy()*500;
        }
        byTelegramId.setClicks(byTelegramId.getClicks() + clicks);

        int countGroups = (int) (byTelegramId.getClicks() / 500);

        byTelegramId.setClicks(byTelegramId.getClicks() % 500);

        double addedBalance = value.doubleValue() * clicks;
        double newBalance = byTelegramId.getBalance() + addedBalance;
        byTelegramId.setBalance(newBalance);

        byTelegramId.setEnergy(byTelegramId.getEnergy() - countGroups);


        byTelegramId.setLastEnergyRegenTime(LocalDateTime.now());

        User saved = userRepository.save(byTelegramId);
        return UpdateBalanceDto.builder()
                .balance(saved.getBalance())
                .energy(saved.getEnergy())
                .build();
    }

    @Transactional
    public void updateNickname(String telegramId,String nickname){
        User user = userRepository.findByTelegramId(telegramId).get();
        nickname=nickname.replace("\"", "");
        user.setUsername(nickname);
        userRepository.save(user);
    }
    @Transactional
    public boolean buyPlayer(String telegramId,Integer playerId){
        User byTelegramId = userRepository.findByTelegramId(telegramId).get();
        Player player = playerRepository.findById(playerId.longValue()).get();
        if((byTelegramId.getBalance()-player.getPrice().longValue())<0){
            return false;
        }
        else {
            byTelegramId.setBalance(byTelegramId.getBalance()-player.getPrice().longValue());
            byTelegramId.getPlayers().add(player);
            return true;
        }
    }

    @Transactional
    public void updateCurrentBallId(String telegramId,Integer ballId){
        Optional<User> byTelegramId = userRepository.findByTelegramId(telegramId);
        byTelegramId.get().setCurrentBallId(ballId);
        userRepository.save(byTelegramId.get());
    }
    @Transactional
    public void updateCurrentPlayerId(String telegramId,Integer playerId){
        Optional<User> byTelegramId = userRepository.findByTelegramId(telegramId);
        byTelegramId.get().setCurrentPlayerId(playerId);
        userRepository.save(byTelegramId.get());
    }
    @Transactional
    public void addUserFriend(String uniqueLink,String telegramid){
        User byUniqueReferalLink = userRepository.findByUniqueReferalLink(uniqueLink);
        if(userRepository.findByTelegramId(telegramid).isEmpty()){
            int counterFriend = byUniqueReferalLink.getCounterFriend();
            byUniqueReferalLink.setCounterFriend(counterFriend+1);
            if(byUniqueReferalLink.getCounterFriend() >=10)
                addUsdToBalance(4L,byUniqueReferalLink.getTelegramId());
            else
                userRepository.save(byUniqueReferalLink);}
    }

    @Transactional
    public void addUsdToBalance(double amount,String telegramId){
        User user = userRepository.findByTelegramId(telegramId).orElseThrow(() -> new IllegalArgumentException("Текущий игрок не найден у пользователя"));
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
    }
    @Transactional
    public UserDto getOrCreateUser(String telegramId, Long userId){
        Optional<User> databaseUser = userRepository.findByTelegramId(telegramId);
        boolean isAdmin=false;
        if (databaseUser.isPresent()){
            return userMapper.fromUserToUserDto(
                    databaseUser.orElseThrow(() -> new IllegalArgumentException("Текущий игрок не найден у пользователя")),
                    isAdmin,
                    isAvailableDailyBonus(databaseUser.get())
            );
        }
        else {
            User saved = userRepository.save(createDefaultUser(telegramId,userId));
            return userMapper.fromUserToUserDto(
                    saved,
                    isAdmin,
                    true

            );
        }}
    public Long getEnergyForUser(String telegramID){
        return userRepository.findByTelegramId(telegramID).orElseThrow(()-> new IllegalArgumentException("no such a user")).getEnergy();
    }
    public double getDailyBonus(String telegramId) {
        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastLogin = user.getLoginDate();

        if (lastLogin != null && ChronoUnit.DAYS.between(lastLogin.toLocalDate(), now.toLocalDate()) < 1) {
            return 0;
        }
        int counterDaily = user.getCounterDaily();
        if (lastLogin != null && isAvailableDailyBonus(user) || counterDaily>=30) {
            user.setCounterDaily(1);
        }

        var bonusAmount = 0.5 * counterDaily;
        user.setBalance(user.getBalance() + bonusAmount);
        user.setCounterDaily(counterDaily+1);
        user.setLoginDate(LocalDateTime.now());

        userRepository.save(user);

        return bonusAmount;
    }

    private boolean isAvailableDailyBonus(User user){
        LocalDateTime now = LocalDateTime.now();
        return ChronoUnit.DAYS.between(user.getLoginDate().toLocalDate(), now.toLocalDate()) >= 1 ;
    }
    @Transactional
    @Scheduled(fixedRate = 300000) // 5 min in milliseconds
    public void regenerateEnergy() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEnergy() < 5 && user.getLastEnergyRegenTime() != null) {
                Duration duration = Duration.between(user.getLastEnergyRegenTime(), LocalDateTime.now());
                if (duration.toMinutes() >= 10) {
                    user.setEnergy(user.getEnergy() + 1);
                    user.setLastEnergyRegenTime(LocalDateTime.now());
                    userRepository.save(user);
                }
            }
        }
    }

    public User createDefaultUser(String telegramId,Long userId){
        return User.builder()
                .telegramId(telegramId)
                .username("Nick_name")
                .balance(0)
                .currentBallId(1)
                .currentPlayerId(1)
                .counterFriend(0)
                .uniqueReferalLink(generateUniqueLink())
                .loginDate(LocalDateTime.now().minusDays(1L))
                .counterDaily(1)
                .lastEnergyRegenTime(LocalDateTime.now())
                .energy(5L)
                .clicks(0L)
                .balls(
                        List.of(ballRepository.findById(1L).get())
                )
                .players(
                        List.of(playerRepository.findById(1L).get())
                )
                .promos(new ArrayList<>())
                .build();
    }
    public static String generateUniqueLink() {
        return UUID.randomUUID().toString();
    }
    public String generateReferralLink(String telegramId) {
        String uniqueLink = userRepository.findByTelegramId(telegramId).orElseThrow(()->new IllegalArgumentException("no such a user")).getUniqueReferalLink();
        return "link" + uniqueLink;
    }
    public void updateBalanceDouble(String telegramId,Double balance){
        User user = userRepository.findByTelegramId(telegramId).orElse(null);
        user.setBalance(user.getBalance()+balance);
        userRepository.save(user);
    }
}


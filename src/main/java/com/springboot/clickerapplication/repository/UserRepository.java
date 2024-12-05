package com.springboot.clickerapplication.repository;

import com.springboot.clickerapplication.dto.AvailableBallsDto;
import com.springboot.clickerapplication.dto.AvailablePlayersDto;
import com.springboot.clickerapplication.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByTelegramId(String telegramId);
    User findByUniqueReferalLink(String link);
    @Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
    BigInteger findAmountOfAll();
    @Override
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT u.balance FROM users u WHERE u.telegram_id like :id", nativeQuery = true)
    Double getBalanceById(@Param("id") String id);


    @Query("Select new com.springboot.clickerapplication.dto.AvailablePlayersDto(p.id,p.name,p.value,p.photo) from Player p join p.userPlayerList u on u.telegramId=:telegramUserId")
    List<AvailablePlayersDto> findAvailablePlayersByUserId(@Param("telegramUserId") String telegramUserId);

    @Query("Select new com.springboot.clickerapplication.dto.AvailableBallsDto(b.id,b.name,b.photo) from Ball b join b.userBalls u on u.telegramId=:telegramUserId")
    List<AvailableBallsDto> findAvailableBallsByUserId(@Param("telegramUserId") String telegramUserId);
}

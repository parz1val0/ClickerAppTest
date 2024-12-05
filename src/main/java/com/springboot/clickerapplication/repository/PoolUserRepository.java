package com.springboot.clickerapplication.repository;

import com.springboot.clickerapplication.models.PoolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolUserRepository extends JpaRepository<PoolUser,Long> {
    @Query(value = "SELECT * FROM pool_user ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<PoolUser> findRandomPoolUsers();
}

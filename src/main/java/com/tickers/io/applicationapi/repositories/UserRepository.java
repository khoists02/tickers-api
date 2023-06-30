package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.TickerType;
import com.tickers.io.applicationapi.model.User;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository  extends BaseEntityRepository<User, UUID> {
    @Query("SELECT case when count(u) > 0 then true else false end  FROM User u WHERE u.userName = :userName and u.email = :email")
    boolean checkExitsUser(@Param("userName") String userName, @Param("email") String email);

    Optional<User> findOneByUsername(String username);
}

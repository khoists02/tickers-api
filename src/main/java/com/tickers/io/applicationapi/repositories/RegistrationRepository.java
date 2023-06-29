package com.tickers.io.applicationapi.repositories;

import com.tickers.io.applicationapi.model.Registration;
import com.tickers.io.applicationapi.model.Stocks;
import com.tickers.io.applicationapi.support.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends BaseEntityRepository<Registration, UUID> {
    @Query("SELECT r from Registration r where r.activated = :activated")
    List<Registration> findAllByActivatedAccount(@Param("activated") Boolean activated);
}

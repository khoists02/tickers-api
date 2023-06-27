package com.tickers.io.applicationapi.api.migrations;

import com.tickers.io.applicationapi.dto.CreateMigrationJobRequest;
import com.tickers.io.applicationapi.model.Migrations;
import com.tickers.io.applicationapi.repositories.MigrationsJobRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/migrations")
public class MigrationsController {

    @Autowired
    private MigrationsJobRepository migrationsJobRepository;

    @PostMapping
    @Transactional
    public void createMigrationJob(@RequestBody @Valid CreateMigrationJobRequest request) throws DateTimeException, SQLException {
        Migrations migrations = new Migrations();
        migrations.setName(request.getName());
        migrations.setTickerName(request.getTickerName());
        migrations.setStartDate(ZonedDateTime.parse(request.getStartDate()));
        migrations.setEndDate(ZonedDateTime.parse(request.getEndDate()));
        migrations.setActive(request.getActive());

        migrationsJobRepository.save(migrations);
    }
}

package com.tickers.io.applicationapi.api.migrations;

import com.tickers.io.applicationapi.dto.CreateMigrationJobRequest;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
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
        boolean checkInsert = migrationsJobRepository.checkActiveMigrationJob(); // only one record have active at the time

        if (checkInsert) throw new BadRequestException("only_one_migration_job_active");
        ZonedDateTime zdtNow = ZonedDateTime.now();

        if (zdtNow.isAfter(ZonedDateTime.parse(request.getEndDate())))
            throw new BadRequestException("end_date_after_now");
        if ( ZonedDateTime.parse(request.getStartDate()).isAfter(ZonedDateTime.parse(request.getEndDate())))
            throw new BadRequestException("start_date_after_end_date");

        Migrations migrations = new Migrations();
        migrations.setName(request.getName());
        migrations.setTickerName(request.getTickerName());
        migrations.setStartDate(ZonedDateTime.parse(request.getStartDate()));
        migrations.setCurrentDate(ZonedDateTime.parse(request.getStartDate()));
        migrations.setEndDate(ZonedDateTime.parse(request.getEndDate()));
        migrations.setActive(request.getActive());

        migrationsJobRepository.save(migrations);
    }
}

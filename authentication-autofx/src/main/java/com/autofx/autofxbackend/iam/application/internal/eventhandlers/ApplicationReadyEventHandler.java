package com.autofx.autofxbackend.iam.application.internal.eventhandlers;

import com.autofx.autofxbackend.iam.domain.model.commands.SeedRolesCommand;
import com.autofx.autofxbackend.iam.domain.services.RoleCommandService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@AllArgsConstructor
@Service
public class ApplicationReadyEventHandler {

    private final RoleCommandService roleCommandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    @EventListener
    public void on(ApplicationReadyEvent event){
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if roles seeding is nedded for {} at {}", applicationName, new Timestamp(System.currentTimeMillis()));
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.execute(seedRolesCommand);
        LOGGER.info("Roles seeding verification finished for {} at {}", applicationName, new Timestamp(System.currentTimeMillis()));
    }

}

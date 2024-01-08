package org.musicshop.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.musicshop.db.FakeStaticDb;
import org.musicshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Autowired
    private UserOutputService userOutputService;

    @Autowired
    private UserInputService userInputService;

    @Autowired
    private DatabaseInitService databaseInitService;

    @Autowired
    private FileProviderService fileProviderService;

    @Override
    public void initAndRead(boolean continueUntilQuit, final String salesFilePath) {

        if (StringUtils.isBlank(salesFilePath))
            throw new IllegalArgumentException("ERROR: Must provide file path for DB init!");

        final var timeBeforeLoad = System.currentTimeMillis();
        final var salesDbAsFile = fileProviderService.readEntireFile(salesFilePath);
        databaseInitService.init(salesDbAsFile);
        final var timeAfterLoad = System.currentTimeMillis();
        final var secondsItTookToLoad = (timeAfterLoad - timeBeforeLoad) / 1000;
        userOutputService.print("DB loaded in " + secondsItTookToLoad + "s...\n");

        do {
            userOutputService.print("Please supply a product identifier...");

            final var userInput = userInputService.read();

            // If we have valid input.
            if (NumberUtils.isCreatable(userInput)) {
                // Find related and create message to print.
                final var relatedProductsMessage = FakeStaticDb.getRelationships()
                        .stream()
                        .filter(relationship -> relationship.getRelated() == Long.parseLong(userInput))
                        .distinct() // Deduplicate across groups
                        .map(Object::toString)
                        .collect(Collectors.joining(",\n"));

                if (StringUtils.isNotBlank(relatedProductsMessage))
                    userOutputService.print(relatedProductsMessage);
                else
                    userOutputService.print("Could not find any related products... please choose another product.");
            }
            // User quiting.
            else if (userInput.equalsIgnoreCase("q") || userInput.equalsIgnoreCase("QUIT"))
                continueUntilQuit = false;
            // Invalid user input.
            else
                userOutputService.error("Must supply a valid number!(" + userInput + ")");

        } while (continueUntilQuit);

        userOutputService.print("Quiting.... goodbye");
    }
}

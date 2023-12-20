package org.musicshop.service.impl;

import org.musicshop.db.FakeStaticDb;
import org.musicshop.db.MockDbInput;
import org.musicshop.service.DatabaseInitService;
import org.musicshop.service.ExecutionService;
import org.musicshop.service.UserInputService;
import org.musicshop.service.UserOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Autowired
    private UserOutputService userOutputService;

    @Autowired
    private UserInputService userInputService;

    @Autowired
    private DatabaseInitService databaseInitService;

    @Override
    public void initAndRead(final boolean continueUntilQuit) {

        do {
            userOutputService.print("Please supply a product identifier...\n");
            databaseInitService.init(MockDbInput.inputJson);

            final var userInput = userInputService.read();

            FakeStaticDb.getProductsByUser().forEach( (id, orders) -> {
                System.out.println("Items purchased by user: " + id);
                orders.forEach(purchase -> System.out.print(purchase + ", "));
                System.out.println();
            });

            FakeStaticDb.getPurchasedProducts().forEach( (order) -> {
                System.out.println("Items purchased by user: " + order);
                System.out.print(order + ", ");
                System.out.println();
            });

        } while (continueUntilQuit);
    }
}

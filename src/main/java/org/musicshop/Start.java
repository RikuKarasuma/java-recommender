package org.musicshop;

import org.musicshop.service.ExecutionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Starts the small spring app. Only using Spring-Context for dependency injection
 * and easy testing of the IOC pattern.
 */
public class Start {

    public static void main(String[] args) {
        // Initialize the spring context, grab our main service bean.
        final var applicationContext = new AnnotationConfigApplicationContext();
        // Scan for services
        applicationContext.scan(Start.class.getPackage().getName());
        applicationContext.refresh();

        final ExecutionService executionService = applicationContext.getBean(ExecutionService.class);
        // Start our main input loop.
        executionService.initAndRead(true, "src/test/resources/sales-report.json");

        applicationContext.close();
    }
}
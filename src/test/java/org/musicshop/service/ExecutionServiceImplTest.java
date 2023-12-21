package org.musicshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.musicshop.db.FakeStaticDb;
import org.musicshop.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringJUnitConfig(ExecutionServiceImplTest.Config.class)
public class ExecutionServiceImplTest {

    @Configuration
    static class Config {

        @Bean
        public ExecutionService executionServiceImpl() {
            return new ExecutionServiceImpl();
        }

        @Bean
        public UserInputService userInputServiceMock() {
            return Mockito.mock(UserInputService.class);
        }

        @Bean
        public UserOutputService userOutputServiceImpl() {
            return Mockito.mock(UserOutputService.class);
        }

        @Bean
        public FileProviderService fileProviderServiceImpl() {
            return new FileProviderServiceImpl();
        }

        @Bean
        public DatabaseInitService databaseInitServiceImpl() {
            return new DatabaseInitServiceImpl();
        }

        @Bean
        public RelationshipGenerationService relationshipGenerationServiceImpl() {
            return new RelationshipGenerationServiceImpl();
        }

        @Bean
        public PurchaseInfoGenerationService purchaseInfoGenerationServiceImpl() {
            return new PurchaseInfoGenerationServiceImpl();
        }

        @Bean
        public UserInfoGenerationService userInfoGenerationServiceImpl() {
            return new UserInfoGenerationServiceImpl();
        }
    }

    @Autowired
    private UserInputService userInputServiceMock;
    @Autowired
    private UserOutputService userOutputServiceMock;
    @Autowired
    private ExecutionService executionService;

    @BeforeEach
    public void setUp() {
        reset(userInputServiceMock, userOutputServiceMock);
        FakeStaticDb.getRelationships().clear();
    }

    // Basic smoke tests
    @Test
    public void shouldCorrectlyLoadAndGenerateAllUniqueRelationshipsThenQuit() {
        final var totalDistinctRelationships = 38188;
        when(userInputServiceMock.read()).thenReturn("q");
        executionService.initAndRead(false, "src/test/resources/sales-report.json");
        assertEquals(FakeStaticDb.getRelationships().size(), totalDistinctRelationships);
        verify(userOutputServiceMock, times(1)).print("Quiting.... goodbye");
    }

    @Test
    public void shouldCorrectlyDisplayAllRelatedThenQuit() {
        when(userInputServiceMock.read()).thenReturn("59831");
        executionService.initAndRead(false, "src/test/resources/sales-report.json");
        verify(userOutputServiceMock, times(1)).print(
            "PurchaseRelationship{product=36426, qty=23, related=59831, m1=1.00},\n" +
            "PurchaseRelationship{product=27111, qty=24, related=59831, m1=1.00},\n" +
            "PurchaseRelationship{product=60119, qty=23, related=59831, m1=1.00},\n" +
            "PurchaseRelationship{product=16377, qty=24, related=59831, m1=1.00},\n" +
            "PurchaseRelationship{product=29932, qty=25, related=59831, m1=0.80},\n" +
            "PurchaseRelationship{product=53412, qty=26, related=59831, m1=0.60},\n" +
            "PurchaseRelationship{product=59959, qty=26, related=59831, m1=0.60},\n" +
            "PurchaseRelationship{product=60353, qty=27, related=59831, m1=0.50},\n" +
            "PurchaseRelationship{product=56971, qty=27, related=59831, m1=0.50},\n" +
            "PurchaseRelationship{product=23227, qty=27, related=59831, m1=0.50},\n" +
            "PurchaseRelationship{product=60028, qty=29, related=59831, m1=0.40},\n" +
            "PurchaseRelationship{product=57602, qty=28, related=59831, m1=0.40},\n" +
            "PurchaseRelationship{product=59612, qty=32, related=59831, m1=0.30},\n" +
            "PurchaseRelationship{product=51360, qty=48, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=57687, qty=60, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=57687, qty=60, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=57687, qty=60, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=59804, qty=53, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=21887, qty=197, related=59831, m1=0.10},\n" +
            "PurchaseRelationship{product=59467, qty=52, related=59831, m1=0.10}");
    }

    @Test
    public void shouldCorrectlyInvalidateUserInputThenQuit() {
        when(userInputServiceMock.read()).thenReturn("231ss");
        executionService.initAndRead(false, "src/test/resources/sales-report-empty.json");
        verify(userOutputServiceMock, times(1)).error("Must supply a valid number!(231ss)");
        verify(userOutputServiceMock, times(1)).print("Quiting.... goodbye");
    }
}
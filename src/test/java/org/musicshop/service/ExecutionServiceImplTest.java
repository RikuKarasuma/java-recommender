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
        final var totalDistinctRelationships = 3336613;
        when(userInputServiceMock.read()).thenReturn("q");
        executionService.initAndRead(false, "src/test/resources/sales-report.json");
        assertEquals(totalDistinctRelationships, FakeStaticDb.getRelationships().size());
        verify(userOutputServiceMock, times(1)).print("Quiting.... goodbye");
    }

    @Test
    public void shouldCorrectlyDisplayAllRelatedThenQuit() {
        when(userInputServiceMock.read()).thenReturn("59959");
        executionService.initAndRead(false, "src/test/resources/sales-report.json");
        verify(userOutputServiceMock, times(1)).print(
            "PurchaseRelationship{product=19614, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=1405, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=20835, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=42699, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=47212, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=703, qty=5, related=59959, m1=0.50},\n" +
            "PurchaseRelationship{product=41114, qty=6, related=59959, m1=0.30},\n" +
            "PurchaseRelationship{product=23457, qty=6, related=59959, m1=0.30},\n" +
            "PurchaseRelationship{product=26735, qty=6, related=59959, m1=0.30},\n" +
            "PurchaseRelationship{product=30062, qty=6, related=59959, m1=0.30},\n" +
            "PurchaseRelationship{product=56976, qty=7, related=59959, m1=0.20},\n" +
            "PurchaseRelationship{product=56835, qty=8, related=59959, m1=0.20},\n" +
            "PurchaseRelationship{product=48641, qty=11, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=59651, qty=25, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=59862, qty=13, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=57687, qty=42, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=55957, qty=35, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=56716, qty=32, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=58212, qty=18, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=59945, qty=24, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=57987, qty=27, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=59831, qty=26, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=59583, qty=112, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=51090, qty=37, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=52706, qty=15, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=47967, qty=16, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=47966, qty=13, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=8491, qty=16, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=57880, qty=9, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=46061, qty=38, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=52226, qty=76, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=47383, qty=11, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=50160, qty=35, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=55707, qty=76, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=51025, qty=49, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=56489, qty=61, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=57629, qty=12, related=59959, m1=0.10},\n" +
            "PurchaseRelationship{product=57722, qty=58, related=59959, m1=0.10}");
    }

    @Test
    public void shouldCorrectlyInvalidateUserInputThenQuit() {
        when(userInputServiceMock.read()).thenReturn("231ss");
        executionService.initAndRead(false, "src/test/resources/sales-report-empty.json");
        verify(userOutputServiceMock, times(1)).error("Must supply a valid number!(231ss)");
        verify(userOutputServiceMock, times(1)).print("Quiting.... goodbye");
    }
}
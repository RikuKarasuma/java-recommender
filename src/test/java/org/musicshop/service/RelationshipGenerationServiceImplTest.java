package org.musicshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.musicshop.pojo.model.PurchaseRelationship;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.impl.FileProviderServiceImpl;
import org.musicshop.service.impl.PurchaseInfoGenerationServiceImpl;
import org.musicshop.service.impl.RelationshipGenerationServiceImpl;
import org.musicshop.service.impl.UserInfoGenerationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@SpringJUnitConfig(RelationshipGenerationServiceImplTest.Config.class)
public class RelationshipGenerationServiceImplTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Configuration
    static class Config {
        @Bean
        public PurchaseInfoGenerationService purchaseInfoGenerationServiceImpl() {
            return new PurchaseInfoGenerationServiceImpl();
        }

        @Bean
        public UserInfoGenerationService userInfoGenerationServiceImpl() {
            return new UserInfoGenerationServiceImpl();
        }
        @Bean
        public FileProviderService fileProviderServiceImpl() {
            return new FileProviderServiceImpl();
        }

        @Bean
        public RelationshipGenerationService relationshipGenerationService() {
            return new RelationshipGenerationServiceImpl();
        }
    }

    @Autowired
    private PurchaseInfoGenerationService purchaseInfoGenerationService;

    @Autowired
    private UserInfoGenerationService userInfoGenerationService;

    @Autowired
    private RelationshipGenerationService relationshipGenerationService;

    @Autowired
    private FileProviderService fileProviderService;

    @Test
    public void shouldCorrectlyGenerateProductRelationshipsInDescendingM1Order() throws JsonProcessingException {

        final var unmarshalledJsonSales = JSON_MAPPER.readValue(fileProviderService.readEntireFile("src/test/resources/sales-report-minimal.json"), Sales.class);
        final var purchasesByUser = userInfoGenerationService.generateUserInfo(unmarshalledJsonSales);
        final var generatedTotalPurchaseInfo = purchaseInfoGenerationService.generatePurchaseInfo(purchasesByUser);

        final var relationships = relationshipGenerationService.generateProductRelationships(purchasesByUser,
                generatedTotalPurchaseInfo);

        final var expectedRelatedInfo = Arrays.asList(
            new PurchaseRelationship(2, 1, 5, 0.5d),
            new PurchaseRelationship(3, 1, 5, 0.5d),
            new PurchaseRelationship(2, 3, 2, 0.2d),
            new PurchaseRelationship(3, 2, 2, 0.20),
            new PurchaseRelationship(1, 2, 5, 0.10),
            new PurchaseRelationship(1, 3, 5, 0.10),
            new PurchaseRelationship(1, 2, 5, 0.10),
            new PurchaseRelationship(1, 3, 5, 0.10),
            new PurchaseRelationship(1, 4, 5, 0.10)
        );

        final var expectedSize = 9;
        assertEquals(expectedSize, expectedRelatedInfo.size());
        assertIterableEquals(expectedRelatedInfo, relationships);
    }
}
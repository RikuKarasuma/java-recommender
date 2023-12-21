package org.musicshop.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.impl.FileProviderServiceImpl;
import org.musicshop.service.impl.PurchaseInfoGenerationServiceImpl;
import org.musicshop.service.impl.UserInfoGenerationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@SpringJUnitConfig(PurchaseInfoGenerationServiceImplTest.Config.class)
public class PurchaseInfoGenerationServiceImplTest {

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
    }

    @Autowired
    private PurchaseInfoGenerationService purchaseInfoGenerationService;

    @Autowired
    private UserInfoGenerationService userInfoGenerationService;

    @Autowired
    private FileProviderService fileProviderService;

    @Test
    public void shouldCorrectlyGenerateTotalPurchaseInfoFromSalesData() throws JsonProcessingException {

        final var unmarshalledJsonSales = JSON_MAPPER.readValue(fileProviderService.readEntireFile("src/test/resources/sales-report-minimal.json"), Sales.class);
        final var purchasesByUser = userInfoGenerationService.generateUserInfo(unmarshalledJsonSales);
        final var generatedTotalPurchaseInfo = purchaseInfoGenerationService.generatePurchaseInfo(purchasesByUser);

        final var expectedPurchaseInfo = Arrays.asList(new ProductInfo(1, 4), new ProductInfo(2, 1),
                new ProductInfo(3, 1), new ProductInfo(4, 1));

        final var expectedSize = 4;
        assertEquals(expectedSize, expectedPurchaseInfo.size());
        assertIterableEquals(expectedPurchaseInfo, generatedTotalPurchaseInfo);
    }
}
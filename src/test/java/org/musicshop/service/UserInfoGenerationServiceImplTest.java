package org.musicshop.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(UserInfoGenerationServiceImplTest.Config.class)
public class UserInfoGenerationServiceImplTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Configuration
    static class Config {
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
    private UserInfoGenerationService userInfoGenerationService;

    @Autowired
    private FileProviderService fileProviderService;

    @Test
    public void shouldCorrectlyGenerateUserPurchaseInfoFromSalesData() throws JsonProcessingException {

        final var unmarshalledJsonSales = JSON_MAPPER.readValue(fileProviderService.readEntireFile("src/test/resources/sales-report-minimal.json"), Sales.class);
        final var generatedUserInfoAndAssociatedPurchases = userInfoGenerationService.generateUserInfo(unmarshalledJsonSales);
        final var expectedUsersWithOrdersSize = 3;
        assertEquals(generatedUserInfoAndAssociatedPurchases.size(), expectedUsersWithOrdersSize);
        final var expectedPurchasesWithFirstUser = 4;
        assertEquals(generatedUserInfoAndAssociatedPurchases.get(1L).size(), expectedPurchasesWithFirstUser);
        final var expectedPurchasesWithSecondUser = 1;
        assertEquals(generatedUserInfoAndAssociatedPurchases.get(2L).size(), expectedPurchasesWithSecondUser);
        final var expectedPurchasesWithThirdUser = 2;
        assertEquals(generatedUserInfoAndAssociatedPurchases.get(3L).size(), expectedPurchasesWithThirdUser);
    }
}
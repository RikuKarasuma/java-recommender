package org.musicshop.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.musicshop.db.FakeStaticDb;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DatabaseInitServiceImpl implements DatabaseInitService {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        JSON_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Autowired
    private UserOutputService userOutputService;

    @Autowired
    private RelationshipGenerationService relationshipGenerationService;

    @Autowired
    private UserInfoGenerationService userInfoGenerationService;

    @Autowired
    private PurchaseInfoGenerationService purchaseInfoGenerationService;

    @Override
    public void init(final String json) {

        if (StringUtils.isBlank(json))
            return;

        try {
            // Take in our sample sales input and store.
            final var unmarshalledJsonSales = JSON_MAPPER.readValue(json, Sales.class);
            handleDbGenerationFromJson(unmarshalledJsonSales);
        } catch (JsonProcessingException exception) {
            userOutputService.error(exception.getMessage());
        }
    }

    private void handleDbGenerationFromJson(final Sales sales) {
        if (Objects.isNull(sales))
            throw new IllegalArgumentException("ERROR: Can't add null product to DB.");
        else if (sales.getSales().length == 0)
            return;

        // Generate users and their orders.
        final var userInfo = userInfoGenerationService.generateUserInfo(sales);
        // Generate purchases and their totals.
        final var purchaseInfo = purchaseInfoGenerationService.generatePurchaseInfo(userInfo);
        // Generate relationships between all user orders.
        final var relationshipInfo = relationshipGenerationService.generateProductRelationships(userInfo, purchaseInfo);

        // Store in DB for later querying.
        FakeStaticDb.addRelationships(relationshipInfo);
    }
}

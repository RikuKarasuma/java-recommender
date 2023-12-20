package org.musicshop.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.musicshop.db.FakeStaticDb;
import org.musicshop.pojo.Product;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.DatabaseInitService;
import org.musicshop.service.RelationshipGenerationService;
import org.musicshop.service.UserOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Sets up our fake DB, imagine JDBC services here.
 */
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

    @Override
    public void init(final String json) {

        if (!StringUtils.hasLength(json) || json.isBlank())
            throw new IllegalArgumentException("ERROR: can't parse null or empty JSON.");

        try {
            // Take in our sample sales input and store.
            FakeStaticDb.addProductsFromSales(JSON_MAPPER.readValue(json, Sales.class));
        } catch (JsonProcessingException exception) {
            userOutputService.error(exception.getMessage());
        }
    }
}

package org.musicshop.service.impl;

import org.musicshop.pojo.Product;
import org.musicshop.pojo.Relationship;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.model.PurchaseRelationship;
import org.musicshop.service.RelationshipGenerationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RelationshipGenerationServiceImpl implements RelationshipGenerationService {
    @Override
    public List<Relationship> generateProductRelationships(final List<ProductInfo> products) {

        if (CollectionUtils.isEmpty(products))
            return Collections.emptyList();

        products.forEach(System.out::println);

        final List<Relationship> productRelationships = new ArrayList<>(products.stream()
                .map(PurchaseRelationship::new).toList());

        for ( int i = 0; i < productRelationships.size(); i ++) {
            for ( int x = 0; x < productRelationships.size(); x ++) {
                // if it's the same product sale, no need to associate.
                if (x == i)
                    continue;


//                System.out.println("Combining...");
            }
        }

        return productRelationships;
    }
}

package org.musicshop.service.impl;

import org.musicshop.pojo.Relationship;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.model.PurchaseRelationship;
import org.musicshop.service.RelationshipGenerationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelationshipGenerationServiceImpl implements RelationshipGenerationService {
    @Override
    public List<Relationship> generateProductRelationships(final Map<Long, List<Long>> purchasesByUser,
            final List<ProductInfo> allPurchases) {

        if (CollectionUtils.isEmpty(allPurchases))
            return Collections.emptyList();

        final List<Relationship> productRelationships = new ArrayList<>();
        for (final Map.Entry<Long, List<Long>> userGroupedPurchases : purchasesByUser.entrySet()) {

            final var relatedItems = userGroupedPurchases.getValue()
                    .stream()
                    .map(purchasedProductId -> allPurchases.stream().filter(product -> product.getProduct() == purchasedProductId).findAny().orElseThrow(RuntimeException::new))
                    .toList();

            for (int i = 0; i < relatedItems.size(); i ++) {
                final var relatedZero = relatedItems.get(i);
                for (int x = 1; x < relatedItems.size(); x ++) {
                    final var relatedOne = relatedItems.get(x);
                    if (relatedZero.getProduct() == relatedOne.getProduct())
                        continue;

                    final var purchaseRelationship = new PurchaseRelationship();
                    final var totalPurchasedBoth = relatedZero.getQty() + relatedOne.getQty();
                    // Calculate relationship correlation factor.
                    final var m1CorrelationFactor = (totalPurchasedBoth / relatedZero.getQty());
                    final var finalM1BetweenZeroAndOne = Math.min((m1CorrelationFactor * .1), 1d);

                    purchaseRelationship.setProduct(relatedZero.getProduct());
                    purchaseRelationship.setRelated(relatedOne.getProduct());
                    purchaseRelationship.setQty(totalPurchasedBoth);
                    purchaseRelationship.setM1(finalM1BetweenZeroAndOne);
                    productRelationships.add(purchaseRelationship);
                }
            }
        }

        sortByDescCorrelationFactor(productRelationships);
        return productRelationships;
    }

    private static void sortByDescCorrelationFactor(final List<Relationship> relationships) {
        relationships.sort( (x1, x2) -> {
            if (x1.getM1() < x2.getM1())
                return 1;
            else if (x1.getM1() > x2.getM1())
                return -1;

            return 0;
        });
    }
}

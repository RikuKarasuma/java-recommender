package org.musicshop.service.impl;

import org.musicshop.pojo.Relationship;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.model.PurchaseRelationship;
import org.musicshop.service.RelationshipGenerationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class RelationshipGenerationServiceImpl implements RelationshipGenerationService {
    @Override
    public List<Relationship> generateProductRelationships(final Map<Long, List<Long>> purchasesByUser,
            final List<ProductInfo> allPurchases) {

        if (CollectionUtils.isEmpty(allPurchases))
            return Collections.emptyList();

        final List<Relationship> productRelationships = new ArrayList<>();

        for (final Map.Entry<Long, List<Long>> userAndOrders : purchasesByUser.entrySet()) {

            final var distinctUserOrders = userAndOrders.getValue().stream().distinct().toList();

            for ( int i = 0, x = 1; x < distinctUserOrders.size(); i ++, x ++) {
                final long userOrder =  distinctUserOrders.get(i);
                final long nextUserOrder =  distinctUserOrders.get(x);

                final var purchaseRelationship = new PurchaseRelationship();

                // Both these must exist or throw runtime error as we can't
                // have an existing order and non-existing product.
                final var thisPurchaseInfo = allPurchases.stream().filter(purchaseInfo -> purchaseInfo.getProduct() == userOrder).findAny().orElseThrow(RuntimeException::new);
                final var nextPurchaseInfo = allPurchases.stream().filter(purchaseInfo -> purchaseInfo.getProduct() == nextUserOrder).findAny().orElseThrow(RuntimeException::new);

                // Calculate totals
                final var totalWhoBoughtProduct = purchasesByUser.entrySet().stream().filter(mapUserAndOrders -> mapUserAndOrders.getValue().contains(userOrder)).count();
                final var totalPurchasedBoth = thisPurchaseInfo.getQty() + nextPurchaseInfo.getQty();
                // Calculate relationship correlation factor.
                final var m1CorrelationFactor = (totalPurchasedBoth / totalWhoBoughtProduct);
                final var finalM1BetweenZeroAndOne = Math.min( (m1CorrelationFactor * .1), 1d);

                purchaseRelationship.setProduct(userOrder);
                purchaseRelationship.setRelated(nextPurchaseInfo.getProduct());
                purchaseRelationship.setQty(totalPurchasedBoth);
                purchaseRelationship.setM1(finalM1BetweenZeroAndOne);
                productRelationships.add(purchaseRelationship);
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

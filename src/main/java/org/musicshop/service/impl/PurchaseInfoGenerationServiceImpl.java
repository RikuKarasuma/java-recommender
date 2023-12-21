package org.musicshop.service.impl;

import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.service.PurchaseInfoGenerationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseInfoGenerationServiceImpl implements PurchaseInfoGenerationService {

    @Override
    public List<ProductInfo> generatePurchaseInfo(final Map<Long, List<Long>> purchasesByUser) {

        final var productInfo = new ArrayList<ProductInfo>();
        // Count occurrences of distinct sales.
        for (final Long userSale : purchasesByUser.values().stream().flatMap(List::stream).toList()) {

            final var productInfoOptional = productInfo.stream()
                    .filter(existingProduct -> existingProduct.getProduct() == userSale)
                    .findAny();

            if (productInfoOptional.isPresent()) {
                final var actualProduct = productInfoOptional.get();
                actualProduct.setQty(actualProduct.getQty() + 1);
            } else {
                final var newProductSold = new ProductInfo();
                newProductSold.setProduct(userSale);
                newProductSold.setQty(1);
                productInfo.add(newProductSold);
            }
        }

        return productInfo;
    }
}

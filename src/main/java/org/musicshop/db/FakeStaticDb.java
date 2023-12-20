package org.musicshop.db;

import org.musicshop.pojo.Relationship;
import org.musicshop.pojo.model.*;

import java.util.*;

public final class FakeStaticDb {

    private static final Map<Long, List<Long>> PRODUCTS_BY_USER = new HashMap<>();
    private static final List<ProductInfo> PRODUCT_INFO = new ArrayList<>();
    private static final List<Relationship> RELATIONSHIPS = new ArrayList<>();
    public static List<Relationship> getRelationships() {
        return RELATIONSHIPS;
    }

    public static void addProductsFromSales(final Sales productInfoToAdd) {
        if (Objects.isNull(productInfoToAdd))
            throw new IllegalArgumentException("ERROR: Can't add null product to DB.");
        else if (productInfoToAdd.getSales().length == 0)
            return;

        // Generate both sales and user purchase info.
        generateUserInfo(productInfoToAdd);
        generatePurchaseInfo(PRODUCTS_BY_USER);
    }

    private static void generateUserInfo(final Sales productInfoToAdd) {
        // Group orders by user.
        for (final Sale userSale: productInfoToAdd.getSales()) {

            final var userId = userSale.getUser();
            System.out.println("User ID: " + userId + "  " + userSale.getProduct());

            if (PRODUCTS_BY_USER.containsKey(userSale.getUser())) {
                final var products = PRODUCTS_BY_USER.get(userId);
                products.add(userSale.getProduct());
            }
            else {
                final var productsList = new ArrayList<Long>();
                productsList.add(userSale.getProduct());
                PRODUCTS_BY_USER.put(userId, productsList);
            }
        }
    }

    private static void generatePurchaseInfo(final Map<Long, List<Long>> purchasesByUser) {

        // Count occurrences of distinct sales.
        for (final Long userSale: purchasesByUser.values().stream().flatMap(List::stream).toList()) {

            final var productInfoOptional = PRODUCT_INFO.stream()
                    .filter(existingProduct -> existingProduct.getProduct() == userSale)
                    .findAny();

            if (productInfoOptional.isPresent()) {
                final var actualProduct = productInfoOptional.get();

                actualProduct.setQty(actualProduct.getQty() + 1);
            } else {
                final var newProductSold = new ProductInfo();
                newProductSold.setProduct(userSale);
                newProductSold.setQty(1);
                PRODUCT_INFO.add(newProductSold);
            }
        }
    }

    public static Map<Long, List<Long>> getProductsByUser() {
        return PRODUCTS_BY_USER;
    }
    public static List<ProductInfo> getPurchasedProducts() {
        return PRODUCT_INFO;
    }
}

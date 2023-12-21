package org.musicshop.service.impl;

import org.musicshop.pojo.model.Sale;
import org.musicshop.pojo.model.Sales;
import org.musicshop.service.UserInfoGenerationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoGenerationServiceImpl implements UserInfoGenerationService {
    @Override
    public Map<Long, List<Long>> generateUserInfo(final Sales productInfoToAdd) {
        final var productsByUser = new HashMap<Long, List<Long>>();
        // Group orders by user.
        for (final Sale userSale: productInfoToAdd.getSales()) {

            final var userId = userSale.getUser();

            if (productsByUser.containsKey(userSale.getUser())) {
                final var products = productsByUser.get(userId);
                products.add(userSale.getProduct());
            }
            else {
                final var productsList = new ArrayList<Long>();
                productsList.add(userSale.getProduct());
                productsByUser.put(userId, productsList);
            }
        }

        return productsByUser;
    }
}

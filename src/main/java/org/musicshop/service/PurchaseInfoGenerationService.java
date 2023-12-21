package org.musicshop.service;

import org.musicshop.pojo.model.ProductInfo;

import java.util.List;
import java.util.Map;

public interface PurchaseInfoGenerationService {

    List<ProductInfo> generatePurchaseInfo(Map<Long, List<Long>> purchasesByUser);
}

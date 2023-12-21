package org.musicshop.service;

import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.Relationship;

import java.util.List;
import java.util.Map;

public interface RelationshipGenerationService {

    List<Relationship> generateProductRelationships(Map<Long, List<Long>> purchasesByUser,
                                                    List<ProductInfo> allPurchases);
}

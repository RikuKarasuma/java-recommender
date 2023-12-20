package org.musicshop.service;

import org.musicshop.pojo.Product;
import org.musicshop.pojo.model.ProductInfo;
import org.musicshop.pojo.Relationship;

import java.util.List;

public interface RelationshipGenerationService {

    List<Relationship> generateProductRelationships(List<ProductInfo> products);
}

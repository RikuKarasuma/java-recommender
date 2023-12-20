package org.musicshop.pojo.model;

import org.musicshop.pojo.Product;
import org.musicshop.pojo.Relationship;

public class PurchaseRelationship extends ProductInfo implements Relationship {

    private long related;
    private long m1;

    public PurchaseRelationship() {}

    public PurchaseRelationship(final ProductInfo product) {
        super(product.getProduct(), product.getQty());

        this.related = 0;
        this.m1 = 0;
    }

    public PurchaseRelationship(final long product,
                                final long related,
                                final long qty,
                                final long m1) {
        super(product, qty);

        this.related = related;
        this.m1 = m1;
    }


    public long getRelated() {
        return related;
    }

    public void setRelated(long related) {
        this.related = related;
    }

    public long getM1() {
        return m1;
    }

    public void setM1(long m1) {
        this.m1 = m1;
    }

    @Override
    public String toString() {
        return "PurchaseRelationship{" +
                "product=" + super.getProduct() +
                ", qty=" + super.getQty() +
                ", related=" + related +
                ", m1=" + m1 +
                '}';
    }
}

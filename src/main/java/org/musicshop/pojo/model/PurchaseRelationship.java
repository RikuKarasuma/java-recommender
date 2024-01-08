package org.musicshop.pojo.model;

import org.musicshop.pojo.Relationship;

public class PurchaseRelationship extends ProductInfo implements Relationship {

    private long related;
    private double m1;

    public PurchaseRelationship() {}

    public PurchaseRelationship(final ProductInfo product) {
        super(product.getProduct(), product.getQty());

        this.related = 0;
        this.m1 = 0;
    }

    public PurchaseRelationship(final long product,
                                final long related,
                                final long qty,
                                final double m1) {
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

    public double getM1() {
        return m1;
    }

    public void setM1(double m1) {
        this.m1 = m1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        final PurchaseRelationship to_compare = (PurchaseRelationship) o;

        return this.getProduct() == to_compare.getProduct() &&
                this.getRelated() == to_compare.getRelated();
    }

    @Override
    public String toString() {
        return "PurchaseRelationship{" +
                "product=" + super.getProduct() +
                ", qty=" + super.getQty() +
                ", related=" + related +
                ", m1=" + String.format("%.2f", m1) +
                '}';
    }
}

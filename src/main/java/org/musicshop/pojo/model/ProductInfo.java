package org.musicshop.pojo.model;

import org.musicshop.pojo.Product;
import org.musicshop.pojo.Sizing;

import java.util.Objects;

/**
 * Originally used record type however class gives us easier polymorphism in this case.
 */
public class ProductInfo implements Product, Sizing {
    private long product;
    private long qty;

    public ProductInfo() {}

    public ProductInfo(final long product, final long qty) {

        this.setProduct(product);
        this.setQty(qty);
    }

    @Override
    public long getProduct() {
        return product;
    }

    @Override
    public void setProduct(long product) {
        this.product = product;
    }

    @Override
    public long getQty() {
        return qty;
    }

    @Override
    public void setQty(long qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "product=" + product +
                ", qty=" + qty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return product == that.product && qty == that.qty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, qty);
    }
}

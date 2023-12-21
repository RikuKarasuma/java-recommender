package org.musicshop.pojo.model;

import org.musicshop.pojo.Product;
import org.musicshop.pojo.User;

public class Sale implements Product, User {

    private long product;
    private long user;

    @Override
    public long getProduct() {
        return product;
    }

    @Override
    public void setProduct(long product) {
        this.product = product;
    }

    @Override
    public long getUser() {
        return user;
    }

    @Override
    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "product=" + product +
                ", user=" + user +
                '}';
    }
}

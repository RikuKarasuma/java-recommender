package org.musicshop.pojo.model;

public class User implements org.musicshop.pojo.User {

    private long userId;

    @Override
    public long getUser() {
        return userId;
    }

    @Override
    public void setUser(long user) {
        this.userId = user;
    }
}

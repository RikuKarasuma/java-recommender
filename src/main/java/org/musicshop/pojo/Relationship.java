package org.musicshop.pojo;

public interface Relationship extends Product {

    long getRelated();
    void setRelated(long related);
    long getM1();
    void setM1(long m1);
}

package org.musicshop.service;

public interface UserOutputService {

    void print(String msg);

    void print(Object msg);

    void error(String msg);
}

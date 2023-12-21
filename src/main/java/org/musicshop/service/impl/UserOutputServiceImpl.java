package org.musicshop.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.musicshop.service.UserOutputService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserOutputServiceImpl implements UserOutputService {
    @Override
    public void print(final String msg) {
        if (StringUtils.isBlank(msg))
            throw new IllegalArgumentException("ERROR: can't print null or empty message.");

        System.out.println(msg);
    }

    public void print(final Object object) {
        if (Objects.isNull(object))
            throw new IllegalArgumentException("ERROR: can't print null or empty message.");

        System.out.println(object);
    }

    @Override
    public void error(String msg) {
        if (StringUtils.isBlank(msg))
            throw new IllegalArgumentException("ERROR: can't print null or empty error message.");

        System.err.println(msg);
    }
}

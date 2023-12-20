package org.musicshop.service.impl;

import org.musicshop.service.UserInputService;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UserInputServiceImpl implements UserInputService {

    private final Scanner STD_IN = new Scanner(System.in);

    @Override
    public String read() {
        return STD_IN.nextLine();
    }
}

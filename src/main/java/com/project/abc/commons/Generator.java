package com.project.abc.commons;

import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

public class Generator {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static int getRandomNumber() {
        return RandomUtils.nextInt(100, 999999999);
    }

    public static int getRandom5DigitNumber() {
        return RandomUtils.nextInt(11111,95658);
    }

    public static String getRandomNumberAsString() {
        return String.valueOf(RandomUtils.nextInt(100, 999999999));
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

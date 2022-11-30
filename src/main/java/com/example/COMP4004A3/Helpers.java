package com.example.COMP4004A3;

import java.security.SecureRandom;

public class Helpers {
    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> tClass){
        int x = random.nextInt(tClass.getEnumConstants().length);
        return tClass.getEnumConstants()[x];
    }

    public static int randomInt(int min, int max){
        return random.nextInt(min, max);
    }

}

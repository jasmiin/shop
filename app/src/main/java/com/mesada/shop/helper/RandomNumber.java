package com.mesada.shop.helper;

import java.util.Random;

public class RandomNumber {

    public static long generateID() {
        Random rnd = new Random();
        char [] digits = new char[11];
        digits[0] = (char) (rnd.nextInt(9) + '1');
        for(int i=1; i<digits.length; i++) {
            digits[i] = (char) (rnd.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

}

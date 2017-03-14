package com.infoDiscover.common.util;

import java.util.Random;

/**
 * Created by sun.
 */
public class RandomUtil {

    public static int generateRandomInRange(int min, int max) {
        Random random = new Random();
        int value = random.nextInt(max) % (max - min + 1) + min;
        System.out.println("value: " + value);
        return value;
    }

    public static long generateRandomLong(long n) {
        Random random = new Random();
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }

    public static double generateRandomDouble(int min, int max) {
        Random random = new Random();
        // to generate a double random in range [min, max)
        return Double.valueOf(Math.floor(random.nextDouble() * (max + 1)));
    }

    public static String geterateRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateRandomDouble(1,10000));
    }
}

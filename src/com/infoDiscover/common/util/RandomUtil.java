package com.infoDiscover.common.util;

import java.util.Random;

/**
 * Created by sun.
 */
public class RandomUtil {

    public static int generateRandomInRange(int range[]) {
        int min = 0;
        int max = 0;

        if (range.length < 2) {
            min = range[0];
            max = range[0];
        } else {
            min = NumericUtil.minInt(range);
            max = NumericUtil.maxInt(range);
        }

        return generateRandomInRange(min, max);
    }

    public static int generateRandomInRange(int min, int max) {
        if(min == max){
            return min;
        }
        Random random = new Random();
        int value = random.nextInt(max) % (max - min + 1) + min;
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

    public static double generateRandomDouble(double range[]) {
        double min = 0d;
        double max = 0d;

        if (range.length < 2) {
            min = range[0];
            max = range[0];
        } else {
            min = NumericUtil.minDouble(range);
            max = NumericUtil.maxDouble(range);
        }

        return generateRandomDouble(min, max);
    }

    public static double generateRandomDouble(final double min, final double max) {
        if (min == max) {
            return min;
        }
        return min + ((max - min) * new Random().nextDouble());
    }

    public static String generateRandomString(int length) {
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

        System.out.println(generateRandomDouble(10000, 20000));
        System.out.println(generateRandomDouble(20000, 30000));
        System.out.println(generateRandomDouble(30000, 40000));
        System.out.println(generateRandomDouble(40000, 50000));
        System.out.println(generateRandomDouble(50000, 60000));
        System.out.println(generateRandomDouble(60000, 70000));
        System.out.println(generateRandomDouble(70000, 80000));


        generateRandomInRange(0,0);
    }
}

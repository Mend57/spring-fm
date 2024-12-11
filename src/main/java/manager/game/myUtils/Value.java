package manager.game.myUtils;

import lombok.Getter;

public class Value {

    @Getter
    private static final int ATTRIBUTES_THRESHOLD = 20, MINIMUM_ATTRIBUTES = 1;

    public static int normalize(int number, int start, int limit){
        double normalized = MINIMUM_ATTRIBUTES + ((double)number - start) / (limit - start) * (ATTRIBUTES_THRESHOLD - MINIMUM_ATTRIBUTES);
        if(normalized >= limit) return limit;
        if (normalized <= MINIMUM_ATTRIBUTES) return MINIMUM_ATTRIBUTES;
        return (int)Math.round(normalized);
    }

    public static double normalize(double number, double start, double limit){
        double normalized = MINIMUM_ATTRIBUTES + (number - start) / (limit - start) * (ATTRIBUTES_THRESHOLD - MINIMUM_ATTRIBUTES);
        if(normalized >= limit) return limit;
        if (normalized <= MINIMUM_ATTRIBUTES) return MINIMUM_ATTRIBUTES;
        return normalized;
    }

    public static int concatenateInts(int num1, int num2){
        return Integer.parseInt("" + num1 + num2);
    }

    public static int reverseNumber(int number) {
        int reversed = 0;
        while (number != 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }
        return reversed;
    }

}
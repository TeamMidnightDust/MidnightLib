package eu.midnightdust.lib.util;

public class MidnightMathUtil {
    public static boolean isEven(int i) {
        return (i | 1) > i;
    }
}

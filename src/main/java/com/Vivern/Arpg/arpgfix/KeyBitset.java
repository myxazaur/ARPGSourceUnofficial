package com.vivern.arpg.arpgfix;

public final class KeyBitset {
    public static final int LONG_COUNT = (KeyboardConstants.KEY_COUNT + 63) / 64; // 4

    public static boolean get(long[] bits, int key) {
        int i = key >>> 6;        // /64
        int o = key & 63;         // %64
        return (bits[i] & (1L << o)) != 0;
    }

    public static void set(long[] bits, int key, boolean value) {
        int i = key >>> 6;
        int o = key & 63;
        long mask = 1L << o;
        if (value) bits[i] |= mask;
        else bits[i] &= ~mask;
    }

    public static boolean equals(long[] a, long[] b) {
        if (a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                return false;
        return true;
    }
}
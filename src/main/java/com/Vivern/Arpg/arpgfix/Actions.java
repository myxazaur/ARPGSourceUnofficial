package com.Vivern.Arpg.arpgfix;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public final class Actions {
    private Actions() {}

    public static final Int2IntOpenHashMap transformer = new Int2IntOpenHashMap();

    static {
        Init.huy();
    }

    private static class Init {
        static {
            transformer.put(KeyboardConstants_CustomKeys.FORWARD, FORWARD);
            transformer.put(KeyboardConstants_CustomKeys.RIGHT, RIGHT);
            transformer.put(KeyboardConstants_CustomKeys.LEFT, LEFT);
            transformer.put(KeyboardConstants_CustomKeys.BACK, BACK);
            transformer.put(KeyboardConstants_CustomKeys.JUMP, JUMP);
            transformer.put(KeyboardConstants_CustomKeys.SPRINT, SPRINT);
            transformer.put(KeyboardConstants_CustomKeys.PRIMARYATTACK, PRIMARYATTACK);
            transformer.put(KeyboardConstants_CustomKeys.SECONDARYATTACK, SECONDARYATTACK);
//            transformer.put(KeyboardConstants_CustomKeys.HEADABILITY, HEADABILITY); // Дубликат
            transformer.put(KeyboardConstants_CustomKeys.SCOPE, SCOPE);
            transformer.put(KeyboardConstants_CustomKeys.GRAPLINGHOOK, GRAPLINGHOOK);
//            transformer.put(KeyboardConstants_CustomKeys.USE, USE); // Дубликат
//            transformer.put(KeyboardConstants_CustomKeys.GRENADE, GRENADE); // Дубликат
        }

        private static void huy() {

        }
    }

    public static final int FORWARD       = 1;
    public static final int RIGHT         = 2;
    public static final int LEFT          = 4;
    public static final int BACK          = 8;
    public static final int JUMP          = 16;
    public static final int SPRINT        = 32;
    public static final int PRIMARYATTACK = 64;     // LMB
    public static final int SECONDARYATTACK=128;    // RMB
//    public static final int HEADABILITY   = 256;
    public static final int SCOPE         = 512;
    public static final int GRAPLINGHOOK  = 1024;
    public static final int DOUBLEJUMP    = 2048;
    public static final int SCOPEACTIVE   = 4096;
//    public static final int USE           = 8192;
//    public static final int GRENADE       = 16384;
//    public static final int GRENADE       = 8192;
}
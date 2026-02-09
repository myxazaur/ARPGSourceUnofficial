package com.Vivern.Arpg.arpgfix;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerKeyState {

    private final long[] keysDown = new long[KeyBitset.LONG_COUNT]; // raw keyboard
    private int packedActions;                                      // actions mask

    private boolean mouseLeft;
    private boolean mouseRight;

    public void updateFromClient(long[] incomingKeysDown, int packedActions, boolean mouseLeft, boolean mouseRight) {
        if (incomingKeysDown != null) {
            System.arraycopy(incomingKeysDown, 0, keysDown, 0, Math.min(incomingKeysDown.length, keysDown.length));
        }
        this.packedActions = packedActions;
        this.mouseLeft = mouseLeft;
        this.mouseRight = mouseRight;
    }

    // ====== raw keyboard ======
    public boolean isRawKeyDown(int keyCode) {
        return keyCode >= 0 && keyCode < KeyboardConstants.KEY_COUNT && KeyBitset.get(keysDown, keyCode);
    }

    // ====== actions ======
    public boolean isActionDown(int actionMask) {
        return (packedActions & actionMask) != 0;
    }

    public int getPackedActions() { return packedActions; }

    public boolean getMouseLeft()  { return mouseLeft; }
    public boolean getMouseRight() { return mouseRight; }

    // ===== storage =====
    private static final Map<UUID, PlayerKeyState> storage = new HashMap<>();

    public static PlayerKeyState get(EntityPlayer player) {
        return storage.computeIfAbsent(player.getUniqueID(), k -> new PlayerKeyState());
    }
}
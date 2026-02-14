package com.vivern.arpg.arpgfix;

import net.minecraft.entity.player.EntityPlayer;

public interface ItemKeyPressInfo {

    default boolean getMouseButtonLeft(EntityPlayer player) {
        return PlayerKeyState.get(player).getMouseLeft();
    }

    default boolean getMouseButtonRight(EntityPlayer player) {
        return PlayerKeyState.get(player).getMouseRight();
    }

    /**
     * Если тебе реально нужен именно raw LWJGL keycode (17=W и т.п.)
     */
    default boolean isRawKeyPressed(EntityPlayer player, int keyCode) {
        return PlayerKeyState.get(player).isRawKeyDown(keyCode);
    }

    /**
     * То, что в оригинале было "unpackCheckKey"
     */
    default boolean isActionPressed(EntityPlayer player, int actionMask) {
        return PlayerKeyState.get(player).isActionDown(actionMask);
    }

    default boolean isKeyPressed(EntityPlayer player, int keyID) {
        return isActionPressed(player, Transformer.getActionMaskFromKeyID(keyID));
    }

    static class Transformer {
        public static int getActionMaskFromKeyID(int keyID) {
            return Actions.transformer.get(keyID);
        }
    }
}
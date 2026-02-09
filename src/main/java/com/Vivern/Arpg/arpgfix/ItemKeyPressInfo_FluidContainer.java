package com.Vivern.Arpg.arpgfix;

import net.minecraftforge.fluids.capability.ItemFluidContainer;

public class ItemKeyPressInfo_FluidContainer extends ItemFluidContainer implements ItemKeyPressInfo {

    /**
     * @param capacity The maximum capacity of this fluid container.
     */
    public ItemKeyPressInfo_FluidContainer(int capacity) {
        super(capacity);
    }
}
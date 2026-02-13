package com.vivern.arpg.arpgfix;

import net.minecraftforge.fluids.capability.ItemFluidContainer;

public class ItemKeyPressInfo_FluidContainer extends ItemFluidContainer implements ItemKeyPressInfo {

    /**
     * @param capacity The maximum capacity of this fluid container.
     */
    public ItemKeyPressInfo_FluidContainer(int capacity) {
        super(capacity);
    }
}
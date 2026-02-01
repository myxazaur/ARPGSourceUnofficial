package com.vivern.arpg.potions;

public class FiberBandaging extends AdvancedPotion {
   protected FiberBandaging(int index) {
      super(false, 9341543, index, false);
      this.setRegistryName("arpg:fiber_bandaging");
      this.setPotionName("Fiber_Bandaging");
      this.setIconIndex(40, 1);
   }
}

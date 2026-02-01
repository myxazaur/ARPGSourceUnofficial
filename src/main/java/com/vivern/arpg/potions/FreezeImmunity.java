package com.vivern.arpg.potions;

public class FreezeImmunity extends AdvancedPotion {
   public FreezeImmunity(int index) {
      super(false, 15524535, index, false);
      this.setRegistryName("arpg:freeze_immunity");
      this.setPotionName("Freeze_immunity");
      this.setIconIndex(41, 1);
   }
}

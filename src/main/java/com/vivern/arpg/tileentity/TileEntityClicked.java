package com.vivern.arpg.tileentity;

import net.minecraft.entity.EntityLivingBase;

public interface TileEntityClicked {
   default void mouseClick(int mouseX, int mouseY, int mouseButton) {
   }

   default void mouseClick(EntityLivingBase sender, int mouseX, int mouseY, int mouseButton) {
      this.mouseClick(mouseX, mouseY, mouseButton);
   }
}

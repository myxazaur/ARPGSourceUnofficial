package com.Vivern.Arpg.elements.animation;

import net.minecraft.nbt.NBTTagCompound;

public class NBTTagReimagined extends NBTTagCompound {
   private ItemStackImagine imagine;

   public ItemStackImagine getOrCreateImagine() {
      if (this.imagine == null) {
         this.imagine = new ItemStackImagine();
      }

      return this.imagine;
   }
}

package com.Vivern.Arpg.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMagicInterflow extends TileEntity {
   public int type;
   public int power;

   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      if (compound.hasKey("type")) {
         this.type = compound.getInteger("type");
      } else {
         this.type = 0;
      }

      if (compound.hasKey("power")) {
         this.power = compound.getInteger("power");
      } else {
         this.power = 0;
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setInteger("type", this.type);
      compound.setInteger("power", this.power);
      return compound;
   }
}

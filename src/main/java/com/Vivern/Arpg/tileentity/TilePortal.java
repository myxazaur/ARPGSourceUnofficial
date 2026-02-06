package com.Vivern.Arpg.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TilePortal extends TileEntity {
   public boolean isMainPortalBlock = false;
   public BlockPos mainBlockPosition = null;
   public BlockPos linkedPortal = null;
   public int dimensionToTeleport;

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("mainBlockposX") && compound.hasKey("mainBlockposY") && compound.hasKey("mainBlockposZ")) {
         this.mainBlockPosition = new BlockPos(
            compound.getInteger("mainBlockposX"), compound.getInteger("mainBlockposY"), compound.getInteger("mainBlockposZ")
         );
      }

      if (compound.hasKey("linkedPortalX") && compound.hasKey("linkedPortalY") && compound.hasKey("linkedPortalZ")) {
         this.linkedPortal = new BlockPos(
            compound.getInteger("linkedPortalX"), compound.getInteger("linkedPortalY"), compound.getInteger("linkedPortalZ")
         );
      }

      if (compound.hasKey("isMainPortalBlock")) {
         this.isMainPortalBlock = compound.getBoolean("isMainPortalBlock");
      }

      if (compound.hasKey("dimensionToTeleport")) {
         this.dimensionToTeleport = compound.getInteger("dimensionToTeleport");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      if (this.mainBlockPosition != null) {
         compound.setInteger("mainBlockposX", this.mainBlockPosition.getX());
         compound.setInteger("mainBlockposY", this.mainBlockPosition.getY());
         compound.setInteger("mainBlockposZ", this.mainBlockPosition.getZ());
      }

      if (this.linkedPortal != null) {
         compound.setInteger("linkedPortalX", this.linkedPortal.getX());
         compound.setInteger("linkedPortalY", this.linkedPortal.getY());
         compound.setInteger("linkedPortalZ", this.linkedPortal.getZ());
      }

      compound.setBoolean("isMainPortalBlock", this.isMainPortalBlock);
      compound.setInteger("dimensionToTeleport", this.dimensionToTeleport);
      return super.writeToNBT(compound);
   }
}

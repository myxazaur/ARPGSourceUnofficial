//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileElectricSieve extends TileSieve {
   public EnergyStorage electricStorage = new EnergyStorage(500, 100, 100, 0);

   @Override
   public void update() {
      super.update();
      if (!this.world.isRemote && this.electricStorage.extractEnergy(30, true) == 30) {
         this.electricStorage.extractEnergy(30, false);
         this.addSievePower(4, 65);
      }
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == CapabilityEnergy.ENERGY ? this.electricStorage : super.getCapability(capability, facing));
   }

   @Override
   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      return super.writeToNBT(compound);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(500, 100, 100, compound.getInteger("rf"));
      }

      super.readFromNBT(compound);
   }
}

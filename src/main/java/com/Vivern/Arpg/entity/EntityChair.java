package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.blocks.Chair;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityChair extends Entity {
   public EntityChair(World world) {
      super(world);
   }

   public void entityInit() {
      this.setSize(0.0F, 0.0F);
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (!this.isBeingRidden()) {
         this.setDead();
      }

      if (!(this.world.getBlockState(this.getPosition()).getBlock() instanceof Chair)) {
         this.setDead();
      }
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
   }
}

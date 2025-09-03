//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FreezingParticle extends Entity {
   public EntityLivingBase entityON;
   public ModelBase model;

   public FreezingParticle(World worldIn) {
      super(worldIn);
   }

   public FreezingParticle(World worldIn, EntityLivingBase entityON) {
      super(worldIn);
      this.entityON = entityON;
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   public void onUpdate() {
      if (this.ticksExisted > 1) {
         this.setDead();
      }
   }
}

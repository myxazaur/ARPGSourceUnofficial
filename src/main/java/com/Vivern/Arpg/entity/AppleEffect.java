//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class AppleEffect extends EntityThrowable {
   public AppleEffect(World world) {
      super(world);
   }

   public AppleEffect(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public AppleEffect(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)4);
         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 10) {
         this.setDead();
      }
   }

   protected float getGravityVelocity() {
      return 0.001F;
   }
}

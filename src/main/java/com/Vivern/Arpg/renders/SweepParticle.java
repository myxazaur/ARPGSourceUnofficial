//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SweepParticle extends Entity {
   public float scale;
   public int livetime;
   public int ticksPerFrame;
   public int light;
   public ResourceLocation texture;
   public float Red;
   public float Green;
   public float Blue;
   public boolean opa;
   public int rotation;
   public float scaleTickAdding = 0.0F;
   public float scaleMax = 1.0E9F;
   public float alphaTickAdding = 0.0F;
   public float alpha = 1.0F;
   public boolean alphaGlowing = false;
   public boolean acidRender = false;
   public float pitch = 0.0F;
   public float yaw = 0.0F;
   public int frameCount;
   public int schedule = 0;

   public SweepParticle(
      ResourceLocation texture,
      float scale,
      int livetime,
      int ticksPerFrame,
      int frameCount,
      int schedule,
      int light,
      World world,
      double x,
      double y,
      double z,
      float pitch,
      float yaw,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int rotation
   ) {
      super(world);
      this.setPosition(x, y, z);
      this.setSize(0.2F, 0.2F);
      this.scale = scale;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotation = rotation;
      this.alphaGlowing = false;
      this.ticksPerFrame = ticksPerFrame;
      this.yaw = yaw;
      this.pitch = pitch;
      this.frameCount = frameCount;
      this.schedule = schedule;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.scale < this.scaleMax) {
         this.scale = this.scale + this.scaleTickAdding;
      }

      if (this.ticksExisted >= this.livetime) {
         this.setDead();
      }

      this.alpha = this.alpha + this.alphaTickAdding;
   }

   public boolean isInWater() {
      return false;
   }

   public boolean isPushedByWater() {
      return false;
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}

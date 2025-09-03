//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GraplingHookParticle extends Entity {
   public float scale = 0.1F;
   public int light = 240;
   public ResourceLocation texture;
   public ResourceLocation texture2;
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public float alpha = 1.0F;
   public float alphatime = 0.2F;
   public double distance;
   public int livetime = 15;
   public float animation = 0.0F;
   public int Tickpl;
   public float lengh = 1.0F;
   public float rotatPitch = 0.0F;
   public float rotatYaw = 0.0F;
   public float pikeLength;
   public float pikeScale;
   public Entity entityOn;

   public GraplingHookParticle(
      World worldIn,
      ResourceLocation texture,
      ResourceLocation texture2,
      float scale,
      int light,
      float Red,
      float Green,
      float Blue,
      float alphatime,
      double distance,
      int livetime,
      float animation,
      float lengh,
      int ticksexisted,
      float pikeLength,
      float pikeScale,
      Vec3d from,
      Vec3d to
   ) {
      super(worldIn);
      this.scale = scale;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.distance = distance;
      this.livetime = livetime;
      this.animation = animation;
      this.Tickpl = ticksexisted;
      this.lengh = lengh;
      this.texture2 = texture2;
      this.pikeLength = pikeLength;
      this.pikeScale = pikeScale;
      float mx = (float)(from.x - to.x);
      float mz = (float)(from.z - to.z);
      float my = (float)(to.y - from.y);
      float moti_zx = (float)Math.sqrt(mx * mx + mz * mz);
      float moti_zy = (float)Math.sqrt(my * my + mz * mz);
      float cosangle_Yaw = mz / moti_zx;
      float cosangle_Pitch = mz / moti_zy;
      float angle_Yaw = (float)Math.toDegrees(Math.acos(cosangle_Yaw));
      float angle_Pitch = (float)Math.toDegrees(Math.acos(cosangle_Pitch));
      float tanangle = my / moti_zx;
      float angle = (float)Math.toDegrees(Math.atan(tanangle));
      if (mx > 0.0F) {
         angle_Yaw = -angle_Yaw;
      }

      if (my < 0.0F) {
         angle_Pitch = -angle_Pitch;
      }

      this.rotatYaw = angle_Yaw + 180.0F;
      this.rotatPitch = -angle;
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }
   }
}

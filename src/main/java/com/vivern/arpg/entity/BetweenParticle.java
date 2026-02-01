package com.vivern.arpg.entity;

import com.vivern.arpg.renders.ParticleTracker;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BetweenParticle extends Entity {
   public float scale = 0.1F;
   public int light = 240;
   public ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public float alpha = 1.0F;
   public float alphatime = 0.2F;
   public double distance;
   public int livetime = 15;
   public float animation = 0.0F;
   public float texlengh = 1.0F;
   public float rotatPitch = 0.0F;
   public float rotatYaw = 0.0F;
   public boolean alphaGlowing = false;
   public boolean horizontal = true;
   public float offset = 0.0F;
   public float texstart = 1.0F;
   public boolean softAnimation = false;
   public ParticleTracker tracker = null;

   public BetweenParticle(
      World worldIn,
      ResourceLocation texture,
      float scale,
      int light,
      float Red,
      float Green,
      float Blue,
      float alphatime,
      double distance,
      int livetime,
      float animation,
      float texlengh,
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
      this.texlengh = texlengh;
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
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
   }

   public BetweenParticle(World worldIn) {
      super(worldIn);
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.tracker != null) {
         this.tracker.update(this);
      }

      this.alpha = this.alpha - this.alphatime;
      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }
   }

   public boolean shouldRenderInPass(int pass) {
      return pass == 1;
   }
}

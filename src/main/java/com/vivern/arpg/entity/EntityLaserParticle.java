package com.vivern.arpg.entity;

import com.vivern.arpg.main.GetMOP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityLaserParticle extends Entity {
   public float scale = 0.1F;
   public int livetime = 15;
   public int light = 240;
   public ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public EntityPlayer player;
   public float rotP;
   public float rotW;
   public float alpha = 0.2F;
   public final double distance;
   public float alphatime = 0.2F;
   public float lengh = 1.0F;
   public float horizOffset = 0.2F;
   public boolean horizontal = true;
   public int timeToAddAlpha = -1;
   public int timeToDecrAlpha = -1;
   public float visibleZone = 1.0F;
   public float animation = 0.0F;
   public float animationDisplace = 0.0F;

   public EntityLaserParticle(
      World worldIn, EntityPlayer player, ResourceLocation texture, float scale, int livetime, int light, float Red, float Green, float Blue, float alphatime
   ) {
      super(worldIn);
      this.player = player;
      this.rotP = player.rotationPitch;
      this.rotW = player.rotationYaw;
      this.scale = scale;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      Vec3d vec = GetMOP.PosRayTrace(100.0, 1.0F, player, 0.01, 0.01);
      this.distance = player.getDistance(vec.x, vec.y, vec.z);
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
   }

   public EntityLaserParticle(
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
      float lengh,
      float rotatPitch,
      float rotatYaw
   ) {
      super(worldIn);
      this.rotP = rotatPitch;
      this.rotW = rotatYaw;
      this.scale = scale;
      this.livetime = livetime;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.distance = distance;
      this.alphatime = alphatime;
      this.lengh = lengh;
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

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.models.LaserModel;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityStreamLaserP extends Entity {
   public float scale = 0.1F;
   public int light = 240;
   public ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public EntityPlayer player;
   public float alpha = 1.0F;
   public float alphatime = 0.2F;
   public double distance;
   public int livetime = 15;
   public float animation = 0.0F;
   public int Tickpl;
   public float lengh = 1.0F;
   public float rotatPitch = 0.0F;
   public float rotatYaw = 0.0F;
   public float prevrotatPitch = 0.0F;
   public float prevrotatYaw = 0.0F;
   public float horizOffset = 0.2F;
   public boolean horizontal = true;
   public boolean useModel = false;
   public LaserModel model;
   public float distanceStart = 0.0F;
   public boolean useOldPositioning = true;
   public float barrelLength;
   public float shoulders;
   public float yoffset;
   public EnumHand hand;
   public long creationDate;

   public EntityStreamLaserP(
      World worldIn,
      EntityPlayer player,
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
      float lengh
   ) {
      super(worldIn);
      this.player = player;
      this.scale = scale;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.distance = distance;
      this.livetime = livetime;
      this.animation = animation;
      this.Tickpl = player.ticksExisted;
      this.lengh = lengh;
      this.ignoreFrustumCheck = true;
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
      this.creationDate = worldIn.getTotalWorldTime() + 1L;
   }

   public EntityStreamLaserP(
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
      float lengh,
      float rotatPitch,
      float rotatYaw,
      int ticksexisted
   ) {
      super(worldIn);
      this.player = null;
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
      this.rotatPitch = rotatPitch;
      this.rotatYaw = rotatYaw;
      this.prevrotatPitch = rotatPitch;
      this.prevrotatYaw = rotatYaw;
      this.ignoreFrustumCheck = true;
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
      this.creationDate = worldIn.getTotalWorldTime() + 1L;
   }

   public boolean shouldRenderInPass(int pass) {
      return pass == 1;
   }

   public boolean isOutsideBorder() {
      return super.isOutsideBorder();
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   public void resetPlayerArmPitchYaw(float partialTicks) {
      if (this.player != null && this.player instanceof EntityPlayerSP) {
         EntityPlayerSP playerSP = (EntityPlayerSP)this.player;
         float playerYaw = GetMOP.partial(playerSP.rotationYaw, playerSP.prevRotationYaw, partialTicks);
         float playerPitch = GetMOP.partial(playerSP.rotationPitch, playerSP.prevRotationPitch, partialTicks);
         playerSP.renderArmPitch = playerPitch + (playerSP.rotationPitch - playerSP.prevRotationPitch);
         playerSP.renderArmYaw = playerYaw + (playerSP.rotationYaw - playerSP.prevRotationYaw);
         playerSP.prevRenderArmPitch = playerSP.renderArmPitch;
         playerSP.prevRenderArmYaw = playerSP.renderArmYaw;
      }
   }

   public Vec3d getPositionEyes(float partialTicks) {
      if (this.useOldPositioning) {
         if (partialTicks == 1.0F) {
            return new Vec3d(this.posX, this.posY, this.posZ);
         } else {
            double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
            double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks;
            double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
            return new Vec3d(d0, d1, d2);
         }
      } else {
         return this.player == Minecraft.getMinecraft().player && Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 0
            ? Weapons.getPlayerShootPoint(this.player, this.hand, partialTicks, this.barrelLength, this.shoulders, this.yoffset, false)
            : Weapons.getPlayerShootPoint(this.player, this.hand, partialTicks, this.barrelLength, this.shoulders, this.yoffset, true);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.player != null && this.useOldPositioning) {
         this.setPosition(this.player.posX, this.player.posY + 1.55, this.player.posZ);
      }

      if (this.ticksExisted > this.livetime || this.world.getTotalWorldTime() > this.creationDate + this.livetime) {
         this.setDead();
      }
   }
}

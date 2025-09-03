//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.mobs.AbstractMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class AbstractMobModel extends ModelBase {
   public static float lbX = 0.0F;
   public static float lbY = 0.0F;
   public static boolean disabledLight = false;

   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      int anim1 = 0;
      int anim2 = 0;
      int anim3 = 0;
      int anim4 = 0;
      boolean isAbstractMob = false;
      if (entityIn instanceof AbstractMob) {
         AbstractMob mob = (AbstractMob)entityIn;
         anim1 = mob.animations[0];
         anim2 = mob.animations[1];
         anim3 = mob.animations[2];
         anim4 = mob.animations[3];
         isAbstractMob = true;
      }

      this.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, anim1, anim2, anim3, anim4, isAbstractMob);
   }

   public abstract void render(
      Entity var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, int var10, int var11, boolean var12
   );

   public static void light(int light, boolean add) {
      lbX = OpenGlHelper.lastBrightnessX;
      lbY = OpenGlHelper.lastBrightnessY;
      GL11.glDisable(2896);
      disabledLight = true;
      if (add) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, light + lbX), Math.min(240.0F, light + lbY));
      } else {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240, light), Math.min(240, light));
      }
   }

   public static void returnlight() {
      if (disabledLight) {
         GL11.glEnable(2896);
         disabledLight = false;
      }

      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
   }

   public static void alphaGlow() {
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   }

   public static void alphaGlowDisable() {
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
   }

   public static void copyModel(ModelRenderer source, ModelRenderer dest) {
      dest.rotateAngleX = source.rotateAngleX;
      dest.rotateAngleY = source.rotateAngleY;
      dest.rotateAngleZ = source.rotateAngleZ;
      dest.rotationPointX = source.rotationPointX;
      dest.rotationPointY = source.rotationPointY;
      dest.rotationPointZ = source.rotationPointZ;
      dest.showModel = source.showModel;
   }

   public static void setLightmapWithBrightnessForRender(Entity entity) {
      BlockPos blockpos = new BlockPos(entity.posX, entity.posY, entity.posZ);
      int i = entity.world.isBlockLoaded(blockpos) ? entity.world.getCombinedLight(blockpos, 0) : 0;
      int j = i >> 16 & 65535;
      int k = i & 65535;
      lbX = OpenGlHelper.lastBrightnessX;
      lbY = OpenGlHelper.lastBrightnessY;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240, j), Math.min(240, k));
   }

   public static void setLightmapWithBrightnessForRender(World world, BlockPos blockpos) {
      int i = world.isBlockLoaded(blockpos) ? world.getCombinedLight(blockpos, 0) : 0;
      int j = i >> 16 & 65535;
      int k = i & 65535;
      lbX = OpenGlHelper.lastBrightnessX;
      lbY = OpenGlHelper.lastBrightnessY;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240, j), Math.min(240, k));
   }

   public static int[] getBrightnessForRender(World world, BlockPos blockpos) {
      int i = world.isBlockLoaded(blockpos) ? world.getCombinedLight(blockpos, 0) : 0;
      int j = i >> 16 & 65535;
      int k = i & 65535;
      return new int[]{Math.min(240, j), Math.min(240, k)};
   }
}

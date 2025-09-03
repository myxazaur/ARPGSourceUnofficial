//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.shader.ShaderMain;
import com.Vivern.Arpg.tileentity.TileSpellForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.ARBShaderObjects;

public class SpellForgeTESR extends TileEntitySpecialRenderer<TileSpellForge> {
   public static float[] translationsX = new float[]{1.0F, 0.0F, -1.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, -1.0F};
   public static float[] translationsZ = new float[]{1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0F, -1.0F, -1.0F};

   public static float getBob(float partialTicks, int number, float amplitude) {
      return MathHelper.sin((AnimationTimer.normaltick + partialTicks) / 20.0F + number * 7.173589F) * amplitude + amplitude - 0.028F;
   }

   public static float getRotate(float partialTicks, int number) {
      return ((AnimationTimer.normaltick + partialTicks) / 40.0F + number * 7.173589F) * (float) (180.0 / Math.PI);
   }

   public static void renderItemMolten(ItemStack stack, float power) {
      RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
      ShaderMain.ForgeShader.start();
      ARBShaderObjects.glUniform1fARB(ShaderMain.ForgeShader.getUniform("time"), AnimationTimer.tick / 40.0F);
      ARBShaderObjects.glUniform1fARB(ShaderMain.ForgeShader.getUniform("power"), power);
      if (!stack.isEmpty()) {
         IBakedModel ibakedmodel = renderItem.getItemModelWithOverrides(stack, (World)null, (EntityLivingBase)null);
         Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         GlStateManager.enableBlend();
         if (Debugger.floats[8] > 0.0F) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         }

         GlStateManager.pushMatrix();
         ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, TransformType.GROUND, false);
         renderItem.renderItem(stack, ibakedmodel);
         GlStateManager.popMatrix();
         GlStateManager.disableBlend();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      ShaderMain.ForgeShader.stop();
   }

   public void render(TileSpellForge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
      GlStateManager.rotate(te.rotation, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(-x - 0.5, -y - 0.5, -z - 0.5);
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      float fy = 1.1125F;
      float size = 0.65F;
      float unratio = GetMOP.partial(te.animRoundChange, te.prevanimRoundChange, partialTicks);
      float ratio = 1.0F - unratio;
      int stacksAmount = 0;

      for (int cell = 0; cell < 9; cell++) {
         ItemStack stack0 = (ItemStack)te.stacks.get(cell);
         if (!stack0.isEmpty()) {
            stacksAmount++;
         }
      }

      float oneAngle = (float)((Math.PI * 2) / stacksAmount);
      int stacksForgedAmount = te.currentForging == null ? 0 : (int)(te.currentForging.hammerCompleteness / te.currentForging.hitsNeed * stacksAmount);
      stacksForgedAmount = MathHelper.clamp(stacksForgedAmount, 0, stacksAmount);
      if (te.animItemsForgedFlags == null || te.animItemsForgedFlags.length != stacksAmount) {
         te.animItemsForgedFlags = new boolean[stacksAmount];
         te.animItemsForged = new float[stacksAmount];
         te.prevanimItemsForged = new float[stacksAmount];
      }

      for (int sf = 0; sf < stacksForgedAmount; sf++) {
         te.animItemsForgedFlags[sf] = true;
      }

      stacksAmount = 0;

      for (int cellx = 0; cellx < 9; cellx++) {
         ItemStack stack0 = (ItemStack)te.stacks.get(cellx);
         if (!stack0.isEmpty()) {
            float animTtemForged = GetMOP.partial(te.animItemsForged[stacksAmount], te.prevanimItemsForged[stacksAmount], partialTicks);
            float completeRadiusAdd = 0.0F;
            if (te.currentForging != null && te.currentForging.hammerCompleteness >= te.currentForging.hitsNeed) {
               completeRadiusAdd = animTtemForged;
            }

            float radius = 0.9F - animTtemForged * 0.7F - completeRadiusAdd * 0.1F;
            float roundx = radius * MathHelper.sin(-AnimationTimer.tick / 25.0F + oneAngle * stacksAmount);
            float roundz = radius * MathHelper.cos(-AnimationTimer.tick / 25.0F + oneAngle * stacksAmount);
            float translX = translationsX[cellx] * 0.34F * ratio + roundx * unratio;
            float translZ = translationsZ[cellx] * 0.34F * ratio + roundz * unratio;
            GlStateManager.pushMatrix();
            GlStateManager.translate(
               (float)x + 0.5F + translX, (float)y + fy + getBob(partialTicks, 0, 0.06F - ratio * 0.05F - animTtemForged * 0.05F), (float)z + 0.5F + translZ
            );
            GlStateManager.scale(size, size, size);
            stacksAmount++;
            GlStateManager.rotate(getRotate(partialTicks, 0), 0.0F, 1.0F, 0.0F);
            if (completeRadiusAdd > 0.0F) {
               renderItemMolten(stack0, completeRadiusAdd);
            } else {
               Minecraft.getMinecraft().getRenderItem().renderItem(stack0, TransformType.GROUND);
            }

            GlStateManager.popMatrix();
         }
      }

      GlStateManager.popMatrix();
   }
}

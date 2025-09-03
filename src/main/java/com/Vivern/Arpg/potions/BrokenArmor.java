//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.PropertiesRegistry;
import gloomyfolkenvivern.arpghooklib.example.AnnotationHooks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BrokenArmor extends AdvancedPotion {
   public static final ResourceLocation textur1 = new ResourceLocation("arpg:textures/cracks.png");
   public static final ResourceLocation textur2 = new ResourceLocation("arpg:textures/cracks_a.png");
   public static final ResourceLocation textur3 = new ResourceLocation("arpg:textures/cracks_b.png");
   public static final ResourceLocation textur4 = new ResourceLocation("arpg:textures/cracks_c.png");

   public BrokenArmor(int index) {
      super(true, 7375264, index, true);
      this.setRegistryName("arpg:broken_armor");
      this.setPotionName("Broken_Armor");
      this.setIconIndex(33, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.ARMOR_PROTECTION, MathHelper.getRandomUUID().toString(), -1.0, 2);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, MathHelper.getRandomUUID().toString(), -1.0, 2);
      this.shouldRender = true;
   }

   public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
      return modifier.getAmount() * (1.0 - 1.0 / (amplifier + 1.5));
   }

   @Override
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
      ResourceLocation tex = textur1;
      if (entityRenderer instanceof RenderLivingBase) {
         RenderLivingBase rlb = (RenderLivingBase)entityRenderer;
         if (rlb.getMainModel() != null) {
            int width = rlb.getMainModel().textureWidth;
            int height = rlb.getMainModel().textureHeight;
            if (width <= 40 && height <= 20) {
               tex = textur3;
            } else if (width <= 40 && height <= 40) {
               tex = textur4;
            } else if (width <= 70 && height <= 40) {
               tex = textur2;
            }
         }
      }

      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.matrixMode(5888);
      AnnotationHooks.bindEnotherTexture = tex;
      entityRenderer.doRender(entityOnEffect, x, y, z, yaw, partialTicks);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      AnnotationHooks.bindEnotherTexture = null;
   }
}

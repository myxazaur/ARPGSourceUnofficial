//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.PropertiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ColdSoul extends AdvancedPotion {
   public static final ResourceLocation textur = new ResourceLocation("arpg:textures/circle_cold_soul.png");

   public ColdSoul(int index) {
      super(true, 12709119, index, true);
      this.setRegistryName("arpg:cold_soul");
      this.setPotionName("Cold_Soul");
      this.setIconIndex(38, 1);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), 0.0, 2);
      this.registerPotionAttributeModifier(PropertiesRegistry.AIRBORNE_MOBILITY, MathHelper.getRandomUUID().toString(), 0.0, 2);
      this.shouldRender = true;
   }

   @Override
   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
      Entity trueSource = event.getSource().getTrueSource();
      if (trueSource != null && !trueSource.world.isRemote && trueSource instanceof EntityPlayer) {
         EntityLivingBase entityLivingBase = event.getEntityLiving();
         Mana.giveMana((EntityPlayer)trueSource, entityLivingBase.getMaxHealth() / 10.0F);
      }
   }

   public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
      return -(-1.0 / (amplifier + 1.7) + 0.9);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
      this.renderCircle(
         x, y + 0.3 * entityOnEffect.height, z, (entityOnEffect.ticksExisted + partialTicks) * 2.0F, 1.3F * entityOnEffect.width
      );
      this.renderCircle(
         x, y + 0.6 * entityOnEffect.height, z, (entityOnEffect.ticksExisted + partialTicks) * 1.713F + 170.0F, 1.1F * entityOnEffect.width
      );
   }

   public void renderCircle(double x, double y, double z, float rotationY, float size) {
      GlStateManager.pushMatrix();
      GL11.glDisable(2884);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.translate(x, y, z);
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(rotationY / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(rotationY / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.scale(size, size, size);
      Minecraft.getMinecraft().renderEngine.bindTexture(textur);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glEnable(2884);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.popMatrix();
   }
}

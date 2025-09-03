//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ChestLock {
   private static BlockModel lockmodel = new BlockModel(true);
   private static BlockModel cursedlockmodel = new BlockModel(false);
   private static ResourceLocation lock = new ResourceLocation("arpg:textures/chest_lock.png");
   public static ResourceLocation lock_winter = new ResourceLocation("arpg:textures/chest_lock_winter.png");
   public static ResourceLocation lock_siren = new ResourceLocation("arpg:textures/chest_lock_siren.png");
   public static ResourceLocation lock_dolerite = new ResourceLocation("arpg:textures/chest_lock_dolerite.png");
   public static ArrayList<ChestLock> locksRegister = new ArrayList<>();
   public static ChestLock PUZZLE = new ChestLock(0) {
      @SideOnly(Side.CLIENT)
      @Override
      public void render(TileARPGChest te, float partialTicks, EnumChest chestType, TileARPGChest.EnumChestStanding standing, float lidRotation) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(ChestLock.lock);
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.02F, -0.02F, -0.02F);
         ChestLock.lockmodel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.065F);
         GlStateManager.popMatrix();
      }
   };
   public static ChestLock WINTER_CURSE = new ChestLock(1) {
      @SideOnly(Side.CLIENT)
      @Override
      public void render(TileARPGChest te, float partialTicks, EnumChest chestType, TileARPGChest.EnumChestStanding standing, float lidRotation) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(lock_winter);
         GlStateManager.pushMatrix();
         float scaling = 0.015F;
         GlStateManager.translate(scaling * -8.0F, scaling * -8.0F, scaling * -8.0F);
         ChestLock.cursedlockmodel = new BlockModel(false);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         float var10001 = AnimationTimer.tick;
         ChestLock.cursedlockmodel.shape1.rotateAngleY = var10001 / 77.0F;
         ChestLock.cursedlockmodel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F + scaling);
         AbstractMobModel.alphaGlowDisable();
         AbstractMobModel.returnlight();
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }
   };
   public static ChestLock RUSTED_KEY = new ChestLock(2) {
      @SideOnly(Side.CLIENT)
      @Override
      public void render(TileARPGChest te, float partialTicks, EnumChest chestType, TileARPGChest.EnumChestStanding standing, float lidRotation) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(ChestLock.lock);
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.02F, -0.02F, -0.02F);
         ChestLock.lockmodel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.065F);
         GlStateManager.popMatrix();
      }
   };
   public static ChestLock DOLERITE = new ChestLock(3) {
      @SideOnly(Side.CLIENT)
      @Override
      public void render(TileARPGChest te, float partialTicks, EnumChest chestType, TileARPGChest.EnumChestStanding standing, float lidRotation) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(lock_dolerite);
         GlStateManager.pushMatrix();
         float scaling = 0.015F;
         GlStateManager.translate(scaling * -8.0F, scaling * -8.0F, scaling * -8.0F);
         ChestLock.cursedlockmodel = new BlockModel(false);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         float var10001 = AnimationTimer.tick;
         ChestLock.cursedlockmodel.shape1.rotateAngleY = var10001 / 87.0F;
         ChestLock.cursedlockmodel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F + scaling);
         AbstractMobModel.alphaGlowDisable();
         AbstractMobModel.returnlight();
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }
   };
   public static ChestLock SIREN = new ChestLock(4) {
      @SideOnly(Side.CLIENT)
      @Override
      public void render(TileARPGChest te, float partialTicks, EnumChest chestType, TileARPGChest.EnumChestStanding standing, float lidRotation) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(lock_siren);
         GlStateManager.pushMatrix();
         float scaling = 0.015F;
         GlStateManager.translate(scaling * -8.0F, scaling * -8.0F, scaling * -8.0F);
         ChestLock.cursedlockmodel = new BlockModel(false);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         float var10001 = AnimationTimer.tick;
         ChestLock.cursedlockmodel.shape1.rotateAngleY = var10001 / 87.0F;
         ChestLock.cursedlockmodel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F + scaling);
         AbstractMobModel.alphaGlowDisable();
         AbstractMobModel.returnlight();
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }
   };
   public int id;

   @SideOnly(Side.CLIENT)
   public abstract void render(TileARPGChest var1, float var2, EnumChest var3, TileARPGChest.EnumChestStanding var4, float var5);

   public ChestLock(int id) {
      this.id = id;
   }

   static {
      locksRegister.add(PUZZLE);
      locksRegister.add(WINTER_CURSE);
      locksRegister.add(RUSTED_KEY);
      locksRegister.add(DOLERITE);
      locksRegister.add(SIREN);
   }

   public static class BlockModel extends ModelBase {
      public ModelRenderer shape1;

      public BlockModel(boolean verticalSides) {
         this.textureWidth = 16;
         this.textureHeight = 16;
         this.shape1 = (ModelRenderer)(verticalSides
            ? new ModelRenderer(this, 0, 0)
            : new ModelRendererLimited(this, 0, 0, true, true, false, false, true, true));
         this.shape1.setRotationPoint(8.0F, 8.0F, 8.0F);
         this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape1.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}

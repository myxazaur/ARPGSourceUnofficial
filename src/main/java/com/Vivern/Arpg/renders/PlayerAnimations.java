//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Weapons;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerAnimations {
   public static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
   public static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
   public final Minecraft mc;
   public ItemStack itemStackMainHand = ItemStack.EMPTY;
   public ItemStack itemStackOffHand = ItemStack.EMPTY;
   public float equippedProgressMainHand = 1.0F;
   public float prevEquippedProgressMainHand = 1.0F;
   public float equippedProgressOffHand = 1.0F;
   public float prevEquippedProgressOffHand = 1.0F;
   public final RenderManager renderManager;
   public final RenderItem itemRenderer;
   public static PlayerAnimations instance;
   public static PlayerAnimation DEFAULT = new PlayerAnimation((byte)0, (byte)0);

   public PlayerAnimations(Minecraft mcIn) {
      this.mc = mcIn;
      this.renderManager = mcIn.getRenderManager();
      this.itemRenderer = mcIn.getRenderItem();
   }

   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, TransformType transform) {
      this.renderItemSide(entityIn, heldStack, transform, false);
   }

   public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, TransformType transform, boolean leftHanded) {
      if (!heldStack.isEmpty()) {
         Item item = heldStack.getItem();
         Block block = Block.getBlockFromItem(item);
         GlStateManager.pushMatrix();
         boolean flag = this.itemRenderer.shouldRenderItemIn3D(heldStack) && block.getRenderLayer() == BlockRenderLayer.TRANSLUCENT;
         if (flag) {
            GlStateManager.depthMask(false);
         }

         this.itemRenderer.renderItem(heldStack, entitylivingbaseIn, transform, leftHanded);
         if (flag) {
            GlStateManager.depthMask(true);
         }

         GlStateManager.popMatrix();
      }
   }

   public void rotateArroundXAndY(float angle, float angleY) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   public void setLightmap() {
      AbstractClientPlayer abstractclientplayer = this.mc.player;
      int i = this.mc
         .world
         .getCombinedLight(
            new BlockPos(
               abstractclientplayer.posX, abstractclientplayer.posY + abstractclientplayer.getEyeHeight(), abstractclientplayer.posZ
            ),
            0
         );
      float f = i & 65535;
      float f1 = i >> 16;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
   }

   public void rotateArm(float p_187458_1_) {
      EntityPlayerSP entityplayersp = this.mc.player;
      float f = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * p_187458_1_;
      float f1 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * p_187458_1_;
      GlStateManager.rotate((entityplayersp.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate((entityplayersp.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   public float getMapAngleFromPitch(float pitch) {
      float f = 1.0F - pitch / 45.0F + 0.1F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      return -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
   }

   public void renderArms() {
      if (!this.mc.player.isInvisible()) {
         GlStateManager.disableCull();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         this.renderArm(EnumHandSide.RIGHT);
         this.renderArm(EnumHandSide.LEFT);
         GlStateManager.popMatrix();
         GlStateManager.enableCull();
      }
   }

   public void renderArm(EnumHandSide p_187455_1_) {
      this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
      Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.player);
      RenderPlayer renderplayer = (RenderPlayer)render;
      GlStateManager.pushMatrix();
      float f = p_187455_1_ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);
      if (p_187455_1_ == EnumHandSide.RIGHT) {
         renderplayer.renderRightArm(this.mc.player);
      } else {
         renderplayer.renderLeftArm(this.mc.player);
      }

      GlStateManager.popMatrix();
   }

   public void renderMapFirstPersonSide(float p_187465_1_, EnumHandSide hand, float p_187465_3_, ItemStack stack) {
      float f = hand == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
      if (!this.mc.player.isInvisible()) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
         this.renderArmFirstPerson(p_187465_1_, p_187465_3_, hand);
         GlStateManager.popMatrix();
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(f * 0.51F, -0.08F + p_187465_1_ * -1.2F, -0.75F);
      float f1 = MathHelper.sqrt(p_187465_3_);
      float f2 = MathHelper.sin(f1 * (float) Math.PI);
      float f3 = -0.5F * f2;
      float f4 = 0.4F * MathHelper.sin(f1 * (float) (Math.PI * 2));
      float f5 = -0.3F * MathHelper.sin(p_187465_3_ * (float) Math.PI);
      GlStateManager.translate(f * f3, f4 - 0.3F * f2, f5);
      GlStateManager.rotate(f2 * -45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(f * f2 * -30.0F, 0.0F, 1.0F, 0.0F);
      this.renderMapFirstPerson(stack);
      GlStateManager.popMatrix();
   }

   public void renderMapFirstPerson(float p_187463_1_, float p_187463_2_, float p_187463_3_) {
      float f = MathHelper.sqrt(p_187463_3_);
      float f1 = -0.2F * MathHelper.sin(p_187463_3_ * (float) Math.PI);
      float f2 = -0.4F * MathHelper.sin(f * (float) Math.PI);
      GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
      float f3 = this.getMapAngleFromPitch(p_187463_1_);
      GlStateManager.translate(0.0F, 0.04F + p_187463_2_ * -1.2F + f3 * -0.5F, -0.72F);
      GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
      this.renderArms();
      float f4 = MathHelper.sin(f * (float) Math.PI);
      GlStateManager.rotate(f4 * 20.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      this.renderMapFirstPerson(this.itemStackMainHand);
   }

   public void renderMapFirstPerson(ItemStack stack) {
      GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.scale(0.38F, 0.38F, 0.38F);
      GlStateManager.disableLighting();
      this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.translate(-0.5F, -0.5F, 0.0F);
      GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
      tessellator.draw();
      MapData mapdata = ((ItemMap)stack.getItem()).getMapData(stack, this.mc.world);
      if (mapdata != null) {
         this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
      }

      GlStateManager.enableLighting();
   }

   public void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_) {
      boolean flag = p_187456_3_ != EnumHandSide.LEFT;
      float f = flag ? 1.0F : -1.0F;
      float f1 = MathHelper.sqrt(p_187456_2_);
      float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
      float f3 = 0.4F * MathHelper.sin(f1 * (float) (Math.PI * 2));
      float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float) Math.PI);
      GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
      GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
      float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float) Math.PI);
      float f6 = MathHelper.sin(f1 * (float) Math.PI);
      GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
      AbstractClientPlayer abstractclientplayer = this.mc.player;
      this.mc.getTextureManager().bindTexture(abstractclientplayer.getLocationSkin());
      GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
      GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
      // FIX: fixed `RenderPlayer` getting
      RenderPlayer renderplayer = this.renderManager.getSkinMap().get(abstractclientplayer.getSkinType().equals("slim") ? "slim" : "default"); // getEntityRenderObject(abstractclientplayer);
      GlStateManager.disableCull();
      if (flag) {
         renderplayer.renderRightArm(abstractclientplayer);
      } else {
         renderplayer.renderLeftArm(abstractclientplayer);
      }

      GlStateManager.enableCull();
   }

   public void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack) {
      float f = this.mc.player.getItemInUseCount() - p_187454_1_ + 1.0F;
      float f1 = f / stack.getMaxItemUseDuration();
      if (f1 < 0.8F) {
         float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);
         GlStateManager.translate(0.0F, f2, 0.0F);
      }

      float f3 = 1.0F - (float)Math.pow(f1, 27.0);
      int i = hand == EnumHandSide.RIGHT ? 1 : -1;
      GlStateManager.translate(f3 * 0.6F * i, f3 * -0.5F, f3 * 0.0F);
      GlStateManager.rotate(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   public void transformFirstPerson(EnumHandSide hand, float p_187453_2_) {
      int i = hand == EnumHandSide.RIGHT ? 1 : -1;
      float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float) Math.PI);
      GlStateManager.rotate(i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
      float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float) Math.PI);
      GlStateManager.rotate(i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(i * -45.0F, 0.0F, 1.0F, 0.0F);
   }

   public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_) {
      int i = hand == EnumHandSide.RIGHT ? 1 : -1;
      GlStateManager.translate(i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
   }

   public void renderItemInFirstPerson(float partialTicks) {
      AbstractClientPlayer abstractclientplayer = this.mc.player;
      float f = abstractclientplayer.getSwingProgress(partialTicks);
      EnumHand enumhand = (EnumHand)MoreObjects.firstNonNull(abstractclientplayer.swingingHand, EnumHand.MAIN_HAND);
      float f1 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
      float f2 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
      boolean flag = true;
      boolean flag1 = true;
      if (abstractclientplayer.isHandActive()) {
         ItemStack itemstack = abstractclientplayer.getActiveItemStack();
         if (itemstack.getItem() instanceof ItemBow) {
            EnumHand enumhand1 = abstractclientplayer.getActiveHand();
            flag = enumhand1 == EnumHand.MAIN_HAND;
            flag1 = !flag;
         }
      }

      this.rotateArroundXAndY(f1, f2);
      this.setLightmap();
      this.rotateArm(partialTicks);
      GlStateManager.enableRescaleNormal();
      if (flag) {
         float f3 = enumhand == EnumHand.MAIN_HAND ? f : 0.0F;
         float f5 = 1.0F - (this.prevEquippedProgressMainHand + (this.equippedProgressMainHand - this.prevEquippedProgressMainHand) * partialTicks);
         if (!ForgeHooksClient.renderSpecificFirstPersonHand(EnumHand.MAIN_HAND, partialTicks, f1, f3, f5, this.itemStackMainHand)) {
            this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.MAIN_HAND, f3, this.itemStackMainHand, f5);
         }
      }

      if (flag1) {
         float f4 = enumhand == EnumHand.OFF_HAND ? f : 0.0F;
         float f6 = 1.0F - (this.prevEquippedProgressOffHand + (this.equippedProgressOffHand - this.prevEquippedProgressOffHand) * partialTicks);
         if (!ForgeHooksClient.renderSpecificFirstPersonHand(EnumHand.OFF_HAND, partialTicks, f1, f4, f6, this.itemStackOffHand)) {
            this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.OFF_HAND, f4, this.itemStackOffHand, f6);
         }
      }

      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
   }

   public void renderItemInFirstPerson(
      AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_
   ) {
      boolean flag = hand == EnumHand.MAIN_HAND;
      EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
      GlStateManager.pushMatrix();
      if (stack.isEmpty()) {
         if (flag && !player.isInvisible()) {
            this.renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
         }
      } else if (stack.getItem() instanceof ItemMap) {
         if (flag && this.itemStackOffHand.isEmpty()) {
            this.renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
         } else {
            this.renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, stack);
         }
      } else {
         boolean flag1 = enumhandside == EnumHandSide.RIGHT;
         if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == hand) {
            int j = flag1 ? 1 : -1;
            switch (stack.getItemUseAction()) {
               case NONE:
                  this.transformSideFirstPerson(enumhandside, p_187457_7_);
                  break;
               case EAT:
               case DRINK:
                  this.transformEatFirstPerson(p_187457_2_, enumhandside, stack);
                  this.transformSideFirstPerson(enumhandside, p_187457_7_);
                  break;
               case BLOCK:
                  this.transformSideFirstPerson(enumhandside, p_187457_7_);
                  break;
               case BOW:
                  this.transformSideFirstPerson(enumhandside, p_187457_7_);
                  GlStateManager.translate(j * -0.2785682F, 0.18344387F, 0.15731531F);
                  GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(j * 35.3F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.rotate(j * -9.785F, 0.0F, 0.0F, 1.0F);
                  float f5 = stack.getMaxItemUseDuration() - (this.mc.player.getItemInUseCount() - p_187457_2_ + 1.0F);
                  float f6 = f5 / 20.0F;
                  f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;
                  if (f6 > 1.0F) {
                     f6 = 1.0F;
                  }

                  if (f6 > 0.1F) {
                     float f7 = MathHelper.sin((f5 - 0.1F) * 1.3F);
                     float f3 = f6 - 0.1F;
                     float f4 = f7 * f3;
                     GlStateManager.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                  }

                  GlStateManager.translate(f6 * 0.0F, f6 * 0.0F, f6 * 0.04F);
                  GlStateManager.scale(1.0F, 1.0F, 1.0F + f6 * 0.2F);
                  GlStateManager.rotate(j * 45.0F, 0.0F, -1.0F, 0.0F);
            }
         } else {
            float f = -0.4F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * (float) Math.PI);
            float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * (float) (Math.PI * 2));
            float f2 = -0.2F * MathHelper.sin(p_187457_5_ * (float) Math.PI);
            int i = flag1 ? 1 : -1;
            GlStateManager.translate(i * f, f1, f2);
            this.transformSideFirstPerson(enumhandside, p_187457_7_);
            this.transformFirstPerson(enumhandside, p_187457_5_);
         }

         this.renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
      }

      GlStateManager.popMatrix();
   }

   public void renderNone(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
      boolean flag = hand == EnumHand.MAIN_HAND;
      EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
      GlStateManager.pushMatrix();
      if (stack.isEmpty()) {
         if (flag && !player.isInvisible()) {
            this.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
         }
      } else {
         boolean flag1 = enumhandside == EnumHandSide.RIGHT;
         this.transformSideFirstPerson(enumhandside, p_187457_7_);
         this.renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
      }

      GlStateManager.popMatrix();
   }

   public void renderOverlays(float partialTicks) {
      GlStateManager.disableAlpha();
      if (this.mc.player.isEntityInsideOpaqueBlock()) {
         IBlockState iblockstate = this.mc.world.getBlockState(new BlockPos(this.mc.player));
         BlockPos overlayPos = new BlockPos(this.mc.player);
         EntityPlayer entityplayer = this.mc.player;

         for (int i = 0; i < 8; i++) {
            double d0 = entityplayer.posX + ((i >> 0) % 2 - 0.5F) * entityplayer.width * 0.8F;
            double d1 = entityplayer.posY + ((i >> 1) % 2 - 0.5F) * 0.1F;
            double d2 = entityplayer.posZ + ((i >> 2) % 2 - 0.5F) * entityplayer.width * 0.8F;
            BlockPos blockpos = new BlockPos(d0, d1 + entityplayer.getEyeHeight(), d2);
            IBlockState iblockstate1 = this.mc.world.getBlockState(blockpos);
            if (iblockstate1.causesSuffocation()) {
               iblockstate = iblockstate1;
               overlayPos = blockpos;
            }
         }

         if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE
            && !ForgeEventFactory.renderBlockOverlay(this.mc.player, partialTicks, OverlayType.BLOCK, iblockstate, overlayPos)) {
            this.renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
         }
      }

      if (!this.mc.player.isSpectator()) {
         if (this.mc.player.isInsideOfMaterial(Material.WATER) && !ForgeEventFactory.renderWaterOverlay(this.mc.player, partialTicks)) {
            this.renderWaterOverlayTexture(partialTicks);
         }

         if (this.mc.player.isBurning() && !ForgeEventFactory.renderFireOverlay(this.mc.player, partialTicks)) {
            this.renderFireInFirstPerson();
         }
      }

      GlStateManager.enableAlpha();
   }

   public void renderBlockInHand(TextureAtlasSprite sprite) {
      this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      float f = 0.1F;
      GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
      GlStateManager.pushMatrix();
      float f1 = -1.0F;
      float f2 = 1.0F;
      float f3 = -1.0F;
      float f4 = 1.0F;
      float f5 = -0.5F;
      float f6 = sprite.getMinU();
      float f7 = sprite.getMaxU();
      float f8 = sprite.getMinV();
      float f9 = sprite.getMaxV();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, -0.5).tex(f7, f9).endVertex();
      bufferbuilder.pos(1.0, -1.0, -0.5).tex(f6, f9).endVertex();
      bufferbuilder.pos(1.0, 1.0, -0.5).tex(f6, f8).endVertex();
      bufferbuilder.pos(-1.0, 1.0, -0.5).tex(f7, f8).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void renderWaterOverlayTexture(float partialTicks) {
      this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      float f = this.mc.player.getBrightness();
      GlStateManager.color(f, f, f, 0.5F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.pushMatrix();
      float f1 = 4.0F;
      float f2 = -1.0F;
      float f3 = 1.0F;
      float f4 = -1.0F;
      float f5 = 1.0F;
      float f6 = -0.5F;
      float f7 = -this.mc.player.rotationYaw / 64.0F;
      float f8 = this.mc.player.rotationPitch / 64.0F;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, -0.5).tex(4.0F + f7, 4.0F + f8).endVertex();
      bufferbuilder.pos(1.0, -1.0, -0.5).tex(0.0F + f7, 4.0F + f8).endVertex();
      bufferbuilder.pos(1.0, 1.0, -0.5).tex(0.0F + f7, 0.0F + f8).endVertex();
      bufferbuilder.pos(-1.0, 1.0, -0.5).tex(4.0F + f7, 0.0F + f8).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
   }

   public void renderFireInFirstPerson() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      float f = 1.0F;

      for (int i = 0; i < 2; i++) {
         GlStateManager.pushMatrix();
         TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
         this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         float f1 = textureatlassprite.getMinU();
         float f2 = textureatlassprite.getMaxU();
         float f3 = textureatlassprite.getMinV();
         float f4 = textureatlassprite.getMaxV();
         float f5 = -0.5F;
         float f6 = 0.5F;
         float f7 = -0.5F;
         float f8 = 0.5F;
         float f9 = -0.5F;
         GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
         GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-0.5, -0.5, -0.5).tex(f2, f4).endVertex();
         bufferbuilder.pos(0.5, -0.5, -0.5).tex(f1, f4).endVertex();
         bufferbuilder.pos(0.5, 0.5, -0.5).tex(f1, f3).endVertex();
         bufferbuilder.pos(-0.5, 0.5, -0.5).tex(f2, f3).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }

   public void updateEquippedItem() {
      this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
      this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
      EntityPlayerSP entityplayersp = this.mc.player;
      ItemStack itemstack = entityplayersp.getHeldItemMainhand();
      ItemStack itemstack1 = entityplayersp.getHeldItemOffhand();
      if (entityplayersp.isRowingBoat()) {
         this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4F, 0.0F, 1.0F);
         this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4F, 0.0F, 1.0F);
      } else {
         float f = entityplayersp.getCooledAttackStrength(1.0F);
         boolean requipM = ForgeHooksClient.shouldCauseReequipAnimation(this.itemStackMainHand, itemstack, entityplayersp.inventory.currentItem);
         boolean requipO = ForgeHooksClient.shouldCauseReequipAnimation(this.itemStackOffHand, itemstack1, -1);
         if (!requipM && !Objects.equals(this.itemStackMainHand, itemstack)) {
            this.itemStackMainHand = itemstack;
         }

         if (!requipM && !Objects.equals(this.itemStackOffHand, itemstack1)) {
            this.itemStackOffHand = itemstack1;
         }

         this.equippedProgressMainHand = this.equippedProgressMainHand
            + MathHelper.clamp((!requipM ? f * f * f : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
         this.equippedProgressOffHand = this.equippedProgressOffHand + MathHelper.clamp((!requipO ? 1 : 0) - this.equippedProgressOffHand, -0.4F, 0.4F);
      }

      if (this.equippedProgressMainHand < 0.1F) {
         this.itemStackMainHand = itemstack;
      }

      if (this.equippedProgressOffHand < 0.1F) {
         this.itemStackOffHand = itemstack1;
      }
   }

   public void resetEquippedProgress(EnumHand hand) {
      if (hand == EnumHand.MAIN_HAND) {
         this.equippedProgressMainHand = 0.0F;
      } else {
         this.equippedProgressOffHand = 0.0F;
      }
   }

   public static class CASCHardSwingRepeat extends PlayerAnimation {
      public CASCHardSwingRepeat(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float contin = 0.4F;
            float ft1 = 1.0F;
            float ft2 = 1.0F;
            float ft3 = GetMOP.getfromto(progress, 0.35F * contin, 0.6F * contin);
            float ft4 = GetMOP.getfromto(progress, 0.6F * contin, contin);
            float ft5 = GetMOP.getfromto(progress, contin, 1.0F);
            float ft6 = GetMOP.getfromto(progress, contin, 1.2F * contin);
            float ft7 = GetMOP.getfromto(progress, 1.65F * contin, 1.0F);
            float decr = 1.0F - ft5;
            float translateX = 1.3F * ft1 - 1.5F * ft3 - 1.9F * ft4 + 2.1F * ft5;
            float translateY = 0.4F * ft2 - 0.1F * ft4 - 0.85F * ft6 + 0.55F * ft7;
            float translateZ = 0.3F * ft1 - 0.5F * ft3 + 0.6F * ft4 - 0.4F * ft5;
            float rotateX = 30.0F * ft1 - 55.0F * ft3 + 25.0F * ft5;
            float rotateY = 110.0F * ft4 - 110.0F * ft5;
            float rotateZ = -40.0F * ft1 - 10.0F * ft3 + 50.0F * ft5;
            GlStateManager.translate(
               Debugger.floats[0] - translateX - 0.9F * decr, Debugger.floats[1] + translateY - 0.9F * decr, Debugger.floats[2] + translateZ
            );
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] - rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] - rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
         float contin = 0.35F;
         float pause = contin + 0.375F;
         float ft1 = GetMOP.getfromto(progress, 0.0F, 0.35F * contin);
         float ft2 = GetMOP.getfromto(progress, 0.0F, 0.25F * contin);
         float ft3 = GetMOP.getfromto(progress, 0.35F * contin, 0.6F * contin);
         float ft4 = GetMOP.getfromto(progress, 0.6F * contin, contin);
         float ft4_5 = GetMOP.getfromto(progress, contin, 1.0F);
         float ft5 = GetMOP.getfromto(progress, pause, 1.0F);
         float ft6 = GetMOP.getfromto(progress, contin, pause);
         if (model != null) {
            if (hand != EnumHandSide.RIGHT) {
               ;
            }

            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.08F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft0_1 = 1.0F - ft0 * 0.7F;
            float ft0_2 = ft0 * 0.5F;
            handrenderer.rotateAngleX *= ft0_1;
            float decr = 1.0F - ft5;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                  (-50.0F + Debugger.floats[6] - 0.0F * ft2 - 5.0F * ft3 + 50.0F * ft4 + 30.0F * ft6) * (float) (Math.PI / 180.0) * decr
                     + model.bipedHead.rotateAngleX * ft0_2
               );
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + (
                  (20.0F + Debugger.floats[7] - 10.0F * ft1 + 10.0F * ft3 - 30.0F * ft4 + 20.0F * ft6) * (float) (Math.PI / 180.0) * decr
                     + model.bipedHead.rotateAngleY * ft0_2
               );
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ
               + (-80.0F + Debugger.floats[8] - 10.0F * ft1 + 35.0F * ft3 + 110.0F * ft4 - 20.0F * ft6) * (float) (Math.PI / 180.0) * decr;
            model.bipedBody.rotateAngleY -= (ft1 * 20.0F - ft3 * 20.0F) * (float) (Math.PI / 180.0) * handMult;
         } else {
            float rotateX = -130.0F - 10.0F * ft1 + 55.0F * ft3 + 35.0F * ft4 + 50.0F * ft6;
            float rotateY = 0.0F;
            float rotateZ = 50.0F + 10.0F * ft1 - 30.0F * ft3 - 30.0F * ft4;
            float translateX = 0.1F - 0.1F * ft4;
            float translateY = 0.2F - 0.2F * ft4;
            float translateZ = -0.2F + 0.2F * ft4;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class CASCStaffShootMiddle extends PlayerAnimation {
      public CASCStaffShootMiddle(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.75F, 1.0F);
            float ft2 = GetMOP.getfromto(progress, 0.05F, 0.15F) - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float translateX = -1.09F * ft1;
            float translateY = 0.0F;
            float translateZ = 0.0F;
            float rotateX = -60.0F * ft2;
            float rotateY = -10.0F * ft1;
            float rotateZ = 2.0F * ft1;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.75F, 1.0F);
         float ft2 = GetMOP.getfromto(progress, 0.05F, 0.15F) - GetMOP.getfromto(progress, 0.85F, 1.0F);
         if (model != null) {
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.15F) - GetMOP.getfromto(progress, 0.85F, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer2.rotateAngleX *= 1.0F - 0.85F * ft0;
            handrenderer.rotateAngleX *= 1.0F - 0.95F * ft0;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + ((Debugger.floats[6] - 68.0F * ft2) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * ft0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((Debugger.floats[7] - 11.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * ft0);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + Debugger.floats[8] * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX
               + ((Debugger.floats[6] - 45.0F * ft2) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * 0.9F * ft0);
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY
               + ((Debugger.floats[7] + 25.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * 0.9F * ft0);
            handrenderer2.rotateAngleZ = handrenderer2.rotateAngleZ + (Debugger.floats[8] + 10.0F * ft1) * (float) (Math.PI / 180.0);
         } else {
            float rotateX = -65.0F * ft2;
            float rotateY = 0.0F;
            float rotateZ = -5.0F * ft1;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] - 0.08F * ft1, Debugger.floats[1], Debugger.floats[2] - 0.15F * ft1);
         }
      }
   }

   public static class CascAimChainsaw extends PlayerAnimation {
      public CascAimChainsaw(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float handMult = enumhandside == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.4F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float translateX = -0.6F * ft1 * handMult;
            float translateY = -0.2F * ft1;
            float translateZ = 0.2F * ft1;
            float rotateX = 10.0F * ft1;
            float rotateY = -5.0F * ft1 * handMult;
            float rotateZ = -30.0F * ft1 * handMult;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float rotateX = -10.0F * ft1;
            float rotateY = 10.0F * ft1;
            float rotateZ = -10.0F * ft1;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] - 0.1F * ft1, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class CascAimDrill extends PlayerAnimation {
      public CascAimDrill(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public boolean transformItemThirdperson() {
         return false;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float handMult = enumhandside == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float translateX = -0.1F * ft1 * handMult;
            float translateY = -0.05F * ft1;
            float translateZ = -0.1F * ft1;
            float rotateX = 3.0F * ft1;
            float rotateY = 3.0F * ft1 * handMult;
            float rotateZ = 5.0F * ft1 * handMult;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }
         }
      }
   }

   public static class PAAimChainsaw extends PlayerAnimation {
      public byte cascadeAnimStart;

      public PAAimChainsaw(byte id, int animationLength, byte cascadeAnimStart) {
         super(id, (byte)animationLength);
         this.cascadeAnimStart = cascadeAnimStart;
      }

      @Override
      public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
         return timePlaying <= 0 || animationPlaying != this.ID && animationPlaying != this.cascadeAnimStart ? this.cascadeAnimStart : this.ID;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float handMult = enumhandside == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float translateX = -0.6F * ft1 * handMult;
            float translateY = -0.2F * ft1;
            float translateZ = 0.2F * ft1;
            float rotateX = 10.0F * ft1;
            float rotateY = -5.0F * ft1 * handMult;
            float rotateZ = -30.0F * ft1 * handMult;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }
         } else {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float rotateX = -10.0F * ft1;
            float rotateY = 10.0F * ft1;
            float rotateZ = -10.0F * ft1;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] - 0.1F * ft1, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAAimDrill extends PlayerAnimation {
      public byte cascadeAnimStart;

      public PAAimDrill(byte id, int animationLength, byte cascadeAnimStart) {
         super(id, (byte)animationLength);
         this.cascadeAnimStart = cascadeAnimStart;
      }

      @Override
      public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
         return timePlaying <= 0 || animationPlaying != this.ID && animationPlaying != this.cascadeAnimStart ? this.cascadeAnimStart : this.ID;
      }

      @Override
      public boolean transformItemThirdperson() {
         return false;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float handMult = enumhandside == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float translateX = -0.1F * ft1 * handMult;
            float translateY = -0.05F * ft1;
            float translateZ = -0.1F * ft1;
            float rotateX = 3.0F * ft1;
            float rotateY = 3.0F * ft1 * handMult;
            float rotateZ = 5.0F * ft1 * handMult;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }
         }
      }
   }

   public static class PAChainMaceSpin extends PlayerAnimation {
      public PAChainMaceSpin(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            if (progress < 1.0F) {
               float mult = NBTHelper.GetNBTboolean(stack, "spindirection") ? 1.0F : -1.0F;
               float ft1 = GetMOP.getfromto(-player.rotationPitch, 20.0F, 90.0F);
               float ft2 = GetMOP.getfromto(player.rotationPitch, 0.0F, 90.0F);
               float ft3 = Math.min(1.25F - GetMOP.getfromto(-player.rotationPitch, 20.0F, 50.0F), 1.0F);
               float translateX = 0.75F - 0.9F * ft1;
               float translateY = 1.2F + 0.3F * ft2 + 0.2F * ft1;
               float translateZ = 0.3F * ft1;
               float rotateX = MathHelper.sin(mult * progress * 6.5F) * 6.0F * ft3 - 40.0F * ft1;
               float rotateY = 0.0F;
               float rotateZ = 20.0F + MathHelper.cos(mult * progress * 6.5F) * 6.0F * ft3 - 20.0F * ft1;
               GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(rotateZ, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(translateX, translateY, translateZ);
            }

            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         boolean spinDirection = NBTHelper.GetNBTboolean(player.getHeldItemMainhand(), "spindirection");
         if (progress < 1.0F) {
            if (model != null) {
               float handmult = hand == EnumHandSide.RIGHT ? 1.0F : -1.0F;
               float mult = spinDirection ? -1.0F : 1.0F;
               handrenderer.rotateAngleX *= 0.1F;
               handrenderer.rotateAngleX = handrenderer.rotateAngleX
                  + (-120.0F + MathHelper.sin(mult * progress * 6.5F) * 4.0F) * (float) (Math.PI / 180.0);
               handrenderer.rotateAngleZ = handrenderer.rotateAngleZ
                  + (-6.0F * handmult + MathHelper.cos(mult * progress * 6.5F) * 4.0F) * (float) (Math.PI / 180.0);
            } else {
               float rotateX = 0.0F;
               float rotateY = 0.0F;
               float rotateZ = 0.0F;
               float translateZ = 0.0F;
               GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(rotateZ, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(0.0F, 0.35F, translateZ);
            }
         }
      }
   }

   public static class PAChainMaceThrow extends PlayerAnimation {
      public PAChainMaceThrow(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            if (progress < 1.0F) {
               float mult = flag1 ? 1.0F : -1.0F;
               float ft1 = GetMOP.getfromto(-player.rotationPitch, 0.0F, 90.0F);
               float ft2 = GetMOP.getfromto(player.rotationPitch, 0.0F, 90.0F);
               float translateX = -0.45F * ft2 * mult;
               float translateY = 0.9F * ft2 - 0.4F * ft1;
               float translateZ = -0.6F + 0.3F * ft2;
               GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            }

            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            if (progress < 1.0F) {
               GlStateManager.rotate(Debugger.floats[3] - 45.0F, 1.0F, 0.0F, 0.0F);
            }

            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (progress < 1.0F) {
            if (model != null) {
               float ft1 = GetMOP.getfromto(-model.bipedHead.rotateAngleX, 0.0F, 1.57F);
               float ft2 = GetMOP.getfromto(model.bipedHead.rotateAngleX, 0.0F, 1.57F);
               float ft3 = GetMOP.getfromto(model.bipedHead.rotateAngleY, 0.0F, 0.785398F);
               float ft4 = GetMOP.getfromto(-model.bipedHead.rotateAngleY, 0.0F, 0.785398F);
               handrenderer.rotateAngleX = (float)(handrenderer.rotateAngleX * 0.1);
               handrenderer.rotateAngleX = handrenderer.rotateAngleX + ((float) (-Math.PI / 4) + model.bipedHead.rotateAngleX);
               handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY;
            } else {
               float ft1 = GetMOP.getfromto(-player.rotationPitch, 0.0F, 90.0F);
               float ft2 = GetMOP.getfromto(player.rotationPitch, 0.0F, 90.0F);
               float rotateX = 0.0F;
               float rotateY = 0.0F;
               float rotateZ = 0.0F;
               float translateX = 0.0F;
               float translateY = 0.1F;
               float translateZ = 0.0F;
               GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(rotateZ, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(translateX, translateY, translateZ);
            }
         }
      }
   }

   public static class PAClassicSwing extends PlayerAnimation {
      public PAClassicSwing(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public boolean transformItemThirdperson() {
         return false;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float f = -0.4F * MathHelper.sin(MathHelper.sqrt(progress) * (float) Math.PI);
            float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(progress) * (float) (Math.PI * 2));
            float f2 = -0.2F * MathHelper.sin(progress * (float) Math.PI);
            int i = flag1 ? 1 : -1;
            GlStateManager.translate(i * f, f1, f2);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            PlayerAnimations.instance.transformFirstPerson(enumhandside, progress);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress, ModelBiped biped, ModelRenderer handrenderer, EnumHandSide enumhandside, ItemStack stack, EntityPlayer player, float partialTicks
      ) {
         if (biped != null) {
            ModelRenderer modelrenderer = getArmForSide(enumhandside, biped);
            biped.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(progress) * (float) (Math.PI * 2)) * 0.2F;
            if (enumhandside == EnumHandSide.LEFT) {
               biped.bipedBody.rotateAngleY *= -1.0F;
            }

            biped.bipedRightArm.rotationPointZ = MathHelper.sin(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedRightArm.rotationPointX = -MathHelper.cos(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedLeftArm.rotationPointZ = -MathHelper.sin(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedLeftArm.rotationPointX = MathHelper.cos(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedRightArm.rotateAngleY = biped.bipedRightArm.rotateAngleY + biped.bipedBody.rotateAngleY;
            biped.bipedLeftArm.rotateAngleY = biped.bipedLeftArm.rotateAngleY + biped.bipedBody.rotateAngleY;
            biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX + biped.bipedBody.rotateAngleY;
            float f1 = 1.0F - progress;
            f1 *= f1;
            f1 *= f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(progress * (float) Math.PI) * -(biped.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float)(modelrenderer.rotateAngleX - (f2 * 1.2 + f3));
            modelrenderer.rotateAngleY = modelrenderer.rotateAngleY + biped.bipedBody.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ = modelrenderer.rotateAngleZ + MathHelper.sin(progress * (float) Math.PI) * -0.4F;
         }
      }
   }

   public static class PADaggerAttack extends PlayerAnimation {
      public PADaggerAttack(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            int i = flag1 ? 1 : -1;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.45F, 1.0F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.3F) - GetMOP.getfromto(progress, 0.3F, 0.8F);
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.22F) - GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.1F, 0.25F) - GetMOP.getfromto(progress, 0.5F, 1.0F);
            float translateX = -0.25F * ft3;
            float translateY = 0.05F * ft4;
            float translateZ = -0.3F * ft2;
            float rotateX = -90.0F * ft1;
            float rotateY = 0.0F;
            float rotateZ = 5.0F * ft3;
            GlStateManager.translate(Debugger.floats[0] + translateX * i, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.17F) - GetMOP.getfromto(progress, 0.45F, 1.0F);
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.22F) - GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft6 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.6F, 1.0F);
            float ft7 = Math.min(1.4F - ft6, 1.0F);
            handrenderer.rotateAngleX *= ft7;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               - (50.0F * ft1 * (float) (Math.PI / 180.0) - model.bipedHead.rotateAngleX * 0.5F * ft6);
            handrenderer.rotateAngleY += -15.0F * ft3 * handMult * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY * 0.8F * ft6;
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.17F) - GetMOP.getfromto(progress, 0.45F, 1.0F);
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.22F) - GetMOP.getfromto(progress, 0.3F, 0.7F);
            float rotateX = -40.0F * ft1;
            float rotateY = 0.0F;
            float rotateZ = 5.0F * ft3;
            float translateZ = -0.1F * ft3;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAFlatBlow extends PlayerAnimation {
      public PAFlatBlow(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.3F, 1.0F);
            float translateX = 0.2F * ft1 - ft3 * 0.2F;
            float translateY = 0.4F * ft1 - ft3 * 0.4F;
            float translateZ = -1.0F * ft2 + ft4 * 1.0F;
            float rotateX = 30.0F * ft1 - 90.0F * ft2 + 60.0F * ft4 + 10.0F * ft5;
            float rotateY = 70.0F * ft1 + 10.0F * ft2 - 80.0F * ft3;
            float rotateZ = -10.0F * ft1 + 10.0F * ft3;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.3F, 1.0F);
            float ft6 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.4F, 0.7F);
            float ft7 = Math.min(1.2F - ft6, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer.rotateAngleX *= ft7;
            handrenderer2.rotateAngleX *= ft7;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               - (
                  (Debugger.floats[6] + 80.0F * ft1 - 60.0F * ft2 - 20.0F * ft4 + 5.0F * ft5) * (float) (Math.PI / 180.0)
                     - model.bipedHead.rotateAngleX * 0.6F * ft6
               );
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + (Debugger.floats[7] - 15.0F * ft1 + 15.0F * ft3) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX + handrenderer.rotateAngleX * 0.8F;
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY - handrenderer.rotateAngleY;
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY * 0.9F * ft6;
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY + model.bipedHead.rotateAngleY * 0.9F * ft6;
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.3F, 1.0F);
            float rotateX = -40.0F * ft2 + 10.0F * ft5 + 40.0F * ft4;
            float rotateY = 85.0F * ft1 - 85.0F * ft3;
            float rotateZ = 0.0F;
            float translateZ = -0.1F * ft1 + 0.1F * ft3;
            float translateY = 0.7F * ft1 - 0.7F * ft3;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAForging extends PlayerAnimation {
      public float modifierRY;
      public float modifierTX;
      public float modifierRZ;
      public float modifierTZ;

      public PAForging(byte id, int animationLength, float modifierRY, float modifierTX, float modifierRZ, float modifierTZ) {
         super(id, (byte)animationLength);
         this.modifierRY = modifierRY;
         this.modifierTX = modifierTX;
         this.modifierRZ = modifierRZ;
         this.modifierTZ = modifierTZ;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.12F);
            float ft2 = GetMOP.getfromto(progress, 0.08F, 0.3F);
            float ft3 = GetMOP.getfromto(progress, 0.33F, 0.39F);
            float ft4 = GetMOP.getfromto(progress, 0.43F, 0.7F);
            float ft5 = GetMOP.getfromto(progress, 0.58F, 1.0F);
            float translateX = 0.1F * ft1 + 0.4F * ft2 - 1.1F * ft3 + 0.1F * ft4 + 0.5F * ft5;
            float translateY = 0.1F * ft1 + 0.6F * ft2 - 0.2F * ft3 - 0.5F * ft5;
            float translateZ = this.modifierTZ * ft1 - 0.2F * ft3 + 0.2F * ft5 - this.modifierTZ * ft5;
            float rotateX = 10.0F * ft1 + 30.0F * ft2 - 78.0F * ft3 + 10.0F * ft4 + 28.0F * ft5;
            float rotateY = (-10.0F * ft2 + 5.0F * ft4 + 5.0F * ft5) * this.modifierRY;
            float rotateZ = (20.0F * ft1 - 20.0F * ft2) * this.modifierRZ - 20.0F * ft2 + 35.0F * ft3 - 15.0F * ft5;
            translateX += this.modifierTX * ft3 - this.modifierTX * ft5;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float modRY = this.modifierRY * 0.6F + 0.4F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.12F);
            float ft2 = GetMOP.getfromto(progress, 0.08F, 0.3F);
            float ft3 = GetMOP.getfromto(progress, 0.33F, 0.39F);
            float ft4 = GetMOP.getfromto(progress, 0.43F, 0.7F);
            float ft5 = GetMOP.getfromto(progress, 0.58F, 1.0F);
            float ft6 = ft1 - ft5;
            float translateX = 0.1F * ft1 + 0.4F * ft2 - 1.1F * ft3 + 0.1F * ft4 + 0.5F * ft5;
            float translateY = 0.1F * ft1 + 0.6F * ft2 - 0.2F * ft3 - 0.5F * ft5;
            float translateZ = this.modifierTZ * ft1 - 0.2F * ft3 + 0.2F * ft5 - this.modifierTZ * ft5;
            float rotateX = 30.0F * ft1 + 90.0F * ft2 - 88.0F * ft3 + 10.0F * ft4 - 42.0F * ft5;
            float rotateY = (-10.0F * ft2 + 5.0F * ft4 + 5.0F * ft5) * modRY + 15.0F * ft3 - 15.0F * ft4;
            float rotateZ = (20.0F * ft1 - 20.0F * ft2) * this.modifierRZ - 20.0F * ft2 + 35.0F * ft3 - 15.0F * ft5;
            translateX += this.modifierTX * ft3 - this.modifierTX * ft5;
            if (hand != EnumHandSide.RIGHT) {
               ;
            }

            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + ((Debugger.floats[6] - rotateX) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * ft6);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((Debugger.floats[7] - rotateY) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * ft6);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + (Debugger.floats[8] - rotateZ) * (float) (Math.PI / 180.0);
            model.bipedBody.rotateAngleY += (ft1 * 20.0F - ft2 * 20.0F - ft3 * 20.0F + ft4 * 20.0F) * (float) (Math.PI / 180.0) * handMult;
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.12F);
            float ft2 = GetMOP.getfromto(progress, 0.08F, 0.3F);
            float ft3 = GetMOP.getfromto(progress, 0.33F, 0.39F);
            float ft4 = GetMOP.getfromto(progress, 0.43F, 0.7F);
            float ft5 = GetMOP.getfromto(progress, 0.58F, 1.0F);
            float rotateX = 10.0F * ft1 + 30.0F * ft2 - 78.0F * ft3 + 10.0F * ft4 + 28.0F * ft5;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX / 2.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2]);
         }
      }
   }

   public static class PAGrenadeslaunReloading extends PlayerAnimation {
      public PAGrenadeslaunReloading(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float translateX = 0.0F;
            float translateY = -0.12F * GetMOP.getfromto(progress, 0.5F, 0.55F) + 0.12F * GetMOP.getfromto(progress, 0.55F, 0.6F);
            float translateZ = -0.15F * GetMOP.getfromto(progress, 0.0F, 0.2F) + 0.15F * GetMOP.getfromto(progress, 0.8F, 1.0F);
            float rotateX = 10.0F * GetMOP.getfromto(progress, 0.0F, 0.085F)
               - 33.0F * GetMOP.getfromto(progress, 0.085F, 0.185F)
               - 13.0F * GetMOP.getfromto(progress, 0.65F, 0.75F)
               + 46.0F * GetMOP.getfromto(progress, 0.75F, 0.9F)
               - 10.0F * GetMOP.getfromto(progress, 0.9F, 1.0F);
            float rotateY = 6.0F * (GetMOP.getfromto(progress, 0.125F, 0.6F) - GetMOP.getfromto(progress, 0.64F, 0.8F));
            float rotateZ = 0.0F;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            model.bipedLeftArm.rotateAngleX *= 1.0F - 0.7F * ft0;
            model.bipedRightArm.rotateAngleX *= 1.0F - 0.7F * ft0;
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            float ft1 = 10.0F * GetMOP.getfromto(progress, 0.5F, 0.55F) - 10.0F * GetMOP.getfromto(progress, 0.55F, 0.6F);
            float ft2 = GetMOP.getfromto(progress, 0.1F, 0.4F) - GetMOP.getfromto(progress, 0.6F, 1.0F);
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                  (
                           -46.0F * GetMOP.getfromto(progress, 0.0F, 0.085F)
                              + ft1
                              + 14.0F * GetMOP.getfromto(progress, 0.085F, 0.185F)
                              + 10.0F * GetMOP.getfromto(progress, 0.65F, 0.75F)
                              - 26.0F * GetMOP.getfromto(progress, 0.75F, 0.9F)
                              + 6.0F * GetMOP.getfromto(progress, 0.9F, 1.0F)
                        )
                        * (float) (Math.PI / 180.0)
                     + model.bipedHead.rotateAngleX * 0.4F
               );
            handrenderer.rotateAngleY += -10.0F * ft2 * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ += -20.0F * ft2 * (float) (Math.PI / 180.0);
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.1F, 0.2F);
            float ft4 = GetMOP.getfromto(progress, 0.2F, 0.38F);
            float ft5 = GetMOP.getfromto(progress, 0.38F, 0.5F);
            float ft6 = GetMOP.getfromto(progress, 0.55F, 1.0F);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX
               + (Debugger.floats[6] + 10.0F * ft3 - 80.0F * ft4 + 40.0F * ft5 + 40.0F * ft6) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY + (Debugger.floats[7] + 25.0F * ft5 - 25.0F * ft6) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleZ = handrenderer2.rotateAngleZ
               + (Debugger.floats[8] + 15.0F * ft3 + 25.0F * ft4 + 10.0F * ft5 - 35.0F * ft6) * (float) (Math.PI / 180.0);
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.34F, 0.44F) - GetMOP.getfromto(progress, 0.55F, 0.68F);
            float rotateX = 10.0F * GetMOP.getfromto(progress, 0.0F, 0.085F)
               - 33.0F * GetMOP.getfromto(progress, 0.085F, 0.185F)
               - 13.0F * GetMOP.getfromto(progress, 0.65F, 0.75F)
               + 46.0F * GetMOP.getfromto(progress, 0.75F, 0.9F)
               - 10.0F * GetMOP.getfromto(progress, 0.9F, 1.0F);
            float rotateY = -10.0F * ft1;
            float rotateZ = 0.0F;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX * 0.6F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAGunAimShootLinear extends PlayerAnimation {
      public PAGunAimShootLinear(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float translateX = 0.0F;
            float translateY = 0.0F;
            float translateZ = (GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.1F, 0.25F)) * 0.2F;
            float rotateX = 0.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.1F, 0.2F);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedRightArm.rotationPointZ = ft3;
               model.bipedLeftArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * 10.0F * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotationPointZ = ft3;
               model.bipedRightArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * -10.0F * (float) (Math.PI / 180.0);
            }
         } else {
            float rotateX = 0.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateZ = (GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.1F, 0.25F)) * 0.15F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAGunAimShootLowPunch extends PlayerAnimation {
      public PAGunAimShootLowPunch(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float translateX = 0.0F;
            float translateY = 0.0F;
            float translateZ = (GetMOP.getfromto(progress, 0.0F, 0.05F) - GetMOP.getfromto(progress, 0.05F, 0.15F)) * 0.1F;
            float rotateX = (GetMOP.getfromto(progress, 0.0F, 0.06F) - GetMOP.getfromto(progress, 0.06F, 0.16F)) * 10.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.07F) - GetMOP.getfromto(progress, 0.07F, 0.17F);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedRightArm.rotationPointZ = ft3;
               model.bipedLeftArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * 10.0F * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotationPointZ = ft3;
               model.bipedRightArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * -10.0F * (float) (Math.PI / 180.0);
            }
         } else {
            float rotateX = (GetMOP.getfromto(progress, 0.0F, 0.06F) - GetMOP.getfromto(progress, 0.06F, 0.16F)) * 10.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAGunAimShootNoPunch extends PlayerAnimation {
      public PAGunAimShootNoPunch(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public boolean transformItemThirdperson() {
         return false;
      }

      @Override
      public boolean transformItemFirstperson() {
         return true;
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }
         }
      }
   }

   public static class PAGunAimShootNormal extends PlayerAnimation {
      public PAGunAimShootNormal(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float rotateX = GetMOP.getfromto(progress, 0.05F, 0.15F) * 8.0F
               - GetMOP.getfromto(progress, 0.15F, 0.3F) * 11.0F
               + GetMOP.getfromto(progress, 0.3F, 0.5F) * 1.5F
               + GetMOP.getfromto(progress, 0.5F, 1.0F) * 1.5F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.5F);
            float translateX = 0.1F * ft1 * (flag1 ? 1 : -1);
            float translateY = -0.1F * ft1;
            float translateZ = 0.2F * ft1;
            float rotateY = -3.0F * ft1;
            GlStateManager.translate(translateX, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.5F);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedRightArm.rotationPointZ = ft3;
               model.bipedLeftArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * 10.0F * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               model.bipedLeftArm.rotationPointZ = ft3;
               model.bipedRightArm.rotationPointZ = -ft3;
               model.bipedBody.rotateAngleY += ft3 * -10.0F * (float) (Math.PI / 180.0);
            }
         } else {
            float rotateX = GetMOP.getfromto(progress, 0.05F, 0.15F) * 8.0F
               - GetMOP.getfromto(progress, 0.15F, 0.3F) * 11.0F
               + GetMOP.getfromto(progress, 0.3F, 0.5F) * 1.5F
               + GetMOP.getfromto(progress, 0.5F, 1.0F) * 1.5F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.5F);
            float translateZ = 0.2F * ft1;
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, translateZ);
         }
      }
   }

   public static class PAHammerAxeAttack extends PlayerAnimation {
      public PAHammerAxeAttack(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.4F, 1.0F);
            float ft6 = GetMOP.getfromto(progress, 0.2F, 0.4F);
            float translateX = 0.2F * ft1 - ft3 * 0.2F - 0.5F * ft2 + 0.5F * ft3 - 0.35F * ft1 + 0.35F * ft4;
            float translateY = 0.4F * ft1 - ft3 * 0.4F;
            float translateZ = -1.3F * ft2 + ft6 * 0.5F + ft4 * 0.8F;
            float rotateX = 40.0F * ft1 - 110.0F * ft2 + 70.0F * ft4 + 10.0F * ft5;
            float rotateY = 0.0F;
            float rotateZ = -10.0F * ft1 + 10.0F * ft3 + 10.0F * ft2 - 10.0F * ft3;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.4F, 1.0F);
            float ft6 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.4F, 0.7F);
            float ft7 = Math.min(1.2F - ft6, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer.rotateAngleX *= ft7;
            handrenderer2.rotateAngleX *= ft7;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               - (
                  (Debugger.floats[6] + 110.0F * ft1 - 80.0F * ft2 - 20.0F * ft4 + 5.0F * ft5) * (float) (Math.PI / 180.0)
                     - model.bipedHead.rotateAngleX * 0.6F * ft6
               );
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + (Debugger.floats[7] - 12.0F * ft1 + 12.0F * ft3) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX + handrenderer.rotateAngleX * 0.8F;
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY - handrenderer.rotateAngleY;
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY * 0.9F * ft6;
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY + model.bipedHead.rotateAngleY * 0.9F * ft6;
            model.bipedBody.rotateAngleY += (ft1 * -10.0F + ft2 * 10.0F) * (float) (Math.PI / 180.0) * handMult;
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.2F);
            float ft5 = GetMOP.getfromto(progress, 0.2F, 0.24F) - GetMOP.getfromto(progress, 0.24F, 0.28F);
            float ft3 = GetMOP.getfromto(progress, 0.3F, 0.7F);
            float ft4 = GetMOP.getfromto(progress, 0.4F, 1.0F);
            float rotateX = 10.0F * ft1 + -60.0F * ft2 + 10.0F * ft5 + 50.0F * ft4;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateZ = -0.1F * ft1 + 0.1F * ft3;
            float translateY = 0.5F * ft1 - 0.5F * ft3;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAHardSwing extends PlayerAnimation {
      public byte repeatCascadeId;

      public PAHardSwing(byte id, int animationLength, byte repeatCascadeId) {
         super(id, (byte)animationLength);
         this.repeatCascadeId = repeatCascadeId;
      }

      @Override
      public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
         return timePlaying > 0 && animationPlaying == this.ID ? this.repeatCascadeId : this.ID;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float contin = 0.25F;
            float pause = contin + 0.375F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.35F * contin);
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.25F * contin);
            float ft3 = GetMOP.getfromto(progress, 0.35F * contin, 0.6F * contin);
            float ft4 = GetMOP.getfromto(progress, 0.6F * contin, contin);
            float ft5 = GetMOP.getfromto(progress, pause, 1.0F);
            float ft6 = GetMOP.getfromto(progress, pause, 1.15F * pause);
            float ft7 = GetMOP.getfromto(progress, 1.4F * pause, 1.0F);
            float translateX = 1.3F * ft1 - 1.5F * ft3 - 1.9F * ft4 + 2.1F * ft5;
            float translateY = 0.4F * ft2 - 0.1F * ft4 - 0.85F * ft6 + 0.55F * ft7;
            float translateZ = 0.3F * ft1 - 0.5F * ft3 + 0.6F * ft4 - 0.4F * ft5;
            float rotateX = 30.0F * ft1 - 55.0F * ft3 + 25.0F * ft5;
            float rotateY = 110.0F * ft4 - 110.0F * ft5;
            float rotateZ = -40.0F * ft1 - 10.0F * ft3 + 50.0F * ft5;
            GlStateManager.translate(translateX, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
         float contin = 0.25F;
         float pause = contin + 0.375F;
         float ft1 = GetMOP.getfromto(progress, 0.0F, 0.35F * contin);
         float ft2 = GetMOP.getfromto(progress, 0.0F, 0.25F * contin);
         float ft3 = GetMOP.getfromto(progress, 0.35F * contin, 0.6F * contin);
         float ft4 = GetMOP.getfromto(progress, 0.6F * contin, contin);
         float ft4_5 = GetMOP.getfromto(progress, contin, 1.0F);
         float ft5 = GetMOP.getfromto(progress, pause, 1.0F);
         if (model != null) {
            if (hand != EnumHandSide.RIGHT) {
               ;
            }

            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.08F) - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft0_1 = 1.0F - ft0 * 0.7F;
            float ft0_2 = ft0 * 0.5F;
            handrenderer.rotateAngleX *= ft0_1;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + ((-80.0F * ft2 + 20.0F * ft3 + 10.0F * ft4 + 50.0F * ft4_5) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * ft0_2);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((50.0F * ft1 - 10.0F * ft3 - 30.0F * ft4 - 20.0F * ft5) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * ft0_2);
            handrenderer.rotateAngleZ += (-30.0F * ft1 - 20.0F * ft3 - 30.0F * ft4 + 80.0F * ft4_5) * (float) (Math.PI / 180.0);
            model.bipedBody.rotateAngleY += (ft1 * 20.0F - ft3 * 20.0F) * (float) (Math.PI / 180.0) * handMult;
         } else {
            float rotateX = 10.0F * ft1 - 140.0F * ft4 + 130.0F * ft5;
            float rotateY = 0.0F;
            float rotateZ = -10.0F * ft1 + 20.0F * ft3 + 40.0F * ft4 - 50.0F * ft5;
            float translateX = 0.1F * ft4 - 0.1F * ft5;
            float translateY = 0.2F * ft4 - 0.2F * ft5;
            float translateZ = -0.2F * ft4 + 0.2F * ft5;
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(translateX, translateY, translateZ);
         }
      }
   }

   public static class PAJackhammer extends PlayerAnimation {
      public PAJackhammer(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.6875F);
            float ft1 = GetMOP.getfromto(ft0, 0.15F, 0.2F);
            float ft2 = GetMOP.getfromto(ft0, 0.0F, 0.12F) - GetMOP.getfromto(ft0, 0.12F, 1.0F);
            float ft3 = GetMOP.getfromto(ft0, 0.2F, 0.26F) - GetMOP.getfromto(ft0, 0.26F, 0.32F);
            float ft4 = GetMOP.getfromto(ft0, 0.36F, 0.7F);
            float ft5 = GetMOP.softfromto(ft0, 0.5F, 1.0F);
            float translateX = -0.1F * ft1 + 0.04F * ft3 + 0.1F * ft5;
            float translateY = 0.05F * ft1 + 0.05F * ft2 - 0.15F * ft4 + 0.1F * ft5;
            float translateZ = -0.1F * ft1 + 0.1F * ft2 + 0.04F * ft3 + 0.1F * ft4;
            float rotateX = 3.0F * ft1 - 8.0F * ft4 + 5.0F * ft5;
            float rotateY = 2.0F * ft1 + 3.0F * ft4 - 5.0F * ft5;
            float rotateZ = 8.0F * ft1 - 8.0F * ft4;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft6 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft7 = 1.0F - ft6;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.6875F);
            float ft1 = GetMOP.getfromto(ft0, 0.15F, 0.2F);
            float ft4 = GetMOP.getfromto(ft0, 0.36F, 0.7F);
            float ft5 = GetMOP.softfromto(ft0, 0.5F, 1.0F);
            float rotateX = 2.0F * ft1 - 17.0F * ft4 + 15.0F * ft5;
            float rotateY = 2.0F * ft1 + 3.0F * ft4 - 5.0F * ft5;
            float rotateZ = 5.0F * ft1 - 5.0F * ft4;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft6 + model.bipedRightArm.rotateAngleY * ft7;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft6 + model.bipedLeftArm.rotateAngleY * ft7;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft6
                  + model.bipedRightArm.rotateAngleX * ft7;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft6
                  + model.bipedLeftArm.rotateAngleX * ft7;
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft6 + model.bipedRightArm.rotateAngleY * ft7;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft6 + model.bipedLeftArm.rotateAngleY * ft7;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft6
                  + model.bipedRightArm.rotateAngleX * ft7;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft6
                  + model.bipedLeftArm.rotateAngleX * ft7;
            }

            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX + (Debugger.floats[6] - rotateX) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + (Debugger.floats[7] - rotateZ) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + (Debugger.floats[8] - rotateY) * (float) (Math.PI / 180.0);
            handrenderer.rotationPointZ = (ft1 - ft4) * -2.0F;
            handrenderer2.rotationPointZ = -handrenderer.rotationPointZ;
            model.bipedBody.rotateAngleY += (ft1 - ft4) * -10.0F * (float) (Math.PI / 180.0);
         } else {
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.6875F);
            float ft1 = GetMOP.getfromto(ft0, 0.15F, 0.2F);
            float ft2 = GetMOP.getfromto(ft0, 0.0F, 0.12F) - GetMOP.getfromto(ft0, 0.12F, 1.0F);
            float ft3 = GetMOP.getfromto(ft0, 0.2F, 0.26F) - GetMOP.getfromto(ft0, 0.26F, 0.32F);
            float ft4 = GetMOP.getfromto(ft0, 0.36F, 0.7F);
            float ft5 = GetMOP.softfromto(ft0, 0.5F, 1.0F);
            float translateZ = -0.1F * ft1 + 0.1F * ft2 + 0.04F * ft3 + 0.1F * ft4;
            float rotateX = 1.0F * ft1 - 4.0F * ft4 + 3.0F * ft5;
            float rotateY = 0.0F;
            float rotateZ = 10.0F * ft1 - 10.0F * ft4;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAPistolAimShoot extends PAGunAimShootNormal {
      public PAPistolAimShoot(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks,
         EnumHand handWithItem
      ) {
         float progressOtherhand = 1.0F
            - Weapons.getPlayerAnimationValue(player, handWithItem == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, partialTicks);
         boolean hasAnimationOtherHand = progressOtherhand > 0.0F;
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.5F);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               if (!hasAnimationOtherHand) {
                  model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
                  model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                     + model.bipedLeftArm.rotateAngleX * ft2;
                  model.bipedLeftArm.rotationPointZ = -ft3;
               }

               model.bipedRightArm.rotationPointZ = ft3;
               model.bipedBody.rotateAngleY += ft3 * 10.0F * (float) (Math.PI / 180.0);
            } else {
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               if (!hasAnimationOtherHand) {
                  model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
                  model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                     + model.bipedRightArm.rotateAngleX * ft2;
                  model.bipedRightArm.rotationPointZ = -ft3;
               }

               model.bipedLeftArm.rotationPointZ = ft3;
               model.bipedBody.rotateAngleY += ft3 * -10.0F * (float) (Math.PI / 180.0);
            }
         } else {
            float rotateX = GetMOP.getfromto(progress, 0.05F, 0.15F) * 8.0F
               - GetMOP.getfromto(progress, 0.15F, 0.3F) * 11.0F
               + GetMOP.getfromto(progress, 0.3F, 0.5F) * 1.5F
               + GetMOP.getfromto(progress, 0.5F, 1.0F) * 1.5F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.5F);
            float translateZ = 0.1F * ft1;
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, translateZ);
         }
      }
   }

   public static class PAPistolAimShootNoPunch extends PAGunAimShootNormal {
      public PAPistolAimShootNoPunch(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public boolean transformItemThirdperson() {
         return false;
      }

      @Override
      public boolean transformItemFirstperson() {
         return false;
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks,
         EnumHand handWithItem
      ) {
         float progressOtherhand = 1.0F
            - Weapons.getPlayerAnimationValue(player, handWithItem == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, partialTicks);
         boolean hasAnimationOtherHand = progressOtherhand > 0.0F;
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
               if (!hasAnimationOtherHand) {
                  model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
                  model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                     + model.bipedLeftArm.rotateAngleX * ft2;
               }
            } else {
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
               if (!hasAnimationOtherHand) {
                  model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
                  model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                     + model.bipedRightArm.rotateAngleX * ft2;
               }
            }
         }
      }
   }

   public static class PAPistolReloadingSpin extends PlayerAnimation {
      public PAPistolReloadingSpin(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float rotateX = GetMOP.getfromto(progress, 0.0F, 0.2F) * -45.0F
               + GetMOP.getfromto(progress, 0.5F, 0.75F) * 160.0F
               + GetMOP.getfromto(progress, 0.75F, 1.0F) * 245.0F;
            float rotateY = (GetMOP.getfromto(progress, 0.0F, 0.15F) - GetMOP.getfromto(progress, 0.3F, 0.4F)) * 8.0F;
            float translateY = -0.2F * GetMOP.getfromto(progress, 0.4F, 0.75F)
               + GetMOP.getfromto(progress, 0.75F, 0.9F) * 0.3F
               + GetMOP.getfromto(progress, 0.9F, 1.0F) * -0.1F;
            float translateZ = GetMOP.getfromto(progress, 0.55F, 0.7F) * 0.4F
               + GetMOP.getfromto(progress, 0.7F, 0.9F) * -0.8F
               + GetMOP.getfromto(progress, 0.9F, 1.0F) * 0.4F;
            GlStateManager.translate(0.0F, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.0F, 0.2F) + GetMOP.getfromto(progress, 0.4F, 0.8F);
            float ft2 = 1.0F - ft1;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedRightArm.rotateAngleY * ft2;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedRightArm.rotateAngleX * ft2;
            } else {
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft1 + model.bipedLeftArm.rotateAngleY * ft2;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft1
                  + model.bipedLeftArm.rotateAngleX * ft2;
            }

            float ft3 = -20.0F * GetMOP.getfromto(progress, 0.4F, 0.75F)
               + GetMOP.getfromto(progress, 0.75F, 0.9F) * 30.0F
               + GetMOP.getfromto(progress, 0.9F, 1.0F) * -10.0F;
            handrenderer.rotateAngleX -= ft3 * (float) (Math.PI / 180.0);
         } else {
            float rotateX = GetMOP.getfromto(progress, 0.0F, 0.2F) * -25.0F
               + GetMOP.getfromto(progress, 0.5F, 0.75F) * 160.0F
               + GetMOP.getfromto(progress, 0.75F, 1.0F) * 225.0F;
            float translateZ = GetMOP.getfromto(progress, 0.55F, 0.7F) * 0.1F
               + GetMOP.getfromto(progress, 0.7F, 0.9F) * -0.2F
               + GetMOP.getfromto(progress, 0.9F, 1.0F) * 0.1F;
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, translateZ);
         }
      }
   }

   public static class PAReloadingGunNormal extends PlayerAnimation {
      public float offsetMult;
      public float fallupTimeOffset;

      public PAReloadingGunNormal(byte id, int animationLength, float XoffsetMult, float fallupTimeOffset) {
         super(id, (byte)animationLength);
         this.offsetMult = XoffsetMult;
         this.fallupTimeOffset = fallupTimeOffset;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float handMult = enumhandside == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.2F);
            float ft1 = ft0 - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float rotateX = -30.0F * ft1;
            float rotateY = 50.0F * (ft0 - GetMOP.getfromto(progress, 0.8F, 0.95F));
            float rotateZ = 30.0F * ft1 + 10.0F * (GetMOP.getfromto(progress, 0.15F, 0.4F) - GetMOP.getfromto(progress, 0.65F, 0.9F));
            float ft2 = GetMOP.getfromto(progress, 0.1F, 0.5F) - GetMOP.getfromto(progress, 0.5F, 0.7F);
            float translateY = (
                     GetMOP.getfromto(progress, 0.4F + this.fallupTimeOffset, 0.45F + this.fallupTimeOffset)
                        - GetMOP.getfromto(progress, 0.45F + this.fallupTimeOffset, 0.5F + this.fallupTimeOffset)
                  )
                  * 0.05F
               - 0.1F * ft2;
            float translateZ = -0.2F * ft1;
            GlStateManager.translate(-0.2F * ft1 * this.offsetMult * handMult, translateY + Debugger.floats[1], translateZ + Debugger.floats[2]);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY * handMult, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotateZ * handMult, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float ft2 = GetMOP.getfromto(progress, 0.2F, 0.35F) - GetMOP.getfromto(progress, 0.35F, 0.5F);
            float ft3 = GetMOP.getfromto(progress, 0.1F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float ft4 = GetMOP.getfromto(progress, 0.55F, 0.65F) - GetMOP.getfromto(progress, 0.65F, 0.75F);
            model.bipedLeftArm.rotateAngleX *= 1.0F - 0.7F * ft1;
            model.bipedRightArm.rotateAngleX *= 1.0F - 0.7F * ft1;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                  (-30.0F * ft1 - 20.0F * ft2 + GetMOP.getfromto(progress, 0.8F, 1.0F) * -30.0F) * (float) (Math.PI / 180.0)
                     + model.bipedHead.rotateAngleX * 0.7F
               );
            handrenderer.rotateAngleY += 5.0F * ft1 * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ += (-10.0F * ft1 + ft4 * 15.0F) * (float) (Math.PI / 180.0);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX
                  + (
                     (-40.0F * ft3 + ft2 * 25.0F + (GetMOP.getfromto(progress, 0.5F, 0.55F) - GetMOP.getfromto(progress, 0.75F, 0.85F)) * -20.0F)
                           * (float) (Math.PI / 180.0)
                        + model.bipedHead.rotateAngleX * 0.7F
                  );
               model.bipedLeftArm.rotateAngleY += ft4 * 10.0F * (float) (Math.PI / 180.0);
               model.bipedLeftArm.rotateAngleZ += (15.0F * ft3 + ft4 * 20.0F) * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX
                  + (
                     (-40.0F * ft3 + ft2 * 25.0F + (GetMOP.getfromto(progress, 0.5F, 0.55F) - GetMOP.getfromto(progress, 0.75F, 0.85F)) * -20.0F)
                           * (float) (Math.PI / 180.0)
                        + model.bipedHead.rotateAngleX * 0.7F
                  );
               model.bipedRightArm.rotateAngleY -= ft4 * 10.0F * (float) (Math.PI / 180.0);
               model.bipedRightArm.rotateAngleZ -= (15.0F * ft3 + ft4 * 20.0F) * (float) (Math.PI / 180.0);
            }
         } else {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float rotateX = 7.0F * ft1;
            float rotateY = 75.0F * ft1;
            float rotateZ = 0.0F;
            float translateZ = -0.4F * ft1 * this.offsetMult;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY * handMult, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ * handMult, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAReloadingMinigun extends PlayerAnimation {
      public float fallupTimeOffset;
      public Vec3d firstpersonOffsetAdd;

      public PAReloadingMinigun(byte id, int animationLength, float fallupTimeOffset, Vec3d firstpersonOffsetAdd) {
         super(id, (byte)animationLength);
         this.fallupTimeOffset = fallupTimeOffset;
         this.firstpersonOffsetAdd = firstpersonOffsetAdd;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.2F);
            float ft1 = ft0 - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float rotateX = 20.0F * ft1;
            float rotateY = 30.0F * (ft0 - GetMOP.getfromto(progress, 0.8F, 0.95F));
            float rotateZ = -10.0F * ft1 - 5.0F * (GetMOP.getfromto(progress, 0.15F, 0.4F) - GetMOP.getfromto(progress, 0.65F, 0.9F));
            float ft2 = GetMOP.getfromto(progress, 0.1F, 0.5F) - GetMOP.getfromto(progress, 0.5F, 0.7F);
            float translateX = 0.0F;
            float translateY = 0.5F * ft1
               + (
                     GetMOP.getfromto(progress, 0.45F + this.fallupTimeOffset, 0.475F + this.fallupTimeOffset)
                        - GetMOP.getfromto(progress, 0.475F + this.fallupTimeOffset, 0.5F + this.fallupTimeOffset)
                  )
                  * 0.1F
               - 0.1F * ft2;
            float translateZ = -1.0F * ft1;
            translateX = (float)(translateX + this.firstpersonOffsetAdd.x * ft1);
            translateY = (float)(translateY + this.firstpersonOffsetAdd.y * ft1);
            translateZ = (float)(translateZ + this.firstpersonOffsetAdd.z * ft1);
            GlStateManager.translate(translateX + Debugger.floats[0], translateY + Debugger.floats[1], translateZ + Debugger.floats[2]);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX + Debugger.floats[3], 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY + Debugger.floats[4], 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotateZ + Debugger.floats[5], 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float ft2 = GetMOP.getfromto(progress, 0.2F, 0.35F) - GetMOP.getfromto(progress, 0.35F, 0.5F);
            float ft3 = GetMOP.getfromto(progress, 0.1F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float ft4 = GetMOP.getfromto(progress, 0.55F, 0.65F) - GetMOP.getfromto(progress, 0.65F, 0.75F);
            model.bipedLeftArm.rotateAngleX *= 1.0F - 0.7F * ft1;
            model.bipedRightArm.rotateAngleX *= 1.0F - 0.7F * ft1;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                  (-30.0F * ft1 - 20.0F * ft2 + GetMOP.getfromto(progress, 0.8F, 1.0F) * -30.0F) * (float) (Math.PI / 180.0)
                     + model.bipedHead.rotateAngleX * 0.7F
               );
            handrenderer.rotateAngleY += 5.0F * ft1 * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ += (-10.0F * ft1 + ft4 * 15.0F) * (float) (Math.PI / 180.0);
            if (hand == EnumHandSide.RIGHT) {
               model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX
                  + (
                     (-40.0F * ft3 + ft2 * 25.0F + (GetMOP.getfromto(progress, 0.5F, 0.55F) - GetMOP.getfromto(progress, 0.75F, 0.85F)) * -20.0F)
                           * (float) (Math.PI / 180.0)
                        + model.bipedHead.rotateAngleX * 0.7F
                  );
               model.bipedLeftArm.rotateAngleY += ft4 * 10.0F * (float) (Math.PI / 180.0);
               model.bipedLeftArm.rotateAngleZ += (15.0F * ft3 + ft4 * 20.0F) * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX
                  + (
                     (-40.0F * ft3 + ft2 * 25.0F + (GetMOP.getfromto(progress, 0.5F, 0.55F) - GetMOP.getfromto(progress, 0.75F, 0.85F)) * -20.0F)
                           * (float) (Math.PI / 180.0)
                        + model.bipedHead.rotateAngleX * 0.7F
                  );
               model.bipedRightArm.rotateAngleY -= ft4 * 10.0F * (float) (Math.PI / 180.0);
               model.bipedRightArm.rotateAngleZ -= (15.0F * ft3 + ft4 * 20.0F) * (float) (Math.PI / 180.0);
            }
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float rotateX = 67.0F * ft1;
            float rotateY = 45.0F * ft1;
            float rotateZ = -25.0F * ft1;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] + 0.3F * ft1, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAReloadingShotgun extends PlayerAnimation {
      public PAReloadingShotgun(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.15F);
            float ft1 = GetMOP.getfromto(progress, 0.15F, 0.3F);
            float ft2 = GetMOP.getfromto(progress, 0.28F, 0.48F) - GetMOP.getfromto(progress, 0.5F, 0.95F);
            float ft3 = GetMOP.getfromto(progress, 0.6F, 0.77F);
            float ft4 = GetMOP.getfromto(progress, 0.77F, 0.94F);
            float translateX = 0.0F;
            float translateY = -0.2F * ft2;
            float translateZ = 0.15F * ft2;
            float rotateX = ft0 * 18.0F + ft1 * 8.0F - 18.0F * ft3 - 8.0F * ft4;
            float rotateY = 5.0F * ft1 - 5.0F * ft4;
            float rotateZ = -5.0F * ft1 + 5.0F * ft4;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float ft2 = (GetMOP.getfromto(progress, 0.28F, 0.48F) - GetMOP.getfromto(progress, 0.5F, 0.95F)) * 0.6F;
         float ft3 = GetMOP.getfromto(progress, 0.6F, 0.77F);
         if (model != null) {
            float ft5 = 1.0F - GetMOP.getfromto(progress, 0.9F, 1.0F);
            float ft6 = 1.0F - ft5;
            float ft7 = GetMOP.getfromto(progress, 0.0F, 0.3F);
            float ft8 = (GetMOP.getfromto(progress, 0.0F, 0.5F) - GetMOP.getfromto(progress, 0.5F, 1.0F)) * 0.35F;
            if (hand == EnumHandSide.RIGHT) {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY) * ft5 + model.bipedRightArm.rotateAngleY * ft6;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY + 0.4F) * ft5 + model.bipedLeftArm.rotateAngleY * ft6;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft5
                  + model.bipedRightArm.rotateAngleX * ft6
                  + 0.18F * ft2
                  + ft8;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft5
                  + model.bipedLeftArm.rotateAngleX * ft6
                  + ft8 * 1.8F;
               model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX
                  + (Debugger.floats[6] - 20.0F * ft7 + 5.0F * ft2 + 20.0F * ft3) * (float) (Math.PI / 180.0);
               model.bipedLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY + (Debugger.floats[7] + 20.0F * ft2) * (float) (Math.PI / 180.0);
               model.bipedLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ + (Debugger.floats[8] - 5.0F * ft2) * (float) (Math.PI / 180.0);
            } else {
               model.bipedRightArm.rotateAngleY = (-0.1F + model.bipedHead.rotateAngleY - 0.4F) * ft5 + model.bipedRightArm.rotateAngleY * ft6;
               model.bipedLeftArm.rotateAngleY = (0.1F + model.bipedHead.rotateAngleY) * ft5 + model.bipedLeftArm.rotateAngleY * ft6;
               model.bipedRightArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft5
                  + model.bipedRightArm.rotateAngleX * ft6
                  + ft8 * 1.8F;
               model.bipedLeftArm.rotateAngleX = ((float) (-Math.PI / 2) + model.bipedHead.rotateAngleX) * ft5
                  + model.bipedLeftArm.rotateAngleX * ft6
                  + 0.18F * ft2
                  + ft8;
               model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX
                  + (Debugger.floats[6] - 20.0F * ft7 + 5.0F * ft2 + 20.0F * ft3) * (float) (Math.PI / 180.0);
               model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY - (Debugger.floats[7] + 20.0F * ft2) * (float) (Math.PI / 180.0);
               model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ + (Debugger.floats[8] - 5.0F * ft2) * (float) (Math.PI / 180.0);
            }
         } else {
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.15F);
            float ft1 = GetMOP.getfromto(progress, 0.15F, 0.3F);
            float ft4 = GetMOP.getfromto(progress, 0.77F, 0.94F);
            float rotateX = ft0 * 18.0F + ft1 * 8.0F - 18.0F * ft3 - 8.0F * ft4;
            float rotateY = 15.0F * ft1 - 15.0F * ft4;
            float rotateZ = -15.0F * ft1 + 15.0F * ft4;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] + 0.1F * ft2, Debugger.floats[1] - 0.2F * ft2, Debugger.floats[2] + 0.1F * ft2);
         }
      }
   }

   public static class PARollFinish extends PlayerAnimation {
      public PARollFinish(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.15F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.373F);
            float ft3 = GetMOP.getfromto(progress, 0.373F, 0.673F);
            float ft4 = GetMOP.getfromto(progress, 0.673F, 0.71F);
            float ft5 = GetMOP.getfromto(progress, 0.71F, 0.725F);
            float ft6 = GetMOP.getfromto(progress, 0.725F, 0.74F);
            float translateX = 0.0F;
            float translateY = 0.01F * ft1 + 0.04F * ft2 + 0.1F * ft3 - 0.15F * ft4 + 0.03F * ft5 - 0.03F * ft6;
            float translateZ = 0.0F;
            float rotateX = 1.5F * ft1 + 3.5F * ft2 + 10.0F * ft3 - 15.0F * ft4 + 3.0F * ft5 - 3.0F * ft6;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.15F);
            float ft2 = GetMOP.getfromto(progress, 0.15F, 0.373F);
            float ft3 = GetMOP.getfromto(progress, 0.373F, 0.673F);
            float ft4 = GetMOP.getfromto(progress, 0.673F, 0.71F);
            float ft5 = GetMOP.getfromto(progress, 0.71F, 0.725F);
            float ft6 = GetMOP.getfromto(progress, 0.725F, 0.74F);
            float ft7 = GetMOP.getfromto(progress, 0.74F, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            float add = -24.0F * ft1 - 8.0F * ft2 - 16.0F * ft3 + 28.0F * ft4 - 4.0F * ft5 + 4.0F * ft6 + 20.0F * ft7;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX + (Debugger.floats[6] + add) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + Debugger.floats[7] * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + Debugger.floats[8] * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleX += add * 0.8F * (float) (Math.PI / 180.0);
         } else {
            float rotateX = 0.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PARollOpen extends PlayerAnimation {
      public PARollOpen(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.2F);
            float ft3 = GetMOP.getfromto(progress, 0.2F, 0.3F);
            float ft4 = GetMOP.getfromto(progress, 0.3F, 0.4F);
            float ft5 = GetMOP.getfromto(progress, 0.45F, 1.0F);
            float translateX = 0.3F * ft2 - 0.5F * ft3 + 0.1F * ft4 + 0.1F * ft5;
            float translateY = 0.15F * ft1 - 0.15F * ft5;
            float translateZ = 0.0F;
            float rotateX = 0.0F;
            float rotateY = -20.0F * ft2 + 20.0F * ft5;
            float rotateZ = -20.0F * ft2 + 30.0F * ft3 - 5.0F * ft4 - 5.0F * ft5;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.1F);
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.2F);
            float ft3 = GetMOP.getfromto(progress, 0.2F, 0.3F);
            float ft4 = GetMOP.getfromto(progress, 0.3F, 0.4F);
            float ft5 = GetMOP.getfromto(progress, 0.45F, 1.0F);
            if (hand != EnumHandSide.RIGHT) {
               ;
            }

            handrenderer.rotateAngleX = handrenderer.rotateAngleX + (Debugger.floats[6] - 20.0F * ft1 + 20.0F * ft5) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + (Debugger.floats[7] + 40.0F * ft2 - 50.0F * ft3 + 5.0F * ft4 + 5.0F * ft5) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + Debugger.floats[8] * (float) (Math.PI / 180.0);
            model.bipedBody.rotateAngleY += (ft2 * 20.0F - ft3 * 20.0F) * (float) (Math.PI / 180.0);
         } else {
            float ft5 = GetMOP.getfromto(progress, 0.45F, 1.0F);
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.2F);
            float rotateX = 0.0F;
            float rotateY = -15.0F * ft2 + 15.0F * ft5;
            float rotateZ = 0.0F;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAShield extends PlayerAnimation {
      public PAShield(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float translateX = 0.0F;
            float translateY = 0.3F;
            float translateZ = 0.6F;
            float rotateX = 0.0F;
            float rotateY = flag1 ? 60.0F : -60.0F;
            float rotateZ = 0.0F;
            GlStateManager.translate(translateX, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateY, 0.0F, 1.0F, 0.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (progress < 1.0F) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            if (model != null) {
               if (hand != EnumHandSide.RIGHT) {
                  ;
               }

               handrenderer.rotateAngleX *= 0.4F;
               handrenderer.rotateAngleX = handrenderer.rotateAngleX + (-0.87266463F + model.bipedHead.rotateAngleX * 0.7F);
               handrenderer.rotateAngleY += -20.0F * handMult * (float) (Math.PI / 180.0);
               handrenderer.rotateAngleZ += (float) (-Math.PI / 18);
               handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY * 0.6F;
            } else {
               float rotateX = 20.0F;
               float rotateY = 40.0F * handMult;
               float rotateZ = 0.0F;
               GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(-0.18F * handMult, 0.05F, 0.0F);
            }
         }
      }
   }

   public static class PASpearAttack extends PlayerAnimation {
      public PASpearAttack(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float rotateX = GetMOP.getfromto(progress, 0.0F, 0.2F) * -70.0F
               + GetMOP.getfromto(progress, 0.2F, 0.4F) * -10.0F
               + GetMOP.getfromto(progress, 0.6F, 1.0F) * 80.0F;
            float translateX = GetMOP.getfromto(progress, 0.1F, 0.4F) * -0.3F - GetMOP.getfromto(progress, 0.6F, 1.0F) * -0.3F;
            float translateY = GetMOP.getfromto(progress, 0.0F, 0.5F) * 0.3F - GetMOP.getfromto(progress, 0.5F, 1.0F) * 0.3F;
            float translateZ = GetMOP.getfromto(progress, 0.3F, 0.5F) * -2.0F + GetMOP.getfromto(progress, 0.5F, 0.8F) * 2.0F;
            GlStateManager.translate(translateX, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft1 = GetMOP.getfromto(progress, 0.2F, 0.4F) - GetMOP.getfromto(progress, 0.4F, 0.7F);
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            handrenderer.rotateAngleX *= 1.0F - ft2;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                     GetMOP.getfromto(progress, 0.0F, 0.3F) * 15.0F
                        + GetMOP.getfromto(progress, 0.3F, 0.5F) * -98.0F
                        + GetMOP.getfromto(progress, 0.5F, 0.8F) * 83.0F
                  )
                  * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + (ft1 * -9.0F * (float) (Math.PI / 180.0) * handMult + model.bipedHead.rotateAngleY * ft2 * 0.7F);
            handrenderer.rotateAngleZ += ft1 * 8.0F * (float) (Math.PI / 180.0) * handMult;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX + model.bipedHead.rotateAngleX * ft2 * 0.85F;
            model.bipedBody.rotateAngleY = model.bipedBody.rotateAngleY
               + (
                     GetMOP.getfromto(progress, 0.0F, 0.3F) * 10.0F
                        + GetMOP.getfromto(progress, 0.3F, 0.5F) * -15.0F
                        + GetMOP.getfromto(progress, 0.5F, 0.8F) * 5.0F
                  )
                  * (float) (Math.PI / 180.0)
                  * handMult;
         } else {
            float rotateX = GetMOP.getfromto(progress, 0.0F, 0.3F) * -20.0F
               + GetMOP.getfromto(progress, 0.3F, 0.5F) * -60.0F
               + GetMOP.getfromto(progress, 0.5F, 0.8F) * 80.0F;
            float translateX = -0.05F * (GetMOP.getfromto(progress, 0.0F, 0.15F) - GetMOP.getfromto(progress, 0.8F, 1.0F));
            float translateY = GetMOP.getfromto(progress, 0.0F, 0.3F) * -0.5F
               + GetMOP.getfromto(progress, 0.3F, 0.5F) * 1.0F
               - GetMOP.getfromto(progress, 0.5F, 0.8F) * 0.5F;
            float translateZ = GetMOP.getfromto(progress, 0.1F, 0.4F) * -0.2F - GetMOP.getfromto(progress, 0.6F, 1.0F) * -0.2F;
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, translateY, translateZ);
         }
      }
   }

   public static class PAStaffCastGroundhit extends PlayerAnimation {
      public PAStaffCastGroundhit(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.37F);
            float ft1_1 = GetMOP.getfromto(progress, 0.16F, 0.37F);
            float ft2 = GetMOP.getfromto(progress, 0.43F, 0.5F);
            float ft3 = GetMOP.getfromto(progress, 0.5F, 0.54F) - GetMOP.getfromto(progress, 0.54F, 0.58F);
            float ft4 = GetMOP.getfromto(progress, 0.65F, 1.0F);
            float ft5 = GetMOP.getfromto(progress, 0.62F, 0.85F) - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float translateX = -1.0F * ft1_1 + 1.0F * ft4;
            float translateY = 2.0F * ft1 - 2.0F * ft2 + 0.07F * ft3;
            float translateZ = 0.1F * ft5;
            float rotateX = 55.0F * ft1 - 55.0F * ft2 - 10.0F * ft5;
            float rotateY = 0.0F;
            float rotateZ = -8.0F * ft5;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            float ft0 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            model.bipedLeftArm.rotateAngleX *= 1.0F - 0.85F * ft0;
            model.bipedRightArm.rotateAngleX *= 1.0F - 0.85F * ft0;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.37F) * -140.0F
               + GetMOP.getfromto(progress, 0.43F, 0.5F) * 60.0F
               + GetMOP.getfromto(progress, 0.5F, 0.54F) * -5.0F
               + GetMOP.getfromto(progress, 0.54F, 0.58F) * 5.0F;
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.28F) * -20.0F;
            float ft3 = GetMOP.getfromto(progress, 0.7F, 1.0F);
            handrenderer.rotateAngleX += (ft1 + ft3 * 80.0F) * (float) (Math.PI / 180.0);
            handrenderer.rotateAngleY += (ft2 + ft3 * 20.0F) * (float) (Math.PI / 180.0);
            float ft4 = GetMOP.getfromto(progress, 0.08F, 0.37F) * -120.0F
               + GetMOP.getfromto(progress, 0.43F, 0.5F) * 45.0F
               + GetMOP.getfromto(progress, 0.5F, 0.54F) * -5.0F
               + GetMOP.getfromto(progress, 0.54F, 0.58F) * 5.0F;
            float ft5 = GetMOP.getfromto(progress, 0.43F, 0.5F);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX + (Debugger.floats[6] + ft4 + ft3 * 75.0F) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY + (Debugger.floats[7] - ft2 + ft3 * -20.0F) * (float) (Math.PI / 180.0);
            handrenderer2.rotateAngleZ = handrenderer2.rotateAngleZ + (Debugger.floats[8] + ft2 + ft5 * 20.0F) * (float) (Math.PI / 180.0);
         } else {
            float ft1 = GetMOP.getfromto(progress, 0.43F, 0.5F) * 10.0F;
            float ft3 = GetMOP.getfromto(progress, 0.7F, 1.0F);
            float rotateX = ft1 - ft3 * 10.0F;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateZ = 0.0F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAStaffShootMiddle extends PlayerAnimation {
      public byte cascadeAnimStart;

      public PAStaffShootMiddle(byte id, int animationLength, byte cascadeAnimStart) {
         super(id, (byte)animationLength);
         this.cascadeAnimStart = cascadeAnimStart;
      }

      @Override
      public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
         return timePlaying <= 0 || animationPlaying != this.ID && animationPlaying != this.cascadeAnimStart ? this.cascadeAnimStart : this.ID;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.75F, 1.0F);
            float ft2 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float translateX = -1.09F * ft1;
            float translateY = 0.0F;
            float translateZ = 0.0F;
            float rotateX = -60.0F * ft2;
            float rotateY = -10.0F * ft1;
            float rotateZ = 2.0F * ft1;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float ft1 = 1.0F - GetMOP.getfromto(progress, 0.75F, 1.0F);
         float ft2 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
         if (model != null) {
            float ft0 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer2.rotateAngleX *= 1.0F - 0.85F * ft0;
            handrenderer.rotateAngleX *= 1.0F - 0.95F * ft0;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + ((Debugger.floats[6] - 68.0F * ft2) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * ft0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((Debugger.floats[7] - 11.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * ft0);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX
               + ((Debugger.floats[6] - 45.0F * ft2) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * 0.9F * ft0);
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY
               + ((Debugger.floats[7] + 25.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * 0.9F * ft0);
            handrenderer2.rotateAngleZ = handrenderer2.rotateAngleZ + (Debugger.floats[8] + 10.0F * ft1) * (float) (Math.PI / 180.0);
         } else {
            float rotateX = -65.0F * ft2;
            float rotateY = 0.0F;
            float rotateZ = -5.0F * ft1;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] - 0.08F * ft1, Debugger.floats[1], Debugger.floats[2] - 0.15F * ft1);
         }
      }
   }

   public static class PAStaffShootMiddlePunch extends PlayerAnimation {
      public byte cascadeAnimStart;
      public float punchAmount;

      public PAStaffShootMiddlePunch(byte id, int animationLength, byte cascadeAnimStart, float punchAmount) {
         super(id, (byte)animationLength);
         this.cascadeAnimStart = cascadeAnimStart;
         this.punchAmount = punchAmount;
      }

      @Override
      public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
         return timePlaying <= 0 || animationPlaying != this.ID && animationPlaying != this.cascadeAnimStart ? this.cascadeAnimStart : this.ID;
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float ft1 = 1.0F - GetMOP.getfromto(progress, 0.75F, 1.0F);
            float ft2 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
            float ft3 = (GetMOP.getfromto(progress, 0.0F, 0.085F) - GetMOP.getfromto(progress, 0.085F, 0.25F)) * this.punchAmount;
            float translateX = -1.09F * ft1;
            float translateY = -0.12F * ft3;
            float translateZ = 0.2F * ft3;
            float rotateX = -60.0F * ft2;
            float rotateY = -10.0F * ft1;
            float rotateZ = 2.0F * ft1;
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         float ft1 = 1.0F - GetMOP.getfromto(progress, 0.75F, 1.0F);
         float ft2 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
         float ft3 = (GetMOP.getfromto(progress, 0.0F, 0.085F) - GetMOP.getfromto(progress, 0.085F, 0.25F)) * this.punchAmount;
         if (model != null) {
            float ft0 = 1.0F - GetMOP.getfromto(progress, 0.85F, 1.0F);
            ModelRenderer handrenderer2 = hand == EnumHandSide.RIGHT ? model.bipedLeftArm : model.bipedRightArm;
            handrenderer2.rotateAngleX *= 1.0F - 0.85F * ft0;
            handrenderer.rotateAngleX *= 1.0F - 0.95F * ft0;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + ((Debugger.floats[6] - 68.0F * ft2 + 14.0F * ft3) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * ft0);
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((Debugger.floats[7] - 11.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * ft0);
            handrenderer2.rotateAngleX = handrenderer2.rotateAngleX
               + ((Debugger.floats[6] - 45.0F * ft2 + 9.0F * ft3) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleX * 0.9F * ft0);
            handrenderer2.rotateAngleY = handrenderer2.rotateAngleY
               + ((Debugger.floats[7] + 25.0F * ft1) * (float) (Math.PI / 180.0) + model.bipedHead.rotateAngleY * 0.9F * ft0);
            handrenderer2.rotateAngleZ = handrenderer2.rotateAngleZ + (Debugger.floats[8] + 10.0F * ft1 + 5.0F * ft3) * (float) (Math.PI / 180.0);
            model.bipedBody.rotateAngleY += ft3 * 10.0F * (float) (Math.PI / 180.0);
         } else {
            float rotateX = -65.0F * ft2 + 14.0F * ft3;
            float rotateY = 0.0F;
            float rotateZ = -5.0F * ft1;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] - 0.08F * ft1, Debugger.floats[1] - 0.2F * ft3, Debugger.floats[2] - 0.15F * ft1 + 0.04F * ft3);
         }
      }
   }

   public static class PAStaffShootNormal extends PlayerAnimation {
      public PAStaffShootNormal(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            int i = flag1 ? 1 : -1;
            float translateX = GetMOP.getfromto(progress, 0.0F, 0.1F) * -0.3F + GetMOP.getfromto(progress, 0.2F, 0.4F) * 0.3F;
            float translateY = GetMOP.getfromto(progress, 0.05F, 0.15F) * 0.25F + GetMOP.getfromto(progress, 0.2F, 0.45F) * -0.25F;
            float translateZ = GetMOP.getfromto(progress, 0.0F, 0.1F) * -0.5F + GetMOP.getfromto(progress, 0.15F, 0.5F) * 0.5F;
            float rotateX = GetMOP.getfromto(progress, 0.0F, 0.1F) * -60.0F + GetMOP.getfromto(progress, 0.15F, 0.8F) * 60.0F;
            float rotateY = GetMOP.getfromto(progress, 0.2F, 0.4F) * 5.0F + GetMOP.getfromto(progress, 0.4F, 0.6F) * -5.0F;
            float rotateZ = 0.0F;
            GlStateManager.translate(Debugger.floats[0] + translateX * i, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            float handMult = hand == EnumHandSide.LEFT ? -1.0F : 1.0F;
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.2F) - GetMOP.getfromto(progress, 0.8F, 1.0F);
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.8F);
            handrenderer.rotateAngleX *= 1.0F - ft2;
            handrenderer.rotateAngleY = handrenderer.rotateAngleY + model.bipedHead.rotateAngleY * ft2 * 0.7F;
            handrenderer.rotateAngleX = handrenderer.rotateAngleX + model.bipedHead.rotateAngleX * ft2 * 0.85F;
            model.bipedBody.rotateAngleY = model.bipedBody.rotateAngleY
               + (GetMOP.getfromto(progress, 0.0F, 0.1F) * -10.0F + GetMOP.getfromto(progress, 0.15F, 0.4F) * 10.0F) * (float) (Math.PI / 180.0) * handMult;
            handrenderer.rotateAngleX += ft3 * -70.0F * (float) (Math.PI / 180.0) * ft2;
         } else {
            float ft3 = GetMOP.getfromto(progress, 0.0F, 0.1F) - GetMOP.getfromto(progress, 0.15F, 0.8F);
            float rotateX = ft3 * -60.0F;
            float rotateY = GetMOP.getfromto(progress, 0.0F, 0.4F) * 15.0F + GetMOP.getfromto(progress, 0.4F, 1.0F) * -15.0F;
            float rotateZ = 0.0F;
            float translateY = GetMOP.getfromto(progress, 0.05F, 0.15F) * 0.1F + GetMOP.getfromto(progress, 0.2F, 0.45F) * -0.1F;
            float translateZ = GetMOP.getfromto(progress, 0.0F, 0.1F) * -0.15F + GetMOP.getfromto(progress, 0.15F, 0.5F) * 0.15F;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0], Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAWhipAttack extends PlayerAnimation {
      public PAWhipAttack(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            int i = flag1 ? 1 : -1;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.18F);
            float ft2 = GetMOP.softfromto(progress, 0.12F, 0.35F);
            float ft3 = GetMOP.getfromto(progress, 0.42F, 0.48F);
            float ft3_4 = GetMOP.softfromto(progress, 0.52F, 0.85F);
            float ft4 = GetMOP.softfromto(progress, 0.62F, 1.0F);
            float ft5 = GetMOP.softfromto(progress, 0.32F, 0.42F) - GetMOP.getfromto(progress, 0.48F, 0.54F);
            float translateX = -0.1F * ft2 - 0.4F * ft3 + 0.5F * ft3_4;
            float translateY = 0.5F * ft2 - 0.1F * ft3 - 0.4F * ft3_4 - 0.3F * ft5;
            float translateZ = 0.2F * ft1 - 0.2F * ft2 - 0.1F * ft3 + 0.1F * ft3_4 - 0.5F * ft5;
            float rotateX = -20.0F * ft1 + 80.0F * ft2 - 125.0F * ft3 + 65.0F * ft4;
            float rotateY = -10.0F * ft2 + 10.0F * ft3;
            float rotateZ = 10.0F * ft2 - 10.0F * ft4;
            GlStateManager.translate(translateX * i, translateY, translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotateY * i, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rotateZ * i, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress,
         @Nullable ModelBiped model,
         @Nullable ModelRenderer handrenderer,
         EnumHandSide hand,
         @Nullable ItemStack stack,
         EntityPlayer player,
         float partialTicks
      ) {
         if (model != null) {
            int handMult = hand == EnumHandSide.RIGHT ? 1 : -1;
            float ft1 = GetMOP.getfromto(progress, 0.0F, 0.18F);
            float ft2 = GetMOP.softfromto(progress, 0.12F, 0.35F);
            float ft3 = GetMOP.getfromto(progress, 0.42F, 0.48F);
            float ft4 = GetMOP.softfromto(progress, 0.62F, 1.0F);
            handrenderer.rotateAngleX = handrenderer.rotateAngleX
               + (
                  (Debugger.floats[6] + 10.0F * ft1 - 150.0F * ft2 + 125.0F * ft3 - 65.0F * ft4) * (float) (Math.PI / 180.0)
                     + model.bipedHead.rotateAngleX
               );
            handrenderer.rotateAngleY = handrenderer.rotateAngleY
               + ((Debugger.floats[7] + 10.0F * ft1 - 15.0F * ft2 + 5.0F * ft4) * (float) (Math.PI / 180.0) * handMult + model.bipedHead.rotateAngleY);
            handrenderer.rotateAngleZ = handrenderer.rotateAngleZ
               + (Debugger.floats[8] + 10.0F * ft1 - 20.0F * ft2 + 10.0F * ft4) * (float) (Math.PI / 180.0) * handMult;
         } else {
            float ft2 = GetMOP.softfromto(progress, 0.12F, 0.35F);
            float ft3 = GetMOP.getfromto(progress, 0.42F, 0.48F);
            float ft3_4 = GetMOP.softfromto(progress, 0.52F, 0.85F);
            float ft4 = GetMOP.softfromto(progress, 0.62F, 1.0F);
            float rotateX = -18.0F * ft3 + 18.0F * ft4;
            float rotateY = 0.0F;
            float rotateZ = 0.0F;
            float translateX = 0.0F;
            float translateY = 0.2F * ft2 - 0.2F * ft3_4;
            float translateZ = -0.1F * ft2 + 0.1F * ft3_4;
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         }
      }
   }

   public static class PAWhipSpecAttack extends PlayerAnimation {
      public PAWhipSpecAttack(byte id, int animationLength) {
         super(id, (byte)animationLength);
      }

      @Override
      public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
         boolean flag = hand == EnumHand.MAIN_HAND;
         EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
         GlStateManager.pushMatrix();
         if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
               PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
            }
         } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            int i = flag1 ? 1 : -1;
            float ft2 = GetMOP.getfromto(progress, 0.0F, 0.175F);
            float ft2_3 = GetMOP.getfromto(progress, 0.175F, 0.245F);
            float ft3 = GetMOP.getfromto(progress, 0.245F, 0.52F);
            float ft4 = GetMOP.getfromto(progress, 0.52F, 0.65F);
            float ft5 = GetMOP.getfromto(progress, 0.65F, 1.0F);
            float translateX = 0.7F - 0.7F * ft2 + 0.3F * ft3 + 0.3F * ft4 - 0.6F * ft5;
            float translateY = 0.3F + 0.2F * ft2 - 0.2F * ft3 + 0.1F * ft4 - 0.4F * ft5;
            float translateZ = -0.1F - 0.5F * ft2 + 0.9F * ft3 - 0.4F * ft4 + 0.1F * ft5;
            float rotateX = 5.0F * ft2 - 5.0F * ft5;
            float rotateY = 40.0F + 40.0F * ft2 + 20.0F * ft2_3 - 40.0F * ft3 - 30.0F * ft4 - 30.0F * ft5;
            float rotateZ = -80.0F + 30.0F * ft3 + 10.0F * ft4 + 40.0F * ft5;
            GlStateManager.translate(Debugger.floats[0] + translateX * i, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
            PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
            GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[4] + rotateY * i, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Debugger.floats[5] + rotateZ * i, 0.0F, 0.0F, 1.0F);
            PlayerAnimations.instance
               .renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
         }

         GlStateManager.popMatrix();
      }

      @Override
      public void transform(
         float progress, ModelBiped biped, ModelRenderer handrenderer, EnumHandSide enumhandside, ItemStack stack, EntityPlayer player, float partialTicks
      ) {
         if (biped != null) {
            ModelRenderer modelrenderer = getArmForSide(enumhandside, biped);
            biped.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(progress) * (float) (Math.PI * 2)) * 0.2F;
            if (enumhandside == EnumHandSide.LEFT) {
               biped.bipedBody.rotateAngleY *= -1.0F;
            }

            biped.bipedRightArm.rotationPointZ = MathHelper.sin(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedRightArm.rotationPointX = -MathHelper.cos(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedLeftArm.rotationPointZ = -MathHelper.sin(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedLeftArm.rotationPointX = MathHelper.cos(biped.bipedBody.rotateAngleY) * 5.0F;
            biped.bipedRightArm.rotateAngleY = biped.bipedRightArm.rotateAngleY + biped.bipedBody.rotateAngleY;
            biped.bipedLeftArm.rotateAngleY = biped.bipedLeftArm.rotateAngleY + biped.bipedBody.rotateAngleY;
            biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX + biped.bipedBody.rotateAngleY;
            float f1 = 1.0F - progress;
            f1 *= f1;
            f1 *= f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(progress * (float) Math.PI) * -(biped.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float)(modelrenderer.rotateAngleX - (f2 * 1.2 + f3));
            modelrenderer.rotateAngleY = modelrenderer.rotateAngleY + biped.bipedBody.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ = modelrenderer.rotateAngleZ + MathHelper.sin(progress * (float) Math.PI) * -0.4F;
         }
      }
   }
}

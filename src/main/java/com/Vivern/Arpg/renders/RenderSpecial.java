package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.EntityCrystalCutterModel;
import com.Vivern.Arpg.elements.models.HorribleEmeraldShootModel;
import com.Vivern.Arpg.elements.models.LordOfPainSpikeModel;
import com.Vivern.Arpg.elements.models.ModelSphere;
import com.Vivern.Arpg.elements.models.ModelsStormledgeMob;
import com.Vivern.Arpg.entity.BlowholeShoot;
import com.Vivern.Arpg.entity.CeratargetShoot;
import com.Vivern.Arpg.entity.CoralPolyp;
import com.Vivern.Arpg.entity.CrystalFanShoot;
import com.Vivern.Arpg.entity.EntityAcidBomb;
import com.Vivern.Arpg.entity.EntityChainMace;
import com.Vivern.Arpg.entity.EntityCrystalCutter;
import com.Vivern.Arpg.entity.EntityFrostfireExplosive;
import com.Vivern.Arpg.entity.EntityGrenade;
import com.Vivern.Arpg.entity.EntityMagneticField;
import com.Vivern.Arpg.entity.EntityMiniNuke;
import com.Vivern.Arpg.entity.EntitySnapball;
import com.Vivern.Arpg.entity.EntitySpellForgeCatalyst;
import com.Vivern.Arpg.entity.EntityTimelessSword;
import com.Vivern.Arpg.entity.LordOfPainSpike;
import com.Vivern.Arpg.entity.WhispersShoot;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.renders.mobrender.RenderTentacles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<Entity>`
@SideOnly(Side.CLIENT)
public class RenderSpecial<T extends Entity> extends Render<Entity> {
   public final int type;
   public ResourceLocation tex;
   public static LordOfPainSpikeModel model1 = new LordOfPainSpikeModel();
   public static ModelsStormledgeMob.ShieldModel model2 = new ModelsStormledgeMob.ShieldModel();
   public static EntityCrystalCutterModel model3 = new EntityCrystalCutterModel();
   public static HorribleEmeraldShootModel modelEmeraldShoot = new HorribleEmeraldShootModel();
   public static ModelSphere sphere1 = new ModelSphere(0.5F, 8);
   public static ModelSphere sphere2 = new ModelSphere(0.18F, 7);
   public static ModelSphere sphere3 = new ModelSphere(0.25F, 7);
   public static ModelSphere sphere4 = new ModelSphere(0.25F, 6);
   public static ModelSphere sphere5 = new ModelSphere(0.25F, 9);
   public static ModelSphere sphere6 = new ModelSphere(0.25F, 12);
   public static ResourceLocation gloss1 = new ResourceLocation("arpg:textures/gloss_map1.png");
   public static ResourceLocation gloss2 = new ResourceLocation("arpg:textures/gloss_map2.png");
   public static ResourceLocation gloss3 = new ResourceLocation("arpg:textures/gloss_map3.png");
   public static ResourceLocation spherefill = new ResourceLocation("arpg:textures/lightnings.png");
   public static ResourceLocation round = new ResourceLocation("arpg:textures/purple_smoke.png");
   public static ResourceLocation crystalCutterTex = new ResourceLocation("arpg:textures/entity_crystal_cutter_model_tex.png");
   public static ResourceLocation crystalCutterTexBeam = new ResourceLocation("arpg:textures/crystal_cutter_shoot.png");
   public static ResourceLocation sparkle3 = new ResourceLocation("arpg:textures/sparkle3.png");
   public static ResourceLocation magic_rocket = new ResourceLocation("arpg:textures/magic_rocket.png");
   public static ResourceLocation plasma_circle_big_a = new ResourceLocation("arpg:textures/plasma_circle_big_a.png");
   public static ResourceLocation plasma_circle_big_b = new ResourceLocation("arpg:textures/plasma_circle_big_b.png");

   public RenderSpecial(RenderManager renderManagerIn, int type, ResourceLocation tex) {
      super(renderManagerIn);
      this.type = type;
      this.tex = tex;
   }

   public void doRender(Entity entityTo, double x, double y, double z, float entityYaw, float partialTicks) {
      if (this.type == 1) {
         LordOfPainSpike entity = (LordOfPainSpike)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + 0.6, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.scale(0.19, 0.19, 0.19);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotation, 0.0F, 1.0F, 0.0F);
         this.bindTexture(this.tex);
         GlStateManager.scale(entity.size, entity.size, entity.size);
         model1.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }

      if (this.type == 2) {
         CrystalFanShoot entity = (CrystalFanShoot)entityTo;
         this.bindTexture(this.tex);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.pushMatrix();
         GlStateManager.disableLighting();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         GlStateManager.enableRescaleNormal();
         GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
         GlStateManager.translate(-4.0F, 0.0F, 0.0F);
         GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.3333, 1.0).endVertex();
         bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.3333, 0.0).endVertex();
         tessellator.draw();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.3333, 1.0).endVertex();
         bufferbuilder.pos(8.0, -2.0, 0.0).tex(1.0, 1.0).endVertex();
         bufferbuilder.pos(8.0, 2.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.3333, 0.0).endVertex();
         tessellator.draw();
         if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.enableLighting();
         GlStateManager.popMatrix();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }

      if (this.type == 3) {
         EntityTimelessSword entity = (EntityTimelessSword)entityTo;
         if (entity.to != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(
               x + entity.to.x - entity.posX,
               y + entity.to.y - entity.posY,
               z + entity.to.z - entity.posZ
            );
            GlStateManager.enableRescaleNormal();
            GlStateManager.rotate(-entity.yaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(entity.pitch + 60.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(entity.rotation, 0.0F, 1.0F, 0.0F);
            this.bindTexture(this.tex);
            GlStateManager.scale(entity.scale, entity.scale, entity.scale);
            GL11.glDepthMask(false);
            GL11.glDisable(2884);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
            GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
            tessellator.draw();
            GlStateManager.disableRescaleNormal();
            GL11.glEnable(2896);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glEnable(2884);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }
      }

      if (this.type == 4) {
         HostileProjectiles.SkyGuardShield entity = (HostileProjectiles.SkyGuardShield)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entity.getYOffset(), (float)z);
         GlStateManager.enableRescaleNormal();
         float RP = 0.0F;
         float RY = (float)entity.shieldTick + entity.displace;
         float RY2 = entity.shieldTick - 6.0F + entity.displace;
         entity.rotationPitch = RP;
         entity.rotationYaw = RY;
         entity.prevRotationPitch = RP;
         entity.prevRotationYaw = RY2;
         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 100.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(90.0F, 0.0F, -1.0F, 0.0F);
         this.bindTexture(this.tex);
         float ang = entity.ticksExisted;
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         model2.render(entity, 0.0F, 0.0F, 0.0F, 1.0F, ang / 3.0F * partialTicks, 0.089999996F);
         if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
      }

      if (this.type == 5) {
         EntityFrostfireExplosive entity = (EntityFrostfireExplosive)entityTo;
         BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.pushMatrix();
         GlStateManager.scale(entity.width, entity.height, entity.width);
         GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
         if (entity.fuse - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - (entity.fuse - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
         }

         float f2 = (1.0F - (entity.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         blockrendererdispatcher.renderBlockBrightness(BlocksRegister.FROSTEXPLOSIVE.getDefaultState(), entity.getBrightness());
         GlStateManager.translate(0.0F, 0.0F, 1.0F);
         if (entity.fuse / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcher.renderBlockBrightness(BlocksRegister.FROSTEXPLOSIVE.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
         }

         GlStateManager.popMatrix();
      }

      if (this.type == 6) {
         EntityAcidBomb entityx = (EntityAcidBomb)entityTo;
         BlockRendererDispatcher blockrendererdispatcherx = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
         if (entityx.fuse - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - (entityx.fuse - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
         }

         float f2 = (1.0F - (entityx.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         blockrendererdispatcherx.renderBlockBrightness(BlocksRegister.ACIDBOMB.getDefaultState(), entityx.getBrightness());
         GlStateManager.translate(0.0F, 0.0F, 1.0F);
         if (entityx.fuse / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcherx.renderBlockBrightness(BlocksRegister.ACIDBOMB.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
         }

         GlStateManager.popMatrix();
      }

      if (this.type == 7) {
         EntityMagneticField entityxx = (EntityMagneticField)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxx.height / 2.0F, (float)z);
         GlStateManager.enableBlend();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         GL11.glDepthMask(false);
         this.bindTexture(this.tex);
         double scale = entityxx.renderPower * 0.001;
         GlStateManager.scale(scale, scale, scale);
         sphere1.renderAnimated(8.0F, AnimationTimer.normaltick);
         GL11.glDepthMask(true);
         AbstractMobModel.returnlight();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      if (this.type == 8) {
         EntitySnapball entityxx = (EntitySnapball)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityTo.height / 2.0F, (float)z);
         if (entityxx.picked) {
            float prevscl = entityxx.renderscale + 0.1F;
            float scale = prevscl + (entityxx.renderscale - prevscl) * partialTicks;
            GlStateManager.scale(scale, scale, scale);
         }

         if (entityxx.powered) {
            GlStateManager.scale(1.4, 1.4, 1.4);
            GlStateManager.pushMatrix();
            this.bindTexture(this.tex);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posX * 60.0), 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posY * 60.0), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posZ * 60.0), 0.0F, 0.0F, 1.0F);
            sphere2.renderWithLight(1.4F, 1.2F, 0.9F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 0.4F);
            this.bindTexture(spherefill);
            GlStateManager.enableBlend();
            AbstractMobModel.light(230, false);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            sphere3.renderScaledtexture();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            this.bindTexture(round);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-AnimationTimer.tick * 2, 0.0F, 0.0F, 1.0F);
            float scale1 = 0.4F + MathHelper.sin(AnimationTimer.tick / 14.0F) / 14.0F;
            GlStateManager.scale(scale1, scale1, scale1);
            GlStateManager.color(0.96F, 1.0F, 0.4F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
            tessellator.draw();
            AbstractMobModel.returnlight();
            AbstractMobModel.alphaGlowDisable();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         } else {
            GlStateManager.pushMatrix();
            this.bindTexture(this.tex);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posX * 60.0), 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posY * 60.0), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)MathHelper.wrapDegrees(entityTo.posZ * 60.0), 0.0F, 0.0F, 1.0F);
            sphere2.renderWithLight(1.0F, 1.0F, 1.4F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            this.bindTexture(gloss1);
            GlStateManager.enableBlend();
            AbstractMobModel.light(200, false);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_COLOR);
            sphere3.renderScaledtexture();
            AbstractMobModel.returnlight();
            AbstractMobModel.alphaGlowDisable();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }

         GlStateManager.popMatrix();
      }

      if (this.type == 9) {
         EntityMiniNuke entityxxx = (EntityMiniNuke)entityTo;
         BlockRendererDispatcher blockrendererdispatcherxx = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
         if (entityxxx.fuse - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - (entityxxx.fuse - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.6F;
            GlStateManager.scale(f1, f1, f1);
         }

         float f2 = (1.0F - (entityxxx.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         blockrendererdispatcherxx.renderBlockBrightness(BlocksRegister.MININUKE.getDefaultState(), entityxxx.getBrightness());
         GlStateManager.translate(0.0F, 0.0F, 1.0F);
         if (entityxxx.fuse / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcherxx.renderBlockBrightness(BlocksRegister.MININUKE.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
         }

         GlStateManager.popMatrix();
      }

      if (this.type == 10) {
         EntityChainMace entityxxxx = (EntityChainMace)entityTo;
         if (entityxxxx.mace != null) {
            entityxxxx.mace.renderEntity(entityxxxx, x, y, z, entityYaw, partialTicks, this);
         }
      }

      if (this.type == 11) {
         EntityGrenade entityxxxx = (EntityGrenade)entityTo;
         if (entityxxxx.grenade != null) {
            entityxxxx.grenade.renderGrenade(entityxxxx, x, y, z, entityYaw, partialTicks, this, true);
         }
      }

      if (this.type == 12) {
         BlowholeShoot entityxxxx = (BlowholeShoot)entityTo;
         GlStateManager.pushMatrix();
         float scl = entityxxxx.getActualSize(entityxxxx.ticksExisted, entityxxxx.ticksExisted - 1, partialTicks) * 2.0F;
         float swingtick = Math.max(3.0F * scl - (entityxxxx.ticksExisted + partialTicks), 0.0F);
         float swing = MathHelper.sin(swingtick * (5.0F / entityxxxx.bubbleSize))
            * swingtick
            * (0.06F + (3.0F - MathHelper.clamp(entityxxxx.bubbleSize, 0.0F, 3.0F)) * 0.1F);
         GlStateManager.translate((float)x, (float)y + entityTo.height / 2.0F, (float)z);
         GlStateManager.scale(
            1.0F + entityxxxx.renderSizeSwingX * swing, 1.0F + entityxxxx.renderSizeSwingY * swing, 1.0F + entityxxxx.renderSizeSwingZ * swing
         );
         this.bindTexture(gloss2);
         GlStateManager.enableBlend();
         AbstractMobModel.light(150, false);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(entityxxxx.renderAngleX1, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(AnimationTimer.tick, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entityxxxx.renderAngleZ1, 0.0F, 0.0F, 1.0F);
         GlStateManager.scale(scl, scl, scl);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_COLOR);
         if (scl < 2.0F) {
            sphere4.renderScaledtexture();
         } else if (scl < 4.0F) {
            sphere5.renderScaledtexture();
         } else {
            sphere6.renderScaledtexture();
         }

         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(entityxxxx.renderAngleX2, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-AnimationTimer.tick * 2, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entityxxxx.renderAngleZ2, 0.0F, 0.0F, 1.0F);
         GlStateManager.scale(scl + 0.1F, scl + 0.1F, scl + 0.1F);
         if (scl < 2.0F) {
            sphere4.renderScaledtexture();
         } else if (scl < 4.0F) {
            sphere5.renderScaledtexture();
         } else {
            sphere6.renderScaledtexture();
         }

         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
         AbstractMobModel.returnlight();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.disableBlend();
      }

      if (this.type == 13) {
         EntityCrystalCutter entityxxxxx = (EntityCrystalCutter)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxx.height / 2.0F, (float)z);
         GlStateManager.rotate(
            entityxxxxx.prevRotationYaw + (entityxxxxx.rotationYaw - entityxxxxx.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F
         );
         GlStateManager.rotate(entityxxxxx.prevRotationPitch + (entityxxxxx.rotationPitch - entityxxxxx.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         this.bindTexture(crystalCutterTex);
         float tick = entityxxxxx.ticksExisted - 1.0F + partialTicks;
         float fromto = GetMOP.getfromto(tick, 0.0F, 10.0F);
         float beamwidth = entityxxxxx.cutterSize
            / 2.0F
            * (fromto - (entityxxxxx.destroying ? GetMOP.getfromto(entityxxxxx.destroyTick + partialTicks, 0.0F, 4.0F) : 0.0F));
         float angleAdd = (1.0F - fromto) * 30.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.1F, 0.0F, -beamwidth);
         GlStateManager.rotate(100.0F + angleAdd, 0.0F, 1.0F, 0.0F);
         float sclx = 0.08F;
         GlStateManager.scale(sclx, sclx, sclx);
         model3.render(entityxxxxx, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.1F, 0.0F, beamwidth);
         GlStateManager.rotate(-100.0F - angleAdd, 0.0F, 1.0F, 0.0F);
         GlStateManager.scale(sclx, sclx, sclx);
         model3.render(entityxxxxx, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         GL11.glDepthMask(false);
         this.bindTexture(crystalCutterTexBeam);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float frame = entityxxxxx.ticksExisted % 3;
         float framesAmount = 3.0F;
         float one = 1.0F / framesAmount;
         float oneBeamBig = one * 0.6818182F;
         float oneBeamSmall = one * 0.3181818F;
         float posBeamBigLower = frame * one;
         float posBeamBigHigher = posBeamBigLower + oneBeamBig;
         float posBeamSmallHigher = posBeamBigHigher + oneBeamSmall;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-beamwidth, 0.0, -0.2F).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth, 0.0, 0.3F).tex(0.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, 0.3F).tex(1.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, -0.2F).tex(1.0, posBeamBigHigher).endVertex();
         if (entityxxxxx.triple) {
            float displace = 0.05F;
            float displace2 = 0.2F;
            bufferbuilder.pos(-beamwidth, displace, -0.2F).tex(0.0, posBeamBigHigher).endVertex();
            bufferbuilder.pos(-beamwidth, displace2, 0.3F).tex(0.0, posBeamBigLower).endVertex();
            bufferbuilder.pos(beamwidth, displace2, 0.3F).tex(1.0, posBeamBigLower).endVertex();
            bufferbuilder.pos(beamwidth, displace, -0.2F).tex(1.0, posBeamBigHigher).endVertex();
            bufferbuilder.pos(-beamwidth, -displace, -0.2F).tex(0.0, posBeamBigHigher).endVertex();
            bufferbuilder.pos(-beamwidth, -displace2, 0.3F).tex(0.0, posBeamBigLower).endVertex();
            bufferbuilder.pos(beamwidth, -displace2, 0.3F).tex(1.0, posBeamBigLower).endVertex();
            bufferbuilder.pos(beamwidth, -displace, -0.2F).tex(1.0, posBeamBigHigher).endVertex();
         }

         float onePartWidth = beamwidth * 2.0F / 3.0F;
         float offsetZ = 0.17F;
         float scaleY = 0.12F;
         bufferbuilder.pos(-beamwidth, -scaleY, -0.2F + offsetZ).tex(0.0, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth, scaleY, -0.2F + offsetZ).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F + offsetZ).tex(0.33333334F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F + offsetZ).tex(0.33333334F, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F + offsetZ).tex(0.33333334F, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F + offsetZ).tex(0.33333334F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F + offsetZ).tex(0.6666667F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F + offsetZ).tex(0.6666667F, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F + offsetZ).tex(0.6666667F, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F + offsetZ).tex(0.6666667F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(beamwidth, scaleY, -0.2F + offsetZ).tex(1.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(beamwidth, -scaleY, -0.2F + offsetZ).tex(1.0, posBeamSmallHigher).endVertex();
         tessellator.draw();
         GL11.glDepthMask(true);
         AbstractMobModel.returnlight();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }

      if (this.type == 14) {
         CeratargetShoot entityxxxxx = (CeratargetShoot)entityTo;
         Entity player = Minecraft.getMinecraft().getRenderViewEntity();
         if (entityxxxxx.impacted && entityxxxxx.impactPos != null && player != null) {
            if (entityxxxxx.impactEntity != null) {
               x = entityxxxxx.impactPos.x
                  + GetMOP.partial(entityxxxxx.impactEntity.posX, entityxxxxx.impactEntity.prevPosX, (double)partialTicks)
                  - GetMOP.partial(player.posX, player.prevPosX, (double)partialTicks);
               y = entityxxxxx.impactPos.y
                  + GetMOP.partial(entityxxxxx.impactEntity.posY, entityxxxxx.impactEntity.prevPosY, (double)partialTicks)
                  - GetMOP.partial(player.posY, player.prevPosY, (double)partialTicks);
               z = entityxxxxx.impactPos.z
                  + GetMOP.partial(entityxxxxx.impactEntity.posZ, entityxxxxx.impactEntity.prevPosZ, (double)partialTicks)
                  - GetMOP.partial(player.posZ, player.prevPosZ, (double)partialTicks);
            } else {
               x = entityxxxxx.impactPos.x - GetMOP.partial(player.posX, player.prevPosX, (double)partialTicks);
               y = entityxxxxx.impactPos.y - GetMOP.partial(player.posY, player.prevPosY, (double)partialTicks);
               z = entityxxxxx.impactPos.z - GetMOP.partial(player.posZ, player.prevPosZ, (double)partialTicks);
            }
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxx.height / 2.0F, (float)z);
         if (entityxxxxx.impacted) {
            GlStateManager.rotate(entityxxxxx.fixedYaw - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(entityxxxxx.fixedPitch, 0.0F, 0.0F, 1.0F);
         } else {
            GlStateManager.rotate(
               entityxxxxx.prevRotationYaw + (entityxxxxx.rotationYaw - entityxxxxx.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F
            );
            GlStateManager.rotate(entityxxxxx.prevRotationPitch + (entityxxxxx.rotationPitch - entityxxxxx.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.5F, 0.0F);
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         Minecraft.getMinecraft().renderEngine.bindTexture(this.tex);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 220.0F, 220.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.disableCull();
         GlStateManager.depthMask(false);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         double motion = Math.sqrt(
            entityxxxxx.motionX * entityxxxxx.motionX
               + entityxxxxx.motionY * entityxxxxx.motionY
               + entityxxxxx.motionZ * entityxxxxx.motionZ
         );
         double zLength = Math.min(-1.5 * motion, -0.3);
         float R2 = 1.0F;
         float G2 = 0.65F;
         float B2 = 0.24F;
         float xDisp = 0.025F;
         float yUp = 0.475F;
         float yDown = 0.525F;
         float xDispAdd = -0.02F;
         float yDispAdd = -0.02F;
         float texYAdd = -AnimationTimer.tick / 40.0F;
         float colorR1 = 1.0F;
         float colorG1 = 1.0F;
         float colorB1 = 1.0F;
         bufferbuilder.pos(-xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yUp - yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yUp, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yDown + yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(-xDisp - xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(xDisp, yUp, 0.0).tex(0.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yUp - yDispAdd, zLength)
            .tex(0.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp + xDispAdd, yDown + yDispAdd, zLength)
            .tex(1.0, 1.0F + texYAdd)
            .color(R2, G2, B2, 0.0F)
            .endVertex();
         bufferbuilder.pos(xDisp, yDown, 0.0).tex(1.0, texYAdd).color(colorR1, colorG1, colorB1, 1.0F).endVertex();
         tessellator.draw();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxx.height / 2.0F, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(AnimationTimer.tick * 2, 0.0F, 0.0F, 1.0F);
         this.bindTexture(sparkle3);
         float finalscale = 0.15F + MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.04F;
         GlStateManager.scale(finalscale, finalscale, finalscale);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxx.height / 2.0F, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-AnimationTimer.tick, 0.0F, 0.0F, 1.0F);
         this.bindTexture(magic_rocket);
         finalscale = 0.2F + MathHelper.sin(AnimationTimer.tick / 60.0F) * 0.05F;
         GlStateManager.scale(finalscale, finalscale, finalscale);
         GlStateManager.color(1.0F, 0.55F, 0.2F, 1.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.1).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.1).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.1).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.1).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
      }

      if (this.type == 15) {
         HostileProjectiles.CircleBlast entityxxxxxx = (HostileProjectiles.CircleBlast)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableFog();
         entityxxxxxx.preRenderBlast();
         this.bindTexture(plasma_circle_big_a);
         float offsetFromBounds = 1.1575F;
         float bigscale = (entityxxxxxx.radius + entityxxxxxx.radiusIncreace * partialTicks) * offsetFromBounds;
         float lowscale = (entityxxxxxx.lowRadius + entityxxxxxx.lowRadiusIncreace * partialTicks) * offsetFromBounds;
         GL11.glDepthMask(false);
         GL11.glDisable(2884);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-bigscale, 0.0, -bigscale).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-bigscale, 0.0, bigscale).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(bigscale, 0.0, bigscale).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(bigscale, 0.0, -bigscale).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         this.bindTexture(plasma_circle_big_b);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-lowscale, 0.0, -lowscale).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-lowscale, 0.0, lowscale).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(lowscale, 0.0, lowscale).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(lowscale, 0.0, -lowscale).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glEnable(2896);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glEnable(2884);
         GlStateManager.enableFog();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
      }

      if (this.type == 16) {
         HostileProjectiles.PlasmaRing entityxxxxxx = (HostileProjectiles.PlasmaRing)entityTo;
         float size = Math.min(entityxxxxxx.ticksExisted + partialTicks, 15.0F) / 15.0F;
         this.bindTexture(this.tex);
         entityxxxxxx.setGlColor();
         GlStateManager.pushMatrix();
         GL11.glDisable(2884);
         GL11.glEnable(3042);
         GL11.glDepthMask(false);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableLighting();
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.rotate(
            entityxxxxxx.prevRotationYaw + (entityxxxxxx.rotationYaw - entityxxxxxx.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F
         );
         GlStateManager.rotate(entityxxxxxx.prevRotationPitch + (entityxxxxxx.rotationPitch - entityxxxxxx.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(AnimationTimer.tick, 1.0F, 0.0F, 0.0F);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         double on1 = (entityxxxxxx.radius * 0.92 + 0.2 * MathHelper.sin(AnimationTimer.tick / 30.0F)) * size;
         bufferbuilder.pos(0.05, -on1, -on1).tex(1.0, 1.0).endVertex();
         bufferbuilder.pos(0.05, -on1, on1).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(0.05, on1, on1).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(0.05, on1, -on1).tex(1.0, 0.0).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(-AnimationTimer.tick, 1.0F, 0.0F, 0.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         on1 = (entityxxxxxx.radius * 0.92 + 0.2 * MathHelper.cos(AnimationTimer.tick / 30.0F)) * size;
         bufferbuilder.pos(-0.05, -on1, -on1).tex(1.0, 1.0).endVertex();
         bufferbuilder.pos(-0.05, -on1, on1).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-0.05, on1, on1).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(-0.05, on1, -on1).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(0.1, -on1, -on1).tex(1.0, 1.0).endVertex();
         bufferbuilder.pos(0.1, -on1, on1).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(0.1, on1, on1).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(0.1, on1, -on1).tex(1.0, 0.0).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
         GlStateManager.enableLighting();
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         super.doRender(entityxxxxxx, x, y, z, entityYaw, partialTicks);
      }

      if (this.type == 17) {
         WhispersShoot entityxxxxxx = (WhispersShoot)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxxx.height / 2.0F, (float)z);
         GlStateManager.rotate(
            entityxxxxxx.prevRotationYaw + (entityxxxxxx.rotationYaw - entityxxxxxx.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F
         );
         GlStateManager.rotate(entityxxxxxx.prevRotationPitch + (entityxxxxxx.rotationPitch - entityxxxxxx.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(entityxxxxxx.rotationRoll, 1.0F, 0.0F, 0.0F);
         float beamwidth = entityxxxxxx.cutterSizeRender / 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         AbstractMobModel.light(240, false);
         AbstractMobModel.alphaGlow();
         GL11.glDepthMask(false);
         this.bindTexture(this.tex);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float frame = entityxxxxxx.ticksExisted % 4;
         float framesAmount = 3.0F;
         float one = 1.0F / framesAmount;
         float oneBeamBig = one * 0.6818182F;
         float oneBeamSmall = one * 0.3181818F;
         float posBeamBigLower = frame * one;
         float posBeamBigHigher = posBeamBigLower + oneBeamBig;
         float posBeamSmallHigher = posBeamBigHigher + oneBeamSmall;
         float zScale = entityxxxxxx.powered ? 4.0F : 1.0F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-beamwidth, 0.0, -0.2F * zScale).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth, 0.0, 0.3F * zScale).tex(0.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, 0.3F * zScale).tex(1.0, posBeamBigLower).endVertex();
         bufferbuilder.pos(beamwidth, 0.0, -0.2F * zScale).tex(1.0, posBeamBigHigher).endVertex();
         float onePartWidth = beamwidth * 2.0F / 3.0F;
         float offsetZ = 0.17F * zScale;
         float scaleY = 0.12F;
         bufferbuilder.pos(-beamwidth, -scaleY, -0.2F * zScale + offsetZ).tex(0.0, posBeamSmallHigher).endVertex();
         bufferbuilder.pos(-beamwidth, scaleY, -0.2F * zScale + offsetZ).tex(0.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F * zScale + offsetZ).tex(0.33333334F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F * zScale + offsetZ)
            .tex(0.33333334F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, -scaleY, -0.04F * zScale + offsetZ)
            .tex(0.33333334F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth, scaleY, -0.04F * zScale + offsetZ).tex(0.33333334F, posBeamBigHigher).endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F * zScale + offsetZ)
            .tex(0.6666667F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F * zScale + offsetZ)
            .tex(0.6666667F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, -scaleY, -0.04F * zScale + offsetZ)
            .tex(0.6666667F, posBeamSmallHigher)
            .endVertex();
         bufferbuilder.pos(-beamwidth + onePartWidth * 2.0F, scaleY, -0.04F * zScale + offsetZ)
            .tex(0.6666667F, posBeamBigHigher)
            .endVertex();
         bufferbuilder.pos(beamwidth, scaleY, -0.2F * zScale + offsetZ).tex(1.0, posBeamBigHigher).endVertex();
         bufferbuilder.pos(beamwidth, -scaleY, -0.2F * zScale + offsetZ).tex(1.0, posBeamSmallHigher).endVertex();
         tessellator.draw();
         GL11.glDepthMask(true);
         AbstractMobModel.returnlight();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }

      if (this.type == 18) {
         HostileProjectiles.KrakenSlime entityxxxxxx = (HostileProjectiles.KrakenSlime)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityTo.height / 2.0F, (float)z);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(AnimationTimer.tick, 0.0F, 1.0F, 0.0F);
         float sclx = 2.0F;
         GlStateManager.scale(sclx, sclx, sclx);
         this.bindTexture(gloss3);
         GlStateManager.enableBlend();
         AbstractMobModel.light(210, false);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_COLOR);
         sphere4.renderScaledtexture();
         AbstractMobModel.returnlight();
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }

      if (this.type == 19) {
         EntitySpellForgeCatalyst entityxxxxxx = (EntitySpellForgeCatalyst)entityTo;
         GlStateManager.pushMatrix();
         float bob = (SpellForgeTESR.getBob(partialTicks, 0, 0.06F) + 0.25F) * (8.0F - entityxxxxxx.spendTick) / 8.0F;
         GlStateManager.translate((float)x, (float)y + bob, (float)z);
         ItemStack stack0 = entityxxxxxx.getItem();
         if (!stack0.isEmpty()) {
            GlStateManager.rotate(SpellForgeTESR.getRotate(partialTicks, 0), 0.0F, 1.0F, 0.0F);
            SpellForgeTESR.renderItemMolten(stack0, MathHelper.clamp(entityxxxxxx.heatClient / 100.0F, 0.0F, 1.0F));
         }

         GlStateManager.popMatrix();
      }

      if (this.type == 20) {
         double cameraX = 0.0;
         double cameraY = 0.0;
         double cameraZ = 0.0;
         Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
         if (rvEntity != null) {
            cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
            cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
            cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
         }

         CoralPolyp entityxxxxxx = (CoralPolyp)entityTo;
         GlStateManager.pushMatrix();
         AbstractMobModel.light(180, false);
         GlStateManager.enableBlend();
         this.bindTexture(this.tex);
         if (entityxxxxxx.segmentPoses != null) {
            float halfLength = entityxxxxxx.segmentPoses.length / 2.0F;
            float scalePrev = 0.05F;
            float scaleAdding = -0.030000001F / halfLength;

            for (int i = 0; i < entityxxxxxx.segmentPoses.length - 1; i++) {
               Vec3d point1 = entityxxxxxx.segmentPoses[i + 1];
               Vec3d point2 = entityxxxxxx.segmentPoses[i];
               Vec3d point1Prev = entityxxxxxx.segmentPosesPrev[i + 1];
               Vec3d point2Prev = entityxxxxxx.segmentPosesPrev[i];
               double pX1 = GetMOP.partial(point1.x, point1Prev.x, (double)partialTicks);
               double pY1 = GetMOP.partial(point1.y, point1Prev.y, (double)partialTicks);
               double pZ1 = GetMOP.partial(point1.z, point1Prev.z, (double)partialTicks);
               double pX2 = GetMOP.partial(point2.x, point2Prev.x, (double)partialTicks);
               double pY2 = GetMOP.partial(point2.y, point2Prev.y, (double)partialTicks);
               double pZ2 = GetMOP.partial(point2.z, point2Prev.z, (double)partialTicks);
               float mx = (float)(pX1 - pX2);
               float mz = (float)(pZ1 - pZ2);
               float my = (float)(pY2 - pY1);
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

               float rotatYaw = angle_Yaw + 180.0F;
               float rotatPitch = -angle;
               double distTo = point1.distanceTo(point2);
               RenderTentacles.renderSegment(
                  distTo,
                  distTo / 14.0,
                  i > halfLength ? (scalePrev += scaleAdding) : scalePrev,
                  scalePrev,
                  pX1 - cameraX,
                  pY1 - cameraY,
                  pZ1 - cameraZ,
                  rotatPitch,
                  rotatYaw
               );
            }
         }

         AbstractMobModel.returnlight();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      if (this.type == 21) {
         HostileProjectiles.HorribleEmeraldShoot entityxxxxxx = (HostileProjectiles.HorribleEmeraldShoot)entityTo;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + entityxxxxxx.height / 2.0F, (float)z);
         this.bindTexture(this.tex);
         modelEmeraldShoot.render(entityxxxxxx, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.popMatrix();
      }
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.tex;
   }

   public static class RenderSpecialFactory implements IRenderFactory {
      public ResourceLocation tex;
      public final int type;

      public RenderSpecialFactory(int type, ResourceLocation tex) {
         this.type = type;
         this.tex = tex;
      }

      public Render createRenderFor(RenderManager manager) {
         return new RenderSpecial(manager, this.type, this.tex);
      }
   }
}

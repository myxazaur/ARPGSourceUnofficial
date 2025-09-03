//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.tileentity.TileRunicMirror;
import java.util.HashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Deprecated
public abstract class Seal {
   public static HashMap<String, Seal> sealRegistry = new HashMap<>();
   public static HashMap<Integer, Seal> sealById = new HashMap<>();
   public static Random rand = new Random();
   public static Seal SEAL_OF_SIN = new SealOfSin();
   public static Seal SEAL_OF_FIRE = new SealOfFire();
   public static Seal SEAL_OF_CURSE = new SealOfCurse();
   public static Seal SEAL_OF_POISON = new SealOfPoison();
   public static Seal SEAL_OF_COLD = new SealOfCold();
   public static Seal SEAL_OF_DEEP = new SealOfDeep();
   public static Seal SEAL_OF_WATER = new SealOfWater();
   public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public static ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
   public static ResourceLocation ice_beam = new ResourceLocation("arpg:textures/ice_beam.png");
   public static ResourceLocation snowflake2 = new ResourceLocation("arpg:textures/snowflake2.png");
   public static ResourceLocation acid_splash5 = new ResourceLocation("arpg:textures/acid_splash5.png");
   public static ResourceLocation acid_splash2 = new ResourceLocation("arpg:textures/acid_splash2.png");
   public static ResourceLocation acid_splash3 = new ResourceLocation("arpg:textures/acid_splash3.png");
   public static ResourceLocation acid_splash6 = new ResourceLocation("arpg:textures/acid_splash6.png");
   public static ResourceLocation ice_cube = new ResourceLocation("arpg:textures/ice_cube.png");
   public static ResourceLocation water_bright = new ResourceLocation("arpg:textures/water_bright.png");
   public static ResourceLocation water_beam = new ResourceLocation("arpg:textures/water_beam.png");
   public static ResourceLocation[] splashes = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/splash1.png"), new ResourceLocation("arpg:textures/splash2.png"), new ResourceLocation("arpg:textures/splash3.png")
   };
   public static ParticleTracker.TrackerChangeTexOnDrop tctod = new ParticleTracker.TrackerChangeTexOnDrop(acid_splash2, true, false);
   public String name;
   public ShardType energyType;
   public float energyNeed;
   public int expCount = 85;
   public int dustCount = 1;
   public ResourceLocation texture;
   public int id = 0;
   public int stableTime = 150;
   public String[] description;
   public float colorR = 1.0F;
   public float colorG = 1.0F;
   public float colorB = 1.0F;

   public Seal(String name, ShardType energyType, float energyNeed, ResourceLocation texture, int id) {
      this.name = name;
      this.energyType = energyType;
      this.energyNeed = energyNeed;
      this.texture = texture;
      this.id = id;
   }

   public void onMirrorTick(TileRunicMirror mirror) {
   }

   public boolean evil(World world, double x, double y, double z, float power, float range, float chaos, Vec3d flowVector, boolean mighty) {
      return false;
   }

   public void renderInTileWithRotation(
      double x, double y, double z, float partialTicks, float rotationPitch, float rotationYaw, float serverPitch, float serverYaw, TileRunicMirror mirror
   ) {
   }

   public BlockPos calculateRandpos(World world, double x, double y, double z, float power, float range, float chaos, Vec3d flowVector) {
      float order = 1.0F - chaos;
      BlockPos poss = new BlockPos(x, y, z);
      double displXchaos = rand.nextGaussian() * chaos * range;
      double displYchaos = rand.nextGaussian() * chaos * range;
      double displZchaos = rand.nextGaussian() * chaos * range;
      int angleOrder = (int)(world.getTotalWorldTime() % 24000L);
      double displaceYOrder = MathHelper.sin((float)world.getTotalWorldTime() / 16.0F);
      double multipledRange = range * (1.0 - Math.abs(displaceYOrder));
      double displYorder = displaceYOrder * range * order;
      double displXorder = MathHelper.cos(angleOrder) * order * multipledRange;
      double displZorder = MathHelper.sin(angleOrder) * order * multipledRange;
      double flowX = flowVector.x * rand.nextFloat() * chaos + flowVector.x * order;
      double flowY = flowVector.y * rand.nextFloat() * chaos + flowVector.y * order;
      double flowZ = flowVector.z * rand.nextFloat() * chaos + flowVector.z * order;
      return poss.add(displXchaos + displXorder + flowX, displYchaos + displYorder + flowY, displZchaos + displZorder + flowZ);
   }

   public static void init() {
      registerSeal(SEAL_OF_SIN);
      registerSeal(SEAL_OF_FIRE);
      registerSeal(SEAL_OF_CURSE);
      registerSeal(SEAL_OF_POISON);
      registerSeal(SEAL_OF_COLD);
      registerSeal(SEAL_OF_DEEP);
      registerSeal(SEAL_OF_WATER);
   }

   public static void registerSeal(Seal seal) {
      if (!sealRegistry.containsKey(seal.name)) {
         sealRegistry.put(seal.name, seal);
         sealById.put(seal.id, seal);
      }
   }

   @Nullable
   public static Seal byId(int id) {
      return sealById.get(id);
   }

   public static class SealOfCold extends Seal {
      public SealOfCold() {
         super("cold", ShardType.COLD, 5.0F, new ResourceLocation("arpg:textures/seals/cold.png"), 5);
         this.dustCount = 4;
         this.stableTime = 300;
         this.description = new String[]{"Cold", "Frost", "Winter"};
         this.colorR = 0.6F;
         this.colorG = 0.75F;
         this.colorB = 1.0F;
      }
   }

   public static class SealOfCurse extends Seal {
      public SealOfCurse() {
         super("curse", ShardType.COLD, 4.0F, new ResourceLocation("arpg:textures/seals/curse.png"), 3);
         this.dustCount = 20;
         this.stableTime = 150;
         this.description = new String[]{"Curse", "Fate", "Burden"};
         this.colorR = 0.4F;
         this.colorG = 0.9F;
         this.colorB = 1.0F;
      }

      @Override
      public void renderInTileWithRotation(
         double x, double y, double z, float partialTicks, float rotationPitch, float rotationYaw, float serverPitch, float serverYaw, TileRunicMirror mirror
      ) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(rotationYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0, 0.5, 0.0);
         GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0, -0.5, 0.0);
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableAlpha();
         GlStateManager.disableCull();
         GlStateManager.depthMask(false);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         double zLength = -0.8;
         float R2 = 0.2F;
         float G2 = 0.8F;
         float B2 = 0.5F;
         bufferbuilder.pos(-0.15, 0.35, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.2, 0.3, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.2, 0.3, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.15, 0.35, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.15, 0.65, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.2, 0.7, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.2, 0.7, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.15, 0.65, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.15, 0.35, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.2, 0.3, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(-0.2, 0.7, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(-0.15, 0.65, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.15, 0.35, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.2, 0.3, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.2, 0.7, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.15, 0.65, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         zLength = -0.4;
         bufferbuilder.pos(-0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(-0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.3, 0.2, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.3, 0.8, zLength).color(R2, G2, B2, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         tessellator.draw();
         GlStateManager.translate(0.15F, 1.57F, 0.0F);
         float alpha = 1.0F;
         if (Math.abs(rotationYaw - serverYaw) < 2.0F && Math.abs(rotationPitch - serverPitch) < 2.0F) {
            for (int i = 0; i < 10; i++) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(-0.15, -1.064, 0.0);
               GlStateManager.rotate(70 * i - AnimationTimer.tick * 2, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(0.15, 1.064, 0.0);
               float zz = -0.4F * i;
               float yy = -0.85F;
               bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
               bufferbuilder.pos(0.0, yy, 0.0 + zz).color(this.colorR, this.colorG, this.colorB, alpha).endVertex();
               bufferbuilder.pos(0.0, yy, -0.3 + zz).color(R2, G2, B2, 0.0F).endVertex();
               alpha = (float)(alpha - 0.1);
               bufferbuilder.pos(-0.3, yy, -0.7 + zz).color(R2, G2, B2, 0.0F).endVertex();
               bufferbuilder.pos(-0.3, yy, -0.4 + zz).color(this.colorR, this.colorG, this.colorB, alpha).endVertex();
               tessellator.draw();
               GlStateManager.popMatrix();
            }
         }

         GlStateManager.depthMask(true);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.popMatrix();
      }
   }

   public static class SealOfDeep extends Seal {
      public SealOfDeep() {
         super("deep", ShardType.POISON, 7.0F, new ResourceLocation("arpg:textures/seals/deep.png"), 6);
         this.dustCount = 6;
         this.stableTime = 140;
         this.description = new String[]{"Deepness", "Chasm", "Abyss"};
         this.colorR = 0.1F;
         this.colorG = 0.0F;
         this.colorB = 0.2F;
      }
   }

   public static class SealOfFire extends Seal {
      public SealOfFire() {
         super("fire", ShardType.FIRE, 5.0F, new ResourceLocation("arpg:textures/mui/fire.png"), 2);
         this.dustCount = 2;
         this.stableTime = 190;
         this.description = new String[]{"Fire", "Flame", "Heat"};
         this.colorR = 1.0F;
         this.colorG = 0.8F;
         this.colorB = 0.2F;
      }

      @Override
      public boolean evil(World world, double x, double y, double z, float power, float range, float chaos, Vec3d flowVector, boolean mighty) {
         BlockPos randpos = this.calculateRandpos(world, x, y, z, power, range, chaos, flowVector);
         if (power >= 1.0F && Blocks.FIRE.canPlaceBlockAt(world, randpos) && world.isAirBlock(randpos)) {
            world.playSound((EntityPlayer)null, randpos, Sounds.fire, SoundCategory.BLOCKS, range * 0.3F, rand.nextFloat() * 0.4F + 0.8F);
            world.setBlockState(randpos, Blocks.FIRE.getDefaultState(), 11);
            return true;
         } else {
            boolean fires = false;
            if (power >= 3.0F) {
               for (Entity entity : GetMOP.getEntitiesInAABBof(world, randpos, (double)(range / 2.0F), null)) {
                  entity.setFire((int)power);
                  entity.attackEntityFrom(DamageSource.IN_FIRE, power);
                  fires = true;
               }
            } else if (power >= 2.0F) {
               for (Entity entity : GetMOP.getEntitiesInAABBof(world, randpos, (double)(range / 2.0F), null)) {
                  entity.setFire((int)power);
                  fires = true;
               }
            }

            if (power >= 4.0F) {
               Explosion explosion = new Explosion(
                  world, null, randpos.getX(), randpos.getY(), randpos.getZ(), power - 3.0F, true, true
               );
               explosion.doExplosionA();
               explosion.doExplosionB(true);
               return true;
            } else {
               return fires;
            }
         }
      }
   }

   public static class SealOfPoison extends Seal {
      public SealOfPoison() {
         super("poison", ShardType.POISON, 8.0F, new ResourceLocation("arpg:textures/seals/poison.png"), 4);
         this.dustCount = 4;
         this.stableTime = 280;
         this.description = new String[]{"Poison", "Toxin", "Venom"};
         this.colorR = 0.5F;
         this.colorG = 1.0F;
         this.colorB = 0.1F;
      }
   }

   public static class SealOfSin extends Seal {
      public SealOfSin() {
         super("sin", ShardType.FIRE, 6.0F, new ResourceLocation("arpg:textures/seals/sin.png"), 1);
         this.dustCount = 2;
         this.stableTime = 250;
         this.description = new String[]{"Sin", "Vice"};
         this.colorR = 0.9F;
         this.colorG = 0.3F;
         this.colorB = 0.5F;
      }

      @Override
      public void renderInTileWithRotation(
         double x, double y, double z, float partialTicks, float rotationPitch, float rotationYaw, float serverPitch, float serverYaw, TileRunicMirror mirror
      ) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(rotationYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0, 0.5, 0.0);
         GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0, -0.5, 0.0);
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableTexture2D();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.disableAlpha();
         GlStateManager.disableCull();
         GlStateManager.depthMask(false);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         double zLength = -0.7;
         bufferbuilder.pos(-0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.25, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.25, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(-0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         bufferbuilder.pos(0.25, 0.25, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, zLength).color(0.5F, 0.1F, 0.9F, 0.0F).endVertex();
         bufferbuilder.pos(0.25, 0.75, 0.0).color(this.colorR, this.colorG, this.colorB, 1.0F).endVertex();
         tessellator.draw();
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.popMatrix();
      }
   }

   public static class SealOfWater extends Seal {
      public SealOfWater() {
         super("water", ShardType.COLD, 7.0F, new ResourceLocation("arpg:textures/seals/water.png"), 7);
         this.dustCount = 8;
         this.stableTime = 220;
         this.description = new String[]{"Water", "Sea", "Ocean"};
         this.colorR = 0.3F;
         this.colorG = 0.9F;
         this.colorB = 0.8F;
      }

      @Override
      public void renderInTileWithRotation(
         double x, double y, double z, float partialTicks, float rotationPitch, float rotationYaw, float serverPitch, float serverYaw, TileRunicMirror mirror
      ) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(rotationYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0, 0.5, 0.0);
         GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0, -0.5, 0.0);
         float lbX = OpenGlHelper.lastBrightnessX;
         float lbY = OpenGlHelper.lastBrightnessY;
         Minecraft.getMinecraft().renderEngine.bindTexture(water_bright);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 220.0F, 220.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.shadeModel(7425);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.disableCull();
         GlStateManager.depthMask(false);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         double zLength = -1.2;
         float R2 = 0.2F;
         float G2 = 0.6F;
         float B2 = 0.9F;
         float xDisp = 0.15F;
         float yUp = 0.35F;
         float yDown = 0.65F;
         float xDispAdd = -0.1F;
         float yDispAdd = -0.1F;
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
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(1.0, texYAdd).color(this.colorR, colorG1, colorB1, 1.0F).endVertex();
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
         xDispAdd = -0.05F;
         yDispAdd = -0.05F;
         zLength = -0.7;
         xDisp = 0.25F;
         yUp = 0.25F;
         yDown = 0.75F;
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
         bufferbuilder.pos(-xDisp, yDown, 0.0).tex(1.0, texYAdd).color(this.colorR, colorG1, colorB1, 1.0F).endVertex();
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
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.shadeModel(7424);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableAlpha();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.popMatrix();
      }
   }
}

package com.vivern.arpg.recipes;

import com.vivern.arpg.renders.RenderTerraformingResearch;
import com.vivern.arpg.renders.TRRenderer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Phenomenons {
   private static HashMap<Integer, Integer> interflowBitById = new HashMap<>();
   private static HashMap<Integer, Integer> idByInterflowBit = new HashMap<>();
   public static HashMap<Integer, TerraformingResearchSurface> tfrSurfaceRegister = new HashMap<>();
   static boolean fill = true;
   public static ResourceLocation tfr_sprites = new ResourceLocation("arpg:textures/gui_tf_research_sprites.png");
   public static ResourceLocation tfr_elements = new ResourceLocation("arpg:textures/gui_research_table_elements.png");
   public static int tfr_sprites_sizeX = 2048;
   public static int tfr_sprites_sizeY = 2048;
   public static TRRenderer.TRRendererSprite phenomenonsRender = new TRRenderer.TRRendererSprite(tfr_elements, 0, 0, 42, 42, 768, 768);
   public static TerraformingResearchSurface surfaceFire = new TerraformingResearchSurface(
         1, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 350, 250, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererSpriteGlimmer(tfr_sprites, 350, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 30.0F)
            .setStyle(TRRenderer.RenderStyle.ADDITIVE),
         new TRRenderer.TRRendererScatter(tfr_sprites, 2, 8, 400, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 20)
            .setAnimation(4, 20)
            .setLayer(1.0F)
            .setStyle(TRRenderer.RenderStyle.ADDITIVE)
      );
   public static TerraformingResearchSurface surfaceRocks = new TerraformingResearchSurface(
         2, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 0, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 4, 5, 0, 250, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 22).setRandomBrightness(0.1F).setLayer(1.0F)
      );
   public static TerraformingResearchSurface surfaceOcean = new TerraformingResearchSurface(
         3, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 600, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setWoodenFrame()
            .setAnimation(6, 20)
            .setDiffuseColor(3104155, false)
      );
   public static TerraformingResearchSurface surfaceAir = new TerraformingResearchSurface(
         4, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 200, 250, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setAtmosphereEffect()
            .setDiffuseColor(1999615, true)
            .setLayer(2.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      )
      .setParticles(
         new TerraformingResearchParticle.TFRSParticleSystem() {
            @Override
            public void update(RenderTerraformingResearch renderTFR, float x, float y, TerraformingResearchSurface surface, Random rand, int arrayX, int arrayY) {
               if (rand.nextFloat() < 0.01F) {
                  float displaceX = (rand.nextFloat() - 0.5F) * 50.0F;
                  float displaceY = (rand.nextFloat() - 0.5F) * 50.0F;
                  boolean small = rand.nextFloat() < 0.5F;
                  TerraformingResearchParticle part = new TerraformingResearchParticle(
                     small ? 297 : 314,
                     small ? 250 + rand.nextInt(6) * 6 : 250 + rand.nextInt(6) * 15,
                     small ? 17 : 36,
                     small ? 6 : 15,
                     Phenomenons.tfr_sprites,
                     Phenomenons.tfr_sprites_sizeX,
                     Phenomenons.tfr_sprites_sizeY
                  );
                  part.livetimePosMotion(120, x + displaceX, y + displaceY, rand.nextFloat() < 0.5F ? -0.3F : 0.3F, 0.0F, 1.0F);
                  part.alpha(0.0F, new Vec3d(0.0, 20.0, 0.05), new Vec3d(100.0, 120.0, -0.05));
                  part.renderStyle(TRRenderer.RenderStyle.TRANSLUCENT);
                  renderTFR.spawnParticle(part);
               }
            }
         }
      );
   public static TerraformingResearchSurface surfacePoison = new TerraformingResearchSurface(
         5, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(new TRRenderer.TRRendererSprite(tfr_sprites, 700, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setAnimation(6, 20));
   public static TerraformingResearchSurface surfaceLava = new TerraformingResearchSurface(
         19, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 650, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setWoodenFrame()
            .setAnimation(7, 20)
            .setDiffuseColor(16752800, false),
         new TRRenderer.TRRendererSprite(tfr_sprites, 0, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setLayer(1.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT),
         new TRRenderer.TRRendererScatter(tfr_sprites, 4, 3, 0, 300, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 22).setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceSteam = new TerraformingResearchSurface(
         20, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE, 50, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 850, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setAtmosphereEffect()
            .setAnimation(5, 20)
            .setLayer(2.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      );
   public static TerraformingResearchSurface surfaceRain = new TerraformingResearchSurface(
         21, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE, 0, 0, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 1000, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setAnimation(3, 20)
            .setLayer(2.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      )
      .setParticles(
         new TerraformingResearchParticle.TFRSParticleSystem() {
            @Override
            public void update(RenderTerraformingResearch renderTFR, float x, float y, TerraformingResearchSurface surface, Random rand, int arrayX, int arrayY) {
               if (rand.nextFloat() < 0.03F) {
                  float displaceX = (rand.nextFloat() - 0.5F) * 50.0F;
                  float displaceY = (rand.nextFloat() - 0.5F) * 50.0F;
                  boolean small = rand.nextFloat() < 0.5F;
                  TerraformingResearchParticle part = new TerraformingResearchParticle(
                     small ? 297 : 314,
                     small ? 250 + rand.nextInt(6) * 6 : 250 + rand.nextInt(6) * 15,
                     small ? 17 : 36,
                     small ? 6 : 15,
                     Phenomenons.tfr_sprites,
                     Phenomenons.tfr_sprites_sizeX,
                     Phenomenons.tfr_sprites_sizeY
                  );
                  part.livetimePosMotion(120, x + displaceX, y + displaceY, rand.nextFloat() < 0.5F ? -0.3F : 0.3F, 0.0F, 1.0F);
                  part.alpha(0.0F, new Vec3d(0.0, 20.0, 0.05), new Vec3d(100.0, 120.0, -0.05));
                  part.renderStyle(TRRenderer.RenderStyle.TRANSLUCENT);
                  renderTFR.spawnParticle(part);
               }
            }
         }
      );
   public static TerraformingResearchSurface surfaceSalts = new TerraformingResearchSurface(
         22, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 200, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 7, 2, 0, 382, 34, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 18).setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceBurningFrost = new TerraformingResearchSurface(
         23, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 0, 0, 50, 50, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 150, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 2, 7, 500, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 20)
            .setAnimation(5, 20)
            .setLayer(1.0F)
            .setStyle(TRRenderer.RenderStyle.ADDITIVE)
      );
   public static TerraformingResearchSurface surfaceBlood = new TerraformingResearchSurface(
         30, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 50, 0, 0, 0, 0, 0, 0, 50, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 750, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setWoodenFrame()
            .setAnimation(7, 20)
            .setDiffuseColor(8487297, false)
      );
   public static TerraformingResearchSurface surfacePotion = new TerraformingResearchSurface(
         36, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 50, 0, 50, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 800, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setWoodenFrame()
            .setAnimation(6, 20)
            .setDiffuseColor(16773240, false)
      );
   public static TerraformingResearchSurface surfaceCrystal = new TerraformingResearchSurface(
         37, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 50, 0, 0, 50, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 50, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setDiffuseColor(16763135, true),
         new TRRenderer.TRRendererSprite(tfr_sprites, 100, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(10878909, false),
         new TRRenderer.TRRendererScatter(tfr_sprites, 7, 2, 0, 546, 34, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 18)
            .setRandomBrightness(0.25F)
            .setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceAsh = new TerraformingResearchSurface(
         40, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 950, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setWoodenFrame()
            .setAnimation(6, 20)
            .setDiffuseColor(13092807, false)
      )
      .setParticles(
         new TerraformingResearchParticle.TFRSParticleSystem() {
            @Override
            public void update(RenderTerraformingResearch renderTFR, float x, float y, TerraformingResearchSurface surface, Random rand, int arrayX, int arrayY) {
               if (rand.nextFloat() < 0.13F) {
                  float displaceX = (rand.nextFloat() - 0.5F) * 50.0F;
                  float displaceY = (rand.nextFloat() - 0.5F) * 50.0F;
                  TerraformingResearchParticle part = new TerraformingResearchParticle(
                     350, 300, 1, 1, Phenomenons.tfr_sprites, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
                  );
                  part.livetimePosMotion(
                     120 - rand.nextInt(30), x + displaceX, y + displaceY, 0.3F + rand.nextFloat() * 0.15F, -0.3F - rand.nextFloat() * 0.15F, 0.99F
                  );
                  part.renderStyle(TRRenderer.RenderStyle.NORMAL);
                  part.animation(2, 60);
                  part.tracker = TerraformingResearchParticle.tfrp_tracker_sparkle;
                  renderTFR.spawnParticle(part);
               }
            }
         }
      );
   public static TerraformingResearchSurface surfaceMud = new TerraformingResearchSurface(
         43, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 150, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(6967097, false),
         new TRRenderer.TRRendererSprite(tfr_sprites, 100, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setLayer(0.1F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      );
   public static TerraformingResearchSurface surfacePoisonGas = new TerraformingResearchSurface(
         45, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE, 0, 0, 0, 50, 50, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 900, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setAtmosphereEffect()
            .setAnimation(4, 20)
            .setLayer(2.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      );
   public static TerraformingResearchSurface surfaceIce = new TerraformingResearchSurface(
         47, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 50, 0, 0, 50, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 250, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(8694783, false),
         new TRRenderer.TRRendererScatter(tfr_sprites, 3, 1, 200, 314, 32, 36, tfr_sprites_sizeX, tfr_sprites_sizeY, 16).setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceDust = new TerraformingResearchSurface(
         60, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 200, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(13487565, false)
      );
   public static TerraformingResearchSurface surfaceBones = new TerraformingResearchSurface(
         32, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 250, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 12, 5, 0, 510, 26, 36, tfr_sprites_sizeX, tfr_sprites_sizeY, 14).setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceMetal = new TerraformingResearchSurface(
         34, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 300, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(5197647, false)
      );
   public static TerraformingResearchSurface surfaceMoney = new TerraformingResearchSurface(
         56, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 50, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 300, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 8, 4, 0, 350, 28, 32, tfr_sprites_sizeX, tfr_sprites_sizeY, 13).setLayer(2.0F)
      );
   public static TerraformingResearchSurface surfaceCorruption = new TerraformingResearchSurface(
         51, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 0, 0, 50, 0, 0, 0, 0, 50, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 350, 150, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame(),
         new TRRenderer.TRRendererScatter(tfr_sprites, 12, 5, 0, 460, 26, 50, tfr_sprites_sizeX, tfr_sprites_sizeY, 10).setLayer(2.0F)
      )
      .setExpansion(5, false);
   public static TerraformingResearchSurface surfaceStorm = new TerraformingResearchSurface(
         74, TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE, 0, 0, 50, 50, 0, 0, 50, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 1000, 0, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY)
            .setAnimation(3, 20)
            .setLayer(2.0F)
            .setStyle(TRRenderer.RenderStyle.TRANSLUCENT)
      )
      .setParticles(
         new TerraformingResearchParticle.TFRSParticleSystem() {
            @Override
            public void update(RenderTerraformingResearch renderTFR, float x, float y, TerraformingResearchSurface surface, Random rand, int arrayX, int arrayY) {
               if (rand.nextFloat() < 0.03F) {
                  float displaceX = (rand.nextFloat() - 0.5F) * 50.0F;
                  float displaceY = (rand.nextFloat() - 0.5F) * 50.0F;
                  boolean small = rand.nextFloat() < 0.5F;
                  TerraformingResearchParticle part = new TerraformingResearchParticle(
                     small ? 0 : 17 + rand.nextInt(6) * 36,
                     small ? 600 + rand.nextInt(6) * 6 : 600,
                     small ? 17 : 36,
                     small ? 6 : 15,
                     Phenomenons.tfr_sprites,
                     Phenomenons.tfr_sprites_sizeX,
                     Phenomenons.tfr_sprites_sizeY
                  );
                  part.livetimePosMotion(120, x + displaceX, y + displaceY, rand.nextFloat() < 0.5F ? -0.3F : 0.3F, 0.0F, 1.0F);
                  part.alpha(0.0F, new Vec3d(0.0, 20.0, 0.05), new Vec3d(100.0, 120.0, -0.05));
                  part.renderStyle(TRRenderer.RenderStyle.TRANSLUCENT);
                  if (!small) {
                     part.animation(2, 3);
                     part.frameController = new TerraformingResearchParticle.FrameControllerStorm();
                  }

                  renderTFR.spawnParticle(part);
               }
            }
         }
      );
   public static TerraformingResearchSurface surfaceSnow = new TerraformingResearchSurface(
         102, TerraformingResearchSurface.TRSurfaceType.TERRAIN, 0, 0, 50, 50, 0, 50, 0, 0, 0, 0, 0, 0
      )
      .setRenders(
         new TRRenderer.TRRendererSprite(tfr_sprites, 0, 200, 50, 50, tfr_sprites_sizeX, tfr_sprites_sizeY).setWoodenFrame().setDiffuseColor(13228262, false),
         new TRRenderer.TRRendererScatter(tfr_sprites, 3, 2, 0, 432, 45, 28, tfr_sprites_sizeX, tfr_sprites_sizeY, 24).setLayer(2.0F)
      )
      .setParticles(
         new TerraformingResearchParticle.TFRSParticleSystem() {
            @Override
            public void update(RenderTerraformingResearch renderTFR, float x, float y, TerraformingResearchSurface surface, Random rand, int arrayX, int arrayY) {
               if (rand.nextFloat() < 0.1F) {
                  float displaceX = (rand.nextFloat() - 0.5F) * 55.0F;
                  float displaceY = (rand.nextFloat() - 0.5F) * 50.0F;
                  boolean small = rand.nextFloat() < 0.5F;
                  TerraformingResearchParticle part = new TerraformingResearchParticle(
                     small ? 253 : 250, 250, 3, 3, Phenomenons.tfr_sprites, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
                  );
                  part.livetimePosMotion(150, x + displaceX, y + displaceY - 50.0F, rand.nextFloat() / 2.0F - 0.25F, 0.3F, 1.0F);
                  part.alpha(0.0F, new Vec3d(0.0, 20.0, 0.05), new Vec3d(145.0, 150.0, -0.2));
                  part.renderStyle(TRRenderer.RenderStyle.TRANSLUCENT);
                  part.tracker = TerraformingResearchParticle.tfrp_tracker_snowfall;
                  part.utilsValue = rand.nextInt(2);
                  renderTFR.spawnParticle(part);
               }
            }
         }
      );

   @Nullable
   public static Phenomenon createById(int id) {
      if (id == 1) {
         return new PhenomenFire();
      } else if (id == 2) {
         return new PhenomenEarth();
      } else if (id == 3) {
         return new PhenomenWater();
      } else if (id == 4) {
         return new PhenomenAir();
      } else if (id == 5) {
         return new PhenomenPoison();
      } else if (id == 6) {
         return new PhenomenCold();
      } else if (id == 7) {
         return new PhenomenElectric();
      } else if (id == 8) {
         return new PhenomenVoid();
      } else if (id == 9) {
         return new PhenomenPleasure();
      } else if (id == 10) {
         return new PhenomenPain();
      } else if (id == 11) {
         return new PhenomenDeath();
      } else if (id == 12) {
         return new PhenomenLive();
      } else if (id == 19) {
         return new PhenomenLava();
      } else if (id == 20) {
         return new PhenomenSteam();
      } else if (id == 21) {
         return new PhenomenRain();
      } else if (id == 22) {
         return new PhenomenSalts();
      } else if (id == 23) {
         return new PhenomenBurningFrost();
      } else if (id == 24) {
         return new PhenomenLightning();
      } else if (id == 25) {
         return new PhenomenFlow();
      } else if (id == 28) {
         return new PhenomenAnnihilation();
      } else if (id == 30) {
         return new PhenomenBlood();
      } else if (id == 32) {
         return new PhenomenBones();
      } else if (id == 34) {
         return new PhenomenMetal();
      } else if (id == 36) {
         return new PhenomenPotion();
      } else if (id == 37) {
         return new PhenomenCrystal();
      } else if (id == 38) {
         return new PhenomenGenocide();
      } else if (id == 40) {
         return new PhenomenAsh();
      } else if (id == 43) {
         return new PhenomenMud();
      } else if (id == 45) {
         return new PhenomenPoisonGas();
      } else if (id == 47) {
         return new PhenomenIce();
      } else if (id == 51) {
         return new PhenomenCorruption();
      } else if (id == 56) {
         return new PhenomenRich();
      } else if (id == 57) {
         return new PhenomenGreed();
      } else if (id == 59) {
         return new PhenomenWeapon();
      } else if (id == 60) {
         return new PhenomenDust();
      } else if (id == 74) {
         return new PhenomenStorm();
      } else if (id == 99) {
         return new PhenomenPurity();
      } else if (id == 102) {
         return new PhenomenSnow();
      } else if (id == 103) {
         return new PhenomenWormhole();
      } else if (id == 104) {
         return new PhenomenExplosion();
      } else {
         return id == 106 ? new PhenomenFlood() : null;
      }
   }

   public static void register() {
      fill = false;
      fillInterflowIdPair(1, 2048);
      fillInterflowIdPair(2, 1024);
      fillInterflowIdPair(3, 512);
      fillInterflowIdPair(4, 256);
      fillInterflowIdPair(5, 128);
      fillInterflowIdPair(6, 64);
      fillInterflowIdPair(7, 32);
      fillInterflowIdPair(8, 16);
      fillInterflowIdPair(9, 8);
      fillInterflowIdPair(10, 4);
      fillInterflowIdPair(11, 2);
      fillInterflowIdPair(12, 1);
      fillInterflowIdPair(19, 3072);
      fillInterflowIdPair(20, 2560);
      fillInterflowIdPair(21, 768);
      fillInterflowIdPair(22, 1152);
      fillInterflowIdPair(23, 192);
      fillInterflowIdPair(24, 288);
      fillInterflowIdPair(25, 544);
      fillInterflowIdPair(28, 18);
      fillInterflowIdPair(30, 516);
      fillInterflowIdPair(32, 1026);
      fillInterflowIdPair(34, 1056);
      fillInterflowIdPair(36, 640);
      fillInterflowIdPair(37, 1600);
      fillInterflowIdPair(38, 130);
      fillInterflowIdPair(40, 2050);
      fillInterflowIdPair(43, 1536);
      fillInterflowIdPair(45, 384);
      fillInterflowIdPair(47, 576);
      fillInterflowIdPair(51, 132);
      fillInterflowIdPair(56, 1032);
      fillInterflowIdPair(57, 1160);
      fillInterflowIdPair(59, 1028);
      fillInterflowIdPair(60, 1280);
      fillInterflowIdPair(74, 800);
      fillInterflowIdPair(99, 24);
      fillInterflowIdPair(102, 832);
      fillInterflowIdPair(103, 560);
      fillInterflowIdPair(104, 2052);
      fillInterflowIdPair(106, 514);

      try {
         Field[] fields = Phenomenons.class.getFields();

         for (Field field : fields) {
            if (field.getType() == TerraformingResearchSurface.class) {
               TerraformingResearchSurface surface = (TerraformingResearchSurface)field.get(null);
               tfrSurfaceRegister.put(surface.id, surface);
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   public static void fillInterflowIdPair(int id, int interflowBit) {
      interflowBitById.put(id, interflowBit);
      idByInterflowBit.put(interflowBit, id);
   }

   public static int getInterflowId(Phenomenon... phenomenons) {
      int allbits = 0;

      for (int i = 0; i < phenomenons.length; i++) {
         int interflowBit = getInterflowBit(phenomenons[i].getId());
         if ((allbits & interflowBit) > 0) {
            return 0;
         }

         allbits |= interflowBit;
      }

      return idByInterflowBit.getOrDefault(allbits, 0);
   }

   public static int getInterflowId(int... phenomenonsIds) {
      int allbits = 0;

      for (int i = 0; i < phenomenonsIds.length; i++) {
         int interflowBit = getInterflowBit(phenomenonsIds[i]);
         if ((allbits & interflowBit) > 0) {
            return 0;
         }

         allbits |= interflowBit;
      }

      return idByInterflowBit.getOrDefault(allbits, 0);
   }

   public static int getInterflowBit(int id) {
      return interflowBitById.getOrDefault(id, 0);
   }

   public static int getIdByBit(int interflowBit) {
      return idByInterflowBit.getOrDefault(interflowBit, 0);
   }

   public static class PhenomenAir extends Phenomenon {
      @Override
      public int getId() {
         return 4;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceAir);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceAir);
      }
   }

   public static class PhenomenAnnihilation extends Phenomenon {
      @Override
      public int getId() {
         return 28;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         super.clickOtherDuringSelected(puzzle, x, y);
         if (puzzle.checkBounds(x, y) && (x != this.posX || y != this.posY) && puzzle.board[x][y].phenomenon != null) {
            this.onUnselected(puzzle);
            puzzle.selected = null;
            puzzle.destroyPhenomenon(this.posX, this.posY);
            puzzle.destroyPhenomenon(x, y);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class PhenomenAsh extends Phenomenon {
      @Override
      public int getId() {
         return 40;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceAsh);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceAsh);
      }
   }

   public static class PhenomenBlood extends Phenomenon {
      @Override
      public int getId() {
         return 30;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBlood);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBlood);
      }
   }

   public static class PhenomenBones extends Phenomenon {
      @Override
      public int getId() {
         return 32;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBones);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBones);
      }
   }

   public static class PhenomenBurningFrost extends Phenomenon {
      @Override
      public int getId() {
         return 23;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBurningFrost);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceBurningFrost);
      }
   }

   public static class PhenomenCold extends Phenomenon {
      @Override
      public int getId() {
         return 6;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }
   }

   public static class PhenomenCorruption extends Phenomenon {
      @Override
      public int getId() {
         return 51;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 0), Phenomenons.surfaceCorruption);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 0), Phenomenons.surfaceCorruption);
      }
   }

   public static class PhenomenCrystal extends Phenomenon {
      @Override
      public int getId() {
         return 37;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, 1, Phenomenons.surfaceCrystal);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, 1, Phenomenons.surfaceCrystal);
      }
   }

   public static class PhenomenDeath extends Phenomenon {
      @Override
      public int getId() {
         return 11;
      }

      @Override
      public EnumClickPossible getUsePossibles() {
         return EnumClickPossible.PHENOMENON;
      }

      @Override
      public boolean use(TerraformingResearchPuzzle puzzle, int x, int y) {
         int idd = puzzle.board[x][y].phenomenon.getId();
         return idd != 8 && idd != 11 ? puzzle.destroyPhenomenon(x, y) : false;
      }

      @Override
      public int getBaseUseReach() {
         return 1;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }
   }

   public static class PhenomenDust extends Phenomenon {
      @Override
      public int getId() {
         return 60;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceDust);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceDust);
      }
   }

   public static class PhenomenEarth extends Phenomenon {
      @Override
      public int getId() {
         return 2;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceRocks);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceRocks);
      }
   }

   public static class PhenomenElectric extends Phenomenon {
      @Override
      public int getId() {
         return 7;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }

      @Override
      public void setFields(TerraformingResearchPuzzle puzzle, boolean[][][] fields) {
         setFieldsInRadius(puzzle, fields, this.posX, this.posY, this.getReachModified(puzzle, 1), 0);
      }

      @Override
      public void updateAnimation(TerraformingResearchPuzzle puzzle, RenderTerraformingResearch renderTFR, float x, float y, int arrayX, int arrayY) {
         if (renderTFR != null) {
            for (int i = 0; i < 2; i++) {
               float radius = this.getReachModified(puzzle, 1) + 0.5F;
               float tick = i == 0 ? puzzle.ticksExisted : puzzle.ticksExisted + 40;
               Vec2f pos1 = getParticlePosInQuad(tick % 80.0F / 80.0F, renderTFR.cellWidth * radius);
               TerraformingResearchParticle part = new TerraformingResearchParticle(
                  351, 300, 9, 9, Phenomenons.tfr_sprites, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
               );
               part.livetimePosMotion(
                  24,
                  x + pos1.x,
                  y + pos1.y,
                  0.2F - RenderTerraformingResearch.rand.nextFloat() * 0.4F,
                  0.2F - RenderTerraformingResearch.rand.nextFloat() * 0.4F,
                  0.99F
               );
               part.renderStyle(TRRenderer.RenderStyle.ADDITIVE);
               part.animation(2, 13);
               part.red = 0.3F + RenderTerraformingResearch.rand.nextFloat() * 0.15F;
               part.green = 0.75F + RenderTerraformingResearch.rand.nextFloat() * 0.1F;
               part.blue = 1.0F;
               renderTFR.spawnParticle(part);
            }
         }
      }
   }

   public static class PhenomenExplosion extends Phenomenon {
      @Override
      public int getId() {
         return 104;
      }

      @Override
      public int onStep(TerraformingResearchPuzzle puzzle) {
         super.onStep(puzzle);
         ArrayList<int[]> list = new ArrayList<>();
         int radius = this.getReachModified(puzzle, this.getBaseUseReach());

         for (int xx = -radius; xx <= radius; xx++) {
            for (int yy = -radius; yy <= radius; yy++) {
               int eX = this.posX + xx;
               int eY = this.posY + yy;
               if (puzzle.checkBounds(eX, eY)) {
                  TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.board[eX][eY];
                  if (cell.phenomenon != null && cell.phenomenon.getId() != 1) {
                     if ((Phenomenons.getInterflowBit(cell.phenomenon.getId()) & 2048) > 0) {
                        cell.phenomenon.onRemoved(puzzle);
                        cell.phenomenon = null;
                        list.add(new int[]{eX, eY});
                     } else {
                        cell.phenomenon.onRemoved(puzzle);
                        cell.phenomenon = null;
                     }
                  }
               }
            }
         }

         if (!list.isEmpty()) {
            for (int[] ints : list) {
               puzzle.addPhenomenon(ints[0], ints[1], 1, 12);
            }
         }

         puzzle.setSurfaces(this.posX, this.posY, radius, Phenomenons.surfaceFire);
         TerraformingResearchPuzzle.TerraformingResearchCell thiscell = puzzle.board[this.posX][this.posY];
         thiscell.phenomenon.onRemoved(puzzle);
         thiscell.phenomenon = null;
         return this.getDelayAfterStep(true);
      }

      @Override
      public int getBaseUseReach() {
         return 1;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }
   }

   public static class PhenomenFire extends Phenomenon {
      @Override
      public int getId() {
         return 1;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceFire);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceFire);
      }
   }

   public static class PhenomenFlood extends Phenomenon {
      @Override
      public int getId() {
         return 106;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.setSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 3), Phenomenons.surfaceOcean);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 3), Phenomenons.surfaceOcean);
      }
   }

   public static class PhenomenFlow extends Phenomenon {
      @Override
      public int getId() {
         return 25;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (puzzle.checkBounds(x, y)) {
            super.clickOtherDuringSelected(puzzle, x, y);
            if (x == this.posX ^ y == this.posY) {
               if (x > this.posX) {
                  boolean is = false;

                  for (int xx = this.posX + 1; xx < puzzle.width; xx++) {
                     if (puzzle.board[xx][this.posY].hasWays()) {
                        is = true;
                        break;
                     }
                  }

                  if (is) {
                     for (int xxx = this.posX + 1; xxx < puzzle.width; xxx++) {
                        if (puzzle.board[xxx][this.posY].hasWays()) {
                           puzzle.board[xxx][this.posY].wayLeft = true;
                           puzzle.board[xxx][this.posY].recalcWayRenderIndex();
                           puzzle.board[this.posX][this.posY].wayRight = true;
                           puzzle.board[this.posX][this.posY].recalcWayRenderIndex();
                           this.onUnselected(puzzle);
                           puzzle.selected = null;
                           puzzle.destroyPhenomenon(this.posX, this.posY);
                           return true;
                        }

                        puzzle.board[xxx][this.posY].wayLeft = true;
                        puzzle.board[xxx][this.posY].wayRight = true;
                        puzzle.board[xxx][this.posY].recalcWayRenderIndex();
                     }
                  }
               }

               if (x < this.posX) {
                  boolean is = false;

                  for (int xxx = this.posX - 1; xxx >= 0; xxx--) {
                     if (puzzle.board[xxx][this.posY].hasWays()) {
                        is = true;
                        break;
                     }
                  }

                  if (is) {
                     for (int xxxx = this.posX - 1; xxxx >= 0; xxxx--) {
                        if (puzzle.board[xxxx][this.posY].hasWays()) {
                           puzzle.board[xxxx][this.posY].wayRight = true;
                           puzzle.board[xxxx][this.posY].recalcWayRenderIndex();
                           puzzle.board[this.posX][this.posY].wayLeft = true;
                           puzzle.board[this.posX][this.posY].recalcWayRenderIndex();
                           this.onUnselected(puzzle);
                           puzzle.selected = null;
                           puzzle.destroyPhenomenon(this.posX, this.posY);
                           return true;
                        }

                        puzzle.board[xxxx][this.posY].wayLeft = true;
                        puzzle.board[xxxx][this.posY].wayRight = true;
                        puzzle.board[xxxx][this.posY].recalcWayRenderIndex();
                     }
                  }
               }

               if (y > this.posY) {
                  boolean is = false;

                  for (int yy = this.posY + 1; yy < puzzle.height; yy++) {
                     if (puzzle.board[this.posX][yy].hasWays()) {
                        is = true;
                        break;
                     }
                  }

                  if (is) {
                     for (int yyx = this.posY + 1; yyx < puzzle.height; yyx++) {
                        if (puzzle.board[this.posX][yyx].hasWays()) {
                           puzzle.board[this.posX][yyx].wayUp = true;
                           puzzle.board[this.posX][yyx].recalcWayRenderIndex();
                           puzzle.board[this.posX][this.posY].wayDown = true;
                           puzzle.board[this.posX][this.posY].recalcWayRenderIndex();
                           this.onUnselected(puzzle);
                           puzzle.selected = null;
                           puzzle.destroyPhenomenon(this.posX, this.posY);
                           return true;
                        }

                        puzzle.board[this.posX][yyx].wayDown = true;
                        puzzle.board[this.posX][yyx].wayUp = true;
                        puzzle.board[this.posX][yyx].recalcWayRenderIndex();
                     }
                  }
               }

               if (y < this.posY) {
                  boolean is = false;

                  for (int yyx = this.posY - 1; yyx >= 0; yyx--) {
                     if (puzzle.board[this.posX][yyx].hasWays()) {
                        is = true;
                        break;
                     }
                  }

                  if (is) {
                     for (int yyxx = this.posY - 1; yyxx >= 0; yyxx--) {
                        if (puzzle.board[this.posX][yyxx].hasWays()) {
                           puzzle.board[this.posX][yyxx].wayDown = true;
                           puzzle.board[this.posX][yyxx].recalcWayRenderIndex();
                           puzzle.board[this.posX][this.posY].wayUp = true;
                           puzzle.board[this.posX][this.posY].recalcWayRenderIndex();
                           this.onUnselected(puzzle);
                           puzzle.selected = null;
                           puzzle.destroyPhenomenon(this.posX, this.posY);
                           return true;
                        }

                        puzzle.board[this.posX][yyxx].wayDown = true;
                        puzzle.board[this.posX][yyxx].wayUp = true;
                        puzzle.board[this.posX][yyxx].recalcWayRenderIndex();
                     }
                  }
               }
            }
         }

         return false;
      }

      @Override
      public TerraformingPlayerCommand getGenerationCommand(TerraformingResearchPuzzle puzzle, Random rand) {
         if (rand.nextFloat() < 0.6F) {
            ArrayList<int[]> list = puzzle.getAllSelectableCells();
            int maxI = Math.min(list.size(), 8);

            for (int i = 0; i < maxI; i++) {
               int[] ints = list.get(rand.nextInt(list.size()));
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.cell(ints);
               if (cell.phenomenon != null && Phenomenons.getInterflowId(cell.phenomenon.getId(), 7) > 0) {
                  return new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, ints[0], ints[1], 0);
               }
            }
         }

         return super.getGenerationCommand(puzzle, rand);
      }
   }

   public static class PhenomenGenocide extends Phenomenon {
      @Override
      public int getId() {
         return 38;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         boolean oneKilled = false;
         if (puzzle.checkBounds(x, y)) {
            super.clickOtherDuringSelected(puzzle, x, y);
            Phenomenon phenomenonKill = puzzle.board[x][y].phenomenon;
            if (phenomenonKill != null && phenomenonKill.getId() != 8 && phenomenonKill.getId() != 11 && (x != this.posX || y != this.posY)) {
               for (int xx = 0; xx < puzzle.width; xx++) {
                  for (int yy = 0; yy < puzzle.height; yy++) {
                     if (puzzle.board[xx][yy].phenomenon != null
                        && puzzle.board[xx][yy].phenomenon.getId() == phenomenonKill.getId()
                        && puzzle.destroyPhenomenon(xx, yy)) {
                        oneKilled = true;
                        puzzle.inventory[11]++;
                     }
                  }
               }

               if (oneKilled) {
                  this.onUnselected(puzzle);
                  puzzle.selected = null;
                  puzzle.destroyPhenomenon(this.posX, this.posY);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static class PhenomenGreed extends Phenomenon {
      public int idToCopy = 0;

      @Override
      public int getId() {
         return 57;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (puzzle.checkBounds(x, y)) {
            if (x == this.posX && y == this.posY) {
               this.idToCopy = 0;
               this.onUnselected(puzzle);
               puzzle.selected = null;
               return false;
            }

            if (this.idToCopy > 0) {
               if (puzzle.board[x][y].canFitPhenomenon() && puzzle.board[x][y].phenomenon == null) {
                  boolean sended = false;

                  for (int i = 0; i < 4; i++) {
                     if (puzzle.addPhenomenonToDirection(this.posX, this.posY, i, this.getId(), 3)) {
                        sended = true;
                     }
                  }

                  if (sended) {
                     puzzle.addPhenomenon(x, y, this.idToCopy, 12);
                  }

                  this.onUnselected(puzzle);
                  puzzle.selected = null;
                  this.idToCopy = 0;
                  return sended;
               }
            } else {
               if (puzzle.board[x][y].phenomenon == null) {
                  this.onUnselected(puzzle);
                  puzzle.selected = null;
                  return false;
               }

               this.idToCopy = puzzle.board[x][y].phenomenon.getId();
            }
         } else {
            this.onUnselected(puzzle);
            puzzle.selected = null;
            this.idToCopy = 0;
         }

         return false;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.setSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 0), Phenomenons.surfaceMoney);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 0), Phenomenons.surfaceMoney);
      }

      @Override
      public void onUnselected(TerraformingResearchPuzzle puzzle) {
         super.onUnselected(puzzle);
         this.idToCopy = 0;
      }
   }

   public static class PhenomenIce extends Phenomenon {
      @Override
      public int getId() {
         return 47;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceIce);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceIce);
      }
   }

   public static class PhenomenLava extends Phenomenon {
      int charge = 0;

      @Override
      public int getId() {
         return 19;
      }

      @Override
      public int onStep(TerraformingResearchPuzzle puzzle) {
         if (this.charge < 3) {
            this.charge++;
            return this.getDelayAfterStep(false);
         } else {
            this.charge = 0;
            puzzle.setSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 2), Phenomenons.surfaceLava);
            return this.getDelayAfterStep(true);
         }
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceLava);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceLava);
      }
   }

   public static class PhenomenLightning extends Phenomenon {
      @Override
      public int getId() {
         return 24;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (puzzle.checkBounds(x, y)) {
            super.clickOtherDuringSelected(puzzle, x, y);
            puzzle.addPhenomenon(x, y, 7, 12);
            this.onUnselected(puzzle);
            puzzle.selected = null;
            puzzle.removePhenomenon(this.posX, this.posY, 7);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public TerraformingPlayerCommand getGenerationCommand(TerraformingResearchPuzzle puzzle, Random rand) {
         if (rand.nextFloat() < 0.75F) {
            ArrayList<int[]> list = puzzle.getAllSelectableCells();
            int maxI = Math.min(list.size(), 8);

            for (int i = 0; i < maxI; i++) {
               int[] ints = list.get(rand.nextInt(list.size()));
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.cell(ints);
               if (cell.phenomenon != null && Phenomenons.getInterflowId(cell.phenomenon.getId(), 7) > 0) {
                  return new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, ints[0], ints[1], 0);
               }
            }
         }

         return super.getGenerationCommand(puzzle, rand);
      }
   }

   public static class PhenomenLive extends Phenomenon {
      @Override
      public int getId() {
         return 12;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }
   }

   public static class PhenomenMetal extends Phenomenon {
      @Override
      public int getId() {
         return 34;
      }

      @Override
      public boolean clickOtherDuringSelected(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (puzzle.checkBounds(x, y)) {
            super.clickOtherDuringSelected(puzzle, x, y);
            int r = this.radiusTo(x, y);
            if (r == 1 && !puzzle.board[x][y].isPoint) {
               puzzle.board[x][y].isPoint = true;
               puzzle.board[x][y].recalcWayRenderIndex();
               this.onUnselected(puzzle);
               puzzle.selected = null;
               puzzle.destroyPhenomenon(this.posX, this.posY);
               return true;
            }
         }

         return false;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMetal);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMetal);
      }
   }

   public static class PhenomenMud extends Phenomenon {
      @Override
      public int getId() {
         return 43;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMud);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMud);
      }
   }

   public static class PhenomenPain extends Phenomenon {
      @Override
      public int getId() {
         return 10;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }
   }

   public static class PhenomenPleasure extends Phenomenon {
      @Override
      public int getId() {
         return 9;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }

      @Override
      public void setFields(TerraformingResearchPuzzle puzzle, boolean[][][] fields) {
         setFieldsInRadius(puzzle, fields, this.posX, this.posY, this.getReachModified(puzzle, 2), 1);
      }
   }

   public static class PhenomenPoison extends Phenomenon {
      @Override
      public int getId() {
         return 5;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePoison);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePoison);
      }
   }

   public static class PhenomenPoisonGas extends Phenomenon {
      @Override
      public int getId() {
         return 45;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePoisonGas);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePoisonGas);
      }
   }

   public static class PhenomenPotion extends Phenomenon {
      @Override
      public int getId() {
         return 36;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePotion);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfacePotion);
      }
   }

   public static class PhenomenPurity extends Phenomenon {
      @Override
      public int getId() {
         return 99;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), TerraformingResearchSurface.TRSurfaceType.TERRAIN);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), TerraformingResearchSurface.TRSurfaceType.ATMOSPHERE);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), TerraformingResearchSurface.TRSurfaceType.CREATURE);
      }
   }

   public static class PhenomenRain extends Phenomenon {
      @Override
      public int getId() {
         return 21;
      }

      @Override
      public EnumClickPossible getUsePossibles() {
         return EnumClickPossible.ANY;
      }

      @Override
      public boolean use(TerraformingResearchPuzzle puzzle, int x, int y) {
         boolean ret = puzzle.addPhenomenon(x, y, 3, 12);
         if (ret) {
            puzzle.removePhenomenon(this.posX, this.posY, 3);
         }

         return ret;
      }

      @Override
      public int getBaseUseReach() {
         return 1;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceRain);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceRain);
      }
   }

   public static class PhenomenRich extends Phenomenon {
      @Override
      public int getId() {
         return 56;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMoney);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceMoney);
      }
   }

   public static class PhenomenSalts extends Phenomenon {
      @Override
      public int getId() {
         return 22;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSalts);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSalts);
      }
   }

   public static class PhenomenSnow extends Phenomenon {
      @Override
      public int getId() {
         return 102;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSnow);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSnow);
      }
   }

   public static class PhenomenSteam extends Phenomenon {
      @Override
      public int getId() {
         return 20;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSteam);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceSteam);
      }
   }

   public static class PhenomenStorm extends Phenomenon {
      @Override
      public int getId() {
         return 74;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceStorm);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceStorm);
      }

      @Override
      public void setFields(TerraformingResearchPuzzle puzzle, boolean[][][] fields) {
         setFieldsInRadius(puzzle, fields, this.posX, this.posY, this.getReachModified(puzzle, 2), 0);
      }

      @Override
      public void updateAnimation(TerraformingResearchPuzzle puzzle, RenderTerraformingResearch renderTFR, float x, float y, int arrayX, int arrayY) {
         if (renderTFR != null) {
            for (int i = 0; i < 2; i++) {
               float radius = this.getReachModified(puzzle, 2) + 0.5F;
               float tick = i == 0 ? puzzle.ticksExisted : puzzle.ticksExisted + 40;
               Vec2f pos1 = getParticlePosInQuad(tick % 80.0F / 80.0F, renderTFR.cellWidth * radius);
               TerraformingResearchParticle part = new TerraformingResearchParticle(
                  351, 300, 9, 9, Phenomenons.tfr_sprites, Phenomenons.tfr_sprites_sizeX, Phenomenons.tfr_sprites_sizeY
               );
               part.livetimePosMotion(
                  24,
                  x + pos1.x,
                  y + pos1.y,
                  0.2F - RenderTerraformingResearch.rand.nextFloat() * 0.4F,
                  0.2F - RenderTerraformingResearch.rand.nextFloat() * 0.4F,
                  0.99F
               );
               part.renderStyle(TRRenderer.RenderStyle.ADDITIVE);
               part.animation(2, 13);
               part.red = 0.3F + RenderTerraformingResearch.rand.nextFloat() * 0.15F;
               part.green = 0.75F + RenderTerraformingResearch.rand.nextFloat() * 0.1F;
               part.blue = 1.0F;
               renderTFR.spawnParticle(part);
            }
         }
      }
   }

   public static class PhenomenVoid extends Phenomenon {
      @Override
      public int getId() {
         return 8;
      }

      @Override
      public int onStep(TerraformingResearchPuzzle puzzle) {
         super.onStep(puzzle);
         boolean is = false;

         for (int i = 0; i < 4; i++) {
            if (puzzle.addPhenomenonToDirection(this.posX, this.posY, i, this.getId(), 1)) {
               is = true;
            }
         }

         return this.getDelayAfterStep(is);
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
      }
   }

   public static class PhenomenWater extends Phenomenon {
      @Override
      public int getId() {
         return 3;
      }

      @Override
      public void onCreated(TerraformingResearchPuzzle puzzle) {
         super.onCreated(puzzle);
         puzzle.addSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceOcean);
      }

      @Override
      public void onRemoved(TerraformingResearchPuzzle puzzle) {
         super.onRemoved(puzzle);
         puzzle.removeSurfaces(this.posX, this.posY, this.getReachModified(puzzle, 1), Phenomenons.surfaceOcean);
      }
   }

   public static class PhenomenWeapon extends Phenomenon {
      public int cooldown = 0;

      @Override
      public int getId() {
         return 59;
      }

      @Override
      public int onStep(TerraformingResearchPuzzle puzzle) {
         super.onStep(puzzle);
         if (this.cooldown > 0) {
            this.cooldown--;
         }

         return this.getDelayAfterStep(false);
      }

      @Override
      public boolean canUse() {
         return this.cooldown <= 0;
      }

      @Override
      public boolean use(TerraformingResearchPuzzle puzzle, int x, int y) {
         if (!this.subselected) {
            return false;
         } else {
            int idToShoot = puzzle.board[this.subselectedX][this.subselectedY].phenomenon.getId();
            Phenomenon phenomenon = Phenomenons.createById(idToShoot);
            if (phenomenon != null) {
               phenomenon.posX = x;
               phenomenon.posY = y;
               phenomenon.onCreated(puzzle);
               puzzle.queue.remove(phenomenon);
               this.cooldown = 3;
               return true;
            } else {
               return false;
            }
         }
      }

      @Override
      public EnumClickPossible getUsePossibles() {
         return EnumClickPossible.ANY;
      }

      @Override
      public EnumClickPossible getSubselectionPossibles() {
         return EnumClickPossible.PHENOMENON;
      }

      @Override
      public int getBaseUseReach() {
         return 3;
      }

      @Override
      public int getBaseSubselectReach() {
         return 1;
      }

      @Override
      public boolean hasSubselectionWhenUse() {
         return true;
      }
   }

   public static class PhenomenWormhole extends Phenomenon {
      @Override
      public int getId() {
         return 103;
      }

      @Override
      public EnumClickPossible getUsePossibles() {
         return EnumClickPossible.EMPTY_POINT;
      }

      @Override
      public EnumClickPossible getSubselectionPossibles() {
         return EnumClickPossible.PHENOMENON;
      }

      @Override
      public boolean use(TerraformingResearchPuzzle puzzle, int x, int y) {
         return puzzle.teleportPhenomenon(this.subselectedX, this.subselectedY, x, y);
      }

      @Override
      public int getBaseUseReach() {
         return 9999;
      }

      @Override
      public int getBaseSubselectReach() {
         return 1;
      }

      @Override
      public boolean hasSubselectionWhenUse() {
         return true;
      }

      @Override
      public TerraformingPlayerCommand getGenerationCommand(TerraformingResearchPuzzle puzzle, Random rand) {
         if (this.subselected) {
            ArrayList<int[]> list = puzzle.getAllFitableOrSelectableCells();
            if (!list.isEmpty()) {
               int[] ints = list.get(rand.nextInt(list.size()));
               return new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, ints[0], ints[1], 0);
            }
         } else if (rand.nextFloat() < 0.95F) {
            ArrayList<int[]> list = puzzle.getAllSelectableInRadius(this.posX, this.posY, 1);

            for (int i = 0; i < list.size(); i++) {
               int[] ints = list.get(rand.nextInt(list.size()));
               TerraformingResearchPuzzle.TerraformingResearchCell cell = puzzle.cell(ints);
               if (cell.phenomenon != null) {
                  return new TerraformingPlayerCommand(TerraformingPlayerCommand.TRPlayerCommandType.BOARD_SELECT, ints[0], ints[1], 0);
               }
            }
         }

         return super.getGenerationCommand(puzzle, rand);
      }
   }

   public static class PlayerAvatar extends Phenomenon {
      @Override
      public int getId() {
         return 0;
      }

      @Override
      public boolean isPlayer() {
         return true;
      }
   }
}

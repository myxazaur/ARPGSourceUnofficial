package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.CubikModel;
import com.Vivern.Arpg.entity.BlowholeShoot;
import com.Vivern.Arpg.entity.EntityCubicParticle;
import com.Vivern.Arpg.entity.EntityShard;
import com.Vivern.Arpg.entity.GunPEmitter;
import com.Vivern.Arpg.entity.TrailParticle;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.CrystalSphereTESR;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.renders.SparkleSubparticle;
import com.Vivern.Arpg.tileentity.ManaBuffer;
import com.Vivern.Arpg.tileentity.TileResearchTable;
import com.Vivern.Arpg.weather.Weather;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class ShardType {
   public static Random rand = new Random();
   public static CrystalSphereTESR.RenderShardsEffect ins = new CrystalSphereTESR.RenderShardsEffect();
   public static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public static float particlesGravityMult = 1.0F;
   public static float maximumBeamDamage = 20.0F;
   public final String name;
   public final int id;
   public float reachDistance;
   public final ResourceLocation textureMui;
   public int frameCount;
   public int energyAnimationSpeed;
   public float colorR;
   public float colorG;
   public float colorB;
   public static float[][] additionalColors = new float[][]{
      {0.0F, 0.0F, 0.0F},
      ColorConverters.hexToRgb("ffdf41"),
      ColorConverters.hexToRgb("675d53"),
      ColorConverters.hexToRgb("4793d4"),
      ColorConverters.hexToRgb("acaad5"),
      ColorConverters.hexToRgb("00ff00"),
      ColorConverters.hexToRgb("80ceff"),
      ColorConverters.hexToRgb("4936ff"),
      ColorConverters.hexToRgb("000000"),
      ColorConverters.hexToRgb("ffb8c2"),
      ColorConverters.hexToRgb("ff0000"),
      ColorConverters.hexToRgb("00f071"),
      ColorConverters.hexToRgb("b5ea34")
   };
   public CrystalSphereTESR.RenderShardsEffect renderInSphere;
   public AxisAlignedBB vialBoundingBox;
   public static AxisAlignedBB emptyVialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.6875, 0.1875);
   public static ShardType FIRE = new ShardTypeFire("fire", 1, 1.0F, 0.6F, 0.1F, ins);
   public static ShardType EARTH = new ShardTypeEarth("earth", 2, 0.67F, 0.47F, 0.51F, ins);
   public static ShardType WATER = new ShardTypeWater("water", 3, 0.06F, 0.51F, 0.84F, ins);
   public static ShardType AIR = new ShardTypeAir("air", 4, 0.62F, 0.56F, 0.79F, ins);
   public static ShardType POISON = new ShardTypePoison("poison", 5, 0.5F, 0.9F, 0.25F, ins);
   public static ShardType COLD = new ShardTypeCold("cold", 6, 0.84F, 0.92F, 1.0F, ins);
   public static ShardType ELECTRIC = new ShardTypeElectric("electric", 7, 0.0F, 1.0F, 1.0F, ins);
   public static ShardType VOID = new ShardTypeVoid("void", 8, 0.0F, 0.0F, 0.0F, ins);
   public static ShardType PLEASURE = new ShardTypePleasure("pleasure", 9, 1.0F, 0.45F, 0.72F, ins);
   public static ShardType PAIN = new ShardTypePain("pain", 10, 0.65F, 0.06F, 0.08F, ins);
   public static ShardType DEATH = new ShardTypeDeath("death", 11, 0.5F, 0.0F, 1.0F, ins);
   public static ShardType LIVE = new ShardTypeLive("live", 12, 1.0F, 0.96F, 0.35F, ins);
   public static ShardType[] registry = new ShardType[]{null, FIRE, EARTH, WATER, AIR, POISON, COLD, ELECTRIC, VOID, PLEASURE, PAIN, DEATH, LIVE};
   public static CubikModel model = new CubikModel();

   public ShardType(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
      this.name = name;
      this.reachDistance = 2.0F;
      this.frameCount = 17;
      this.energyAnimationSpeed = 7;
      this.id = id;
      this.colorR = colorR;
      this.colorG = colorG;
      this.colorB = colorB;
      this.renderInSphere = renderInSphere;
      this.textureMui = pixel;
   }

   public static void registerElements() {
      TileResearchTable.registerPhenomenons();
   }

   public void renderShardEntity(EntityShard entity, double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      Minecraft.getMinecraft().renderEngine.bindTexture(pixel);
      float scale = 0.00390625F;
      GlStateManager.scale(scale, scale, scale);
      GL11.glDisable(2896);
      if (this == VOID) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_COLOR);
      }

      AbstractMobModel.light(240, false);
      GL11.glEnable(3042);
      float colorProgr = MathHelper.sin(entity.ticks / 20.0F) * 0.5F + 0.5F;
      float fR = GetMOP.partial(this.colorR, this.getAdditionalColor()[0], colorProgr);
      float fG = GetMOP.partial(this.colorG, this.getAdditionalColor()[1], colorProgr);
      float fB = GetMOP.partial(this.colorB, this.getAdditionalColor()[2], colorProgr);
      GlStateManager.color(fR, fG, fB, 1.0F);
      model.setRotateAngle(
         model.shape1,
         0.017453F * GetMOP.partial(entity.rotationX, entity.prevrotationX, partialTicks),
         0.017453F * GetMOP.partial(entity.rotationY, entity.prevrotationY, partialTicks),
         0.017453F * GetMOP.partial(entity.rotationZ, entity.prevrotationZ, partialTicks)
      );
      model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      float scale2 = 3.0F;
      GlStateManager.scale(scale2, scale2, scale2);
      GlStateManager.color(fR * 0.5F, fG * 0.5F, fB * 0.5F, 0.5F);
      if (this == VOID) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      }

      model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      AbstractMobModel.returnlight();
      GL11.glEnable(2896);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
   }

   public void spawnNativeParticle(
      World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
   ) {
   }

   public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
   }

   public void onAffectBlock(Object dealer, World world, BlockPos blockPos, Object damagePosition, float elementAmount) {
   }

   public static void spawnShards(ShardType shardType, Entity entitySpawnOn, float energyAmount) {
      spawnShards(
         entitySpawnOn.world, shardType, entitySpawnOn.posX, entitySpawnOn.posY + 0.0625, entitySpawnOn.posZ, energyAmount
      );
   }

   public static void spawnShards(World world, ShardType shardType, double x, double y, double z, float energyAmount) {
      if (energyAmount > 0.0F) {
         int count = Math.min((int)Math.round(Math.sqrt(energyAmount)), 10);
         if (count > 0) {
            int forone = MathHelper.floor(energyAmount / count);
            float forlast = energyAmount - forone * count;
            if (count > 0) {
               for (int c = 0; c < count; c++) {
                  EntityShard shard = new EntityShard(world, shardType);
                  shard.setPosition(x, y, z);
                  shard.energy = forone;
                  world.spawnEntity(shard);
               }
            }

            if (forlast > 0.0F) {
               EntityShard shard = new EntityShard(world, shardType);
               shard.setPosition(x, y, z);
               shard.energy = forlast;
               world.spawnEntity(shard);
            }
         }
      }
   }

   public static void dropShardsFromMob(EntityLivingBase entity) {
      if (entity instanceof AbstractMob) {
         ((AbstractMob)entity).dropShards();
      } else {
         if (entity instanceof EntityWitherSkeleton && rand.nextFloat() < 0.4) {
            spawnShards(DEATH, entity, 2.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityWitch && rand.nextFloat() < 0.7) {
            spawnShards(POISON, entity, 2.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityEnderman && rand.nextFloat() < 0.1) {
            spawnShards(VOID, entity, 1.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityShulker) {
            if (rand.nextFloat() < 0.3) {
               spawnShards(VOID, entity, 1.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.3) {
               spawnShards(AIR, entity, 1.0F * AbstractMob.dropShardsQuantity);
            }
         }

         if (entity instanceof EntityMagmaCube && rand.nextFloat() < 0.3) {
            spawnShards(FIRE, entity, 1.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityBlaze && rand.nextFloat() < 0.4) {
            spawnShards(FIRE, entity, 2.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityGhast) {
            spawnShards(FIRE, entity, 4.0F * AbstractMob.dropShardsQuantity);
            spawnShards(PAIN, entity, 4.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityEvoker) {
            if (rand.nextFloat() < 0.5) {
               spawnShards(LIVE, entity, 2.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.6) {
               spawnShards(VOID, entity, 2.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.6) {
               spawnShards(PLEASURE, entity, 2.0F * AbstractMob.dropShardsQuantity);
            }
         }

         if (entity instanceof EntitySlime && rand.nextFloat() < 0.1) {
            spawnShards(POISON, entity, 1.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityGuardian && rand.nextFloat() < 0.4) {
            spawnShards(WATER, entity, 1.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityElderGuardian) {
            spawnShards(WATER, entity, 4.0F * AbstractMob.dropShardsQuantity);
            spawnShards(COLD, entity, 2.0F * AbstractMob.dropShardsQuantity);
         }

         if (entity instanceof EntityWither) {
            if (rand.nextFloat() < 0.9) {
               spawnShards(DEATH, entity, 6.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.8) {
               spawnShards(POISON, entity, 3.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.8) {
               spawnShards(FIRE, entity, 3.0F * AbstractMob.dropShardsQuantity);
            }

            if (rand.nextFloat() < 0.8) {
               spawnShards(PAIN, entity, 3.0F * AbstractMob.dropShardsQuantity);
            }
         }
      }
   }

   @Override
   public String toString() {
      return this.name;
   }

   public float[] getAdditionalColor() {
      return additionalColors[this.id];
   }

   @Nullable
   public ShardType opposite() {
      if (this == FIRE) {
         return COLD;
      } else if (this == COLD) {
         return FIRE;
      } else if (this == PAIN) {
         return PLEASURE;
      } else if (this == PLEASURE) {
         return PAIN;
      } else if (this == LIVE) {
         return DEATH;
      } else {
         return this == DEATH ? LIVE : null;
      }
   }

   public TileResearchTable.Phenomenon getRawPhenomenon() {
      if (this == FIRE) {
         return TileResearchTable.FIRE;
      } else if (this == COLD) {
         return TileResearchTable.COLD;
      } else if (this == EARTH) {
         return TileResearchTable.EARTH;
      } else if (this == WATER) {
         return TileResearchTable.WATER;
      } else if (this == AIR) {
         return TileResearchTable.AIR;
      } else if (this == ELECTRIC) {
         return TileResearchTable.ELECTRIC;
      } else if (this == POISON) {
         return TileResearchTable.POISON;
      } else if (this == VOID) {
         return TileResearchTable.VOID;
      } else if (this == PLEASURE) {
         return TileResearchTable.PLEASURE;
      } else if (this == PAIN) {
         return TileResearchTable.PAIN;
      } else if (this == DEATH) {
         return TileResearchTable.DEATH;
      } else {
         return this == LIVE ? TileResearchTable.LIVE : null;
      }
   }

   public static ShardType byName(String name) {
      for (int i = 1; i < registry.length; i++) {
         ShardType type = registry[i];
         if (type.getName().equals(name)) {
            return type;
         }
      }

      return FIRE;
   }

   @Nullable
   public static ShardType byId(int id) {
      return id >= 1 && id <= 12 ? registry[id] : null;
   }

   public static ShardType byNameNullable(String name) {
      for (int i = 1; i < registry.length; i++) {
         ShardType type = registry[i];
         if (type.getName().equals(name)) {
            return type;
         }
      }

      return null;
   }

   public String getName() {
      return this.name;
   }

   public abstract SoundEvent getLoopedBeamSound();

   public static void dealShardDamage(
      Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float damageAmount, float knockback, String damageType
   ) {
      Entity damager = dealer instanceof Entity ? (Entity)dealer : null;
      float damage = Math.min(damageAmount, maximumBeamDamage);
      WeaponDamage damageSource = new WeaponDamage(null, damager, projectile, false, false, damagePosition, damageType);
      if (damageSource.damagePosition != null) {
         Weapons.dealDamage(
            damageSource,
            damage,
            damager,
            entity,
            true,
            knockback,
            damageSource.damagePosition.x,
            damageSource.damagePosition.y,
            damageSource.damagePosition.z
         );
      } else {
         Weapons.dealDamage(damageSource, damage, damager, entity, true, 0.0F);
      }

      entity.hurtResistantTime = 0;
   }

   public static class ShardTypeAir extends ShardType {
      public static ResourceLocation airdust = new ResourceLocation("arpg:textures/airdust.png");

      public ShardTypeAir(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.0625, 0.0, -0.0625, 0.0625, 0.71875, 0.0625);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.4) {
               float col = rand.nextFloat();
               int lt = 20 + rand.nextInt(20);
               float scl = (0.02F + rand.nextFloat() * 0.01F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  pixel,
                  scl,
                  -0.002F * particlesGravityMult,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F - col * 0.35F,
                  1.0F - col * 0.28F,
                  1.0F - col * 0.35F,
                  false,
                  rand.nextInt(360)
               );
               part.disableLightning = false;
               world.spawnEntity(part);
            } else if (rand.nextFloat() < 0.28F) {
               int addlt = 0;
               int lt = 56 + addlt * 2;
               float scl = (0.6F + rand.nextFloat() * 0.4F) * scaleMultiplier;
               AnimatedGParticle part = new AnimatedGParticle(
                  airdust,
                  scl,
                  0.0F * particlesGravityMult,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX / 2.0F,
                  (float)motionY / 2.0F,
                  (float)motionZ / 2.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  rand.nextInt(360)
               );
               part.disableLightning = false;
               part.scaleTickAdding = 0.01F * scaleMultiplier;
               part.framecount = 32;
               part.animDelay = 1;
               part.disableOnEnd = true;
               part.useNormalTime = true;
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(motionX, motionY, motionZ));
               part.rotationPitchYaw = new Vec2f((float)pitchYaw.x, (float)pitchYaw.y);
               part.animCounter = addlt;
               world.spawnEntity(part);
            } else {
               world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, motionX, motionY, motionZ, new int[0]);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_air;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         WeaponDamage damageSource = new WeaponDamage(null, null, projectile, false, false, damagePosition, "");
         if (damageSource.damagePosition != null) {
            SuperKnockback.applyKnockback(
               entity,
               (float)Math.sqrt(elementAmount * 8.0F),
               damageSource.damagePosition.x,
               damageSource.damagePosition.y,
               damageSource.damagePosition.z
            );
         }
      }
   }

   public static class ShardTypeCold extends ShardType {
      public static ResourceLocation snowflake5 = new ResourceLocation("arpg:textures/snowflake5.png");
      public static ResourceLocation star = new ResourceLocation("arpg:textures/star.png");
      public static ParticleTracker.TrackerSinusScaling ptss = new ParticleTracker.TrackerSinusScaling(0.33F, 0.9F);

      public ShardTypeCold(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.625, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.6F) {
               int lt = 60 + rand.nextInt(40);
               float scl = (0.02F + rand.nextFloat() * 0.02F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  star,
                  scl,
                  0.0F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x + motionX * rand.nextFloat(),
                  y + motionY * rand.nextFloat(),
                  z + motionZ * rand.nextFloat(),
                  (float)motionX * 0.3F,
                  (float)motionY * 0.3F,
                  (float)motionZ * 0.3F,
                  0.8F + rand.nextFloat() * 0.2F,
                  1.0F,
                  1.0F,
                  false,
                  rand.nextInt(360)
               );
               part.scaleTickAdding = -scl / (lt + 20);
               part.tracker = ptss;
               world.spawnEntity(part);
            }

            if (rand.nextFloat() < 0.3F) {
               int lt = 20 + rand.nextInt(15);
               float scl = 0.35F + 0.05F * scaleMultiplier + rand.nextFloat() * 0.4F;
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  scl,
                  0.002F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX * 0.8F,
                  (float)motionY * 0.8F,
                  (float)motionZ * 0.8F,
                  0.5F + rand.nextFloat() * 0.2F,
                  0.7F + rand.nextFloat() * 0.2F,
                  1.0F,
                  true,
                  rand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -1.0F / lt;
               world.spawnEntity(bigsmoke);
            } else {
               int lt = 20 + rand.nextInt(40);
               float scl = (0.025F + rand.nextFloat() * 0.01F) * scaleMultiplier;
               GUNParticle hailp = new GUNParticle(
                  snowflake5,
                  scl,
                  (0.01F + (rand.nextFloat() - 0.5F) * 0.016F) * particlesGravityMult,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  rand.nextInt(360),
                  true,
                  5.0F
               );
               hailp.scaleTickDropAdding = -0.002F;
               float value = 0.2F;
               hailp.tracker = new ParticleTracker.TrackerMotionVector(
                  Weather.getWindX(x, z), Weather.getWindY(x, z), Weather.getWindZ(x, z), value / 3.0F, value / 10.0F, 60
               );
               world.spawnEntity(hailp);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_cold;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 8.0F, (float)Math.sqrt(elementAmount * 4.0F), WeaponDamage.frost);
         PotionEffect lastdebaff = Weapons.mixPotion(
            entity,
            PotionEffects.FREEZING,
            30.0F * elementAmount + ManaBuffer.TICKRATE,
            1.0F,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumMathOperation.PLUS,
            Weapons.EnumMathOperation.PLUS,
            180,
            40
         );
         Freezing.tryPlaySound(entity, lastdebaff);
         DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ICING, 1.0F);
      }
   }

   public static class ShardTypeDeath extends ShardType {
      public static ResourceLocation ghostly_shoot = new ResourceLocation("arpg:textures/ghostly_shoot.png");
      public static ResourceLocation blueexplode = new ResourceLocation("arpg:textures/blueexplode.png");
      public static ParticleTracker.TrackerRandomMotion trm = new ParticleTracker.TrackerRandomMotion(null, 0.01F, 1);

      public ShardTypeDeath(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.75, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.4) {
               float colR = 0.75F - 0.2F * rand.nextFloat();
               float colG = 1.0F;
               float colB = 0.6F - 0.2F * rand.nextFloat();
               float EcolR = 1.0F - 0.1F * rand.nextFloat();
               float EcolG = 0.5F - 0.3F * rand.nextFloat();
               float EcolB = 1.0F;
               int lt = 15 + rand.nextInt(15);
               float sclF = rand.nextFloat();
               float scl = (0.07F + sclF * 0.02F) * scaleMultiplier;
               TrailParticle part = new TrailParticle(
                  blueexplode,
                  scl,
                  -0.002F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  colR,
                  colG,
                  colB,
                  true,
                  rand.nextInt(360),
                  false,
                  1.0F,
                  ghostly_shoot,
                  (0.12F + sclF * 0.02F) * scaleMultiplier,
                  240,
                  colR,
                  colG,
                  colB,
                  0.1F,
                  5,
                  -0.15F,
                  9999.0F
               );
               part.alphaGlowing = true;
               part.TalphaGlowing = true;
               part.Ttexstart = 0.1F;
               part.Toffset = -0.1F;
               ParticleTracker pt = new ParticleTracker.TrackerTrailParticleGradient(
                  new Vec3d(colR, colG, colB), new Vec3d(EcolR, EcolG, EcolB), (int)(lt * 0.2F), lt - 4
               );
               part.tracker = new ParticleTracker.Multitracker(trm, pt);
               world.spawnEntity(part);
            } else {
               float colR = 0.75F - 0.3F * rand.nextFloat();
               float colG = 1.0F;
               float colB = 0.6F - 0.3F * rand.nextFloat();
               float EcolR = 1.0F - 0.1F * rand.nextFloat();
               float EcolG = 0.5F - 0.3F * rand.nextFloat();
               float EcolB = 1.0F;
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(motionX, motionY, motionZ));
               int lt = 13 + rand.nextInt(15);
               float scl = (0.1F + rand.nextFloat() * 0.04F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  ghostly_shoot,
                  scl,
                  0.003F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  colR,
                  colG,
                  colB,
                  true,
                  (int)(-pitchYaw.x) - 90
               );
               part.alphaGlowing = true;
               part.rotationPitchYaw = new Vec2f(0.0F, (float)pitchYaw.y + 90.0F);
               part.scaleTickAdding = -scl / lt;
               part.tracker = new ParticleTracker.TrackerGradient(new Vec3d(colR, colG, colB), new Vec3d(EcolR, EcolG, EcolB), (int)(lt * 0.25F), lt - 5);
               world.spawnEntity(part);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_death;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         boolean deal = true;
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase creature = (EntityLivingBase)entity;
            if (creature.isEntityUndead()) {
               creature.heal(elementAmount * 10.0F);
               deal = false;
            }
         }

         if (deal) {
            dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 12.0F, (float)Math.sqrt(elementAmount), WeaponDamage.magic);
         }
      }
   }

   public static class ShardTypeEarth extends ShardType {
      public static ResourceLocation[] stone_shards = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/stone_shard_1.png"),
         new ResourceLocation("arpg:textures/stone_shard_2.png"),
         new ResourceLocation("arpg:textures/stone_shard_3.png")
      };
      public static ResourceLocation pixel_cubic = new ResourceLocation("arpg:textures/pixel_cubic.png");
      public static ResourceLocation boulder2 = new ResourceLocation("arpg:textures/boulder2.png");
      public static ParticleTracker.TrackerRotate[] ptrs = new ParticleTracker.TrackerRotate[]{
         new ParticleTracker.TrackerRotate(7),
         new ParticleTracker.TrackerRotate(-7),
         new ParticleTracker.TrackerRotate(14),
         new ParticleTracker.TrackerRotate(-14)
      };

      public ShardTypeEarth(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.5, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            float r = 1.0F;
            float g = 1.0F;
            float b = 1.0F;
            if (rand.nextFloat() < 0.5F) {
               r = 0.65F - rand.nextFloat() * 0.35F;
               g = 0.65F - rand.nextFloat() * 0.25F;
               b = 0.65F - rand.nextFloat() * 0.35F;
            } else {
               r = 0.65F - rand.nextFloat() * 0.3F;
               g = r;
               b = r;
            }

            int lt = 6 + rand.nextInt(10);
            float scl = (0.02F + rand.nextFloat() * 0.01F) * 3.0F * scaleMultiplier;
            float colGB = rand.nextFloat() * 0.16F;
            GUNParticle part = new GUNParticle(
               stone_shards[rand.nextInt(3)],
               scl,
               0.02F * particlesGravityMult,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)motionX * 2.0F,
               (float)motionY * 2.0F,
               (float)motionZ * 2.0F,
               1.0F,
               1.0F - colGB,
               1.0F - colGB * 3.0F,
               false,
               rand.nextInt(360)
            );
            part.scaleTickAdding = -scl / lt;
            part.tracker = ptrs[rand.nextInt(4)];
            world.spawnEntity(part);
            if (rand.nextFloat() < 0.84F) {
               lt = 20 + rand.nextInt(40);
               scl = 0.001953125F;
               EntityCubicParticle partx = new EntityCubicParticle(
                  pixel_cubic,
                  scl,
                  0.024F * particlesGravityMult,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  r,
                  g,
                  b,
                  false,
                  rand.nextFloat(),
                  rand.nextFloat(),
                  rand.nextFloat(),
                  0.2F,
                  true,
                  0.0F
               );
               partx.collide = true;
               partx.frictionMultipl = 3.0F;
               world.spawnEntity(partx);
            } else {
               lt = 20 + rand.nextInt(40);
               scl = 0.00390625F;
               EntityCubicParticle partx = new EntityCubicParticle(
                  boulder2,
                  scl,
                  0.024F * particlesGravityMult,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  r,
                  g,
                  b,
                  false,
                  rand.nextFloat(),
                  rand.nextFloat(),
                  rand.nextFloat(),
                  0.2F,
                  true,
                  0.0F
               );
               partx.collide = true;
               partx.frictionMultipl = 3.0F;
               world.spawnEntity(partx);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_earth;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 6.0F, (float)Math.sqrt(elementAmount * 4.0F), WeaponDamage.pierce);
         Weapons.setPotionIfEntityLB(entity, PotionEffects.BROKEN_ARMOR, 500, Math.max((int)elementAmount, 0));
         DeathEffects.applyDeathEffect(entity, DeathEffects.DE_SAND, 1.0F);
      }
   }

   public static class ShardTypeElectric extends ShardType {
      public static ResourceLocation sparkle7 = new ResourceLocation("arpg:textures/sparkle7.png");
      public static ResourceLocation lightning3 = new ResourceLocation("arpg:textures/lightning3.png");
      public static ParticleTracker.TrackerLinkBeams ptlb = new ParticleTracker.TrackerLinkBeams(13, lightning3, 0.8F, 1.0F, 1.0F, 0.05F, 3, 5, 0.07F, 2.0F);

      public ShardTypeElectric(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.625, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            int lt = 40 + rand.nextInt(60);
            float scl = (0.02F + rand.nextFloat() * 0.02F) * scaleMultiplier;
            AnimatedGParticle part = new AnimatedGParticle(
               sparkle7,
               scl,
               0.02F * particlesGravityMult,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)motionX,
               (float)motionY,
               (float)motionZ,
               0.8F + rand.nextFloat() * 0.2F,
               1.0F,
               1.0F,
               true,
               rand.nextInt(360),
               true,
               1.3F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.framecount = 4;
            part.animDelay = 2 + rand.nextInt(4);
            part.disableOnEnd = false;
            part.animCounter = rand.nextInt(4);
            if (rand.nextFloat() < 0.8F) {
               part.tracker = scaleMultiplier == 1.0F
                  ? ptlb
                  : new ParticleTracker.TrackerLinkBeams(13, lightning3, 0.8F, 1.0F, 1.0F, 0.05F * scaleMultiplier, 3, 5, 0.07F, 2.0F);
            }

            world.spawnEntity(part);
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_electric;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 10.0F, (float)Math.sqrt(elementAmount * 2.0F), WeaponDamage.electric);
         Weapons.setPotionIfEntityLB(entity, PotionEffects.SHOCK, 100, Math.max((int)(elementAmount / 2.0F), 0));
         DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ELECTRIC, 1.0F);
      }
   }

   public static class ShardTypeFire extends ShardType {
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/shard__fire_tex.png");
      public static ResourceLocation sparkle6 = new ResourceLocation("arpg:textures/sparkle6.png");
      ParticleTracker.TrackerColorTimeMultiply tctm = new ParticleTracker.TrackerColorTimeMultiply(0.95F, 0.9F, 0.9F, 10, 40);

      public ShardTypeFire(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.625, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            SparkleSubparticle sparklepart = new SparkleSubparticle(
               x,
               y,
               z,
               (0.02F + rand.nextFloat() * 0.01F) * scaleMultiplier,
               80 + rand.nextInt(30) + (rand.nextFloat() < 0.15 ? rand.nextInt(50) + 40 : 0),
               (float)motionX / 2.0F,
               (float)motionY / 2.0F,
               (float)motionZ / 2.0F,
               0.0F
            );
            SparkleSubparticle.particles.add(sparklepart);
            if (rand.nextFloat() < 0.5) {
               int lt = 20 + rand.nextInt(40);
               float scl = (0.02F + rand.nextFloat() * 0.01F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  pixel,
                  scl,
                  0.025F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  0.5F + rand.nextFloat() * 0.5F,
                  rand.nextFloat() * rand.nextFloat(),
                  false,
                  0,
                  true,
                  2.5F
               );
               part.tracker = this.tctm;
               part.scaleTickAdding = -scl / lt;
               world.spawnEntity(part);
            } else if (rand.nextFloat() < 0.77) {
               int lt = 30 + rand.nextInt(60);
               float scl = (0.025F + rand.nextFloat() * 0.015F) * scaleMultiplier;
               AnimatedGParticle part = new AnimatedGParticle(
                  sparkle6,
                  scl,
                  0.025F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  0.5F + rand.nextFloat() * 0.5F,
                  rand.nextFloat() * rand.nextFloat(),
                  true,
                  0,
                  true,
                  2.5F
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               part.framecount = 5;
               part.animDelay = 2 + rand.nextInt(3);
               part.disableOnEnd = false;
               part.animCounter = rand.nextInt(5);
               world.spawnEntity(part);
            } else {
               int lt = 25 + rand.nextInt(65);
               float scl = (0.03F + rand.nextFloat() * 0.01F) * scaleMultiplier;
               GunPEmitter emi = new GunPEmitter(
                  pixel,
                  scl,
                  0.02F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  0.5F + rand.nextFloat() * 0.5F,
                  rand.nextFloat() * rand.nextFloat(),
                  false,
                  2,
                  true,
                  EnumParticleTypes.SMOKE_NORMAL,
                  0.0F,
                  0.0F,
                  0.0F
               );
               emi.tracker = this.tctm;
               emi.scaleTickAdding = -scl / lt;
               world.spawnEntity(emi);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_fire;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 10.0F, (float)Math.sqrt(elementAmount * 2.0F), WeaponDamage.fire);
         entity.setFire((int)Math.max(elementAmount * 20.0F, 8.0F));
         DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 1.0F);
      }
   }

   public static class ShardTypeLive extends ShardType {
      public static ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
      public static ResourceLocation[] live_fractals = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/live_fractal_1.png"),
         new ResourceLocation("arpg:textures/live_fractal_2.png"),
         new ResourceLocation("arpg:textures/live_fractal_3.png")
      };
      public static ParticleTracker.TrackerRandomMotion trm = new ParticleTracker.TrackerRandomMotion(null, 0.007F, 1);
      public static ParticleTracker.TrackerSinusScaling ptss = new ParticleTracker.TrackerSinusScaling(0.33F, 0.5F);

      public ShardTypeLive(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.9375, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.6F) {
               int lt = 40 + rand.nextInt(30);
               int EElt = 10 + rand.nextInt(10);
               float scl = (0.03F + rand.nextFloat() * 0.03F) / 2.0F * scaleMultiplier;
               float rgb = rand.nextFloat() * 0.15F;
               float r = 1.0F - rgb - rand.nextFloat() * 0.3F;
               float g = 1.0F - rgb - rand.nextFloat() * 0.3F;
               float b = 0.5F - rgb - rand.nextFloat() * 0.35F;
               GunPEmitter part = new GunPEmitter(
                  mana_flow,
                  scl * 3.0F,
                  0.002F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x + motionX * rand.nextFloat(),
                  y + motionY * rand.nextFloat(),
                  z + motionZ * rand.nextFloat(),
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  r,
                  g,
                  b,
                  true,
                  3,
                  false,
                  mana_flow,
                  scl * 2.0F,
                  0.003F,
                  EElt,
                  240,
                  250.0F,
                  250.0F,
                  250.0F,
                  r,
                  g,
                  b,
                  true
               );
               part.alpha = 3.0F;
               part.alphaTickAdding = -3.0F / lt;
               part.scaleTickAdding = -scl * 3.0F / lt / 2.0F;
               part.EEsclTickAdd = -(scl * 2.0F) / EElt;
               part.tracker = new ParticleTracker.Multitracker(trm, ptss);
               part.alphaGlowing = true;
               part.EEalphaGlowing = true;
               world.spawnEntity(part);
            } else {
               int amountFractal = 10 + rand.nextInt(10);
               int showTime = 7;
               int showedTime = 11;
               int hideTime = 7;
               int lt = amountFractal + showedTime + hideTime;
               float scl = (0.07F + rand.nextFloat() * 0.07F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  live_fractals[rand.nextInt(3)],
                  scl,
                  0.0F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F - rand.nextFloat() * 0.4F,
                  1.0F - rand.nextFloat() * 0.4F,
                  1.0F - rand.nextFloat() * 0.9F,
                  true,
                  1
               );
               part.alphaGlowing = true;
               Vec3d motionvec = new Vec3d(motionX, motionY, motionZ);
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motionvec);
               part.rotationPitchYaw = new Vec2f((float)pitchYaw.x - 90.0F, (float)pitchYaw.y);
               part.tracker = new ParticleTracker.TrackerFractalRender(
                  amountFractal, 0.87F + rand.nextFloat() * 0.1F, 3 + rand.nextInt(13), 1.0F + rand.nextFloat() * 0.18F, 0.3F, showTime, showedTime, hideTime
               );
               world.spawnEntity(part);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_live;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         boolean deal = false;
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase creature = (EntityLivingBase)entity;
            if (!creature.isEntityUndead()) {
               creature.heal(elementAmount * 10.0F);
            } else {
               deal = true;
            }
         }

         if (deal) {
            dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 12.0F, (float)Math.sqrt(elementAmount), WeaponDamage.magic);
         }
      }
   }

   public static class ShardTypePain extends ShardType {
      public static ResourceLocation pain_particle1 = new ResourceLocation("arpg:textures/pain_particle1.png");
      public static ResourceLocation pain_particle2 = new ResourceLocation("arpg:textures/pain_particle2.png");
      public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
      ParticleTracker.TrackerColorTimeMultiply tctm = new ParticleTracker.TrackerColorTimeMultiply(1.0F, 0.85F, 0.85F, 0, 20);

      public ShardTypePain(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.125, 0.0, -0.125, 0.125, 0.625, 0.125);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.25) {
               int lt = 20 + rand.nextInt(15);
               float scl = (0.06F + rand.nextFloat() * 0.03F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  star2,
                  scl,
                  0.01F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  0.98F,
                  0.98F,
                  true,
                  rand.nextInt(360)
               );
               part.scaleTickAdding = -scl / lt;
               part.tracker = this.tctm;
               part.alphaGlowing = true;
               world.spawnEntity(part);
            }

            if (rand.nextFloat() < 0.5) {
               int lt = 40 + rand.nextInt(20);
               float scl = (0.06F + rand.nextFloat() * 0.03F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  star2,
                  scl,
                  0.04F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX,
                  (float)motionY,
                  (float)motionZ,
                  1.0F,
                  0.98F,
                  0.98F,
                  true,
                  rand.nextInt(360),
                  true,
                  2.0F
               );
               part.scaleTickAdding = -scl / lt;
               part.tracker = this.tctm;
               part.alphaGlowing = true;
               world.spawnEntity(part);
            } else {
               Vec3d motionvec = new Vec3d(motionX, motionY, motionZ);
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motionvec);
               int lt = 20;
               float scl = (0.65F + rand.nextFloat() * 0.4F) * scaleMultiplier;
               Vec3d posAdd = motionvec.normalize().scale(scl / 1.45);
               AnimatedGParticle part = new AnimatedGParticle(
                  rand.nextFloat() < 0.5F ? pain_particle1 : pain_particle2,
                  scl,
                  0.0F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x + posAdd.x,
                  y + posAdd.y,
                  z + posAdd.z,
                  (float)motionX / 1.5F,
                  (float)motionY / 1.5F,
                  (float)motionZ / 1.5F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  (int)(-pitchYaw.x + 135.0)
               );
               part.alphaGlowing = true;
               part.rotationPitchYaw = new Vec2f(0.0F, (float)pitchYaw.y + 90.0F);
               part.framecount = 11;
               part.disableOnEnd = true;
               part.animDelay = 1 + rand.nextInt(4);
               world.spawnEntity(part);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_pain;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 10.0F, 0.0F, WeaponDamage.dismember);
         DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 1.0F);
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase creature = (EntityLivingBase)entity;
            creature.removePotionEffect(PotionEffects.RAINBOW);
            creature.removePotionEffect(PotionEffects.SIREN_SONG);
            creature.removePotionEffect(MobEffects.REGENERATION);
            Weapons.setPotionIfEntityLB(creature, PotionEffects.INSANE, 60, 0);
         }
      }
   }

   public static class ShardTypePleasure extends ShardType {
      public static ResourceLocation pleasure_sparks = new ResourceLocation("arpg:textures/pleasure_sparks.png");
      public static ResourceLocation present_random_5 = new ResourceLocation("arpg:textures/present_random_5.png");
      public static ResourceLocation shard_trace = new ResourceLocation("arpg:textures/shard_trace.png");
      public static ResourceLocation firework_sparkle = new ResourceLocation("arpg:textures/firework_sparkle.png");

      public ShardTypePleasure(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.8125, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            Vec3d rgb = rand.nextFloat() < 0.65
               ? new Vec3d(1.0, 0.3F + rand.nextFloat() * 0.3F, 0.8F + rand.nextFloat() * 0.2F)
               : ColorConverters.getRainbow(rand.nextFloat());
            int lt = 20 + rand.nextInt(20);
            float scl = (0.03F + rand.nextFloat() * 0.02F) * scaleMultiplier;
            GUNParticle part = new GUNParticle(
               firework_sparkle,
               scl,
               0.015F * particlesGravityMult,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)motionX,
               (float)motionY,
               (float)motionZ,
               (float)rgb.x,
               (float)rgb.y,
               (float)rgb.z,
               true,
               rand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            world.spawnEntity(part);
            if (rand.nextFloat() < 0.4) {
               rgb = ColorConverters.getRainbow(rand.nextFloat());
               lt = 32;
               scl = (0.25F + rand.nextFloat() * 0.1F) * scaleMultiplier;
               AnimatedGParticle part0 = new AnimatedGParticle(
                  pleasure_sparks, scl, 0.0F * particlesGravityMult, lt, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, rand.nextInt(41) - 20
               );
               if (rand.nextFloat() < 0.8) {
                  part0.Red = (float)(rgb.x * 0.6 + 0.4);
                  part0.Green = (float)(rgb.y * 0.6 + 0.4);
                  part0.Blue = (float)(rgb.z * 0.6 + 0.4);
               }

               part0.alphaGlowing = true;
               part0.framecount = 16;
               part0.disableOnEnd = true;
               part0.animDelay = 1 + rand.nextInt(3);
               int lt2 = 30 + rand.nextInt(30);
               float scl2 = (0.04F + rand.nextFloat() * 0.02F) * scaleMultiplier;
               TrailParticle partx = new TrailParticle(
                  present_random_5,
                  scl2,
                  0.025F * particlesGravityMult,
                  lt2,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX * 1.1F,
                  (float)motionY * 1.1F,
                  (float)motionZ * 1.1F,
                  (float)rgb.x,
                  (float)rgb.y,
                  (float)rgb.z,
                  true,
                  rand.nextInt(360),
                  true,
                  5.0F,
                  shard_trace,
                  scl2 / 2.0F,
                  240,
                  (float)rgb.x,
                  (float)rgb.y,
                  (float)rgb.z,
                  0.1F,
                  3,
                  -0.15F,
                  9999.0F
               );
               partx.onlyHorizontal = true;
               partx.alphaGlowing = true;
               partx.TalphaGlowing = true;
               partx.tracker = new ParticleTracker.TrackerSpawnEntity(part0, 0, true);
               partx.dropMode = true;
               world.spawnEntity(partx);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_pleasure;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         Weapons.setPotionIfEntityLB(entity, PotionEffects.RAINBOW, 400, 0);
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase creature = (EntityLivingBase)entity;
            creature.removePotionEffect(PotionEffects.BLOOD_THIRST);
            creature.removePotionEffect(PotionEffects.BERSERK);
            creature.removePotionEffect(PotionEffects.WINTER_CURSE);
            creature.removePotionEffect(MobEffects.BLINDNESS);
            if (entity instanceof EntityLiving) {
               EntityLiving living = (EntityLiving)entity;
               living.setAttackTarget(null);
            }
         }
      }
   }

   public static class ShardTypePoison extends ShardType {
      public static ResourceLocation acid_splash7 = new ResourceLocation("arpg:textures/acid_splash7.png");
      public static ParticleTracker.TrackerRotate[] ptrs = new ParticleTracker.TrackerRotate[]{
         new ParticleTracker.TrackerRotate(5),
         new ParticleTracker.TrackerRotate(-5),
         new ParticleTracker.TrackerRotate(8),
         new ParticleTracker.TrackerRotate(-8)
      };

      public ShardTypePoison(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.125, 0.0, -0.125, 0.125, 0.6875, 0.125);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            int lt = 40 + rand.nextInt(50);
            float scl = (0.02F + rand.nextFloat() * 0.01F) * 5.3F * scaleMultiplier;
            AnimatedGParticle part = new AnimatedGParticle(
               acid_splash7,
               scl,
               0.023F * particlesGravityMult,
               lt,
               180,
               world,
               x,
               y,
               z,
               (float)motionX,
               (float)motionY,
               (float)motionZ,
               1.0F,
               1.0F,
               1.0F,
               true,
               rand.nextInt(360),
               true,
               3.0F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.framecount = 5;
            part.animDelay = 3 + rand.nextInt(4);
            part.disableOnEnd = false;
            part.stopOnFrame = 4;
            part.tracker = ptrs[rand.nextInt(4)];
            world.spawnEntity(part);
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_poison;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         boolean continie = true;
         if (entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem)entity;
            if (entityItem.getItem().getItem() == ItemsRegister.PHOTORESISTEDPLATE && damagePosition instanceof Vec3d) {
               Vec3d vec = (Vec3d)damagePosition;
               Vec3d subtraction = vec.subtract(entityItem.getPositionVector());
               double angle = GetMOP.getAngleBetweenVectors(new Vec3d(0.0, 1.0, 0.0), subtraction, 1.0, subtraction.length());
               if (angle < 60.0) {
                  entityItem.setItem(new ItemStack(ItemsRegister.LITOGRAPHEDPLATE));
                  EntityItem newitem = new EntityItem(
                     entityItem.world,
                     entityItem.posX,
                     entityItem.posY,
                     entityItem.posZ,
                     new ItemStack(ItemsRegister.PROCESSORPATTERN)
                  );
                  entityItem.world.spawnEntity(newitem);
                  newitem.setPickupDelay(40);
                  entityItem.setPickupDelay(40);
                  newitem.motionX /= 2.0;
                  newitem.motionY /= 2.0;
                  newitem.motionZ /= 2.0;
                  newitem.velocityChanged = true;
                  continie = false;
               }
            }
         }

         if (continie) {
            dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 4.0F, 0.0F, WeaponDamage.toxin);
            int toxin = Math.max((int)elementAmount, 0) - 1;
            if (toxin >= 0) {
               Weapons.setPotionIfEntityLB(entity, PotionEffects.TOXIN, 50, toxin);
            }

            Weapons.setPotionIfEntityLB(entity, MobEffects.POISON, 600, Math.max((int)(elementAmount / 3.0F), 0));
            DeathEffects.applyDeathEffect(entity, DeathEffects.DE_COLOREDACID, 1.0F);
         }
      }
   }

   public static class ShardTypeVoid extends ShardType {
      public static ResourceLocation lava_drop = new ResourceLocation("arpg:textures/lava_drop.png");
      public static ResourceLocation void_parts_1 = new ResourceLocation("arpg:textures/void_parts_1.png");
      public static ResourceLocation void_parts_2 = new ResourceLocation("arpg:textures/void_parts_2.png");
      ParticleTracker.TrackerVoidRender ptvr = new ParticleTracker.TrackerVoidRender(4.0F, 0.0F, 25.0F);

      public ShardTypeVoid(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.125, 0.0, -0.125, 0.125, 0.9375, 0.125);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.6F) {
               int lt = 20 + rand.nextInt(40);
               float scl = (0.06F + rand.nextFloat() * 0.03F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  lava_drop, scl, 0.0F * particlesGravityMult, lt, 0, world, x, y, z, (float)motionX, (float)motionY, (float)motionZ, 0.0F, 0.0F, 0.0F, true, 0
               );
               part.isPushedByLiquids = false;
               part.tracker = this.ptvr;
               world.spawnEntity(part);
            } else {
               Vec3d motionvec = new Vec3d(motionX, motionY, motionZ);
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motionvec);
               int lt = 10 + rand.nextInt(30);
               float scl = (0.6F + rand.nextFloat() * 0.35F) * scaleMultiplier;
               Vec3d posAdd = motionvec.normalize().scale(scl / 1.45);
               AnimatedGParticle part = new AnimatedGParticle(
                  rand.nextFloat() < 0.5F ? void_parts_1 : void_parts_2,
                  scl,
                  0.0F * particlesGravityMult,
                  lt,
                  0,
                  world,
                  x + posAdd.x,
                  y + posAdd.y,
                  z + posAdd.z,
                  (float)motionX / 1.2F,
                  (float)motionY / 1.2F,
                  (float)motionZ / 1.2F,
                  0.0F,
                  0.0F,
                  0.0F,
                  false,
                  (int)(-pitchYaw.x + 135.0)
               );
               part.scaleTickAdding = -scl / lt;
               part.rotationPitchYaw = new Vec2f(0.0F, (float)pitchYaw.y + 90.0F);
               part.framecount = 32;
               part.disableOnEnd = false;
               part.animDelay = 2 + rand.nextInt(3);
               world.spawnEntity(part);
            }
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_void;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 7.0F, -0.2F * ManaBuffer.TICKRATE, WeaponDamage.portal);
      }
   }

   public static class ShardTypeWater extends ShardType {
      public static ResourceLocation[] waters = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/water1.png"), new ResourceLocation("arpg:textures/water2.png"), new ResourceLocation("arpg:textures/water3.png")
      };
      public static ResourceLocation water_splash = new ResourceLocation("arpg:textures/water_splash.png");
      public static ParticleTracker.TrackerChangeTexOnDropAnim tctod = new ParticleTracker.TrackerChangeTexOnDropAnim(water_splash, false, false, 8, 2, true);

      public ShardTypeWater(String name, int id, float colorR, float colorG, float colorB, CrystalSphereTESR.RenderShardsEffect renderInSphere) {
         super(name, id, colorR, colorG, colorB, renderInSphere);
         this.vialBoundingBox = new AxisAlignedBB(-0.1875, 0.0, -0.1875, 0.1875, 0.6875, 0.1875);
      }

      @Override
      public void spawnNativeParticle(
         World world, float scaleMultiplier, double x, double y, double z, double motionX, double motionY, double motionZ, boolean sparkle
      ) {
         if (sparkle) {
            if (rand.nextFloat() < 0.7F) {
               int lt = 7 + rand.nextInt(12);
               float scl = (0.04F + rand.nextFloat() * 0.02F) * scaleMultiplier;
               GUNParticle part = new GUNParticle(
                  rand.nextFloat() < 0.5 ? BlowholeShoot.bubbleglow1 : BlowholeShoot.bubbleglow2,
                  scl,
                  0.013F * particlesGravityMult,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)motionX * 1.8F,
                  (float)motionY * 1.8F,
                  (float)motionZ * 1.8F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  rand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               world.spawnEntity(part);
            }

            int tex = rand.nextInt(3);
            int lt = 35 + rand.nextInt(25);
            float scl = (0.05F + rand.nextFloat() * 0.1F) * scaleMultiplier;
            AnimatedGParticle part = new AnimatedGParticle(
               waters[tex],
               scl,
               0.018F * particlesGravityMult,
               lt,
               -1,
               world,
               x,
               y,
               z,
               (float)motionX,
               (float)motionY,
               (float)motionZ,
               1.0F,
               1.0F,
               1.0F,
               true,
               rand.nextInt(21) - 10,
               true,
               3.0F
            );
            part.framecount = tex == 2 ? 26 : 16;
            part.animDelay = 1 + rand.nextInt(4);
            part.disableOnEnd = false;
            part.tracker = tctod;
            part.dropMode = true;
            part.scaleTickDropAdding = scl * 1.2F;
            part.scaleMax = scl * 2.2F;
            world.spawnEntity(part);
         }
      }

      @Override
      public SoundEvent getLoopedBeamSound() {
         return Sounds.mirror_beam_water;
      }

      @Override
      public void onAffectEntity(Object dealer, @Nullable Entity projectile, Entity entity, Object damagePosition, float elementAmount) {
         if (rand.nextFloat() < 0.25) {
            dealShardDamage(dealer, projectile, entity, damagePosition, elementAmount * 4.0F, (float)Math.sqrt(elementAmount * 5.0F), WeaponDamage.water);
         } else {
            WeaponDamage damageSource = new WeaponDamage(null, null, projectile, false, false, damagePosition, WeaponDamage.water);
            if (damageSource.damagePosition != null) {
               SuperKnockback.applyKnockback(
                  entity,
                  (float)Math.sqrt(elementAmount * 5.0F),
                  damageSource.damagePosition.x,
                  damageSource.damagePosition.y,
                  damageSource.damagePosition.z
               );
            }
         }

         Weapons.doWetEntity(entity);
      }
   }
}

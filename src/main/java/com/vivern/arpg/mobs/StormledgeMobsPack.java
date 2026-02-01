package com.vivern.arpg.mobs;

import com.vivern.arpg.elements.models.ModelsStormledgeMob;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.main.BloodType;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.renders.GUNParticle;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class StormledgeMobsPack {
   public static String mobsteam = "stormledge.mob.team";

   public static void init() {
      AbstractMob.addToRegister(Skyguard.class, "Skyguard", 2504551, 8445951);
      AbstractMob.addToRegister(Screenguard.class, "Screenguard", 6450337, 7386367);
      AbstractMob.addToRegister(Thunderbird.class, "Thunderbird", 1320, 2883583);
      AbstractMob.addToRegister(Zarpion.class, "Zarpion", 1516604, 15176191);
      AbstractMob.addToRegister(Gust.class, "Gust", 14670051, 15777405);
      AbstractMob.addToRegister(Windbreak.class, "Windbreak", 13886450, 16766296);
      AbstractMob.addToRegister(Cloudbug.class, "Cloudbug", 12831711, 4270721);
      AbstractMob.addToRegister(Homingbird.class, "Homingbird", 10813431, 1447462);
      AbstractMob.addToRegister(CloudEater.class, "Cloud Eater", 3355232, 16301402);
      AbstractMob.addToRegister(BossOphanim.class, "Boss Ophanim", 12624099, 16108398, 128, 1);
      AbstractMob.addToRegister(OphanimGuard.class, "Ophanim Guard", 12624099, 16755551, 128, 1);
   }

   public static void initRender() {
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.ThunderbirdModel(),
            new ResourceLocation("arpg:textures/thunderbird_model_tex.png"),
            1.2F,
            Thunderbird.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.ZarpionModel(), new ResourceLocation("arpg:textures/zarpion_model_tex.png"), 0.4F, Zarpion.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.GustModel(), new ResourceLocation("arpg:textures/gust_model_tex.png"), 0.3F, Gust.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.WindbreakModel(), new ResourceLocation("arpg:textures/windbreak_model_tex.png"), 0.4F, Windbreak.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.SkyGuardModel(),
            new ResourceLocation("arpg:textures/sky_screen_guard_model_tex.png"),
            0.3F,
            Skyguard.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.ScreenGuardModel(),
            new ResourceLocation("arpg:textures/sky_screen_guard_model_tex.png"),
            0.3F,
            Screenguard.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.CloudbugModel(), new ResourceLocation("arpg:textures/cloudbug_model_tex.png"), 0.25F, Cloudbug.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.HomingbirdModel(),
            new ResourceLocation("arpg:textures/homingbird_model_tex.png"),
            0.2F,
            Homingbird.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.CloudEaterModel(),
            new ResourceLocation("arpg:textures/cloud_eater_model_tex.png"),
            0.7F,
            CloudEater.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.BossOphanimModel(), new ResourceLocation("arpg:textures/boss_ophanim_model_tex.png"), 2.0F, BossOphanim.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.OphanimGuardModel(),
            new ResourceLocation("arpg:textures/ophanim_guard_model_tex.png"),
            0.4F,
            OphanimGuard.class
         )
      );
   }

   public static class CloudEater extends AbstractMob {
      public int ticksCalculate = 30;
      public int dashDelay = 20;
      public int maxdashDelay = 40;
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/circle2.png");
      public static ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle2.png");

      public CloudEater(World world) {
         super(world, 1.75F, 1.5F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_storm_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(380.0, 48.0, 21.0, 0.07, 0.0, 9.0, 0.45, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 1.0F, 0, 1, 3, 0),
               new MobDrop(ItemsRegister.THUNDERSTONE, 0.55F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.TANK, 6);
         this.soul = Soul.IONIZED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.2) {
            ShardType.spawnShards(ShardType.AIR, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.VOID, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      protected float getSoundPitch() {
         return 0.86F;
      }

      public float getEyeHeight() {
         return this.height * 0.45F;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.dashDelay <= 0) {
               if (!this.isAIDisabled() && !this.isPotionActive(PotionEffects.STUN) && this.getHealth() > 0.0F) {
                  for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, this, 8.0, this)) {
                     if (entity instanceof EntityThrowable) {
                        EntityThrowable proj = (EntityThrowable)entity;
                        if (Team.checkIsOpponent(proj.getThrower(), this)) {
                           AxisAlignedBB aabb = proj.getEntityBoundingBox();
                           Vec3d velocity = new Vec3d(proj.motionX, proj.motionY, proj.motionZ);
                           float grav = proj.hasNoGravity() ? 0.0F : this.getgravityvelocity(proj);

                           for (int i = 0; i < this.ticksCalculate; i++) {
                              aabb = aabb.offset(velocity);
                              if (aabb.intersects(this.getEntityBoundingBox())) {
                                 this.dashOutOf(proj);
                                 return;
                              }

                              velocity = velocity.add(0.0, -grav, 0.0);
                           }
                        }
                     } else if (entity instanceof EntityArrow) {
                        EntityArrow proj = (EntityArrow)entity;
                        if (Team.checkIsOpponent(proj.shootingEntity, this)) {
                           AxisAlignedBB aabb = proj.getEntityBoundingBox();
                           Vec3d velocity = new Vec3d(proj.motionX, proj.motionY, proj.motionZ);
                           float grav = proj.hasNoGravity() ? 0.0F : 0.05F;

                           for (int i = 0; i < this.ticksCalculate; i++) {
                              aabb = aabb.offset(velocity);
                              if (aabb.intersects(this.getEntityBoundingBox())) {
                                 this.dashOutOf(proj);
                                 return;
                              }

                              velocity = velocity.add(0.0, -grav, 0.0);
                           }
                        }
                     } else if (entity instanceof IProjectile) {
                        if (Team.checkIsOpponent(entity, this)) {
                           AxisAlignedBB aabb = entity.getEntityBoundingBox();
                           Vec3d velocity = new Vec3d(entity.motionX, entity.motionY, entity.motionZ);

                           for (int i = 0; i < this.ticksCalculate; i++) {
                              aabb = aabb.offset(velocity);
                              if (aabb.intersects(this.getEntityBoundingBox())) {
                                 this.dashOutOf(entity);
                                 return;
                              }
                           }
                        }
                     } else if (entity instanceof EntityFireball) {
                        EntityFireball proj = (EntityFireball)entity;
                        if (Team.checkIsOpponent(proj.shootingEntity, this)) {
                           AxisAlignedBB aabb = proj.getEntityBoundingBox();
                           Vec3d velocity = new Vec3d(proj.motionX, proj.motionY, proj.motionZ);
                           float mf = this.getmotionfactor(proj);

                           for (int ix = 0; ix < this.ticksCalculate; ix++) {
                              aabb = aabb.offset(velocity);
                              if (aabb.intersects(this.getEntityBoundingBox())) {
                                 this.dashOutOf(proj);
                                 return;
                              }

                              velocity = new Vec3d(
                                 (velocity.x + proj.accelerationX) * mf,
                                 (velocity.y + proj.accelerationY) * mf,
                                 (velocity.z + proj.accelerationZ) * mf
                              );
                           }
                        }
                     }

                     if (entity instanceof EntityShulkerBullet) {
                        Object obj = ReflectionHelper.getPrivateValue(EntityShulkerBullet.class, (EntityShulkerBullet)entity, new String[]{"owner"});
                        if ((obj == null || !(obj instanceof EntityLivingBase) || Team.checkIsOpponent((EntityLivingBase)obj, this))
                           && this.getDistanceSq(entity) < 6.0) {
                           this.dashOutOf(entity);
                           return;
                        }
                     }
                  }
               }
            } else {
               this.dashDelay--;
            }
         }
      }

      public void dashOutOf(Entity projectile) {
         this.dashDelay = this.maxdashDelay;
         Vec3d vectorFromto = new Vec3d(
               projectile.posX - this.posX, projectile.posY - this.posY, projectile.posZ - this.posZ
            )
            .normalize();
         Vec3d vectorPer = new Vec3d(-vectorFromto.y, vectorFromto.x, -vectorFromto.z);
         Vec3d vectorDash = GetMOP.rotateVecAroundAxis(vectorPer, vectorFromto, (float)(this.rand.nextFloat() * Math.PI * 2.0));
         if (!this.world.collidesWithAnyBlock(this.getEntityBoundingBox().offset(vectorDash.scale(2.5)))) {
            this.setPosition(
               this.posX + vectorDash.x * 2.5,
               this.posY + vectorDash.y * 2.5,
               this.posZ + vectorDash.z * 2.5
            );
         }

         this.motionX = this.motionX + vectorDash.x;
         this.motionY = this.motionY + vectorDash.y;
         this.motionZ = this.motionZ + vectorDash.z;
         this.velocityChanged = true;
         this.world.setEntityState(this, (byte)11);
      }

      public float getgravityvelocity(EntityThrowable projectile) {
         try {
            Method method = projectile.getClass().getDeclaredMethod("getGravityVelocity");
            Object objkj = method.invoke(projectile);
            if (objkj instanceof Float) {
               return (Float)objkj;
            }
         } catch (Exception var4) {
         }

         return 0.03F;
      }

      public float getmotionfactor(EntityFireball projectile) {
         try {
            Method method = projectile.getClass().getDeclaredMethod("getMotionFactor");
            Object objkj = method.invoke(projectile);
            if (objkj instanceof Float) {
               return (Float)objkj;
            }
         } catch (Exception var4) {
         }

         return 0.95F;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         Weapons.setPotionIfEntityLB(entityIn, MobEffects.SLOWNESS, 35, 4);
         Weapons.setPotionIfEntityLB(entityIn, PotionEffects.BROKEN_ARMOR, 350, 0);
         return super.attackEntityAsMob(entityIn);
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            Vec3d poss = GetMOP.PosRayTrace(3.0, 1.0F, this, 0.8F, 0.8F);
            int countOfParticles = 16;
            float R = 0.2F + this.rand.nextFloat() / 40.0F;

            for (int i = 0; i < countOfParticles; i++) {
               float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
               float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (this.rand.nextBoolean()) {
                  Z *= -1.0F;
               }

               float scale = 0.2F + this.rand.nextFloat() / 6.0F;
               GUNParticle particle = new GUNParticle(
                  sparkle,
                  scale,
                  0.007F,
                  40,
                  240,
                  this.world,
                  poss.x,
                  poss.y,
                  poss.z,
                  X,
                  Y,
                  Z,
                  0.9F,
                  1.0F,
                  1.0F,
                  true,
                  0
               );
               particle.alphaGlowing = true;
               particle.scaleTickAdding = -scale / 40.0F;
               this.world.spawnEntity(particle);
            }

            GUNParticle particle = new GUNParticle(
               texture,
               0.1F,
               0.0F,
               10,
               240,
               this.world,
               poss.x,
               poss.y,
               poss.z,
               0.0F,
               0.0F,
               0.0F,
               0.5F + this.rand.nextFloat() * 0.3F,
               0.8F - this.rand.nextFloat() * 0.15F,
               1.0F,
               true,
               0
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = 0.8F;
            particle.alphaTickAdding = -0.08F;
            this.world.spawnEntity(particle);
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.electric_impact,
                  SoundCategory.HOSTILE,
                  1.5F,
                  1.3F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         EntityAIAttackSweep swe = new EntityAIAttackSweep(this, 50, 0.8F, 3.0F, 4.0F, 10, true, 10.0F, 5.0F, Sounds.mob_ghost_hurt).setTriggerOnStart();
         swe.swoshSound = Sounds.cloud_eater_attack;
         swe.hitSound = Sounds.supersonic_clap;
         swe.swoshSoundVolume = 1.0F;
         swe.hitSoundVolume = 3.0F;
         swe.sendId = 8;
         this.tasks.addTask(1, new EntityAIRayLogicFly(this));
         this.tasks.addTask(2, swe);
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Cloudbug extends AbstractMob {
      public Cloudbug(World world) {
         super(world, 0.95F, 1.0F);
         this.hurtSound = Sounds.mob_alienfly_hurt;
         this.deathSound = Sounds.mob_alienfly_death;
         this.livingSound = Sounds.mob_storm_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(65.0, 48.0, 13.0, 0.16, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 0.38F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.THUNDERSTONE, 0.07F, 0, 1, 1, 0)
            }
         );
         this.var2 = 0.0F;
         if (world.isRemote) {
            int c = 1;
            int cl = 0;
            cl |= c << this.rand.nextInt(8);
            cl |= c << this.rand.nextInt(8);
            cl |= c << this.rand.nextInt(8);
            this.var1 = cl;
            int a = 0;
            a |= this.rand.nextInt(16);
            a |= this.rand.nextInt(16) << 4;
            a |= this.rand.nextInt(16) << 8;
            this.var2 = a;
         }

         this.setRoleValues(EnumMobRole.WEAK_SOLDIER, 6);
         this.soul = Soul.IONIZED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.AIR, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      @Override
      public void playLivingSound() {
         SoundEvent soundevent = this.getAmbientSound();
         if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), 1.4F);
         }
      }

      public double getMountedYOffset() {
         return 1.5;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.electric_impact,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 3.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 4, 15.0F, 1, false, 5.0F, 4.0F));
         this.tasks
            .addTask(
               2,
               new EntityAIArcAttack(this, 50, 3, 4.0F, 1.5F, 1.2F, 2, (byte)8)
                  .setPotionEffect(new PotionEffect(PotionEffects.SHOCK, 60), 0.4F)
                  .setMissToBlock(true, 4.0F, 4.0F)
            );
         this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }
   }

   public static class Gust extends AbstractMob {
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/impetus.png");
      public static ResourceLocation cloud2 = new ResourceLocation("arpg:textures/clouds3.png");
      public int cloudCooldown = 0;
      public int clouds = 10;

      public Gust(World world) {
         super(world, 0.8F, 1.6F);
         this.hurtSound = Sounds.mob_ghost_hurt;
         this.deathSound = Sounds.mob_ghost_death;
         this.livingSound = Sounds.mob_ghost_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(170.0, 32.0, 14.0, 0.065, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMBRASS, 0.75F, 0, 1, 4, 0),
               new MobDrop(ItemsRegister.SKYCRYSTALPIECE, 0.75F, 0, 1, 2, 0),
               new MobDrop(ItemsRegister.WINDNATURE, 0.37F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 6);
         this.soul = Soul.LIGHT;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.AIR, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WIND_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.clouds > 0) {
               if (this.ticksExisted % 7 == 0 && this.getAttackTarget() != null) {
                  EntityLivingBase attarg = this.getAttackTarget();
                  double d0 = this.getDistanceSq(attarg.posX, attarg.getEntityBoundingBox().minY, attarg.posZ);
                  boolean cansee = this.getEntitySenses().canSee(attarg);
                  if (cansee && d0 < 256.0 && d0 > 4.0) {
                     this.world.setEntityState(this, (byte)8);
                     HostileProjectiles.GustCloud entity = new HostileProjectiles.GustCloud(this.world, this);
                     entity.setPosition(attarg.posX, attarg.posY + 0.1F, attarg.posZ);
                     entity.damage = 17.0F;
                     this.world.spawnEntity(entity);
                     this.clouds--;
                  }
               }
            } else {
               this.cloudCooldown++;
            }

            if (this.cloudCooldown > 80) {
               this.clouds = 10;
               this.cloudCooldown = 0;
            }
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.electric_arc,
                  SoundCategory.HOSTILE,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 11) {
            for (int i = 0; i < 5; i++) {
               GUNParticle part = new GUNParticle(
                  cloud,
                  1.1F - i / 10.0F,
                  0.0F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.9F,
                  0.86F,
                  0.9F,
                  true,
                  this.rand.nextInt(360)
               );
               part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.2F - i / 8.0F, 0.0F);
               part.rotationPitchYaw = new Vec2f(this.rotationPitch, this.rotationYaw);
               part.alphaTickAdding = -0.04F - i * 0.01F;
               part.alphaGlowing = true;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               this.world.spawnEntity(part);
            }

            for (int i = 0; i < 6; i++) {
               GUNParticle part = new GUNParticle(
                  cloud2,
                  0.4F + this.rand.nextFloat() / 3.0F,
                  0.0F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.9F,
                  0.9F,
                  0.9F,
                  true,
                  this.rand.nextInt(360)
               );
               part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.1F - i / 8.0F, 6.0F);
               part.alphaTickAdding = -0.04F;
               part.alphaGlowing = true;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               this.world.spawnEntity(part);
            }
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 1, 1.0F, 1, false, 10.0F, 5.0F));
         this.tasks.addTask(2, new EntityAIForceAttack(this, 10.0F, 40, 4.5F, 0.0F, 2.5F, 0.4F, 5.0F, 1.2F).setSoundOnAttack(Sounds.impetus_1));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Homingbird extends AbstractMob {
      public Homingbird(World world) {
         super(world, 0.8F, 0.8F);
         this.hurtSound = Sounds.mob_alienfly_hurt;
         this.deathSound = Sounds.mob_alienfly_death;
         this.livingSound = Sounds.mob_alienfly_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(32.0, 48.0, 9.0, 0.18, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0);
         this.var2 = 180.0F;
         this.var3 = 180.0F;
         this.setRoleValues(EnumMobRole.SWARMER, 6);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.2) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote && this.rand.nextFloat() < 0.025F) {
            this.var3 = this.rand.nextInt(360);
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRayLogicFly(this));
         this.tasks.addTask(2, new EntityAIDash(this, 100, 3.0F, 1.0F, 3.0F, false, 0.0F));
         this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }
   }

   public static class OphanimGuard extends AbstractMob {
      public int type = 0;
      public int fireDelayMax = 20;
      public int fireCooldownMax = 100;
      public int fireDelay = 0;
      public int fireCooldown = 40;
      public int charges = 0;
      public int chargesMax = 10;
      public boolean despawnIfNoBoss = false;
      public BossOphanim boss;
      public boolean isShooting = false;
      public int timeFarFromBoss = 0;

      public OphanimGuard(World world) {
         super(world, 1.8F, 2.8F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_storm_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(1000.0, 64.0, 22.0, 0.1, 0.0, 6.0, 0.6, 0.1, 0.0, 0.0);
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 6);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      protected float getSoundVolume() {
         return 1.5F;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         return source == DamageSource.IN_WALL ? false : super.attackEntityFrom(source, amount);
      }

      public float getEyeHeight() {
         return this.height * 0.66F;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setInteger("fireCooldownMax", this.fireCooldownMax);
         compound.setInteger("type", this.type);
         compound.setBoolean("despawnIfNoBoss", this.despawnIfNoBoss);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("fireCooldownMax")) {
            this.fireCooldownMax = compound.getInteger("fireCooldownMax");
         }

         if (compound.hasKey("type")) {
            this.type = compound.getInteger("type");
         }

         if (compound.hasKey("despawnIfNoBoss")) {
            this.despawnIfNoBoss = compound.getBoolean("despawnIfNoBoss");
         }
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         this.type = 1 + this.rand.nextInt(3);
         this.fireCooldownMax = 100 + this.rand.nextInt(40);
         return super.onInitialSpawn(difficulty, livingdata);
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.PlasmaRing entity = new HostileProjectiles.PlasmaRing(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.8F, 0.0F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            entity.type = this.type;
            entity.radius = 3.5F;
            this.world.spawnEntity(entity);
         }
      }

      protected boolean canDespawn() {
         return this.despawnIfNoBoss ? false : super.canDespawn();
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.despawnIfNoBoss && this.boss == null) {
               this.setDead();
            }

            if (this.boss != null) {
               if (this.getDistanceSq(this.boss) >= 70.0) {
                  this.timeFarFromBoss++;
               } else {
                  this.timeFarFromBoss = 0;
               }
            }

            if (this.ticksExisted <= 2 || this.ticksExisted % 40 == 0) {
               this.world.setEntityState(this, (byte)(this.type + 8));
               if (this.boss != null && this.getAttackTarget() == null && this.boss.getAttackTarget() != null) {
                  this.setAttackTarget(this.boss.getAttackTarget());
               }
            }

            if (!this.isAIDisabled()) {
               if (this.fireCooldown <= 0) {
                  if (this.getAttackTarget() != null) {
                     double prefirePower = 2.0;
                     EntityLivingBase at = this.getAttackTarget();
                     double xx = at.posX + at.motionX * prefirePower;
                     double yy = at.posY + at.motionY * prefirePower;
                     double zz = at.posZ + at.motionZ * prefirePower;
                     this.getLookHelper().setLookPosition(xx, yy, zz, 90.0F, 90.0F);
                     this.lookTo(xx, yy, zz, 90.0F, 90.0F);
                     this.isShooting = true;
                     if (this.getEntitySenses().canSee(at)) {
                        if (this.fireDelay <= 0) {
                           this.fireDelay = this.fireDelayMax;
                           if (this.charges > 0) {
                              this.shoot();
                              this.charges--;
                           } else {
                              this.fireCooldown = this.fireCooldownMax;
                              this.isShooting = false;
                           }
                        } else {
                           this.fireDelay--;
                        }
                     }
                  } else {
                     this.isShooting = false;
                  }
               } else {
                  this.fireCooldown--;
                  if (this.ticksExisted % 20 == 0 && this.charges < this.chargesMax) {
                     this.charges++;
                  }
               }
            }
         }
      }

      public void lookTo(double x, double y, double z, float maxYawIncrease, float maxPitchIncrease) {
         double d0 = x - this.posX;
         double d1 = y - this.posY;
         double d2 = z - this.posZ;
         double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
         float f = (float)(MathHelper.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0F;
         float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180.0 / Math.PI)));
         this.rotationPitch = this.updateRotation(this.rotationPitch, f1, maxPitchIncrease);
         this.rotationYaw = this.updateRotation(this.rotationYaw, f, maxYawIncrease);
      }

      public float updateRotation(float angle, float targetAngle, float maxIncrease) {
         float f = MathHelper.wrapDegrees(targetAngle - angle);
         if (f > maxIncrease) {
            f = maxIncrease;
         }

         if (f < -maxIncrease) {
            f = -maxIncrease;
         }

         return angle + f;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id >= 8 && id <= 11) {
            this.type = id - 8;
            this.var1 = this.type;
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.ophanim_guard_attack,
                  SoundCategory.HOSTILE,
                  2.3F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Screenguard extends AbstractMob {
      public static float screenProtection = 35.0F;
      public int noScreenTime = 0;
      public int noAttackTime = 0;
      public boolean disableNextTick = false;

      public Screenguard(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(135.0, 48.0, 15.0, 0.1, 0.0, 5.0, 0.5, 0.2, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 0.45F, 0, 1, 4, 0),
               new MobDrop(ItemsRegister.THUNDERSTONE, 0.25F, 0, 1, 1, 0)
            }
         );
         this.var2 = 0.0F;
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 6);
         this.soul = Soul.IONIZED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            for (int i = 0; i < 3; i++) {
               float ang = MathHelper.wrapDegrees(this.var2 + this.rotationYaw + 120 * i);
               Vec3d vec = GetMOP.RotatedPosRayTrace(15.0, 1.0F, this, 0.7, 0.6, this.rotationPitch, ang);
               double damageRadius = 1.0;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  vec.x - damageRadius,
                  vec.y - damageRadius,
                  vec.z - damageRadius,
                  vec.x + damageRadius,
                  vec.y + damageRadius,
                  vec.z + damageRadius
               );
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (Team.checkIsOpponent(this, entitylivingbase)) {
                        this.world.setEntityState(this, (byte)8);
                        HostileProjectiles.SkyGuardShoot entity = new HostileProjectiles.SkyGuardShoot(this.world, this);
                        entity.shoot(this, this.rotationPitch, ang, 0.0F, 1.4F, 0.2F);
                        entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                        this.world.spawnEntity(entity);
                        this.noAttackTime = 0;
                     }
                  }
               }
            }
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && this.var2 % 360.0F == 0.0F) {
            this.world.setEntityState(this, (byte)9);
         }

         if (!this.world.isRemote && (this.ticksExisted % 40 == 0 || this.ticksExisted <= 1)) {
            this.world.setEntityState(this, (byte)(this.noScreenTime > 0 ? 14 : 15));
         }

         this.var2 += 4.0F;
         if (this.noScreenTime > 0) {
            this.noScreenTime--;
            if (this.noScreenTime == 0) {
               this.world.setEntityState(this, (byte)13);
            }
         }

         if (this.ticksExisted % 2 == 0 && this.getAttackTarget() != null) {
            this.noAttackTime += 2;
            this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 180.0F, 180.0F);
            this.shoot();
            if (this.noAttackTime > 80) {
               this.motionY = this.motionY + (this.getAttackTarget().posY - this.posY) / 8.0;
               this.velocityChanged = true;
            }
         }

         if (!this.world.isRemote && this.disableNextTick) {
            this.noScreenTime = 30;
            this.disableNextTick = false;
            this.world.setEntityState(this, (byte)12);
         }
      }

      public float getCollisionBorderSize() {
         return this.noScreenTime == 0 ? 0.8F : 0.0F;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (this.noScreenTime == 0 && amount > 0.0F) {
            amount -= screenProtection;
            this.disableNextTick = true;
         }

         return super.attackEntityFrom(source, amount);
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stormledge_shoot_b,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 9) {
            this.var2 = 0.0F;
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield,
                  SoundCategory.HOSTILE,
                  0.6F,
                  0.95F + this.rand.nextFloat() / 7.0F,
                  false
               );
         }

         if (id == 12) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield_impact,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.8F + this.rand.nextFloat() / 4.0F,
                  false
               );
            this.var1 = 0;
         }

         if (id == 13) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield_trigger,
                  SoundCategory.HOSTILE,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.var1 = 1;
         }

         if (id == 14) {
            this.var1 = 0;
         }

         if (id == 15) {
            this.var1 = 1;
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 4, 15.0F, 1, false, 8.0F, 3.0F));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 20, 0.6F));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Skyguard extends AbstractMob {
      public boolean shieldsSpawned = false;

      public Skyguard(World world) {
         super(world, 0.8F, 1.0F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(125.0, 48.0, 15.0, 0.1, 0.0, 5.0, 0.75, 0.2, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 0.45F, 0, 1, 4, 0),
               new MobDrop(ItemsRegister.THUNDERSTONE, 0.2F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 6);
         this.soul = Soul.IONIZED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.SkyGuardShoot entity = new HostileProjectiles.SkyGuardShoot(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.2F, 0.8F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entity);
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && !this.shieldsSpawned) {
            HostileProjectiles.SkyGuardShield shield1 = new HostileProjectiles.SkyGuardShield(this.world, this);
            this.world.spawnEntity(shield1);
            HostileProjectiles.SkyGuardShield shield2 = new HostileProjectiles.SkyGuardShield(this.world, this);
            shield2.displace = 120;
            this.world.spawnEntity(shield2);
            HostileProjectiles.SkyGuardShield shield3 = new HostileProjectiles.SkyGuardShield(this.world, this);
            shield3.displace = 240;
            this.world.spawnEntity(shield3);
            this.shieldsSpawned = true;
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stormledge_shoot_b,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 23, 18.0F, 1, true, 8.0F, 3.0F));
         this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Thunderbird extends AbstractMob {
      public float rotateDirectPitch = 0.0F;
      public float rotateDirectYaw = 0.0F;
      public int roarCooldown = 0;

      public Thunderbird(World world) {
         super(world, 1.7F, 1.7F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(2000.0, 84.0, 15.0, 0.25, 4.0, 6.0, 0.85, 0.4, 0.0, 0.2);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 1.0F, 0, 2, 8, 0),
               new MobDrop(ItemsRegister.THUNDERSTONE, 1.0F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.THUNDERBIRDFEATHER, 1.0F, 0, 4, 7, 0)
            }
         );
         this.var2 = 0.0F;
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.MINI_BOSS, 6);
         this.soul = Soul.NOBLE;
      }

      @Override
      public void dropShards() {
         ShardType.spawnShards(ShardType.ELECTRIC, this, 10.0F * dropShardsQuantity);
         ShardType.spawnShards(ShardType.AIR, this, 5.0F * dropShardsQuantity);
         ShardType.spawnShards(ShardType.FIRE, this, 5.0F * dropShardsQuantity);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      protected float getSoundPitch() {
         return 0.9F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.ThunderbirdShoot entity = new HostileProjectiles.ThunderbirdShoot(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.7F, 0.5F);
            entity.damage = 25.0F;
            this.world.spawnEntity(entity);
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            this.roarCooldown--;
            if (this.var1 == 2 && this.rand.nextFloat() < 0.1 && this.animations[3] <= 60) {
               this.triggerAnimation(-4);
               if (this.rand.nextFloat() < 0.2) {
                  this.world.setEntityState(this, (byte)3);
               }
            }

            if (this.var1 == 1) {
               if (this.var2 < 1.0F) {
                  this.var2 = (float)(this.var2 + 0.1);
               }
            } else if (this.var2 > 0.0F) {
               this.var2 = (float)(this.var2 - 0.1);
            }

            if (this.var1 == 3) {
               if (this.animations[3] <= 83) {
                  this.triggerAnimation(-4);
               }

               if (this.var3 < 1.0F) {
                  this.var3 = (float)(this.var3 + 0.1);
               }
            } else if (this.var3 > 0.0F) {
               this.var3 = (float)(this.var3 - 0.1);
            }
         }

         if (this.var1 == 2) {
            float fun = (MathHelper.wrapDegrees(this.rotateDirectYaw) - MathHelper.wrapDegrees(this.rotationYaw) + 360.0F) % 360.0F;
            float derection;
            if (fun < 180.0F) {
               derection = MathHelper.clamp(Math.abs(fun), 0.0F, 10.0F);
            } else {
               derection = -MathHelper.clamp(Math.abs(fun), 0.0F, 10.0F);
            }

            this.rotationYaw += derection;
            fun = (MathHelper.wrapDegrees(this.rotateDirectPitch) - MathHelper.wrapDegrees(this.rotationPitch) + 360.0F) % 360.0F;
            if (fun < 180.0F) {
               derection = Math.min(4.0F, Math.abs(fun));
            } else {
               derection = -Math.min(4.0F, Math.abs(fun));
            }

            this.rotationPitch += derection;
            Vec3d vecpw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
            this.rotateDirectPitch = (float)vecpw.x;
            this.rotateDirectYaw = (float)vecpw.y;
         }

         if (!this.world.isRemote) {
            double speedSq = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
            if (speedSq > 0.25) {
               AxisAlignedBB aabb = this.getEntityBoundingBox()
                  .offset(this.motionX, this.motionY < -0.7 ? this.motionY : 0.0, this.motionZ);
               List<BlockPos> list = GetMOP.getBlockPosesCollidesAABB(this.world, aabb, false);
               boolean destroys = false;
               if (!list.isEmpty()) {
                  this.motionX *= 0.6;
                  this.motionY *= 0.6;
                  this.motionZ *= 0.6;

                  for (BlockPos pos : list) {
                     if (Weapons.easyBreakBlockFor(this.world, 10.0F, pos)) {
                        this.world.destroyBlock(pos, true);
                        destroys = true;
                     }
                  }

                  if (destroys) {
                     this.world.setEntityState(this, (byte)7);
                  }
               }
            }
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.SHOCK
               && potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stormledge_shoot_a,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.ENTITY_WITHER_BREAK_BLOCK,
                  SoundCategory.HOSTILE,
                  1.3F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id >= 10 && id <= 14) {
            this.var1 = id - 10;
            if (this.rand.nextFloat() < 0.25 && this.roarCooldown <= 0) {
               this.roarCooldown = 170;
               this.world
                  .playSound(
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.thunderbird_roar,
                     SoundCategory.HOSTILE,
                     2.5F,
                     0.8F + this.rand.nextFloat() * 0.4F,
                     false
                  );
            }
         }

         if (id == 15 && this.animations[3] <= 70) {
            this.triggerAnimation(-4);
         }

         if (id == 3 && this.roarCooldown <= 0) {
            this.roarCooldown = 170;
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.thunderbird_roar,
                  SoundCategory.HOSTILE,
                  2.5F,
                  0.8F + this.rand.nextFloat() * 0.4F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIBird(this, 100, 30.0F, 2, true, 8.0F, 3.0F, true));
         this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
         this.tasks
            .addTask(
               3,
               new EntityAIStrikeLightning(this, 130, 40.0F, 2.0F, 18.0F, 20, true, false, 60)
                  .setPotionEffect(new PotionEffect(PotionEffects.SHOCK, 80, 1))
                  .setSoundOnCharge(Sounds.thunderbird_charge)
            );
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }
   }

   public static class Windbreak extends AbstractMob {
      public int tornadoCooldown = 0;

      public Windbreak(World world) {
         super(world, 1.5F, 1.5F);
         this.hurtSound = Sounds.mob_ghost_hurt;
         this.deathSound = Sounds.mob_ghost_death;
         this.livingSound = Sounds.mob_ghost_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setNoGravity(true);
         this.setattributes(500.0, 48.0, 8.0, 0.05, 12.0, 1.0, 0.2, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMBRASS, 0.75F, 0, 2, 5, 0),
               new MobDrop(ItemsRegister.SKYCRYSTALPIECE, 0.75F, 0, 1, 3, 0),
               new MobDrop(ItemsRegister.WINDNATURE, 0.8F, 0, 1, 1, 0)
            }
         );
         this.var2 = 0.0F;
         this.var3 = 0.0F;
         this.setRoleValues(EnumMobRole.ELITE_ENEMY, 6);
         this.soul = Soul.LIGHT;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.AIR, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WIND_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote && this.getAttackTarget() != null) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.WindbreakShoot entity = new HostileProjectiles.WindbreakShoot(this.world, this);
            entity.target = this.getAttackTarget();
            entity.shoot(this, this.rotationPitch + 15.0F, this.rotationYaw + (this.rand.nextFloat() < 0.5 ? 60 : -60), 0.0F, 1.0F, 20.0F);
            entity.damage = 16.0F;
            this.world.spawnEntity(entity);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.tornadoCooldown <= 0) {
               if (this.getAttackTarget() != null) {
                  EntityLivingBase attarg = this.getAttackTarget();
                  double d0 = this.getDistanceSq(attarg.posX, attarg.getEntityBoundingBox().minY, attarg.posZ);
                  boolean cansee = this.getEntitySenses().canSee(attarg);
                  if (cansee && d0 < 400.0 && d0 > 16.0) {
                     this.world.setEntityState(this, (byte)8);
                     HostileProjectiles.Whirl entity = new HostileProjectiles.Whirl(this.world, this);

                     for (int i = 0; i < 10; i++) {
                        double posxx = attarg.posX + this.rand.nextInt(7) - 3.0;
                        double posyy = attarg.posY + this.rand.nextInt(2);
                        double poszz = attarg.posZ + this.rand.nextInt(7) - 3.0;
                        entity.setPosition(posxx, posyy, poszz);
                        if (entity.isNotColliding()) {
                           break;
                        }

                        if (i == 9) {
                           entity.setPosition(attarg.posX, attarg.posY, attarg.posZ);
                           if (!entity.isNotColliding()) {
                              entity.setPosition(this.posX, this.posY, this.posZ);
                           }
                        }
                     }

                     entity.damage = 10.0F;
                     this.world.spawnEntity(entity);
                     this.tornadoCooldown = 400;
                  }
               }
            } else {
               this.tornadoCooldown--;
            }
         } else {
            Vec3d motionVec = new Vec3d(this.motionX, 0.0, this.motionZ);
            motionVec = motionVec.normalize();
            Vec3d rotationVecR = GetMOP.PitchYawToVec3d(0.0F, this.rotationYaw + 90.0F).normalize();
            Vec3d rotationVecL = GetMOP.PitchYawToVec3d(0.0F, this.rotationYaw - 90.0F).normalize();
            double distR = motionVec.distanceTo(rotationVecR);
            if (distR < 1.0 && this.var3 < 0.5) {
               this.var3 = (float)(this.var3 + (1.0 - distR) * 0.07);
            }

            double distL = motionVec.distanceTo(rotationVecL);
            if (distL < 1.0 && this.var3 > -0.5) {
               this.var3 = (float)(this.var3 - (1.0 - distL) * 0.07);
            }

            this.var2 = (float)MathHelper.clamp(this.var2 - this.motionY * 0.2, -0.5, 0.5);
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.electric_arc,
                  SoundCategory.HOSTILE,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 75, 30.0F, 1, true, 7.0F, 7.0F).setBurst(true, 6, 0, false, 5, 0));
         this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Zarpion extends AbstractMob implements IEntitySynchronize {
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/voltrident_beam.png");
      public int WEAPON = 0;
      public float shieldPower = 0.0F;
      public int weaponCooldown = 0;
      public int arcCooldown = 0;
      public int arcTime = 0;

      public Zarpion(World world) {
         super(world, 0.7F, 1.7F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_storm_living;
         this.defaultteam = StormledgeMobsPack.mobsteam;
         this.setattributes(300.0, 48.0, 12.0, 0.1, 2.0, 6.0, 0.0, 0.1, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.NUGGETSTORMSTEEL, 0.5F, 0, 2, 4, 0), new MobDrop(ItemsRegister.THUNDERSTONE, 0.4F, 0, 1, 1, 0)
            }
         );
         this.var2 = 0.0F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 6);
         this.soul = Soul.IONIZED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.FIRE, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ELECTRIC_BLOOD;
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         this.WEAPON = this.rand.nextInt(4);
         return super.onInitialSpawn(difficulty, livingdata);
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         Vec3d pos2 = new Vec3d(x, y, z);
         Vec3d pos1 = new Vec3d(a, b, c);
         BetweenParticle laser = new BetweenParticle(
            this.world,
            texture,
            0.1F + this.rand.nextFloat() / 5.0F,
            240,
            1.0F,
            1.0F,
            1.0F,
            0.1F,
            pos1.distanceTo(pos2),
            10,
            0.2F,
            9999.0F,
            pos1,
            pos2
         );
         laser.alphaGlowing = true;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.electric_arc,
               SoundCategory.HOSTILE,
               0.7F,
               0.8F + this.rand.nextFloat() / 2.5F,
               false
            );
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.ZarpionBeam entity = new HostileProjectiles.ZarpionBeam(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.7F, 0.2F);
            entity.damage = 17.0F;
            this.world.spawnEntity(entity);
         }
      }

      public void shootBlaster() {
         this.world.setEntityState(this, (byte)9);
         HostileProjectiles.ZarpionBlaterShoot entity = new HostileProjectiles.ZarpionBlaterShoot(this.world, this);
         entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.1F, 2.0F);
         entity.setPosition(this.posX, this.posY + this.getEyeHeight() - 0.25, this.posZ);
         entity.damage = 14.0F;
         this.world.spawnEntity(entity);
      }

      public void shootGrenade() {
         this.world.setEntityState(this, (byte)10);
         HostileProjectiles.Grenade entity = new HostileProjectiles.Grenade(this.world, this);
         entity.shoot(this, this.rotationPitch - 17.0F, this.rotationYaw, 0.0F, 0.8F, 0.3F);
         entity.setPosition(this.posX, this.posY + this.getEyeHeight() - 0.25, this.posZ);
         entity.damage = 22.0F;
         this.world.spawnEntity(entity);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted <= 1) {
               this.world.setEntityState(this, (byte)(this.WEAPON + 64));
               if (this.WEAPON == 3) {
                  this.world.setEntityState(this, (byte)(this.shieldPower > 0.0F ? 13 : 14));
               }
            }

            if (this.ticksExisted % 40 == 0) {
               this.world.setEntityState(this, (byte)(this.WEAPON + 64));
               if (this.WEAPON == 3) {
                  this.world.setEntityState(this, (byte)(this.shieldPower > 0.0F ? 15 : 16));
               }
            }

            if (this.getAttackTarget() != null) {
               EntityLivingBase attarg = this.getAttackTarget();
               double d0 = this.getDistanceSq(attarg.posX, attarg.getEntityBoundingBox().minY, attarg.posZ);
               boolean cansee = this.getEntitySenses().canSee(attarg);
               if (this.weaponCooldown <= 0) {
                  Vec3d pwvec = this.getLookVec();
                  pwvec = pwvec.scale(Math.sqrt(d0));
                  double vX = attarg.posX - this.posX;
                  double vY = attarg.posY - this.posY;
                  double vZ = attarg.posZ - this.posZ;
                  if (cansee) {
                     if (this.WEAPON == 1 && d0 < 256.0 && pwvec.squareDistanceTo(vX, vY, vZ) < 0.9) {
                        this.shootBlaster();
                        this.weaponCooldown = 6;
                     }

                     if (this.WEAPON == 2 && d0 < 100.0 && pwvec.squareDistanceTo(vX, vY, vZ) < 3.0) {
                        this.shootGrenade();
                        this.weaponCooldown = 60;
                     }
                  }
               }

               if (this.arcCooldown <= 0 && this.ticksExisted % 5 == 0) {
                  if (d0 < 16.0 && cansee && this.arcTime < 120) {
                     IEntitySynchronize.sendSynchronize(
                        this,
                        32.0,
                        this.posX,
                        this.posY + this.height / 2.0F,
                        this.posZ,
                        attarg.posX,
                        attarg.posY + attarg.height / 2.0F,
                        attarg.posZ
                     );
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, this, WeaponDamage.electric),
                        (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                        this,
                        attarg,
                        true
                     );
                     attarg.hurtResistantTime = 0;
                     this.arcTime += 5;
                  } else if (this.arcTime > 0) {
                     this.arcCooldown = 170;
                     this.arcTime = 0;
                  }
               }
            }

            if (this.arcCooldown > 0) {
               this.arcCooldown--;
            }

            if (this.weaponCooldown > 0) {
               this.weaponCooldown--;
            }

            if (this.WEAPON == 3) {
               if (this.shieldPower < 40.0F) {
                  boolean l = this.shieldPower <= 0.0F;
                  this.shieldPower += 0.1F;
                  if (l && this.shieldPower > 0.0F) {
                     this.world.setEntityState(this, (byte)13);
                  }
               }

               if (this.ticksExisted % 40 == 0) {
                  this.world.setEntityState(this, (byte)11);
               }
            }
         }
      }

      public float getCollisionBorderSize() {
         return this.WEAPON == 3 && this.shieldPower > 0.0F ? 1.2F : 0.0F;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (this.WEAPON == 3 && this.shieldPower > 0.0F && amount > 0.0F) {
            float lam = amount;
            amount -= this.shieldPower;
            this.shieldPower -= lam;
            if (this.shieldPower <= 0.0F) {
               this.world.setEntityState(this, (byte)14);
            } else if (amount <= 0.0F) {
               this.world.setEntityState(this, (byte)12);
            }
         }

         return super.attackEntityFrom(source, amount);
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.BLOOD_THIRST
               && potion != PotionEffects.BERSERK
               && potion != MobEffects.WEAKNESS
               && potion != MobEffects.POISON
               && potion != MobEffects.REGENERATION
               && potion != MobEffects.INSTANT_HEALTH
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.laser,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stormledge_shoot_b,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 10) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.swosh_a,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.8F + this.rand.nextFloat() / 4.0F,
                  false
               );
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield,
                  SoundCategory.HOSTILE,
                  0.6F,
                  0.95F + this.rand.nextFloat() / 7.0F,
                  false
               );
         }

         if (id == 12) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield_impact,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.8F + this.rand.nextFloat() / 4.0F,
                  false
               );
         }

         if (id == 13 || id == 14) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.forcefield_trigger,
                  SoundCategory.HOSTILE,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 13) {
            this.var2 = 1.0F;
         }

         if (id == 14) {
            this.var2 = 0.0F;
         }

         if (id == 15) {
            this.var2 = 1.0F;
         }

         if (id == 16) {
            this.var2 = 0.0F;
         }

         if (id >= 64 && id <= 67) {
            this.WEAPON = id - 64;
            this.var1 = this.WEAPON;
         }
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("weapon", this.WEAPON);
         if (this.WEAPON == 3) {
            compound.setFloat("shield", this.shieldPower);
         }

         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("weapon")) {
            this.WEAPON = compound.getInteger("weapon");
            this.var1 = this.WEAPON;
         }

         if (compound.hasKey("shield") && this.WEAPON == 3) {
            this.shieldPower = compound.getFloat("shield");
            this.var2 = this.shieldPower > 0.0F ? 1.0F : 0.0F;
         }

         super.readEntityFromNBT(compound);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFloatingSkeleton(this, 50, 20.0F, 5, true, 8.0F, 4.0F, 1.4F, 0.25F, true, true));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }
}

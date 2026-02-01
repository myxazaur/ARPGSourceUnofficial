package com.vivern.arpg.mobs;

import com.vivern.arpg.elements.models.ModelsDungeonMob;
import com.vivern.arpg.entity.AbstractGlyphid;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.CrystalFanShoot;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.main.BloodType;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.MovingSoundEntity;
import com.vivern.arpg.main.ParticleFastSummon;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DungeonMobsPack {
   public static String mobsteam = "dungeon.mob.team";

   public static void init() {
      AbstractMob.addToRegister(Weaver.class, "Weaver", 2434086, 8679771);
      AbstractMob.addToRegister(Beholder.class, "Beholder", 15249819, 14942112);
      AbstractMob.addToRegister(Devourer.class, "Devourer", 3222049, 15056015);
      AbstractMob.addToRegister(Deg.class, "Deg", 4603708, 13746618);
      AbstractMob.addToRegister(StoneEater.class, "Stone Eater", 15772054, 7745345);
      AbstractMob.addToRegister(DoleriteEater.class, "Dolerite Eater", 3816525, 7777266);
      AbstractMob.addToRegister(UnderworldDigger.class, "Underworld Digger", 7630672, 15571316);
      AbstractMob.addToRegister(UnderworldSymbiote.class, "Underworld Symbiote", 7630672, 16318326);
      AbstractMob.addToRegister(Larva.class, "Larva", 14996897, 16777204);
      AbstractMob.addToRegister(LarvaFlyer.class, "Larva Flyer", 14996897, 16773410);
      AbstractMob.addToRegister(ShadowMage.class, "Shadow Mage", 3944788, 4006460);
   }

   public static void initRender() {
      AbstractMob.addToRender(new AbstractMob.RenderAbstractMobEntry(new ModelsDungeonMob.WeaverModel(), 0.36F, Weaver.class));
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.BeholderModel(), new ResourceLocation("arpg:textures/beholder_model_tex.png"), 0.4F, Beholder.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.DevourerModel(), new ResourceLocation("arpg:textures/devourer_model_tex.png"), 1.0F, Devourer.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.DegModel(), new ResourceLocation("arpg:textures/deg_model_tex.png"), 0.45F, Deg.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.StoneEaterModel(false),
            new ResourceLocation("arpg:textures/stone_eater_model_tex.png"),
            0.4375F,
            StoneEater.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.StoneEaterModel(true),
            new ResourceLocation("arpg:textures/dolerite_eater_tex.png"),
            0.4375F,
            DoleriteEater.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.UnderworldDiggerModel(),
            new ResourceLocation("arpg:textures/underworld_digger_model_tex.png"),
            0.55F,
            UnderworldDigger.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.UnderworldSymbioteModel(),
            new ResourceLocation("arpg:textures/underworld_symbiote_model_tex.png"),
            0.25F,
            UnderworldSymbiote.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.LarvaModel(), new ResourceLocation("arpg:textures/larva_model_tex.png"), 0.2F, Larva.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.LarvaFlyerModel(), new ResourceLocation("arpg:textures/larva_flyer_model_tex.png"), 0.5F, LarvaFlyer.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsDungeonMob.ShadowMageModel(), new ResourceLocation("arpg:textures/shadow_mage_model_tex.png"), 0.3F, ShadowMage.class
         )
      );
   }

   public static class Beholder extends AbstractMob {
      public static ResourceLocation shard_trace = new ResourceLocation("arpg:textures/shard_trace.png");
      public static ResourceLocation magic_effect_3 = new ResourceLocation("arpg:textures/magic_effect_3.png");
      public static ResourceLocation magic_effect_2 = new ResourceLocation("arpg:textures/magic_effect_2.png");
      public static ResourceLocation lightning1 = new ResourceLocation("arpg:textures/lightning1.png");
      public int lazerTimer = 0;
      public int maxlazerTime = 30;
      public int attackDelay = 10;
      public int maxattackDelay = 90;
      public int attackTimer = 0;

      public Beholder(World world) {
         super(world, 0.95F, 1.8F);
         this.hurtSound = Sounds.beholder_hurt;
         this.deathSound = Sounds.mob_mutant_death;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(200.0, 48.0, 3.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.EREBRISSHARD, 0.65F, 0, 1, 4, 0),
               new MobDrop(ItemsRegister.EYEOFBEHOLDER, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.PALEMEATRAW, 0.9F, 0, 0, 2, 3)
            }
         );
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.VOID, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.66F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)12);
            this.lazerTimer = this.maxlazerTime;
         }
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (!this.world.isRemote && amount > 3.0F && this.rand.nextFloat() < 0.85F && this.animations[3] <= 0) {
            this.triggerAnimation(-4);
         }

         return super.attackEntityFrom(source, amount);
      }

      public void attackMelee() {
         if (this.isEntityAlive()) {
            for (Entity entit : GetMOP.getEntitiesInAABBof(this.world, this, 3.0, this)) {
               if (Team.checkIsOpponent(this, entit)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this, null, false, false, this, WeaponDamage.electric),
                     (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + this.getMobDifficulty() * 0.5F,
                     this,
                     entit,
                     true,
                     0.7F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  if (entit.hurtResistantTime > 5) {
                     entit.hurtResistantTime = 5;
                  }
               }
            }
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.attackTimer > 0) {
               this.attackTimer--;
               if (this.attackTimer < 12) {
                  this.attackMelee();
                  this.world.setEntityState(this, (byte)(-this.attackTimer - 10));
               }
            }

            if (this.ticksExisted % 40 == 0 && this.rand.nextFloat() < 0.12F && this.animations[1] <= 0) {
               this.triggerAnimation(-2);
            }

            if (this.ticksExisted % 80 == 0 && this.rand.nextFloat() < 0.18F && this.animations[3] <= 0) {
               this.triggerAnimation(-4);
            }

            if (this.attackDelay <= 0) {
               if (this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 9.0) {
                  this.attackTimer = 20;
                  this.triggerAnimation(-1);
                  this.attackDelay = this.maxattackDelay;
                  this.world.setEntityState(this, (byte)13);
               }
            } else {
               this.attackDelay--;
            }
         }

         if (this.isEntityAlive() && this.lazerTimer > 0) {
            this.lazerTimer--;
            Vec3d vec = GetMOP.RotatedPosRayTrace(40.0, 1.0F, this, 0.08, 0.1, this.prevRotationPitch, this.prevRotationYaw);
            if (this.world.isRemote) {
               this.spawnBetwParticle(
                  this.getPositionEyes(1.0F).add(this.getVectorForRotation(0.0F, this.rotationYaw).scale(this.width / 2.2F)), vec
               );
            } else {
               for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, vec, 1.0, this)) {
                  if (Team.checkIsOpponent(this, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, this.getPositionEyes(1.0F), WeaponDamage.laser), 3.0F, this, entity, false
                     );
                     if (entity instanceof EntityPlayer) {
                        if (entity.hurtResistantTime > 15) {
                           entity.hurtResistantTime = 15;
                        }
                     } else if (entity.hurtResistantTime > 8) {
                        entity.hurtResistantTime = 8;
                     }
                  }
               }
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void spawnBetwParticle(Vec3d from, Vec3d to) {
         if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, shard_trace, 0.13F, 240, 0.95F, 1.0F, 0.45F, 0.15F, from.distanceTo(to), 3, 0.5F, 4.0F, from, to
            );
            laser.ignoreFrustumCheck = true;
            laser.setPosition(from.x, from.y, from.z);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);

            for (int ss = 0; ss < 2; ss++) {
               int lt = 19 + this.rand.nextInt(9);
               float scl = 0.22F + this.rand.nextFloat() * 0.08F;
               GUNParticle part = new GUNParticle(
                  magic_effect_3,
                  scl,
                  -0.008F,
                  lt,
                  240,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 35.0F,
                  (float)this.rand.nextGaussian() / 35.0F,
                  (float)this.rand.nextGaussian() / 35.0F,
                  1.0F - this.rand.nextFloat() * 0.1F,
                  1.0F - this.rand.nextFloat() * 0.1F,
                  1.0F - this.rand.nextFloat() * 0.1F,
                  true,
                  this.rand.nextInt(15) - 14
               );
               part.alphaGlowing = true;
               part.alphaTickAdding = -0.9F / lt;
               part.scaleTickAdding = -scl / lt * 0.8F;
               this.world.spawnEntity(part);
            }

            int lt = 6 + this.rand.nextInt(4);
            float scl = 1.4F + this.rand.nextFloat() * 0.85F;
            GUNParticle part = new GUNParticle(
               magic_effect_2,
               0.05F,
               0.0F,
               lt,
               240,
               this.world,
               to.x,
               to.y,
               to.z,
               0.0F,
               0.0F,
               0.0F,
               0.95F - this.rand.nextFloat() * 0.1F,
               1.0F - this.rand.nextFloat() * 0.1F,
               0.5F,
               true,
               this.rand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.alphaTickAdding = -1.0F / lt;
            part.scaleTickAdding = scl / lt;
            this.world.spawnEntity(part);
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 12) {
            this.lazerTimer = this.maxlazerTime;
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.beholder_shoot,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 13) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.beholder_attack,
                  SoundCategory.HOSTILE,
                  1.3F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id <= -10 && id >= -21) {
            for (int i = 0; i < 5; i++) {
               Vec3d vecadd = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian() / 6.0, this.rand.nextGaussian())
                  .normalize()
                  .scale(0.8 + (-id - 10) / 5.0);
               GUNParticle part = new GUNParticle(
                  lightning1,
                  0.6F + this.rand.nextFloat() / 3.0F,
                  0.0F,
                  3,
                  240,
                  this.world,
                  this.posX + vecadd.x,
                  this.posY + 0.3 + vecadd.y,
                  this.posZ + vecadd.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.6F + this.rand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               this.world.spawnEntity(part);
            }
         }
      }

      public void onLivingUpdate() {
         super.onLivingUpdate();
         if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.75;
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         EntityAIFloatingSkeleton ai = new EntityAIFloatingSkeleton(this, 160, 32.0F, 20, true, 11.0F, 5.0F, 1.75F, 0.1F, true, true);
         ai.levitateOverMobs = false;
         this.tasks.addTask(1, ai);
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Deg extends AbstractMob {
      public Deg(World world) {
         super(world, 0.8F, 0.9F);
         this.hurtSound = SoundEvents.ENTITY_RABBIT_HURT;
         this.deathSound = Sounds.mob_plant_death;
         this.stepSound = SoundEvents.ENTITY_WOLF_STEP;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(60.0, 64.0, 11.0, 0.6, 0.0, 0.0, 0.0, 0.0, 1.0, 0.1);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.EREBRISSHARD, 0.25F, 0, 1, 1, 0), new MobDrop(ItemsRegister.PALEMEATRAW, 0.8F, 0, 0, 2, 3)
            }
         );
         this.setRoleValues(EnumMobRole.SOLDIER, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.PAIN, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_RED_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == MobEffects.BLINDNESS ? false : super.isPotionApplicable(potioneffectIn);
      }

      public float getEyeHeight() {
         return this.height * 0.2F;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, false));
         this.tasks.addTask(1, new EntityAIDash(this, 100, 7.0F, 3.0F, 1.4F, true, 0.5F));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Devourer extends AbstractMob implements IEntitySynchronize {
      public int tailattackTimer = 0;
      public int tailattackDelay = 0;
      public int maxtailattackDelay = 50;
      public int resistanceDelay = 0;
      public int maxresistanceDelay = 25;

      public Devourer(World world) {
         super(world, 1.95F, 1.7F);
         this.hurtSound = Sounds.mob_zombie_hurt;
         this.deathSound = Sounds.mob_zombie_death;
         this.livingSound = Sounds.mob_zombie_living;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(400.0, 64.0, 10.0, 0.4, 5.0, 4.0, 0.4, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.EREBRISSHARD, 0.65F, 0, 1, 4, 0),
               new MobDrop(ItemsRegister.DEVOURERSTEETH, 0.05F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.PALEMEATRAW, 1.0F, 0, 1, 3, 4)
            }
         );
         this.stepHeight = 1.0F;
         this.setRoleValues(EnumMobRole.TANK, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.EARTH, this, 5.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_RED_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.7F;
      }

      protected float updateDistance(float p_110146_1_, float p_110146_2_) {
         float maxadd = 2.0F;
         float f = MathHelper.wrapDegrees(p_110146_1_ - this.renderYawOffset);
         this.renderYawOffset = this.renderYawOffset + MathHelper.clamp(f * 0.3F, -maxadd, maxadd);
         float f1 = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
         boolean flag = f1 < -90.0F || f1 >= 90.0F;
         if (f1 < -75.0F) {
            f1 = -75.0F;
         }

         if (f1 >= 75.0F) {
            f1 = 75.0F;
         }

         if (f1 * f1 > 2500.0F) {
            this.renderYawOffset = this.renderYawOffset + MathHelper.clamp(f1 * 0.2F, -maxadd, maxadd);
         }

         if (flag) {
            p_110146_2_ *= -1.0F;
         }

         return p_110146_2_;
      }

      public int getHorizontalFaceSpeed() {
         return 2;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         boolean at = super.attackEntityFrom(source, amount);
         if (amount > 0.0F && this.resistanceDelay <= 0 && !this.world.isRemote) {
            Weapons.mixPotion(
               this,
               MobEffects.RESISTANCE,
               130.0F,
               1.0F,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumMathOperation.NONE,
               Weapons.EnumMathOperation.PLUS,
               130,
               5
            );
            Weapons.mixPotion(
               this,
               PotionEffects.TENACITY,
               260.0F,
               1.0F,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumMathOperation.NONE,
               Weapons.EnumMathOperation.PLUS,
               260,
               4
            );
            this.resistanceDelay = this.maxresistanceDelay;
            this.triggerAnimation(-4);
            this.world.setEntityState(this, (byte)9);
         }

         return at;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         this.var1++;
         if (this.var1 % 190 == 0) {
            this.var1 = 0;
            this.world.setEntityState(this, (byte)7);
         }

         if (!this.world.isRemote) {
            if (this.animations[0] == 77) {
               Vec3d poss = this.getPositionVector().add(this.getVectorForRotation(0.0F, this.renderYawOffset));
               RayTraceResult res = this.world.rayTraceBlocks(poss.add(0.0, this.height, 0.0), poss.add(0.0, -3.0, 0.0));
               if (res != null && res.getBlockPos() != null && res.hitVec != null) {
                  IBlockState state = this.world.getBlockState(res.getBlockPos());
                  if (state.getMaterial() != Material.AIR) {
                     IEntitySynchronize.sendSynchronize(
                        this,
                        64.0,
                        res.hitVec.x,
                        res.hitVec.y,
                        res.hitVec.z,
                        Block.getStateId(state),
                        1.0,
                        0.0
                     );
                  }
               }
            }

            this.tailattackDelay--;
            this.resistanceDelay--;
            if (this.tailattackTimer > 0) {
               this.tailattackTimer--;
               if (this.tailattackTimer == 0) {
                  float rot = MathHelper.sin(this.var1 / 30.239439F) * 50.0F;
                  Vec3d posTail = this.getPositionVector().add(this.getVectorForRotation(0.0F, this.renderYawOffset + 180.0F + rot).scale(3.0));

                  for (Entity entit : GetMOP.getEntitiesInAABBof(this.world, posTail, 2.8, this)) {
                     if (Team.checkIsOpponent(this, entit)) {
                        Weapons.dealDamage(
                           new WeaponDamage(null, this, null, false, false, posTail, WeaponDamage.heavymelee),
                           (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                           this,
                           entit,
                           true,
                           1.8F,
                           posTail.x,
                           posTail.y - 1.0,
                           posTail.z
                        );
                     }
                  }

                  RayTraceResult res = this.world
                     .rayTraceBlocks(posTail.add(0.0, this.height, 0.0), posTail.add(0.0, -3.0, 0.0));
                  if (res != null && res.getBlockPos() != null && res.hitVec != null) {
                     IBlockState state = this.world.getBlockState(res.getBlockPos());
                     if (state.getMaterial() != Material.AIR) {
                        IEntitySynchronize.sendSynchronize(
                           this,
                           64.0,
                           res.hitVec.x,
                           res.hitVec.y,
                           res.hitVec.z,
                           Block.getStateId(state),
                           0.0,
                           0.0
                        );
                     }
                  }
               }
            } else if (this.ticksExisted % 6 == 0 && this.getAttackTarget() != null && this.tailattackDelay <= 0) {
               float rot = MathHelper.sin(this.var1 / 30.239439F) * 50.0F;
               Vec3d posTail = this.getPositionVector().add(this.getVectorForRotation(0.0F, this.renderYawOffset + 180.0F + rot).scale(3.5));

               for (Entity entitx : GetMOP.getEntitiesInAABBof(this.world, posTail, 1.75, this)) {
                  if (Team.checkIsOpponent(this, entitx) && Weapons.canDealDamageTo(entitx)) {
                     this.tailattackDelay = this.maxtailattackDelay;
                     this.tailattackTimer = 17;
                     this.triggerAnimation(-2);
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         this.world
            .playSound(
               x,
               y,
               z,
               b > 0.0 ? Sounds.melee_mob_attack_a : Sounds.melee_mob_attack_b,
               SoundCategory.HOSTILE,
               1.3F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int i = 0; i < 126; i++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.BLOCK_DUST,
                  x,
                  y + 0.1,
                  z,
                  this.rand.nextGaussian() / 2.5,
                  this.rand.nextGaussian() / 4.0,
                  this.rand.nextGaussian() / 2.5,
                  new int[]{(int)a}
               );
         }

         for (int i = 0; i < 20; i++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.CLOUD,
                  x,
                  y + 0.1,
                  z,
                  this.rand.nextGaussian() / 5.0,
                  this.rand.nextGaussian() / 8.0,
                  this.rand.nextGaussian() / 5.0,
                  new int[0]
               );
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.var1 = 0;
         }

         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_d,
                  SoundCategory.HOSTILE,
                  0.8F,
                  0.6F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.hurtTime = 0;
         }
      }

      protected void initEntityAI() {
         EntityAIAttackSweep sw = new EntityAIAttackSweep(this, 60, 2.0F, 2.5F, 2.5F, 23).setTriggerOnStart().setBreakBlocks(15.0F);
         sw.swoshSound = null;
         sw.hitSound = null;
         sw.avoidDownbreak = false;
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, sw);
         this.tasks.addTask(2, new EntityAIRush(this, true, false, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class DoleriteEater extends AbstractWorm {
      public int destroyTimer = 0;
      public int maxdestroyTime = 80;
      public int destroyDelay = 20;
      public int maxdestroyDelay = 250;

      public DoleriteEater(World world, boolean isSegment, int number, AbstractWorm headEntity) {
         super(world, 0.975F, 0.975F, isSegment, number, headEntity);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(240.0, 64.0, 13.0, 0.085, 10.0, 5.0, 0.9, 0.3, 0.0, 0.0);
         this.segmentDistance = 0.85F;
         if (!this.isSubMob) {
            this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.DUSTGLOWINGCRYSTAL, 0.5F, 0, 1, 3, 2)});
            this.deathSound = Sounds.mob_ice_death;
         }

         this.setRoleValues(EnumMobRole.ELITE_ENEMY, 4);
         this.soul = Soul.SHADOW;
      }

      public DoleriteEater(World world) {
         this(world, false, 0, null);
      }

      @Override
      public void dropShards() {
         ShardType.spawnShards(ShardType.EARTH, this, 3.0F * dropShardsQuantity);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_BLOOD;
      }

      @Override
      public int getSegmentsCount() {
         return 10;
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData inits = super.onInitialSpawn(difficulty, livingdata);
         if (!this.world.isRemote && !this.isSubMob) {
            for (int i = 0; i < this.getSegmentsCount(); i++) {
               DoleriteEater part = new DoleriteEater(this.world, true, i, this);
               part.setPosition(this.posX, this.posY - i / 5.0, this.posZ);
               this.world.spawnEntity(part);
               part.onInitialSpawn();
               part.team = this.team;
            }
         }

         return inits;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.destroyDelay <= 0) {
               if (this.ticksExisted % 50 == 0 && this.getAttackTarget() != null) {
               }
            } else {
               this.destroyDelay--;
            }

            if (this.destroyTimer > 0 && this.poslist.size() > 3) {
               this.destroyTimer--;
               Vec3d posv = this.poslist.get(3);
               if (posv != null) {
                  for (BlockPos pos : GetMOP.getBlockPosesCollidesAABB(this.world, GetMOP.newAABB(posv, (double)(this.width / 2.0F)), false)) {
                     if (Weapons.easyBreakBlockFor(this.world, 15.0F, pos)) {
                        this.world.destroyBlock(pos, this.rand.nextFloat() < 0.1);
                     }
                  }
               }
            }
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISegmAABBAttack(this, 30, 0.4F));
         if (!this.isSubMob) {
            this.tasks.addTask(2, new EntityAIWorm(this, 80, 10.0F, 6.0F, 0.0F, 1.3F, 0.3F, 1.12F, true, 20));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
            this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
         }
      }
   }

   public static class Larva extends AbstractMob {
      public Larva(World world) {
         super(world, 0.8F, 0.3125F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(25.0, 64.0, 4.0, 0.36, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.setRoleValues(EnumMobRole.SWARMER, 4);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.LARVA_BLOOD;
      }

      @Override
      protected void playStepSound(BlockPos pos, Block blockIn) {
      }

      public void fall(float distance, float damageMultiplier) {
         super.fall(distance - 4.0F, damageMultiplier * 0.4F);
      }

      public float getEyeHeight() {
         return this.height * 0.75F;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIPanic(this, 1.0));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class LarvaFlyer extends AbstractMob {
      public int shootTime = 0;
      public int breedingReady = 0;
      public int breedingTick = 0;
      public boolean isBreeding = false;
      public static int larvasMaxCount = 50;
      public boolean firstUpdate1 = true;
      EntityAIFlyingKeepDistToTarget aiFlyingRandom;
      EntityAICorrupter aiFlyingFollow;
      EntityAIEasyRunaway aiFlyingRun;
      EntityAIShootDelayed aiShoot;

      public LarvaFlyer(World world) {
         super(world, 0.99F, 2.1F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(180.0, 64.0, 13.0, 0.1, 10.0, 2.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.EREBRISSHARD, 0.75F, 0, 0, 5, 0)});
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.AIR, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.LIVE, this, 4.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.LARVA_BLOOD;
      }

      public boolean isTooMuchLarvas() {
         List<Larva> larvas = this.world.getEntitiesWithinAABB(Larva.class, GetMOP.newAABB(this, 64.0));
         return larvas.size() > larvasMaxCount;
      }

      public float getCollisionBorderSize() {
         return 0.1F;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)11);
            HostileProjectiles.LarvaFlyerShoot entity = new HostileProjectiles.LarvaFlyerShoot(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.7F + this.getMobDifficulty() * 0.15F, 0.0F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entity);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (!this.isAIDisabled()) {
               if (this.shootTime > 0) {
                  this.shootTime--;
               }

               if (this.breedingReady < 300) {
                  this.breedingReady++;
               } else if (!this.isBreeding && this.getAttackTarget() != null) {
                  if (!this.isTooMuchLarvas()) {
                     this.isBreeding = true;
                     this.shootTime = 0;
                     this.triggerAnimation(-4);
                  } else {
                     this.breedingReady -= 20;
                  }
               }

               if (this.isBreeding) {
                  this.breedingTick++;
                  if (this.breedingTick == 50) {
                     this.triggerAnimation(-2);
                     this.world.setEntityState(this, (byte)12);
                  }

                  if (this.breedingTick >= 75) {
                     this.breedingTick = 0;
                     this.isBreeding = false;
                     this.breedingReady = 0;

                     for (int i = 0; i < 5; i++) {
                        Larva larva = new Larva(this.world);
                        larva.setPosition(this.posX, this.posY + this.height / 2.0F, this.posZ);
                        SuperKnockback.shoot(larva, this, this.rotationPitch, this.rotationYaw, 0.0F, 0.8F, 6.0F);
                        larva.velocityChanged = true;
                        this.world.spawnEntity(larva);
                        larva.onInitialSpawn();
                        larva.team = this.team;
                        larva.isAgressive = this.isAgressive;
                        larva.canDropLoot = this.canDropLoot;
                     }
                  }
               }

               if (this.getAttackTarget() != null) {
                  double distSq = this.getAttackTarget().getDistanceSq(this);
                  if (this.rand.nextFloat() < 0.008F && this.shootTime <= 0 && !this.isBreeding) {
                     this.shootTime = 150 + this.rand.nextInt(4) * 50;
                  }

                  if (distSq > 1156.0) {
                     this.mode(0);
                  } else if (distSq < 36.0) {
                     this.mode(2);
                  } else if (this.shootTime > 0 && !this.isBreeding) {
                     this.mode(3);
                  } else {
                     this.mode(1);
                  }
               }
            }
         } else if (this.firstUpdate1) {
            this.firstUpdate1 = false;
            Minecraft.getMinecraft()
               .getSoundHandler()
               .playSound(new MovingSoundEntity(this, Sounds.larva_flyer_fly, SoundCategory.HOSTILE, 0.8F, 1.0F, true));
         }
      }

      public void mode(int mode) {
         if (mode == 0) {
            this.aiFlyingFollow.enable = true;
            this.aiFlyingRandom.enable = false;
            this.aiFlyingRun.enable = false;
            this.aiShoot.enable = false;
         }

         if (mode == 1) {
            this.aiFlyingFollow.enable = false;
            this.aiFlyingRandom.enable = true;
            this.aiFlyingRun.enable = false;
            this.aiShoot.enable = false;
         }

         if (mode == 2) {
            this.aiFlyingFollow.enable = false;
            this.aiFlyingRandom.enable = false;
            this.aiFlyingRun.enable = true;
            this.aiShoot.enable = true;
         }

         if (mode == 3) {
            this.aiFlyingFollow.enable = false;
            this.aiFlyingRandom.enable = false;
            this.aiFlyingRun.enable = false;
            this.aiShoot.enable = true;
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 12) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.larva_flyer_breed,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.larva_flyer_attack,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.aiFlyingRandom = new EntityAIFlyingKeepDistToTarget(this, 60, 32.0F, 0.08F, 32.0F, 10.0F, 0.0F, 1.05F);
         this.aiFlyingFollow = new EntityAICorrupter(this, 50, 38.0F, 10, false, 4.0F, 4.0F);
         this.aiFlyingRun = new EntityAIEasyRunaway(this, 0.08F, 1.0F);
         this.aiShoot = new EntityAIShootDelayed(this, 40, 38.0F, 8, true, 60);
         this.tasks.addTask(0, this.aiShoot);
         this.tasks.addTask(1, this.aiFlyingRun);
         this.tasks.addTask(2, this.aiFlyingFollow);
         this.tasks.addTask(3, this.aiFlyingRandom);
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class ShadowMage extends AbstractMob implements IEntitySynchronize {
      public static ResourceLocation circle3 = new ResourceLocation("arpg:textures/circle3.png");
      public static ResourceLocation generic_beam6 = new ResourceLocation("arpg:textures/generic_beam6.png");
      public int teleportCooldownMax = 70;
      public int teleportCooldown = this.teleportCooldownMax;
      public int[] teleportBehaviour = new int[3];
      public int spellCooldown;
      public int spellCasting;
      public int spellsAttempts = 0;
      public static int spellCooldownMax = 170;
      public static int spellsMax = 4;
      public static int spellsAttemptsMax = 2;
      public static float spellRadius = 4.0F;
      public ArrayList<Vec3d> spellPoses = new ArrayList<>();
      public boolean canUseShield = false;
      public float shieldHP = 0.0F;
      public static float shieldHPMax = 140.0F;
      public int shieldCasting;
      public static int shieldCastingMax = 60;
      public static ParticleTracker.TrackerSmoothShowHideBetweenP ssh = new ParticleTracker.TrackerSmoothShowHideBetweenP(
         new Vec3d[]{new Vec3d(0.0, 4.0, 0.25), new Vec3d(6.0, 13.0, -0.14285715F)}, null
      );
      public EntityAIRush rushAi;

      public ShadowMage(World world) {
         super(world, 0.7F, 1.8F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(110.0, 84.0, 13.0, 0.36, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.VITREOUSHEART, 0.75F, 0, 1, 1, 0)});
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 4);
         this.soul = Soul.MYSTIC;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.VOID, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.EARTH, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.CRYSTAL_BLOOD;
      }

      public int getCastDelay() {
         return 40 - this.getMobDifficulty() * 4;
      }

      public boolean isShieldActive() {
         return this.shieldHP > 0.0F;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         this.teleportBehaviour[0] = (int)(this.teleportBehaviour[0] + (10.0F + amount * 2.0F));
         float reduced = this.isShieldActive() ? Math.min(amount, this.shieldHP) : 0.0F;
         this.shieldHP -= reduced;
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)(this.isShieldActive() ? 10 : 9));
         }

         boolean ret = super.attackEntityFrom(source, amount - reduced);
         if (this.getHealth() <= this.getMaxHealth() * 0.25F) {
            this.teleportBehaviour[0] = this.teleportBehaviour[0] + 15;
         }

         this.canUseShield = true;
         return ret;
      }

      public boolean canTeleport() {
         return this.shieldCasting <= 0 && this.spellCasting <= 0;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && !this.isAIDisabled()) {
            EntityLivingBase target = this.getAttackTarget();
            boolean canCastCrystals = false;
            if (this.rand.nextFloat() < 0.4F) {
               this.teleportBehaviour[2]++;
            }

            if (this.teleportCooldown > 0) {
               this.teleportCooldown--;
            } else {
               if (target != null) {
                  if (this.getEntitySenses().canSee(target) && this.getDistanceSq(target) < 2304.0) {
                     canCastCrystals = true;
                     this.rushAi.enable = false;
                  } else {
                     this.rushAi.enable = true;
                     this.teleportBehaviour[1]++;
                  }

                  if (this.teleportBehaviour[1] > 50 && this.canTeleport()) {
                     this.teleportToEntity(this.getAttackTarget());
                  }
               }

               if (this.teleportBehaviour[0] > 80 && this.canTeleport()) {
                  this.teleportRandomly();
               }

               if (this.teleportBehaviour[2] > 80 && this.canTeleport()) {
                  this.teleportRandomly();
               }
            }

            if (this.ticksExisted % 20 == 0) {
               this.world.setEntityState(this, (byte)(this.isShieldActive() ? 10 : 9));
            }

            if (target != null) {
               if (this.canUseShield) {
                  boolean shouldStartCastShield = this.shieldHP <= 0.0F
                     || this.getHealth() <= this.getMaxHealth() * 0.5F && this.shieldHP < shieldHPMax * 0.6F;
                  if (shouldStartCastShield && this.shieldCasting <= 0) {
                     this.shieldCasting = shieldCastingMax;
                     this.triggerAnimation(-1);
                     this.world.setEntityState(this, (byte)11);
                  } else if (this.shieldCasting > 0) {
                     this.shieldCasting--;
                     if (this.shieldCasting == 1) {
                        this.shieldHP = shieldHPMax;
                        this.world.setEntityState(this, (byte)12);
                     }

                     canCastCrystals = false;
                  }
               }

               if (this.spellCasting > 0) {
                  if (this.spellCasting == 1) {
                     if (!this.spellPoses.isEmpty()) {
                        for (Vec3d pos : this.spellPoses) {
                           this.spawnCrystalSpell(pos, true);
                        }
                     }

                     this.spellCasting = 0;
                     this.spellCooldown = spellCooldownMax;
                     if (this.spellsAttempts > 0) {
                        this.spellsAttempts--;
                        if (this.spellsAttempts != 0) {
                           this.spellCooldown = 0;
                        }
                     }
                  } else {
                     if (this.spellCasting % 10 == 0 && !this.spellPoses.isEmpty()) {
                        for (Vec3d pos : this.spellPoses) {
                           this.spawnCrystalSpell(pos, false);
                        }

                        this.triggerAnimation(-1);
                     }

                     this.spellCasting--;
                  }
               } else {
                  this.spellCooldown--;
                  if (canCastCrystals && this.spellCooldown <= 0) {
                     this.spellPoses.clear();
                     this.spellPoses.add(target.getPositionVector());
                     Vec3d speed = new Vec3d(target.motionX, 0.0, target.motionZ).scale(40.0);
                     Vec3d speedAdded = target.getPositionVector().add(speed);
                     this.spellPoses.add(speedAdded);
                     int smax = spellsMax - this.spellPoses.size();

                     for (int i = 0; i < smax; i++) {
                        Vec3d randomvec = new Vec3d(this.rand.nextGaussian(), 0.0, this.rand.nextGaussian())
                           .normalize()
                           .scale(5.0F + this.rand.nextFloat() * 3.0F);
                        GetMOP.BlockTraceResult result = GetMOP.blockTrace(
                           this.world, new BlockPos(speedAdded.add(randomvec)).up(3), EnumFacing.DOWN, 10, null
                        );
                        if (result != null) {
                           this.spellPoses
                              .add(new Vec3d(result.prevPos.getX() + 0.5, result.prevPos.getY() + 0.2, result.prevPos.getZ() + 0.5));
                        } else {
                           this.spellPoses.add(speedAdded.add(randomvec));
                        }
                     }

                     this.spellCasting = this.getCastDelay();
                     if (this.spellsAttempts == 0) {
                        this.spellsAttempts = spellsAttemptsMax;
                        this.world.setEntityState(this, (byte)11);
                     }

                     if (!this.spellPoses.isEmpty()) {
                        for (Vec3d pos : this.spellPoses) {
                           IEntitySynchronize.sendSynchronize(this, 64.0, pos.x, pos.y, pos.z, -999.0);
                        }
                     }
                  }
               }
            }
         }
      }

      protected boolean teleportRandomly() {
         double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
         double d1 = this.posY + (this.rand.nextInt(64) - 32);
         double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
         return this.teleportTo(d0, d1, d2);
      }

      protected boolean teleportToEntity(Entity p_70816_1_) {
         Vec3d vec3d = new Vec3d(
            this.posX - p_70816_1_.posX,
            this.getEntityBoundingBox().minY + this.height / 2.0F - p_70816_1_.posY + p_70816_1_.getEyeHeight(),
            this.posZ - p_70816_1_.posZ
         );
         vec3d = vec3d.normalize();
         double d0 = 16.0;
         double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
         double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.y * 16.0;
         double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
         return this.teleportTo(d1, d2, d3);
      }

      private boolean teleportTo(double x, double y, double z) {
         EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0.0F);
         if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
         } else {
            boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
            if (flag) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.prevPosX,
                     this.prevPosY,
                     this.prevPosZ,
                     SoundEvents.ENTITY_ENDERMEN_TELEPORT,
                     this.getSoundCategory(),
                     1.0F,
                     1.0F
                  );
               this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
               this.teleportBehaviour[0] = 0;
               this.teleportBehaviour[1] = 0;
               this.teleportBehaviour[2] = 0;
               this.teleportCooldown = this.teleportCooldownMax;
            }

            return flag;
         }
      }

      public void spawnCrystalSpell(Vec3d pos, boolean phase2) {
         if (phase2) {
            double yy = 0.0;

            for (int i = 0; i < 7; i++) {
               Vec3d posAdd = new Vec3d(this.rand.nextFloat() - 0.5, 0.0, this.rand.nextFloat() - 0.5)
                  .normalize()
                  .scale(spellRadius * this.rand.nextFloat());
               Vec3d poss = new Vec3d(
                  pos.x + posAdd.x, pos.y + posAdd.y + 0.1F, pos.z + posAdd.z
               );
               Vec3d posUp = poss.add(0.0, 6.0F + this.rand.nextFloat() * 3.0F, 0.0);
               RayTraceResult result = this.world.rayTraceBlocks(poss, posUp);
               Vec3d finalpos = result == null ? posUp : result.hitVec;
               if (i == 0) {
                  yy = finalpos.y - poss.y;
               }

               CrystalFanShoot projectile = new CrystalFanShoot(this.world, this);
               projectile.setPosition(finalpos.x, finalpos.y - 0.25, finalpos.z);
               projectile.canSpawnBonus = false;
               projectile.motionY = -1.2;
               projectile.motionX = (this.rand.nextFloat() - 0.5) * 0.2;
               projectile.motionZ = (this.rand.nextFloat() - 0.5) * 0.2;
               projectile.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 2.0F;
               projectile.soundAndParticleChance = 0.5F;
               this.world.spawnEntity(projectile);
            }

            for (Entity entity : this.world
               .getEntitiesWithinAABBExcludingEntity(
                  this,
                  new AxisAlignedBB(
                     pos.x - spellRadius,
                     pos.y - 2.0,
                     pos.z - spellRadius,
                     pos.x + spellRadius,
                     pos.y + yy,
                     pos.z + spellRadius
                  )
               )) {
               if (GetMOP.flatDistance(entity.getPositionVector(), pos) <= spellRadius + entity.width / 2.0F && Team.checkIsOpponent(this, entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this, null, false, false, null, WeaponDamage.magic),
                     (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                     this,
                     entity,
                     true
                  );
                  entity.hurtResistantTime = 0;
               }
            }

            IEntitySynchronize.sendSynchronize(this, 64.0, pos.x, pos.y, pos.z, yy);
         } else {
            IEntitySynchronize.sendSynchronize(this, 64.0, pos.x, pos.y + 0.0625, pos.z);
         }
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 3) {
            Vec3d posCenter = new Vec3d(args[0], args[1], args[2]);
            ParticleFastSummon.round2invertedAlpha(this.world, circle3, posCenter, spellRadius, 1.0F, 30, 240, 0.8F, 0.41F, 0.69F, 2);

            for (int i = 0; i < 6; i++) {
               Vec3d posAdd = new Vec3d(this.rand.nextFloat() - 0.5, 0.0, this.rand.nextFloat() - 0.5)
                  .normalize()
                  .scale(spellRadius);
               ParticleFastSummon.crystalSpell(this.world, posCenter.add(posAdd), 1.0F, -1.0F);
            }
         }

         if (args.length == 4) {
            if (args[3] == -999.0) {
               this.world
                  .playSound(
                     args[0], args[1], args[2], Sounds.shadow_mage_cast, SoundCategory.HOSTILE, 1.0F, 0.9F + this.rand.nextFloat() / 5.0F, false
                  );
            } else {
               Vec3d posCenter = new Vec3d(args[0], args[1], args[2]);

               for (int i = 0; i < 7; i++) {
                  Vec3d posAdd1 = new Vec3d(this.rand.nextFloat() - 0.5, 0.0, this.rand.nextFloat() - 0.5)
                     .normalize()
                     .scale(spellRadius * this.rand.nextFloat());
                  Vec3d posAdd2 = new Vec3d(this.rand.nextFloat() - 0.5, 0.0, this.rand.nextFloat() - 0.5)
                     .normalize()
                     .scale(spellRadius * this.rand.nextFloat())
                     .add(0.0, args[3], 0.0);
                  double dist = posAdd1.distanceTo(posAdd2);
                  Vec3d pos1 = posCenter.add(posAdd1);
                  BetweenParticle particle = new BetweenParticle(
                     this.world, generic_beam6, 0.4F, 240, 0.8F, 0.28F, 0.55F, 0.0F, dist, 13, 0.0F, 9000.0F, pos1, posCenter.add(posAdd2)
                  );
                  particle.setPosition(pos1.x, pos1.y, pos1.z);
                  particle.alphaGlowing = true;
                  particle.alpha = 0.0F;
                  particle.tracker = ssh;
                  this.world.spawnEntity(particle);
               }
            }
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.shadow_mage_prepare,
                  SoundCategory.HOSTILE,
                  1.6F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 12) {
            this.shieldHP = 10.0F;
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.shadow_mage_cast,
                  SoundCategory.HOSTILE,
                  1.4F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 10) {
            this.shieldHP = 10.0F;
         }

         if (id == 9) {
            this.shieldHP = 0.0F;
         }
      }

      protected void initEntityAI() {
         this.rushAi = new EntityAIRush(this, true, true, false);
         this.rushAi.enable = false;
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, this.rushAi);
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }
   }

   public static class StoneEater extends AbstractWorm {
      public StoneEater(World world, boolean isSegment, int number, AbstractWorm headEntity) {
         super(world, 0.875F, 0.875F, isSegment, number, headEntity);
         this.hurtSound = Sounds.mob_plant_hurt;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(100.0, 64.0, 9.0, 0.1, 1.0, 1.0, 0.5, 0.0, 0.0, 0.0);
         this.segmentDistance = 0.65F;
         if (!this.isSubMob) {
            this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.DUSTSTONE, 1.0F, 0, 0, 1, 10)});
            this.deathSound = Sounds.mob_plant_death;
         }

         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.EARTH, this, 2.0F * dropShardsQuantity);
         }
      }

      public StoneEater(World world) {
         this(world, false, 0, null);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_BLOOD;
      }

      @Override
      public int getSegmentsCount() {
         return 9;
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData inits = super.onInitialSpawn(difficulty, livingdata);
         if (!this.world.isRemote && !this.isSubMob) {
            for (int i = 0; i < this.getSegmentsCount(); i++) {
               StoneEater part = new StoneEater(this.world, true, i, this);
               part.setPosition(this.posX, this.posY - i / 5.0, this.posZ);
               this.world.spawnEntity(part);
               part.onInitialSpawn();
               part.team = this.team;
            }
         }

         return inits;
      }

      public void onLivingUpdate() {
         super.onLivingUpdate();
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISegmAABBAttack(this, 30, 0.3F));
         if (!this.isSubMob) {
            this.tasks.addTask(2, new EntityAIWorm(this, 60, 10.0F, 6.0F, 0.0F, 1.4F, 0.35F, 1.12F, true, 20));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
            this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
         }
      }
   }

   public static class UnderworldDigger extends AbstractWorm {
      public float healthSymbiots;
      public float maxhealthSymbiots = 20.0F;
      public static ResourceLocation slime = new ResourceLocation("arpg:textures/slimesplash.png");

      public UnderworldDigger(World world, boolean isSegment, int number, AbstractWorm headEntity) {
         super(world, 1.5F, 1.5F, isSegment, number, headEntity);
         this.hurtSound = Sounds.mob_monster_hurt;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(1250.0, 64.0, 13.0, 0.06, 5.0, 2.0, 1.0, 0.3, 0.0, 0.0);
         this.segmentDistance = 1.6F;
         if (!this.isSubMob) {
            this.registerLOOT(
               new MobDrop[]{
                  new MobDrop(ItemsRegister.EREBRISSHARD, 1.0F, 0, 1, 5, 0),
                  new MobDrop(ItemsRegister.EREBRISFRAGMENT, 0.75F, 0, 1, 2, 0)
               }
            );
            this.livingSound = Sounds.mob_monster_living;
         }

         this.setRoleValues(EnumMobRole.MINI_BOSS, 4);
         this.soul = Soul.NOBLE;
      }

      @Override
      public void dropShards() {
         ShardType.spawnShards(ShardType.EARTH, this, 8.0F * dropShardsQuantity);
         ShardType.spawnShards(ShardType.VOID, this, 5.0F * dropShardsQuantity);
         ShardType.spawnShards(ShardType.LIVE, this, 10.0F * dropShardsQuantity);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_BLOOD;
      }

      @Override
      protected SoundEvent getDeathSound() {
         return this.isSubMob ? null : Sounds.underworld_digger_death;
      }

      protected float getSoundVolume() {
         return 2.75F;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("sybiots", this.var1);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("sybiots")) {
            this.var1 = compound.getInteger("sybiots");
         }

         super.readEntityFromNBT(compound);
      }

      public UnderworldDigger(World world) {
         this(world, false, 0, null);
      }

      @Override
      public int getSegmentsCount() {
         return 20;
      }

      @Override
      public float getSegmentMotionPower(Vec3d vec) {
         return (float)GetMOP.getSpeed(this.getOwnerIfSegment());
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && (this.ticksExisted < 3 || this.ticksExisted % 60 == 0)) {
            this.world.setEntityState(this, (byte)(MathHelper.clamp(this.var1, 0, 4) - 10));
         }
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if ((
               source != DamageSource.OUT_OF_WORLD
                  || this.world.getBlockState(this.getPosition()).getCollisionBoundingBox(this.world, this.getPosition()) == null
            )
            && amount > 0.0F
            && !this.world.isRemote) {
            List<UnderworldSymbiote> list = this.world
               .getEntitiesWithinAABB(UnderworldSymbiote.class, GetMOP.newAABB(this, 64.0));
            int allowToSpawned = 64 - list.size();
            if (allowToSpawned > 0) {
               int varhas = this.var1;

               for (this.healthSymbiots -= amount; this.healthSymbiots <= 0.0F && this.var1 > 0 && allowToSpawned > -16; allowToSpawned--) {
                  this.healthSymbiots = this.healthSymbiots + this.maxhealthSymbiots;
                  this.var1--;
                  UnderworldSymbiote part = new UnderworldSymbiote(this.world);
                  part.setPosition(this.posX, this.posY + this.height / 2.0F, this.posZ);
                  part.motionX = this.rand.nextGaussian() / 3.0;
                  part.motionY = this.rand.nextGaussian() / 3.0;
                  part.motionZ = this.rand.nextGaussian() / 3.0;
                  part.velocityChanged = true;
                  this.world.spawnEntity(part);
                  part.onInitialSpawn();
                  part.team = this.team;
                  part.canDropLoot = this.canDropLoot;
                  if (this.getOwnerIfSegment() != null) {
                     part.isAgressive = this.getOwnerIfSegment().isAgressive;
                  } else {
                     part.isAgressive = this.isAgressive;
                  }

                  if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase) {
                     part.setAttackTarget((EntityLivingBase)source.getTrueSource());
                  }
               }

               if (source != DamageSource.OUT_OF_WORLD && this.var1 != varhas) {
                  this.world.setEntityState(this, (byte)(MathHelper.clamp(this.var1, 0, 4) - 10));
                  this.world.setEntityState(this, (byte)7);
               }
            }
         }

         return super.attackEntityFrom(source, amount);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id <= -6 && id >= -10) {
            this.var1 = id + 10;
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slimeshoot_a,
                  SoundCategory.HOSTILE,
                  1.2F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 6; ss++) {
               GUNParticle bubble = new GUNParticle(
                  slime,
                  0.6F + this.rand.nextFloat() / 3.0F,
                  -0.001F,
                  10 + this.rand.nextInt(10),
                  -1,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 19.0F,
                  (float)this.rand.nextGaussian() / 22.0F + 0.05F,
                  (float)this.rand.nextGaussian() / 19.0F,
                  0.77F + (float)this.rand.nextGaussian() / 10.0F,
                  0.68F + (float)this.rand.nextGaussian() / 10.0F,
                  0.53F + (float)this.rand.nextGaussian() / 10.0F,
                  true,
                  this.rand.nextInt(180)
               );
               bubble.alphaTickAdding = -0.04F;
               bubble.scaleTickAdding = 0.07F;
               this.world.spawnEntity(bubble);
            }
         }
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData inits = super.onInitialSpawn(difficulty, livingdata);
         if (!this.world.isRemote && !this.isSubMob) {
            for (int i = 0; i < this.getSegmentsCount(); i++) {
               UnderworldDigger part = new UnderworldDigger(this.world, true, i, this);
               part.setPosition(this.posX, this.posY - i / 5.0, this.posZ);
               this.world.spawnEntity(part);
               part.onInitialSpawn();
               part.team = this.team;
            }
         }

         this.var1 = 4;
         this.healthSymbiots = this.maxhealthSymbiots;
         return inits;
      }

      public void onLivingUpdate() {
         super.onLivingUpdate();
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISegmAABBAttack(this, 50, 0.65F));
         if (!this.isSubMob) {
            this.tasks.addTask(2, new EntityAIWorm(this, 100, 10.0F, 10.0F, 0.0F, 0.8F, 0.35F, 1.05F, true, 20));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
            this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
         }
      }
   }

   public static class UnderworldSymbiote extends AbstractMob {
      public float friction = 0.98F;

      public UnderworldSymbiote(World world) {
         super(world, 0.78F, 0.78F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.livingSound = Sounds.mob_squish_living;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(30.0, 64.0, 4.0, 0.08, 0.0, 0.0, 0.0, 0.0, 0.3, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.EREBRISSHARD, 0.065F, 0, 1, 1, 0)});
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.SWARMER, 4);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.AIR, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WORM_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         this.motionX = this.motionX * this.friction;
         this.motionY = this.motionY * this.friction;
         this.motionZ = this.motionZ * this.friction;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRayLogicFly(this));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 20, 0.1F));
         this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Weaver extends AbstractGlyphid {
      public static ResourceLocation tex0 = new ResourceLocation("arpg:textures/weaver_model_tex1.png");
      public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/weaver_model_tex2.png");
      public static ResourceLocation tex2 = new ResourceLocation("arpg:textures/weaver_model_tex3.png");
      public int VARIANT = 0;

      public Weaver(World world) {
         super(world, 0.8F, 0.8F);
         this.hurtSound = SoundEvents.ENTITY_SPIDER_HURT;
         this.deathSound = SoundEvents.ENTITY_SPIDER_DEATH;
         this.livingSound = SoundEvents.ENTITY_SPIDER_AMBIENT;
         this.stepSound = SoundEvents.ENTITY_SPIDER_STEP;
         this.defaultteam = DungeonMobsPack.mobsteam;
         this.setattributes(80.0, 32.0, 10.0, 0.16, 7.0, 2.0, 0.1, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.STRING, 0.8F, 0, 1, 2, 2),
               new MobDrop(Items.SPIDER_EYE, 0.5F, 0, 1, 2, 2),
               new MobDrop(ItemsRegister.EREBRISSHARD, 0.45F, 0, 0, 2, 0),
               new MobDrop(ItemsRegister.WEBGH, 0.03F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.SOLDIER, 4);
         this.soul = Soul.SHADOW;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.POISON, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.WEAVER_BLOOD;
      }

      public float getCollisionBorderSize() {
         return 0.35F;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("variant", this.VARIANT);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("variant")) {
            this.VARIANT = compound.getInteger("variant");
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            this.VARIANT = this.rand.nextInt(3);
            if (this.VARIANT == 0) {
               this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.maxHealth + 5.0);
            }

            if (this.VARIANT == 1) {
               this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed * 1.15);
            }

            if (this.VARIANT == 2) {
               this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.damage + 2.0);
            }
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (!this.world.isRemote) {
            if (this.VARIANT == 1) {
               Weapons.disableShield(entityIn, false);
            }

            if (this.VARIANT == 2) {
               Weapons.setPotionIfEntityLB(entityIn, MobEffects.POISON, 60 + this.rand.nextInt(100) + this.getMobDifficulty() * 20, 1);
            }
         }

         return super.attackEntityAsMob(entityIn);
      }

      @Override
      public void onUpdate() {
         AxisAlignedBB aabb = this.getEntityBoundingBox().grow(0.5);
         boolean is = this.world.collidesWithAnyBlock(aabb);
         if (is) {
            this.fallDistance = 0.0F;
         }

         this.setNoGravity(is);
         super.onUpdate();
         if (this.ticksExisted <= 2 || this.ticksExisted % 41 == 0) {
            this.world.setEntityState(this, (byte)(this.VARIANT + 8));
         }
      }

      public void fall(float distance, float damageMultiplier) {
         super.fall(distance, damageMultiplier * 0.7F);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 7) {
            for (int i = 0; i < 5; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.CLOUD,
                     this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     this.posY + 0.5 + this.rand.nextFloat() * this.height,
                     this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
               this.world
                  .spawnParticle(
                     EnumParticleTypes.HEART,
                     this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     this.posY + 0.5 + this.rand.nextFloat() * this.height,
                     this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
            }
         }

         if (id >= 8 && id <= 10) {
            this.VARIANT = id - 8;
         }

         super.handleStatusUpdate(id);
      }

      public void setInWeb() {
         this.fallDistance = 0.0F;
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.ARTHROPOD;
      }

      protected void initEntityAI() {
         this.ai = new EntityAIGlyphid(this);
         this.tasks.addTask(1, this.ai);
         this.tasks.addTask(3, new EntityAIAABBAttack(this, 40, 0.5F));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         if (this.VARIANT == 0) {
            return tex0;
         } else {
            return this.VARIANT == 1 ? tex1 : tex2;
         }
      }
   }
}

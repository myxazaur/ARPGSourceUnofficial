package com.vivern.arpg.mobs;

import baubles.api.BaublesApi;
import com.vivern.arpg.elements.models.BlubberModel;
import com.vivern.arpg.elements.models.CinderCrawlerModel;
import com.vivern.arpg.elements.models.EnderSeerModel;
import com.vivern.arpg.elements.models.ModelsOtherMob;
import com.vivern.arpg.elements.models.VoidGuardModel;
import com.vivern.arpg.entity.AbstractGlyphid;
import com.vivern.arpg.entity.EntityArrowBouncing;
import com.vivern.arpg.entity.EntityPart;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.BloodType;
import com.vivern.arpg.main.Catalyst;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OtherMobsPack {
   public static String mobsteamnether = "nether.mob.team";
   public static String mobsteamender = "ender.mob.team";
   public static String mobsteamoverworld = "overworld.mob.team";

   public static void init() {
      AbstractMob.addToRegister(Varmint.class, "Varmint", 6563873, 4670063);
      AbstractMob.addToRegister(LavaSprinkler.class, "Lava Sprinkler", 2755594, 16754725);
      AbstractMob.addToRegister(CinderCrawler.class, "Cinder Crawler", 16768943, 16771868);
      AbstractMob.addToRegister(VoidGuard.class, "Void Guard", 1048610, 9699542);
      AbstractMob.addToRegister(EnderSeer.class, "Ender Seer", 4597633, 10364856, 128, 1);
      AbstractMob.addToRegister(EntityPart.class, "Entity Part", 0, 16711935);
      AbstractMob.addToRegister(Hellhound.class, "Hellhound", 2236476, 9055713);
      AbstractMob.addToRegister(MechanicalWatcher.class, "Mechanical Watcher", 2695751, 14108666);
      AbstractMob.addToRegister(HorribleEvoker.class, "Horrible Evoker", 9345947, 2444078);
      AbstractMob.addToRegister(HorribleVindicator.class, "Horrible Vindicator", 9345947, 2631720);
      AbstractMob.addToRegister(Equestrian.class, "Equestrian", 9021880, 3355443);
      AbstractMob.addToRegister(Blubber.class, "Blubber", 14117083, 3607113);
   }

   public static void initRender() {
      AbstractMob.addToRender(new AbstractMob.RenderAbstractMobEntry(new ModelsOtherMob.VarmintModel(), 0.3F, Varmint.class));
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.LavaSprinklerModel(),
            new ResourceLocation("arpg:textures/lava_sprinkler_model_tex.png"),
            0.6F,
            LavaSprinkler.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new CinderCrawlerModel(), new ResourceLocation("arpg:textures/cinder_crawler_model_tex.png"), 0.25F, CinderCrawler.class
         )
      );
      AbstractMob.addToRender(new AbstractMob.RenderAbstractMobEntry(new VoidGuardModel(), 0.2F, VoidGuard.class));
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new EnderSeerModel(), new ResourceLocation("arpg:textures/ender_seer_model_tex.png"), 1.0F, EnderSeer.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.HellhoundModel(), new ResourceLocation("arpg:textures/hellhound_model_tex.png"), 0.4F, Hellhound.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.MechanicalWatcherModel(),
            new ResourceLocation("arpg:textures/mechanical_watcher_model_tex.png"),
            0.25F,
            MechanicalWatcher.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.HorribleEvokerModel(),
            new ResourceLocation("arpg:textures/horrible_evoker_model_tex.png"),
            0.4F,
            HorribleEvoker.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.HorribleVindicatorModel(),
            new ResourceLocation("arpg:textures/horrible_vindicator_model_tex.png"),
            0.3F,
            HorribleVindicator.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.EquestrianModel(), new ResourceLocation("arpg:textures/equestrian_model_tex.png"), 0.43F, Equestrian.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(new BlubberModel(), new ResourceLocation("arpg:textures/blubber_model_tex.png"), 0.2F, Blubber.class)
      );
   }

   public static class CinderCrawler extends AbstractGlyphid {
      public CinderCrawler(World world) {
         super(world, 0.8F, 0.8F);
         this.hurtSound = Sounds.mob_wild_hurt;
         this.deathSound = Sounds.mob_wild_death;
         this.livingSound = Sounds.mob_wild_living;
         this.defaultteam = OtherMobsPack.mobsteamnether;
         this.setattributes(25.0, 32.0, 6.0, 0.1, 4.0, 1.5, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.RAWRIBS, 0.4F, 0, 1, 1, 1), new MobDrop(ItemsRegister.NUGGETMOLTEN, 0.1F, 0, 1, 1, 1)
            }
         );
         this.isImmuneToFire = true;
         this.setRoleValues(EnumMobRole.SOLDIER, 1);
         this.soul = Soul.EXHAUSTED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.FIRE, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.CINDER_BLOOD;
      }

      public float getCollisionBorderSize() {
         return 0.5F;
      }

      @Override
      public boolean isAiCompatible(EntityAIGlyphid ai) {
         return ai.glyphidAiType == 1 || ai.glyphidAiType == 0;
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (player.getHeldItemMainhand().getItem() == ItemsRegister.MAGMABLOOMSEEDS) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.owner == null && this.rand.nextFloat() < 0.5) {
               this.isAgressive = false;
               this.owner = player;
               this.team = Team.getTeamFor(player);
               this.navigator.clearPath();
               this.setAttackTarget((EntityLivingBase)null);
               this.world.setEntityState(this, (byte)7);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      protected float getSoundPitch() {
         return super.getSoundPitch() * 0.8F;
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
      }

      public void fall(float distance, float damageMultiplier) {
         super.fall(distance, damageMultiplier * 0.9F);
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
                     EnumParticleTypes.FLAME,
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

         super.handleStatusUpdate(id);
      }

      public void setInWeb() {
         this.fallDistance = 0.0F;
      }

      @Override
      public boolean checkPosition(World world, BlockPos pos) {
         return world.getBlockState(pos).getMaterial() == Material.LAVA || super.checkPosition(world, pos);
      }

      @Override
      public boolean isPassable(World world, BlockPos pos) {
         IBlockState state = world.getBlockState(pos);
         PathNodeType nodetype = state.getBlock().getAiPathNodeType(state, world, pos, this);
         if (nodetype == PathNodeType.DAMAGE_FIRE || nodetype == PathNodeType.LAVA) {
            return true;
         } else {
            return nodetype == PathNodeType.DAMAGE_OTHER ? false : state.getCollisionBoundingBox(world, pos) == null;
         }
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.ARTHROPOD;
      }

      protected void initEntityAI() {
         this.ai = new EntityAILavaGlyphid(this);
         this.tasks.addTask(1, this.ai);
         this.tasks.addTask(2, new EntityAIDash(this, 70, 5.0F, 0.0F, 1.2F, false, 0.23F));
         this.tasks.addTask(3, new EntityAIAABBAttack(this, 30, 0.3F));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }
   }

   public static class EnderSeer extends AbstractMob {
      ResourceLocation texture = new ResourceLocation("arpg:textures/purple_smoke.png");

      public EnderSeer(World world) {
         super(world, 4.5F, 2.25F);
         this.setNoGravity(true);
         this.hurtSound = Sounds.ender_seer_hurt;
         this.deathSound = Sounds.ender_seer_dead;
         this.livingSound = Sounds.ender_seer_living;
         this.defaultteam = OtherMobsPack.mobsteamender;
         this.setattributes(75.0, 64.0, 1.0, 0.1, 5.0, 3.0, 0.7, 0.0, 1.0, 0.1);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.EYEOFSEER, 1.0F, 0, 1, 1, 0)});
         this.setDropMoney(-5, 15, 0.9F);
         this.experienceValue = 20;
         this.soul = Soul.NOBLE;
      }

      @Override
      public void dropShards() {
         ShardType.spawnShards(ShardType.VOID, this, 6.0F * dropShardsQuantity);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ENDER_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      protected boolean canDespawn() {
         return this.getAttackTarget() == null || this.getDistance(this.getAttackTarget()) > 100.0F;
      }

      protected float getSoundVolume() {
         return 10.0F;
      }

      public void onEntityUpdate() {
         if (this.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(
               this.texture,
               1.9F + (float)this.rand.nextGaussian() / 3.0F,
               -0.002F,
               15 + this.rand.nextInt(5),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               -0.3F,
               0.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               0.5F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire2.alphaTickAdding = -0.02F;
            fire2.scaleTickAdding = -0.07F;
            fire2.alphaGlowing = true;
            this.world.spawnEntity(fire2);
         }

         super.onEntityUpdate();
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.ender_seer_shoot,
                  SoundCategory.AMBIENT,
                  15.6F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIFlying(this, 150, 25.0F, 0.02F, false));
         this.tasks.addTask(2, new EntityAIEnderSeerAttack(this));
         this.tasks.addTask(2, new EntityAIFlyingKeepDistToTarget(this, 200, 60.0F, 0.4F, 20.0F, 65.0F, 20.0F, 2.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Equestrian extends AbstractMob {
      public static int shootCooldown = 50;
      public static int maxTicksFly = 200;
      public static int maxFlyCooldown = 250;
      public int ticksFly;
      public int flyCooldown;
      public EntityAISkeleton aiSkeleton;
      public EntityAICorrupter aiCorrupter;

      public Equestrian(World world) {
         super(world, 1.0F, 1.3F);
         this.hurtSound = SoundEvents.ENTITY_SILVERFISH_HURT;
         this.deathSound = SoundEvents.EVOCATION_ILLAGER_DEATH;
         this.livingSound = SoundEvents.ENTITY_SILVERFISH_AMBIENT;
         this.defaultteam = OtherMobsPack.mobsteamoverworld;
         this.setattributes(40.0, 32.0, 2.0, 0.2, 5.0, 1.0, 0.3, 0.0, 0.0, 0.2);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.EMERALD, 0.2F, 0, 1, 1, 1),
               new MobDrop(ItemsRegister.ARROWBOUNCING, 0.85F, 0, 2, 4, 3),
               new MobDrop(ItemsRegister.BLACKSTRAP, 0.8F, 0, 1, 1, 0)
            }
         );
         this.leadershipBase = 10;
         this.setDropMoney(-3, 6, 1.0F);
         this.soul = Soul.GREEDY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.VOID, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.BLACK_STRAP_BLOOD;
      }

      @Override
      public void shoot() {
         EntityLivingBase target = this.getAttackTarget();
         if (target != null) {
            EntityArrowBouncing projectile = new EntityArrowBouncing(this.world, this);
            projectile.shoot(this, this.rotationPitch - 4.0F, this.rotationYaw, 0.0F, 1.8F, 2.0F);
            projectile.pickupStatus = PickupStatus.CREATIVE_ONLY;
            projectile.bowDamage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(projectile);
            this.triggerAnimation(-1);
         }
      }

      public void fall(float distance, float damageMultiplier) {
         super.fall(Math.max(distance - 10.0F, 0.0F), damageMultiplier);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            if (!this.isAIDisabled()) {
               if (this.ticksFly > 0) {
                  this.ticksFly--;
                  this.fallDistance = 0.0F;
                  if (this.ticksFly == 0) {
                     this.disableFlight();
                  }
               }

               if (this.flyCooldown > 0) {
                  this.flyCooldown--;
               }

               if (target != null
                  && this.getNavigator().noPath()
                  && !this.getEntitySenses().canSee(target)
                  && this.flyCooldown <= 0
                  && this.ticksFly <= 0) {
                  this.allowFlight();
               }
            }
         }
      }

      public void allowFlight() {
         this.aiSkeleton.enable = false;
         this.aiCorrupter.enable = true;
         this.ticksFly = maxTicksFly;
         this.setNoGravity(true);
         this.motionY += 0.1;
         this.velocityChanged = true;
      }

      public void disableFlight() {
         this.aiSkeleton.enable = true;
         this.aiCorrupter.enable = false;
         this.flyCooldown = maxFlyCooldown;
         this.setNoGravity(false);
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (this.flyCooldown <= 0
            && !this.world.isRemote
            && this.ticksFly <= 0
            && source.getTrueSource() != null
            && amount >= 2.0F
            && Team.checkIsOpponent(source.getTrueSource(), this)
            && this.getDistanceSq(source.getTrueSource()) < 16.0) {
            this.allowFlight();
         }

         return super.attackEntityFrom(source, amount);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == -1) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.ENTITY_ARROW_SHOOT,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == -3) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.crossbow_load,
                  SoundCategory.HOSTILE,
                  1.2F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         super.handleStatusUpdate(id);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.aiSkeleton = new EntityAISkeleton(this, 1.0, shootCooldown, 32.0F, 10);
         this.aiCorrupter = new EntityAICorrupter(this, shootCooldown, 32.0F, 10, true, 14.0F, 4.0F);
         this.aiCorrupter.enable = false;
         this.aiSkeleton.triggerAnim3 = true;
         this.aiCorrupter.triggerAnim3 = true;
         this.tasks.addTask(2, this.aiSkeleton);
         this.tasks.addTask(2, this.aiCorrupter);
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Hellhound extends AbstractMob {
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/clouds3.png");
      public int fireCooldown = 20;
      public int fireCooldownMax = 120;
      public int fireTime = 0;
      public static int fireTimeMax = 80;
      public static int fireTimeDamageTime = 50;

      public Hellhound(World world) {
         super(world, 1.0F, 0.95F);
         this.hurtSound = Sounds.mob_monster_hurt;
         this.deathSound = SoundEvents.ENTITY_WOLF_DEATH;
         this.livingSound = Sounds.mob_plant_living;
         this.stepSound = SoundEvents.ENTITY_WOLF_STEP;
         this.defaultteam = OtherMobsPack.mobsteamnether;
         this.setattributes(35.0, 48.0, 5.0, 0.32, 2.0, 2.0, 0.4, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.HELLHOUNDCOLLAR, 0.07F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.HELLHOUNDFUR, 0.85F, 0, 1, 1, 1)
            }
         );
         this.leadershipBase = 7;
         this.isImmuneToFire = true;
         this.setRoleValues(EnumMobRole.WEAK_TANK, 1);
         this.soul = Soul.EXHAUSTED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.DEMONIC_VIOLET_BLOOD;
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (player.getHeldItemMainhand().getItem() instanceof ItemFood && ((ItemFood)player.getHeldItemMainhand().getItem()).isWolfsFavoriteMeat()) {
            if (this.owner == null) {
               if (!player.capabilities.isCreativeMode) {
                  player.getHeldItemMainhand().shrink(1);
               }

               if (this.rand.nextFloat() < 0.15) {
                  this.tame(player);
                  this.world.setEntityState(this, (byte)7);
                  ItemStack ring1 = BaublesApi.getBaublesHandler(player).getStackInSlot(1).copy();
                  if (Catalyst.checkAndApplyHexToItem(ring1, "hex_hellhound_own", "Licked by Hellhound")) {
                     BaublesApi.getBaublesHandler(player).setStackInSlot(1, ring1);
                  }

                  ItemStack ring2 = BaublesApi.getBaublesHandler(player).getStackInSlot(2).copy();
                  if (Catalyst.checkAndApplyHexToItem(ring2, "hex_hellhound_own", "Licked by Hellhound")) {
                     BaublesApi.getBaublesHandler(player).setStackInSlot(2, ring2);
                  }
               }
            } else if (!player.getCooldownTracker().hasCooldown(player.getHeldItemMainhand().getItem())) {
               this.heal(6.0F);
               player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 10);
               if (!player.capabilities.isCreativeMode) {
                  player.getHeldItemMainhand().shrink(1);
               }
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      protected float getSoundPitch() {
         return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.85F;
      }

      public float getCollisionBorderSize() {
         return 0.3F;
      }

      public float getEyeHeight() {
         return this.height * 0.85F;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.DEMONIC_BURN ? false : super.isPotionApplicable(potioneffectIn);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.fireTime > 0) {
            this.fireTime--;
            if (this.fireTime <= fireTimeDamageTime) {
               if (this.fireTime == fireTimeDamageTime) {
                  this.world
                     .playSound(
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.fire_d,
                        SoundCategory.HOSTILE,
                        1.5F,
                        0.9F + this.rand.nextFloat() / 5.0F,
                        false
                     );
               }

               if (!this.world.isRemote) {
                  for (EntityLivingBase base : GetMOP.MopRayTrace(4.5F + this.world.getDifficulty().getId() / 1.25F, 1.0F, this, 0.85F, 0.6F)) {
                     if (Team.checkIsOpponent(this, base)) {
                        Weapons.dealDamage(new WeaponDamage(null, this, null, false, false, this, WeaponDamage.soul), 2.0F, this, base, true, 0.2F);
                        base.addPotionEffect(new PotionEffect(PotionEffects.DEMONIC_BURN, 100));
                        DeathEffects.applyDeathEffect(base, DeathEffects.DE_FIRE, 0.4F);
                     }
                  }
               } else {
                  GUNParticle part = new GUNParticle(
                     flame,
                     0.1F,
                     -0.004F,
                     20 + this.rand.nextInt(10),
                     240,
                     this.world,
                     this.posX,
                     this.posY + this.getEyeHeight(),
                     this.posZ,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.15F + this.rand.nextFloat() * 0.2F,
                     0.0F,
                     1.0F,
                     true,
                     this.rand.nextInt(360),
                     true,
                     1.1F
                  );
                  part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.45F, 4.0F);
                  part.scaleTickAdding = 0.05F;
                  part.alphaGlowing = true;
                  this.world.spawnEntity(part);
                  part = new GUNParticle(
                     cloud,
                     0.05F,
                     -0.006F,
                     15 + this.rand.nextInt(10),
                     200,
                     this.world,
                     this.posX,
                     this.posY + this.getEyeHeight(),
                     this.posZ,
                     0.0F,
                     0.0F,
                     0.0F,
                     1.0F - this.rand.nextFloat() * 0.3F,
                     0.15F + this.rand.nextFloat() * 0.2F,
                     1.0F,
                     true,
                     this.rand.nextInt(360),
                     true,
                     1.1F
                  );
                  part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.45F, 5.0F);
                  part.scaleTickAdding = 0.03F;
                  part.alphaGlowing = true;
                  this.world.spawnEntity(part);
               }
            }
         } else if (!this.world.isRemote) {
            if (this.fireCooldown <= 0) {
               if (this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) <= 9.0 && this.getEntitySenses().canSee(this.getAttackTarget())) {
                  this.fireCooldown = this.fireCooldownMax;
                  this.fireTime = fireTimeMax;
                  this.triggerAnimation(-2);
                  this.world.setEntityState(this, (byte)8);
               }
            } else {
               this.fireCooldown--;
            }
         }
      }

      public float getAIMoveSpeed() {
         return this.fireTime > 0 ? 0.0F : super.getAIMoveSpeed();
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.fireTime = fireTimeMax;
         }

         if (id == 7) {
            for (int i = 0; i < 5; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.FLAME,
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
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, false));
         this.tasks.addTask(1, new EntityAIDash(this, 70, 3.0F, 0.0F, 0.23F, true, 0.4F));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class HorribleEvoker extends AbstractMob {
      public int spellCooldown;
      public int spellCasting;
      public static int spellCooldownMax = 220;
      public static int spellsMax = 3;
      public ArrayList<Vec3d> spellPoses = new ArrayList<>();

      public HorribleEvoker(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
         this.deathSound = SoundEvents.ENTITY_ILLAGER_DEATH;
         this.livingSound = SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
         this.defaultteam = OtherMobsPack.mobsteamoverworld;
         this.setattributes(20.0, 32.0, 4.0, 0.28, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.EMERALD, 0.35F, 0, 1, 1, 1), new MobDrop(ItemsRegister.BLACKSTRAP, 0.75F, 0, 1, 1, 0)
            }
         );
         this.leadershipBase = 6;
         this.setDropMoney(-3, 5, 1.0F);
         this.soul = Soul.MYSTIC;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.VOID, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.BLACK_STRAP_BLOOD;
      }

      @Override
      public void shoot() {
         EntityLivingBase target = this.getAttackTarget();
         if (target != null) {
            this.world.setEntityState(this, (byte)11);
            HostileProjectiles.HorribleEmeraldShoot projectile = new HostileProjectiles.HorribleEmeraldShoot(this.world, this);
            projectile.motionY = 0.1;
            projectile.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            projectile.targetEntity = target;
            projectile.shootAcceleration = 1.3F;
            this.world.spawnEntity(projectile);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            if (!this.isAIDisabled() && target != null) {
               if (this.spellCasting > 0) {
                  if (this.spellCasting == 1) {
                     if (!this.spellPoses.isEmpty()) {
                        for (Vec3d pos : this.spellPoses) {
                           this.spawnPotionSpell(pos, true);
                        }
                     }

                     this.world.setEntityState(this, (byte)7);
                     this.spellCasting = 0;
                     this.spellCooldown = spellCooldownMax;
                  } else {
                     this.spellCasting--;
                  }
               } else {
                  this.spellCooldown--;
                  if (this.spellCooldown <= 0 && target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                     this.spellPoses.clear();
                     this.spellPoses.add(target.getPositionVector());
                     if (GetMOP.getSpeed(target) > 0.05) {
                        Vec3d speed = new Vec3d(target.motionX, 0.0, target.motionZ).normalize().scale(5.0);
                        this.spellPoses.add(target.getPositionVector().add(speed));
                     }

                     int smax = spellsMax - this.spellPoses.size();

                     for (int i = 0; i < smax; i++) {
                        Vec3d randomvec = new Vec3d(this.rand.nextGaussian(), 0.0, this.rand.nextGaussian())
                           .normalize()
                           .scale(3.0F + this.rand.nextFloat() * 3.0F);
                        GetMOP.BlockTraceResult result = GetMOP.blockTrace(
                           this.world, new BlockPos(target.getPositionEyes(1.0F).add(randomvec)), EnumFacing.DOWN, 3, null
                        );
                        if (result != null) {
                           this.spellPoses
                              .add(new Vec3d(result.prevPos.getX() + 0.5, result.prevPos.getY() + 0.2, result.prevPos.getZ() + 0.5));
                        } else {
                           this.spellPoses.add(target.getPositionVector().add(randomvec));
                        }
                     }

                     this.spellCasting = 40;
                     if (!this.spellPoses.isEmpty()) {
                        for (Vec3d pos : this.spellPoses) {
                           this.spawnPotionSpell(pos, false);
                        }
                     }

                     this.world.setEntityState(this, (byte)8);
                  }
               }
            }
         }
      }

      public void spawnPotionSpell(Vec3d pos, boolean phase2) {
         if (phase2) {
            EntityPotion entitypotion = new EntityPotion(
               this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.HARMING)
            );
            entitypotion.rotationPitch -= -8.0F;
            entitypotion.setPosition(pos.x, pos.y + 0.25, pos.z);
            this.world.spawnEntity(entitypotion);
         } else {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, pos.x, pos.y, pos.z);
            entityareaeffectcloud.setOwner(this);
            entityareaeffectcloud.setParticle(EnumParticleTypes.SPELL);
            entityareaeffectcloud.setRadius(1.65F);
            entityareaeffectcloud.setDuration(30);
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 2));
            this.world.spawnEntity(entityareaeffectcloud);
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK,
                  SoundCategory.HOSTILE,
                  1.2F,
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
                  SoundEvents.EVOCATION_ILLAGER_CAST_SPELL,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_l,
                  SoundCategory.HOSTILE,
                  1.2F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         super.handleStatusUpdate(id);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         EntityAISkeleton ai3 = new EntityAISkeleton(this, 1.0, 70, 48.0F, 1).setBurst(true, 2, 0, true, 7);
         this.tasks.addTask(2, ai3);
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class HorribleVindicator extends AbstractMob {
      public int invisibleCooldown;
      public static int invisibleCooldownMax = 200;
      public int dashBackTime;
      public static float dashPower = 2.0F;
      public EntityAIDash dashAi;

      public HorribleVindicator(World world) {
         super(world, 0.6F, 1.8F);
         this.hurtSound = SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
         this.deathSound = SoundEvents.ENTITY_ILLAGER_DEATH;
         this.livingSound = SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
         this.defaultteam = OtherMobsPack.mobsteamoverworld;
         this.setattributes(30.0, 32.0, 5.0, 0.32, 4.0, 2.0, 0.0, 0.0, 0.0, 0.2);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.EMERALD, 0.2F, 0, 1, 1, 1), new MobDrop(ItemsRegister.BLACKSTRAP, 0.85F, 0, 1, 1, 0)
            }
         );
         this.leadershipBase = 7;
         this.setDropMoney(-3, 6, 1.0F);
         this.soul = Soul.GREEDY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.VOID, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.BLACK_STRAP_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
         super.fall(Math.max(distance - 2.0F, 0.0F), damageMultiplier);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            if (!this.isAIDisabled()) {
               if (this.invisibleCooldown <= 0) {
                  if (target != null && this.getEntitySenses().canSee(target)) {
                     this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 80));
                     if (target instanceof EntityCreature) {
                        EntityCreature creature = (EntityCreature)target;
                        if (creature.getAttackTarget() == this) {
                           creature.setAttackTarget(null);
                        }
                     }

                     this.world.setEntityState(this, (byte)8);
                     if (this.dashAi != null) {
                        this.dashAi.attackTimer = 90;
                     }

                     this.invisibleCooldown = invisibleCooldownMax;
                  }
               } else {
                  this.invisibleCooldown--;
               }
            }

            if (this.dashBackTime > 0) {
               this.dashBackTime--;
               if (this.dashBackTime == 20 && target != null) {
                  SuperKnockback.applyMove(this, dashPower, target.posX, target.posY, target.posZ);
                  this.motionY += 0.08F;
                  this.velocityChanged = true;
                  this.world.setEntityState(this, (byte)11);
               }

               if (this.dashBackTime < 20) {
                  GetMOP.BlockTraceResult result = GetMOP.blockTrace(
                     this.world,
                     new BlockPos(this.posX + this.motionX, this.posY, this.posZ + this.motionZ),
                     EnumFacing.DOWN,
                     4,
                     null
                  );
                  if (result == null) {
                     this.motionX *= 0.05;
                     this.motionZ *= 0.05;
                     this.dashBackTime = 0;
                  } else {
                     PathNodeType pathNodeType = this.getNavigator()
                        .getNodeProcessor()
                        .getPathNodeType(this.world, result.pos.getX(), result.pos.getY(), result.pos.getZ());
                     if (pathNodeType != null
                        && (
                           pathNodeType == PathNodeType.DAMAGE_CACTUS
                              || pathNodeType == PathNodeType.DAMAGE_FIRE
                              || pathNodeType == PathNodeType.DAMAGE_OTHER
                              || pathNodeType == PathNodeType.DANGER_CACTUS
                              || pathNodeType == PathNodeType.DANGER_FIRE
                              || pathNodeType == PathNodeType.DANGER_OTHER
                              || pathNodeType == PathNodeType.LAVA
                        )) {
                        this.motionX *= 0.05;
                        this.motionZ *= 0.05;
                        this.dashBackTime = 0;
                     }
                  }
               }
            }
         }
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (!this.isAIDisabled() && this.dashBackTime == 0) {
            this.dashBackTime = 25;
         }

         return super.attackEntityAsMob(entityIn);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.horrible_vindicator_invisible,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int i = 0; i < 20; i++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.SMOKE_LARGE,
                     this.posX + (this.rand.nextFloat() - 0.5) * 2.0 * this.width,
                     this.posY + this.rand.nextFloat() * this.height,
                     this.posZ + (this.rand.nextFloat() - 0.5) * 2.0 * this.width,
                     0.0,
                     0.0,
                     0.0,
                     new int[0]
                  );
            }
         }

         if (id == -1) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.horrible_vindicator_attack,
                  SoundCategory.HOSTILE,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.dash_a,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         super.handleStatusUpdate(id);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.tasks.addTask(2, new EntityAIRush(this, true, true, true));
         this.dashAi = new EntityAIDash(this, 50, 6.0F, 2.0F, dashPower, false, 0.08F).setSoundOnDash(Sounds.dash_a);
         this.tasks.addTask(3, this.dashAi);
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class LavaSprinkler extends AbstractMob {
      public LavaSprinkler(World world) {
         super(world, 0.85F, 2.0F);
         this.hurtSound = Sounds.mob_slime_hurt;
         this.deathSound = Sounds.mob_zombie_death;
         this.livingSound = Sounds.mob_zombie_living;
         this.defaultteam = OtherMobsPack.mobsteamnether;
         this.setattributes(35.0, 48.0, 4.0, 0.08, 2.0, 2.0, 0.3, 0.1, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.LIQUIDFIRE, 0.85F, 0, 1, 3, 2), new MobDrop(ItemsRegister.RAWRIBS, 0.9F, 0, 1, 3, 2)
            }
         );
         this.isImmuneToFire = true;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 1);
         this.soul = Soul.EXHAUSTED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.EARTH, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.LAVA_BLOOD;
      }

      public float getCollisionBorderSize() {
         return 0.3F;
      }

      public void onLivingUpdate() {
         super.onLivingUpdate();
         if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
         }
      }

      public float getEyeHeight() {
         return this.height * 0.2F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.LavaShoot entity = new HostileProjectiles.LavaShoot(this.world, this);
            entity.shoot(this, this.rotationPitch - 6.0F, this.rotationYaw, 0.0F, 1.2F, 3.5F);
            entity.damage = 5.0F;
            this.world.spawnEntity(entity);
         }
      }

      public void fall(float distance, float damageMultiplier) {
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
                  Sounds.lava_shoot,
                  SoundCategory.HOSTILE,
                  1.1F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFloatingSkeleton(this, 40, 16.0F, 0, true, 9.0F, 3.0F, 3.5F, 0.15F, true, false));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 30, 0.3F));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class MechanicalWatcher extends AbstractMob {
      public static ResourceLocation void_explode = new ResourceLocation("arpg:textures/void_explode.png");
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
      public double homeX = 0.0;
      public double homeY = 0.0;
      public double homeZ = 0.0;
      public double postHomeX = 0.0;
      public double postHomeY = 0.0;
      public double postHomeZ = 0.0;
      public int livetime = 200;

      public MechanicalWatcher(World world) {
         super(world, 0.6F, 0.85F);
         this.hurtSound = Sounds.mob_alienfly_hurt;
         this.deathSound = Sounds.mob_alienfly_death;
         this.livingSound = Sounds.mob_alienfly_living;
         this.defaultteam = OtherMobsPack.mobsteamender;
         this.setattributes(50.0, 20.0, 5.0, 0.0, 3.0, 3.0, 0.2, 0.0, 0.0, 0.0);
         this.setNoGravity(true);
         this.leadershipBase = 0;
         this.experienceValue = 0;
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ENDER_BLOOD;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setDouble("homeX", this.homeX);
         compound.setDouble("homeY", this.homeY);
         compound.setDouble("homeZ", this.homeZ);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("homeX")) {
            this.homeX = compound.getDouble("homeX");
         }

         if (compound.hasKey("homeY")) {
            this.homeY = compound.getDouble("homeY");
         }

         if (compound.hasKey("homeZ")) {
            this.homeZ = compound.getDouble("homeZ");
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.livetime > 0) {
               this.livetime--;
            } else if (this.livetime == 0 && this.ticksExisted % 12 == 0) {
               if (this.getHealth() >= 3.0F) {
                  this.setHealth(this.getHealth() - 2.0F);
                  this.homeY -= 0.1;
                  this.postHomeY -= 0.1;
               } else {
                  this.attackEntityFrom(DamageSource.GENERIC, 20.0F);
                  this.setNoGravity(false);
                  this.homeY -= 5.0;
                  this.postHomeY -= 5.0;
                  this.motionY -= 0.7;
               }
            }

            if (this.owner != null && this.ticksExisted % 10 == 0) {
               EntityLivingBase enemy = GetMOP.findNearestEnemy(
                  this.world, this.owner, this.owner.posX, this.owner.posY, this.owner.posZ, 6.0, true
               );
               if (enemy != null
                  && enemy != this
                  && enemy != this.owner
                  && GetMOP.thereIsNoBlockCollidesBetween(this.world, this.getPositionEyes(1.0F), GetMOP.entityCenterPos(enemy), 1.0F, null, false)) {
                  this.setAttackTarget(enemy);
               }
            }
         }
      }

      public void onLivingUpdate() {
         super.onLivingUpdate();
         if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.3;
         }

         if (this.rand.nextFloat() < 0.06 || this.postHomeX == 0.0 && this.postHomeY == 0.0 && this.postHomeZ == 0.0) {
            this.postHomeX = this.homeX + this.rand.nextDouble() * 2.0 - 1.0;
            this.postHomeY = this.homeY + this.rand.nextDouble() * 2.0 - 1.0;
            this.postHomeZ = this.homeZ + this.rand.nextDouble() * 2.0 - 1.0;
         }

         if (this.getDistanceSq(this.postHomeX, this.postHomeY, this.postHomeZ) > 1.0) {
            SuperKnockback.applyMove(this, -0.05F, this.postHomeX, this.postHomeY, this.postHomeZ);
         }
      }

      @Override
      protected void onDeathUpdate() {
         super.onDeathUpdate();
         if (this.deathTime > 0) {
            this.setNoGravity(false);
         }

         if (this.deathTime == 19) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode5,
                  SoundCategory.HOSTILE,
                  1.6F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)9);
               double damageRadius = 2.5;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 1.2, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius * 0.6, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (Team.checkIsOpponent(this, entitylivingbase)) {
                        Weapons.dealDamage(
                           new WeaponDamage(null, this, null, true, false, this, WeaponDamage.explode),
                           (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 2.0F,
                           this,
                           entitylivingbase,
                           true,
                           -1.0F
                        );
                        entitylivingbase.hurtResistantTime = 0;
                     }
                  }
               }
            }
         }
      }

      public float getEyeHeight() {
         return this.height * 0.15F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.WatcherLaser entity = new HostileProjectiles.WatcherLaser(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.7F, 0.4F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entity);
         }
      }

      public void fall(float distance, float damageMultiplier) {
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
                  Sounds.watcher_shoot,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 9) {
            for (int ss = 0; ss < 6; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.4F + (float)this.rand.nextGaussian() / 10.0F,
                  -0.005F,
                  33 + this.rand.nextInt(20),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 8.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 8.0F,
                  1.0F,
                  0.2F,
                  1.0F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }

            for (int i = 0; i < 20; i++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.PORTAL,
                     this.posX + (this.rand.nextDouble() - 0.5),
                     this.posY + this.rand.nextDouble() - 0.25,
                     this.posZ + (this.rand.nextDouble() - 0.5),
                     (this.rand.nextDouble() - 0.5) * 2.0,
                     -this.rand.nextDouble(),
                     (this.rand.nextDouble() - 0.5) * 2.0,
                     new int[0]
                  );
            }

            float fsize = (float)(1.0 + this.rand.nextGaussian() / 6.0);
            int lt = 4 + this.rand.nextInt(3);
            GUNParticle part = new GUNParticle(
               void_explode,
               0.4F,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.5F,
               0.8F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.8F / lt;
            part.randomDeath = false;
            this.world.spawnEntity(part);
            int ltx = 6 + this.rand.nextInt(4);
            GUNParticle partx = new GUNParticle(
               void_explode,
               4.5F,
               0.0F,
               ltx,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            partx.scaleTickAdding = -4.5F / ltx;
            partx.alphaGlowing = true;
            partx.alphaTickAdding = -0.8F / ltx;
            partx.randomDeath = false;
            this.world.spawnEntity(partx);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIShootDelayed(this, 8, 20.0F, 0, false, 80));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Varmint extends AbstractMob {
      public static ResourceLocation tex0 = new ResourceLocation("arpg:textures/varmint_model_tex0.png");
      public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/varmint_model_tex1.png");
      public static ResourceLocation tex2 = new ResourceLocation("arpg:textures/varmint_model_tex2.png");
      public static ResourceLocation tex3 = new ResourceLocation("arpg:textures/varmint_model_tex3.png");
      public int VARIANT = 0;
      public boolean ELITE = false;
      public boolean aiAdded = false;

      public Varmint(World world) {
         super(world, 0.8F, 1.4F);
         this.hurtSound = SoundEvents.ENTITY_VEX_HURT;
         this.deathSound = Sounds.mob_mutant_death;
         this.livingSound = SoundEvents.ENTITY_VEX_AMBIENT;
         this.defaultteam = OtherMobsPack.mobsteamnether;
         this.setattributes(26.0, 48.0, 3.5, 0.3, 4.0, 1.0, 0.0, 0.0, 1.0, 0.1);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.RAWRIBS, 0.4F, 0, 1, 1, 1),
               new MobDrop(ItemsRegister.DEMONITESHARD, 0.04F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.NUGGETINFERNUM, 0.08F, 0, 1, 2, 1),
               new MobDrop(ItemsRegister.MAGIC_POWDER, 0.05F, 0, 1, 2, 1),
               new MobDrop(ItemsRegister.FIREEATER, 0.02F, 0, 1, 1, 0)
            }
         );
         this.leadershipBase = 3;
         this.isImmuneToFire = true;
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 1);
         this.soul = Soul.EXHAUSTED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.2 && this.VARIANT == 3) {
            ShardType.spawnShards(ShardType.DEATH, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.2 && this.VARIANT == 2) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         if (this.VARIANT == 0) {
            return DeathEffects.NETHER_BLOOD;
         } else if (this.VARIANT == 1) {
            return DeathEffects.CINDER_BLOOD;
         } else {
            return this.VARIANT == 2 ? DeathEffects.RED_BLOOD : DeathEffects.SKELETON_BLOOD;
         }
      }

      public float getCollisionBorderSize() {
         return 0.4F;
      }

      public void applyEntityCollision(Entity entityIn) {
         if (!this.isRidingSameEntity(entityIn) && !entityIn.noClip && !this.noClip) {
            double d0 = entityIn.posX - this.posX;
            double d1 = entityIn.posZ - this.posZ;
            double d2 = MathHelper.absMax(d0, d1);
            if (d2 >= 0.01F) {
               d2 = MathHelper.sqrt(d2);
               d0 /= d2;
               d1 /= d2;
               double d3 = 1.0 / d2;
               if (d3 > 1.0) {
                  d3 = 1.0;
               }

               d0 *= d3;
               d1 *= d3;
               d0 *= 0.05F;
               d1 *= 0.05F;
               d0 *= 1.0F - this.entityCollisionReduction;
               d1 *= 1.0F - this.entityCollisionReduction;
               float mult = entityIn instanceof Varmint ? 0.3F : 1.0F;
               if (!this.isBeingRidden()) {
                  this.addVelocity(-d0 * mult, 0.0, -d1 * mult);
               }

               if (!entityIn.isBeingRidden()) {
                  entityIn.addVelocity(d0 * mult, 0.0, d1 * mult);
               }
            }
         }
      }

      @Override
      public void shoot() {
         EntityLivingBase target = this.getAttackTarget();
         if (target != null) {
            for (EntityLivingBase entit : GetMOP.MopRayTrace(8.0, 1.0F, this, 0.4, 0.3)) {
               if (!Team.checkIsOpponent(this, entit)) {
                  return;
               }
            }

            double d0 = target.posY + target.getEyeHeight() - 1.1F;
            double d1 = target.posX + target.motionX - this.posX;
            double d2 = d0 - this.posY;
            double d3 = target.posZ + target.motionZ - this.posZ;
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
            PotionType potiontype = PotionTypes.HARMING;
            if (f >= 8.0F && !target.isPotionActive(MobEffects.HUNGER)) {
               potiontype = PotionEffects.HUNGER_POTIONTYPE_MAIN;
            } else if (target.getHealth() >= 8.0F && !target.isPotionActive(PotionEffects.FIERYOIL)) {
               potiontype = PotionEffects.FIERYOIL_POTIONTYPE_STRONG;
            } else if (f < 8.0F && !target.isPotionActive(MobEffects.SLOWNESS)) {
               potiontype = PotionTypes.SLOWNESS;
            } else if (f <= 3.0F && !target.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25F) {
               potiontype = PotionTypes.WEAKNESS;
            }

            EntityPotion entitypotion = new EntityPotion(this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potiontype));
            entitypotion.rotationPitch -= -8.0F;
            entitypotion.shoot(d1, d2 + f * 0.2F, d3, 1.15F, 6.0F);
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.ENTITY_WITCH_THROW,
                  this.getSoundCategory(),
                  1.0F,
                  0.8F + this.rand.nextFloat() * 0.4F
               );
            this.world.spawnEntity(entitypotion);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted <= 2 || this.ticksExisted % 41 == 0) {
            this.world.setEntityState(this, (byte)(this.VARIANT + (this.ELITE ? 16 : 8)));
         }

         if (!this.aiAdded) {
            this.aiAdded = true;
            if (this.VARIANT == 3) {
               this.tasks.addTask(2, new EntityAISkeleton(this, 1.0, 150, 8.0F, 1).setApproach());
            } else {
               this.tasks.addTask(2, new EntityAIRush(this, true, true, true));
               this.tasks.addTask(6, new EntityAITeasing(this, EntityPigZombie.class, true, 22.0));
               if (this.ELITE) {
                  this.tasks.addTask(3, new EntityAIBuildWay(this, 20, false, BlocksRegister.SUMMONEDHELLSTONE.getDefaultState()));
               }
            }
         }
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("variant", this.VARIANT);
         compound.setBoolean("elite", this.ELITE);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("variant")) {
            this.VARIANT = compound.getInteger("variant");
         }

         if (compound.hasKey("elite")) {
            this.ELITE = compound.getBoolean("elite");
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            this.VARIANT = this.rand.nextInt(4);
            if (this.VARIANT == 1) {
               this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.maxHealth + 4.0);
            }

            if (this.VARIANT == 2) {
               this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.damage + 2.0);
            }

            if (this.VARIANT == 3) {
               this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed * 1.1);
            }

            this.ELITE = this.rand.nextFloat() < 0.3F;
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (!this.world.isRemote) {
            if (this.VARIANT == 1) {
               entityIn.setFire(2 + this.rand.nextInt(4));
               Weapons.disableShield(entityIn, false);
            }

            if (this.VARIANT == 2 && entityIn.isBurning()) {
               Weapons.setPotionIfEntityLB(entityIn, PotionEffects.DEMONIC_BURN, 40 + this.rand.nextInt(80), 0);
            }
         }

         return super.attackEntityAsMob(entityIn);
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (player.getHeldItemMainhand().getItem() == ItemsRegister.SULFUR) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.owner == null && this.rand.nextFloat() < 0.3) {
               this.isAgressive = false;
               this.owner = player;
               this.team = Team.getTeamFor(player);
               this.navigator.clearPath();
               this.setAttackTarget((EntityLivingBase)null);
               this.world.setEntityState(this, (byte)7);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
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
                     EnumParticleTypes.FLAME,
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

         if (id >= 8 && id <= 11) {
            this.VARIANT = id - 8;
            this.ELITE = false;
         }

         if (id >= 16 && id <= 19) {
            this.VARIANT = id - 16;
            this.ELITE = true;
            this.var2 = 1.0F;
         }

         super.handleStatusUpdate(id);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         if (this.VARIANT == 0) {
            return tex0;
         } else if (this.VARIANT == 1) {
            return tex1;
         } else {
            return this.VARIANT == 2 ? tex2 : tex3;
         }
      }
   }

   public static class VoidGuard extends AbstractMob {
      public List<Vec3d> poslist = new ArrayList<>();
      public static int segments = 5;
      public int number;
      public VoidGuard headEntity;
      public UUID headEntityID;
      public static ResourceLocation tex0 = new ResourceLocation("arpg:textures/void_guard_model_tex.png");
      public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/void_guard_part_model_tex.png");

      public VoidGuard(World world, boolean isSegment, int number, VoidGuard headEntity) {
         super(world, 0.6F, 0.6F);
         this.setNoGravity(true);
         this.noClip = true;
         this.hurtSound = Sounds.gnater_hurt;
         this.isSubMob = isSegment;
         this.number = number;
         this.headEntity = headEntity;
         if (headEntity != null) {
            this.headEntityID = headEntity.getUniqueID();
         }

         this.defaultteam = OtherMobsPack.mobsteamender;
         this.setattributes(30.0, 48.0, 6.0, 0.13, 0.0, 2.0, 0.45, 0.1, 0.0, 0.0);
         if (!this.isSubMob) {
            this.registerLOOT(
               new MobDrop[]{
                  new MobDrop(ItemsRegister.VOIDCRYSTAL, 0.1F, 0, 1, 1, 0),
                  new MobDrop(ItemsRegister.ENDERGH, 0.02F, 0, 1, 1, 0),
                  new MobDrop(ItemsRegister.ENDERLEECH, 0.03F, 0, 1, 1, 0)
               }
            );
            this.deathSound = Sounds.gnater_dead;
         }

         this.setDropMoney(0, 6, 0.35F);
         this.experienceValue = 8;
         this.soul = Soul.STRANGER;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.65) {
            ShardType.spawnShards(ShardType.VOID, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ENDER_BLOOD;
      }

      public VoidGuard(World world) {
         this(world, false, 0, null);
      }

      public boolean hasNoGravity() {
         return true;
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData inits = super.onInitialSpawn(difficulty, livingdata);
         if (!this.world.isRemote && !this.isSubMob) {
            for (int i = 0; i < segments; i++) {
               VoidGuard part = new VoidGuard(this.world, true, i, this);
               part.setPosition(this.posX, this.posY - i / 5.0, this.posZ);
               this.world.spawnEntity(part);
               part.onInitialSpawn();
               part.team = this.team;
            }
         }

         return inits;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         if (this.headEntity != null) {
            compound.setUniqueId("ownerId", this.headEntity.getUniqueID());
         }

         compound.setInteger("segmentnumber", this.number);
         compound.setBoolean("issegment", this.isSubMob);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("ownerIdMost") && compound.hasKey("ownerIdLeast")) {
            this.headEntityID = compound.getUniqueId("ownerId");
         }

         if (compound.hasKey("segmentnumber")) {
            this.number = compound.getInteger("segmentnumber");
         }

         if (compound.hasKey("issegment")) {
            this.isSubMob = compound.getBoolean("issegment");
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public AbstractMob getOwnerIfSegment() {
         return this.isSubMob ? this.headEntity : null;
      }

      public Vec3d getSegmCoord(int number) {
         return number < this.poslist.size() ? this.poslist.get(number) : this.getPositionVector();
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (source == DamageSource.CRAMMING) {
            return false;
         } else if (source == DamageSource.IN_WALL) {
            return false;
         } else if (!this.isSubMob) {
            return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
         } else if (this.headEntity != null) {
            this.world.setEntityState(this, (byte)2);
            boolean att = this.headEntity.attackEntityFrom(source, amount);
            if (this.headEntity.getHealth() <= 0.0F) {
               super.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
            }

            return att;
         } else {
            return !this.isEntityInvulnerable(source);
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.isSubMob = false;
         }

         if (id == 9) {
            this.isSubMob = true;
         }

         super.handleStatusUpdate(id);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
            this.world.setEntityState(this, (byte)(this.isSubMob ? 9 : 8));
         }

         if (!this.isSubMob) {
            Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
            this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
            this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
            if (this.poslist != null) {
               Vec3d vec = new Vec3d(this.posX, this.posY, this.posZ);
               if (this.poslist.size() > 0) {
                  if (vec != null) {
                     if (this.poslist.get(0) == null) {
                        this.poslist.set(0, vec);
                     } else if (vec.distanceTo(this.poslist.get(0)) > 0.6) {
                        this.poslist.add(0, vec);
                     }

                     if (this.poslist.size() > segments + 1) {
                        this.poslist.remove(segments + 1);
                     }
                  }
               } else {
                  this.poslist.add(vec);
               }
            }
         } else {
            if (!this.world.isRemote) {
               if (this.headEntity != null) {
                  if (this.headEntity.poslist.size() > this.number + 1) {
                     Vec3d vec1 = this.headEntity.poslist.get(this.number);
                     Vec3d vec2 = this.headEntity.poslist.get(this.number + 1);
                     Vec3d vec = new Vec3d(
                        (vec1.x + vec2.x) / 2.0,
                        (vec1.y + vec2.y) / 2.0,
                        (vec1.z + vec2.z) / 2.0
                     );
                     if (vec != null) {
                        this.motionX /= 2.0;
                        this.motionY /= 2.0;
                        this.motionZ /= 2.0;
                        double power = 1.0;
                        double prunex = (vec.x - this.posX) * power;
                        double pruney = (vec.y - this.posY) * power;
                        double prunez = (vec.z - this.posZ) * power;
                        this.motionX += prunex;
                        this.motionY += pruney;
                        this.motionZ += prunez;
                     }
                  }

                  if (this.headEntity.getHealth() <= 0.0F) {
                     this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
                  }
               } else {
                  if (this.headEntityID != null) {
                     Entity entit = (VoidGuard)getEntityByUUID(this.world, this.headEntityID);
                     if (entit instanceof VoidGuard) {
                        this.headEntity = (VoidGuard)entit;
                     }
                  }

                  if (this.ticksExisted > 20 && this.headEntity == null) {
                     this.setDead();
                  }
               }
            }

            if (this.headEntity != null) {
               if (this.headEntity.poslist.size() > this.number) {
                  if (this.number > 0) {
                     Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.headEntity.poslist.get(this.number - 1)));
                     this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
                     this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
                  }

                  if (this.number == 0) {
                     Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.headEntity.getPositionVector()));
                     this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
                     this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
                  }
               }

               if (this.headEntity.getAttackTarget() != null) {
                  this.setAttackTarget(this.headEntity.getAttackTarget());
               }
            }
         }
      }

      public void onLivingUpdate() {
         if (this.world.isRemote && !this.isSubMob) {
            for (int i = 0; i < 2; i++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.PORTAL,
                     this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                     this.posY + this.rand.nextDouble() * this.height - 0.25,
                     this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                     (this.rand.nextDouble() - 0.5) * 2.0,
                     -this.rand.nextDouble(),
                     (this.rand.nextDouble() - 0.5) * 2.0,
                     new int[0]
                  );
            }
         }

         super.onLivingUpdate();
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISegmAABBAttack(this, 40, 0.2F));
         this.initToHead();
      }

      public void initToHead() {
         if (!this.isSubMob) {
            this.tasks.addTask(2, new EntityAIWorm(this, 70, 7.0F, 4.0F, 0.0F, 1.5F, 0.5F, 1.15F, false, 15));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
            this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
         }
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         return this.isSubMob ? tex1 : tex0;
      }
   }
}

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.elements.IWrenchUser;
import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.elements.Wrench;
import com.Vivern.Arpg.elements.models.ModelsToxicomaniaMob;
import com.Vivern.Arpg.elements.models.SummonedSnowmanModel;
import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.recipes.Soul;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.LayerRandomItem;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicomaniaMobsPack {
   public static ModelBase NULL_MODEL = new SummonedSnowmanModel();
   public static ResourceLocation NULL_TEX = new ResourceLocation("arpg:textures/no_texture.png");

   public static void init() {
      AbstractMob.addToRegister(MutantZombie.class, "Mutant Zombie", 2318654, 6467902);
      AbstractMob.addToRegister(MutantBeast.class, "Mutant Beast", 144392, 3711550);
      AbstractMob.addToRegister(TestTubeSubstance.class, "Test Tube Substance", 9819757, 8996174);
      AbstractMob.addToRegister(TestTubeCreature.class, "Test Tube Creature", 7779165, 5710656);
      AbstractMob.addToRegister(Experiment9.class, "Experiment 9", 3116418, 3974480);
      AbstractMob.addToRegister(GlowingSkeleton.class, "Glowing Skeleton", 7966074, 14941952);
      AbstractMob.addToRegister(RocketBot.class, "Rocket Bot", 7494733, 15838262);
      AbstractMob.addToRegister(KillBot.class, "Kill Bot", 5128246, 2594614);
      AbstractMob.addToRegister(Turret.class, "Turret", 3024668, 11710379);
      AbstractMob.addToRegister(Dron.class, "Dron", 7434609, 15541543);
      AbstractMob.addToRegister(VineChops.class, "Vine Chops", 938777, 3698751);
      AbstractMob.addToRegister(Springer.class, "Springer", 9877615, 13531502);
      AbstractMob.addToRegister(SlimeJellyfish.class, "Slime Jellyfish", 11796334, 14352255);
      AbstractMob.addToRegister(Hose.class, "Hose", 6004564, 11517970);
      AbstractMob.addToRegister(FlowerSpider.class, "Flower Spider", 16352713, 9887332);
      AbstractMob.addToRegister(Mosquito.class, "Mosquito", 1845022, 3560222);
      AbstractMob.addToRegister(BossAbomination.class, "Abomination", 5093711, 7463800);
      AbstractMob.addToRegister(Lump.class, "Lump", 5093711, 10289016);
      AbstractMob.addToRegister(AbominationCannon.class, "Abomination Cannon", 5093711, 3749958);
      AbstractMob.addToRegister(PoisonSpitter.class, "Poison Spitter", 4280136, 11393895);
   }

   public static void initRender() {
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.Experiment9Model(),
            new ResourceLocation("arpg:textures/experiment9_model_tex.png"),
            0.4F,
            Experiment9.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.VineChopsModel(),
            new ResourceLocation("arpg:textures/vine_chops_model_tex.png"),
            1.0F,
            VineChops.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.SpringerModel(), new ResourceLocation("arpg:textures/springer_model_tex.png"), 0.3F, Springer.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.MosquitoModel(), new ResourceLocation("arpg:textures/mosquito_model_tex.png"), 0.1F, Mosquito.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.BossAbominationModel(), new ResourceLocation("arpg:textures/boss_abomination_model_tex.png"), 1.0F, BossAbomination.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.AbominationCannonModel(),
            new ResourceLocation("arpg:textures/abomination_cannon_model_tex.png"),
            0.4F,
            AbominationCannon.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.LumpModel(), new ResourceLocation("arpg:textures/lump_model_tex.png"), 0.8F, Lump.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.MutantBeastModel(),
            new ResourceLocation("arpg:textures/mutant_beast_model_tex.png"),
            0.6F,
            MutantBeast.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsToxicomaniaMob.TestTubeSubstanceModel(),
               new ResourceLocation("arpg:textures/test_tube_substance_model_tex.png"),
               0.7F,
               TestTubeSubstance.class
            )
            .setLayerRandomItem(new LayerRandomItem(0.4F, true))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.TestTubeCreatureModel(),
            new ResourceLocation("arpg:textures/test_tube_creature_model_tex.png"),
            1.0F,
            TestTubeCreature.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsToxicomaniaMob.MutantZombieModel(),
               new ResourceLocation("arpg:textures/mutant_zombie_model_tex.png"),
               0.3F,
               MutantZombie.class
            )
            .setLayerHeldItem()
            .setlayerArmor()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.RocketBotModel(),
            new ResourceLocation("arpg:textures/rocket_bot_model_tex.png"),
            0.5F,
            RocketBot.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.KillBotModel(), new ResourceLocation("arpg:textures/kill_bot_model_tex.png"), 0.3F, KillBot.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.DronModel(), new ResourceLocation("arpg:textures/dron_model_tex.png"), 0.15F, Dron.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.TurretModel(), new ResourceLocation("arpg:textures/turret_model_tex.png"), 0.25F, Turret.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsToxicomaniaMob.PoisonSpitterModel(),
            new ResourceLocation("arpg:textures/poison_spitter_model_tex.png"),
            0.15F,
            PoisonSpitter.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsToxicomaniaMob.GlowingSkeletonModel(),
               new ResourceLocation("arpg:textures/glowing_skeleton_model_tex.png"),
               0.3F,
               GlowingSkeleton.class
            )
            .setLayerHeldItem()
            .setlayerArmor()
      );
   }

   public static class AbominationCannon extends AbstractMob {
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
      public static ResourceLocation slime = new ResourceLocation("arpg:textures/slime_beam.png");
      public int typeCannon = 0;
      public BossAbomination boss;
      public boolean returning = false;
      public int bulletCannOverheat = 0;

      public AbominationCannon(World world) {
         super(world, 1.2F, 1.2F);
         this.hurtSound = Sounds.mob_robot_hurt;
         this.deathSound = Sounds.mob_robot_death;
         this.defaultteam = "toxic";
         this.setNoGravity(true);
         this.setattributes(100.0, 64.0, 7.0, 0.12, 4.0, 7.0, 0.75, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.SCRAPMETAL, 1.0F, 0, 1, 1, 1), new MobDrop(ItemsRegister.IMPULSETHRUSTER, 0.5F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 3);
         this.soul = null;
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROBOT_OIL_BLOOD;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         return source.getTrueSource() != null && source.getTrueSource() instanceof BossAbomination ? false : super.attackEntityFrom(source, amount);
      }

      public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         if (this.boss != null) {
            compound.setUniqueId("boss", this.boss.getUniqueID());
         }

         compound.setInteger("typecannon", this.typeCannon);
         return super.writeToNBT(compound);
      }

      public void readFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("boss")) {
            UUID id = compound.getUniqueId("boss");

            for (Entity e : this.world.loadedEntityList) {
               if (e.getUniqueID().equals(id) && e instanceof BossAbomination) {
                  this.boss = (BossAbomination)e;
               }
            }
         }

         if (compound.hasKey("typecannon")) {
            this.typeCannon = compound.getInteger("typecannon");
         }

         super.readFromNBT(compound);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.tasks.taskEntries.isEmpty() && !this.world.isRemote) {
            int attCooldown = 0;
            if (this.typeCannon == 0) {
               attCooldown = 30;
            }

            if (this.typeCannon == 1) {
               attCooldown = 3;
            }

            if (this.typeCannon == 2) {
               attCooldown = 140 - this.getMobDifficulty() * 20;
            }

            this.tasks.addTask(1, new EntityAICorrupter(this, attCooldown, 45.0F, 1, true, 7.0F, 3.0F));
         }

         if (this.boss != null && !this.world.isRemote) {
            double distSq = this.getDistanceSq(this.boss);
            if (!this.returning) {
               if (distSq > 64.0) {
                  SuperKnockback.applyMove(this, -0.3F, this.boss.posX, this.boss.posY, this.boss.posZ);
                  if (distSq > 256.0) {
                     this.noClip = true;
                     this.returning = true;
                  }
               }

               if (distSq < 16.0) {
                  SuperKnockback.applyMove(this, 0.15F, this.boss.posX, this.boss.posY, this.boss.posZ);
               }
            } else {
               SuperKnockback.applyMove(this, -0.35F, this.boss.posX, this.boss.posY, this.boss.posZ);
               if (distSq < 9.0) {
                  NBTTagCompound comp = new NBTTagCompound();
                  comp.setFloat("health", this.getHealth());
                  comp.setInteger("type", this.typeCannon);
                  if (this.boss.getEntityData().hasKey("cannons")) {
                     this.boss.getEntityData().getTagList("cannons", 10).appendTag(comp);
                  } else {
                     NBTTagList tlist = new NBTTagList();
                     tlist.appendTag(comp);
                     this.boss.getEntityData().setTag("cannons", tlist);
                  }

                  this.returning = false;
                  this.setDead();
               }
            }
         }

         if (this.bulletCannOverheat > 0) {
            this.bulletCannOverheat--;
         }

         if (this.ticksExisted < 2 || this.ticksExisted % 40 == 0 && !this.world.isRemote) {
            this.world.setEntityState(this, (byte)(this.typeCannon + 10));
         }

         if (this.world.isRemote && this.boss == null) {
            AbstractMob ba = (AbstractMob)this.world.findNearestEntityWithinAABB(BossAbomination.class, this.getEntityBoundingBox().grow(8.0), this);
            if (ba instanceof BossAbomination) {
               this.boss = (BossAbomination)ba;
            }
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.TOXIN && potion != MobEffects.POISON && potion != MobEffects.REGENERATION && potion != PotionEffects.CHLORITE
            ? super.isPotionApplicable(potioneffectIn)
            : false;
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
                  Sounds.explode,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            if (!this.world.isRemote) {
               int count = this.rand.nextInt(3) + 1;
               this.world.setEntityState(this, (byte)9);
               double damageRadius = 2.0;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 1.2, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius * 0.6, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (Team.checkIsOpponent(this, entitylivingbase)) {
                        SuperKnockback.applyKnockback(entitylivingbase, 1.0F, this.posX, this.posY - 0.3, this.posZ);
                        IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                        double baseValue = entityAttribute.getBaseValue();
                        entityAttribute.setBaseValue(1.0);
                        entitylivingbase.attackEntityFrom(DamageSource.causeExplosionDamage(this), 8.0F + this.getMobDifficulty() * 4);
                        entityAttribute.setBaseValue(baseValue);
                     }
                  }
               }
            }
         }
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            if (this.typeCannon == 0) {
               this.world.setEntityState(this, (byte)6);
               HostileProjectiles.DronLaser entity = new HostileProjectiles.DronLaser(this.world, this);
               entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.6F + this.getMobDifficulty() * 0.2F, 0.2F);
               entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + this.getMobDifficulty();
               this.world.spawnEntity(entity);
            }

            if (this.typeCannon == 1 && this.bulletCannOverheat <= 0) {
               this.world.setEntityState(this, (byte)7);
               HostileProjectiles.Bullet entity = new HostileProjectiles.Bullet(this.world, this);
               entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.9F + this.getMobDifficulty() * 0.2F, 3.0F);
               entity.damage = 6.0F;
               this.world.spawnEntity(entity);
               this.bulletCannOverheat -= 2;
               if (this.bulletCannOverheat < -80) {
                  this.bulletCannOverheat = -this.bulletCannOverheat;
               }
            }

            if (this.typeCannon == 2) {
               this.world.setEntityState(this, (byte)8);
               HostileProjectiles.Rocket entity = new HostileProjectiles.Rocket(this.world, this);
               entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.9F + this.getMobDifficulty() * 0.15F, 1.0F);
               entity.damage = 15.0F;
               entity.damageRadius = 2.5F;
               this.world.spawnEntity(entity);
            }
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id >= 10 && id <= 12) {
            this.typeCannon = id - 10;
         }

         if (id == 6) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.laser,
                  SoundCategory.HOSTILE,
                  1.7F,
                  1.1F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.shoot,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.firework,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.mob_robot_attack,
                  SoundCategory.HOSTILE,
                  1.7F,
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
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.8F + (float)this.rand.nextGaussian() / 10.0F,
                  0.02F,
                  53 + this.rand.nextInt(20),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 29.0F,
                  this.rand.nextFloat() / 3.0F,
                  (float)this.rand.nextGaussian() / 29.0F,
                  1.0F,
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }
         }
      }

      public void onEntityUpdate() {
         super.onEntityUpdate();
         if (this.world.isRemote && this.boss != null) {
            Vec3d vec2 = new Vec3d(this.posX, this.posY + this.height / 2.0F, this.posZ);
            Vec3d vec1 = new Vec3d(this.boss.posX, this.boss.posY + this.boss.height / 2.0F, this.boss.posZ);
            BlockPos poss = new BlockPos(this);
            int light = Math.max(this.world.getLightFor(EnumSkyBlock.BLOCK, poss), this.world.getLightFor(EnumSkyBlock.SKY, poss)) * 15;
            BetweenParticle laser = new BetweenParticle(
               this.world, slime, 0.28F, light, 1.0F, 1.0F, 1.0F, 0.0F, vec1.distanceTo(vec2), 1, 0.0F, 9999.0F, vec1, vec2
            );
            laser.setPosition(vec1.x, vec1.y, vec1.z);
            laser.alphaGlowing = false;
            laser.ignoreFrustumCheck = true;
            this.world.spawnEntity(laser);
         }
      }

      protected void initEntityAI() {
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      }
   }

   public static class Dron extends AbstractMob {
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");

      public Dron(World world) {
         super(world, 0.6F, 0.6F);
         this.hurtSound = Sounds.mob_robot_hurt;
         this.deathSound = Sounds.mob_robot_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = "toxic";
         this.setNoGravity(true);
         this.setattributes(30.0, 64.0, 6.0, 0.13, 3.0, 3.0, 0.35, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.CIRCUIT, 0.2F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.SCRAPMETAL, 1.0F, 0, 1, 1, 1),
               new MobDrop(BlocksRegister.SCRAPELECTRONICS, 0.2F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.IMPULSETHRUSTER, 0.33F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 3);
         this.soul = null;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.2) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.AIR, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROBOT_OIL_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      protected float getSoundPitch() {
         return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.2F;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.TOXIN && potion != MobEffects.POISON && potion != MobEffects.REGENERATION
            ? super.isPotionApplicable(potioneffectIn)
            : false;
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
                  Sounds.explode,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            if (!this.world.isRemote) {
               int count = this.rand.nextInt(3) + 1;
               this.world.setEntityState(this, (byte)9);
               double damageRadius = 2.0;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 1.2, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius * 0.6, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     SuperKnockback.applyKnockback(entitylivingbase, 1.0F, this.posX, this.posY - 0.3, this.posZ);
                     IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     entitylivingbase.attackEntityFrom(DamageSource.causeExplosionDamage(this), 5.0F + this.getMobDifficulty() * 4);
                     entityAttribute.setBaseValue(baseValue);
                  }
               }
            }
         }
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.DronLaser entity = new HostileProjectiles.DronLaser(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.1F, 0.8F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entity);
         }
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
                  1.7F,
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
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.8F + (float)this.rand.nextGaussian() / 10.0F,
                  0.02F,
                  53 + this.rand.nextInt(20),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 29.0F,
                  this.rand.nextFloat() / 3.0F,
                  (float)this.rand.nextGaussian() / 29.0F,
                  1.0F,
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 40, 32.0F, 1, true, 7.0F, 5.0F).setBurst(true, 2, 5, false, 15, 0));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Experiment9 extends AbstractMob {
      public Experiment9(World world) {
         super(world, 0.7F, 1.0F);
         this.hurtSound = Sounds.mob_mutant_hurt;
         this.deathSound = Sounds.mob_mutant_death;
         this.livingSound = Sounds.mob_mutant_living;
         this.stepSound = SoundEvents.ENTITY_HUSK_STEP;
         this.defaultteam = "toxic";
         this.setattributes(46.0, 32.0, 6.0, 0.45, 3.0, 1.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ANTIDOTE, 0.1F, 0, 1, 1, 0), new MobDrop(ItemsRegister.MUTAGEN, 0.1F, 0, 1, 1, 0)
            }
         );
         this.leadershipBase = 5;
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 3);
         this.soul = Soul.MUTATED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.POISON, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.LIVE, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.MUTANT_PURPLE_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted % 20 == 0 && GetMOP.containsBlock(this.world, this.getEntityBoundingBox(), BlocksRegister.FLUIDPOISON)) {
            this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 350));
         }
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (!player.getCooldownTracker().hasCooldown(Items.ROTTEN_FLESH) && player.getHeldItemMainhand().getItem() == Items.ROTTEN_FLESH) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.owner == null) {
               if (this.rand.nextFloat() < 0.4) {
                  this.tame(player);
                  this.world.setEntityState(this, (byte)7);
               }
            } else {
               this.heal(8.0F);
               player.getCooldownTracker().setCooldown(Items.ROTTEN_FLESH, 10);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 7) {
            for (int i = 0; i < 7; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
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

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.tasks.addTask(1, new EntityAIDash(this, 20, 2.0F, 0.0F, 0.4F, true, 0.4F).setSoundOnDash(Sounds.mob_mutant_attack));
         this.tasks.addTask(2, new EntityAIDash(this, 100, 7.0F, 3.0F, 1.2F, false, 0.2F).setSoundOnDash(Sounds.mob_mutant_attack));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class FlowerSpider extends AbstractMob {
      public static Predicate<Block> blocks = new Predicate<Block>() {
         public boolean apply(Block input) {
            return input == BlocksRegister.MUTAFLOWERPINK;
         }
      };

      public FlowerSpider(World world) {
         super(world, 0.95F, 0.7F);
         this.hurtSound = SoundEvents.ENTITY_SPIDER_HURT;
         this.deathSound = Sounds.mob_slime_death;
         this.stepSound = SoundEvents.ENTITY_SPIDER_STEP;
         this.defaultteam = "toxic";
         this.setattributes(50.0, 32.0, 6.0, 0.35, 5.0, 8.0, 0.2, 0.0, 0.0, 0.1);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.VISCOSASEEDS, 0.8F, 0, 1, 1, 2)});
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.ARTHROPOD;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.FlowerAcidShoot entity = new HostileProjectiles.FlowerAcidShoot(this.world, this);
            entity.shoot(this, this.rotationPitch - 9.0F, this.rotationYaw, 0.0F, 0.7F, 2.0F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entity);
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
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
                  Sounds.larva_water_attack,
                  SoundCategory.HOSTILE,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIHunt(this, false, true, true, 30, blocks));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class GlowingSkeleton extends AbstractMob {
      public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");

      public GlowingSkeleton(World world) {
         super(world, 0.7F, 1.8F);
         this.hurtSound = SoundEvents.ENTITY_SKELETON_HURT;
         this.deathSound = SoundEvents.ENTITY_SKELETON_DEATH;
         this.livingSound = SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
         this.stepSound = SoundEvents.ENTITY_SKELETON_STEP;
         this.defaultteam = "toxic";
         this.setattributes(45.0, 48.0, 8.0, 0.32, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.BONE, 1.0F, 0, 0, 2, 3),
               new MobDrop(Items.ARROW, 0.33F, 0, 0, 8, 6),
               new MobDrop(ItemsRegister.ARROWVICIOUS, 0.33F, 0, 0, 8, 6),
               new MobDrop(BlocksRegister.BOMBRUSTED, 0.03F, 0, 1, 1, 0),
               new MobDrop(BlocksRegister.BOMBSMALL, 0.03F, 0, 1, 1, 0),
               new MobDrop(BlocksRegister.BOMBTOXIC, 0.03F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 3);
         this.soul = Soul.PALE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.DEATH, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.VOID, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.POISON, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.GLOWING_NUCLEAR_BLOOD;
      }

      @Nullable
      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
         this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
         return super.onInitialSpawn(difficulty, livingdata);
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.ArrowRadioactive entityarrow = new HostileProjectiles.ArrowRadioactive(this.world, this);
            entityarrow.setPotionEffect();
            entityarrow.shoot(this, this.rotationPitch - 6.0F, this.rotationYaw, 0.0F, 1.35F, 1.4F);
            entityarrow.velocityChanged = true;
            entityarrow.pickupStatus = PickupStatus.DISALLOWED;
            this.world.spawnEntity(entityarrow);
         }
      }

      @Override
      protected void onDeathUpdate() {
         super.onDeathUpdate();
         if (this.deathTime == 19) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            if (!this.world.isRemote) {
               int count = this.rand.nextInt(3) + 1;
               this.world.setEntityState(this, (byte)9);
               double damageRadius = 2.0 + this.getMobDifficulty() * 0.75;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 1.2, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius * 0.6, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     SuperKnockback.applyKnockback(entitylivingbase, 2.0F, this.posX, this.posY - 0.3, this.posZ);
                     IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     entitylivingbase.attackEntityFrom(DamageSource.causeExplosionDamage(this), 8.0F + this.getMobDifficulty() * 4);
                     entitylivingbase.hurtResistantTime = 0;
                     entityAttribute.setBaseValue(baseValue);
                  }
               }
            }
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 9) {
            for (int ss = 0; ss < 6; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.7F + (float)this.rand.nextGaussian() / 6.0F,
                  -0.001F,
                  34,
                  60,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  0
               );
               bigsmoke.alphaTickAdding = -0.028F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 10; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.6F + (float)this.rand.nextGaussian() / 8.0F,
                  -0.005F,
                  33 + this.rand.nextInt(20),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 7.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 7.0F,
                  1.0F,
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 10; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  1.0F + (float)this.rand.nextGaussian() / 8.0F,
                  0.02F,
                  53 + this.rand.nextInt(20),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 29.0F,
                  this.rand.nextFloat() / 2.0F,
                  (float)this.rand.nextGaussian() / 29.0F,
                  1.0F,
                  1.0F,
                  0.8F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               this.world.spawnEntity(fire);
            }
         }

         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.ENTITY_ARROW_SHOOT,
                  SoundCategory.HOSTILE,
                  0.9F,
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
                  Sounds.bow_aim_hostile,
                  SoundCategory.HOSTILE,
                  1.5F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         super.handleStatusUpdate(id);
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISwimming(this));
         EntityAISkeleton ai1 = new EntityAISkeleton(this, 1.0, 47, 25.0F, 10);
         ai1.triggerAnim3 = true;
         this.tasks.addTask(2, ai1);
         this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(6, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Hose extends AbstractMob {
      public List<Vec3d> poslist = new ArrayList<>();
      public static int segments = 10;
      public int number;
      public Hose owner;

      public Hose(World world, boolean isSegment, int number, Hose owner) {
         super(world, 0.5F, 0.5F);
         this.setNoGravity(true);
         this.noClip = true;
         this.hurtSound = Sounds.mob_slime_hurt;
         this.isSubMob = isSegment;
         if (!isSegment) {
            this.deathSound = Sounds.mob_slime_death;
            this.livingSound = Sounds.mob_slime_living;
            this.tasks.addTask(2, new EntityAIWorm(this, 70, 8.0F, 4.0F, 0.0F, 1.5F, 0.3F, 1.15F, true, 15));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
            this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
         }

         this.number = number;
         this.owner = owner;
         this.defaultteam = "toxic";
         this.setattributes(270.0, 64.0, 9.0, 0.17, 1.0, 0.0, 0.95, 0.5, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.VILESUBSTANCE, 0.85F, 0, 1, 3, 1)});
      }

      public Hose(World world) {
         this(world, false, 0, null);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.TEST_TUBE_BLOOD;
      }

      @Override
      public AbstractMob getOwnerIfSegment() {
         return this.isSubMob ? this.owner : null;
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
         } else if (this.owner != null) {
            this.world.setEntityState(this, (byte)2);
            boolean att = this.owner.attackEntityFrom(source, amount);
            if (this.owner.getHealth() <= 0.0F) {
               super.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
            }

            return att;
         } else {
            return !this.isEntityInvulnerable(source);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.isSubMob) {
            Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
            this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
            this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
            if (this.ticksExisted == 1) {
               for (int i = 0; i < segments; i++) {
                  if (!this.world.isRemote) {
                     Hose part = new Hose(this.world, true, i, this);
                     part.setPosition(this.posX, this.posY - i / 5.0, this.posZ);
                     this.world.spawnEntity(part);
                  }
               }
            }

            if (this.poslist != null) {
               Vec3d vec = new Vec3d(this.posX, this.posY, this.posZ);
               if (this.poslist.size() > 0) {
                  if (vec != null) {
                     if (this.poslist.get(0) == null) {
                        this.poslist.set(0, vec);
                     } else if (vec.distanceTo(this.poslist.get(0)) > 0.5) {
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
               if (this.owner != null) {
                  if (this.owner.poslist.size() > this.number) {
                     Vec3d vec = this.owner.poslist.get(this.number);
                     if (vec != null) {
                        this.motionX /= 2.0;
                        this.motionY /= 2.0;
                        this.motionZ /= 2.0;
                        float power = (float)this.getDistance(vec.x, vec.y, vec.z);
                        SuperKnockback.applyMove(this, -power, vec.x, vec.y, vec.z);
                     }
                  }

                  if (this.owner.getHealth() <= 0.0F) {
                     this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
                  }
               } else {
                  this.setDead();
               }
            }

            if (this.owner != null) {
               if (this.owner.poslist.size() > this.number) {
                  if (this.number > 0) {
                     Vec3d pitchYawx = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.owner.poslist.get(this.number - 1)));
                     this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYawx.x);
                     this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYawx.y);
                  }

                  if (this.number == 0) {
                     Vec3d pitchYawx = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.owner.getPositionVector()));
                     this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYawx.x);
                     this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYawx.y);
                  }
               }

               if (this.owner.getAttackTarget() != null) {
                  this.setAttackTarget(this.owner.getAttackTarget());
               }
            }
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISegmAABBAttack(this, 40, 0.2F));
      }
   }

   public static class KillBot extends AbstractMob {
      public KillBot(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = Sounds.mob_robot_hurt;
         this.deathSound = Sounds.mob_robot_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = "toxic";
         this.setattributes(80.0, 32.0, 10.0, 0.45, 6.0, 3.0, 0.1, 0.0, 0.0, 0.2);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.CIRCUITADVANCED, 0.25F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.WIRECOPPER, 0.5F, 0, 0, 2, 1),
               new MobDrop(ItemsRegister.SCRAPMETAL, 0.75F, 0, 0, 2, 1),
               new MobDrop(BlocksRegister.SCRAPELECTRONICS, 0.35F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.BEARINGLEAD, 0.25F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.BULLETLEAD, 0.45F, 0, 10, 20, 25)
            }
         );
         this.stepHeight = 1.0F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 3);
         this.soul = null;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.PAIN, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROBOT_OIL_BLOOD;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var2;
            float sp = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.var2 += sp;
         }
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.TOXIN && potion != MobEffects.POISON && potion != MobEffects.REGENERATION
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.Bullet entity = new HostileProjectiles.Bullet(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.0F + this.getMobDifficulty() * 0.2F, 1.0F);
            entity.damage = 5.0F;
            this.world.spawnEntity(entity);
         }
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
                  Sounds.shoot,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true).configureDistance(0.0F, 4.0F));
         this.tasks.addTask(3, new EntityAISkeleton(this, 0.4, 45, 32.0F, 1).setBurst(true, 2, 0, false, 7));
         this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(6, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Lump extends AbstractMob {
      public Lump(World world) {
         super(world, 0.9F, 0.9F);
         this.hurtSound = Sounds.mob_slime_hurt;
         this.deathSound = Sounds.mob_slime_death;
         this.livingSound = Sounds.mob_slime_living;
         this.stepSound = SoundEvents.BLOCK_SLIME_STEP;
         this.defaultteam = "toxic";
         this.setattributes(30.0, 64.0, 5.0, 0.4, 2.0, 2.0, 0.0, 0.1, 0.0, 0.25);
         this.registerLOOT(new MobDrop[]{new MobDrop(Items.SLIME_BALL, 0.4F, 0, 1, 2, 2)});
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 3);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ABOMINATION_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         if (potion == MobEffects.POISON) {
            return false;
         } else if (potion == PotionEffects.CHLORITE) {
            return false;
         } else {
            return potion == PotionEffects.SLIME ? false : super.isPotionApplicable(potioneffectIn);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      @Override
      public void onDeath(DamageSource cause) {
         if (!this.world.isRemote) {
            double damageRadius = 50.0;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<BossAbomination> list = this.world.getEntitiesWithinAABB(BossAbomination.class, axisalignedbb);
            if (!list.isEmpty()) {
               boolean dealDamage = true;

               for (BossAbomination entitylivingbase : list) {
                  if (entitylivingbase.lumped) {
                     entitylivingbase.lumpKills++;
                     entitylivingbase.hurtResistantTime = 0;
                     if (dealDamage) {
                        entitylivingbase.attackEntityFrom(cause, 5.0F);
                        dealDamage = false;
                     }

                     if (entitylivingbase.lumpKills > 11 + this.getMobDifficulty() * 2) {
                        entitylivingbase.unsplit();
                     }
                  }
               }
            }
         }

         super.onDeath(cause);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 20, 0.4F));
         this.tasks.addTask(2, new EntityAIJumpingMovement(this, 20, 0.5F).setAnimationOnJump(-3));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Mosquito extends AbstractMob {
      public boolean byPlayer;
      public boolean berserk;
      public boolean weak;

      public Mosquito(World world) {
         super(world, 0.2F, 0.2F);
         this.hurtSound = SoundEvents.ENTITY_SILVERFISH_HURT;
         this.deathSound = SoundEvents.ENTITY_SILVERFISH_DEATH;
         this.defaultteam = "toxic";
         this.setattributes(12.0, 10.0, 3.0F * WeaponParameters.EXlevelTOXINIUM, 0.14, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
         this.byPlayer = false;
         this.setNoGravity(true);
         this.berserk = false;
         this.weak = false;
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 3);
      }

      public Mosquito(World world, EntityPlayer player, EntityLivingBase attacktarg, boolean berserk, boolean weak) {
         super(world, 0.2F, 0.2F);
         this.hurtSound = SoundEvents.ENTITY_SILVERFISH_HURT;
         this.deathSound = SoundEvents.ENTITY_SILVERFISH_DEATH;
         this.team = Team.getTeamFor(player);
         this.setattributes(12.0, 10.0, (berserk ? 2 : 3) * WeaponParameters.EXlevelTOXINIUM, 0.14, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
         this.byPlayer = true;
         this.setAttackTarget(attacktarg);
         this.setNoGravity(true);
         this.berserk = berserk;
         this.weak = weak;
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 3);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROTTEN_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.ARTHROPOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setBoolean("byplayer", this.byPlayer);
         compound.setString("team", this.team);
         compound.setBoolean("berserk", this.berserk);
         compound.setBoolean("weak", this.weak);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("byplayer")) {
            this.byPlayer = compound.getBoolean("byplayer");
         }

         if (compound.hasKey("team")) {
            this.team = compound.getString("team");
         }

         if (compound.hasKey("berserk")) {
            this.berserk = compound.getBoolean("berserk");
         }

         if (compound.hasKey("weak")) {
            this.weak = compound.getBoolean("weak");
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.byPlayer && this.ticksExisted > 250) {
            this.setDead();
         }
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (entityIn == this.getAttackTarget()) {
            if (this.berserk) {
               if (entityIn instanceof EntityLivingBase) {
                  PotionEffect debaff = new PotionEffect(PotionEffects.BERSERK, 70);
                  ((EntityLivingBase)entityIn).addPotionEffect(debaff);
                  return super.attackEntityAsMob(entityIn);
               }
            } else {
               if (!this.weak) {
                  return super.attackEntityAsMob(entityIn);
               }

               if (entityIn instanceof EntityLivingBase) {
                  PotionEffect debaff = new PotionEffect(MobEffects.WEAKNESS, 300);
                  ((EntityLivingBase)entityIn).addPotionEffect(debaff);
                  return super.attackEntityAsMob(entityIn);
               }
            }
         }

         return false;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAICorrupter(this, 20, 32.0F, 1, false, 2.5F, 3.0F));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 15, 0.3F));
         this.tasks.addTask(3, new EntityAIDash(this, 25, 4.0F, 0.5F, 1.0F, false, 0.0F));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
      }
   }

   public static class MutantBeast extends AbstractMob {
      public static ResourceLocation circle3 = new ResourceLocation("arpg:textures/circle3.png");
      public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");

      public MutantBeast(World world) {
         super(world, 1.2F, 2.15F);
         this.hurtSound = Sounds.mob_zombie_hurt;
         this.deathSound = Sounds.mob_zombie_death;
         this.livingSound = Sounds.mob_zombie_living;
         this.stepSound = SoundEvents.ENTITY_HUSK_STEP;
         this.defaultteam = "toxic";
         this.setattributes(150.0, 64.0, 13.0, 0.23, 5.0, 1.0, 0.3, 0.3, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.ROTTEN_FLESH, 1.0F, 0, 0, 4, 4),
               new MobDrop(ItemsRegister.ANTIDOTE, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.WASTEBURGER, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.TOXEDGEBREAD, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.TOXICOLA, 0.1F, 0, 1, 1, 0),
               new MobDrop(BlocksRegister.TOXICTORCH, 0.1F, 0, 2, 4, 4)
            }
         );
         this.leadershipBase = 35;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 3);
         this.soul = Soul.MUTATED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.DEATH, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PAIN, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROTTEN_BLOOD;
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (!player.getCooldownTracker().hasCooldown(ItemsRegister.WASTEBURGER) && player.getHeldItemMainhand().getItem() == ItemsRegister.WASTEBURGER) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.owner == null) {
               if (this.rand.nextFloat() < 0.3) {
                  this.tame(player);
                  this.world.setEntityState(this, (byte)7);
                  player.getCooldownTracker().setCooldown(ItemsRegister.WASTEBURGER, 100);
               }
            } else {
               this.heal(13.0F);
               player.getCooldownTracker().setCooldown(ItemsRegister.WASTEBURGER, 100);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 7) {
            for (int i = 0; i < 15; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
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

         if (id == 13) {
            RayTraceResult result = this.world
               .rayTraceBlocks(this.getPositionVector(), new Vec3d(this.posX, this.posY - 3.0, this.posZ));
            Vec3d posr = result != null ? result.hitVec : this.getPositionVector();
            GUNParticle particle = new GUNParticle(
               circle3,
               0.1F,
               0.0F,
               7,
               230,
               this.world,
               posr.x,
               posr.y + 0.1,
               posr.z,
               0.0F,
               0.0F,
               0.0F,
               0.8F,
               0.8F,
               0.8F,
               true,
               this.rand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = 0.7F;
            particle.alphaTickAdding = -0.08F;
            particle.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
            particle.randomDeath = false;
            this.world.spawnEntity(particle);

            for (int ss = 0; ss < 20; ss++) {
               int lt = 16 + this.rand.nextInt(10);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  0.8F + (float)this.rand.nextGaussian() / 8.0F,
                  -0.003F,
                  lt,
                  200,
                  this.world,
                  posr.x,
                  posr.y + 0.1,
                  posr.z,
                  (float)this.rand.nextGaussian() / 8.0F,
                  0.0F,
                  (float)this.rand.nextGaussian() / 8.0F,
                  0.8F,
                  0.8F,
                  0.8F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -1.0F / lt;
               this.world.spawnEntity(bigsmoke);
            }

            BlockPos poss = this.getPosition().down();
            int radius = 4;

            for (int x = -radius; x <= radius; x++) {
               for (int z = -radius; z <= radius; z++) {
                  int y = poss.getY() + 1;

                  while (y >= poss.getY() - 2 && !this.spawnParticleOnBlockTop(new BlockPos(poss.getX() + x, y, poss.getZ() + z))) {
                     y--;
                  }
               }
            }
         }

         super.handleStatusUpdate(id);
      }

      public boolean spawnParticleOnBlockTop(BlockPos pos) {
         BlockPos posup = pos.up();
         IBlockState iBlockStateUp = this.world.getBlockState(posup);
         if (iBlockStateUp.getCollisionBoundingBox(this.world, posup) == null) {
            IBlockState iBlockState = this.world.getBlockState(pos);
            AxisAlignedBB aabb = iBlockState.getCollisionBoundingBox(this.world, pos);
            if (aabb != null) {
               int imax = 1 + this.rand.nextInt(2);

               for (int i = 0; i < imax; i++) {
                  double x = pos.getX() + aabb.minX + (aabb.maxX - aabb.minX) * this.rand.nextFloat();
                  double y = pos.getY() + aabb.maxY + 0.02;
                  double z = pos.getZ() + aabb.minZ + (aabb.maxZ - aabb.minZ) * this.rand.nextFloat();
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        x,
                        y,
                        z,
                        0.0,
                        0.15 + this.rand.nextGaussian() / 20.0,
                        0.0,
                        new int[]{Block.getStateId(iBlockState)}
                     );
                  if (this.rand.nextFloat() < 0.6F) {
                     this.world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0, 0.0, 0.0, new int[0]);
                  }
               }

               return true;
            }
         }

         return false;
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         EntityAIAttackSweep attackSweep = new EntityAIAttackSweep(this, 60, 2.0F, 2.5F, 3.5F, 15).setBreakBlocks(13.0F).setTriggerOnStart();
         attackSweep.canReflect = true;
         attackSweep.damageToReflection = 9.5F;
         this.tasks.addTask(2, attackSweep);
         this.tasks.addTask(2, new EntityAIRush(this, false, false, false));
         this.tasks
            .addTask(
               2,
               new EntityAIJumpBeastAttack(this, 80, 16.0F, 4.0F, 1.0F, 0.25F, -1, 13.0F, false)
                  .setSounds(Sounds.mutant_beast_jump, SoundEvents.ENTITY_GENERIC_EXPLODE)
            );
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class MutantZombie extends AbstractMob {
      public MutantZombie(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = SoundEvents.ENTITY_HUSK_HURT;
         this.deathSound = SoundEvents.ENTITY_HUSK_DEATH;
         this.livingSound = SoundEvents.ENTITY_HUSK_AMBIENT;
         this.stepSound = SoundEvents.ENTITY_HUSK_STEP;
         this.defaultteam = "toxic";
         this.setattributes(46.0, 64.0, 7.0, 0.263, 4.0, 3.0, 0.0, 0.1, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.ROTTEN_FLESH, 1.0F, 0, 0, 2, 3),
               new MobDrop(ItemsRegister.ANTIDOTE, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.WASTEBURGER, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.TOXEDGEBREAD, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.TOXICOLA, 0.1F, 0, 1, 1, 0),
               new MobDrop(BlocksRegister.TOXICTORCH, 0.1F, 0, 2, 4, 4)
            }
         );
         this.leadershipBase = 5;
         this.setRoleValues(EnumMobRole.SOLDIER, 3);
         this.soul = Soul.PALE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.DEATH, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROTTEN_BLOOD;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (entityIn instanceof EntityPlayer) {
            Mana.addRad((EntityPlayer)entityIn, 27, true);
         }

         return super.attackEntityAsMob(entityIn);
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (!player.getCooldownTracker().hasCooldown(Items.ROTTEN_FLESH) && player.getHeldItemMainhand().getItem() == Items.ROTTEN_FLESH) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.owner == null) {
               if (this.rand.nextFloat() < 0.5) {
                  this.tame(player);
                  this.world.setEntityState(this, (byte)7);
               }
            } else {
               this.heal(8.0F);
               player.getCooldownTracker().setCooldown(Items.ROTTEN_FLESH, 10);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 7) {
            for (int i = 0; i < 7; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
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

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @Nullable
      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
         super.onInitialSpawn(difficulty, livingdata);
         if (this.rand.nextFloat() < 0.2F) {
            ItemStack stack;
            if (this.rand.nextFloat() < 0.25F) {
               stack = new ItemStack(Items.IRON_HELMET);
            } else if (this.rand.nextFloat() < 0.2F) {
               stack = new ItemStack(Items.GOLDEN_HELMET);
            } else if (this.rand.nextFloat() < 0.2F) {
               stack = new ItemStack(Items.LEATHER_HELMET);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(Items.IRON_HELMET);
            } else if (this.rand.nextFloat() < 0.15F) {
               stack = new ItemStack(Items.IRON_HELMET);
            } else {
               stack = new ItemStack(Items.CHAINMAIL_HELMET);
            }

            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
            this.setDropChance(EntityEquipmentSlot.HEAD, 0.3F);
         }

         if (this.rand.nextFloat() < 0.17F) {
            ItemStack stack;
            if (this.rand.nextFloat() < 0.25F) {
               stack = new ItemStack(Items.LEATHER_CHESTPLATE);
            } else if (this.rand.nextFloat() < 0.08F) {
               stack = new ItemStack(Items.IRON_CHESTPLATE);
            } else if (this.rand.nextFloat() < 0.15F) {
               stack = new ItemStack(Items.GOLDEN_CHESTPLATE);
            } else {
               stack = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
            }

            this.setItemStackToSlot(EntityEquipmentSlot.CHEST, stack);
            this.setDropChance(EntityEquipmentSlot.CHEST, 0.3F);
         }

         if (this.rand.nextFloat() < 0.15F) {
            ItemStack stack;
            if (this.rand.nextFloat() < 0.45F) {
               stack = new ItemStack(Items.LEATHER_LEGGINGS);
            } else {
               stack = new ItemStack(Items.CHAINMAIL_LEGGINGS);
            }

            this.setItemStackToSlot(EntityEquipmentSlot.LEGS, stack);
            this.setDropChance(EntityEquipmentSlot.LEGS, 0.3F);
         }

         if (this.rand.nextFloat() < 0.1F) {
            ItemStack stack;
            if (this.rand.nextFloat() < 0.45F) {
               stack = new ItemStack(Items.LEATHER_BOOTS);
            } else {
               stack = new ItemStack(Items.CHAINMAIL_BOOTS);
            }

            this.setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
            this.setDropChance(EntityEquipmentSlot.FEET, 0.3F);
         }

         if (this.rand.nextFloat() < 0.27F) {
            ItemStack stack;
            if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(ItemsRegister.TOXICOLA);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(Items.BONE);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(Items.STONE_SWORD);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(Items.WOODEN_SWORD);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(Items.STONE_AXE);
            } else if (this.rand.nextFloat() < 0.1F) {
               stack = new ItemStack(ItemsRegister.DECEIDUSJUICE);
            } else if (this.rand.nextFloat() < 0.07F) {
               stack = new ItemStack(ItemsRegister.WASTEBURGER);
            } else if (this.rand.nextFloat() < 0.05F) {
               stack = new ItemStack(ItemsRegister.ANTIRADPILLS);
            } else {
               stack = new ItemStack(ItemsRegister.TOXIBERRYSTICK);
            }

            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
            this.setDropChance(EntityEquipmentSlot.MAINHAND, 0.8F);
         }

         if (this.rand.nextFloat() < 0.75F) {
            this.setCanPickUpLoot(true);
         }

         return livingdata;
      }
   }

   public static class PoisonSpitter extends AbstractMob {
      public PoisonSpitter(World world) {
         super(world, 0.8F, 1.7F);
         this.hurtSound = Sounds.mob_plant_hurt;
         this.deathSound = Sounds.mob_slime_death;
         this.defaultteam = "toxic";
         this.setattributes(65.0, 32.0, 5.0, 0.0, 3.0, 0.0, 1.0, 0.1, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Items.SLIME_BALL, 1.0F, 0, 0, 2, 3)});
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 3);
         this.soul = Soul.MUTATED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.55) {
            ShardType.spawnShards(ShardType.EARTH, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.POISON, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.TEST_TUBE_BLOOD;
      }

      public AxisAlignedBB getEntityBoundingBox() {
         return super.getEntityBoundingBox();
      }

      protected boolean canDespawn() {
         return false;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         if (potioneffectIn.getPotion() == MobEffects.POISON) {
            return false;
         } else {
            return potioneffectIn.getPotion() == PotionEffects.TOXIN && potioneffectIn.getAmplifier() < 2 ? false : super.isPotionApplicable(potioneffectIn);
         }
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.PoisonSpit entityspit = new HostileProjectiles.PoisonSpit(this.world, this);
            entityspit.shoot(this, this.rotationPitch - 7.0F, this.rotationYaw, 0.0F, 0.9F, 1.4F);
            this.world.spawnEntity(entityspit);
         }
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
                  Sounds.slimeshoot,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIShoot(this, 45, 30.0F, 70));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(6, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class RocketBot extends AbstractMob {
      public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");

      public RocketBot(World world) {
         super(world, 1.0F, 2.0F);
         this.hurtSound = Sounds.mob_robot_hurt;
         this.deathSound = Sounds.mob_robot_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = "toxic";
         this.setattributes(150.0, 64.0, 12.0, 0.17, 10.0, 5.0, 0.9, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.CIRCUIT, 0.5F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.WIRECOPPER, 0.5F, 0, 0, 2, 2),
               new MobDrop(ItemsRegister.SCRAPMETAL, 1.0F, 0, 0, 2, 2),
               new MobDrop(BlocksRegister.SCRAPELECTRONICS, 0.6F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.BEARINGLEAD, 0.4F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.ROCKETCOMMON, 1.0F, 0, 0, 6, 3)
            }
         );
         this.stepHeight = 1.0F;
         this.setRoleValues(EnumMobRole.ELITE_ENEMY, 3);
         this.soul = null;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.FIRE, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROBOT_OIL_BLOOD;
      }

      public boolean isPushedByWater() {
         return false;
      }

      public float getEyeHeight() {
         return this.height * 0.92F;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var2;
         }
      }

      protected float getSoundPitch() {
         return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.8F;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.TOXIN && potion != MobEffects.POISON && potion != MobEffects.REGENERATION
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.Rocket entity = new HostileProjectiles.Rocket(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.7F + this.getMobDifficulty() * 0.15F, 1.0F);
            entity.damage = 15.0F;
            this.world.spawnEntity(entity);
         }
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
                  Sounds.firework,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.mob_robot_attack,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 27) {
            this.var2 += 0.2F;
         }

         if (id == 28) {
            this.var2 -= 0.2F;
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISkeleton(this, 0.4, 50, 40.0F, 1).setApproach());
         this.tasks.addTask(2, new EntityAIAttackSweep(this, 30, 1.7F, 2.0F, 3.0F, 10).setTriggerOnStart());
         this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(6, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class SlimeJellyfish extends AbstractMob {
      public SlimeJellyfish(World world) {
         super(world, 0.5F, 0.5F);
         this.hurtSound = SoundEvents.ENTITY_GUARDIAN_HURT;
         this.deathSound = SoundEvents.ENTITY_SQUID_DEATH;
         this.defaultteam = "";
         this.setattributes(10.0, 16.0, 2.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Items.SLIME_BALL, 1.0F, 0, 0, 3, 3)});
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected void initEntityAI() {
      }
   }

   public static class Springer extends AbstractMob {
      public Springer(World world) {
         super(world, 0.55F, 0.5F);
         this.hurtSound = Sounds.mob_wild_hurt;
         this.deathSound = Sounds.mob_wild_death;
         this.livingSound = Sounds.mob_wild_living;
         this.defaultteam = "toxic";
         this.setattributes(20.0, 64.0, 5.0, 0.5, 1.0, 0.0, 0.0, 0.0, 1.0, 0.2);
         this.leadershipBase = 2;
         this.setRoleValues(EnumMobRole.SWARMER, 3);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.2) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.MUTANT_PURPLE_BLOOD;
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         Item itemm = player.getHeldItemMainhand().getItem();
         if ((
               itemm == ItemsRegister.TOXIBERRYWEEPINGSEEDS
                  || itemm == ItemsRegister.TOXIBERRYVIBRANTSEEDS
                  || itemm == ItemsRegister.SMALLTOXIBERRY
                  || itemm == ItemsRegister.DECEIDUSSEEDS
            )
            && this.owner == null) {
            if (!player.capabilities.isCreativeMode) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (this.rand.nextFloat() < 0.5) {
               this.tame(player);
               this.world.setEntityState(this, (byte)7);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      @Override
      public void handleStatusUpdate(byte id) {
         if (id == 7) {
            for (int i = 0; i < 7; i++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
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

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFollowSummoner(this, 1.0));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true));
         this.tasks.addTask(2, new EntityAIJumpingMovement(this, 25 + this.rand.nextInt(6), 0.4F).setAnimationOnJump(-4));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class TestTubeCreature extends AbstractMob {
      ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");

      public TestTubeCreature(World world) {
         super(world, 1.8F, 1.5F);
         this.hurtSound = Sounds.mob_slime_hurt;
         this.deathSound = Sounds.mob_slime_death;
         this.livingSound = Sounds.mob_slime_living;
         this.stepSound = SoundEvents.BLOCK_SLIME_STEP;
         this.defaultteam = "toxic";
         this.setattributes(50.0, 64.0, 8.0, 0.32, 0.0, 0.0, 0.3, 0.3, 0.0, 0.35);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.VILESUBSTANCE, 1.0F, 0, 1, 3, 1)});
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 3);
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.POISON, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.LIVE, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.TEST_TUBE_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 9) {
            for (int ss = 0; ss < 10; ss++) {
               GUNParticle bubble = new GUNParticle(
                  this.texturbubble,
                  0.13F + this.rand.nextFloat() / 20.0F,
                  0.03F,
                  40 + this.rand.nextInt(20),
                  200,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F + 0.15F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  0.5F,
                  0.85F + (float)this.rand.nextGaussian() / 5.0F,
                  1.0F,
                  false,
                  this.rand.nextInt(180),
                  true,
                  1.3F
               );
               this.world.spawnEntity(bubble);
            }
         }
      }

      @Override
      public void onDeath(DamageSource cause) {
         super.onDeath(cause);
         if (!this.world.isRemote) {
            int count = this.rand.nextInt(4) + 1;
            this.world.setEntityState(this, (byte)9);

            for (int i = 0; i < count; i++) {
               TestTubeSubstance mob = new TestTubeSubstance(this.world);
               mob.setPosition(this.posX, this.posY, this.posZ);
               mob.motionX = (this.rand.nextFloat() - 0.5F) / 13.0F;
               mob.motionY = this.rand.nextFloat() / 22.0F;
               mob.motionZ = (this.rand.nextFloat() - 0.5F) / 13.0F;
               this.world.spawnEntity(mob);
               mob.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(mob)), (IEntityLivingData)null);
               mob.team = this.team;
               mob.isAgressive = this.isAgressive;
               mob.canDropLoot = this.canDropLoot;
            }
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 40, 0.4F));
         this.tasks.addTask(2, new EntityAIJumpingMovement(this, 35, 0.55F).setAnimationOnJump(-3));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class TestTubeSubstance extends AbstractMob {
      public TestTubeSubstance(World world) {
         super(world, 1.3F, 1.1F);
         this.hurtSound = Sounds.mob_slime_hurt;
         this.deathSound = Sounds.mob_slime_death;
         this.livingSound = Sounds.mob_slime_living;
         this.stepSound = SoundEvents.BLOCK_SLIME_STEP;
         this.defaultteam = "toxic";
         this.setattributes(30.0, 40.0, 5.0, 0.4, 0.0, 0.0, 0.0, 0.2, 0.0, 0.35);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.VILESUBSTANCE, 0.8F, 0, 1, 1, 2)});
         this.setRoleValues(EnumMobRole.SOLDIER, 3);
         this.soul = Soul.MUTATED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.POISON, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.TEST_TUBE_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 40, 0.4F));
         this.tasks.addTask(2, new EntityAIJumpingMovement(this, 30, 0.6F).setAnimationOnJump(-3));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (this.getHeldItemMainhand().isEmpty()) {
            if (this.rand.nextFloat() < 0.25F) {
               ItemStack stack;
               if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(ItemsRegister.TOXICOLA);
               } else if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(Items.IRON_PICKAXE, 1, this.rand.nextInt(245));
               } else if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(Items.IRON_SWORD, 1, this.rand.nextInt(245));
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(Items.DIAMOND_PICKAXE, 1, this.rand.nextInt(500));
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(Items.DIAMOND_AXE, 1, this.rand.nextInt(500));
               } else if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(ItemsRegister.ANTIDOTE);
               } else if (this.rand.nextFloat() < 0.08F) {
                  stack = new ItemStack(ItemsRegister.ANTIPOTION);
               } else if (this.rand.nextFloat() < 0.08F) {
                  stack = new ItemStack(ItemsRegister.ANTIRADINJECTOR);
               } else if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(ItemsRegister.ANTIRADPILLS);
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(ItemsRegister.WASTEBURGER);
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(Items.ARROW);
               } else if (this.rand.nextFloat() < 0.1F) {
                  stack = new ItemStack(ItemsRegister.GRENADECLASSIC);
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(Item.getItemFromBlock(Blocks.TNT));
               } else if (this.rand.nextFloat() < 0.06F) {
                  stack = new ItemStack(Item.getItemFromBlock(BlocksRegister.TOXIBULB));
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(ItemsRegister.ROCKETCHEMICAL);
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(ItemsRegister.ROCKETMINING);
               } else if (this.rand.nextFloat() < 0.05F) {
                  stack = new ItemStack(ItemsRegister.ROCKETDEMOLISHING);
               } else {
                  Item it = Item.getByNameOrId("thermalfoundation:fertilizer");
                  if (it != null) {
                     stack = new ItemStack(it, 1, 2);
                  } else {
                     stack = new ItemStack(Items.ARROW);
                  }
               }

               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
            }

            if (this.rand.nextFloat() < 0.65F) {
               this.setCanPickUpLoot(true);
            }
         }

         this.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
         return super.onInitialSpawn(difficulty, livingdata);
      }

      public EntityItem entityDropItem(ItemStack stack, float offsetY) {
         EntityItem entityItem = super.entityDropItem(stack, offsetY);
         if (entityItem != null && entityItem.getItem().getItem() == ItemsRegister.BUNKERKEYCARD) {
            entityItem.setEntityInvulnerable(true);
            entityItem.lifespan = 2000000;
            entityItem.setGlowing(true);
         }

         return entityItem;
      }
   }

   public static class Turret extends AbstractMob implements IEntitySynchronize, IWrenchUser {
      public float previousDeployStage = 0.0F;
      public float deployStage = 0.0F;
      public int shouldSend = 0;
      public boolean autoDeploy = false;
      public boolean infinityAmmo = true;
      public int ammo = 0;
      public int maxammo = 1000;
      public int mightlvl = 0;
      public ItemBullet bullets = null;

      public Turret(World world) {
         super(world, 0.8F, 0.9F);
         this.hurtSound = Sounds.mob_robot_hurt;
         this.deathSound = Sounds.mob_robot_death;
         this.livingSound = Sounds.mob_robot_living;
         this.defaultteam = "toxic";
         this.setattributes(55.0, 25.0, 4.0, 0.0, 4.0, 4.0, 0.95, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.CIRCUIT, 0.2F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.SCRAPMETAL, 1.0F, 0, 1, 1, 2),
               new MobDrop(BlocksRegister.SCRAPELECTRONICS, 0.2F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.BEARINGLEAD, 0.2F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.BULLETLEAD, 0.4F, 0, 10, 15, 15)
            }
         );
         this.emptyDespawn = false;
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 3);
         this.soul = null;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ROBOT_OIL_BLOOD;
      }

      protected void collideWithEntity(Entity entityIn) {
         if (!entityIn.isRidingSameEntity(this) && !this.noClip && !entityIn.noClip) {
            double d0 = this.posX - entityIn.posX;
            double d1 = this.posZ - entityIn.posZ;
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
               d0 *= 0.05;
               d1 *= 0.05;
               if (!entityIn.isBeingRidden()) {
                  entityIn.addVelocity(-d0, 0.0, -d1);
               }

               if (!this.isBeingRidden()) {
                  this.addVelocity(d0 / 50.0, 0.0, d1 / 50.0);
               }
            }
         }
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
               d0 *= 0.05;
               d1 *= 0.05;
               if (!this.isBeingRidden()) {
                  this.addVelocity(-d0 / 50.0, 0.0, -d1 / 50.0);
               }

               if (!entityIn.isBeingRidden()) {
                  entityIn.addVelocity(d0, 0.0, d1);
               }
            }
         }
      }

      public String getName() {
         return this.infinityAmmo
            ? super.getName()
            : super.getName() + " | " + (this.bullets == null ? "AMMO" : this.bullets.getNbtName()) + ": " + this.ammo;
      }

      public boolean isAIDisabled() {
         return this.deployStage < 1.0F || super.isAIDisabled();
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (!this.world.isRemote && !Team.checkIsOpponent(player, this)) {
            boolean movewrench = true;
            ItemStack stack = player.getHeldItemMainhand();
            if (!stack.isEmpty()
               && stack.getItem() instanceof ItemBullet
               && (this.bullets == null || this.bullets == stack.getItem() || this.ammo <= 0)) {
               int count = Math.min(stack.getCount(), this.maxammo - this.ammo);
               if (count > 0) {
                  if (this.bullets == null || this.ammo <= 0) {
                     this.bullets = (ItemBullet)stack.getItem();
                  }

                  stack.shrink(count);
                  this.ammo += count;
                  this.playSound(Sounds.vampireknifes, 0.5F, 1.0F + this.rand.nextFloat() / 5.0F);
                  movewrench = false;
                  moveBulletsToHand(player, EnumHand.MAIN_HAND, this.bullets);
                  IEntitySynchronize.sendSynchronize(
                     this,
                     32.0,
                     this.deployStage,
                     this.previousDeployStage,
                     this.infinityAmmo ? 2000000.0 : this.ammo,
                     this.bullets == null ? 0.0 : this.bullets.id,
                     0.0,
                     0.0
                  );
               }
            }

            if (movewrench) {
               Wrench.moveToHand(player, EnumHand.MAIN_HAND);
            }
         }

         return super.applyPlayerInteraction(player, vec, hand);
      }

      @Override
      public void onUseWrench(World world, Entity entityUser, ItemStack wrench, float power, BlockPos pos) {
         if (!world.isRemote && entityUser != null && !Team.checkIsOpponent(entityUser, this)) {
            boolean shouldHeal = this.deployStage >= 1.0F && this.getHealth() < this.getMaxHealth();
            boolean shouldAnimateAndSound = false;
            float ds = this.deployStage + power * (entityUser.isSneaking() ? -0.02F : 0.02F);
            this.deployStage = MathHelper.clamp(ds, 0.0F, 1.0F);
            this.shouldSend = 2;
            if (this.deployStage >= 1.0F && this.previousDeployStage < 1.0F) {
               this.playSound(Sounds.turret_deploy, 1.1F, 0.9F + this.rand.nextFloat() / 5.0F);
            }

            if (this.deployStage != this.previousDeployStage || shouldHeal) {
               shouldAnimateAndSound = true;
            }

            if (ds < 0.0F) {
               this.dropAsItem(world, entityUser);
            }

            if (entityUser instanceof EntityPlayer) {
               if (!((EntityPlayer)entityUser).getCooldownTracker().hasCooldown(wrench.getItem())) {
                  if (shouldAnimateAndSound) {
                     ((EntityPlayer)entityUser).getCooldownTracker().setCooldown(wrench.getItem(), 10);
                     this.playSound(Sounds.wrench, 0.8F, 0.9F + this.rand.nextFloat() / 5.0F);
                  }

                  if (shouldHeal) {
                     this.heal(power * 4.0F);
                  }
               }
            } else if (shouldHeal) {
               this.heal(power * 0.4F);
            }

            if (wrench.attemptDamageItem(1, this.rand, entityUser instanceof EntityPlayerMP ? (EntityPlayerMP)entityUser : null)) {
               wrench.shrink(1);
               if (entityUser instanceof EntityPlayer) {
                  EntityPlayer entityplayer = (EntityPlayer)entityUser;
                  entityplayer.addStat(StatList.getObjectBreakStats(wrench.getItem()));
                  entityplayer.renderBrokenItemStack(wrench);
               }

               wrench.setItemDamage(0);
            }
         }
      }

      public static void moveBulletsToHand(EntityPlayer player, EnumHand hand, @Nullable ItemBullet bullet) {
         if (player.getHeldItem(hand).isEmpty()) {
            int slot = FindAmmo.getSlotForItemBullet(player.inventory, bullet, false);
            if (slot >= 0) {
               ItemStack stack = player.inventory.getStackInSlot(slot).copy();
               player.setHeldItem(hand, stack);
               player.inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
            }
         }
      }

      public void dropAsItem(World world, Entity entityUser) {
         ItemStack stack = new ItemStack(ItemsRegister.ITEMTURRET);
         NBTTagCompound itemCompound = new NBTTagCompound();
         stack.setTagCompound(itemCompound);
         NBTHelper.GiveNBTfloat(stack, this.getHealth(), "health");
         NBTHelper.GiveNBTint(stack, this.ammo, "ammo");
         if (this.bullets != null) {
            NBTHelper.GiveNBTstring(stack, this.bullets.getNbtName(), "bullet");
         }

         stack.getTagCompound().setTag("display", new NBTTagCompound());
         if (this.hasCustomName()) {
            stack.setStackDisplayName(this.getCustomNameTag());
         }

         if (this.mightlvl > 0) {
            stack.addEnchantment(EnchantmentInit.MIGHT, this.mightlvl);
         }

         if (entityUser instanceof EntityPlayer && ((EntityPlayer)entityUser).addItemStackToInventory(stack)) {
            this.setDead();
         } else {
            world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, stack));
            this.setDead();
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            this.previousDeployStage = this.deployStage;
            if (this.autoDeploy && (this.deployStage < 1.0F || this.ticksExisted % 21 == 0)) {
               this.deployStage += 0.02F;
               IEntitySynchronize.sendSynchronize(
                  this,
                  32.0,
                  this.deployStage,
                  this.previousDeployStage,
                  this.infinityAmmo ? 2000000.0 : this.ammo,
                  this.bullets == null ? 0.0 : this.bullets.id,
                  0.0,
                  0.0
               );
            }

            if (this.shouldSend > 0) {
               this.shouldSend--;
               IEntitySynchronize.sendSynchronize(
                  this,
                  32.0,
                  this.deployStage,
                  this.previousDeployStage,
                  this.infinityAmmo ? 2000000.0 : this.ammo,
                  this.bullets == null ? 0.0 : this.bullets.id,
                  0.0,
                  0.0
               );
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void turn(float yaw, float pitch) {
         if (this.getRidingEntity() != null) {
            this.getRidingEntity().applyOrientationToEntity(this);
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         this.var3 = (float)y;
         this.var2 = (float)x;
         this.ammo = (int)z;
         this.infinityAmmo = z > 999999.0;
         this.bullets = ItemBullet.getItemBulletFromId((int)a);
      }

      public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         compound.setFloat("deploystage", this.deployStage);
         if (this.mightlvl > 0) {
            compound.setInteger("mightlvl", this.mightlvl);
         }

         compound.setInteger("ammo", this.ammo);
         compound.setBoolean("infinityammo", this.infinityAmmo);
         if (this.bullets != null) {
            compound.setString("bullettype", this.bullets.getNbtName());
         }

         return super.writeToNBT(compound);
      }

      public void readFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("deploystage")) {
            this.deployStage = compound.getFloat("deploystage");
         }

         if (compound.hasKey("mightlvl")) {
            this.mightlvl = compound.getInteger("mightlvl");
         }

         if (compound.hasKey("ammo")) {
            this.ammo = compound.getInteger("ammo");
         }

         if (compound.hasKey("infinityammo")) {
            this.infinityAmmo = compound.getBoolean("infinityammo");
         }

         if (compound.hasKey("bullettype")) {
            this.bullets = ItemBullet.getItemBulletFromString(compound.getString("bullettype"));
         }

         super.readFromNBT(compound);
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         this.autoDeploy = true;
         return super.onInitialSpawn(difficulty, livingdata);
      }

      @Override
      public IEntityLivingData onInitialSpawn() {
         this.autoDeploy = true;
         return super.onInitialSpawn();
      }

      protected float getJumpUpwardsMotion() {
         return 0.0F;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion != PotionEffects.TOXIN && potion != MobEffects.POISON && potion != MobEffects.REGENERATION
            ? super.isPotionApplicable(potioneffectIn)
            : false;
      }

      @Override
      public void shoot() {
         if ((this.infinityAmmo || this.ammo > 0) && !this.world.isRemote && this.deployStage >= 1.0F) {
            this.world.setEntityState(this, (byte)8);
            HostileProjectiles.Bullet entity = new HostileProjectiles.Bullet(this.world, this);
            entity.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.6F + this.getMobDifficulty() * 0.13F, 2.5F);
            entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            entity.bullet = this.bullets;
            this.world.spawnEntity(entity);
            if (!this.infinityAmmo) {
               this.ammo--;
               IEntitySynchronize.sendSynchronize(
                  this, 32.0, this.deployStage, this.previousDeployStage, this.ammo, this.bullets == null ? 0.0 : this.bullets.id, 0.0, 0.0
               );
            }
         }
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
                  Sounds.shoot,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.triggerAnimation(-1);
         }

         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.sharkfire,
                  SoundCategory.PLAYERS,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(3, new EntityAISkeleton(this, 0.0, 50, 25.0F, 1).setBurst(true, 10, 0, false, 7));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, 5, true));
      }
   }

   public static class VineChops extends AbstractMob {
      public VineChops(World world) {
         super(world, 0.8F, 1.8F);
         this.hurtSound = Sounds.mob_plant_hurt;
         this.deathSound = Sounds.mob_plant_death;
         this.livingSound = Sounds.mob_plant_living;
         this.defaultteam = "toxic";
         this.setattributes(90.0, 64.0, 8.0, 0.26, 2.0, 1.0, 0.3, 0.2, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.PLANTFIBER, 1.0F, 0, 0, 4, 4), new MobDrop(ItemsRegister.JUNGLEGH, 0.04F, 0, 1, 1, 0)
            }
         );
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 3);
         this.soul = Soul.MUTATED;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.LIVE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.POISON, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.VINES_BLOOD;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         boolean r = super.attackEntityAsMob(entityIn);
         if (entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 900, 1));
         }

         return r;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         Potion potion = potioneffectIn.getPotion();
         return potion == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIDash(this, 120, 8.0F, 3.0F, 1.5F, false, 0.3F).setSoundOnDash(Sounds.mob_mutant_attack));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }
}

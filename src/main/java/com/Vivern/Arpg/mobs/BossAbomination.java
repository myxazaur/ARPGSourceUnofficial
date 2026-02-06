package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BossAbomination extends AbstractBoss implements IEntitySynchronize {
   public float damagToSplit = 0.0F;
   public int lumpKills = 0;
   public boolean lumped = false;
   public int heating = 0;
   public int radAttack = 0;
   public int fallCooldown = 40;
   public boolean summonning = false;
   public int summonY = 0;
   public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/toxic_spell.png");
   public static ResourceLocation spl = new ResourceLocation("arpg:textures/acid_splash5.png");

   public BossAbomination(World world) {
      super(world, 3.5F, 3.5F, Color.GREEN);
      this.hurtSound = Sounds.mob_slime_hurt;
      this.deathSound = Sounds.mob_slime_death;
      this.livingSound = Sounds.mob_slime_living;
      this.stepSound = SoundEvents.BLOCK_SLIME_STEP;
      this.defaultteam = "toxic";
      this.setattributes(1250.0, 64.0, 10.0, 0.4, 5.0, 3.0, 0.75, 0.2, 0.0, 0.55);
      this.registerLOOT(
         new MobDrop[]{
            new MobDrop(ItemsRegister.BLACKGOO, 1.0F, 0, 8, 12, 0),
            new MobDrop(ItemsRegister.TOXICWINGS, 0.25F, 0, 1, 1, 0),
            new MobDrop(ItemsRegister.DETOXICATOR, 0.25F, 0, 1, 1, 0),
            new MobDrop(ItemsRegister.PAINFULROOT, 0.25F, 0, 1, 1, 0)
         }
      );
      this.setDropMoney(150, 200, 1.0F);
      this.experienceValue = 500;
   }

   @Override
   public void dropShards() {
      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.ELECTRIC, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.FIRE, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.WATER, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.9) {
         ShardType.spawnShards(ShardType.POISON, this, 12.0F * dropShardsQuantity);
      }
   }

   @Override
   public BloodType getBloodType() {
      return DeathEffects.ABOMINATION_BLOOD;
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setFloat("damagetosplit", this.damagToSplit);
      compound.setInteger("lumpkills", this.lumpKills);
      compound.setBoolean("lumped", this.lumped);
      compound.setInteger("heating", this.heating);
      compound.setInteger("radattack", this.radAttack);
      compound.setBoolean("summonning", this.summonning);
      compound.setInteger("summonposy", this.summonY);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("damagetosplit")) {
         this.damagToSplit = compound.getFloat("damagetosplit");
      }

      if (compound.hasKey("lumpkills")) {
         this.lumpKills = compound.getInteger("lumpkills");
      }

      if (compound.hasKey("lumped")) {
         this.lumped = compound.getBoolean("lumped");
      }

      if (compound.hasKey("heating")) {
         this.heating = compound.getInteger("heating");
      }

      if (compound.hasKey("radattack")) {
         this.radAttack = compound.getInteger("radattack");
      }

      if (compound.hasKey("summonning")) {
         this.summonning = compound.getBoolean("summonning");
      }

      if (compound.hasKey("summonposy")) {
         this.summonY = compound.getInteger("summonposy");
      }

      if (this.lumped) {
         this.setSize(0.9F, 0.9F);
         this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
         this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
      } else {
         this.setSize(3.5F, 3.5F);
         this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
         this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.75);
      }

      super.readFromNBT(compound);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      this.var1 = (int)x;
      if (y != 0.0) {
         this.summonY = (int)y;
         this.summonning = z > 0.0;
      }
   }

   public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
      if (!this.summonning) {
         super.knockBack(entityIn, strength, xRatio, zRatio);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.summonning) {
            if (this.posY < this.summonY) {
               this.noClip = true;
               this.motionX = 0.0;
               this.motionY = 0.3;
               this.motionZ = 0.0;
               if (this.ticksExisted % 10 == 0) {
                  IEntitySynchronize.sendSynchronize(this, 64.0, this.heating, this.summonY, 1.0, 0.0, 0.0, 0.0);
               }
            } else {
               this.noClip = false;
               this.summonning = false;
               IEntitySynchronize.sendSynchronize(this, 64.0, this.heating, this.summonY, 0.0, 0.0, 0.0, 0.0);
            }
         }

         this.fallCooldown--;
         if (this.onGround && this.fallCooldown <= 0 && !this.summonning) {
            HostileProjectiles.SlimeCloud sl = new HostileProjectiles.SlimeCloud(this.world, this);
            sl.setPosition(this.posX, this.posY - 0.1, this.posZ);
            sl.damage = 8.0F;
            this.world.spawnEntity(sl);
            this.fallCooldown = 25;
         }

         if (this.ticksExisted % 20 == 0) {
            if (this.lumped) {
               this.world.setEntityState(this, (byte)8);
            } else {
               this.world.setEntityState(this, (byte)9);
            }

            if (!this.noClip
               && this.ticksExisted > 100
               && (!this.getEntityData().hasKey("needSpawnCannons") || this.getEntityData().getBoolean("needSpawnCannons"))) {
               this.getEntityData().setBoolean("needSpawnCannons", false);

               for (int i = 0; i < 3; i++) {
                  ToxicomaniaMobsPack.AbominationCannon cannon = new ToxicomaniaMobsPack.AbominationCannon(this.world);
                  cannon.setPosition(this.posX, this.posY + 1.5, this.posZ);
                  cannon.boss = this;
                  cannon.typeCannon = i;
                  cannon.team = this.team;
                  this.world.spawnEntity(cannon);
                  cannon.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(cannon)), (IEntityLivingData)null);
               }
            }

            if (!this.noClip && !this.lumped && this.getEntityData().hasKey("cannons")) {
               NBTTagList tags = this.getEntityData().getTagList("cannons", 10);
               if (!tags.isEmpty()) {
                  for (NBTBase nbt : tags) {
                     if (nbt.getId() == 10) {
                        ToxicomaniaMobsPack.AbominationCannon cannon = new ToxicomaniaMobsPack.AbominationCannon(this.world);
                        cannon.setPosition(this.posX, this.posY + 1.5, this.posZ);
                        cannon.boss = this;
                        cannon.setHealth(((NBTTagCompound)nbt).getFloat("health"));
                        cannon.typeCannon = ((NBTTagCompound)nbt).getInteger("type");
                        cannon.team = this.team;
                        this.world.spawnEntity(cannon);
                     }
                  }
               }

               this.getEntityData().removeTag("cannons");
            }
         }

         if (this.getHealth() <= this.getMaxHealth() * 0.4) {
            this.heating++;
         } else if (this.heating > 0) {
            this.heating--;
         }

         if (this.radAttack > 0) {
            this.radAttack++;
         }

         if (this.heating > 100 && (!this.lumped || this.getHealth() < this.getMaxHealth() * 0.3) && this.radAttack <= 0) {
            this.radAttack = 1;
            this.world
               .playSound(
                  null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode_rad,
                  SoundCategory.BLOCKS,
                  2.8F,
                  0.9F + this.world.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)10);
            this.heating -= 100;
         }

         if (this.radAttack > 15) {
            this.radAttack = 0;
            double damageRadius = 50.0;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(this, entitylivingbase)
                     && GetMOP.thereIsNoBlockCollidesBetween(
                        this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbase), 1.0F, null, false
                     )) {
                     entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
                     if (entitylivingbase instanceof EntityPlayer) {
                        Mana.addRad((EntityPlayer)entitylivingbase, 225, true);
                        if (entitylivingbase == Minecraft.getMinecraft().player) {
                           Booom.lastTick = 10;
                           Booom.frequency = 4.0F;
                           Booom.x = (float)this.rand.nextGaussian();
                           Booom.y = (float)this.rand.nextGaussian();
                           Booom.z = (float)this.rand.nextGaussian();
                           Booom.power = 0.16F;
                        }
                     }
                  }
               }
            }

            IEntitySynchronize.sendSynchronize(this, 50.0, this.heating, 0.0, 0.0, 0.0, 0.0, 0.0);
         }
      } else {
         if (this.getHealth() <= this.getMaxHealth() * 0.4) {
            this.var1++;
         } else if (this.var1 > 0) {
            this.var1--;
         }

         if (this.summonning && this.ticksExisted % 2 == 0) {
            float scalen = this.rand.nextFloat() * 0.5F + 0.36F;
            GUNParticle part = new GUNParticle(
               spl,
               0.1F,
               -0.007F,
               30,
               150,
               this.world,
               this.posX + this.rand.nextGaussian(),
               this.summonY - this.rand.nextFloat() / 2.0F,
               this.posZ + this.rand.nextGaussian(),
               0.0F,
               0.0F,
               0.0F,
               1.0F - this.rand.nextFloat() * 0.3F,
               1.0F - this.rand.nextFloat() * 0.3F,
               1.0F - this.rand.nextFloat() * 0.3F,
               false,
               this.rand.nextInt(21) - 20
            );
            part.scaleTickAdding = scalen / 50.0F;
            this.world.spawnEntity(part);
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.IN_WALL) {
         return false;
      } else if (this.summonning) {
         return false;
      } else {
         boolean suc = super.attackEntityFrom(source, amount);
         if (!this.world.isRemote) {
            if (suc) {
               this.damagToSplit += amount;
               this.heating += (int)(amount * 2.0F);
               IEntitySynchronize.sendSynchronize(this, 50.0, this.heating, 0.0, 0.0, 0.0, 0.0, 0.0);
               if (this.getHealth() < this.getMaxHealth() * 0.5
                  && !this.lumped
                  && !this.noClip
                  && (!this.getEntityData().hasKey("needSpawnCannons2") || this.getEntityData().getBoolean("needSpawnCannons2"))) {
                  this.getEntityData().setBoolean("needSpawnCannons2", false);

                  for (int i = 0; i < 3; i++) {
                     ToxicomaniaMobsPack.AbominationCannon cannon = new ToxicomaniaMobsPack.AbominationCannon(this.world);
                     cannon.setPosition(this.posX, this.posY + 1.5, this.posZ);
                     cannon.boss = this;
                     cannon.typeCannon = i;
                     this.world.spawnEntity(cannon);
                     cannon.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(cannon)), (IEntityLivingData)null);
                     cannon.team = this.team;
                     cannon.isAgressive = this.isAgressive;
                  }
               }
            }

            if (this.damagToSplit > 300.0F && !this.lumped && this.getHealth() > this.getMaxHealth() * 0.5) {
               int maxlumps = 16 + this.getMobDifficulty() * 2;

               for (int i = 0; i < maxlumps; i++) {
                  ToxicomaniaMobsPack.Lump lump = new ToxicomaniaMobsPack.Lump(this.world);
                  lump.setPosition(
                     this.posX + this.rand.nextFloat() - 0.5,
                     this.posY + this.rand.nextFloat(),
                     this.posZ + this.rand.nextFloat() - 0.5
                  );
                  lump.motionX = this.rand.nextGaussian() / 13.0;
                  lump.motionY = this.rand.nextGaussian() / 17.0 + 0.4;
                  lump.motionZ = this.rand.nextGaussian() / 13.0;
                  this.world.spawnEntity(lump);
                  lump.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(lump)), (IEntityLivingData)null);
                  lump.team = this.team;
                  lump.isAgressive = this.isAgressive;
               }

               this.setSize(0.9F, 0.9F);
               this.world.setEntityState(this, (byte)8);
               this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
               this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
               this.lumped = true;
            }
         }

         return suc;
      }
   }

   public void unsplit() {
      this.damagToSplit = 0.0F;
      this.lumpKills = 0;
      this.setSize(3.5F, 3.5F);
      this.world.setEntityState(this, (byte)9);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
      this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.75);
      this.lumped = false;
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

   @SideOnly(Side.CLIENT)
   @Override
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8) {
         this.setSize(0.9F, 0.9F);
      }

      if (id == 9) {
         this.setSize(3.5F, 3.5F);
      }

      if (id == 10) {
         for (int ss = 0; ss < 3; ss++) {
            float fsize = (float)((3.3F + this.rand.nextGaussian() / 9.0) * 5.0);
            int lt = 10 + this.rand.nextInt(10);
            GUNParticle part = new GUNParticle(
               circle,
               -4.2F,
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
               0.65F,
               1.0F,
               0.65F + this.rand.nextFloat() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.025F;
            if (ss == 0) {
               part.dropped = true;
            } else {
               part.rotationPitchYaw = new Vec2f(this.rand.nextInt(60), this.rand.nextInt(60));
            }

            this.world.spawnEntity(part);
         }

         for (int ss = 0; ss < 65; ss++) {
            float fsize1 = 0.15F + this.rand.nextFloat() * 0.1F;
            int lt1 = 25 + this.rand.nextInt(20);
            GUNParticle part1 = new GUNParticle(
               tex,
               fsize1,
               0.004F * (float)this.rand.nextGaussian(),
               lt1,
               240,
               this.world,
               this.posX + this.rand.nextGaussian() * 1.5,
               this.posY + this.rand.nextFloat() * 3.0F,
               this.posZ + this.rand.nextGaussian() * 1.5,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F + 0.1F,
               (float)this.rand.nextGaussian() / 9.0F,
               0.4F,
               0.8F,
               1.0F,
               true,
               0
            );
            part1.scaleTickAdding = -fsize1 / lt1;
            part1.alphaGlowing = true;
            this.world.spawnEntity(part1);
         }
      }
   }

   protected void initEntityAI() {
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMoveThroughWalls(this, 30, 0.31F, 64.0F, 60, 50.0F));
      this.tasks.addTask(2, new EntityAIAABBAttack(this, 10, 0.6F));
      this.tasks.addTask(3, new EntityAIJumpingMovement(this, 50, 0.6F).setAnimationOnJump(-3));
      this.tasks.addTask(4, new EntityAIDash(this, 50, 80.0F, 4.0F, 0.8F, true, 0.6F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
   }

   public static class EntityAIMoveThroughWalls extends EntityAIBase {
      public AbstractMob entity;
      public int maxCooldown = 50;
      public float speed = 0.15F;
      public float maxDistSq = 400.0F;
      public float distanceToChaseSq = 400.0F;
      public int maxTimeWaitToExecute = 50;
      public int TimeWaitToExecute = 50;
      public boolean nowMoving = false;
      public Vec3d pos = null;
      public int cooldown = 0;
      public boolean throughBlocks = false;

      public EntityAIMoveThroughWalls(AbstractMob entity, int maxCooldown, float speed, float maxDist, int maxTimeWaitToExecute, float distanceToChase) {
         this.entity = entity;
         this.maxCooldown = maxCooldown;
         this.speed = speed;
         this.maxDistSq = maxDist * maxDist;
         this.maxTimeWaitToExecute = maxTimeWaitToExecute;
         this.distanceToChaseSq = distanceToChase * distanceToChase;
      }

      public EntityAIMoveThroughWalls(AbstractMob entity) {
         this.entity = entity;
      }

      public boolean shouldExecute() {
         return true;
      }

      public void updateTask() {
         EntityLivingBase attackTarg = this.entity.getAttackTarget();
         this.cooldown--;
         if (attackTarg != null && !this.nowMoving && this.cooldown <= 0) {
            if (this.TimeWaitToExecute >= this.maxTimeWaitToExecute) {
               this.pos = GetMOP.entityCenterPos(attackTarg);
               if (this.entity.getPositionVector().squareDistanceTo(this.pos) <= this.maxDistSq) {
                  this.nowMoving = true;
                  this.throughBlocks = !GetMOP.thereIsNoBlockCollidesBetween(
                     this.entity.world, GetMOP.entityCenterPos(this.entity), this.pos, 1.0F, null, false
                  );
               }
            } else if (!this.entity.getEntitySenses().canSee(attackTarg) || this.entity.getNavigator().noPath()) {
               this.TimeWaitToExecute++;
            }

            if (this.entity.getDistanceSq(attackTarg) > this.distanceToChaseSq) {
               this.TimeWaitToExecute = this.maxTimeWaitToExecute;
            }
         }

         if (this.nowMoving) {
            this.entity.noClip = true;
            this.entity.setNoGravity(true);
            SuperKnockback.setMove(this.entity, -this.speed, this.pos.x, this.pos.y, this.pos.z);
            if (this.entity.getPositionVector().squareDistanceTo(this.pos) <= 2.0) {
               if (this.entity.isNotColliding()) {
                  this.entity.noClip = false;
                  this.entity.setNoGravity(false);
                  this.nowMoving = false;
                  this.TimeWaitToExecute = 0;
                  this.pos = null;
                  this.cooldown = this.maxCooldown;
                  this.throughBlocks = false;
               } else {
                  this.nowMoving = false;
               }
            }
         }
      }
   }
}

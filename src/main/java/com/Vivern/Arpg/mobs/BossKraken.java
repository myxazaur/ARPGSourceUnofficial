//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.EntityPart;
import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.entity.IMultipartMob;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BossKraken extends AbstractBoss implements IEntitySynchronize, IMultipartMob {
   public static float motionMaxSpeed = 1.0F;
   public static float speedIncrease = 0.04F;
   public boolean move = false;
   public int timeInAir = 0;
   public double tentaclesAlignX = 0.0;
   public double tentaclesAlignY = 0.0;
   public double tentaclesAlignZ = 0.0;
   public double prevtentaclesAlignX = 0.0;
   public double prevtentaclesAlignY = 0.0;
   public double prevtentaclesAlignZ = 0.0;
   public int roarCooldown = 0;
   public static float hardnessBreaks = 35.0F;
   public boolean jawsOpened = true;
   public byte spawnTentacklesPhase1 = 1;
   public byte spawnTentacklesPhase2 = 0;
   public byte spawnTentacklesPhase3 = 0;
   public byte spawnTentacklesPhase4 = 0;
   public byte spawnTentacklesPhase5 = 0;
   public int timeEnemyNoInWater = 0;
   public boolean hasAngryTentacle = false;
   public EntityAIKrakenSwim aiRandSwim;

   public BossKraken(World world) {
      super(world, 8.0F, 8.0F, Color.BLUE);
      this.hurtSound = Sounds.mob_fish_hurt;
      this.deathSound = Sounds.mob_fish_death;
      this.defaultteam = AquaticaMobsPack.mobsteam;
      this.moveHelper = new AquaticaMobsPack.WaterMoveHelper(this);
      this.setattributes(4500.0, 100.0, 8.0, 0.04, 4.0, 5.0, 0.75, 0.3, 0.0, 0.0);
      this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.KRAKENSKIN, 1.0F, 0, 12, 15, 0)});
      this.ignoreFrustumCheck = true;
      if (world.isRemote) {
         this.var2 = 0.0F;
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
      }

      this.setDropMoney(300, 400, 1.0F);
      this.experienceValue = 900;
   }

   @Override
   public void dropShards() {
      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.ELECTRIC, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.VOID, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.9) {
         ShardType.spawnShards(ShardType.WATER, this, 12.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.POISON, this, 5.0F * dropShardsQuantity);
      }
   }

   @Override
   public BloodType getBloodType() {
      return DeathEffects.KRAKEN_BLOOD;
   }

   public void handleKrakenPhase() {
      float maxhealth = this.getMaxHealth();
      if (this.getHealth() < maxhealth * 0.8F && this.spawnTentacklesPhase2 == 0) {
         this.spawnTentacklesPhase2 = 1;
      }

      if (this.getHealth() < maxhealth * 0.6F && this.spawnTentacklesPhase3 == 0) {
         this.spawnTentacklesPhase3 = 1;
      }

      if (this.getHealth() < maxhealth * 0.4F && this.spawnTentacklesPhase4 == 0) {
         this.spawnTentacklesPhase4 = 1;
      }

      if (this.getHealth() < maxhealth * 0.2F && this.spawnTentacklesPhase5 == 0) {
         this.spawnTentacklesPhase5 = 1;
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean ret = super.attackEntityFrom(source, amount);
      this.handleKrakenPhase();
      return ret;
   }

   public void spawnTentacle(int type) {
      if (type == 0) {
         AquaticaMobsPack.KrakenTentacleBite mob = new AquaticaMobsPack.KrakenTentacleBite(this.world);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }

      if (type == 1) {
         AquaticaMobsPack.KrakenTentacleShock mob = new AquaticaMobsPack.KrakenTentacleShock(this.world);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }

      if (type == 2) {
         AquaticaMobsPack.KrakenTentacleMain mob = new AquaticaMobsPack.KrakenTentacleMain(this.world);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }

      if (type == 3) {
         AquaticaMobsPack.KrakenTentacleCrash mob = new AquaticaMobsPack.KrakenTentacleCrash(this.world);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }

      if (type == 4) {
         AquaticaMobsPack.KrakenTentacleGrab mob = new AquaticaMobsPack.KrakenTentacleGrab(this.world);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }

      if (type == 5) {
         AquaticaMobsPack.KrakenTentacleGrab mob = new AquaticaMobsPack.KrakenTentacleGrab(this.world, true);
         mob.setPosition(
            this.tentaclesAlignX + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignY + this.rand.nextGaussian() * 3.0,
            this.tentaclesAlignZ + this.rand.nextGaussian() * 3.0
         );
         this.world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.team = this.team;
         mob.isAgressive = this.isAgressive;
         mob.kraken = this;
      }
   }

   public Vec3d getTentaclesAlign(float partialTicks) {
      return new Vec3d(
         GetMOP.partial(this.tentaclesAlignX, this.prevtentaclesAlignX, (double)partialTicks),
         GetMOP.partial(this.tentaclesAlignY, this.prevtentaclesAlignY, (double)partialTicks),
         GetMOP.partial(this.tentaclesAlignZ, this.prevtentaclesAlignZ, (double)partialTicks)
      );
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRender3d(double x, double y, double z) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      return true;
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   protected boolean canDespawn() {
      return false;
   }

   public void breakBlocks() {
      for (BlockPos pos : GetMOP.getBlockPosesCollidesAABB(this.world, this.getEntityBoundingBox().grow(0.2), false)) {
         IBlockState blockState = this.world.getBlockState(pos);
         if (Weapons.easyBreakBlockFor(this.world, hardnessBreaks, pos, blockState)
            && !blockState.getMaterial().isLiquid()
            && blockState.getMaterial() != Material.AIR) {
            if (this.rand.nextFloat() < 0.08) {
               this.world.destroyBlock(pos, true);
               if (this.isAroundWater(this.world, pos)) {
                  this.world.setBlockState(pos, Blocks.WATER.getDefaultState());
               }
            } else if (this.isAroundWater(this.world, pos)) {
               this.world.setBlockState(pos, Blocks.WATER.getDefaultState());
            } else {
               this.world.setBlockToAir(pos);
            }
         }
      }
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      for (EnumFacing facing : EnumFacing.VALUES) {
         if (facing != EnumFacing.DOWN) {
            BlockPos poss = pos.offset(facing);
            IBlockState state2 = world.getBlockState(poss);
            if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void onUpdate() {
      int airr = this.getAir();
      super.onUpdate();
      Vec3d lookVec = GetMOP.PitchYawToVec3d(-this.rotationPitch, -this.rotationYaw);
      float lookScale = 3.5F;
      this.prevtentaclesAlignX = this.tentaclesAlignX;
      this.prevtentaclesAlignY = this.tentaclesAlignY;
      this.prevtentaclesAlignZ = this.tentaclesAlignZ;
      this.tentaclesAlignX = lookVec.x * lookScale + this.posX;
      this.tentaclesAlignY = lookVec.y * lookScale + this.posY + this.height / 2.0F;
      this.tentaclesAlignZ = lookVec.z * lookScale + this.posZ;
      if (this.world.isRemote) {
         this.var3 = this.var4;
         if (this.move) {
            this.var5 = (float)(this.var5 * 0.9);
            this.var4 = this.var4 + this.var5;
         } else if (this.var5 < motionMaxSpeed) {
            this.var5 = this.var5 + speedIncrease;
         }

         if (this.jawsOpened) {
            this.var2 = Math.max(-0.3886F, this.var2 - 0.038F);
         } else {
            this.var2 = Math.min(0.068F, this.var2 + 0.018F);
         }
      } else {
         if (this.ticksExisted % 15 == 0) {
            this.breakBlocks();
         }

         if (this.roarCooldown > 0) {
            this.roarCooldown--;
         } else if (this.rand.nextFloat() < 0.02F) {
            this.roarCooldown = 400 + this.rand.nextInt(150);
            this.world.setEntityState(this, (byte)7);
         }

         if (this.spawnTentacklesPhase1 == 1) {
            this.spawnTentacklesPhase1 = 2;
            this.spawnTentacle(2);
            this.spawnTentacle(0);
            this.spawnTentacle(0);
            this.spawnTentacle(0);
            this.spawnTentacle(3);
         }

         if (this.spawnTentacklesPhase2 == 1) {
            this.spawnTentacklesPhase2 = 2;
            this.spawnTentacle(2);
            this.spawnTentacle(0);
            this.spawnTentacle(4);
         }

         if (this.spawnTentacklesPhase3 == 1) {
            this.spawnTentacklesPhase3 = 2;
            this.spawnTentacle(3);
            this.spawnTentacle(3);
            this.spawnTentacle(0);
            this.spawnTentacle(0);
            this.spawnTentacle(4);
         }

         if (this.spawnTentacklesPhase4 == 1) {
            this.spawnTentacklesPhase4 = 2;
            this.spawnTentacle(1);
            this.spawnTentacle(1);
         }

         if (this.spawnTentacklesPhase5 == 1) {
            this.spawnTentacklesPhase5 = 2;
            this.spawnTentacle(0);
            this.spawnTentacle(1);
            this.spawnTentacle(2);
            this.spawnTentacle(3);
            this.spawnTentacle(4);
         }

         if (this.getAttackTarget() != null && !this.hasAngryTentacle && !this.getAttackTarget().isInWater()) {
            this.timeEnemyNoInWater++;
            if (this.timeEnemyNoInWater > 100) {
               this.spawnTentacle(5);
               this.hasAngryTentacle = true;
            }
         }
      }

      if (this.isEntityAlive() && !this.isInWater()) {
         this.timeInAir++;
         if (this.timeInAir > 20 && this.aiRandSwim != null) {
            this.timeInAir = 0;
            this.aiRandSwim.createNewVector();
            this.aiRandSwim.move = false;
            this.aiRandSwim.randomMotionSpeed = 0.0F;
         }

         this.setAir(--airr);
         if (this.getAir() == -40) {
            this.setAir(0);
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            if (this.getHealth() <= 0.0F) {
               this.loot = null;
            }
         }
      } else {
         this.setAir(300);
      }

      float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

      while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.02F;
      this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.02F;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   protected PathNavigate createNavigator(World worldIn) {
      return new PathNavigateSwimmer(this, worldIn);
   }

   public void travel(float strafe, float vertical, float forward) {
      if (this.isServerWorld() && this.isInWater()) {
         this.moveRelative(strafe, vertical, forward, 0.1F);
         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         this.motionX *= 0.9F;
         this.motionY *= 0.9F;
         this.motionZ *= 0.9F;
      } else {
         super.travel(strafe, vertical, forward);
      }
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(x * x + z * z);
         this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
         this.prevRotationYaw = this.rotationYaw;
         this.prevRotationPitch = this.rotationPitch;
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 29) {
         this.move = true;
      }

      if (id == 28) {
         this.move = false;
      }

      if (id == 7) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.kraken_roar,
               SoundCategory.HOSTILE,
               5.0F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id == 14) {
         this.jawsOpened = true;
      }

      if (id == 15) {
         this.jawsOpened = false;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.kraken_grab,
               SoundCategory.HOSTILE,
               2.0F,
               0.7F + this.rand.nextFloat() / 5.0F,
               false
            );
      }
   }

   protected void initEntityAI() {
      this.tasks
         .addTask(
            4, new EntityAIGrapBite(this, true, 80, 4.5F + Debugger.floats[0], 0.0F, 3.0F, 200, 15.0F, false, 8.0F + Debugger.floats[1], true, true, true)
         );
      this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.aiRandSwim = new EntityAIKrakenSwim(this, motionMaxSpeed, speedIncrease, 0.01F, false, 38.0, 260);
      this.aiRandSwim.motionSlowdown = 0.96F;
      this.aiRandSwim.advancedCollisionCheck = true;
      this.tasks.addTask(7, this.aiRandSwim);
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
   }

   @Override
   public boolean attackEntityFromPart(EntityPart part, DamageSource source, float damage) {
      return false;
   }

   @Override
   public EntityLivingBase getMob() {
      return this;
   }

   @Override
   public String getTeamString() {
      return this.team;
   }

   @Override
   public boolean isDead() {
      return !this.isEntityAlive();
   }

   public static class EntityAIKrakenSwim extends EntityAIBase {
      public AbstractMob entity;
      public float motionMaxSpeed;
      public float speedIncrease;
      public boolean sendToClients = true;
      public float directionChangeChance;
      boolean isIdle;
      public double maxDistFromTargetSq;
      public int timeFollow;
      public int maxtimeFollow;
      public float randomMotionVecX;
      public float randomMotionVecY;
      public float randomMotionVecZ;
      public float randomMotionSpeed;
      public boolean move = false;
      public float motionSlowdown = 0.9F;
      public boolean advancedCollisionCheck = false;

      public EntityAIKrakenSwim(
         AbstractMob entity,
         float motionMaxSpeed,
         float speedIncrease,
         float directionChangeChance,
         boolean isIdle,
         double maxDistFromTarget,
         int maxtimeFollow
      ) {
         this.entity = entity;
         this.motionMaxSpeed = motionMaxSpeed;
         this.speedIncrease = speedIncrease;
         this.directionChangeChance = directionChangeChance;
         this.isIdle = isIdle;
         this.maxDistFromTargetSq = maxDistFromTarget * maxDistFromTarget;
         this.maxtimeFollow = maxtimeFollow;
         this.createNewVector();
      }

      public boolean shouldExecute() {
         return this.entity.isInWater() && (!this.isIdle || this.entity.getAttackTarget() == null);
      }

      public boolean shouldContinueExecuting() {
         return this.entity.isInWater() && (!this.isIdle || this.entity.getAttackTarget() == null);
      }

      public void updateTask() {
         if (this.timeFollow > 0) {
            this.timeFollow--;
         } else if (this.entity.getAttackTarget() != null) {
            double distsq = this.entity.getDistanceSq(this.entity.getAttackTarget());
            if (distsq > this.maxDistFromTargetSq) {
               this.timeFollow = this.maxtimeFollow;
            }
         }

         if (this.move) {
            float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            this.entity.motionX = this.entity.motionX * 0.9 + this.randomMotionVecX * this.randomMotionSpeed * speed;
            this.entity.motionY = this.entity.motionY * 0.9 + this.randomMotionVecY * this.randomMotionSpeed * speed;
            this.entity.motionZ = this.entity.motionZ * 0.9 + this.randomMotionVecZ * this.randomMotionSpeed * speed;
            this.randomMotionSpeed = this.randomMotionSpeed * this.motionSlowdown;
            if (this.randomMotionSpeed < 0.05) {
               this.move = false;
               if (this.sendToClients) {
                  this.entity.world.setEntityState(this.entity, (byte)28);
               }
            }

            if (this.advancedCollisionCheck) {
               AxisAlignedBB aabb = this.entity.getEntityBoundingBox().offset(this.entity.motionX, this.entity.motionY, this.entity.motionZ);

               for (BlockPos poss : GetMOP.getBlockPosesCollidesAABB(this.entity.world, aabb, false)) {
                  IBlockState st = this.entity.world.getBlockState(poss);
                  if (st.getCollisionBoundingBox(this.entity.world, poss) != null || st.getMaterial() != Material.WATER) {
                     this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.2);
                     this.createNewVector();
                     break;
                  }
               }
            } else {
               BlockPos possx = new BlockPos(
                  this.entity.posX + this.entity.motionX,
                  this.entity.posY + this.entity.motionY,
                  this.entity.posZ + this.entity.motionZ
               );
               IBlockState st = this.entity.world.getBlockState(possx);
               if (st.getCollisionBoundingBox(this.entity.world, possx) != null || st.getMaterial() != Material.WATER) {
                  this.randomMotionSpeed = (float)(this.randomMotionSpeed * 0.2);
                  this.createNewVector();
               }
            }
         } else {
            float speedx = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            this.entity.motionX = this.entity.motionX * 0.9 + this.randomMotionVecX * 0.03 * speedx;
            this.entity.motionY = this.entity.motionY * 0.9 + this.randomMotionVecY * 0.03 * speedx;
            this.entity.motionZ = this.entity.motionZ * 0.9 + this.randomMotionVecZ * 0.03 * speedx;
            if (this.randomMotionSpeed < this.motionMaxSpeed) {
               this.randomMotionSpeed = this.randomMotionSpeed + this.speedIncrease;
               if (this.entity.getRNG().nextFloat() < this.directionChangeChance || this.timeFollow > 0) {
                  this.createNewVector();
               }
            } else {
               this.move = true;
               this.entity.world.setEntityState(this.entity, (byte)29);
            }
         }
      }

      public void createNewVector() {
         if (this.timeFollow > 0 && this.entity.getAttackTarget() != null) {
            Vec3d targ = GetMOP.entityCenterPos(this.entity.getAttackTarget()).subtract(GetMOP.entityCenterPos(this.entity)).normalize();
            this.randomMotionVecX = (float)targ.x;
            this.randomMotionVecY = (float)targ.y
               + (
                  this.entity.world.getBlockState(this.entity.getPosition().up(3)).getMaterial() == Material.AIR
                     ? -0.8F
                     : 0.0F
               );
            this.randomMotionVecZ = (float)targ.z;
         } else {
            Vec3d targ = new Vec3d(
               this.entity.getRNG().nextGaussian(), this.entity.getRNG().nextGaussian(), this.entity.getRNG().nextGaussian()
            );
            targ = targ.normalize();
            this.randomMotionVecX = (float)targ.x;
            this.randomMotionVecY = (float)targ.y
               + (
                  this.entity.world.getBlockState(this.entity.getPosition().up(3)).getMaterial() == Material.AIR
                     ? -0.8F
                     : 0.0F
               );
            this.randomMotionVecZ = (float)targ.z;
         }
      }
   }
}

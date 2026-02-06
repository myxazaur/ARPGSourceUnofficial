package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.elements.models.LaserModel;
import com.Vivern.Arpg.entity.EntityPart;
import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.entity.IMultipartMob;
import com.Vivern.Arpg.entity.LightningStrike;
import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BossOphanim extends AbstractBoss implements IEntitySynchronize, IMultipartMob {
   public static ResourceLocation generic_beam2 = new ResourceLocation("arpg:textures/generic_beam2.png");
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
   public float circlesRotation = 0.0F;
   public float circlesSpeed = 2.0F;
   public int circlesAttackCooldown = 40;
   public int circlesCharges = 5;
   public int circlesAttackTick = 0;
   public int laserCooldown = 0;
   public int laserTimer = 0;
   public int maxlazerTime = 100;
   public boolean isDashing = false;
   public int dashTime = 0;
   public static int dashTimeReady = 10;
   public static int dashTimeMax = 32;
   public Vec3d dashVector = Vec3d.ZERO;
   public int dashCooldown = 0;
   public int dashCooldownMax = 460;
   public float laserRotationPitch = 0.0F;
   public float laserRotationYaw = 0.0F;
   public float prevlaserPitch = 0.0F;
   public float prevlaserYaw = 0.0F;
   public boolean spawnGuards = true;
   public StormledgeMobsPack.OphanimGuard[] guards;
   public boolean spawnParts = true;
   public EntityPart[] partsCircle1;
   public EntityPart[] partsCircle2;
   public EntityPart[] partsCircle3;
   public EntityPart[] partsCircle4;
   public float[] circlesHealth = new float[]{1400.0F, 1200.0F, 1000.0F, 800.0F};
   public int[] circlesRedRender = new int[]{0, 0, 0, 0};
   public boolean hasShield = true;
   public float shieldHP = 400.0F;
   public float shieldHPMAX = 400.0F;
   public int lightningCooldown = 50;
   public int lightningsTime = 0;
   public int lightningsCount = 0;
   public int lightningsMax = 10;
   public Vec3d[] lightningsPoses;
   public Vec3d[] lightningsPosesTargets;
   public boolean circlesSpeedChanged = false;
   public boolean firstUpdate1 = true;
   public EntityAICorrupter ai;
   public EntityAIAABBAttack aiAABBattack;

   public int getlightningCooldownMax() {
      return 140 - this.getMobDifficulty() * 20;
   }

   public int getlaserCooldownMax() {
      return 220 - this.getMobDifficulty() * 20;
   }

   public int getcirclesAttackCooldownMax() {
      return 260 - this.getMobDifficulty() * 30;
   }

   public BossOphanim(World world) {
      super(world, 1.3F, 1.3F, Color.PINK);
      this.hurtSound = Sounds.mob_storm_hurt;
      this.deathSound = Sounds.mob_storm_death;
      this.defaultteam = StormledgeMobsPack.mobsteam;
      this.setNoGravity(true);
      this.setattributes(6000.0, 86.0, 24.0, 0.05, 4.0, 4.0, 0.85, 0.2, 0.0, 0.0);
      this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.SOLIDIFIEDLIGHTNING, 1.0F, 0, 8, 10, 2)});
      this.setDropMoney(400, 600, 1.0F);
      this.experienceValue = 1200;
   }

   @Override
   public boolean isLootNoGravity() {
      return true;
   }

   @Override
   public void dropShards() {
      if (this.rand.nextFloat() < 0.9) {
         ShardType.spawnShards(ShardType.ELECTRIC, this, 12.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.VOID, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.AIR, this, 5.0F * dropShardsQuantity);
      }

      if (this.rand.nextFloat() < 0.8) {
         ShardType.spawnShards(ShardType.FIRE, this, 5.0F * dropShardsQuantity);
      }
   }

   @Override
   public BloodType getBloodType() {
      return DeathEffects.WIND_BLOOD;
   }

   protected float getSoundPitch() {
      return 0.7F;
   }

   protected float getSoundVolume() {
      return 3.0F;
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setFloat("healthCircle1", this.circlesHealth[0]);
      compound.setFloat("healthCircle2", this.circlesHealth[1]);
      compound.setFloat("healthCircle3", this.circlesHealth[2]);
      compound.setFloat("healthCircle4", this.circlesHealth[3]);
      compound.setFloat("shieldHP", this.shieldHP);
      compound.setFloat("shieldHPMAX", this.shieldHPMAX);
      compound.setBoolean("hasShield", this.hasShield);
      super.writeEntityToNBT(compound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("healthCircle1")
         && compound.hasKey("healthCircle2")
         && compound.hasKey("healthCircle3")
         && compound.hasKey("healthCircle4")) {
         this.circlesHealth = new float[]{
            compound.getFloat("healthCircle1"),
            compound.getFloat("healthCircle2"),
            compound.getFloat("healthCircle3"),
            compound.getFloat("healthCircle4")
         };
      }

      if (compound.hasKey("shieldHP")) {
         this.shieldHP = compound.getFloat("shieldHP");
      }

      if (compound.hasKey("shieldHPMAX")) {
         this.shieldHPMAX = compound.getFloat("shieldHPMAX");
      }

      if (compound.hasKey("hasShield")) {
         this.hasShield = compound.getBoolean("hasShield");
      }

      super.readEntityFromNBT(compound);
   }

   @Override
   public void shoot() {
      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)7);
         this.laserTimer = this.maxlazerTime;
         this.laserCooldown = this.getlaserCooldownMax();
         this.laserRotationPitch = this.rotationPitch;
         this.laserRotationYaw = this.rotationYaw;
      }
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public void breakBlocks(int radius) {
      BlockPos poss = this.getPosition();

      for (BlockPos pos : GetMOP.getPosesInsideSphere(poss.getX(), poss.getY(), poss.getZ(), radius)) {
         if (Weapons.easyBreakBlockFor(this.world, 100.0F, pos)) {
            if (this.rand.nextFloat() < 0.2) {
               this.world.destroyBlock(pos, false);
            } else {
               this.world.setBlockToAir(pos);
            }
         }
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 2) {
         this.circlesRotation = (float)args[0];
         this.circlesSpeed = (float)args[1];
      }

      if (args.length == 3) {
         this.rotationPitch = (float)args[0];
         this.rotationYaw = (float)args[1];
         this.rotationYawHead = (float)args[1];
         this.laserRotationPitch = (float)args[0];
         this.laserRotationYaw = (float)args[1];
         this.laserTimer = (int)args[2];
      }

      if (args.length == 1) {
         this.circlesRedRender[MathHelper.clamp((int)args[0], 0, 3)] = 10;
      }

      if (args.length == 4) {
         this.circlesHealth = new float[]{(float)args[0], (float)args[1], (float)args[2], (float)args[3]};
      }
   }

   public void onUpdateLaserRotation(double targerX, double targerY, double targerZ) {
      float delta = 1.0F;
      double d0 = targerX - this.posX;
      double d1 = targerY - (this.posY + this.getEyeHeight());
      double d2 = targerZ - this.posZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(MathHelper.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0F;
      float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180.0 / Math.PI)));
      this.laserRotationPitch = this.updateRotationLaser(this.laserRotationPitch, f1, delta);
      this.laserRotationYaw = this.updateRotationLaser(this.laserRotationYaw, f, delta);
   }

   public float updateRotationLaser(float rotation, float increment, float delta) {
      float f = MathHelper.wrapDegrees(increment - rotation);
      if (f > delta) {
         f = delta;
      }

      if (f < -delta) {
         f = -delta;
      }

      return rotation + f;
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

   @Override
   public boolean attackEntityFromPart(EntityPart part, DamageSource source, float damage) {
      if (this.spawnParts) {
         return false;
      } else if (source == DamageSource.IN_WALL) {
         part.hurtResistantTime = 0;
         return false;
      } else {
         int num = MathHelper.clamp(part.var1 - 1, 0, 3);
         this.circlesHealth[num] = this.circlesHealth[num] - damage;
         if (damage > 0.0F) {
            this.playHurtSound(source);
            IEntitySynchronize.sendSynchronize(this, 128.0, num);
         }

         if (this.circlesHealth[num] <= 0.0F) {
            this.world.setEntityState(this, (byte)(100 + num));
            if (this.partsCircle1 != null && num == 0) {
               for (EntityPart partelem : this.partsCircle1) {
                  partelem.setDead();
               }

               this.partsCircle1 = null;
            }

            if (this.partsCircle2 != null && num == 1) {
               for (EntityPart partelem : this.partsCircle2) {
                  partelem.setDead();
               }

               this.partsCircle2 = null;
            }

            if (this.partsCircle3 != null && num == 2) {
               for (EntityPart partelem : this.partsCircle3) {
                  partelem.setDead();
               }

               this.partsCircle3 = null;
            }

            if (this.partsCircle4 != null && num == 3) {
               for (EntityPart partelem : this.partsCircle4) {
                  partelem.setDead();
               }

               this.partsCircle4 = null;
            }
         }

         if (this.aiAABBattack != null) {
            int cicle = 0;

            for (int i = 3; i >= 0; i--) {
               if (this.circlesHealth[i] > 0.0F) {
                  cicle = i + 1;
                  break;
               }
            }

            this.aiAABBattack.grow = cicle * 0.5F;
            if (cicle == 0) {
               this.setSize(1.6F, 1.6F);
               this.world.setEntityState(this, (byte)14);
            }
         }

         return true;
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.circlesHealth[0] > 0.0F || this.circlesHealth[1] > 0.0F || this.circlesHealth[2] > 0.0F || this.circlesHealth[3] > 0.0F) {
         amount *= 0.4F;
      }

      if (this.hasShield && this.shieldHP > 0.0F && amount > 0.0F) {
         this.shieldHP -= amount;
         amount = Math.max(amount - 50.0F, 0.0F);
         if (this.shieldHP <= 0.0F) {
            this.hasShield = false;
            this.shieldHP = 0.0F;
            this.world.setEntityState(this, (byte)11);
         } else {
            this.world.setEntityState(this, (byte)12);
         }
      }

      if (this.shieldHPMAX <= 0.0F) {
         this.world.setEntityState(this, (byte)13);
      }

      return super.attackEntityFrom(source, amount);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.world.isRemote) {
         for (int i = 0; i < 4; i++) {
            if (this.circlesRedRender[i] > 0) {
               this.circlesRedRender[i]--;
            }
         }

         if (this.firstUpdate1) {
            this.firstUpdate1 = false;
            Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundEntity(this, Sounds.ophanim, SoundCategory.HOSTILE, 0.8F, 1.0F, true));
         }
      }

      if (this.posY > 256.0) {
         this.setPosition(this.posX, 256.0, this.posZ);
         this.motionX *= 0.5;
         this.motionY *= 0.5;
         this.motionZ *= 0.5;
      }

      if (this.posY < 0.0) {
         this.setPosition(this.posX, 0.0, this.posZ);
         this.motionX *= 0.5;
         this.motionY *= 0.5;
         this.motionZ *= 0.5;
      }

      if (!this.world.isRemote) {
         for (EntityPlayer entityPlayer : this.world
            .getPlayers(EntityPlayer.class, player -> this.getDistanceSq(player) < 16384.0 && player.posY > 270.0)) {
            entityPlayer.motionY = entityPlayer.motionY - (entityPlayer.posY - 270.0) * 0.05F;
            entityPlayer.velocityChanged = true;
         }
      }

      this.circlesRotation = this.circlesRotation + this.circlesSpeed;
      this.prevlaserPitch = this.laserRotationPitch;
      this.prevlaserYaw = this.laserRotationYaw;
      if (this.isEntityAlive() && this.laserTimer > 0) {
         this.laserTimer--;
         if (this.laserTimer == this.maxlazerTime / 2) {
            this.circlesRotation = 0.0F;
         }

         if (this.getAttackTarget() != null) {
            this.onUpdateLaserRotation(
               this.getAttackTarget().posX,
               this.getAttackTarget().posY + this.getAttackTarget().height / 2.0F,
               this.getAttackTarget().posZ
            );
         }

         IEntitySynchronize.sendSynchronize(this, 128.0, this.laserRotationPitch, this.laserRotationYaw, this.laserTimer);
         if (this.laserTimer < 60) {
            Vec3d vec3d = this.getPositionEyes(1.0F);
            Vec3d vec3d1 = GetMOP.PitchYawToVec3d(this.laserRotationPitch, this.laserRotationYaw);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * 70.0, vec3d1.y * 70.0, vec3d1.z * 70.0);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
            if (raytraceresult != null) {
               vec3d2 = new Vec3d(
                  raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z
               );
            }

            List<Entity> list = GetMOP.findEntitiesOnPath(vec3d, vec3d2, this.world, this, 2.0, 2.0);
            if (this.world.isRemote) {
               this.spawnBetwParticle(vec3d, vec3d2, this.laserRotationPitch, this.laserRotationYaw, this.prevlaserPitch, this.prevlaserYaw);
            } else {
               boolean strong = this.getHealth() < this.getMaxHealth() * 0.4;

               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this, entity)) {
                     float size = GetMOP.getfromto((float)(this.maxlazerTime - this.laserTimer), 20.0F, 25.0F)
                        - GetMOP.getfromto((float)(this.maxlazerTime - this.laserTimer), 85.0F, 100.0F);
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, vec3d, WeaponDamage.laser),
                        strong ? size * 25.0F : size * 20.0F,
                        this,
                        entity,
                        true,
                        0.1F,
                        this.posX,
                        this.posY + this.getEyeHeight(),
                        this.posZ
                     );
                     if (strong) {
                        Weapons.setPotionIfEntityLB(entity, MobEffects.SLOWNESS, 50, 3);
                     }
                  }
               }
            }
         }
      }

      if (!this.world.isRemote) {
         if (this.ticksExisted < 3 || this.ticksExisted % 60 == 0 || this.circlesSpeedChanged) {
            IEntitySynchronize.sendSynchronize(this, 128.0, this.circlesRotation, this.circlesSpeed);
            this.circlesSpeedChanged = false;
         }

         if (this.circlesHealth != null && (this.ticksExisted >= 3 && this.ticksExisted < 6 || this.ticksExisted % 98 == 0)) {
            IEntitySynchronize.sendSynchronize(this, 128.0, this.circlesHealth[0], this.circlesHealth[1], this.circlesHealth[2], this.circlesHealth[3]);
         }

         this.handlePartsCircle();
         if (this.spawnGuards) {
            this.spawnGuards = false;
            this.guards = new StormledgeMobsPack.OphanimGuard[3];

            for (int ix = 0; ix < 3; ix++) {
               StormledgeMobsPack.OphanimGuard mob = new StormledgeMobsPack.OphanimGuard(this.world);
               mob.setPosition(this.posX, this.posY, this.posZ);
               this.world.spawnEntity(mob);
               mob.onInitialSpawn();
               mob.team = this.team;
               mob.isAgressive = this.isAgressive;
               mob.type = ix + 1;
               mob.despawnIfNoBoss = true;
               mob.boss = this;
               this.guards[ix] = mob;
            }
         }

         if (this.guards != null) {
            boolean hasLivingGuard = false;

            for (int ix = 0; ix < 3; ix++) {
               StormledgeMobsPack.OphanimGuard guard = this.guards[ix];
               if (guard.isEntityAlive()) {
                  if (guard.isAddedToWorld()) {
                     hasLivingGuard = true;
                  }

                  Vec3d posvec = this.getVectorForRotation(this.rotationPitch / 4.0F, this.renderYawOffset - (ix - 1) * 40)
                     .scale(6.0)
                     .add(this.getPositionVector());
                  double friction = 0.9;
                  guard.motionX *= friction;
                  guard.motionY *= friction;
                  guard.motionZ *= friction;
                  if (guard.timeFarFromBoss >= 55) {
                     guard.setPositionAndUpdate(posvec.x, posvec.y - 5.0, posvec.z);
                     guard.timeFarFromBoss = 0;
                  } else {
                     SuperKnockback.applyMove(guard, -0.3F, posvec.x, posvec.y - 5.0, posvec.z);
                  }

                  if (!guard.isShooting) {
                     guard.rotationPitch = this.rotationPitch / 2.0F;
                     guard.rotationYaw = this.renderYawOffset - (ix - 1) * 40;
                  }
               }
            }

            if (!hasLivingGuard) {
               if (this.shieldHPMAX > 0.0F) {
                  this.hasShield = false;
                  this.shieldHPMAX = 0.0F;
                  this.world.setEntityState(this, (byte)11);
               }

               if (this.lightningCooldown <= 0) {
                  this.lightningsTime++;
                  if (this.lightningsTime <= this.lightningsPoses.length * 5) {
                     if (this.lightningsTime % 5 == 0) {
                        int target = this.lightningsTime / 5;
                        if (target < this.lightningsPoses.length && target >= 0) {
                           HostileProjectiles.GustCloud proj = new HostileProjectiles.GustCloud(this.world, this);
                           Vec3d vec = this.lightningsPoses[target];
                           proj.setPosition(vec.x, vec.y, vec.z);
                           proj.damage = 20.0F;
                           this.world.spawnEntity(proj);
                        }
                     }
                  } else if (this.lightningsTime <= this.lightningsPoses.length * 10) {
                     if (this.lightningsTime % 5 == 0) {
                        int target = (this.lightningsTime - this.lightningsPoses.length * 5) / 5;
                        if (target < this.lightningsPoses.length && target >= 0) {
                           Vec3d vec = this.lightningsPoses[target];
                           LightningStrike proj = new LightningStrike(
                              this.world, vec.x, vec.y, vec.z, this, this.lightningsPosesTargets[target]
                           );
                           proj.setPosition(vec.x, vec.y, vec.z);
                           proj.damage = 23.0F;
                           this.world.spawnEntity(proj);
                        }
                     }
                  } else {
                     this.lightningCooldown = this.getlightningCooldownMax();
                  }
               } else {
                  this.lightningCooldown--;
                  if (this.lightningCooldown <= 0) {
                     this.lightningsCount = this.rand.nextInt(this.lightningsMax) + 1;
                     this.lightningsTime = 0;
                     this.lightningsPoses = new Vec3d[this.lightningsCount];
                     this.lightningsPosesTargets = new Vec3d[this.lightningsCount];
                     List<Entity> list = GetMOP.getEntitiesInAABBof(this.world, this, 20.0, this);

                     for (int ixx = 0; ixx < this.lightningsCount; ixx++) {
                        if (this.rand.nextFloat() < 0.4F && this.getAttackTarget() != null) {
                           this.lightningsPoses[ixx] = this.findLightningPosNearEntity(this.getAttackTarget(), this.rand.nextFloat() * 1.0F);
                           this.lightningsPosesTargets[ixx] = this.getAttackTarget().getPositionVector();
                        } else if (!list.isEmpty()) {
                           int randi = this.rand.nextInt(list.size());
                           Entity inlist = list.get(randi);
                           if (Team.checkIsOpponent(this, inlist)) {
                              this.lightningsPoses[ixx] = this.findLightningPosNearEntity(inlist, this.rand.nextFloat() * 1.5F);
                              this.lightningsPosesTargets[ixx] = inlist.getPositionVector();
                           } else {
                              this.lightningsPoses[ixx] = this.findLightningPosNearEntity(this, this.rand.nextFloat() * 13.5F);
                              this.lightningsPosesTargets[ixx] = this.lightningsPoses[ixx].add(0.0, -8.0, 0.0);
                           }
                        } else {
                           this.lightningsPoses[ixx] = this.findLightningPosNearEntity(this, this.rand.nextFloat() * 13.5F);
                           this.lightningsPosesTargets[ixx] = this.lightningsPoses[ixx].add(0.0, -8.0, 0.0);
                        }
                     }
                  }
               }
            } else {
               if (this.shieldHP > 15.0F && !this.hasShield) {
                  this.hasShield = true;
                  this.world.setEntityState(this, (byte)10);
               }

               if (this.shieldHP < this.shieldHPMAX) {
                  this.shieldHP += 2.0F;
               }
            }
         }

         if (this.laserCooldown <= 0) {
            if (this.ticksExisted % 11 == 0
               && (this.circlesAttackCooldown > 0 || this.circlesCharges <= 0)
               && !this.isDashing
               && this.getAttackTarget() != null
               && this.getEntitySenses().canSee(this.getAttackTarget())) {
               Vec3d vec = GetMOP.RotatedPosRayTrace(67.0, 1.0F, this, 3.5, 3.5, this.rotationPitch, this.rotationYaw);

               for (Entity entityx : GetMOP.getEntitiesInAABBof(this.world, vec, 2.7, this)) {
                  if (Team.checkIsOpponent(this, entityx)) {
                     this.shoot();
                     break;
                  }
               }
            }
         } else {
            this.laserCooldown--;
         }

         if (this.isDashing) {
            if (this.dashTime < dashTimeReady) {
               this.circlesSpeed += 3.0F;
               this.circlesSpeedChanged = true;
               this.motionX = this.motionX - this.dashVector.x * 0.3;
               this.motionY = this.motionY - this.dashVector.y * 0.3;
               this.motionZ = this.motionZ - this.dashVector.z * 0.3;
            } else if (this.dashTime >= dashTimeReady) {
               this.motionX = this.motionX + this.dashVector.x;
               this.motionY = this.motionY + this.dashVector.y;
               this.motionZ = this.motionZ + this.dashVector.z;
               this.breakBlocks(2);
            }

            this.dashTime++;
            if (this.dashTime >= dashTimeMax - 1) {
               this.motionX *= 0.4;
               this.motionY *= 0.4;
               this.motionZ *= 0.4;
            }

            if (this.dashTime >= dashTimeMax || this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) >= 1600.0) {
               this.motionX *= 0.4;
               this.motionY *= 0.4;
               this.motionZ *= 0.4;
               this.isDashing = false;
               this.noClip = false;
               this.circlesSpeed = 2.0F;
               this.circlesSpeedChanged = true;
               if (this.ai != null) {
                  this.ai.enable = true;
               }

               if (this.aiAABBattack != null) {
                  this.aiAABBattack.grow = 0.0F;
                  this.aiAABBattack.maxCooldown = 15;
               }
            }
         } else if (this.ticksExisted % 15 == 0) {
            this.breakBlocks(3);
         }

         if (this.dashCooldown <= 0) {
            if (this.getAttackTarget() != null
               && this.laserTimer <= 0
               && (this.circlesAttackCooldown > 0 || this.circlesCharges <= 0)
               && !this.isAIDisabled()
               && (!this.getEntitySenses().canSee(this.getAttackTarget()) || this.ticksExisted % 60 == 0)) {
               this.isDashing = true;
               this.dashVector = new Vec3d(
                     this.getAttackTarget().posX - this.posX,
                     this.getAttackTarget().posY - this.posY,
                     this.getAttackTarget().posZ - this.posZ
                  )
                  .normalize()
                  .scale(0.6);
               this.dashTime = 0;
               if (this.circlesHealth[0] <= 0.0F && this.circlesHealth[1] <= 0.0F && this.circlesHealth[2] <= 0.0F && this.circlesHealth[3] <= 0.0F) {
                  this.dashCooldown = (int)(this.dashCooldownMax * 0.65);
               } else {
                  this.dashCooldown = this.dashCooldownMax;
               }

               this.noClip = true;
               if (this.ai != null) {
                  this.ai.enable = false;
               }

               if (this.aiAABBattack != null) {
                  this.aiAABBattack.grow = 1.5F;
                  this.aiAABBattack.maxCooldown = 1;
               }

               this.world.setEntityState(this, (byte)9);
            }
         } else if (this.getAttackTarget() != null && !this.getEntitySenses().canSee(this.getAttackTarget())) {
            this.dashCooldown -= 2;
         } else {
            this.dashCooldown--;
         }

         if (this.circlesAttackCooldown <= 0) {
            if (this.circlesCharges > 0) {
               if (this.circlesAttackTick == 0) {
                  this.world.setEntityState(this, (byte)8);
               }

               this.circlesAttackTick++;
               if (this.circlesAttackTick == 3) {
                  this.triggerAnimation(-1);
               }

               if (this.circlesAttackTick >= 17 && !this.isAIDisabled()) {
                  int cicle = 0;

                  for (int ixxx = 3; ixxx >= 0; ixxx--) {
                     if (this.circlesHealth[ixxx] > 0.0F) {
                        cicle = ixxx + 1;
                        break;
                     }
                  }

                  if (cicle == 0) {
                     this.circlesAttackCooldown = this.getcirclesAttackCooldownMax();
                  } else {
                     this.circlesAttackTick = 0;
                     this.circlesCharges--;
                     HostileProjectiles.CircleBlast entityxx = new HostileProjectiles.CircleBlast(this.world, this);
                     entityxx.damage = 28 + this.getMobDifficulty() * 4;
                     entityxx.radius = 0.5F * cicle;
                     entityxx.lowRadius = entityxx.radius;
                     entityxx.knockback = 1.0F;
                     entityxx.maxradius = 64.0F;
                     entityxx.radiusIncreace = 1.3F;
                     entityxx.lowRadiusIncreace = 0.9F;
                     entityxx.rotateAngleYaw = this.renderYawOffset;
                     float lazerFromTo = 0.0F;
                     if (this.animations[1] > 0) {
                        float anim2 = 100.0F - this.animations[1];
                        lazerFromTo = GetMOP.getfromto(anim2, 0.0F, 20.0F) - GetMOP.getfromto(anim2, 85.0F, 100.0F);
                     }

                     Vec3d anglesc = anglesOfCircles(cicle, this.circlesRotation, lazerFromTo, this.laserRotationPitch, this.laserRotationYaw);
                     entityxx.rotateAngleX = (float)anglesc.x;
                     entityxx.rotateAngleY = (float)anglesc.y;
                     entityxx.rotateAngleZ = (float)anglesc.z;
                     entityxx.normalVector = GetMOP.getNormalForRotation(entityxx.rotateAngleX, entityxx.rotateAngleY, entityxx.rotateAngleZ);
                     this.world.spawnEntity(entityxx);
                  }
               }
            } else {
               this.circlesAttackCooldown = this.getcirclesAttackCooldownMax() + this.rand.nextInt(this.getcirclesAttackCooldownMax() / 2);
               this.circlesSpeed = 2.0F;
               this.circlesSpeedChanged = true;
               this.circlesAttackTick = 0;
            }
         } else if (this.laserTimer <= 0) {
            if (this.circlesAttackCooldown == 1) {
               if (this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) < 3136.0) {
                  this.circlesSpeed = 4.0F;
                  this.circlesSpeedChanged = true;
                  this.circlesAttackCooldown--;
               }
            } else {
               this.circlesAttackCooldown--;
               if (this.circlesAttackCooldown % 20 == 1) {
                  this.circlesCharges++;
               }
            }
         }
      }
   }

   public void handlePartsCircle() {
      int count4 = 16;
      int count3 = 12;
      int count2 = 9;
      int count1 = 7;
      float dist4 = 3.5F;
      float dist3 = 2.8F;
      float dist2 = 2.1F;
      float dist1 = 1.5F;
      float angle1 = 360 / count1;
      float angle2 = 360 / count2;
      float angle3 = 360 / count3;
      float angle4 = 360 / count4;
      if (this.spawnParts) {
         this.partsCircle1 = new EntityPart[count1];
         this.partsCircle2 = new EntityPart[count2];
         this.partsCircle3 = new EntityPart[count3];
         this.partsCircle4 = new EntityPart[count4];

         for (int i = 0; i < count1; i++) {
            this.partsCircle1[i] = new EntityPart(this.world, this, this.team, 0.8F, 0.8F);
            Vec3d vec = this.getPositionOfCircleElement(i, 1, count1, dist1);
            this.partsCircle1[i].setPositionAndUpdate(vec.x, vec.y, vec.z);
            this.partsCircle1[i].var1 = 1;
            this.world.spawnEntity(this.partsCircle1[i]);
         }

         for (int i = 0; i < count2; i++) {
            this.partsCircle2[i] = new EntityPart(this.world, this, this.team, 0.8F, 0.8F);
            Vec3d vec = this.getPositionOfCircleElement(i, 2, count2, dist2);
            this.partsCircle2[i].setPositionAndUpdate(vec.x, vec.y, vec.z);
            this.partsCircle2[i].var1 = 2;
            this.world.spawnEntity(this.partsCircle2[i]);
         }

         for (int i = 0; i < count3; i++) {
            this.partsCircle3[i] = new EntityPart(this.world, this, this.team, 0.8F, 0.8F);
            Vec3d vec = this.getPositionOfCircleElement(i, 3, count3, dist3);
            this.partsCircle3[i].setPositionAndUpdate(vec.x, vec.y, vec.z);
            this.partsCircle3[i].var1 = 3;
            this.world.spawnEntity(this.partsCircle3[i]);
         }

         for (int i = 0; i < count4; i++) {
            this.partsCircle4[i] = new EntityPart(this.world, this, this.team, 0.8F, 0.8F);
            Vec3d vec = this.getPositionOfCircleElement(i, 4, count4, dist4);
            this.partsCircle4[i].setPositionAndUpdate(vec.x, vec.y, vec.z);
            this.partsCircle4[i].var1 = 4;
            this.world.spawnEntity(this.partsCircle4[i]);
         }

         this.spawnParts = false;
      } else {
         if (this.partsCircle1 != null) {
            for (int i = 0; i < count1; i++) {
               Vec3d vec = this.getPositionOfCircleElement(i, 1, count1, dist1);
               this.partsCircle1[i].setPosition(vec.x, vec.y, vec.z);
            }
         }

         if (this.partsCircle2 != null) {
            for (int i = 0; i < count2; i++) {
               Vec3d vec = this.getPositionOfCircleElement(i, 2, count2, dist2);
               this.partsCircle2[i].setPosition(vec.x, vec.y, vec.z);
            }
         }

         if (this.partsCircle3 != null) {
            for (int i = 0; i < count3; i++) {
               Vec3d vec = this.getPositionOfCircleElement(i, 3, count3, dist3);
               this.partsCircle3[i].setPosition(vec.x, vec.y, vec.z);
            }
         }

         if (this.partsCircle4 != null) {
            for (int i = 0; i < count4; i++) {
               Vec3d vec = this.getPositionOfCircleElement(i, 4, count4, dist4);
               this.partsCircle4[i].setPosition(vec.x, vec.y, vec.z);
            }
         }
      }
   }

   public Vec3d getPositionOfCircleElement(int elem, int circle, int allcount, float dist) {
      float angle = 360.0F / allcount;
      float lazerFromTo = 0.0F;
      if (this.animations[1] > 0) {
         float anim2 = 100.0F - this.animations[1];
         lazerFromTo = GetMOP.getfromto(anim2, 0.0F, 20.0F) - GetMOP.getfromto(anim2, 85.0F, 100.0F);
      }

      Vec3d angles = anglesOfCircles(circle, this.circlesRotation, lazerFromTo, this.laserRotationPitch, this.laserRotationYaw);
      Vec3d normal = GetMOP.getNormalForRotation((float)angles.x, (float)angles.y, (float)angles.z);
      Vec3d base = getPointOnPlane(normal, 10.0, 210.0).normalize().scale(dist);
      return GetMOP.entityCenterPos(this).add(GetMOP.rotateVecAroundAxis(base, normal, angle * elem));
   }

   public static Vec3d getPointOnPlane(Vec3d normalVector, double freeX, double freeY) {
      double Z = -(normalVector.x * freeX + normalVector.y * freeY) / normalVector.z;
      return new Vec3d(freeX, freeY, Z);
   }

   public static Vec3d anglesOfCircles(int circle, float timer, float lazerFromTo, float laserPitch, float laserYaw) {
      float raX = 0.0F;
      float raY = 0.0F;
      float raZ = 0.0F;
      float noLazer = 1.0F - lazerFromTo;
      if (circle == 1) {
         raZ = MathHelper.wrapDegrees(timer * 0.6F) * (float) (Math.PI / 180.0) * noLazer;
         raX = MathHelper.sin(timer / 76.0F) * 0.5F * noLazer + lazerFromTo * (-1.4F - laserPitch * (float) (Math.PI / 180.0));
         raY = lazerFromTo * (laserYaw * (float) (Math.PI / 180.0));
      }

      if (circle == 2) {
         raX = MathHelper.wrapDegrees(timer * 0.5F) * (float) (Math.PI / 180.0) * noLazer + lazerFromTo * (0.5F - laserPitch * (float) (Math.PI / 180.0));
         raY = MathHelper.sin(timer / 57.0F) * 0.6F * noLazer + lazerFromTo * (laserYaw * (float) (Math.PI / 180.0));
      }

      if (circle == 3) {
         raY = MathHelper.wrapDegrees(timer * 0.4F) * (float) (Math.PI / 180.0) * noLazer + lazerFromTo * (laserYaw * (float) (Math.PI / 180.0));
         raX = MathHelper.cos(timer / 61.0F) * 0.7F * noLazer + lazerFromTo * (-1.0F - laserPitch * (float) (Math.PI / 180.0));
         raZ = MathHelper.sin(timer / 61.0F) * 0.7F * noLazer;
      }

      if (circle == 4) {
         raX = MathHelper.wrapDegrees(-timer * 0.65F) * (float) (Math.PI / 180.0) * noLazer + lazerFromTo * (1.0F - laserPitch * (float) (Math.PI / 180.0));
         raY = MathHelper.wrapDegrees(-timer * 0.2F) * (float) (Math.PI / 180.0) * noLazer + lazerFromTo * (laserYaw * (float) (Math.PI / 180.0));
      }

      return new Vec3d(raX, raY, raZ);
   }

   @SideOnly(Side.CLIENT)
   public void spawnBetwParticle(Vec3d from, Vec3d to, float pitch, float yaw, float prevpitch, float prevyaw) {
      if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
         if (this.ticksExisted % 2 == 0) {
            float soundsize = GetMOP.getfromto((float)(this.maxlazerTime - this.laserTimer), 80.0F, 100.0F);
            this.world
               .playSound(
                  (from.x + to.x) / 2.0,
                  (from.y + to.y) / 2.0,
                  (from.z + to.z) / 2.0,
                  Sounds.ophanim_beam,
                  SoundCategory.HOSTILE,
                  2.3F - soundsize * 1.5F,
                  1.0F - soundsize * 0.65F,
                  false
               );
         }

         float size = GetMOP.getfromto((float)(this.maxlazerTime - this.laserTimer), 20.0F, 25.0F)
            - GetMOP.getfromto((float)(this.maxlazerTime - this.laserTimer), 85.0F, 100.0F);
         EntityStreamLaserP laser = new EntityStreamLaserP(
            this.world, generic_beam2, size, 240, 1.0F, 1.0F, 1.0F, 0.15F, from.distanceTo(to), 3, 0.5F, 0.0F, pitch, yaw, this.ticksExisted
         );
         laser.setPosition(from.x, from.y, from.z);
         laser.ignoreFrustumCheck = true;
         laser.horizOffset = 0.0F;
         float[] color1 = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
         laser.model = new LaserModel.LaserModelLinear(generic_beam2, 240, 3, size, 0.0F, color1, color1, 0.0F, 3.0F, 1.6F, -0.07F);
         laser.model.next = new LaserModel.LaserModelCircles(
            new ResourceLocation("arpg:textures/circle2.png"), 240, 50.0, 12, 10, size + 0.1F, size * 0.9F, color1, color1
         );
         if (this.getHealth() < this.getMaxHealth() * 0.4) {
            laser.model.next.next = new LaserModel.LaserModelLightnings(null, 240, 20, 2, size + 0.4F, 0.06F, 0.5F, true, color1, color1);
         }

         laser.useModel = true;
         laser.prevRotationPitch = prevpitch;
         laser.prevRotationYaw = prevyaw;
         this.world.spawnEntity(laser);

         for (int ss = 0; ss < 3; ss++) {
            int lt = 25 + this.rand.nextInt(12);
            float scl = 0.25F + this.rand.nextFloat() * 0.2F;
            GUNParticle part = new GUNParticle(
               star2,
               scl,
               -0.009F,
               lt,
               240,
               this.world,
               to.x,
               to.y,
               to.z,
               (float)this.rand.nextGaussian() / 15.0F,
               (float)this.rand.nextGaussian() / 15.0F,
               (float)this.rand.nextGaussian() / 15.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            this.world.spawnEntity(part);
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

   public int getVerticalFaceSpeed() {
      return 2;
   }

   public int getHorizontalFaceSpeed() {
      return 2;
   }

   public Vec3d findLightningPosNearEntity(Entity entity, float radius) {
      int height = (int)(entity.posY + 20.0);

      for (int yy = (int)entity.posY; yy <= entity.posY + 20.0; yy++) {
         BlockPos pos = new BlockPos(entity.posX, yy, entity.posZ);
         if (this.world.getBlockState(pos).getCollisionBoundingBox(this.world, pos) == null
            && this.world.getBlockState(pos.up()).getCollisionBoundingBox(this.world, pos.up()) != null) {
            height = yy;
            break;
         }
      }

      return new Vec3d(
         entity.posX + (this.rand.nextFloat() - 0.5) * 2.0 * radius,
         height,
         entity.posZ + (this.rand.nextFloat() - 0.5) * 2.0 * radius
      );
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
               Sounds.ophanim_circle,
               SoundCategory.HOSTILE,
               3.7F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
      }

      if (id == 9) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.ophanim_dash,
               SoundCategory.HOSTILE,
               4.5F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
         this.triggerAnimation(-3);
      }

      if (id == 7) {
         this.laserTimer = this.maxlazerTime;
         this.triggerAnimation(-2);
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.ophanim_beam_start,
               SoundCategory.HOSTILE,
               3.8F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
      }

      if (id == 10) {
         this.hasShield = true;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.forcefield_trigger,
               SoundCategory.HOSTILE,
               1.4F,
               0.8F + this.rand.nextFloat() / 4.0F,
               false
            );
      }

      if (id == 11) {
         this.hasShield = false;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.forcefield_impact,
               SoundCategory.HOSTILE,
               1.9F,
               0.8F + this.rand.nextFloat() / 4.0F,
               false
            );
      }

      if (id == 12) {
         this.hasShield = true;
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.forcefield_impact,
               SoundCategory.HOSTILE,
               1.9F,
               0.8F + this.rand.nextFloat() / 4.0F,
               false
            );
      }

      if (id == 13) {
         this.hasShield = false;
      }

      if (id == 14) {
         this.setSize(1.6F, 1.6F);
      }

      if (id >= 100 && id <= 103) {
         int num = id - 100;
         this.circlesHealth[num] = 0.0F;
      }
   }

   protected void initEntityAI() {
      this.ai = new EntityAICorrupter(this, 180, 65.0F, 0, false, 20.0F, 8.0F);
      this.ai.deltaRotation = 90;
      this.aiAABBattack = new EntityAIAABBAttack(this, 15, 2.0F);
      this.tasks.addTask(1, this.ai);
      this.tasks.addTask(2, this.aiAABBattack);
      this.tasks.addTask(4, new EntityAICorrupterIdle(this));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
   }

   public class EntityAIOphanim extends EntityAIBase {
      public boolean enable = true;
      public final AbstractMob entity;
      public final float maxAttackDistancesq;
      public int seeTime;
      public boolean strafingClockwise;
      public boolean strafingBackwards;
      public int strafingTime = -1;
      public int inUse = 0;
      public boolean isHandActive = false;
      public int deltaRotation = 30;
      public float strafingDistanceSq = 0.0F;
      public float movingDirection = 0.0F;
      public float strafingAmplitude = 0.0F;
      public float dislocDirectionX = 0.0F;
      public float dislocDirectionY = 0.0F;
      public float dislocDirectionZ = 0.0F;
      public final Random rand;

      public EntityAIOphanim(AbstractMob mob, float maxAttackDistance, float strafingDistance, float strafingAmplitude) {
         this.entity = mob;
         this.rand = mob.getRNG();
         this.maxAttackDistancesq = maxAttackDistance * maxAttackDistance;
         this.strafingDistanceSq = strafingDistance * strafingDistance;
         this.strafingAmplitude = strafingAmplitude;
         this.dislocDirectionX = (float)(this.rand.nextGaussian() / 4.0);
         this.dislocDirectionY = (float)(this.rand.nextGaussian() / 4.0);
         this.dislocDirectionZ = (float)(this.rand.nextGaussian() / 4.0);
         this.setMutexBits(3);
      }

      public boolean shouldExecute() {
         return this.entity.getAttackTarget() != null && this.enable;
      }

      public boolean shouldContinueExecuting() {
         return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.enable;
      }

      public void startExecuting() {
         super.startExecuting();
      }

      public void resetTask() {
         super.resetTask();
         this.seeTime = 0;
         this.entity.resetActiveHand();
      }

      public void updateTask() {
         EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
         if (entitylivingbase != null) {
            double d0 = this.entity
               .getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
               this.seeTime = 0;
            }

            if (flag) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (d0 <= this.maxAttackDistancesq && this.seeTime >= 20) {
               this.entity.getNavigator().clearPath();
               this.strafingTime++;
            } else {
               this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
               if (this.entity.getRNG().nextFloat() < 0.3) {
                  this.strafingClockwise = !this.strafingClockwise;
               }

               if (this.entity.getRNG().nextFloat() < 0.3) {
                  this.strafingBackwards = !this.strafingBackwards;
               }

               this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
               if (d0 > this.maxAttackDistancesq * 0.75F) {
                  this.strafingBackwards = false;
               } else if (d0 < this.maxAttackDistancesq * 0.25F) {
                  this.strafingBackwards = true;
               }
            }

            double directDist = this.strafingDistanceSq - d0 - 6.0;
            if (this.rand.nextFloat() * this.strafingAmplitude < Math.abs(directDist)) {
               float flyspeed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
               this.movingDirection = this.rand.nextFloat() * flyspeed * (this.rand.nextGaussian() * this.strafingAmplitude > directDist ? -1 : 1);
            }

            SuperKnockback.applyMove(
               this.entity, this.movingDirection, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ
            );
            if (this.rand.nextFloat() < 0.07) {
               this.dislocDirectionX = (float)(this.rand.nextGaussian() / 4.0);
               this.dislocDirectionY = (float)(this.rand.nextGaussian() / 4.0);
               this.dislocDirectionZ = (float)(this.rand.nextGaussian() / 4.0);
            }

            SuperKnockback.applyMove(
               this.entity,
               this.movingDirection / 2.5F,
               this.entity.posX + this.dislocDirectionX,
               this.entity.posY + this.dislocDirectionY,
               this.entity.posZ + this.dislocDirectionZ
            );
            if (this.entity.posY < 0.0) {
               this.entity.motionY += 0.4;
            }
         }
      }
   }
}

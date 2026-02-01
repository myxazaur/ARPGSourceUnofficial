package com.vivern.arpg.entity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArrowTwin extends AbstractArrow implements IEntitySynchronize {
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/lightning4.png");
   public boolean isMain = false;
   public EntityArrowTwin twin;
   public boolean createTwin = false;
   public boolean active = false;
   public MovingSoundTwin soundArc;
   public int timeSparkling = 0;
   public Entity entityStucked;
   public double stuckedX;
   public double stuckedY;
   public double stuckedZ;
   public int ticksStucked = 0;
   public float stuckedRotPitch = 0.0F;
   public float stuckedRotYaw = 0.0F;
   public boolean stucked = false;
   public float allDamageLinkDealen = 0.0F;

   public EntityArrowTwin(World worldIn) {
      super(worldIn);
   }

   public EntityArrowTwin(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowTwin(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
      super.shoot(shooter, pitch, this.isMain ? yaw - 7.0F : yaw + 7.0F, p_184547_4_, velocity, inaccuracy);
      if (this.createTwin && !this.world.isRemote && this.pickupStatus == PickupStatus.ALLOWED) {
         this.createTwin = false;
         EntityArrowTwin arrow = new EntityArrowTwin(this.world, this.posX, this.posY, this.posZ);
         arrow.shootingEntity = this.shootingEntity;
         arrow.pickupStatus = PickupStatus.ALLOWED;
         arrow.shoot(shooter, pitch, yaw, p_184547_4_, velocity, inaccuracy);
         this.world.spawnEntity(arrow);
         this.twin = arrow;
         arrow.isTrueUnpickableArrow = true;
         this.isTrueUnpickableArrow = true;
         arrow.itemArrow = this.itemArrow;
      }
   }

   @Override
   public void setDamage(double damageIn) {
      super.setDamage(damageIn);
      if (this.twin != null && this.isMain) {
         this.twin.setDamage(damageIn);
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         Entity e = this.world.getEntityByID((int)args[0]);
         if (e != null && e instanceof EntityArrowTwin) {
            this.twin = (EntityArrowTwin)e;
         }

         this.isMain = true;
      }

      if (args.length == 6) {
         this.stuckedRotPitch = (float)args[0];
         this.stuckedRotYaw = (float)args[1];
         Entity e = this.world.getEntityByID((int)args[2]);
         if (e != null) {
            this.entityStucked = e;
            this.stucked = true;
            this.stuckedX = args[3];
            this.stuckedY = args[4];
            this.stuckedZ = args[5];
            this.livetimeAir = 450;
         }
      }
   }

   @Override
   public void onHit(RayTraceResult raytraceResultIn) {
      Entity entityhit = raytraceResultIn.entityHit;
      if (this.entityStucked == null || entityhit == null) {
         if (!this.world.isRemote
            && (this.shootingEntity == null || entityhit != this.shootingEntity || this.ticksExisted >= 10)
            && entityhit != null
            && entityhit.isEntityAlive()
            && (entityhit.width > 0.25 || entityhit.height > 0.25)
            && Weapons.canDealDamageTo(entityhit)
            && !(entityhit instanceof EntityArrow)) {
            this.entityStucked = entityhit;
            Vec3d vecstucked = GetMOP.getNearestPointInAABBtoPoint(this.getPositionVector(), entityhit.getEntityBoundingBox());
            float insertion = Math.min(Math.min(entityhit.width, entityhit.height), 0.2F);
            this.stuckedX = vecstucked.x - entityhit.posX;
            this.stuckedY = vecstucked.y - entityhit.posY;
            this.stuckedZ = vecstucked.z - entityhit.posZ;
            if (this.stuckedX > this.stuckedZ) {
               if (this.stuckedX > 0.0) {
                  this.stuckedX -= insertion;
               } else {
                  this.stuckedX += insertion;
               }
            } else if (this.stuckedZ > 0.0) {
               this.stuckedZ -= insertion;
            } else {
               this.stuckedZ += insertion;
            }

            if (this.stuckedY > entityhit.height * 0.9F) {
               this.stuckedY -= insertion;
            } else if (this.stuckedY < 0.1F) {
               this.stuckedY += insertion;
            }

            this.stuckedRotPitch = this.rotationPitch;
            this.stuckedRotYaw = this.rotationYaw;
            IEntitySynchronize.sendSynchronize(
               this, 64.0, this.rotationPitch, this.rotationYaw, entityhit.getEntityId(), this.stuckedX, this.stuckedY, this.stuckedZ
            );
            this.stucked = true;
            this.livetimeAir = 450;
         }

         super.onHit(raytraceResultIn);
         if (this.stucked) {
            this.setIsCritical(false);
         }
      }
   }

   @Override
   public void removeArrowEntity(Entity entityHitted) {
      if (entityHitted == null || this.entityStucked == null && entityHitted.isEntityAlive()) {
         super.removeArrowEntity(entityHitted);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.twin != null && this.twin.isDead) {
         this.twin = null;
      }

      if (this.entityStucked != null) {
         if (this.entityStucked.isEntityAlive()) {
            this.setPosition(
               this.entityStucked.posX + this.stuckedX,
               this.entityStucked.posY + this.stuckedY,
               this.entityStucked.posZ + this.stuckedZ
            );
            this.ticksStucked++;
         } else {
            this.stucked = false;
            this.entityStucked = null;
            this.ticksStucked = 0;
            this.world.setEntityState(this, (byte)11);
         }
      }

      if (this.stucked) {
         this.rotationPitch = this.stuckedRotPitch;
         this.rotationYaw = this.stuckedRotYaw;
         this.prevRotationPitch = this.stuckedRotPitch;
         this.prevRotationYaw = this.stuckedRotYaw;
      }

      if (this.isMain) {
         if (!this.world.isRemote) {
            if (this.twin != null) {
               if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
                  IEntitySynchronize.sendSynchronize(this, 64.0, this.twin.getEntityId());
               }

               if (this.ticksExisted % 5 == 0) {
                  double dist = this.getDistance(this.twin);
                  if (dist > 0.8 && dist < 8.0) {
                     if (!this.active && (this.timeInGround > 14 || this.ticksStucked > 14)) {
                        Vec3d point1 = this.getPositionVector().subtract(GetMOP.PitchYawToVec3d(-this.rotationPitch, -this.rotationYaw).scale(0.6));
                        Vec3d point2 = this.twin
                           .getPositionVector()
                           .subtract(GetMOP.PitchYawToVec3d(-this.twin.rotationPitch, -this.twin.rotationYaw).scale(0.6));
                        if (this.pickupStatus == PickupStatus.ALLOWED
                           && GetMOP.thereIsNoBlockCollidesBetween(this.world, point1, point2, 1.0F, null, false)) {
                           this.active = true;
                           this.world.setEntityState(this, (byte)9);
                           this.pickupStatus = PickupStatus.DISALLOWED;
                           this.twin.pickupStatus = PickupStatus.DISALLOWED;
                        }
                     }

                     if (this.active) {
                        Vec3d point1 = this.getPositionVector().subtract(GetMOP.PitchYawToVec3d(-this.rotationPitch, -this.rotationYaw).scale(0.6));
                        Vec3d point2 = this.twin
                           .getPositionVector()
                           .subtract(GetMOP.PitchYawToVec3d(-this.twin.rotationPitch, -this.twin.rotationYaw).scale(0.6));
                        if (GetMOP.thereIsNoBlockCollidesBetween(this.world, point1, point2, 1.0F, null, false)) {
                           List<Entity> list = GetMOP.findEntitiesOnPath(point1, point2, this.world, this.shootingEntity, 0.3, 0.2);
                           WeaponParameters parameters = WeaponParameters.getWeaponParameters(ItemsRegister.ARROWTWIN);
                           float damage_link = parameters.get("damage_link");
                           float damage_link_length_mult = parameters.get("damage_link_length_mult");

                           for (Entity entity : list) {
                              if (Team.checkIsOpponent(this.shootingEntity, entity)) {
                                 float damageDealen = damage_link + (float)dist * damage_link_length_mult;
                                 Weapons.dealDamage(
                                    new WeaponDamage(null, this.shootingEntity, this, false, false, point1, WeaponDamage.electric),
                                    damageDealen,
                                    this.shootingEntity,
                                    entity,
                                    true
                                 );
                                 entity.hurtResistantTime = 0;
                                 this.allDamageLinkDealen += damageDealen;
                                 if (this.allDamageLinkDealen > parameters.get("damage_link_max")) {
                                    this.active = false;
                                 }
                              }
                           }

                           this.timeSparkling += 5;
                           if (this.timeSparkling > 400) {
                              this.world.setEntityState(this, (byte)10);
                              this.setDead();
                              this.twin.setDead();
                           }
                        } else {
                           this.active = false;
                           this.world.setEntityState(this, (byte)10);
                        }
                     }
                  } else if (this.active) {
                     this.active = false;
                     this.world.setEntityState(this, (byte)10);
                  }
               }
            }
         } else if (this.active && this.twin != null) {
            Vec3d point1 = this.getPositionVector().subtract(GetMOP.PitchYawToVec3d(-this.rotationPitch, -this.rotationYaw).scale(0.6));
            Vec3d point2 = this.twin
               .getPositionVector()
               .subtract(GetMOP.PitchYawToVec3d(-this.twin.rotationPitch, -this.twin.rotationYaw).scale(0.6));
            this.doArcParticle(point1, point2);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void doArcParticle(Vec3d pos1, Vec3d pos2) {
      double dist = pos1.distanceTo(pos2);
      BetweenParticle laser = new BetweenParticle(
         this.world,
         texture,
         0.19F + this.rand.nextFloat() * 0.1F - (float)(dist * 0.02),
         240,
         0.9F,
         1.0F,
         1.0F,
         0.0F,
         dist,
         1,
         (this.rand.nextFloat() - 0.5F) * 0.1F,
         this.rand.nextFloat() - 0.5F + 0.8F,
         pos1,
         pos2
      );
      laser.alphaGlowing = true;
      laser.setPosition(pos1.x, pos1.y, pos1.z);
      laser.ignoreFrustumCheck = true;
      laser.offset = this.rand.nextFloat() * 10.0F;
      this.world.spawnEntity(laser);
   }

   @Override
   public SoundEvent getHitSound() {
      return this.ticksStucked < 2 ? Sounds.arrow_mithril : null;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWTWIN);
   }

   public void onCollideWithPlayer(EntityPlayer entityIn) {
      if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
         boolean flag = this.pickupStatus == PickupStatus.ALLOWED || this.pickupStatus == PickupStatus.CREATIVE_ONLY && entityIn.capabilities.isCreativeMode;
         if (flag
            && this.twin != null
            && this.twin.isEntityAlive()
            && this.getDistanceSq(this.twin) <= 3.0
            && entityIn.inventory.addItemStackToInventory(this.getArrowStack())) {
            entityIn.onItemPickup(this, 1);
            this.setDead();
            entityIn.onItemPickup(this.twin, 1);
            this.twin.setDead();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         this.active = true;
         if (this.isMain && this.twin != null) {
            this.soundArc = new MovingSoundTwin(this, this.twin, Sounds.arrow_twin_arc, this.getSoundCategory(), 1.0F, 1.0F, false);
            Minecraft.getMinecraft().getSoundHandler().playSound(this.soundArc);
         }
      } else if (id == 10) {
         this.active = false;
         if (this.soundArc != null) {
            this.soundArc.stop = true;
            this.soundArc = null;
         }
      } else if (id == 11) {
         this.stucked = false;
         this.ticksStucked = 0;
      } else {
         super.handleStatusUpdate(id);
      }
   }

   @SideOnly(Side.CLIENT)
   public static class MovingSoundTwin extends MovingSound {
      public final Entity entity1;
      public final Entity entity2;
      public boolean stop = false;

      public MovingSoundTwin(Entity entity1, Entity entity2, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean repeat) {
         super(sound, category);
         this.entity1 = entity1;
         this.entity2 = entity2;
         this.repeat = repeat;
         this.volume = volume;
         this.pitch = pitch;
      }

      public void update() {
         if (!this.entity1.isDead && !this.entity2.isDead && !this.stop) {
            this.xPosF = (float)(this.entity1.posX + (this.entity2.posX - this.entity1.posX) / 2.0);
            this.yPosF = (float)(this.entity1.posY + (this.entity2.posY - this.entity1.posY) / 2.0);
            this.zPosF = (float)(this.entity1.posZ + (this.entity2.posZ - this.entity1.posZ) / 2.0);
         } else {
            this.donePlaying = true;
         }
      }
   }
}

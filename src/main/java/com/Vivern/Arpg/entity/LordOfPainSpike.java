//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LordOfPainSpike extends EntityThrowable implements INailer {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int delayBeforeGrow = 0;
   public double maxSize = 1.0;
   public boolean damageDealed = true;
   public double size = 0.0;
   public boolean growed = false;
   public Entity prickedEntity = null;
   public int detachtime = 0;
   public int rotation = 0;
   public boolean sound = false;

   public LordOfPainSpike(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.8F, 0.8F);
      this.rotation = this.rand.nextInt(360);
      this.ignoreFrustumCheck = true;
   }

   public LordOfPainSpike(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.8F, 0.8F);
      this.rotation = this.rand.nextInt(360);
      this.ignoreFrustumCheck = true;
   }

   public LordOfPainSpike(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.LAVADROPPER);
      this.setSize(0.8F, 0.8F);
      this.rotation = this.rand.nextInt(360);
      this.ignoreFrustumCheck = true;
   }

   public LordOfPainSpike(World world, EntityLivingBase thrower, ItemStack itemstack, float power, int delayBeforeGrow, double maxSize) {
      super(world, thrower);
      this.setSize(0.8F, 0.8F);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.delayBeforeGrow = delayBeforeGrow;
      this.maxSize = maxSize;
      this.damageDealed = false;
      this.rotation = this.rand.nextInt(360);
      this.ignoreFrustumCheck = true;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionX *= 0.8;
      this.motionY *= 0.8;
      this.motionZ *= 0.8;
      if (!this.world.isRemote) {
         if (this.ticksExisted > this.delayBeforeGrow) {
            if (!this.sound) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.spike_a,
                     SoundCategory.AMBIENT,
                     0.9F,
                     1.9F - (float)this.maxSize + this.rand.nextFloat() / 5.0F
                  );
               this.sound = true;
            }

            if (this.size < this.maxSize && !this.growed) {
               this.size += 0.105;
               this.motionY = 0.48;
               this.world.setEntityState(this, (byte)5);
            } else {
               this.growed = true;
            }

            if (this.growed) {
               this.size -= 0.005;
               this.motionY = -0.05;
               this.world.setEntityState(this, (byte)1);
            }
         }

         if (this.ticksExisted > 100 + this.delayBeforeGrow) {
            this.setDead();
         }
      }

      if (this.prickedEntity != null) {
         double dist = this.getDistance(this.prickedEntity);
         if (this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox()).contains(this.prickedEntity)) {
            this.prickedEntity.motionX *= 0.8;
            this.prickedEntity.motionY *= 0.8;
            this.prickedEntity.motionZ *= 0.8;
            SuperKnockback.applyKnockback(this.prickedEntity, -0.5F, this.posX, this.posY, this.posZ);
            if (this.detachtime > 0) {
               this.detachtime--;
            }
         } else {
            this.detachtime++;
         }

         if (dist > this.prickedEntity.height * 3.0F || this.detachtime > 20) {
            this.prickedEntity = null;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.size += 0.105;
      }

      if (id == 1) {
         this.size -= 0.005;
      }
   }

   @Override
   public void setNailedEntity(Entity entity) {
      this.prickedEntity = entity;
   }

   @Override
   public Entity getNailedEntity() {
      return this.prickedEntity;
   }

   @Override
   public boolean isprickedToWall() {
      return false;
   }

   @Override
   public boolean canPrickParticle() {
      return true;
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null && !this.damageDealed && Team.checkIsOpponent(this.thrower, result.entityHit)) {
         this.damageDealed = true;
         if (!this.world.isRemote) {
            double damageRadius = 0.7;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (!(entity instanceof LordOfPainSpike) && Team.checkIsOpponent(this.thrower, entity)) {
                     Weapons.dealDamage(
                        null,
                        (10.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) * 2.0F) * this.magicPower,
                        this.thrower,
                        entity,
                        true,
                        1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
                        this.posX,
                        this.posY - 10.0,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                     boolean apricked = NailGunShoot.isEntityPricked(this.world, entity);
                     boolean healthmin = true;
                     if (entity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                        healthmin = entitylivingbase.getHealth() <= 25.0F;
                        if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.75 && entitylivingbase.deathTime < 1) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                        }
                     }

                     if (healthmin && !apricked && this.prickedEntity == null) {
                        this.prickedEntity = entity;
                     }
                  }
               }
            }
         }
      }
   }
}

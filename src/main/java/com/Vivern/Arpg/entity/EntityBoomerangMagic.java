package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBoomerangMagic extends StandardBullet {
   public static ResourceLocation resoue = new ResourceLocation("arpg:textures/spellblue.png");
   public final ItemStack weaponstack;
   public float damage = 7.0F;
   public float knockback = 0.0F;
   public float magicPower = 1.0F;
   public boolean shouldReturn = false;

   public EntityBoomerangMagic(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICBOOMERANG);
   }

   public EntityBoomerangMagic(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICBOOMERANG);
   }

   public EntityBoomerangMagic(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.MAGICBOOMERANG);
   }

   public EntityBoomerangMagic(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public float getGravityVelocity() {
      return 0.0F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 340) {
         this.setDead();
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.ticksExisted % 4 == 0) {
         this.world.setEntityState(this, (byte)2);
      }

      Entity target = this.getThrower();
      if (this.ticksExisted < 100) {
         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      }

      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      float flyspeed = parameters.getEnchanted("return_speed", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack));
      if (target != null
         && (
            this.ticksExisted > parameters.getEnchantedi("ticks_flying", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack)) / flyspeed
               || this.shouldReturn
         )) {
         float dist = target.getDistance(this);
         float prunex = (float)((target.posX - this.posX) / dist / 2.0);
         float pruney = (float)((target.posY + 1.0 - this.posY) / dist / 2.0);
         float prunez = (float)((target.posZ - this.posZ) / dist / 2.0);
         this.setVelocity(prunex * flyspeed, pruney * flyspeed, prunez * flyspeed);
         double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         int cooldownToSet = parameters.getEnchantedi("cooldown", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase == this.getThrower() && this.getThrower() instanceof EntityPlayer) {
                  EntityPlayer player = (EntityPlayer)this.getThrower();
                  player.getCooldownTracker().setCooldown(ItemsRegister.MAGICBOOMERANG, cooldownToSet);
                  this.setDead();
                  break;
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.hitmagic, SoundCategory.AMBIENT, 0.7F, 1.5F, false);

         for (int ss = 0; ss < 7; ss++) {
            GUNParticle spelll = new GUNParticle(
               resoue,
               0.1F,
               0.0F,
               16,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               0.7F + (float)this.rand.nextGaussian() / 4.0F,
               0.7F + (float)this.rand.nextGaussian() / 4.0F,
               1.0F,
               false,
               0
            );
            spelll.scaleTickAdding = -0.00625F;
            this.world.spawnEntity(spelll);
         }
      }

      if (id == 2) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.swosh_a, SoundCategory.AMBIENT, 0.5F, 1.5F, false);
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (!this.world.isRemote && Team.checkIsOpponent(this.thrower, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
            Weapons.dealDamage(null, this.damage * this.magicPower, this.thrower, result.entityHit, false, this.knockback);
            result.entityHit.hurtResistantTime = 5;
         }

         this.shouldReturn = true;
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
      }
   }
}

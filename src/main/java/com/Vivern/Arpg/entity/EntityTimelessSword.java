package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTimelessSword extends Entity implements IEntitySynchronize {
   public EntityPlayer player;
   public ItemStack weaponstack;
   public Vec3d to;
   public float damage;
   public boolean crit;
   public float knockback;
   public int randomDelay;
   public boolean activated = false;
   public float scale = 1.5F + this.rand.nextFloat() / 4.0F;
   public float yaw = 0.0F;
   public float pitch = 0.0F;
   public float rotation = 0.0F;
   public float Red = 1.0F;
   public float Green = 1.0F;
   public float Blue = 1.0F;
   public float alpha = 0.0F;

   public EntityTimelessSword(World worldIn) {
      super(worldIn);
      this.weaponstack = new ItemStack(ItemsRegister.TIMELESSSWORD);
      this.setSize(0.1F, 0.1F);
      this.ignoreFrustumCheck = true;
   }

   public EntityTimelessSword(
      World worldIn, ItemStack weaponstack, EntityPlayer player, Vec3d to, float damage, boolean crit, float knockback, float yaw, float pitch, float rotation
   ) {
      super(worldIn);
      this.weaponstack = weaponstack;
      this.player = player;
      this.to = to;
      this.damage = damage;
      this.crit = crit;
      this.knockback = knockback;
      this.randomDelay = this.rand.nextInt(9 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, weaponstack) * 3);
      this.setSize(0.1F, 0.1F);
      this.yaw = yaw;
      this.pitch = pitch;
      this.rotation = rotation;
      this.ignoreFrustumCheck = true;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return distance < d0 * d0;
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      this.yaw = (float)x;
      this.pitch = (float)y;
      this.rotation = (float)z;
      this.to = new Vec3d(a, b, c);
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.to != null && this.ticksExisted <= 1) {
         IEntitySynchronize.sendSynchronize(
            this, 64.0, this.yaw, this.pitch, this.rotation, this.to.x, this.to.y, this.to.z
         );
      }

      if (this.activated) {
         this.randomDelay--;
         if (!this.isDead && this.randomDelay <= 0) {
            this.activate();
         }
      }

      if (this.ticksExisted > 2400) {
         this.setDead();
      }

      if (this.world.isRemote) {
         float rv = (float)Math.sin(this.ticksExisted / 5.0F);
         this.Red = 0.75F + rv / 4.0F;
         this.Green = 0.66F - rv / 3.0F;
         this.Blue = 0.62F - rv * 0.13F;
         this.alpha = Math.min(this.ticksExisted, 20) / 40.0F + 0.25F + (float)Math.sin(this.ticksExisted / 7.0F + 5.0F) / 4.0F;
      }
   }

   public void activate() {
      if (this.weaponstack.getItem() instanceof IWeapon) {
         boolean reuse = this.rand.nextFloat() < EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack) * 0.3F;
         boolean dead = true;
         if (this.player != null) {
            dead = sweep(
                  (IWeapon)this.weaponstack.getItem(),
                  new Vec3d(this.posX, this.posY, this.posZ),
                  this.to,
                  this.player,
                  this.weaponstack,
                  false,
                  this.rand,
                  this.damage,
                  this.crit,
                  this.knockback,
                  this.yaw,
                  this.pitch,
                  this.rotation
               )
               || !reuse;
         }

         if (dead) {
            this.setDead();
         } else {
            this.activated = false;
         }
      } else {
         this.setDead();
      }
   }

   public static boolean sweep(
      IWeapon iweapon,
      Vec3d from,
      Vec3d to,
      final EntityPlayer player,
      ItemStack stack,
      boolean fromSword,
      Random rand,
      float damage,
      boolean crit,
      float knockback,
      float yaw,
      float pitch,
      float rotation
   ) {
      Predicate<Entity> filterEntityToIgnore = new Predicate<Entity>() {
         public boolean apply(Entity input) {
            return !Team.checkIsOpponent(input, player);
         }
      };
      World world = player.world;
      float sweepingAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, stack) / 5.0F;
      Vec3d vec = GetMOP.logicRayTrace(world, from, to, 1.0F, filterEntityToIgnore, 0.9 + sweepingAdd, 0.4, false);
      double damageRadius = 1.0 + sweepingAdd;
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - damageRadius,
         vec.y - damageRadius,
         vec.z - damageRadius,
         vec.x + damageRadius,
         vec.y + damageRadius,
         vec.z + damageRadius
      );
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, aabb);
      boolean ret = false;
      if (!list.isEmpty()) {
         world.playSound(
            (EntityPlayer)null,
            vec.x,
            vec.y,
            vec.z,
            Sounds.melee_magic,
            SoundCategory.PLAYERS,
            0.7F,
            0.9F + rand.nextFloat() / 5.0F
         );
         if (crit) {
            world.playSound(
               (EntityPlayer)null,
               vec.x,
               vec.y,
               vec.z,
               SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
               SoundCategory.PLAYERS,
               0.7F,
               0.9F + rand.nextFloat() / 5.0F
            );
         }

         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
         if (knockback > 0.0F) {
            entityAttributek.applyModifier(modifierk);
         }

         for (Entity entity : list) {
            if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity)) {
               if (iweapon.attackEntityMelee(entity, stack, player, EnumHand.MAIN_HAND, crit)) {
                  ret = true;
               }

               if (entity instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                  if (entitylivingbase.getHealth() <= 0.0F) {
                     if (rand.nextFloat() < 0.2) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_CUT);
                     } else if (rand.nextFloat() < 0.2) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  }
               }
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0.0F) {
            entityAttributek.removeModifier(modifierk);
         }
      } else {
         world.playSound(
            (EntityPlayer)null,
            vec.x,
            vec.y,
            vec.z,
            Sounds.melee_miss_magic,
            SoundCategory.PLAYERS,
            0.6F,
            0.9F + rand.nextFloat() / 5.0F
         );
      }

      IWeapon.fireEffect(
         ItemsRegister.TIMELESSSWORD,
         player,
         world,
         64.0,
         vec.x,
         vec.y,
         vec.z,
         (double)rotation,
         (double)pitch,
         (double)yaw,
         0.0,
         0.0,
         0.0
      );
      return ret;
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}

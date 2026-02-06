package com.Vivern.Arpg.main;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class WeaponDamage extends EntityDamageSource {
   public static String explode = "explode";
   public static String blade = "blade";
   public static String heavymelee = "heavymelee";
   public static String bullet = "bullet";
   public static String fire = "fire";
   public static String soul = "soul";
   public static String frost = "frost";
   public static String toxin = "toxin";
   public static String acid = "acid";
   public static String radiation = "radiation";
   public static String magic = "magic";
   public static String portal = "portal";
   public static String dismember = "dismember";
   public static String plasma = "plasma";
   public static String electric = "electric";
   public static String laser = "laser";
   public static String bite = "bite";
   public static String water = "water";
   public static String pierce = "pierce";
   public static String shards = "shards";
   public static String sand = "sand";
   public static String vines = "vines";
   public static String drill = "drill";
   @Nullable
   public Entity entityProjectile;
   public boolean shouldRewrite = false;
   public boolean areaOfEffect;
   public ItemStack weapon = ItemStack.EMPTY;
   @Nullable
   public Vec3d damagePosition;
   public static WeaponDamage RADIATION = new WeaponDamage(null, null, null, false, false, null, radiation);

   @Deprecated
   private WeaponDamage(@Nullable Entity entityDamager, @Nullable Entity entityProjectile, boolean explosion, boolean areaOfEffect, String type) {
      super(type, entityDamager);
      this.entityProjectile = entityProjectile;
      this.areaOfEffect = areaOfEffect;
      if (explosion) {
         this.setExplosion();
      }
   }

   public WeaponDamage(
      @Nullable ItemStack weapon,
      @Nullable Entity entityDamager,
      @Nullable Entity entityProjectile,
      boolean explosion,
      boolean projectile,
      @Nullable Object damagePosition,
      String type
   ) {
      this(entityDamager, entityProjectile, explosion, damagePosition == null, type);
      this.weapon = weapon == null ? ItemStack.EMPTY : weapon;
      if (projectile) {
         this.setProjectile();
      }

      if (damagePosition != null) {
         if (damagePosition instanceof Vec3d) {
            this.damagePosition = (Vec3d)damagePosition;
         } else if (damagePosition instanceof Entity) {
            this.damagePosition = GetMOP.entityCenterPos((Entity)damagePosition);
         } else if (damagePosition instanceof BlockPos) {
            BlockPos blockPos = (BlockPos)damagePosition;
            this.damagePosition = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
         } else if (damagePosition instanceof RayTraceResult) {
            RayTraceResult rayTraceResult = (RayTraceResult)damagePosition;
            if (rayTraceResult.hitVec != null) {
               this.damagePosition = rayTraceResult.hitVec;
            }
         }
      }
   }

   @Deprecated
   private WeaponDamage(@Nullable Entity entityDamager, @Nullable Entity entityProjectile, boolean explosion, boolean areaOfEffect) {
      this(entityDamager, entityProjectile, explosion, areaOfEffect, "weapon");
   }

   @Deprecated
   private WeaponDamage(@Nullable Entity entityProjectile, boolean explosion, boolean areaOfEffect, String type) {
      this(null, entityProjectile, explosion, areaOfEffect, type);
      this.shouldRewrite = true;
   }

   public void setEntityDamager(Entity entity) {
      this.damageSourceEntity = entity;
   }

   public WeaponDamage setIsThornsDamage() {
      super.setIsThornsDamage();
      return this;
   }

   @Nullable
   public Entity getImmediateSource() {
      return this.entityProjectile;
   }

   @Nullable
   public Vec3d getDamageLocation() {
      if (this.damagePosition != null) {
         return this.damagePosition;
      } else if (this.entityProjectile != null) {
         return GetMOP.entityCenterPos(this.entityProjectile);
      } else {
         return this.damageSourceEntity != null ? this.damageSourceEntity.getPositionVector() : null;
      }
   }

   public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
      EntityLivingBase entitydamager = entityLivingBaseIn.getAttackingEntity();
      String s = "death.attack." + this.damageType;
      String s1 = s + ".player";
      return entitydamager != null && I18n.canTranslate(s1)
         ? new TextComponentTranslation(s1, new Object[]{entityLivingBaseIn.getDisplayName(), entitydamager.getDisplayName()})
         : new TextComponentTranslation(s, new Object[]{entityLivingBaseIn.getDisplayName()});
   }
}

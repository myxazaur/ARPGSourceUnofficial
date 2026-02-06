package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

public class InfernalBlade extends ItemWeapon {
   static ResourceLocation fire_circle2 = new ResourceLocation("arpg:textures/fire_circle2.png");
   static ResourceLocation fireball = new ResourceLocation("arpg:textures/fireball.png");
   static ResourceLocation sparkle6 = new ResourceLocation("arpg:textures/sparkle6.png");

   public InfernalBlade() {
      this.setRegistryName("infernal_blade");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("infernal_blade");
      this.setMaxDamage(660);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public void explodeEntity(ItemStack itemstack, World world, Entity entityExploded, EntityPlayer player) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
      AxisAlignedBB axisalignedbb = entityExploded.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityExploded, axisalignedbb);
      float health = 1.0F;
      if (entityExploded instanceof EntityLivingBase) {
         health = ((EntityLivingBase)entityExploded).getMaxHealth();
      }

      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(player, entity)) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
               Weapons.dealDamage(
                  new WeaponDamage(itemstack, player, entityExploded, true, false, entityExploded, WeaponDamage.explode),
                  Math.min(parameters.getEnchanted("damage_explode_per_health", might) * health, parameters.get("damage_explode_max")),
                  player,
                  entity,
                  true,
                  parameters.getEnchanted("knockback_explode", impulse),
                  entityExploded.posX,
                  entityExploded.posY,
                  entityExploded.posZ
               );
               entity.hurtResistantTime = 0;
               DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.1F);
            }
         }
      }

      world.playSound(
         (EntityPlayer)null,
         entityExploded.posX,
         entityExploded.posY,
         entityExploded.posZ,
         Sounds.explode7,
         SoundCategory.HOSTILE,
         1.0F,
         0.9F + itemRand.nextFloat() / 5.0F
      );
      IWeapon.fireEffect(
         this,
         entityExploded,
         world,
         48.0,
         entityExploded.posX,
         entityExploded.posY,
         entityExploded.posZ,
         (double)entityExploded.width,
         (double)entityExploded.height,
         0.0,
         0.0,
         0.0,
         0.0
      );
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle particle = new GUNParticle(
         fire_circle2, 0.1F, 0.0F, 5, 240, world, x, y + 0.1, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, itemRand.nextInt(360)
      );
      particle.alphaGlowing = true;
      particle.scaleTickAdding = 0.9F;
      particle.alphaTickAdding = -0.185F;
      particle.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
      particle.randomDeath = false;
      world.spawnEntity(particle);
      y += b / 3.0;

      for (int i = 0; i < 10; i++) {
         int lt = 30 + itemRand.nextInt(30);
         float scl = 0.025F + itemRand.nextFloat() * 0.02F;
         AnimatedGParticle part = new AnimatedGParticle(
            sparkle6,
            scl,
            0.028F,
            lt,
            240,
            world,
            x + (itemRand.nextFloat() - 0.5F) * a,
            y + itemRand.nextFloat() * b,
            z + (itemRand.nextFloat() - 0.5F) * a,
            (float)itemRand.nextGaussian() / 8.0F,
            (float)itemRand.nextGaussian() / 8.0F + 0.1F,
            (float)itemRand.nextGaussian() / 8.0F,
            1.0F,
            0.5F + itemRand.nextFloat() * 0.5F,
            itemRand.nextFloat() * itemRand.nextFloat(),
            true,
            0,
            true,
            2.5F
         );
         part.alphaGlowing = true;
         part.scaleTickAdding = -scl / lt;
         part.framecount = 5;
         part.animDelay = 2 + itemRand.nextInt(3);
         part.disableOnEnd = false;
         part.animCounter = itemRand.nextInt(5);
         world.spawnEntity(part);
      }

      for (int i = 0; i < 20; i++) {
         world.spawnParticle(
            EnumParticleTypes.SMOKE_LARGE,
            x + (itemRand.nextFloat() - 0.5F) * a,
            y + itemRand.nextFloat() * b,
            z + (itemRand.nextFloat() - 0.5F) * a,
            (itemRand.nextFloat() - 0.5F) * 0.5F,
            (itemRand.nextFloat() - 0.5F) * 0.5F,
            (itemRand.nextFloat() - 0.5F) * 0.5F,
            new int[0]
         );
      }

      for (int i = 0; i < 10; i++) {
         int lt = 18 + itemRand.nextInt(10);
         float scl = 0.2F + itemRand.nextFloat() * 0.18F;
         float colF = 1.0F - itemRand.nextFloat() * itemRand.nextFloat() * 0.3F;
         GUNParticle part = new GUNParticle(
            fireball,
            scl,
            0.05F,
            lt,
            240,
            world,
            x,
            y,
            z,
            (float)itemRand.nextGaussian() / 7.0F,
            (float)itemRand.nextGaussian() / 7.0F + 0.2F,
            (float)itemRand.nextGaussian() / 7.0F,
            1.0F,
            colF,
            colF,
            false,
            itemRand.nextInt(360),
            true,
            10.0F
         );
         part.scaleTickAdding = -scl / lt;
         part.alphaGlowing = true;
         part.dropMode = true;
         world.spawnEntity(part);
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            EnumHand hand = null;
            if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
               hand = EnumHand.MAIN_HAND;
            } else if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown2) {
               hand = EnumHand.OFF_HAND;
            }

            if (hand != null) {
               MeleeAttackResult result = IWeapon.doMeleeSwordAttack(this, itemstack, player, hand, itemRand.nextFloat() < 0.2);
               if (result.success) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.melee_sword,
                     SoundCategory.PLAYERS,
                     0.7F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                     for (Entity entity : result.attackedEntities) {
                        if (!entity.isEntityAlive() && Weapons.isPotionActive(entity, PotionEffects.FIERYOIL)) {
                           this.explodeEntity(itemstack, world, entity, player);
                        }
                     }
                  }
               } else {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.melee_miss_sword,
                     SoundCategory.PLAYERS,
                     0.6F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }

               Weapons.setPlayerAnimationOnServer(player, 1, hand);
               player.addExhaustion(0.1F);
               double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
               player.getCooldownTracker()
                  .setCooldown(
                     (Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack))
                  );
            }
         }
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}

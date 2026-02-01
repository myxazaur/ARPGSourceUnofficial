package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityTimelessSword;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.SweepParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TimelessSword extends ItemWeapon {
   static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep_timeless.png");

   public TimelessSword() {
      this.setRegistryName("timeless_sword");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("timeless_sword");
      this.setMaxDamage(6000);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
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
            if (player.getHeldItemMainhand() == itemstack) {
               if (click && !hascooldown) {
                  hand = EnumHand.MAIN_HAND;
               } else if (click2 && !hascooldown2) {
                  hand = EnumHand.OFF_HAND;
               }

               if (hand == EnumHand.MAIN_HAND) {
                  int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack);
                  int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, itemstack);
                  int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack);
                  if (sweep(
                     this,
                     itemstack,
                     player,
                     hand,
                     10 + sharpness,
                     0.4F + knockback * 0.45F,
                     3.2F + sweeping * 0.3F,
                     0.9F + sweeping * 0.2F,
                     1.0F + sweeping * 0.2F,
                     itemRand.nextFloat() < 0.2
                  )) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_sword,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  Weapons.setPlayerAnimationOnServer(player, 1, hand);
                  player.addExhaustion(0.1F);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               } else if (hand == EnumHand.OFF_HAND) {
                  List<Entity> list = world.getLoadedEntityList();
                  player.getCooldownTracker().setCooldown(ItemsRegister.EXP, 10);

                  for (Entity e : list) {
                     if (e instanceof EntityTimelessSword) {
                        EntityTimelessSword entityt = (EntityTimelessSword)e;
                        if (entityt.player != null && entityt.player == player) {
                           entityt.activated = true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean sweep(
      IWeapon iweapon,
      ItemStack stack,
      EntityPlayer player,
      EnumHand hand,
      float damage,
      float knockback,
      float length,
      float size,
      float endSize,
      boolean isCritical
   ) {
      World world = player.world;
      Vec3d vec = GetMOP.PosRayTrace(length, 1.0F, player, size, 0.4);
      float effectrotat = itemRand.nextBoolean() ? 180 - itemRand.nextInt(50) : itemRand.nextInt(50);
      IWeapon.fireEffect(
         ItemsRegister.TIMELESSSWORD,
         player,
         world,
         64.0,
         vec.x,
         vec.y,
         vec.z,
         (double)effectrotat,
         (double)player.rotationPitch,
         (double)player.rotationYaw,
         0.0,
         0.0,
         0.0
      );
      boolean shouldTimeless = true;
      float crit = 0.0F;
      if (itemRand.nextFloat() < 0.2) {
         crit = 4.0F;
         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
            SoundCategory.PLAYERS,
            0.7F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      }

      if (IWeapon.doMeleeSwordAttack(iweapon, stack, player, hand, damage, knockback, length, size, endSize, isCritical).success) {
         shouldTimeless = false;
      }

      if (shouldTimeless && !world.isRemote) {
         float damag = (float)(
            10.0
               + player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
               + EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack)
               + crit
         );
         float kn = (float)(player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).getAttributeValue() + knockback / 2.0);
         EntityTimelessSword esword = new EntityTimelessSword(
            world, stack, player, vec, damag, crit > 0.0F, kn, player.rotationYaw, player.rotationPitch, effectrotat
         );
         Vec3d pose = player.getPositionEyes(1.0F);
         esword.setPosition(pose.x, pose.y, pose.z);
         world.spawnEntity(esword);
         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.time_sword,
            SoundCategory.PLAYERS,
            0.6F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      }

      return !shouldTimeless;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      SweepParticle particle = new SweepParticle(
         sweeptex,
         1.5F + itemRand.nextFloat() / 4.0F,
         6,
         1,
         4,
         2,
         (int)(player.getBrightness() * 150.0F),
         world,
         x,
         y,
         z,
         (float)b,
         (float)c,
         1.0F,
         1.0F,
         1.0F,
         true,
         (int)a
      );
      particle.alphaGlowing = true;
      world.spawnEntity(particle);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 21 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 3;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
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

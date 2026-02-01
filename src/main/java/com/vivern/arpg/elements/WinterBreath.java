package com.vivern.arpg.elements;

import com.vivern.arpg.elements.armor.IItemAttacked;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class WinterBreath extends ItemWeapon implements IItemAttacked {
   static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
   static ResourceLocation snow2 = new ResourceLocation("arpg:textures/snowflake2.png");

   public WinterBreath() {
      this.setRegistryName("winter_breath");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("winter_breath");
      this.setMaxDamage(1600);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("916DD27B-A123-455F-8C7F-6112A1B50A4C"), "Shield speed mh", -0.25, 1)
            );
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("134CA27A-B123-501F-4D8F-3782C6B52C0A"), "Shield speed oh", -0.25, 1)
            );
         }
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "blocking");
            NBTHelper.GiveNBTint(itemstack, 0, "snow");
            int blocks = NBTHelper.GetNBTint(itemstack, "blocking");
            if (player.getHeldItemMainhand() == itemstack && click || player.getHeldItemOffhand() == itemstack && click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (blocks <= 0) {
                     WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.shield_block,
                        SoundCategory.AMBIENT,
                        0.4F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     NBTHelper.SetNBTint(itemstack, parameters.geti("max_hits"), "blocking");
                     player.addExhaustion(0.6F);
                  } else {
                     if (player.ticksExisted % 7 == 0) {
                        Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     }

                     if (blocks == 1 && player.ticksExisted % 8 == 0) {
                        this.findSnowAndCharge(world, itemstack, player);
                     }
                  }
               }
            } else {
               if (blocks > 0) {
                  NBTHelper.SetNBTint(itemstack, 0, "blocking");
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               }

               NBTHelper.SetNBTint(itemstack, 0, "snow");
            }
         }
      }
   }

   @Override
   public float onAttackedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (!player.world.isRemote && NBTHelper.GetNBTint(stack, "blocking") > 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         float damageBlocks = parameters.getEnchanted("damage_reduce", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         if (!IWeapon.checkShieldAngle(stack, player, source)) {
            return hurtdamage;
         } else {
            int blocking = NBTHelper.GetNBTint(stack, "blocking") - 1;
            if (attacker != null
               || source != DamageSource.CRAMMING
                  && source != DamageSource.DROWN
                  && source != DamageSource.FALL
                  && source != DamageSource.HOT_FLOOR
                  && source != DamageSource.IN_FIRE
                  && source != DamageSource.IN_WALL
                  && source != DamageSource.LAVA
                  && source != DamageSource.MAGIC
                  && source != DamageSource.ON_FIRE
                  && source != DamageSource.OUT_OF_WORLD
                  && source != DamageSource.STARVE
                  && source != DamageSource.WITHER) {
               if (player.hurtResistantTime > 0) {
                  return 0.0F;
               } else {
                  if (attacker != null) {
                     int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack);
                     int potionValue1 = parameters.getEnchantedi("freezing_time_add", witchery);
                     int potionValue2 = parameters.getEnchantedi("freezing_highest_power_add", witchery);
                     int potionValue3 = parameters.getEnchantedi("freezing_time_max", witchery);
                     int potionValue4 = parameters.getEnchantedi("freezing_power_max", witchery);
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        attacker,
                        PotionEffects.FREEZING,
                        (float)potionValue1,
                        (float)(potionValue2 - blocking),
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        potionValue3,
                        potionValue4
                     );
                     Freezing.tryPlaySound(attacker, lastdebaff);
                     SuperKnockback.applyShieldBlock(
                        player,
                        attacker,
                        parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)),
                        parameters.get("self_knockback")
                     );
                  }

                  NBTHelper.AddNBTint(stack, -1, "blocking");
                  if (blocking <= 0) {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_break,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     this.winterbreak(player.world, stack, player);
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(stack));
                  } else {
                     if (blocking == 1) {
                        this.findSnowAndCharge(player.world, stack, player);
                     }

                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_metall,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                  }

                  player.addExhaustion(0.1F);
                  if (!player.capabilities.isCreativeMode) {
                     stack.damageItem(1, player);
                  }

                  player.hurtResistantTime = 10;
                  return hurtdamage - damageBlocks;
               }
            } else {
               return hurtdamage;
            }
         }
      } else {
         return hurtdamage;
      }
   }

   public void winterbreak(World world, ItemStack stack, EntityPlayer player) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      int snowNeed = parameters.getEnchantedi("winterbreak_snow_need", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack));
      if (NBTHelper.GetNBTint(stack, "snow") >= snowNeed) {
         Vec3d vec = GetMOP.entityCenterPos(player);
         double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack));
         AxisAlignedBB aabb = new AxisAlignedBB(
            vec.x - damageRadius,
            vec.y - damageRadius,
            vec.z - damageRadius,
            vec.x + damageRadius,
            vec.y + damageRadius,
            vec.z + damageRadius
         );
         List<Entity> list = world.getEntitiesWithinAABB(Entity.class, aabb);
         boolean effect = false;

         for (Entity en : list) {
            if (Team.checkIsOpponent(player, en)) {
               int spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack);
               SuperKnockback.applyKnockback(
                  en,
                  parameters.getEnchanted("winterbreak_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)),
                  player.posX,
                  player.posY - 0.3,
                  player.posZ
               );
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack);
               int potionValue1 = parameters.getEnchantedi("slowness_time_add", witchery);
               int potionValue2 = parameters.getEnchantedi("slowness_power_add", witchery);
               int potionValue3 = parameters.getEnchantedi("slowness_time_max", witchery);
               Weapons.mixPotion(
                  en,
                  MobEffects.SLOWNESS,
                  (float)potionValue1,
                  (float)potionValue2,
                  Weapons.EnumPotionMix.WITH_MAXIMUM,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumMathOperation.PLUS,
                  Weapons.EnumMathOperation.PLUS,
                  potionValue3,
                  0
               );
               if (spec > 0) {
                  potionValue1 = parameters.getEnchantedi("frostburn_time_add", witchery);
                  potionValue2 = parameters.getEnchantedi("frostburn_power_add", witchery);
                  potionValue3 = parameters.getEnchantedi("frostburn_time_max", witchery);
                  Weapons.mixPotion(
                     en,
                     PotionEffects.FROSTBURN,
                     (float)potionValue1,
                     (float)potionValue2,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumPotionMix.GREATEST,
                     Weapons.EnumMathOperation.PLUS,
                     Weapons.EnumMathOperation.PLUS,
                     potionValue3,
                     0
                  );
               }
            }
         }

         IWeapon.fireEffect(this, player, world, 64.0, vec.x, vec.y, vec.z, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         NBTHelper.SetNBTint(stack, 0, "snow");
         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.winter_break,
            SoundCategory.NEUTRAL,
            1.4F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      }
   }

   public void findSnowAndCharge(World world, ItemStack stack, EntityPlayer player) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      int need = parameters.getEnchantedi("winterbreak_snow_need", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack));
      boolean effect = false;
      if (NBTHelper.GetNBTint(stack, "snow") < need) {
         for (int x = -3; x <= 3; x++) {
            for (int y = -2; y <= 2; y++) {
               for (int z = -3; z <= 3; z++) {
                  if (itemRand.nextFloat() < 0.7) {
                     BlockPos pos = new BlockPos(player.posX + x, player.posY + y, player.posZ + z);
                     Block bl = world.getBlockState(pos).getBlock();
                     if (bl == Blocks.SNOW || bl == Blocks.SNOW_LAYER || bl == BlocksRegister.LOOSESNOW) {
                        effect = true;
                        world.setBlockToAir(pos);
                        NBTHelper.AddNBTint(stack, 1, "snow");
                        if (NBTHelper.GetNBTint(stack, "snow") >= need) {
                           IWeapon.fireEffect(
                              this,
                              player,
                              world,
                              64.0,
                              player.posX,
                              player.posY,
                              player.posZ,
                              1.0,
                              (double)player.getEntityId(),
                              0.0,
                              0.0,
                              0.0,
                              0.0
                           );
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.item_misc_d,
                              SoundCategory.NEUTRAL,
                              0.7F,
                              0.95F + itemRand.nextFloat() / 5.0F
                           );
                           return;
                        }
                     }
                  }
               }
            }
         }
      }

      if (effect) {
         IWeapon.fireEffect(
            this,
            player,
            world,
            64.0,
            player.posX,
            player.posY,
            player.posZ,
            1.0,
            (double)player.getEntityId(),
            0.0,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (a == 0.0) {
         BlockPos pos = new BlockPos(x, y, z);
         int light = Math.max(world.getLightFor(EnumSkyBlock.BLOCK, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) * 15;
         int countOfParticles = 30;
         float R = 0.2F + itemRand.nextFloat() * 0.03F;

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
            float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (itemRand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               snow,
               0.15F + (float)itemRand.nextGaussian() / 30.0F,
               0.01F,
               22 + itemRand.nextInt(10),
               light,
               world,
               x,
               y,
               z,
               X,
               Y,
               Z,
               1.0F - (float)itemRand.nextGaussian() / 14.0F,
               1.0F,
               1.0F,
               false,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            world.spawnEntity(particle);
         }
      } else {
         Entity en = world.getEntityByID((int)b);
         ParticleTracker.TrackerFollowDynamicPoint tfdp = en == null
            ? null
            : new ParticleTracker.TrackerFollowDynamicPoint(en, false, 0.0F, 1.0E-4F, 0.005F).setYadd(en.height / 2.0F);
         int limit = 30;

         for (int xx = -3; xx <= 3; xx++) {
            for (int yy = -2; yy <= 2; yy++) {
               for (int zz = -3; zz <= 3; zz++) {
                  if (limit > 0 && itemRand.nextFloat() < 0.7) {
                     BlockPos pos = new BlockPos(x + xx, y + yy, z + zz);
                     Block bl = world.getBlockState(pos).getBlock();
                     if (bl == Blocks.SNOW || bl == Blocks.SNOW_LAYER || bl == BlocksRegister.LOOSESNOW) {
                        limit--;
                        GUNParticle particle = new GUNParticle(
                           snow2,
                           0.08F + itemRand.nextFloat() * 0.1F,
                           0.0F,
                           20 + itemRand.nextInt(15),
                           180,
                           world,
                           pos.getX() + 0.5,
                           pos.getY() + 0.1,
                           pos.getZ() + 0.5,
                           0.0F,
                           0.0F,
                           0.0F,
                           1.0F - (float)itemRand.nextGaussian() / 14.0F,
                           1.0F,
                           1.0F,
                           false,
                           itemRand.nextInt(360)
                        );
                        particle.tracker = tfdp;
                        world.spawnEntity(particle);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(
         (float)NBTHelper.GetNBTint(itemstack, "blocking") / WeaponParameters.getWeaponParameters(itemstack.getItem()).geti("max_hits"), 0.0F, 1.0F
      );
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "blocking") > 0;
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
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean cancelOnNull() {
      return true;
   }
}

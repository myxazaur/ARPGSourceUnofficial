package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.animation.EnumFlick;
import com.Vivern.Arpg.elements.animation.Flicks;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Buzdygan extends ItemWeapon {
   public static int maxAngle = 3600;
   public static int angleAdd = maxAngle / 60;

   public Buzdygan() {
      this.setRegistryName("buzdygan");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("buzdygan");
      this.setMaxDamage(1750);
      this.setMaxStackSize(1);
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param == 0) {
         Booom.lastTick = 16;
         Booom.frequency = 0.196F;
         Booom.x = 1.0F;
         Booom.y = itemRand.nextFloat() < 0.5 ? 0.2F : -0.2F;
         Booom.z = 0.0F;
         Booom.power = -0.25F;
      }

      if (param == 1) {
         Booom.lastTick = 14;
         Booom.frequency = 0.225F;
         Booom.x = -1.0F;
         Booom.y = (float)itemRand.nextGaussian() / 7.0F;
         Booom.z = (float)itemRand.nextGaussian() / 7.0F;
         Booom.power = 0.3F;
      }
   }

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 0) {
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.SPIN, 0, maxAngle, angleAdd, 0);
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click1 = false;
            boolean click2 = false;
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            EnumHand hand = null;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int maxrunes = parameters.geti("max_charges");
            if (player.getHeldItemMainhand() == itemstack) {
               hand = EnumHand.MAIN_HAND;
               click1 = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
               click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            } else if (player.getHeldItemOffhand() == itemstack) {
               hand = EnumHand.OFF_HAND;
               click2 = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
               click1 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            }

            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (hand != null) {
               boolean active = NBTHelper.GetNBTboolean(itemstack, "active");
               float runes = NBTHelper.GetNBTfloat(itemstack, "runes");
               if (!active) {
                  if (runes >= maxrunes && click2 && !hascooldown) {
                     itemstack.damageItem(4, player);
                     NBTHelper.GiveNBTboolean(itemstack, true, "active");
                     NBTHelper.SetNBTboolean(itemstack, true, "active");
                     IWeapon.sendIWeaponState(itemstack, player, 0, itemSlot, hand);
                  } else if (delay <= 0 && click1 && !hascooldown) {
                     NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
                     NBTHelper.SetNBTint(itemstack, 5, "atdelay");
                     int randAnim = itemRand.nextInt(3);
                     if (randAnim == 0) {
                        Weapons.setPlayerAnimationOnServer(player, 29, EnumHand.MAIN_HAND);
                     }

                     if (randAnim == 1) {
                        Weapons.setPlayerAnimationOnServer(player, 30, EnumHand.MAIN_HAND);
                     }

                     if (randAnim == 2) {
                        Weapons.setPlayerAnimationOnServer(player, 31, EnumHand.MAIN_HAND);
                     }

                     double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                     player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                     IWeapon.fireBomEffect(this, player, world, 0);
                  }

                  if (delay == 1) {
                     if (IWeapon.doMeleeSwordAttack(this, itemstack, player, hand, false).success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.chain_mace_impact,
                           SoundCategory.PLAYERS,
                           0.7F,
                           0.8F + itemRand.nextFloat() / 5.0F
                        );
                        if (runes < maxrunes) {
                           NBTHelper.GiveNBTfloat(itemstack, 0.0F, "runes");
                           NBTHelper.AddNBTfloat(itemstack, 1.0F, "runes");
                        }

                        IWeapon.fireBomEffect(this, player, world, 1);
                     } else {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.swosh_a,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.6F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     player.addExhaustion(0.1F);
                  }
               } else if (runes > 0.0F) {
                  if (!hascooldown) {
                     NBTHelper.AddNBTfloat(itemstack, parameters.get("charge_decrement"), "runes");
                     player.addExhaustion(0.02F);
                     player.getCooldownTracker().setCooldown(this, parameters.geti("charged_hit_delay"));
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.buzdygan_rotate,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     if (runes >= 1.0F) {
                        Weapons.setPlayerAnimationOnServer(player, 22, hand);
                     }

                     int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
                     int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
                     double length = parameters.getEnchanted("charged_length", range);
                     double size = parameters.getEnchanted("charged_size", range);
                     double slowdown = parameters.get("charged_slowdown");
                     float cdamage = parameters.getEnchanted("charged_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                     float cknockback = parameters.getEnchanted("charged_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                     Vec3d vec3d = player.getPositionEyes(1.0F);
                     Vec3d vec3d1 = player.getLook(1.0F);
                     Vec3d vec3d2 = vec3d.add(vec3d1.x * length, vec3d1.y * length, vec3d1.z * length);
                     RayTraceResult raytraceresult = player.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
                     if (raytraceresult != null) {
                        vec3d2 = new Vec3d(
                           raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z
                        );
                     }

                     List<Entity> list = GetMOP.findEntitiesOnPath(vec3d, vec3d2, world, player, size, size * 0.7);
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.heavymelee),
                                 cdamage,
                                 player,
                                 entity,
                                 true,
                                 cknockback,
                                 player.posX,
                                 player.posY,
                                 player.posZ
                              );
                              entity.hurtResistantTime = 0;
                              entity.motionX *= slowdown;
                              entity.motionY *= slowdown;
                              entity.motionZ *= slowdown;
                              Weapons.setPotionIfEntityLB(
                                 entity, MobEffects.SLOWNESS, parameters.getEnchantedi("slowness_time", witchery), parameters.geti("slowness_power")
                              );
                           }
                        }
                     }
                  }
               } else {
                  NBTHelper.SetNBTfloat(itemstack, 0.0F, "runes");
                  NBTHelper.SetNBTboolean(itemstack, false, "active");
               }
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      Weapons.mixPotion(
         entity,
         PotionEffects.BROKEN_ARMOR,
         (float)parameters.getEnchantedi("brokenarmor_time", witchery),
         (float)parameters.geti("brokenarmor_power_add"),
         Weapons.EnumPotionMix.GREATEST,
         Weapons.EnumPotionMix.WITH_MAXIMUM,
         Weapons.EnumMathOperation.NONE,
         Weapons.EnumMathOperation.PLUS,
         parameters.getEnchantedi("brokenarmor_time", witchery),
         parameters.geti("brokenarmor_power_max")
      );
      return super.attackEntityMelee(entity, stack, player, hand, isCritical);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
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

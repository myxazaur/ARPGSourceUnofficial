package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityMagicRocket;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicRocket extends ItemWeapon {
   public static ResourceLocation textur = new ResourceLocation("arpg:textures/circle.png");

   public MagicRocket() {
      this.setRegistryName("magic_rocket");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("magic_rocket");
      this.setMaxDamage(3300);
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         float power = Mana.getMagicPowerMax(player);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         float manacost = parameters.getEnchanted("manacost", sor);
         if (player.getHeldItemMainhand() == itemstack) {
            if (click2) {
               Entity captured = world.getEntityByID(NBTHelper.GetNBTint(itemstack, "actualid"));
               if (captured instanceof EntityMagicRocket) {
                  if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) == 0) {
                     if (!world.isRemote) {
                        EntityMagicRocket rocket = (EntityMagicRocket)captured;
                        rocket.disableMagic = 3;
                        Vec3d vec = GetMOP.PosRayTrace(5.0, 1.0F, player, 0.2, 0.2);
                        rocket.motionX *= 0.7;
                        rocket.motionY *= 0.7;
                        rocket.motionZ *= 0.7;
                        SuperKnockback.applyMove(rocket, -0.27F, vec.x, vec.y, vec.z);
                        rocket.velocityChanged = true;
                     }
                  } else {
                     EntityMagicRocket rocket = (EntityMagicRocket)captured;
                     rocket.disableMagic = 3;
                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     List<EntityLivingBase> list = GetMOP.MopRayTrace(edist, 1.0F, player, 0.2, 0.2);
                     if (!list.isEmpty() && list.get(0) != null) {
                        rocket.specialTarget = (Entity)list.get(0);
                        rocket.useTarget = true;
                        if (world.isRemote) {
                           GUNParticle bigboom = new GUNParticle(
                              textur,
                              list.get(0).width,
                              -0.001F,
                              12,
                              240,
                              world,
                              list.get(0).posX,
                              list.get(0).posY + 0.1,
                              list.get(0).posZ,
                              0.0F,
                              0.1F,
                              0.0F,
                              0.5F,
                              0.75F,
                              1.0F,
                              true,
                              1
                           );
                           bigboom.dropped = true;
                           bigboom.scaleTickAdding = -0.05F;
                           bigboom.alphaGlowing = true;
                           world.spawnEntity(bigboom);
                        }
                     } else if (!world.isRemote) {
                        Vec3d vec = GetMOP.PosRayTrace(5.0, 1.0F, player, 0.2, 0.2);
                        rocket.motionX *= 0.85;
                        rocket.motionY *= 0.85;
                        rocket.motionZ *= 0.85;
                        SuperKnockback.applyMove(rocket, -0.27F, vec.x, vec.y, vec.z);
                        rocket.velocityChanged = true;
                     }
                  }
               }
            }

            if (Mana.getMana(player) > manacost && !world.isRemote && !player.getCooldownTracker().hasCooldown(this) && click) {
               NBTHelper.GiveNBTint(itemstack, 0, "actualid");
               Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.MAIN_HAND);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.magic_n,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               EntityMagicRocket projectile = new EntityMagicRocket(world, player, itemstack, power);
               Weapons.shoot(
                  projectile,
                  EnumHand.MAIN_HAND,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.1F,
                  0.5F,
                  0.2F
               );
               projectile.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
               world.spawnEntity(projectile);
               NBTHelper.SetNBTint(itemstack, projectile.getEntityId(), "actualid");
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}

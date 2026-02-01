package com.vivern.arpg.elements;

import com.vivern.arpg.entity.PistolFishStrike;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PistolFish extends ItemWeapon {
   public static int maxammo = 47;

   public PistolFish() {
      this.setRegistryName("pistol_fish");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("pistol_fish");
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

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(itemIn);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            EnumHand hand = click && player.getHeldItemMainhand() == itemstack
               ? EnumHand.MAIN_HAND
               : (click2 && player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (hand != null) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if ((!hascooldown || hand != EnumHand.MAIN_HAND) && (!hascooldown2 || hand != EnumHand.OFF_HAND)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.pistol_fish,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 5, hand);
                     if (!player.capabilities.isCreativeMode) {
                        if (itemRand.nextFloat()
                           < parameters.getEnchanted("ammo_consume_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))) {
                           this.addAmmo(ammo, itemstack, -1);
                        }

                        itemstack.damageItem(1, player);
                     }

                     PistolFishStrike projectile = new PistolFishStrike(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        hand,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.1F,
                        0.4F,
                        0.2F
                     );
                     projectile.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(projectile);
                     if (itemRand.nextFloat()
                        < parameters.getEnchanted("special_shoot_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack))) {
                        Entity htarget = projectile.getHypotheticalTarget();
                        if (htarget != null) {
                           double radius = player.getDistance(htarget) * 0.3;
                           List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(
                              world, GetMOP.entityCenterPos(htarget), radius, radius / 2.0, player, htarget
                           );
                           if (list.size() >= 1) {
                              for (int i = 0; i < 6; i++) {
                                 EntityLivingBase newtarget = list.get(itemRand.nextInt(list.size()));
                                 if (GetMOP.thereIsNoBlockCollidesBetween(
                                    world, player.getPositionEyes(1.0F), GetMOP.entityCenterPos(newtarget), 1.0F, null, false
                                 )) {
                                    PistolFishStrike projectile2 = new PistolFishStrike(world, player, itemstack);
                                    Weapons.shoot(projectile2, hand, player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0F, 0.0F, -0.1F, 0.4F, 0.2F);
                                    Vec3d pos1 = projectile2.getPositionVector();
                                    Vec3d pos2 = GetMOP.entityCenterPos(newtarget);
                                    Vec3d pos3 = pos2.subtract(pos1);
                                    double dist = Math.sqrt(pos3.x * pos3.x + pos3.z * pos3.z);
                                    SuperKnockback.setMove(
                                       projectile2, -parameters.get("velocity"), pos2.x, pos2.y + dist * 0.1, pos2.z
                                    );
                                    projectile2.livetime = parameters.getEnchantedi(
                                       "livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack)
                                    );
                                    world.spawnEntity(projectile2);
                                    world.playSound(
                                       (EntityPlayer)null,
                                       player.posX,
                                       player.posY,
                                       player.posZ,
                                       Sounds.pistol_fish,
                                       SoundCategory.AMBIENT,
                                       0.9F,
                                       1.4F + itemRand.nextFloat() / 5.0F
                                    );
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.FISHFEED, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.pistol_fish_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, hand);
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom(boolean mainhand) {
      Booom.lastTick = 11;
      Booom.frequency = -0.285F;
      Booom.x = 1.0F;
      Booom.y = (float)itemRand.nextGaussian() / 10.0F;
      Booom.z = (float)itemRand.nextGaussian() / 10.0F;
      Booom.power = 0.22F;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ConiferRod extends ItemWeapon {
   public ConiferRod() {
      this.setRegistryName("conifer_rod");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("conifer_rod");
      this.setMaxDamage(10);
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
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            float power = Mana.getMagicPowerMax(player);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            if (player.getHeldItemMainhand() == itemstack && !player.getCooldownTracker().hasCooldown(this)) {
               if (click2) {
                  player.openGui(
                     Main.instance,
                     11,
                     world,
                     MathHelper.floor(player.posX),
                     MathHelper.floor(player.posY),
                     MathHelper.floor(player.posZ)
                  );
               } else if (click) {
                  int mode = NBTHelper.GetNBTint(itemstack, "mode");
                  int selectedFilterMinion = NBTHelper.GetNBTint(itemstack, "selecFilterMinion");
                  int selectedFilterHostil = NBTHelper.GetNBTint(itemstack, "selecFilterHostil");
                  Predicate<EntityLivingBase> filterMinion = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterMinion, false);
                  Predicate<EntityLivingBase> filterHostile = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterHostil, true);
                  if (mode == 0 && mana > 5.0F - sor) {
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.MAIN_HAND);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.magic_k,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -5.0F + sor);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                     }
                  } else if (mode >= 1 && mode <= 4) {
                     RayTraceResult res = GetMOP.fixedRayTraceBlocks(world, player, 64.0, 1.0, 1.0, false, false, true, false);
                     if (res != null && res.entityHit != null && res.entityHit instanceof AbstractMob) {
                        AbstractMob mob = (AbstractMob)res.entityHit;
                        if ((filterMinion == null || filterMinion.apply(mob)) && player == mob.owner) {
                           if (mode <= 2) {
                              mob.isAgressive = mode == 2;
                              if (!mob.isAgressive) {
                                 mob.setAttackTarget(null);
                              }
                           } else {
                              mob.isStaying = mode == 4;
                           }
                        }
                     }
                  } else if (mode == 6) {
                     RayTraceResult res = GetMOP.fixedRayTraceBlocks(world, player, 64.0, 1.0, 1.0, false, false, true, false);
                     if (res != null && res.entityHit != null && res.entityHit instanceof EntityLivingBase) {
                        EntityLivingBase mob = (EntityLivingBase)res.entityHit;
                        if (filterHostile == null || filterHostile.apply(mob)) {
                           for (AbstractMob minion : Weapons.getPlayerMinions(player)) {
                              if ((filterMinion == null || filterMinion.apply(minion)) && Team.checkIsOpponent(minion, mob)) {
                                 minion.setAttackTarget(mob);
                              }
                           }
                        }
                     }
                  } else if (mode == 5) {
                     RayTraceResult res = GetMOP.fixedRayTraceBlocks(world, player, 32.0, 1.0, 1.0, false, false, true, false);
                     if (res != null && res.hitVec != null) {
                        double damageRadius = 6.0;
                        AxisAlignedBB aabb = new AxisAlignedBB(
                           res.hitVec.x - damageRadius,
                           res.hitVec.y - damageRadius,
                           res.hitVec.z - damageRadius,
                           res.hitVec.x + damageRadius,
                           res.hitVec.y + damageRadius,
                           res.hitVec.z + damageRadius
                        );
                        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                        if (!list.isEmpty()) {
                           player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                           List<AbstractMob> minions = filterMinion == null ? Weapons.getPlayerMinions(player) : Weapons.getPlayerMinions(player, filterMinion);
                           this.agree(minions, list, filterHostile);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void agree(List<AbstractMob> minions, List<EntityLivingBase> listMobs, @Nullable Predicate<EntityLivingBase> filterHostile) {
      List<AbstractMob> removedlist = new ArrayList<>();
      List<EntityLivingBase> listMobsCopy = new ArrayList<>();

      for (EntityLivingBase en : listMobs) {
         listMobsCopy.add(en);
      }

      boolean atLeastOneHostile = false;

      for (AbstractMob minion : minions) {
         EntityLivingBase entitylivingbase = this.getBestToAgreeAndChangeList(listMobsCopy, minion, filterHostile);
         if (entitylivingbase != null) {
            atLeastOneHostile = true;
            minion.setAttackTarget(entitylivingbase);
         } else {
            removedlist.add(minion);
         }
      }

      if (atLeastOneHostile && !removedlist.isEmpty()) {
         this.agree(removedlist, listMobs, filterHostile);
      }
   }

   @Nullable
   public EntityLivingBase getBestToAgreeAndChangeList(List<EntityLivingBase> listMobs, AbstractMob minion, @Nullable Predicate<EntityLivingBase> filterHostile) {
      double distMIN = 999999.0;
      EntityLivingBase mobToAgree = null;
      if (!listMobs.isEmpty()) {
         for (EntityLivingBase mob : listMobs) {
            if ((filterHostile == null || filterHostile.apply(mob)) && Team.checkIsOpponent(minion, mob)) {
               double distsq = mob.getDistanceSq(minion);
               if (distsq < distMIN) {
                  distMIN = distsq;
                  mobToAgree = mob;
               }
            }
         }
      }

      if (mobToAgree != null) {
         listMobs.remove(mobToAgree);
      }

      return mobToAgree;
   }

   @Override
   public void guiClick(ItemStack itemstack, EntityLivingBase player, int mouseX, int mouseY, int mouseButton) {
      NBTHelper.GiveNBTint(itemstack, 0, "mode");
      int selectedFilterM = NBTHelper.GetNBTint(itemstack, "selecFilterMinion");
      int selectedFilterH = NBTHelper.GetNBTint(itemstack, "selecFilterHostil");
      if (mouseY > 0 && mouseY <= 16 && mouseX > 23 && mouseX <= 39) {
         NBTHelper.SetNBTint(itemstack, 0, "mode");
      } else if (mouseY > 8 && mouseY <= 24 && mouseX > 39 && mouseX <= 55) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 1, "mode");
         } else if (mouseButton == 1 && player instanceof EntityPlayer) {
            Predicate<EntityLivingBase> filterMinion = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterM, false);

            for (AbstractMob minion : Weapons.getPlayerMinions((EntityPlayer)player)) {
               if (filterMinion == null || filterMinion.apply(minion)) {
                  minion.isAgressive = false;
                  minion.setAttackTarget(null);
               }
            }
         }
      } else if (mouseY > 25 && mouseY <= 41 && mouseX > 45 && mouseX <= 61) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 2, "mode");
         } else if (mouseButton == 1 && player instanceof EntityPlayer) {
            Predicate<EntityLivingBase> filterMinion = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterM, false);

            for (AbstractMob minionx : Weapons.getPlayerMinions((EntityPlayer)player)) {
               if (filterMinion == null || filterMinion.apply(minionx)) {
                  minionx.isAgressive = true;
               }
            }
         }
      } else if (mouseY > 41 && mouseY <= 57 && mouseX > 32 && mouseX <= 48) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 3, "mode");
         } else if (mouseButton == 1 && player instanceof EntityPlayer) {
            Predicate<EntityLivingBase> filterMinion = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterM, false);

            for (AbstractMob minionxx : Weapons.getPlayerMinions((EntityPlayer)player)) {
               if (filterMinion == null || filterMinion.apply(minionxx)) {
                  minionxx.isStaying = false;
               }
            }
         }
      } else if (mouseY > 41 && mouseY <= 57 && mouseX > 13 && mouseX <= 29) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 4, "mode");
         } else if (mouseButton == 1 && player instanceof EntityPlayer) {
            Predicate<EntityLivingBase> filterMinion = NBTHelper.ReadNBTSummonFilter(itemstack, selectedFilterM, false);

            for (AbstractMob minionxxx : Weapons.getPlayerMinions((EntityPlayer)player)) {
               if (filterMinion == null || filterMinion.apply(minionxxx)) {
                  minionxxx.isStaying = true;
               }
            }
         }
      } else if (mouseY > 26 && mouseY <= 42 && mouseX > 1 && mouseX <= 17) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 5, "mode");
         }
      } else if (mouseY > 8 && mouseY <= 24 && mouseX > 6 && mouseX <= 22) {
         if (mouseButton == 0) {
            NBTHelper.SetNBTint(itemstack, 6, "mode");
         }
      } else if (mouseX >= 97 && mouseX <= 215) {
         for (int number = 0; number < 99; number++) {
            int startY = -40 + number * 20;
            if (mouseY >= startY && mouseY <= startY + 18) {
               if (number == selectedFilterM) {
                  NBTHelper.GiveNBTint(itemstack, -1, "selecFilterMinion");
                  NBTHelper.SetNBTint(itemstack, -1, "selecFilterMinion");
               } else {
                  NBTHelper.GiveNBTint(itemstack, number, "selecFilterMinion");
                  NBTHelper.SetNBTint(itemstack, number, "selecFilterMinion");
               }
               break;
            }
         }
      } else if (mouseX >= -155 && mouseX <= -38) {
         for (int numberx = 0; numberx < 99; numberx++) {
            int startY = -40 + numberx * 20;
            if (mouseY >= startY && mouseY <= startY + 18) {
               if (numberx == selectedFilterH) {
                  NBTHelper.GiveNBTint(itemstack, -1, "selecFilterHostil");
                  NBTHelper.SetNBTint(itemstack, -1, "selecFilterHostil");
               } else {
                  NBTHelper.GiveNBTint(itemstack, numberx, "selecFilterHostil");
                  NBTHelper.SetNBTint(itemstack, numberx, "selecFilterHostil");
               }
               break;
            }
         }
      } else if (mouseX >= 217 && mouseX <= 228) {
         for (int numberxx = 0; numberxx < 99; numberxx++) {
            int startY = -37 + numberxx * 20;
            if (mouseY >= startY && mouseY <= startY + 12) {
               NBTHelper.RemoveNBTSummonFilter(itemstack, numberxx, false);
               break;
            }
         }
      } else if (mouseX >= -35 && mouseX <= -24) {
         for (int numberxxx = 0; numberxxx < 99; numberxxx++) {
            int startY = -37 + numberxxx * 20;
            if (mouseY >= startY && mouseY <= startY + 12) {
               NBTHelper.RemoveNBTSummonFilter(itemstack, numberxxx, true);
               break;
            }
         }
      }
   }

   @Override
   public void receiveClientString(ItemStack itemstack, EntityLivingBase player, String string) {
      char ch = string.charAt(0);
      int filtermode = 0;
      byte var12;
      if (ch == '1') {
         var12 = 1;
      } else if (ch == '2') {
         var12 = 2;
      } else if (ch == '3') {
         var12 = 3;
      } else {
         if (ch != '4') {
            return;
         }

         var12 = 4;
      }

      boolean hostile = false;
      char chHos = string.charAt(1);
      if (chHos == '0') {
         hostile = false;
      } else {
         if (chHos != '1') {
            return;
         }

         hostile = true;
      }

      boolean inverted = false;
      char chInv = string.charAt(2);
      if (chInv == '0') {
         inverted = false;
      } else {
         if (chInv != '1') {
            return;
         }

         inverted = true;
      }

      String string2 = string.substring(3);
      int count = NBTHelper.getNBTSummonFiltersCount(itemstack, hostile);
      NBTHelper.WriteNBTSummonFilter(itemstack, var12, count, inverted, string2, hostile);
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 9 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 3;
   }
}

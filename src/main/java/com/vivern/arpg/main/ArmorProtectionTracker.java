package com.vivern.arpg.main;

import baubles.api.BaublesApi;
import com.vivern.arpg.elements.IItemDamaged;
import com.vivern.arpg.elements.armor.IItemAttacked;
import com.vivern.arpg.elements.armor.IItemDamaging;
import com.vivern.arpg.elements.armor.IItemHurted;
import com.vivern.arpg.elements.armor.IItemKilling;
import com.vivern.arpg.potions.AdvancedPotion;
import com.vivern.arpg.renders.KillScore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class ArmorProtectionTracker {
   @SubscribeEvent
   public static void onLivingDamage(LivingDamageEvent event) {
      DamageSource source = event.getSource();
      float damage = event.getAmount();
      boolean cancelOn0 = false;
      if (event.getEntityLiving() instanceof EntityPlayer) {
         IInventory inv = BaublesApi.getBaubles((EntityPlayer)event.getEntityLiving());

         for (int i = 0; i < 7; i++) {
            ItemStack bitem = inv.getStackInSlot(i);
            if (bitem.getItem() instanceof IItemDamaged) {
               IItemDamaged iItemDamaged = (IItemDamaged)bitem.getItem();
               damage = iItemDamaged.onDamagedWithItem(damage, bitem, (EntityPlayer)event.getEntityLiving(), source);
               if (iItemDamaged.cancelOnNull()) {
                  cancelOn0 = true;
               }
            }
         }
      }

      if (cancelOn0 && damage <= 0.0F) {
         event.setCanceled(true);
      }

      event.setAmount(damage);
   }

   @SubscribeEvent
   public static void onLivingHurt(LivingHurtEvent event) {
      KillScore.onLivingHurt(event);
      DamageSource source = event.getSource();
      float damage = event.getAmount();
      boolean cancelOn0 = false;
      if (source != DamageSource.DROWN
         && source != DamageSource.ON_FIRE
         && source != DamageSource.MAGIC
         && source != DamageSource.OUT_OF_WORLD
         && source != DamageSource.STARVE
         && source != DamageSource.WITHER
         && source != DamageSource.FALL
         && damage > 1.0F) {
         damage = Math.max(damage - (float)Mana.getArmorProtection(event.getEntityLiving()), 1.0F);
      }

      if (event.getEntityLiving() instanceof EntityPlayer) {
         for (ItemStack stack : ((EntityPlayer)event.getEntityLiving()).inventory.armorInventory) {
            if (stack.getItem() instanceof IItemHurted) {
               IItemHurted iItemHurted = (IItemHurted)stack.getItem();
               damage = iItemHurted.onHurtWithItem(damage, stack, (EntityPlayer)event.getEntityLiving(), source);
               if (iItemHurted.cancelOnNull()) {
                  cancelOn0 = true;
               }
            }
         }

         IInventory inv = BaublesApi.getBaubles((EntityPlayer)event.getEntityLiving());

         for (int i = 0; i < 7; i++) {
            ItemStack bitem = inv.getStackInSlot(i);
            if (bitem.getItem() instanceof IItemHurted) {
               IItemHurted iItemHurted = (IItemHurted)bitem.getItem();
               damage = iItemHurted.onHurtWithItem(damage, bitem, (EntityPlayer)event.getEntityLiving(), source);
               if (iItemHurted.cancelOnNull()) {
                  cancelOn0 = true;
               }
            }
         }

         ItemStack stackm = event.getEntityLiving().getHeldItemMainhand();
         ItemStack stacko = event.getEntityLiving().getHeldItemOffhand();
         if (stackm.getItem() instanceof IItemHurted) {
            IItemHurted iItemHurted = (IItemHurted)stackm.getItem();
            damage = iItemHurted.onHurtWithItem(damage, stackm, (EntityPlayer)event.getEntityLiving(), source);
            if (iItemHurted.cancelOnNull()) {
               cancelOn0 = true;
            }
         }

         if (stacko.getItem() instanceof IItemHurted) {
            IItemHurted iItemHurted = (IItemHurted)stacko.getItem();
            damage = iItemHurted.onHurtWithItem(damage, stacko, (EntityPlayer)event.getEntityLiving(), source);
            if (iItemHurted.cancelOnNull()) {
               cancelOn0 = true;
            }
         }
      }

      for (PotionEffect effect : event.getEntityLiving().getActivePotionEffects()) {
         if (effect.getPotion() instanceof AdvancedPotion) {
            damage = ((AdvancedPotion)effect.getPotion()).onHurtThis(event.getEntityLiving(), source, damage, effect);
         }
      }

      if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityPlayer) {
         EntityPlayer damager = (EntityPlayer)source.getTrueSource();

         for (ItemStack stackx : damager.inventory.armorInventory) {
            if (stackx.getItem() instanceof IItemDamaging) {
               damage = ((IItemDamaging)stackx.getItem()).onCauseDamageWithItem(damage, stackx, damager, event.getEntityLiving(), source);
            }
         }

         IInventory inv = BaublesApi.getBaubles(damager);

         for (int ix = 0; ix < 7; ix++) {
            ItemStack bitem = inv.getStackInSlot(ix);
            if (bitem.getItem() instanceof IItemDamaging) {
               damage = ((IItemDamaging)bitem.getItem()).onCauseDamageWithItem(damage, bitem, damager, event.getEntityLiving(), source);
            }
         }

         ItemStack stackmx = damager.getHeldItemMainhand();
         ItemStack stackox = damager.getHeldItemOffhand();
         if (stackmx.getItem() instanceof IItemDamaging) {
            damage = ((IItemDamaging)stackmx.getItem()).onCauseDamageWithItem(damage, stackmx, damager, event.getEntityLiving(), source);
         }

         if (stackox.getItem() instanceof IItemDamaging) {
            damage = ((IItemDamaging)stackox.getItem()).onCauseDamageWithItem(damage, stackox, damager, event.getEntityLiving(), source);
         }
      }

      if (source.getTrueSource() instanceof EntityLivingBase && !source.damageType.equals("thorns")) {
         EntityLivingBase damager = (EntityLivingBase)source.getTrueSource();
         double vampirism = damager.getEntityAttribute(PropertiesRegistry.VAMPIRISM).getAttributeValue();
         if (vampirism > 0.0) {
            damager.heal((float)(damage * vampirism));
         } else if (vampirism < 0.0) {
            damager.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), (float)(-(damage * vampirism)));
         }
      }

      if (cancelOn0 && damage <= 0.0F) {
         event.setCanceled(true);
      }

      event.setAmount(damage);
   }

   @SubscribeEvent
   public static void onLivingKnockBack(LivingKnockBackEvent event) {
      if (event.getAttacker() instanceof EntityLivingBase) {
         event.setStrength(
            event.getStrength() + (float)((EntityLivingBase)event.getAttacker()).getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).getAttributeValue()
         );
      }
   }

   public static void onEntityLivingDeath(LivingDeathEvent event) {
      if (event.getSource().getTrueSource() instanceof EntityPlayer) {
         DamageSource source = event.getSource();
         EntityPlayer player = (EntityPlayer)source.getTrueSource();

         for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem() instanceof IItemKilling) {
               ((IItemKilling)stack.getItem()).onKillWithItem(stack, player, event.getEntityLiving(), source);
            }
         }

         IInventory inv = BaublesApi.getBaubles(player);

         for (int i = 0; i < 7; i++) {
            ItemStack bitem = inv.getStackInSlot(i);
            if (bitem.getItem() instanceof IItemKilling) {
               ((IItemKilling)bitem.getItem()).onKillWithItem(bitem, player, event.getEntityLiving(), source);
            }
         }

         ItemStack stackm = player.getHeldItemMainhand();
         ItemStack stacko = player.getHeldItemOffhand();
         if (stackm.getItem() instanceof IItemKilling) {
            ((IItemKilling)stackm.getItem()).onKillWithItem(stackm, player, event.getEntityLiving(), source);
         }

         if (stacko.getItem() instanceof IItemKilling) {
            ((IItemKilling)stacko.getItem()).onKillWithItem(stacko, player, event.getEntityLiving(), source);
         }
      }
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingAttackEvent event) {
      if (event.getEntityLiving() instanceof EntityPlayer) {
         DamageSource source = event.getSource();
         EntityPlayer player = (EntityPlayer)event.getEntityLiving();
         float damage = event.getAmount();

         for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem() instanceof IItemAttacked) {
               IItemAttacked itematt = (IItemAttacked)stack.getItem();
               damage = itematt.onAttackedWithItem(damage, stack, player, source);
               if (damage <= 0.0F && itematt.cancelOnNull()) {
                  event.setCanceled(true);
               }
            }
         }

         IInventory inv = BaublesApi.getBaubles(player);

         for (int i = 0; i < 7; i++) {
            ItemStack bitem = inv.getStackInSlot(i);
            if (bitem.getItem() instanceof IItemAttacked) {
               IItemAttacked itematt = (IItemAttacked)bitem.getItem();
               damage = itematt.onAttackedWithItem(damage, bitem, player, source);
               if (damage <= 0.0F && itematt.cancelOnNull()) {
                  event.setCanceled(true);
               }
            }
         }

         ItemStack stackm = player.getHeldItemMainhand();
         ItemStack stacko = player.getHeldItemOffhand();
         if (stackm.getItem() instanceof IItemAttacked) {
            IItemAttacked itematt = (IItemAttacked)stackm.getItem();
            damage = itematt.onAttackedWithItem(damage, stackm, player, source);
            if (damage <= 0.0F && itematt.cancelOnNull()) {
               event.setCanceled(true);
            }
         }

         if (stacko.getItem() instanceof IItemAttacked) {
            IItemAttacked itematt = (IItemAttacked)stacko.getItem();
            damage = itematt.onAttackedWithItem(damage, stacko, player, source);
            if (damage <= 0.0F && itematt.cancelOnNull()) {
               event.setCanceled(true);
            }
         }
      }
   }
}

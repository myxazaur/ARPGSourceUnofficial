//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Mana;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemEatable extends ItemFood {
   public int burntime = -1;
   public boolean beacon = false;
   public boolean ench = false;
   public int eatTime = 32;
   public boolean returnsItem = false;
   public ItemStack stackreturn = ItemStack.EMPTY;
   public boolean drink = false;
   public PotionEffect[] effectsToAdd = null;
   public float[] chances = null;
   public int radiation = 0;
   public float mana = 0.0F;
   public int burnSeconds = 0;

   public ItemEatable(
      String name,
      int maxdamage,
      int maxstacksize,
      int hunger,
      float saturat,
      boolean wolfFood,
      int eatTime,
      @Nullable PotionEffect[] effectsToAdd,
      @Nullable float[] chances,
      boolean drink,
      int radiation
   ) {
      super(hunger, saturat, wolfFood);
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.FOOD);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(maxstacksize);
      this.effectsToAdd = effectsToAdd;
      this.chances = chances;
      this.drink = drink;
      this.eatTime = eatTime;
      this.radiation = radiation;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return this.drink ? EnumAction.DRINK : EnumAction.EAT;
   }

   public ItemEatable setStackToReturn(ItemStack stack) {
      this.returnsItem = true;
      this.stackreturn = stack;
      return this;
   }

   public ItemEatable setMana(float mana) {
      this.mana = mana;
      return this;
   }

   public ItemEatable setBurn(int burnSeconds) {
      this.burnSeconds = burnSeconds;
      return this;
   }

   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
      if (!worldIn.isRemote) {
         entityLiving.curePotionEffects(stack);
      }

      if (entityLiving instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)entityLiving;
         entityplayer.getFoodStats().addStats(this, stack);
         if (!this.drink) {
            worldIn.playSound(
               (EntityPlayer)null,
               entityplayer.posX,
               entityplayer.posY,
               entityplayer.posZ,
               SoundEvents.ENTITY_PLAYER_BURP,
               SoundCategory.PLAYERS,
               0.5F,
               worldIn.rand.nextFloat() * 0.1F + 0.9F
            );
         }

         this.onFoodEaten(stack, worldIn, entityplayer);
         if (this.radiation != 0) {
            Mana.addRad(entityplayer, this.radiation, false);
         }

         if (this.mana != 0.0F) {
            Mana.giveMana(entityplayer, this.mana);
         }

         if (this.burnSeconds != 0) {
            entityplayer.setFire(this.burnSeconds);
         }

         entityplayer.addStat(StatList.getObjectUseStats(this));
         if (this.returnsItem) {
            entityplayer.addItemStackToInventory(this.stackreturn.copy());
         }

         if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
         }
      }

      stack.shrink(1);
      return stack;
   }

   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
      if (!worldIn.isRemote && this.effectsToAdd != null) {
         int size = this.effectsToAdd.length;

         for (int i = 0; i < size; i++) {
            PotionEffect eff = this.effectsToAdd[i];
            if (this.chances == null || worldIn.rand.nextFloat() < this.chances[i]) {
               player.addPotionEffect(new PotionEffect(eff));
            }
         }
      }
   }

   public int getMaxItemUseDuration(ItemStack stack) {
      return this.eatTime;
   }

   public ItemEatable setFuel(int time) {
      this.burntime = time;
      return this;
   }

   public ItemEatable setBeacon() {
      this.beacon = true;
      return this;
   }

   public ItemEatable setEnchantGlow() {
      this.ench = true;
      return this;
   }

   public int getItemBurnTime(ItemStack itemStack) {
      return this.burntime;
   }

   public boolean isBeaconPayment(ItemStack stack) {
      return this.beacon ? this.beacon : super.isBeaconPayment(stack);
   }

   public boolean hasEffect(ItemStack stack) {
      return this.ench ? this.ench : super.hasEffect(stack);
   }
}

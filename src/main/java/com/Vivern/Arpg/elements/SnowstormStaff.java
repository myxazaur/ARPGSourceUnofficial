//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.SnowstormEntity;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class SnowstormStaff extends Item {
   public SnowstormStaff() {
      this.setRegistryName("snowstorm_staff");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("snowstorm_staff");
      this.setMaxDamage(23);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.PASS, itemstack);
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         if (itemstack.getItemDamage() == itemIn.getMaxDamage()) {
            itemstack.setItemDamage(0);
            player.getCooldownTracker().setCooldown(this, 250 - rapidity * 40);
         }

         if (!click && itemstack.getItemDamage() > 0) {
            itemstack.setItemDamage(itemIn.getMaxDamage());
         }

         if (player.getActiveItemStack() == itemstack
            && mana > 5.0F
            && click
            && !player.getCooldownTracker().hasCooldown(itemIn)
            && itemstack.getItemDamage() < itemIn.getMaxDamage()) {
            if (itemstack.getItemDamage() == 1) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.snowstorm,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
            }

            player.addStat(StatList.getObjectUseStats(this));
            if (!player.capabilities.isCreativeMode) {
               itemstack.damageItem(1, player);
            }

            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack) / 5.0F);
               Mana.setManaSpeed(player, 0.001F);
            }

            if (!world.isRemote) {
               SnowstormEntity bolt = new SnowstormEntity(world, player, itemstack);
               bolt.shoot(player, player.rotationPitch, player.rotationYaw, 1.5F, 1.9F, 8.8F - acc);
               world.spawnEntity(bolt);
               SnowstormEntity bolt1 = new SnowstormEntity(world, player, itemstack);
               bolt1.shoot(player, player.rotationPitch, player.rotationYaw, 1.5F, 1.9F, 8.8F - acc);
               world.spawnEntity(bolt1);
               SnowstormEntity bolt2 = new SnowstormEntity(world, player, itemstack);
               bolt2.shoot(player, player.rotationPitch, player.rotationYaw, 1.5F, 1.9F, 8.8F - acc);
               world.spawnEntity(bolt2);
            }
         }
      }
   }
}

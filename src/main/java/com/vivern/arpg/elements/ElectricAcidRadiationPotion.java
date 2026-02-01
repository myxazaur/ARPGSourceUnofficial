package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityElectricAcidRadiationPotion;
import com.vivern.arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
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

public class ElectricAcidRadiationPotion extends Item {
   public ElectricAcidRadiationPotion() {
      this.setRegistryName("electric_acid_radiation_potion");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("electric_acid_radiation_potion");
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
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         if (player.getHeldItemMainhand() == itemstack && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.swosh_a,
               SoundCategory.AMBIENT,
               0.8F,
               0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
            );
            player.getCooldownTracker().setCooldown(this, 34);
            player.addStat(StatList.getObjectUseStats(this));
            if (!player.capabilities.isCreativeMode && damage <= itemIn.getMaxDamage() - 1) {
               player.getHeldItemMainhand().shrink(1);
            }

            if (!world.isRemote) {
               EntityElectricAcidRadiationPotion potion = new EntityElectricAcidRadiationPotion(world, player);
               potion.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 3.0F);
               world.spawnEntity(potion);
            }
         }
      }
   }
}

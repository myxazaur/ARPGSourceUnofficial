package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntitySunrise;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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

public class Sunrise extends Item {
   public Sunrise() {
      this.setRegistryName("sunrise");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("sunrise");
      this.setMaxDamage(10000);
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
         if (player.getActiveItemStack() == itemstack && mana > 1.1F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.fire,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F / (itemRand.nextFloat() * 0.4F + 0.8F)
            );
            player.getCooldownTracker().setCooldown(this, 5);
            player.addStat(StatList.getObjectUseStats(this));
            itemstack.damageItem(1, (EntityLivingBase)entityIn);
            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -1.1F);
               Mana.setManaSpeed(player, 0.001F);
            }

            if (!world.isRemote) {
               EntitySunrise entit = new EntitySunrise(world, player);
               entit.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.7F, 0.6F);
               world.spawnEntity(entit);
            }
         }
      }
   }
}

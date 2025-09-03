//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityFlyApple;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.lwjgl.input.Mouse;

@EventBusSubscriber(
   modid = "arpg"
)
public class ItemFirst extends Item {
   public ItemFirst() {
      this.setRegistryName("first_item");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("FirstItem");
      this.setMaxDamage(20);
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
         if (player.getActiveItemStack() == itemstack && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            if (damage <= 19) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.applecannon,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
               player.getCooldownTracker().setCooldown(this, 5);
               player.addStat(StatList.getObjectUseStats(this));
            }

            if (itemstack.getItemDamage() > 19 && player.inventory.hasItemStack(new ItemStack(Items.APPLE, 1))) {
               player.inventory.clearMatchingItems(new ItemStack(Items.APPLE, 1).getItem(), -1, 1, null);
               itemstack.setItemDamage(0);
               player.getCooldownTracker().setCooldown(this, 40);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  SoundEvents.BLOCK_BREWING_STAND_BREW,
                  SoundCategory.NEUTRAL,
                  0.5F,
                  0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
            }

            if (!player.capabilities.isCreativeMode && damage <= 19) {
               itemstack.damageItem(1, player);
            }

            if (!world.isRemote && damage <= 19) {
               EntityFlyApple apple = new EntityFlyApple(world, player);
               apple.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
               world.spawnEntity(apple);
            }
         }
      }
   }
}

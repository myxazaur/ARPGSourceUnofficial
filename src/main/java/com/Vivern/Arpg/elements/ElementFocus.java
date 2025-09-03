//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.ElementProjectile;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import java.util.ArrayList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

public class ElementFocus extends Item {
   public ElementFocus() {
      this.setRegistryName("element_focus");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("element_focus");
      this.setMaxDamage(10);
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

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   private void bom(World worldIn, EntityPlayer player, SoundEvent sound) {
      worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, sound, SoundCategory.AMBIENT, 1.0F, 1.0F);
      Booom.lastTick = 5;
      Booom.frequency = -0.6F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.22F;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         float reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         if (!worldIn.isRemote) {
            if (player.getHeldItemMainhand() == itemstack) {
               if (!itemstack.hasTagCompound()) {
                  NBTTagCompound itemCompound = new NBTTagCompound();
                  itemstack.setTagCompound(itemCompound);
               } else {
                  if (!itemstack.getTagCompound().hasKey("charge")) {
                     itemstack.getTagCompound().setInteger("charge", 0);
                  }

                  if (itemstack.getTagCompound().hasKey("charge") && player.getCooldownTracker().hasCooldown(itemIn)) {
                     itemstack.getTagCompound().setInteger("charge", itemstack.getTagCompound().getInteger("charge") + 1);
                  }
               }
            }

            if (player.getActiveItemStack() == itemstack && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
               ArrayList<Item> ammolist = new ArrayList<>();
               ammolist.add(ItemsRegister.ELAMMOAIR);
               ammolist.add(ItemsRegister.ELAMMOEARTH);
               ammolist.add(ItemsRegister.ELAMMOFIRE);
               ammolist.add(ItemsRegister.ELAMMOWATER);
               Item ammotype = FindAmmo.FindModulate(player.inventory, ammolist);
               if (ammotype != null) {
                  player.addStat(StatList.getObjectUseStats(this));
                  if (ammotype == ItemsRegister.ELAMMOAIR) {
                     ElementProjectile proj1 = new ElementProjectile(world, player, 1, itemstack);
                     proj1.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.5F, 0.2F - acclvl / 15.0F);
                     world.spawnEntity(proj1);
                     player.getCooldownTracker().setCooldown(this, 4 - rapidity);
                     this.bom(worldIn, player, Sounds.el_air);
                     if (!player.capabilities.isCreativeMode && Math.random() > 0.6 + reuse / 8.0F) {
                        player.inventory.clearMatchingItems(ammotype, -1, 1, null);
                     }
                  }

                  if (ammotype == ItemsRegister.ELAMMOWATER) {
                     ElementProjectile proj2 = new ElementProjectile(world, player, 4, itemstack);
                     proj2.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.3F, 0.3F - acclvl / 10.0F);
                     world.spawnEntity(proj2);
                     player.getCooldownTracker().setCooldown(this, 7 - rapidity);
                     this.bom(worldIn, player, Sounds.el_water);
                     if (Math.random() > reuse / 5.0F) {
                        player.inventory.clearMatchingItems(ammotype, -1, 1, null);
                     }
                  }

                  if (ammotype == ItemsRegister.ELAMMOFIRE) {
                     ElementProjectile proj2 = new ElementProjectile(world, player, 3, itemstack);
                     proj2.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.4F, 0.4F - acclvl / 7.5F);
                     world.spawnEntity(proj2);
                     player.getCooldownTracker().setCooldown(this, 6 - rapidity);
                     this.bom(worldIn, player, Sounds.el_fire);
                     if (!player.capabilities.isCreativeMode && Math.random() > reuse * 0.3) {
                        player.inventory.clearMatchingItems(ammotype, -1, 1, null);
                     }
                  }

                  if (ammotype == ItemsRegister.ELAMMOEARTH) {
                     ElementProjectile proj2 = new ElementProjectile(world, player, 2, itemstack);
                     proj2.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.3F, 0.3F - acclvl / 10.0F);
                     world.spawnEntity(proj2);
                     player.getCooldownTracker().setCooldown(this, 8 - rapidity);
                     this.bom(worldIn, player, Sounds.el_earth);
                     if (!player.capabilities.isCreativeMode && Math.random() > reuse / 5.0F) {
                        player.inventory.clearMatchingItems(ammotype, -1, 1, null);
                     }
                  }
               }
            }
         }
      }
   }
}

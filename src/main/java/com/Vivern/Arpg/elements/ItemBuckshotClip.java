//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBuckshotClip extends Item implements IItemColor {
   public final int maxAmmoStore;
   public boolean specialAmmo = false;

   public ItemBuckshotClip(String name, CreativeTabs tab, int maxstacksize, int maxAmmoStore) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxAmmoStore);
      this.setMaxStackSize(maxstacksize);
      this.maxAmmoStore = maxAmmoStore;
   }

   public ItemBuckshotClip(String name, CreativeTabs tab, int maxstacksize, int maxAmmoStore, boolean special) {
      this(name, tab, maxstacksize, maxAmmoStore);
      this.setHasSubtypes(special);
      this.specialAmmo = special;
   }

   public int colorMultiplier(ItemStack stack, int tintIndex) {
      ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
      return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
   }

   public double getDurabilityForDisplay(ItemStack stack) {
      if (this.specialAmmo) {
         return stack.getMetadata() == 0 ? 1.0 : 0.0;
      } else {
         return NBTHelper.GetNBTstring(stack, "bullet").isEmpty() ? 1.0 : super.getDurabilityForDisplay(stack);
      }
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (this.specialAmmo) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         String buckshotHas = hasBuckshots(player.inventory, this.maxAmmoStore);
         if (buckshotHas != null) {
            removeBuckshots(player.inventory, buckshotHas, this.maxAmmoStore);
            world.playSound(
               (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.vampireknifes, SoundCategory.PLAYERS, 0.5F, 1.0F
            );
            ItemStack newstack = new ItemStack(this);
            NBTHelper.GiveNBTstring(newstack, buckshotHas, "bullet");
            NBTHelper.SetNBTstring(newstack, buckshotHas, "bullet");
            if (!player.inventory.addItemStackToInventory(newstack) && !player.world.isRemote) {
               player.world
                  .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
            }

            itemstack.shrink(1);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
         } else {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }
      }
   }

   public static String hasBuckshots(InventoryPlayer inventory, int amount) {
      HashMap<String, Integer> map = new HashMap<>();

      for (int i = 0; i < 36; i++) {
         ItemStack stack = inventory.getStackInSlot(i);
         if (stack.getItem() == ItemsRegister.BUCKSHOT) {
            String name = NBTHelper.GetNBTstring(stack, "bullet");
            if (map.containsKey(name)) {
               int g = map.get(name);
               map.replace(name, g + stack.getCount());
            } else {
               map.put(name, stack.getCount());
            }

            if (map.get(name) >= amount) {
               return name;
            }
         }
      }

      return null;
   }

   public static void removeBuckshots(InventoryPlayer inventory, String nameTo, int amount) {
      for (int i = 0; i < 36; i++) {
         ItemStack stack = inventory.getStackInSlot(i);
         if (stack.getItem() == ItemsRegister.BUCKSHOT) {
            String name = NBTHelper.GetNBTstring(stack, "bullet");
            if (nameTo.equals(name)) {
               int removed = Math.min(stack.getCount(), amount);
               stack.shrink(removed);
               amount -= removed;
               if (amount <= 0) {
                  return;
               }
            }
         }
      }
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (this.specialAmmo) {
         tooltip.add("Ammo: " + (stack.getMetadata() == 0 ? "empty" : "full"));
      } else {
         tooltip.add("Ammo: " + NBTHelper.GetNBTstring(stack, "bullet"));
      }
   }
}

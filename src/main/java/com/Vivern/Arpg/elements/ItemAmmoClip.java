//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import java.util.HashMap;
import java.util.List;
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

public class ItemAmmoClip extends Item {
   public final int maxAmmoStore;
   public boolean specialAmmo = false;

   public ItemAmmoClip(String name, CreativeTabs tab, int maxstacksize, int maxAmmoStore) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxAmmoStore);
      this.setMaxStackSize(maxstacksize);
      this.maxAmmoStore = maxAmmoStore;
   }

   public ItemAmmoClip(String name, CreativeTabs tab, int maxstacksize, int maxAmmoStore, boolean special) {
      this(name, tab, maxstacksize, maxAmmoStore);
      this.setHasSubtypes(special);
      this.specialAmmo = special;
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
      } else if (!player.isSneaking()) {
         return this.fillWithAmmo(world, player, itemstack)
            ? new ActionResult(EnumActionResult.SUCCESS, itemstack)
            : new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         int i = 0;

         while (i < 64 && this.fillWithAmmo(world, player, itemstack)) {
            i++;
         }

         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
      }
   }

   public boolean fillWithAmmo(World world, EntityPlayer player, ItemStack itemstack) {
      if (!itemstack.isEmpty()) {
         HashMap<ItemBullet, Integer> counts = FindInstanceof(player.inventory);
         if (itemstack.getItemDamage() == this.maxAmmoStore || !itemstack.hasTagCompound()) {
            for (ItemBullet key : counts.keySet()) {
               int count = counts.get(key);
               if (count >= this.maxAmmoStore) {
                  player.inventory.clearMatchingItems(key, -1, this.maxAmmoStore, null);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.vampireknifes,
                     SoundCategory.PLAYERS,
                     0.5F,
                     1.0F
                  );
                  ItemStack newstack = new ItemStack(this);
                  NBTHelper.GiveNBTstring(newstack, key.getNbtName(), "bullet");
                  NBTHelper.SetNBTstring(newstack, key.getNbtName(), "bullet");
                  if (!player.inventory.addItemStackToInventory(newstack) && !player.world.isRemote) {
                     player.world
                        .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                  }

                  itemstack.shrink(1);
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static HashMap<ItemBullet, Integer> FindInstanceof(InventoryPlayer inventory) {
      HashMap<ItemBullet, Integer> map = new HashMap<>();

      for (int i = 0; i < 36; i++) {
         ItemStack stack = inventory.getStackInSlot(i);
         if (stack.getItem() instanceof ItemBullet) {
            ItemBullet stitem = (ItemBullet)stack.getItem();
            if (map.containsKey(stitem)) {
               int g = map.get(stitem);
               map.replace(stitem, g + stack.getCount());
            } else {
               map.put(stitem, stack.getCount());
            }
         }
      }

      return map;
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (this.specialAmmo) {
         tooltip.add("Ammo: " + (stack.getMetadata() == 0 ? "empty" : "full"));
      } else {
         tooltip.add("Ammo: " + NBTHelper.GetNBTstring(stack, "bullet"));
      }
   }
}

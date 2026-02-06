package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.loot.Treasure;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Sounds;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemLootCase extends Item {
   public int burntime = -1;
   public boolean beacon = false;
   public boolean ench = false;
   public List<Treasure> entries;
   public static List<Treasure> EMPTY_LIST = new ArrayList<>();

   public ItemLootCase(String name, CreativeTabs tab, int maxstacksize, Treasure[] entries) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.entries = Arrays.asList(entries);
   }

   public ItemLootCase(String name, CreativeTabs tab, int maxstacksize, List<Treasure> entries) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.entries = entries;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (!world.isRemote) {
         ItemStack gift = getStack(itemRand, this.entries);
         world.spawnEntity(new EntityItem(world, player.posX, player.posY + player.height / 2.0F, player.posZ, gift));
      }

      itemstack.shrink(1);
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.lootbox_unlock,
         SoundCategory.PLAYERS,
         0.8F,
         0.9F + itemRand.nextFloat() / 5.0F
      );
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }

   public static ItemStack getStack(Random rand, List<Treasure> entries) {
      Collections.shuffle(entries);

      for (Treasure treasure : entries) {
         if (rand.nextFloat() <= treasure.chance) {
            ItemStack st = new ItemStack(treasure.item, treasure.mincount + rand.nextInt(treasure.maxcount - treasure.mincount + 1), treasure.damage);
            st.setTagCompound(treasure.nbtTag);
            return st;
         }
      }

      Treasure treasure2 = entries.get(rand.nextInt(entries.size()));
      ItemStack st2 = new ItemStack(treasure2.item, treasure2.mincount + rand.nextInt(treasure2.maxcount - treasure2.mincount + 1), treasure2.damage);
      st2.setTagCompound(treasure2.nbtTag);
      return st2;
   }

   public ItemLootCase setFuel(int time) {
      this.burntime = time;
      return this;
   }

   public ItemLootCase setBeacon() {
      this.beacon = true;
      return this;
   }

   public ItemLootCase setEnchantGlow() {
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

   public static List<Treasure> entriesWeaponEnch() {
      List<Treasure> list = new ArrayList<>();
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.ACCURACY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.2F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.IMPULSE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.25F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.MIGHT, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.06F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.RAPIDITY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.15F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.RELOADING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.05F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.REUSE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.15F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.SORCERY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.04F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.SPECIAL, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.13F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.RANGE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(EnchantmentInit.WITCHERY, 1)));
      return list;
   }

   public static List<Treasure> entriesSimpleEnch() {
      List<Treasure> list = new ArrayList<>();
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.AQUA_AFFINITY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.BANE_OF_ARTHROPODS, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.01F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.BINDING_CURSE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.BLAST_PROTECTION, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.DEPTH_STRIDER, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.3F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.EFFICIENCY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FEATHER_FALLING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FIRE_ASPECT, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FIRE_PROTECTION, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FLAME, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FORTUNE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.FROST_WALKER, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.01F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.INFINITY, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.KNOCKBACK, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.LOOTING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.LUCK_OF_THE_SEA, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.LURE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.MENDING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.3F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.POWER, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.PROJECTILE_PROTECTION, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.3F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.PROTECTION, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.PUNCH, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.RESPIRATION, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.3F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.SHARPNESS, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.01F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.SILK_TOUCH, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.SMITE, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.03F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.SWEEPING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.01F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.THORNS, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.1F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.UNBREAKING, 1)));
      list.add(new Treasure(Items.ENCHANTED_BOOK, 0.01F, 0, 1, 1).setNbt(getEnchantmentNbt(Enchantments.VANISHING_CURSE, 1)));
      return list;
   }

   public static List<Treasure> entriesAllEnch() {
      List<Treasure> list = new ArrayList<>();

      for (Enchantment registered : Enchantment.REGISTRY) {
         list.add(new Treasure(Items.ENCHANTED_BOOK, registered.getRarity().getWeight() / 100.0F, 0, 1, 1).setNbt(getEnchantmentNbt(registered, 1)));
      }

      return list;
   }

   public static NBTTagCompound getEnchantmentNbt(Enchantment ench, int level) {
      NBTTagCompound tag = new NBTTagCompound();
      tag.setTag("StoredEnchantments", new NBTTagList());
      NBTTagList nbttaglist = tag.getTagList("StoredEnchantments", 10);
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setShort("id", (short)Enchantment.getEnchantmentID(ench));
      nbttagcompound.setShort("lvl", (byte)level);
      nbttaglist.appendTag(nbttagcompound);
      return tag;
   }
}

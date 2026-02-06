package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.IWeapon;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;

public class NBTHelper {
   public static void GiveNBTint(ItemStack itemstack, int baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         if (!itemCompound.hasKey(name)) {
            itemCompound.setInteger(name, baseValue);
         }

         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setInteger(name, baseValue);
      }
   }

   public static int GetNBTint(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getInteger(name) : 0;
   }

   public static void SetNBTint(ItemStack itemstack, int Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setInteger(name, Value);
      }
   }

   public static void AddNBTint(ItemStack itemstack, int Value, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound nbt = itemstack.getTagCompound();
         if (nbt.hasKey(name)) {
            nbt.setInteger(name, nbt.getInteger(name) + Value);
         }
      }
   }

   public static void GiveNBTboolean(ItemStack itemstack, boolean baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setBoolean(name, baseValue);
      }
   }

   public static boolean GetNBTboolean(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getBoolean(name) : false;
   }

   public static void SetNBTboolean(ItemStack itemstack, boolean Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setBoolean(name, Value);
      }
   }

   public static void ChangeNBTboolean(ItemStack itemstack, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound nbt = itemstack.getTagCompound();
         if (nbt.hasKey(name)) {
            if (nbt.getBoolean(name)) {
               nbt.setBoolean(name, false);
            } else {
               nbt.setBoolean(name, true);
            }
         }
      }
   }

   public static void GiveNBTdouble(ItemStack itemstack, double baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setDouble(name, baseValue);
      }
   }

   public static double GetNBTdouble(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getDouble(name) : 0.0;
   }

   public static void SetNBTdouble(ItemStack itemstack, double Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setDouble(name, Value);
      }
   }

   public static void AddNBTdouble(ItemStack itemstack, double Value, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound nbt = itemstack.getTagCompound();
         if (nbt.hasKey(name)) {
            nbt.setDouble(name, nbt.getDouble(name) + Value);
         }
      }
   }

   public static void GiveNBTfloat(ItemStack itemstack, float baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setFloat(name, baseValue);
      }
   }

   public static float GetNBTfloat(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getFloat(name) : 0.0F;
   }

   public static void SetNBTfloat(ItemStack itemstack, float Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setFloat(name, Value);
      }
   }

   public static void AddNBTfloat(ItemStack itemstack, float Value, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound nbt = itemstack.getTagCompound();
         if (nbt.hasKey(name)) {
            nbt.setFloat(name, nbt.getFloat(name) + Value);
         }
      }
   }

   public static void GiveNBTstring(ItemStack itemstack, String baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemCompound.setString(name, baseValue);
         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setString(name, baseValue);
      }
   }

   public static String GetNBTstring(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getString(name) : "";
   }

   public static void SetNBTstring(ItemStack itemstack, String Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setString(name, Value);
      }
   }

   public static void AddNBTstring(ItemStack itemstack, String Value, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound nbt = itemstack.getTagCompound();
         if (nbt.hasKey(name)) {
            nbt.setString(name, nbt.getString(name) + Value);
         }
      }
   }

   public static Item GetNBTitemFromString(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? Item.getByNameOrId(itemstack.getTagCompound().getString(name)) : null;
   }

   public static void GiveNBTBlockPos(ItemStack itemstack, BlockPos pos, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      }

      if (itemstack.hasTagCompound()) {
         String x = name + "x";
         String y = name + "y";
         String z = name + "z";
         NBTTagCompound tags = itemstack.getTagCompound();
         if (!tags.hasKey(x)) {
            tags.setInteger(x, pos.getX());
         }

         if (!tags.hasKey(y)) {
            tags.setInteger(y, pos.getY());
         }

         if (!tags.hasKey(z)) {
            tags.setInteger(z, pos.getZ());
         }
      }
   }

   public static BlockPos GetNBTBlockPos(ItemStack itemstack, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound tags = itemstack.getTagCompound();
         String x = name + "x";
         String y = name + "y";
         String z = name + "z";
         if (tags.hasKey(x) && tags.hasKey(y) && tags.hasKey(z)) {
            return new BlockPos(tags.getInteger(x), tags.getInteger(y), tags.getInteger(z));
         }
      }

      return null;
   }

   public static void SetNBTBlockPos(ItemStack itemstack, BlockPos pos, String name) {
      if (itemstack.hasTagCompound()) {
         String x = name + "x";
         String y = name + "y";
         String z = name + "z";
         NBTTagCompound tags = itemstack.getTagCompound();
         if (tags.hasKey(x)) {
            tags.setInteger(x, pos.getX());
         }

         if (tags.hasKey(y)) {
            tags.setInteger(y, pos.getY());
         }

         if (tags.hasKey(z)) {
            tags.setInteger(z, pos.getZ());
         }
      }
   }

   public static void GiveNBTtag(ItemStack itemstack, NBTTagCompound baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemCompound.setTag(name, baseValue);
         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setTag(name, baseValue);
      }
   }

   @Nullable
   public static NBTTagCompound GetNBTtag(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getCompoundTag(name) : null;
   }

   public static void SetNBTtag(ItemStack itemstack, NBTTagCompound Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setTag(name, Value);
      }
   }

   public static void WriteNBTSummonFilter(ItemStack itemstack, int mode, int number, boolean inverted, String stringdata, boolean hostile) {
      String filterType = hostile ? "filtershostile" : "filters";
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      }

      if (itemstack.hasTagCompound()) {
         if (!itemstack.getTagCompound().hasKey(filterType)) {
            itemstack.getTagCompound().setTag(filterType, new NBTTagCompound());
         }

         NBTTagCompound filtersTAG = itemstack.getTagCompound().getCompoundTag(filterType);
         NBTTagCompound filter = new NBTTagCompound();
         filter.setInteger("mode", mode);
         if (stringdata != null) {
            filter.setString("data", stringdata);
         }

         filter.setBoolean("inverted", inverted);
         filtersTAG.setTag("" + number, filter);
      }
   }

   @Nullable
   public static Predicate<EntityLivingBase> ReadNBTSummonFilter(ItemStack itemstack, int number, boolean hostile) {
      if (number < 0) {
         return null;
      } else {
         String filterType = hostile ? "filtershostile" : "filters";
         if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(filterType)) {
            NBTTagCompound filtersTAG = itemstack.getTagCompound().getCompoundTag(filterType);
            if (filtersTAG.hasKey("" + number)) {
               NBTTagCompound filter = filtersTAG.getCompoundTag("" + number);
               if (filter.hasKey("mode")) {
                  int mode = filter.getInteger("mode");
                  if (filter.hasKey("data") && filter.hasKey("inverted")) {
                     return IWeapon.getSummonerFilter(itemstack, mode, filter.getString("data"), filter.getBoolean("inverted"));
                  }
               }
            }
         }

         return null;
      }
   }

   public static int getNBTSummonFiltersCount(ItemStack itemstack, boolean hostile) {
      String filterType = hostile ? "filtershostile" : "filters";
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(filterType)) {
         NBTTagCompound filtersTAG = itemstack.getTagCompound().getCompoundTag(filterType);

         for (int i = 0; i < 99; i++) {
            if (!filtersTAG.hasKey("" + i)) {
               return i;
            }
         }
      }

      return 0;
   }

   public static void RemoveNBTSummonFilter(ItemStack itemstack, int number, boolean hostile) {
      String filterType = hostile ? "filtershostile" : "filters";
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(filterType)) {
         NBTTagCompound filtersTAG = itemstack.getTagCompound().getCompoundTag(filterType);
         if (filtersTAG.hasKey("" + number)) {
            filtersTAG.removeTag("" + number);

            while (filtersTAG.hasKey(++number + "")) {
               NBTTagCompound filter = filtersTAG.getCompoundTag("" + number);
               filtersTAG.removeTag("" + number);
               filtersTAG.setTag("" + (number - 1), filter);
            }
         }
      }
   }

   @Nullable
   public static Spell[] readSpellsFromNbt(ItemStack itemstack) {
      if (!itemstack.hasTagCompound()) {
         return null;
      } else {
         NBTTagCompound compound = itemstack.getTagCompound();
         Spell[] spells = new Spell[GetNBTint(itemstack, "spells_amount")];

         for (int i = 0; i < spells.length; i++) {
            int spellid = compound.getInteger("" + i);
            spells[i] = Spell.spellsRegistry.get(spellid);
         }

         return spells;
      }
   }

   public static void writeSpellsToNbt(ItemStack itemstack, Spell[] spells) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      }

      if (itemstack.hasTagCompound()) {
         NBTTagCompound compound = itemstack.getTagCompound();

         for (int i = 0; i < spells.length; i++) {
            compound.setInteger("" + i, spells[i].id);
         }

         compound.setInteger("spells_amount", spells.length);
      }
   }

   public static Spell[] readSpellsFromNbt(NBTTagCompound compound) {
      Spell[] spells = new Spell[compound.hasKey("spells_amount") ? compound.getInteger("spells_amount") : 0];

      for (int i = 0; i < spells.length; i++) {
         int spellid = compound.getInteger("" + i);
         spells[i] = Spell.spellsRegistry.get(spellid);
      }

      return spells;
   }

   public static void writeSpellsToNbt(NBTTagCompound compound, Spell[] spells) {
      for (int i = 0; i < spells.length; i++) {
         compound.setInteger("" + i, spells[i].id);
      }

      compound.setInteger("spells_amount", spells.length);
   }

   public static NBTTagList GetNbtTagList(ItemStack itemstack, String name, int typeOfTagsInList) {
      if (!itemstack.hasTagCompound()) {
         itemstack.setTagCompound(new NBTTagCompound());
      }

      NBTTagCompound nbtTagCompound = itemstack.getTagCompound();
      if (!nbtTagCompound.hasKey(name, 9)) {
         NBTTagList tagList = new NBTTagList();
         nbtTagCompound.setTag(name, tagList);
         return tagList;
      } else {
         return nbtTagCompound.getTagList(name, typeOfTagsInList);
      }
   }

   public static void GiveNBTlong(ItemStack itemstack, long baseValue, String name) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         if (!itemCompound.hasKey(name)) {
            itemCompound.setLong(name, baseValue);
         }

         itemstack.setTagCompound(itemCompound);
      } else if (!itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setLong(name, baseValue);
      }
   }

   public static long GetNBTlong(ItemStack itemstack, String name) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name) ? itemstack.getTagCompound().getLong(name) : 0L;
   }

   public static void SetNBTlong(ItemStack itemstack, long Value, String name) {
      if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(name)) {
         itemstack.getTagCompound().setLong(name, Value);
      }
   }

   public static void SetLore(ItemStack itemstack, String lore) {
      if (!itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = new NBTTagCompound();
         itemstack.setTagCompound(itemCompound);
      }

      NBTTagCompound itemCompound = itemstack.getTagCompound();
      if (!itemCompound.hasKey("display", 10)) {
         itemCompound.setTag("display", new NBTTagCompound());
      }

      NBTTagCompound display = itemCompound.getCompoundTag("display");
      if (!display.hasKey("Lore", 9)) {
         display.setTag("Lore", new NBTTagList());
      }

      if (display.getTagId("Lore") == 9) {
         NBTTagList nbttaglist3 = display.getTagList("Lore", 8);
         nbttaglist3.appendTag(new NBTTagString(lore));
      }
   }
}

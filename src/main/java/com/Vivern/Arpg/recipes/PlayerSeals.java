package com.Vivern.Arpg.recipes;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerSeals {
   public static void cloneSeals(EntityPlayer player, EntityPlayer playerNew) {
      if (player.getEntityData().hasKey("seals", 10)) {
         NBTTagCompound tag = player.getEntityData().getCompoundTag("seals");
         playerNew.getEntityData().setTag("seals", tag.copy());
      }
   }

   public static void saveSealToNBT(EntityPlayer player, Seal seal) {
   }

   public static boolean hasSeal(EntityPlayer player, Seal seal) {
      return player.getEntityData().hasKey("seals", 10) && player.getEntityData().getCompoundTag("seals").getBoolean(seal.name);
   }

   public ArrayList<Seal> getAllSeals(EntityPlayer player) {
      ArrayList<Seal> allseals = new ArrayList<>();
      if (player.getEntityData().hasKey("seals", 10)) {
         NBTTagCompound tag = player.getEntityData().getCompoundTag("seals");

         for (String name : tag.getKeySet()) {
            if (tag.getBoolean(name)) {
               Seal seal = Seal.sealRegistry.get(name);
               if (seal != null) {
                  allseals.add(seal);
               }
            }
         }
      }

      return allseals;
   }
}

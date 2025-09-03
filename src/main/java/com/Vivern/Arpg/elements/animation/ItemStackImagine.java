//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.animation;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackImagine {
   public ArrayList<SynchronizedField> fields = new ArrayList<>();

   public static ItemStackImagine getFromStack(ItemStack itemstack) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound tag = itemstack.getTagCompound();
         if (tag instanceof NBTTagReimagined) {
            return ((NBTTagReimagined)tag).getOrCreateImagine();
         } else {
            NBTTagReimagined tagReimagined = new NBTTagReimagined();
            tagReimagined.merge(tag);
            itemstack.setTagCompound(tagReimagined);
            return tagReimagined.getOrCreateImagine();
         }
      } else {
         NBTTagReimagined tagReimagined = new NBTTagReimagined();
         itemstack.setTagCompound(tagReimagined);
         return tagReimagined.getOrCreateImagine();
      }
   }

   public static enum EnumSynchronize {
      NONE,
      FROM_SERVER_TO_CLIENT,
      FROM_CLIENT_TO_SERVER;
   }

   public static class SynchronizedField {
      public EnumSynchronize synchronizeType;
   }

   public static class SynchronizedFloat extends SynchronizedField {
      public float value;
   }
}

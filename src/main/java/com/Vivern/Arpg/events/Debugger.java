package com.Vivern.Arpg.events;

import com.Vivern.Arpg.elements.SoulStone;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.OreDicHelper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector4f;

public class Debugger {
   public static float[] floats = new float[]{
      0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F
   };
   public static boolean profilerBounderMode = true;
   public static int[] bounder = new int[]{
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
   };
   public static long[] profTimes = new long[]{
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L
   };
   public static long[] profTimesAdd = new long[]{
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L,
      0L
   };
   public static int boundValue = 1;
   public static String string = "";
   public static boolean itemTransformHookEnabled = false;
   public static HashMap<String, Vector4f> debugColors = new HashMap<>();
   static Vector4f zero4f = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
   public static boolean press = false;

   public static Vector4f getDebugColor(String name) {
      return debugColors.containsKey(name) ? debugColors.get(name) : zero4f;
   }

   public static final void startPROFILING(int number) {
      if (profilerBounderMode) {
         if (bounder[number] < boundValue) {
            profTimes[number] = System.nanoTime();
         }
      } else {
         profTimes[number] = System.nanoTime();
      }
   }

   public static final void startPROFILING() {
      profTimes[0] = System.nanoTime();
   }

   public static final void endPROFILING(int number) {
      if (profilerBounderMode) {
         if (bounder[number] < boundValue) {
            System.out.println("TIME " + number + " : " + (System.nanoTime() - profTimes[number]));
            bounder[number]++;
         }
      } else {
         System.out.println("TIME " + number + " : " + (System.nanoTime() - profTimes[number]));
      }
   }

   public static final void endPROFILINGandADD(int number) {
      profTimesAdd[number] = profTimesAdd[number] + (System.nanoTime() - profTimes[number]);
   }

   public static final void endPROFILINGandPRINT(int number) {
      System.out.println("TIME " + number + " : " + profTimesAdd[number]);
   }

   public static final void allPROFILINGandPRINT() {
      for (int i = 0; i < profTimesAdd.length; i++) {
         if (profTimesAdd[i] != 0L) {
            endPROFILINGandPRINT(i);
         }
      }
   }

   public static final void resetPROFILING() {
      for (int i = 0; i < bounder.length; i++) {
         bounder[i] = 0;
      }

      for (int i = 0; i < profTimesAdd.length; i++) {
         profTimesAdd[i] = 0L;
      }
   }

   public static void checkChest(EntityPlayer player) {
   }

   public static String getAsIngridient(ItemStack st, boolean formatToItemStack) throws IllegalArgumentException, IllegalAccessException {
      String text = "";
      boolean shouldOredig = true;
      if (st.isEmpty()) {
         return "EM";
      } else if (st.getItem() instanceof SoulStone) {
         int soul = SoulStone.getSoul(st);
         return "SoulStone.getSouledStack(" + soul + ")";
      } else {
         String getter = itemgetter(st.getItem());
         if (getter != null) {
            return formatToItemStack ? formatingridient(getter, st.getCount(), st.getMetadata()) : getter;
         } else if (OreDicHelper.hasOreName(st)) {
            List<String> nams = OreDicHelper.getOreNames(st);
            return !nams.isEmpty() ? "OreDicHelper.getOrNull(\"" + nams.get(0) + "\", " + st.getCount() + ")" : "EXEPTION";
         } else {
            String itemmm = "Item.getByNameOrId(\"" + st.getItem().getRegistryName().toString() + "\")";
            return formatToItemStack ? formatingridient(itemmm, st.getCount(), st.getMetadata()) : itemmm;
         }
      }
   }

   public static String formatingridient(String item, int count, int meta) {
      if (count != 0 && meta == 0) {
         return "new ItemStack(" + item + ", " + count + ")";
      } else {
         return count == 0 && meta == 0 ? "new ItemStack(" + item + ")" : "new ItemStack(" + item + ", " + count + ", " + meta + ")";
      }
   }

   public static String itemgetter(Item item) throws IllegalArgumentException, IllegalAccessException {
      if (item instanceof ItemBlock) {
         if (item.getRegistryName().getNamespace().equals("minecraft")) {
            Field[] fields = Blocks.class.getFields();

            for (Field f : fields) {
               if (f.get(null) instanceof Block) {
                  Block block = (Block)f.get(null);
                  if (Item.getItemFromBlock(block) == item) {
                     return "Blocks." + f.getName();
                  }
               }
            }
         } else {
            if (!item.getRegistryName().getNamespace().equals("arpg")) {
               return null;
            }

            Field[] fields = BlocksRegister.class.getFields();

            for (Field fx : fields) {
               if (fx.get(null) instanceof Block) {
                  Block block = (Block)fx.get(null);
                  if (Item.getItemFromBlock(block) == item) {
                     return "BlocksRegister." + fx.getName();
                  }
               }
            }
         }
      } else if (item.getRegistryName().getNamespace().equals("minecraft")) {
         Field[] fields = Items.class.getFields();

         for (Field fxx : fields) {
            if (fxx.get(null) == item) {
               return "Items." + fxx.getName();
            }
         }
      } else {
         if (!item.getRegistryName().getNamespace().equals("arpg")) {
            return null;
         }

         Field[] fields = ItemsRegister.class.getFields();

         for (Field fxxx : fields) {
            if (fxxx.get(null) == item) {
               return "ItemsRegister." + fxxx.getName();
            }
         }
      }

      return null;
   }

   public static void setDebugSourceDestFactors() {
      GlStateManager.blendFunc(
         SourceFactor.values()[MathHelper.clamp((int)floats[0], 0, SourceFactor.values().length - 1)],
         DestFactor.values()[MathHelper.clamp((int)floats[1], 0, DestFactor.values().length - 1)]
      );
   }

   static {
      debugColors.put("pop", new Vector4f(1.0F, 0.3F, 0.2F, 1.0F));
      debugColors.put("t", new Vector4f(0.1F, 0.0F, 1.0F, 1.0F));
      debugColors.put("au", new Vector4f(0.1F, 1.0F, 0.9F, 1.0F));
   }
}

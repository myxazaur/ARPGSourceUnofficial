//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.recipes.Ingridient;
import com.Vivern.Arpg.recipes.NetherMelterRecipe;
import com.Vivern.Arpg.tileentity.TileManaBottle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NetherMelterRecipesRegister {
   public static final Ingridient EM = Ingridient.EMPTY;
   public static List<NetherMelterRecipe> allRecipes = new ArrayList<>();

   @Nullable
   public static NetherMelterRecipe addRecipe(
      Ingridient output5,
      Ingridient output6,
      Ingridient output7,
      float mana,
      Ingridient input0,
      Ingridient input1,
      Ingridient input2,
      Ingridient input3,
      Ingridient input4
   ) {
      List<Ingridient> listoutput = new ArrayList<>();
      if (output5 != Ingridient.EMPTY) {
         listoutput.add(output5);
      }

      if (output6 != Ingridient.EMPTY) {
         listoutput.add(output6);
      }

      if (output7 != Ingridient.EMPTY) {
         listoutput.add(output7);
      }

      List<Ingridient> listinput = new ArrayList<>();
      if (input0 != Ingridient.EMPTY) {
         listinput.add(input0);
      }

      if (input1 != Ingridient.EMPTY) {
         listinput.add(input1);
      }

      if (input2 != Ingridient.EMPTY) {
         listinput.add(input2);
      }

      if (input3 != Ingridient.EMPTY) {
         listinput.add(input3);
      }

      if (input4 != Ingridient.EMPTY) {
         listinput.add(input4);
      }

      return addRecipe(listoutput.toArray(new Ingridient[listoutput.size()]), mana, listinput.toArray(new Ingridient[listinput.size()]));
   }

   @Nullable
   public static NetherMelterRecipe addRecipe(ItemStack[] outputSt, float mana, ItemStack[] inputSt) {
      Ingridient[] output = new Ingridient[outputSt.length];
      Ingridient[] input = new Ingridient[inputSt.length];

      for (int i = 0; i < output.length; i++) {
         if (outputSt[i] == null) {
            return null;
         }

         output[i] = Ingridient.getIngridient(outputSt[i]);
      }

      for (int i = 0; i < input.length; i++) {
         if (inputSt[i] == null) {
            return null;
         }

         input[i] = Ingridient.getIngridient(inputSt[i]);
      }

      return addRecipe(output, mana, input);
   }

   @Nullable
   public static NetherMelterRecipe addRecipe(Ingridient[] output, float mana, Ingridient[] input) {
      for (Ingridient st : input) {
         if (st == null) {
            return null;
         }
      }

      for (Ingridient stx : output) {
         if (stx == null) {
            return null;
         }
      }

      NetherMelterRecipe recipe = new NetherMelterRecipe(output, mana, input);
      setregister(recipe);
      return recipe;
   }

   public static void register() {
      addRecipe(
         new Ingridient.IngridientItem("arpg:mushrooms_in_sauce", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         4.0F,
         new Ingridient.IngridientItem("minecraft:mushroom_stew", 1, 0, false),
         new Ingridient.IngridientItem("arpg:moonshroom_meat", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:chicken", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:milk_bucket", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:smoked_sausage", 4, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("minecraft:porkchop", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:beef", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:chicken", 1, 0, false),
         new Ingridient.IngridientItem("arpg:raw_ribs", 1, 0, false),
         new Ingridient.IngridientItem("dustSalt", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hot_spicy_ribs", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:raw_ribs", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sugar", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 1, 0, false),
         new Ingridient.IngridientItem("arpg:red_pepper", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:borsch", 2, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:meat_broth", 2, 0, false),
         new Ingridient.IngridientItem("cropBeetroot", 4),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("cropPotato", 1),
         new Ingridient.IngridientItem("cropCarrot", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 4, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 4, 0, false),
         new Ingridient.IngridientItem("minecraft:porkchop", 1, 0, false),
         new Ingridient.IngridientItem("dustSalt", 1),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 4, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 4, 0, false),
         new Ingridient.IngridientItem("minecraft:beef", 1, 0, false),
         new Ingridient.IngridientItem("dustSalt", 1),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 3, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 3, 0, false),
         new Ingridient.IngridientItem("minecraft:chicken", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 6, 0, false),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 2, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 2, 0, false),
         new Ingridient.IngridientItem("minecraft:rabbit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 4, 0, false),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 3, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 3, 0, false),
         new Ingridient.IngridientItem("arpg:raw_ribs", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 6, 0, false),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:meat_broth", 3, 0, false),
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         EM,
         3.0F,
         new Ingridient.IngridientItem("minecraft:bowl", 3, 0, false),
         new Ingridient.IngridientItem("minecraft:mutton", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 6, 0, false),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_diavola", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("arpg:smoked_sausage", 1, 0, false),
         new Ingridient.IngridientItem("arpg:red_pepper", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_chicken", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:chicken", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:brown_mushroom", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_toxedge", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:toxedge_dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("arpg:contemplant_seed", 1, 0, false),
         new Ingridient.IngridientItem("arpg:weeping_toxiberry_seed", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_cheeze", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 3, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_glowing", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("arpg:pale_meat_raw", 1, 0, false),
         new Ingridient.IngridientItem("arpg:blue_glowing_mushroom", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pizza_seafood", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:dough", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mozzarella", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("arpg:fish_steak_raw", 1, 0, false),
         new Ingridient.IngridientItem("arpg:seaweed_block", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:stuffed_fiery_bean", 1, 0, false),
         EM,
         EM,
         4.0F,
         new Ingridient.IngridientItem("arpg:fiery_bean", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:mutton", 1, 0, false),
         new Ingridient.IngridientItem("arpg:salt_grains", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cherry_tomato", 1, 0, false),
         new Ingridient.IngridientItem("cropBeetroot", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:lead_bearing", 2, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotLead", 2),
         new Ingridient.IngridientItem("ingotTin", 1),
         new Ingridient.IngridientItem("ingotCopper", 1),
         new Ingridient.IngridientItem("ingotSteel", 2),
         new Ingridient.IngridientItem("materialRubber", 2)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         new Ingridient.IngridientItem("ingotLead", 1),
         new Ingridient.IngridientItem("ingotCopper", 1),
         new Ingridient.IngridientItem("ingotSteel", 2),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:molten_ingot", 1, 0, false),
         EM,
         EM,
         20.0F,
         new Ingridient.IngridientItem("arpg:molten_ore", 1, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:molten_ingot", 1, 0, false),
         EM,
         EM,
         5.0F,
         new Ingridient.IngridientItem("arpg:molten_nugget", 9, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientSoulStone(0),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("arpg:demonite", 1, 0, false),
         new Ingridient.IngridientItem("arpg:rhinestone", 2, 0, false),
         new Ingridient.IngridientItem("minecraft:soul_sand", 4, 0, false),
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:demonite", 1, 0, false),
         EM,
         EM,
         20.0F,
         new Ingridient.IngridientItem("arpg:demonite_shard", 4, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:chemical_glass", 6, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("blockGlassColorless", 1),
         new Ingridient.IngridientItem("ingotLead", 1),
         new Ingridient.IngridientItem("gemQuartz", 4),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("ingotElectrum", 2),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotSilver", 1),
         new Ingridient.IngridientItem("ingotGold", 1),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("ingotBronze", 4),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotCopper", 3),
         new Ingridient.IngridientItem("ingotTin", 1),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:vial_empty", 4, 0, false),
         EM,
         EM,
         8.0F,
         new Ingridient.IngridientItem("minecraft:soul_sand", 1, 0, false),
         new Ingridient.IngridientItem("arpg:rhinestone", 1, 0, false),
         new Ingridient.IngridientItem("arpg:demonite_shard", 1, 0, false),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:retort", 1, 0, false),
         EM,
         EM,
         16.0F,
         new Ingridient.IngridientItem("ingotGold", 1),
         new Ingridient.IngridientItem("blockGlassColorless", 1),
         new Ingridient.IngridientItem("arpg:rhinestone", 6, 0, false),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:glass_heart", 1, 0, false),
         EM,
         EM,
         20.0F,
         new Ingridient.IngridientSoulStone(8),
         new Ingridient.IngridientItem("gemCitrine", 1),
         new Ingridient.IngridientItem("nuggetGold", 1),
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:mana_pump", 1, 0, false),
         EM,
         EM,
         18.0F,
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("gemRhinestone", 2),
         new Ingridient.IngridientItem("ingotSilver", 1),
         new Ingridient.IngridientItem("gemAmethyst", 1),
         new Ingridient.IngridientItem("gemTopaz", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:runic_mirror", 1, 0, false),
         EM,
         EM,
         40.0F,
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("ingotGold", 4),
         new Ingridient.IngridientItem("arpg:stained_glass_marigold", 1, 0, false),
         new Ingridient.IngridientSoulStone(7),
         EM
      );
      NBTTagCompound compound2 = new NBTTagCompound();
      NBTTagCompound compound3 = new NBTTagCompound();
      compound2.setFloat("max", TileManaBottle.CAPACITY_MANABOTTLE_TIER_2);
      compound3.setFloat("max", TileManaBottle.CAPACITY_MANABOTTLE_TIER_3);
      NBTTagCompound itemcompound2 = new NBTTagCompound();
      NBTTagCompound itemcompound3 = new NBTTagCompound();
      itemcompound2.setTag("BlockEntityTag", compound2);
      itemcompound3.setTag("BlockEntityTag", compound3);
      addRecipe(
         new Ingridient.IngridientItem("arpg:mana_bottle", 1, 0, false),
         new Ingridient.IngridientSoulStone(0),
         EM,
         10.0F,
         new Ingridient.IngridientItem("blockGlassColorless", 1),
         new Ingridient.IngridientItem("gemRhinestone", 5),
         new Ingridient.IngridientItem("ingotSilver", 2),
         new Ingridient.IngridientSoulStone(1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:mana_bottle", 1, 0, false).setNbt(itemcompound2),
         new Ingridient.IngridientSoulStone(0),
         EM,
         15.0F,
         new Ingridient.IngridientItem("blockGlassColorless", 1),
         new Ingridient.IngridientItem("gemRhinestone", 8),
         new Ingridient.IngridientItem("ingotSilver", 3),
         new Ingridient.IngridientSoulStone(4),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:mana_bottle", 1, 0, false).setNbt(itemcompound3),
         new Ingridient.IngridientSoulStone(0),
         EM,
         20.0F,
         new Ingridient.IngridientItem("blockGlassColorless", 1),
         new Ingridient.IngridientItem("gemRhinestone", 11),
         new Ingridient.IngridientItem("ingotSilver", 4),
         new Ingridient.IngridientSoulStone(9),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:block_crystal_sphere", 1, 0, false),
         EM,
         EM,
         20.0F,
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("gemRhinestone", 4),
         new Ingridient.IngridientSoulStone(7),
         new Ingridient.IngridientItem("nuggetSilver", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotBeryllium", 1),
         new Ingridient.IngridientItem("minecraft:chorus_fruit_popped", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotEnderium", 1),
         new Ingridient.IngridientItem("minecraft:chorus_fruit_popped", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("ingotPulsatingIron", 1),
         new Ingridient.IngridientItem("minecraft:chorus_fruit_popped", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         EM,
         EM,
         40.0F,
         new Ingridient.IngridientItem("arpg:adamantium_ore", 1, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         EM,
         EM,
         40.0F,
         new Ingridient.IngridientItem("arpg:adamantium_dust", 1, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("arpg:titanium_slag", 2, 0, false),
         new Ingridient.IngridientItem("arpg:stone_dust", 1, 0, false),
         25.0F,
         new Ingridient.IngridientItem("arpg:titanium_ore", 1, 0, false),
         new Ingridient.IngridientItem("itemCoal", 1),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("arpg:titanium_slag", 2, 0, false),
         new Ingridient.IngridientItem("arpg:radioactive_dust", 1, 0, false),
         25.0F,
         new Ingridient.IngridientItem("arpg:radioactive_titanium_ore", 1, 0, false),
         new Ingridient.IngridientItem("itemCoal", 1),
         EM,
         EM,
         EM
      );
      addRecipe(new Ingridient.IngridientItem("ingotTitanium", 1), EM, EM, 20.0F, new Ingridient.IngridientItem("dustTitanium", 1), EM, EM, EM, EM);
      addRecipe(
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         EM,
         EM,
         150.0F,
         new Ingridient.IngridientItem("arpg:stormbrass_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 2, 0, false),
         new Ingridient.IngridientItem("arpg:wolfram_dust", 1, 0, false),
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:topazitron_crystal", 1, 0, false),
         EM,
         EM,
         100.0F,
         new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false),
         new Ingridient.IngridientItem("arpg:pink_arthrostelecha_rod", 4, 0, false),
         new Ingridient.IngridientItem("gemTopaz", 9),
         new Ingridient.IngridientItem("dustRedstone", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         EM,
         EM,
         30.0F,
         new Ingridient.IngridientItem("arpg:stormsteel_dust", 1, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         EM,
         EM,
         30.0F,
         new Ingridient.IngridientItem("arpg:stormsteel_ore", 1, 0, false),
         EM,
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:rust_metal", 2, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("arpg:radioactive_cobblestone", 2, 0, false),
         new Ingridient.IngridientItem("arpg:scrap_metal", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:dark_rust_metal", 2, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("arpg:rust_metal", 1, 0, false),
         new Ingridient.IngridientItem("obsidian", 1),
         new Ingridient.IngridientItem("ingotLead", 1),
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:lab_plating", 2, 0, false),
         EM,
         EM,
         5.0F,
         new Ingridient.IngridientItem("arpg:rust_metal", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:copper_transformer", 1, 0, false),
         EM,
         EM,
         5.0F,
         new Ingridient.IngridientItem("arpg:copper_wire", 8, 0, false),
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("ingotSilicium", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gold_transformer", 1, 0, false),
         EM,
         EM,
         10.0F,
         new Ingridient.IngridientItem("arpg:gold_wire", 8, 0, false),
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("ingotManganese", 1),
         new Ingridient.IngridientItem("ingotSilicium", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:silver_transformer", 1, 0, false),
         EM,
         EM,
         15.0F,
         new Ingridient.IngridientItem("arpg:silver_wire", 8, 0, false),
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         new Ingridient.IngridientItem("ingotSilicium", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:glass_bottle", 20, 0, false),
         EM,
         EM,
         5.0F,
         new Ingridient.IngridientItem("dustQuartz", 2),
         new Ingridient.IngridientItem("gemRhinestone", 1),
         EM,
         EM,
         EM
      );
   }

   public static void setregister(NetherMelterRecipe rec) {
      allRecipes.add(rec);
   }
}

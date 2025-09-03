//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AssemblyTableRecipesRegister {
   public static int TURNING = 1;
   public static int PRESS = 2;
   public static int WELD = 3;
   public static int PLASMA = 4;
   public static int MOLECULAR = 5;
   public static float craftTimeMultiplier = 1.0F;
   public static final Ingridient EM = Ingridient.EMPTY;
   public static List<AssemblyTableRecipe> allRecipes = new ArrayList<>();

   public static AssemblyTableRecipe addRecipe(
      ItemStack craftresult,
      int RFtoAll,
      ItemStack craftresult2,
      int timeToCraft,
      ItemStack slot0,
      ItemStack slot1,
      ItemStack slot2,
      ItemStack slot3,
      ItemStack slot4,
      ItemStack slot5,
      ItemStack slot6,
      ItemStack slot7,
      ItemStack slot8,
      AssemblyTableRecipe.AugmentCost cost1,
      AssemblyTableRecipe.AugmentCost cost2,
      AssemblyTableRecipe.AugmentCost cost3,
      AssemblyTableRecipe.AugmentCost cost4,
      AssemblyTableRecipe.AugmentCost cost5
   ) {
      if (slot0 != null
         && slot1 != null
         && slot2 != null
         && slot3 != null
         && slot4 != null
         && slot5 != null
         && slot6 != null
         && slot7 != null
         && slot8 != null
         && craftresult != null
         && craftresult2 != null) {
         ArrayList<AssemblyTableRecipe.AugmentCost> listcosts = new ArrayList<>();
         if (cost1 != null && cost1.ingridient != null) {
            listcosts.add(cost1);
         }

         if (cost2 != null && cost2.ingridient != null) {
            listcosts.add(cost2);
         }

         if (cost3 != null && cost3.ingridient != null) {
            listcosts.add(cost3);
         }

         if (cost4 != null && cost4.ingridient != null) {
            listcosts.add(cost4);
         }

         if (cost5 != null && cost5.ingridient != null) {
            listcosts.add(cost5);
         }

         AssemblyTableRecipe recipe = new AssemblyTableRecipe(
            true,
            new Ingridient[]{
               Ingridient.getIngridient(slot0),
               Ingridient.getIngridient(slot1),
               Ingridient.getIngridient(slot2),
               Ingridient.getIngridient(slot3),
               Ingridient.getIngridient(slot4),
               Ingridient.getIngridient(slot5),
               Ingridient.getIngridient(slot6),
               Ingridient.getIngridient(slot7),
               Ingridient.getIngridient(slot8)
            },
            Ingridient.getIngridient(craftresult),
            Ingridient.getIngridient(craftresult2),
            (int)(timeToCraft * craftTimeMultiplier),
            RFtoAll
         );
         if (!listcosts.isEmpty()) {
            recipe.setAugmentsRecipe(listcosts);
         }

         setregister(recipe);
         return recipe;
      } else {
         return new AssemblyTableRecipe(true, new Ingridient[]{EM, EM, EM, EM, EM, EM, EM, EM, EM}, EM, EM, 1, 1);
      }
   }

   public static AssemblyTableRecipe addRecipe(
      Ingridient craftresult,
      int RFtoAll,
      Ingridient craftresult2,
      int timeToCraft,
      Ingridient slot0,
      Ingridient slot1,
      Ingridient slot2,
      Ingridient slot3,
      Ingridient slot4,
      Ingridient slot5,
      Ingridient slot6,
      Ingridient slot7,
      Ingridient slot8,
      AssemblyTableRecipe.AugmentCost... costs
   ) {
      if (slot0 != null
         && slot1 != null
         && slot2 != null
         && slot3 != null
         && slot4 != null
         && slot5 != null
         && slot6 != null
         && slot7 != null
         && slot8 != null
         && craftresult != null
         && craftresult2 != null) {
         if (costs != null) {
            for (AssemblyTableRecipe.AugmentCost cost : costs) {
               if (cost.ingridient == null) {
                  return new AssemblyTableRecipe(true, new Ingridient[]{EM, EM, EM, EM, EM, EM, EM, EM, EM}, EM, EM, 1, 1);
               }
            }
         }

         AssemblyTableRecipe recipe = new AssemblyTableRecipe(
            true,
            new Ingridient[]{slot0, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8},
            craftresult,
            craftresult2,
            (int)(timeToCraft * craftTimeMultiplier),
            RFtoAll
         );
         recipe.setAugmentsRecipe(costs);
         setregister(recipe);
         return recipe;
      } else {
         return new AssemblyTableRecipe(true, new Ingridient[]{EM, EM, EM, EM, EM, EM, EM, EM, EM}, EM, EM, 1, 1);
      }
   }

   public static void register() {
      Item it = ItemsRegister.BATTERYLEADACID;
      int bulletCT = 20;
      craftTimeMultiplier = 1.5F;
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_lead", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_lead", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotLead", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_copper", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_copper", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotCopper", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_silver", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_silver", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSilver", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_gold", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_gold", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotGold", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_exploding", 24, 0, false),
         2000,
         EM,
         bulletCT,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("minecraft:tnt", 1, 0, false),
         new Ingridient.IngridientItem("nuggetIron", 1),
         EM,
         new Ingridient.IngridientItem("arpg:bullet_lead", 24, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_frozen", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_frozen", 32, 0, false),
         bulletCT,
         EM,
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotIron", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_incendiary", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_incendiary", 32, 0, false),
         bulletCT,
         EM,
         new Ingridient.IngridientItem("arpg:molten_nugget", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotBrickNether", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_poisonous", 64, 0, false),
         2000,
         new Ingridient.IngridientItem("arpg:bullet_poisonous", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_thunder", 64, 0, false),
         8000,
         new Ingridient.IngridientItem("arpg:bullet_thunder", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_toxic", 64, 0, false),
         4000,
         new Ingridient.IngridientItem("arpg:bullet_toxic", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_adamantium", 64, 0, false),
         6000,
         new Ingridient.IngridientItem("arpg:bullet_adamantium", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_niveous", 64, 0, false),
         3200,
         new Ingridient.IngridientItem("arpg:bullet_niveous", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:niveolite", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_festival", 48, 0, false),
         2000,
         EM,
         bulletCT,
         EM,
         new Ingridient.IngridientItem("arpg:bullet_copper", 12, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:bullet_silver", 12, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 1, 0, false),
         new Ingridient.IngridientItem("arpg:bullet_gold", 12, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:bullet_lead", 12, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bullet_coral", 64, 0, false),
         6400,
         new Ingridient.IngridientItem("arpg:bullet_coral", 32, 0, false),
         bulletCT,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:coral", 4, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:common_rocket", 15, 0, false),
         4000,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotIron", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:napalm_rocket", 15, 0, false),
         4000,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("arpg:liquid_fire", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:void_rocket", 15, 0, false),
         6400,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("arpg:void_crystal", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:demolishing_rocket", 3, 0, false),
         800,
         EM,
         40,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("minecraft:tnt", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:common_rocket", 3, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:mining_rocket", 15, 0, false),
         4000,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("itemFlint", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotBrass", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:shell_rocket", 15, 0, false),
         4000,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("arpg:seashell", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:sea_urchin", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:surprise_rocket", 15, 0, false),
         6400,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("arpg:gift", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotGold", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("gunpowder", 5),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:modern_arrow", 24, 0, false),
         2400,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("dyeRed", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("nuggetZinc", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:firejet_arrow", 24, 0, false),
         2000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("arpg:liquid_fire", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("itemBlazePowder", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:frozen_arrow", 24, 0, false),
         2000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:conifer_stick", 4, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:conifer_leaves", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bengal_arrow", 24, 0, false),
         2600,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("dustGlowstone", 1),
         EM,
         new Ingridient.IngridientItem("dustAluminum", 1),
         new Ingridient.IngridientItem("arpg:conifer_stick", 4, 0, false),
         new Ingridient.IngridientItem("gunpowder", 1),
         new Ingridient.IngridientItem("treeLeaves", 1),
         new Ingridient.IngridientItem("arpg:gift", 1, 0, false),
         new Ingridient.IngridientItem("treeLeaves", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bouncing_arrow", 24, 0, false),
         500,
         EM,
         40,
         EM,
         new Ingridient.IngridientItem("arpg:white_slimeball", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("minecraft:arrow", 24, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("materialPlastic", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:twin_arrow", 24, 0, false),
         48000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:frag_grenade", 8, 0, false),
         4000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("ingotIron", 1),
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         new Ingridient.IngridientItem("itemFlint", 1),
         new Ingridient.IngridientItem("gunpowder", 2),
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:bomb", 2, 0, false),
         1000,
         EM,
         50,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("minecraft:tnt", 2, 0, false),
         new Ingridient.IngridientItem("nuggetIron", 1),
         EM,
         new Ingridient.IngridientItem("nuggetIron", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:cryogrenade", 8, 0, false),
         6000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("ingotTin", 1),
         EM,
         new Ingridient.IngridientItem("gunpowder", 1),
         new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
         new Ingridient.IngridientItem("gunpowder", 1),
         EM,
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gas_grenade", 4, 0, false),
         8000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("dyeYellow", 1),
         new Ingridient.IngridientItem("arpg:chlorine_belcher", 1, 0, false),
         new Ingridient.IngridientItem("dyeBlack", 1),
         EM,
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("circuitBasic", 1),
         6000,
         EM,
         100,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("ingotTin", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         16000,
         EM,
         160,
         new Ingridient.IngridientItem("nuggetGold", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("nuggetGold", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("circuitBasic", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("nuggetGold", 1),
         new Ingridient.IngridientItem("dustGlowstone", 1),
         new Ingridient.IngridientItem("nuggetGold", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dyeBlue", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ion_battery", 1, 0, false),
         20000,
         EM,
         160,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("dustZinc", 1),
         new Ingridient.IngridientItem("dustRedstone", 2),
         new Ingridient.IngridientItem("dustSilver", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("materialPlastic", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:turning_augment", 1, 0, false),
         40000,
         EM,
         300,
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false),
         new Ingridient.IngridientItem("materialPlastic", 2),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("materialPlastic", 2),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:press_augment", 1, 0, false),
         50000,
         EM,
         300,
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("arpg:steel_stamp", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:press_augment", 1, 0, false),
         50000,
         EM,
         300,
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("arpg:steel_stamp", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:weld_augment", 1, 0, false),
         60000,
         EM,
         300,
         new Ingridient.IngridientItem("arpg:wolfram_wire", 2, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:wolfram_wire", 2, 0, false),
         new Ingridient.IngridientItem("arpg:gold_transformer", 1, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:gold_wire", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:wolfram_ingot", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_spray_augment", 1, 0, false),
         500000,
         EM,
         300,
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:silver_transformer", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("minecraft:hardened_clay", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pyrolysis_module", 1, 0, false),
         40000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotBrickNether", 2),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrickNether", 2),
         new Ingridient.IngridientItem("ingotBrickNether", 2),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("ingotBrickNether", 2),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:molten_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:electrolyzer_module", 1, 0, false),
         20000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotCopper", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotCopper", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("arpg:copper_transformer", 1, 0, false),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("ingotZinc", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:fermenter_module", 1, 0, false),
         10000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotBrick", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotBrick", 1),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("arpg:health_berry", 1, 0, false),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrick", 1),
         new Ingridient.IngridientItem("arpg:magic_wood", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:centrifuge_module", 1, 0, false),
         16000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("gemSapphire", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("gemSapphire", 1),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("gemSapphire", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("gemSapphire", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotBrass", 4)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("blockGlassColorless", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:distillation_module", 1, 0, false),
         80000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotSteel", 2),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("ingotCopper", 2),
         new Ingridient.IngridientItem("ingotSteel", 2),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("ingotCopper", 2),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:polymerization_module", 1, 0, false),
         100000,
         EM,
         250,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         new Ingridient.IngridientItem("ingotAluminum", 1),
         new Ingridient.IngridientItem("ingotAluminum", 1),
         new Ingridient.IngridientItem("arpg:copper_transformer", 1, 0, false),
         new Ingridient.IngridientItem("ingotAluminum", 1),
         new Ingridient.IngridientItem("ingotSilver", 1),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("ingotSilver", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotTitanium", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:biofiltering_module", 1, 0, false),
         80000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("arpg:embryo", 1, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:block_item_charger", 1, 0, false),
         16000,
         EM,
         200,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:copper_transformer", 1, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("arpg:machinery_casing", 1, 0, false),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         new Ingridient.IngridientItem("ingotZinc", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("logWood", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:copper_wire", 3, 0, false),
         2600,
         EM,
         60,
         EM,
         EM,
         new Ingridient.IngridientItem("materialRubber", 1),
         EM,
         new Ingridient.IngridientItem("materialRubber", 1),
         EM,
         new Ingridient.IngridientItem("materialRubber", 1),
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotCopper", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:silver_wire", 3, 0, false),
         12000,
         EM,
         60,
         EM,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSilver", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gold_wire", 3, 0, false),
         4800,
         EM,
         60,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:toxiberry_juice_drip", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxiberry_juice_drip", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxiberry_juice_drip", 1, 0, false),
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotGold", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:wolfram_wire", 3, 0, false),
         15000,
         EM,
         70,
         EM,
         EM,
         new Ingridient.IngridientItem("itemClay", 1),
         EM,
         new Ingridient.IngridientItem("itemClay", 1),
         EM,
         new Ingridient.IngridientItem("itemClay", 1),
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:wolfram_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:wolfram_wire", 3, 0, false),
         12000,
         EM,
         70,
         EM,
         EM,
         new Ingridient.IngridientItem("itemConduitBinder", 1),
         EM,
         new Ingridient.IngridientItem("itemConduitBinder", 1),
         EM,
         new Ingridient.IngridientItem("itemConduitBinder", 1),
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:wolfram_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:empty_cell", 9, 0, false),
         2000,
         EM,
         60,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("blockGlassColorless", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:empty_cell", 15, 0, false),
         1800,
         EM,
         80,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:chemical_glass", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:silicium_wafer", 4, 0, false),
         3000,
         EM,
         100,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSilicium", 1),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSilicium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         50000,
         EM,
         200,
         EM,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         EM,
         new Ingridient.IngridientItem("nuggetGold", 1),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("nuggetGold", 1),
         EM,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:galvanized_plate", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustCopper", 1)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustSilver", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("nuggetGold", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:canister", 1, 0, false),
         8000,
         EM,
         140,
         EM,
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("ingotBrass", 1),
         EM,
         new Ingridient.IngridientItem("ingotChromium", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:tnt", 1, 0, false),
         500,
         EM,
         20,
         EM,
         new Ingridient.IngridientItem("string", 1),
         EM,
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("gunpowder", 2),
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("arpg:alchemical_wax", 1, 0, false),
         new Ingridient.IngridientItem("dyeRed", 1),
         new Ingridient.IngridientItem("arpg:alchemical_wax", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("minecraft:tnt", 1, 0, false),
         500,
         EM,
         20,
         EM,
         new Ingridient.IngridientItem("string", 1),
         EM,
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("gunpowder", 2),
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("arpg:paraffin", 1, 0, false),
         new Ingridient.IngridientItem("dyeRed", 1),
         new Ingridient.IngridientItem("arpg:paraffin", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         4000,
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         160,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientFluidBucket("sulfuric_acid"),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new Ingridient.IngridientItem("materialPlastic", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotLead", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pump_shotgun", 1, 0, false),
         40000,
         EM,
         250,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("logWood", 1),
         new Ingridient.IngridientItem("logWood", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotSteel", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:grenade_launcher", 1, 0, false),
         60000,
         EM,
         250,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("logWood", 1),
         EM,
         new Ingridient.IngridientItem("logWood", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotSteel", 6))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:watching_grenade", 8, 0, false),
         40000,
         EM,
         200,
         EM,
         new Ingridient.IngridientItem("pearlEnderEye", 8),
         EM,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         EM,
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 2, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("obsidian", 1)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:void_crystal", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gravity_grenade", 6, 0, false),
         60000,
         EM,
         200,
         EM,
         new Ingridient.IngridientItem("minecraft:shulker_shell", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("enderpearl", 1),
         new Ingridient.IngridientItem("arpg:void_crystal", 1, 0, false),
         new Ingridient.IngridientItem("enderpearl", 1),
         EM,
         new Ingridient.IngridientItem("minecraft:shulker_shell", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hadron_blaster", 1, 0, false),
         150000,
         EM,
         300,
         new Ingridient.IngridientItem("gemCitrine", 1),
         new Ingridient.IngridientItem("gemCitrine", 1),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new Ingridient.IngridientSoulStone(2),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 2, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:gold_transformer", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotBrass", 4)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:snapball", 1, 0, false),
         150000,
         EM,
         300,
         new Ingridient.IngridientItem("dyeBlue", 1),
         new Ingridient.IngridientItem("dyeBlue", 1),
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 2, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 6, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 4),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:impulse_thruster", 7, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotElectrum", 4)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:instancer", 1, 0, false),
         50000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("ingotCopper", 2),
         new Ingridient.IngridientItem("ingotCopper", 2),
         new Ingridient.IngridientItem("arpg:spawner_piece", 6, 0, false),
         new Ingridient.IngridientItem("ingotZinc", 4),
         new Ingridient.IngridientSoulStone(1),
         new Ingridient.IngridientItem("ingotZinc", 4),
         new Ingridient.IngridientItem("arpg:lead_bearing", 3, 0, false),
         new Ingridient.IngridientItem("circuitBasic", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("logWood", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotIron", 6)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("enderpearl", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:brass_knuckles", 1, 0, false),
         15000,
         EM,
         160,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("nuggetBrass", 1),
         new Ingridient.IngridientItem("nuggetBrass", 1),
         new Ingridient.IngridientItem("nuggetBrass", 1),
         EM,
         new Ingridient.IngridientItem("ingotBrass", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotBrass", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hazard_glove", 1, 0, false),
         20000,
         EM,
         120,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:miners_glove", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:detoxicator", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:laser_pistol", 1, 0, false),
         32000,
         EM,
         230,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("dustRedstone", 2),
         new Ingridient.IngridientItem("ingotAluminum", 3),
         new Ingridient.IngridientItem("ingotAluminum", 3),
         new Ingridient.IngridientItem("gemEmerald", 2),
         EM,
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:ion_battery", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotBrass", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotBeryllium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:laser_rifle", 1, 0, false),
         42000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 4),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 4),
         new Ingridient.IngridientItem("gemRuby", 3),
         new Ingridient.IngridientItem("dustRedstone", 5),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("ingotAluminum", 6),
         new Ingridient.IngridientItem("ingotAluminum", 6),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotBrass", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotZinc", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:laser_sniper", 1, 0, false),
         50000,
         EM,
         250,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 4),
         new Ingridient.IngridientItem("arpg:rhinestone", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 4),
         new Ingridient.IngridientItem("ingotAluminum", 6),
         new Ingridient.IngridientItem("ingotAluminum", 6),
         new Ingridient.IngridientItem("gemSapphire", 4),
         new Ingridient.IngridientItem("dustRedstone", 5),
         new Ingridient.IngridientItem("arpg:ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotBrass", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:slime_shotgun", 1, 0, false),
         55000,
         EM,
         250,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("materialPlastic", 5),
         new Ingridient.IngridientItem("materialPlastic", 5),
         new Ingridient.IngridientItem("materialPlastic", 5),
         EM,
         new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
         new Ingridient.IngridientItem("ingotAluminum", 5),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialPlastic", 5)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 5))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         10000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("arpg:void_crystal", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:eye_of_seer", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:vacuum_gun", 1, 0, false),
         70000,
         EM,
         270,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false),
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("blockGlassHardened", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ender_protector", 1, 0, false),
         77000,
         EM,
         270,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false),
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 5, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:purpur_alloy", 5, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("blockGlassHardened", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ender_instancer", 1, 0, false),
         85000,
         EM,
         270,
         EM,
         new Ingridient.IngridientItem("ingotBeryllium", 2),
         new Ingridient.IngridientItem("ingotBeryllium", 2),
         new Ingridient.IngridientItem("arpg:spawner_piece", 6, 0, false),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         new Ingridient.IngridientSoulStone(3),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         new Ingridient.IngridientItem("arpg:lead_bearing", 3, 0, false),
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("minecraft:end_crystal", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("minecraft:shulker_shell", 6, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:enigmate_twins", 1, 0, false),
         250000,
         EM,
         270,
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:dimension_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:purpur_alloy", 4, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:processor", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:vacuum_gun_pellets", 6, 0, false),
         40000,
         EM,
         150,
         EM,
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
         new Ingridient.IngridientItem("arpg:void_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:quantum_slimeball", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("nuggetZinc", 8))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:grapling_hook", 1, 0, false),
         40000,
         EM,
         200,
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotIron", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nether_grinder", 1, 0, false),
         66600,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("arpg:molten_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:molten_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 3, 0, false),
         new Ingridient.IngridientItem("rodBlaze", 2),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:nether_grinder_ammo", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_ingot", 3, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:infernum_ingot", 3, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nether_grinder_ammo", 2, 0, false),
         5000,
         EM,
         80,
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("ingotGold", 1),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false),
         new Ingridient.IngridientItem("ingotBrickNether", 1),
         new Ingridient.IngridientItem("arpg:infernum_nugget", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_armor_boots", 1, 0, false),
         30000,
         EM,
         140,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_armor_leggins", 1, 0, false),
         32000,
         EM,
         140,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_armor_chestplate", 1, 0, false),
         35000,
         EM,
         140,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_armor_helmet", 1, 0, false),
         33000,
         EM,
         140,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_sword", 1, 0, false),
         3000,
         EM,
         80,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:conifer_stick", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:firework_launcher", 1, 0, false),
         30000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:gift", 5, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 5, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 5, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_planks", 1, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_planks", 1, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_planks", 1, 0, false),
         new Ingridient.IngridientItem("arpg:molten_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_planks", 1, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_stick", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:firework_pack", 4, 0, false),
         2000,
         EM,
         60,
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new Ingridient.IngridientItem("minecraft:fireworks", 8, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("paper", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:xmass_launcher", 1, 0, false),
         100000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:lead_bearing", 2, 0, false),
         new Ingridient.IngridientItem("arpg:garland", 4, 0, false),
         new Ingridient.IngridientItem("arpg:garland", 4, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:ice_gem", 6, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 10, 0, false),
         new Ingridient.IngridientItem("arpg:gift", 10, 0, false),
         new Ingridient.IngridientItem("arpg:conifer_log", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 8)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:northern_ingot", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:firework_dragon", 2, 0, false),
         1000,
         new Ingridient.IngridientItem("minecraft:glass_bottle", 1, 0, false),
         60,
         EM,
         new Ingridient.IngridientItem("dustAluminum", 1),
         EM,
         new Ingridient.IngridientItem("paper", 1),
         new Ingridient.IngridientItem("minecraft:dragon_breath", 1, 0, false),
         new Ingridient.IngridientItem("paper", 1),
         EM,
         new Ingridient.IngridientItem("gunpowder", 2),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("itemBlazePowder", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("minecraft:fire_charge", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:xmass_bundle", 2, 0, false),
         2800,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("arpg:christmas_balls", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:christmas_balls", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:sapling", 2, 1, true),
         new Ingridient.IngridientItem("arpg:christmas_balls", 1, 0, false),
         new Ingridient.IngridientItem("gunpowder", 1),
         new Ingridient.IngridientItem("arpg:garland", 1, 0, false),
         new Ingridient.IngridientItem("gunpowder", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:cooled_rifle", 1, 0, false),
         80000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:ice_gem", 6, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 6, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 6, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 2),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:cooled_rifle_clip", 1, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 2),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 4)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotIron", 5))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:cooled_rifle_clip", 8, 0, false),
         8000,
         EM,
         60,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         new Ingridient.IngridientItem("ingotIron", 1),
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:cryo_gun", 1, 0, false),
         85000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("arpg:ice_gem", 8, 0, false),
         new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         EM,
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 2)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotIron", 5)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 12, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:snowball_cannon", 1, 0, false),
         55000,
         EM,
         180,
         EM,
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 3, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 1),
         new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 1),
         EM,
         new Ingridient.IngridientItem("ingotIron", 4),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 2)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:icicle_minigun", 1, 0, false),
         90000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("ingotIron", 5),
         new Ingridient.IngridientItem("arpg:ice_gem", 4, 0, false),
         new Ingridient.IngridientItem("arpg:ice_gem", 4, 0, false),
         new Ingridient.IngridientItem("ingotIron", 5),
         new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 1, 0, true),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 8)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 8, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:icicle_minigun_clip", 6, 0, true),
         5000,
         EM,
         70,
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("arpg:conifer_log", 1, 0, false),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new Ingridient.IngridientItem("nuggetIron", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:winter_instancer", 1, 0, false),
         130000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("arpg:northern_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:northern_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:frozen_spawner_piece", 6, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 4),
         new Ingridient.IngridientSoulStone(5),
         new Ingridient.IngridientItem("ingotChromium", 4),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 3, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:conifer_log", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:winter_scale", 6, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 20, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:niveolite", 3, 0, false),
         5000,
         EM,
         70,
         EM,
         new Ingridient.IngridientItem("minecraft:snowball", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("minecraft:snowball", 1, 0, false),
         new Ingridient.IngridientItem("arpg:niveolite", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:snowball", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("minecraft:snowball", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:niveolite_block", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:cryo_destroyer", 1, 0, false),
         100000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("arpg:gothic_gear", 5, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 5),
         new Ingridient.IngridientItem("arpg:winter_scale", 6, 0, false),
         new Ingridient.IngridientItem("arpg:empty_cell", 1, 0, false),
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         new Ingridient.IngridientItem("arpg:gothic_gear", 5, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 5),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ice_gem", 6, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 1)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:northern_ingot", 20, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ice_compass", 1, 0, false),
         8000,
         EM,
         200,
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gothic_gear", 4, 0, false),
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gothic_gear", 4, 0, false),
         new Ingridient.IngridientItem("arpg:gothic_gem", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gothic_gear", 4, 0, false),
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gothic_gear", 4, 0, false),
         new Ingridient.IngridientItem("arpg:ice_dust", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         1000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("arpg:weather_fragments", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:ice_circle", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:gothic_gear", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false),
         24000,
         EM,
         140,
         new Ingridient.IngridientItem("arpg:gold_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gold_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:gold_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         new Ingridient.IngridientItem("dustGlowstone", 1),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("dustGlowstone", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 6)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:arsenic_nugget", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:compound_bow", 1, 0, false),
         140000,
         EM,
         260,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 8),
         new Ingridient.IngridientItem("arpg:nylon", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 4)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:wolfram_ingot", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:military_instancer", 1, 0, false),
         200000,
         EM,
         270,
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:uranium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:uranium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:rusted_spawner_piece", 6, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientSoulStone(6),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("materialAdvancedPolymer", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:toxinium_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:wolfram_wire", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:impulse_corslet", 1, 0, false),
         150000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:dash_belt_black", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotAluminum", 4)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("circuitAdvanced", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         8000,
         EM,
         160,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("dustManganese", 1),
         new Ingridient.IngridientItem("dustLithium", 1),
         new Ingridient.IngridientItem("arpg:gold_wire", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotAluminum", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:snakewhip", 1, 0, false),
         120000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:plant_fiber", 10, 0, false),
         new Ingridient.IngridientItem("arpg:nylon", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("arpg:nylon", 1, 0, false),
         new Ingridient.IngridientItem("arpg:plant_fiber", 10, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 2),
         new Ingridient.IngridientItem("arpg:plant_fiber", 10, 0, false),
         new Ingridient.IngridientItem("arpg:nylon", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 9, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:submachine", 1, 0, false),
         95000,
         EM,
         200,
         EM,
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("ingotBrass", 1),
         new Ingridient.IngridientItem("ingotSteel", 3),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("ingotSteel", 3),
         EM,
         new Ingridient.IngridientItem("arpg:submachine_clip", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 8),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 6, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:submachine_clip", 10, 0, false),
         7000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("ingotBrass", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 4),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nail_gun", 1, 0, false),
         145000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:nail_gun_clip", 1, 0, true),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 10),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nail_gun_clip", 4, 0, true),
         8000,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nail_gun_clip", 1, 1, true),
         90000,
         EM,
         40,
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail_gun_clip", 1, 0, true),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new Ingridient.IngridientItem("arpg:nail", 16, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("gunpowder", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:skull_crasher", 1, 0, false),
         140000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 4),
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 8),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 4),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arsenic_ingot", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nail", 64, 0, false),
         2600,
         EM,
         40,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotZinc", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:rocket_launcher", 1, 0, false),
         180000,
         EM,
         270,
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("dyeRed", 1),
         new Ingridient.IngridientItem("arpg:linear_motor", 12, 0, false),
         new Ingridient.IngridientItem("arpg:wolfram_wire", 6, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("ingotSteel", 4),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 6),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 4)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("circuitAdvanced", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotSteel", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:arsenic_pickaxe", 1, 0, false),
         100000,
         EM,
         100,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:arsenic_axe", 1, 0, false),
         100000,
         EM,
         100,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:arsenic_shovel", 1, 0, false),
         100000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 2, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:acid_fire", 1, 0, false),
         170000,
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         250,
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 5),
         new Ingridient.IngridientFluidBucket("biogenic_acid"),
         new Ingridient.IngridientItem("ingotLead", 4),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:toxinium_ingot", 6, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_shotgun", 1, 0, false),
         160000,
         EM,
         260,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_shotgun_clip", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 10),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_shotgun_clip", 6, 0, false),
         16000,
         EM,
         60,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxic_nuclear_cannon", 1, 0, false),
         200000,
         EM,
         300,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 10),
         new Ingridient.IngridientItem("ingotSteel", 8),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:toxinium_ingot", 6, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxic_nuclear_warhead", 4, 0, false),
         40000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:uranium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:uranium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("gunpowder", 4),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotLead", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxic_nuclear_warhead", 4, 0, false),
         40000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("ic2:nuclear", 1, 5, true),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("ic2:nuclear", 1, 2, true),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("gunpowder", 4),
         new Ingridient.IngridientItem("arpg:arsenic_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotLead", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:toxinium_nugget", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_shield", 1, 0, false),
         150000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 2),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotLead", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gasmask", 1, 0, false),
         22000,
         EM,
         100,
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("paneGlassColorless", 1),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("paneGlassColorless", 1),
         new Ingridient.IngridientItem("arpg:gas_filter", 1, 0, false),
         new Ingridient.IngridientItem("materialRubber", 1),
         new Ingridient.IngridientItem("arpg:gas_filter", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("itemLeather", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotAluminum", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:gas_filter", 1, 0, false),
         2500,
         EM,
         60,
         EM,
         new Ingridient.IngridientItem("paper", 1),
         EM,
         new Ingridient.IngridientItem("arpg:dried_plant_fiber", 1, 0, false),
         new Ingridient.IngridientItem("arpg:dried_plant_fiber", 1, 0, false),
         new Ingridient.IngridientItem("arpg:dried_plant_fiber", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("paper", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustSalt", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("itemCharcoal", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialPlastic", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:item_turret", 1, 0, false),
         45000,
         EM,
         250,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_ingot", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 1),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:linear_motor", 1, 0, false),
         new Ingridient.IngridientItem("ingotLead", 1),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         new Ingridient.IngridientItem("ingotLead", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotSteel", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:wrench", 1, 0, false),
         16000,
         EM,
         100,
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("dyeRed", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_helmet", 1, 0, false),
         85000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_chestplate", 1, 0, false),
         100000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 5),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 5),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 3)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:electric_motor", 3, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_leggins", 1, 0, false),
         90000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 3)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:linear_motor", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_boots", 1, 0, false),
         80000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 4),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 4, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hazard_suit_helmet", 1, 0, false),
         90000,
         EM,
         160,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 3),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 3),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 3),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 3),
         new Ingridient.IngridientItem("blockGlassHardened", 1),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 3),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 5)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hazard_suit_chestplate", 1, 0, false),
         105000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("materialRubber", 5),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 20),
         new Ingridient.IngridientItem("materialRubber", 5),
         new Ingridient.IngridientItem("materialRubber", 5),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("materialRubber", 5),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 3)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:silver_wire", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hazard_suit_leggins", 1, 0, false),
         95000,
         EM,
         160,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("materialRubber", 10),
         EM,
         new Ingridient.IngridientItem("materialRubber", 10),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 8),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 8),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 3)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 8)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:cmo", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hazard_suit_boots", 1, 0, false),
         85000,
         EM,
         160,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 7),
         EM,
         new Ingridient.IngridientItem("materialAdvancedPolymer", 7),
         new Ingridient.IngridientItem("materialRubber", 5),
         EM,
         new Ingridient.IngridientItem("materialRubber", 5),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotAluminum", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPlastic", 6))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:antirad_pack", 1, 0, false),
         80000,
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         120,
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("arpg:embryo", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPlastic", 2),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientFluidBucket("luminescent_liquid"),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotChromium", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:anti_rad_plating", 3, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:crystal_cutter", 1, 0, false),
         380000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         new Ingridient.IngridientItem("arpg:lead_bearing", 2, 0, false),
         new Ingridient.IngridientItem("arpg:lead_bearing", 2, 0, false),
         new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:magic_cave_crystal", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:magic_stone", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:crystal_cutter_ammo", 4, 0, false),
         64000,
         EM,
         70,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:magic_crystal_dust", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:magic_crystal_dust", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:magic_cave_crystal", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:crystal_cutter_ammo", 4, 0, false),
         64000,
         EM,
         70,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:magic_crystal_dust", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:magic_crystal_dust", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:magic_stone", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:holy_shotgun", 1, 0, false),
         450000,
         EM,
         260,
         EM,
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:mithril_ingot", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_minigun", 1, 0, false),
         600000,
         EM,
         280,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:adamantium_minigun_clip", 1, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_minigun_clip", 4, 0, false),
         60000,
         EM,
         80,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("nuggetZinc", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("nuggetZinc", 1),
         new Ingridient.IngridientItem("nuggetZinc", 1),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("nuggetZinc", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_longsword", 1, 0, false),
         320000,
         EM,
         270,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_knife", 1, 0, false),
         300000,
         EM,
         270,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 7, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false),
         64000,
         EM,
         180,
         new Ingridient.IngridientItem("arpg:mithril_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mithril_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:mithril_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_battle_axe", 1, 0, false),
         500000,
         EM,
         280,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:adamantium_ingot", 3, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_revolver", 1, 0, false),
         360000,
         EM,
         270,
         EM,
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("ingotSteel", 1),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_armor_boots", 1, 0, false),
         250000,
         EM,
         180,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 3, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 3, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_armor_leggins", 1, 0, false),
         260000,
         EM,
         180,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_armor_chestplate", 1, 0, false),
         265000,
         EM,
         180,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_armor_helmet", 1, 0, false),
         255000,
         EM,
         180,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 18, 0, false),
         16000,
         EM,
         60,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:adamantium_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 2, 0, false),
         16000,
         EM,
         60,
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:adamantium_nugget", 9, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:ancient_battery", 1, 0, false),
         24000,
         EM,
         90,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:erebris_shard", 1, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 1, 0, false),
         new Ingridient.IngridientItem("arpg:eye_of_beholder", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 1, 0, false),
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("nuggetSilver", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:coral_rifle", 1, 0, false),
         570000,
         EM,
         250,
         EM,
         new Ingridient.IngridientItem("arpg:coral", 10, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 10, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_pearl", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:coral_rifle_clip", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 20, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:coral_rifle_clip", 4, 0, false),
         10000,
         EM,
         50,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_nugget", 1, 0, false)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_instancer", 1, 0, false),
         650000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:aquatic_pearl", 1, 0, false),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 4, 0, false),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_spawner_piece", 6, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientSoulStone(12),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 3, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 40, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:giant_shell", 2, 0, true)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:void_crystal", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hydraulic_shotgun", 1, 0, false),
         580000,
         EM,
         250,
         new Ingridient.IngridientItem("arpg:pirate_sextant", 1, 0, false),
         new Ingridient.IngridientItem("arpg:pirate_sextant", 1, 0, false),
         new Ingridient.IngridientItem("arpg:pirate_sextant", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 16, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 16, 0, false),
         new Ingridient.IngridientItem("arpg:placoderm_scales", 8, 0, false),
         new Ingridient.IngridientItem("arpg:placoderm_scales", 8, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 16, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("blockGlassHardened", 1)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:hydraulic_shotgun_clip", 2, 0, false),
         5000,
         EM,
         40,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:coral", 1, 0, false),
         EM,
         EM,
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         120000,
         EM,
         150,
         new Ingridient.IngridientItem("arpg:mesoglea", 3, 0, false),
         new Ingridient.IngridientItem("arpg:mesoglea", 3, 0, false),
         new Ingridient.IngridientItem("arpg:mesoglea", 3, 0, false),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 3, 0, false),
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 3, 0, false),
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:black_pearl", 1, 0, false),
         new Ingridient.IngridientItem("arpg:processor", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:placoderm_scales", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:gold_wire", 3, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false),
         240000,
         EM,
         100,
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:processor", 2, 0, false),
         new Ingridient.IngridientItem("materialAdvancedPolymer", 1),
         new Ingridient.IngridientItem("arpg:processor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:silver_wire", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:sky_crystal_piece", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustRedstone", 6)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:holographic_shield", 1, 0, false),
         1200000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 16, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:thunder_capacitor", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("arpg:mithril_dust", 2, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_railgun_bolts", 4, 0, false),
         20000,
         EM,
         60,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:pink_arthrostelecha_rod", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         30000,
         EM,
         80,
         EM,
         new Ingridient.IngridientItem("arpg:silver_transformer", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:sky_crystal_piece", 1, 0, false),
         new Ingridient.IngridientItem("arpg:topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:sky_crystal_piece", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("circuitAdvanced", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:blue_arthrostelecha_rod", 2, 0, false),
         6000,
         EM,
         60,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arthrostelecha_log_brass", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arthrostelecha_log_brass", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:pink_arthrostelecha_rod", 2, 0, false),
         6000,
         EM,
         60,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:arthrostelecha_log_pink", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:arthrostelecha_log_pink", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_pistol", 1, 0, false),
         400000,
         EM,
         200,
         EM,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 4, 0, false),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_rifle", 1, 0, false),
         1400000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:thunder_stone", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 2, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 2, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 5, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:thunder_capacitor", 3, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_railgun", 1, 0, false),
         1600000,
         EM,
         260,
         new Ingridient.IngridientItem("arpg:copper_wire", 3, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 3, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 4, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 4, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 3, 0, false),
         new Ingridient.IngridientItem("arpg:copper_wire", 3, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 12, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 12, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:thunder_capacitor", 2, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:sky_crystal", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_accelerator", 1, 0, false),
         2000000,
         EM,
         300,
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 10, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:thunder_stone", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 2)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_minigun", 1, 0, false),
         2200000,
         EM,
         300,
         EM,
         new Ingridient.IngridientItem("ingotTitanium", 2),
         new Ingridient.IngridientItem("ingotCopper", 2),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 4, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:plasma_minigun_clip", 1, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:thunder_stone", 5, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 2)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:electric_motor", 5, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_minigun_clip", 4, 0, false),
         50000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("materialRubber", 1),
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("ingotCopper", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 5, 0, false),
         50000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("dustRedstone", 1)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("nuggetTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         90000,
         EM,
         100,
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_nugget", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("nuggetTitanium", 4))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:thunderbird_wings", 1, 0, false),
         1800000,
         EM,
         300,
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 8, 0, false),
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new Ingridient.IngridientItem("minecraft:elytra", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunderbird_feather", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 4, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("arpg:stormbrass_dust", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:annihilation_gun", 1, 0, false),
         3000000,
         EM,
         320,
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("arpg:sky_crystal", 3, 0, false),
         new Ingridient.IngridientItem("arpg:solidified_lightning", 5, 0, false),
         new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("arpg:sky_crystal", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("ingotTitanium", 2)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:stabilization_cell", 2, 0, false),
         30000,
         EM,
         100,
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:chemical_glass", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 1),
         new Ingridient.IngridientItem("arpg:phaseolite", 1, 0, false),
         new Ingridient.IngridientItem("ingotTitanium", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:antimatter_charge", 1, 0, false),
         50000,
         EM,
         50,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:solidified_lightning", 1, 0, false),
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:stabilization_cell", 1, 0, false),
         EM
      );
      int toolcrafttime = 250;
      addRecipe(
         new Ingridient.IngridientItem("arpg:diamond_drill_electric", 1, 0, false),
         40000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("circuitBasic", 1),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotIron", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:diamond_drill_fuel", 1, 0, false),
         40000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("ingotBrass", 5),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false),
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("ingotSteel", 5),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotIron", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:diamond_chainsaw_electric", 1, 0, false),
         40000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("circuitBasic", 1),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 1, 0, false),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("gemDiamond", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotIron", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:diamond_chainsaw_fuel", 1, 0, false),
         40000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("ingotBrass", 5),
         new Ingridient.IngridientItem("dustRedstone", 1),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("arpg:lead_bearing", 1, 0, false),
         new Ingridient.IngridientItem("ingotSteel", 5),
         new Ingridient.IngridientItem("gemDiamond", 1),
         new Ingridient.IngridientItem("gemDiamond", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotIron", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:laser_digger", 1, 0, false),
         50000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("ingotBrass", 5),
         new Ingridient.IngridientItem("circuitBasic", 1),
         new Ingridient.IngridientItem("gemDiamond", 5),
         new Ingridient.IngridientItem("gemRuby", 5),
         new Ingridient.IngridientItem("blockRedstone", 1),
         EM,
         new Ingridient.IngridientItem("ingotBrass", 5),
         new Ingridient.IngridientItem("arpg:lead_acid_battery", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("ingotCopper", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("dustGlowstone", 2))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_drill_electric", 1, 0, false),
         150000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPolymer", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_drill_fuel", 1, 0, false),
         150000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("ingotGold", 5),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:wolfram_ingot", 5, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPolymer", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_chainsaw_electric", 1, 0, false),
         150000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 2, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPolymer", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:toxinium_chainsaw_fuel", 1, 0, false),
         150000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("ingotGold", 5),
         new Ingridient.IngridientItem("arpg:impulse_thruster", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:wolfram_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 3, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPolymer", 10))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:nuclear_mining_ray", 1, 0, false),
         175000,
         EM,
         toolcrafttime,
         new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false),
         new Ingridient.IngridientItem("ingotGold", 5),
         new Ingridient.IngridientItem("arpg:toxic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:toxinium_ingot", 12, 0, false),
         new Ingridient.IngridientItem("arpg:chemical_glass", 5, 0, false),
         new Ingridient.IngridientItem("arpg:gold_transformer", 1, 0, false),
         new Ingridient.IngridientItem("arpg:anti_rad_plating", 2, 0, false),
         new Ingridient.IngridientItem("ingotGold", 5),
         new Ingridient.IngridientItem("arpg:li_ion_battery", 3, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("ingotSteel", 10)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("materialAdvancedPolymer", 10)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:wolfram_ingot", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_drill_electric", 1, 0, false),
         400000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_battery", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_drill_fuel", 1, 0, false),
         400000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("ingotChromium", 5),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("ingotManganese", 5),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_chainsaw_electric", 1, 0, false),
         400000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:adamantium_chainsaw_fuel", 1, 0, false),
         400000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("ingotChromium", 5),
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:arsenic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("ingotManganese", 5),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 5, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:eyelight_prospector", 1, 0, false),
         500000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:dolerite_column", 1, 0, false),
         new Ingridient.IngridientItem("arpg:resistant_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:adamantium_ingot", 10, 0, false),
         new Ingridient.IngridientItem("arpg:eye_of_beholder", 1, 0, false),
         new Ingridient.IngridientItem("arpg:erebris_chunk", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:dolerite_column", 1, 0, false),
         new Ingridient.IngridientItem("arpg:ancient_battery", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:ancient_spawner_piece", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:mithril_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("ingotManganese", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_drill_electric", 1, 0, false),
         980000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatronic_battery", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 32, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:seashell", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_drill_fuel", 1, 0, false),
         980000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:kraken_skin", 3, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 6, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:kraken_skin", 3, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 32, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:seashell", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_chainsaw_electric", 1, 0, false),
         980000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatronic_battery", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 32, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:seashell", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:aquatic_chainsaw_fuel", 1, 0, false),
         980000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:kraken_skin", 3, 0, false),
         new Ingridient.IngridientItem("arpg:glowing_pearl", 6, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:low_friction_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:kraken_skin", 3, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 4, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 32, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:seashell", 10, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:corrosive_waterblaster", 1, 0, false),
         1000000,
         EM,
         toolcrafttime,
         new Ingridient.IngridientItem("arpg:mesoglea", 10, 0, false),
         new Ingridient.IngridientItem("arpg:placoderm_scales", 5, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_ingot", 16, 0, false),
         new Ingridient.IngridientItem("arpg:aquatic_pearl", 1, 0, false),
         new Ingridient.IngridientItem("arpg:tidal_heart", 1, 0, false),
         new Ingridient.IngridientItem("arpg:mesoglea", 10, 0, false),
         new Ingridient.IngridientItem("arpg:placoderm_scales", 5, 0, false),
         new Ingridient.IngridientItem("arpg:aquatronic_battery", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:coral", 32, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:seashell", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:pearl", 4, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:storm_drill_electric", 1, 0, false),
         1900000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormbrass_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:storm_drill_fuel", 1, 0, false),
         1900000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_ingot", 5, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormbrass_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:storm_chainsaw_electric", 1, 0, false),
         1900000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:electric_motor", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormbrass_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:storm_chainsaw_fuel", 1, 0, false),
         1900000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:stormsteel_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:electromagnetic_bearing", 1, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_ingot", 5, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 2, 0, false),
         EM,
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormbrass_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:plasma_cutter", 1, 0, false),
         2000000,
         EM,
         toolcrafttime,
         EM,
         new Ingridient.IngridientItem("arpg:pink_arthrostelecha_rod", 5, 0, false),
         new Ingridient.IngridientItem("arpg:storm_circuit", 1, 0, false),
         new Ingridient.IngridientItem("arpg:phaseolite", 7, 0, false),
         new Ingridient.IngridientItem("arpg:thunder_stone", 15, 0, false),
         new Ingridient.IngridientItem("arpg:stormbrass_plasmatron", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:pink_arthrostelecha_rod", 5, 0, false),
         new Ingridient.IngridientItem("arpg:battery_topazitron_crystal", 2, 0, false),
         new AssemblyTableRecipe.AugmentCost(TURNING, new Ingridient.IngridientItem("arpg:stormsteel_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("arpg:stormbrass_ingot", 10, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:thunder_capacitor", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("dustTitanium", 1)),
         new AssemblyTableRecipe.AugmentCost(PLASMA, new Ingridient.IngridientItem("arpg:sky_crystal", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:personal_frostinguisher", 1, 0, false),
         24000,
         EM,
         160,
         EM,
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         new Ingridient.IngridientItem("arpg:liquid_fire", 1, 0, false),
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("arpg:infernum_ingot", 1, 0, false),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:personal_extinguisher", 1, 0, false),
         24000,
         EM,
         160,
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM,
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         new Ingridient.IngridientItem("minecraft:water_bucket", 1, 0, false),
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("ingotAluminum", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:flame_suppressor", 1, 0, false),
         200000,
         EM,
         300,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:personal_frostinguisher", 1, 0, false),
         new Ingridient.IngridientItem("arpg:personal_extinguisher", 1, 0, false),
         new Ingridient.IngridientItem("arpg:holy_extinguisher", 1, 0, false),
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:wolfram_ingot", 1, 0, false)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("ingotBrass", 1)),
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("ingotTitanium", 1))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:lightning_socks", 1, 0, false),
         32000,
         EM,
         100,
         EM,
         EM,
         EM,
         EM,
         new Ingridient.IngridientItem("arpg:runners_socks", 1, 0, false),
         EM,
         EM,
         EM,
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:conductive_belt", 1, 0, false))
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:holy_extinguisher", 1, 0, false),
         24000,
         EM,
         160,
         EM,
         new Ingridient.IngridientItem("ingotSilver", 1),
         EM,
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         new Ingridient.IngridientItem("arpg:cross_chainlet", 1, 0, false),
         new Ingridient.IngridientItem("arpg:winter_scale", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("ingotSilver", 1),
         EM
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:frostfire_explosive", 2, 0, false),
         4000,
         new Ingridient.IngridientItem("minecraft:bucket", 1, 0, false),
         80,
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:conifer_rosin", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
         new Ingridient.IngridientFluidBucket("cryon"),
         new Ingridient.IngridientItem("arpg:cryogen_cell", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1),
         new Ingridient.IngridientItem("arpg:conifer_rosin", 1, 0, false),
         new Ingridient.IngridientItem("nuggetAluminum", 1)
      );
      addRecipe(
         new Ingridient.IngridientItem("arpg:mini_nuke", 1, 0, false),
         40000,
         EM,
         100,
         EM,
         new Ingridient.IngridientItem("dustRedstone", 1),
         EM,
         new Ingridient.IngridientItem("arpg:uranium_ingot", 1, 0, false),
         new Ingridient.IngridientItem("arpg:dark_rust_metal", 1, 0, false),
         new Ingridient.IngridientItem("arpg:uranium_ingot", 1, 0, false),
         EM,
         new Ingridient.IngridientItem("gunpowder", 1),
         EM,
         new AssemblyTableRecipe.AugmentCost(WELD, new Ingridient.IngridientItem("arpg:copper_wire", 1, 0, false))
      );

      for (ItemBullet bullet : ItemBullet.bulletsRegister) {
         Ingridient.IngridientItem resultAdamantium = new Ingridient.IngridientItem("arpg:adamantium_rounds", 5, 0, false);
         Ingridient.IngridientItem resulBuckshot = new Ingridient.IngridientItem("arpg:buckshot", 12, 0, false);
         String name = bullet.getNbtName();
         NBTTagCompound itemCompound1 = new NBTTagCompound();
         itemCompound1.setString("bullet", name);
         resultAdamantium.setNbt(itemCompound1);
         NBTTagCompound itemCompound2 = new NBTTagCompound();
         itemCompound2.setString("bullet", name);
         resulBuckshot.setNbt(itemCompound2);
         String bulletItemName = bullet.getRegistryName().toString();
         addRecipe(
            resultAdamantium,
            5000,
            EM,
            40,
            EM,
            new Ingridient.IngridientItem(bulletItemName, 5, 0, false),
            EM,
            new Ingridient.IngridientItem(bulletItemName, 5, 0, false),
            new Ingridient.IngridientItem("arpg:adamantium_nugget", 1, 0, false),
            new Ingridient.IngridientItem(bulletItemName, 5, 0, false),
            EM,
            new Ingridient.IngridientItem(bulletItemName, 5, 0, false),
            EM,
            new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("nuggetBrass", 1))
         );
         addRecipe(
            resulBuckshot,
            2000,
            EM,
            15,
            EM,
            EM,
            EM,
            EM,
            new Ingridient.IngridientItem(bulletItemName, 32, 0, false),
            EM,
            EM,
            new Ingridient.IngridientItem("materialPlastic", 1),
            EM,
            new AssemblyTableRecipe.AugmentCost(PRESS, new Ingridient.IngridientItem("nuggetBrass", 1))
         );
      }

      craftTimeMultiplier = 1.0F;
   }

   public static void setregister(AssemblyTableRecipe rec) {
      allRecipes.add(rec);
   }
}

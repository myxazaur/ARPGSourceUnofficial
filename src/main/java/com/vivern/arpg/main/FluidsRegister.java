package com.vivern.arpg.main;

import com.vivern.arpg.blocks.FluidCryon;
import com.vivern.arpg.blocks.FluidFluid;
import java.util.HashMap;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidsRegister {
   public static Fluid CRYON = new FluidCryon("cryon", "cryon_still", "cryon_flow");
   public static Fluid POISON = new Fluid(
         "poison", new ResourceLocation("arpg", "poison_still"), new ResourceLocation("arpg", "poison_flow"), new ResourceLocation("arpg", "poison_overlay")
      )
      .setColor(-8663990);
   public static Fluid SLIME = new Fluid(
         "slime", new ResourceLocation("arpg", "slime_still"), new ResourceLocation("arpg", "slime_flow"), new ResourceLocation("arpg", "slime_overlay")
      )
      .setColor(-8275600)
      .setViscosity(1600)
      .setDensity(1300);
   public static Fluid DARKNESS = new Fluid(
         "darkness",
         new ResourceLocation("arpg", "liquid_darkness"),
         new ResourceLocation("arpg", "liquid_darkness"),
         new ResourceLocation("arpg", "liquid_darkness")
      )
      .setColor(-16777216)
      .setViscosity(2000)
      .setDensity(5000)
      .setTemperature(155);
   public static Fluid LARVAWATER = new Fluid(
         "larva_water", new ResourceLocation("arpg", "larva_water_still"), new ResourceLocation("arpg", "larva_water_flow")
      )
      .setColor(-7744837);
   public static Fluid HYDROTHERMAL = new Fluid(
         "hydrothermal_solution",
         new ResourceLocation("arpg", "hydrothermal_solution_still"),
         new ResourceLocation("arpg", "hydrothermal_solution_flow"),
         (ResourceLocation)null
      )
      .setColor(-2769179)
      .setViscosity(1100)
      .setTemperature(450);
   public static Fluid BIOGENICACID = new Fluid(
         "biogenic_acid",
         new ResourceLocation("arpg", "biogenic_acid_still"),
         new ResourceLocation("arpg", "biogenic_acid_flow"),
         new ResourceLocation("arpg", "poison_overlay")
      )
      .setColor(-8663990)
      .setDensity(1664);
   public static Fluid SULFURICACID = new FluidFluid("sulfuric_acid", "sulfuric_acid_still", "sulfuric_acid_flow", 10655026)
      .setViscosity(2792)
      .setDensity(1835);
   public static Fluid LUMINESCENT = new FluidFluid("luminescent_liquid", "luminescent_liquid_still", "luminescent_liquid_flow", 12189679)
      .setViscosity(1200)
      .setDensity(1100)
      .setTemperature(290);
   public static Fluid TOXIN = new FluidFluid("toxin", "toxin_still", "toxin_flow", 1024783);
   public static Fluid SULFURICGAS = new FluidFluid("sulfuric_gas", "sulfuric_gas_still", "sulfuric_gas_flow", 12828261).setDensity(3).setViscosity(1100);
   public static Fluid MANAOIL = new FluidFluid("mana_oil", "mana_oil_still", "mana_oil_flow", 3280695).setDensity(850).setViscosity(1200);
   public static Fluid PETROLEUM = new FluidFluid("petroleum", "petroleum_still", "petroleum_flow", 2167296).setDensity(730).setViscosity(1100);
   public static Fluid GASOLINE = new FluidFluid("gasoline", "gasoline_still", "gasoline_flow", 13417302).setDensity(700).setViscosity(1010);
   public static Fluid FUELOIL = new FluidFluid("fuel_oil", "fuel_oil_still", "fuel_oil_flow", 4598272).setDensity(900).setViscosity(1700);
   public static Fluid NITRICACID = new FluidFluid("nitric_acid", "nitric_acid_still", "nitric_acid_flow", 16777215).setDensity(1513);
   public static Fluid NATURALGAS = new FluidFluid("natural_gas", "natural_gas_still", "natural_gas_flow", 9878493).setDensity(1).setViscosity(1300);
   public static Fluid DISSOLVEDTOXINIUM = new FluidFluid("dissolved_toxinium", "dissolved_toxinium_still", "dissolved_toxinium_flow", 48703)
      .setDensity(1795)
      .setViscosity(1080);
   public static HashMap<Fluid, CoolantInfo> coolantInfoMap = new HashMap<>();

   public static void register() {
      FluidRegistry.registerFluid(CRYON);
      FluidRegistry.addBucketForFluid(CRYON);
      FluidRegistry.registerFluid(POISON);
      FluidRegistry.addBucketForFluid(POISON);
      FluidRegistry.registerFluid(SLIME);
      FluidRegistry.addBucketForFluid(SLIME);
      FluidRegistry.registerFluid(DARKNESS);
      FluidRegistry.registerFluid(LARVAWATER);
      FluidRegistry.addBucketForFluid(LARVAWATER);
      FluidRegistry.registerFluid(HYDROTHERMAL);
      FluidRegistry.addBucketForFluid(HYDROTHERMAL);
      FluidRegistry.registerFluid(BIOGENICACID);
      FluidRegistry.addBucketForFluid(BIOGENICACID);
      FluidRegistry.registerFluid(SULFURICACID);
      FluidRegistry.addBucketForFluid(SULFURICACID);
      FluidRegistry.registerFluid(LUMINESCENT);
      FluidRegistry.addBucketForFluid(LUMINESCENT);
      FluidRegistry.registerFluid(TOXIN);
      FluidRegistry.addBucketForFluid(TOXIN);
      FluidRegistry.registerFluid(SULFURICGAS);
      FluidRegistry.addBucketForFluid(SULFURICGAS);
      FluidRegistry.registerFluid(MANAOIL);
      FluidRegistry.addBucketForFluid(MANAOIL);
      FluidRegistry.registerFluid(PETROLEUM);
      FluidRegistry.addBucketForFluid(PETROLEUM);
      FluidRegistry.registerFluid(GASOLINE);
      FluidRegistry.addBucketForFluid(GASOLINE);
      FluidRegistry.registerFluid(FUELOIL);
      FluidRegistry.addBucketForFluid(FUELOIL);
      FluidRegistry.registerFluid(NITRICACID);
      FluidRegistry.addBucketForFluid(NITRICACID);
      FluidRegistry.registerFluid(NATURALGAS);
      FluidRegistry.addBucketForFluid(NATURALGAS);
      FluidRegistry.registerFluid(DISSOLVEDTOXINIUM);
      FluidRegistry.addBucketForFluid(DISSOLVEDTOXINIUM);
      coolantInfoMap.put(CRYON, new CoolantInfo(8, null, 1, 0, CRYON.getTemperature()));
   }

   public static void postInitFluids() {
      Fluid cryotheum = FluidRegistry.getFluid("cryotheum");
      if (cryotheum != null) {
         coolantInfoMap.put(cryotheum, new CoolantInfo(10, null, 1, 0, cryotheum.getTemperature()));
      }

      Fluid steam = FluidRegistry.getFluid("steam");
      if (steam != null) {
         coolantInfoMap.put(FluidRegistry.WATER, new CoolantInfo(4, steam, 1, 4, FluidRegistry.WATER.getTemperature()));
      } else {
         coolantInfoMap.put(FluidRegistry.WATER, new CoolantInfo(4, null, 1, 0, FluidRegistry.WATER.getTemperature()));
      }
   }

   public static void registerRender() {
      modelLoader(BlocksRegister.FLUIDCRYON, "cryon");
      modelLoader(BlocksRegister.FLUIDPOISON, "poison");
      modelLoader(BlocksRegister.FLUIDSLIME, "slime");
      modelLoader(BlocksRegister.FLUIDDARKNESS, "darkness");
      modelLoader(BlocksRegister.FLUIDLARVAWATER, "larva_water");
      modelLoader(BlocksRegister.FLUIDHYDROTHERMAL, "hydrothermal_solution");
      modelLoader(BlocksRegister.FLUIDBIOGENICACID, "biogenic_acid");
      modelLoader(BlocksRegister.FLUIDTOXIN, "toxin");
      modelLoader(BlocksRegister.FLUIDSULFURICACID, "sulfuric_acid");
      modelLoader(BlocksRegister.FLUIDLUMINESCENT, "luminescent_liquid");
      modelLoader(BlocksRegister.FLUIDSULFURICGAS, "sulfuric_gas");
      modelLoader(BlocksRegister.FLUIDMANAOIL, "mana_oil");
      modelLoader(BlocksRegister.FLUIDPETROLEUM, "petroleum");
      modelLoader(BlocksRegister.FLUIDGASOLINE, "gasoline");
      modelLoader(BlocksRegister.FLUIDFUELOIL, "fuel_oil");
      modelLoader(BlocksRegister.FLUIDNITRICACID, "nitric_acid");
      modelLoader(BlocksRegister.FLUIDNATURALGAS, "natural_gas");
      modelLoader(BlocksRegister.FLUIDDISSOLVEDTOXINIUM, "dissolved_toxinium");
   }

   @SideOnly(Side.CLIENT)
   public static void modelLoader(Block block, String variant) {
      final ModelResourceLocation flLocation = new ModelResourceLocation("arpg:fluid", variant);
      Item fl = Item.getItemFromBlock(block);
      ModelBakery.registerItemVariants(fl, new ResourceLocation[0]);
      ModelLoader.setCustomMeshDefinition(fl, stack -> flLocation);
      ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
         protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return flLocation;
         }
      });
   }

   public static CoolantInfo getFluidCoolantInfo(Fluid coolant) {
      return coolantInfoMap.get(coolant);
   }

   public static int biomeToLiquidTemperature(float biometemp) {
      return (int)MathHelper.clamp((4.5F + biometemp) * 60.0F, 0.0F, 10000.0F);
   }

   public static int getRFgeneratedFromFuelBucket(Fluid fluid) {
      String name = fluid.getName();
      if ("gasoline".equals(name)) {
         return 1200000;
      } else if ("petroleum".equals(name)) {
         return 200000;
      } else if ("fuel_oil".equals(name)) {
         return 500000;
      } else if ("mana_oil".equals(name)) {
         return 20000;
      } else if ("natural_gas".equals(name)) {
         return 100000;
      } else if ("creosote".equals(name) || "ic2creosote".equals(name)) {
         return 40000;
      } else if ("coal".equals(name)) {
         return 400000;
      } else if ("refined_oil".equals(name)) {
         return 1000000;
      } else if ("refined_fuel".equals(name)) {
         return 1500000;
      } else if ("tree_oil".equals(name)) {
         return 400000;
      } else if ("seed_oil".equals(name)) {
         return 80000;
      } else if ("refined_biofuel".equals(name)) {
         return 800000;
      } else if ("ic2biogas".equals(name)) {
         return 50000;
      } else if ("ic2hydrogen".equals(name)) {
         return 400000;
      } else if ("hootch".equals(name)) {
         return 300000;
      } else if ("fire_water".equals(name)) {
         return 750000;
      } else {
         return "rocket_fuel".equals(name) ? 1000000 : 0;
      }
   }

   public static class CoolantInfo {
      public int coolingAmount;
      @Nullable
      public Fluid existedFluid;
      public int existedExponent;
      public int inputExponent;
      public int coolingMinimum;

      public CoolantInfo(int coolingAmount, @Nullable Fluid existedFluid, int inputExponent, int existedExponent, int coolingMinimum) {
         this.coolingAmount = coolingAmount;
         this.existedFluid = existedFluid;
         this.inputExponent = inputExponent;
         this.existedExponent = existedExponent;
         this.coolingMinimum = coolingMinimum;
      }
   }
}

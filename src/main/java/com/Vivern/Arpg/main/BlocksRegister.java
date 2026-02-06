package com.Vivern.Arpg.main;

import com.Vivern.Arpg.blocks.AbsorptionTotem;
import com.Vivern.Arpg.blocks.AdvancedBlockDetector;
import com.Vivern.Arpg.blocks.AlchemicLab;
import com.Vivern.Arpg.blocks.AlchemicVaporizer;
import com.Vivern.Arpg.blocks.AquaticaPortal;
import com.Vivern.Arpg.blocks.AquaticaPortalFrame;
import com.Vivern.Arpg.blocks.ArthrostelechaLeaves;
import com.Vivern.Arpg.blocks.ArthrostelechaLog;
import com.Vivern.Arpg.blocks.AshBlock;
import com.Vivern.Arpg.blocks.AssemblyTable;
import com.Vivern.Arpg.blocks.BeamRock;
import com.Vivern.Arpg.blocks.BioCell;
import com.Vivern.Arpg.blocks.BlockARPGChest;
import com.Vivern.Arpg.blocks.BlockAcidBomb;
import com.Vivern.Arpg.blocks.BlockAssemblyAugment;
import com.Vivern.Arpg.blocks.BlockBlock;
import com.Vivern.Arpg.blocks.BlockBlockCraftingTable;
import com.Vivern.Arpg.blocks.BlockBlockHard;
import com.Vivern.Arpg.blocks.BlockBunkerDoor;
import com.Vivern.Arpg.blocks.BlockCalibrationBundle;
import com.Vivern.Arpg.blocks.BlockCollider;
import com.Vivern.Arpg.blocks.BlockColumn;
import com.Vivern.Arpg.blocks.BlockCrystalSphere;
import com.Vivern.Arpg.blocks.BlockDetector;
import com.Vivern.Arpg.blocks.BlockDrill;
import com.Vivern.Arpg.blocks.BlockElectricSieve;
import com.Vivern.Arpg.blocks.BlockElectromagnet;
import com.Vivern.Arpg.blocks.BlockEtheriteInvocator;
import com.Vivern.Arpg.blocks.BlockFluidBiogenicAcid;
import com.Vivern.Arpg.blocks.BlockFluidCryon;
import com.Vivern.Arpg.blocks.BlockFluidDarkness;
import com.Vivern.Arpg.blocks.BlockFluidDissolvedToxinium;
import com.Vivern.Arpg.blocks.BlockFluidFuelOil;
import com.Vivern.Arpg.blocks.BlockFluidGasoline;
import com.Vivern.Arpg.blocks.BlockFluidHydrothermal;
import com.Vivern.Arpg.blocks.BlockFluidLarvaWater;
import com.Vivern.Arpg.blocks.BlockFluidLuminescent;
import com.Vivern.Arpg.blocks.BlockFluidManaOil;
import com.Vivern.Arpg.blocks.BlockFluidNaturalGas;
import com.Vivern.Arpg.blocks.BlockFluidNitricAcid;
import com.Vivern.Arpg.blocks.BlockFluidPetroleum;
import com.Vivern.Arpg.blocks.BlockFluidPoison;
import com.Vivern.Arpg.blocks.BlockFluidSlime;
import com.Vivern.Arpg.blocks.BlockFluidSulfuricAcid;
import com.Vivern.Arpg.blocks.BlockFluidSulfuricGas;
import com.Vivern.Arpg.blocks.BlockFluidToxin;
import com.Vivern.Arpg.blocks.BlockFulminiherba;
import com.Vivern.Arpg.blocks.BlockItemCharger;
import com.Vivern.Arpg.blocks.BlockLootBlob;
import com.Vivern.Arpg.blocks.BlockMoneyBox;
import com.Vivern.Arpg.blocks.BlockNiveoliteGame;
import com.Vivern.Arpg.blocks.BlockOre;
import com.Vivern.Arpg.blocks.BlockPlacer;
import com.Vivern.Arpg.blocks.BlockPuzzle;
import com.Vivern.Arpg.blocks.BlockRadioactive;
import com.Vivern.Arpg.blocks.BlockRotated;
import com.Vivern.Arpg.blocks.BlockRunicMirror;
import com.Vivern.Arpg.blocks.BlockSeaweed;
import com.Vivern.Arpg.blocks.BlockSieve;
import com.Vivern.Arpg.blocks.BlockSoulCatcher;
import com.Vivern.Arpg.blocks.BlockSpawner;
import com.Vivern.Arpg.blocks.BlockSpeleothem;
import com.Vivern.Arpg.blocks.BlockStalactiteBase;
import com.Vivern.Arpg.blocks.BlockTeamBanner;
import com.Vivern.Arpg.blocks.BlockVial;
import com.Vivern.Arpg.blocks.BlockWeaponSpawner;
import com.Vivern.Arpg.blocks.BlockWinterAltar;
import com.Vivern.Arpg.blocks.BlueGlowingMushroom;
import com.Vivern.Arpg.blocks.BonesPile;
import com.Vivern.Arpg.blocks.Bookcase;
import com.Vivern.Arpg.blocks.BrownSlime;
import com.Vivern.Arpg.blocks.BurningFrost;
import com.Vivern.Arpg.blocks.CaveCrystal;
import com.Vivern.Arpg.blocks.Chair;
import com.Vivern.Arpg.blocks.Chest;
import com.Vivern.Arpg.blocks.ChlorineBelcher;
import com.Vivern.Arpg.blocks.ChristmasBalls;
import com.Vivern.Arpg.blocks.CleanIce;
import com.Vivern.Arpg.blocks.ColliderPipe;
import com.Vivern.Arpg.blocks.ConiferLeaves;
import com.Vivern.Arpg.blocks.ConiferLog;
import com.Vivern.Arpg.blocks.ConiferSapling;
import com.Vivern.Arpg.blocks.CoralChandelier;
import com.Vivern.Arpg.blocks.CoralTorch;
import com.Vivern.Arpg.blocks.Corallimorpha;
import com.Vivern.Arpg.blocks.CreativeElementDistributor;
import com.Vivern.Arpg.blocks.CrystalChandelier;
import com.Vivern.Arpg.blocks.CrystalTorch;
import com.Vivern.Arpg.blocks.Crystallizer;
import com.Vivern.Arpg.blocks.CustomPlant;
import com.Vivern.Arpg.blocks.DebugColorBlock;
import com.Vivern.Arpg.blocks.DecorativeChain;
import com.Vivern.Arpg.blocks.DecorativePipe;
import com.Vivern.Arpg.blocks.DemonicFire;
import com.Vivern.Arpg.blocks.DisenchantmentTable;
import com.Vivern.Arpg.blocks.DoleriteColumn;
import com.Vivern.Arpg.blocks.DungeonFloorLadder;
import com.Vivern.Arpg.blocks.DungeonPortal;
import com.Vivern.Arpg.blocks.DungeonTopLadder;
import com.Vivern.Arpg.blocks.ElectrofernLeaves;
import com.Vivern.Arpg.blocks.EthernalFrostPortal;
import com.Vivern.Arpg.blocks.ExoheliaSpike;
import com.Vivern.Arpg.blocks.FieryBeanBlock;
import com.Vivern.Arpg.blocks.FieryBeanLeaves;
import com.Vivern.Arpg.blocks.FieryBeanLog;
import com.Vivern.Arpg.blocks.FieryBeanSapling;
import com.Vivern.Arpg.blocks.FrostedWeed;
import com.Vivern.Arpg.blocks.FrostfireExplosive;
import com.Vivern.Arpg.blocks.FrozenChandelier;
import com.Vivern.Arpg.blocks.FrozenCobblestone;
import com.Vivern.Arpg.blocks.FrozenSlime;
import com.Vivern.Arpg.blocks.FrozenStone;
import com.Vivern.Arpg.blocks.FrozenTileRoof;
import com.Vivern.Arpg.blocks.FrozenTorch;
import com.Vivern.Arpg.blocks.FrozenTreasureBarrel;
import com.Vivern.Arpg.blocks.FrozenVase;
import com.Vivern.Arpg.blocks.FulminiortumBonny;
import com.Vivern.Arpg.blocks.FulminiortumBulb;
import com.Vivern.Arpg.blocks.Garland;
import com.Vivern.Arpg.blocks.GemsparkBlock;
import com.Vivern.Arpg.blocks.GiantFlowerLeaves;
import com.Vivern.Arpg.blocks.GiantShell;
import com.Vivern.Arpg.blocks.Glacier;
import com.Vivern.Arpg.blocks.Glossary;
import com.Vivern.Arpg.blocks.Glowbush;
import com.Vivern.Arpg.blocks.GlowingCaveCrystal;
import com.Vivern.Arpg.blocks.GlowingVein;
import com.Vivern.Arpg.blocks.GreenGlowingMushroom;
import com.Vivern.Arpg.blocks.HealthFlowerLeaves;
import com.Vivern.Arpg.blocks.IHasSubtypes;
import com.Vivern.Arpg.blocks.IceSpikes;
import com.Vivern.Arpg.blocks.IndustrialMixer;
import com.Vivern.Arpg.blocks.InfernumFurnace;
import com.Vivern.Arpg.blocks.LarvaBlock;
import com.Vivern.Arpg.blocks.LivingSponge;
import com.Vivern.Arpg.blocks.LooseSnow;
import com.Vivern.Arpg.blocks.LoppyToxiberry;
import com.Vivern.Arpg.blocks.LoppyToxistem;
import com.Vivern.Arpg.blocks.MagicGenerator;
import com.Vivern.Arpg.blocks.MagicLog;
import com.Vivern.Arpg.blocks.MagicOrnament;
import com.Vivern.Arpg.blocks.MagmaBloom;
import com.Vivern.Arpg.blocks.ManaBottle;
import com.Vivern.Arpg.blocks.ManaFlowerLeaves;
import com.Vivern.Arpg.blocks.ManaPump;
import com.Vivern.Arpg.blocks.MelanzaStem;
import com.Vivern.Arpg.blocks.MetallicCoral;
import com.Vivern.Arpg.blocks.MiniCoral;
import com.Vivern.Arpg.blocks.MiniNuke;
import com.Vivern.Arpg.blocks.MutatedFlowerPink;
import com.Vivern.Arpg.blocks.MutatedFlowerRed;
import com.Vivern.Arpg.blocks.NativeSilver;
import com.Vivern.Arpg.blocks.NetherMelter;
import com.Vivern.Arpg.blocks.NiveoliteBlock;
import com.Vivern.Arpg.blocks.NiveousHole;
import com.Vivern.Arpg.blocks.PalmFruitBunch;
import com.Vivern.Arpg.blocks.PalmLeaves;
import com.Vivern.Arpg.blocks.PalmLog;
import com.Vivern.Arpg.blocks.PalmSapling;
import com.Vivern.Arpg.blocks.PalmTorch;
import com.Vivern.Arpg.blocks.Pane;
import com.Vivern.Arpg.blocks.Pilaster;
import com.Vivern.Arpg.blocks.PoisonLily;
import com.Vivern.Arpg.blocks.PresentBox;
import com.Vivern.Arpg.blocks.PyrocrystallineConverter;
import com.Vivern.Arpg.blocks.RedPepperVine;
import com.Vivern.Arpg.blocks.RedstonedFrozenBricks;
import com.Vivern.Arpg.blocks.ResearchTable;
import com.Vivern.Arpg.blocks.Retort;
import com.Vivern.Arpg.blocks.RustLamp;
import com.Vivern.Arpg.blocks.RustedPipe;
import com.Vivern.Arpg.blocks.SacrificialAltar;
import com.Vivern.Arpg.blocks.SeaLock;
import com.Vivern.Arpg.blocks.SeaUrchin;
import com.Vivern.Arpg.blocks.Seagrass;
import com.Vivern.Arpg.blocks.SeleniteCrystal;
import com.Vivern.Arpg.blocks.ShimmeringBeastbloom;
import com.Vivern.Arpg.blocks.SlimeBlob;
import com.Vivern.Arpg.blocks.SlimeGlob;
import com.Vivern.Arpg.blocks.SnowIce;
import com.Vivern.Arpg.blocks.SnowSewingTable;
import com.Vivern.Arpg.blocks.SoundTypeCrunchy;
import com.Vivern.Arpg.blocks.SoundTypeShards;
import com.Vivern.Arpg.blocks.SpellForgeBlock;
import com.Vivern.Arpg.blocks.Splitter;
import com.Vivern.Arpg.blocks.Stairs;
import com.Vivern.Arpg.blocks.StarLantern;
import com.Vivern.Arpg.blocks.StormConductor;
import com.Vivern.Arpg.blocks.StormRack;
import com.Vivern.Arpg.blocks.StormledgePortal;
import com.Vivern.Arpg.blocks.StormledgePortalFrame;
import com.Vivern.Arpg.blocks.SulfurCrystal;
import com.Vivern.Arpg.blocks.SummonedHellstone;
import com.Vivern.Arpg.blocks.SweetNectarFlower;
import com.Vivern.Arpg.blocks.Table;
import com.Vivern.Arpg.blocks.TideBeacon;
import com.Vivern.Arpg.blocks.TomatoStem;
import com.Vivern.Arpg.blocks.TopazCrystal;
import com.Vivern.Arpg.blocks.ToxiberryTreeLeaves;
import com.Vivern.Arpg.blocks.ToxiberryTreeLog;
import com.Vivern.Arpg.blocks.ToxiberryTreeSapling;
import com.Vivern.Arpg.blocks.ToxicBarrel;
import com.Vivern.Arpg.blocks.ToxicChandelier;
import com.Vivern.Arpg.blocks.ToxicGrass;
import com.Vivern.Arpg.blocks.ToxicPortalFrame;
import com.Vivern.Arpg.blocks.ToxicTallgrass;
import com.Vivern.Arpg.blocks.ToxicTorch;
import com.Vivern.Arpg.blocks.ToxicomaniaPortal;
import com.Vivern.Arpg.blocks.TritonHearth;
import com.Vivern.Arpg.blocks.VirulentCapsule;
import com.Vivern.Arpg.blocks.VoidCrystal;
import com.Vivern.Arpg.blocks.WoodenShaft;
import com.Vivern.Arpg.elements.ItemARPGChest;
import com.Vivern.Arpg.elements.ItemBlockFuel;
import com.Vivern.Arpg.elements.ItemBlockHasSubtypes;
import com.Vivern.Arpg.loot.OreDrop;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.TEISRBlocks;
import com.Vivern.Arpg.renders.TEISRChests;
import com.Vivern.Arpg.tileentity.EnumChest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlocksRegister {
   public static BlocksRegister instance = new BlocksRegister();
   public static HashSet<Block> forrender = new HashSet<>();
   public static final Hardres HR_CONIFER_LOG = new Hardres(6.0F, 30.0F, 0.4F, 1.0F, 4);
   public static final Hardres HR_CONIFER_PLANKS = new Hardres(5.5F, 35.0F, 0.4F, 1.0F, 4);
   public static final Hardres HR_SNOWICE_GLACIER = new Hardres(4.5F, 10.0F, 0.2F, 1.0F, 3);
   public static final Hardres HR_FROZEN_STONE = new Hardres(7.0F, 80.0F, 0.04F, 1.0F, 5);
   public static final Hardres HR_FROZEN_COBBLESTONE = new Hardres(7.5F, 90.0F, 0.04F, 1.0F, 5);
   public static final Hardres HR_FROZEN_BRICK = new Hardres(10.0F, 100.0F, 0.02F, 1.0F, 5);
   public static final Hardres HR_FROZEN_ROOF = new Hardres(8.0F, 80.0F, 0.02F, 1.0F, 4);
   public static final Hardres HR_FROZEN_FURNITURE = new Hardres(6.0F, 35.0F, 0.2F, 1.0F, 3);
   public static final Hardres HR_PUZZLE = new Hardres(20.0F, 100.0F, 0.0F, 1.0F, 7);
   public static final Hardres HR_NIVEOLITE = new Hardres(12.0F, 85.0F, 0.01F, 1.0F, 5);
   public static final Hardres HR_NIVEOUS_HALL = new Hardres(12.0F, 130.0F, 0.01F, 1.0F, 6);
   public static final Hardres HR_FROZEN_DEBRIS = new Hardres(50.0F, 1400.0F, 0.2F, 1.0F, 6);
   public static final Hardres HR_RADIOACTIVE_STONE = new Hardres(13.0F, 120.0F, 0.01F, 1.0F, 5);
   public static final Hardres HR_RADIOACTIVE_WASTE = new Hardres(13.0F, 40.0F, 0.5F, 1.0F, 5);
   public static final Hardres HR_CHLORINE_BELCHER = new Hardres(20.0F, 40.0F, 0.1F, 0.9F, 4);
   public static final Hardres HR_NECTAR_FLOWER = new Hardres(25.0F, 80.0F, 0.01F, 0.9F, 4);
   public static final Hardres HR_ARSENIC = new Hardres(13.0F, 120.0F, 0.01F, 1.0F, 7);
   public static final Hardres HR_WOLFRAM_AND_BIOCELLS = new Hardres(14.0F, 1500.0F, 0.01F, 1.2F, 8);
   public static final Hardres HR_TOXINIUM = new Hardres(11.0F, 115.0F, 0.01F, 1.0F, 6);
   public static final Hardres HR_BUNKER_MISC = new Hardres(18.0F, 40.0F, 0.1F, 1.0F, 7);
   public static final Hardres HR_BUNKER = new Hardres(18.0F, 250.0F, 0.01F, 1.0F, 9);
   public static final Hardres HR_TOXIBERRY_LOG = new Hardres(12.0F, 35.0F, 0.4F, 1.0F, 5);
   public static final Hardres HR_TOXIBERRY_FURNITURE = new Hardres(11.0F, 28.0F, 0.4F, 1.0F, 3);
   public static final Hardres HR_MITHRIL_ADAMANTIUM_ORE = new Hardres(22.0F, 110.0F, 0.01F, 1.0F, 7);
   public static final Hardres HR_DUNGEON_STONES = new Hardres(24.0F, 120.0F, 0.01F, 1.0F, 9);
   public static final Hardres HR_DOLERITE_BRICKS = new Hardres(28.0F, 230.0F, 0.01F, 1.0F, 9);
   public static final Hardres HR_PALM = new Hardres(18.0F, 50.0F, 0.14F, 1.0F, 9);
   public static final Hardres HR_CORALS = new Hardres(26.0F, 180.0F, 0.03F, 1.0F, 9);
   public static final Hardres HR_METALLIC_CORALS = new Hardres(30.0F, 1250.0F, 0.01F, 1.0F, 10);
   public static final Hardres HR_SHELLROCKS = new Hardres(26.0F, 800.0F, 0.03F, 1.0F, 10);
   public static final Hardres HR_CORALBRICKS = new Hardres(35.0F, 2000.0F, 0.01F, 1.0F, 11);
   public static final Hardres HR_SANKTUARYBRICKS = new Hardres(45.0F, 2600.0F, 0.01F, 1.0F, 11);
   public static final Hardres HR_STORM_FOLIAGE = new Hardres(40.0F, 60.0F, 0.13F, 1.0F, 10);
   public static final Hardres HR_STORMBRASS_ROCKS = new Hardres(60.0F, 3000.0F, 0.01F, 1.0F, 11);
   public static final Hardres HR_ZARPION_ROCKS = new Hardres(80.0F, 4000.0F, 0.01F, 1.0F, 12);
   public static final Hardres HR_STORMSTEEL_ORE = new Hardres(35.0F, 1000.0F, 0.01F, 1.0F, 11);
   public static final Hardres HR_SPAWNER_VOIDCRYSTAL = new Hardres(12.0F, 40.0F, 0.15F, 1.0F, 3);
   public static final Hardres HR_SPAWNER_FROZEN = new Hardres(11.0F, 30.0F, 0.15F, 1.0F, 4);
   public static final Hardres HR_SPAWNER_RUSTED = new Hardres(22.0F, 45.0F, 0.15F, 1.0F, 5);
   public static final Hardres HR_SPAWNER_ANCIENT = new Hardres(34.0F, 150.0F, 0.15F, 1.0F, 8);
   public static final Hardres HR_SPAWNER_AQUATIC = new Hardres(46.0F, 300.0F, 0.15F, 1.0F, 10);
   public static final Hardres HR_SPAWNER_STORM = new Hardres(65.0F, 1000.0F, 0.15F, 1.0F, 11);
   public static Block DEMONICFIRE = new DemonicFire();
   public static Block OREDEMONITE = new BlockOre(Material.ROCK, "demonite_ore", 3.5F, 80.0F, 2, 8).setHarvestLvl("pickaxe", 3);
   public static Block OREINFERNUM = new BlockBlock(Material.ROCK, "infernum_ore", 3.5F, 80.0F)
      .setHarvest("pickaxe", 3)
      .setisReplaceableOreGen(false);
   public static Block OREMOLTEN = new BlockBlock(Material.ROCK, "molten_ore", 8.0F, 100.0F)
      .setHarvest("pickaxe", 4)
      .setisReplaceableOreGen(false)
      .setLightLevel(1.0F);
   public static Block MAGMABLOOM = new MagmaBloom();
   public static Block SUMMONEDHELLSTONE = new SummonedHellstone();
   public static Block REDPEPPERVINE = new RedPepperVine();
   public static Block FROZENSTONE = new FrozenStone();
   public static Block FROZENCOBBLE = new FrozenCobblestone();
   public static Block FROZENBRICKS = new BlockBlockHard(Material.ROCK, "frozen_stone_bricks", HR_FROZEN_BRICK, "pickaxe", false);
   public static Block FROZENBRICKSTAIRS = new Stairs.HardStairs(
      FROZENBRICKS.getDefaultState(),
      Material.ROCK,
      "frozen_brick_stairs",
      HR_FROZEN_BRICK.HARDNESS,
      HR_FROZEN_BRICK.RESISTANCE,
      HR_FROZEN_BRICK.SLOW,
      HR_FROZEN_BRICK.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_FROZEN_BRICK.LVL,
      false
   );
   public static Block FROZENBRICKPILASTER = new Pilaster.HardPilaster(
      Material.ROCK,
      "frozen_brick_pilaster",
      HR_FROZEN_BRICK.HARDNESS,
      HR_FROZEN_BRICK.RESISTANCE,
      HR_FROZEN_BRICK.SLOW,
      HR_FROZEN_BRICK.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_FROZEN_BRICK.LVL,
      false
   );
   public static Block FROZENROOF = new FrozenTileRoof();
   public static Block FROZENROOFSTAIRS = new Stairs.HardStairs(
      FROZENROOF.getDefaultState(),
      Material.ROCK,
      "frozen_tile_roof_stairs",
      HR_FROZEN_ROOF.HARDNESS,
      HR_FROZEN_ROOF.RESISTANCE,
      HR_FROZEN_ROOF.SLOW,
      HR_FROZEN_ROOF.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_FROZEN_ROOF.LVL,
      false
   );
   public static Block FLUIDCRYON = new BlockFluidCryon();
   public static Block PORTALFROST = new EthernalFrostPortal();
   public static Block FROSTEDWEED = new FrostedWeed();
   public static Block LOOSESNOW = new LooseSnow();
   public static Block SNOWICE = new SnowIce();
   public static Block GLACIER = new Glacier();
   public static Block CLEANICE = new CleanIce();
   public static Block ICEPANE = new Pane(Material.GLASS, "ice_pane", 0.5F, 0.3F, false).setSound(SoundType.GLASS);
   public static Block STALACTITEFROZEN = new BlockStalactiteBase("frozen_stalactite_base", Material.ROCK, 0, "pickaxe", 0);
   public static Block STALACTITEFROZENADD = new BlockStalactiteBase("frozen_stalactite_addition", Material.ROCK, 1, "pickaxe", 0);
   public static Block STALAGMITEFROZEN = new BlockStalactiteBase("frozen_stalagmite_base", Material.ROCK, 2, "pickaxe", 0);
   public static Block FROZENVASE = new FrozenVase();
   public static Block FROZENCHANDELIER = new FrozenChandelier();
   public static Block FROZENTORCH = new FrozenTorch();
   public static Block FROZENTABLE = new Table(
      Material.ROCK,
      "frozen_table",
      HR_FROZEN_FURNITURE.HARDNESS,
      HR_FROZEN_FURNITURE.RESISTANCE,
      SoundType.STONE,
      "pickaxe",
      HR_FROZEN_FURNITURE.LVL
   );
   public static Block FROZENCHAIR = new Chair(
      Material.ROCK,
      "frozen_chair",
      HR_FROZEN_FURNITURE.HARDNESS,
      HR_FROZEN_FURNITURE.RESISTANCE,
      SoundType.STONE,
      "pickaxe",
      HR_FROZEN_FURNITURE.LVL
   );
   public static Block ICESPIKES = new IceSpikes();
   public static Block BURNINGFROST = new BurningFrost();
   public static Block FROSTEXPLOSIVE = new FrostfireExplosive();
   public static Block FROZENSLIME = new FrozenSlime();
   public static Block REDSTONEDFROZENBRICKS = new RedstonedFrozenBricks();
   public static Block MOBSPAWNERFROZEN = new BlockSpawner(Material.ROCK, "frozen_spawner", HR_SPAWNER_FROZEN, 15, 25)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false);
   public static CustomPlant ICEFLOWER = CustomPlant.createCustomPlant(
      "ice_flower",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      new Block[]{SNOWICE, CLEANICE, GLACIER, Blocks.ICE, Blocks.SNOW},
      true,
      "arpg:ice_flower_seed,2,4,0",
      4,
      15,
      0.03F,
      0,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant CRIMBERRY = CustomPlant.createCustomPlant(
      "crimberry",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      new Block[]{Blocks.SNOW},
      true,
      "arpg:crimberry_seed,1,4,0",
      10,
      15,
      0.04F,
      2,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant WINTERWILLOW = CustomPlant.createCustomPlant(
      "winter_willow",
      0.5F,
      0.0F,
      SoundType.PLANT,
      null,
      new Block[]{SNOWICE, Blocks.SNOW, Blocks.DIRT, Blocks.GRASS},
      true,
      "arpg:winter_willow_seed,2,3,0",
      8,
      15,
      0.035F,
      0,
      null,
      0,
      0,
      0.0F,
      2
   );
   public static Block FROZENBARREL = new FrozenTreasureBarrel();
   public static Block WINTERALTAR = new BlockWinterAltar();
   public static Block SNOWSEWINGTABLE = new SnowSewingTable();
   public static Block PUZZLE = new BlockPuzzle();
   public static Block FROZENCHEST = new Chest(Material.WOOD, "frozen_chest", 1.5F, 2.25F, SoundType.WOOD, "axe", 0, EnumChest.FROZEN);
   public static Block GARLAND = new Garland();
   public static Block PRESENTBOX = new PresentBox();
   public static Block STARLANTERN = new StarLantern();
   public static Block CHRISTMASBALLS = new ChristmasBalls();
   public static Block CONIFERCRAFTINGTABLE = new BlockBlockCraftingTable(Material.WOOD, "conifer_crafting_table", HR_CONIFER_PLANKS, "axe", true)
      .setSound(SoundType.WOOD);
   public static Block CONIFERSAPLING = new ConiferSapling();
   public static Block CONIFERLEAVES = new ConiferLeaves();
   public static Block CONIFERLOG = new ConiferLog();
   public static Block CONIFERPLANKS = new BlockBlockHard(Material.WOOD, "conifer_planks", HR_CONIFER_PLANKS, "axe", true)
      .setSound(SoundType.WOOD);
   public static Block CONIFERSTAIRS = new Stairs.HardStairs(
      CONIFERPLANKS.getDefaultState(), Material.WOOD, "conifer_stairs", SoundType.WOOD, HR_CONIFER_PLANKS, "axe", true
   );
   public static Block CONIFERORNAMENT = new BlockBlockHard(Material.WOOD, "conifer_ornament", HR_CONIFER_PLANKS, "axe", true)
      .setSound(SoundType.WOOD);
   public static Block CONIFERPILASTER = new Pilaster.HardPilaster(
      Material.WOOD, "conifer_pilaster", SoundType.WOOD, HR_CONIFER_PLANKS, "axe", true
   );
   public static Block CONIFERTABLE = new Table(
      Material.WOOD,
      "conifer_table",
      HR_CONIFER_PLANKS.HARDNESS,
      HR_CONIFER_PLANKS.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_CONIFER_PLANKS.LVL
   );
   public static Block CONIFERCHAIR = new Chair(
      Material.WOOD,
      "conifer_chair",
      HR_CONIFER_PLANKS.HARDNESS,
      HR_CONIFER_PLANKS.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_CONIFER_PLANKS.LVL
   );
   public static Block NIVEOLITEBLOCK = new NiveoliteBlock();
   public static Block NIVEOUSBRICKS = new BlockBlockHard(
         Material.ROCK,
         "niveous_bricks",
         HR_NIVEOUS_HALL.HARDNESS,
         HR_NIVEOUS_HALL.RESISTANCE,
         HR_NIVEOUS_HALL.SLOW,
         HR_NIVEOUS_HALL.FAST,
         HR_NIVEOUS_HALL.LVL,
         "pickaxe",
         false
      )
      .setSlipperiness(0.7F);
   public static Block POLISHEDNIVEOUSBLOCK = new BlockBlockHard(
         Material.ROCK,
         "polished_niveous_block",
         HR_NIVEOUS_HALL.HARDNESS,
         HR_NIVEOUS_HALL.RESISTANCE,
         HR_NIVEOUS_HALL.SLOW,
         HR_NIVEOUS_HALL.FAST,
         HR_NIVEOUS_HALL.LVL,
         "pickaxe",
         false
      )
      .setSlipperiness(0.999F);
   public static Block NIVEOUSCOLUMN = new BlockColumn.HardBlockColumn(
      Material.ROCK,
      "niveous_column",
      HR_NIVEOUS_HALL.HARDNESS,
      HR_NIVEOUS_HALL.RESISTANCE,
      HR_NIVEOUS_HALL.SLOW,
      HR_NIVEOUS_HALL.FAST,
      HR_NIVEOUS_HALL.LVL,
      "pickaxe",
      false
   );
   public static Block NIVEOUSPILASTER = new Pilaster.HardPilaster(
      Material.ROCK,
      "niveous_pilaster",
      HR_NIVEOUS_HALL.HARDNESS,
      HR_NIVEOUS_HALL.RESISTANCE,
      HR_NIVEOUS_HALL.SLOW,
      HR_NIVEOUS_HALL.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_NIVEOUS_HALL.LVL,
      false
   );
   public static Block POLISHEDNIVEOUSPILASTER = new Pilaster.HardPilaster(
      Material.ROCK,
      "polished_niveous_pilaster",
      HR_NIVEOUS_HALL.HARDNESS,
      HR_NIVEOUS_HALL.RESISTANCE,
      HR_NIVEOUS_HALL.SLOW,
      HR_NIVEOUS_HALL.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_NIVEOUS_HALL.LVL,
      false
   );
   public static Block NIVEOUSSTAIRS = new Stairs.HardStairs(
      NIVEOUSBRICKS.getDefaultState(),
      Material.ROCK,
      "niveous_stairs",
      HR_NIVEOUS_HALL.HARDNESS,
      HR_NIVEOUS_HALL.RESISTANCE,
      HR_NIVEOUS_HALL.SLOW,
      HR_NIVEOUS_HALL.FAST,
      SoundType.STONE,
      "pickaxe",
      HR_NIVEOUS_HALL.LVL,
      false
   );
   public static Block NIVEOUSHOLE = new NiveousHole();
   public static Block NIVEOLITEGAMEBLOCK = new BlockNiveoliteGame();
   public static Block OREICEGL = new BlockOre(Material.ROCK, "ice_ore_glacier", 2.7F, 0.8F, 2, 4).setHarvestLvl("pickaxe", 2);
   public static Block OREICESN = new BlockOre(Material.CLAY, "ice_ore_snow_ice", 2.5F, 0.8F, 2, 4)
      .setHarvestLvl("shovel", 2)
      .setSoundType(SoundTypeShards.SHARDS);
   public static Block FROZENDEBRIS = new BlockBlockHard(Material.IRON, "frozen_debris", HR_FROZEN_DEBRIS, "pickaxe", false);
   public static Block FLUIDPOISON = new BlockFluidPoison();
   public static Block FLUIDSLIME = new BlockFluidSlime();
   public static Block FLUIDBIOGENICACID = new BlockFluidBiogenicAcid();
   public static Block FLUIDSULFURICACID = new BlockFluidSulfuricAcid();
   public static Block FLUIDLUMINESCENT = new BlockFluidLuminescent();
   public static Block FLUIDTOXIN = new BlockFluidToxin();
   public static Block FLUIDDISSOLVEDTOXINIUM = new BlockFluidDissolvedToxinium();
   public static Block TOXICPORTALFRAME = new ToxicPortalFrame();
   public static Block TOXICPORTAL = new ToxicomaniaPortal();
   public static Block LOOTBLOB = new BlockLootBlob();
   public static Block ACIDBOMB = new BlockAcidBomb();
   public static Block MININUKE = new MiniNuke();
   public static Block BIOCELL = new BioCell();
   public static Block SWEETNECTARFLOWER = new SweetNectarFlower();
   public static Block VIRULENTCAPSULE = new VirulentCapsule();
   public static Block SLIMEBLOB = new SlimeBlob();
   public static Block SLIMEGLOB = new SlimeGlob();
   public static Block BROWNSLIME = new BrownSlime();
   public static Block CHLORINEBELCHER = new ChlorineBelcher();
   public static Block TOXICBARREL = new ToxicBarrel();
   public static Block BOMBTOXIC = new BlockRotated.HardBlockRotated(
         Material.IRON, "bomb_toxic", HR_RADIOACTIVE_WASTE, "pickaxe", false, false, new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block BOMBSMALL = new BlockRotated.HardBlockRotated(
         Material.IRON, "bomb_small", HR_RADIOACTIVE_WASTE, "pickaxe", false, false, new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.6, 0.75)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block BOMBRUSTED = new BlockRotated.HardBlockRotated(
         Material.IRON, "bomb_rusted", HR_RADIOACTIVE_WASTE, "pickaxe", false, false, new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.3, 0.75)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block RUSTEDPIPE = new RustedPipe();
   public static Block JUNKPILE = new BlockBlock(Material.CLAY, "junk_pile", 0.1F, 0.1F)
      .setSound(SoundTypeCrunchy.CRUNCHY)
      .setHarvest("shovel", 0)
      .setOpaque(false)
      .setFullcube(false)
      .setAABB(new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.25, 0.9), Block.NULL_AABB)
      .setOffsets(EnumOffsetType.XZ);
   public static Block TOXICTALLGRASS = new ToxicTallgrass();
   public static Block TOXICGRASS = new ToxicGrass();
   public static Block TOXICDIRT = new BlockBlockHard(Material.GROUND, "toxic_dirt", HR_RADIOACTIVE_WASTE, "shovel", true)
      .setSound(SoundType.GROUND);
   public static Block SLUDGE = new BlockBlockHard(Material.CLAY, "sludge", HR_RADIOACTIVE_WASTE, "shovel", true).setSound(SoundType.GROUND);
   public static Block NUCLEARWASTE = new BlockRadioactive(Material.CLAY, "nuclear_waste", HR_RADIOACTIVE_WASTE, "shovel", 25, 3, 0, 0)
      .setSound(SoundType.GROUND)
      .setLightLevel(0.4F);
   public static Block JUNK = new BlockBlockHard(Material.CLAY, "junk", HR_RADIOACTIVE_WASTE, "shovel", true).setSound(SoundType.GROUND);
   public static Block SCRAP = new BlockBlockHard(Material.CLAY, "scrap", HR_RADIOACTIVE_WASTE, "shovel", true).setSound(SoundTypeCrunchy.CRUNCHY);
   public static Block SCRAPELECTRONICS = new BlockBlockHard(Material.IRON, "scrap_electronics", HR_RADIOACTIVE_WASTE, "shovel", true)
      .setSound(SoundTypeCrunchy.CRUNCHY);
   public static Block ROTTENPLANKS = new BlockBlock(Material.WOOD, "rotten_planks", 0.4F, 0.2F)
      .setSound(SoundType.WOOD)
      .setHarvest("axe", 0);
   public static Block BRICKSHARDS = new BlockBlock(Material.GROUND, "brick_shards", 0.5F, 0.8F)
      .setSound(SoundTypeShards.SHARDS)
      .setHarvest("shovel", 0);
   public static Block RADIOCOBBLE = new BlockRadioactive(Material.ROCK, "radioactive_cobblestone", HR_RADIOACTIVE_STONE, "pickaxe", 0, 0, 10, 0);
   public static Block RADIOSTONE = new BlockRadioactive(Material.ROCK, "radioactive_stone", HR_RADIOACTIVE_STONE, "pickaxe", 0, 0, 10, 0);
   public static Block RADIOACTIVESPELEOTHEM = new BlockSpeleothem(
      Material.ROCK, "radioactive_speleothem", HR_RADIOACTIVE_WASTE.HARDNESS, HR_RADIOACTIVE_WASTE.RESISTANCE
   );
   public static Block ORELEPIDOLITE = new BlockOre.BlockOreHard(Material.ROCK, "lepidolite_ore", HR_RADIOACTIVE_STONE, "pickaxe", 2, 5);
   public static Block OREARSENIC = new BlockBlockHard(Material.ROCK, "arsenic_ore", HR_ARSENIC, "pickaxe", false);
   public static Block ORETOXINIUM = new BlockBlockHard(Material.ROCK, "toxinium_ore", HR_TOXINIUM, "pickaxe", false);
   public static Block OREWOLFRAM = new BlockBlockHard(Material.ROCK, "wolfram_ore", HR_WOLFRAM_AND_BIOCELLS, "pickaxe", true);
   public static Block ORETITANIUMRADIOACTIVE = new BlockRadioactive(
         Material.ROCK, "radioactive_titanium_ore", HR_RADIOACTIVE_STONE, "pickaxe", 0, 0, 20, 0
      )
      .setisReplaceableOreGen(false);
   public static Block[] toxicGrounds = new Block[]{TOXICGRASS, TOXICDIRT, SLUDGE, NUCLEARWASTE, JUNK};
   public static CustomPlant TOXIBERRYARCANO = CustomPlant.createCustomPlant(
      "toxiberry_arcanophyllum",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:toxiberry_arcanophyllum_seed,1,3,0 arpg:plant_fiber,0,2,0",
      6,
      15,
      0.02F,
      0,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant TOXIBERRYVIBRANT = CustomPlant.createCustomPlant(
      "toxiberry_vibrant",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:toxiberry_vibrant_seed,1,4,0",
      3,
      15,
      0.015F,
      1,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant MOSSPLANT = CustomPlant.createCustomPlant(
      "mossplant",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:mossplant_seed,1,2,0 arpg:plant_fiber,0,2,0",
      1,
      15,
      0.01F,
      0,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant CONTEMPLANT = CustomPlant.createCustomPlant(
         "contemplant",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:contemplant_seed,2,3,0 arpg:plant_fiber,0,2,0",
         6,
         15,
         0.02F,
         2,
         MobEffects.POISON,
         50,
         0,
         0.5F,
         1
      )
      .setSeedRadioactive(30);
   public static CustomPlant MUCOPHILLUS = CustomPlant.createCustomPlant(
         "mucophillus",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:mucophillus_seed,1,2,0",
         8,
         15,
         0.03F,
         4,
         PotionEffects.TOXIN,
         40,
         1,
         0.95F,
         1
      )
      .setSeedRadioactive(40);
   public static CustomPlant MUCOPHILLUSBROWN = CustomPlant.createCustomPlant(
         "brown_mucophillus",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:brown_mucophillus_seed,1,5,0",
         8,
         15,
         0.027F,
         4,
         PotionEffects.TOXIN,
         70,
         0,
         0.95F,
         1
      )
      .setSeedRadioactive(60);
   public static CustomPlant VISCOSA = CustomPlant.createCustomPlant(
      "viscosa",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:viscosa_seed,1,2,0 arpg:plant_fiber,0,2,0",
      6,
      15,
      0.007F,
      3,
      PotionEffects.TOXIN,
      55,
      1,
      0.99F,
      1
   );
   public static CustomPlant TOXIBERRYWEEPING = CustomPlant.createCustomPlant(
         "weeping_toxiberry",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:weeping_toxiberry_seed,2,7,0 arpg:plant_fiber,0,2,0",
         6,
         15,
         0.023F,
         1,
         PotionEffects.TOXIN,
         25,
         0,
         0.9F,
         1
      )
      .setSeedRadioactive(20);
   public static CustomPlant TOXINIA = CustomPlant.createCustomPlant(
      "toxinia",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:toxinia_seed,1,3,0 arpg:plant_fiber,0,2,0",
      6,
      15,
      0.035F,
      1,
      PotionEffects.TOXIN,
      35,
      0,
      0.96F,
      1
   );
   public static CustomPlant ARELIA = CustomPlant.createCustomPlant(
      "arelia",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:arelia_seed,1,2,0 arpg:plant_fiber,0,1,0",
      6,
      15,
      0.025F,
      0,
      null,
      0,
      0,
      0.0F,
      1
   );
   public static CustomPlant DECEIDUS = CustomPlant.createCustomPlant(
         "deceidus",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:deceidus_seed,2,4,0 arpg:plant_fiber,0,1,0",
         6,
         15,
         0.015F,
         3,
         PotionEffects.TOXIN,
         75,
         0,
         0.7F,
         1
      )
      .setSeedRadioactive(10);
   public static CustomPlant JUNKWEED = CustomPlant.createCustomPlant(
         "junkweed",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:junkweed_seed,1,4,0 arpg:plant_fiber,0,1,0",
         6,
         15,
         0.035F,
         0,
         null,
         0,
         0,
         0.0F,
         1
      )
      .setFuelToSeed(4);
   public static CustomPlant TOXEDGE = CustomPlant.createCustomPlant(
         "toxedge",
         0.0F,
         0.0F,
         SoundType.PLANT,
         null,
         toxicGrounds,
         true,
         "arpg:toxedge_seed,1,3,0 arpg:plant_fiber,0,1,0",
         6,
         15,
         0.03F,
         0,
         null,
         0,
         0,
         0.0F,
         1
      )
      .setFuelToSeed(4);
   public static CustomPlant TOXIBULB = CustomPlant.createCustomPlant(
      "toxibulb",
      0.0F,
      0.0F,
      SoundType.PLANT,
      null,
      toxicGrounds,
      true,
      "arpg:toxibulb_seed,1,3,0 arpg:plant_fiber,1,2,0",
      8,
      15,
      0.01F,
      4,
      MobEffects.WITHER,
      100,
      1,
      0.9F,
      3
   );
   public static Block POISONLILY = new PoisonLily();
   public static Block LOPPYTOXIBERRY = new LoppyToxiberry();
   public static Block LOPPYTOXISTEM = new LoppyToxistem();
   public static Block TOXIBERRYSAPLING = new ToxiberryTreeSapling();
   public static Block MUTAFLOWERRED = new MutatedFlowerRed();
   public static Block MUTAFLOWERPINK = new MutatedFlowerPink();
   public static Block GIANTFLOWERLEAVES = new GiantFlowerLeaves();
   public static Block LABPLATING = new BlockBlockHard(Material.CLAY, "lab_plating", HR_BUNKER, "pickaxe", false)
      .setSound(SoundType.METAL)
      .setisReplaceableOreGen(false);
   public static Block RUSTLAMP = new RustLamp();
   public static Block RUSTMETALL = new BlockBlockHard(Material.IRON, "rust_metal", HR_BUNKER, "pickaxe", false).setSound(SoundType.METAL);
   public static Block RUSTARMATURE = new BlockBlockHard(Material.IRON, "rust_armature", HR_BUNKER_MISC, "pickaxe", false)
      .setOpaque(false)
      .setSound(SoundType.METAL)
      .setRenderLayer(BlockRenderLayer.CUTOUT_MIPPED);
   public static Block DARKRUSTMETALL = new BlockBlockHard(Material.IRON, "dark_rust_metal", HR_BUNKER, "pickaxe", false)
      .setSound(SoundType.METAL);
   public static Block BUNKERDOOR = new BlockBunkerDoor();
   public static Block TOXICCHEST = new Chest(Material.WOOD, "toxic_chest", 1.5F, 2.25F, SoundType.WOOD, "axe", 0, EnumChest.FROZEN);
   public static Block RUSTEDCHEST = new Chest(Material.IRON, "rusted_chest", 2.5F, 4.25F, SoundType.METAL, "pickaxe", 1, EnumChest.FROZEN);
   public static Block MOBSPAWNERRUSTED = new BlockSpawner(Material.ROCK, "rusted_spawner", HR_SPAWNER_RUSTED, 20, 35)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false);
   public static Block TOXIBERRYLOG = new ToxiberryTreeLog();
   public static Block TOXIBERRYLEAVES = new ToxiberryTreeLeaves();
   public static Block TOXICPLANKS = new BlockBlockHard(Material.WOOD, "toxic_planks", HR_TOXIBERRY_FURNITURE, "axe", true)
      .setSound(SoundType.WOOD);
   public static Block TOXICTABLE = new Table(
      Material.WOOD,
      "toxic_table",
      HR_TOXIBERRY_FURNITURE.HARDNESS,
      HR_TOXIBERRY_FURNITURE.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_TOXIBERRY_FURNITURE.LVL
   );
   public static Block TOXICSTAIRS = new Stairs(
      TOXICPLANKS.getDefaultState(),
      Material.WOOD,
      "toxic_planks_stairs",
      HR_TOXIBERRY_FURNITURE.HARDNESS,
      HR_TOXIBERRY_FURNITURE.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_TOXIBERRY_FURNITURE.LVL
   );
   public static Block TOXICPILASTER = new Pilaster(
      Material.WOOD,
      "toxic_planks_pilaster",
      HR_TOXIBERRY_FURNITURE.HARDNESS,
      HR_TOXIBERRY_FURNITURE.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_TOXIBERRY_FURNITURE.LVL
   );
   public static Block TOXICCHAIR = new Chair(
      Material.WOOD,
      "toxic_planks_chair",
      HR_TOXIBERRY_FURNITURE.HARDNESS,
      HR_TOXIBERRY_FURNITURE.RESISTANCE,
      SoundType.WOOD,
      "axe",
      HR_TOXIBERRY_FURNITURE.LVL
   );
   public static Block TOXICTORCH = new ToxicTorch();
   public static Block TOXICCHANDELIER = new ToxicChandelier();
   public static Block FLUIDHYDROTHERMAL = new BlockFluidHydrothermal();
   public static Block FLUIDLARVAWATER = new BlockFluidLarvaWater();
   public static Block DEEPROCK = new BlockBlockHard(Material.ROCK, "deep_rock", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block CALCITE = new BlockBlockHard(Material.ROCK, "calcite", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block CAVEONYX = new BlockBlockHard(Material.ROCK, "cave_onyx", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block GREENONYX = new BlockBlockHard(Material.ROCK, "green_onyx", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block SELENITE = new BlockBlockHard(Material.ROCK, "selenite", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block MAGICSTONE = new BlockBlockHard(Material.ROCK, "magic_stone", HR_DUNGEON_STONES, "pickaxe", false)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setFullcube(true)
      .setisReplaceableOreGen(false);
   public static Block GLOWINGVEIN = new GlowingVein();
   public static Block DOLERITE = new BlockBlockHard(Material.ROCK, "dolerite", HR_DUNGEON_STONES, "pickaxe", false);
   public static Block SELENITESPELEOTHEM = new BlockSpeleothem(Material.ROCK, "selenite_speleothem", 1.0F, 2.0F);
   public static Block BLUEGLOWINGMUSH = new BlueGlowingMushroom();
   public static Block GREENGLOWINGMUSH = new GreenGlowingMushroom();
   public static Block WOODENSHAFT = new WoodenShaft();
   public static Block LARVABLOCK = new LarvaBlock();
   public static Block SELENITECRYSTALS = new SeleniteCrystal();
   public static Block CAVECRYSTALS = new CaveCrystal();
   public static Block GLOWINGCAVECRYSTALS = new GlowingCaveCrystal();
   public static Block DUNGEONPORTALFRAME = new BlockBlock(Material.ROCK, "dungeon_portal_frame", 10.0F, 60.0F).setHarvest("pickaxe", 5);
   public static Block DUNGEONPORTAL = new DungeonPortal();
   public static Block DUNGEONTRAVELFLOOR = new DungeonFloorLadder();
   public static Block DUNGEONTRAVELTOP = new DungeonTopLadder();
   public static Block MOBSPAWNERANCIENT = new BlockSpawner(Material.ROCK, "ancient_spawner", HR_SPAWNER_ANCIENT, 25, 45)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false);
   public static Block CRYSTALLIZER = new Crystallizer();
   public static Block PYROCRYSTALLCONVERTER = new PyrocrystallineConverter();
   public static Block CRYSTALCHEST = new Chest(Material.ROCK, "crystal_chest", 3.5F, 4.25F, SoundType.STONE, "pickaxe", 1, EnumChest.FROZEN);
   public static Block CRYSTALTABLE = new Table(Material.WOOD, "crystal_table", 3.4F, 35.0F, SoundType.STONE, "pickaxe", 0);
   public static Block CRYSTALCHAIR = new Chair(Material.WOOD, "crystal_chair", 3.4F, 35.0F, SoundType.STONE, "pickaxe", 0);
   public static Block CRYSTALTORCH = new CrystalTorch();
   public static Block CRYSTALVASE = new BlockBlock(Material.GLASS, "crystal_vase", 2.4F, 35.0F)
      .setSound(SoundTypeShards.SHARDS)
      .setPlaceAsVase(true)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.1875, 0.8125))
      .setOpaque(false)
      .setFullcube(false);
   public static Block CRYSTALCHANDELIER = new CrystalChandelier();
   public static Block DOLERITEBRICKS = new BlockBlockHard(Material.ROCK, "dolerite_bricks", HR_DOLERITE_BRICKS, "pickaxe", false);
   public static Block DOLERITESTAIRS = new Stairs.HardStairs(
      DOLERITEBRICKS.getDefaultState(), Material.ROCK, "dolerite_stairs", SoundType.STONE, HR_DOLERITE_BRICKS, "pickaxe", false
   );
   public static Block DOLERITEPILASTER = new Pilaster.HardPilaster(
      Material.ROCK, "dolerite_pilaster", SoundType.STONE, HR_DOLERITE_BRICKS, "pickaxe", false
   );
   public static Block DOLERITECOLUMN = new DoleriteColumn();
   public static Block OREMITHRIL = new BlockBlockHard(Material.ROCK, "mithril_ore", HR_MITHRIL_ADAMANTIUM_ORE, "pickaxe", false)
      .setisReplaceableOreGen(false);
   public static Block OREADAMANTIUM = new BlockBlockHard(Material.ROCK, "adamantium_ore", HR_MITHRIL_ADAMANTIUM_ORE, "pickaxe", false)
      .setisReplaceableOreGen(false);
   public static Block AQUATICAPORTAL = new AquaticaPortal();
   public static Block AQUATICAPORTALFRAME = new AquaticaPortalFrame(Material.ROCK, "aquatica_portal_frame", 14.0F, 46.0F);
   public static Block SHELLROCK = new BlockBlockHard(Material.ROCK, "shellrock", HR_SHELLROCKS, "pickaxe", false);
   public static Block CHALKROCK = new BlockBlockHard(Material.ROCK, "chalkrock", HR_SHELLROCKS, "pickaxe", false);
   public static Block STROMATOLITE = new BlockBlockHard(Material.ROCK, "stromatolite", HR_SHELLROCKS, "pickaxe", false);
   public static Block SEASTONE = new BlockBlockHard(Material.ROCK, "sea_stone", HR_SHELLROCKS, "shovel", false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block CORALBRICKS = new BlockBlockHard(Material.ROCK, "coral_bricks", HR_CORALBRICKS, "pickaxe", false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block PEARLESCENTBRICKS = new BlockBlockHard(Material.ROCK, "pearlescent_bricks", HR_CORALBRICKS, "pickaxe", false);
   public static Block RADIANTBRICK = new BlockBlockHard(Material.ROCK, "radiant_brick", HR_CORALBRICKS, "pickaxe", false);
   public static Block CORALTORCH = new CoralTorch();
   public static Block CORALTABLE = new Table(Material.ROCK, "coral_table", 7.5F, 40.0F, SoundType.STONE, "pickaxe", 0);
   public static Block CORALCHAIR = new Chair(Material.ROCK, "coral_chair", 7.5F, 40.0F, SoundType.STONE, "pickaxe", 0);
   public static Block CORALCHANDELIER = new CoralChandelier();
   public static Block CORALVASE = new BlockBlock(Material.ROCK, "coral_vase", 7.3F, 40.0F)
      .setSound(SoundTypeShards.SHARDS)
      .setPlaceAsVase(true)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125), new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block PEARLESCENTSTAIRS = new Stairs.HardStairs(
      PEARLESCENTBRICKS.getDefaultState(), Material.ROCK, "pearlescent_stairs", SoundType.STONE, HR_CORALBRICKS, "pickaxe", false
   );
   public static Block PEARLESCENTPILASTER = new Pilaster.HardPilaster(
      Material.ROCK, "pearlescent_pilaster", SoundType.STONE, HR_CORALBRICKS, "pickaxe", false
   );
   public static Block CORALYELLOW = new BlockOre.BlockOreHard(Material.ROCK, "coral_yellow", HR_CORALS, "pickaxe", 0, 0);
   public static Block CORALPINK = new BlockOre.BlockOreHard(Material.ROCK, "coral_pink", HR_CORALS, "pickaxe", 0, 0);
   public static Block CORALWHITE = new BlockOre.BlockOreHard(Material.ROCK, "coral_white", HR_CORALS, "pickaxe", 0, 0);
   public static Block CORALRED = new BlockOre.BlockOreHard(Material.ROCK, "coral_red", HR_CORALS, "pickaxe", 0, 0);
   public static Block CORALORANGE = new BlockOre.BlockOreHard(Material.ROCK, "coral_orange", HR_CORALS, "pickaxe", 0, 0)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block CORALLIMORPHABLUE = new Corallimorpha("corallimorpha_blue");
   public static Block CORALLIMORPHARED = new Corallimorpha("corallimorpha_red");
   public static Block CORALLIMORPHAYELLOW = new Corallimorpha("corallimorpha_yellow");
   public static Block CORALLIMORPHALILAC = new Corallimorpha("corallimorpha_lilac");
   public static Block CORALLIMORPHAPINK = new Corallimorpha("corallimorpha_pink");
   public static Block CORALLIMORPHAGREEN = new Corallimorpha("corallimorpha_green");
   public static Block CORALLIMORPHABROWN = new Corallimorpha("corallimorpha_brown");
   public static Block MINICORALPURPLE = new MiniCoral("acropora_purple", BlockRenderLayer.CUTOUT);
   public static Block MINICORALWHITE = new MiniCoral("acropora_white", BlockRenderLayer.CUTOUT);
   public static Block MINICORALBROWN = new MiniCoral("gorgonaria_brown", BlockRenderLayer.CUTOUT);
   public static Block MINICORALRED = new MiniCoral("gorgonaria_red", BlockRenderLayer.CUTOUT);
   public static Block MINICORALWHITE2 = new MiniCoral("gorgonaria_white", BlockRenderLayer.CUTOUT);
   public static Block MINICORALBLUE = new MiniCoral("helioporacea", BlockRenderLayer.CUTOUT);
   public static Block MINICORALPURPLEBIG = new MiniCoral("acropora_purple_big", BlockRenderLayer.CUTOUT);
   public static Block MINICORALWHITEBIG = new MiniCoral("acropora_white_big", BlockRenderLayer.CUTOUT);
   public static Block MINICORALBROWNBIG = new MiniCoral("gorgonaria_brown_big", BlockRenderLayer.CUTOUT);
   public static Block MINICORALREDBIG = new MiniCoral("gorgonaria_red_big", BlockRenderLayer.CUTOUT);
   public static Block MINICORALWHITE2BIG = new MiniCoral("gorgonaria_white_big", BlockRenderLayer.CUTOUT);
   public static Block MINICORALFAVIARED = new MiniCoral("favia_red", BlockRenderLayer.SOLID).setOffset(0.3);
   public static Block MINICORALFAVIAGREEN = new MiniCoral("favia_green", BlockRenderLayer.SOLID).setOffset(0.3);
   public static Block MINICORALFAVIABLUE = new MiniCoral("favia_blue", BlockRenderLayer.SOLID).setOffset(0.3);
   public static Block MINICORALFAVIAYELLOW = new MiniCoral("favia_yellow", BlockRenderLayer.SOLID).setOffset(0.3);
   public static Block MINICORALBRAIN = new MiniCoral("favia_brain", BlockRenderLayer.SOLID).setOffset(0.25);
   public static Block GIANTSHELL = new GiantShell("giant_shell", BlockRenderLayer.SOLID).setOffset(0.5);
   public static Block ACTINIFORABLUE = new MiniCoral("actinifora_ramiform", BlockRenderLayer.CUTOUT).setDropGlowingPearls().setLightLevel(0.7F);
   public static Block ACTINIFORAYELLOW = new MiniCoral("actinifora_bright", BlockRenderLayer.CUTOUT).setDropGlowingPearls().setLightLevel(0.8F);
   public static Block ACTINIFORARED = new MiniCoral("actinifora_arenaceous", BlockRenderLayer.CUTOUT).setDropGlowingPearls().setLightLevel(0.6F);
   public static Block ACTINIFORABLUEBIG = new MiniCoral("actinifora_segregate", BlockRenderLayer.CUTOUT).setLightLevel(0.8F);
   public static Block ACTINIFORAREDBIG = new MiniCoral("actinifora_giant", BlockRenderLayer.CUTOUT).setLightLevel(0.8F);
   public static Block PALMLOG = new PalmLog();
   public static Block PALMLEAVES = new PalmLeaves("palm_leaves");
   public static Block PALMSAPLING = new PalmSapling();
   public static Block PALMDRYLEAVES = new PalmLeaves("palm_dry_leaves");
   public static Block PALMFRUITBUNCH = new PalmFruitBunch();
   public static Block PALMTABLE = new Table(Material.WOOD, "palm_table", 2.2F, 2.2F, SoundType.WOOD, "axe", 0);
   public static Block PALMCHAIR = new Chair(Material.WOOD, "palm_chair", 2.2F, 2.2F, SoundType.WOOD, "axe", 0);
   public static Block PALMTORCH = new PalmTorch();
   public static Block PALMPLANKS = new BlockBlockHard(Material.WOOD, "palm_planks", HR_PALM, "axe", false).setSound(SoundType.WOOD);
   public static Block SEAWEEDBLOCK = new BlockSeaweed();
   public static Block SEAGRASS = new Seagrass();
   public static Block LIVINGSPONGE = new LivingSponge();
   public static Block METALLICCORAL = new MetallicCoral();
   public static Block SHELLROCKSPELEOTHEM = new BlockSpeleothem(Material.ROCK, "shellrock_speleothem", 1.0F, 2.0F);
   public static Block TRITONHEARTH = new TritonHearth();
   public static Block TIDEBEACON = new TideBeacon();
   public static Block MOBSPAWNERAQUATIC = new BlockSpawner(Material.IRON, "aquatic_spawner", HR_SPAWNER_AQUATIC, 30, 55)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false);
   public static Block SEALOCK = new SeaLock();
   public static Block SANCTUARYBRICKS = new BlockBlockHard(Material.ROCK, "sanctuary_bricks", HR_SANKTUARYBRICKS, "pickaxe", false);
   public static Block SEAURCHIN = new SeaUrchin();
   public static Block STORMLEDGEPORTAL = new StormledgePortal();
   public static Block STORMLEDGEPORTALFRAME = new StormledgePortalFrame(Material.ROCK, "stormledge_portal_frame", 24.0F, 56.0F);
   public static Block ETHERITEINVOCATOR = new BlockEtheriteInvocator();
   public static Block METALLICROCK = new BlockBlockHard(Material.ROCK, "metallic_rock", HR_STORMBRASS_ROCKS, "pickaxe", false);
   public static Block BEAMROCK = new BeamRock();
   public static Block METALLICROCKPILASTER = new Pilaster.HardPilaster(
      Material.ROCK, "metallic_rock_pilaster", SoundType.STONE, HR_STORMBRASS_ROCKS, "pickaxe", false
   );
   public static Block STORMMASONRY = new BlockBlockHard(Material.ROCK, "storm_masonry", HR_ZARPION_ROCKS, "pickaxe", false).setOpacity(0);
   public static Block STORMPLATE = new BlockBlockHard(Material.ROCK, "storm_plate", HR_ZARPION_ROCKS, "pickaxe", false).setOpacity(0);
   public static Block STORMPILASTER = new Pilaster.HardPilaster(
      Material.ROCK, "storm_pilaster", SoundType.STONE, HR_ZARPION_ROCKS, "pickaxe", false
   );
   public static Block STORMCONDUCTOR = new StormConductor();
   public static Block STORMRACK = new StormRack();
   public static Block FULMINIFLORA = new BlockBlock(Material.ROCK, "fulminiflora", 8.0F, 35.0F);
   public static Block FULMINIHERBA = new BlockFulminiherba();
   public static Block ARTHROSTELECHALOGBRASS = new ArthrostelechaLog(true);
   public static Block ARTHROSTELECHALOGPINK = new ArthrostelechaLog(false);
   public static Block ARTHROSTELECHALEAVESBRASS = new ArthrostelechaLeaves(true);
   public static Block ARTHROSTELECHALEAVESPINK = new ArthrostelechaLeaves(false);
   public static Block GLOWBUSH = new Glowbush();
   public static Block FULMINIORTUMBULB = new FulminiortumBulb();
   public static Block FULMINIORTUMBONNY = new FulminiortumBonny();
   public static Block SHIMMERINGBEASTBLOOM = new ShimmeringBeastbloom();
   public static Block LAIGANIASTEM = new BlockBlock(Material.ROCK, "laigania_stem", 4.5F, 40.0F).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   public static Block LAIGANIAPULP = new BlockBlock(Material.ROCK, "laigania_pulp", 4.5F, 40.0F)
      .setLightOptions(5, 240)
      .setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   public static Block VENTASTOLATRUNK = new BlockBlock(Material.ROCK, "ventastola_trunk", 4.5F, 40.0F)
      .setLightLevel(0.25F)
      .setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   public static Block VENTASTOLATCATCHER = new Pane(Material.WEB, "ventastola_catcher", 1.0F, 10.0F, true).setSound(SoundType.CLOTH);
   public static Block MELANZABULB = new BlockBlock(Material.PLANTS, "melanza_bulb", 3.0F, 15.0F)
      .setOpaque(false)
      .setLightOptions(13, 220)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   public static Block MELANZASTEM = new MelanzaStem();
   public static Block ELECTRICVINE = new BlockBlock(Material.ROCK, "electric_vine", 3.5F, 50.0F)
      .setOpaque(false)
      .setFullcube(false)
      .setOpacity(0)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block ELECTROFERNLEAVES = new ElectrofernLeaves();
   public static Block ELECTROFERNSTEM = new DecorativePipe(
      Material.WOOD,
      "electrofern_stem",
      HR_STORM_FOLIAGE,
      "axe",
      CreativeTabs.DECORATIONS,
      SoundTypeCrunchy.CRUNCHY,
      0.125F,
      0.125F,
      (state, facing) -> state.getBlock() == ELECTROFERNLEAVES
         ? state.getValue(ElectrofernLeaves.FACING) == facing
              // FIX: Add `BlocksRegister.` before `ELECTROFERNSTEM`
         : state.getBlock() == BlocksRegister.ELECTROFERNSTEM || state.getBlock() == FULMINIFLORA || state.getBlock() == Blocks.STONE
   );
   public static Block ARTHROHELIASTEM = new BlockRotated.HardBlockRotated(
         Material.ROCK, "arthrohelia_stem", HR_STORM_FOLIAGE, "axe", false, true, Block.FULL_BLOCK_AABB
      )
      .setOpaque(false)
      .setFullCube(false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block ARTHROHELIASTALK = new BlockRotated.HardBlockRotated(
         Material.ROCK, "arthrohelia_stalk", HR_STORM_FOLIAGE, "axe", false, true, Block.FULL_BLOCK_AABB
      )
      .setOpaque(false)
      .setFullCube(false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block ARTHROHELIASPIKE = new BlockRotated.HardBlockRotated(
         Material.ROCK, "arthrohelia_spike", HR_STORM_FOLIAGE, "axe", false, true, Block.FULL_BLOCK_AABB
      )
      .setOpaque(false)
      .setFullCube(false)
      .setRenderLayer(BlockRenderLayer.CUTOUT);
   public static Block EXOHELIASPIKE = new ExoheliaSpike();
   public static Block ORESTORMSTEEL = new BlockBlockHard(Material.ROCK, "stormsteel_ore", HR_STORMSTEEL_ORE, "pickaxe", false)
      .setisReplaceableOreGen(false);
   public static Block MOBSPAWNERSTORM = new BlockSpawner(Material.IRON, "storm_spawner", HR_SPAWNER_STORM, 35, 65)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false);
   public static Block BLOCKDETECTOR = new BlockDetector();
   public static Block ADVBLOCKDETECTOR = new AdvancedBlockDetector();
   public static Block BLOCKPLACER = new BlockPlacer();
   public static Block CRYSTALSPHERE = new BlockCrystalSphere();
   public static Block FLUIDDARKNESS = new BlockFluidDarkness();
   public static Block SPELLFORGE = new SpellForgeBlock();
   public static Block MANABOTTLE = new ManaBottle();
   public static Block TOPAZCRYSTAL = new TopazCrystal();
   public static Block NETHERMELTER = new NetherMelter();
   public static Block SACRIFICIALALTAR = new SacrificialAltar();
   public static Block INFERNUMFURNACE = new InfernumFurnace();
   public static Block BONESBLOCK = new BlockBlock(Material.GROUND, "bones_block", 1.3F, 25.0F)
      .setHarvest("shovel", 1)
      .setSound(SoundTypeCrunchy.CRUNCHY);
   public static Block BONESPILE = new BonesPile();
   public static Block ALCHEMICLAB = new AlchemicLab();
   public static Block ALCHEMICVAPORIZER = new AlchemicVaporizer();
   public static Block RUNICMIRROR = new BlockRunicMirror();
   public static Block ORERUBY = new BlockOre(Material.ROCK, "ruby_ore", 3.0F, 15.0F, 2, 8);
   public static Block ORESAPPHIRE = new BlockOre(Material.ROCK, "sapphire_ore", 3.0F, 15.0F, 2, 8);
   public static Block ORECITRINE = new BlockOre(Material.ROCK, "citrine_ore", 3.0F, 15.0F, 2, 8);
   public static Block ORETOPAZ = new BlockOre(Material.ROCK, "topaz_ore", 2.0F, 12.0F, 2, 5);
   public static Block OREAMETHYST = new BlockOre(Material.ROCK, "amethyst_ore", 2.0F, 12.0F, 2, 5);
   public static Block ORERHINESTONE = new BlockOre(Material.ROCK, "rhinestone_ore", 2.0F, 12.0F, 2, 5);
   public static Block DRILL = new BlockDrill();
   public static Block AUGMENTCUT = new BlockAssemblyAugment(
         Material.IRON, "turning_augment", 5.0F, 5.0F, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block AUGMENTPRESS = new BlockAssemblyAugment(
         Material.IRON, "press_augment", 5.0F, 5.0F, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block AUGMENTWELD = new BlockAssemblyAugment(
         Material.IRON, "weld_augment", 5.0F, 5.0F, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block AUGMENTPLASMA = new BlockAssemblyAugment(
         Material.IRON, "plasma_spray_augment", 5.0F, 5.0F, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block AUGMENTMOLECULAR = new BlockAssemblyAugment(
         Material.IRON, "molecular_printer_augment", 5.0F, 5.0F, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0)
      )
      .setSound(SoundType.METAL)
      .setOpaque(false)
      .setFullCube(false);
   public static Block ASSEMBLYTABLE = new AssemblyTable();
   public static Block WEAPONSPAWNER = new BlockWeaponSpawner();
   public static Block ELECTROMAGNET = new BlockElectromagnet();
   public static Block COLLIDERPIPE = new ColliderPipe();
   public static Block COLLIDER = new BlockCollider();
   public static Block STONESPELEOTHEM = new BlockSpeleothem(Material.ROCK, "stone_speleothem", 1.0F, 2.0F);
   public static Block BLOCKITEMCHARGER = new BlockItemCharger();
   public static Block ORECHROMIUM = new BlockBlock(Material.ROCK, "chromium_ore", 3.5F, 15.0F)
      .setisReplaceableOreGen(false)
      .setHarvest("pickaxe", 2);
   public static Block OREZINC = new BlockBlock(Material.ROCK, "zinc_ore", 3.0F, 15.0F).setisReplaceableOreGen(false).setHarvest("pickaxe", 1);
   public static Block OREALUMINIUM = new BlockBlock(Material.ROCK, "aluminium_ore", 3.0F, 10.0F);
   public static Block BAUXITEBLOCK = new BlockOre(Material.CLAY, "bauxite_block", 0.6F, 3.0F, 0, 1)
      .setHarvestLvl("shovel", 0)
      .setSoundType(SoundType.GROUND);
   public static Block FIERYBEANLOG = new FieryBeanLog();
   public static Block FIERYBEANBLOCK = new FieryBeanBlock();
   public static Block FIERYBEANLEAVES = new FieryBeanLeaves("fiery_bean_leaves");
   public static Block FIERYBEANSAPLING = new FieryBeanSapling();
   public static Block SULFURCRYSTAL = new SulfurCrystal();
   public static Block FLUIDSULFURICGAS = new BlockFluidSulfuricGas();
   public static Block FIERYBEANPLANKS = new BlockBlock(Material.WOOD, "fiery_bean_planks", 1.0F, 10.0F)
      .setSound(SoundType.WOOD)
      .setHarvest("axe", 0);
   public static Block NATIVESILVER = new NativeSilver();
   public static Block VOIDCRYSTAL = new VoidCrystal(Material.PORTAL, "void_crystal_block", HR_SPAWNER_VOIDCRYSTAL, 10, 30);
   public static Block ORESILVER = new BlockBlock(Material.ROCK, "silver_ore", 3.0F, 15.0F).setHarvest("pickaxe", 2).setisReplaceableOreGen(false);
   public static Block RHINESTONEBLOCK = new BlockBlock(Material.ROCK, "rhinestone_block", 4.0F, 40.0F);
   public static Block INDUSTRIALMIXER = new IndustrialMixer();
   public static Block MAGICWOOD = new MagicLog();
   public static Block RESEARCHTABLE = new ResearchTable();
   public static Block MAGICBRICKS = new BlockBlock(Material.ROCK, "magic_bricks", 2.5F, 50.0F).setHarvest("pickaxe", 2);
   public static Block MAGICORNAMENT = new MagicOrnament(Material.ROCK, "magic_ornament", 2.8F, 55.0F).setHarvest("pickaxe", 2);
   public static Block STAINEDGLASSMARIGOLD = new BlockBlock(Material.GLASS, "stained_glass_marigold", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block STAINEDGLASSSUNSET = new BlockBlock(Material.GLASS, "stained_glass_sunset", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block STAINEDGLASSROSE = new BlockBlock(Material.GLASS, "stained_glass_rose", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block STAINEDGLASSSKY = new BlockBlock(Material.GLASS, "stained_glass_sky", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block STAINEDGLASSSTARLIGHT = new BlockBlock(Material.GLASS, "stained_glass_starlight", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block STAINEDGLASSAURORA = new BlockBlock(Material.GLASS, "stained_glass_aurora", 2.5F, 40.0F)
      .setRenderLayer(BlockRenderLayer.TRANSLUCENT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNRUBY = new BlockBlock(Material.GLASS, "ruby_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNSAPPHIRE = new BlockBlock(Material.GLASS, "sapphire_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNCITRINE = new BlockBlock(Material.GLASS, "citrine_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNEMERALD = new BlockBlock(Material.GLASS, "emerald_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNAMETHYST = new BlockBlock(Material.GLASS, "amethyst_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNTOPAZ = new BlockBlock(Material.GLASS, "topaz_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNRHINESTONE = new BlockBlock(Material.GLASS, "rhinestone_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LANTERNDIAMOND = new BlockBlock(Material.GLASS, "diamond_lantern", 2.5F, 40.0F)
      .setLightLevel(1.0F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPRUBY = new BlockBlock(Material.GLASS, "ruby_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPSAPPHIRE = new BlockBlock(Material.GLASS, "sapphire_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPCITRINE = new BlockBlock(Material.GLASS, "citrine_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPEMERALD = new BlockBlock(Material.GLASS, "emerald_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPAMETHYST = new BlockBlock(Material.GLASS, "amethyst_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPTOPAZ = new BlockBlock(Material.GLASS, "topaz_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPRHINESTONE = new BlockBlock(Material.GLASS, "rhinestone_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block LAMPDIAMOND = new BlockBlock(Material.GLASS, "diamond_lamp", 1.5F, 30.0F)
      .setAABB(new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125))
      .setOpaque(false)
      .setFullcube(false)
      .setLightLevel(0.93F)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block DECORATIVECHAIN = new DecorativeChain();
   public static Block SPLITTER = new Splitter();
   public static Block CREATIVEELEMENTDISTRIBUTOR = new CreativeElementDistributor();
   public static Block RETORT = new Retort();
   public static Block GEMSPARKBLOCK = new GemsparkBlock();
   public static Block BLOCKVIAL = new BlockVial();
   public static Block BOOKCASEWOODEN = new Bookcase("wooden_bookcase", SoundType.WOOD, "axe", 0);
   public static Block GLOSSARY = new Glossary();
   public static Block ABSORPTIONTOTEM = new AbsorptionTotem();
   public static Block DISENCHANTMENTTABLE = new DisenchantmentTable();
   public static Block FLUIDMANAOIL = new BlockFluidManaOil();
   public static Block MANAOILSPELEOTHEM = new BlockSpeleothem(Material.WOOD, "manaoil_speleothem", 0.5F, 2.0F, SoundType.WOOD)
      .setAlwaysDrops();
   public static Block MANAFLOWERLEAVES = new ManaFlowerLeaves();
   public static Block MANAFLOWER = new ManaFlowerLeaves.ManaFlower();
   public static Block HEALTHFLOWERLEAVES = new HealthFlowerLeaves();
   public static Block HEALTHFLOWER = new HealthFlowerLeaves.HealthFlower();
   public static Block TOMATOSTEM = new TomatoStem();
   public static Block FLUIDPETROLEUM = new BlockFluidPetroleum();
   public static Block FLUIDGASOLINE = new BlockFluidGasoline();
   public static Block FLUIDFUELOIL = new BlockFluidFuelOil();
   public static Block FLUIDNITRICACID = new BlockFluidNitricAcid();
   public static Block FLUIDNATURALGAS = new BlockFluidNaturalGas();
   public static Block ORETITANIUM = new BlockBlock(Material.ROCK, "titanium_ore", 4.0F, 50.0F).setisReplaceableOreGen(false);
   public static Block MACHINERYCASING = new BlockBlock(Material.IRON, "machinery_casing", 3.0F, 50.0F).setSound(SoundType.METAL);
   public static Block CHROMIUMGLASS = new BlockBlock(Material.IRON, "hardened_chromium_glass", 10.0F, 1000.0F)
      .setSound(SoundType.GLASS)
      .setRenderLayer(BlockRenderLayer.CUTOUT)
      .setOpaque(false)
      .setCreativeTab(CreativeTabs.DECORATIONS);
   public static Block ASHBLOCK = new AshBlock();
   public static Block SILVERBLOCK = new BlockBlock(Material.IRON, "silver_block", 5.0F, 60.0F).setSound(SoundType.METAL);
   public static Block ORESALT = new BlockOre(Material.ROCK, "salt_ore", 1.3F, 15.0F, 0, 2);
   public static Block MONEYBOX = new BlockMoneyBox();
   public static Block BLOCKCALIBRATIONBUNDLE = new BlockCalibrationBundle();
   public static Block SOULCATCHER = new BlockSoulCatcher();
   public static Block MANAPUMP = new ManaPump();
   public static Block SIEVE = new BlockSieve(Material.WOOD, "sieve");
   public static Block ELECTRICSIEVE = new BlockElectricSieve();
   public static Block CHESTFROZEN = new BlockARPGChest(
      Material.WOOD, "chest_frozen", HR_FROZEN_STONE, "axe", SoundType.WOOD, EnumChest.FROZEN
   );
   public static Block CHESTTOXIC = new BlockARPGChest(
      Material.WOOD, "chest_toxic", HR_TOXIBERRY_LOG, "axe", SoundType.WOOD, EnumChest.TOXIC
   );
   public static Block CHESTRUSTED = new BlockARPGChest(
      Material.IRON, "chest_rusted", HR_BUNKER, "pickaxe", SoundType.METAL, EnumChest.RUSTED
   );
   public static Block CHESTROTTEN = new BlockARPGChest(
      Material.WOOD, "chest_rotten", HR_MITHRIL_ADAMANTIUM_ORE, "axe", SoundType.WOOD, EnumChest.ROTTEN
   );
   public static Block CHESTCRYSTAL = new BlockARPGChest(
      Material.ROCK, "chest_crystal", HR_DOLERITE_BRICKS, "pickaxe", SoundType.STONE, EnumChest.CRYSTAL
   );
   public static Block CHESTSUNKEN = new BlockARPGChest(Material.WOOD, "chest_sunken", HR_CORALS, "axe", SoundType.WOOD, EnumChest.SUNKEN);
   public static Block CHESTCORAL = new BlockARPGChest(
      Material.ROCK, "chest_coral", HR_CORALBRICKS, "pickaxe", SoundType.STONE, EnumChest.CORAL
   );
   public static Block CHESTSTORM = new BlockARPGChest(
      Material.IRON, "chest_storm", HR_ZARPION_ROCKS, "pickaxe", SoundType.ANVIL, EnumChest.STORM
   );
   public static Block DEBUGCOLORBLOCK = new DebugColorBlock();
   public static Block MAGICGENERATOR = new MagicGenerator();
   public static Block TEAMBANNER = new BlockTeamBanner();

   public static void setupAfterItems() {
      ((BlockOre)OREDEMONITE).setOreDrops(new OreDrop(1, 1, ItemsRegister.DEMONITE, 5), new OreDrop(0, 3, ItemsRegister.DEMONITESHARD, 7));
      ((BlockOre)OREICEGL).setOreDrops(new OreDrop(1, 1, ItemsRegister.ICEGEM, 7));
      ((BlockOre)OREICESN).setOreDrops(new OreDrop(1, 1, ItemsRegister.ICEGEM, 7));
      ((BlockOre)ORERUBY).setOreDrops(new OreDrop(1, 1, ItemsRegister.RUBY, 0));
      ((BlockOre)ORESAPPHIRE).setOreDrops(new OreDrop(1, 1, ItemsRegister.SAPPHIRE, 0));
      ((BlockOre)ORECITRINE).setOreDrops(new OreDrop(1, 1, ItemsRegister.CITRINE, 0));
      ((BlockOre)ORETOPAZ).setOreDrops(new OreDrop(1, 1, ItemsRegister.TOPAZ, 0));
      ((BlockOre)OREAMETHYST).setOreDrops(new OreDrop(1, 1, ItemsRegister.AMETHYST, 0));
      ((BlockOre)ORERHINESTONE).setOreDrops(new OreDrop(1, 1, ItemsRegister.RHINESTONE, 0));
      ((BlockOre)BAUXITEBLOCK).setOreDrops(new OreDrop(2, 4, ItemsRegister.BAUXITE, 1));
      ((BlockOre)ORESALT).setOreDrops(new OreDrop(1, 3, ItemsRegister.SALT, 1));
      ((BlockBlock)RADIOSTONE).setItemDropped("arpg:radioactive_cobblestone");
      ((BlockBlock)STROMATOLITE).setItemDropped("arpg:chalkrock");
      ((BlockOre.BlockOreHard)ORELEPIDOLITE).setOreDrops(new OreDrop[]{new OreDrop(1, 1, ItemsRegister.LEPIDOLITE, 7)});
      ((BlockOre.BlockOreHard)CORALORANGE).setOreDrops(new OreDrop[]{new OreDrop(1, 2, ItemsRegister.CORAL, 7)});
      ((BlockOre.BlockOreHard)CORALWHITE).setOreDrops(new OreDrop[]{new OreDrop(1, 2, ItemsRegister.CORAL, 7)});
      ((BlockOre.BlockOreHard)CORALYELLOW).setOreDrops(new OreDrop[]{new OreDrop(1, 2, ItemsRegister.CORAL, 7)});
      ((BlockOre.BlockOreHard)CORALRED).setOreDrops(new OreDrop[]{new OreDrop(1, 2, ItemsRegister.CORAL, 7)});
      ((BlockOre.BlockOreHard)CORALPINK).setOreDrops(new OreDrop[]{new OreDrop(1, 2, ItemsRegister.CORAL, 7)});
   }

   public static void registerBlocks() throws IllegalArgumentException, IllegalAccessException {
      HashMap<Block, Integer> fuels = new HashMap<>();
      fuels.put(FIERYBEANLOG, 60);
      fuels.put(PALMLOG, 20);
      fuels.put(PALMDRYLEAVES, 5);
      fuels.put(ROTTENPLANKS, 8);
      fuels.put(TOXICPLANKS, 12);
      fuels.put(TOXICSTAIRS, 9);
      fuels.put(TOXICPILASTER, 6);
      fuels.put(JUNKPILE, 5);
      fuels.put(WOODENSHAFT, 5);
      fuels.put(TOXICCHEST, 50);
      fuels.put(TOXICTABLE, 25);
      fuels.put(TOXICCHAIR, 20);
      fuels.put(TOXICTORCH, 2);
      fuels.put(TOXIBERRYSAPLING, 15);
      fuels.put(PRESENTBOX, 10);
      fuels.put(PALMSAPLING, 40);
      fuels.put(PALMFRUITBUNCH, 45);
      fuels.put(PALMTABLE, 35);
      fuels.put(PALMCHAIR, 30);
      fuels.put(PALMTORCH, 3);
      fuels.put(SNOWSEWINGTABLE, 15);
      fuels.put(PALMPLANKS, 15);
      fuels.put(FIERYBEANPLANKS, 25);
      fuels.put(MAGICWOOD, 30);
      fuels.put(MANAOILSPELEOTHEM, 20);
      fuels.put(CONIFERLOG, -1);
      fuels.put(CONIFERPLANKS, -1);
      fuels.put(CONIFERORNAMENT, -1);
      Field[] fields = BlocksRegister.class.getFields();

      for (Field field : fields) {
         if (field.getType() == Block.class || field.getType() == CustomPlant.class) {
            Block block = (Block)field.get(instance);
            setRegister(block, fuels);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public static void registerBlocksRender() {
      setRender(FROZENSTONE);
      setRender(GLACIER);
      setRender(FROZENCOBBLE);
      setRender(SNOWICE);
      setRender(CLEANICE);
      setRender(LOOSESNOW);
      setRender(STALACTITEFROZEN);
      setRender(STALACTITEFROZENADD);
      setRender(STALAGMITEFROZEN);
      setRender(FROZENBARREL);
      setRender(CONIFERLOG);
      setRender(CONIFERLEAVES);
      setRender(FROSTEDWEED);
      setRender(FROZENVASE);
      setRender(FROZENCHANDELIER);
      setRender(FROZENTORCH);
      setRender(FROZENTABLE);
      setRender(FROZENCHAIR);
      setRender(FROZENBRICKS);
      setRender(FROZENBRICKSTAIRS);
      setRender(FROZENBRICKPILASTER);
      setRender(FROZENROOF);
      setRender(FROZENROOFSTAIRS);
      setRender(DEMONICFIRE);
      setRender(CHRISTMASBALLS);
      setRender(BLOCKDETECTOR);
      setRender(ADVBLOCKDETECTOR);
      setRender(BLOCKPLACER);
      setRender(DEEPROCK);
      setRender(CALCITE);
      setRender(CAVEONYX);
      setRender(GREENONYX);
      setRender(SELENITE);
      setRender(CAVECRYSTALS);
      setRender(MAGICSTONE);
      setRender(GLOWINGCAVECRYSTALS);
      setRender(GLOWINGVEIN);
      setRender(SELENITECRYSTALS);
      setRender(DOLERITE);
      setRender(BLUEGLOWINGMUSH);
      setRender(GREENGLOWINGMUSH);
      setRender(WOODENSHAFT);
      setRender(ICESPIKES);
      setRender(SLIMEGLOB);
      setRender(LARVABLOCK);
      setRender(SPELLFORGE);
      setRender(OREDEMONITE);
      setRender(OREINFERNUM);
      setRender(OREMOLTEN);
      setRender(OREICEGL);
      setRender(OREICESN);
      setRender(TOPAZCRYSTAL);
      setRender(NETHERMELTER);
      setRender(SACRIFICIALALTAR);
      setRender(INFERNUMFURNACE);
      setRender(SLUDGE);
      setRender(NUCLEARWASTE);
      setRender(JUNK);
      setRender(SCRAP);
      setRender(SCRAPELECTRONICS);
      setRender(TOXICDIRT);
      setRender(BONESBLOCK);
      setRender(TOXICGRASS);
      setRender(RADIOCOBBLE);
      setRender(RADIOSTONE);
      setRender(ALCHEMICLAB);
      setRender(ALCHEMICVAPORIZER);
      setRender(PUZZLE);
      setRender(SEALOCK);
      setRender(TOXIBERRYLOG);
      setRender(TOXIBERRYLEAVES);
      setRender(DARKRUSTMETALL);
      setRender(ROTTENPLANKS);
      setRender(RUSTMETALL);
      setRender(RUSTARMATURE);
      setRender(BRICKSHARDS);
      setRender(TOXICTALLGRASS);
      setRender(BOMBRUSTED);
      setRender(BOMBTOXIC);
      setRender(BOMBSMALL);
      setRender(RUSTEDPIPE);
      setRender(TOXICPLANKS);
      setRender(TOXICTABLE);
      setRender(TOXICSTAIRS);
      setRender(TOXICPILASTER);
      setRender(TOXICCHAIR);
      setRender(TOXICTORCH);
      setRender(POISONLILY);
      setRender(LOPPYTOXIBERRY);
      setRender(LOPPYTOXISTEM);
      setRender(JUNKPILE);
      setRender(TOXIBERRYSAPLING);
      setRender(SLIMEBLOB);
      setRender(BROWNSLIME);
      setRender(BONESPILE);
      setRender(MUTAFLOWERRED);
      setRender(MUTAFLOWERPINK);
      setRender(GIANTFLOWERLEAVES);
      setRender(BURNINGFROST);
      setRender(ORERUBY);
      setRender(ORESAPPHIRE);
      setRender(ORECITRINE);
      setRender(OREAMETHYST);
      setRender(ORETOPAZ);
      setRender(ORERHINESTONE);
      setRender(CONIFERPLANKS);
      setRender(CONIFERSTAIRS);
      setRender(CONIFERORNAMENT);
      setRender(ICEPANE);
      setRender(CONIFERTABLE);
      setRender(CONIFERCHAIR);
      setRender(STARLANTERN);
      setRender(CONIFERPILASTER);
      setRender(GARLAND);
      setRender(PRESENTBOX);
      setRender(CRYSTALTABLE);
      setRender(CRYSTALCHAIR);
      setRender(CRYSTALTORCH);
      setRender(CORALTORCH);
      setRender(CRYSTALVASE);
      setRender(CRYSTALCHANDELIER);
      setRender(TOXICCHANDELIER);
      setRender(CORALTABLE);
      setRender(CORALCHAIR);
      setRender(CORALCHANDELIER);
      setRender(CORALVASE);
      setRender(CORALBRICKS);
      setRender(PEARLESCENTBRICKS);
      setRender(PEARLESCENTSTAIRS);
      setRender(PEARLESCENTPILASTER);
      setRender(SEAWEEDBLOCK);
      setRender(PALMLOG);
      setRender(PALMLEAVES);
      setRender(PALMSAPLING);
      setRender(PALMDRYLEAVES);
      setRender(PALMFRUITBUNCH);
      setRender(PALMTABLE);
      setRender(PALMCHAIR);
      setRender(PALMTORCH);
      setRender(SEASTONE);
      setRender(CORALYELLOW);
      setRender(CORALPINK);
      setRender(CORALWHITE);
      setRender(CORALRED);
      setRender(CORALORANGE);
      setRender(CORALLIMORPHABLUE);
      setRender(CORALLIMORPHARED);
      setRender(CORALLIMORPHAYELLOW);
      setRender(CORALLIMORPHAPINK);
      setRender(CORALLIMORPHALILAC);
      setRender(CORALLIMORPHABROWN);
      setRender(CORALLIMORPHAGREEN);
      setRender(MINICORALPURPLE);
      setRender(MINICORALWHITE);
      setRender(MINICORALBROWN);
      setRender(MINICORALRED);
      setRender(MINICORALBLUE);
      setRender(MINICORALPURPLEBIG);
      setRender(MINICORALWHITE2);
      setRender(MINICORALREDBIG);
      setRender(MINICORALWHITE2BIG);
      setRender(MINICORALBROWNBIG);
      setRender(MINICORALWHITEBIG);
      setRender(MINICORALFAVIABLUE);
      setRender(MINICORALFAVIAGREEN);
      setRender(MINICORALFAVIARED);
      setRender(MINICORALFAVIAYELLOW);
      setRender(MINICORALBRAIN);
      setRender(GIANTSHELL);
      setRender(ACTINIFORABLUE);
      setRender(ACTINIFORARED);
      setRender(ACTINIFORAYELLOW);
      setRender(ACTINIFORAREDBIG);
      setRender(ACTINIFORABLUEBIG);
      setRender(FROSTEXPLOSIVE);
      setRender(ACIDBOMB);
      setRender(COLLIDERPIPE);
      setRender(REDSTONEDFROZENBRICKS);
      setRender(FULMINIFLORA);
      setRender(FULMINIHERBA);
      setRender(GLOWBUSH);
      setRender(FULMINIORTUMBULB);
      setRender(FULMINIORTUMBONNY);
      setRender(ARTHROSTELECHALOGBRASS);
      setRender(ARTHROSTELECHALOGPINK);
      setRender(ARTHROSTELECHALEAVESBRASS);
      setRender(ARTHROSTELECHALEAVESPINK);
      setRender(LOOTBLOB);
      setRender(MININUKE);
      setRender(OREARSENIC);
      setRender(BIOCELL);
      setRender(CHLORINEBELCHER);
      setRender(VIRULENTCAPSULE);
      setRender(SWEETNECTARFLOWER);
      setRender(LABPLATING);
      setRender(RUSTLAMP);
      setRender(SEAGRASS);
      setRender(SHELLROCK);
      setRender(LIVINGSPONGE);
      setRender(CHALKROCK);
      setRender(STROMATOLITE);
      setRender(METALLICCORAL);
      setRender(MOBSPAWNERFROZEN);
      setRender(ORETOXINIUM);
      setRender(OREWOLFRAM);
      setRender(MOBSPAWNERRUSTED);
      setRender(BUNKERDOOR);
      setRender(WINTERALTAR);
      setRender(CONIFERSAPLING);
      setRender(SNOWSEWINGTABLE);
      setRender(STONESPELEOTHEM);
      setRender(SHELLROCKSPELEOTHEM);
      setRender(SELENITESPELEOTHEM);
      setRender(DOLERITEBRICKS);
      setRender(DOLERITEPILASTER);
      setRender(DOLERITESTAIRS);
      setRender(DOLERITECOLUMN);
      setRender(METALLICROCK);
      setRender(BEAMROCK);
      setRender(METALLICROCKPILASTER);
      setRender(STORMMASONRY);
      setRender(STORMPLATE);
      setRender(STORMPILASTER);
      setRender(STORMCONDUCTOR);
      setRender(STORMRACK);
      setRender(OREADAMANTIUM);
      setRender(RADIANTBRICK);
      setRender(ORECHROMIUM);
      setRender(OREZINC);
      setRender(OREALUMINIUM);
      setRender(BAUXITEBLOCK);
      setRender(FIERYBEANBLOCK);
      setRender(FIERYBEANLEAVES);
      setRender(FIERYBEANLOG);
      setRender(FIERYBEANSAPLING);
      setRender(MAGMABLOOM);
      setRender(SULFURCRYSTAL);
      setRender(PALMPLANKS);
      setRender(FIERYBEANPLANKS);
      setRender(NATIVESILVER);
      setRender(TOXICPORTALFRAME);
      setRender(DUNGEONPORTALFRAME);
      setRender(MOBSPAWNERANCIENT);
      setRender(RADIOACTIVESPELEOTHEM);
      setRender(FROZENSLIME);
      setRender(ORESILVER);
      setRender(RHINESTONEBLOCK);
      setRender(MOBSPAWNERAQUATIC);
      setRender(NIVEOLITEBLOCK);
      setRender(MAGICWOOD);
      setRender(RESEARCHTABLE);
      setRender(MAGICBRICKS);
      setRender(MAGICORNAMENT);
      setRender(STAINEDGLASSMARIGOLD);
      setRender(STAINEDGLASSROSE);
      setRender(STAINEDGLASSSUNSET);
      setRender(STAINEDGLASSSKY);
      setRender(STAINEDGLASSSTARLIGHT);
      setRender(STAINEDGLASSAURORA);
      setRender(LANTERNAMETHYST);
      setRender(LANTERNCITRINE);
      setRender(LANTERNEMERALD);
      setRender(LANTERNRUBY);
      setRender(LANTERNSAPPHIRE);
      setRender(LANTERNTOPAZ);
      setRender(LANTERNRHINESTONE);
      setRender(LANTERNDIAMOND);
      setRender(LAMPAMETHYST);
      setRender(LAMPCITRINE);
      setRender(LAMPDIAMOND);
      setRender(LAMPEMERALD);
      setRender(LAMPRHINESTONE);
      setRender(LAMPRUBY);
      setRender(LAMPSAPPHIRE);
      setRender(LAMPTOPAZ);
      setRender(DECORATIVECHAIN);
      setRender(VENTASTOLATCATCHER);
      setRender(LAIGANIASTEM);
      setRender(LAIGANIAPULP);
      setRender(VENTASTOLATRUNK);
      setRender(MELANZABULB);
      setRender(MELANZASTEM);
      setRender(SPLITTER);
      setRender(CREATIVEELEMENTDISTRIBUTOR);
      setRender(RETORT);
      setRender(GEMSPARKBLOCK);
      setRender(SUMMONEDHELLSTONE);
      setRender(BOOKCASEWOODEN);
      setRender(ABSORPTIONTOTEM);
      setRender(DISENCHANTMENTTABLE);
      setRender(MANAOILSPELEOTHEM);
      setRender(MANAFLOWERLEAVES);
      setRender(MANAFLOWER);
      setRender(HEALTHFLOWERLEAVES);
      setRender(HEALTHFLOWER);
      setRender(REDPEPPERVINE);
      setRender(TOMATOSTEM);
      setRender(ELECTROFERNLEAVES);
      setRender(ELECTROFERNSTEM);
      setRender(ARTHROHELIASTEM);
      setRender(ARTHROHELIASTALK);
      setRender(ARTHROHELIASPIKE);
      setRender(EXOHELIASPIKE);
      setRender(OREMITHRIL);
      setRender(ORESTORMSTEEL);
      setRender(ORETITANIUM);
      setRender(ORETITANIUMRADIOACTIVE);
      setRender(MACHINERYCASING);
      setRender(CHROMIUMGLASS);
      setRender(ASHBLOCK);
      setRender(SILVERBLOCK);
      setRender(ORESALT);
      setRender(MONEYBOX);
      setRender(NIVEOUSBRICKS);
      setRender(NIVEOUSCOLUMN);
      setRender(NIVEOUSPILASTER);
      setRender(NIVEOUSSTAIRS);
      setRender(POLISHEDNIVEOUSBLOCK);
      setRender(POLISHEDNIVEOUSPILASTER);
      setRender(NIVEOUSHOLE);
      setRender(FROZENDEBRIS);
      setRender(CONIFERCRAFTINGTABLE);
      setRender(ORELEPIDOLITE);
      setRender(TOXICBARREL);
      setRender(AQUATICAPORTALFRAME);
      setRender(DEBUGCOLORBLOCK);
      setRender(MOBSPAWNERSTORM);
      setRender(STORMLEDGEPORTALFRAME);
      setRender(SEAURCHIN);
      setTEISR(ASSEMBLYTABLE);
      setTEISR(INDUSTRIALMIXER);
      setTEISR(TRITONHEARTH);
      setTEISR(TIDEBEACON);
      setTEISR(GLOSSARY);
      setTEISR(RUNICMIRROR);
      setTEISR(VOIDCRYSTAL);
      setTEISR(BIOCELL);
      setTEISR(SHIMMERINGBEASTBLOOM);
      setTEISR(AUGMENTCUT);
      setTEISR(AUGMENTPRESS);
      setTEISR(AUGMENTWELD);
      setTEISR(AUGMENTPLASMA);
      setTEISR(AUGMENTMOLECULAR);
      setTEISR(MANABOTTLE);
      setTEISR(CRYSTALSPHERE);
      setTEISR(SOULCATCHER);
      setTEISR(MANAPUMP);
      setTEISR(SIEVE);
      setTEISR(ELECTRICSIEVE);
      setTEISR(NIVEOLITEGAMEBLOCK);
      setTEISR(PRESENTBOX);
      setTEISR(BLOCKITEMCHARGER);
      setTEISR(CHESTFROZEN);
      setTEISR(CHESTRUSTED);
      setTEISR(CHESTTOXIC);
      setTEISR(CHESTROTTEN);
      setTEISR(CHESTCRYSTAL);
      setTEISR(CHESTSUNKEN);
      setTEISR(CHESTCORAL);
      setTEISR(CHESTSTORM);
      setTEISR(MAGICGENERATOR);
      setTEISR(SANCTUARYBRICKS);
      setTEISR(ETHERITEINVOCATOR);
      setTEISR(TEAMBANNER);

      for (Block block : forrender) {
         setRender(block);
      }
   }

   private static void setRegister(Block block, HashMap<Block, Integer> fuels) {
      int burn = fuels.getOrDefault(block, 0);
      if (burn == 0) {
         ForgeRegistries.BLOCKS.register(block);
         if (block instanceof BlockARPGChest) {
            ForgeRegistries.ITEMS.register(((Item)new ItemARPGChest(block).setRegistryName(block.getRegistryName())).setTranslationKey(block.getTranslationKey()));
         } else if (block instanceof IHasSubtypes) {
            ForgeRegistries.ITEMS
               .register(((Item)new ItemBlockHasSubtypes(block).setRegistryName(block.getRegistryName())).setTranslationKey(block.getTranslationKey()));
         } else {
            ForgeRegistries.ITEMS.register(((Item)new ItemBlock(block).setRegistryName(block.getRegistryName())).setTranslationKey(block.getTranslationKey()));
         }
      } else {
         ForgeRegistries.BLOCKS.register(block);
         ForgeRegistries.ITEMS.register(((Item)new ItemBlockFuel(block, burn).setRegistryName(block.getRegistryName())).setTranslationKey(block.getTranslationKey()));
      }
   }

   @SideOnly(Side.CLIENT)
   private static void setRender(Block block) {
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
   }

   @SideOnly(Side.CLIENT)
   private static void setRender(Block block, ModelResourceLocation model) {
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, model);
   }

   @SideOnly(Side.CLIENT)
   public static void setTEISRChest(final Block block) {
      Item chestitem = Item.getItemFromBlock(block);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(chestitem, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(block.getRegistryName(), "inventory");
         }
      });
      chestitem.setTileEntityItemStackRenderer(TEISRChests.instance);
   }

   @SideOnly(Side.CLIENT)
   public static void setTEISR(final Block block) {
      Item chestitem = Item.getItemFromBlock(block);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(chestitem, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(block.getRegistryName(), "inventory");
         }
      });
      chestitem.setTileEntityItemStackRenderer(TEISRBlocks.instance);
   }

   public static class Hardres {
      public final float HARDNESS;
      public final float RESISTANCE;
      public final float SLOW;
      public final float FAST;
      public final int LVL;

      public Hardres(float hARDNESS, float rESISTANCE, float sLOW, float fAST, int lvl) {
         this.HARDNESS = hARDNESS;
         this.RESISTANCE = rESISTANCE;
         this.SLOW = sLOW;
         this.FAST = fAST;
         this.LVL = lvl;
      }

      public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
         return toolLevel >= this.LVL && state.getBlock().isToolEffective(tool, state) ? originalSpeed * this.FAST : originalSpeed * this.SLOW;
      }
   }
}

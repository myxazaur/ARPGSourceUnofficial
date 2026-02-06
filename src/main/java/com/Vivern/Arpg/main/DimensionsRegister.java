package com.Vivern.Arpg.main;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.Vivern.Arpg.dimensions.dungeon.DimensionDungeon;
import com.Vivern.Arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.Vivern.Arpg.dimensions.generationutils.BlockAtPos;
import com.Vivern.Arpg.dimensions.mortuorus.DimensionMortuorus;
import com.Vivern.Arpg.dimensions.stormledge.DimensionStormledge;
import com.Vivern.Arpg.dimensions.toxicomania.ARPGTeleporter;
import com.Vivern.Arpg.dimensions.toxicomania.DimensionToxicomania;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionsRegister {
   public static final int ethernal_frost_id = 100;
   public static final int toxicomania_id = 101;
   public static final int dungeon_id = 102;
   public static final int aquatica_id = 103;
   public static final int stormledge_id = 104;
   public static final int mortuorus_id = 106;
   public static final DimensionType ETHERNAL_FROST = DimensionType.register("Ethernal_Frost", "_ethernal_frost", 100, DimensionEthernalFrost.class, false);
   public static final DimensionType DUNGEON = DimensionType.register("Dungeon", "_dungeon", 102, DimensionDungeon.class, false);
   public static final DimensionType TOXICOMANIA = DimensionType.register("Toxicomania", "_toxicomania", 101, DimensionToxicomania.class, false);
   public static final DimensionType AQUATICA = DimensionType.register("Aquatica", "_aquatica", 103, DimensionAquatica.class, false);
   public static final DimensionType STORMLEDGE = DimensionType.register("Stormledge", "_stormledge", 104, DimensionStormledge.class, false);
   public static final DimensionType MORTUORUS = DimensionType.register("Mortuorus", "_mortuorus", 106, DimensionMortuorus.class, false);
   public static boolean canPortalsBreak = true;
   public static ARPGTeleporter teleporterTOXICOMANIA;
   public static ARPGTeleporter teleporterEVERFROST;
   public static ARPGTeleporter teleporterDUNGEON;
   public static ARPGTeleporter teleporterAQUATICA;
   public static ARPGTeleporter teleporterSTORMLEDGE;

   public static void registerDimensions() {
      DimensionManager.registerDimension(100, ETHERNAL_FROST);
      DimensionManager.registerDimension(102, DUNGEON);
      DimensionManager.registerDimension(101, TOXICOMANIA);
      DimensionManager.registerDimension(103, AQUATICA);
      DimensionManager.registerDimension(104, STORMLEDGE);
      DimensionManager.registerDimension(106, MORTUORUS);
   }

   public static boolean isAbstractRPGDimension(int dimension) {
      return dimension == 100 || dimension == 101 || dimension == 102 || dimension == 103 || dimension == 104 || dimension == 106;
   }

   public static void registerTeleporters() {
      ArrayList<BlockPos> membraneConfiguration = new ArrayList<>();
      ArrayList<BlockAtPos> frameConfiguration = new ArrayList<>();
      ArrayList<BlockPos> groundCheck = new ArrayList<>();
      ArrayList<BlockPos> spawnPoints = new ArrayList<>();
      IBlockState framestate = Blocks.SNOW.getDefaultState();
      membraneConfiguration.add(new BlockPos(0, 0, 0));
      membraneConfiguration.add(new BlockPos(1, 0, 0));
      membraneConfiguration.add(new BlockPos(-1, 0, 0));
      membraneConfiguration.add(new BlockPos(0, 1, 0));
      membraneConfiguration.add(new BlockPos(1, 1, 0));
      membraneConfiguration.add(new BlockPos(-1, 1, 0));
      membraneConfiguration.add(new BlockPos(0, -1, 0));
      membraneConfiguration.add(new BlockPos(1, -1, 0));
      membraneConfiguration.add(new BlockPos(-1, -1, 0));
      frameConfiguration.add(new BlockAtPos(-2, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
      groundCheck.add(new BlockPos(0, -3, 0));
      groundCheck.add(new BlockPos(-1, -3, 0));
      groundCheck.add(new BlockPos(1, -3, 0));
      groundCheck.add(new BlockPos(-2, -3, 0));
      groundCheck.add(new BlockPos(2, -3, 0));
      add8Poses(spawnPoints, new BlockPos(0, 1, 0));
      add8Poses(spawnPoints, new BlockPos(0, 0, 0));
      add8Poses(spawnPoints, new BlockPos(0, -1, 0));
      add8Poses(spawnPoints, new BlockPos(0, -2, 0));
      teleporterEVERFROST = new ARPGTeleporter(
         membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, 100, BlocksRegister.PORTALFROST.getDefaultState(), 5, false
      );
      membraneConfiguration = new ArrayList<>();
      frameConfiguration = new ArrayList<>();
      groundCheck = new ArrayList<>();
      spawnPoints = new ArrayList<>();
      framestate = BlocksRegister.TOXICPORTALFRAME.getDefaultState();
      membraneConfiguration.add(new BlockPos(0, 0, 0));
      membraneConfiguration.add(new BlockPos(1, 0, 0));
      membraneConfiguration.add(new BlockPos(-1, 0, 0));
      membraneConfiguration.add(new BlockPos(0, 1, 0));
      membraneConfiguration.add(new BlockPos(1, 1, 0));
      membraneConfiguration.add(new BlockPos(-1, 1, 0));
      frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -1, 1, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -1, 1, framestate));
      frameConfiguration.add(new BlockAtPos(1, -1, 1, framestate));
      frameConfiguration.add(new BlockAtPos(-2, -1, 1, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, 1, framestate));
      frameConfiguration.add(new BlockAtPos(0, -1, -1, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -1, -1, framestate));
      frameConfiguration.add(new BlockAtPos(1, -1, -1, framestate));
      frameConfiguration.add(new BlockAtPos(-2, -1, -1, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, -1, framestate));
      groundCheck.add(new BlockPos(0, -2, 0));
      groundCheck.add(new BlockPos(-1, -2, 0));
      groundCheck.add(new BlockPos(1, -2, 0));
      groundCheck.add(new BlockPos(-2, -2, 0));
      groundCheck.add(new BlockPos(2, -2, 0));
      groundCheck.add(new BlockPos(0, -2, 1));
      groundCheck.add(new BlockPos(-1, -2, 1));
      groundCheck.add(new BlockPos(1, -2, 1));
      groundCheck.add(new BlockPos(-2, -2, 1));
      groundCheck.add(new BlockPos(2, -2, 1));
      groundCheck.add(new BlockPos(0, -2, -1));
      groundCheck.add(new BlockPos(-1, -2, -1));
      groundCheck.add(new BlockPos(1, -2, -1));
      groundCheck.add(new BlockPos(-2, -2, -1));
      groundCheck.add(new BlockPos(2, -2, -1));
      add8Poses(spawnPoints, new BlockPos(0, 0, 0));
      add8Poses(spawnPoints, new BlockPos(0, 1, 0));
      teleporterTOXICOMANIA = new ARPGTeleporter(
         membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, 101, BlocksRegister.TOXICPORTAL.getDefaultState(), 4, true
      );
      teleporterTOXICOMANIA.prototypePortalBlock = Blocks.STAINED_GLASS_PANE
         .getDefaultState()
         .withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.GREEN);
      membraneConfiguration = new ArrayList<>();
      frameConfiguration = new ArrayList<>();
      groundCheck = new ArrayList<>();
      spawnPoints = new ArrayList<>();
      framestate = BlocksRegister.DUNGEONPORTALFRAME.getDefaultState();
      membraneConfiguration.add(new BlockPos(0, 0, 0));
      membraneConfiguration.add(new BlockPos(-1, 0, 0));
      membraneConfiguration.add(new BlockPos(1, 0, 0));
      membraneConfiguration.add(new BlockPos(-2, 0, 0));
      membraneConfiguration.add(new BlockPos(2, 0, 0));
      membraneConfiguration.add(new BlockPos(0, 0, 1));
      membraneConfiguration.add(new BlockPos(-1, 0, 1));
      membraneConfiguration.add(new BlockPos(1, 0, 1));
      membraneConfiguration.add(new BlockPos(-2, 0, 1));
      membraneConfiguration.add(new BlockPos(2, 0, 1));
      membraneConfiguration.add(new BlockPos(0, 0, -1));
      membraneConfiguration.add(new BlockPos(-1, 0, -1));
      membraneConfiguration.add(new BlockPos(1, 0, -1));
      membraneConfiguration.add(new BlockPos(-2, 0, -1));
      membraneConfiguration.add(new BlockPos(2, 0, -1));
      membraneConfiguration.add(new BlockPos(0, 0, -2));
      membraneConfiguration.add(new BlockPos(-1, 0, -2));
      membraneConfiguration.add(new BlockPos(1, 0, -2));
      membraneConfiguration.add(new BlockPos(0, 0, 2));
      membraneConfiguration.add(new BlockPos(-1, 0, 2));
      membraneConfiguration.add(new BlockPos(1, 0, 2));
      frameConfiguration.add(new BlockAtPos(0, 0, -3, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 0, -3, framestate));
      frameConfiguration.add(new BlockAtPos(1, 0, -3, framestate));
      frameConfiguration.add(new BlockAtPos(0, 0, 3, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 0, 3, framestate));
      frameConfiguration.add(new BlockAtPos(1, 0, 3, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 0, -2, framestate));
      frameConfiguration.add(new BlockAtPos(2, 0, -2, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 0, 2, framestate));
      frameConfiguration.add(new BlockAtPos(2, 0, 2, framestate));
      frameConfiguration.add(new BlockAtPos(-3, 0, -1, framestate));
      frameConfiguration.add(new BlockAtPos(3, 0, -1, framestate));
      frameConfiguration.add(new BlockAtPos(-3, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(3, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-3, 0, 1, framestate));
      frameConfiguration.add(new BlockAtPos(3, 0, 1, framestate));

      for (BlockPos pos : frameConfiguration) {
         groundCheck.add(pos.down());
      }

      for (BlockPos pos : membraneConfiguration) {
         groundCheck.add(pos.down());
      }

      add8Poses(spawnPoints, new BlockPos(0, -2, 0));
      add8Poses(spawnPoints, new BlockPos(0, -3, 0));
      add8Poses(spawnPoints, new BlockPos(0, -4, 0));
      add8Poses(spawnPoints, new BlockPos(0, -5, 0));
      spawnPoints.add(new BlockPos(3, 1, 0));
      spawnPoints.add(new BlockPos(-3, 1, 0));
      spawnPoints.add(new BlockPos(0, 1, 3));
      spawnPoints.add(new BlockPos(0, 1, -3));
      spawnPoints.add(new BlockPos(2, 1, 2));
      spawnPoints.add(new BlockPos(-2, 1, 2));
      spawnPoints.add(new BlockPos(2, 1, -2));
      spawnPoints.add(new BlockPos(-2, 1, -2));
      teleporterDUNGEON = new ARPGTeleporter(
         membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, 102, BlocksRegister.DUNGEONPORTAL.getDefaultState(), 1, false
      );
      teleporterDUNGEON.canPlaceUnderground = true;
      membraneConfiguration = new ArrayList<>();
      frameConfiguration = new ArrayList<>();
      groundCheck = new ArrayList<>();
      spawnPoints = new ArrayList<>();
      framestate = BlocksRegister.AQUATICAPORTALFRAME.getDefaultState();
      membraneConfiguration.add(new BlockPos(0, 0, 0));
      membraneConfiguration.add(new BlockPos(1, 0, 0));
      membraneConfiguration.add(new BlockPos(-1, 0, 0));
      membraneConfiguration.add(new BlockPos(0, 1, 0));
      membraneConfiguration.add(new BlockPos(1, 1, 0));
      membraneConfiguration.add(new BlockPos(-1, 1, 0));
      membraneConfiguration.add(new BlockPos(0, -1, 0));
      membraneConfiguration.add(new BlockPos(1, -1, 0));
      membraneConfiguration.add(new BlockPos(-1, -1, 0));
      membraneConfiguration.add(new BlockPos(0, 2, 0));
      membraneConfiguration.add(new BlockPos(2, 0, 0));
      membraneConfiguration.add(new BlockPos(0, -2, 0));
      membraneConfiguration.add(new BlockPos(-2, 0, 0));
      frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-3, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(3, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -3, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, 3, 0, framestate));
      groundCheck.add(new BlockPos(0, -4, 0));
      groundCheck.add(new BlockPos(-1, -4, 0));
      groundCheck.add(new BlockPos(1, -4, 0));
      groundCheck.add(new BlockPos(-2, -4, 0));
      groundCheck.add(new BlockPos(2, -4, 0));
      groundCheck.add(new BlockPos(-3, -4, 0));
      groundCheck.add(new BlockPos(3, -4, 0));
      add8Poses(spawnPoints, new BlockPos(0, 1, 0));
      add8Poses(spawnPoints, new BlockPos(0, 0, 0));
      add8Poses(spawnPoints, new BlockPos(0, -1, 0));
      add8Poses(spawnPoints, new BlockPos(0, -2, 0));
      add8Poses(spawnPoints, new BlockPos(0, -3, 0));
      add8Poses(spawnPoints, new BlockPos(0, -4, 0));
      teleporterAQUATICA = new ARPGTeleporter(
         membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, 103, BlocksRegister.AQUATICAPORTAL.getDefaultState(), 7, false
      );
      membraneConfiguration = new ArrayList<>();
      frameConfiguration = new ArrayList<>();
      groundCheck = new ArrayList<>();
      spawnPoints = new ArrayList<>();
      framestate = BlocksRegister.STORMLEDGEPORTALFRAME.getDefaultState();
      membraneConfiguration.add(new BlockPos(0, 0, 0));
      membraneConfiguration.add(new BlockPos(1, 0, 0));
      membraneConfiguration.add(new BlockPos(-1, 0, 0));
      membraneConfiguration.add(new BlockPos(0, 1, 0));
      membraneConfiguration.add(new BlockPos(1, 1, 0));
      membraneConfiguration.add(new BlockPos(-1, 1, 0));
      membraneConfiguration.add(new BlockPos(0, -1, 0));
      membraneConfiguration.add(new BlockPos(1, -1, 0));
      membraneConfiguration.add(new BlockPos(-1, -1, 0));
      membraneConfiguration.add(new BlockPos(0, 3, 0));
      membraneConfiguration.add(new BlockPos(0, -3, 0));
      membraneConfiguration.add(new BlockPos(3, 0, 0));
      membraneConfiguration.add(new BlockPos(-3, 0, 0));
      frameConfiguration.add(new BlockAtPos(-2, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
      frameConfiguration.add(new BlockAtPos(3, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(4, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-3, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-4, 1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(3, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(4, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-3, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-4, -1, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-4, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(4, 0, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 3, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, 4, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 3, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, 4, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -3, 0, framestate));
      frameConfiguration.add(new BlockAtPos(-1, -4, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -3, 0, framestate));
      frameConfiguration.add(new BlockAtPos(1, -4, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, -4, 0, framestate));
      frameConfiguration.add(new BlockAtPos(0, 4, 0, framestate));
      groundCheck.add(new BlockPos(0, -6, 0));
      groundCheck.add(new BlockPos(-1, -6, 0));
      groundCheck.add(new BlockPos(1, -6, 0));
      groundCheck.add(new BlockPos(-2, -6, 0));
      groundCheck.add(new BlockPos(2, -6, 0));
      groundCheck.add(new BlockPos(-3, -6, 0));
      groundCheck.add(new BlockPos(3, -6, 0));
      groundCheck.add(new BlockPos(-4, -6, 0));
      groundCheck.add(new BlockPos(4, -6, 0));
      add8Poses(spawnPoints, new BlockPos(0, 3, 0));
      add8Poses(spawnPoints, new BlockPos(0, 2, 0));
      add8Poses(spawnPoints, new BlockPos(0, 1, 0));
      add8Poses(spawnPoints, new BlockPos(0, 0, 0));
      teleporterSTORMLEDGE = new ARPGTeleporter(
         membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, 104, BlocksRegister.STORMLEDGEPORTAL.getDefaultState(), 9, false
      );
   }

   @Nullable
   public static ARPGTeleporter getTeleporterToDimension(int dimensionId) {
      if (dimensionId == 100) {
         return teleporterEVERFROST;
      } else if (dimensionId == 101) {
         return teleporterTOXICOMANIA;
      } else if (dimensionId == 102) {
         return teleporterDUNGEON;
      } else {
         return dimensionId == 103 ? teleporterAQUATICA : null;
      }
   }

   private static void add8Poses(ArrayList<BlockPos> list, BlockPos mainpos) {
      list.add(mainpos.add(1, 0, 1));
      list.add(mainpos.add(-1, 0, 1));
      list.add(mainpos.add(1, 0, -1));
      list.add(mainpos.add(-1, 0, -1));
      list.add(mainpos.add(-1, 0, 0));
      list.add(mainpos.add(1, 0, 0));
      list.add(mainpos.add(0, 0, 1));
      list.add(mainpos.add(0, 0, -1));
   }
}

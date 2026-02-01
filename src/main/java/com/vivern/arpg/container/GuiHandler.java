package com.vivern.arpg.container;

import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.tileentity.TileAlchemicLab;
import com.vivern.arpg.tileentity.TileAlchemicVaporizer;
import com.vivern.arpg.tileentity.TileAssemblyTable;
import com.vivern.arpg.tileentity.TileCrystallizer;
import com.vivern.arpg.tileentity.TileDisenchantmentTable;
import com.vivern.arpg.tileentity.TileElementDistributor;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import com.vivern.arpg.tileentity.TileInfernumFurnace;
import com.vivern.arpg.tileentity.TileItemCharger;
import com.vivern.arpg.tileentity.TileNetherMelter;
import com.vivern.arpg.tileentity.TilePyrocrystallineConverter;
import com.vivern.arpg.tileentity.TileResearchTable;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
   public static final int NETHER_MELTER_GUIID = 0;
   public static final int INFERNUM_FURNACE_GUIID = 1;
   public static final int ALCHEMIC_LAB_GUIID = 2;
   public static final int ALCHEMIC_VAPORIZER_GUIID = 3;
   public static final int PUZZLE_GUIID = 4;
   public static final int SEALOCK_GUIID = 5;
   public static final int TRADER_GUIID = 6;
   public static final int CRYSTALLIZER_GUIID = 7;
   public static final int PYROCRYSTALLINE_CONV_GUIID = 8;
   public static final int ASSEMBLY_TABLE_GUIID = 9;
   public static final int ELECTROMAGNET_GUIID = 10;
   public static final int SUMMON_GUIID = 11;
   public static final int ITEM_CHARGER_GUIID = 12;
   public static final int INDUSTRIAL_MIXER_GUIID = 13;
   public static final int RESEARCH_TABLE_GUIID = 14;
   public static final int CREATIVE_ELEMENT_DISTRIBUTOR_GUIID = 15;
   public static final int DISENCHANTMENT_TABLE_GUIID = 16;
   public static final int MECHANIC_GUIID = 17;

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if (ID == 0) {
         return new ContainerNetherMelter(player.inventory, (TileNetherMelter)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 1) {
         return new ContainerInfernumFurnace(player.inventory, (TileInfernumFurnace)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 2) {
         return new ContainerAlchemicLab(player.inventory, (TileAlchemicLab)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 3) {
         return new ContainerAlchemicVaporizer(player.inventory, (TileAlchemicVaporizer)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 6) {
         return new ContainerTrader(player.inventory);
      } else if (ID == 7) {
         return new ContainerCrystallizer(player.inventory, (TileCrystallizer)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 8) {
         return new ContainerPyrocrystalline(player.inventory, (TilePyrocrystallineConverter)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 9) {
         return new ContainerAssemblyTable(player.inventory, (TileAssemblyTable)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 12) {
         return new ContainerCharger(player.inventory, (TileItemCharger)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 13) {
         return new ContainerIndustrialMixer(player.inventory, (TileIndustrialMixer)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 14) {
         return new ContainerResearchTable(player.inventory, (TileResearchTable)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 15) {
         return new ContainerElementDistributor(player.inventory, (TileElementDistributor)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 16) {
         return new Container1(player.inventory, (TileDisenchantmentTable)world.getTileEntity(new BlockPos(x, y, z)));
      } else {
         if (ID == 17) {
            Entity e = world.getEntityByID(x);
            if (e instanceof NPCMobsPack.NpcMechanic) {
               return new ContainerMechanic(player.inventory, (NPCMobsPack.NpcMechanic)e);
            }
         }

         return null;
      }
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if (ID == 0) {
         return new GUINetherMelter(player.inventory, (TileNetherMelter)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 1) {
         return new GUIInfernumFurnace(player.inventory, (TileInfernumFurnace)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 2) {
         return new GUIAlchemicLab(player.inventory, (TileAlchemicLab)world.getTileEntity(new BlockPos(x, y, z)));
      } else if (ID == 3) {
         return new GUIAlchemicVaporizer(player.inventory, (TileAlchemicVaporizer)world.getTileEntity(new BlockPos(x, y, z)));
      } else {
         if (ID == 6) {
            Entity e = world.getEntityByID(x);
            if (e instanceof NPCMobsPack.NpcTrader) {
               return new GUITrader((NPCMobsPack.NpcTrader)e, player);
            }
         }

         if (ID == 7) {
            return new GUICrystallizer(player.inventory, (TileCrystallizer)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 8) {
            return new GUIPyrocrystallineConverter(player.inventory, (TilePyrocrystallineConverter)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 9) {
            return new GUIAssemblyTable(player.inventory, (TileAssemblyTable)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 12) {
            return new GUICharger(player.inventory, (TileItemCharger)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 13) {
            return new GUIIndustrialMixer(player.inventory, (TileIndustrialMixer)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 14) {
            return new GUIResearchTable(player.inventory, (TileResearchTable)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 15) {
            return new GUIElementDistributor(player.inventory, (TileElementDistributor)world.getTileEntity(new BlockPos(x, y, z)));
         } else if (ID == 16) {
            return new GUIDisenchantmentTable(player.inventory, (TileDisenchantmentTable)world.getTileEntity(new BlockPos(x, y, z)));
         } else {
            if (ID == 17) {
               Entity e = world.getEntityByID(x);
               if (e instanceof NPCMobsPack.NpcMechanic) {
                  return new GUIMechanic((NPCMobsPack.NpcMechanic)e, player);
               }
            }

            return null;
         }
      }
   }

   public static void displayGui(EntityPlayer player, @Nullable GuiScreen gui) {
      if (player instanceof EntityPlayerSP) {
         Minecraft.getMinecraft().displayGuiScreen(gui);
      }
   }
}

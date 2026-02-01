package com.vivern.arpg.blocks;

import com.vivern.arpg.elements.ItemCalibrationThing;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.recipes.ExploringField;
import com.vivern.arpg.tileentity.TileResearchTable;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ResearchTable extends Table {
   static Vec3d col = ColorConverters.DecimaltoRGB(7286418);

   public ResearchTable() {
      super(Material.WOOD, "research_table", 3.0F, 25.0F, SoundType.WOOD, "axe", 0);
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileResearchTable) {
         TileResearchTable tile = (TileResearchTable)tileentity;
         if (tile.specialization == 1) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.MAGICEXPLORINGKIT));
         }

         if (tile.specialization == 2) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.MAGICRESEARCHKIT));
         }

         if (tile.specialization == 3) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.MAGICWRITINGKIT));
         }
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
         TileResearchTable tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            if (tile.specialization == 0) {
               Item item = player.getHeldItemMainhand().getItem();
               if (item == ItemsRegister.MAGICEXPLORINGKIT) {
                  tile.specialization = 1;
                  tile.rotation = player.getHorizontalFacing().getHorizontalIndex();
                  player.getHeldItemMainhand().shrink(1);
                  PacketHandler.trySendPacketUpdate(worldIn, pos, tile, 64.0);
                  player.getCooldownTracker().setCooldown(ItemsRegister.MAGICEXPLORINGKIT, 20);
               } else if (item == ItemsRegister.MAGICRESEARCHKIT) {
                  tile.specialization = 2;
                  tile.rotation = player.getHorizontalFacing().getHorizontalIndex();
                  player.getHeldItemMainhand().shrink(1);
                  PacketHandler.trySendPacketUpdate(worldIn, pos, tile, 64.0);
                  player.getCooldownTracker().setCooldown(ItemsRegister.MAGICEXPLORINGKIT, 20);
               } else if (item == ItemsRegister.MAGICWRITINGKIT) {
                  tile.specialization = 3;
                  tile.rotation = player.getHorizontalFacing().getHorizontalIndex();
                  player.getHeldItemMainhand().shrink(1);
                  PacketHandler.trySendPacketUpdate(worldIn, pos, tile, 64.0);
                  player.getCooldownTracker().setCooldown(ItemsRegister.MAGICEXPLORINGKIT, 20);
               }
            } else if (!player.getCooldownTracker().hasCooldown(ItemsRegister.MAGICEXPLORINGKIT)) {
               if (player instanceof EntityPlayerMP) {
                  ExploringField.SendExploringInfoToClient((EntityPlayerMP)player);
               }

               player.openGui(AbstractRPG.instance, 14, worldIn, pos.getX(), pos.getY(), pos.getZ());
               tile.openTable(player);
               return true;
            }
         }
      }

      return true;
   }

   public Class<TileResearchTable> getTileEntityClass() {
      return TileResearchTable.class;
   }

   public TileResearchTable getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileResearchTable)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileResearchTable createTileEntity(World world, IBlockState blockState) {
      return new TileResearchTable();
   }

   public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
      TileResearchTable tile = this.getTileEntity(world, pos);
      if (tile.specialization == 1) {
         for (int i = -3; i <= 3; i++) {
            Vec3d addd = GetMOP.YawToVec3d(tile.rotation * 90.0F + i * 45).scale(0.375);
            Vec3d finalpos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1 + 0.4375 - Math.abs(i) * 0.0625, pos.getZ() + 0.5)
               .add(addd);
            ItemCalibrationThing.spawnCandleLightParticle(world, finalpos, col, false);
         }
      }
   }
}

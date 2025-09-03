//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.tileentity.TileStarLantern;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StarLantern extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.75);

   public StarLantern() {
      super(Material.WOOD);
      this.setRegistryName("star_lantern");
      this.setTranslationKey("star_lantern");
      this.blockHardness = BlocksRegister.HR_CONIFER_PLANKS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_CONIFER_PLANKS.RESISTANCE;
      this.setHarvestLevel("axe", BlocksRegister.HR_CONIFER_PLANKS.LVL);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(1.0F);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         ItemStack stack = player.getHeldItem(hand);
         List<String> ores = OreDicHelper.getOreNames(stack);
         TileStarLantern tile = this.getTileEntity(worldIn, pos);
         if (!ores.isEmpty()) {
            for (String name : ores) {
               if ("dyeBlack".equals(name)) {
                  tile.red /= 2.0F;
                  tile.green /= 2.0F;
                  tile.blue /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeRed".equals(name)) {
                  tile.green /= 2.0F;
                  tile.blue /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeGreen".equals(name)) {
                  tile.red /= 2.0F;
                  tile.blue /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeBrown".equals(name)) {
                  tile.red = Math.max(0.0F, tile.red - 0.1F);
                  tile.green = Math.max(0.0F, tile.green - 0.2F);
                  tile.blue = Math.max(0.0F, tile.blue - 0.3F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeBlue".equals(name)) {
                  tile.red /= 2.0F;
                  tile.green /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyePurple".equals(name)) {
                  tile.green /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeCyan".equals(name)) {
                  tile.red /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeLightGray".equals(name)) {
                  tile.red = Math.max(0.0F, tile.red - 0.1F);
                  tile.green = Math.max(0.0F, tile.green - 0.1F);
                  tile.blue = Math.max(0.0F, tile.blue - 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeGray".equals(name)) {
                  tile.red = Math.max(0.0F, tile.red - 0.2F);
                  tile.green = Math.max(0.0F, tile.green - 0.2F);
                  tile.blue = Math.max(0.0F, tile.blue - 0.2F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyePink".equals(name)) {
                  tile.green = Math.max(0.0F, tile.green - 0.1F);
                  tile.blue = Math.max(0.0F, tile.blue - 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeLime".equals(name)) {
                  tile.red = Math.max(0.0F, tile.red - 0.1F);
                  tile.blue = Math.max(0.0F, tile.blue - 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeYellow".equals(name)) {
                  tile.blue /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeLightBlue".equals(name)) {
                  tile.red = Math.max(0.0F, tile.red - 0.1F);
                  tile.green = Math.max(0.0F, tile.green - 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeMagenta".equals(name)) {
                  tile.green = Math.max(0.0F, tile.green - 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeOrange".equals(name)) {
                  tile.green = Math.max(0.0F, tile.green - 0.1F);
                  tile.blue /= 2.0F;
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }

               if ("dyeWhite".equals(name)) {
                  tile.red = Math.min(1.0F, tile.red + 0.1F);
                  tile.green = Math.min(1.0F, tile.green + 0.1F);
                  tile.blue = Math.min(1.0F, tile.blue + 0.1F);
                  trySendPacketUpdate(worldIn, pos, tile);
                  stack.shrink(1);
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileStarLantern tile) {
      int range = 32;

      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + range,
            pos.getY() + range,
            pos.getZ() + range,
            pos.getX() - range,
            pos.getY() - range,
            pos.getZ() - range
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public Class<TileStarLantern> getTileEntityClass() {
      return TileStarLantern.class;
   }

   public TileStarLantern getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileStarLantern)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileStarLantern createTileEntity(World world, IBlockState blockState) {
      return new TileStarLantern();
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}

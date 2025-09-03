//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSeaweed extends Block {
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool ISTOP = PropertyBool.create("istop");
   public static boolean canSeaweedFall = true;

   public BlockSeaweed() {
      super(Material.WATER);
      this.setRegistryName("seaweed_block");
      this.setTranslationKey("seaweed_block");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.5F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.setSoundType(SoundType.PLANT);
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XZ;
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return state.withProperty(ISTOP, worldIn.getBlockState(pos.up()).getBlock() != this);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, world, pos, blockIn, fromPos);
      if (!this.canStayAt(world, pos)) {
         this.dropBlockAsItem(world, pos, state, 0);
         world.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
         this.breakup(world, pos);
      }
   }

   public boolean canStayAt(World worldIn, BlockPos pos) {
      IBlockState state1 = worldIn.getBlockState(pos.up());
      if (state1.getMaterial() == Material.WATER || state1.isOpaqueCube()) {
         IBlockState state2 = worldIn.getBlockState(pos.east());
         if (state2.getMaterial() == Material.WATER || state2.isOpaqueCube()) {
            IBlockState state3 = worldIn.getBlockState(pos.south());
            if (state3.getMaterial() == Material.WATER || state3.isOpaqueCube()) {
               IBlockState state4 = worldIn.getBlockState(pos.west());
               if (state4.getMaterial() == Material.WATER || state4.isOpaqueCube()) {
                  IBlockState state5 = worldIn.getBlockState(pos.north());
                  if (state5.getMaterial() == Material.WATER || state5.isOpaqueCube()) {
                     IBlockState state6 = worldIn.getBlockState(pos.down());
                     if (state6.getBlock() == this || state6.isTopSolid()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      IBlockState state0 = worldIn.getBlockState(pos);
      return state0.getBlock().isReplaceable(worldIn, pos) ? this.canStayAt(worldIn, pos) : false;
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (rand.nextFloat() < 0.99) {
         int weeds = 0;

         for (int y = pos.getY() - 1; y > pos.getY() - 9; y--) {
            BlockPos poss = new BlockPos(pos.getX(), y, pos.getZ());
            if (world.getBlockState(poss).getBlock() != this) {
               break;
            }

            weeds++;
         }

         if (weeds < 8 && this.canPlaceBlockAt(world, pos.up())) {
            world.setBlockState(pos.up(), this.getDefaultState(), 2);
         }
      }
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      this.breakup(world, pos);
      if (this.isAroundWater(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
      }

      super.breakBlock(world, pos, state);
   }

   public void breakup(World world, BlockPos pos) {
      if (canSeaweedFall) {
         for (int y = pos.getY() + 1; y < 256; y++) {
            BlockPos poss = new BlockPos(pos.getX(), y, pos.getZ());
            IBlockState state2 = world.getBlockState(poss);
            if (state2.getBlock() != this) {
               break;
            }

            this.dropBlockAsItem(world, poss, state2, 0);
            if (this.isAroundWater(world, poss)) {
               world.setBlockState(poss, Blocks.WATER.getDefaultState(), 2);
            }
         }
      }
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.HORIZONTALS) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return FULL_BLOCK_AABB;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(ISTOP, meta == 1);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(ISTOP) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LEVEL, ISTOP});
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}

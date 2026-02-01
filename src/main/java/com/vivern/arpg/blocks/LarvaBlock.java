package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LarvaBlock extends Block {
   protected static final AxisAlignedBB[] LARVA_AABB = new AxisAlignedBB[]{
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.325, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.975, 1.0)
   };
   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 0, 3);

   public LarvaBlock() {
      super(Material.CLAY);
      this.setRegistryName("larva_block");
      this.setTranslationKey("larva_block");
      this.blockHardness = 3.0F;
      this.blockResistance = 3.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public void tryDisperse(IBlockState state, World world, BlockPos pos) {
      int l = (Integer)state.getValue(LAYERS);
      if (l >= 2) {
         int ne = RANDOM.nextInt(4);

         for (int i = 0; i < 4; i++) {
            ne = GetMOP.next(ne, 1, 4);
            EnumFacing face = EnumFacing.HORIZONTALS[ne];
            IBlockState statef = world.getBlockState(pos.offset(face));
            if (statef.getBlock() == Blocks.AIR) {
               world.setBlockState(pos, state.withProperty(LAYERS, l - 1));
               world.setBlockState(pos.offset(face), state.withProperty(LAYERS, 1));
               return;
            }
         }
      }
   }

   public boolean tryFall(IBlockState state, World world, BlockPos pos) {
      int l = (Integer)state.getValue(LAYERS);
      IBlockState statef = world.getBlockState(pos.down());
      if (statef.getBlock() == BlocksRegister.LARVABLOCK) {
         int l2 = (Integer)statef.getValue(LAYERS);
         int resultl1 = l - (3 - l2);
         if (resultl1 < 1) {
            world.setBlockToAir(pos);
         } else {
            world.setBlockState(pos, state.withProperty(LAYERS, resultl1));
         }

         int resultl2 = l2 + l;
         if (resultl2 < 4) {
            world.setBlockState(pos.down(), state.withProperty(LAYERS, resultl2));
            return true;
         } else {
            world.setBlockState(pos.down(), state.withProperty(LAYERS, 3));
            return true;
         }
      } else if (statef.getBlock() == Blocks.AIR && world.getBlockState(pos.down(2)).isTopSolid()) {
         world.setBlockToAir(pos);
         world.setBlockState(pos.down(), state);
         return true;
      } else {
         return false;
      }
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      super.onBlockAdded(worldIn, pos, state);
      if (!this.tryFall(state, worldIn, pos)) {
         this.tryDisperse(state, worldIn, pos);
      }
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      if (!this.tryFall(state, worldIn, pos)) {
         this.tryDisperse(state, worldIn, pos);
      }
   }

   public boolean isTopSolid(IBlockState state) {
      return (Integer)state.getValue(LAYERS) == 3;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      int i = (Integer)blockState.getValue(LAYERS);
      AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
      return i == 3
         ? LARVA_AABB[3]
         : new AxisAlignedBB(
            axisalignedbb.minX,
            axisalignedbb.minY,
            axisalignedbb.minZ,
            axisalignedbb.maxX,
            i / 3.0F,
            axisalignedbb.maxZ
         );
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return LARVA_AABB[state.getValue(LAYERS)];
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.SNOWBALL;
   }

   public int quantityDropped(Random random) {
      return 5 + random.nextInt(6);
   }

   public boolean isFullCube(IBlockState state) {
      return (Integer)state.getValue(LAYERS) == 3;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(LAYERS, meta);
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(LAYERS);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LAYERS});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(LAYERS, 3);
   }
}

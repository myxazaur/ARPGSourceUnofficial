package com.vivern.arpg.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStalactiteBase extends Block {
   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 1.0, 0.7);
   public static AxisAlignedBB AABBsmall = new AxisAlignedBB(0.35, 0.0, 0.35, 0.65, 1.0, 0.65);
   public final int type;

   public BlockStalactiteBase(String name, Material material, int type, String tool, int harvestlvl) {
      super(material);
      this.type = type;
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = 1.0F;
      this.blockResistance = 0.8F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setHarvestLevel(tool, harvestlvl);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      if (this.type == 0) {
         return side == EnumFacing.DOWN;
      } else if (this.type == 1) {
         return side == EnumFacing.DOWN || side == EnumFacing.UP;
      } else if (this.type == 2) {
         return side == EnumFacing.UP;
      } else if (this.type == 3) {
         return side == EnumFacing.DOWN && !worldIn.isAirBlock(pos.down())
            ? true
            : side == EnumFacing.UP && !worldIn.isAirBlock(pos.up());
      } else {
         return false;
      }
   }

   public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
      return false;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (this.type == 0) {
         if (worldIn.isAirBlock(pos.up())) {
            worldIn.destroyBlock(pos, true);
         }
      } else if (this.type == 1) {
         if (worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.down())) {
            worldIn.destroyBlock(pos, true);
         }
      } else if (this.type == 2) {
         if (worldIn.isAirBlock(pos.down())) {
            worldIn.destroyBlock(pos, true);
         }
      } else if (this.type == 3 && (worldIn.isAirBlock(pos.up()) || worldIn.isAirBlock(pos.down()))) {
         worldIn.destroyBlock(pos, true);
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return this.type == 1 ? AABBsmall : AABB;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public int damageDropped(IBlockState state) {
      return ((EnumType)state.getValue(VARIANT)).getMetadata();
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return ((EnumType)state.getValue(VARIANT)).getMapColor();
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumType)state.getValue(VARIANT)).getMetadata();
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{VARIANT});
   }

   public static enum EnumType implements IStringSerializable {
      S1(0, "s1", MapColor.STONE),
      S2(1, "s2", MapColor.STONE),
      S3(2, "s3", MapColor.STONE),
      S4(3, "s4", MapColor.STONE),
      S5(4, "s5", MapColor.STONE);

      private static final EnumType[] META_LOOKUP = new EnumType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;
      private final MapColor mapColor;

      private EnumType(int metaIn, String nameIn, MapColor mapColorIn) {
         this(metaIn, nameIn, nameIn, mapColorIn);
      }

      private EnumType(int metaIn, String nameIn, String unlocalizedNameIn, MapColor mapColorIn) {
         this.meta = metaIn;
         this.name = nameIn;
         this.unlocalizedName = unlocalizedNameIn;
         this.mapColor = mapColorIn;
      }

      public int getMetadata() {
         return this.meta;
      }

      public MapColor getMapColor() {
         return this.mapColor;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static EnumType byMetadata(int meta) {
         if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
         }

         return META_LOOKUP[meta];
      }

      public String getName() {
         return this.name;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      static {
         for (EnumType blockplanks$enumtype : values()) {
            META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype;
         }
      }
   }
}

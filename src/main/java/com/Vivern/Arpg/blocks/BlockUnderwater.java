//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.Vivern.Arpg.main.ColorConverters;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer.StateImplementation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUnderwater extends Block {
   public static final PropertyBool WET = PropertyBool.create("wet");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

   public BlockUnderwater(Material materialIn) {
      super(materialIn);
   }

   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!isInWater(world, pos)) {
         world.setBlockState(pos, state.withProperty(WET, false));
      } else {
         world.setBlockState(pos, state.withProperty(WET, true));
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public static boolean isInWater(IBlockAccess worldIn, BlockPos pos) {
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
                     if (state6.getMaterial() == Material.WATER || state6.isOpaqueCube()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public static boolean isAroundWater(IBlockAccess world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
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

   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      return isAroundWater(world, pos)
         ? world.setBlockState(pos, Blocks.WATER.getDefaultState(), world.isRemote ? 10 : 2)
         : world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 10 : 2);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return super.getActualState(state, worldIn, pos).withProperty(LEVEL, 0).withProperty(WET, isInWater(worldIn, pos));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, isInWater(worldIn, pos));
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }

   public static class BlockBlockUnderwater extends BlockUnderwater {
      public boolean replaceableOreGen = true;
      public String dropped = null;
      public BlockRenderLayer layer = BlockRenderLayer.SOLID;
      public boolean opaque = true;
      public boolean fullcub = true;
      public boolean fullbloc = true;
      public AxisAlignedBB aabbSEL = FULL_BLOCK_AABB;
      public AxisAlignedBB aabbCOL = FULL_BLOCK_AABB;
      public EnumOffsetType offsets = EnumOffsetType.NONE;
      public boolean vasePlace = false;
      public int packedLightmapCoords = -1;
      public float blockSlipperiness = 0.6F;

      public BlockBlockUnderwater(Material mater, String name, float hard, float resi) {
         super(mater);
         this.setRegistryName(name);
         this.setTranslationKey(name);
         this.blockHardness = hard;
         this.blockResistance = resi;
         this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
         this.setLightOpacity(255);
      }

      protected BlockStateContainer createBlockState() {
         return new BlockStateContainer(this, new IProperty[]{LEVEL, WET});
      }

      public IBlockState getStateFromMeta(int meta) {
         return this.getDefaultState();
      }

      public int getMetaFromState(IBlockState state) {
         return 0;
      }

      public BlockBlockUnderwater setSlipperiness(float blockSlipperiness) {
         this.blockSlipperiness = blockSlipperiness;
         return this;
      }

      public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
         return this.blockSlipperiness;
      }

      @SideOnly(Side.CLIENT)
      public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
         return this.packedLightmapCoords == -1 ? super.getPackedLightmapCoords(state, source, pos) : this.packedLightmapCoords;
      }

      public BlockBlockUnderwater setLightOptions(int lightValue, int lightmapCoords) {
         this.lightValue = lightValue;
         this.packedLightmapCoords = ColorConverters.RGBAtoDecimal255(lightmapCoords, 0, lightmapCoords, 0);
         return this;
      }

      @SideOnly(Side.CLIENT)
      public BlockRenderLayer getRenderLayer() {
         return this.layer;
      }

      public BlockBlockUnderwater setOffsets(EnumOffsetType offsets) {
         this.offsets = offsets;
         return this;
      }

      public BlockBlockUnderwater setPlaceAsVase(boolean b) {
         this.vasePlace = b;
         return this;
      }

      public BlockBlockUnderwater setOpacity(int i) {
         this.setLightOpacity(i);
         return this;
      }

      public EnumOffsetType getOffsetType() {
         return this.offsets;
      }

      @Override
      public boolean isOpaqueCube(IBlockState state) {
         return this.opaque;
      }

      public BlockBlockUnderwater setOpaque(boolean b) {
         this.opaque = b;
         return this;
      }

      public BlockBlockUnderwater setFullcube(boolean b) {
         this.fullcub = b;
         return this;
      }

      public BlockBlockUnderwater setAABB(AxisAlignedBB aabbSELECT, AxisAlignedBB aabbCOLLIDE) {
         this.aabbSEL = aabbSELECT;
         this.aabbCOL = aabbCOLLIDE;
         this.fullbloc = false;
         return this;
      }

      public BlockBlockUnderwater setAABB(AxisAlignedBB aabb) {
         this.aabbSEL = aabb;
         this.aabbCOL = aabb;
         this.fullbloc = false;
         return this;
      }

      public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
         return this.aabbSEL;
      }

      public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
         return this.aabbCOL;
      }

      public BlockBlockUnderwater setRenderLayer(BlockRenderLayer render) {
         this.layer = render;
         return this;
      }

      public BlockBlockUnderwater setSound(SoundType sound) {
         this.setSoundType(sound);
         return this;
      }

      public BlockBlockUnderwater setisReplaceableOreGen(boolean b) {
         this.replaceableOreGen = b;
         return this;
      }

      public BlockBlockUnderwater setHarvest(String tool, int lvl) {
         this.setHarvestLevel(tool, lvl);
         return this;
      }

      public BlockBlockUnderwater setItemDropped(String dropped) {
         this.dropped = dropped;
         return this;
      }

      public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
         return this.vasePlace && worldIn.isAirBlock(pos.down()) ? false : super.canPlaceBlockAt(worldIn, pos);
      }

      public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
         return this.replaceableOreGen;
      }

      public Item getItemDropped(IBlockState state, Random rand, int fortune) {
         if (this.dropped != null) {
            Item dr = Item.getByNameOrId(this.dropped);
            return dr != null ? dr : Items.AIR;
         } else {
            return Item.getItemFromBlock(this);
         }
      }

      @Override
      public boolean isFullCube(IBlockState state) {
         return this.fullcub;
      }

      public boolean isFullBlock(IBlockState state) {
         return this.fullbloc;
      }
   }

   public static class BlockStateContainerUnderwater extends BlockStateContainer {
      public BlockStateContainerUnderwater(Block blockIn, IProperty<?>[] properties) {
         super(blockIn, properties);
      }

      protected BlockStateContainerUnderwater(Block blockIn, IProperty<?>[] properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
         super(blockIn, properties, unlistedProperties);
      }

      protected StateImplementation createState(
         Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties
      ) {
         return new StateImplementationUnderwater(block, properties);
      }
   }

   public static class StateImplementationUnderwater extends StateImplementation {
      protected StateImplementationUnderwater(Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn) {
         super(blockIn, propertiesIn);
      }

      protected StateImplementationUnderwater(
         Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn, ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable
      ) {
         super(blockIn, propertiesIn, propertyValueTable);
      }

      public <T extends Comparable<T>> T getValue(IProperty<T> property) {
         return (T)("level".equals(property.getName())
            ? Blocks.WATER.getDefaultState().getValue(BlockLiquid.LEVEL)
            : super.getValue(property));
      }
   }
}

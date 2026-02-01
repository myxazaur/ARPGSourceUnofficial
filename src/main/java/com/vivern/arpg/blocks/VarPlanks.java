package com.vivern.arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class VarPlanks extends Block {
   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

   public VarPlanks(String name) {
      super(Material.WOOD);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.FROZEN));
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public int damageDropped(IBlockState state) {
      return ((EnumType)state.getValue(VARIANT)).getMetadata();
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      for (EnumType blockplanks$enumtype : EnumType.values()) {
         items.add(new ItemStack(this, 1, blockplanks$enumtype.getMetadata()));
      }
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
      FROZEN(0, "frozen", MapColor.WOOD);

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

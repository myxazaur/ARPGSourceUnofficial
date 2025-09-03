//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.ColorConverters;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBlock extends Block {
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

   public BlockBlock(Material mater, String name, float hard, float resi) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setLightOpacity(255);
   }

   public BlockBlock setSlipperiness(float blockSlipperiness) {
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

   public BlockBlock setLightOptions(int lightValue, int lightmapCoords) {
      this.lightValue = lightValue;
      this.packedLightmapCoords = ColorConverters.RGBAtoDecimal255(lightmapCoords, 0, lightmapCoords, 0);
      return this;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return this.layer;
   }

   public BlockBlock setOffsets(EnumOffsetType offsets) {
      this.offsets = offsets;
      return this;
   }

   public BlockBlock setPlaceAsVase(boolean b) {
      this.vasePlace = b;
      return this;
   }

   public BlockBlock setOpacity(int i) {
      this.setLightOpacity(i);
      return this;
   }

   public EnumOffsetType getOffsetType() {
      return this.offsets;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return this.opaque;
   }

   public BlockBlock setOpaque(boolean b) {
      this.opaque = b;
      return this;
   }

   public BlockBlock setFullcube(boolean b) {
      this.fullcub = b;
      return this;
   }

   public BlockBlock setAABB(AxisAlignedBB aabbSELECT, AxisAlignedBB aabbCOLLIDE) {
      this.aabbSEL = aabbSELECT;
      this.aabbCOL = aabbCOLLIDE;
      this.fullbloc = false;
      return this;
   }

   public BlockBlock setAABB(AxisAlignedBB aabb) {
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

   public BlockBlock setRenderLayer(BlockRenderLayer render) {
      this.layer = render;
      return this;
   }

   public BlockBlock setSound(SoundType sound) {
      this.setSoundType(sound);
      return this;
   }

   public BlockBlock setisReplaceableOreGen(boolean b) {
      this.replaceableOreGen = b;
      return this;
   }

   public BlockBlock setHarvest(String tool, int lvl) {
      this.setHarvestLevel(tool, lvl);
      return this;
   }

   public BlockBlock setItemDropped(String dropped) {
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

   public boolean isFullCube(IBlockState state) {
      return this.fullcub;
   }

   public boolean isFullBlock(IBlockState state) {
      return this.fullbloc;
   }
}

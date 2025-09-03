//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ConiferLog extends BlockRotatedPillar implements IBlockHardBreak {
   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);

   public ConiferLog() {
      super(Material.WOOD);
      this.setRegistryName("conifer_log");
      this.setTranslationKey("conifer_log");
      this.blockHardness = BlocksRegister.HR_CONIFER_LOG.HARDNESS;
      this.blockResistance = BlocksRegister.HR_CONIFER_LOG.RESISTANCE;
      this.setHarvestLevel("axe", BlocksRegister.HR_CONIFER_LOG.LVL);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
      this.setSoundType(SoundType.WOOD);
   }

   public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
      return true;
   }

   public boolean isWood(IBlockAccess world, BlockPos pos) {
      return true;
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      int i = 4;
      int j = 5;
      if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
         for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
               iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
            }
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y);
      switch (meta) {
         case 0:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
            break;
         case 8:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
            break;
         default:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumAxis)state.getValue(LOG_AXIS)) {
         case X:
            i = 4;
            break;
         case Z:
            i = 8;
            break;
         case NONE:
            i = 12;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.BROWN;
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
   }

   protected ItemStack getSilkTouchDrop(IBlockState state) {
      return new ItemStack(Item.getItemFromBlock(this));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((EnumAxis)state.getValue(LOG_AXIS)) {
               case X:
                  return state.withProperty(LOG_AXIS, EnumAxis.Z);
               case Z:
                  return state.withProperty(LOG_AXIS, EnumAxis.X);
               default:
                  return state;
            }
         default:
            return state;
      }
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_CONIFER_LOG.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}

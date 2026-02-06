package com.Vivern.Arpg.blocks;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockColumn extends BlockBlock {
   public static final PropertyEnum<EnumAxis> AXIS = PropertyEnum.create("axis", EnumAxis.class);

   public BlockColumn(Material mater, String name, float hard, float resi) {
      super(mater, name, hard, resi);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.Y));
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState().withProperty(AXIS, EnumAxis.Y);
      switch (meta) {
         case 0:
            iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Y);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(AXIS, EnumAxis.X);
            break;
         case 8:
            iblockstate = iblockstate.withProperty(AXIS, EnumAxis.Z);
            break;
         default:
            iblockstate = iblockstate.withProperty(AXIS, EnumAxis.NONE);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumAxis)state.getValue(AXIS)) {
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
      return new BlockStateContainer(this, new IProperty[]{AXIS});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((EnumAxis)state.getValue(AXIS)) {
               case X:
                  return state.withProperty(AXIS, EnumAxis.Z);
               case Z:
                  return state.withProperty(AXIS, EnumAxis.X);
               default:
                  return state;
            }
         default:
            return state;
      }
   }

   public static class HardBlockColumn extends BlockColumn implements IBlockHardBreak {
      public float slowSpeed;
      public float fastSpeed;
      public boolean canDropWhithoutTool;
      public int level;
      public String tool;

      public HardBlockColumn(
         Material mater, String name, float hard, float resi, float slowSpeed, float fastSpeed, int harvestlvl, String tool, boolean canDropWhithoutTool
      ) {
         super(mater, name, hard, resi);
         this.slowSpeed = slowSpeed;
         this.canDropWhithoutTool = canDropWhithoutTool;
         this.level = harvestlvl;
         this.tool = tool;
         this.fastSpeed = fastSpeed;
         this.setHarvest(tool, harvestlvl);
      }

      public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
         player.addStat(StatList.getBlockStats(this));
         player.addExhaustion(0.005F);
         if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            List<ItemStack> items = new ArrayList<>();
            ItemStack itemstack = this.getSilkTouchDrop(state);
            if (!itemstack.isEmpty()) {
               items.add(itemstack);
            }

            ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0F, true, player);

            for (ItemStack item : items) {
               spawnAsEntity(worldIn, pos, item);
            }
         } else {
            this.harvesters.set(player);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            if (this.canDropWhithoutTool || stack.getItem().getHarvestLevel(stack, this.tool, player, state) >= this.level) {
               this.dropBlockAsItem(worldIn, pos, state, i);
            }

            this.harvesters.set(null);
         }
      }

      @Override
      public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
         return toolLevel >= this.level && tool.equals(this.getHarvestTool(state)) ? originalSpeed * this.fastSpeed : originalSpeed * this.slowSpeed;
      }
   }
}

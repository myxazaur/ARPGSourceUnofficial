//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class Pilaster extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

   public Pilaster(Material mater, String name, float hard, float resi, SoundType stype, String tool, int harvestlvl) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setSoundType(stype);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setHarvestLevel(tool, harvestlvl);
   }

   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return ((EnumFacing)state.getValue(FACING)).getOpposite() == side;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      switch ((EnumFacing)blockState.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState();
      switch (meta) {
         case 1:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
            break;
         case 2:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
            break;
         case 3:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
            break;
         case 5:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN);
            break;
         default:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      byte var3;
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            var3 = 1;
            break;
         case WEST:
            var3 = 2;
            break;
         case SOUTH:
            var3 = 3;
            break;
         case NORTH:
            var3 = 4;
            break;
         case DOWN:
            var3 = 5;
            break;
         case UP:
            var3 = 0;
            break;
         default:
            var3 = 0;
      }

      return var3;
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockUnderwater.BlockStateContainerUnderwater(this, new IProperty[]{FACING});
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, facing);
   }

   public static class HardPilaster extends Pilaster implements IBlockHardBreak {
      public float slowSpeed;
      public float fastSpeed;
      public boolean canDropWhithoutTool;
      public int level;
      public String tool;

      public HardPilaster(
         Material mater,
         String name,
         float hard,
         float resi,
         float slowSpeed,
         float fastSpeed,
         SoundType stype,
         String tool,
         int harvestlvl,
         boolean canDropWhithoutTool
      ) {
         super(mater, name, hard, resi, stype, tool, harvestlvl);
         this.slowSpeed = slowSpeed;
         this.canDropWhithoutTool = canDropWhithoutTool;
         this.level = harvestlvl;
         this.tool = tool;
         this.fastSpeed = fastSpeed;
      }

      public HardPilaster(Material mater, String name, SoundType stype, BlocksRegister.Hardres hardres, String tool, boolean canDropWhithoutTool) {
         super(mater, name, hardres.HARDNESS, hardres.RESISTANCE, stype, tool, hardres.LVL);
         this.slowSpeed = hardres.SLOW;
         this.canDropWhithoutTool = canDropWhithoutTool;
         this.level = hardres.LVL;
         this.tool = tool;
         this.fastSpeed = hardres.FAST;
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

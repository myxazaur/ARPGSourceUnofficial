package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.tileentity.TileBlockPlacer;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockPlacer extends Block {
   public static final PropertyDirection FACING = BlockDirectional.FACING;
   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

   public BlockPlacer() {
      super(Material.ROCK);
      this.setRegistryName("block_placer");
      this.setTranslationKey("block_placer");
      this.blockHardness = 1.5F;
      this.blockResistance = 1.5F;
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
   }

   public int tickRate(World worldIn) {
      return 4;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      boolean flag1 = (Boolean)state.getValue(TRIGGERED);
      if (flag && !flag1) {
         worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
         NBTTagCompound tag = new NBTTagCompound();
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof TileBlockPlacer) {
            TileBlockPlacer tileBlockPlacer = (TileBlockPlacer)tileentity;
            tileBlockPlacer.writeToNBT(tag);
         }

         worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true), 4);
         TileEntity tileentity2 = worldIn.getTileEntity(pos);
         if (tileentity2 instanceof TileBlockPlacer) {
            TileBlockPlacer tileBlockPlacer2 = (TileBlockPlacer)tileentity2;
            tileBlockPlacer2.readFromNBT(tag);
         }
      } else if (!flag && flag1) {
         NBTTagCompound tagx = new NBTTagCompound();
         TileEntity tileentityx = worldIn.getTileEntity(pos);
         if (tileentityx instanceof TileBlockPlacer) {
            TileBlockPlacer tileBlockPlacer = (TileBlockPlacer)tileentityx;
            tileBlockPlacer.writeToNBT(tagx);
         }

         worldIn.setBlockState(pos, state.withProperty(TRIGGERED, false), 4);
         TileEntity tileentity2 = worldIn.getTileEntity(pos);
         if (tileentity2 instanceof TileBlockPlacer) {
            TileBlockPlacer tileBlockPlacer2 = (TileBlockPlacer)tileentity2;
            tileBlockPlacer2.readFromNBT(tagx);
         }
      }
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote) {
         this.tryPlaceBlock(state, worldIn, pos);
      }
   }

   public void tryPlaceBlock(IBlockState state, World worldIn, BlockPos pos) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileBlockPlacer) {
         TileBlockPlacer tileBlockPlacer = (TileBlockPlacer)tileentity;
         BlockPos offset = pos.offset((EnumFacing)state.getValue(FACING));
         if (worldIn.isAirBlock(offset) || worldIn.getBlockState(offset).getBlock().isReplaceable(worldIn, offset)) {
            int stackid = tileBlockPlacer.getRandStackId();
            if (stackid >= 0) {
               ItemStack stack = tileBlockPlacer.getStackInSlot(stackid);
               if (stack.getItem() instanceof ItemBlock) {
                  Block block = ((ItemBlock)stack.getItem()).getBlock();
                  if (block.canPlaceBlockAt(worldIn, offset)) {
                     worldIn.setBlockState(offset, block.getDefaultState());
                     stack.shrink(1);
                     worldIn.playSound(null, offset, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                  }
               } else if (stack.getItem() instanceof IPlantable) {
                  IPlantable seeds = (IPlantable)stack.getItem();
                  IBlockState plantState = seeds.getPlant(worldIn, offset);
                  IBlockState ground = worldIn.getBlockState(offset.down());
                  if (ground.getBlock().canSustainPlant(ground, worldIn, offset.down(), EnumFacing.UP, seeds) && worldIn.isAirBlock(offset)) {
                     worldIn.setBlockState(offset, plantState);
                     stack.shrink(1);
                  }
               } else if (stack.getItem() instanceof ISeed) {
                  ISeed seeds = (ISeed)stack.getItem();
                  IBlockState plantState = seeds.getPlant(worldIn, offset);
                  if (seeds.canGrowAt(worldIn, offset)) {
                     worldIn.setBlockState(offset, plantState);
                     stack.shrink(1);
                  }
               }
            }
         }
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         return true;
      } else {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof TileBlockPlacer) {
            playerIn.displayGUIChest((TileBlockPlacer)tileentity);
         }

         return true;
      }
   }

   public Class<TileBlockPlacer> getTileEntityClass() {
      return TileBlockPlacer.class;
   }

   public TileBlockPlacer getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileBlockPlacer)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileBlockPlacer createTileEntity(World world, IBlockState blockState) {
      return new TileBlockPlacer();
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileBlockPlacer) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (TileBlockPlacer)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean hasComparatorInputOverride(IBlockState state) {
      return true;
   }

   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
      return Container.calcRedstone(worldIn.getTileEntity(pos));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(TRIGGERED, false);
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7)).withProperty(TRIGGERED, (meta & 8) > 0);
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      i |= ((EnumFacing)state.getValue(FACING)).getIndex();
      if ((Boolean)state.getValue(TRIGGERED)) {
         i |= 8;
      }

      return i;
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING, TRIGGERED});
   }
}

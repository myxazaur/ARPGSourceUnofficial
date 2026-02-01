package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConiferLeaves extends BlockLeaves {
   public ConiferLeaves() {
      this.setRegistryName("conifer_leaves");
      this.setTranslationKey("conifer_leaves");
      this.setHardness(0.1F);
      this.setResistance(0.01F);
      this.setTickRandomly(true);
      this.setLightOpacity(1);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setSoundType(SoundType.PLANT);
      this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
      this.setHarvestLevel("shears", 0);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return rand.nextFloat() < 0.45F ? ItemsRegister.CONIFERSTICK : Item.getItemFromBlock(BlocksRegister.CONIFERSAPLING);
   }

   protected int getSaplingDropChance(IBlockState state) {
      return 6;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      Random rand = RANDOM;
      int chance = this.getSaplingDropChance(state);
      if (fortune > 0) {
         chance -= fortune;
         if (chance < 2) {
            chance = 2;
         }
      }

      if (rand.nextInt(chance) == 0) {
         ItemStack drop = new ItemStack(this.getItemDropped(state, rand, fortune), 1, this.damageDropped(state));
         if (!drop.isEmpty()) {
            drops.add(drop);
         }
      }
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1));
   }

   protected ItemStack getSilkTouchDrop(IBlockState state) {
      return new ItemStack(Item.getItemFromBlock(this), 1);
   }

   public IBlockState getStateFromMeta(int meta) {
      switch (meta) {
         case 0:
            return this.getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
         case 1:
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return this.getDefaultState();
         case 2:
            return this.getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, true);
         case 4:
            return this.getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, true);
         case 8:
            return this.getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, false);
      }
   }

   public int getMetaFromState(IBlockState state) {
      if (!(Boolean)state.getValue(DECAYABLE) && !(Boolean)state.getValue(CHECK_DECAY)) {
         return 0;
      } else if (!(Boolean)state.getValue(DECAYABLE) && (Boolean)state.getValue(CHECK_DECAY)) {
         return 2;
      } else if ((Boolean)state.getValue(DECAYABLE) && (Boolean)state.getValue(CHECK_DECAY)) {
         return 4;
      } else {
         return state.getValue(DECAYABLE) && !state.getValue(CHECK_DECAY) ? 8 : 0;
      }
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{CHECK_DECAY, DECAYABLE});
   }

   public int damageDropped(IBlockState state) {
      return 0;
   }

   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
      if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
         player.addStat(StatList.getBlockStats(this));
      } else {
         super.harvestBlock(worldIn, player, pos, state, te, stack);
      }
   }

   public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
      return NonNullList.withSize(1, new ItemStack(this, 1));
   }

   public EnumType getWoodType(int meta) {
      return EnumType.SPRUCE;
   }
}

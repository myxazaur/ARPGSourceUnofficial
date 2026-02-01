package com.vivern.arpg.blocks;

import com.vivern.arpg.entity.EntityCoin;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrozenTreasureBarrel extends Block {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

   public FrozenTreasureBarrel() {
      super(Material.WOOD);
      this.setRegistryName("frozen_treasure_barrel");
      this.setTranslationKey("frozen_treasure_barrel");
      this.blockHardness = 0.2F;
      this.blockResistance = 0.1F;
      this.setSoundType(SoundType.WOOD);
      this.setCreativeTab(CreativeTabs.MISC);
      this.setHarvestLevel("axe", 0);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      int maxr = 1 + RANDOM.nextInt(2);

      for (int r = 0; r < maxr; r++) {
         if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
            if (RANDOM.nextFloat() < 0.4 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.SNOWFLAKESHUR, 2 + RANDOM.nextInt(15)));
            } else if (RANDOM.nextFloat() < 0.15 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(BlocksRegister.FROZENTORCH, 6 + RANDOM.nextInt(10)));
            } else if (RANDOM.nextFloat() < 0.15 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.CONIFERROSIN, 2 + RANDOM.nextInt(5)));
            } else if (RANDOM.nextFloat() < 0.15 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(Items.COAL, 1 + RANDOM.nextInt(2)));
            } else if (RANDOM.nextFloat() < 0.1 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(Items.ARROW, 4 + RANDOM.nextInt(5)));
            } else if (RANDOM.nextFloat() < 0.25 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.CONIFERSTICK, 2 + RANDOM.nextInt(4)));
            } else if (RANDOM.nextFloat() < 0.15 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(Items.STICK, 2 + RANDOM.nextInt(4)));
            } else if (RANDOM.nextFloat() < 0.15 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.ICEFLOWERSEEDS, 1 + RANDOM.nextInt(3)));
            } else if (RANDOM.nextFloat() < 0.25 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(BlocksRegister.CONIFERSAPLING, 2 + RANDOM.nextInt(4)));
            } else if (RANDOM.nextFloat() < 0.16 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.ARROWFROZEN, 4 + RANDOM.nextInt(8)));
            } else if (RANDOM.nextFloat() < 0.2 * chance) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.HAILTEAR, 1 + RANDOM.nextInt(3)));
            }

            if (RANDOM.nextFloat() < 0.2 * chance) {
               for (int i = 0; i < 1 + RANDOM.nextInt(7); i++) {
                  EntityCoin coin = new EntityCoin(worldIn, 1 + (RANDOM.nextFloat() < 0.3 ? 1 : 0));
                  coin.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                  worldIn.spawnEntity(coin);
               }
            }
         }
      }
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
      return true;
   }

   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return MathHelper.getInt(RANDOM, 0, 5);
   }
}

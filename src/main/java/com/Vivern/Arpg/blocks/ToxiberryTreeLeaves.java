package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxiberryTreeLeaves extends Block implements IShearable {
   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
   int[] surroundings;

   public ToxiberryTreeLeaves() {
      super(Material.LEAVES);
      this.setRegistryName("toxiberry_tree_leaves");
      this.setTranslationKey("toxiberry_tree_leaves");
      this.setHardness(0.1F);
      this.setResistance(0.01F);
      this.setTickRandomly(true);
      this.setLightOpacity(1);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setSoundType(SoundType.PLANT);
      this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
      this.setHarvestLevel("shears", 0);
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      int i = 1;
      int j = 2;
      int k = pos.getX();
      int l = pos.getY();
      int i1 = pos.getZ();
      if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2))) {
         for (int j1 = -1; j1 <= 1; j1++) {
            for (int k1 = -1; k1 <= 1; k1++) {
               for (int l1 = -1; l1 <= 1; l1++) {
                  BlockPos blockpos = pos.add(j1, k1, l1);
                  IBlockState iblockstate = worldIn.getBlockState(blockpos);
                  if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
                     iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                  }
               }
            }
         }
      }
   }

   private void destroy(World worldIn, BlockPos pos) {
      this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
      worldIn.setBlockToAir(pos);
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isTopSolid() && rand.nextInt(15) == 1) {
         double d0 = pos.getX() + rand.nextFloat();
         double d1 = pos.getY() - 0.05;
         double d2 = pos.getZ() + rand.nextFloat();
         worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
      }
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote && (Boolean)state.getValue(CHECK_DECAY) && (Boolean)state.getValue(DECAYABLE)) {
         int i = 4;
         int j = 5;
         int k = pos.getX();
         int l = pos.getY();
         int i1 = pos.getZ();
         int j1 = 32;
         int k1 = 1024;
         int l1 = 16;
         if (this.surroundings == null) {
            this.surroundings = new int[32768];
         }

         if (!worldIn.isAreaLoaded(pos, 1)) {
            return;
         }

         if (worldIn.isAreaLoaded(pos, 6)) {
            MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

            for (int i2 = -4; i2 <= 4; i2++) {
               for (int j2 = -4; j2 <= 4; j2++) {
                  for (int k2 = -4; k2 <= 4; k2++) {
                     IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
                     Block block = iblockstate.getBlock();
                     if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                        if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                           this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                        } else {
                           this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                        }
                     } else {
                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                     }
                  }
               }
            }

            for (int i3 = 1; i3 <= 4; i3++) {
               for (int j3 = -4; j3 <= 4; j3++) {
                  for (int k3 = -4; k3 <= 4; k3++) {
                     for (int l3 = -4; l3 <= 4; l3++) {
                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
                           if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                              this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                           }

                           if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                              this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                           }

                           if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2) {
                              this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                           }

                           if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2) {
                              this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                           }

                           if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2) {
                              this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                           }

                           if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2) {
                              this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                           }
                        }
                     }
                  }
               }
            }
         }

         int l2 = this.surroundings[16912];
         if (l2 >= 0) {
            worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
         } else {
            this.destroy(worldIn, pos);
         }
      }
   }

   public boolean causesSuffocation(IBlockState state) {
      return false;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
      return true;
   }

   public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
      if (!(Boolean)state.getValue(CHECK_DECAY)) {
         world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
      }
   }

   public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
      return true;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      Random rand = world instanceof World ? ((World)world).rand : new Random();
      if (rand.nextInt(17) == 0) {
         drops.add(new ItemStack(BlocksRegister.TOXIBERRYSAPLING));
      }

      if (rand.nextInt(4) == 0) {
         drops.add(new ItemStack(ItemsRegister.SMALLTOXIBERRY));
      }

      if (rand.nextInt(5) == 0) {
         drops.add(new ItemStack(ItemsRegister.SMALLTOXIBERRY));
      }

      if (rand.nextInt(4) == 0) {
         drops.add(new ItemStack(ItemsRegister.PLANTFIBER));
      }
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
}

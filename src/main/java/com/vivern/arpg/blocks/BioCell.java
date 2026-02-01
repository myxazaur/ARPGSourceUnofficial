package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.tileentity.TileBioCell;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BioCell extends BlockBlockHard {
   public static final ResourceLocation texbubble = new ResourceLocation("arpg:textures/blob.png");
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 2.0, 0.9375);

   public BioCell() {
      super(Material.GLASS, "bio_cell", BlocksRegister.HR_WOLFRAM_AND_BIOCELLS, "pickaxe", false);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.GLASS);
   }

   public Class<TileBioCell> getTileEntityClass() {
      return TileBioCell.class;
   }

   public TileBioCell getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileBioCell)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileBioCell createTileEntity(World world, IBlockState blockState) {
      TileBioCell t = new TileBioCell();
      t.seed = RANDOM.nextInt(1000);
      return t;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.8F) {
         GUNParticle spelll = new GUNParticle(
            texbubble,
            0.04F + rand.nextFloat() * 0.015F,
            -0.0012F,
            63,
            180,
            worldIn,
            pos.getX() + 0.125F + rand.nextFloat() * 0.75F,
            pos.getY() + 0.1875F,
            pos.getZ() + 0.125F + rand.nextFloat() * 0.75F,
            0.0F,
            0.0F,
            0.0F,
            0.6F,
            1.0F,
            0.5F,
            true,
            0
         );
         worldIn.spawnEntity(spelll);
      }
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
         if (RANDOM.nextFloat() < 0.25) {
            if (RANDOM.nextFloat() < 0.2) {
               ToxicomaniaMobsPack.TestTubeCreature mob = new ToxicomaniaMobsPack.TestTubeCreature(worldIn);
               mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
               worldIn.spawnEntity(mob);
            } else {
               for (int i = 0; i < 1 + RANDOM.nextInt(4); i++) {
                  ToxicomaniaMobsPack.TestTubeSubstance mob = new ToxicomaniaMobsPack.TestTubeSubstance(worldIn);
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  worldIn.spawnEntity(mob);
               }
            }

            return;
         }

         if (RANDOM.nextFloat() < 0.6 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.EMBRYO));
         }

         worldIn.setBlockState(pos, BlocksRegister.FLUIDBIOGENICACID.getDefaultState());
         if (worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), BlocksRegister.FLUIDBIOGENICACID.getDefaultState());
         }
      }
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_WOLFRAM_AND_BIOCELLS.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }

   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return MathHelper.getInt(RANDOM, 5, 18);
   }
}

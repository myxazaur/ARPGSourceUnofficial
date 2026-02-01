package com.vivern.arpg.blocks;

import com.vivern.arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalChandelier extends Block {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/star.png");
   public static final PropertyEnum<FrozenChandelier.EnumAxis> ROTATE = PropertyEnum.create("rotate", FrozenChandelier.EnumAxis.class);
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.2, 0.75);

   public CrystalChandelier() {
      super(Material.GLASS);
      this.setRegistryName("crystal_chandelier");
      this.setTranslationKey("crystal_chandelier");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.1F;
      this.setSoundType(SoundType.STONE);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.9F);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return !worldIn.isAirBlock(pos.down());
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      switch (meta) {
         case 0:
            return this.getDefaultState().withProperty(ROTATE, FrozenChandelier.EnumAxis.X);
         case 1:
            return this.getDefaultState().withProperty(ROTATE, FrozenChandelier.EnumAxis.Z);
         default:
            return this.getDefaultState().withProperty(ROTATE, FrozenChandelier.EnumAxis.X);
      }
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((FrozenChandelier.EnumAxis)state.getValue(ROTATE)) {
         case X:
            i = 0;
            break;
         case Z:
            i = 1;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{ROTATE});
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(ROTATE, FrozenChandelier.EnumAxis.fromFacingAxis(placer.getHorizontalFacing().getOpposite().getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(ROTATE, rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180 ? FrozenChandelier.EnumAxis.Z : FrozenChandelier.EnumAxis.X);
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      FrozenChandelier.EnumAxis enumaxis = (FrozenChandelier.EnumAxis)stateIn.getValue(ROTATE);
      if (rand.nextFloat() < 0.06F) {
         this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, rand);
      }

      if (enumaxis == FrozenChandelier.EnumAxis.X) {
         if (rand.nextFloat() < 0.06F) {
            this.spawnpart(worldIn, pos.getX() + 0.1875, pos.getY() + 1.06, pos.getZ() + 0.5, rand);
         }

         if (rand.nextFloat() < 0.06F) {
            this.spawnpart(worldIn, pos.getX() + 0.8125, pos.getY() + 1.06, pos.getZ() + 0.5, rand);
         }
      } else {
         if (rand.nextFloat() < 0.06F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.06, pos.getZ() + 0.1875, rand);
         }

         if (rand.nextFloat() < 0.06F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.06, pos.getZ() + 0.8125, rand);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void spawnpart(World worldIn, double d0, double d1, double d2, Random rand) {
      double d3 = 0.22;
      double d4 = 0.27;
      int livetime = 30 + rand.nextInt(40);
      float scale = 0.07F + rand.nextFloat() / 30.0F;
      float scaleTickAdding = -(scale / livetime);
      float randispl1 = (rand.nextFloat() - 0.5F) / 2.0F;
      float randispl2 = (rand.nextFloat() - 0.5F) / 2.0F;
      float randispl3 = (rand.nextFloat() - 0.5F) / 2.0F;
      float randsp1 = (rand.nextFloat() - 0.5F) / 60.0F;
      float randsp2 = (rand.nextFloat() - 0.5F) / 60.0F;
      float randsp3 = (rand.nextFloat() - 0.5F) / 60.0F;
      GUNParticle spelll = new GUNParticle(
         res,
         scale,
         0.001F,
         livetime,
         240,
         worldIn,
         d0 + randispl1,
         d1 + randispl2,
         d2 + randispl3,
         randsp1,
         randsp2,
         randsp3,
         1.0F,
         0.8F + rand.nextFloat() * 0.15F,
         0.9F + rand.nextFloat() * 0.1F,
         false,
         0
      );
      spelll.alpha = 0.1F;
      spelll.alphaTickAdding = 0.1F;
      spelll.scaleTickAdding = scaleTickAdding;
      spelll.alphaGlowing = true;
      worldIn.spawnEntity(spelll);
   }
}

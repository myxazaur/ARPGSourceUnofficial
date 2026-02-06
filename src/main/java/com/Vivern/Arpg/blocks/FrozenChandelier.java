package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.renders.GUNParticle;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrozenChandelier extends Block {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/frostlight.png");
   public static final PropertyEnum<EnumAxis> ROTATE = PropertyEnum.create("rotate", EnumAxis.class);
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.2, 0.75);

   public FrozenChandelier() {
      super(Material.GLASS);
      this.setRegistryName("frozen_chandelier");
      this.setTranslationKey("frozen_chandelier");
      this.blockHardness = BlocksRegister.HR_FROZEN_FURNITURE.HARDNESS;
      this.blockResistance = BlocksRegister.HR_FROZEN_FURNITURE.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_FROZEN_FURNITURE.LVL);
      this.setSoundType(SoundType.METAL);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.7F);
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
            return this.getDefaultState().withProperty(ROTATE, EnumAxis.X);
         case 1:
            return this.getDefaultState().withProperty(ROTATE, EnumAxis.Z);
         default:
            return this.getDefaultState().withProperty(ROTATE, EnumAxis.X);
      }
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumAxis)state.getValue(ROTATE)) {
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
      return this.getStateFromMeta(meta).withProperty(ROTATE, EnumAxis.fromFacingAxis(placer.getHorizontalFacing().getOpposite().getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(ROTATE, rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180 ? EnumAxis.Z : EnumAxis.X);
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      EnumAxis enumaxis = (EnumAxis)stateIn.getValue(ROTATE);
      double xx = pos.getX() + 0.5;
      double yy = pos.getY() + 1.6;
      double zz = pos.getZ() + 0.5;
      double d3 = 0.26;
      GUNParticle spelll = new GUNParticle(
         res,
         0.1F + rand.nextFloat() / 20.0F,
         -0.001F,
         20 + rand.nextInt(10),
         240,
         worldIn,
         xx,
         yy + 0.07,
         zz,
         0.0F,
         0.0F,
         0.0F,
         0.9F + rand.nextFloat() / 10.0F,
         1.0F,
         1.0F,
         false,
         0
      );
      spelll.scaleTickAdding = -0.0037F;
      worldIn.spawnEntity(spelll);
      if (enumaxis == EnumAxis.X) {
         GUNParticle spellx = new GUNParticle(
            res,
            0.1F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            xx + d3,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spellx.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spellx);
         GUNParticle spelx = new GUNParticle(
            res,
            0.1F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            xx - d3,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spelx.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spelx);
      } else {
         GUNParticle spellz = new GUNParticle(
            res,
            0.1F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            xx,
            yy,
            zz + d3,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spellz.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spellz);
         GUNParticle spelz = new GUNParticle(
            res,
            0.1F + rand.nextFloat() / 20.0F,
            -0.001F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            xx,
            yy,
            zz - d3,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            false,
            0
         );
         spelz.scaleTickAdding = -0.0037F;
         worldIn.spawnEntity(spelz);
      }
   }

   public static enum EnumAxis implements IStringSerializable {
      X("x"),
      Z("z");

      private final String name;

      private EnumAxis(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static EnumAxis fromFacingAxis(Axis axis) {
         switch (axis) {
            case X:
               return X;
            case Z:
               return Z;
            default:
               return X;
         }
      }

      public String getName() {
         return this.name;
      }
   }
}

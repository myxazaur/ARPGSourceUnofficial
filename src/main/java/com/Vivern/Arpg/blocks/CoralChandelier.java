package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoralChandelier extends BlockUnderwater {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/mana_flow.png");
   public static final PropertyEnum<FrozenChandelier.EnumAxis> ROTATE = PropertyEnum.create("rotate", FrozenChandelier.EnumAxis.class);
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.2, 0.75);

   public CoralChandelier() {
      super(Material.WATER);
      this.setRegistryName("coral_chandelier");
      this.setTranslationKey("coral_chandelier");
      this.blockHardness = 7.1F;
      this.blockResistance = 10.0F;
      this.setSoundType(SoundType.STONE);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.8F);
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

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
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
      return new BlockStateContainer(this, new IProperty[]{ROTATE, LEVEL, WET});
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   @Override
   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(ROTATE, FrozenChandelier.EnumAxis.fromFacingAxis(placer.getHorizontalFacing().getOpposite().getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(ROTATE, rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180 ? FrozenChandelier.EnumAxis.Z : FrozenChandelier.EnumAxis.X);
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      FrozenChandelier.EnumAxis enumaxis = (FrozenChandelier.EnumAxis)stateIn.getValue(ROTATE);
      if (rand.nextFloat() < 0.19F) {
         this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.625, pos.getZ() + 0.5, rand);
      }

      if (enumaxis == FrozenChandelier.EnumAxis.X) {
         if (rand.nextFloat() < 0.19F) {
            this.spawnpart(worldIn, pos.getX() + 0.15625, pos.getY() + 1.4375, pos.getZ() + 0.5, rand);
         }

         if (rand.nextFloat() < 0.19F) {
            this.spawnpart(worldIn, pos.getX() + 0.84375, pos.getY() + 1.4375, pos.getZ() + 0.5, rand);
         }
      } else {
         if (rand.nextFloat() < 0.19F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.4375, pos.getZ() + 0.15625, rand);
         }

         if (rand.nextFloat() < 0.19F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.4375, pos.getZ() + 0.84375, rand);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void spawnpart(World worldIn, double d0, double d1, double d2, Random rand) {
      int livetime = 80;
      float scale = 0.2F + rand.nextFloat() / 10.0F;
      float scaleTickAdding = scale / livetime;
      GUNParticle spelll = new GUNParticle(
         res,
         0.15F,
         0.0F,
         livetime,
         210,
         worldIn,
         d0,
         d1,
         d2,
         0.0F,
         0.0F,
         0.0F,
         0.5F + rand.nextFloat() * 0.1F,
         1.0F,
         0.85F + rand.nextFloat() * 0.15F,
         true,
         0
      );
      spelll.alpha = 1.0F;
      spelll.alphaTickAdding = -0.0125F;
      spelll.scaleTickAdding = scaleTickAdding;
      spelll.alphaGlowing = true;
      spelll.isPushedByLiquids = false;
      worldIn.spawnEntity(spelll);
   }

   @Override
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}

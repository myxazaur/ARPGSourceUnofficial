//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicChandelier extends Block {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/toxic_spell.png");
   public static final PropertyEnum<FrozenChandelier.EnumAxis> ROTATE = PropertyEnum.create("rotate", FrozenChandelier.EnumAxis.class);
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);

   public ToxicChandelier() {
      super(Material.WOOD);
      this.setRegistryName("toxic_chandelier");
      this.setTranslationKey("toxic_chandelier");
      this.blockHardness = BlocksRegister.HR_TOXIBERRY_FURNITURE.HARDNESS;
      this.blockResistance = BlocksRegister.HR_TOXIBERRY_FURNITURE.RESISTANCE;
      this.setSoundType(SoundType.WOOD);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(0.85F);
      this.setHarvestLevel("axe", BlocksRegister.HR_TOXIBERRY_FURNITURE.LVL);
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
      if (enumaxis == FrozenChandelier.EnumAxis.X) {
         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.1875, pos.getY() + 1.0, pos.getZ() + 0.5, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.8125, pos.getY() + 0.9, pos.getZ() + 0.5, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.1875, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.4, pos.getY() + 0.55, pos.getZ() + 0.8125, rand);
         }
      } else {
         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.1875, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.8125, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.8125, pos.getY() + 0.75, pos.getZ() + 0.5, rand);
         }

         if (rand.nextFloat() < 0.075F) {
            this.spawnpart(worldIn, pos.getX() + 0.1875, pos.getY() + 0.55, pos.getZ() + 0.4, rand);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void spawnpart(World worldIn, double d0, double d1, double d2, Random rand) {
      int livetime = 40 + rand.nextInt(40);
      float scale = 0.07F + rand.nextFloat() / 30.0F;
      float scaleTickAdding = -(scale / livetime);
      float randispl1 = (rand.nextFloat() - 0.5F) / 3.0F;
      float randispl2 = (rand.nextFloat() - 0.5F) / 3.0F;
      float randispl3 = (rand.nextFloat() - 0.5F) / 3.0F;
      float randsp1 = (rand.nextFloat() - 0.5F) / 40.0F;
      float randsp2 = (rand.nextFloat() - 0.5F) / 40.0F;
      float randsp3 = (rand.nextFloat() - 0.5F) / 40.0F;
      GUNParticle spelll = new GUNParticle(
         res,
         scale,
         0.0F,
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
         0.9F + rand.nextFloat() / 10.0F,
         1.0F,
         true,
         0
      );
      spelll.alpha = 0.1F;
      spelll.alphaTickAdding = 0.1F;
      spelll.scaleTickAdding = scaleTickAdding;
      spelll.alphaGlowing = true;
      worldIn.spawnEntity(spelll);
   }
}

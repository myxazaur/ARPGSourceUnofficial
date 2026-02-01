package com.vivern.arpg.blocks;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagmaBloom extends Block implements IGrowable {
   public static final AxisAlignedBB[] AABB = new AxisAlignedBB[]{
      new AxisAlignedBB(0.175, 0.0, 0.175, 0.825, 0.565, 0.825),
      new AxisAlignedBB(0.145, 0.0, 0.145, 0.855, 0.7875, 0.855),
      new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.9375, 0.875)
   };
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
   public static ResourceLocation res = new ResourceLocation("arpg:textures/liquid_fire.png");

   public MagmaBloom() {
      super(Material.PLANTS);
      this.setRegistryName("magma_bloom");
      this.setTranslationKey("magma_bloom");
      this.blockHardness = 0.2F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      IBlockState st = worldIn.getBlockState(pos.down());
      Block block = st.getBlock();
      return block != Blocks.LAVA && block != Blocks.FLOWING_LAVA ? false : (Integer)st.getValue(BlockLiquid.LEVEL) <= 1;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      if (!this.canPlaceBlockAt(worldIn, pos)) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB[state.getValue(AGE)];
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
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

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote && rand.nextFloat() < 0.2F) {
         this.grow(worldIn, rand, pos, state);
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && player.getHeldItem(hand).getItem() == ItemsRegister.MAGIC_POWDER) {
         player.getHeldItem(hand).shrink(1);
         this.grow(worldIn, RANDOM, pos, state);
         return true;
      } else {
         return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
      }
   }

   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return true;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return false;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumOffsetType block$enumoffsettype = this.getOffsetType();
      if (block$enumoffsettype == EnumOffsetType.NONE) {
         return Vec3d.ZERO;
      } else {
         long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
         return new Vec3d(
            ((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.5, ((float)(i >> 20 & 15L) / 15.0F - 0.4) * 0.4, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.5
         );
      }
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      if ((Integer)state.getValue(AGE) == 2) {
         int count = RANDOM.nextInt(3);
         if (count > 0) {
            drops.add(new ItemStack(ItemsRegister.LIQUIDFIRE, count));
         }

         drops.add(new ItemStack(ItemsRegister.MAGMABLOOMSEEDS, RANDOM.nextInt(2) + 1 + (fortune > 0 ? RANDOM.nextInt(fortune + 1) : 0)));
      }
   }

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      if (rand.nextFloat() < 0.4) {
         if (this.canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockState(pos, state.withProperty(AGE, Math.min((Integer)state.getValue(AGE) + 1, 2)));
         } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
         }
      }
   }

   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      if (!world.isRemote) {
         player.setFire(5);
      }

      return super.removedByPlayer(state, world, pos, player, willHarvest);
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      int age = (Integer)stateIn.getValue(AGE);
      if (rand.nextInt(10 - age * 2) == 0) {
         Vec3d offset = this.getOffset(stateIn, worldIn, pos);
         double xx = d0 + offset.x + 0.5;
         double yy = d1 + stateIn.getBoundingBox(worldIn, pos).maxY - 0.05 + offset.y;
         double zz = d2 + offset.z + 0.5;
         GUNParticle spelll = new GUNParticle(
            res,
            0.1F + rand.nextFloat() / 20.0F + age * 0.05F,
            -8.0E-4F,
            20 + rand.nextInt(10),
            200,
            worldIn,
            xx,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.9F + rand.nextFloat() / 10.0F,
            1.0F,
            true,
            rand.nextInt(41) - 20
         );
         spelll.scaleTickAdding = -0.0055F;
         spelll.alphaTickAdding = -0.03F;
         spelll.alphaGlowing = true;
         worldIn.spawnEntity(spelll);
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(AGE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(AGE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{AGE});
   }
}

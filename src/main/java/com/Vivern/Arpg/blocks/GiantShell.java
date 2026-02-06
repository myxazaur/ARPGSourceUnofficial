package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.aquatica.DimensionAquatica;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GiantShell extends Block implements IHasSubtypes {
   public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 7);
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.15, 0.0, 0.15, 0.85, 0.5, 0.85);
   public BlockRenderLayer layer = BlockRenderLayer.SOLID;
   public double offset = 0.4;

   public GiantShell(String name, BlockRenderLayer layer) {
      super(Material.WATER);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = 1.0F;
      this.blockResistance = 2.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
      this.layer = layer;
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.isInWater(world, pos)) {
         world.setBlockState(pos, state.withProperty(WET, false));
      } else {
         world.setBlockState(pos, state.withProperty(WET, true));
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public boolean isInWater(World worldIn, BlockPos pos) {
      IBlockState state1 = worldIn.getBlockState(pos.up());
      if (state1.getMaterial() == Material.WATER || state1.isOpaqueCube()) {
         IBlockState state2 = worldIn.getBlockState(pos.east());
         if (state2.getMaterial() == Material.WATER || state2.isOpaqueCube()) {
            IBlockState state3 = worldIn.getBlockState(pos.south());
            if (state3.getMaterial() == Material.WATER || state3.isOpaqueCube()) {
               IBlockState state4 = worldIn.getBlockState(pos.west());
               if (state4.getMaterial() == Material.WATER || state4.isOpaqueCube()) {
                  IBlockState state5 = worldIn.getBlockState(pos.north());
                  if (state5.getMaterial() == Material.WATER || state5.isOpaqueCube()) {
                     IBlockState state6 = worldIn.getBlockState(pos.down());
                     if (state6.getMaterial() == Material.WATER || state6.isOpaqueCube()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return this.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.offset(side.getOpposite())).isOpaqueCube();
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      if ((Boolean)state.getValue(WET) && this.isAroundWater(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState());
      }

      super.breakBlock(world, pos, state);
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XZ;
   }

   public GiantShell setOffset(double offsetxz) {
      this.offset = offsetxz;
      return this;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
      return new Vec3d(((float)(i >> 16 & 15L) / 15.0F - 0.5) * this.offset, 0.0, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * this.offset);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return STANDING_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return STANDING_AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return this.layer;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
      return this.getItemStack(state);
   }

   public ItemStack getItemStack(IBlockState state) {
      int variant = (Integer)state.getValue(TYPE);
      ItemStack stack = new ItemStack(this);
      NBTHelper.GiveNBTint(stack, variant, "type");
      NBTHelper.SetNBTint(stack, variant, "type");
      return stack;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      drops.add(this.getItemStack(state));
   }

   public IBlockState getStateFromMeta(int meta) {
      boolean wett = meta >= 8;
      return this.getDefaultState().withProperty(TYPE, wett ? meta - 8 : meta).withProperty(LEVEL, 0).withProperty(WET, wett);
   }

   public int getMetaFromState(IBlockState state) {
      int i = (Integer)state.getValue(TYPE);
      return state.getValue(WET) ? i + 8 : i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{TYPE, LEVEL, WET});
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      for (int i = 0; i < 8; i++) {
         ItemStack stack = new ItemStack(this);
         NBTHelper.GiveNBTint(stack, i, "type");
         NBTHelper.SetNBTint(stack, i, "type");
         items.add(stack);
      }
   }

   public IBlockState getStateForPlacement(
      World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand
   ) {
      return placer.getHeldItem(hand).getItem() == Item.getItemFromBlock(BlocksRegister.GIANTSHELL)
         ? this.getDefaultState()
            .withProperty(TYPE, NBTHelper.GetNBTint(placer.getHeldItem(hand), "type"))
            .withProperty(LEVEL, 0)
            .withProperty(WET, this.isInWater(world, pos))
         : this.getDefaultState();
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}

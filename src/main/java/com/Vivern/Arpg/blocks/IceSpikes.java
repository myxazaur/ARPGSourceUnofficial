package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponDamage;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IceSpikes extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool NOTPERMANENT = PropertyBool.create("notpermanent");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.7, 0.7);
   protected static final AxisAlignedBB M_NORTH_AABB = new AxisAlignedBB(0.25, 0.2, 0.6, 0.75, 0.9, 1.0);
   protected static final AxisAlignedBB M_SOUTH_AABB = new AxisAlignedBB(0.25, 0.2, 0.0, 0.75, 0.9, 0.4);
   protected static final AxisAlignedBB M_WEST_AABB = new AxisAlignedBB(0.6, 0.2, 0.25, 1.0, 0.9, 0.75);
   protected static final AxisAlignedBB M_EAST_AABB = new AxisAlignedBB(0.0, 0.2, 0.25, 0.4, 0.9, 0.75);

   public IceSpikes() {
      super(Material.GLASS);
      this.setRegistryName("ice_spikes");
      this.setTranslationKey("ice_spikes");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.GLASS);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setLightOpacity(0);
      this.setTickRandomly(true);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if (!worldIn.isRemote && entityIn instanceof EntityLivingBase) {
         ItemStack stack = ((EntityLivingBase)entityIn).getHeldItemMainhand();
         if (stack.getItem() == ItemsRegister.ICEBEAM && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0) {
            return;
         }

         EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
         entityIn.attackEntityFrom(new WeaponDamage(null, null, null, false, false, pos.offset(enumfacing.getOpposite()), WeaponDamage.pierce), 6.7F);
         entityIn.hurtResistantTime = 0;
         worldIn.setBlockToAir(pos);
         worldIn.playSound(
            (EntityPlayer)null,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            Sounds.ice_spikes,
            SoundCategory.AMBIENT,
            0.7F,
            0.9F + RANDOM.nextFloat() / 5.0F
         );
      }
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote && (Boolean)state.getValue(NOTPERMANENT) && worldIn.getLightFor(EnumSkyBlock.SKY, pos) > 0 && rand.nextFloat() < 0.4F) {
         worldIn.setBlockToAir(pos);
      }
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : EnumFacing.values()) {
         if (canPlaceAt(worldIn, pos, enumfacing)) {
            return true;
         }
      }

      return false;
   }

   public static boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
      BlockPos blockpos = pos.offset(facing.getOpposite());
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);
      if ((facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) && canPlaceOn(worldIn, blockpos)) {
         return true;
      } else {
         return facing != EnumFacing.UP && facing != EnumFacing.DOWN ? !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID : false;
      }
   }

   private static boolean canPlaceOn(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos).getBlock();
      return block.canPlaceTorchOnTop(worldIn.getBlockState(pos), worldIn, pos);
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      this.checkForDrop(worldIn, pos, state);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      this.onNeighborChangeInternal(worldIn, pos, state);
   }

   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
      if (!this.checkForDrop(worldIn, pos, state)) {
         return true;
      } else {
         EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
         Axis enumfacing$axis = enumfacing.getAxis();
         EnumFacing enumfacing1 = enumfacing.getOpposite();
         BlockPos blockpos = pos.offset(enumfacing1);
         boolean flag = false;
         if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
            flag = true;
         } else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, blockpos)) {
            flag = true;
         }

         if (flag) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
      if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING))) {
         return true;
      } else {
         if (worldIn.getBlockState(pos).getBlock() == this) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
         }

         return false;
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            return M_EAST_AABB;
         case WEST:
            return M_WEST_AABB;
         case SOUTH:
            return M_SOUTH_AABB;
         case NORTH:
            return M_NORTH_AABB;
         default:
            return STANDING_AABB;
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState();
      boolean permanent = meta >= 6;
      if (permanent) {
         meta -= 6;
      }

      switch (meta) {
         case 1:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
            break;
         case 2:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
            break;
         case 3:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
            break;
         case 5:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN);
            break;
         default:
            iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
      }

      return iblockstate.withProperty(NOTPERMANENT, permanent);
   }

   public int getMetaFromState(IBlockState state) {
      int permanent = state.getValue(NOTPERMANENT) ? 6 : 0;
      switch ((EnumFacing)state.getValue(FACING)) {
         case EAST:
            return 1 + permanent;
         case WEST:
            return 2 + permanent;
         case SOUTH:
            return 3 + permanent;
         case NORTH:
            return 4 + permanent;
         case DOWN:
            return 5 + permanent;
         default:
            return 0 + permanent;
      }
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING, NOTPERMANENT});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      if (canPlaceAt(worldIn, pos, facing)) {
         return this.getDefaultState().withProperty(FACING, facing);
      } else {
         for (EnumFacing enumfacing : Plane.HORIZONTAL) {
            if (canPlaceAt(worldIn, pos, enumfacing)) {
               return this.getDefaultState().withProperty(FACING, enumfacing);
            }
         }

         return this.getDefaultState();
      }
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}

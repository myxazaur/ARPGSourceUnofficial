package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.tileentity.TileToxicPortal;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicomaniaPortal extends Block {
   public static final PropertyEnum<EnumAxis> ROTATE = PropertyEnum.create("rotate", EnumAxis.class);

   public ToxicomaniaPortal() {
      super(Material.PORTAL);
      this.setRegistryName("toxicomania_portal");
      this.setTranslationKey("toxicomania_portal");
      this.blockResistance = 0.2F;
      this.setBlockUnbreakable();
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.35F);
      this.setLightOpacity(0);
      this.setDefaultState(this.getDefaultState().withProperty(ROTATE, EnumAxis.X));
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (DimensionsRegister.canPortalsBreak) {
         Block block1 = worldIn.getBlockState(pos.up()).getBlock();
         Block block2 = worldIn.getBlockState(pos.down()).getBlock();
         Block block3 = worldIn.getBlockState(pos.south()).getBlock();
         Block block4 = worldIn.getBlockState(pos.north()).getBlock();
         Block block5 = worldIn.getBlockState(pos.east()).getBlock();
         Block block6 = worldIn.getBlockState(pos.west()).getBlock();
         if (this.isBlockSupports(block1) && this.isBlockSupports(block2)) {
            if (this.isBlockSupports(block3) && this.isBlockSupports(block4)) {
               return;
            }

            if (this.isBlockSupports(block5) && this.isBlockSupports(block6)) {
               return;
            }
         }

         worldIn.setBlockToAir(pos);
      }
   }

   public boolean isBlockSupports(Block block) {
      return block == this || block == BlocksRegister.TOXICPORTALFRAME;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(50) == 0) {
         worldIn.playSound(
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            Sounds.portal_toxic,
            SoundCategory.BLOCKS,
            0.5F,
            rand.nextFloat() * 0.4F + 0.8F,
            false
         );
      }
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if (entityIn != null
         && entityIn instanceof EntityPlayer
         && !worldIn.isRemote
         && !entityIn.isRiding()
         && entityIn.timeUntilPortal <= 0
         && !entityIn.isBeingRidden()
         && entityIn.isNonBoss()
         && entityIn.getEntityBoundingBox().intersects(state.getBoundingBox(worldIn, pos).offset(pos))) {
         ((EntityPlayer)entityIn).timeUntilPortal = 100;
         DimensionsRegister.teleporterTOXICOMANIA.teleport((EntityPlayer)entityIn, pos);
      }
   }

   public Class<TileToxicPortal> getTileEntityClass() {
      return TileToxicPortal.class;
   }

   public TileToxicPortal getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileToxicPortal)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileToxicPortal createTileEntity(World world, IBlockState blockState) {
      return new TileToxicPortal();
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
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

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(ROTATE, EnumAxis.fromFacingAxis(placer.getHorizontalFacing().getOpposite().getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180
         ? state.withProperty(ROTATE, state.getValue(ROTATE) == EnumAxis.Z ? EnumAxis.X : EnumAxis.Z)
         : state;
   }
}

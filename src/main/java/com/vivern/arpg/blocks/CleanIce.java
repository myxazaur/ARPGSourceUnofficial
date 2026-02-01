package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CleanIce extends BlockBreakable {
   protected static final AxisAlignedBB CLEAN_ICE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

   public CleanIce() {
      super(Material.ICE, false);
      this.setRegistryName("clean_ice");
      this.setTranslationKey("clean_ice");
      this.blockHardness = 0.6F;
      this.blockResistance = 0.2F;
      this.setTickRandomly(true);
      this.setSoundType(SoundType.GLASS);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.slipperiness = 0.99F;
   }

   public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
      Block bl = world.getBlockState(pos.up()).getBlock();
      return bl != BlocksRegister.LOOSESNOW && bl != Blocks.SNOW_LAYER ? super.getLightOpacity(state, world, pos) : 255;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      worldIn.checkLight(pos);
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return CLEAN_ICE_AABB;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public void onLanded(World worldIn, Entity entityIn) {
      this.breakIce(worldIn, entityIn);
   }

   public void breakIce(World worldIn, Entity entityIn) {
      AxisAlignedBB aabb = entityIn.getEntityBoundingBox();
      BlockPos pos1 = new BlockPos(aabb.minX, entityIn.posY - 1.0, aabb.minZ);
      BlockPos pos2 = new BlockPos(aabb.minX, entityIn.posY - 1.0, aabb.maxZ);
      BlockPos pos3 = new BlockPos(aabb.maxX, entityIn.posY - 1.0, aabb.minZ);
      BlockPos pos4 = new BlockPos(aabb.maxX, entityIn.posY - 1.0, aabb.maxZ);
      this.breakIce(worldIn, pos1);
      this.breakIce(worldIn, pos2);
      this.breakIce(worldIn, pos3);
      this.breakIce(worldIn, pos4);
   }

   public void breakIce(World worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos).getBlock() == BlocksRegister.CLEANICE) {
         worldIn.setBlockToAir(pos);
         worldIn.playSound(
            null,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            SoundEvents.BLOCK_GLASS_BREAK,
            SoundCategory.BLOCKS,
            1.0F,
            0.85F + worldIn.rand.nextFloat() / 4.0F
         );
         if (worldIn instanceof WorldServer) {
            ((WorldServer)worldIn)
               .spawnParticle(
                  EnumParticleTypes.BLOCK_DUST,
                  pos.getX(),
                  pos.getY(),
                  pos.getZ(),
                  15,
                  -0.5,
                  -0.5,
                  -0.5,
                  0.08,
                  new int[]{Block.getStateId(BlocksRegister.CLEANICE.getDefaultState())}
               );
         }
      }
   }

   public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
      worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getStateFromMeta(9));
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - this.getDefaultState().getLightOpacity()) {
         worldIn.setBlockToAir(pos);
         worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getStateFromMeta(9));
      }
   }

   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      this.breakIce(worldIn, entityIn);
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
   }

   public EnumPushReaction getPushReaction(IBlockState state) {
      return EnumPushReaction.NORMAL;
   }
}

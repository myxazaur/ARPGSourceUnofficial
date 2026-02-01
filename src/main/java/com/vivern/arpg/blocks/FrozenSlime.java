package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.mobs.EverfrostMobsPack;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrozenSlime extends Block {
   public FrozenSlime() {
      super(Material.ICE);
      this.setRegistryName("frozen_slime");
      this.setTranslationKey("frozen_slime");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.1F;
      this.setTickRandomly(true);
      this.setSoundType(SoundType.GLASS);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.slipperiness = 0.99F;
      this.setLightOpacity(2);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      worldIn.checkLight(pos);
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public void onLanded(World worldIn, Entity entityIn) {
      this.breakIce(worldIn, entityIn);
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      if (!worldIn.isRemote) {
         int max = RANDOM.nextInt(fortune + 1) + 1;

         for (int i = 0; i < max; i++) {
            EverfrostMobsPack.IceCube mob = new EverfrostMobsPack.IceCube(worldIn);
            mob.setPosition(pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5);
            worldIn.spawnEntity(mob);
            mob.onInitialSpawn();
            mob.canDropLoot = true;
         }
      }

      super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
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
      if (worldIn.getBlockState(pos).getBlock() == this) {
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
         if (!worldIn.isRemote) {
            EverfrostMobsPack.IceCube mob = new EverfrostMobsPack.IceCube(worldIn);
            mob.setPosition(pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5);
            worldIn.spawnEntity(mob);
            mob.onInitialSpawn();
            mob.canDropLoot = true;
         }

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
      this.breakIce(worldIn, pos);
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
         this.breakIce(worldIn, pos);
         worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getStateFromMeta(9));
      }

      EntityPlayer player = worldIn.getNearestAttackablePlayer(pos, 4.0, 2.5);
      if (player != null) {
         this.breakIce(worldIn, pos);
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

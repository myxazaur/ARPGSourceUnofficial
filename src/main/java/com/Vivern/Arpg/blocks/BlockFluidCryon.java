package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidCryon extends BlockFluidClassic {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/frostlight.png");
   public static PotionEffect effectForAi = new PotionEffect(PotionEffects.FROSTBURN, 500, 1);

   public BlockFluidCryon() {
      super(FluidsRegister.CRYON, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_cryon_block");
      this.setRegistryName("fluid_cryon_block");
      this.setLightLevel(0.45F);
      this.setRenderLayer(BlockRenderLayer.CUTOUT);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (!worldIn.isRemote) {
         Weapons.setPotionIfEntityLB(entityIn, PotionEffects.FROSTBURN, 500, 1);
      }
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      if (rand.nextInt(50) == 0
         && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR
         && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
         double xx = d0 + rand.nextFloat();
         double yy = d1 + stateIn.getBoundingBox(worldIn, pos).maxY + 0.05;
         double zz = d2 + rand.nextFloat();
         GUNParticle spelll = new GUNParticle(
            res,
            0.22F + rand.nextFloat() / 20.0F,
            -0.0013F,
            20 + rand.nextInt(10),
            240,
            worldIn,
            xx,
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
         spelll.scaleTickAdding = -0.0037F;
         spelll.isPushedByLiquids = false;
         worldIn.spawnEntity(spelll);
      }

      if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
         Material material = worldIn.getBlockState(pos.down(2)).getMaterial();
         if (!material.blocksMovement() && !material.isLiquid()) {
            double xx = d0 + rand.nextFloat();
            double yy = d1 - 1.05;
            double zz = d2 + rand.nextFloat();
            GUNParticle spelll = new GUNParticle(
               res,
               0.13F + rand.nextFloat() / 20.0F,
               7.0E-4F,
               30 + rand.nextInt(10),
               240,
               worldIn,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               0.9F + rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               false,
               0,
               true,
               1.0F
            );
            spelll.scaleTickAdding = -0.0032F;
            worldIn.spawnEntity(spelll);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 13107400;
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   private void mergerFluids(BlockPos pos, World world) {
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
               if (frompos.getY() > pos.getY()) {
                  world.setBlockState(pos, BlocksRegister.FROZENSTONE.getDefaultState());
               } else {
                  world.setBlockState(frompos, BlocksRegister.FROZENSTONE.getDefaultState());
               }

               world.playSound(null, frompos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }

            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
               world.setBlockState(frompos, Blocks.ICE.getDefaultState());
               world.playSound(null, frompos, Sounds.fluid_freezing, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return entity != null && !entity.isPotionApplicable(effectForAi) ? PathNodeType.WATER : PathNodeType.LAVA;
   }
}

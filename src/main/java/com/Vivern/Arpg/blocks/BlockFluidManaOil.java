package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidManaOil extends BlockFluidClassic {
   public static final ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");

   public BlockFluidManaOil() {
      super(FluidsRegister.MANAOIL, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_mana_oil");
      this.setRegistryName("fluid_mana_oil");
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 10 == 0) {
         PotionEffect baff = new PotionEffect(PotionEffects.MANA_OIL, 160, 0);
         ((EntityLivingBase)entityIn).addPotionEffect(baff);
      }

      entityIn.motionX *= 0.9;
      entityIn.motionY *= 0.9;
      entityIn.motionZ *= 0.9;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      int i = (Integer)stateIn.getValue(LEVEL);
      if (i > 0 && i < 8 && rand.nextInt(64) == 0) {
         worldIn.playSound(
            d0 + 0.5, d1 + 0.5, d2 + 0.5, Sounds.slime_fluid, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false
         );
      }

      if (rand.nextInt(14) == 0
         && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR
         && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
         double xx = d0 + rand.nextFloat();
         double yy = d1 + stateIn.getBoundingBox(worldIn, pos).maxY;
         double zz = d2 + rand.nextFloat();
         int lt = 30 + rand.nextInt(30);
         float scl = 0.08F + rand.nextFloat() * 0.08F;
         GUNParticle spelll = new GUNParticle(
            star2,
            scl,
            -0.0015F,
            lt,
            240,
            worldIn,
            xx,
            yy,
            zz,
            (float)rand.nextGaussian() / 50.0F,
            0.0F,
            (float)rand.nextGaussian() / 50.0F,
            0.2F + rand.nextFloat() * 0.45F,
            0.75F * rand.nextFloat(),
            1.0F - rand.nextFloat() * 0.35F,
            true,
            rand.nextInt(360)
         );
         spelll.scaleTickAdding = -scl / lt;
         spelll.isPushedByLiquids = false;
         spelll.alphaGlowing = true;
         spelll.alpha = 0.0F;
         spelll.alphaTickAdding = 0.1F;
         worldIn.spawnEntity(spelll);
      }

      if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
         BlockPos down2 = pos.down(2);
         Material material = worldIn.getBlockState(down2).getMaterial();
         if (!material.blocksMovement() && !material.isLiquid()) {
            int light = Math.max(worldIn.getLightFor(EnumSkyBlock.BLOCK, down2), worldIn.getLightFor(EnumSkyBlock.SKY, down2)) * 15;
            double xx = d0 + rand.nextFloat();
            double yy = d1 - 0.95;
            double zz = d2 + rand.nextFloat();
            int lt = 30 + rand.nextInt(30);
            float scl = 0.08F + rand.nextFloat() * 0.08F;
            GUNParticle spelll = new GUNParticle(
               star2,
               scl,
               8.0E-4F,
               lt,
               240,
               worldIn,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               0.2F + rand.nextFloat() * 0.45F,
               0.75F * rand.nextFloat(),
               1.0F - rand.nextFloat() * 0.35F,
               true,
               rand.nextInt(360)
            );
            spelll.scaleTickAdding = -scl / lt;
            spelll.isPushedByLiquids = false;
            spelll.alphaGlowing = true;
            spelll.alpha = 0.0F;
            spelll.alphaTickAdding = 0.1F;
            worldIn.spawnEntity(spelll);
         }
      }
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
      int i = (Integer)world.getBlockState(pos).getValue(LEVEL);
      boolean full = i == 0;
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
               if (full) {
                  world.setBlockState(pos, Blocks.SLIME_BLOCK.getDefaultState());
               } else {
                  world.setBlockState(pos, BlocksRegister.SUMMONEDHELLSTONE.getDefaultState());
               }

               world.playSound(null, frompos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }

            if (facing == EnumFacing.UP && (block == Blocks.WATER || block == Blocks.FLOWING_WATER)) {
               if (full) {
                  world.setBlockState(pos, Blocks.SLIME_BLOCK.getDefaultState());
               } else {
                  world.setBlockState(pos, BlocksRegister.SLIMEGLOB.getDefaultState());
               }

               world.playSound(null, frompos, Sounds.bubble_fish, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }
}

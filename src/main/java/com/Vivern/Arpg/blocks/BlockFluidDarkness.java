//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidDarkness extends BlockFluidClassic {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/lava_drop.png");

   public BlockFluidDarkness() {
      super(FluidsRegister.DARKNESS, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_darkness_block");
      this.setRegistryName("fluid_darkness_block");
      this.setRenderLayer(BlockRenderLayer.CUTOUT);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 20 == 0 && !worldIn.isRemote) {
         if (RANDOM.nextInt(10) == 0) {
            worldIn.playSound(null, pos, Sounds.liquid_darkness, SoundCategory.BLOCKS, RANDOM.nextFloat() * 0.25F + 0.75F, RANDOM.nextFloat() + 0.5F);
         }

         float health = ((EntityLivingBase)entityIn).getHealth();
         if (health > 1.0F) {
            ((EntityLivingBase)entityIn).setHealth(health - 0.4F);
         } else {
            ((EntityLivingBase)entityIn).attackEntityFrom(DamageSource.OUT_OF_WORLD, 5.0F);
         }
      }

      entityIn.motionY = -0.05;
      entityIn.motionZ *= 0.95;
      entityIn.motionX *= 0.95;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      if (rand.nextInt(80) == 0
         && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR
         && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
         double xx = d0 + rand.nextFloat();
         double yy = d1 + stateIn.getBoundingBox(worldIn, pos).maxY + 0.05;
         double zz = d2 + rand.nextFloat();
         GUNParticle spelll = new GUNParticle(
            res, 0.03F + rand.nextFloat() / 15.0F, -0.0014F, 30 + rand.nextInt(30), -1, worldIn, xx, yy, zz, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 0
         );
         spelll.isPushedByLiquids = false;
         worldIn.spawnEntity(spelll);
      }

      if (rand.nextInt(30) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
         BlockPos down2 = pos.down(2);
         Material material = worldIn.getBlockState(down2).getMaterial();
         if (!material.blocksMovement() && !material.isLiquid()) {
            double xx = d0 + rand.nextFloat();
            double yy = d1 - 1.05;
            double zz = d2 + rand.nextFloat();
            GUNParticle spelll = new GUNParticle(
               res, 0.015F + rand.nextFloat() / 20.0F, 5.0E-4F, 30 + rand.nextInt(30), -1, worldIn, xx, yy, zz, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 0
            );
            spelll.scaleTickAdding = 0.0025F;
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

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
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
                  world.setBlockState(pos, BlocksRegister.SLIMEGLOB.getDefaultState());
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

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return PathNodeType.LAVA;
   }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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

public class BlockFluidSlime extends BlockFluidClassic {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/acid_splash3.png");

   public BlockFluidSlime() {
      super(FluidsRegister.SLIME, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_slime_block");
      this.setRegistryName("fluid_slime_block");
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 10 == 0) {
         if (((EntityLivingBase)entityIn).isPotionActive(PotionEffects.FIBER_BANDAGING) && RANDOM.nextFloat() < 0.88F) {
            return;
         }

         PotionEffect baff = new PotionEffect(PotionEffects.SLIME, 110, 1);
         ((EntityLivingBase)entityIn).addPotionEffect(baff);
      }
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

      if (rand.nextInt(130) == 0
         && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR
         && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
         double xx = d0 + rand.nextFloat();
         double yy = d1 + stateIn.getBoundingBox(worldIn, pos).maxY + 0.05;
         double zz = d2 + rand.nextFloat();
         GUNParticle spelll = new GUNParticle(
            res,
            0.11F + rand.nextFloat() / 10.0F,
            -0.0013F,
            30 + rand.nextInt(10),
            -1,
            worldIn,
            xx,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            0.87F + rand.nextFloat() / 10.0F,
            1.0F,
            true,
            0
         );
         spelll.scaleTickAdding = -0.002F;
         spelll.isPushedByLiquids = false;
         worldIn.spawnEntity(spelll);
         worldIn.playSound(
            xx, yy, zz, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false
         );
      }

      if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
         BlockPos down2 = pos.down(2);
         Material material = worldIn.getBlockState(down2).getMaterial();
         if (!material.blocksMovement() && !material.isLiquid()) {
            int light = Math.max(worldIn.getLightFor(EnumSkyBlock.BLOCK, down2), worldIn.getLightFor(EnumSkyBlock.SKY, down2)) * 15;
            double xx = d0 + rand.nextFloat();
            double yy = d1 - 1.05;
            double zz = d2 + rand.nextFloat();
            GUNParticle spelll = new GUNParticle(
               res,
               0.09F + rand.nextFloat() / 20.0F,
               3.0E-4F,
               30 + rand.nextInt(10),
               light,
               worldIn,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               0.9F + rand.nextFloat() / 10.0F,
               0.87F + rand.nextFloat() / 10.0F,
               1.0F,
               true,
               0
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
}

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlimeGlob extends Block {
   static ResourceLocation slime = new ResourceLocation("arpg:textures/slimesplash.png");
   static ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");

   public SlimeGlob() {
      super(Material.CLAY);
      this.setRegistryName("slime_glob");
      this.setTranslationKey("slime_glob");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      worldIn.setBlockToAir(pos);
      worldIn.playSound(
         null,
         pos.getX(),
         pos.getY(),
         pos.getZ(),
         Sounds.pop,
         SoundCategory.BLOCKS,
         1.0F,
         0.85F + worldIn.rand.nextFloat() / 4.0F
      );
      if (entityIn instanceof EntityLivingBase) {
         PotionEffect baff = new PotionEffect(PotionEffects.SLIME, 100);
         ((EntityLivingBase)entityIn).addPotionEffect(baff);
      }

      if (worldIn.isRemote) {
         this.effect(worldIn, pos);
      }
   }

   @SideOnly(Side.CLIENT)
   public void effect(World world, BlockPos pos) {
      for (int ss = 0; ss < 5; ss++) {
         Entity bubble = new GUNParticle(
            texturbubble,
            0.08F + RANDOM.nextFloat() / 20.0F,
            0.03F,
            50 + RANDOM.nextInt(20),
            200,
            world,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            (float)RANDOM.nextGaussian() / 9.0F,
            (float)RANDOM.nextGaussian() / 9.0F + 0.15F,
            (float)RANDOM.nextGaussian() / 9.0F,
            0.5F,
            0.8F + (float)RANDOM.nextGaussian() / 5.0F,
            1.0F,
            false,
            RANDOM.nextInt(180),
            true,
            1.3F
         );
         world.spawnEntity(bubble);
      }

      for (int ss = 0; ss < 4; ss++) {
         GUNParticle bubble = new GUNParticle(
            slime,
            0.5F + RANDOM.nextFloat() / 3.0F,
            -0.001F,
            10 + RANDOM.nextInt(10),
            -1,
            world,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            (float)RANDOM.nextGaussian() / 19.0F,
            (float)RANDOM.nextGaussian() / 22.0F + 0.05F,
            (float)RANDOM.nextGaussian() / 19.0F,
            0.9F + (float)RANDOM.nextGaussian() / 10.0F,
            0.9F + (float)RANDOM.nextGaussian() / 10.0F,
            0.9F + (float)RANDOM.nextGaussian() / 10.0F,
            true,
            RANDOM.nextInt(180)
         );
         bubble.alphaTickAdding = -0.04F;
         bubble.alphaGlowing = true;
         world.spawnEntity(bubble);
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}

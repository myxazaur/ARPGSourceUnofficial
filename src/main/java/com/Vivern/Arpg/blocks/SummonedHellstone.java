//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SummonedHellstone extends Block {
   public static int EXPIRE_MAX = 4;
   public static final PropertyInteger EXPIRE = PropertyInteger.create("expire", 0, EXPIRE_MAX);

   public SummonedHellstone() {
      super(Material.ROCK);
      this.setRegistryName("summoned_hellstone");
      this.setTranslationKey("summoned_hellstone");
      this.blockHardness = 1.0F;
      this.blockResistance = 1.0F;
      this.setSoundType(SoundTypeShards.STONE);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.setLightLevel(0.3F);
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
      double xRand = (float)(i >> 16 & 15L) / 15.0F - 0.5;
      double yRand = (float)(i >> 20 & 15L) / 15.0F - 1.0;
      double zRand = (float)(i >> 24 & 15L) / 15.0F - 0.5;
      return new Vec3d(xRand * 0.25, yRand * 0.15, zRand * 0.25);
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(world, pos, state, rand);
      if (!world.isRemote) {
         int expireInt = (Integer)state.getValue(EXPIRE);
         if (expireInt < EXPIRE_MAX) {
            world.setBlockState(pos, state.withProperty(EXPIRE, expireInt + 1));
         } else {
            world.setBlockToAir(pos);
         }
      }
   }

   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
      super.onEntityWalk(worldIn, pos, entityIn);
      IBlockState state = worldIn.getBlockState(pos);
      int expireInt = (Integer)state.getValue(EXPIRE);
      if (expireInt > 0) {
         worldIn.setBlockState(pos, state.withProperty(EXPIRE, 0));
      }
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(EXPIRE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(EXPIRE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{EXPIRE});
   }
}

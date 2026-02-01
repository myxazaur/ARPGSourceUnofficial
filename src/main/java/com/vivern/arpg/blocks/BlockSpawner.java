package com.vivern.arpg.blocks;

import com.vivern.arpg.entity.EntityCubicParticle;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ParticleFastSummon;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpawner extends BlockBlock implements IBlockHardBreak {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/frostlight.png");
   public static final ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
   public static final ResourceLocation magic_effect_3 = new ResourceLocation("arpg:textures/magic_effect_3.png");
   public static final ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
   public static final ResourceLocation clouds4 = new ResourceLocation("arpg:textures/clouds4.png");
   public int xpDropMin;
   public int xpDropAdd;
   public BlocksRegister.Hardres hardres;
   public ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 5.0, 0.12), new Vec3d(5.0, 10.0, -0.12)}
   );

   public BlockSpawner(Material mater, String name, BlocksRegister.Hardres hardres, int xpDropMin, int xpDropMax) {
      super(mater, name, hardres.HARDNESS, hardres.RESISTANCE);
      this.xpDropMin = xpDropMin;
      this.xpDropAdd = xpDropMax - xpDropMin + 1;
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.hardres = hardres;
      this.setHarvest("pickaxe", hardres.LVL);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return this.hardres;
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      int i = 0;
      if (random.nextFloat() < 0.5F) {
         i++;
         if (random.nextFloat() < 0.2F) {
            i++;
         }
      }

      return i;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      if (this == BlocksRegister.MOBSPAWNERFROZEN) {
         return ItemsRegister.FROZENSPAWNERPIECE;
      } else if (this == BlocksRegister.MOBSPAWNERRUSTED) {
         return ItemsRegister.RUSTEDSPAWNERPIECE;
      } else if (this == BlocksRegister.MOBSPAWNERANCIENT) {
         return ItemsRegister.ANCIENTSPAWNERPIECE;
      } else if (this == BlocksRegister.MOBSPAWNERAQUATIC) {
         return ItemsRegister.AQUATICSPAWNERPIECE;
      } else {
         return this == BlocksRegister.MOBSPAWNERSTORM ? ItemsRegister.STORMSPAWNERPIECE : Items.AIR;
      }
   }

   public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
      return false;
   }

   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return this.xpDropMin + RANDOM.nextInt(this.xpDropAdd + fortune);
   }

   public Class<TileMonsterSpawner> getTileEntityClass() {
      return TileMonsterSpawner.class;
   }

   public TileMonsterSpawner getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileMonsterSpawner)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileMonsterSpawner createTileEntity(World world, IBlockState blockState) {
      return new TileMonsterSpawner();
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (this == BlocksRegister.MOBSPAWNERFROZEN) {
         double xx = pos.getX() + rand.nextDouble();
         double yy = pos.getY() + rand.nextDouble();
         double zz = pos.getZ() + rand.nextDouble();
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

      if (this == BlocksRegister.MOBSPAWNERRUSTED) {
         double xx = pos.getX() + 0.2 + rand.nextDouble() * 0.6;
         double yy = pos.getY() + 0.2 + rand.nextDouble() * 0.6;
         double zz = pos.getZ() + 0.2 + rand.nextDouble() * 0.6;
         worldIn.spawnParticle(
            EnumParticleTypes.SMOKE_NORMAL, xx, yy, zz, rand.nextGaussian() / 26.0, rand.nextGaussian() / 26.0, rand.nextGaussian() / 26.0, new int[0]
         );
         if (rand.nextFloat() < 0.5) {
            GUNParticle spelll = new GUNParticle(
               circle,
               0.05F,
               0.0F,
               10,
               240,
               worldIn,
               pos.getX() + 0.5,
               pos.getY(),
               pos.getZ() + 0.5,
               0.0F,
               0.1F,
               0.0F,
               0.2F + rand.nextFloat() / 10.0F,
               0.7F,
               1.0F,
               true,
               0
            );
            spelll.isPushedByLiquids = false;
            spelll.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
            spelll.alphaGlowing = true;
            spelll.tracker = this.ssh;
            spelll.randomDeath = false;
            worldIn.spawnEntity(spelll);
         }
      }

      if (this == BlocksRegister.MOBSPAWNERANCIENT) {
         double xx = pos.getX() + 0.5 + rand.nextGaussian() * 0.05;
         double yy = pos.getY() + 0.5 + rand.nextGaussian() * 0.05;
         double zz = pos.getZ() + 0.5 + rand.nextGaussian() * 0.05;
         int lt = 30 + rand.nextInt(15);
         GUNParticle spelll = new GUNParticle(
            magic_effect_3,
            0.3F + rand.nextFloat() * 0.25F,
            -8.0E-4F - rand.nextFloat() * 6.0E-4F,
            lt,
            240,
            worldIn,
            xx,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() / 10.0F,
            0.9F + rand.nextFloat() / 10.0F,
            0.9F + rand.nextFloat() / 10.0F,
            true,
            0
         );
         spelll.scaleTickAdding = -0.006F;
         spelll.isPushedByLiquids = false;
         spelll.alphaGlowing = true;
         spelll.alphaTickAdding = -1.0F / lt;
         worldIn.spawnEntity(spelll);
      }

      if (this == BlocksRegister.MOBSPAWNERAQUATIC) {
         double d0 = pos.getX() + 0.5;
         double d1 = pos.getY() + 0.5;
         double d2 = pos.getZ() + 0.5;
         int livetime = 70;
         float scale = 0.4F + rand.nextFloat() / 10.0F;
         float scaleTickAdding = scale / livetime;
         GUNParticle spelll = new GUNParticle(
            mana_flow,
            0.25F,
            0.0F,
            livetime,
            210,
            worldIn,
            d0,
            d1,
            d2,
            0.0F,
            0.0F,
            0.0F,
            0.5F + rand.nextFloat() * 0.1F,
            1.0F,
            0.85F + rand.nextFloat() * 0.15F,
            true,
            0
         );
         spelll.alpha = 1.0F;
         spelll.alphaTickAdding = -0.0125F;
         spelll.scaleTickAdding = scaleTickAdding;
         spelll.alphaGlowing = true;
         spelll.isPushedByLiquids = false;
         worldIn.spawnEntity(spelll);
         if (rand.nextFloat() < 0.5) {
            double f0 = rand.nextGaussian() * 0.15;
            double f1 = rand.nextGaussian() * 0.15;
            double f2 = rand.nextGaussian() * 0.15;
            f0 += f0 > 0.0 ? 0.51 : -0.51;
            f1 += f1 > 0.0 ? 1.01 : 0.0;
            f2 += f2 > 0.0 ? 0.51 : -0.51;
            worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + f0, pos.getY() + f1, d2 + f2, 0.0, 0.0, 0.0, new int[0]);
         }
      }

      if (this == BlocksRegister.MOBSPAWNERSTORM) {
         double d0 = pos.getX() + 0.5;
         double d1 = pos.getY() + 0.5;
         double d2 = pos.getZ() + 0.5;
         EntityCubicParticle spel = new EntityCubicParticle(
            clouds4,
            0.032F,
            0.0F,
            40 + rand.nextInt(10),
            240,
            worldIn,
            d0,
            d1,
            d2,
            0.0F,
            0.0F,
            0.0F,
            0.9F + rand.nextFloat() * 0.1F,
            0.9F + rand.nextFloat() * 0.1F,
            1.0F,
            true,
            rand.nextFloat(),
            rand.nextFloat(),
            rand.nextFloat(),
            0.15F,
            true,
            0.0F
         );
         spel.alphaGlowing = true;
         worldIn.spawnEntity(spel);
         EnumFacing face = EnumFacing.HORIZONTALS[rand.nextInt(4)];
         EnumFacing face2 = face.rotateY();
         double offset = rand.nextFloat() - 0.5F;
         Vec3d pos1 = new Vec3d(
            pos.getX() + 0.5 + face.getXOffset() * 0.5 + face2.getXOffset() * offset,
            pos.getY() + rand.nextFloat(),
            pos.getZ() + 0.5 + face.getZOffset() * 0.5 + face2.getZOffset() * offset
         );
         Vec3d pos2 = new Vec3d(
            pos.getX() + 0.5 + face.getXOffset() * 2.5 + face2.getXOffset() * offset * 3.0 + rand.nextGaussian(),
            pos.getY() - 2,
            pos.getZ() + 0.5 + face.getZOffset() * 2.5 + face2.getZOffset() * offset * 3.0 + rand.nextGaussian()
         );
         RayTraceResult result = worldIn.rayTraceBlocks(pos1, pos2, false, true, false);
         Vec3d pos3;
         if (result != null && result.hitVec != null) {
            pos3 = result.hitVec;
         } else {
            pos3 = pos2;
         }

         ParticleFastSummon.coloredLightning(
            worldIn,
            0.03F,
            4,
            240,
            0.9F + rand.nextFloat() * 0.1F,
            0.9F + rand.nextFloat() * 0.1F,
            1.0F,
            5,
            pos1,
            pos3,
            pos1.squareDistanceTo(pos3) > 2.0 ? 0.5F : 0.25F,
            0.1F,
            ParticleFastSummon.rand
         );
      }
   }
}

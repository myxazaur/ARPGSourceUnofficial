//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.tileentity.TileAlchemicVaporizer;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public abstract class VaporizeAction {
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public int id;
   public FluidStack fluid;
   public float manaOutputPerTick;
   public int ticksInAction;

   public VaporizeAction(FluidStack fluid, int ticksInAction, float manaOutput) {
      this.fluid = fluid;
      this.ticksInAction = ticksInAction;
      this.manaOutputPerTick = manaOutput / ticksInAction;
   }

   public abstract void vaporizeTick(World var1, BlockPos var2, TileAlchemicVaporizer var3, Random var4);

   public abstract float getCatalystPower(ItemStack var1);

   public static class VaporizeActionManaOil extends VaporizeAction {
      public VaporizeActionManaOil() {
         super(new FluidStack(FluidsRegister.MANAOIL, 100), 200, 25.0F);
      }

      @Override
      public void vaporizeTick(World world, BlockPos vaporizerPos, TileAlchemicVaporizer tile, Random rand) {
         if (!world.isRemote) {
            tile.getManaBuffer().addMana(this.manaOutputPerTick);
            if (rand.nextFloat() < 0.025F) {
               int[] samples = new int[]{5 + rand.nextInt(14)};
               this.recursiveGenerate(world, vaporizerPos, EnumFacing.random(rand), rand, samples);
            }
         } else {
            if (rand.nextFloat() < 0.2F) {
               int absPos = rand.nextInt(2);
               AxisAlignedBB aabb = world.getBlockState(vaporizerPos).getCollisionBoundingBox(world, vaporizerPos);
               double x = absPos == 0
                  ? (rand.nextFloat() < 0.5 ? aabb.minX - 0.05 : aabb.maxX + 0.05)
                  : aabb.minX + (aabb.maxX - aabb.minX) * rand.nextDouble();
               double y = aabb.minY + (aabb.maxY - aabb.minY) * rand.nextDouble();
               double z = absPos == 1
                  ? (rand.nextFloat() < 0.5 ? aabb.minZ - 0.05 : aabb.maxZ + 0.05)
                  : aabb.minZ + (aabb.maxZ - aabb.minZ) * rand.nextDouble();
               int lt = 50 + rand.nextInt(10) + (rand.nextFloat() < 0.2F ? rand.nextInt(70) : 0);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  0.1F,
                  -0.002F,
                  lt,
                  240,
                  world,
                  vaporizerPos.getX() + x,
                  vaporizerPos.getY() + y,
                  vaporizerPos.getZ() + z,
                  (float)(rand.nextGaussian() / 36.0 + (x - 0.5) * 0.07),
                  (float)(rand.nextGaussian() / 36.0 + (y - 0.5) * 0.01),
                  (float)(rand.nextGaussian() / 36.0 + (z - 0.5) * 0.07),
                  0.0F,
                  0.0F,
                  0.0F,
                  true,
                  rand.nextInt(360),
                  true,
                  1.0F
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -1.0F / lt;
               bigsmoke.scaleTickAdding = 0.02F + rand.nextFloat() * 0.03F;
               bigsmoke.tracker = new ParticleTracker.TrackerGradient(
                  new Vec3d(0.65F, 0.2F - rand.nextFloat() * 0.2F, 0.45F), new Vec3d(0.0, 0.8F - rand.nextFloat() * 0.55F, 1.0), 0, lt
               );
               world.spawnEntity(bigsmoke);
            }

            if (rand.nextFloat() < 0.15F) {
               world.spawnParticle(
                  EnumParticleTypes.SMOKE_NORMAL,
                  vaporizerPos.getX() + 0.4 + rand.nextFloat() * 0.2F,
                  vaporizerPos.getY() + 1.0625,
                  vaporizerPos.getZ() + 0.4 + rand.nextFloat() * 0.2F,
                  (rand.nextFloat() - 0.5F) * 0.01,
                  0.02,
                  (rand.nextFloat() - 0.5F) * 0.01,
                  new int[0]
               );
            }
         }
      }

      public void recursiveGenerate(World world, BlockPos prevPos, EnumFacing prevFacingOpp, Random rand, int[] samples) {
         int ni = rand.nextInt(5);
         if (ni == prevFacingOpp.getIndex()) {
            ni = 5;
         }

         EnumFacing newFacing = EnumFacing.byIndex(ni);
         BlockPos newPos = prevPos.offset(newFacing);
         if (rand.nextInt(MathHelper.clamp(samples[0], 1, 10)) == 0) {
            IBlockState stateOn = world.getBlockState(newPos);
            if (stateOn.getMaterial() == Material.AIR) {
               if (rand.nextFloat() < 0.5F
                  && (
                     world.isSideSolid(newPos.down(), EnumFacing.UP)
                        || world.getBlockState(newPos.down()).getBlock() == BlocksRegister.MANAOILSPELEOTHEM
                  )) {
                  world.setBlockState(
                     newPos, BlocksRegister.MANAOILSPELEOTHEM.getStateForPlacement(world, newPos, EnumFacing.UP, 0.0F, 0.0F, 0.0F, 0, null, EnumHand.MAIN_HAND)
                  );
                  return;
               }

               if (world.isSideSolid(newPos.up(), EnumFacing.DOWN)
                  || world.getBlockState(newPos.up()).getBlock() == BlocksRegister.MANAOILSPELEOTHEM) {
                  world.setBlockState(
                     newPos,
                     BlocksRegister.MANAOILSPELEOTHEM.getStateForPlacement(world, newPos, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F, 0, null, EnumHand.MAIN_HAND)
                  );
                  return;
               }
            } else if (stateOn.isFullBlock()) {
               return;
            }
         }

         samples[0]--;
         if (samples[0] > 0) {
            this.recursiveGenerate(world, newPos, newFacing.getOpposite(), rand, samples);
         }
      }

      @Override
      public float getCatalystPower(ItemStack itemstack) {
         return TileEntityFurnace.getItemBurnTime(itemstack) / 200.0F;
      }
   }
}

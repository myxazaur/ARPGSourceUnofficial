//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidNaturalGas extends BlockFluidClassic {
   public static final ResourceLocation res = new ResourceLocation("arpg:textures/largecloud.png");
   public static ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 40.0, 0.025), new Vec3d(40.0, 80.0, -0.025)}, null
   );

   public BlockFluidNaturalGas() {
      super(FluidsRegister.NATURALGAS, Material.GROUND);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_natural_gas_block");
      this.setRegistryName("fluid_natural_gas_block");
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      applyNaturalGasPotions(entityIn);
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      double d0 = pos.getX();
      double d1 = pos.getY();
      double d2 = pos.getZ();
      if (rand.nextInt(50) == 0) {
         double xx = d0 + rand.nextFloat();
         double yy = d1 + rand.nextFloat();
         double zz = d2 + rand.nextFloat();
         int lt = 55 + rand.nextInt(25);
         GUNParticle spelll = new GUNParticle(
            res,
            0.3F + rand.nextFloat() / 4.0F,
            -4.0E-4F,
            lt,
            100,
            worldIn,
            xx,
            yy,
            zz,
            0.0F,
            0.0F,
            0.0F,
            0.8F + rand.nextFloat() / 10.0F,
            1.0F,
            1.0F,
            true,
            rand.nextInt(360)
         );
         spelll.alpha = 0.0F;
         spelll.scaleTickAdding = 0.001F;
         spelll.isPushedByLiquids = false;
         spelll.tracker = ssh;
         worldIn.spawnEntity(spelll);
      }
   }

   public static void applyNaturalGasPotions(Entity entityIn) {
      if (!entityIn.world.isRemote && entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 25 == 0) {
         EntityLivingBase base = (EntityLivingBase)entityIn;
         if (base instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer)base, ItemsRegister.GASMASK) >= 0) {
            return;
         }

         PotionEffect baff = new PotionEffect(MobEffects.NAUSEA, 300);
         base.addPotionEffect(baff);
         if (base.getHealth() <= 1.0F) {
            base.attackEntityFrom(DamageSource.DROWN, 1.0F);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d vec, float partialTicks) {
      float filled = this.getFilledPercentage(world, pos);
      boolean is = filled < 0.0F ? vec.y > pos.getY() + filled + 1.0F : vec.y < pos.getY() + filled;
      if (!is) {
         BlockPos otherPos = pos.down(this.densityDir);
         IBlockState otherState = world.getBlockState(otherPos);
         return otherState.getBlock().getFogColor(world, otherPos, otherState, entity, vec, partialTicks);
      } else {
         return new Vec3d(0.6, 0.68, 0.82);
      }
   }

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return PathNodeType.DAMAGE_OTHER;
   }
}

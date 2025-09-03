//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.Random;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DoleriteColumn extends BlockRotatedPillar implements IBlockHardBreak {
   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);
   public static ResourceLocation res1 = new ResourceLocation("arpg:textures/dolerite_rune1.png");
   public static ResourceLocation res2 = new ResourceLocation("arpg:textures/dolerite_rune2.png");
   public static ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 40.0, 0.025), new Vec3d(80.0, 120.0, -0.025)}, null
   );

   public DoleriteColumn() {
      super(Material.ROCK);
      this.setRegistryName("dolerite_column");
      this.setTranslationKey("dolerite_column");
      this.setHardness(20.0F);
      this.setResistance(20.0F);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
      this.setSoundType(SoundType.STONE);
      this.setHarvestLevel("pickaxe", 0);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_DOLERITE_BRICKS;
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.07) {
         EnumFacing face = EnumFacing.VALUES[rand.nextInt(6)];
         EnumAxis axis = (EnumAxis)stateIn.getValue(LOG_AXIS);
         if (axis == EnumAxis.Y && face.getAxis() != Axis.Y) {
            double x = pos.getX() + 0.5 + face.getXOffset() * 0.51;
            double y = pos.getY() + 0.5 + face.getYOffset() * 0.51;
            double z = pos.getZ() + 0.5 + face.getZOffset() * 0.51;
            GUNParticle spelll = new GUNParticle(
               rand.nextFloat() < 0.5 ? res1 : res2, 0.1875F, 0.0F, 120, 240, worldIn, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, 0
            );
            spelll.alpha = 0.0F;
            spelll.tracker = ssh;
            spelll.alphaGlowing = true;
            spelll.rotationPitchYaw = new Vec2f(0.0F, face.getHorizontalAngle() + 180.0F);
            worldIn.spawnEntity(spelll);
         }

         if (axis == EnumAxis.X && face.getAxis() != Axis.X) {
            double x = pos.getX() + 0.5 + face.getXOffset() * 0.51;
            double y = pos.getY() + 0.5 + face.getYOffset() * 0.51;
            double z = pos.getZ() + 0.5 + face.getZOffset() * 0.51;
            GUNParticle spelll = new GUNParticle(
               rand.nextFloat() < 0.5 ? res1 : res2, 0.1875F, 0.0F, 120, 240, worldIn, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, -90
            );
            spelll.alpha = 0.0F;
            spelll.tracker = ssh;
            spelll.alphaGlowing = true;
            spelll.rotationPitchYaw = new Vec2f(face.getAxis() == Axis.Y ? 90.0F : 0.0F, 0.0F);
            worldIn.spawnEntity(spelll);
         }

         if (axis == EnumAxis.Z && face.getAxis() != Axis.Z) {
            double x = pos.getX() + 0.5 + face.getXOffset() * 0.51;
            double y = pos.getY() + 0.5 + face.getYOffset() * 0.51;
            double z = pos.getZ() + 0.5 + face.getZOffset() * 0.51;
            GUNParticle spelll = new GUNParticle(
               rand.nextFloat() < 0.5 ? res1 : res2,
               0.1875F,
               0.0F,
               120,
               240,
               worldIn,
               x,
               y,
               z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               face.getAxis() == Axis.X ? 90 : 180
            );
            spelll.alpha = 0.0F;
            spelll.tracker = ssh;
            spelll.alphaGlowing = true;
            spelll.rotationPitchYaw = new Vec2f(face.getAxis() == Axis.Y ? 90.0F : 0.0F, face.getAxis() == Axis.X ? 90.0F : 0.0F);
            worldIn.spawnEntity(spelll);
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      IBlockState iblockstate = this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y);
      switch (meta) {
         case 0:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
            break;
         case 4:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
            break;
         case 8:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
            break;
         default:
            iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
      }

      return iblockstate;
   }

   public int getMetaFromState(IBlockState state) {
      int i = 0;
      switch ((EnumAxis)state.getValue(LOG_AXIS)) {
         case X:
            i = 4;
            break;
         case Z:
            i = 8;
            break;
         case NONE:
            i = 12;
      }

      return i;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.OBSIDIAN;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getStateFromMeta(meta).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      switch (rot) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch ((EnumAxis)state.getValue(LOG_AXIS)) {
               case X:
                  return state.withProperty(LOG_AXIS, EnumAxis.Z);
               case Z:
                  return state.withProperty(LOG_AXIS, EnumAxis.X);
               default:
                  return state;
            }
         default:
            return state;
      }
   }
}

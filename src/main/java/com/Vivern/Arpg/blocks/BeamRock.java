//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BeamRock extends BlockRotatedPillar implements IBlockHardBreak {
   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);

   public BeamRock() {
      super(Material.ROCK);
      this.setRegistryName("beamrock");
      this.setTranslationKey("beamrock");
      this.blockHardness = BlocksRegister.HR_STORMBRASS_ROCKS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_STORMBRASS_ROCKS.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_STORMBRASS_ROCKS.LVL);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
      this.setSoundType(SoundType.STONE);
      this.setLightLevel(0.25F);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_STORMBRASS_ROCKS;
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
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

   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      return super.removedByPlayer(state, world, pos, player, willHarvest);
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

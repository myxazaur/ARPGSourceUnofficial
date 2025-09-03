//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TileDungeonPortal;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DungeonPortal extends Block {
   public DungeonPortal() {
      super(Material.PORTAL);
      this.setRegistryName("dungeon_portal");
      this.setTranslationKey("dungeon_portal");
      this.blockResistance = 0.2F;
      this.setBlockUnbreakable();
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightOpacity(0);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (DimensionsRegister.canPortalsBreak) {
         Block block3 = worldIn.getBlockState(pos.south()).getBlock();
         Block block4 = worldIn.getBlockState(pos.north()).getBlock();
         Block block5 = worldIn.getBlockState(pos.east()).getBlock();
         Block block6 = worldIn.getBlockState(pos.west()).getBlock();
         if (this.isBlockSupports(block3) && this.isBlockSupports(block4) && this.isBlockSupports(block5) && this.isBlockSupports(block6)) {
            return;
         }

         worldIn.setBlockToAir(pos);
      }
   }

   public boolean isBlockSupports(Block block) {
      return block == this || block == BlocksRegister.DUNGEONPORTALFRAME;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(150) == 0) {
         worldIn.playSound(
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            Sounds.portal_dungeon,
            SoundCategory.BLOCKS,
            0.5F,
            rand.nextFloat() * 0.4F + 0.8F,
            false
         );
      }
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if (entityIn != null
         && entityIn instanceof EntityPlayer
         && !worldIn.isRemote
         && !entityIn.isRiding()
         && entityIn.timeUntilPortal <= 0
         && !entityIn.isBeingRidden()
         && entityIn.isNonBoss()
         && entityIn.getEntityBoundingBox().intersects(state.getBoundingBox(worldIn, pos).offset(pos))) {
         ((EntityPlayer)entityIn).timeUntilPortal = 100;
         DimensionsRegister.teleporterDUNGEON.teleport((EntityPlayer)entityIn, pos);
      }
   }

   public Class<TileDungeonPortal> getTileEntityClass() {
      return TileDungeonPortal.class;
   }

   public TileDungeonPortal getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileDungeonPortal)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileDungeonPortal createTileEntity(World world, IBlockState blockState) {
      return new TileDungeonPortal();
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}

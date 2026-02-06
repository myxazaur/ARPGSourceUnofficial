package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.entity.EntitySpellForgeCatalyst;
import com.Vivern.Arpg.main.Weapons;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GemsparkBlock extends BlockFalling {
   public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

   public GemsparkBlock() {
      super(Material.ROCK);
      this.setRegistryName("gemspark_block");
      this.setTranslationKey("gemspark_block");
      this.blockHardness = 10.0F;
      this.blockResistance = 20.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.setLightLevel(0.35F);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean isTopSolid(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return side == EnumFacing.DOWN;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
      if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entityIn)) {
         entityIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
      }

      super.onEntityWalk(worldIn, pos, entityIn);
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return face.getAxis() != Axis.Y;
   }

   public int tickRate(World worldIn) {
      return 10;
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(world, pos, state, rand);
      if (!world.isRemote) {
         AxisAlignedBB aabbBig = new AxisAlignedBB(
            pos.getX() - 0.75,
            pos.getY(),
            pos.getZ() - 0.75,
            pos.getX() + 1.75,
            pos.getY() + 1.5,
            pos.getZ() + 1.75
         );
         AxisAlignedBB aabbSmall = new AxisAlignedBB(
            pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1.5, pos.getZ() + 1
         );

         for (EntityItem entityItem : world.getEntitiesWithinAABB(EntityItem.class, aabbSmall)) {
            if (entityItem.getItem() != null && entityItem.getItem().getCount() == 1) {
               EntitySpellForgeCatalyst forgeCatalyst = new EntitySpellForgeCatalyst(
                  world, entityItem.posX, entityItem.posY, entityItem.posZ, entityItem.getItem(), 20
               );
               world.spawnEntity(forgeCatalyst);
               entityItem.setDead();
            }
         }

         for (EntitySpellForgeCatalyst entityCatalyst : world.getEntitiesWithinAABB(EntitySpellForgeCatalyst.class, aabbBig)) {
            entityCatalyst.addHeat(40);
         }

         for (EnumFacing face : EnumFacing.HORIZONTALS) {
            if (rand.nextFloat() < 0.4F) {
               BlockPos posoff = pos.offset(face);
               IBlockState iBlockState = world.getBlockState(posoff);
               if (iBlockState.getMaterial() != Material.FIRE
                  && !iBlockState.getMaterial().isLiquid()
                  && (
                     Weapons.easyBreakBlockFor(world, 1.0F, posoff, iBlockState)
                        || iBlockState.getMaterial() == Material.WOOD && Weapons.easyBreakBlockFor(world, 10.0F, posoff, iBlockState)
                  )) {
                  world.destroyBlock(posoff, true);
                  world.setBlockState(posoff, Blocks.FIRE.getDefaultState());
               }
            }
         }
      }
   }

   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
      return PathNodeType.DAMAGE_OTHER;
   }
}

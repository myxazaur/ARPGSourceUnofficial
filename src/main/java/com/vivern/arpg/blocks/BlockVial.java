package com.vivern.arpg.blocks;

import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.tileentity.TileVial;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVial extends Block {
   public BlockVial() {
      super(Material.GLASS);
      this.setRegistryName("block_vial");
      this.setTranslationKey("block_vial");
      this.blockHardness = 1.0F;
      this.blockResistance = 0.2F;
      this.setSoundType(SoundTypeShards.SHARDS);
   }

   public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
      if (!world.isRemote) {
         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof TileVial) {
            TileVial tileVial = (TileVial)tileEntity;

            for (int i = 0; i < tileVial.vials.length; i++) {
               ItemStack itemStack = tileVial.vials[i];
               if (itemStack != null) {
                  float posx = tileVial.positions[i][0] / 16.0F;
                  float posz = tileVial.positions[i][1] / 16.0F;
                  EntityItem entityItem = new EntityItem(world, pos.getX() + posx, pos.getY(), pos.getZ() + posz, itemStack);
                  world.spawnEntity(entityItem);
                  tileVial.vials[i] = null;
                  tileVial.hitboxes[i] = null;
               }
            }
         }
      }

      super.onBlockHarvested(world, pos, state, player);
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   protected boolean canSilkHarvest() {
      return false;
   }

   public void onBlockClicked(World world, BlockPos pos, EntityPlayer playerIn) {
      if (!world.isRemote) {
         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof TileVial) {
            TileVial tileVial = (TileVial)tileEntity;
            Vec3d vec3d = playerIn.getPositionEyes(1.0F).add(-pos.getX(), -pos.getY(), -pos.getZ());
            Vec3d vec3d1 = playerIn.getLook(1.0F);
            double blockReachDistance = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
            Vec3d vec3d2 = vec3d.add(
               vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
            );
            RayTraceResult finalResult = null;
            int tracedBoxIndex = 0;
            double lowestDist = Double.MAX_VALUE;

            for (int i = 0; i < tileVial.hitboxes.length; i++) {
               AxisAlignedBB aabb = tileVial.hitboxes[i];
               if (aabb != null) {
                  RayTraceResult result = aabb.calculateIntercept(vec3d, vec3d2);
                  if (result != null) {
                     if (finalResult == null) {
                        finalResult = result;
                        tracedBoxIndex = i;
                     } else {
                        double distsq = finalResult.hitVec.squareDistanceTo(result.hitVec);
                        if (distsq < lowestDist) {
                           finalResult = result;
                           tracedBoxIndex = i;
                           lowestDist = distsq;
                        }
                     }
                  }
               }
            }

            if (finalResult != null) {
               float posx = tileVial.positions[tracedBoxIndex][0] / 16.0F;
               float posz = tileVial.positions[tracedBoxIndex][1] / 16.0F;
               ItemStack itemStack = tileVial.removeVialItem(tracedBoxIndex);
               EntityItem entityItem = new EntityItem(world, pos.getX() + posx, pos.getY(), pos.getZ() + posz, itemStack);
               entityItem.setNoPickupDelay();
               world.spawnEntity(entityItem);
               if (tileVial.hasNoItems()) {
                  world.setBlockToAir(pos);
               } else {
                  PacketHandler.trySendPacketUpdate(world, pos, tileVial, 64.0);
               }
            }
         }
      }
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileVial createTileEntity(World world, IBlockState blockState) {
      return new TileVial();
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
   }

   public void addCollisionBoxToList(
      IBlockState state,
      World world,
      BlockPos pos,
      AxisAlignedBB entityBox,
      List<AxisAlignedBB> collidingBoxes,
      @Nullable Entity entityIn,
      boolean isActualState
   ) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity != null && tileEntity instanceof TileVial) {
         TileVial tileVial = (TileVial)tileEntity;

         for (AxisAlignedBB boxHas : tileVial.hitboxes) {
            if (boxHas != null) {
               addCollisionBoxToList(pos, entityBox, collidingBoxes, boxHas);
            }
         }
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity != null && tileEntity instanceof TileVial) {
         TileVial tileVial = (TileVial)tileEntity;
         return tileVial.fullHitbox == null ? FULL_BLOCK_AABB : tileVial.fullHitbox;
      } else {
         return FULL_BLOCK_AABB;
      }
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}

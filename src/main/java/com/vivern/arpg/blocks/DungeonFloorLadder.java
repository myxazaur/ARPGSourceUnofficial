package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.dungeon.DimensionDungeon;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.network.PacketDoSomethingToClients;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.tileentity.TileDungeonLadder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DungeonFloorLadder extends Block {
   public static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
   public static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(-0.25, 0.0, -0.25, 1.0, 1.0, 0.0);
   public static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0, 0.0, 1.0, 1.25, 1.0, 1.25);
   public static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(1.0, 0.0, 0.0, 1.25, 1.0, 1.0);
   public static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(-0.25, 0.0, 0.0, 0.0, 1.0, 1.0);

   public DungeonFloorLadder() {
      super(Material.ROCK);
      this.setRegistryName("dungeon_floor_ladder");
      this.setTranslationKey("dungeon_floor_ladder");
      this.blockHardness = 99999.9F;
      this.setBlockUnbreakable();
      this.blockResistance = 999999.9F;
      this.setCreativeTab(CreativeTabs.TRANSPORTATION);
      this.setLightOpacity(1);
   }

   public void addCollisionBoxToList(
      IBlockState state,
      World worldIn,
      BlockPos pos,
      AxisAlignedBB entityBox,
      List<AxisAlignedBB> collidingBoxes,
      @Nullable Entity entityIn,
      boolean isActualState
   ) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return FULL_BLOCK_AABB;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public Class<TileDungeonLadder> getTileEntityClass() {
      return TileDungeonLadder.class;
   }

   public TileDungeonLadder getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileDungeonLadder)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileDungeonLadder createTileEntity(World world, IBlockState blockState) {
      return new TileDungeonLadder();
   }

   public boolean teleport(Entity entity, BlockPos pos) {
      if (entity != null && !entity.world.isRemote) {
         BlockPos coordsCR = DimensionDungeon.getCaveRegionCoords(pos);
         Vec3d relativePos = DimensionDungeon.getRelativePosInRegion(
            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, coordsCR.getX(), coordsCR.getZ()
         );
         int number = DimensionDungeon.getCaveRegionNumberFromCoord(coordsCR.getX(), coordsCR.getZ());
         BlockPos nextFloorCoordsCR = DimensionDungeon.getCaveRegionCoordFromNumber(number + 1);
         BlockPos nextFloorCenterPos = DimensionDungeon.getRegionCenterPos(nextFloorCoordsCR.getX(), nextFloorCoordsCR.getZ());
         entity.setPositionAndUpdate(
            nextFloorCenterPos.getX() + relativePos.x,
            254.8 - entity.height,
            nextFloorCenterPos.getZ() + relativePos.z
         );
         entity.world.playSound(null, pos, Sounds.dungeon_ladder, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
         entity.world
            .playSound(
               null,
               entity.posX,
               entity.posY,
               entity.posZ,
               Sounds.dungeon_ladder,
               SoundCategory.BLOCKS,
               0.7F,
               0.85F + RANDOM.nextFloat() * 0.3F
            );
         return true;
      } else {
         return false;
      }
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if (!worldIn.isRemote && entityIn.getEntityBoundingBox().minY <= pos.getY() + 0.125 && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         if (player.timeUntilPortal <= 0) {
            player.timeUntilPortal = 80;
            this.teleport(player, pos);
            if (player instanceof EntityPlayerMP) {
               PacketDoSomethingToClients pack = new PacketDoSomethingToClients();
               pack.writeargs(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 8);
               PacketHandler.sendTo(pack, (EntityPlayerMP)player);
            }
         }
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return true;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      if (face == EnumFacing.UP) {
         return BlockFaceShape.BOWL;
      } else {
         return face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
      }
   }
}

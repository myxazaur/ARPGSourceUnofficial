//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.dungeon.DimensionDungeon;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.tileentity.TileDungeonLadder;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook.EnumFlags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DungeonTopLadder extends Block {
   public static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0, 0.9375, 0.0, 1.0, 1.0, 1.0);
   public static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(-0.25, 0.0, -0.25, 1.0, 1.0, 0.0);
   public static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0, 0.0, 1.0, 1.25, 1.0, 1.25);
   public static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(1.0, 0.0, 0.0, 1.25, 1.0, 1.0);
   public static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(-0.25, 0.0, 0.0, 0.0, 1.0, 1.0);

   public DungeonTopLadder() {
      super(Material.ROCK);
      this.setRegistryName("dungeon_top_ladder");
      this.setTranslationKey("dungeon_top_ladder");
      this.blockHardness = 99999.9F;
      this.setBlockUnbreakable();
      this.blockResistance = 999999.9F;
      this.setCreativeTab(CreativeTabs.TRANSPORTATION);
      this.setLightOpacity(0);
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
      TileDungeonLadder t = new TileDungeonLadder();
      t.ladderStyle = 3;
      return t;
   }

   public boolean teleport(Entity entity, BlockPos pos) {
      System.out.println("teleport");
      if (entity != null) {
         System.out.println("entity!= null");
         if (!entity.world.isRemote) {
            System.out.println("!entity.world.isRemote");
            BlockPos coordsCR = DimensionDungeon.getCaveRegionCoords(pos);
            int number = DimensionDungeon.getCaveRegionNumberFromCoord(coordsCR.getX(), coordsCR.getZ());
            if (number <= 1) {
               return false;
            }

            System.out.println("number");
            System.out.println(" ");
            Vec3d relativePos = DimensionDungeon.getRelativePosInRegion(
               pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, coordsCR.getX(), coordsCR.getZ()
            );
            BlockPos nextFloorCoordsCR = DimensionDungeon.getCaveRegionCoordFromNumber(number - 1);
            BlockPos nextFloorCenterPos = DimensionDungeon.getRegionCenterPos(nextFloorCoordsCR.getX(), nextFloorCoordsCR.getZ());
            this.getTileEntity(entity.world, pos).playerToTeleport = entity;
            this.getTileEntity(entity.world, pos).positionToTeleport = new Vec3d(
               nextFloorCenterPos.getX() + relativePos.x, 1.125, nextFloorCenterPos.getZ() + relativePos.z
            );
            this.getTileEntity(entity.world, pos).teleportTick = 10;
            BlockPos ftilePos = new BlockPos(
               nextFloorCenterPos.getX() + relativePos.x, 1.0, nextFloorCenterPos.getZ() + relativePos.z
            );
            if (entity.world.getTileEntity(ftilePos) != null) {
               TileEntity te = entity.world.getTileEntity(ftilePos);
               if (te instanceof TileDungeonLadder) {
                  TileDungeonLadder ladder = (TileDungeonLadder)te;
                  ladder.playsoundTick = 4;
               }
            }

            entity.world.playSound(null, pos, Sounds.dungeon_ladder, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
            return true;
         }
      }

      return false;
   }

   public static void doTeleportAgressively(Entity teleportingEntity, double argX, double argY, double argZ, float argYaw, float argPitch) {
      if (teleportingEntity instanceof EntityPlayerMP) {
         EntityPlayerMP playerMP = (EntityPlayerMP)teleportingEntity;
         Set<EnumFlags> set = EnumSet.noneOf(EnumFlags.class);
         float f = MathHelper.wrapDegrees(argYaw);
         float f1 = MathHelper.wrapDegrees(argPitch);
         teleportingEntity.dismountRidingEntity();
         playerMP.connection.setPlayerLocation(argX, argY, argZ, f, f1, set);
         teleportingEntity.setRotationYawHead(f);
      } else {
         float f2 = MathHelper.wrapDegrees(argYaw);
         float f3 = MathHelper.wrapDegrees(argPitch);
         f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
         teleportingEntity.setLocationAndAngles(argX, argY, argZ, f2, f3);
         teleportingEntity.setRotationYawHead(f2);
      }

      if (!(teleportingEntity instanceof EntityLivingBase) || !((EntityLivingBase)teleportingEntity).isElytraFlying()) {
         teleportingEntity.motionY = 0.0;
         teleportingEntity.onGround = true;
      }
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if (!worldIn.isRemote && entityIn.posY + entityIn.height >= pos.getY() + 0.85 && entityIn instanceof EntityPlayer) {
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
      if (face == EnumFacing.DOWN) {
         return BlockFaceShape.BOWL;
      } else {
         return face == EnumFacing.UP ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
      }
   }
}

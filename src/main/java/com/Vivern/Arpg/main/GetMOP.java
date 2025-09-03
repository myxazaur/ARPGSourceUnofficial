//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.IFluidBlock;

public class GetMOP {
   public static Random rand = new Random();
   public static final EnumFacing[] XY_TICALS = new EnumFacing[]{EnumFacing.UP, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.WEST};
   public static final EnumFacing[] ZY_TICALS = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.DOWN, EnumFacing.SOUTH};
   public static Predicate<IBlockState> SOLID_BLOCKS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.isFullCube() && input.getMaterial().blocksMovement();
      }
   };
   public static Predicate<IBlockState> AIR_BLOCKS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == Blocks.AIR || input.getMaterial() == Material.AIR;
      }
   };
   public static Predicate<IBlockState> SOLID_NON_PLANTS_BLOCKS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.isFullCube()
            && input.getMaterial().blocksMovement()
            && input.getMaterial() != Material.LEAVES
            && input.getMaterial() != Material.VINE
            && input.getMaterial() != Material.CACTUS
            && input.getMaterial() != Material.PLANTS
            && input.getMaterial() != Material.WOOD
            && input.getMaterial() != Material.SNOW;
      }
   };
   public static Predicate<IBlockState> ALL_BLOCKS = state -> true;
   public static Predicate<IBlockState> WATER_BLOCKS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == Blocks.WATER || input.getBlock() == Blocks.FLOWING_WATER;
      }
   };
   public static Predicate<IBlockState> IFLUID_BLOCKS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() instanceof IFluidBlock || input.getBlock() instanceof BlockLiquid;
      }
   };

   public static List<EntityLivingBase> MopRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntitieslivingOnPath(vec3d, vec3d2, entity.world, entity, size, step);
   }

   protected static List<EntityLivingBase> findEntitieslivingOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      List<EntityLivingBase> moblist = new ArrayList<>();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Cube);
         if (!list.isEmpty()) {
            for (EntityLivingBase entityliving : list) {
               if (entityliving != shooter) {
                  moblist.add(entityliving);
                  return moblist;
               }
            }
         }
      }

      return moblist;
   }

   public static boolean isCollideBlockOnPos(World world, double x, double y, double z) {
      BlockPos pos = new BlockPos(x, y, z);
      return world.getBlockState(pos).getCollisionBoundingBox(world, pos) != null;
   }

   public static Vec3d PosRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step);
   }

   public static Vec3d PosRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, boolean checkTeam, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step, checkTeam);
   }

   public static Vec3d logicRayTrace(
      World world, Vec3d from, Vec3d to, float partialTicks, Predicate<? super Entity> filterEntityToIgnore, double size, double step, boolean stoponLiquid
   ) {
      RayTraceResult raytraceresult = normalizeRayTraceResult(world.rayTraceBlocks(from, to, stoponLiquid, true, false));
      if (raytraceresult != null) {
         to = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(from, to, world, filterEntityToIgnore, size, step);
   }

   public static Vec3d logicRayTraceIgnoreMobs(
      World world, Vec3d from, Vec3d to, float partialTicks, Predicate<? super IBlockState> filterBlockToIgnore, boolean stoponLiquid
   ) {
      RayTraceResult raytraceresult = rayTraceBlocks(world, from, to, filterBlockToIgnore, stoponLiquid, true, false);
      if (raytraceresult != null) {
         to = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return to;
   }

   public static boolean thereIsNoBlockCollidesBetween(
      World world, Vec3d from, Vec3d to, float partialTicks, @Nullable Predicate<? super IBlockState> filterBlockToIgnore, boolean stoponLiquid
   ) {
      RayTraceResult result = rayTraceBlocks(world, from, to, filterBlockToIgnore, stoponLiquid, true, false);
      return result == null
         || world.getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(world.getBlockState(result.getBlockPos()), world, result.getBlockPos())
            == null;
   }

   public static EntityLivingBase findEntityOnPath(
      double blockReachDistance, Vec3d from, Vec3d to, float partialTicks, EntityLivingBase entity, boolean checkTeam, double size, double step
   ) {
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(from, to, false, true, false);
      if (raytraceresult != null) {
         to = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntityOnPath(from, to, entity.world, entity, size, step, checkTeam);
   }

   public static EntityLivingBase findEntityOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      new ArrayList();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Cube);
         if (!list.isEmpty()) {
            for (EntityLivingBase entityliving : list) {
               if (entityliving != shooter) {
                  if (!checkTeam) {
                     return entityliving;
                  }

                  if (Team.checkIsOpponent(shooter, entityliving)) {
                     return entityliving;
                  }
               }
            }
         }
      }

      return null;
   }

   public static Entity findEntity2OnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      new ArrayList();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, Cube);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (entity != shooter) {
                  if (!checkTeam) {
                     return entity;
                  }

                  if (Team.checkIsOpponent(shooter, entity)) {
                     return entity;
                  }
               }
            }
         }
      }

      return null;
   }

   public static List<Entity> findEntitiesOnPath(Vec3d start, Vec3d end, World world, @Nullable Entity shooter, double size, double raystep) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      List<Entity> moblist = new ArrayList<>();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, Cube);
         if (!list.isEmpty()) {
            for (Entity entityliving : list) {
               if (entityliving.canBeCollidedWith() && !moblist.contains(entityliving)) {
                  moblist.add(entityliving);
               }
            }
         }
      }

      return moblist;
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, Cube);
         if (!list.isEmpty()) {
            for (Entity entit : list) {
               if (entit.canBeCollidedWith()) {
                  return CenterVertex;
               }
            }
         }
      }

      return end;
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, Cube);
         if (!list.isEmpty()) {
            for (Entity entit : list) {
               if (entit.canBeCollidedWith() && (!checkTeam || Team.checkIsOpponent(shooter, entit))) {
                  return CenterVertex;
               }
            }
         }
      }

      return end;
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Predicate<? super Entity> filterEntityToIgnore, double size, double raystep) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      new ArrayList();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, Cube);
         if (!list.isEmpty()) {
            for (Entity entit : list) {
               if (entit.canBeCollidedWith() && !filterEntityToIgnore.apply(entit)) {
                  return CenterVertex;
               }
            }
         }
      }

      return end;
   }

   public static Vec3d RotatedPosRayTrace(
      double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step, float rotationPitch, float rotationYaw
   ) {
      return RotatedPosRayTrace(blockReachDistance, partialTicks, entity, size, step, rotationPitch, rotationYaw, false);
   }

   public static Vec3d RotatedPosRayTrace(
      double blockReachDistance, float partialTicks, Vec3d start, EntityLivingBase entity, double size, double step, float rotationPitch, float rotationYaw
   ) {
      Vec3d vec3d1 = PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = start.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(start, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(start, vec3d2, entity.world, entity, size, step);
   }

   public static Vec3d RotatedPosRayTrace(
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw,
      boolean checkTeam
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step, checkTeam);
   }

   public static Vec3d findRandPosNearEntity(Entity entity, float radius) {
      return findRandPosNearPoint(entity.posX, entity.posY, entity.posZ, radius);
   }

   public static Vec3d findRandPosNearPoint(double xx, double yy, double zz, float radius) {
      float rand1 = rand.nextFloat() * 2.0F - 1.0F;
      float rand2 = rand.nextFloat() * 2.0F - 1.0F;
      float X = rand1 * radius;
      float new_R = (float)Math.sqrt(radius * radius - X * X);
      float Y = rand2 * new_R;
      float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
      if (rand.nextBoolean()) {
         Z *= -1.0F;
      }

      return new Vec3d(xx, yy, zz).add(X, Y, Z);
   }

   public static Vec3d PitchYawToVec3d(float pitch, float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3d(f1 * f2, f3, f * f2);
   }

   public static Vec3d YawToVec3d(float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      return new Vec3d(-f1, 0.0, -f);
   }

   @Deprecated
   public static Vec3d Vec3dToPitchYaw(Vec3d vec) {
      float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
      float rotationYaw = (float)(MathHelper.atan2(vec.x, -vec.z) * (180.0 / Math.PI));
      float rotationPitch = (float)(MathHelper.atan2(vec.y, f) * (180.0 / Math.PI));
      return new Vec3d(rotationPitch, rotationYaw, 0.0);
   }

   public static Vec2f Vec3dToPitchYawFixed(Vec3d vec) {
      float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
      float rotationYaw = (float)(MathHelper.atan2(vec.x, -vec.z) * (180.0 / Math.PI));
      float rotationPitch = (float)(MathHelper.atan2(vec.y, f) * (180.0 / Math.PI));
      return new Vec2f(-rotationPitch, MathHelper.wrapDegrees(rotationYaw + 180.0F));
   }

   public static float Vec2dToYaw(double x, double z) {
      float f = MathHelper.sqrt(x + z);
      return (float)(MathHelper.atan2(x, -z) * (180.0 / Math.PI));
   }

   public static boolean approximatelyEqual(double value, double value2, double range) {
      return Math.abs(value - value2) <= range;
   }

   public static boolean approximatelyEqual(int value, int value2, int range) {
      return Math.abs(value - value2) <= range;
   }

   public static int floatToIntWithChance(float value, Random rand) {
      int full = MathHelper.floor(value);
      if (rand.nextFloat() < value - full) {
         full++;
      }

      return full;
   }

   public static boolean collidesWithBlock(World world, BlockPos pos, Block block) {
      return world.getBlockState(pos.up()).getBlock() == block
         || world.getBlockState(pos.down()).getBlock() == block
         || world.getBlockState(pos.west()).getBlock() == block
         || world.getBlockState(pos.south()).getBlock() == block
         || world.getBlockState(pos.north()).getBlock() == block
         || world.getBlockState(pos.east()).getBlock() == block;
   }

   public static boolean collidesWithBlockHorizontal(IBlockAccess world, BlockPos pos, Block block) {
      return world.getBlockState(pos.west()).getBlock() == block
         || world.getBlockState(pos.south()).getBlock() == block
         || world.getBlockState(pos.north()).getBlock() == block
         || world.getBlockState(pos.east()).getBlock() == block;
   }

   public static boolean collidesWithAnyBlock(World world, BlockPos pos) {
      return world.getBlockState(pos.up()).getCollisionBoundingBox(world, pos) != null
         || world.getBlockState(pos.down()).getCollisionBoundingBox(world, pos) != null
         || world.getBlockState(pos.west()).getCollisionBoundingBox(world, pos) != null
         || world.getBlockState(pos.south()).getCollisionBoundingBox(world, pos) != null
         || world.getBlockState(pos.north()).getCollisionBoundingBox(world, pos) != null
         || world.getBlockState(pos.east()).getCollisionBoundingBox(world, pos) != null;
   }

   public static boolean collidesWithBlockExcept(World world, BlockPos pos, Block block, EnumFacing facingExcept) {
      for (EnumFacing facing : EnumFacing.VALUES) {
         if (facing != facingExcept && world.getBlockState(pos.offset(facing)).getBlock() == block) {
            return true;
         }
      }

      return false;
   }

   public static int next(int value, int amountnext, int bound) {
      int result = value + amountnext;
      if (result >= bound && amountnext < bound) {
         result -= bound;
      }

      if (amountnext == bound) {
         return value;
      } else {
         if (amountnext > bound) {
            int perc = amountnext % bound;
            result = value + perc;
         }

         return result;
      }
   }

   public static int followNumber(int baseValue, int targetValue, int followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static float followNumber(float baseValue, float targetValue, float followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static double followNumber(double baseValue, double targetValue, double followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static float getDirectionBetweenAngles(float baseValue, float targetValue) {
      baseValue = MathHelper.wrapDegrees(baseValue) + 180.0F;
      targetValue = MathHelper.wrapDegrees(targetValue) + 180.0F;
      if (targetValue == baseValue) {
         return 0.0F;
      } else {
         float baseValue180 = MathHelper.wrapDegrees(baseValue) + 180.0F;
         if (targetValue == baseValue180) {
            return 180.0F;
         } else {
            if (baseValue180 > baseValue) {
               if (targetValue > baseValue && targetValue < baseValue180) {
                  return targetValue - baseValue;
               }

               if (targetValue >= 0.0F && targetValue < baseValue) {
                  return targetValue - baseValue;
               }

               if (targetValue <= 360.0F && targetValue > baseValue180) {
                  return -baseValue - (360.0F - targetValue);
               }
            } else {
               if (targetValue < baseValue && targetValue > baseValue180) {
                  return targetValue - baseValue;
               }

               if (targetValue >= 0.0F && targetValue < baseValue180) {
                  return 360.0F - baseValue + targetValue;
               }

               if (targetValue <= 360.0F && targetValue > baseValue) {
                  return targetValue - baseValue;
               }
            }

            return 0.0F;
         }
      }
   }

   public static int cycle(int value, int cycleBound) {
      if (cycleBound == 1) {
         return 0;
      } else {
         return value >= 0 ? value % cycleBound : value + (-value / cycleBound + 1) * cycleBound;
      }
   }

   public static double cycle(double value, double cycleBound) {
      return value >= 0.0 ? value % cycleBound : value + (-value / cycleBound + 1.0) * cycleBound;
   }

   public static double arcsinusoid(double value) {
      double shifted = cycle(value, 4.0);
      return arcsinusoidPart(shifted);
   }

   private static double arcsinusoidPart(double value) {
      if (value >= 0.0 && value < 2.0) {
         return Math.asin(value - 1.0) / Math.PI * 2.0;
      } else {
         return value >= 2.0 && value < 4.0 ? Math.asin(-value + 3.0) / Math.PI * 2.0 : 0.0;
      }
   }

   public static List<BlockPos> getPosesInsideSphere(int x, int y, int z, int radius) {
      List<BlockPos> list = new ArrayList<>();
      int radiusSq = radius * radius;

      for (int xr = -radius; xr <= radius; xr++) {
         for (int yr = -radius; yr <= radius; yr++) {
            for (int zr = -radius; zr <= radius; zr++) {
               if (xr * xr + yr * yr + zr * zr <= radiusSq) {
                  list.add(new BlockPos(xr + x, yr + y, zr + z));
               }
            }
         }
      }

      return list;
   }

   @Nullable
   public static RayTraceResult rayTraceBlocks(
      World world,
      Vec3d vec31,
      Vec3d vec32,
      Predicate<? super IBlockState> filterBlockToIgnore,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
         return null;
      } else if (!Double.isNaN(vec32.x) && !Double.isNaN(vec32.y) && !Double.isNaN(vec32.z)) {
         int i = MathHelper.floor(vec32.x);
         int j = MathHelper.floor(vec32.y);
         int k = MathHelper.floor(vec32.z);
         int l = MathHelper.floor(vec31.x);
         int i1 = MathHelper.floor(vec31.y);
         int j1 = MathHelper.floor(vec31.z);
         BlockPos blockpos = new BlockPos(l, i1, j1);
         IBlockState iblockstate = world.getBlockState(blockpos);
         Block block = iblockstate.getBlock();
         if ((
               !ignoreBlockWithoutBoundingBox
                  || iblockstate.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB
                  || stopOnLiquid && iblockstate.getMaterial().isLiquid()
            )
            && block.canCollideCheck(iblockstate, stopOnLiquid)
            && (filterBlockToIgnore == null || !filterBlockToIgnore.apply(iblockstate))) {
            RayTraceResult raytraceresult = iblockstate.collisionRayTrace(world, blockpos, vec31, vec32);
            if (raytraceresult != null) {
               return raytraceresult;
            }
         }

         RayTraceResult raytraceresult2 = null;
         int k1 = 200;

         while (k1-- >= 0) {
            if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
               return null;
            }

            if (l == i && i1 == j && j1 == k) {
               return returnLastUncollidableBlock ? raytraceresult2 : null;
            }

            boolean flag2 = true;
            boolean flag = true;
            boolean flag1 = true;
            double d0 = 999.0;
            double d1 = 999.0;
            double d2 = 999.0;
            if (i > l) {
               d0 = l + 1.0;
            } else if (i < l) {
               d0 = l + 0.0;
            } else {
               flag2 = false;
            }

            if (j > i1) {
               d1 = i1 + 1.0;
            } else if (j < i1) {
               d1 = i1 + 0.0;
            } else {
               flag = false;
            }

            if (k > j1) {
               d2 = j1 + 1.0;
            } else if (k < j1) {
               d2 = j1 + 0.0;
            } else {
               flag1 = false;
            }

            double d3 = 999.0;
            double d4 = 999.0;
            double d5 = 999.0;
            double d6 = vec32.x - vec31.x;
            double d7 = vec32.y - vec31.y;
            double d8 = vec32.z - vec31.z;
            if (flag2) {
               d3 = (d0 - vec31.x) / d6;
            }

            if (flag) {
               d4 = (d1 - vec31.y) / d7;
            }

            if (flag1) {
               d5 = (d2 - vec31.z) / d8;
            }

            if (d3 == -0.0) {
               d3 = -1.0E-4;
            }

            if (d4 == -0.0) {
               d4 = -1.0E-4;
            }

            if (d5 == -0.0) {
               d5 = -1.0E-4;
            }

            EnumFacing enumfacing;
            if (d3 < d4 && d3 < d5) {
               enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
               vec31 = new Vec3d(d0, vec31.y + d7 * d3, vec31.z + d8 * d3);
            } else if (d4 < d5) {
               enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
               vec31 = new Vec3d(vec31.x + d6 * d4, d1, vec31.z + d8 * d4);
            } else {
               enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
               vec31 = new Vec3d(vec31.x + d6 * d5, vec31.y + d7 * d5, d2);
            }

            l = MathHelper.floor(vec31.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
            i1 = MathHelper.floor(vec31.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
            j1 = MathHelper.floor(vec31.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
            blockpos = new BlockPos(l, i1, j1);
            IBlockState iblockstate1 = world.getBlockState(blockpos);
            Block block1 = iblockstate1.getBlock();
            if ((
                  !ignoreBlockWithoutBoundingBox
                     || iblockstate1.getMaterial() == Material.PORTAL
                     || iblockstate1.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB
                     || stopOnLiquid && iblockstate1.getMaterial().isLiquid()
               )
               && (filterBlockToIgnore == null || !filterBlockToIgnore.apply(iblockstate1))) {
               if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
                  RayTraceResult raytraceresult1 = iblockstate1.collisionRayTrace(world, blockpos, vec31, vec32);
                  if (raytraceresult1 != null) {
                     return raytraceresult1;
                  }
               } else {
                  raytraceresult2 = new RayTraceResult(Type.MISS, vec31, enumfacing, blockpos);
               }
            }
         }

         return returnLastUncollidableBlock ? raytraceresult2 : null;
      } else {
         return null;
      }
   }

   public static RayTraceResult rayTraceLiquids(World world, Vec3d start, Vec3d end) {
      double dist = start.distanceTo(end);
      double step = 0.0625;
      int iterations = (int)(dist / step);
      Vec3d vecadd = end.subtract(start).normalize().scale(step);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int i = 0; i < iterations; i++) {
         blockpos$pooledmutableblockpos.setPos(
            MathHelper.floor(start.x + vecadd.x * i),
            MathHelper.floor(start.y + vecadd.y * i),
            MathHelper.floor(start.z + vecadd.z * i)
         );
         IBlockState iBlockState = world.getBlockState(blockpos$pooledmutableblockpos);
         if (iBlockState.getMaterial().isLiquid()) {
            boolean succ = false;
            if (iBlockState.getBlock() instanceof IFluidBlock) {
               IFluidBlock fluidBlock = (IFluidBlock)iBlockState.getBlock();
               if (fluidBlock.getFilledPercentage(world, blockpos$pooledmutableblockpos) >= 1.0F) {
                  succ = true;
               }
            } else if (iBlockState.getBlock() instanceof BlockLiquid && (Integer)iBlockState.getValue(BlockLiquid.LEVEL) == 0) {
               succ = true;
            }

            if (succ) {
               RayTraceResult result = new RayTraceResult(
                  Type.BLOCK,
                  new Vec3d(
                     start.x + vecadd.x * i,
                     start.y + vecadd.y * i,
                     start.z + vecadd.z * i
                  ),
                  EnumFacing.UP,
                  blockpos$pooledmutableblockpos.toImmutable()
               );
               blockpos$pooledmutableblockpos.release();
               return result;
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return null;
   }

   public static boolean containsBlock(World world, AxisAlignedBB bb, Block cblock) {
      int j2 = MathHelper.floor(bb.minX);
      int k2 = MathHelper.ceil(bb.maxX);
      int l2 = MathHelper.floor(bb.minY);
      int i3 = MathHelper.ceil(bb.maxY);
      int j3 = MathHelper.floor(bb.minZ);
      int k3 = MathHelper.ceil(bb.maxZ);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int l3 = j2; l3 < k2; l3++) {
         for (int i4 = l2; i4 < i3; i4++) {
            for (int j4 = j3; j4 < k3; j4++) {
               IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
               if (iblockstate1.getMaterial().isLiquid() && iblockstate1.getBlock() == cblock) {
                  blockpos$pooledmutableblockpos.release();
                  return true;
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return false;
   }

   @Nullable
   public static IBlockState firstBlockStateContains(World world, AxisAlignedBB bb, Predicate<IBlockState> blocksToAccess) {
      int j2 = MathHelper.floor(bb.minX);
      int k2 = MathHelper.ceil(bb.maxX);
      int l2 = MathHelper.floor(bb.minY);
      int i3 = MathHelper.ceil(bb.maxY);
      int j3 = MathHelper.floor(bb.minZ);
      int k3 = MathHelper.ceil(bb.maxZ);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int l3 = j2; l3 < k2; l3++) {
         for (int i4 = l2; i4 < i3; i4++) {
            for (int j4 = j3; j4 < k3; j4++) {
               IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
               if (blocksToAccess.apply(iblockstate1)) {
                  blockpos$pooledmutableblockpos.release();
                  return iblockstate1;
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return null;
   }

   @Nullable
   public static <T extends Entity> T findNearestEntityWithinAABB(World world, Class<? extends T> entityType, AxisAlignedBB aabb, Vec3d closestTo) {
      List<T> list = world.getEntitiesWithinAABB(entityType, aabb);
      T t = null;
      double d0 = Double.MAX_VALUE;

      for (int j2 = 0; j2 < list.size(); j2++) {
         T t1 = (T)list.get(j2);
         if (EntitySelectors.NOT_SPECTATING.apply(t1)) {
            double d1 = closestTo.distanceTo(t1.getPositionVector());
            if (d1 <= d0) {
               t = t1;
               d0 = d1;
            }
         }
      }

      return t;
   }

   public static double getAngleBetweenVectors(double vec1x, double vec1y, double vec2x, double vec2y) {
      double scalar = vec1x * vec2x + vec1y * vec2y;
      double m1 = Math.sqrt(vec1x * vec1x + vec1y * vec1y);
      double m2 = Math.sqrt(vec2x * vec2x + vec2y * vec2y);
      return Math.acos(scalar / (m1 * m2)) * 57.2958;
   }

   public static double getAngleBetweenVectors(double vec1x, double vec1y, double vec1z, double vec2x, double vec2y, double vec2z) {
      double scalar = vec1x * vec2x + vec1y * vec2y + vec1z * vec2z;
      double m1 = Math.sqrt(vec1x * vec1x + vec1y * vec1y + vec1z * vec1z);
      double m2 = Math.sqrt(vec2x * vec2x + vec2y * vec2y + vec2z * vec2z);
      return Math.acos(scalar / (m1 * m2)) * 57.2958;
   }

   public static double getAngleBetweenVectors(Vec3d vec1, Vec3d vec2, double vec1length, double vec2length) {
      double scalar = vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
      return Math.acos(scalar / (vec1length * vec2length)) * 57.2958;
   }

   public static List<Entity> entityMopRayTrace(
      Class<? extends Entity> eclass,
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findCustomEntityOnPath(eclass, vec3d, vec3d2, entity.world, entity, size, step);
   }

   public static EntityLivingBase entityRayTrace(
      World world, Vec3d start, Vec3d end, double size, double step, Predicate<? super IBlockState> filterBlockToIgnore
   ) {
      RayTraceResult raytraceresult = filterBlockToIgnore == null
         ? world.rayTraceBlocks(start, end, false, true, false)
         : rayTraceBlocks(world, start, end, filterBlockToIgnore, false, true, false);
      if (raytraceresult != null) {
         end = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntityOnPath(start, end, world, null, size, step, false);
   }

   protected static List<Entity> findCustomEntityOnPath(
      Class<? extends Entity> eclass, Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep
   ) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      List<Entity> moblist = new ArrayList<>();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABB(eclass, Cube);
         if (!list.isEmpty()) {
            for (Entity entityliving : list) {
               if (entityliving.canBeCollidedWith() && entityliving != shooter) {
                  moblist.add(entityliving);
                  return moblist;
               }
            }
         }
      }

      return moblist;
   }

   public static BlockPos getTrueHeight(World world, BlockPos pos) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         Material mat = chunk.getBlockState(pos.getX(), y, pos.getZ()).getMaterial();
         if (!mat.isLiquid() && mat != Material.AIR) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlock(World world, BlockPos pos, Block block) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         if (chunk.getBlockState(pos.getX(), y, pos.getZ()).getBlock() == block) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlocks(World world, BlockPos pos, Block... blocks) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         Block blockgetted = chunk.getBlockState(pos.getX(), y, pos.getZ()).getBlock();

         for (Block block : blocks) {
            if (blockgetted == block) {
               return new BlockPos(pos.getX(), y, pos.getZ());
            }
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlockExcluding(World world, BlockPos pos, Block block, boolean useLiquid) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         IBlockState state = chunk.getBlockState(pos.getX(), y, pos.getZ());
         Material mat = state.getMaterial();
         if (mat != Material.AIR && state.getBlock() != block && (useLiquid || !mat.isLiquid())) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   @Nullable
   public static BlockTraceResult blockTrace(
      World world, BlockPos startpos, EnumFacing direction, int distance, @Nullable Predicate<? super IBlockState> filterBlockToCollide
   ) {
      if (filterBlockToCollide == null) {
         IBlockState laststate = world.getBlockState(startpos);
         PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

         for (int i = 1; i <= distance; i++) {
            blockpos$pooledmutableblockpos.setPos(
               startpos.getX() + direction.getXOffset() * i,
               startpos.getY() + direction.getYOffset() * i,
               startpos.getZ() + direction.getZOffset() * i
            );
            IBlockState newstate = world.getBlockState(blockpos$pooledmutableblockpos);
            if (newstate.getBlock() != laststate.getBlock()) {
               BlockTraceResult res = new BlockTraceResult();
               res.impactState = newstate;
               res.prevState = laststate;
               res.pos = new BlockPos(blockpos$pooledmutableblockpos);
               res.prevPos = res.pos.offset(direction.getOpposite());
               res.facing = direction;
               blockpos$pooledmutableblockpos.release();
               return res;
            }

            laststate = newstate;
         }

         blockpos$pooledmutableblockpos.release();
      } else {
         IBlockState laststate = world.getBlockState(startpos);
         PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

         for (int i = 1; i <= distance; i++) {
            blockpos$pooledmutableblockpos.setPos(
               startpos.getX() + direction.getXOffset() * i,
               startpos.getY() + direction.getYOffset() * i,
               startpos.getZ() + direction.getZOffset() * i
            );
            IBlockState newstate = world.getBlockState(blockpos$pooledmutableblockpos);
            if (filterBlockToCollide.apply(newstate)) {
               BlockTraceResult res = new BlockTraceResult();
               res.impactState = newstate;
               res.prevState = laststate;
               res.pos = new BlockPos(blockpos$pooledmutableblockpos);
               res.prevPos = res.pos.offset(direction.getOpposite());
               res.facing = direction;
               blockpos$pooledmutableblockpos.release();
               return res;
            }

            laststate = newstate;
         }

         blockpos$pooledmutableblockpos.release();
      }

      return null;
   }

   public static List<BlockPos> getBlockPosesCollidesAABB(World world, AxisAlignedBB bb, boolean getLiquids) {
      List<BlockPos> list = new ArrayList<>();
      int j2 = MathHelper.floor(bb.minX);
      int k2 = MathHelper.ceil(bb.maxX);
      int l2 = MathHelper.floor(bb.minY);
      int i3 = MathHelper.ceil(bb.maxY);
      int j3 = MathHelper.floor(bb.minZ);
      int k3 = MathHelper.ceil(bb.maxZ);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int l3 = j2; l3 < k2; l3++) {
         for (int i4 = l2; i4 < i3; i4++) {
            for (int j4 = j3; j4 < k3; j4++) {
               IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
               if (iblockstate1.getMaterial() != Material.AIR && (getLiquids || !iblockstate1.getMaterial().isLiquid())) {
                  list.add(new BlockPos(l3, i4, j4));
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return list;
   }

   public static List<BlockPos> getBlockPosesCollidesAABBwithTheirHitbox(World world, AxisAlignedBB bb, boolean getLiquids) {
      List<BlockPos> list = new ArrayList<>();
      int j2 = MathHelper.floor(bb.minX);
      int k2 = MathHelper.ceil(bb.maxX);
      int l2 = MathHelper.floor(bb.minY);
      int i3 = MathHelper.ceil(bb.maxY);
      int j3 = MathHelper.floor(bb.minZ);
      int k3 = MathHelper.ceil(bb.maxZ);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int l3 = j2; l3 < k2; l3++) {
         for (int i4 = l2; i4 < i3; i4++) {
            for (int j4 = j3; j4 < k3; j4++) {
               IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
               if (iblockstate1.getMaterial() != Material.AIR && (getLiquids || !IFLUID_BLOCKS.apply(iblockstate1))) {
                  AxisAlignedBB aabb = iblockstate1.getBoundingBox(world, blockpos$pooledmutableblockpos);
                  if (aabb != null && aabb.offset(l3, i4, j4).intersects(bb)) {
                     list.add(new BlockPos(l3, i4, j4));
                  }
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return list;
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double blockReachDistance,
      double size,
      double raystep,
      boolean checkTeam,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock,
      float rotationPitch,
      float rotationYaw
   ) {
      Vec3d vec3d = shooter.getPositionEyes(1.0F);
      Vec3d vec3d1 = PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      return fixedRayTraceBlocks(
         world, shooter, size, raystep, checkTeam, vec3d, vec3d2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock
      );
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double blockReachDistance,
      double size,
      double raystep,
      boolean checkTeam,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      Vec3d vec3d = shooter.getPositionEyes(1.0F);
      Vec3d vec3d1 = shooter.getLook(1.0F);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      return fixedRayTraceBlocks(
         world, shooter, size, raystep, checkTeam, vec3d, vec3d2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock
      );
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double size,
      double raystep,
      boolean checkTeam,
      Vec3d start,
      Vec3d end,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock,
      @Nullable Predicate<? super IBlockState> filterBlockToIgnore
   ) {
      RayTraceResult result = rayTraceBlocks(world, start, end, filterBlockToIgnore, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
      if (result != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
         RayTraceResult baseres = findEntityAndPosOnPath(start, result.hitVec, world, shooter, size, raystep, checkTeam);
         if (baseres != null) {
            result.entityHit = baseres.entityHit;
            result.typeOfHit = Type.ENTITY;
            result.hitVec = baseres.hitVec;
         }
      } else if (result == null || result.hitVec == null || result.typeOfHit == Type.MISS) {
         RayTraceResult baseres = findEntityAndPosOnPath(start, end, world, shooter, size, raystep, checkTeam);
         if (baseres != null) {
            result = new RayTraceResult(baseres.entityHit, baseres.hitVec);
            result.typeOfHit = Type.ENTITY;
         } else {
            result = new RayTraceResult(Type.MISS, end, null, null);
         }
      }

      return normalizeRayTraceResult(result);
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double size,
      double raystep,
      boolean checkTeam,
      Vec3d start,
      Vec3d end,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      return fixedRayTraceBlocks(
         world, shooter, size, raystep, checkTeam, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, null
      );
   }

   public static RayTraceResult findEntityAndPosOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      new ArrayList();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, Cube);
         if (!list.isEmpty()) {
            for (Entity entityliving : list) {
               if (entityliving.canBeCollidedWith()) {
                  if (!checkTeam) {
                     return new RayTraceResult(entityliving, CenterVertex);
                  }

                  if (Team.checkIsOpponent(shooter, entityliving)) {
                     return new RayTraceResult(entityliving, CenterVertex);
                  }
               }
            }
         }
      }

      return null;
   }

   public static Vec3d rotateVecAroundAxis(Vec3d vector, Vec3d axisVector, float angle) {
      axisVector = axisVector.normalize();
      double cosangle = Math.cos(angle);
      return axisVector.scale(axisVector.dotProduct(vector) * (1.0 - cosangle))
         .add(axisVector.crossProduct(vector).scale(Math.sin(angle)))
         .add(vector.scale(cosangle));
   }

   public static Vec3d rotateVecAroundAxis(Vec3d vector, Vec3d axisVector, Rotation rotation) {
      double cosangle = 0.0;
      double sinangle = 0.0;
      if (rotation == Rotation.NONE) {
         cosangle = 1.0;
         sinangle = 0.0;
      } else if (rotation == Rotation.CLOCKWISE_90) {
         cosangle = 0.0;
         sinangle = 1.0;
      } else if (rotation == Rotation.CLOCKWISE_180) {
         cosangle = -1.0;
         sinangle = 0.0;
      } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
         cosangle = 0.0;
         sinangle = -1.0;
      }

      return axisVector.scale(axisVector.dotProduct(vector) * (1.0 - cosangle))
         .add(axisVector.crossProduct(vector).scale(sinangle))
         .add(vector.scale(cosangle));
   }

   public static Vec3d getNormalForRotation(float rotationX, float rotationY, float rotationZ) {
      Vec3d vec = new Vec3d(0.0, 1.0, 0.0);
      Vec3d axisvec1 = new Vec3d(0.0, 0.0, 1.0);
      Vec3d axisvec2 = new Vec3d(0.0, 1.0, 0.0);
      Vec3d axisvec3 = new Vec3d(1.0, 0.0, 0.0);
      if (rotationZ != 0.0F) {
         vec = rotateVecAroundAxis(vec, axisvec1, -rotationZ * -1.0F);
         axisvec2 = rotateVecAroundAxis(axisvec2, axisvec1, -rotationZ * -1.0F);
         axisvec3 = rotateVecAroundAxis(axisvec3, axisvec1, -rotationZ * -1.0F);
      }

      if (rotationY != 0.0F) {
         vec = rotateVecAroundAxis(vec, axisvec2, -rotationY);
         axisvec3 = rotateVecAroundAxis(axisvec3, axisvec2, -rotationY);
      }

      if (rotationX != 0.0F) {
         vec = rotateVecAroundAxis(vec, axisvec3, -rotationX);
      }

      return vec;
   }

   public static float getfromto(float mainNumber, float from, float to) {
      if (mainNumber < from) {
         return 0.0F;
      } else if (mainNumber > to) {
         return 1.0F;
      } else {
         float n = Math.max(mainNumber - from, 0.0F);
         float f = 1.0F / (to - from);
         return n * f;
      }
   }

   public static double getfromto(double mainNumber, double from, double to) {
      if (mainNumber < from) {
         return 0.0;
      } else if (mainNumber > to) {
         return 1.0;
      } else {
         double n = Math.max(mainNumber - from, 0.0);
         double f = 1.0 / (to - from);
         return n * f;
      }
   }

   public static float softfromto(float mainNumber, float from, float to) {
      if (mainNumber < from) {
         return 0.0F;
      } else {
         return mainNumber > to ? 1.0F : -MathHelper.cos((mainNumber - from) * (float) Math.PI / (to - from)) / 2.0F + 0.5F;
      }
   }

   public static Vec3d getNearestPointInAABBtoPoint(Vec3d point, AxisAlignedBB aabb) {
      if (point.y >= aabb.maxY) {
         if (point.x >= aabb.maxX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.maxX, aabb.maxY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ);
            }
         } else if (point.x < aabb.maxX && point.x > aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(point.x, aabb.maxY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(point.x, aabb.maxY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(point.x, aabb.maxY, aabb.minZ);
            }
         } else if (point.x <= aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.minX, aabb.maxY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.minX, aabb.maxY, aabb.minZ);
            }
         }
      } else if (point.y < aabb.maxY && point.y > aabb.minY) {
         if (point.x >= aabb.maxX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.maxX, point.y, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.maxX, point.y, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.maxX, point.y, aabb.minZ);
            }
         } else if (point.x < aabb.maxX && point.x > aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(point.x, point.y, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return point;
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(point.x, point.y, aabb.minZ);
            }
         } else if (point.x <= aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.minX, point.y, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.minX, point.y, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.minX, point.y, aabb.minZ);
            }
         }
      } else if (point.y <= aabb.minY) {
         if (point.x >= aabb.maxX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.maxX, aabb.minY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.maxX, aabb.minY, aabb.minZ);
            }
         } else if (point.x < aabb.maxX && point.x > aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(point.x, aabb.minY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(point.x, aabb.minY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(point.x, aabb.minY, aabb.minZ);
            }
         } else if (point.x <= aabb.minX) {
            if (point.z >= aabb.maxZ) {
               return new Vec3d(aabb.minX, aabb.minY, aabb.maxZ);
            }

            if (point.z < aabb.maxZ && point.z > aabb.minZ) {
               return new Vec3d(aabb.minX, aabb.minY, point.z);
            }

            if (point.z <= aabb.minZ) {
               return new Vec3d(aabb.minX, aabb.minY, aabb.minZ);
            }
         }
      }

      return aabb.getCenter();
   }

   public static Vec3d entityCenterPos(Entity entity) {
      return new Vec3d(entity.posX, entity.posY + entity.height / 2.0F, entity.posZ);
   }

   public static Vec3d entityCenterPos(Entity entity, float partialTicks) {
      float he2 = entity.height / 2.0F;
      return new Vec3d(
         partial(entity.posX, entity.prevPosX, (double)partialTicks),
         partial(entity.posY + he2, entity.prevPosY + he2, (double)partialTicks),
         partial(entity.posZ, entity.prevPosZ, (double)partialTicks)
      );
   }

   public static float partial(float value, float previousValue, float partialTicks) {
      return previousValue + (value - previousValue) * partialTicks;
   }

   public static float partial(float value, float previousValue) {
      return partial(value, previousValue, Minecraft.getMinecraft().getRenderPartialTicks());
   }

   public static double partial(double value, double previousValue, double partialTicks) {
      return previousValue + (value - previousValue) * partialTicks;
   }

   public static double partial(double value, double previousValue) {
      return partial(value, previousValue, (double)Minecraft.getMinecraft().getRenderPartialTicks());
   }

   public static EnumFacing rotate(EnumFacing face, int rotation) {
      if (rotation > 3) {
         rotation %= 4;
      }

      if (rotation == 0) {
         return face;
      } else if (rotation == 1) {
         return face.rotateY();
      } else if (rotation == 2) {
         return face.getOpposite();
      } else {
         return rotation == 3 ? face.rotateYCCW() : face;
      }
   }

   public static EnumFacing rotateZ(EnumFacing face, boolean clockwise) {
      if (face != EnumFacing.SOUTH && face != EnumFacing.NORTH) {
         if (clockwise) {
            if (face == EnumFacing.WEST) {
               return EnumFacing.UP;
            }

            if (face == EnumFacing.UP) {
               return EnumFacing.EAST;
            }

            if (face == EnumFacing.EAST) {
               return EnumFacing.DOWN;
            }

            if (face == EnumFacing.DOWN) {
               return EnumFacing.WEST;
            }
         } else {
            if (face == EnumFacing.UP) {
               return EnumFacing.WEST;
            }

            if (face == EnumFacing.EAST) {
               return EnumFacing.UP;
            }

            if (face == EnumFacing.DOWN) {
               return EnumFacing.EAST;
            }

            if (face == EnumFacing.WEST) {
               return EnumFacing.DOWN;
            }
         }

         return face;
      } else {
         return face;
      }
   }

   public static EnumFacing rotateX(EnumFacing face, boolean clockwise) {
      if (face != EnumFacing.WEST && face != EnumFacing.EAST) {
         if (clockwise) {
            if (face == EnumFacing.SOUTH) {
               return EnumFacing.UP;
            }

            if (face == EnumFacing.UP) {
               return EnumFacing.NORTH;
            }

            if (face == EnumFacing.NORTH) {
               return EnumFacing.DOWN;
            }

            if (face == EnumFacing.DOWN) {
               return EnumFacing.SOUTH;
            }
         } else {
            if (face == EnumFacing.UP) {
               return EnumFacing.SOUTH;
            }

            if (face == EnumFacing.NORTH) {
               return EnumFacing.UP;
            }

            if (face == EnumFacing.DOWN) {
               return EnumFacing.NORTH;
            }

            if (face == EnumFacing.SOUTH) {
               return EnumFacing.DOWN;
            }
         }

         return face;
      } else {
         return face;
      }
   }

   public static EnumFacing rotate(EnumFacing face, boolean clockwise, Axis axis) {
      if (axis == Axis.X) {
         return rotateX(face, clockwise);
      } else if (axis == Axis.Y) {
         return clockwise ? face.rotateY() : face.rotateYCCW();
      } else {
         return rotateZ(face, clockwise);
      }
   }

   @Nullable
   public static EntityLivingBase findNearestEnemy(World world, Entity enemyTo, double x, double y, double z, double radius, boolean bestSearch) {
      double max = Double.MAX_VALUE;
      EntityLivingBase targ = null;
      if (!bestSearch) {
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(enemyTo, entitylivingbase) && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)) {
                  double dist = entitylivingbase.getDistance(x, y, z);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbase;
                  }
               }
            }
         }
      } else {
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbasex : list) {
               if (entitylivingbasex.isCreatureType(EnumCreatureType.MONSTER, false)
                  && Team.checkIsOpponent(enemyTo, entitylivingbasex)
                  && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasex)
                  && entitylivingbasex.getHealth() > 0.0F) {
                  double dist = entitylivingbasex.getDistance(x, y, z);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbasex;
                  }
               }
            }

            if (targ == null) {
               for (EntityLivingBase entitylivingbasexx : list) {
                  if (Team.checkIsOpponent(enemyTo, entitylivingbasexx)
                     && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasexx)
                     && entitylivingbasexx.getHealth() > 0.0F) {
                     double dist = entitylivingbasexx.getDistance(x, y, z);
                     if (dist < max) {
                        max = dist;
                        targ = entitylivingbasexx;
                     }
                  }
               }
            }
         }
      }

      return targ;
   }

   public static List<EntityLivingBase> getHostilesInAABBto(
      World world, Vec3d forPosition, double radiusXZ, double radiusY, @Nullable EntityLivingBase hostileTo, @Nullable Entity entityExluding
   ) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.x - radiusXZ,
         forPosition.y - radiusY,
         forPosition.z - radiusXZ,
         forPosition.x + radiusXZ,
         forPosition.y + radiusY,
         forPosition.z + radiusXZ
      );
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
      List<EntityLivingBase> list2 = new ArrayList<>();

      for (EntityLivingBase entity : list) {
         if (Team.checkIsOpponent(hostileTo, entity)) {
            list2.add(entity);
         }
      }

      return list2;
   }

   public static List<Entity> getEntitiesInAABBof(World world, Entity forPosition, double radius, @Nullable Entity entityExluding) {
      Vec3d cenVec3d = entityCenterPos(forPosition);
      AxisAlignedBB bb = new AxisAlignedBB(
         cenVec3d.x - radius,
         cenVec3d.y - radius,
         cenVec3d.z - radius,
         cenVec3d.x + radius,
         cenVec3d.y + radius,
         cenVec3d.z + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExluding, bb);
   }

   public static List<Entity> getEntitiesInAABBof(World world, Vec3d forPosition, double radius, @Nullable Entity entityExluding) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.x - radius,
         forPosition.y - radius,
         forPosition.z - radius,
         forPosition.x + radius,
         forPosition.y + radius,
         forPosition.z + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExluding, bb);
   }

   public static List<Entity> getEntitiesInAABBof(World world, BlockPos forPosition, double radius, @Nullable Entity entityExluding) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.getX() - radius,
         forPosition.getY() - radius,
         forPosition.getZ() - radius,
         forPosition.getX() + 1 + radius,
         forPosition.getY() + 1 + radius,
         forPosition.getZ() + 1 + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExluding, bb);
   }

   public static double getSpeed(Entity entity) {
      return Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
   }

   public static AxisAlignedBB newAABB(Vec3d center, double radius) {
      return new AxisAlignedBB(
         center.x - radius,
         center.y - radius,
         center.z - radius,
         center.x + radius,
         center.y + radius,
         center.z + radius
      );
   }

   public static AxisAlignedBB newAABB(Entity center, double radius) {
      return new AxisAlignedBB(
         center.posX - radius,
         center.posY - radius,
         center.posZ - radius,
         center.posX + radius,
         center.posY + radius,
         center.posZ + radius
      );
   }

   public static AxisAlignedBB newAABB(int pixelsWidth, int pixelsHeight, int pixelsYoffset) {
      double xradius = 0.03125 * pixelsWidth;
      double height = 0.0625 * pixelsHeight;
      double y = 0.0625 * pixelsYoffset;
      return new AxisAlignedBB(0.5 - xradius, y, 0.5 - xradius, 0.5 + xradius, y + height, 0.5 + xradius);
   }

   public static List<Entity> entityUncollidedRayTrace(
      Class<? extends Entity> eclass,
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = PitchYawToVec3d(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findUncollidedEntityOnPath(eclass, vec3d, vec3d2, entity.world, entity, size, step);
   }

   protected static List<Entity> findUncollidedEntityOnPath(
      Class<? extends Entity> eclass, Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep
   ) {
      Vec3d FromStartToEnd = end.subtract(start);
      Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
      List<Entity> moblist = new ArrayList<>();
      double step = raystep / FromStartToEnd.length();

      for (double k = 0.0; k <= 1.0; k += step) {
         Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
         Vec3d DownVertex = CenterVertex.subtract(ToVertex);
         Vec3d UpVertex = CenterVertex.add(ToVertex);
         AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
         List<Entity> list = world.getEntitiesWithinAABB(eclass, Cube);
         if (!list.isEmpty()) {
            for (Entity entityliving : list) {
               if (entityliving != shooter) {
                  moblist.add(entityliving);
                  return moblist;
               }
            }
         }
      }

      return moblist;
   }

   @Nonnull
   public static RayTraceResult rayTrace(World world, double blockReachDistance, EntityLivingBase entity, boolean useLiquids) {
      float f = entity.rotationPitch;
      float f1 = entity.rotationYaw;
      double d0 = entity.posX;
      double d1 = entity.posY + entity.getEyeHeight();
      double d2 = entity.posZ;
      Vec3d vec3d = new Vec3d(d0, d1, d2);
      float f2 = MathHelper.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
      float f5 = MathHelper.sin(-f * (float) (Math.PI / 180.0));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      Vec3d vec3d1 = vec3d.add(f6 * blockReachDistance, f5 * blockReachDistance, f7 * blockReachDistance);
      RayTraceResult res = normalizeRayTraceResult(rayTraceBlocks(world, vec3d, vec3d1, null, useLiquids, true, false));
      return res != null ? res : new RayTraceResult(Type.MISS, vec3d1, null, new BlockPos(vec3d1));
   }

   @Nullable
   public static Entity loadEntity(World world, int chunkX, int chunkZ, UUID entityUuid) {
      ClassInheritanceMultiMap<Entity>[] entityLists = world.getChunk(chunkX, chunkZ).getEntityLists();

      for (int k = 0; k < entityLists.length; k++) {
         if (!entityLists[k].isEmpty()) {
            for (Entity entity : entityLists[k]) {
               if (entityUuid.equals(entity.getUniqueID())) {
                  return entity;
               }
            }
         }
      }

      return null;
   }

   @Nullable
   public static Entity loadEntity(World world, ChunkPos chunk, UUID entityUuid) {
      return loadEntity(world, chunk.x, chunk.z, entityUuid);
   }

   public static int byWeight(Random rand, int... weights) {
      int summ = 0;

      for (int i = 0; i < weights.length; i++) {
         summ += weights[i];
      }

      return byWeight(summ, rand, weights);
   }

   public static int byWeight(int summ, Random rand, int... weights) {
      int r = rand.nextInt(summ);
      int all = 0;

      for (int i = 0; i < weights.length; i++) {
         int weight = weights[i];
         all += weight;
         if (r < all) {
            return i;
         }
      }

      return -1;
   }

   @Nullable
   public static RayTraceResult normalizeRayTraceResult(@Nullable RayTraceResult sourceResult, double offset) {
      if (sourceResult != null && sourceResult.sideHit != null && sourceResult.hitVec != null) {
         Vec3d vec = sourceResult.hitVec;
         Vec3d newvec = new Vec3d(
            vec.x + sourceResult.sideHit.getXOffset() * offset,
            vec.y + sourceResult.sideHit.getYOffset() * offset,
            vec.z + sourceResult.sideHit.getZOffset() * offset
         );
         sourceResult.hitVec = newvec;
      }

      return sourceResult;
   }

   @Nullable
   public static RayTraceResult normalizeRayTraceResult(@Nullable RayTraceResult sourceResult) {
      return normalizeRayTraceResult(sourceResult, 0.001);
   }

   public static double isPointInPlane(Vec3d planePoint, Vec3d normalVector, Vec3d point) {
      return normalVector.x * (point.x - planePoint.x)
         + normalVector.y * (point.y - planePoint.y)
         + normalVector.z * (point.z - planePoint.z);
   }

   public static boolean isBoxInPlane(Vec3d planePoint, Vec3d normalVector, AxisAlignedBB aabb) {
      double sign1 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ));
      double sign2 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.minX, aabb.minY, aabb.minZ));
      double sign3 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ));
      double sign4 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.minX, aabb.maxY, aabb.minZ));
      double sign5 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ));
      double sign6 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.minX, aabb.minY, aabb.maxZ));
      double sign7 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.maxX, aabb.minY, aabb.minZ));
      double sign8 = isPointInPlane(planePoint, normalVector, new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ));
      return sign1 > 0.0 && sign2 > 0.0 && sign3 > 0.0 && sign4 > 0.0 && sign5 > 0.0 && sign6 > 0.0 && sign7 > 0.0 && sign8 > 0.0
         ? false
         : !(sign1 < 0.0) || !(sign2 < 0.0) || !(sign3 < 0.0) || !(sign4 < 0.0) || !(sign5 < 0.0) || !(sign6 < 0.0) || !(sign7 < 0.0) || !(sign8 < 0.0);
   }

   public static Vec3d getNearestPointInLineToPoint(Vec3d linePoint, Vec3d lineDirection, Vec3d point) {
      double Xc = point.x;
      double Yc = point.y;
      double Zc = point.z;
      double Xa = linePoint.x;
      double Ya = linePoint.y;
      double Za = linePoint.z;
      double Ax = lineDirection.x;
      double Ay = lineDirection.y;
      double Az = lineDirection.z;
      double tmin = (Ax * (Xc - Xa) + Ay * (Yc - Ya) + Az * (Zc - Za)) / (Ax * Ax + Ay * Ay + Az * Az);
      double X = Xa + Ax * tmin;
      double Y = Ya + Ay * tmin;
      double Z = Za + Az * tmin;
      return new Vec3d(X, Y, Z);
   }

   public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f = yawOffset - prevYawOffset;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   public static double linearDistance(Vec3d vec1, Vec3d vec2, Axis axis) {
      if (axis == Axis.X) {
         return Math.abs(vec1.x - vec2.x);
      } else {
         return axis == Axis.Y ? Math.abs(vec1.y - vec2.y) : Math.abs(vec1.z - vec2.z);
      }
   }

   public static float flatDistance(Vec3d vec1, Vec3d vec2) {
      float f = (float)(vec1.x - vec2.x);
      float f2 = (float)(vec1.z - vec2.z);
      return MathHelper.sqrt(f * f + f2 * f2);
   }

   public static EnumFacing getRandomFacingExcept(@Nullable EnumFacing except) {
      int randi = rand.nextInt(6);

      for (int i = 0; i < 6; i++) {
         EnumFacing getted = EnumFacing.byIndex(randi);
         if (getted != except) {
            return getted;
         }

         randi = next(randi, 1, 6);
      }

      return EnumFacing.SOUTH;
   }

   public static List<Entity> getEntitiesWithinAABBExcludingEntity(World world, Entity excluding, AxisAlignedBB aabb) {
      return world.getEntitiesWithinAABBExcludingEntity(excluding, aabb);
   }

   public static float isInCaves(World world, BlockPos position) {
      int airs = 0;

      for (int radius = 3; radius < 10; radius += 5) {
         double radius2 = Math.sqrt(radius * radius / 2.0);
         if (world.isAirBlock(position.east(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.west(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.north(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.south(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.add(radius2, 0.0, radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(-radius2, 0.0, radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(radius2, 0.0, -radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(-radius2, 0.0, -radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.up(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.add(0.0, radius2, radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(0.0, radius2, -radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(radius2, radius2, 0.0))) {
            airs++;
         }

         if (world.isAirBlock(position.add(-radius2, radius2, 0.0))) {
            airs++;
         }

         if (world.isAirBlock(position.down(radius))) {
            airs++;
         }

         if (world.isAirBlock(position.add(0.0, -radius2, radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(0.0, -radius2, -radius2))) {
            airs++;
         }

         if (world.isAirBlock(position.add(radius2, -radius2, 0.0))) {
            airs++;
         }

         if (world.isAirBlock(position.add(-radius2, -radius2, 0.0))) {
            airs++;
         }
      }

      return 1.0F - airs / 36.0F;
   }

   public static Random getBestRandomBasedOnCoordinates(int coord1, int coord2, long coord3) {
      Random rand1 = new Random(coord3 ^ coord1 ^ coord2);

      for (int i = 0; i < coord1 % 5; i++) {
         rand1.nextInt();
      }

      for (int i = 0; i < coord2 % 5; i++) {
         rand1.nextInt();
      }

      for (int i = 0; i < coord3 % 5L; i++) {
         rand1.nextInt();
      }

      int rint1 = rand1.nextInt();
      Random rand2 = new Random(rint1);
      int exit = 0;

      for (int i = 0; i < 32; i++) {
         exit |= doSomeWithBits(coord1, coord2, i, rand2.nextInt(6));
      }

      Random rand3 = new Random(exit);
      Random rand4 = new Random(coord1);
      Random rand5 = new Random(coord2);
      exit = 0;

      for (int i = 0; i < 32; i++) {
         exit |= doSomeWithBits(rand4.nextInt(), rand3.nextInt(), i, rand5.nextInt(6));
      }

      rand3 = new Random(exit);
      exit = 0;

      for (int i = 0; i < 32; i++) {
         exit |= doSomeWithBits(rand3.nextInt(), rand5.nextInt(), i, rand4.nextInt(6));
      }

      return new Random(exit);
   }

   private static int doSomeWithBits(int number1, int number2, int bitOffset, int operation) {
      int stencil = 1 << bitOffset;
      int exit = 0;
      if (operation == 0) {
         exit = number1 & number2;
      }

      if (operation == 1) {
         exit = number1 | number2;
      }

      if (operation == 2) {
         exit = number1 ^ number2;
      }

      if (operation == 3) {
         exit = ~(number1 | number2);
      }

      if (operation == 4) {
         exit = ~(number1 ^ number2);
      }

      if (operation == 5) {
         exit = ~(number1 & number2);
      }

      return exit & stencil;
   }

   public static class BlockTraceResult {
      public BlockPos prevPos;
      public BlockPos pos;
      public IBlockState impactState;
      public IBlockState prevState;
      public EnumFacing facing;

      @Override
      public String toString() {
         return "prevPos: "
            + this.prevPos
            + " | pos: "
            + this.pos
            + " | prevState: "
            + this.prevState.getBlock().getRegistryName()
            + " | impactState: "
            + this.impactState.getBlock().getRegistryName();
      }
   }
}

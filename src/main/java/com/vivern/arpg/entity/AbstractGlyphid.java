package com.vivern.arpg.entity;

import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.EntityAIGlyphid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractGlyphid extends AbstractMob implements EntityFlying {
   public EntityAIGlyphid ai;
   public int currentCost = 999999;
   public boolean pathDepecated = false;
   public EnumClimbingState currentState = EnumClimbingState.NONE;
   public float angleX = 0.0F;
   public float angleY = 0.0F;
   public float angleZ = 0.0F;
   public float angle = 0.0F;
   public float anglevec = 0.0F;
   public float renderangleX = 0.0F;
   public float renderangleY = 0.0F;
   public float renderangleZ = 0.0F;
   public float renderangle = 0.0F;
   public float renderanglevec = 0.0F;
   public float prevrenderangleX = 0.0F;
   public float prevrenderangleY = 0.0F;
   public float prevrenderangleZ = 0.0F;
   public float prevrenderangle = 0.0F;
   public float prevrenderanglevec = 0.0F;

   public AbstractGlyphid(World worldIn, float sizeWidth, float sizeHeight) {
      super(worldIn, sizeWidth, sizeHeight);
   }

   public boolean isAiCompatible(EntityAIGlyphid ai) {
      return ai.glyphidAiType == 0;
   }

   public boolean checkPosition(World world, BlockPos pos) {
      if (this.isPassable(world, pos)) {
         if (this.hasCollision(world, pos.add(0, 1, 0))
            || this.hasCollision(world, pos.add(0, -1, 0))
            || this.hasCollision(world, pos.add(-1, 0, 0))
            || this.hasCollision(world, pos.add(1, 0, 0))
            || this.hasCollision(world, pos.add(0, 0, -1))
            || this.hasCollision(world, pos.add(0, 0, 1))) {
            return true;
         }

         if (this.hasCollision(world, pos.add(1, 0, 1))
            || this.hasCollision(world, pos.add(-1, 0, -1))
            || this.hasCollision(world, pos.add(1, 0, -1))
            || this.hasCollision(world, pos.add(-1, 0, 1))
            || this.hasCollision(world, pos.add(-1, 1, 0))
            || this.hasCollision(world, pos.add(1, 1, 0))
            || this.hasCollision(world, pos.add(-1, -1, 0))
            || this.hasCollision(world, pos.add(1, -1, 0))
            || this.hasCollision(world, pos.add(0, 1, -1))
            || this.hasCollision(world, pos.add(0, 1, 1))
            || this.hasCollision(world, pos.add(0, -1, -1))
            || this.hasCollision(world, pos.add(0, -1, 1))) {
            return true;
         }
      }

      return false;
   }

   public boolean hasCollision(World world, BlockPos pos) {
      IBlockState state = world.getBlockState(pos);
      PathNodeType nodetype = state.getBlock().getAiPathNodeType(state, world, pos, this);
      return nodetype != PathNodeType.DAMAGE_FIRE && nodetype != PathNodeType.LAVA ? state.getCollisionBoundingBox(world, pos) != null : false;
   }

   public boolean isPassable(World world, BlockPos pos) {
      IBlockState state = world.getBlockState(pos);
      PathNodeType nodetype = state.getBlock().getAiPathNodeType(state, world, pos, this);
      return nodetype != PathNodeType.DAMAGE_FIRE && nodetype != PathNodeType.DAMAGE_OTHER && nodetype != PathNodeType.LAVA
         ? state.getCollisionBoundingBox(world, pos) == null
         : false;
   }

   public static float addRenderValue(float a, float target, float amount) {
      if (target > a) {
         return a + Math.min(target - a, amount);
      } else {
         return target < a ? a - Math.min(a - target, amount) : a;
      }
   }

   public static float addRenderAngle(float a, float target, float amount) {
      float fun = (MathHelper.wrapDegrees(target) - MathHelper.wrapDegrees(a) + 360.0F) % 360.0F;
      float derection;
      if (fun < 180.0F) {
         derection = MathHelper.clamp(Math.abs(fun), 0.0F, amount);
      } else {
         derection = -MathHelper.clamp(Math.abs(fun), 0.0F, amount);
      }

      return a + derection;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.pathDepecated = true;
      if (this.world.isRemote) {
         this.prevrenderangleX = this.renderangleX;
         this.prevrenderangleY = this.renderangleY;
         this.prevrenderangleZ = this.renderangleZ;
         this.prevrenderangle = this.renderangle;
         this.prevrenderanglevec = this.renderanglevec;
         this.renderangleX = addRenderValue(this.renderangleX, this.angleX, 0.2F);
         this.renderangleY = addRenderValue(this.renderangleY, this.angleY, 0.2F);
         this.renderangleZ = addRenderValue(this.renderangleZ, this.angleZ, 0.2F);
         this.renderangle = addRenderValue(this.renderangle, this.angle, 10.0F);
         this.renderanglevec = addRenderAngle(this.renderanglevec, this.anglevec, 10.0F);
         this.currentState = this.getClimbingState();
         double atan = Math.atan2(this.motionX, this.motionZ);
         float add = 0.0F;
         switch (this.currentState) {
            case ZPLUS:
               this.angleX = 1.0F;
               this.angle = 90.0F;
               this.angleZ = 0.0F;
               atan = Math.atan2(this.motionX, this.motionY);
               break;
            case ZMINUS:
               this.angleX = -1.0F;
               this.angle = 90.0F;
               this.angleZ = 0.0F;
               atan = Math.atan2(this.motionX, -this.motionY);
               break;
            case XPLUS:
               this.angleX = 0.0F;
               this.angle = 90.0F;
               this.angleZ = 1.0F;
               add = 90.0F;
               atan = Math.atan2(this.motionZ, -this.motionY);
               break;
            case XMINUS:
               this.angleX = 0.0F;
               this.angle = 90.0F;
               this.angleZ = -1.0F;
               add = 90.0F;
               atan = Math.atan2(this.motionZ, this.motionY);
               break;
            case UP:
               this.angleX = 1.0F;
               this.angle = 180.0F;
               this.angleZ = 0.0F;
               atan = Math.atan2(this.motionX, -this.motionZ);
               break;
            case DOWN:
               this.angleX = 1.0F;
               this.angle = 0.0F;
               this.angleZ = 0.0F;
               break;
            case DOWN_XPLUS:
               this.angleX = 0.5F;
               this.angle = 90.0F;
               this.angleZ = 0.5F;
               break;
            case DOWN_XMINUS:
               this.angleX = 0.5F;
               this.angle = 90.0F;
               this.angleZ = -0.5F;
               break;
            case DOWN_ZPLUS:
               this.angleX = 0.5F;
               this.angle = 90.0F;
               this.angleZ = 1.0F;
               break;
            case DOWN_ZMINUS:
               this.angleX = -0.5F;
               this.angle = 90.0F;
               this.angleZ = -1.0F;
               break;
            case UP_XPLUS:
               this.angleX = 0.5F;
               this.angle = 135.0F;
               this.angleZ = 0.5F;
               break;
            case UP_XMINUS:
               this.angleX = 0.5F;
               this.angle = 135.0F;
               this.angleZ = -0.5F;
               break;
            case UP_ZPLUS:
               this.angleX = 0.5F;
               this.angle = 135.0F;
               this.angleZ = 0.0F;
               break;
            case UP_ZMINUS:
               this.angleX = -0.5F;
               this.angle = 135.0F;
               this.angleZ = 0.0F;
               break;
            case XPLUS_ZPLUS:
               this.angleX = 0.5F;
               this.angle = 90.0F;
               this.angleZ = 1.0F;
               atan = Math.atan2((this.motionX + this.motionZ) / 2.0, this.motionY);
               break;
            case XMINUS_ZPLUS:
               this.angleX = 0.5F;
               this.angle = 90.0F;
               this.angleZ = -1.0F;
               atan = Math.atan2((this.motionX + this.motionZ) / 2.0, this.motionY);
               break;
            case XPLUS_ZMINUS:
               this.angleX = -0.5F;
               this.angle = 90.0F;
               this.angleZ = 0.0F;
               atan = Math.atan2((this.motionX + this.motionZ) / 2.0, this.motionY);
               break;
            case XMINUS_ZMINUS:
               this.angleX = -0.5F;
               this.angle = 90.0F;
               this.angleZ = 0.0F;
               atan = Math.atan2((this.motionX + this.motionZ) / 2.0, this.motionY);
               break;
            default:
               this.angleX = 1.0F;
               this.angleY = 0.0F;
               this.angleZ = 0.0F;
               this.angle = 0.0F;
         }

         this.anglevec = (float)(-(atan / Math.PI * 180.0)) + 180.0F + add;
      }
   }

   public void setPath(EntityAIGlyphid.PathPointGlyphid point) {
      if (this.ai != null) {
         this.pathDepecated = false;
         this.ai.path = point;
         this.currentCost = point.cost;
      }
   }

   public boolean hasPath() {
      return this.ai != null && this.ai.path != null;
   }

   public EnumClimbingState getClimbingState() {
      BlockPos pos = this.getPosition();
      if (this.hasCollision(this.world, pos.add(0, 1, 0))) {
         return EnumClimbingState.UP;
      } else if (this.hasCollision(this.world, pos.add(0, -1, 0))) {
         return EnumClimbingState.DOWN;
      } else if (this.hasCollision(this.world, pos.add(-1, 0, 0))) {
         return EnumClimbingState.XMINUS;
      } else if (this.hasCollision(this.world, pos.add(1, 0, 0))) {
         return EnumClimbingState.XPLUS;
      } else if (this.hasCollision(this.world, pos.add(0, 0, -1))) {
         return EnumClimbingState.ZMINUS;
      } else if (this.hasCollision(this.world, pos.add(0, 0, 1))) {
         return EnumClimbingState.ZPLUS;
      } else if (this.hasCollision(this.world, pos.add(1, 0, 1))) {
         return EnumClimbingState.XPLUS_ZPLUS;
      } else if (this.hasCollision(this.world, pos.add(-1, 0, -1))) {
         return EnumClimbingState.XMINUS_ZMINUS;
      } else if (this.hasCollision(this.world, pos.add(1, 0, -1))) {
         return EnumClimbingState.XPLUS_ZMINUS;
      } else if (this.hasCollision(this.world, pos.add(-1, 0, 1))) {
         return EnumClimbingState.XMINUS_ZPLUS;
      } else if (this.hasCollision(this.world, pos.add(-1, 1, 0))) {
         return EnumClimbingState.UP_XMINUS;
      } else if (this.hasCollision(this.world, pos.add(1, 1, 0))) {
         return EnumClimbingState.UP_XPLUS;
      } else if (this.hasCollision(this.world, pos.add(-1, -1, 0))) {
         return EnumClimbingState.DOWN_XMINUS;
      } else if (this.hasCollision(this.world, pos.add(1, -1, 0))) {
         return EnumClimbingState.DOWN_XPLUS;
      } else if (this.hasCollision(this.world, pos.add(0, 1, -1))) {
         return EnumClimbingState.UP_ZMINUS;
      } else if (this.hasCollision(this.world, pos.add(0, 1, 1))) {
         return EnumClimbingState.UP_ZPLUS;
      } else if (this.hasCollision(this.world, pos.add(0, -1, -1))) {
         return EnumClimbingState.DOWN_ZMINUS;
      } else {
         return this.hasCollision(this.world, pos.add(0, -1, 1))
            ? EnumClimbingState.DOWN_ZPLUS
            : EnumClimbingState.NONE;
      }
   }

   public static enum EnumClimbingState {
      DOWN,
      UP,
      XPLUS,
      XMINUS,
      ZPLUS,
      ZMINUS,
      DOWN_XPLUS,
      DOWN_XMINUS,
      DOWN_ZPLUS,
      DOWN_ZMINUS,
      UP_XPLUS,
      UP_XMINUS,
      UP_ZPLUS,
      UP_ZMINUS,
      XPLUS_ZPLUS,
      XMINUS_ZPLUS,
      XPLUS_ZMINUS,
      XMINUS_ZMINUS,
      NONE;
   }
}

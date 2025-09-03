//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.BlockRunicMirror;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.recipes.EnergyCost;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class TileRunicMirror extends TileEntity implements ITickable, IVialElementsAccepter, IMagicVision, ITileEntitySynchronize {
   public ShardType elementType = null;
   public float elementCollected = 0.0F;
   public float maxElementCollected = 32.0F;
   public float rotationPitch = 0.0F;
   public float rotationYaw = 0.0F;
   public float motionPitch = 0.0F;
   public float motionYaw = 0.0F;
   public float rendrotationPitch = 0.0F;
   public float rendrotationYaw = 0.0F;
   public float prevrendrotationPitch = 0.0F;
   public float prevrendrotationYaw = 0.0F;
   public AxisAlignedBB renderAABB;
   public float lastTraceLength = 0.0F;
   public Vec3d clientBeamLook = Vec3d.ZERO;
   public MovingSoundTileEntity beamSound;
   public int ticksSoundPlays;

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.elementCollected = (float)args[0];
      }
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return this.renderAABB == null ? INFINITE_EXTENT_AABB : this.renderAABB;
   }

   @Override
   public float acceptVialElements(ItemStack vial_or_spellRod, ShardType shardType, float count) {
      if (!this.isEmpty() && this.elementType != shardType) {
         return count;
      } else {
         this.elementType = shardType;
         if (count > 0.0F) {
            float add = Math.min(this.maxElementCollected - this.elementCollected, count);
            this.elementCollected += add;
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            return count - add;
         } else {
            float remove = Math.max(-this.elementCollected, count);
            this.elementCollected += remove;
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            return count - remove;
         }
      }
   }

   @Override
   public float getElementCount(ShardType shardType) {
      return shardType == this.elementType ? this.elementCollected : 0.0F;
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return this.getElementCount(shardType);
   }

   @Override
   public float getMana() {
      return 0.0F;
   }

   public boolean isEmpty() {
      return this.elementType == null || this.elementCollected <= 0.0F;
   }

   public void rotateMirror(BlockPos targetPos) {
      Vec3d thispos = new Vec3d(
         this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5
      );
      Vec3d targpos = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
      double angleYaw = GetMOP.getAngleBetweenVectors(0.0, 1.0, targpos.x - thispos.x, targpos.z - thispos.z);
      double anglePitch = GetMOP.getAngleBetweenVectors(
            0.0,
            1.0,
            0.0,
            targpos.x - thispos.x,
            targpos.y - thispos.y,
            targpos.z - thispos.z
         )
         - 90.0;
      if (targpos.x - thispos.x > 0.0) {
         angleYaw = -angleYaw;
      }

      this.rotationPitch = (float)anglePitch;
      this.rotationYaw = (float)angleYaw;
      PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
   }

   public void rotateMirror(float anglePitch, float angleYaw) {
      this.rotationPitch = anglePitch;
      this.rotationYaw = MathHelper.wrapDegrees(angleYaw);
      PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
   }

   public void update() {
      this.prevrendrotationPitch = this.rendrotationPitch;
      this.prevrendrotationYaw = this.rendrotationYaw;
      this.rendrotationPitch = this.rendrotationPitch + this.motionPitch;
      this.rendrotationYaw = this.rendrotationYaw + this.motionYaw;
      this.motionPitch *= 0.93F;
      this.motionYaw *= 0.93F;
      if (this.rotationPitch < this.rendrotationPitch) {
         this.motionPitch = (float)(this.motionPitch - Math.min((double)(this.rendrotationPitch - this.rotationPitch), 0.5));
      }

      if (this.rotationPitch > this.rendrotationPitch) {
         this.motionPitch = (float)(this.motionPitch + Math.min((double)(this.rotationPitch - this.rendrotationPitch), 0.5));
      }

      if (this.rotationYaw < this.rendrotationYaw) {
         this.motionYaw = (float)(this.motionYaw - Math.min((double)(this.rendrotationYaw - this.rotationYaw), 0.5));
      }

      if (this.rotationYaw > this.rendrotationYaw) {
         this.motionYaw = (float)(this.motionYaw + Math.min((double)(this.rotationYaw - this.rendrotationYaw), 0.5));
      }

      if (this.world.isRemote) {
         if (!this.isEmpty()) {
            this.clientBeamLook = GetMOP.PitchYawToVec3d(this.rendrotationPitch, this.rendrotationYaw);
            if (GetMOP.rand.nextFloat() < 0.3) {
               this.spawnParticleAtMirror();
            }

            this.ticksSoundPlays++;
            if (this.beamSound == null) {
               this.beamSound = new MovingSoundTileEntity(this, this.elementType.getLoopedBeamSound(), SoundCategory.BLOCKS, 1.0F, 1.0F, true, 0);
               this.ticksSoundPlays = 0;
               Minecraft.getMinecraft().getSoundHandler().playSound(this.beamSound);
            } else if (this.ticksSoundPlays > 16) {
               this.ticksSoundPlays = 0;
               if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.beamSound)) {
                  this.beamSound.stop = true;
                  this.beamSound = new MovingSoundTileEntity(this, this.elementType.getLoopedBeamSound(), SoundCategory.BLOCKS, 1.0F, 1.0F, true, 0);
                  Minecraft.getMinecraft().getSoundHandler().playSound(this.beamSound);
               } else if (this.beamSound.sound != this.elementType.getLoopedBeamSound()) {
                  this.beamSound.stop = true;
                  this.beamSound = null;
                  this.ticksSoundPlays = 0;
               }
            }
         } else if (this.beamSound != null) {
            this.beamSound.stop = true;
            this.beamSound = null;
            this.ticksSoundPlays = 0;
         }
      }

      if (this.world.getWorldTime() % ManaBuffer.TICKRATE == 0L) {
         ManaBuffer.Calibration mirrorCalibration = null;
         if (!this.world.isRemote) {
            ITileEntitySynchronize.sendSynchronize(this, 64.0, this.elementCollected);
         }

         if (this.elementCollected > 0.0F) {
            float prevCollected = this.elementCollected;
            Vec3d start = new Vec3d(
               this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5
            );
            Vec3d end = start.add(GetMOP.PitchYawToVec3d(this.rendrotationPitch, this.rendrotationYaw).scale(20.0));
            RayTraceResult result = fixedRayTraceBlocks(
               this.world, this.getPos(), null, 0.8, 0.8, false, start, end, this.elementType != ShardType.WATER, true, false
            );
            if (result != null) {
               if (!this.world.isRemote) {
                  mirrorCalibration = ManaBuffer.getCalibrationAt(this.world, this.getPos());
                  if (result.typeOfHit == Type.BLOCK) {
                     TileEntity tileEntity = this.world.getTileEntity(result.getBlockPos());
                     if (tileEntity != null && tileEntity instanceof IVialElementsAccepter) {
                        float flowSpeed = this.getMirrorFlowSpeed(result.typeOfHit, true, mirrorCalibration);
                        float amount = Math.min(flowSpeed, this.elementCollected);
                        IVialElementsAccepter accepter = (IVialElementsAccepter)tileEntity;
                        this.elementCollected -= amount;
                        this.elementCollected = this.elementCollected + accepter.acceptVialElements(ItemStack.EMPTY, this.elementType, amount);
                     } else {
                        float flowSpeed = this.getMirrorFlowSpeed(result.typeOfHit, false, mirrorCalibration);
                        float amount = Math.min(flowSpeed, this.elementCollected);
                        this.elementType.onAffectBlock(this, this.world, result.getBlockPos(), start, amount);
                        this.elementCollected -= amount;
                     }
                  } else if (result.typeOfHit == Type.ENTITY) {
                     float flowSpeed = this.getMirrorFlowSpeed(result.typeOfHit, false, mirrorCalibration);
                     float amount = Math.min(flowSpeed, this.elementCollected);
                     this.elementType.onAffectEntity(this, null, result.entityHit, start, amount);
                     this.elementCollected -= amount;
                  } else if (result.typeOfHit == Type.MISS) {
                     float flowSpeed = this.getMirrorFlowSpeed(result.typeOfHit, false, mirrorCalibration);
                     float amount = Math.min(flowSpeed, this.elementCollected);
                     this.elementCollected -= amount;
                  }
               } else {
                  this.renderAABB = new AxisAlignedBB(this.getPos()).union(GetMOP.newAABB(result.hitVec, 0.5));
                  this.lastTraceLength = (float)start.distanceTo(result.hitVec);
                  if (this.elementType != null && GetMOP.rand.nextFloat() < 0.2) {
                     this.elementType
                        .spawnNativeParticle(
                           this.world,
                           1.2F,
                           result.hitVec.x,
                           result.hitVec.y,
                           result.hitVec.z,
                           (GetMOP.rand.nextFloat() - 0.5) * 0.2,
                           (GetMOP.rand.nextFloat() - 0.5) * 0.2,
                           (GetMOP.rand.nextFloat() - 0.5) * 0.2,
                           true
                        );
                  }
               }
            }
         }

         if (!this.world.isRemote) {
            if (this.isEmpty()) {
               int f = GetMOP.rand.nextInt(6);

               for (int i = 0; i < 6; i++) {
                  EnumFacing facing = EnumFacing.byIndex(f);
                  BlockPos offpos = this.pos.offset(facing);
                  TileEntity tileEntity = this.world.getTileEntity(offpos);
                  if (tileEntity != null && tileEntity instanceof IVialElementsAccepter) {
                     IVialElementsAccepter elementsAccepter = (IVialElementsAccepter)tileEntity;
                     if (mirrorCalibration == null) {
                        mirrorCalibration = ManaBuffer.getCalibrationAt(this.world, this.getPos());
                     }

                     float drainAmount = this.getMirrorDrainSpeed(offpos, mirrorCalibration);
                     EnergyCost cost = elementsAccepter.provideVialElements(ItemStack.EMPTY, drainAmount, false);
                     if (cost != null) {
                        this.acceptVialElements(ItemStack.EMPTY, cost.type, cost.amount);
                        break;
                     }
                  }

                  f = GetMOP.next(f, 1, 6);
               }
            } else {
               int f = GetMOP.rand.nextInt(6);

               for (int i = 0; i < 6; i++) {
                  EnumFacing facing = EnumFacing.byIndex(f);
                  BlockPos offpos = this.pos.offset(facing);
                  TileEntity tileEntity = this.world.getTileEntity(offpos);
                  if (tileEntity != null && tileEntity instanceof IVialElementsAccepter) {
                     IVialElementsAccepter elementsAccepterx = (IVialElementsAccepter)tileEntity;
                     if (elementsAccepterx.getElementCount(this.elementType) > 0.0F) {
                        if (mirrorCalibration == null) {
                           mirrorCalibration = ManaBuffer.getCalibrationAt(this.world, this.getPos());
                        }

                        float drainAmount = this.getMirrorDrainSpeed(offpos, mirrorCalibration);
                        EnergyCost cost = elementsAccepterx.provideVialElements(ItemStack.EMPTY, drainAmount, false);
                        if (cost != null) {
                           if (cost.type == this.elementType) {
                              this.acceptVialElements(ItemStack.EMPTY, cost.type, cost.amount);
                           } else {
                              elementsAccepterx.acceptVialElements(ItemStack.EMPTY, cost.type, cost.amount);
                           }
                        }
                     }
                  }

                  f = GetMOP.next(f, 1, 6);
               }
            }
         }
      }
   }

   public float getMirrorFlowSpeed(Type typeOfHit, boolean accepter, ManaBuffer.Calibration mirrorCalibration) {
      float mult = 1.0F + mirrorCalibration.speed * 0.5F;
      if (accepter) {
         return 0.1F * ManaBuffer.TICKRATE * mult;
      } else if (typeOfHit == Type.BLOCK) {
         return 0.01F * ManaBuffer.TICKRATE * mult;
      } else {
         return typeOfHit == Type.ENTITY ? 0.1F * ManaBuffer.TICKRATE * mult : 0.05F * ManaBuffer.TICKRATE * mult;
      }
   }

   public float getMirrorDrainSpeed(BlockPos drainFrom, ManaBuffer.Calibration mirrorCalibration) {
      ManaBuffer.Calibration otherCalibration = ManaBuffer.getCalibrationAt(this.world, drainFrom);
      float mult = 1.0F + Math.min(mirrorCalibration.speed, otherCalibration.speed) * 0.5F;
      return Math.min(0.1F * mult, this.maxElementCollected - this.elementCollected);
   }

   public void spawnParticleAtMirror() {
      if (this.elementType != null) {
         float randvalue1 = (GetMOP.rand.nextFloat() - 0.5F) * 8.0F * 0.0625F;
         float randvalue2 = (GetMOP.rand.nextFloat() - 0.5F) * 8.0F * 0.0625F;
         Vec3d vecV = GetMOP.PitchYawToVec3d(this.rendrotationPitch - 90.0F, this.rendrotationYaw).scale(randvalue1);
         Vec3d vecH = GetMOP.PitchYawToVec3d(this.rendrotationPitch, this.rendrotationYaw - 90.0F).scale(randvalue2);
         float grav = ShardType.particlesGravityMult;
         if (this.elementType == ShardType.DEATH) {
            ShardType.particlesGravityMult = 1.0F;
         }

         if (this.elementType == ShardType.EARTH) {
            ShardType.particlesGravityMult = 0.1F;
         } else {
            ShardType.particlesGravityMult = 0.0F;
         }

         this.elementType
            .spawnNativeParticle(
               this.world,
               this.elementType == ShardType.VOID ? 1.0F : 1.6F,
               this.pos.getX() + 0.5 + vecV.x + vecH.x + this.clientBeamLook.x * 0.25,
               this.pos.getY() + 1 + vecV.y + vecH.y + this.clientBeamLook.y * 0.25,
               this.pos.getZ() + 0.5 + vecV.z + vecH.z + this.clientBeamLook.z * 0.25,
               this.clientBeamLook.x / 5.0,
               this.clientBeamLook.y / 5.0,
               this.clientBeamLook.z / 5.0,
               true
            );
         ShardType.particlesGravityMult = grav;
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("elementType")) {
         this.elementType = ShardType.byId(compound.getByte("elementType"));
      }

      if (compound.hasKey("elementCollected")) {
         this.elementCollected = compound.getFloat("elementCollected");
      }

      if (compound.hasKey("pitch")) {
         this.rotationPitch = compound.getFloat("pitch");
      }

      if (compound.hasKey("yaw")) {
         this.rotationYaw = compound.getFloat("yaw");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (this.elementType != null) {
         compound.setByte("elementType", (byte)this.elementType.id);
      } else {
         compound.setByte("elementType", (byte)0);
      }

      compound.setFloat("elementCollected", this.elementCollected);
      compound.setFloat("pitch", this.rotationPitch);
      compound.setFloat("yaw", this.rotationYaw);
      return super.writeToNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      this.rendrotationPitch = this.rotationPitch;
      this.rendrotationYaw = this.rotationYaw;
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      BlockPos posToIgnore,
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
      RayTraceResult result = rayTraceBlocks(world, posToIgnore, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
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

      return GetMOP.normalizeRayTraceResult(result);
   }

   @Nullable
   public static RayTraceResult rayTraceBlocks(
      World world,
      BlockPos posToIgnore,
      Vec3d vec31,
      Vec3d vec32,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
         return null;
      } else if (!Double.isNaN(vec32.x) && !Double.isNaN(vec32.y) && !Double.isNaN(vec32.z)) {
         int startX = MathHelper.floor(vec31.x);
         int startY = MathHelper.floor(vec31.y);
         int startZ = MathHelper.floor(vec31.z);
         int i = MathHelper.floor(vec32.x);
         int j = MathHelper.floor(vec32.y);
         int k = MathHelper.floor(vec32.z);
         int l = startX;
         int i1 = startY;
         int j1 = startZ;
         BlockPos blockpos = new BlockPos(startX, startY, startZ);
         IBlockState iblockstate = world.getBlockState(blockpos);
         Block block = iblockstate.getBlock();
         if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB)
            && block.canCollideCheck(iblockstate, stopOnLiquid)) {
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
            BlockPos blockposDown = blockpos.down();
            IBlockState iblockstateDown = world.getBlockState(blockposDown);
            Block blockDown = iblockstateDown.getBlock();
            if ((
                  blockposDown.getX() != posToIgnore.getX()
                     || blockposDown.getY() != posToIgnore.getY()
                     || blockposDown.getZ() != posToIgnore.getZ()
               )
               && blockDown == BlocksRegister.RUNICMIRROR) {
               RayTraceResult raytraceresult1 = rayTrace(blockposDown, blockposDown, vec31, vec32, BlockRunicMirror.MIRROR_AABB);
               if (raytraceresult1 != null) {
                  return raytraceresult1;
               }
            }

            if ((
                  blockpos.getX() != posToIgnore.getX()
                     || blockpos.getY() != posToIgnore.getY()
                     || blockpos.getZ() != posToIgnore.getZ()
               )
               && block1 == BlocksRegister.RUNICMIRROR) {
               RayTraceResult raytraceresult1 = rayTrace(blockpos, blockpos, vec31, vec32, BlockRunicMirror.ALL_AABB3);
               if (raytraceresult1 != null) {
                  return raytraceresult1;
               }

               RayTraceResult raytraceresult3 = rayTrace(blockpos, blockpos, vec31, vec32, BlockRunicMirror.MIRROR_AABB);
               if (raytraceresult3 != null) {
                  return raytraceresult3;
               }
            }

            if (!ignoreBlockWithoutBoundingBox
               || iblockstate1.getMaterial() == Material.PORTAL
               || iblockstate1.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB) {
               if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
                  RayTraceResult raytraceresult1x = iblockstate1.collisionRayTrace(world, blockpos, vec31, vec32);
                  if (raytraceresult1x != null) {
                     return raytraceresult1x;
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

   @Nullable
   protected static RayTraceResult rayTrace(BlockPos tilepos, BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
      Vec3d vec3d = start.subtract(pos.getX(), pos.getY(), pos.getZ());
      Vec3d vec3d1 = end.subtract(pos.getX(), pos.getY(), pos.getZ());
      RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
      return raytraceresult == null
         ? null
         : new RayTraceResult(
            raytraceresult.hitVec.add(pos.getX(), pos.getY(), pos.getZ()), raytraceresult.sideHit, tilepos
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
               if (entityliving.canBeCollidedWith() || entityliving instanceof EntityItem) {
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
}

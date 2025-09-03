//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketManaBufferToClients;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ManaBuffer {
   public static final ResourceLocation tex = new ResourceLocation("arpg:textures/mana_flow.png");
   public static int TICKRATE = 5;
   @Nullable
   public TileEntity tileEntity;
   public IManaBuffer iBuffer;
   private float manaMaximum;
   private float manaStored;
   private boolean manaChangedFlag;
   public int initialFieldRadius;
   public float initialAttraction;
   public float initialFlowSpeed;
   public static NoiseGenerator3D noisegenerator3dX = new NoiseGenerator3D(1L);
   public static NoiseGenerator3D noisegenerator3dY = new NoiseGenerator3D(7L);
   public static NoiseGenerator3D noisegenerator3dZ = new NoiseGenerator3D(60L);
   public static ParticleTracker.TrackerTurbulence pt_turbulence = new ParticleTracker.TrackerTurbulence(
      noisegenerator3dX, noisegenerator3dY, noisegenerator3dZ, 1.0F, 0.04F
   );
   public static Random rand = new Random();

   public ManaBuffer(
      @Nullable TileEntity tileEntity, IManaBuffer iBuffer, float manaMaximum, int initialFieldRadius, float initialAttraction, float initialFlowSpeed
   ) {
      this.tileEntity = tileEntity;
      this.iBuffer = iBuffer;
      this.manaMaximum = manaMaximum;
      this.manaChangedFlag = true;
      this.initialFieldRadius = initialFieldRadius;
      this.initialAttraction = initialAttraction;
      this.initialFlowSpeed = initialFlowSpeed;
   }

   public float getManaStored() {
      return this.manaStored;
   }

   public float getManaStorageSize() {
      return this.manaMaximum;
   }

   public void setManaStorageSize(float maximum) {
      this.manaMaximum = maximum;
   }

   public void setMana(float mana) {
      if (this.manaStored != mana) {
         this.manaStored = mana;
         this.manaChangedFlag = true;
      }
   }

   public float getMana() {
      return this.manaStored;
   }

   public float addMana(float mana) {
      float stored = this.getManaStored();
      stored += mana;
      float manaStorageSize = this.getManaStorageSize();
      if (stored > manaStorageSize) {
         float ret = stored - manaStorageSize;
         this.setMana(manaStorageSize);
         return ret;
      } else if (stored < 0.0F) {
         this.setMana(0.0F);
         return stored;
      } else {
         this.setMana(stored);
         return 0.0F;
      }
   }

   public float provideMana(float manaNeed, BlockPos consumerPos) {
      if (this.iBuffer.canProvideMana()) {
         float toProvide = Math.min(manaNeed, this.getManaStored());
         this.addMana(-toProvide);
         return toProvide;
      } else {
         return 0.0F;
      }
   }

   public void updateManaBuffer(World world, BlockPos pos) {
      boolean debug = false;
      if (world.getWorldTime() % TICKRATE == 0L) {
         if (debug) {
            System.out.println("TICKRATE");
         }

         if (this.getManaStored() < this.getManaStorageSize()) {
            double distModifier = 0.0;
            float sizeMultiplier = 0.0F;
            if (debug) {
               System.out.println("getManaStored() < getManaStorageSize()");
            }

            if (world.isRemote && Minecraft.getMinecraft().getRenderViewEntity() != null) {
               distModifier = MathHelper.clamp(1.0 - Math.sqrt(Minecraft.getMinecraft().getRenderViewEntity().getDistanceSq(pos)) / 64.0, 0.0, 1.0);
               sizeMultiplier = 1.0F + 5.0F * (float)(1.0 - distModifier);
            }

            Calibration modifiers = getCalibrationAt(world, pos);
            float attraction = this.initialAttraction + modifiers.attraction;
            float floatradius = this.initialFieldRadius + modifiers.range;
            int radius = Math.max(MathHelper.ceil(floatradius), 0);
            float radiusSq = floatradius * floatradius;

            for (int xr = -radius; xr <= radius; xr++) {
               int xrSq = xr * xr;

               for (int yr = -radius; yr <= radius; yr++) {
                  int yrSq = yr * yr;

                  for (int zr = -radius; zr <= radius; zr++) {
                     int distSq = xrSq + yrSq + zr * zr;
                     if (distSq <= radiusSq && distSq != 0) {
                        BlockPos finalpos = new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ());
                        TileEntity tile = world.getTileEntity(finalpos);
                        if (tile instanceof IManaBuffer) {
                           if (debug) {
                              System.out.println("finded IManaBuffer " + finalpos);
                           }

                           IManaBuffer ibuffer = (IManaBuffer)tile;
                           ManaBuffer buffer = ibuffer.getManaBuffer();
                           Calibration anotherModifiers = getCalibrationAt(world, finalpos);
                           float anotherAttraction = buffer.initialAttraction + anotherModifiers.attraction;
                           if (anotherAttraction < attraction) {
                              if (debug) {
                                 System.out.println("anotherAttraction < attraction " + anotherAttraction + " | " + attraction);
                              }

                              boolean passwordAccessed = checkPasswords(modifiers, anotherModifiers, debug);
                              if (passwordAccessed) {
                                 if (debug) {
                                    System.out.println("passwordAccessed");
                                 }

                                 float manaNeed = Math.min(
                                    this.getManaStorageSize() - this.getManaStored(), (this.initialFlowSpeed + modifiers.speed) * TICKRATE
                                 );
                                 if (manaNeed == 0.0F) {
                                    return;
                                 }

                                 float manaGetted = buffer.provideMana(Math.min(manaNeed, (buffer.initialFlowSpeed + anotherModifiers.speed) * TICKRATE), pos);
                                 if (!world.isRemote) {
                                    this.addMana(manaGetted);
                                 }

                                 if (debug) {
                                    System.out.println("addMana " + manaGetted);
                                 }

                                 if (world.isRemote && manaGetted > 0.0F) {
                                    spawnFlowParticles(world, finalpos, pos, manaGetted, (int)(TICKRATE * distModifier / 2.0), sizeMultiplier);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (this.manaChangedFlag && !world.isRemote) {
            sendManaToClients(world, pos, this);
            this.manaChangedFlag = false;
         }
      }
   }

   public static Calibration getCalibrationAt(World world, BlockPos pos) {
      Calibration calibration = new Calibration();

      for (EnumFacing face : EnumFacing.HORIZONTALS) {
         BlockPos finalpos = pos.offset(face);
         if (world.getBlockState(finalpos).getBlock() == BlocksRegister.BLOCKCALIBRATIONBUNDLE) {
            TileEntity tileEntity = world.getTileEntity(finalpos);
            if (tileEntity instanceof TileCalibrationBundle) {
               TileCalibrationBundle calibrationBundle = (TileCalibrationBundle)tileEntity;
               calibration.range = calibration.range + calibrationBundle.range;
               calibration.attraction = calibration.attraction + calibrationBundle.attraction;
               calibration.speed = calibration.speed + calibrationBundle.speed;
               calibration.passwords.addAll(calibrationBundle.passwords);
            }
         }
      }

      return calibration;
   }

   public static boolean checkPasswords(Calibration modifiersWhatAttracts, Calibration modifiersAnother, boolean debug) {
      if (modifiersAnother.isPasswordsEmpty()) {
         if (debug) {
            System.out.println("Another.passwords.isEmpty");
         }

         return true;
      } else if (modifiersWhatAttracts.isPasswordsEmpty()) {
         if (debug) {
            System.out.println("WhatAttracts.passwords.isEmpty");
         }

         return false;
      } else {
         for (TileCalibrationBundle.SpellPassword passwordNeed : modifiersAnother.passwords) {
            boolean has = false;

            for (TileCalibrationBundle.SpellPassword passwordHas : modifiersWhatAttracts.passwords) {
               if (passwordHas.passwordEquals(passwordNeed)) {
                  has = true;
                  break;
               }
            }

            if (!has) {
               if (debug) {
                  System.out.println("password not has");
               }

               return false;
            }
         }

         return true;
      }
   }

   private static void sendManaToClients(World world, BlockPos pos, ManaBuffer buffer, int distance) {
      if (!world.isRemote) {
         PacketManaBufferToClients packet = new PacketManaBufferToClients();
         packet.writeargs(pos.getX(), pos.getY(), pos.getZ(), buffer.getManaStored());
         PacketHandler.sendToAllAround(packet, world, pos.getX(), pos.getY(), pos.getZ(), distance);
      }
   }

   private static void sendManaToClients(World world, BlockPos pos, ManaBuffer buffer) {
      sendManaToClients(world, pos, buffer, 64);
   }

   public static void spawnFlowParticles(World world, BlockPos posfrom, BlockPos posto, float mana, int count, float size) {
      AxisAlignedBB aabb = world.getBlockState(posfrom).getCollisionBoundingBox(world, posfrom);
      if (aabb == null) {
         aabb = Block.FULL_BLOCK_AABB;
      }

      aabb = aabb.offset(posfrom);
      spawnFlowParticles(world, aabb, posto, mana, count, size);
   }

   public static void spawnFlowParticles(World world, AxisAlignedBB from, BlockPos posto, float mana, int count, float size) {
      float scaleMult = MathHelper.clamp(mana / TICKRATE * 0.4F, 0.15F, 4.0F) * size;
      AxisAlignedBB aabb = from;
      ParticleTracker.TrackerFollowStaticPoint tfsp = new ParticleTracker.TrackerFollowStaticPoint(
         new Vec3d(posto.getX() + 0.5, posto.getY() + 0.5, posto.getZ() + 0.5), true, 0.4F, 0.001F, 0.02F
      );
      int livetime = (int)(Math.sqrt(from.getCenter().squareDistanceTo(posto.getX(), posto.getY(), posto.getZ())) * 20.0);
      livetime = Math.min(livetime, 500);
      ParticleTracker.Multitracker multitracker = new ParticleTracker.Multitracker(pt_turbulence, tfsp);

      for (int i = 0; i < count; i++) {
         float scale = (0.1F + rand.nextFloat() * 0.1F) * scaleMult;
         GUNParticle spelll = new GUNParticle(
            tex,
            scale,
            0.0F,
            livetime,
            240,
            world,
            aabb.minX + (aabb.maxX - aabb.minX) * rand.nextFloat(),
            aabb.minY + (aabb.maxY - aabb.minY) * rand.nextFloat(),
            aabb.minZ + (aabb.maxZ - aabb.minZ) * rand.nextFloat(),
            0.0F,
            0.0F,
            0.0F,
            rand.nextFloat() * 0.5F,
            0.5F + rand.nextFloat() * 0.5F,
            1.0F,
            true,
            0
         );
         spelll.alpha = 0.0F;
         spelll.alphaTickAdding = 0.05F;
         spelll.isPushedByLiquids = false;
         spelll.alphaGlowing = true;
         spelll.scaleTickAdding = -scale / (livetime + 4);
         spelll.tracker = multitracker;
         world.spawnEntity(spelll);
      }
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("manaStored")) {
         this.setMana(compound.getFloat("manaStored"));
      }
   }

   public void writeToNBT(NBTTagCompound compound) {
      compound.setFloat("manaStored", this.getManaStored());
   }

   public static class Calibration {
      public float attraction;
      public float range;
      public float speed;
      public ArrayList<TileCalibrationBundle.SpellPassword> passwords = new ArrayList<>();

      public boolean isPasswordsEmpty() {
         for (TileCalibrationBundle.SpellPassword spellPassword : this.passwords) {
            if (!spellPassword.isEmpty()) {
               return false;
            }
         }

         return true;
      }
   }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.AssemblyTable;
import com.Vivern.Arpg.elements.models.PlasmaAugmentModel;
import com.Vivern.Arpg.elements.models.PressAugmentModel;
import com.Vivern.Arpg.elements.models.TurningAugmentModel;
import com.Vivern.Arpg.elements.models.WeldAugmentModel;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class TileAssemblyAugment extends TileEntity implements ITickable, ITileEntitySynchronize {
   static ResourceLocation sparkle3 = new ResourceLocation("arpg:textures/sparkle3.png");
   static ResourceLocation star3 = new ResourceLocation("arpg:textures/star3.png");
   static ResourceLocation plasma_cloud = new ResourceLocation("arpg:textures/plasma_cloud.png");
   static ResourceLocation plasma_ring = new ResourceLocation("arpg:textures/plasma_ring.png");
   public boolean startedSend;
   public int workTime = 30;
   public int prevworkTime = 30;
   public int maxworkTime = 30;
   public int allMaxWorkTime;
   public int augment;
   public float turningX;
   public float turningZ;
   public float turningTargetX;
   public float turningTargetZ;
   public float weldToolAngle;
   public float weldTargetangleY1;
   public float weldTargetangleY2;
   public float manBrotateAngleY;
   public float manCrotateAngleY;
   public static float[] plasmaToolAnglesB = new float[]{125.0F, -8.0F, 26.0F, 28.0F, 83.0F, 68.0F, 34.0F, 52.0F, 107.0F};
   public static float[] plasmaToolAnglesC = new float[]{-125.0F, -15.0F, -40.0F, -15.0F, -86.0F, -99.0F, -86.0F, -125.0F, -141.0F};
   public float plasmaToolOpening;

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.allMaxWorkTime = (int)args[0];
      }
   }

   public void update() {
      if (this.augment == 0) {
         IBlockState blockState = this.getWorld().getBlockState(this.getPos());
         Block block = blockState.getBlock();
         if (block == BlocksRegister.AUGMENTCUT) {
            this.augment = 1;
         }

         if (block == BlocksRegister.AUGMENTPRESS) {
            this.augment = 2;
         }

         if (block == BlocksRegister.AUGMENTWELD) {
            this.augment = 3;
         }

         if (block == BlocksRegister.AUGMENTPLASMA) {
            this.augment = 4;
         }

         if (block == BlocksRegister.AUGMENTMOLECULAR) {
            this.augment = 5;
         }
      }

      if (this.world.isRemote) {
         this.prevworkTime = this.workTime;
         if (this.workTime < this.maxworkTime) {
            this.workTime++;
            if (this.augment == 1) {
               float follow = 1.0F / this.maxworkTime;
               this.turningX = GetMOP.followNumber(this.turningX, this.turningTargetX, follow);
               this.turningZ = GetMOP.followNumber(this.turningZ, this.turningTargetZ, follow);
            }

            if (this.augment == 3) {
               float ft1 = GetMOP.getfromto((float)this.workTime, 0.0F, (float)this.maxworkTime);
               if (ft1 > 0.25F && ft1 < 0.8F) {
                  IBlockState blockStatex = this.getWorld().getBlockState(this.getPos());
                  EnumFacing facing = (EnumFacing)blockStatex.getValue(AssemblyTable.FACING);
                  if (facing != null) {
                     Random rand = TileAssemblyTable.rand;
                     Vec3d partpos = this.calculateWeldParticlePos(facing);
                     GUNParticle part = new GUNParticle(
                        sparkle3,
                        0.01F,
                        0.0F,
                        4,
                        240,
                        this.world,
                        partpos.x,
                        partpos.y,
                        partpos.z,
                        0.0F,
                        0.0F,
                        0.0F,
                        1.0F,
                        1.0F,
                        1.0F,
                        true,
                        rand.nextInt(360)
                     );
                     part.alphaGlowing = true;
                     part.scaleTickAdding = 0.1F + rand.nextFloat() * 0.03F;
                     if (rand.nextFloat() < 0.3F) {
                        part.rotationPitchYaw = new Vec2f(rand.nextInt(360), rand.nextInt(360));
                     }

                     this.world.spawnEntity(part);
                     if (rand.nextFloat() < 0.5) {
                        part = new GUNParticle(
                           star3,
                           0.03F,
                           0.0F,
                           6,
                           240,
                           this.world,
                           partpos.x,
                           partpos.y,
                           partpos.z,
                           0.0F,
                           0.0F,
                           0.0F,
                           0.9F,
                           0.95F,
                           1.0F,
                           true,
                           rand.nextInt(360)
                        );
                        part.alphaGlowing = true;
                        part.scaleTickAdding = 0.12F + rand.nextFloat() * 0.03F;
                        this.world.spawnEntity(part);
                     }

                     this.world
                        .spawnParticle(
                           EnumParticleTypes.SMOKE_NORMAL,
                           partpos.x,
                           partpos.y,
                           partpos.z,
                           (rand.nextFloat() - 0.5) * 0.02,
                           (rand.nextFloat() - 0.5) * 0.04,
                           (rand.nextFloat() - 0.5) * 0.02,
                           new int[0]
                        );
                  }
               }
            }

            if (this.augment == 4) {
               if (this.plasmaToolOpening < 2.0F) {
                  this.plasmaToolOpening += 0.05F;
               }

               float ft1 = GetMOP.getfromto((float)this.workTime, 0.0F, (float)this.maxworkTime);
               float one = 1.0F / plasmaToolAnglesB.length;
               if (Minecraft.getMinecraft().gameSettings.particleSetting < 2 && this.plasmaToolOpening >= 1.0F && ft1 > one) {
                  IBlockState blockStatex = this.getWorld().getBlockState(this.getPos());
                  EnumFacing facing = (EnumFacing)blockStatex.getValue(AssemblyTable.FACING);
                  if (facing != null) {
                     Random randx = TileAssemblyTable.rand;
                     Vec3d partposx = this.calculatePlasmaParticlePos(facing);
                     GUNParticle partx = new GUNParticle(
                        plasma_cloud,
                        0.09F,
                        0.0F,
                        3,
                        240,
                        this.world,
                        partposx.x,
                        partposx.y,
                        partposx.z,
                        0.0F,
                        -0.14F,
                        0.0F,
                        0.7F + randx.nextFloat() * 0.3F,
                        1.0F,
                        1.0F,
                        true,
                        randx.nextInt(360)
                     );
                     partx.alphaGlowing = true;
                     partx.scaleTickAdding = 0.02F;
                     partx.randomDeath = false;
                     this.world.spawnEntity(partx);
                     partx = new GUNParticle(
                        plasma_ring,
                        0.1F,
                        0.0F,
                        6,
                        240,
                        this.world,
                        partposx.x,
                        partposx.y - 0.3125,
                        partposx.z,
                        0.0F,
                        0.0F,
                        0.0F,
                        0.7F + randx.nextFloat() * 0.3F,
                        0.7F + randx.nextFloat() * 0.3F,
                        1.0F,
                        true,
                        randx.nextInt(360)
                     );
                     partx.alphaGlowing = true;
                     partx.scaleTickAdding = 0.035F;
                     partx.randomDeath = false;
                     partx.rotationPitchYaw = new Vec2f(-90.0F, 0.0F);
                     partx.alphaTickAdding = -0.12F;
                     this.world.spawnEntity(partx);
                  }
               }
            }
         } else if (this.augment == 4 && this.plasmaToolOpening > 0.0F) {
            this.plasmaToolOpening = Math.max(this.plasmaToolOpening - 0.05F, 0.0F);
         }

         if (this.allMaxWorkTime > 0) {
            this.allMaxWorkTime--;
            if (this.workTime >= this.maxworkTime) {
               this.startWorkingOnClient(TileAssemblyTable.rand);
            }
         }
      }
   }

   public void setAnimationPress(PressAugmentModel model, float partialTicks) {
      float ft1 = GetMOP.getfromto(GetMOP.partial((float)this.workTime, (float)this.prevworkTime, partialTicks), 0.0F, (float)this.maxworkTime);
      float ft2 = GetMOP.getfromto(ft1, 0.0F, 0.4F) - GetMOP.getfromto(ft1, 0.6F, 0.8F);
      model.shape2.offsetY = 0.25F * ft2;
      model.shapeRotate1.rotateAngleZ = -6.283185F * ft2;
      model.shapeRotate2.rotateAngleZ = 6.283185F * ft2;
   }

   public void setAnimationTurning(TurningAugmentModel model, float partialTicks) {
      float ft1 = GetMOP.getfromto(GetMOP.partial((float)this.workTime, (float)this.prevworkTime, partialTicks), 0.0F, (float)this.maxworkTime);
      float ft2 = GetMOP.getfromto(ft1, 0.0F, 0.15F) - GetMOP.getfromto(ft1, 0.85F, 1.0F);
      model.engine1.offsetX = this.turningX * 0.0625F;
      model.engine1.offsetZ = this.turningZ * 0.0625F;
      model.shapeMove1.offsetX = this.turningX * 0.0625F;
      model.shapeMove2.offsetZ = this.turningZ * 0.0625F;
      model.engine2.rotateAngleY = ft1 * 31.41593F;
      model.rotate.rotateAngleZ = ft1 * -12.56637F;
   }

   public void setAnimationWeld(WeldAugmentModel model, float partialTicks) {
      float ft1 = GetMOP.getfromto(GetMOP.partial((float)this.workTime, (float)this.prevworkTime, partialTicks), 0.0F, (float)this.maxworkTime);
      float ft2 = GetMOP.getfromto(ft1, 0.0F, 0.05F) - GetMOP.getfromto(ft1, 0.95F, 1.0F);
      float ft3 = GetMOP.getfromto(ft1, 0.1F, 0.25F) - GetMOP.getfromto(ft1, 0.85F, 1.0F);
      float ft4 = GetMOP.getfromto(ft1, 0.3F, 0.8F);
      model.manB.rotateAngleY = (this.weldTargetangleY1 + this.weldTargetangleY2 * ft4) * ft2;
      this.manBrotateAngleY = model.manB.rotateAngleY;
      model.setManipulatorAngle(ft3, this.weldToolAngle);
   }

   public void setAnimationPlasmaSpray(PlasmaAugmentModel model, float partialTicks) {
      float ft1 = GetMOP.getfromto(GetMOP.partial((float)this.workTime, (float)this.prevworkTime, partialTicks), 0.0F, (float)this.maxworkTime);
      float one = 1.0F / plasmaToolAnglesB.length;
      int pos = Math.min((int)(ft1 / one), plasmaToolAnglesB.length - 1);
      int pos2 = pos + 1;
      if (pos2 >= plasmaToolAnglesB.length) {
         pos2 = 0;
      }

      float ft2 = GetMOP.getfromto(ft1, pos * one, (pos + 1) * one);
      float open = Math.min(this.plasmaToolOpening, 1.0F);
      float unopen = 1.0F - open;
      model.manB.rotateAngleY = unopen * (float) (Math.PI / 2) + open * GetMOP.partial(plasmaToolAnglesB[pos2], plasmaToolAnglesB[pos], ft2) * 0.017453F;
      model.manC.rotateAngleY = unopen * (float) -Math.PI + open * GetMOP.partial(plasmaToolAnglesC[pos2], plasmaToolAnglesC[pos], ft2) * 0.017453F;
      this.manBrotateAngleY = model.manB.rotateAngleY;
      this.manCrotateAngleY = model.manC.rotateAngleY;
   }

   public void startWorkingOnClient(Random rand) {
      this.workTime = 0;
      this.prevworkTime = 0;
      if (this.augment == 1) {
         if (rand.nextFloat() < 0.5F) {
            this.turningTargetX = (rand.nextFloat() - 0.5F) * 7.0F;
         } else {
            this.turningTargetZ = (rand.nextFloat() - 0.5F) * 7.0F;
         }

         this.maxworkTime = 20 + rand.nextInt(20);
         this.world
            .playSound(
               this.pos.getX(),
               this.pos.getY(),
               this.pos.getZ(),
               Sounds.augment_turning,
               SoundCategory.BLOCKS,
               1.0F,
               1.0F,
               false
            );
      }

      if (this.augment == 2) {
         this.maxworkTime = 60 + rand.nextInt(30);
         this.playsound(Sounds.augment_press, 75, 15, this.maxworkTime);
      }

      if (this.augment == 3) {
         this.maxworkTime = 40 + rand.nextInt(40);
         this.weldToolAngle = 1.3F + 0.6F * rand.nextFloat();
         this.weldTargetangleY1 = (rand.nextFloat() - 0.5F) * 1.25F;
         this.weldTargetangleY2 = (rand.nextFloat() - 0.5F) * 0.45F;
         this.playsound(Sounds.augment_weld, 60, 20, this.maxworkTime);
      }

      if (this.augment == 4) {
         this.maxworkTime = 70 + rand.nextInt(35);
         this.playsound(Sounds.augment_plasma, 87, 17, this.maxworkTime);
      }
   }

   public void startWorkingOnServer(int allMaxWorkTime) {
      ITileEntitySynchronize.sendSynchronize(this, 64.0, allMaxWorkTime);
   }

   public Vec3d calculateWeldParticlePos(EnumFacing blockfacing) {
      EnumFacing facing = blockfacing.getOpposite();
      Vec3d v = GetMOP.YawToVec3d(blockfacing.getHorizontalAngle() + this.manBrotateAngleY * (float) (180.0 / Math.PI))
         .scale(1.2 - this.weldToolAngle * 0.4);
      Vec3d rotationPoint = new Vec3d(
         this.pos.getX() + 0.5 + facing.getXOffset() * 0.4375,
         this.pos.getY() + 0.6875,
         this.pos.getZ() + 0.5 + facing.getZOffset() * 0.4375
      );
      return rotationPoint.add(v);
   }

   public Vec3d calculatePlasmaParticlePos(EnumFacing blockfacing) {
      EnumFacing facing = blockfacing.getOpposite();
      float angl1 = blockfacing.getHorizontalAngle() + this.manBrotateAngleY * (float) (180.0 / Math.PI);
      Vec3d v = GetMOP.YawToVec3d(angl1).scale(0.25);
      Vec3d rotationPoint = new Vec3d(
         this.pos.getX() + 0.5 + facing.getXOffset() * 0.4375,
         this.pos.getY() + 1.0F,
         this.pos.getZ() + 0.5 + facing.getZOffset() * 0.4375
      );
      Vec3d pos1 = rotationPoint.add(v);
      Vec3d v2 = GetMOP.YawToVec3d(angl1 + this.manCrotateAngleY * (float) (180.0 / Math.PI)).scale(0.4375);
      return pos1.add(v2);
   }

   public void playsound(SoundEvent soundEvent, int averageLength, int lengthAmplitude, int length) {
      float amplitude = (float)lengthAmplitude / averageLength;
      float difference = (float)(averageLength - length) / lengthAmplitude;
      this.world
         .playSound(
            this.pos.getX(),
            this.pos.getY(),
            this.pos.getZ(),
            soundEvent,
            SoundCategory.BLOCKS,
            1.0F,
            1.0F + difference * amplitude,
            false
         );
   }
}

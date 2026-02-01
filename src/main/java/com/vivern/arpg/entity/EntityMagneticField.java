package com.vivern.arpg.entity;

import com.vivern.arpg.tileentity.TileElectromagnet;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMagneticField extends Entity implements IEntitySynchronize {
   public int power = 0;
   public int maximumPower = 0;
   public int fieldSize = 0;
   public float renderPower = 0.0F;
   public boolean hasMagnetCircle = true;

   public EntityMagneticField(World worldIn) {
      super(worldIn);
      this.setSize(0.9F, 0.9F);
      this.noClip = true;
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      this.power = (int)x;
      this.maximumPower = (int)y;
      this.fieldSize = (int)z;
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      if (this.world.isRemote) {
         if (this.renderPower < this.power) {
            this.renderPower = this.renderPower + Math.max((this.power - this.renderPower) / 6.0F, 0.2F);
         } else if (this.renderPower > this.power) {
            this.renderPower = this.renderPower - Math.max((this.renderPower - this.power) / 5.0F, 0.2F);
         }
      }

      this.power--;
      if (!this.world.isRemote && this.power <= 0) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         int energyNeed = MathHelper.clamp(this.maximumPower - this.power, 0, 7500);
         int energyNeedPerOne = 0;
         if (energyNeed >= 15) {
            energyNeedPerOne = energyNeed / 15;
         }

         int checkNumber = this.ticksExisted % 15;
         int checkSize = checkNumber % 5 + 1;
         int checkRotation = checkNumber / 5;
         if (this.isMagnetConfiguration(this.getPosition(), checkSize, checkRotation, energyNeedPerOne)) {
            this.hasMagnetCircle = true;
            this.fieldSize += checkSize;
            if (this.power < this.maximumPower) {
               this.power += energyNeedPerOne;
            }
         }

         if (this.ticksExisted % 15 == 0) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.power, this.maximumPower, this.fieldSize, 0.0, 0.0, 0.0);
            if (this.hasMagnetCircle) {
               this.hasMagnetCircle = false;
            } else {
               this.setDead();
            }

            this.maximumPower = this.fieldSize * 2000 + 3000;
            this.fieldSize = 0;
         }
      }
   }

   public boolean isMagnetConfiguration(BlockPos cetner, int size, int rotation, int drainEnergy) {
      int i = size;
      int consumers = size * 8;
      if (drainEnergy > 0) {
         drainEnergy = Math.max(drainEnergy / consumers, 1);
      }

      if (rotation == 0) {
         BlockPos side1 = cetner.east(size);
         BlockPos side2 = cetner.west(size);
         BlockPos up = cetner.up(size);
         BlockPos down = cetner.down(size);
         boolean succes = true;
         if (!this.isMagnet(drainEnergy, side1)) {
            succes = false;
         }

         if (!this.isMagnet(drainEnergy, side2)) {
            succes = false;
         }

         if (!this.isMagnet(drainEnergy, up)) {
            succes = false;
         }

         if (succes) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1 = up.east(u);
               BlockPos up2 = up.west(u);
               if (!this.isMagnet(drainEnergy, up1)) {
                  succes = false;
               }

               if (!this.isMagnet(drainEnergy, up2)) {
                  succes = false;
               }
            }
         }

         if (succes) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1x = down.east(u);
               BlockPos up2x = down.west(u);
               if (!this.isMagnet(drainEnergy, up1x)) {
                  succes = false;
               }

               if (!this.isMagnet(drainEnergy, up2x)) {
                  succes = false;
               }
            }
         }

         if (i > 1) {
            if (succes) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1 = side1.up(u);
                  BlockPos usd2 = side1.down(u);
                  if (!this.isMagnet(drainEnergy, usd1)) {
                     succes = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2)) {
                     succes = false;
                  }
               }
            }

            if (succes) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1x = side2.up(u);
                  BlockPos usd2x = side2.down(u);
                  if (!this.isMagnet(drainEnergy, usd1x)) {
                     succes = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2x)) {
                     succes = false;
                  }
               }
            }
         }

         if (succes) {
            return true;
         }
      }

      if (rotation == 1) {
         BlockPos side1x = cetner.north(i);
         BlockPos side2x = cetner.south(i);
         BlockPos upx = cetner.up(i);
         BlockPos downx = cetner.down(i);
         boolean succesx = true;
         if (!this.isMagnet(drainEnergy, side1x)) {
            succesx = false;
         }

         if (!this.isMagnet(drainEnergy, side2x)) {
            succesx = false;
         }

         if (!this.isMagnet(drainEnergy, upx)) {
            succesx = false;
         }

         if (succesx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xx = upx.north(u);
               BlockPos up2xx = upx.south(u);
               if (!this.isMagnet(drainEnergy, up1xx)) {
                  succesx = false;
               }

               if (!this.isMagnet(drainEnergy, up2xx)) {
                  succesx = false;
               }
            }
         }

         if (succesx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxx = downx.north(u);
               BlockPos up2xxx = downx.south(u);
               if (!this.isMagnet(drainEnergy, up1xxx)) {
                  succesx = false;
               }

               if (!this.isMagnet(drainEnergy, up2xxx)) {
                  succesx = false;
               }
            }
         }

         if (i > 1) {
            if (succesx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xx = side1x.up(u);
                  BlockPos usd2xx = side1x.down(u);
                  if (!this.isMagnet(drainEnergy, usd1xx)) {
                     succesx = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2xx)) {
                     succesx = false;
                  }
               }
            }

            if (succesx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxx = side2x.up(u);
                  BlockPos usd2xxx = side2x.down(u);
                  if (!this.isMagnet(drainEnergy, usd1xxx)) {
                     succesx = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2xxx)) {
                     succesx = false;
                  }
               }
            }
         }

         if (succesx) {
            return true;
         }
      }

      if (rotation == 2) {
         BlockPos side1xx = cetner.west(i);
         BlockPos side2xx = cetner.east(i);
         BlockPos upxx = cetner.north(i);
         BlockPos downxx = cetner.south(i);
         boolean succesxx = true;
         if (!this.isMagnet(drainEnergy, side1xx)) {
            succesxx = false;
         }

         if (!this.isMagnet(drainEnergy, side2xx)) {
            succesxx = false;
         }

         if (!this.isMagnet(drainEnergy, upxx)) {
            succesxx = false;
         }

         if (succesxx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxxx = upxx.west(u);
               BlockPos up2xxxx = upxx.east(u);
               if (!this.isMagnet(drainEnergy, up1xxxx)) {
                  succesxx = false;
               }

               if (!this.isMagnet(drainEnergy, up2xxxx)) {
                  succesxx = false;
               }
            }
         }

         if (succesxx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxxxx = downxx.west(u);
               BlockPos up2xxxxx = downxx.east(u);
               if (!this.isMagnet(drainEnergy, up1xxxxx)) {
                  succesxx = false;
               }

               if (!this.isMagnet(drainEnergy, up2xxxxx)) {
                  succesxx = false;
               }
            }
         }

         if (i > 1) {
            if (succesxx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxxx = side1xx.north(u);
                  BlockPos usd2xxxx = side1xx.south(u);
                  if (!this.isMagnet(drainEnergy, usd1xxxx)) {
                     succesxx = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2xxxx)) {
                     succesxx = false;
                  }
               }
            }

            if (succesxx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxxxx = side2xx.north(u);
                  BlockPos usd2xxxxx = side2xx.south(u);
                  if (!this.isMagnet(drainEnergy, usd1xxxxx)) {
                     succesxx = false;
                  }

                  if (!this.isMagnet(drainEnergy, usd2xxxxx)) {
                     succesxx = false;
                  }
               }
            }
         }

         if (succesxx) {
            return true;
         }
      }

      return false;
   }

   public boolean isMagnet(int consumePower, BlockPos pos) {
      TileEntity en = this.world.getTileEntity(pos);
      return en != null && en instanceof TileElectromagnet ? consumePower == 0 || ((TileElectromagnet)en).consumePower(consumePower) : false;
   }

   protected void entityInit() {
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public boolean handleWaterMovement() {
      return false;
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("power")) {
         this.power = compound.getInteger("power");
      }

      if (compound.hasKey("maxpower")) {
         this.maximumPower = compound.getInteger("maxpower");
      }
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      compound.setInteger("power", this.power);
      compound.setInteger("maxpower", this.maximumPower);
   }
}

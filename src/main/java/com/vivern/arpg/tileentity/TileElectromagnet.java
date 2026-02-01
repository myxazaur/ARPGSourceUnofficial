package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.BlockElectromagnet;
import com.vivern.arpg.entity.EntityMagneticField;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.FluidsRegister;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileElectromagnet extends TileEntity implements ITickable, IFillDrain {
   public EnergyStorage electricStorage;
   public int ticks = 0;
   public FluidStorage tank1 = null;
   public FluidStorage tank2 = null;
   public int fluidMax = 0;
   public int type = 0;
   public int rotat = 0;
   public int heat = 0;

   public TileElectromagnet() {
      this.electricStorage = new EnergyStorage(2000, 500, 500, 0);
   }

   public boolean consumePower(int amount) {
      int maxheat = this.type == 0 ? 400 : 1000;
      amount = (int)(amount * this.getResistance());
      if (this.electricStorage.extractEnergy(amount, true) >= amount) {
         if (this.heat < maxheat) {
            this.heat = this.heat + Math.max(amount, 1);
         }

         return this.electricStorage.extractEnergy(amount, false) >= amount;
      } else {
         return false;
      }
   }

   public float getResistance() {
      float metalresistance = this.type == 0 ? 1.25F : (this.type == 1 ? 1.05F : 1.2F);
      return this.heat < 180 && this.type == 2 ? 1.0F : (int)(metalresistance + this.heat * 0.004);
   }

   public void initMagnet() {
      IBlockState state = this.getWorld().getBlockState(this.getPos());
      int type = (Integer)state.getValue(BlockElectromagnet.TYPE);
      this.type = type;
      this.fluidMax = type * 1000;
      int[] conf = this.getMagnetConfiguration(type);
      this.rotat = conf[1];
      if (type > 0 && this.tank1 == null && this.tank2 == null) {
         this.tank1 = new FluidStorage(this.fluidMax, this);
         this.tank2 = new FluidStorage(this.fluidMax, this);
         this.tank1.setTileEntity(this);
         this.tank2.setTileEntity(this);
      }
   }

   public void update() {
      this.ticks++;
      int tempOfBiome = FluidsRegister.biomeToLiquidTemperature(this.world.getBiome(this.getPos()).getTemperature(this.getPos()));
      if (this.heat > tempOfBiome) {
         this.heat--;
      }

      if (this.heat < tempOfBiome) {
         this.heat++;
      }

      if (!this.world.isRemote) {
         if (this.ticks == 1 || this.ticks % 39 == 0) {
            this.initMagnet();
         }

         if (this.ticks % 20 == 0) {
            int[] conf = this.getMagnetConfiguration(this.type);
            if (conf[0] != 0) {
               BlockPos centr = conf[1] == 2 ? this.getPos().north(conf[0]) : this.getPos().up(conf[0]);
               if (centr != null) {
                  AxisAlignedBB aabb = new AxisAlignedBB(centr);
                  List<EntityMagneticField> list = this.world.getEntitiesWithinAABB(EntityMagneticField.class, aabb);
                  if (list.isEmpty() && this.consumePower(100)) {
                     EntityMagneticField mfield = new EntityMagneticField(this.world);
                     mfield.setPosition(centr.getX() + 0.5, centr.getY() + 0.01, centr.getZ() + 0.5);
                     mfield.rotationPitch = conf[1] == 2 ? 90.0F : 0.0F;
                     mfield.rotationYaw = conf[1] == 1 ? 90.0F : 0.0F;
                     this.world.spawnEntity(mfield);
                     mfield.power = 100;
                     mfield.maximumPower = 100;
                  }
               }
            }
         }

         if (this.ticks % 3 == 0 && this.type > 0 && this.tank1 != null && this.tank2 != null && this.tank1.getFluid() != null) {
            FluidsRegister.CoolantInfo cinfo = FluidsRegister.getFluidCoolantInfo(this.tank1.getFluid().getFluid());
            if (cinfo != null
               && this.heat >= cinfo.coolingMinimum
               && this.heat >= cinfo.coolingAmount
               && cinfo.inputExponent <= this.tank1.getFluidAmount()
               && (cinfo.existedFluid == null || cinfo.existedExponent == 0 || this.tank2.getFluid().getFluid() == cinfo.existedFluid)
               && this.tank2.getFluidAmount() + cinfo.existedExponent <= this.tank2.getCapacity()) {
               this.tank1.drain(cinfo.inputExponent, true);
               if (cinfo.existedFluid != null) {
                  this.tank2.fill(new FluidStack(cinfo.existedFluid, cinfo.existedExponent), true);
               }

               this.heat = MathHelper.clamp(this.heat - cinfo.coolingAmount, 0, 1000);
            }
         }
      }
   }

   public int[] getMagnetConfiguration(int type) {
      for (int i = 1; i < 5; i++) {
         BlockPos cetner = this.getPos().up(i);
         BlockPos side1 = cetner.east(i);
         BlockPos side2 = cetner.west(i);
         BlockPos up = cetner.up(i);
         boolean succes = true;
         if (!this.isMagnet(type, side1)) {
            succes = false;
         }

         if (!this.isMagnet(type, side2)) {
            succes = false;
         }

         if (!this.isMagnet(type, up)) {
            succes = false;
         }

         if (succes) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1 = up.east(u);
               BlockPos up2 = up.west(u);
               if (!this.isMagnet(type, up1)) {
                  succes = false;
               }

               if (!this.isMagnet(type, up2)) {
                  succes = false;
               }
            }
         }

         if (succes) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1x = this.getPos().east(u);
               BlockPos up2x = this.getPos().west(u);
               if (!this.isMagnet(type, up1x)) {
                  succes = false;
               }

               if (!this.isMagnet(type, up2x)) {
                  succes = false;
               }
            }
         }

         if (i > 1) {
            if (succes) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1 = side1.up(u);
                  BlockPos usd2 = side1.down(u);
                  if (!this.isMagnet(type, usd1)) {
                     succes = false;
                  }

                  if (!this.isMagnet(type, usd2)) {
                     succes = false;
                  }
               }
            }

            if (succes) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1x = side2.up(u);
                  BlockPos usd2x = side2.down(u);
                  if (!this.isMagnet(type, usd1x)) {
                     succes = false;
                  }

                  if (!this.isMagnet(type, usd2x)) {
                     succes = false;
                  }
               }
            }
         }

         if (succes) {
            return new int[]{i, 0};
         }
      }

      for (int i = 1; i < 5; i++) {
         BlockPos cetnerx = this.getPos().up(i);
         BlockPos side1x = cetnerx.north(i);
         BlockPos side2x = cetnerx.south(i);
         BlockPos upx = cetnerx.up(i);
         boolean succesx = true;
         if (!this.isMagnet(type, side1x)) {
            succesx = false;
         }

         if (!this.isMagnet(type, side2x)) {
            succesx = false;
         }

         if (!this.isMagnet(type, upx)) {
            succesx = false;
         }

         if (succesx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xx = upx.north(u);
               BlockPos up2xx = upx.south(u);
               if (!this.isMagnet(type, up1xx)) {
                  succesx = false;
               }

               if (!this.isMagnet(type, up2xx)) {
                  succesx = false;
               }
            }
         }

         if (succesx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxx = this.getPos().north(u);
               BlockPos up2xxx = this.getPos().south(u);
               if (!this.isMagnet(type, up1xxx)) {
                  succesx = false;
               }

               if (!this.isMagnet(type, up2xxx)) {
                  succesx = false;
               }
            }
         }

         if (i > 1) {
            if (succesx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xx = side1x.up(u);
                  BlockPos usd2xx = side1x.down(u);
                  if (!this.isMagnet(type, usd1xx)) {
                     succesx = false;
                  }

                  if (!this.isMagnet(type, usd2xx)) {
                     succesx = false;
                  }
               }
            }

            if (succesx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxx = side2x.up(u);
                  BlockPos usd2xxx = side2x.down(u);
                  if (!this.isMagnet(type, usd1xxx)) {
                     succesx = false;
                  }

                  if (!this.isMagnet(type, usd2xxx)) {
                     succesx = false;
                  }
               }
            }
         }

         if (succesx) {
            return new int[]{i, 1};
         }
      }

      for (int i = 1; i < 5; i++) {
         BlockPos cetnerxx = this.getPos().north(i);
         BlockPos side1xx = cetnerxx.west(i);
         BlockPos side2xx = cetnerxx.east(i);
         BlockPos upxx = cetnerxx.north(i);
         boolean succesxx = true;
         if (!this.isMagnet(type, side1xx)) {
            succesxx = false;
         }

         if (!this.isMagnet(type, side2xx)) {
            succesxx = false;
         }

         if (!this.isMagnet(type, upxx)) {
            succesxx = false;
         }

         if (succesxx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxxx = upxx.west(u);
               BlockPos up2xxxx = upxx.east(u);
               if (!this.isMagnet(type, up1xxxx)) {
                  succesxx = false;
               }

               if (!this.isMagnet(type, up2xxxx)) {
                  succesxx = false;
               }
            }
         }

         if (succesxx) {
            for (int u = 1; u <= i; u++) {
               BlockPos up1xxxxx = this.getPos().west(u);
               BlockPos up2xxxxx = this.getPos().east(u);
               if (!this.isMagnet(type, up1xxxxx)) {
                  succesxx = false;
               }

               if (!this.isMagnet(type, up2xxxxx)) {
                  succesxx = false;
               }
            }
         }

         if (i > 1) {
            if (succesxx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxxx = side1xx.north(u);
                  BlockPos usd2xxxx = side1xx.south(u);
                  if (!this.isMagnet(type, usd1xxxx)) {
                     succesxx = false;
                  }

                  if (!this.isMagnet(type, usd2xxxx)) {
                     succesxx = false;
                  }
               }
            }

            if (succesxx) {
               for (int u = 1; u < i; u++) {
                  BlockPos usd1xxxxx = side2xx.north(u);
                  BlockPos usd2xxxxx = side2xx.south(u);
                  if (!this.isMagnet(type, usd1xxxxx)) {
                     succesxx = false;
                  }

                  if (!this.isMagnet(type, usd2xxxxx)) {
                     succesxx = false;
                  }
               }
            }
         }

         if (succesxx) {
            return new int[]{i, 2};
         }
      }

      return new int[]{0, 0};
   }

   public boolean isMagnet(int type, BlockPos pos) {
      IBlockState state = this.world.getBlockState(pos);
      return state.getBlock() == BlocksRegister.ELECTROMAGNET ? (Integer)state.getValue(BlockElectromagnet.TYPE) == type : false;
   }

   @Override
   public int fill(FluidStorage thisstorage, FluidStack resource, boolean doFill) {
      return !thisstorage.canFillFluidType(resource) ? 0 : thisstorage.fillInternal(resource, doFill);
   }

   @Override
   public FluidStack drain(FluidStorage thisstorage, int maxDrain, boolean doDrain) {
      return !thisstorage.canDrainFluidType(thisstorage.getFluid()) ? null : thisstorage.drainInternal(maxDrain, doDrain);
   }

   @Override
   public void onContentsChanged(FluidStorage thisstorage, boolean fluidTypeChanges) {
      BlockElectromagnet.trySendPacketUpdate(this.world, this.getPos(), this, fluidTypeChanges ? 64 : 8);
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      if (this.world.getBlockState(this.getPos().offset(facing)).getBlock() == BlocksRegister.ELECTROMAGNET) {
         return (T)super.getCapability(capability, facing);
      } else {
         if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null) {
            if (this.rotat == 2) {
               if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
                  return (T)this.tank1;
               }

               return (T)this.tank2;
            }

            if (this.rotat == 0) {
               if (facing != EnumFacing.SOUTH && facing != EnumFacing.NORTH) {
                  return (T)this.tank1;
               }

               return (T)this.tank2;
            }

            if (this.rotat == 1) {
               if (facing != EnumFacing.EAST && facing != EnumFacing.WEST) {
                  return (T)this.tank1;
               }

               return (T)this.tank2;
            }
         }

         return (T)(capability == CapabilityEnergy.ENERGY ? this.electricStorage : super.getCapability(capability, facing));
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(2000, 200, 200, compound.getInteger("rf"));
      }

      if (compound.hasKey("type")) {
         this.type = compound.getInteger("type");
      }

      if (compound.hasKey("rotate")) {
         this.rotat = compound.getInteger("rotate");
      }

      if (compound.hasKey("fluidmax")) {
         this.fluidMax = compound.getInteger("fluidmax");
      }

      if (compound.hasKey("heat")) {
         this.heat = compound.getInteger("heat");
      }

      if (this.type > 0 && this.tank1 != null && this.tank2 != null) {
         this.tank1.readFromNBT(compound, "tank1");
         this.tank2.readFromNBT(compound, "tank2");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setInteger("type", this.type);
      compound.setInteger("rotate", this.rotat);
      compound.setInteger("fluidmax", this.fluidMax);
      compound.setInteger("heat", this.heat);
      if (this.tank1 != null && this.tank2 != null) {
         this.tank1.writeToNBT(compound, "tank1");
         this.tank2.writeToNBT(compound, "tank2");
      }

      return super.writeToNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
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
}

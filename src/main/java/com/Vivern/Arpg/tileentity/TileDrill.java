package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.BlockDrill;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileDrill extends TileEntity implements ITickable {
   public EnergyStorage electricStorage;
   public ItemStack pickaxe = ItemStack.EMPTY;
   public float progress = 0.0F;
   public float breakSpeed = 0.0F;
   public int redstone = 0;
   public boolean capture = false;

   public TileDrill() {
      this.electricStorage = new EnergyStorage(1000, 4, 4, 0);
      this.pickaxe = new ItemStack(Items.IRON_PICKAXE);
   }

   public boolean consumePower(int amount) {
      return this.electricStorage.extractEnergy(amount, true) >= amount ? this.electricStorage.extractEnergy(amount, false) >= amount : false;
   }

   public void update() {
      if (!this.world.isRemote) {
         if (this.redstone == 0) {
            BlockPos posoffset = this.getPos()
               .offset((EnumFacing)this.world.getBlockState(this.getPos()).getValue(BlockDrill.FACING));
            IBlockState state = this.world.getBlockState(posoffset);
            float hard = state.getBlockHardness(this.world, posoffset);
            int rftick = (int)MathHelper.clamp(hard / 5.0F, 1.0F, 20.0F);
            float haste = this.breakSpeed == 0.0F ? this.getDigSpeed(state, posoffset, hard) : this.breakSpeed;
            if (haste > 0.0F && this.consumePower(rftick)) {
               if (this.progress < 100.0F) {
                  this.breakSpeed = haste;
                  this.progress += haste;
               } else {
                  this.progress = 0.0F;
                  this.breakSpeed = 0.0F;
                  this.world.destroyBlock(posoffset, true);
               }
            }
         } else {
            this.progress = 0.0F;
            this.breakSpeed = 0.0F;
         }
      }
   }

   public float getDigSpeed(IBlockState state, BlockPos pos, float hardsess) {
      if (!(hardsess < 0.0F) && !state.getBlock().isAir(state, this.world, pos)) {
         float f = this.pickaxe.getDestroySpeed(state);
         if (f > 1.0F) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, this.pickaxe);
            if (i > 0 && !this.pickaxe.isEmpty()) {
               f += i * i + 1;
            }
         }

         return f < 0.0F ? 0.0F : f;
      } else {
         return 0.0F;
      }
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == CapabilityEnergy.ENERGY ? this.electricStorage : super.getCapability(capability, facing));
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("pickaxe")) {
         NBTTagCompound sttag = compound.getCompoundTag("pickaxe");
         this.pickaxe = new ItemStack(sttag);
      }

      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(1000, 4, 4, compound.getInteger("rf"));
      }

      if (compound.hasKey("progress")) {
         this.progress = compound.getFloat("progress");
      }

      if (compound.hasKey("breakspeed")) {
         this.breakSpeed = compound.getFloat("breakspeed");
      }

      if (compound.hasKey("redstone")) {
         this.redstone = compound.getBoolean("redstone") ? 1 : 0;
      }

      if (compound.hasKey("capture")) {
         this.capture = compound.getBoolean("capture");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      NBTTagCompound sttag = new NBTTagCompound();
      this.pickaxe.writeToNBT(sttag);
      compound.setTag("pickaxe", sttag);
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setFloat("progress", this.progress);
      compound.setFloat("breakspeed", this.breakSpeed);
      compound.setBoolean("redstone", this.redstone > 0);
      compound.setBoolean("capture", this.capture);
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

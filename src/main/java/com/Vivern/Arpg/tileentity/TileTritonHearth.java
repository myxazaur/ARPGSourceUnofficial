package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.TritonHearth;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileTritonHearth extends TileEntity implements IMagicVision, ITickable, IVialElementsAccepter, IManaBuffer, ITileEntitySynchronize {
   public static float maxElementCount = 64.0F;
   public static int workTicksMax = 60;
   public float elementCollected;
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 40.0F, 4, 1.0F, 1.0F);
   public boolean hasMaterials = false;
   public int workTicks = 0;

   @Override
   public void onClient(double... args) {
      if (args.length == 1 && args[0] == 1.0) {
         for (int i = 0; i < 10; i++) {
            ShardType.WATER
               .spawnNativeParticle(
                  this.world,
                  1.0F,
                  this.getPos().getX() + 0.5,
                  this.getPos().getY() + 0.9,
                  this.getPos().getZ() + 0.5,
                  GetMOP.rand.nextGaussian() / 22.0,
                  GetMOP.rand.nextGaussian() / 20.0 + 0.07F,
                  GetMOP.rand.nextGaussian() / 22.0,
                  true
               );
         }
      }
   }

   @Override
   public ManaBuffer getManaBuffer() {
      return this.manaBuffer;
   }

   public void update() {
      this.getManaBuffer().updateManaBuffer(this.world, this.pos);
      if (!this.world.isRemote) {
         if (this.hasMaterials) {
            IBlockState state = this.world.getBlockState(this.pos);
            boolean wet = state.getBlock() == BlocksRegister.TRITONHEARTH ? (Boolean)state.getValue(TritonHearth.WET) : false;
            if (this.workTicks > (wet ? workTicksMax : workTicksMax * 1.5F)) {
               this.workTicks = 0;
               this.hasMaterials = false;
               this.getWorld()
                  .spawnEntity(
                     new EntityItem(
                        this.getWorld(),
                        this.getPos().getX() + 0.5,
                        this.getPos().getY() + 0.9,
                        this.getPos().getZ() + 0.5,
                        new ItemStack(ItemsRegister.INGOTAQUATIC)
                     )
                  );
               ITileEntitySynchronize.sendSynchronize(this, 48.0, 1.0);
               this.world
                  .playSound(
                     (EntityPlayer)null, this.getPos(), Sounds.triton_hearth_end, SoundCategory.BLOCKS, 0.6F, 0.9F + GetMOP.rand.nextFloat() * 0.2F
                  );
            } else {
               this.workTicks++;
            }
         } else if (this.workTicks > 20) {
            IBlockState state = this.world.getBlockState(this.pos);
            this.checkMaterials(state.getBlock() == BlocksRegister.TRITONHEARTH ? (Boolean)state.getValue(TritonHearth.WET) : false);
            this.workTicks = 0;
         } else {
            this.workTicks++;
         }
      }
   }

   public void checkMaterials(boolean wet) {
      if (!this.hasMaterials) {
         float manaNeed = wet ? 20.0F : 24.0F;
         if (this.getManaBuffer().getManaStored() < manaNeed || this.elementCollected < 2.0F) {
            return;
         }

         float bounds = 0.35F;
         AxisAlignedBB aabb = new AxisAlignedBB(
            this.pos.getX() + bounds,
            this.pos.getY() + 1,
            this.pos.getZ() + bounds,
            this.pos.getX() + 1.0F - bounds,
            this.pos.getY() + 1.6F,
            this.pos.getZ() + 1.0F - bounds
         );
         List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, aabb);
         if (list.size() == 1) {
            EntityItem entityItem = list.get(0);
            if (entityItem != null && entityItem.getItem() != null) {
               ItemStack stack = entityItem.getItem();
               boolean is = false;
               if (stack.getItem() == Item.getItemFromBlock(BlocksRegister.METALLICCORAL) && stack.getCount() >= 8) {
                  stack.shrink(8);
                  is = true;
               } else if (stack.getItem() == ItemsRegister.DUSTAQUATIC && stack.getCount() >= 1) {
                  stack.shrink(1);
                  is = true;
               } else if (stack.getItem() == ItemsRegister.NUGGETAQUATIC && stack.getCount() >= 9) {
                  stack.shrink(9);
                  is = true;
               }

               if (is) {
                  this.hasMaterials = true;
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.getPos(),
                        Sounds.triton_hearth_start,
                        SoundCategory.BLOCKS,
                        0.6F,
                        0.95F + GetMOP.rand.nextFloat() * 0.1F
                     );
                  this.getManaBuffer().addMana(-manaNeed);
                  this.addElementEnergy(ShardType.WATER, -2.0F);
                  PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
                  ITileEntitySynchronize.sendSynchronize(this, 48.0, 1.0);
               }
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      super.readFromNBT(compound);
      if (compound.hasKey("elementCollected")) {
         this.elementCollected = compound.getFloat("elementCollected");
      }

      this.manaBuffer.readFromNBT(compound);
      if (compound.hasKey("hasMaterials")) {
         this.hasMaterials = compound.getBoolean("hasMaterials");
      }

      if (compound.hasKey("workTicks")) {
         this.workTicks = compound.getInteger("workTicks");
      }
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setFloat("elementCollected", this.elementCollected);
      this.manaBuffer.writeToNBT(compound);
      compound.setBoolean("hasMaterials", this.hasMaterials);
      compound.setInteger("workTicks", this.workTicks);
      return compound;
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

   @Override
   public float acceptVialElements(ItemStack vial, ShardType shardType, float count) {
      if (shardType != ShardType.WATER) {
         return count;
      } else if (count > 0.0F) {
         float add = Math.min(maxElementCount - this.elementCollected, count);
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

   public void addElementEnergy(ShardType shardType, float amount) {
      if (shardType == ShardType.WATER) {
         if (amount > 0.0F) {
            float add = Math.min(maxElementCount - this.elementCollected, amount);
            this.elementCollected += add;
         } else {
            float remove = Math.max(-this.elementCollected, amount);
            this.elementCollected += remove;
         }
      }
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return shardType != ShardType.WATER ? 0.0F : this.elementCollected;
   }

   @Override
   public float getMana() {
      return this.getManaBuffer().getMana();
   }

   @Override
   public float getElementCount(ShardType shardType) {
      return shardType != ShardType.WATER ? 0.0F : this.elementCollected;
   }

   @Override
   public float getManaStorageSize(World world, BlockPos pos) {
      return this.getManaBuffer().getManaStorageSize();
   }
}

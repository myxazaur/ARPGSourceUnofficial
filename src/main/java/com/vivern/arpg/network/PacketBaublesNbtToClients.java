package com.vivern.arpg.network;

import baubles.api.BaublesApi;
import com.vivern.arpg.main.Weapons;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBaublesNbtToClients extends Packet {
   public byte nbtTypeId;
   public String name;
   public float value;
   public Weapons.EnumMathOperation mathOperation;
   public byte baublesSlot;
   public int entityId;

   public static void send(
      EntityPlayer player, double distance, int nbtTypeId, char name, float value, Weapons.EnumMathOperation mathOperation, int baublesSlot
   ) {
      if (!player.world.isRemote) {
         PacketBaublesNbtToClients packet = new PacketBaublesNbtToClients();
         packet.write((byte)nbtTypeId, name, value, mathOperation, (byte)baublesSlot, player.getEntityId());
         PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, distance);
      }
   }

   public void write(byte nbtTypeId, char name, float value, Weapons.EnumMathOperation mathOperation, byte baublesSlot, int entityId) {
      this.buf().writeFloat(value);
      this.buf().writeInt(entityId);
      this.buf().writeChar(name);
      this.buf().writeByte(nbtTypeId);
      this.buf().writeByte(mathOperation.ordinalByte());
      this.buf().writeByte(baublesSlot);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.value = buf.readFloat();
      this.entityId = buf.readInt();
      this.name = "" + buf.readChar();
      this.nbtTypeId = buf.readByte();
      this.mathOperation = Weapons.EnumMathOperation.byId(buf.readByte());
      this.baublesSlot = buf.readByte();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      if (this.mathOperation != null) {
         Entity entity = player.world.getEntityByID(this.entityId);
         if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player2 = (EntityPlayer)entity;
            ItemStack itemStack = BaublesApi.getBaubles(player2).getStackInSlot(this.baublesSlot);
            if (!itemStack.isEmpty()) {
               if (!itemStack.hasTagCompound()) {
                  itemStack.setTagCompound(new NBTTagCompound());
               }

               NBTTagCompound stackNbt = itemStack.getTagCompound();
               if (this.nbtTypeId == 0) {
                  if (this.mathOperation == Weapons.EnumMathOperation.NONE) {
                     stackNbt.setBoolean(this.name, this.value > 0.0F);
                  } else if (this.mathOperation == Weapons.EnumMathOperation.PLUS && this.value > 0.0F) {
                     boolean oldValue = stackNbt.getBoolean(this.name);
                     stackNbt.setBoolean(this.name, !oldValue);
                  }
               }

               if (this.nbtTypeId == 1) {
                  byte oldValue = stackNbt.getByte(this.name);
                  stackNbt.setByte(this.name, (byte)this.mathOperation.apply(oldValue, (byte)this.value));
               }

               if (this.nbtTypeId == 2) {
                  short oldValue = stackNbt.getShort(this.name);
                  stackNbt.setShort(this.name, (short)this.mathOperation.apply(oldValue, (short)this.value));
               }

               if (this.nbtTypeId == 3) {
                  int oldValue = stackNbt.getInteger(this.name);
                  stackNbt.setInteger(this.name, this.mathOperation.apply(oldValue, (int)this.value));
               }

               if (this.nbtTypeId == 4) {
                  long oldValue = stackNbt.getLong(this.name);
                  stackNbt.setLong(this.name, this.mathOperation.apply(oldValue, (long)this.value));
               }

               if (this.nbtTypeId == 5) {
                  float oldValue = stackNbt.getFloat(this.name);
                  stackNbt.setFloat(this.name, this.mathOperation.apply(oldValue, this.value));
               }

               if (this.nbtTypeId == 6) {
                  double oldValue = stackNbt.getDouble(this.name);
                  stackNbt.setDouble(this.name, this.mathOperation.apply(oldValue, (double)this.value));
               }
            }
         }
      }
   }
}

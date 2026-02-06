package com.Vivern.Arpg.network;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.potions.AdvancedPotion;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAdvPotionToClients extends Packet {
   NBTTagCompound itemstacknbt;
   int index = 0;
   int duration = 0;
   int amplifier = 0;
   int entityId = 0;

   public void writeargs(ItemStack itemstack, int index, int duration, int amplifier, int entityId) {
      this.buf().writeInt(index);
      this.buf().writeInt(duration);
      this.buf().writeInt(amplifier);
      this.buf().writeInt(entityId);
      ByteBufUtils.writeTag(this.buf(), itemstack.getTagCompound());
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.index = buffer.readInt();
      this.duration = buffer.readInt();
      this.amplifier = buffer.readInt();
      this.entityId = buffer.readInt();
      this.itemstacknbt = ByteBufUtils.readTag(buffer);
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         Entity entity = world.getEntityByID(this.entityId);
         if (entity != null && entity instanceof EntityLivingBase) {
            EntityLivingBase entitylb = (EntityLivingBase)entity;
            if (this.duration == 0) {
               entitylb.removeActivePotionEffect(AdvancedPotion.potionByIndex.get(this.index));
            } else {
               AdvancedPotion potion = AdvancedPotion.potionByIndex.get(this.index);
               entitylb.removeActivePotionEffect(potion);
               PotionEffect effect = new PotionEffect(potion, this.duration, this.amplifier);
               ItemStack stack = new ItemStack(ItemsRegister.EXP);
               stack.setTagCompound(this.itemstacknbt);
               List<ItemStack> stacks = new ArrayList<>();
               stacks.add(stack);
               effect.setCurativeItems(stacks);
               entitylb.addPotionEffect(effect);
            }
         }
      } catch (ConcurrentModificationException var8) {
         var8.printStackTrace();
      }
   }
}

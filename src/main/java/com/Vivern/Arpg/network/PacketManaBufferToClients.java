//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.tileentity.IManaBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketManaBufferToClients extends Packet {
   float mana;
   int posx = 0;
   int posy = 0;
   int posz = 0;

   public void writeargs(int posx, int posy, int posz, float mana) {
      this.buf().writeInt(posx);
      this.buf().writeInt(posy);
      this.buf().writeInt(posz);
      this.buf().writeFloat(mana);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.posx = buffer.readInt();
      this.posy = buffer.readInt();
      this.posz = buffer.readInt();
      this.mana = buffer.readFloat();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      BlockPos poss = new BlockPos(this.posx, this.posy, this.posz);
      TileEntity tileEntity = player.world.getTileEntity(poss);
      if (tileEntity != null && tileEntity instanceof IManaBuffer) {
         IManaBuffer entityi = (IManaBuffer)tileEntity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(entityi, this.mana));
      }
   }

   void processMessage(IManaBuffer entityi, float mana) {
      entityi.getManaBuffer().setMana(mana);
   }
}

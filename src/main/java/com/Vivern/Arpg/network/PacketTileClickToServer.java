//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.tileentity.TileEntityClicked;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileClickToServer extends Packet {
   public int x = 0;
   public int y = 0;
   public int z = 0;
   public int mx = 0;
   public int my = 0;
   public int key = 0;
   public boolean isMessage;

   public void writeints(int x, int y, int z, int mouseX, int mouseY, int mouseButton) {
      this.buf().writeInt(x);
      this.buf().writeInt(y);
      this.buf().writeInt(z);
      this.buf().writeInt(mouseX);
      this.buf().writeInt(mouseY);
      this.buf().writeInt(mouseButton);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.mx = buffer.readInt();
      this.my = buffer.readInt();
      this.key = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         TileEntity tile = player.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
         if (tile != null && tile instanceof TileEntityClicked) {
            ((TileEntityClicked)tile).mouseClick(player, this.mx, this.my, this.key);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}

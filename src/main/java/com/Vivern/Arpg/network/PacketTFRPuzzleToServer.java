//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.recipes.TerraformingPlayerCommand;
import com.Vivern.Arpg.tileentity.TileResearchTable;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTFRPuzzleToServer extends Packet {
   public int x = 0;
   public int y = 0;
   public int z = 0;
   public int boardSelectX = 0;
   public int boardSelectY = 0;
   public int data = 0;
   public TerraformingPlayerCommand.TRPlayerCommandType commandType;

   public void writeints(int x, int y, int z, TerraformingPlayerCommand command) {
      this.buf().writeInt(x);
      this.buf().writeInt(y);
      this.buf().writeInt(z);
      this.buf().writeInt(command.boardSelectX);
      this.buf().writeInt(command.boardSelectY);
      this.buf().writeInt(command.data);
      this.buf().writeByte(command.commandType.ordinal());
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.boardSelectX = buffer.readInt();
      this.boardSelectY = buffer.readInt();
      this.data = buffer.readInt();
      byte t = buffer.readByte();
      if (t >= 0 && t < TerraformingPlayerCommand.TRPlayerCommandType.values().length) {
         this.commandType = TerraformingPlayerCommand.TRPlayerCommandType.values()[t];
      }
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         if (this.commandType != null && player.getDistanceSq(this.x, this.y, this.z) < 64.0) {
            TileEntity tile = player.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
            if (tile != null && tile instanceof TileResearchTable && player instanceof EntityPlayer) {
               ((TileResearchTable)tile).puzzleCommand = new TerraformingPlayerCommand(
                  this.commandType, this.boardSelectX, this.boardSelectY, this.data, (EntityPlayer)player
               );
            }
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.InvasionInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketNexusInfoToClients extends Packet {
   public float nexusHealth = 0.0F;
   public float waveCompleteRate = 0.0F;
   public int wave = 0;
   public int id = 0;

   public void writeargs(float nexusHealth, float waveCompleteRate, short wave, short id, boolean finalwave) {
      this.buf().writeFloat(nexusHealth);
      this.buf().writeFloat(waveCompleteRate);
      this.buf().writeShort(finalwave ? -wave : wave);
      this.buf().writeShort(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.nexusHealth = buffer.readFloat();
      this.waveCompleteRate = buffer.readFloat();
      this.wave = buffer.readShort();
      this.id = buffer.readShort();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      boolean fw = this.wave < 0;
      int nw = Math.abs(this.wave);
      if (this.waveCompleteRate == -200.0F) {
         InvasionInfo.wave = nw;
         InvasionInfo.loseAnim = 160;
         player.world
            .playSound(player, player.posX, player.posY, player.posZ, Sounds.invasion_lose, SoundCategory.AMBIENT, 2.0F, 1.0F);
         InvasionInfo.nexusHealth = 0.0F;
      } else if (this.waveCompleteRate == -100.0F) {
         InvasionInfo.wave = nw;
         InvasionInfo.winAnim = 200;
         player.world
            .playSound(player, player.posX, player.posY, player.posZ, Sounds.final_wave, SoundCategory.AMBIENT, 2.0F, 1.0F);
         player.world
            .playSound(
               player, player.posX, player.posY, player.posZ, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.AMBIENT, 2.0F, 1.0F
            );
         InvasionInfo.nexusHealth = this.nexusHealth;
         InvasionInfo.waveCompleteRate = 1.0F;
      } else {
         if (nw > InvasionInfo.wave && !fw) {
            InvasionInfo.wave = nw;
            InvasionInfo.nextwaveAnim = 130;
            player.world
               .playSound(player, player.posX, player.posY, player.posZ, Sounds.next_wave, SoundCategory.AMBIENT, 2.0F, 1.0F);
         } else {
            InvasionInfo.wave = nw;
         }

         if (fw && !InvasionInfo.finalwave) {
            InvasionInfo.finalwaveAnim = 160;
            player.world
               .playSound(player, player.posX, player.posY, player.posZ, Sounds.final_wave, SoundCategory.AMBIENT, 2.0F, 1.0F);
         }

         InvasionInfo.nexusHealth = this.nexusHealth;
         InvasionInfo.waveCompleteRate = this.waveCompleteRate;
      }

      InvasionInfo.finalwave = fw;
      InvasionInfo.currentInfo = InvasionInfo.invasionsIdToInfo.get(this.id);
      InvasionInfo.lastUpdateWorldTime = player.world.getTotalWorldTime();
   }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class CPacketPotionParticles extends Packet {
   public CPacketPotionParticles() {
   }

   public CPacketPotionParticles(double x, double y, double z) {
      this.buf().writeDouble(x);
      this.buf().writeDouble(y);
      this.buf().writeDouble(z);
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      double x = this.buf().readDouble();
      double y = this.buf().readDouble();
      double z = this.buf().readDouble();
      double doublePi = Math.PI * 2;
      double RADIUS = 0.4;

      for (double i = 0.0; i < doublePi; i += doublePi / 50.0) {
         double newPosX = x + 0.4 * Math.cos(i);
         double newPosZ = z + 0.4 * Math.sin(i);
         double speedX = (newPosX - x) * 0.2;
         double speedZ = (newPosZ - z) * 0.2;
         player.world.spawnParticle(EnumParticleTypes.FLAME, newPosX, y, newPosZ, speedX, 0.0, speedZ, new int[0]);
      }
   }
}

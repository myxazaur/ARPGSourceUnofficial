//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WhipSegment {
   public double posX;
   public double posY;
   public double posZ;
   public double motionX;
   public double motionY;
   public double motionZ;
   public WhipSegment previous = null;
   public WhipSegment next = null;
   public int number;
   public float maxDist = 0.4F;
   public float minDist = 0.2F;

   public WhipSegment(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int number) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.motionX = motionX;
      this.motionY = motionY;
      this.motionZ = motionZ;
      this.number = number;
   }

   public void update(World world, ItemStack itemstack, EntityPlayer player) {
      if (this.number == 0) {
         this.posX = player.posX;
         this.posY = player.posY + 1.5;
         this.posZ = player.posZ;
      } else {
         this.posX = this.posX + this.motionX;
         this.posY = this.posY + this.motionY;
         this.posZ = this.posZ + this.motionZ;
         if (this.previous != null) {
            if (this.getDistance(this.previous) > this.maxDist) {
               applyMove(this, -0.6F, this.previous.posX, this.previous.posY, this.previous.posZ);
            }

            if (this.getDistance(this.previous) < this.minDist) {
               applyMove(this, 0.4F, this.previous.posX, this.previous.posY, this.previous.posZ);
            }
         }
      }
   }

   public static void applyMove(WhipSegment segm, float power, double fromX, double fromY, double fromZ) {
      float dist = segm.getDistance(fromX, fromY, fromZ);
      float prunex = (float)((fromX - segm.posX) / dist / 2.0 * power);
      float pruney = (float)((fromY - segm.posY) / dist / 2.0 * power);
      float prunez = (float)((fromZ - segm.posZ) / dist / 2.0 * power);
      segm.motionX += -prunex;
      segm.motionY += -pruney;
      segm.motionZ += -prunez;
   }

   public float getDistance(double x, double y, double z) {
      double d0 = this.posX - x;
      double d1 = this.posY - y;
      double d2 = this.posZ - z;
      return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public float getDistance(WhipSegment other) {
      double d0 = this.posX - other.posX;
      double d1 = this.posY - other.posY;
      double d2 = this.posZ - other.posZ;
      return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }
}

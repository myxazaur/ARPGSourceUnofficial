//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.BetweenParticle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WhipParticle extends BetweenParticle {
   public float startmotionX = 0.0F;
   public float startmotionY = 0.0F;
   public float startmotionZ = 0.0F;
   public float targetmotionX = 0.0F;
   public float targetmotionY = 0.0F;
   public float targetmotionZ = 0.0F;
   public Vec3d from;
   public Vec3d to;

   public WhipParticle(
      World worldIn,
      ResourceLocation texture,
      float scale,
      int light,
      float Red,
      float Green,
      float Blue,
      float alphatime,
      double distance,
      int livetime,
      float animation,
      float texlengh,
      Vec3d from,
      Vec3d to
   ) {
      super(worldIn);
      this.scale = scale;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.distance = distance;
      this.livetime = livetime;
      this.animation = animation;
      this.texlengh = texlengh;
      this.alphatime = alphatime;
      AxisAlignedBB bb = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setEntityBoundingBox(bb);
      this.from = from;
      this.to = to;
      float mx = (float)(from.x - to.x);
      float mz = (float)(from.z - to.z);
      float my = (float)(to.y - from.y);
      float moti_zx = (float)Math.sqrt(mx * mx + mz * mz);
      float moti_zy = (float)Math.sqrt(my * my + mz * mz);
      float cosangle_Yaw = mz / moti_zx;
      float cosangle_Pitch = mz / moti_zy;
      float angle_Yaw = (float)Math.toDegrees(Math.acos(cosangle_Yaw));
      float angle_Pitch = (float)Math.toDegrees(Math.acos(cosangle_Pitch));
      float tanangle = my / moti_zx;
      float angle = (float)Math.toDegrees(Math.atan(tanangle));
      if (mx > 0.0F) {
         angle_Yaw = -angle_Yaw;
      }

      if (my < 0.0F) {
         angle_Pitch = -angle_Pitch;
      }

      this.rotatYaw = angle_Yaw + 180.0F;
      this.rotatPitch = -angle;
   }
}

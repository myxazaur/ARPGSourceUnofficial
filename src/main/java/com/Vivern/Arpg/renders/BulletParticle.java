//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.main.ParticleFastSummon;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BulletParticle extends Entity {
   public static ResourceLocation textureExplode = new ResourceLocation("arpg:textures/bullet_explode1.png");
   public static ResourceLocation textureSmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static ResourceLocation textureSparkle = new ResourceLocation("arpg:textures/sparkle2.png");
   public float Red;
   public float Green;
   public float Blue;
   public float smokeRed = 0.8F;
   public float smokeGreen = 0.8F;
   public float smokeBlue = 0.8F;
   public float smokeAlpha = 0.6F;
   public float Red2;
   public float Green2;
   public float Blue2;
   public int livetime;
   public int hidetime;
   public float scale;
   public Vec3d from;
   public Vec3d to;
   public float rotatPitch = 0.0F;
   public float rotatYaw = 0.0F;
   public float length = 0.0F;
   public Vec3d smokeDisplace;
   public int blockDustId = -1;
   public int blockDustCount = 3;
   public int smokesCount = 5;
   public boolean doFullEffect;
   public int textureVariant = 1;

   public BulletParticle(World world, Vec3d from, Vec3d to, float scale, int livetime, int hidetime, float Red, float Green, float Blue, boolean doFullEffect) {
      this(world, from, to, scale, livetime, hidetime, (float)from.distanceTo(to), Red, Green, Blue, doFullEffect);
   }

   public BulletParticle(
      World world, Vec3d from, Vec3d to, float scale, int livetime, int hidetime, float length, float Red, float Green, float Blue, boolean doFullEffect
   ) {
      super(world);
      this.setPosition(
         from.x + (to.x - from.x) / 2.0,
         from.y + (to.y - from.y) / 2.0,
         from.z + (to.z - from.z) / 2.0
      );
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.livetime = livetime;
      this.hidetime = hidetime;
      this.scale = scale;
      this.from = from;
      this.to = to;
      this.length = length;
      this.smokeDisplace = new Vec3d(
         (this.rand.nextFloat() - 0.5) / 2.0, (this.rand.nextFloat() - 0.5) / 2.0, (this.rand.nextFloat() - 0.5) / 2.0
      );
      this.ignoreFrustumCheck = true;
      this.Red2 = Red;
      this.Green2 = Green;
      this.Blue2 = Blue;
      this.doFullEffect = doFullEffect;
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

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime + this.hidetime) {
         this.setDead();
      }

      if (this.ticksExisted == this.livetime && this.doFullEffect) {
         ParticleFastSummon.bulletImpact(
            this.world,
            this.textureVariant,
            this.scale,
            this.Red,
            this.Green,
            this.Blue,
            this.from,
            this.to,
            this.smokeRed,
            this.smokeGreen,
            this.smokeBlue,
            this.smokesCount,
            this.hidetime,
            this.blockDustCount,
            this.blockDustId
         );
      }
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}

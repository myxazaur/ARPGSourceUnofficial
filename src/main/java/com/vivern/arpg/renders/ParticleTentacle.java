package com.vivern.arpg.renders;

import com.vivern.arpg.main.Weapons;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleTentacle extends GUNParticle {
   public float overlap;
   public float scaleEnd;
   public ParticleTentaclePOINT start;
   public ParticleTentaclePOINT end;
   public int segments;
   public float rotationVecLength;

   public ParticleTentacle(
      ResourceLocation texture,
      float scaleStart,
      float scaleEnd,
      float gravity,
      int livetime,
      int light,
      World world,
      Vec3d start,
      Vec3d end,
      Vec3d rotationStart,
      Vec3d rotationEnd,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int segments,
      float rotationVecLength
   ) {
      super(
         texture,
         scaleStart,
         gravity,
         livetime,
         light,
         world,
         (start.x + end.x) / 2.0,
         (start.y + end.y) / 2.0,
         (start.z + end.z) / 2.0,
         speedx,
         speedy,
         speedz,
         Red,
         Green,
         Blue,
         opa,
         0
      );
      this.randomDeath = false;
      this.isPushedByLiquids = false;
      this.scaleEnd = scaleEnd;
      this.start = new PTPOINTsimple(start, rotationStart);
      this.end = new PTPOINTsimple(end, rotationEnd);
      this.segments = segments;
      this.rotationVecLength = rotationVecLength;
   }

   public ParticleTentacle(
      ResourceLocation texture,
      float scaleStart,
      float scaleEnd,
      float gravity,
      int livetime,
      int light,
      World world,
      ParticleTentaclePOINT start,
      ParticleTentaclePOINT end,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int segments,
      float rotationVecLength
   ) {
      super(texture, scaleStart, gravity, livetime, light, world, 0.0, 0.0, 0.0, speedx, speedy, speedz, Red, Green, Blue, opa, 0);
      this.randomDeath = false;
      this.isPushedByLiquids = false;
      this.scaleEnd = scaleEnd;
      this.start = start;
      this.end = end;
      this.segments = segments;
      this.rotationVecLength = rotationVecLength;
      Vec3d vecpos = start.getPosition(1.0F);
      this.setPosition(vecpos.x, vecpos.y, vecpos.z);
   }

   public static class PTPOINTplayerWeaponArm extends ParticleTentaclePOINT {
      public EnumHand hand;
      public EntityPlayer player;
      public double shoulders;
      public double yoffset;

      public PTPOINTplayerWeaponArm(EntityPlayer player, @Nullable EnumHand hand) {
         this.player = player;
         this.hand = hand;
         this.shoulders = 0.2;
         this.yoffset = -0.1;
      }

      public PTPOINTplayerWeaponArm(EntityPlayer player, @Nullable EnumHand hand, double shoulders, double yoffset) {
         this.player = player;
         this.hand = hand;
         this.shoulders = shoulders;
         this.yoffset = yoffset;
      }

      @Override
      public Vec3d getPosition(float partialTicks) {
         return Weapons.getPlayerShootPoint(this.player, this.hand, partialTicks, this.shoulders, this.yoffset);
      }

      @Override
      public Vec3d getNormalizedRotationVector(float partialTicks) {
         return this.player.getLook(partialTicks);
      }
   }

   public static class PTPOINTsimple extends ParticleTentaclePOINT {
      public Vec3d position;
      public Vec3d rotation;

      public PTPOINTsimple(Vec3d position, Vec3d rotation) {
         this.position = position;
         this.rotation = rotation;
      }

      @Override
      public Vec3d getPosition(float partialTicks) {
         return this.position;
      }

      @Override
      public Vec3d getNormalizedRotationVector(float partialTicks) {
         return this.rotation;
      }
   }

   public abstract static class ParticleTentaclePOINT {
      public abstract Vec3d getPosition(float var1);

      public abstract Vec3d getNormalizedRotationVector(float var1);
   }
}

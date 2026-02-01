package com.vivern.arpg.entity;

import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TrailParticle extends GUNParticle {
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public float Tscale = 0.1F;
   public int Tlight = 240;
   public ResourceLocation Ttexture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   public float TRed = 1.0F;
   public float TGreen = 1.0F;
   public float TBlue = 1.0F;
   public float Talphatime = 0.2F;
   public int Tlivetime = 15;
   public float Tanimation = 0.0F;
   public float Ttexlengh = 1.0F;
   public boolean TalphaGlowing = false;
   public boolean onlyHorizontal = false;
   public float Ttexstart = 0.0F;
   public float Toffset = 0.0F;

   public TrailParticle(
      ResourceLocation texture,
      float scale,
      float gravity,
      int livetime,
      int light,
      World world,
      double x,
      double y,
      double z,
      float speedx,
      float speedy,
      float speedz,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      int rotation,
      boolean collide,
      float frictionMultipl,
      ResourceLocation Ttexture,
      float Tscale,
      int Tlight,
      float TRed,
      float TGreen,
      float TBlue,
      float Talphatime,
      int Tlivetime,
      float Tanimation,
      float Ttexlengh
   ) {
      super(texture, scale, gravity, livetime, light, world, x, y, z, speedx, speedy, speedz, Red, Green, Blue, opa, rotation, collide, frictionMultipl);
      this.Tscale = Tscale;
      this.Tlight = Tlight;
      this.TRed = TRed;
      this.TGreen = TGreen;
      this.TBlue = TBlue;
      this.Talphatime = Talphatime;
      this.Tlivetime = Tlivetime;
      this.Tanimation = Tanimation;
      this.Ttexlengh = Ttexlengh;
      this.Ttexture = Ttexture;
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.Ttexture,
            this.Tscale,
            this.Tlight,
            this.TRed,
            this.TGreen,
            this.TBlue,
            this.Talphatime,
            this.pos1.distanceTo(this.pos2),
            this.Tlivetime,
            this.Tanimation,
            this.Ttexlengh,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = this.TalphaGlowing;
         laser.horizontal = !this.onlyHorizontal;
         laser.texstart = this.Ttexstart;
         laser.offset = this.Toffset;
         this.world.spawnEntity(laser);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.pos1 != Vec3d.ZERO) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = this.getPositionVector();
      }

      if (this.getPositionVector() != Vec3d.ZERO) {
         this.pos1 = this.getPositionVector();
      } else {
         this.pos1 = this.thrower.getPositionVector();
      }
   }
}

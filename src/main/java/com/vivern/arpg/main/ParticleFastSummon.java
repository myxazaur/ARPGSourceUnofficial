package com.vivern.arpg.main;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleFastSummon {
   public static ResourceLocation shard_trace = new ResourceLocation("arpg:textures/shard_trace.png");
   public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
   public static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
   public static ResourceLocation[] texturesExplode = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/bullet_explode1.png"),
      new ResourceLocation("arpg:textures/bullet_explode1.png"),
      new ResourceLocation("arpg:textures/bullet_explode2.png"),
      new ResourceLocation("arpg:textures/bullet_explode3.png")
   };
   public static int[] framecountsExplode = new int[]{16, 16, 8, 14};
   public static ResourceLocation textureSmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static ResourceLocation textureSparkle = new ResourceLocation("arpg:textures/sparkle2.png");
   public static ResourceLocation spellblue4 = new ResourceLocation("arpg:textures/spellblue4.png");
   public static ResourceLocation crystal_spell = new ResourceLocation("arpg:textures/crystal_spell.png");
   public static Random rand = new Random();
   public static ParticleTracker.TrackerSmoothShowHide trssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 12.0, 0.08), new Vec3d(13.0, 55.0, -0.04)}, null
   );

   public static void bulletImpact(
      World world,
      int texture,
      float scale,
      float Red,
      float Green,
      float Blue,
      Vec3d from,
      Vec3d to,
      float smokeRed,
      float smokeGreen,
      float smokeBlue,
      int smokesCount,
      int hidetime,
      int blockDustCount,
      int blockDustId
   ) {
      AnimatedGParticle anim = new AnimatedGParticle(
         texturesExplode[texture],
         scale * 4.0F,
         0.0F,
         framecountsExplode[texture],
         240,
         world,
         to.x,
         to.y,
         to.z,
         0.0F,
         0.0F,
         0.0F,
         Red,
         Green,
         Blue,
         true,
         rand.nextInt(360)
      );
      anim.framecount = framecountsExplode[texture];
      anim.alphaGlowing = true;
      anim.randomDeath = false;
      world.spawnEntity(anim);
      anim = new AnimatedGParticle(
         texturesExplode[texture],
         scale * 2.0F,
         0.0F,
         framecountsExplode[texture],
         240,
         world,
         to.x,
         to.y,
         to.z,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         1.0F,
         1.0F,
         true,
         rand.nextInt(360)
      );
      anim.framecount = framecountsExplode[texture];
      anim.alphaGlowing = true;
      anim.randomDeath = false;
      world.spawnEntity(anim);

      for (int ss = 0; ss < smokesCount; ss++) {
         int lt = 25 + hidetime + rand.nextInt(hidetime * 2);
         GUNParticle bigsmoke = new GUNParticle(
            textureSmoke,
            scale + rand.nextFloat() * scale,
            -0.002F,
            lt,
            -1,
            world,
            to.x,
            to.y,
            to.z,
            (float)rand.nextGaussian() / 25.0F,
            (float)rand.nextGaussian() / 25.0F,
            (float)rand.nextGaussian() / 25.0F,
            smokeRed,
            smokeGreen,
            smokeBlue,
            true,
            rand.nextInt(360)
         );
         bigsmoke.alpha = 0.0F;
         bigsmoke.scaleTickAdding = (0.05F + rand.nextFloat() / 20.0F) * scale;
         bigsmoke.tracker = trssh;
         world.spawnEntity(bigsmoke);
      }

      Vec3d normMotVect = new Vec3d(from.x - to.x, from.y - to.y, from.z - to.z)
         .normalize();

      for (int ss = 0; ss < 4; ss++) {
         if (rand.nextFloat() < 0.4F) {
            int lt = 25 + hidetime + rand.nextInt(hidetime * 2);
            float scl = (0.1F + rand.nextFloat() * scale) * 0.4F;
            GUNParticle sparklee = new GUNParticle(
               textureSparkle,
               scl,
               0.04F,
               lt,
               240,
               world,
               to.x,
               to.y,
               to.z,
               (float)rand.nextGaussian() / 11.0F,
               (float)rand.nextGaussian() / 11.0F,
               (float)rand.nextGaussian() / 11.0F,
               Red,
               Green,
               Blue,
               true,
               rand.nextInt(360)
            );
            sparklee.alphaGlowing = true;
            sparklee.scaleTickAdding = -scl / lt;
            sparklee.motionX = sparklee.motionX + normMotVect.x / 2.5;
            sparklee.motionY = sparklee.motionY + normMotVect.y / 2.5;
            sparklee.motionZ = sparklee.motionZ + normMotVect.z / 2.5;
            world.spawnEntity(sparklee);
         }
      }

      if (blockDustId != -1) {
         for (int ssx = 0; ssx < blockDustCount + rand.nextInt(blockDustCount); ssx++) {
            world.spawnParticle(
               EnumParticleTypes.BLOCK_CRACK,
               to.x,
               to.y,
               to.z,
               normMotVect.x / 2.5 + rand.nextGaussian() / 15.0,
               normMotVect.y / 2.5 + rand.nextGaussian() / 15.0,
               normMotVect.z / 2.5 + rand.nextGaussian() / 15.0,
               new int[]{blockDustId}
            );
         }
      }
   }

   public static void coloredLightning(
      World world,
      float scale,
      int livetime,
      int light,
      float Red,
      float Green,
      float Blue,
      int points,
      Vec3d from,
      Vec3d to,
      float randomDisplace,
      float uprise,
      Random random
   ) {
      Vec3d[] pointsVectors = new Vec3d[points];
      Vec3d subtract = to.subtract(from);
      double d1 = 1.0 / points;

      for (int i = 0; i < points; i++) {
         double upMult = Math.sin(i * Math.PI / points);
         if (i != 0 && i != points - 1) {
            pointsVectors[i] = subtract.scale(d1 * i)
               .add(
                  from.x + (rand.nextDouble() - 0.5) * randomDisplace,
                  upMult * uprise + from.y + (rand.nextDouble() - 0.5) * randomDisplace,
                  from.z + (rand.nextDouble() - 0.5) * randomDisplace
               );
         } else {
            pointsVectors[i] = subtract.scale(d1 * i).add(from.x, from.y, from.z);
         }
      }

      float alphatime = 1.0F / (livetime - 1);

      for (int ix = 0; ix < points - 1; ix++) {
         BetweenParticle particle = new BetweenParticle(
            world,
            shard_trace,
            scale,
            light,
            Red,
            Green,
            Blue,
            alphatime,
            pointsVectors[ix].distanceTo(pointsVectors[ix + 1]),
            livetime,
            0.0F,
            0.0F,
            pointsVectors[ix],
            pointsVectors[ix + 1]
         );
         particle.setPosition(pointsVectors[ix].x, pointsVectors[ix].y, pointsVectors[ix].z);
         particle.alphaGlowing = true;
         world.spawnEntity(particle);
      }
   }

   public static void round(
      World world,
      Vec3d pos,
      float scaleStart,
      float scaleEnd,
      int livetime,
      int light,
      float Red,
      float Green,
      float Blue,
      int textureVariant,
      boolean faceToCamera
   ) {
      GUNParticle particle = new GUNParticle(
         textureVariant == 1 ? circle : circle2,
         scaleStart,
         0.0F,
         livetime,
         light,
         world,
         pos.x,
         pos.y,
         pos.z,
         0.0F,
         0.0F,
         0.0F,
         Red,
         Green,
         Blue,
         true,
         rand.nextInt(360)
      );
      particle.alphaGlowing = true;
      particle.randomDeath = false;
      if (scaleEnd > scaleStart) {
         particle.alpha = 1.0F;
         particle.alphaTickAdding = -1.0F / livetime;
      } else {
         particle.alpha = 0.0F;
         particle.alphaTickAdding = 1.0F / livetime;
      }

      particle.scaleTickAdding = (scaleEnd - scaleStart) / livetime;
      particle.isPushedByLiquids = false;
      if (!faceToCamera) {
         particle.rotationPitchYaw = new Vec2f(rand.nextInt(360), rand.nextInt(360));
      }

      world.spawnEntity(particle);
   }

   public static GUNParticle round2(
      World world,
      ResourceLocation texture,
      Vec3d pos,
      float scaleStart,
      float scaleEnd,
      int livetime,
      int light,
      float Red,
      float Green,
      float Blue,
      int faceStyle
   ) {
      GUNParticle particle = new GUNParticle(
         texture,
         scaleStart,
         0.0F,
         livetime,
         light,
         world,
         pos.x,
         pos.y,
         pos.z,
         0.0F,
         0.0F,
         0.0F,
         Red,
         Green,
         Blue,
         true,
         rand.nextInt(360)
      );
      particle.alphaGlowing = true;
      particle.randomDeath = false;
      if (scaleEnd > scaleStart) {
         particle.alpha = 1.0F;
         particle.alphaTickAdding = -1.0F / livetime;
      } else {
         particle.alpha = 0.0F;
         particle.alphaTickAdding = 1.0F / livetime;
      }

      particle.scaleTickAdding = (scaleEnd - scaleStart) / livetime;
      particle.isPushedByLiquids = false;
      if (faceStyle == 1) {
         particle.rotationPitchYaw = new Vec2f(rand.nextInt(360), rand.nextInt(360));
      }

      if (faceStyle == 2) {
         particle.rotationPitchYaw = new Vec2f(-90.0F, 0.0F);
      }

      world.spawnEntity(particle);
      return particle;
   }

   public static GUNParticle round2invertedAlpha(
      World world,
      ResourceLocation texture,
      Vec3d pos,
      float scaleStart,
      float scaleEnd,
      int livetime,
      int light,
      float Red,
      float Green,
      float Blue,
      int faceStyle
   ) {
      GUNParticle particle = new GUNParticle(
         texture,
         scaleStart,
         0.0F,
         livetime,
         light,
         world,
         pos.x,
         pos.y,
         pos.z,
         0.0F,
         0.0F,
         0.0F,
         Red,
         Green,
         Blue,
         true,
         rand.nextInt(360)
      );
      particle.alphaGlowing = true;
      particle.randomDeath = false;
      if (scaleEnd < scaleStart) {
         particle.alpha = 1.0F;
         particle.alphaTickAdding = -1.0F / livetime;
      } else {
         particle.alpha = 0.0F;
         particle.alphaTickAdding = 1.0F / livetime;
      }

      particle.scaleTickAdding = (scaleEnd - scaleStart) / livetime;
      particle.isPushedByLiquids = false;
      if (faceStyle == 1) {
         particle.rotationPitchYaw = new Vec2f(rand.nextInt(360), rand.nextInt(360));
      }

      if (faceStyle == 2) {
         particle.rotationPitchYaw = new Vec2f(-90.0F, 0.0F);
      }

      world.spawnEntity(particle);
      return particle;
   }

   public static void placeInSphere(Entity entity, float radius, float motion) {
      float rand1 = rand.nextFloat() * 2.0F - 1.0F;
      float rand2 = rand.nextFloat() * 2.0F - 1.0F;
      float X = rand1 * motion;
      float new_R = (float)Math.sqrt(motion * motion - X * X);
      float Y = rand2 * new_R;
      float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
      if (rand.nextBoolean()) {
         Z *= -1.0F;
      }

      entity.motionX = X;
      entity.motionY = Y;
      entity.motionZ = Z;
      if (radius != 0.0F) {
         Vec3d mVec = new Vec3d(X, Y, Z).normalize().scale(radius);
         entity.setPosition(entity.posX + mVec.x, entity.posY + mVec.y, entity.posZ + mVec.z);
      }
   }

   public static void debugParticle(Vec3d pos, float scale, int livetime) {
      GUNParticle particle = new GUNParticle(
         spellblue4,
         scale,
         0.0F,
         livetime,
         240,
         Minecraft.getMinecraft().world,
         pos.x,
         pos.y,
         pos.z,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         1.0F,
         1.0F,
         false,
         45
      );
      particle.randomDeath = false;
      particle.isPushedByLiquids = false;
      Minecraft.getMinecraft().world.spawnEntity(particle);
   }

   public static void crystalSpell(World world, Vec3d pos, float scaleMultiplier, float gravityMultiplier) {
      int frameDelay = 6 + rand.nextInt(5);
      AnimatedGParticle anim = new AnimatedGParticle(
         crystal_spell,
         (0.2F + rand.nextFloat() * 0.1F) * scaleMultiplier,
         0.001F * gravityMultiplier,
         frameDelay * 11,
         240,
         world,
         pos.x,
         pos.y,
         pos.z,
         0.0F,
         0.0F,
         0.0F,
         1.0F - rand.nextFloat() * 0.1F,
         1.0F - rand.nextFloat() * 0.1F,
         1.0F,
         false,
         0
      );
      anim.framecount = 11;
      anim.animDelay = frameDelay;
      anim.randomDeath = false;
      world.spawnEntity(anim);
   }
}

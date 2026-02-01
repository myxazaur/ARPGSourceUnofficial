package com.vivern.arpg.renders;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.TrailParticle;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.NoiseGenerator3D;
import com.vivern.arpg.main.SuperKnockback;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class ParticleTracker<T extends Entity> {
   public static Random rand = new Random();
   public static TrackerDelayCollide dcinstance = new TrackerDelayCollide();
   public static TrackerMagicSpin magicspin = new TrackerMagicSpin(false, 10.0F);
   public static TrackerMagicSpin magicspinNeg = new TrackerMagicSpin(true, 10.0F);

   public void update(T entity) {
   }

   public void render(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
   }

   public static class Multitracker extends ParticleTracker {
      public ParticleTracker[] trackers;

      public Multitracker(ParticleTracker... trackers) {
         this.trackers = trackers;
      }

      @Override
      public void render(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
         for (ParticleTracker tracker : this.trackers) {
            tracker.render(entity, x, y, z, entityYaw, partialTicks);
         }
      }

      @Override
      public void update(Entity entity) {
         for (ParticleTracker tracker : this.trackers) {
            tracker.update(entity);
         }
      }
   }

   public static class TrackerChangeTexOnDrop extends ParticleTracker<GUNParticle> {
      public ResourceLocation tex;
      public boolean enableAcidRend = false;
      public boolean enableAlphaGlow = false;

      public TrackerChangeTexOnDrop(ResourceLocation tex, boolean enableAcidRend, boolean enableAlphaGlow) {
         this.tex = tex;
         this.enableAcidRend = enableAcidRend;
         this.enableAlphaGlow = enableAlphaGlow;
      }

      public void update(GUNParticle entity) {
         if (entity.dropped) {
            entity.texture = this.tex;
            if (this.enableAcidRend) {
               entity.acidRender = true;
               entity.alphaGlowing = false;
            } else if (this.enableAlphaGlow) {
               entity.acidRender = false;
               entity.alphaGlowing = true;
            }
         }
      }
   }

   public static class TrackerChangeTexOnDropAnim extends ParticleTracker<AnimatedGParticle> {
      public ResourceLocation tex;
      public boolean enableAcidRend = false;
      public boolean enableAlphaGlow = false;
      public int newFramecount;
      public int newDelay;
      public boolean randomizeRotation = true;
      public boolean newDisableOnEnd;

      public TrackerChangeTexOnDropAnim(
         ResourceLocation tex, boolean enableAcidRend, boolean enableAlphaGlow, int newFramecount, int newDelay, boolean newDisableOnEnd
      ) {
         this.tex = tex;
         this.enableAcidRend = enableAcidRend;
         this.enableAlphaGlow = enableAlphaGlow;
         this.newFramecount = newFramecount;
         this.newDelay = newDelay;
         this.newDisableOnEnd = newDisableOnEnd;
      }

      public void update(AnimatedGParticle entity) {
         if (entity.dropped) {
            if (entity.texture != this.tex && this.randomizeRotation) {
               entity.rotation = rand.nextInt(360);
            }

            entity.texture = this.tex;
            entity.framecount = this.newFramecount;
            entity.animDelay = this.newDelay;
            if (this.enableAcidRend) {
               entity.acidRender = true;
               entity.alphaGlowing = false;
            } else if (this.enableAlphaGlow) {
               entity.acidRender = false;
               entity.alphaGlowing = true;
            }

            if (this.newDisableOnEnd && !entity.disableOnEnd) {
               entity.disableOnEnd = true;
               entity.animCounter = 0;
            }
         }
      }
   }

   public static class TrackerColorTimeMultiply extends ParticleTracker<GUNParticle> {
      public float colorRmult;
      public float colorGmult;
      public float colorBmult;
      public int startTime;
      public int targetTime;

      public TrackerColorTimeMultiply(float colorRmult, float colorGmult, float colorBmult, int startTime, int targetTime) {
         this.startTime = startTime;
         this.targetTime = targetTime;
         this.colorRmult = colorRmult;
         this.colorGmult = colorGmult;
         this.colorBmult = colorBmult;
      }

      public void update(GUNParticle entity) {
         if (entity.ticksExisted >= this.startTime && entity.ticksExisted <= this.targetTime) {
            entity.Red = entity.Red * this.colorRmult;
            entity.Green = entity.Green * this.colorGmult;
            entity.Blue = entity.Blue * this.colorBmult;
         }
      }
   }

   public static class TrackerDelayCollide extends ParticleTracker<GUNParticle> {
      public void update(GUNParticle entity) {
         if (entity.ticksExisted > 4) {
            entity.collide = true;
         }
      }
   }

   public static class TrackerDrunkFollowPoint extends ParticleTracker {
      public Vec3d point = null;
      public boolean deadOnArrive = false;
      public float arriveradius = 0.2F;
      public float tickPowerIncrease = 0.0F;
      public float power = 0.1F;
      public float frictionMult = 0.9F;
      public float tickfrictionAdd = 0.0F;
      public float sinusMultipl;
      public float drunkPower;
      public Vec3d sinAxis;

      public TrackerDrunkFollowPoint(
         Vec3d point, boolean deadOnArrive, float arriveradius, float tickPowerIncrease, float power, float sinusMultipl, float drunkPower, Vec3d direction
      ) {
         this.point = point;
         this.deadOnArrive = deadOnArrive;
         this.arriveradius = arriveradius * arriveradius;
         this.tickPowerIncrease = tickPowerIncrease;
         this.power = power;
         this.sinusMultipl = sinusMultipl;
         this.drunkPower = drunkPower;
         this.sinAxis = direction;
      }

      @Override
      public void update(Entity entity) {
         entity.motionX = entity.motionX * this.frictionMult;
         entity.motionY = entity.motionY * this.frictionMult;
         entity.motionZ = entity.motionZ * this.frictionMult;
         float drunk = MathHelper.sin(entity.ticksExisted * this.sinusMultipl) * this.drunkPower;
         entity.motionX = entity.motionX + this.sinAxis.x * drunk;
         entity.motionY = entity.motionY + this.sinAxis.y * drunk;
         entity.motionZ = entity.motionZ + this.sinAxis.z * drunk;
         SuperKnockback.applyMove(entity, -this.power, this.point.x, this.point.y, this.point.z);
         this.power = this.power + this.tickPowerIncrease;
         this.frictionMult = this.frictionMult + this.tickfrictionAdd;
         if (this.deadOnArrive && entity.getDistanceSq(this.point.x, this.point.y, this.point.z) < this.arriveradius) {
            entity.setDead();
         }
      }
   }

   public static class TrackerFollowDynamicPoint extends ParticleTracker {
      public Entity point = null;
      public boolean deadOnArrive = false;
      public float arriveradius = 0.2F;
      public float tickPowerIncrease = 0.0F;
      public float power = 0.1F;
      public float frictionMult = 0.9F;
      public float tickfrictionAdd = 0.0F;
      public float addY = 0.0F;

      public TrackerFollowDynamicPoint(Entity point, boolean deadOnArrive, float arriveradius, float tickPowerIncrease, float power) {
         this.point = point;
         this.deadOnArrive = deadOnArrive;
         this.arriveradius = arriveradius * arriveradius;
         this.tickPowerIncrease = tickPowerIncrease;
         this.power = power;
      }

      public TrackerFollowDynamicPoint setYadd(float f) {
         this.addY = f;
         return this;
      }

      @Override
      public void update(Entity entity) {
         entity.motionX = entity.motionX * this.frictionMult;
         entity.motionY = entity.motionY * this.frictionMult;
         entity.motionZ = entity.motionZ * this.frictionMult;
         SuperKnockback.applyMove(entity, -this.power, this.point.posX, this.point.posY + this.addY, this.point.posZ);
         this.power = this.power + this.tickPowerIncrease;
         this.frictionMult = this.frictionMult + this.tickfrictionAdd;
         if (this.deadOnArrive
            && entity.getDistanceSq(this.point.posX, this.point.posY + this.addY, this.point.posZ) < this.arriveradius) {
            entity.setDead();
         }
      }
   }

   public static class TrackerFollowStaticPoint extends ParticleTracker {
      public Vec3d point = null;
      public boolean deadOnArrive = false;
      public float arriveradius = 0.2F;
      public float tickPowerIncrease = 0.0F;
      public float power = 0.1F;
      public float frictionMult = 0.9F;
      public float tickfrictionAdd = 0.0F;

      public TrackerFollowStaticPoint(Vec3d point, boolean deadOnArrive, float arriveradius, float tickPowerIncrease, float power) {
         this.point = point;
         this.deadOnArrive = deadOnArrive;
         this.arriveradius = arriveradius * arriveradius;
         this.tickPowerIncrease = tickPowerIncrease;
         this.power = power;
      }

      @Override
      public void update(Entity entity) {
         entity.motionX = entity.motionX * this.frictionMult;
         entity.motionY = entity.motionY * this.frictionMult;
         entity.motionZ = entity.motionZ * this.frictionMult;
         SuperKnockback.applyMove(entity, -this.power, this.point.x, this.point.y, this.point.z);
         this.power = this.power + this.tickPowerIncrease;
         this.frictionMult = this.frictionMult + this.tickfrictionAdd;
         if (this.deadOnArrive && entity.getDistanceSq(this.point.x, this.point.y, this.point.z) < this.arriveradius) {
            entity.setDead();
         }
      }
   }

   public static class TrackerFractalRender extends ParticleTracker<GUNParticle> {
      public int count;
      public float stepMultiplier;
      public float dirX;
      public float dirY;
      public float dirZ;
      public float rotateAngle;
      public float rotateExponent;
      public int showTime;
      public int showedTime;
      public int hideTime;
      public int redirectionStep1;
      public int redirectionStep2;

      public TrackerFractalRender(
         int count, float stepMultiplier, float rotateAngle, float rotateExponent, float redirectionChance, int showTime, int showedTime, int hideTime
      ) {
         this.count = count;
         this.stepMultiplier = stepMultiplier;
         this.rotateAngle = rotateAngle;
         this.rotateExponent = rotateExponent;
         this.showTime = showTime;
         this.showedTime = showedTime;
         this.hideTime = hideTime;
         this.recreateDirection(rand);
         if (rand.nextFloat() < redirectionChance) {
            this.redirectionStep1 = rand.nextInt(count);
         } else {
            this.redirectionStep1 = -1;
         }

         if (rand.nextFloat() < redirectionChance) {
            this.redirectionStep2 = rand.nextInt(count);
         } else {
            this.redirectionStep2 = -1;
         }
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.pushMatrix();
         float translate = 1.7F;
         float rotate = this.rotateAngle;
         float size = 1.0F;
         float time = entity.ticksExisted + partialTicks;

         for (int i = 0; i < this.count; i++) {
            float alpha = GetMOP.getfromto(time, (float)i, (float)(i + this.showTime))
               - GetMOP.getfromto(time, (float)(i + this.showedTime), (float)(i + this.showedTime + this.hideTime));
            GlStateManager.color(entity.Red, entity.Green, entity.Blue, alpha * entity.alpha);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-size, -size, 0.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-size, size, 0.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(size, size, 0.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(size, -size, 0.0).tex(1.0, 1.0).endVertex();
            tessellator.draw();
            GlStateManager.rotate(rotate, this.dirX, this.dirY, this.dirZ);
            GlStateManager.translate(0.0F, translate, 0.0F);
            translate *= this.stepMultiplier;
            size *= this.stepMultiplier;
            rotate *= this.rotateExponent;
            if (i == this.redirectionStep1 || i == this.redirectionStep2) {
               this.recreateDirection(new Random(this.redirectionStep1 + this.redirectionStep2));
            }
         }

         float alpha1 = GetMOP.getfromto(time, 0.0F, (float)this.showTime)
            - GetMOP.getfromto(time, (float)this.showedTime, (float)(this.showedTime + this.hideTime));
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, alpha1 * entity.alpha);
         GlStateManager.popMatrix();
      }

      public void recreateDirection(Random rand1) {
         this.dirX = rand1.nextFloat() - 0.5F;
         this.dirY = rand1.nextFloat() - 0.5F;
         this.dirZ = rand1.nextFloat() - 0.5F;
      }
   }

   public static class TrackerGlassShard extends ParticleTracker<GUNParticle> {
      public float rotPitch;
      public float rotYaw;
      public int rotMain;
      public boolean insert;

      public TrackerGlassShard(float rotSpeedPitch, float rotSpeedYaw, int rotSpeedMain, boolean insert) {
         this.rotPitch = rotSpeedPitch;
         this.rotYaw = rotSpeedYaw;
         this.rotMain = rotSpeedMain;
         this.insert = insert;
      }

      public void update(GUNParticle entity) {
         if (!entity.dropped) {
            Vec2f vec = entity.rotationPitchYaw;
            entity.rotationPitchYaw = new Vec2f(vec.x + this.rotPitch, vec.y + this.rotYaw);
            entity.rotation = entity.rotation + this.rotMain;
         } else if (!this.insert) {
            entity.rotationPitchYaw = null;
         }
      }
   }

   public static class TrackerGradient extends ParticleTracker<GUNParticle> {
      public Vec3d startColor;
      public Vec3d newColor;
      public int colorChangeStartTime;
      public int colorChangeEndTime;

      public TrackerGradient(Vec3d startColor, Vec3d newColor, int colorChangeStartTime, int colorChangeEndTime) {
         this.startColor = startColor;
         this.newColor = newColor;
         this.colorChangeStartTime = colorChangeStartTime;
         this.colorChangeEndTime = colorChangeEndTime;
      }

      public void update(GUNParticle entity) {
         float ft1 = GetMOP.getfromto((float)entity.ticksExisted, (float)this.colorChangeStartTime, (float)this.colorChangeEndTime);
         float ft2 = 1.0F - ft1;
         entity.Red = (float)(ft1 * this.newColor.x + ft2 * this.startColor.x);
         entity.Green = (float)(ft1 * this.newColor.y + ft2 * this.startColor.y);
         entity.Blue = (float)(ft1 * this.newColor.z + ft2 * this.startColor.z);
      }
   }

   public static class TrackerLinkBeams extends ParticleTracker<GUNParticle> {
      public ResourceLocation beamTex;
      public float colorR;
      public float colorG;
      public float colorB;
      public float size;
      public int maxNodes;
      public int livetimeBeam;
      public float chance;
      public float spread = 0.1F;
      public float upMotion = 0.1F;
      public static long insuranceWorldTime = 0L;
      public boolean useMultipliedSize = false;
      public float findRadius = 1.3F;
      public int identifier;

      public TrackerLinkBeams(
         int identifier,
         ResourceLocation beamTex,
         float colorR,
         float colorG,
         float colorB,
         float size,
         int maxNodes,
         int livetimeBeam,
         float chance,
         float findRadius
      ) {
         this.beamTex = beamTex;
         this.colorR = colorR;
         this.colorG = colorG;
         this.colorB = colorB;
         this.size = size;
         this.maxNodes = maxNodes;
         this.livetimeBeam = livetimeBeam;
         this.chance = chance;
         this.findRadius = findRadius;
         this.identifier = identifier;
      }

      @Override
      public int hashCode() {
         return this.identifier;
      }

      public TrackerLinkBeams useMultipliedSize() {
         this.useMultipliedSize = true;
         return this;
      }

      public void update(GUNParticle entity) {
         if (entity.world.getWorldTime() != insuranceWorldTime && rand.nextFloat() < this.chance) {
            List<Entity> list = GetMOP.getEntitiesInAABBof(entity.world, entity, (double)this.findRadius, entity);
            GUNParticle other = null;

            for (Entity e : list) {
               if (e instanceof GUNParticle) {
                  GUNParticle gunParticle = (GUNParticle)e;
                  if (gunParticle.tracker != null && gunParticle.tracker.hashCode() == this.identifier) {
                     other = gunParticle;
                  }
               }
            }

            if (other != null) {
               int nodes = rand.nextInt(this.maxNodes) + 1;
               Vec3d pos1 = entity.getPositionVector();
               Vec3d posTarg = other.getPositionVector();
               Vec3d subVector = posTarg.subtract(pos1).scale(1.0F / nodes);
               Vec3d prevpos1 = pos1;

               for (int i = 1; i <= nodes; i++) {
                  Vec3d pos2;
                  if (i == nodes) {
                     pos2 = posTarg;
                  } else {
                     pos2 = pos1.add(
                        subVector.x * i + rand.nextGaussian() * this.spread,
                        subVector.y * i + rand.nextGaussian() * this.spread + this.upMotion * rand.nextFloat(),
                        subVector.z * i + rand.nextGaussian() * this.spread
                     );
                  }

                  BetweenParticle laser = new BetweenParticle(
                     entity.world,
                     this.beamTex,
                     this.useMultipliedSize ? entity.scale * this.size : this.size,
                     240,
                     this.colorR,
                     this.colorG,
                     this.colorB,
                     1.0F / this.livetimeBeam,
                     prevpos1.distanceTo(pos2),
                     this.livetimeBeam,
                     0.0F,
                     9999.0F,
                     prevpos1,
                     pos2
                  );
                  laser.alphaGlowing = true;
                  laser.setPosition(prevpos1.x, prevpos1.y, prevpos1.z);
                  entity.world.spawnEntity(laser);
                  prevpos1 = pos2;
               }

               insuranceWorldTime = entity.world.getWorldTime();
            }
         }
      }
   }

   public static class TrackerMagicSpin extends ParticleTracker<GUNParticle> {
      public boolean negative;
      public float speed;

      public TrackerMagicSpin(boolean negative, float speed) {
         this.negative = negative;
         this.speed = speed;
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
         GlStateManager.rotate((entity.ticksExisted + partialTicks) * this.speed, 0.0F, 0.0F, 1.0F);
      }
   }

   public static class TrackerMotionVector extends ParticleTracker<Entity> {
      public float motionX;
      public float motionY;
      public float motionZ;
      public float power;
      public int ticksToPowerMax;
      public float powerMax;

      public TrackerMotionVector(float motionX, float motionY, float motionZ, float power, float powerMax, int ticksToPowerMax) {
         this.motionX = motionX;
         this.motionY = motionY;
         this.motionZ = motionZ;
         this.power = power;
         this.ticksToPowerMax = ticksToPowerMax;
         this.powerMax = powerMax;
      }

      @Override
      public void update(Entity entity) {
         float a = MathHelper.clamp((float)entity.ticksExisted / this.ticksToPowerMax, 0.0F, 1.0F);
         float finalPower = this.power * (1.0F - a) + this.powerMax * a;
         entity.motionX = entity.motionX + this.motionX * finalPower;
         entity.motionY = entity.motionY + this.motionY * finalPower;
         entity.motionZ = entity.motionZ + this.motionZ * finalPower;
      }
   }

   public static class TrackerRandomMotion extends ParticleTracker<Entity> {
      public Vec3d mainVector;
      public float power;
      public int delay;
      Random rand = new Random();

      public TrackerRandomMotion(@Nullable Vec3d mainVector, float power, int delay) {
         this.mainVector = mainVector;
         this.power = power;
         this.delay = delay;
      }

      @Override
      public void update(Entity entity) {
         if (this.mainVector == null) {
            if (entity.ticksExisted % this.delay == 0) {
               entity.motionX = entity.motionX + this.rand.nextGaussian() * this.power;
               entity.motionY = entity.motionY + this.rand.nextGaussian() * this.power;
               entity.motionZ = entity.motionZ + this.rand.nextGaussian() * this.power;
               entity.velocityChanged = true;
            }
         } else if (entity.ticksExisted % this.delay == 0) {
            entity.motionX = entity.motionX + (this.rand.nextGaussian() * this.power + this.rand.nextFloat() * this.mainVector.x);
            entity.motionY = entity.motionY + (this.rand.nextGaussian() * this.power + this.rand.nextFloat() * this.mainVector.y);
            entity.motionZ = entity.motionZ + (this.rand.nextGaussian() * this.power + this.rand.nextFloat() * this.mainVector.z);
            entity.velocityChanged = true;
         }
      }
   }

   public static class TrackerRotate extends ParticleTracker<GUNParticle> {
      public int speed;
      public boolean rotateOnDropped = true;

      public TrackerRotate(int speed) {
         this.speed = speed;
      }

      public TrackerRotate(int speed, boolean rotateOnDropped) {
         this.speed = speed;
         this.rotateOnDropped = rotateOnDropped;
      }

      public void update(GUNParticle entity) {
         if (this.rotateOnDropped || !entity.dropped) {
            entity.rotation = entity.rotation + this.speed;
         }
      }
   }

   public static class TrackerSinusScaling extends ParticleTracker<GUNParticle> {
      public float amplitude;
      public float sinSpeed;

      public TrackerSinusScaling(float amplitude, float sinSpeed) {
         this.amplitude = amplitude;
         this.sinSpeed = sinSpeed;
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
         float finalscale = 1.0F
            + MathHelper.sin((entity.ticksExisted + entity.getEntityId() * 2.3481033F + partialTicks) * this.sinSpeed) * this.amplitude;
         GlStateManager.scale(finalscale, finalscale, finalscale);
      }
   }

   public static class TrackerSmoothShowHide extends ParticleTracker<GUNParticle> {
      public Vec3d[] vectorsAlpha = null;
      public Vec3d[] vectorsScale = null;

      public TrackerSmoothShowHide(@Nullable Vec3d[] vectorsAlpha, @Nullable Vec3d[] vectorsScale) {
         this.vectorsAlpha = vectorsAlpha;
         this.vectorsScale = vectorsScale;
      }

      public void update(GUNParticle entity) {
         if (this.vectorsAlpha != null) {
            for (Vec3d vect : this.vectorsAlpha) {
               if (entity.ticksExisted > vect.x && entity.ticksExisted < vect.y) {
                  entity.alpha = (float)(entity.alpha + vect.z);
                  break;
               }
            }
         }

         if (this.vectorsScale != null) {
            for (Vec3d vectx : this.vectorsScale) {
               if (entity.ticksExisted > vectx.x && entity.ticksExisted < vectx.y) {
                  entity.scale = (float)(entity.scale + vectx.z);
                  break;
               }
            }
         }
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
         if (this.vectorsScale != null) {
            for (Vec3d vect : this.vectorsScale) {
               if (entity.ticksExisted > vect.x && entity.ticksExisted < vect.y) {
                  float finalscale = (float)(1.0 - vect.z + vect.z * partialTicks);
                  GlStateManager.scale(finalscale, finalscale, finalscale);
                  break;
               }
            }
         }
      }
   }

   public static class TrackerSmoothShowHideBetweenP extends ParticleTracker<BetweenParticle> {
      public Vec3d[] vectorsAlpha = null;
      public Vec3d[] vectorsScale = null;

      public TrackerSmoothShowHideBetweenP(@Nullable Vec3d[] vectorsAlpha, @Nullable Vec3d[] vectorsScale) {
         this.vectorsAlpha = vectorsAlpha;
         this.vectorsScale = vectorsScale;
      }

      public void update(BetweenParticle entity) {
         if (this.vectorsAlpha != null) {
            for (Vec3d vect : this.vectorsAlpha) {
               if (entity.ticksExisted > vect.x && entity.ticksExisted < vect.y) {
                  entity.alpha = (float)(entity.alpha + vect.z);
                  break;
               }
            }
         }

         if (this.vectorsScale != null) {
            for (Vec3d vectx : this.vectorsScale) {
               if (entity.ticksExisted > vectx.x && entity.ticksExisted < vectx.y) {
                  entity.scale = (float)(entity.scale + vectx.z);
                  break;
               }
            }
         }
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      }
   }

   public static class TrackerSpawnEntity extends ParticleTracker<GUNParticle> {
      public Entity entityToSpawn;
      public int type;
      public boolean lastDropped = false;
      public boolean deleteOriginalParticle;

      public TrackerSpawnEntity(Entity entityToSpawn, int type, boolean deleteOriginalParticle) {
         this.entityToSpawn = entityToSpawn;
         this.type = type;
         this.deleteOriginalParticle = deleteOriginalParticle;
      }

      public void update(GUNParticle entity) {
         if (this.entityToSpawn != null) {
            if (this.type == 0) {
               if (entity.dropped && !this.lastDropped) {
                  this.entityToSpawn.setPosition(entity.posX, entity.posY, entity.posZ);
                  entity.world.spawnEntity(this.entityToSpawn);
                  this.entityToSpawn = null;
                  if (this.deleteOriginalParticle) {
                     entity.setDead();
                  }
               }

               this.lastDropped = entity.dropped;
            } else if (entity.ticksExisted == this.type) {
               this.entityToSpawn.setPosition(entity.posX, entity.posY, entity.posZ);
               entity.world.spawnEntity(this.entityToSpawn);
               this.entityToSpawn = null;
               if (this.deleteOriginalParticle) {
                  entity.setDead();
               }
            }
         }
      }
   }

   public static class TrackerTrailParticleGradient extends ParticleTracker<TrailParticle> {
      public Vec3d startColor;
      public Vec3d newColor;
      public int colorChangeStartTime;
      public int colorChangeEndTime;

      public TrackerTrailParticleGradient(Vec3d startColor, Vec3d newColor, int colorChangeStartTime, int colorChangeEndTime) {
         this.startColor = startColor;
         this.newColor = newColor;
         this.colorChangeStartTime = colorChangeStartTime;
         this.colorChangeEndTime = colorChangeEndTime;
      }

      public void update(TrailParticle entity) {
         float ft1 = GetMOP.getfromto((float)entity.ticksExisted, (float)this.colorChangeStartTime, (float)this.colorChangeEndTime);
         float ft2 = 1.0F - ft1;
         entity.Red = (float)(ft1 * this.newColor.x + ft2 * this.startColor.x);
         entity.Green = (float)(ft1 * this.newColor.y + ft2 * this.startColor.y);
         entity.Blue = (float)(ft1 * this.newColor.z + ft2 * this.startColor.z);
         entity.TRed = entity.Red;
         entity.TGreen = entity.Green;
         entity.TBlue = entity.Blue;
      }
   }

   public static class TrackerTrajectory extends ParticleTracker<GUNParticle> {
      public int[] times;
      public Vec3d[] positions;

      public TrackerTrajectory(int[] times, Vec3d[] positions) {
         this.times = times;
         this.positions = positions;
      }

      public void update(GUNParticle entity) {
         for (int i = 1; i < this.times.length; i++) {
            int time = this.times[i];
            int prevtime = this.times[i - 1];
            if (entity.ticksExisted < time && entity.ticksExisted >= prevtime) {
               float ft1 = GetMOP.getfromto((float)entity.ticksExisted, (float)prevtime, (float)time);
               Vec3d point = this.positions[i];
               Vec3d prevpoint = this.positions[i - 1];
               if (Debugger.floats[0] == 0.0F) {
                  entity.posX = GetMOP.partial(point.x, prevpoint.x, (double)ft1);
                  entity.posY = GetMOP.partial(point.y, prevpoint.y, (double)ft1);
                  entity.posZ = GetMOP.partial(point.z, prevpoint.z, (double)ft1);
               } else {
                  entity.setPosition(
                     GetMOP.partial(point.x, prevpoint.x, (double)ft1),
                     GetMOP.partial(point.y, prevpoint.y, (double)ft1),
                     GetMOP.partial(point.z, prevpoint.z, (double)ft1)
                  );
               }
            }
         }
      }
   }

   public static class TrackerTurbulence extends ParticleTracker<GUNParticle> {
      public NoiseGenerator3D noiseX;
      public NoiseGenerator3D noiseY;
      public NoiseGenerator3D noiseZ;
      public float noiseScale;
      public float speedScale;

      public TrackerTurbulence(NoiseGenerator3D noiseX, NoiseGenerator3D noiseY, NoiseGenerator3D noiseZ, float noiseScale, float speedScale) {
         this.noiseX = noiseX;
         this.noiseY = noiseY;
         this.noiseZ = noiseZ;
         this.noiseScale = noiseScale;
         this.speedScale = speedScale;
      }

      public void update(GUNParticle entity) {
         double scaledX = entity.posX * this.noiseScale;
         double scaledY = entity.posY * this.noiseScale;
         double scaledZ = entity.posZ * this.noiseScale;
         entity.motionX = entity.motionX + this.speedScale * this.noiseX.getValue(scaledX, scaledY, scaledZ);
         entity.motionY = entity.motionY + this.speedScale * this.noiseY.getValue(scaledX, scaledY, scaledZ);
         entity.motionZ = entity.motionZ + this.speedScale * this.noiseZ.getValue(scaledX, scaledY, scaledZ);
      }
   }

   public static class TrackerVoidRender extends ParticleTracker<GUNParticle> {
      public float amountTranslate;
      public float from;
      public float to;

      public TrackerVoidRender(float amountTranslate, float from, float to) {
         this.amountTranslate = amountTranslate;
         this.from = from;
         this.to = to;
      }

      public void render(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
         float ft = GetMOP.getfromto(entity.ticksExisted + partialTicks, this.from, this.to) * this.amountTranslate;
         double xx = x * ft;
         double yy = y * ft;
         double zz = z * ft;
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha / 2.0F);
         GlStateManager.translate(xx, yy, zz);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.translate(-xx * 2.0, -yy * 2.0, -zz * 2.0);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.translate(xx, yy, zz);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      }
   }
}

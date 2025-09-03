//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.elements.ItemWeatherRocket;
import com.Vivern.Arpg.main.ParticleFastSummon;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.RenderModule;
import com.Vivern.Arpg.weather.WorldEvent;
import com.Vivern.Arpg.weather.WorldEventsHandler;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWeatherRocket extends StandardBullet implements RenderModule.IRenderModuleOverride {
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
   public static ResourceLocation star3 = new ResourceLocation("arpg:textures/star3.png");
   @Nullable
   public String eventName;
   public boolean stopAllBeforeStart;
   private static final DataParameter<Integer> TEXTURE_ID = EntityDataManager.createKey(EntityWeatherRocket.class, DataSerializers.VARINT);

   public EntityWeatherRocket(World world) {
      super(world);
      this.rotationPitch = 90.0F;
      this.prevRotationPitch = 90.0F;
   }

   public EntityWeatherRocket(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.rotationPitch = 90.0F;
      this.prevRotationPitch = 90.0F;
   }

   public EntityWeatherRocket(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.rotationPitch = 90.0F;
      this.prevRotationPitch = 90.0F;
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(TEXTURE_ID, 0);
   }

   public void setTextureId(int id) {
      this.dataManager.set(TEXTURE_ID, id);
   }

   @Override
   public void rewriteModuleParameters(RenderModule module) {
      int index = (Integer)this.dataManager.get(TEXTURE_ID);
      if (index >= 0 && index < ItemWeatherRocket.rocketTextures.size()) {
         module.tex = ItemWeatherRocket.rocketTextures.get(index);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.motionY += 0.05;
      if (!this.world.isRemote) {
         if (this.ticksExisted > 60) {
            if (this.world.provider instanceof AbstractWorldProvider) {
               AbstractWorldProvider abstractWorldProvider = (AbstractWorldProvider)this.world.provider;
               WorldEventsHandler worldEventsHandler = abstractWorldProvider.getWorldEventsHandler();
               if (worldEventsHandler != null) {
                  if (this.stopAllBeforeStart) {
                     for (int i = 0; i < worldEventsHandler.events.length; i++) {
                        WorldEvent has = worldEventsHandler.events[i];
                        if (has.isStarted) {
                           has.stop();
                        }
                     }
                  }

                  if (this.eventName != null) {
                     for (int ix = 0; ix < worldEventsHandler.events.length; ix++) {
                        WorldEvent has = worldEventsHandler.events[ix];
                        String name = has.getClass().getSimpleName();
                        if (name.equals(this.eventName)) {
                           has.start();
                           break;
                        }
                     }
                  }
               }
            } else if (this.stopAllBeforeStart && this.eventName == null) {
               WorldInfo worldinfo = this.world.getWorldInfo();
               int ixx = (300 + new Random().nextInt(600)) * 20;
               worldinfo.setCleanWeatherTime(ixx);
               worldinfo.setRainTime(0);
               worldinfo.setThunderTime(0);
               worldinfo.setRaining(false);
               worldinfo.setThundering(false);
            }

            this.world.setEntityState(this, (byte)11);
            this.setDead();
         }
      } else {
         int lt = 10 + this.rand.nextInt(5);
         float scl = 0.16F;
         GUNParticle part = new GUNParticle(
            star2,
            scl,
            0.002F,
            lt,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            -0.01F,
            0.0F,
            this.getRED(),
            this.getGREEN(),
            this.getBLUE(),
            true,
            this.rand.nextInt(360)
         );
         part.scaleTickAdding = -scl / (lt + 2);
         part.randomDeath = false;
         part.alphaGlowing = true;
         this.world.spawnEntity(part);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 11) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.weather_firework_explode,
               SoundCategory.AMBIENT,
               10.0F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
         ParticleFastSummon.round2(
            this.world, ParticleFastSummon.circle2, this.getPositionVector(), 0.1F, 30.0F, 40, 240, this.getRED(), this.getGREEN(), this.getBLUE(), 2
         );
         float r = this.getRED();
         float g = this.getGREEN();
         float b = this.getBLUE();

         for (int i = 0; i < 25; i++) {
            int lt = 50 + this.rand.nextInt(30);
            int EElt = 20 + this.rand.nextInt(10);
            float scl = 0.25F;
            GunPEmitter part = new GunPEmitter(
               star3,
               scl * 6.0F,
               0.002F,
               lt,
               240,
               this.world,
               this.posX + this.motionX * this.rand.nextFloat(),
               this.posY + this.motionY * this.rand.nextFloat(),
               this.posZ + this.motionZ * this.rand.nextFloat(),
               (float)this.rand.nextGaussian(),
               (float)this.rand.nextGaussian() * 0.5F + 0.5F,
               (float)this.rand.nextGaussian(),
               r,
               g,
               b,
               true,
               3,
               false,
               star2,
               scl * 1.5F,
               0.003F,
               EElt,
               240,
               250.0F,
               250.0F,
               250.0F,
               r,
               g,
               b,
               true
            );
            part.alpha = 3.0F;
            part.alphaTickAdding = -3.0F / lt;
            part.scaleTickAdding = -scl * 3.0F / lt / 2.0F;
            part.EEsclTickAdd = -(scl * 2.0F) / EElt;
            part.alphaGlowing = true;
            part.EEalphaGlowing = true;
            this.world.spawnEntity(part);
         }
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
   }
}

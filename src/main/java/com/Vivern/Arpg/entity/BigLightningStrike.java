//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.models.LaserModel;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BigLightningStrike extends Entity {
   public BigLightningSegment mainSegment;
   public Random strikerand;
   private static final DataParameter<Float> SEGMENT_LENGTH = EntityDataManager.createKey(BigLightningStrike.class, DataSerializers.FLOAT);
   private static final DataParameter<Float> SEGMENT_WIDTH = EntityDataManager.createKey(BigLightningStrike.class, DataSerializers.FLOAT);
   public int segmentsCount = 0;
   public static float[] color = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
   static ResourceLocation lightning4 = new ResourceLocation("arpg:textures/lightning4.png");
   public LaserModel model;
   public boolean blockImpacted = false;
   public boolean deadd = false;
   AxisAlignedBB renderbb;

   public BigLightningStrike(World worldIn) {
      super(worldIn);
      this.strikerand = new Random(this.getEntityId());
   }

   public boolean isInRangeToRenderDist(double distance) {
      return distance < 65536.0;
   }

   protected void entityInit() {
      this.dataManager.register(SEGMENT_LENGTH, 6.0F);
      this.dataManager.register(SEGMENT_WIDTH, 1.0F);
   }

   public void setSegmentParams(float length, float width) {
      this.dataManager.set(SEGMENT_LENGTH, length);
      this.dataManager.set(SEGMENT_WIDTH, width);
   }

   public float getSegmentLength() {
      return (Float)this.dataManager.get(SEGMENT_LENGTH);
   }

   public float getSegmentWidth() {
      return (Float)this.dataManager.get(SEGMENT_WIDTH);
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("deadd")) {
         this.deadd = compound.getBoolean("deadd");
      }
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      compound.setBoolean("deadd", true);
   }

   public static int getSegmentsLimit() {
      return 50;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.deadd) {
         this.setDead();
      } else {
         if (this.model == null) {
            this.model = new LaserModel.LaserModelLinear(
               lightning4, 240, 2, this.getSegmentWidth(), 0.0F, color, color, 0.0F, 0.0F, this.getSegmentLength(), 0.0F
            );
         }

         if (this.mainSegment == null) {
            this.mainSegment = new BigLightningSegment(this.getPositionVector(), this.nextPos(this.getPositionVector(), 0.2F), 0, 1.0F, null);
            if (this.renderbb == null) {
               this.renderbb = new AxisAlignedBB(
                  this.posX - 100.0,
                  this.posY - 250.0,
                  this.posZ - 100.0,
                  this.posX + 100.0,
                  this.posY + 250.0,
                  this.posZ + 100.0
               );
            }
         }

         this.mainSegment.onUpdate(this);
         if (!this.world.isRemote && this.ticksExisted > getSegmentsLimit() + 20) {
            this.setDead();
         }

         if (this.ticksExisted == 1) {
            this.world.setEntityState(this, (byte)11);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 11 && Minecraft.getMinecraft().player != null) {
         this.world
            .playSound(
               this.posX,
               Minecraft.getMinecraft().player.posY,
               this.posZ,
               Sounds.lightning,
               SoundCategory.WEATHER,
               5.0F,
               0.85F + this.rand.nextFloat() / 4.0F,
               false
            );
         if (this.rand.nextFloat() < 0.3) {
            this.world
               .playSound(
                  this.posX,
                  Minecraft.getMinecraft().player.posY,
                  this.posZ,
                  Sounds.thunder,
                  SoundCategory.WEATHER,
                  15.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }
   }

   public void move(MoverType type, double x, double y, double z) {
   }

   public boolean handleWaterMovement() {
      return false;
   }

   public boolean isOverWater() {
      return false;
   }

   protected boolean pushOutOfBlocks(double x, double y, double z) {
      return false;
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return this.renderbb == null ? super.getRenderBoundingBox() : this.renderbb;
   }

   public Vec3d nextPos(Vec3d startpos, float horizontality) {
      float vertically = 1.0F - horizontality;
      Vec3d randDirection = new Vec3d(
            this.strikerand.nextGaussian() * horizontality, -this.strikerand.nextFloat() * vertically, this.strikerand.nextGaussian() * horizontality
         )
         .normalize();
      return startpos.add(randDirection.scale(this.getSegmentLength()));
   }

   public static class BigLightningSegment {
      public Vec3d posStart;
      public Vec3d posEnd;
      public int age;
      public float chanceContinue;
      public ArrayList<BigLightningSegment> childs = new ArrayList<>();
      public int ticksExisted;
      public boolean generated = false;
      public BigLightningSegment parent;

      public BigLightningSegment(Vec3d posStart, Vec3d posEnd, int age, float chanceContinue, BigLightningSegment parent) {
         this.posStart = posStart;
         this.posEnd = posEnd;
         this.age = age;
         this.chanceContinue = chanceContinue;
         this.parent = parent;
      }

      public float getBrightness() {
         return GetMOP.getfromto((float)this.ticksExisted, 0.0F, 2.0F)
            - GetMOP.getfromto((float)this.ticksExisted, 4.0F, 10.0F)
            + GetMOP.getfromto((float)this.ticksExisted, 100.0F, 102.0F)
            - GetMOP.getfromto((float)this.ticksExisted, 109.0F, 120.0F);
      }

      public void onUpdate(BigLightningStrike lightningStrike) {
         if (!this.childs.isEmpty()) {
            for (BigLightningSegment bigLightningSegment : this.childs) {
               bigLightningSegment.onUpdate(lightningStrike);
            }
         }

         if (!this.generated) {
            if (!GetMOP.thereIsNoBlockCollidesBetween(lightningStrike.world, this.posStart, this.posEnd, 1.0F, null, true)) {
               for (BigLightningSegment pr = this; pr != null; pr = pr.parent) {
                  pr.ticksExisted = 100;
               }

               lightningStrike.blockImpacted = true;
            }

            if (!lightningStrike.blockImpacted && this.chanceContinue > 0.0F && lightningStrike.strikerand.nextFloat() < this.chanceContinue) {
               if (lightningStrike.segmentsCount < BigLightningStrike.getSegmentsLimit()) {
                  this.childs
                     .add(
                        new BigLightningSegment(
                           this.posEnd, lightningStrike.nextPos(this.posEnd, 0.2F), this.age + 1, this.chanceContinue - 0.03F, this
                        )
                     );
                  lightningStrike.segmentsCount++;
               }

               if (this.age > 2
                  && lightningStrike.strikerand.nextFloat() < this.chanceContinue
                  && lightningStrike.segmentsCount < BigLightningStrike.getSegmentsLimit()) {
                  this.childs
                     .add(new BigLightningSegment(this.posEnd, lightningStrike.nextPos(this.posEnd, 0.5F), this.age + 1, 0.7F, this));
                  lightningStrike.segmentsCount++;
               }
            }

            this.generated = true;
         }

         this.ticksExisted++;
      }

      @SideOnly(Side.CLIENT)
      public void render(BigLightningStrike entity, double x, double y, double z, float entityYaw, float partialTicks) {
         if (entity.model != null) {
            float bright = this.getBrightness();
            if (bright > 0.0F) {
               BigLightningStrike.color[0] = bright;
               BigLightningStrike.color[1] = bright;
               BigLightningStrike.color[2] = bright;
               entity.model.renderLaserModelInWorld(entity, this.posStart, this.posEnd, partialTicks);
            }
         }

         if (!this.childs.isEmpty()) {
            for (BigLightningSegment bigLightningSegment : this.childs) {
               bigLightningSegment.render(entity, x, y, z, entityYaw, partialTicks);
            }
         }
      }
   }
}

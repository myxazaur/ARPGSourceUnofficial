//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.recipes.EnergyCost;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.IMagicVision;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class TileCrystalSphere extends TileEntity implements ITickable, IVialElementsAccepter, IMagicVision {
   public static ResourceLocation clouds11 = new ResourceLocation("arpg:textures/clouds11.png");
   public static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   public float energyStored = 0.0F;
   public float maxEnergyStored = 1000.0F;
   public ShardType energyType = null;
   public int gettingCooldown = 0;
   public static Random rand = new Random();

   @Override
   public float getElementEnergy(ShardType shardType) {
      return shardType == this.energyType ? this.energyStored : 0.0F;
   }

   @Override
   public float getElementCount(ShardType shardType) {
      return shardType == this.energyType ? this.energyStored : 0.0F;
   }

   @Override
   public float getMana() {
      return 0.0F;
   }

   public float addEnergy(float value) {
      float prev = this.energyStored;
      this.energyStored += value;
      if (this.energyStored > this.maxEnergyStored) {
         float ret = this.energyStored - this.maxEnergyStored;
         this.energyStored = this.maxEnergyStored;
         if (prev != this.energyStored) {
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         }

         return ret;
      } else {
         if (prev != this.energyStored) {
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         }

         return 0.0F;
      }
   }

   @Override
   public float acceptVialElements(ItemStack vial, ShardType shardType, float count) {
      if (!this.isEmpty() && this.energyType != shardType) {
         return count;
      } else {
         this.energyType = shardType;
         if (count > 0.0F) {
            float add = Math.min(this.maxEnergyStored - this.energyStored, count);
            this.energyStored += add;
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            return count - add;
         } else {
            float remove = Math.max(-this.energyStored, count);
            this.energyStored += remove;
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            return count - remove;
         }
      }
   }

   @Override
   public EnergyCost provideVialElements(ItemStack vial, float amount, boolean dontDecreaseIfLow) {
      float provided = Math.min(amount, this.energyStored);
      if (!dontDecreaseIfLow || provided == amount) {
         this.energyStored -= provided;
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, this.energyStored == 0.0F ? 64.0 : 16.0);
      }

      return provided == 0.0F ? null : new EnergyCost(this.energyType, provided);
   }

   public boolean isEmpty() {
      return this.energyType == null || this.energyStored <= 0.0F;
   }

   public void update() {
      if (this.world.isRemote && this.energyType == ShardType.AIR) {
         if (rand.nextFloat() < 0.15F) {
            double xx = this.pos.getX() + 0.5;
            double yy = this.pos.getY() + 0.375;
            double zz = this.pos.getZ() + 0.5;
            int lt = 30 + rand.nextInt(20);
            float vdecr = 110.0F;
            GUNParticle fog = new GUNParticle(
               clouds11,
               0.125F,
               0.0F,
               lt,
               170,
               this.world,
               xx,
               yy,
               zz,
               (rand.nextFloat() - 0.5F) / vdecr,
               (rand.nextFloat() - 0.5F) / vdecr,
               (rand.nextFloat() - 0.5F) / vdecr,
               this.energyType.colorR,
               this.energyType.colorG,
               this.energyType.colorB,
               true,
               rand.nextInt(360)
            );
            fog.alphaGlowing = true;
            fog.alphaTickAdding = -1.0F / lt;
            this.world.spawnEntity(fog);
         }

         if (rand.nextFloat() < 0.215F) {
            double xx = this.pos.getX() + 0.5;
            double rangeY = rand.nextFloat() - 0.5;
            double yy = this.pos.getY() + 0.375 + 0.25 * rangeY;
            double zz = this.pos.getZ() + 0.5;
            Vec3d vec = new Vec3d(xx, yy, zz);
            ParticleTracker.TrackerFollowStaticPoint tracker = new ParticleTracker.TrackerFollowStaticPoint(vec, false, 0.0F, 0.0F, 0.05F);
            tracker.frictionMult = 1.0F;
            int yawRand = rand.nextInt(360);
            Vec3d poss = GetMOP.YawToVec3d(yawRand).scale(0.1675 - 0.1 * Math.abs(rangeY)).add(vec);
            Vec3d direction = GetMOP.YawToVec3d(yawRand - 90).scale(0.06);
            float scl = 0.019625F + rand.nextFloat() * 0.01F;
            int lt = 50 + rand.nextInt(10);
            GUNParticle part = new GUNParticle(
               pixel,
               scl,
               0.0F,
               lt,
               150 + rand.nextInt(90),
               this.world,
               poss.x,
               poss.y,
               poss.z,
               (float)direction.x,
               (float)direction.y,
               (float)direction.z,
               this.energyType.colorR,
               this.energyType.colorG,
               this.energyType.colorB,
               false,
               0
            );
            part.scaleTickAdding = -scl / lt / 1.2F;
            part.tracker = tracker;
            this.world.spawnEntity(part);
         }
      }
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound tags = new NBTTagCompound();
      tags.setFloat("stored", this.energyStored);
      if (this.energyType != null) {
         tags.setString("type", this.energyType.getName());
      } else {
         tags.setString("type", "");
      }

      return new SPacketUpdateTileEntity(this.pos, 1, tags);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("type")) {
         this.energyType = ShardType.byNameNullable(compound.getString("type"));
      }
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound tags = super.getUpdateTag();
      tags.setFloat("stored", this.energyStored);
      if (this.energyType != null) {
         tags.setString("type", this.energyType.getName());
      } else {
         tags.setString("type", "");
      }

      return tags;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("type")) {
         this.energyType = ShardType.byNameNullable(compound.getString("type"));
      }

      super.handleUpdateTag(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("stored")) {
         this.energyStored = compound.getFloat("stored");
      }

      if (compound.hasKey("type")) {
         this.energyType = ShardType.byNameNullable(compound.getString("type"));
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setFloat("stored", this.energyStored);
      if (this.energyType != null) {
         compound.setString("type", this.energyType.getName());
      } else {
         compound.setString("type", "");
      }

      return super.writeToNBT(compound);
   }
}

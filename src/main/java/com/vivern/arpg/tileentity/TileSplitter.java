package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.Splitter;
import com.vivern.arpg.elements.Beaker;
import com.vivern.arpg.main.ItemsElements;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.renders.IMagicVision;
import com.vivern.arpg.renders.ParticleTentacle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileSplitter extends TileEntity implements ITickable, IMagicVision {
   public static ResourceLocation effectTex = new ResourceLocation("arpg:textures/generic_beam6.png");
   public static ResourceLocation effectTexVoid = new ResourceLocation("arpg:textures/generic_beam7.png");
   public float[] elementsDissolved = new float[12];
   public float dissolvedElementsSumm = 0.0F;
   public float maxElementsStore = 256.0F;
   public int ticksExisted = 0;
   public Item lastDissolvedItem = null;
   public int lastDissolvedMetadata = 0;
   public float[] color1 = new float[3];
   public float[] color2 = new float[3];
   public float[] color3 = new float[3];
   public float[] color4 = new float[3];
   public float[] targetcolor1 = new float[3];
   public float[] targetcolor2 = new float[3];
   public float[] targetcolor3 = new float[3];
   public float[] targetcolor4 = new float[3];
   public static Random rand = new Random();

   public void onPlayerUseBeaker(ItemStack beaker, EntityPlayer playerIn) {
      Beaker.BeakerFluid fluidlast = Beaker.readFromNbt(beaker, 16);
      if (fluidlast.isEmpty && this.dissolvedElementsSumm >= 8.0F) {
         Beaker.BeakerFluid beakerFluid = new Beaker.BeakerFluid(16);
         float laysAmount = 6.0F + Math.min(this.dissolvedElementsSumm, 64.0F) / 64.0F * 6.0F;
         if (this.dissolvedElementsSumm > 64.0F) {
            laysAmount += (this.dissolvedElementsSumm - 64.0F) / 64.0F * 4.0F;
         }

         for (int i = 0; i < laysAmount / 3.0F; i++) {
            int randslot = this.getRandomDissolveSlot();
            if (randslot >= 0) {
               spawnTentacleParticle(
                  this.world,
                  new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 0.75, this.pos.getZ() + 0.5),
                  new Vec3d(rand.nextFloat() - 0.5, 2.0, rand.nextFloat() - 0.5).normalize(),
                  playerIn,
                  ShardType.byId(randslot + 1),
                  6.0F,
                  false
               );
            }
         }

         this.generateBeakerPuzzle((int)MathHelper.clamp(laysAmount, 6.0F, 16.0F), beakerFluid);
         Beaker.writeToNbt(beaker, beakerFluid);
         this.lastDissolvedItem = null;
         this.lastDissolvedMetadata = 0;
         PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
      }
   }

   public static void spawnTentacleParticle(
      World world, Vec3d start, Vec3d rotationStart, EntityPlayer player, ShardType shardType, float vectorLength, boolean inverse
   ) {
      spawnTentacleParticle(world, start, rotationStart, player, EnumHand.MAIN_HAND, 0.2, -0.1, shardType, vectorLength, inverse);
   }

   public static void spawnTentacleParticle(
      World world,
      Vec3d start,
      Vec3d rotationStart,
      EntityPlayer player,
      @Nullable EnumHand hand,
      double shoulders,
      double yoffset,
      ShardType shardType,
      float vectorLength,
      boolean inverse
   ) {
      ParticleTentacle.PTPOINTsimple fixed = new ParticleTentacle.PTPOINTsimple(start, rotationStart);
      ParticleTentacle.PTPOINTplayerWeaponArm dynamic = new ParticleTentacle.PTPOINTplayerWeaponArm(player, hand, shoulders, yoffset);
      ParticleTentacle particleTentacle = inverse
         ? new ParticleTentacle(
            shardType != ShardType.VOID ? effectTex : effectTexVoid,
            0.1875F + rand.nextFloat() * 0.04F,
            0.1F,
            0.0F,
            13 + rand.nextInt(8),
            240,
            world,
            dynamic,
            fixed,
            0.0F,
            0.0F,
            0.0F,
            shardType.colorR,
            shardType.colorG,
            shardType.colorB,
            true,
            15,
            (1.0F + rand.nextFloat() * 0.25F) * vectorLength
         )
         : new ParticleTentacle(
            shardType != ShardType.VOID ? effectTex : effectTexVoid,
            0.1875F + rand.nextFloat() * 0.04F,
            0.1F,
            0.0F,
            13 + rand.nextInt(8),
            240,
            world,
            fixed,
            dynamic,
            0.0F,
            0.0F,
            0.0F,
            shardType.colorR,
            shardType.colorG,
            shardType.colorB,
            true,
            15,
            (1.0F + rand.nextFloat() * 0.25F) * vectorLength
         );
      particleTentacle.alphaGlowing = shardType != ShardType.VOID;
      world.spawnEntity(particleTentacle);
   }

   public void generateBeakerPuzzle(int laysAddAmount, Beaker.BeakerFluid beakerFluid) {
      HashMap<Integer, SimulatedRetort> simulatedRetorts = new HashMap<>();
      Set<Integer> idsUsed = new HashSet<>();
      int successAmount = 0;

      for (int i = 0; i < laysAddAmount; i++) {
         int randslot = this.getRandomDissolveSlot();
         if (randslot >= 0 && this.elementsDissolved[randslot] >= 1.0F) {
            int elementId = randslot + 1;
            if (simulatedRetorts.containsKey(elementId)) {
               SimulatedRetort retort = simulatedRetorts.get(elementId);
               retort.add();
               this.addElementDissolved(randslot, -1.0F);
               successAmount++;
            } else {
               SimulatedRetort retort = new SimulatedRetort(16, elementId);
               retort.add();
               simulatedRetorts.put(elementId, retort);
               this.addElementDissolved(randslot, -1.0F);
               successAmount++;
            }

            idsUsed.add(elementId);
         }
      }

      if (successAmount >= 6) {
         Integer[] idsUsedArray = new Integer[idsUsed.size()];
         idsUsed.toArray(idsUsedArray);

         for (int ix = 0; ix < laysAddAmount; ix++) {
            int elementId = idsUsedArray[rand.nextInt(idsUsedArray.length)];
            SimulatedRetort randomRetort = simulatedRetorts.get(elementId);
            if (rand.nextFloat() < 0.33F) {
               randomRetort.makeTopReactive();
            }

            byte modifier = randomRetort.take();
            if (modifier >= 0) {
               beakerFluid.add(elementId, modifier);
            } else {
               idsUsed.remove(elementId);
               if (idsUsed.size() == 0) {
                  return;
               }

               idsUsedArray = new Integer[idsUsed.size()];
               idsUsed.toArray(idsUsedArray);
            }
         }
      }
   }

   public void update() {
      this.ticksExisted++;
      if (!this.world.isRemote) {
         if (this.ticksExisted % 15 == 0) {
            double zxoffset = 1.0 - Splitter.boundOffset - Splitter.wallThickness;
            AxisAlignedBB aabb = new AxisAlignedBB(
               this.pos.getX() + Splitter.boundOffset,
               this.pos.getY() + 0.3125,
               this.pos.getZ() + Splitter.boundOffset,
               this.pos.getX() + zxoffset,
               this.pos.getY() + 0.85,
               this.pos.getZ() + zxoffset
            );
            List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, aabb);
            boolean atLeastOneDissolved = false;

            for (EntityItem entityItem : list) {
               for (int a = 0; a < 5; a++) {
                  if (this.dissolveItemStack(entityItem.getItem())) {
                     atLeastOneDissolved = true;
                  }
               }
            }

            if (atLeastOneDissolved) {
               PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
            }
         }
      } else {
         if (this.ticksExisted % 47 == 0 && this.dissolvedElementsSumm > 0.0F) {
            int slot = this.getRandomDissolveSlot();
            if (slot >= 0) {
               int randColArray = rand.nextInt(4);
               ShardType shardType = ShardType.byId(slot + 1);
               if (shardType != null) {
                  if (randColArray == 0) {
                     this.targetcolor1[0] = shardType.colorR;
                     this.targetcolor1[1] = shardType.colorG;
                     this.targetcolor1[2] = shardType.colorB;
                  } else if (randColArray == 1) {
                     this.targetcolor2[0] = shardType.colorR;
                     this.targetcolor2[1] = shardType.colorG;
                     this.targetcolor2[2] = shardType.colorB;
                  } else if (randColArray == 2) {
                     this.targetcolor3[0] = shardType.colorR;
                     this.targetcolor3[1] = shardType.colorG;
                     this.targetcolor3[2] = shardType.colorB;
                  } else if (randColArray == 3) {
                     this.targetcolor4[0] = shardType.colorR;
                     this.targetcolor4[1] = shardType.colorG;
                     this.targetcolor4[2] = shardType.colorB;
                  }
               }
            }
         }

         this.updateColorArray(this.color1, this.targetcolor1);
         this.updateColorArray(this.color2, this.targetcolor2);
         this.updateColorArray(this.color3, this.targetcolor3);
         this.updateColorArray(this.color4, this.targetcolor4);
      }
   }

   public int getRandomDissolveSlot() {
      float randf = rand.nextFloat() * this.dissolvedElementsSumm;

      for (int i = 0; i < 12; i++) {
         float amount = this.elementsDissolved[i];
         randf -= amount;
         if (randf <= 0.0F) {
            return i;
         }
      }

      return -1;
   }

   public void updateColorArray(float[] color, float[] targetcolor) {
      float add = 0.01F;

      for (int i = 0; i < 3; i++) {
         if (color[i] < targetcolor[i]) {
            color[i] = Math.min(color[i] + add, targetcolor[i]);
         } else if (color[i] > targetcolor[i]) {
            color[i] = Math.max(color[i] - add, targetcolor[i]);
         }
      }
   }

   public boolean dissolveItemStack(ItemStack itemStack) {
      if (itemStack.getCount() <= 0) {
         return false;
      } else {
         ItemsElements.ElementsPack elementsPack = ItemsElements.getAllElements(itemStack);

         for (int i = 0; i < 12; i++) {
            float extracted = Math.min(this.maxElementsStore - this.dissolvedElementsSumm, elementsPack.elementsAmount[i]);
            this.addElementDissolved(i, extracted);
         }

         this.lastDissolvedItem = itemStack.getItem();
         this.lastDissolvedMetadata = itemStack.getMetadata();
         itemStack.shrink(1);
         return true;
      }
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return this.elementsDissolved[shardType.id - 1];
   }

   @Override
   public float getMana() {
      return 0.0F;
   }

   public void addElementDissolved(int arraySlot, float amount) {
      this.elementsDissolved[arraySlot] = this.elementsDissolved[arraySlot] + amount;
      this.dissolvedElementsSumm += amount;
   }

   public void write(NBTTagCompound compound) {
      for (int i = 0; i < 12; i++) {
         compound.setFloat("dissolved_" + i, this.elementsDissolved[i]);
      }

      compound.setFloat("maxElementsStore", this.maxElementsStore);
   }

   public void read(NBTTagCompound compound) {
      this.dissolvedElementsSumm = 0.0F;

      for (int i = 0; i < 12; i++) {
         String tagname = "dissolved_" + i;
         if (compound.hasKey(tagname)) {
            float amount = compound.getFloat(tagname);
            this.elementsDissolved[i] = amount;
            this.dissolvedElementsSumm += amount;
         }
      }

      if (compound.hasKey("maxElementsStore")) {
         this.maxElementsStore = compound.getFloat("maxElementsStore");
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public static class SimulatedRetort {
      public int elementId;
      public int elementCount;
      public byte[] modifiers;

      public SimulatedRetort(int maxSize, int elementId) {
         this.modifiers = new byte[maxSize];
         this.elementId = elementId;
      }

      public void add() {
         if (this.elementCount < this.modifiers.length) {
            this.modifiers[this.elementCount] = 0;
            this.elementCount++;
         }
      }

      public byte take() {
         if (this.elementCount > 0) {
            this.elementCount--;
            return this.modifiers[this.elementCount];
         } else {
            return -1;
         }
      }

      public void makeTopReactive() {
         if (this.elementCount >= 3 && this.modifiers[this.elementCount - 1] == 0 && this.modifiers[this.elementCount - 2] == 0) {
            this.modifiers[this.elementCount - 2] = 1;
            this.modifiers[this.elementCount - 1] = 2;
         }
      }
   }
}

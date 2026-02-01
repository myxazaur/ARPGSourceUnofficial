package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketWorldEventToClients;
import java.util.Collection;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldEvent extends IRenderHandler {
   public AbstractWorldProvider worldProvider;
   public int ticksExisted;
   public int livetime;
   public int livetimeMin;
   public int livetimeMax;
   public boolean isStarted;
   public byte index;
   public int currentCooldown;
   public float showness;
   public float chanceToStart;
   public int delayToPacket;
   public int rainSoundCounter;
   public SoundEvent soundRain;
   public SoundEvent soundRainAbove;

   public WorldEvent(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
      this.worldProvider = worldProvider;
      this.index = (byte)index;
      this.livetimeMin = (int)(livetimeMin * WorldEventsHandler.timeOverclock);
      this.livetimeMax = (int)(livetimeMax * WorldEventsHandler.timeOverclock);
      this.chanceToStart = chanceToStart;
   }

   public void onUpdate() {
      this.ticksExisted++;
      if (this.ticksExisted > this.livetime) {
         this.stop();
      }

      if (this.delayToPacket >= 0) {
         if (this.delayToPacket % 20 == 0) {
            PacketWorldEventToClients packet = new PacketWorldEventToClients();
            packet.writeargs(this.index, (byte)3);
            PacketHandler.sendToDimension(packet, this.worldProvider.getDimension());
         }

         this.delayToPacket--;
      }
   }

   public void onUpdateClient(EntityPlayer player) {
      if (this.soundRain != null) {
         this.addRainSoundEffect(GetMOP.rand, 3, this.soundRain, this.soundRainAbove);
      }
   }

   public void delayedSendPacket() {
      if (this.delayToPacket < 41) {
         this.delayToPacket += 20;
      }
   }

   public void start() {
      this.isStarted = true;
      this.ticksExisted = 0;
      int var10003 = this.livetimeMax - this.livetimeMin;
      this.livetime = this.livetimeMin + this.worldProvider.getWorld().rand.nextInt(var10003 + 1);
      if (!this.worldProvider.getWorld().isRemote) {
         PacketWorldEventToClients packet = new PacketWorldEventToClients();
         packet.writeargs(this.index, (byte)1);
         PacketHandler.sendToDimension(packet, this.worldProvider.getDimension());
      }
   }

   public void stop() {
      this.isStarted = false;
      this.currentCooldown = (int)(this.getCooldown() * WorldEventsHandler.timeOverclock);
      if (!this.worldProvider.getWorld().isRemote) {
         PacketWorldEventToClients packet = new PacketWorldEventToClients();
         packet.writeargs(this.index, (byte)0);
         PacketHandler.sendToDimension(packet, this.worldProvider.getDimension());
      }
   }

   public int getCooldown() {
      return 12000;
   }

   public boolean canOverlapWith(WorldEvent other) {
      return true;
   }

   public boolean canStart() {
      return true;
   }

   public void updateAllLoaded(Random rand, float chance, int loadCheckRadius) {
      World world = this.worldProvider.getWorld();
      if (!world.isRemote && world.getChunkProvider() instanceof ChunkProviderServer) {
         ChunkProviderServer chunkProvider = (ChunkProviderServer)world.getChunkProvider();
         Collection<Chunk> chunks = chunkProvider.getLoadedChunks();
         if (!chunks.isEmpty()) {
            for (Chunk chunk : chunks) {
               int x16 = chunk.x * 16;
               int z16 = chunk.z * 16;

               for (int xx = 0; xx < 16; xx++) {
                  for (int zz = 0; zz < 16; zz++) {
                     if (rand.nextFloat() < chance) {
                        int x = x16 + xx;
                        int z = z16 + zz;
                        if (world.isAreaLoaded(
                           new BlockPos(x - loadCheckRadius, 0, z - loadCheckRadius), new BlockPos(x + loadCheckRadius, 255, z + loadCheckRadius)
                        )) {
                           this.updateLoadedXZ(world, x, z);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void updateLoadedXZ(World world, int posX, int posZ) {
   }

   public void writeToNbt(NBTTagCompound compound) {
      compound.setInteger("ticksExisted", this.ticksExisted);
      compound.setBoolean("isStarted", this.isStarted);
      compound.setInteger("livetime", this.livetime);
      compound.setInteger("currentCooldown", this.currentCooldown);
   }

   public void readFromNbt(NBTTagCompound compound) {
      if (compound.hasKey("ticksExisted")) {
         this.ticksExisted = compound.getInteger("ticksExisted");
      }

      if (compound.hasKey("isStarted")) {
         this.isStarted = compound.getBoolean("isStarted");
      }

      if (compound.hasKey("livetime")) {
         this.livetime = compound.getInteger("livetime");
      }

      if (compound.hasKey("currentCooldown")) {
         this.currentCooldown = compound.getInteger("currentCooldown");
      }
   }

   @SideOnly(Side.CLIENT)
   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      this.renderClouds(partialTicks, world, mc);
      if (this.isStarted && this.showness < 1.0F) {
         this.showness = (float)(this.showness + 0.0025);
      }

      if (!this.isStarted && this.showness > 0.0F) {
         this.showness = (float)(this.showness - 0.0025);
         if (this.showness <= 0.0F) {
            this.stopRenderClouds();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
   }

   @SideOnly(Side.CLIENT)
   public void stopRenderClouds() {
   }

   @SideOnly(Side.CLIENT)
   public void spawnSnowRainParticle(BlockPos pos, int allUsedPosesCount, float dist) {
   }

   @SideOnly(Side.CLIENT)
   public static Vec3d calcCelestialPosition(float celestialAngle, double distance) {
      Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
      if (entity == null) {
         return Vec3d.ZERO;
      } else {
         celestialAngle += 0.75F;
         float f2 = -MathHelper.cos(-celestialAngle * (float) (Math.PI * 2));
         float f3 = MathHelper.sin(-celestialAngle * (float) (Math.PI * 2));
         return new Vec3d(f2 + entity.posX, f3 + entity.world.getHorizon(), 0.0 + entity.posZ).scale(distance);
      }
   }

   @SideOnly(Side.CLIENT)
   public static Vec3d calcCelestialVector(float celestialAngle) {
      celestialAngle += 0.75F;
      float f2 = -MathHelper.cos(-celestialAngle * (float) (Math.PI * 2));
      float f3 = MathHelper.sin(-celestialAngle * (float) (Math.PI * 2));
      return new Vec3d(f2, f3, 0.0);
   }

   public boolean isRainingAt(Entity position) {
      if (position instanceof EntityPlayer) {
         System.out.println("isRainingAt " + position);
      }

      return this.isRainingAt(position.getPosition());
   }

   public boolean isRainingAt(BlockPos position) {
      return this.worldProvider.getWorld().getPrecipitationHeight(position).getY() <= position.getY();
   }

   public void addRainSoundEffect(Random random, int rarity, SoundEvent sound, SoundEvent soundAbove) {
      Minecraft mc = Minecraft.getMinecraft();
      Entity entity = mc.getRenderViewEntity();
      World world = mc.world;
      BlockPos blockpos = new BlockPos(entity);
      int i = 10;
      double d0 = 0.0;
      double d1 = 0.0;
      double d2 = 0.0;
      int j = 0;
      int k = 20;
      if (mc.gameSettings.particleSetting == 1) {
         k >>= 1;
      } else if (mc.gameSettings.particleSetting == 2) {
         k = 0;
      }

      if (random.nextInt(rarity) < this.rainSoundCounter++) {
         for (int l = 0; l < k; l++) {
            BlockPos blockpos1 = world.getPrecipitationHeight(
               blockpos.add(random.nextInt(10) - random.nextInt(10), 0, random.nextInt(10) - random.nextInt(10))
            );
            BlockPos blockpos2 = blockpos1.down();
            IBlockState iblockstate = world.getBlockState(blockpos2);
            if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10) {
               double d3 = random.nextDouble();
               double d4 = random.nextDouble();
               AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, blockpos2);
               if (iblockstate.getMaterial() != Material.LAVA
                  && iblockstate.getBlock() != Blocks.MAGMA
                  && iblockstate.getMaterial() != Material.AIR) {
                  if (random.nextInt(++j) == 0) {
                     d0 = blockpos2.getX() + d3;
                     d1 = blockpos2.getY() + 0.1F + axisalignedbb.maxY - 1.0;
                     d2 = blockpos2.getZ() + d4;
                  }
                  break;
               }
            }
         }

         if (j > 0) {
            this.rainSoundCounter = 0;
            if (d1 > blockpos.getY() + 1 && world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor(blockpos.getY())) {
               mc.world.playSound(d0, d1, d2, soundAbove, SoundCategory.WEATHER, 0.1F, 0.5F, false);
            } else {
               mc.world.playSound(d0, d1, d2, sound, SoundCategory.WEATHER, 0.2F, 1.0F, false);
            }
         }
      }
   }
}

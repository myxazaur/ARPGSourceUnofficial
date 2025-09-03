//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.MobSpawn;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.NexusCap;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketNexusInfoToClients;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;

public abstract class TileNexus extends TileEntity implements ITickable {
   public int invasionTime = 0;
   public boolean invasionStarted = false;
   public AxisAlignedBB[] MOB_SPAWN_ZONES;
   public double ZONEradius = 64.0;
   public double ZONEheight = 16.0;
   public double ZONEhollow = 48.0;
   public int maxLandingYdecrease = 24;
   public int maxCalmTime = 320;
   public Random rand = new Random();
   public int MAX_HEALTH = 100;
   public int FINAL_WAVE = 10;
   public int MOBS_SPAWNED = 0;
   public int WAVE = 0;
   public float waveCompleteRate = 0.0F;
   public int calmTime = 0;
   public double spawnRate = 0.05F;
   public int calmTimeBonus = 0;
   public int groupsSpawnedThisWave = 0;
   public float nexusHealth = 0.0F;
   public short nexusId = 0;
   public boolean hasDespawnFix = false;

   public void startInvasion(String team) {
      this.WAVE = 1;
      this.waveCompleteRate = 0.0F;
      this.groupsSpawnedThisWave = 0;
      this.calmTime = 0;
      this.calmTimeBonus = 0;
      this.spawnRate = this.getCooledSpawnRate(true);
      this.MOBS_SPAWNED = 0;
      if (this.MOB_SPAWN_ZONES == null) {
         this.initSpawnZones();
      }

      int startedInworld = 0;

      for (TileEntity tilee : this.world.tickableTileEntities) {
         if (tilee instanceof TileNexus && ((TileNexus)tilee).invasionStarted) {
            startedInworld++;
         }
      }

      if (startedInworld < getMaxInvasionsInWorld()) {
         this.invasionStarted = true;
         if (!this.world.isRemote) {
            AxisAlignedBB aabb = new AxisAlignedBB(this.getPos().add(-3, -3, -3), this.getPos().add(3, 3, 3));
            List<NexusCap> list = this.world.getEntitiesWithinAABB(NexusCap.class, aabb);
            if (!list.isEmpty()) {
               for (NexusCap cap1 : list) {
                  cap1.setDead();
               }
            }

            this.spawnNexusCap(team);
         }

         this.nexusHealth = this.MAX_HEALTH;
         this.sendPacket();
      }
   }

   public void spawnNexusCap(String team) {
      NexusCap cap = new NexusCap(this.getWorld(), this, this.MAX_HEALTH);
      cap.setPosition(
         this.pos.getX() + 0.5,
         this.pos.getY() + 0.5 - cap.height / 2.0,
         this.pos.getZ() + 0.5
      );
      this.world.spawnEntity(cap);
      cap.onInitialSpawn();
      cap.team = team;
      cap.nexusPos = this.getPos();
   }

   public void onInvaderKilled(AbstractMob entity, DamageSource source) {
      float seriousness = this.getEntitySeriousness(entity);
      this.calmTimeBonus = MathHelper.clamp(this.calmTimeBonus + (int)(seriousness * 5.0F), 0, this.maxCalmTime * 2);
      this.waveCompleteRate += seriousness;
      if (seriousness != 0.0F) {
         this.sendPacket();
      }
   }

   public void onInvaderDespawn(AbstractMob entity) {
   }

   public void update() {
      if (this.invasionStarted) {
         this.invasionTime++;
         if (this.waveCompleteRate >= 100.0F) {
            this.calmTime = this.maxCalmTime + this.calmTimeBonus;
            this.calmTimeBonus = 0;
            this.startNextWave();
         }

         if (!this.world.isRemote) {
            boolean sended = false;
            if (this.calmTime > 0) {
               this.calmTime--;
            } else if (this.invasionTime % 5 == 0) {
               if (this.groupsSpawnedThisWave >= this.getMaxGroupsCountOnWave(this.WAVE)) {
                  this.waveCompleteRate = this.waveCompleteRate + this.getPassiveCompleting();
                  this.sendPacket();
                  sended = true;
               } else if (this.rand.nextFloat() < this.spawnRate) {
                  this.spawnRate = this.getCooledSpawnRate(false);
                  Vec3d randpos = this.getRandomPosInZone();
                  this.landMobGroup(randpos.x, randpos.y, randpos.z);
                  this.waveCompleteRate = this.waveCompleteRate + this.getPassiveSpawnCompleting();
                  this.sendPacket();
                  sended = true;
               } else {
                  this.spawnRate *= 2.0;
               }
            }

            if (this.invasionTime % 51 == 0) {
               if (!sended) {
                  this.sendPacket();
               }

               if (this.hasDespawnFix && this.groupsSpawnedThisWave >= this.getMaxGroupsCountOnWave(this.WAVE)) {
                  float summSerious = 0.0F;

                  for (AbstractMob mob : this.getNexusMobsAround()) {
                     summSerious += this.getEntitySeriousness(mob);
                  }

                  if (summSerious < 120.0F - this.waveCompleteRate) {
                     this.groupsSpawnedThisWave--;
                  }
               }
            }

            if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
               this.onInvasionEnd(false);
            }
         }
      }
   }

   public static int getMaxInvasionsInWorld() {
      return 3;
   }

   public void startNextWave() {
      this.WAVE++;
      this.groupsSpawnedThisWave = 0;
      this.spawnRate = this.getCooledSpawnRate(true);
      if (this.WAVE > this.FINAL_WAVE) {
         this.WAVE = this.FINAL_WAVE;
         this.onInvasionEnd(true);
         this.waveCompleteRate = 100.0F;
      } else {
         this.waveCompleteRate = 0.0F;
         this.sendPacket();
      }
   }

   public void onInvasionEnd(boolean success) {
      this.invasionStarted = false;
      AxisAlignedBB aabb = new AxisAlignedBB(this.getPos().add(-3, -3, -3), this.getPos().add(3, 3, 3));
      List<NexusCap> list = this.world.getEntitiesWithinAABB(NexusCap.class, aabb);
      if (!list.isEmpty()) {
         for (NexusCap cap : list) {
            cap.winTime = 1;
         }
      }

      if (success) {
         this.sendWinPacket();
         if (!this.world.isRemote) {
            for (Entity enitty : GetMOP.getEntitiesInAABBof(this.world, this.getPos(), 64.0, null)) {
               if (enitty instanceof EntityPlayer) {
                  MobSpawn.chillAfterBoss((EntityPlayer)enitty);
               }
            }
         }
      } else {
         this.sendLosePacket();
      }
   }

   public double getCooledSpawnRate(boolean forWaveStart) {
      return forWaveStart ? 5.0E-5F : 0.005F;
   }

   public float getPassiveCompleting() {
      return 0.25F;
   }

   public float getPassiveSpawnCompleting() {
      return 1.0F;
   }

   public int getMaxGroupsCountOnWave(int wave) {
      return 5 + wave;
   }

   public void initSpawnZoneWithDisplacement(EnumFacing direction) {
      double posX = this.getPos().getX() + 0.5;
      double posY = this.getPos().getY() + 0.5;
      double posZ = this.getPos().getZ() + 0.5;
   }

   public void initSpawnZones() {
      double posX = this.getPos().getX() + 0.5;
      double posY = this.getPos().getY() + 0.5;
      double posZ = this.getPos().getZ() + 0.5;
      this.MOB_SPAWN_ZONES = new AxisAlignedBB[]{null, null, null, null};
      this.MOB_SPAWN_ZONES[0] = new AxisAlignedBB(
         posX + this.ZONEhollow, posY - this.ZONEheight, posZ - this.ZONEhollow, posX + this.ZONEradius, posY + this.ZONEheight, posZ + this.ZONEradius
      );
      this.MOB_SPAWN_ZONES[1] = new AxisAlignedBB(
         posX - this.ZONEradius, posY - this.ZONEheight, posZ + this.ZONEhollow, posX + this.ZONEhollow, posY + this.ZONEheight, posZ + this.ZONEradius
      );
      this.MOB_SPAWN_ZONES[2] = new AxisAlignedBB(
         posX - this.ZONEradius, posY - this.ZONEheight, posZ - this.ZONEradius, posX - this.ZONEhollow, posY + this.ZONEheight, posZ + this.ZONEhollow
      );
      this.MOB_SPAWN_ZONES[3] = new AxisAlignedBB(
         posX - this.ZONEhollow, posY - this.ZONEheight, posZ - this.ZONEradius, posX + this.ZONEradius, posY + this.ZONEheight, posZ - this.ZONEhollow
      );
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("invasionTime", this.invasionTime);
      compound.setBoolean("invasionStarted", this.invasionStarted);
      compound.setInteger("maxCalm", this.maxCalmTime);
      compound.setInteger("finalWave", this.FINAL_WAVE);
      compound.setInteger("wave", this.WAVE);
      compound.setInteger("mobsSpawned", this.MOBS_SPAWNED);
      compound.setFloat("waveCompleteRate", this.waveCompleteRate);
      compound.setInteger("calmTime", this.calmTime);
      compound.setDouble("spawnRate", this.spawnRate);
      compound.setInteger("calmTimeBonus", this.calmTimeBonus);
      compound.setInteger("groupsOnThisWave", this.groupsSpawnedThisWave);
      if (this.MOB_SPAWN_ZONES != null) {
         NBTTagList list = new NBTTagList();

         for (AxisAlignedBB aabb : this.MOB_SPAWN_ZONES) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("maxX", aabb.maxX);
            tag.setDouble("maxY", aabb.maxY);
            tag.setDouble("maxZ", aabb.maxZ);
            tag.setDouble("minX", aabb.minX);
            tag.setDouble("minY", aabb.minY);
            tag.setDouble("minZ", aabb.minZ);
            list.appendTag(tag);
         }

         compound.setTag("spawnzones", list);
         compound.setInteger("zonescount", this.MOB_SPAWN_ZONES.length);
      }

      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("invasionTime")) {
         this.invasionTime = compound.getInteger("invasionTime");
      }

      if (compound.hasKey("invasionStarted")) {
         this.invasionStarted = compound.getBoolean("invasionStarted");
      }

      if (compound.hasKey("maxCalm")) {
         this.maxCalmTime = compound.getInteger("maxCalm");
      }

      if (compound.hasKey("finalWave")) {
         this.FINAL_WAVE = compound.getInteger("finalWave");
      }

      if (compound.hasKey("wave")) {
         this.WAVE = compound.getInteger("wave");
      }

      if (compound.hasKey("mobsSpawned")) {
         this.MOBS_SPAWNED = compound.getInteger("mobsSpawned");
      }

      if (compound.hasKey("waveCompleteRate")) {
         this.waveCompleteRate = compound.getFloat("waveCompleteRate");
      }

      if (compound.hasKey("calmTime")) {
         this.calmTime = compound.getInteger("calmTime");
      }

      if (compound.hasKey("spawnRate")) {
         this.spawnRate = compound.getDouble("spawnRate");
      }

      if (compound.hasKey("calmTimeBonus")) {
         this.calmTimeBonus = compound.getInteger("calmTimeBonus");
      }

      if (compound.hasKey("groupsOnThisWave")) {
         this.groupsSpawnedThisWave = compound.getInteger("groupsOnThisWave");
      }

      if (compound.hasKey("spawnzones") && compound.hasKey("zonescount")) {
         int zonescount = compound.getInteger("zonescount");
         this.MOB_SPAWN_ZONES = new AxisAlignedBB[zonescount];
         NBTTagList list = compound.getTagList("spawnzones", 10);
         if (!list.isEmpty()) {
            int c = 0;

            for (NBTBase nbt : list) {
               if (nbt instanceof NBTTagCompound) {
                  NBTTagCompound tagc = (NBTTagCompound)nbt;
                  if (tagc.hasKey("maxX")
                     && tagc.hasKey("maxY")
                     && tagc.hasKey("maxZ")
                     && tagc.hasKey("minX")
                     && tagc.hasKey("minY")
                     && tagc.hasKey("minZ")) {
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        tagc.getDouble("minX"),
                        tagc.getDouble("minY"),
                        tagc.getDouble("minZ"),
                        tagc.getDouble("maxX"),
                        tagc.getDouble("maxY"),
                        tagc.getDouble("maxZ")
                     );
                     this.MOB_SPAWN_ZONES[c] = aabb;
                     c++;
                  }
               }
            }
         }
      }

      super.readFromNBT(compound);
   }

   @Nullable
   public Vec3d getRandomPosInZone() {
      if (this.MOB_SPAWN_ZONES != null) {
         AxisAlignedBB aabb = this.MOB_SPAWN_ZONES[this.rand.nextInt(this.MOB_SPAWN_ZONES.length)];
         if (aabb.minX < aabb.maxX) {
            double x = aabb.minX + this.rand.nextDouble() * (aabb.maxX - aabb.minX);
            if (aabb.minZ < aabb.maxZ) {
               double z = aabb.minZ + this.rand.nextDouble() * (aabb.maxZ - aabb.minZ);
               if (aabb.minY < aabb.maxY) {
                  double clYmax = Math.min(aabb.maxY, 255.0);
                  double clYmin = Math.max(aabb.minY, 0.0);
                  double y = clYmin + this.rand.nextDouble() * (clYmax - clYmin);
                  return new Vec3d(x, y, z);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public void landMobGroup(double x, double y, double z) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = this.getPos().getY() - this.maxLandingYdecrease;

      while (y > minn) {
         if (this.world
               .getBlockState(blockpos$pooledmutableblockpos.setPos(x, y, z))
               .isSideSolid(this.world, blockpos$pooledmutableblockpos, EnumFacing.UP)
            && this.world.isAirBlock(blockpos$pooledmutableblockpos.setPos(x, y + 1.0, z))) {
            blockpos$pooledmutableblockpos.release();
            this.spawnMobGroup(x, y + 1.0, z);
            return;
         }

         y--;
      }

      blockpos$pooledmutableblockpos.release();
   }

   public abstract void spawnMobGroup(double var1, double var3, double var5);

   public boolean onKillNexusCap(NexusCap cap, DamageSource source) {
      this.onInvasionEnd(false);
      return true;
   }

   public void onAttackNexus(NexusCap cap, DamageSource source, float amount) {
      this.nexusHealth = cap.getHealth();
      this.sendPacket();
   }

   public void onHealNexus(NexusCap cap, float amount) {
      this.nexusHealth = cap.getHealth();
      this.sendPacket();
   }

   public float getEntitySeriousness(AbstractMob mob) {
      return 1.0F;
   }

   public void sendPacket() {
      if (!this.world.isRemote) {
         if (this.nexusHealth == 0.0F && this.invasionStarted) {
            AxisAlignedBB aabb = new AxisAlignedBB(this.getPos().add(-1, -1, -1), this.getPos().add(1, 1, 1));
            List<NexusCap> list = this.world.getEntitiesWithinAABB(NexusCap.class, aabb);
            NexusCap nexuscap = null;
            if (!list.isEmpty()) {
               for (NexusCap cap : list) {
                  if (nexuscap == null) {
                     nexuscap = cap;
                  } else {
                     cap.setDead();
                  }
               }
            }

            if (nexuscap != null) {
               this.nexusHealth = nexuscap.getHealth();
            }
         }

         PacketNexusInfoToClients packet = new PacketNexusInfoToClients();
         if (this.invasionStarted) {
            packet.writeargs(
               MathHelper.clamp(this.nexusHealth / this.MAX_HEALTH, 0.0F, 1.0F),
               MathHelper.clamp(this.waveCompleteRate / 100.0F, 0.0F, 1.0F),
               (short)this.WAVE,
               this.nexusId,
               this.WAVE == this.FINAL_WAVE
            );
         } else {
            packet.writeargs(0.0F, 0.0F, (short)0, this.nexusId, this.WAVE == this.FINAL_WAVE);
         }

         PacketHandler.sendToAllAround(
            packet,
            this.world,
            this.getPos().getX(),
            this.getPos().getY(),
            this.getPos().getZ(),
            this.ZONEradius
         );
      }
   }

   public void sendWinPacket() {
      if (!this.world.isRemote) {
         PacketNexusInfoToClients packet = new PacketNexusInfoToClients();
         packet.writeargs(MathHelper.clamp(this.nexusHealth / this.MAX_HEALTH, 0.0F, 1.0F), -100.0F, (short)this.WAVE, this.nexusId, true);
         PacketHandler.sendToAllAround(
            packet,
            this.world,
            this.getPos().getX(),
            this.getPos().getY(),
            this.getPos().getZ(),
            this.ZONEradius
         );
      }
   }

   public void sendLosePacket() {
      if (!this.world.isRemote) {
         PacketNexusInfoToClients packet = new PacketNexusInfoToClients();
         packet.writeargs(0.0F, -200.0F, (short)this.WAVE, this.nexusId, this.WAVE == this.FINAL_WAVE);
         PacketHandler.sendToAllAround(
            packet,
            this.world,
            this.getPos().getX(),
            this.getPos().getY(),
            this.getPos().getZ(),
            this.ZONEradius
         );
      }
   }

   public void sendNexusMessageToAllAround(String text) {
      if (!this.world.isRemote) {
         for (EntityPlayerMP playerIn : this.world
            .getEntitiesWithinAABB(
               EntityPlayerMP.class,
               new AxisAlignedBB(
                  this.pos.getX() + this.ZONEradius,
                  this.pos.getY() + this.ZONEheight,
                  this.pos.getZ() + this.ZONEradius,
                  this.pos.getX() - this.ZONEradius,
                  this.pos.getY() - this.ZONEheight,
                  this.pos.getZ() - this.ZONEradius
               )
            )) {
            playerIn.sendMessage(new TextComponentString(text));
         }
      }
   }

   public List<AbstractMob> getNexusMobsAround() {
      return this.world
         .getEntitiesWithinAABB(AbstractMob.class, new AxisAlignedBB(this.getPos()).grow(this.ZONEradius + 8.0), mob -> mob.nexus != null);
   }
}

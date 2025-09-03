package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnNether extends MobSpawn {
   public MobSpawnNether() {
      super(-1);
      this.setSwarmFrequency(7200);
      this.addReactor(new MobReactor(32, 11, OtherMobsPack.LavaSprinkler.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN, 32, 48, 20, true));
      this.addReactor(new MobReactor(25, 5, EntityWitherSkeleton.class, 1, 3, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(20, 4, EntityBlaze.class, 1, 3, MobReactor.GroupSpawnMode.LAND, 32, 48, 16, false));
      this.addReactor(new MobReactor(10, 7, EntityPigZombie.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(15, 12, OtherMobsPack.CinderCrawler.class, 1, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 16, false));
      this.addReactor(new MobReactor(12, 14, OtherMobsPack.Varmint.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(25, 10, OtherMobsPack.Hellhound.class, 1, 2, MobReactor.GroupSpawnMode.LAND, 32, 52, 14, false));
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.RED + Ln.translate("swarm_nether_alert");
   }

   public MobSpawnNether(boolean survivorMode) {
      super(-1);
      this.inSurvivorMode = survivorMode;
      this.addReactor(new MobReactor(25, 5, EntityWitherSkeleton.class, 1, 3, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(10, 7, EntityPigZombie.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(12, 14, OtherMobsPack.Varmint.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 14, false));
      this.addReactor(new MobReactor(15, 12, OtherMobsPack.CinderCrawler.class, 1, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 16, false).setSurvivorLogic(1));
      this.addReactor(new MobReactor(32, 11, OtherMobsPack.LavaSprinkler.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN, 32, 48, 20, true).setSurvivorLogic(1));
      this.addReactor(new MobReactor(20, 4, EntityBlaze.class, 1, 3, MobReactor.GroupSpawnMode.LAND, 32, 48, 16, false).setSurvivorLogic(1));
      this.addReactor(new MobReactor(25, 10, OtherMobsPack.Hellhound.class, 1, 2, MobReactor.GroupSpawnMode.LAND, 32, 52, 14, false).setSurvivorLogic(1));
      this.addReactor(new MobReactor(14, 4, SmokeDemon.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN, 32, 48, 20, true).setSurvivorLogic(2));
      this.addReactor(new MobReactor(5, 4, EntityMagmaCube.class, 2, 3, MobReactor.GroupSpawnMode.LAND, 32, 52, 14, false).setSurvivorLogic(2));
      this.addReactor(new MobReactor(45, 6, EntityGhast.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 40, 64, 20, true).setSurvivorLogic(3));
      this.addReactor(new MobReactor(250, 8, EntityWither.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 48, 64, 18, false).setSurvivorLogic(4));
   }
}

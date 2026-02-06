package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnDungeon extends MobSpawn {
   public MobSpawnDungeon() {
      super(102);
      this.setSwarmFrequency(10000);
      this.addReactor(new MobReactor(10, 20, DungeonMobsPack.Deg.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(34, 10, DungeonMobsPack.Devourer.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(9, 15, DungeonMobsPack.Weaver.class, 4, 8, MobReactor.GroupSpawnMode.LAND, 28, 32, 20, true));
      this.addReactor(new MobReactor(18, 11, DungeonMobsPack.Beholder.class, 1, 2, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(3, 8, DungeonMobsPack.UnderworldSymbiote.class, 2, 5, MobReactor.GroupSpawnMode.AIRBORN, 32, 64, 20, false));
      this.addReactor(new MobReactor(24, 10, DungeonMobsPack.LarvaFlyer.class, 1, 2, MobReactor.GroupSpawnMode.LAND, 30, 40, 20, false));
      this.addReactor(new MobReactor(26, 7, DungeonMobsPack.ShadowMage.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(25, 7, DungeonMobsPack.DoleriteEater.class, 1, 1, MobReactor.GroupSpawnMode.UNDERGROUND, 28, 56, 28, true));
      this.addReactor(new MobReactor(14, 12, DungeonMobsPack.StoneEater.class, 1, 2, MobReactor.GroupSpawnMode.UNDERGROUND, 28, 56, 28, true));
      this.addMiniBoss(new MobReactor(200, 1, DungeonMobsPack.UnderworldDigger.class, 1, 1, MobReactor.GroupSpawnMode.UNDERGROUND, 28, 56, 28, true));
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.GOLD + Ln.translate("swarm_dungeon_alert");
   }

   @Override
   public int getPointsPerPlayerPerSecond(EntityPlayer player, int difficulty) {
      float inCaves = GetMOP.isInCaves(player.world, player.getPosition());
      if (inCaves > 0.61F) {
         return 0;
      } else {
         return inCaves > 0.44F ? 1 : 2;
      }
   }

   @Override
   public int getPointsPerPlayerOnSwarm(EntityPlayer player, int difficulty) {
      int value = super.getPointsPerPlayerOnSwarm(player, difficulty);
      float inCaves = GetMOP.isInCaves(player.world, player.getPosition());
      return inCaves > 0.61F ? value / 2 : value;
   }
}

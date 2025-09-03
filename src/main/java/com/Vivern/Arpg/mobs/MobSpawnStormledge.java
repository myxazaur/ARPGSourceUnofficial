package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnStormledge extends MobSpawn {
   public MobSpawnStormledge() {
      super(104);
      this.setSwarmFrequency(7600);
      this.addReactor(new MobReactor(13, 15, StormledgeMobsPack.Skyguard.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(17, 13, StormledgeMobsPack.Screenguard.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(34, 8, StormledgeMobsPack.CloudEater.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(9, 7, StormledgeMobsPack.Cloudbug.class, 1, 6, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(4, 4, StormledgeMobsPack.Homingbird.class, 6, 11, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(19, 11, StormledgeMobsPack.Gust.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(29, 7, StormledgeMobsPack.Windbreak.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 32, 56, 10, false));
      this.addReactor(new MobReactor(21, 10, StormledgeMobsPack.Zarpion.class, 1, 2, MobReactor.GroupSpawnMode.LAND, 32, 56, 25, false));
      this.addMiniBoss(new MobReactor(200, 1, StormledgeMobsPack.Thunderbird.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 48, 64, 25, true));
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.AQUA + Ln.translate("swarm_stormledge_alert");
   }
}

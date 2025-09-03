package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnEverfrost extends MobSpawn {
   public MobSpawnEverfrost() {
      super(100);
      this.setSwarmFrequency(7600);
      this.addReactor(
         new MobReactor(6, 15, EverfrostMobsPack.Snowrover.class, 3, 8, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false).setHeightLogic(35, 255, false)
      );
      this.addReactor(new MobReactor(35, 9, EverfrostMobsPack.HarridanOfIce.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(12, 9, EverfrostMobsPack.Frost.class, 2, 4, MobReactor.GroupSpawnMode.AIRBORN, 42, 56, 32, true));
      this.addReactor(new MobReactor(16, 7, EverfrostMobsPack.Gelum.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 42, 56, 32, true));
      this.addReactor(new MobReactor(2, 7, EverfrostMobsPack.IceCube.class, 3, 15, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(
         new MobReactor(48, 6, EverfrostMobsPack.IceApparition.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false).setHeightLogic(35, 255, false)
      );
      this.addReactor(
         new MobReactor(8, 8, EverfrostMobsPack.Fentral.class, 4, 7, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false).setHeightLogic(58, 255, false)
      );
      this.addReactor(
         new MobReactor(2, 10, EverfrostMobsPack.IceCube.class, 4, 8, MobReactor.GroupSpawnMode.LAND, 22, 48, 20, false).setHeightLogic(10, 52, true)
      );
      this.addReactor(
         new MobReactor(12, 50, EverfrostMobsPack.HailWraith.class, 2, 3, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false)
            .setHeightLogic(50, 255, true)
            .setWorldEventsLogic(MobReactor.WorldEventsSpawnLogic.ONLY_IN_ASSIGNED, 1)
      );
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.WHITE + Ln.translate("swarm_everfrost_alert");
   }
}

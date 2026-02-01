package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.MobReactor;
import com.vivern.arpg.main.MobSpawn;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnAquatica extends MobSpawn {
   public MobSpawnAquatica() {
      super(103);
      this.setSwarmFrequency(10000);
      this.addReactor(new MobReactor(10, 14, AquaticaMobsPack.Needletooth.class, 3, 4, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false));
      this.addReactor(new MobReactor(40, 7, AquaticaMobsPack.Archelon.class, 1, 1, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false));
      this.addReactor(new MobReactor(16, 12, AquaticaMobsPack.Hydromona.class, 1, 3, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, true));
      this.addReactor(new MobReactor(20, 10, AquaticaMobsPack.Trachymona.class, 1, 1, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false));
      this.addReactor(new MobReactor(3, 7, AquaticaMobsPack.Breed.class, 4, 6, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false));
      this.addReactor(new MobReactor(18, 12, AquaticaMobsPack.Blisterfish.class, 1, 2, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false));
      this.addReactor(
         new MobReactor(24, 7, AquaticaMobsPack.Dartfish.class, 1, 1, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, false).setHeightLogic(0, 160, false)
      );
      this.addReactor(new MobReactor(60, 5, AquaticaMobsPack.OceanSpirit.class, 1, 1, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, true));
      this.addReactor(new MobReactor(45, 7, AquaticaMobsPack.Wizardfish.class, 1, 1, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, true));
      this.addReactor(new MobReactor(18, 14, AquaticaMobsPack.Polipoid.class, 1, 3, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, true));
      this.addReactor(
         new MobReactor(50, 1, AquaticaMobsPack.Mermaid.class, 1, 3, MobReactor.GroupSpawnMode.WATER, 32, 48, 20, true).setHeightLogic(0, 150, false)
      );
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.DARK_AQUA + Ln.translate("swarm_aquatica_alert");
   }
}

package com.vivern.arpg.mobs;

import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.MobReactor;
import com.vivern.arpg.main.MobSpawn;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnToxicomania extends MobSpawn {
   public MobSpawnToxicomania() {
      super(101);
      this.setSwarmFrequency(7600);
      this.addReactor(new MobReactor(6, 26, ToxicomaniaMobsPack.MutantZombie.class, 3, 6, MobReactor.GroupSpawnMode.LAND, 32, 52, 20, false));
      this.addReactor(new MobReactor(30, 15, ToxicomaniaMobsPack.MutantBeast.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 52, 20, false));
      this.addReactor(new MobReactor(16, 10, ToxicomaniaMobsPack.TestTubeCreature.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(4, 8, ToxicomaniaMobsPack.Experiment9.class, 2, 7, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(new MobReactor(10, 14, ToxicomaniaMobsPack.GlowingSkeleton.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 48, 20, false));
      this.addReactor(
         new MobReactor(60, 8, ToxicomaniaMobsPack.RocketBot.class, 1, 1, MobReactor.GroupSpawnMode.LAND, 32, 56, 28, false).setHeightLogic(35, 255, false)
      );
      this.addReactor(
         new MobReactor(9, 10, ToxicomaniaMobsPack.Dron.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 48, 60, 32, true).setHeightLogic(58, 255, false)
      );
      this.addReactor(
         new MobReactor(10, 8, ToxicomaniaMobsPack.Springer.class, 5, 13, MobReactor.GroupSpawnMode.LAND, 32, 64, 20, false).setHeightLogic(58, 255, false)
      );
      this.addReactor(
         new MobReactor(6, 16, ToxicomaniaMobsPack.Experiment9.class, 2, 4, MobReactor.GroupSpawnMode.LAND, 22, 48, 20, false).setHeightLogic(10, 48, true)
      );
      this.addReactor(
         new MobReactor(15, 40, ToxicomaniaMobsPack.VineChops.class, 2, 3, MobReactor.GroupSpawnMode.LAND, 22, 48, 20, false)
            .setBiomeLogic(biome -> biome == BiomesRegister.TOXIC_JUNGLE)
      );
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.DARK_GREEN + Ln.translate("swarm_toxicomania_alert");
   }
}

package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.MobReactor;
import com.vivern.arpg.main.MobSpawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class MobSpawnEnder extends MobSpawn {
   public MobSpawnEnder() {
      super(1);
      this.setSwarmFrequency(7200);
      this.addReactor(new MobReactor(60, 1, OtherMobsPack.EnderSeer.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN, 50, 70, 20, true).setEnderLogic());
      this.addReactor(new MobReactor(6, 16, Blubber.class, 2, 5, MobReactor.GroupSpawnMode.LAND, 32, 56, 25, false).setEnderLogic());
   }

   @Override
   public String getChatMessage() {
      return "" + TextFormatting.ITALIC + TextFormatting.DARK_PURPLE + Ln.translate("swarm_ender_alert");
   }

   @Override
   public int getPointsPerPlayerOnSwarm(EntityPlayer player, int difficulty) {
      return 90 + 30 * difficulty;
   }

   @Override
   public int getPointsPerPlayerPerSecond(EntityPlayer player, int difficulty) {
      return 0;
   }
}

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MobSpawnOverworld extends MobSpawn {
   public MobSpawnOverworld() {
      super(0);
      this.addReactor(
         new MobReactor(10, 5, EntityRegistry.getEntry(WhiteSlime.class), 2, 6, MobReactor.GroupSpawnMode.LAND, 18, 48, 12, false)
            .setHeightLogic(0, 50, false)
            .setLightLogic(MobReactor.GroupSpawnLight.DARK)
      );
      this.addReactor(
         new MobReactor(20, 3, EntityRegistry.getEntry(Troglodyte.class), 2, 4, MobReactor.GroupSpawnMode.LAND, 22, 48, 12, true)
            .setHeightLogic(11, 50, false)
            .setLightLogic(MobReactor.GroupSpawnLight.DARK)
      );
   }

   public MobSpawnOverworld(boolean survivorMode) {
      super(0);
      this.inSurvivorMode = survivorMode;
      this.addReactor(new MobReactor(10, 5, EntityRegistry.getEntry(WhiteSlime.class), 2, 6, MobReactor.GroupSpawnMode.LAND, 18, 48, 12, false));
      this.addReactor(new MobReactor(15, 3, EntityRegistry.getEntry(Moonshroom.class), 1, 3, MobReactor.GroupSpawnMode.AIRBORN, 24, 48, 16, true));
      this.addReactor(
         new MobReactor(25, 4, EntityRegistry.getEntry(Gnater.class), 2, 4, MobReactor.GroupSpawnMode.AIRBORN, 24, 48, 16, true).setSurvivorLogic(1)
      );
      this.addReactor(
         new MobReactor(20, 3, EntityRegistry.getEntry(Troglodyte.class), 2, 4, MobReactor.GroupSpawnMode.LAND, 22, 48, 12, true).setSurvivorLogic(1)
      );
      this.addReactor(new MobReactor(8, 5, EntityRegistry.getEntry(EntityZombie.class), 4, 8, MobReactor.GroupSpawnMode.LAND, 20, 64, 12, false));
      this.addReactor(new MobReactor(10, 4, EntityRegistry.getEntry(EntitySpider.class), 1, 4, MobReactor.GroupSpawnMode.LAND, 20, 64, 12, false));
      this.addReactor(
         new MobReactor(20, 2, EntityRegistry.getEntry(EntityCreeper.class), 1, 3, MobReactor.GroupSpawnMode.LAND, 20, 64, 12, false).setSurvivorLogic(1)
      );
      this.addReactor(
         new MobReactor(15, 3, EntityRegistry.getEntry(EntitySkeleton.class), 2, 4, MobReactor.GroupSpawnMode.LAND, 20, 64, 12, false).setSurvivorLogic(1)
      );
      this.addReactor(
         new MobReactor(10, 4, EntityRegistry.getEntry(EntityCaveSpider.class), 3, 7, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false).setSurvivorLogic(2)
      );
      this.addReactor(
         new MobReactor(30, 5, EntityRegistry.getEntry(EntityWitch.class), 1, 2, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false).setSurvivorLogic(2)
      );
      this.addReactor(
         new MobReactor(17, 5, EntityRegistry.getEntry(EntityStray.class), 1, 4, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false).setSurvivorLogic(2)
      );
      this.addReactor(
         new MobReactor(14, 10, EntityRegistry.getEntry(EntityVindicator.class), 2, 5, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false).setSurvivorLogic(3)
      );
      this.addReactor(
         new MobReactor(30, 9, EntityRegistry.getEntry(EntityEvoker.class), 1, 2, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false).setSurvivorLogic(3)
      );
      this.addReactor(
         new MobReactor(16, 10, EntityRegistry.getEntry(OtherMobsPack.HorribleVindicator.class), 1, 3, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false)
            .setSurvivorLogic(3)
      );
      this.addReactor(
         new MobReactor(30, 8, EntityRegistry.getEntry(OtherMobsPack.HorribleEvoker.class), 1, 2, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false)
            .setSurvivorLogic(3)
      );
      this.addReactor(
         new MobReactor(35, 8, EntityRegistry.getEntry(OtherMobsPack.Equestrian.class), 1, 2, MobReactor.GroupSpawnMode.LAND, 20, 48, 12, false)
            .setSurvivorLogic(3)
      );
      this.addReactor(
         new MobReactor(40, 8, EntityRegistry.getEntry(EntityIllusionIllager.class), 1, 1, MobReactor.GroupSpawnMode.LAND, 30, 48, 12, false)
            .setSurvivorLogic(4)
      );
      this.addReactor(
         new MobReactor(50, 9, EntityRegistry.getEntry(EntitySlime.class), 1, 1, MobReactor.GroupSpawnMode.LAND, 40, 64, 12, false).setSurvivorLogic(4)
      );
   }
}

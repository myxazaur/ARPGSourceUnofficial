package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.tileentity.SpawnerTuner;

public class SpawnerTuners {
   public static SpawnerTuner MOUND = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.Atorpid.class, 4, 2, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.IceCube.class, 1, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(2);
   public static SpawnerTuner ICECASTLE = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.IceWarrior.class, 6, 3, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Fentral.class, 3, 2, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Frost.class, 1, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Gelum.class, 1, 6, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.IceApparition.class, 1, 15, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Gargoyle.class, 2, 4, MobReactor.GroupSpawnMode.AIRBORN)
      .setNearCheckDistance(16)
      .setMaximumMobTypes(3);
   public static SpawnerTuner ICECASTLEPARAPET = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.Frost.class, 1, 4, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(EverfrostMobsPack.Gelum.class, 1, 6, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(EverfrostMobsPack.Gargoyle.class, 5, 2, MobReactor.GroupSpawnMode.AIRBORN)
      .setMaximumMobTypes(3);
   public static SpawnerTuner NIVEOUSHALL = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.IceWarrior.class, 1, 3, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Snowrover.class, 4, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(2)
      .setMobsCountPerSpawn(3.2F);
   public static SpawnerTuner EVERFROST_STRUCTURES = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.IceCube.class, 1, 1, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Fentral.class, 1, 2, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Frost.class, 1, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(EverfrostMobsPack.Atorpid.class, 2, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(2);
   public static SpawnerTuner EVERFROST_GRAVE = new SpawnerTuner()
      .addCreature(EverfrostMobsPack.IceApparition.class, 1, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(1)
      .setMobsCountPerSpawn(1.0F, 1.0F);
   public static SpawnerTuner TOXICOMANIA_STRUCTURES = new SpawnerTuner()
      .addCreature(ToxicomaniaMobsPack.MutantZombie.class, 5, 1, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.GlowingSkeleton.class, 2, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Experiment9.class, 2, 3, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Dron.class, 1, 4, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(ToxicomaniaMobsPack.PoisonSpitter.class, 1, 5, MobReactor.GroupSpawnMode.LAND)
      .setMobsCountPerSpawn(3.0F)
      .setMaximumMobTypes(3);
   public static SpawnerTuner LABORATORY = new SpawnerTuner()
      .addCreature(ToxicomaniaMobsPack.MutantZombie.class, 5, 2, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.GlowingSkeleton.class, 1, 8, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Experiment9.class, 5, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.TestTubeCreature.class, 2, 9, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.TestTubeSubstance.class, 1, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Springer.class, 1, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(3);
   public static SpawnerTuner BUNKER = new SpawnerTuner()
      .addCreature(ToxicomaniaMobsPack.KillBot.class, 1, 5, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.RocketBot.class, 1, 14, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Dron.class, 1, 2, MobReactor.GroupSpawnMode.LAND)
      .addCreature(ToxicomaniaMobsPack.Turret.class, 1, 6, MobReactor.GroupSpawnMode.LAND)
      .setMobsCountPerSpawn(2.0F)
      .setInitialMaxMobsNearby(6)
      .setMaximumMobTypes(2);
   public static SpawnerTuner DUNGEON_STRUCTURES = new SpawnerTuner()
      .addCreature(DungeonMobsPack.Larva.class, 5, 1, MobReactor.GroupSpawnMode.LAND)
      .addCreature(DungeonMobsPack.Larva.class, 3, 1, MobReactor.GroupSpawnMode.LAND)
      .addCreature(DungeonMobsPack.LarvaFlyer.class, 2, 12, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(DungeonMobsPack.Deg.class, 3, 4, MobReactor.GroupSpawnMode.LAND)
      .addCreature(DungeonMobsPack.UnderworldSymbiote.class, 4, 1, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(DungeonMobsPack.Weaver.class, 3, 3, MobReactor.GroupSpawnMode.LAND)
      .addCreature(DungeonMobsPack.Beholder.class, 1, 18, MobReactor.GroupSpawnMode.LAND)
      .setMobsCountPerSpawn(4.0F)
      .setMaximumMobTypes(4);
   public static SpawnerTuner JELLYFISHCAVE = new SpawnerTuner()
      .addCreature(AquaticaMobsPack.Breed.class, 8, 1, MobReactor.GroupSpawnMode.LAND)
      .addCreature(AquaticaMobsPack.Polipoid.class, 4, 8, MobReactor.GroupSpawnMode.LAND)
      .addCreature(AquaticaMobsPack.Trachymona.class, 3, 9, MobReactor.GroupSpawnMode.LAND)
      .addCreature(AquaticaMobsPack.Hydromona.class, 5, 10, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(4);
   public static SpawnerTuner SUNKENTOWN = new SpawnerTuner()
      .addCreature(AquaticaMobsPack.Mermaid.class, 1, 1, MobReactor.GroupSpawnMode.LAND)
      .setMaximumMobTypes(1);
   public static SpawnerTuner SIRENSANCTUARY = new SpawnerTuner()
      .addCreature(AquaticaMobsPack.Siren.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN)
      .setMaximumMobTypes(1);
   public static SpawnerTuner ZARPION_INTRICACY = new SpawnerTuner()
      .addCreature(StormledgeMobsPack.Zarpion.class, 3, 10, MobReactor.GroupSpawnMode.LAND)
      .addCreature(StormledgeMobsPack.Zarpion.class, 3, 10, MobReactor.GroupSpawnMode.LAND)
      .addCreature(StormledgeMobsPack.Zarpion.class, 3, 10, MobReactor.GroupSpawnMode.LAND)
      .addCreature(StormledgeMobsPack.Gust.class, 1, 6, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(StormledgeMobsPack.Cloudbug.class, 1, 2, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(StormledgeMobsPack.CloudEater.class, 1, 15, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(StormledgeMobsPack.Screenguard.class, 1, 5, MobReactor.GroupSpawnMode.AIRBORN)
      .addCreature(StormledgeMobsPack.Homingbird.class, 1, 1, MobReactor.GroupSpawnMode.AIRBORN)
      .setMobsCountPerSpawn(2.0F)
      .setMaximumMobTypes(3);
}

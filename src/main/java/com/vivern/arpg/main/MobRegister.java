package com.vivern.arpg.main;

import com.vivern.arpg.mobs.AquaticaMobsPack;
import com.vivern.arpg.mobs.BossSpine;
import com.vivern.arpg.mobs.BossSpineMinion;
import com.vivern.arpg.mobs.BossSpineSegment;
import com.vivern.arpg.mobs.DungeonMobsPack;
import com.vivern.arpg.mobs.EverfrostMobsPack;
import com.vivern.arpg.mobs.Gnater;
import com.vivern.arpg.mobs.Moonshroom;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.mobs.NexusCap;
import com.vivern.arpg.mobs.OtherMobsPack;
import com.vivern.arpg.mobs.SmokeDemon;
import com.vivern.arpg.mobs.SnowClod;
import com.vivern.arpg.mobs.StormledgeMobsPack;
import com.vivern.arpg.mobs.SummonedBlaze;
import com.vivern.arpg.mobs.SummonedSnowman;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import com.vivern.arpg.mobs.Troglodyte;
import com.vivern.arpg.mobs.WhiteSlime;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@EventBusSubscriber(
   modid = "arpg"
)
public class MobRegister {
   public static List<EntityEntry> toregister = new ArrayList<>();
   public static int startid = 319;
   public static EntityEntry SUMMONED_BLAZE = EntityEntryBuilder.create()
      .entity(SummonedBlaze.class)
      .name("Summoned Blaze")
      .id("summoned_blaze", 300)
      .egg(16763648, 5509376)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry GNATER = EntityEntryBuilder.create()
      .entity(Gnater.class)
      .name("Gnater")
      .id("gnater", 301)
      .egg(5456137, 8853769)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry WHITE_SLIME = EntityEntryBuilder.create()
      .entity(WhiteSlime.class)
      .name("White Slime")
      .id("white_slime", 302)
      .egg(12111579, 6453159)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry TROGLODYTE = EntityEntryBuilder.create()
      .entity(Troglodyte.class)
      .name("Troglodyte")
      .id("troglodyte", 303)
      .egg(7244390, 2042901)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry MOONSHROOM = EntityEntryBuilder.create()
      .entity(Moonshroom.class)
      .name("Moonshroom")
      .id("moonshroom", 304)
      .egg(16184831, 7911152)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry SMOKEDEMON = EntityEntryBuilder.create()
      .entity(SmokeDemon.class)
      .name("Smoke Demon")
      .id("smoke_demon", 310)
      .egg(1972736, 16310616)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry SUMMONED_SNOWMAN = EntityEntryBuilder.create()
      .entity(SummonedSnowman.class)
      .name("Summoned Snowman")
      .id("summoned_snowman", 311)
      .egg(8830207, 11053491)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry BOSS_SPINE = EntityEntryBuilder.create()
      .entity(BossSpine.class)
      .name("Spine")
      .id("spine", 312)
      .egg(12506599, 13164015)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry BOSS_SPINE_SEGMENT = EntityEntryBuilder.create()
      .entity(BossSpineSegment.class)
      .name("Spine Segment")
      .id("spine_segment", 313)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry BOSS_SPINE_MINION = EntityEntryBuilder.create()
      .entity(BossSpineMinion.class)
      .name("Spine Minion")
      .id("spine_minion", 314)
      .egg(12506599, 11914986)
      .tracker(64, 1, true)
      .build();
   public static EntityEntry NEXUS_CAP = EntityEntryBuilder.create()
      .entity(NexusCap.class)
      .name("Nexus Cap")
      .id("nexus_cap", 317)
      .egg(0, 0)
      .tracker(64, 40, true)
      .build();
   public static EntityEntry SNOWCLOD = EntityEntryBuilder.create()
      .entity(SnowClod.class)
      .name("Snow Clod")
      .id("snow_clod", 318)
      .egg(14544625, 2171953)
      .tracker(64, 1, true)
      .build();

   @SubscribeEvent
   public static void registerMobs(Register<EntityEntry> event) {
      ToxicomaniaMobsPack.init();
      NPCMobsPack.init();
      EverfrostMobsPack.init();
      StormledgeMobsPack.init();
      AquaticaMobsPack.init();
      OtherMobsPack.init();
      DungeonMobsPack.init();
      event.getRegistry()
         .registerAll(
            new EntityEntry[]{
               SUMMONED_BLAZE,
               GNATER,
               WHITE_SLIME,
               TROGLODYTE,
               MOONSHROOM,
               SMOKEDEMON,
               SUMMONED_SNOWMAN,
               BOSS_SPINE,
               BOSS_SPINE_SEGMENT,
               BOSS_SPINE_MINION,
               SNOWCLOD,
               NEXUS_CAP
            }
         );

      for (EntityEntry entry : toregister) {
         event.getRegistry().register(entry);
      }
   }
}

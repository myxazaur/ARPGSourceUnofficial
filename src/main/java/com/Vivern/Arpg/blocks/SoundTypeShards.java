package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Sounds;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

public class SoundTypeShards extends SoundType {
   public static final SoundType SHARDS = new SoundTypeShards(
      1.0F, 1.0F, Sounds.st_shards_break, Sounds.st_shards_step, Sounds.st_shards_place, Sounds.st_shards_hit, Sounds.st_shards_fall
   );

   public SoundTypeShards(
      float volumeIn, float pitchIn, SoundEvent breakSoundIn, SoundEvent stepSoundIn, SoundEvent placeSoundIn, SoundEvent hitSoundIn, SoundEvent fallSoundIn
   ) {
      super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
   }
}

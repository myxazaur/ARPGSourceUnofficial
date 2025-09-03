package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Sounds;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

public class SoundTypeCrunchy extends SoundType {
   public static final SoundType CRUNCHY = new SoundType(
      1.0F, 1.0F, Sounds.st_crunchy_break, Sounds.st_crunchy_step, Sounds.st_crunchy_place, Sounds.st_crunchy_hit, Sounds.st_crunchy_fall
   );

   public SoundTypeCrunchy(
      float volumeIn, float pitchIn, SoundEvent breakSoundIn, SoundEvent stepSoundIn, SoundEvent placeSoundIn, SoundEvent hitSoundIn, SoundEvent fallSoundIn
   ) {
      super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
   }
}

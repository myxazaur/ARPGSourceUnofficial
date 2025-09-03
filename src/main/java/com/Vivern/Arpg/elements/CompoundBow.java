package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Sounds;
import net.minecraft.util.SoundEvent;

public class CompoundBow extends AbstractBow {
   public CompoundBow() {
      super("compound_bow", 500, 4.2F, 0.5F, 28, 10, 4.0F, 1.0F);
      this.pullSoundPitch = 0.95F;
   }

   @Override
   public SoundEvent getShootSound() {
      return Sounds.compound_bow;
   }
}

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Sounds;
import net.minecraft.util.SoundEvent;

public class MithrilBow extends AbstractBow {
   public MithrilBow() {
      super("mithril_bow", 1800, 3.4F, 0.8F, 20, 4, 6.0F, 1.5F);
   }

   @Override
   public SoundEvent getShootSound() {
      return Sounds.mithril_bow;
   }
}

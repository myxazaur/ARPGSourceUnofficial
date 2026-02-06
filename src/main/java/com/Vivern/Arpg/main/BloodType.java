package com.Vivern.Arpg.main;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class BloodType {
   public float red;
   public float green;
   public float blue;
   public SoundEvent dismemberSound;
   public SoundEvent cutSound;
   public boolean isMechanical;
   public boolean hasBlood;
   public boolean isGlowing;
   public boolean isAdditiveBlend;

   public BloodType(double red, double green, double blue, SoundEvent dismemberSound, SoundEvent cutSound, boolean isMechanical, boolean hasBlood) {
      this.red = (float)red;
      this.green = (float)green;
      this.blue = (float)blue;
      this.dismemberSound = dismemberSound;
      this.cutSound = cutSound;
      this.isMechanical = isMechanical;
      this.hasBlood = hasBlood;
   }

   public BloodType(double red, double green, double blue, boolean isMechanical, boolean hasBlood) {
      this(red, green, blue, Sounds.de_dismemberment, Sounds.de_cut, isMechanical, hasBlood);
   }

   public BloodType(double red, double green, double blue) {
      this(red, green, blue, Sounds.de_dismemberment, Sounds.de_cut, false, true);
   }

   public BloodType(int decimalColor, SoundEvent dismemberSound, SoundEvent cutSound, boolean isMechanical, boolean hasBlood) {
      Vec3d vec = ColorConverters.DecimaltoRGB(decimalColor);
      this.red = (float)vec.x;
      this.green = (float)vec.y;
      this.blue = (float)vec.z;
      this.dismemberSound = dismemberSound;
      this.cutSound = cutSound;
      this.isMechanical = isMechanical;
      this.hasBlood = hasBlood;
   }

   public BloodType(int decimalColor, boolean isMechanical, boolean hasBlood) {
      this(decimalColor, Sounds.de_dismemberment, Sounds.de_cut, isMechanical, hasBlood);
   }

   public BloodType(int decimalColor) {
      this(decimalColor, Sounds.de_dismemberment, Sounds.de_cut, false, true);
   }

   public BloodType setGlowing() {
      this.isGlowing = true;
      return this;
   }

   public BloodType setAdditiveBlend() {
      this.isAdditiveBlend = true;
      return this;
   }
}

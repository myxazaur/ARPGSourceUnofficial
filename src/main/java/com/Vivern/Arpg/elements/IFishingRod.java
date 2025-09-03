package com.Vivern.Arpg.elements;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;

public interface IFishingRod {
   List<Block> allowedLiquids = new ArrayList<>();
   float length = 10.0F;
   float controllability = 1.0F;
   float roundRadius = 1.0F;
   float luck = 1.0F;

   default float getFlength() {
      return 10.0F;
   }

   default float getFcontrollability() {
      return 1.0F;
   }

   default float getFroundRadius() {
      return 1.0F;
   }

   default float getFluck() {
      return 1.0F;
   }
}

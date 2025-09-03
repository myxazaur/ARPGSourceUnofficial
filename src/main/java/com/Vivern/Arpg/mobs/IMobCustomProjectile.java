package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.CustomMobProjectile;
import net.minecraft.util.math.RayTraceResult;

public interface IMobCustomProjectile {
   void onImpact(CustomMobProjectile var1, RayTraceResult var2);

   default void onUpdate(CustomMobProjectile entity) {
   }

   default void onImpactClient(CustomMobProjectile entity) {
   }

   default void onUpdateClient(CustomMobProjectile entity) {
   }
}

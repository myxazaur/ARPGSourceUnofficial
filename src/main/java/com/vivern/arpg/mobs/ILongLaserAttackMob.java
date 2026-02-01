package com.vivern.arpg.mobs;

import net.minecraft.util.math.Vec3d;

public interface ILongLaserAttackMob {
   default void onLaserTick(Vec3d hitPos, int attackDuration) {
   }
}

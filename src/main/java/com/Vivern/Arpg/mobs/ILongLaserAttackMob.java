package com.Vivern.Arpg.mobs;

import net.minecraft.util.math.Vec3d;

public interface ILongLaserAttackMob {
   default void onLaserTick(Vec3d hitPos, int attackDuration) {
   }
}

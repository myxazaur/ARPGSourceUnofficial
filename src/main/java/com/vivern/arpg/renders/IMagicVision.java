package com.vivern.arpg.renders;

import com.vivern.arpg.main.ShardType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMagicVision {
   float getElementEnergy(ShardType var1);

   float getMana();

   default float getManaStorageSize(World world, BlockPos pos) {
      return 0.0F;
   }
}

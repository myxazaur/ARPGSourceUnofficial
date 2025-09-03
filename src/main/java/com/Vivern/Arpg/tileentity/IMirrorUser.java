package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.recipes.Seal;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.util.math.BlockPos;

@Deprecated
public interface IMirrorUser {
   ArrayList<BlockPos> getMirrors();

   boolean hasSeals(HashMap<Seal, Integer> var1);

   void spendSeals();

   void tryAddMirrorPos(BlockPos var1);
}

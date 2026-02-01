package com.vivern.arpg.tileentity;

import com.vivern.arpg.recipes.Seal;
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

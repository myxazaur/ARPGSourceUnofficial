package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.GetMOP;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ModulePlaceMultiblock extends Module {
   public ArrayList<BlockAtPos> blocks = new ArrayList<>();
   public boolean rotateRandom;

   public ModulePlaceMultiblock(ModularStructureGenerator generator, boolean rotateRandom) {
      super(generator);
      this.canBeQueued = false;
      this.canDebug = false;
      this.rotateRandom = rotateRandom;
   }

   public ModulePlaceMultiblock add(IBlockState state, int x, int y, int z) {
      this.blocks.add(new BlockAtPos(x, y, z, state));
      return this;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         if (this.rotateRandom) {
            Rotation rotation = Rotation.values()[this.generator.rand.nextInt(4)];

            for (BlockAtPos blockAtPos : this.blocks) {
               Vec3d vec = new Vec3d(blockAtPos);
               Vec3d rotated = GetMOP.rotateVecAroundAxis(vec, new Vec3d(facing.getXOffset(), facing.getYOffset(), facing.getZOffset()), rotation);
               this.generator.setBlockState(sourcePos.add(rotated.x, rotated.y, rotated.z), blockAtPos.state, 2);
            }
         } else {
            for (BlockAtPos blockAtPos : this.blocks) {
               this.generator.setBlockState(sourcePos.add(blockAtPos), blockAtPos.state, 2);
            }
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }
}

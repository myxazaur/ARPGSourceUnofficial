package com.Vivern.Arpg.dimensions.generationutils;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ModulePlaceBlock extends Module {
   public IBlockState iblockstate;
   @Nullable
   public IOnModulePlaceBlock interfaceOn;

   public ModulePlaceBlock(ModularStructureGenerator generator, IBlockState iblockstate) {
      super(generator);
      this.iblockstate = iblockstate;
      this.canBeQueued = false;
      this.canDebug = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (!this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
         this.generator.setBlockState(sourcePos, this.iblockstate, 2);
         if (this.interfaceOn != null) {
            this.interfaceOn.onSetBlockState(sourcePos, this.iblockstate, facing, sourceModule, age);
         }

         this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
      }
   }

   public interface IOnModulePlaceBlock {
      void onSetBlockState(BlockPos var1, IBlockState var2, EnumFacing var3, Module var4, int var5);
   }
}

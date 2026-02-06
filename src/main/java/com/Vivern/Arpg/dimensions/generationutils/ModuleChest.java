package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class ModuleChest extends Module {
   public Block chest;
   public ResourceLocation lootTable;
   public float chestChance;
   public boolean checkNearWall;

   public ModuleChest(ModularStructureGenerator generator, Block chest, ResourceLocation lootTable, float chestChance, boolean checkNearWall) {
      super(generator);
      this.chest = chest;
      this.lootTable = lootTable;
      this.chestChance = chestChance;
      this.checkNearWall = checkNearWall;
      this.canBeQueued = false;
      this.canDebug = false;
   }

   @Override
   public void generate(BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (this.generator.rand.nextFloat() < this.chestChance) {
         if (this.generator.cannotGenerate(this, sourcePos, facing, sourceModule, age)) {
            return;
         }

         boolean continuee = !this.checkNearWall;
         if (this.checkNearWall) {
            for (EnumFacing facing2 : EnumFacing.HORIZONTALS) {
               if (!this.generator.isAirBlock(sourcePos.offset(facing2))) {
                  continuee = true;
                  break;
               }
            }
         }

         for (EnumFacing facing2x : EnumFacing.HORIZONTALS) {
            if (this.generator.getBlockState(sourcePos.offset(facing2x)).getBlock() == this.chest) {
               continuee = false;
               break;
            }
         }

         if (continuee) {
            this.generator.setBlockState(sourcePos, this.chest.getDefaultState().withRotation(Rotation.values()[facing.getHorizontalIndex()]), 2);
            TileEntity tileentity = this.generator.getAccess().getTileEntity(sourcePos);
            if (tileentity instanceof TileEntityLockableLoot) {
               ((TileEntityLockableLoot)tileentity).setLootTable(this.lootTable, this.generator.rand.nextLong());
            }

            this.generator.onEndGenerate(this, sourcePos, facing, sourceModule, age);
         }
      }
   }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import java.util.ArrayDeque;
import java.util.Queue;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlocksDecayer extends Entity {
   private Queue<BlockPos> decayingLogs = new ArrayDeque<>();
   public int iterationsPerTick = 1;

   public BlocksDecayer(World worldIn) {
      super(worldIn);
   }

   public void addDecayPos(BlockPos pos) {
      this.decayingLogs.add(pos);
   }

   public void onUpdate() {
      super.onUpdate();

      for (int i = 0; i < this.iterationsPerTick; i++) {
         BlockPos pos = this.decayingLogs.poll();
         if (pos == null) {
            this.setDead();
            break;
         }

         if (this.world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
               IBlockState iblockstate = this.world.getBlockState(blockpos);
               if (iblockstate.getBlock().isLeaves(iblockstate, this.world, blockpos)) {
                  iblockstate.getBlock().updateTick(this.world, blockpos, iblockstate, this.rand);
               }
            }
         }
      }
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}

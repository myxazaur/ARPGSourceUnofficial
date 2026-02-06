package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.BlockBunkerDoor;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Weapons;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class TileBunkerDoor extends TileEntity implements ITickable {
   public boolean started = false;
   public boolean triggeredByOtherDoor = false;
   public ItemStack keyCard = ItemStack.EMPTY;
   public BlockPos relativeCurrentPos = BlockPos.ORIGIN;
   public int ticks = 0;

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setBoolean("started", this.started);
      compound.setBoolean("triggeredByOtherDoor", this.triggeredByOtherDoor);
      NBTTagCompound tag = new NBTTagCompound();
      this.keyCard.writeToNBT(tag);
      compound.setTag("keycard", tag);
      if (this.relativeCurrentPos != null) {
         compound.setInteger("relativeposX", this.relativeCurrentPos.getX());
         compound.setInteger("relativeposY", this.relativeCurrentPos.getY());
         compound.setInteger("relativeposZ", this.relativeCurrentPos.getZ());
      }

      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("started")) {
         this.started = compound.getBoolean("started");
      }

      if (compound.hasKey("triggeredByOtherDoor")) {
         this.triggeredByOtherDoor = compound.getBoolean("triggeredByOtherDoor");
      }

      if (compound.hasKey("keycard")) {
         this.keyCard = new ItemStack(compound.getCompoundTag("keycard"));
      }

      if (compound.hasKey("relativeposX") && compound.hasKey("relativeposY") && compound.hasKey("relativeposZ")) {
         this.relativeCurrentPos = new BlockPos(
            compound.getInteger("relativeposX"), compound.getInteger("relativeposY"), compound.getInteger("relativeposZ")
         );
      }

      super.readFromNBT(compound);
   }

   public void resetAndStart(boolean triggeredByOtherDoor) {
      EnumFacing face = (EnumFacing)this.world.getBlockState(this.pos).getValue(BlockBunkerDoor.FACING);
      this.relativeCurrentPos = BlockPos.ORIGIN.offset(face);
      this.started = true;
      this.ticks = 0;
      this.triggeredByOtherDoor = triggeredByOtherDoor;
   }

   public void update() {
      if (this.started) {
         this.ticks++;
         if (this.ticks > 15) {
            this.ticks = 0;
            ArrayList<BlockPos> posesFromMove = new ArrayList<>();
            ArrayList<BlockPos> posesToMove = new ArrayList<>();
            EnumFacing face = (EnumFacing)this.world.getBlockState(this.pos).getValue(BlockBunkerDoor.FACING);
            BlockPos currentPos = this.pos.add(this.relativeCurrentPos);
            if (!this.world.getBlockState(currentPos.offset(face)).isOpaqueCube()) {
               this.started = false;
               return;
            }

            this.initPoses(posesFromMove, currentPos, face.getAxis());
            this.initPoses(posesToMove, currentPos.offset(face), face.getAxis());

            for (BlockPos poss : posesToMove) {
               if (!this.world.getBlockState(poss).getBlock().isReplaceable(this.world, poss) || this.world.isOutsideBuildHeight(poss)
                  )
                {
                  this.started = false;
                  return;
               }
            }

            boolean allBlocksAir = true;

            for (BlockPos possx : posesFromMove) {
               IBlockState blockst = this.world.getBlockState(possx);
               if (blockst.getBlock() != Blocks.AIR) {
                  allBlocksAir = false;
               }

               if (this.isAccessBlock(blockst, possx)) {
                  this.world.setBlockToAir(possx);
                  this.world.setBlockState(possx.offset(face), blockst);
               }
            }

            if (allBlocksAir) {
               this.started = false;
               if (!this.triggeredByOtherDoor) {
                  for (int i = 2; i < 32; i++) {
                     BlockPos findPos = this.pos.offset(face, i);
                     if (!this.world.getBlockState(findPos).isOpaqueCube()) {
                        break;
                     }

                     if (this.world.getBlockState(findPos).getBlock() == BlocksRegister.BUNKERDOOR) {
                        TileEntity tileentity = this.world.getTileEntity(findPos);
                        if (tileentity != null && tileentity instanceof TileBunkerDoor) {
                           TileBunkerDoor door = (TileBunkerDoor)tileentity;
                           if (!door.started) {
                              door.resetAndStart(true);
                           }
                           break;
                        }
                     }
                  }
               }
            }

            this.relativeCurrentPos = this.relativeCurrentPos.offset(face);
            if (this.relativeCurrentPos.getX() >= 32 || this.relativeCurrentPos.getY() >= 32 || this.relativeCurrentPos.getZ() >= 32
               )
             {
               this.started = false;
            }
         }
      }
   }

   public boolean isAccessBlock(IBlockState block, BlockPos pos) {
      return block.getBlock() != BlocksRegister.RUSTMETALL
            && block.getBlock() != BlocksRegister.RUSTEDPIPE
            && block.getBlock() != BlocksRegister.RUSTARMATURE
            && block.getBlock() != BlocksRegister.RUSTLAMP
            && block.getBlock() != BlocksRegister.DARKRUSTMETALL
            && block.getBlock() != BlocksRegister.LABPLATING
            && block.getBlock() != BlocksRegister.SCRAPELECTRONICS
            && block.getBlock() != Blocks.IRON_BLOCK
            && block.getBlock() != Blocks.IRON_BARS
         ? Weapons.easyBreakBlockFor(this.world, 12.0F, pos, block)
         : true;
   }

   public void initPoses(ArrayList<BlockPos> poses, BlockPos center, Axis axis) {
      if (axis == Axis.X) {
         poses.add(new BlockPos(center.add(0, 1, 0)));
         poses.add(new BlockPos(center.add(0, 1, 1)));
         poses.add(new BlockPos(center.add(0, 0, 1)));
         poses.add(new BlockPos(center.add(0, -1, 1)));
         poses.add(new BlockPos(center.add(0, -1, 0)));
         poses.add(new BlockPos(center.add(0, -1, -1)));
         poses.add(new BlockPos(center.add(0, 0, -1)));
         poses.add(new BlockPos(center.add(0, 1, -1)));
      } else if (axis == Axis.Z) {
         poses.add(new BlockPos(center.add(0, 1, 0)));
         poses.add(new BlockPos(center.add(1, 1, 0)));
         poses.add(new BlockPos(center.add(1, 0, 0)));
         poses.add(new BlockPos(center.add(1, -1, 0)));
         poses.add(new BlockPos(center.add(0, -1, 0)));
         poses.add(new BlockPos(center.add(-1, -1, 0)));
         poses.add(new BlockPos(center.add(-1, 0, 0)));
         poses.add(new BlockPos(center.add(-1, 1, 0)));
      } else if (axis == Axis.Y) {
         poses.add(new BlockPos(center.add(0, 0, 1)));
         poses.add(new BlockPos(center.add(1, 0, 1)));
         poses.add(new BlockPos(center.add(1, 0, 0)));
         poses.add(new BlockPos(center.add(1, 0, -1)));
         poses.add(new BlockPos(center.add(0, 0, -1)));
         poses.add(new BlockPos(center.add(-1, 0, -1)));
         poses.add(new BlockPos(center.add(-1, 0, 0)));
         poses.add(new BlockPos(center.add(-1, 0, 1)));
      }
   }
}

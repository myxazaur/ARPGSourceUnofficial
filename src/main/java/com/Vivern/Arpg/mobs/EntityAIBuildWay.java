//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EntityAIBuildWay extends EntityAIBase {
   public AbstractMob entity;
   public int maxCooldown = 30;
   public boolean useMaxBlocks = false;
   public IBlockState block;
   public int cooldownTimer = 0;
   public List<BlockPos> way = new ArrayList<>();

   public EntityAIBuildWay(AbstractMob entity, int maxCooldown, boolean useMaxBlocks, IBlockState block) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.useMaxBlocks = useMaxBlocks;
      this.block = block;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && (!this.useMaxBlocks || this.entity.var1 > 0);
   }

   public boolean shouldContinueExecuting() {
      return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.cooldownTimer--;
      if (attackTarg != null && this.entity.getNavigator().noPath() && this.cooldownTimer <= 0 && (!this.useMaxBlocks || this.entity.var1 > 0)) {
         if (Math.abs(this.entity.posY - attackTarg.posY) <= 1.5) {
            BlockPos center = new BlockPos(this.entity.posX, this.entity.posY - 0.9, this.entity.posZ);
            double maxDist = 9999999.0;
            BlockPos finalpos = null;

            for (EnumFacing face : EnumFacing.HORIZONTALS) {
               BlockPos offpos = center.offset(face);
               double d0 = offpos.getX() - attackTarg.posX;
               double d2 = offpos.getZ() - attackTarg.posZ;
               double distt = d0 * d0 + d2 * d2;
               if (maxDist > distt
                  && this.checknoInList(offpos)
                  && this.entity.world.getBlockState(offpos).getBlock().isReplaceable(this.entity.world, offpos)) {
                  maxDist = distt;
                  finalpos = offpos;
               }
            }

            if (finalpos != null) {
               this.entity.world.setBlockState(finalpos, this.block);
               this.entity
                  .playSound(this.block.getBlock().getSoundType().getPlaceSound(), 1.0F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
               boolean succ = this.entity.getNavigator().tryMoveToXYZ(finalpos.getX(), finalpos.getY() + 1.1, finalpos.getZ(), 1.0);
               this.cooldownTimer = this.maxCooldown;
               if (succ) {
                  this.cooldownTimer *= 2;
               }

               if (this.useMaxBlocks) {
                  this.entity.var1--;
               }

               this.way.add(finalpos.up());
            }
         } else if (this.entity.posY < attackTarg.posY) {
            BlockPos center = new BlockPos(this.entity.posX, this.entity.posY + 0.4, this.entity.posZ);
            double maxDist = 9999999.0;
            BlockPos finalpos = null;

            for (EnumFacing facex : EnumFacing.HORIZONTALS) {
               BlockPos offpos = center.offset(facex);
               double d0 = offpos.getX() - attackTarg.posX;
               double d1 = offpos.getY() - attackTarg.posY;
               double d2 = offpos.getZ() - attackTarg.posZ;
               double distt = d0 * d0 + d1 * d1 + d2 * d2;
               if (maxDist > distt
                  && this.checknoInList(offpos)
                  && this.entity.world.getBlockState(offpos).getBlock().isReplaceable(this.entity.world, offpos)) {
                  maxDist = distt;
                  finalpos = offpos;
               }
            }

            if (finalpos != null) {
               this.entity.world.setBlockState(finalpos, this.block);
               this.entity
                  .playSound(this.block.getBlock().getSoundType().getPlaceSound(), 1.0F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
               if (this.checknoInList(finalpos.down())
                  && this.entity
                     .world
                     .getBlockState(finalpos.down())
                     .getBlock()
                     .isReplaceable(this.entity.world, finalpos.down())) {
                  this.entity.world.setBlockState(finalpos.down(), this.block);
               }

               boolean succx = this.entity
                  .getNavigator()
                  .tryMoveToXYZ(finalpos.getX(), finalpos.getY() + 1.1, finalpos.getZ(), 1.0);
               this.cooldownTimer = this.maxCooldown;
               if (succx) {
                  this.cooldownTimer *= 2;
               }

               if (this.useMaxBlocks) {
                  this.entity.var1--;
               }

               this.way.add(finalpos.up(1));
               this.way.add(finalpos.up(2));
               this.way.add(center.up(2));
            }
         }
      }
   }

   public boolean checknoInList(BlockPos pos) {
      for (BlockPos poss : this.way) {
         if (poss.equals(pos)) {
            return false;
         }
      }

      return true;
   }
}

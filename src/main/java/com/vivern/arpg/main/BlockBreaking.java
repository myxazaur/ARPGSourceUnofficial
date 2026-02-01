package com.vivern.arpg.main;

import com.vivern.arpg.blocks.IBlockHardBreak;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockBreaking {
   public static HashMap<WorldServer, BlockBreaking> map = new HashMap<>();
   public static int maxBreakProgressesPossible = 128;
   public int entityIdStart = Integer.MAX_VALUE - maxBreakProgressesPossible;
   public WorldServer world;
   public ArrayList<BreakingProgress> list = new ArrayList<>();
   public static ItemStack ghostPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
   public static ItemStack ghostAxe = new ItemStack(Items.DIAMOND_AXE);
   public static ItemStack ghostShovel = new ItemStack(Items.DIAMOND_SHOVEL);
   public static ItemStack ghostShears = new ItemStack(Items.SHEARS);

   public static BlockBreaking getBlockBreaking(WorldServer world) {
      BlockBreaking blockBreaking = map.get(world);
      if (blockBreaking == null) {
         blockBreaking = new BlockBreaking(world);
         map.put(world, blockBreaking);
      }

      return blockBreaking;
   }

   public BlockBreaking(WorldServer world) {
      this.world = world;
   }

   public int damageMultiblock(
      IMultiblockBreakHandler handler,
      @Nullable EntityPlayer player,
      BlockPos pos,
      float hardnessAmount,
      String tool,
      int toolLevel,
      int fortune,
      boolean withSilkTouch
   ) {
      if (!this.list.isEmpty()) {
         for (int i = 0; i < this.list.size(); i++) {
            BreakingProgress breakingProgress = this.list.get(i);
            if (pos.equals(breakingProgress.pos)) {
               int ret = breakingProgress.damageMultiblock(handler, player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
               if (ret > 0) {
                  this.list.remove(i);
                  this.world.sendBlockBreakProgress(breakingProgress.entityId, breakingProgress.pos, 0);
               }

               return ret;
            }
         }
      }

      this.entityIdStart++;
      if (this.entityIdStart == Integer.MAX_VALUE) {
         this.entityIdStart = Integer.MAX_VALUE - maxBreakProgressesPossible;
      }

      if (!this.list.isEmpty()) {
         for (int ix = 0; ix < this.list.size(); ix++) {
            BreakingProgress inlist = this.list.get(ix);
            if (inlist.entityId == this.entityIdStart) {
               inlist.pos = pos;
               inlist.damage = 0.0F;
               int ret = inlist.damageMultiblock(handler, player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
               if (ret > 0) {
                  this.list.remove(ix);
                  this.world.sendBlockBreakProgress(inlist.entityId, inlist.pos, 0);
               }

               return ret;
            }
         }
      }

      BreakingProgress breakingProgress = new BreakingProgress(pos, this.entityIdStart);
      int ret = breakingProgress.damageMultiblock(handler, player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
      if (ret <= 0) {
         this.list.add(breakingProgress);
      }

      return ret;
   }

   public boolean damageBlock(@Nullable EntityPlayer player, BlockPos pos, float hardnessAmount, String tool, int toolLevel, int fortune, boolean withSilkTouch) {
      if (!this.list.isEmpty()) {
         for (int i = 0; i < this.list.size(); i++) {
            BreakingProgress breakingProgress = this.list.get(i);
            if (pos.equals(breakingProgress.pos)) {
               boolean ret = breakingProgress.damageBlock(player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
               if (ret) {
                  this.list.remove(i);
                  this.world.sendBlockBreakProgress(breakingProgress.entityId, breakingProgress.pos, 0);
               }

               return ret;
            }
         }
      }

      this.entityIdStart++;
      if (this.entityIdStart == Integer.MAX_VALUE) {
         this.entityIdStart = Integer.MAX_VALUE - maxBreakProgressesPossible;
      }

      if (!this.list.isEmpty()) {
         for (int ix = 0; ix < this.list.size(); ix++) {
            BreakingProgress inlist = this.list.get(ix);
            if (inlist.entityId == this.entityIdStart) {
               inlist.pos = pos;
               inlist.damage = 0.0F;
               boolean ret = inlist.damageBlock(player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
               if (ret) {
                  this.list.remove(ix);
                  this.world.sendBlockBreakProgress(inlist.entityId, inlist.pos, 0);
               }

               return ret;
            }
         }
      }

      BreakingProgress breakingProgress = new BreakingProgress(pos, this.entityIdStart);
      boolean ret = breakingProgress.damageBlock(player, this.world, hardnessAmount, tool, toolLevel, fortune, withSilkTouch);
      if (!ret) {
         this.list.add(breakingProgress);
      }

      return ret;
   }

   public static boolean isToolEffective(String tool, IBlockState state) {
      if ("pickaxe".equals(tool)) {
         return Items.DIAMOND_PICKAXE.getDestroySpeed(ghostPickaxe, state) > 1.0F;
      } else if ("axe".equals(tool)) {
         return Items.DIAMOND_AXE.getDestroySpeed(ghostAxe, state) > 1.0F;
      } else if ("shovel".equals(tool)) {
         return Items.DIAMOND_SHOVEL.getDestroySpeed(ghostShovel, state) > 1.0F;
      } else {
         return "shears".equals(tool) ? Items.SHEARS.getDestroySpeed(ghostShears, state) > 1.0F : false;
      }
   }

   public static String getMostEffectiveTool(IBlockState state) {
      String tool = state.getBlock().getHarvestTool(state);
      if (tool == null) {
         float highestSpeed = Float.MIN_VALUE;
         tool = "pickaxe";
         float speed = Items.DIAMOND_PICKAXE.getDestroySpeed(ghostPickaxe, state);
         if (speed > highestSpeed) {
            highestSpeed = speed;
            tool = "pickaxe";
         }

         float speed2 = Items.DIAMOND_AXE.getDestroySpeed(ghostAxe, state);
         if (speed2 > highestSpeed) {
            highestSpeed = speed2;
            tool = "axe";
         }

         float speed3 = Items.DIAMOND_SHOVEL.getDestroySpeed(ghostShovel, state);
         if (speed3 > highestSpeed) {
            highestSpeed = speed3;
            tool = "shovel";
         }

         float speed4 = Items.SHEARS.getDestroySpeed(ghostShears, state);
         if (speed4 > highestSpeed) {
            tool = "shears";
         }
      }

      return tool;
   }

   public class BreakingProgress {
      public BlockPos pos;
      public float damage;
      public int entityId;

      public BreakingProgress(BlockPos pos, int entityId) {
         this.pos = pos;
         this.entityId = entityId;
      }

      public int damageToProgress(float blockdamage, float maxBlockdamage) {
         return MathHelper.clamp(Math.round(blockdamage / maxBlockdamage * 10.0F), 0, 9);
      }

      public boolean damageBlock(
         @Nullable EntityPlayer player, WorldServer world, float hardnessAmount, String tool, int toolLevel, int fortune, boolean withSilkTouch
      ) {
         IBlockState blockState = world.getBlockState(this.pos);
         float hardness = blockState.getBlockHardness(world, this.pos);
         int blocklvl = blockState.getBlock().getHarvestLevel(blockState);
         if (blockState.getBlock() instanceof IBlockHardBreak) {
            hardnessAmount = ((IBlockHardBreak)blockState.getBlock()).getBlockBreakingSpeed(world, tool, toolLevel, blockState, this.pos, hardnessAmount);
         } else {
            if (toolLevel < blocklvl) {
               hardnessAmount /= 3.3333333F;
            }

            if (!BlockBreaking.isToolEffective(tool, blockState)) {
               hardnessAmount /= 16.0F;
            }
         }

         float lastDamage = this.damage;
         this.damage += hardnessAmount;
         if (this.damage >= hardness) {
            Weapons.destroyBlockAsTool(world, this.pos, player, tool, toolLevel, fortune, withSilkTouch);
            this.damage = 0.0F;
            return true;
         } else {
            if (lastDamage == 0.0F) {
               world.sendBlockBreakProgress(this.entityId, this.pos, this.damageToProgress(this.damage, hardness));
            } else {
               int lastProgress = this.damageToProgress(lastDamage, hardness);
               int newProgress = this.damageToProgress(this.damage, hardness);
               if (lastProgress != newProgress) {
                  world.sendBlockBreakProgress(this.entityId, this.pos, newProgress);
               }
            }

            return false;
         }
      }

      public int damageMultiblock(
         IMultiblockBreakHandler handler,
         @Nullable EntityPlayer player,
         WorldServer world,
         float hardnessAmount,
         String tool,
         int toolLevel,
         int fortune,
         boolean withSilkTouch
      ) {
         IBlockState blockState = world.getBlockState(this.pos);
         float hardness = handler.getDamageToBreak(world, blockState, this.pos);
         int blocklvl = blockState.getBlock().getHarvestLevel(blockState);
         if (blockState.getBlock() instanceof IBlockHardBreak) {
            hardnessAmount = ((IBlockHardBreak)blockState.getBlock()).getBlockBreakingSpeed(world, tool, toolLevel, blockState, this.pos, hardnessAmount);
         } else {
            if (toolLevel < blocklvl) {
               hardnessAmount /= 3.3333333F;
            }

            if (!BlockBreaking.isToolEffective(tool, blockState)) {
               hardnessAmount /= 16.0F;
            }
         }

         float lastDamage = this.damage;
         this.damage += hardnessAmount;
         if (this.damage >= hardness) {
            int destroyed = handler.destroy(world, this.pos, player, tool, toolLevel, fortune, withSilkTouch);
            this.damage = 0.0F;
            return destroyed;
         } else {
            if (lastDamage == 0.0F) {
               world.sendBlockBreakProgress(this.entityId, this.pos, this.damageToProgress(this.damage, hardness));
            } else {
               int lastProgress = this.damageToProgress(lastDamage, hardness);
               int newProgress = this.damageToProgress(this.damage, hardness);
               if (lastProgress != newProgress) {
                  world.sendBlockBreakProgress(this.entityId, this.pos, newProgress);
               }
            }

            return 0;
         }
      }
   }

   public interface IMultiblockBreakHandler {
      float getDamageToBreak(World var1, IBlockState var2, BlockPos var3);

      int destroy(World var1, BlockPos var2, @Nullable EntityPlayer var3, String var4, int var5, int var6, boolean var7);
   }
}

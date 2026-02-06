package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockHardBreak {
   default float getBlockBreakingSpeed(EntityPlayer player, IBlockState state, BlockPos pos, float originalSpeed) {
      ItemStack instrument = player.getHeldItemMainhand();
      Set<String> classes = instrument.getItem().getToolClasses(instrument);
      float modifiedbreakspeed = 0.0F;

      for (String tool : classes) {
         int lvl = instrument.getItem().getHarvestLevel(instrument, tool, player, state);
         float speed = this.getBlockBreakingSpeed(player.world, tool, lvl, state, pos, originalSpeed);
         if (speed > modifiedbreakspeed) {
            modifiedbreakspeed = speed;
         }
      }

      return modifiedbreakspeed;
   }

   default float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return this.getHardres() != null ? this.getHardres().getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed) : originalSpeed;
   }

   default BlocksRegister.Hardres getHardres() {
      return null;
   }
}

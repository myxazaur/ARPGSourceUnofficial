//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockBlockHard extends BlockBlock implements IBlockHardBreak {
   public float slowSpeed;
   public float fastSpeed;
   public boolean canDropWhithoutTool;
   public int level;
   public String tool;

   public BlockBlockHard(
      Material mater, String name, float hardness, float resi, float slowSpeed, float fastSpeed, int level, String tool, boolean canDropWhithoutTool
   ) {
      super(mater, name, hardness, resi);
      this.slowSpeed = slowSpeed;
      this.canDropWhithoutTool = canDropWhithoutTool;
      this.level = level;
      this.tool = tool;
      this.fastSpeed = fastSpeed;
      this.setHarvest(tool, level);
   }

   public BlockBlockHard(Material mater, String name, BlocksRegister.Hardres hardnessResistance, String tool, boolean canDropWhithoutTool) {
      super(mater, name, hardnessResistance.HARDNESS, hardnessResistance.RESISTANCE);
      this.slowSpeed = hardnessResistance.SLOW;
      this.canDropWhithoutTool = canDropWhithoutTool;
      this.level = hardnessResistance.LVL;
      this.tool = tool;
      this.fastSpeed = hardnessResistance.FAST;
      this.setHarvest(tool, this.level);
   }

   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
      player.addStat(StatList.getBlockStats(this));
      player.addExhaustion(0.005F);
      if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
         List<ItemStack> items = new ArrayList<>();
         ItemStack itemstack = this.getSilkTouchDrop(state);
         if (!itemstack.isEmpty()) {
            items.add(itemstack);
         }

         ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0F, true, player);

         for (ItemStack item : items) {
            spawnAsEntity(worldIn, pos, item);
         }
      } else {
         this.harvesters.set(player);
         int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
         if (this.canDropWhithoutTool || stack.getItem().getHarvestLevel(stack, this.tool, player, state) >= this.level) {
            this.dropBlockAsItem(worldIn, pos, state, i);
         }

         this.harvesters.set(null);
      }
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return toolLevel >= this.level && tool.equals(this.tool) ? originalSpeed * this.fastSpeed : originalSpeed * this.slowSpeed;
   }
}

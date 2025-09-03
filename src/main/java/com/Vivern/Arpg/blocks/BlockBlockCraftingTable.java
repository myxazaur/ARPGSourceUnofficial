//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.container.ContainerMyWorkbench;
import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockBlockCraftingTable extends BlockBlockHard {
   public BlockBlockCraftingTable(Material mater, String name, BlocksRegister.Hardres hardnessResistance, String tool, boolean canDropWhithoutTool) {
      super(mater, name, hardnessResistance, tool, canDropWhithoutTool);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         return true;
      } else {
         playerIn.displayGui(new InterfaceCraftingTable(worldIn, pos));
         playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
         return true;
      }
   }

   public static class InterfaceCraftingTable implements IInteractionObject {
      private final World world;
      private final BlockPos position;

      public InterfaceCraftingTable(World worldIn, BlockPos pos) {
         this.world = worldIn;
         this.position = pos;
      }

      public String getName() {
         return "crafting_table";
      }

      public boolean hasCustomName() {
         return false;
      }

      public ITextComponent getDisplayName() {
         return new TextComponentTranslation(Blocks.CRAFTING_TABLE.getTranslationKey() + ".name", new Object[0]);
      }

      public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
         return new ContainerMyWorkbench(playerInventory, this.world, this.position);
      }

      public String getGuiID() {
         return "minecraft:crafting_table";
      }
   }
}

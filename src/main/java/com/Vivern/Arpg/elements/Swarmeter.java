//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.MobSpawn;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Swarmeter extends Item {
   public Swarmeter() {
      this.setRegistryName("swarmeter");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("swarmeter");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
      if (!world.isRemote && player instanceof EntityPlayerMP) {
         int st = Mana.getSwarmTicks(player);
         String message = "Swarm points: " + Mana.getSwarmPoints(player) + " | ticks: " + st + " | SWM: " + Mana.getSwarmsWithoutMiniboss(player);
         MobSpawn spawn = MobSpawn.spawnByDimension.get(player.dimension);
         if (spawn != null && spawn.enableSwarms) {
            message = message + " | ticks%: " + st % spawn.getSwarmFrequency();
         }

         ((EntityPlayerMP)player).sendMessage(new TextComponentString(message));
      }

      return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      tooltip.add("Provides information about swarms. For debug or better understanding");
      super.addInformation(stack, worldIn, tooltip, flagIn);
   }
}
